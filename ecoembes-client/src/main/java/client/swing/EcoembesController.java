package client.swing;

import client.proxies.*;
import client.data.*;

public class EcoembesController {
    private IEcoembesServiceProxy serviceProxy = new RestTemplateServiceProxy();
    private String token = null;

    public boolean login(String email, String password) {
        try {
            String result = serviceProxy.login(new CredentialsDTO(email, password));
            if (result != null && !result.isEmpty()) {
                this.token = result;
                return true;
            }
        } catch (Exception e) {
            System.err.println("Error en Controller: " + e.getMessage());
        }
        return false;
    }

    public void logout() {
        if (this.token != null) {
            serviceProxy.logout(this.token);
            this.token = null;
        }
    }

    public String getToken() {
        return token;
    }

    // ---------------- Persona B ----------------
    public DumpsterDTO createDumpster(DumpsterDTO dto) {
        return serviceProxy.createDumpster(dto, this.token);
    }

    public java.util.List<DumpsterDTO> listDumpsters() {
        return serviceProxy.listDumpsters(this.token);
    }
}
