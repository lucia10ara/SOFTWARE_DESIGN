package client.proxies;

import client.data.CredentialsDTO;
import client.data.DumpsterDTO;

import java.util.List;

public interface IEcoembesServiceProxy {
    String login(CredentialsDTO credentials);
    void logout(String token);

    // Persona B
    DumpsterDTO createDumpster(DumpsterDTO dumpster, String token);
    List<DumpsterDTO> listDumpsters(String token);
}
