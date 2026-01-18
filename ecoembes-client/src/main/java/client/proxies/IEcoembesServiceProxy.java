package client.proxies;

import client.data.AssignDumpsterRequestDTO;
import client.data.CredentialsDTO;
import client.data.DumpsterDTO;
import client.data.PlantDTO;

import java.util.List;

public interface IEcoembesServiceProxy {
    String login(CredentialsDTO credentials);
    void logout(String token);

    DumpsterDTO createDumpster(DumpsterDTO dumpster, String token);
    List<DumpsterDTO> listDumpsters(String token);
    
    List<PlantDTO> listPlants(String token);
    int getPlantCapacity(int rpId, String token);
    void assignDumpster(AssignDumpsterRequestDTO dto, String token);
}
