package plasSB.entity;



import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.time.LocalDate;
	
@Entity
public class CapacityRecord {

    @Id
    private LocalDate date; // Usamos la fecha como clave primaria
    
    private int capacityInTons;
    
    public CapacityRecord() {}
    
    public CapacityRecord(LocalDate date, int capacityInTons) {
        this.date = date;
        this.capacityInTons = capacityInTons;
    }

    public LocalDate getDate() {
        return date;
    }
    
    public int getCapacityInTons() {
        return capacityInTons;
    }

    // Se necesitan Setters para JPA

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setCapacityInTons(int capacityInTons) {
        this.capacityInTons = capacityInTons;
    }
}