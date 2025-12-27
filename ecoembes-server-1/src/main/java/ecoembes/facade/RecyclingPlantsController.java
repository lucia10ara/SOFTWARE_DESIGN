package ecoembes.facade;

import java.time.LocalDate;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.format.annotation.DateTimeFormat;

import ecoembes.dto.AssignDumpsterRequestDTO;
import ecoembes.dto.AssignmentNotificationDTO;  
import ecoembes.entity.Assignment; 
import ecoembes.service.RecyclingPlantService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;




@RestController
@RequestMapping("/plants")
@Tag(name = "Recycling Plants Controller", description = "Plant capacity and assignment operations")
public class RecyclingPlantsController {

    private final RecyclingPlantService plantService;

    public RecyclingPlantsController(RecyclingPlantService plantService) {
        this.plantService = plantService;
    }

    // ---------------------------------------------------------
    // GET /plants/{id}/capacity?date=YYYY-MM-DD
    // ---------------------------------------------------------
    @Operation(summary = "Check plant capacity by date")
    @GetMapping("/{id}/capacity")
    public ResponseEntity<Integer> capacity(
            @PathVariable("id") int id,
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {

        Optional<Integer> cap = plantService.getCapacity(id, date);

        if (cap.isEmpty() || cap.get() <= 0) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.ok(cap.get());
    }

    // ---------------------------------------------------------
    // POST /plants/assign
    // ---------------------------------------------------------
    @Operation(summary = "Assign dumpster to recycling plant")
    @PostMapping("/assign")
    public ResponseEntity<AssignmentNotificationDTO> assign(  // 👈 CAMBIO: Tipo de respuesta
            @RequestBody AssignDumpsterRequestDTO dto,
            @RequestHeader(value = "Authorization", required = false) String authHeader) {
        try {
            // Extraer token del header
            String token = null;
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                token = authHeader.substring(7);
            }
            if (token == null || token.isEmpty()) {
                System.err.println("Token missing!");
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
            
            System.out.println("Token recibido: " + token);
            
            // Llamada al servicio
            Assignment a = plantService.assignDumpster(dto, token);
            
            //lo cambio, usar AssignmentNotificationDTO
            AssignmentNotificationDTO response = new AssignmentNotificationDTO(a);
            
            return new ResponseEntity<>(response, HttpStatus.CREATED);
            
        } catch (RuntimeException e) {
            System.err.println("Assignment failed: " + e.getMessage());
            e.printStackTrace();
            if (e.getMessage().contains("Invalid token")) {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
            if (e.getMessage().contains("capacity") || e.getMessage().contains("not found")) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}