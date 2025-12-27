package plasSB.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "assignments")
public class Assignment {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "assignment_id")
    private Integer assignmentId;
    
    @Column(name = "dumpster_id")
    private Integer dumpsterId;
    
    @Column(name = "plant_id")
    private Integer plantId;
    
    @Column(name = "date")
    private LocalDate date;
    
    @Column(name = "load_in_tons")
    private Integer loadInTons;
    
    // Constructor vacío
    public Assignment() {}
    
    // Constructor con parámetros
    public Assignment(Integer assignmentId, Integer dumpsterId, Integer plantId, LocalDate date, Integer loadInTons) {
        this.assignmentId = assignmentId;
        this.dumpsterId = dumpsterId;
        this.plantId = plantId;
        this.date = date;
        this.loadInTons = loadInTons;
    }
    
    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Integer getAssignmentId() { return assignmentId; }
    public void setAssignmentId(Integer assignmentId) { this.assignmentId = assignmentId; }
    
    public Integer getDumpsterId() { return dumpsterId; }
    public void setDumpsterId(Integer dumpsterId) { this.dumpsterId = dumpsterId; }
    
    public Integer getPlantId() { return plantId; }
    public void setPlantId(Integer plantId) { this.plantId = plantId; }
    
    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
    
    public Integer getLoadInTons() { return loadInTons; }
    public void setLoadInTons(Integer loadInTons) { this.loadInTons = loadInTons; }
}