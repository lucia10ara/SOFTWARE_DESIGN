package ecoembes.external;

import ecoembes.dto.AssignDumpsterRequestDTO;
import ecoembes.dto.AssignmentNotificationDTO;
import ecoembes.entity.Assignment;
import ecoembes.entity.RecyclingPlant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate; // O WebClient, RestTemplate es más simple para empezar



@Component
public class PlasSBGateway implements IRecyclingPlantGateway {

    private final RestTemplate restTemplate = new RestTemplate();  //Crea instancia directa
    
    // endpoints desde properties 
    @Value("${recycling.plants.endpoints.capacity:/api/capacity}")
    private String capacityEndpoint;
    
    @Value("${recycling.plants.endpoints.assignments:/api/assignments}")
    private String assignmentsEndpoint;
    

    @Override
    public int getCapacity(RecyclingPlant plant, String date) {
        // URL base viene de la BD, endpoint desde properties para no tenerlo hardcodeado
        String url = plant.getUrl() + capacityEndpoint + "?date=" + date;
        
        System.out.println("🔍 === DEBUG getCapacity ===");
        System.out.println("   Plant URL: " + plant.getUrl());
        System.out.println("   Endpoint: " + capacityEndpoint);
        System.out.println("   Full URL: " + url);
        
        try {
            Integer capacity = restTemplate.getForObject(url, Integer.class);
            System.out.println("✓ Capacidad recibida: " + capacity);
            return capacity != null ? capacity : 0;
        } catch (Exception e) {
            System.err.println("❌ Error communicating with PlasSB: " + e.getMessage());
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public boolean notifyAssignment(RecyclingPlant rp, Assignment a) {
        String baseUrl = rp.getUrl();
        
        if (baseUrl.endsWith("/")) {
            baseUrl = baseUrl.substring(0, baseUrl.length() - 1);
        }
        
        // ✅ Endpoint desde properties
        String fullUrl = baseUrl + assignmentsEndpoint;
        
        AssignmentNotificationDTO dto = new AssignmentNotificationDTO(a);
        
        System.out.println("🔍 Notificando a: " + fullUrl);
        
        try {
            restTemplate.postForEntity(fullUrl, dto, String.class);
            System.out.println("✓ Notificación exitosa a planta " + rp.getRp_id());
            return true;
            
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode().value() == 409) {
                System.err.println("❌ Planta rechazó asignación (409 Conflict)");
                throw new RuntimeException("Plant rejected assignment: " + e.getResponseBodyAsString());
            }
            throw new RuntimeException("HTTP error notifying plant: " + e.getMessage());
            
        } catch (Exception e) {
            System.err.println("❌ Error al notificar planta " + rp.getRp_id() + ": " + e.getMessage());
            throw new RuntimeException("Failed to notify plant: " + e.getMessage());
        }
    }
}
