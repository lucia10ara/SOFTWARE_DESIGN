package ecoembes.entity;
import jakarta.persistence.*;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import ecoembes.dao.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import ecoembes.entity.*;

@Entity
public class Dumpster {
	
    @Id
    private int dumpster_id;
    private String address;
    private int postalCode;
    private String city;          
    private String country;        
    private int initialCapacity;
    private int num_containers;    
    private int currentFillLevel;
    private Status currentStatus;
    
    @OneToMany(mappedBy = "dumpster", fetch = FetchType.EAGER)
    @JsonIgnore
    private List<DumpsterUsageRecord> list_usage;
    
    @OneToMany(mappedBy = "dumpster", fetch = FetchType.EAGER)
    @JsonIgnore
    private List<Assignment> list_assignments;

    // Constructores
    public Dumpster() {
        this.list_usage = new ArrayList<>();
        this.list_assignments = new ArrayList<>();
    }

    public Dumpster(int dumpster_id, String address, String city, int postalCode,  String country, int initialCapacity, int num_containers, int currentFillLevel) {
		this.dumpster_id = dumpster_id;
		this.address = address;
		this.city = city;
		this.postalCode = postalCode;
		this.country = country;
		this.initialCapacity = initialCapacity;
		this.num_containers = num_containers;
		this.currentFillLevel = currentFillLevel;
		this.currentStatus = Status.GREEN;
		this.list_usage = new ArrayList<>();
		this.list_assignments = new ArrayList<>();
	}
		    
    public void setCity(String city) {
        this.city = city;
    }
    
    public void setCountry(String country) {
        this.country = country;
    }
    
    
    public void setNum_containers(int num_containers) {
        this.num_containers = num_containers;
    }
    
    

    public String getCity() {
		return city;
	}

	public String getCountry() {
		return country;
	}

	public int getNum_containers() {
		return num_containers;
	}

	public int getDumpster_id() {
        return dumpster_id;
    }

    public int getPostalCode() {
        return postalCode;
    }

    public int getInitialCapacity() {
        return initialCapacity;
    }
    
    public String getAddress() {
    	return address;
    }

    public int getCurrentFillLevel() { 
        return currentFillLevel;
    }
    
    public void setCurrentFillLevel(int currentFillLevel) {
        this.currentFillLevel = currentFillLevel;
    }

    public Status getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(Status currentStatus) {
        this.currentStatus = currentStatus;
    }

	public List<DumpsterUsageRecord> getList_usage() {
		return list_usage;
	}

	public void setList_usage(List<DumpsterUsageRecord> list_usage) {
		this.list_usage = list_usage;
	}

	public List<Assignment> getList_assignments() {
		return list_assignments;
	}

	public void setList_assignments(List<Assignment> list_assignments) {
		this.list_assignments = list_assignments;
	}
    
	public void setDumpster_id(int dumpster_id) {
        this.dumpster_id = dumpster_id;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    
    public void setPostalCode(int postalCode) {
        this.postalCode = postalCode;
    }
    
    public void setInitialCapacity(int initialCapacity) {
        this.initialCapacity = initialCapacity;
    }
    
    

	@Override
	public int hashCode() {
		return Objects.hash(dumpster_id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Dumpster other = (Dumpster) obj;
		return dumpster_id == other.dumpster_id;
	}

}
