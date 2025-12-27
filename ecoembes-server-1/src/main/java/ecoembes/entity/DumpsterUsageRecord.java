package ecoembes.entity;

import java.time.LocalDate;
import java.util.Objects;

import jakarta.persistence.*;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class DumpsterUsageRecord {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int dur_id;

	
	@ManyToOne 
    @JoinColumn(name = "dumpster_fk") 
    @JsonIgnore // ← Simple y efectivo
    private Dumpster dumpster;
    private LocalDate start_date;
    private LocalDate end_date;
    private int fillLevel;
    private int num_containers;
    private Status status; 

    //necesitamos un constructor vacio
    public DumpsterUsageRecord() {}

    public DumpsterUsageRecord(Dumpster dumpster, LocalDate start_date, LocalDate end_date, int fillLevel, int num_containers, Status status) {
        // Inicialización del ID puede ser manual o a través de la DB
        // this.dur_id = ... 
		this.dumpster = dumpster;
		this.start_date = start_date;
		this.end_date = end_date;
		this.fillLevel = fillLevel;
		this.num_containers = num_containers;
		this.status = status;
	}
	
    public int getDur_id() {
        return dur_id;
    }

    public void setDur_id(int dur_id) {
        this.dur_id = dur_id;
    }
    
	public Dumpster getDumpster() {
		return dumpster;
	}
	
	public LocalDate getStart_date() {
		return start_date;
	}
	
	public LocalDate getEnd_date() {
		return end_date;
	}
	
	public int getFillLevel() {
		return fillLevel;
	}
	
	public int getNum_containers() {
		return num_containers;
	}
	
	public Status getStatus() {
		return status;
	}
	
	public void setDumpster(Dumpster dumpster) {
	    this.dumpster = dumpster;
	}
	
	public void setStart_date(LocalDate start_date) {
	    this.start_date = start_date;
	}
	
	public void setEnd_date(LocalDate end_date) {
	    this.end_date = end_date;
	}
	
	public void setFillLevel(int fillLevel) {
	    this.fillLevel = fillLevel;
	}
	
	public void setNum_containers(int num_containers) {
	    this.num_containers = num_containers;
	}

	@Override
	public int hashCode() {
		return Objects.hash(dur_id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DumpsterUsageRecord other = (DumpsterUsageRecord) obj;
		return dur_id == other.dur_id;
	}


	
}