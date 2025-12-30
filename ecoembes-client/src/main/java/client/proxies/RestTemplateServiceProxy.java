package client.proxies;


import org.springframework.web.client.RestTemplate;

import client.data.EmployeeDTO;
import client.data.CredentialsDTO;



public class RestTemplateServiceProxy implements IEcoembesServiceProxy {
    private final RestTemplate restTemplate = new RestTemplate();
    private final String apiBaseUrl = "http://localhost:8080";

    @Override
    public String login(CredentialsDTO credentials) {
        try {
            // Pedimos String.class porque el body de respuesta es "19ad59fb345"
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
}