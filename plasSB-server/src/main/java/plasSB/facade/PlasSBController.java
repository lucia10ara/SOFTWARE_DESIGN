package plasSB.facade;


import plasSB.dto.AssignmentConfirmationDTO;

import plasSB.dto.AssignmentNotificationDTO;
import plasSB.service.PlasSBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import org.springframework.http.HttpStatus;

import plasSB.service.PlasSBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class PlasSBController {
    
    private final PlasSBService plasSBService;

    // Inyección por constructor (recomendado)
    public PlasSBController(PlasSBService plasSBService) {
        this.plasSBService = plasSBService;
    }

    @GetMapping("/capacity")
    public Integer getCapacity(
        @RequestParam(name = "date", required = false) 
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) 
        LocalDate date
    ) {
        try {
            if (date == null) {
                date = LocalDate.now();
                System.out.println("⚠️ No se proporcionó fecha, usando fecha actual: " + date);
            }
            
            System.out.println("🔍 Consultando capacidad para fecha: " + date);
            Integer capacity = plasSBService.getCapacity(date);
            
            System.out.println("✓ Capacidad calculada: " + capacity);
            return capacity;
            
        } catch (Exception e) {
            System.err.println("❌ ERROR en getCapacity: " + e.getMessage());
            e.printStackTrace();
            return 0;
        }
    }

    @PostMapping("/assignments")
    public ResponseEntity<Map<String, Object>> receiveAssignment(@RequestBody Map<String, Object> assignmentData) {
        try {
        	
            Integer assignmentId = (Integer) assignmentData.get("assignment_id");
            Integer dumpsterId = (Integer) assignmentData.get("dumpster_id");
            Integer plantId = (Integer) assignmentData.get("rp_id");
            String dateStr = (String) assignmentData.get("date");
            LocalDate date = LocalDate.parse(dateStr);
            Integer loadInTons = 1;

            System.out.println("\n📦 Asignación recibida:");
            System.out.println("   Assignment ID: " + assignmentId);
            System.out.println("   Dumpster ID: " + dumpsterId);
            System.out.println("   Plant ID: " + plantId);
            System.out.println("   Date: " + date);

            // ✅ Usamos la instancia inyectada
            boolean accepted = plasSBService.processAssignment(assignmentId, dumpsterId, plantId, date, loadInTons);

            if (accepted) {
                return ResponseEntity.ok(Map.of(
                    "response", "ACCEPTED",
                    "dumpster_id", dumpsterId.toString(),
                    "message", "Assignment processed successfully"
                ));
            } else {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of(
                    "response", "REJECTED",
                    "dumpster_id", dumpsterId.toString(),
                    "message", "Failed to process assignment - no capacity available"
                ));
            }

        } catch (Exception e) {
            System.err.println("❌ Error en endpoint /assignments: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                "response", "ERROR",
                "message", e.getMessage()
            ));
        }
    }
}