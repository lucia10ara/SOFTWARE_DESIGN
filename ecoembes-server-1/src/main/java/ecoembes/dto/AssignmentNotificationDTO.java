package ecoembes.dto;

import ecoembes.entity.Assignment;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AssignmentNotificationDTO {

    @JsonProperty("assignment_id")
    private Integer assignment_id;
    
    @JsonProperty("date")
    @JsonFormat(pattern = "yyyy-MM-dd")  //esto controla cómo se serializa a JSON
    private LocalDate date;  
    
    @JsonProperty("emp_id")
    private Integer emp_id;
    
    @JsonProperty("rp_id")
    private Integer rp_id;
    
    @JsonProperty("dumpster_id")
    private Integer dumpster_id;

    // Constructor vacío
    public AssignmentNotificationDTO() {}

    // Constructor desde Assignment
    public AssignmentNotificationDTO(Assignment assignment) {
        this.assignment_id = assignment.getAssignmentId();
        this.date = assignment.getDate();
        this.emp_id = assignment.getEmployee() != null ? assignment.getEmployee().getEmp_id() : null;
        this.rp_id = assignment.getRp() != null ? assignment.getRp().getRp_id() : null;
        this.dumpster_id = assignment.getDumpster() != null ? assignment.getDumpster().getDumpster_id() : null;
    }

    // Getters y Setters
    public Integer getAssignment_id() {
        return assignment_id;
    }

    public void setAssignment_id(Integer assignment_id) {
        this.assignment_id = assignment_id;
    }

    public LocalDate getDate() {
        return date;
    }
    
    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Integer getEmp_id() {
        return emp_id;
    }

    public void setEmp_id(Integer emp_id) {
        this.emp_id = emp_id;
    }

    public Integer getRp_id() {
        return rp_id;
    }

    public void setRp_id(Integer rp_id) {
        this.rp_id = rp_id;
    }

    public Integer getDumpster_id() {
        return dumpster_id;
    }

    public void setDumpster_id(Integer dumpster_id) {
        this.dumpster_id = dumpster_id;
    }
    
    @Override
    public String toString() {
        return "AssignmentNotificationDTO{" +
                "assignment_id=" + assignment_id +
                ", date='" + date + '\'' +
                ", emp_id=" + emp_id +
                ", rp_id=" + rp_id +
                ", dumpster_id=" + dumpster_id +
                '}';
    }
}