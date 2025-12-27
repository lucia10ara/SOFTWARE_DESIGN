package ecoembes.entity;
import jakarta.persistence.*;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class Assignment {
	@Id // <--- AÑADIDO: Marca el campo como clave primaria
    private int assignment_id;
    private LocalDate date;
    @ManyToOne // Una asignación se relaciona con UN empleado
    @JoinColumn(name = "employee_fk") // Columna de la Clave Foránea
    @JsonIgnore // ← Simple y efectivo
    private Employee employee; 
    
    @ManyToOne // Una asignación se relaciona con UNA planta
    @JoinColumn(name = "rp_fk")
    private RecyclingPlant rp; 
    
    // ESTE ES EL CAMPO QUE CAUSA EL ERROR:
    @ManyToOne // Una asignación se relaciona con UN dumpster
    @JoinColumn(name = "dumpster_fk")
    @JsonIgnore // ← Simple y efectivo
    private Dumpster dumpster;

    
    public Assignment() {};

    
    public Assignment(int assignment_id, LocalDate date, Employee employee, RecyclingPlant rp, Dumpster dumpster) {
        this.assignment_id = assignment_id;
        this.date = date; 
        this.employee = employee;
        this.rp = rp;
        this.dumpster = dumpster;
    }

    public void setAssignment_id(int assignment_id) {
        this.assignment_id = assignment_id;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
    
    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
    
    public void setRp(RecyclingPlant rp) {
        this.rp = rp;
    }

    public void setDumpster(Dumpster dumpster) {
        this.dumpster = dumpster;
    }


    public int getAssignmentId() {
        return assignment_id;
    }

    public LocalDate getDate() {
        return date;
    }

    public Employee getEmployee() {
        return employee;
    }

    public RecyclingPlant getRp() {
        return rp;
    }

    public Dumpster getDumpster() {
        return dumpster;
    }

    public Integer getDumpster_id() {
        return dumpster != null ? dumpster.getDumpster_id() : null;
    }

    @Override
    public int hashCode() {
        return Objects.hash(assignment_id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Assignment other = (Assignment) obj;
        return assignment_id == other.assignment_id;
    }
    
    
}

