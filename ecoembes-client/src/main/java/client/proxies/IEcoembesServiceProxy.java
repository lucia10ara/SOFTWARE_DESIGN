package client.proxies;

import client.data.EmployeeDTO;
import client.data.CredentialsDTO;

public interface IEcoembesServiceProxy {
    // CAMBIO: Debe devolver EmployeeDTO, no String
    String login(CredentialsDTO credentials); 
    void logout(String token);
}