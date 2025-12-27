package ecoembes.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ecoembes.dto.AssignDumpsterRequestDTO;
import ecoembes.entity.*;
import ecoembes.dao.*;
import ecoembes.external.RecyclingPlantGatewayFactory;
import ecoembes.external.IRecyclingPlantGateway;

@Service
public class RecyclingPlantService {

    @Autowired
    private RecyclingPlantRepository recyclingPlantRepository;
    
    @Autowired
    private AssignmentRepository assignmentRepository;
    
    private final DumpsterService dumpsterService;
    private final AuthService authService;
    private final RecyclingPlantGatewayFactory gatewayFactory;

    public RecyclingPlantService(DumpsterService dumpsterService, AuthService authService, RecyclingPlantGatewayFactory gatewayFactory) {
        this.dumpsterService = dumpsterService;
        this.authService = authService;
        this.gatewayFactory = gatewayFactory;
    }

    public Optional<Integer> getCapacity(int plantId, LocalDate date) {
        Optional<RecyclingPlant> plantOpt = recyclingPlantRepository.findById(plantId);
        if (plantOpt.isEmpty()) return Optional.empty();

        RecyclingPlant foundPlant = plantOpt.get();
        IRecyclingPlantGateway gateway = gatewayFactory.createGateway(foundPlant);
        try {
            int capacity = gateway.getCapacity(foundPlant, date.toString());
            return Optional.of(capacity);
        } catch (Exception e) {
            System.err.println("Failed to get capacity from external plant: " + e.getMessage());
            return Optional.of(0);
        }
    }
    
    public Assignment assignDumpster(AssignDumpsterRequestDTO dto, String token) {
        try {
            Employee employeeFromToken = authService.getEmployeeByToken(token);
            if (employeeFromToken == null) {
                throw new RuntimeException("Invalid token");
            }
            System.out.println("✓ Token válido para empleado: " + employeeFromToken.getEmp_id());
            
            // Usar directamente el empleado del token, no lo pido por parametro, pq el empleado q asigna la planta es el q esta logineado
            Employee emp = employeeFromToken;
            System.out.println("✓ Asignación será realizada por: " + emp.getEmp_id());
            
            RecyclingPlant rp = recyclingPlantRepository.findById(dto.getRp_id())
                    .orElseThrow(() -> new RuntimeException("Plant not found"));
            System.out.println("✓ Planta encontrada: " + rp.getRp_id());
            
            Dumpster d = dumpsterService.findById(dto.getDumpster_id());
            if (d == null) throw new RuntimeException("Dumpster not found");
            System.out.println("✓ Contenedor encontrado: " + d.getDumpster_id());

            if (emp.getList_assignments() == null) emp.setList_assignments(new ArrayList<>());
            if (rp.getList_assignments() == null) rp.setList_assignments(new ArrayList<>());
            if (d.getList_assignments() == null) d.setList_assignments(new ArrayList<>());
            if (d.getList_usage() == null) d.setList_usage(new ArrayList<>());

            LocalDate date = LocalDate.now();

            int capacity = getCapacity(rp.getRp_id(), date)
                    .orElseThrow(() -> new RuntimeException("Plant has no capacity"));
            System.out.println("✓ Capacidad disponible: " + capacity);

            List<Dumpster> dumpstersToAssign = Collections.singletonList(d);
            int totalContainers = dumpsterService.calculateTotalLoad(dumpstersToAssign);
            System.out.println("✓ Carga total: " + totalContainers);

            if (totalContainers > capacity) {
                throw new RuntimeException("Not enough capacity in plant: " + totalContainers + " > " + capacity);
            }

            int assignmentIdInt = Math.abs((int) UUID.randomUUID().getMostSignificantBits());
            Assignment a = new Assignment(assignmentIdInt, date, emp, rp, d);
            System.out.println("✓ Assignment creado:");
            System.out.println("   - ID: " + assignmentIdInt);
            System.out.println("   - Employee: " + emp.getEmp_id());
            System.out.println("   - Plant: " + rp.getRp_id());
            System.out.println("   - Dumpster: " + d.getDumpster_id());
            System.out.println("   - Date: " + date);

            assignmentRepository.save(a);
            dumpsterService.saveAssignment(a);
            System.out.println("✓ Assignment guardado en base de datos");

            try {
                IRecyclingPlantGateway gateway = gatewayFactory.createGateway(rp);
                boolean notified = gateway.notifyAssignment(rp, a);
                System.out.println("✓ Planta notificada exitosamente");
            } catch (Exception ex) {
                System.err.println("⚠ No se pudo notificar a la planta: " + ex.getMessage());
            }

            System.out.println("\n=== ✅ ASIGNACIÓN COMPLETADA CON ÉXITO ===\n");
            return a;

        } catch (Exception e) {
            System.err.println("\n❌ ERROR EN ASIGNACIÓN: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

}
