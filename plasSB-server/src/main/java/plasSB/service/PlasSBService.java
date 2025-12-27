package plasSB.service;


import plasSB.dao.*;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;

import plasSB.dao.AssignmentRepository;
import plasSB.entity.Assignment;

@Service
public class PlasSBService {

    @Autowired
    private CapacityRepository capacityRepository;
    
    @Autowired
    private AssignmentRepository assignmentRepository; 
    
    
    //Obtiene la capacidad TOTAL configurada para una fecha

    public int getTotalCapacity(LocalDate date) {
        return capacityRepository.findByDate(date)
                .map(capacityRecord -> capacityRecord.getCapacityInTons())
                .orElse(0);
    }
   
    
    //Obtiene la capacidad DISPONIBLE (total - usada)
     
    public int getAvailableCapacity(LocalDate date) {
        int totalCapacity = getTotalCapacity(date);
        int usedCapacity = assignmentRepository.getTotalLoadByDate(date);
        int available = totalCapacity - usedCapacity;
        
        System.out.println("📊 Capacidad para " + date + ":");
        System.out.println("   Total: " + totalCapacity + " tons");
        System.out.println("   Usada: " + usedCapacity + " tons");
        System.out.println("   Disponible: " + available + " tons");
        
        return available;
    }
    
    /** NUEVO
     * Método para mantener compatibilidad (ahora devuelve capacidad disponible)
     */
    public int getCapacity(LocalDate date) {
        return getAvailableCapacity(date);
    }
    

    /**
     * Procesa y GUARDA una asignación de contenedor recibida de Ecoembes
     */
    public boolean processAssignment(Integer assignmentId, Integer dumpsterId, Integer plantId, LocalDate date, Integer loadInTons) {
        try {
            // Validaciones básicas
            if (dumpsterId == null || plantId == null || date == null) {
                System.err.println("❌ Invalid assignment data");
                return false;
            }
            
            // Si no se especifica carga, asumir 1 tonelada
            if (loadInTons == null || loadInTons <= 0) {
                loadInTons = 1;
            }
            
            // Verifica si hay capacidad DISPONIBLE
            int availableCapacity = getAvailableCapacity(date);
            if (availableCapacity < loadInTons) {
                System.err.println("❌ No hay suficiente capacidad para la fecha: " + date);
                System.err.println("   Necesaria: " + loadInTons + " tons");
                System.err.println("   Disponible: " + availableCapacity + " tons");
                return false;
            }
            
            // GUARDAR la asignación en la BD
            Assignment assignment = new Assignment(assignmentId, dumpsterId, plantId, date, loadInTons);
            assignmentRepository.save(assignment);
            
            // Log de la asignación aceptada
            System.out.println("✅ PlasSB Assignment SAVED:");
            System.out.println("   Assignment ID: " + assignmentId);
            System.out.println("   Dumpster: " + dumpsterId);
            System.out.println("   Plant: " + plantId);
            System.out.println("   Date: " + date);
            System.out.println("   Load: " + loadInTons + " tons");
            System.out.println("   Remaining capacity: " + (availableCapacity - loadInTons) + " tons");
            
            return true;
            
        } catch (Exception e) {
            System.err.println("❌ Error processing assignment: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    
    }
}