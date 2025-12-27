package plasSB.dto;

import java.time.LocalDate;

/**
 * DTO para recibir notificaciones de asignación desde Ecoembes
 * NOTA: Actualmente NO se usa - El controlador usa Map<String, Object>
 */
public class AssignmentNotificationDTO {
    
    private Integer assignment_id;
    private Integer dumpster_id;
    private Integer rp_id;
    private Integer emp_id;
    private String date;  // String porque viene como JSON string

    // Constructores
    public AssignmentNotificationDTO() {}

    public AssignmentNotificationDTO(Integer assignment_id, Integer dumpster_id, 
                                    Integer rp_id, Integer emp_id, String date) {
        this.assignment_id = assignment_id;
        this.dumpster_id = dumpster_id;
        this.rp_id = rp_id;
        this.emp_id = emp_id;
        this.date = date;
    }

    // Getters y Setters
    public Integer getAssignment_id() {
        return assignment_id;
    }

    public void setAssignment_id(Integer assignment_id) {
        this.assignment_id = assignment_id;
    }

    public Integer getDumpster_id() {
        return dumpster_id;
    }

    public void setDumpster_id(Integer dumpster_id) {
        this.dumpster_id = dumpster_id;
    }

    public Integer getRp_id() {
        return rp_id;
    }

    public void setRp_id(Integer rp_id) {
        this.rp_id = rp_id;
    }

    public Integer getEmp_id() {
        return emp_id;
    }

    public void setEmp_id(Integer emp_id) {
        this.emp_id = emp_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
    
    @Override
    public String toString() {
        return "AssignmentNotificationDTO{" +
                "assignment_id=" + assignment_id +
                ", dumpster_id=" + dumpster_id +
                ", rp_id=" + rp_id +
                ", emp_id=" + emp_id +
                ", date='" + date + '\'' +
                '}';
    }
}