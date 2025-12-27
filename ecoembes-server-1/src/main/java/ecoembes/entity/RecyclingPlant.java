package ecoembes.entity;

import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
public class RecyclingPlant {
	@Id // <--- AÑADIDO: Marca el campo como clave primaria
    private int rp_id;
    private int recyclingCapacity;
    
    //TW2
    private String url; 
    private String ip; 
    private int port; 

    @OneToMany(mappedBy = "rp", fetch = FetchType.LAZY)
    @JsonIgnore // ← Simple y efectivo
    private List<Assignment> list_assignments;
    
    
    
    public RecyclingPlant() {};
    public RecyclingPlant(int rp_id, int capacity) {
        this.rp_id = rp_id;
        this.recyclingCapacity = capacity;
        this.list_assignments = new ArrayList<Assignment>();
    }
    
    
    public RecyclingPlant(int rp_id, int capacity, String url, String ip, int port) {
        this.rp_id = rp_id;
        this.recyclingCapacity = capacity;
        this.url = url;
        this.ip = ip;
        this.port = port;
        this.list_assignments = new ArrayList<Assignment>();
    }
 

    public int getRp_id() {
        return rp_id;
    }

    public void setCapacity(int capacity) {
        this.recyclingCapacity = capacity;
    }
    
    public int getRecyclingCapacity() { // AÑADIDO
        return recyclingCapacity;
    }

    public Integer getCapacity(LocalDate date) {
        return recyclingCapacity;
    }
    
	public List<Assignment> getList_assignments() {
		return list_assignments;
	}

	public void setList_assignments(List<Assignment> list_assignments) {
		this.list_assignments = list_assignments;
	}
	
	//para el service gateway
    public String getUrl() {
        return url;
    }

    public String getIp() {
        return ip;
    }

    public int getPort() {
        return port;
    }
	@Override
	public int hashCode() {
		return Objects.hash(rp_id);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RecyclingPlant other = (RecyclingPlant) obj;
		return rp_id == other.rp_id;
	}

    
    
}

