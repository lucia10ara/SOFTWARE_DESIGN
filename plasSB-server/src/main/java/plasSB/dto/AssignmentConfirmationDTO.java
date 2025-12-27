package plasSB.dto;
import java.time.LocalDate;

// Lo que RESPONDE la planta a Ecoembes (confirmación)
public class AssignmentConfirmationDTO {
    private String response;      // "ACCEPTED" o "REJECTED"
    private String dumpster_id;   // El contenedor que se procesó
    private String message;       // Mensaje descriptivo

    // Constructores
    public AssignmentConfirmationDTO() {}

    public AssignmentConfirmationDTO(String response, String dumpster_id, String message) {
        this.response = response;
        this.dumpster_id = dumpster_id;
        this.message = message;
    }

    // Getters y Setters
    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getDumpster_id() {
        return dumpster_id;
    }

    public void setDumpster_id(String dumpster_id) {
        this.dumpster_id = dumpster_id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}