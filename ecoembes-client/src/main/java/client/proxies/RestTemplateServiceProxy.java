package client.proxies;

import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;

import client.data.AssignDumpsterRequestDTO;
import client.data.CredentialsDTO;
import client.data.DumpsterDTO;
import client.data.PlantDTO;

import java.util.Arrays;
import java.util.List;

public class RestTemplateServiceProxy implements IEcoembesServiceProxy {
    private final RestTemplate restTemplate = new RestTemplate();
    private final String apiBaseUrl = "http://localhost:8080";

    private HttpHeaders buildHeaders(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        if (token != null && !token.isBlank()) {
            headers.set("Authorization", "Bearer " + token);
        }
        return headers;
    }

    @Override
    public String login(CredentialsDTO credentials) {
        try {
            return restTemplate.postForObject(apiBaseUrl + "/auth/login", credentials, String.class);
        } catch (Exception e) {
            throw new RuntimeException("Error en el login: " + e.getMessage());
        }
    }

    @Override
    public void logout(String token) {
        try {
            String url = apiBaseUrl + "/auth/logout?token=" + token;
            restTemplate.postForObject(url, null, Void.class);
        } catch (Exception e) {
            System.out.println("Nota: El servidor no confirmó el logout (400), pero cerramos sesión local.");
        }
    }

    // -----------------
    // Persona B
    // -----------------
    @Override
    public DumpsterDTO createDumpster(DumpsterDTO dumpster, String token) {
        try {
            String url = apiBaseUrl + "/dumpsters";
            HttpEntity<DumpsterDTO> entity = new HttpEntity<>(dumpster, buildHeaders(token));
            ResponseEntity<DumpsterDTO> response = restTemplate.exchange(url, HttpMethod.POST, entity, DumpsterDTO.class);
            return response.getBody();
        } catch (Exception e) {
            throw new RuntimeException("Error creando dumpster: " + e.getMessage());
        }
    }

    @Override
    public List<DumpsterDTO> listDumpsters(String token) {
        try {
            String url = apiBaseUrl + "/dumpsters";
            HttpEntity<Void> entity = new HttpEntity<>(buildHeaders(token));
            ResponseEntity<DumpsterDTO[]> response = restTemplate.exchange(url, HttpMethod.GET, entity, DumpsterDTO[].class);
            DumpsterDTO[] arr = response.getBody();
            return arr == null ? List.of() : Arrays.asList(arr);
        } catch (Exception e) {
            throw new RuntimeException("Error listando dumpsters: " + e.getMessage());
        }
    }
    
	 // Plantas y Asignación:
    
	 @Override
	 public List<PlantDTO> listPlants(String token) {
	     try {
	         String url = apiBaseUrl + "/plants";
	         HttpEntity<Void> entity = new HttpEntity<>(buildHeaders(token));
	         ResponseEntity<PlantDTO[]> response = restTemplate.exchange(url, HttpMethod.GET, entity, PlantDTO[].class);
	         PlantDTO[] arr = response.getBody();
	         return arr == null ? List.of() : Arrays.asList(arr);
	     } catch (Exception e) {
	         throw new RuntimeException("Error listando plantas: " + e.getMessage());
	     }
	 }
	
	 @Override
	 public int getPlantCapacity(int rpId, String token) {
	     try {
	         String url = apiBaseUrl + "/plants/" + rpId + "/capacity";
	         HttpEntity<Void> entity = new HttpEntity<>(buildHeaders(token));
	         ResponseEntity<Integer> response = restTemplate.exchange(url, HttpMethod.GET, entity, Integer.class);
	         return response.getBody() != null ? response.getBody() : 0;
	     } catch (Exception e) {
	         throw new RuntimeException("Error obteniendo capacidad de planta: " + e.getMessage());
	     }
	 }
	
	 @Override
	 public void assignDumpster(AssignDumpsterRequestDTO dto, String token) {
	     try {
	         String url = apiBaseUrl + "/assignments";
	         HttpEntity<AssignDumpsterRequestDTO> entity = new HttpEntity<>(dto, buildHeaders(token));
	         restTemplate.exchange(url, HttpMethod.PUT, entity, Void.class);
	     } catch (Exception e) {
	         throw new RuntimeException("Error asignando dumpster: " + e.getMessage());
	     }
	 }

}
