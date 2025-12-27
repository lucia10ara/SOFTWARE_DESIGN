package ecoembes.dto;

import java.time.LocalDate;

import ecoembes.entity.Dumpster;



public class DumpsterDetailsDTO {
    	
    private int dumpster_id;
    private String address;
    private int pc;
    private String city;
    private String country;
    private double initialCapacity;
    private int num_containers;
    
    // Constructor vacío
    public DumpsterDetailsDTO() {}
    
    // Getters y Setters
    
    public int getDumpster_id() { return dumpster_id; }
    public void setDumpster_id(int dumpster_id) { this.dumpster_id = dumpster_id; }
    
    
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    
    public int getPc() { return pc; }
    public void setPc(int pc) { this.pc = pc; }
    
    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }
    
    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }
    
    public double getInitialCapacity() { return initialCapacity; }
    public void setInitialCapacity(double initialCapacity) { 
        this.initialCapacity = initialCapacity; 
    }
    
    public int getNum_containers() { return num_containers; }
    public void setNum_containers(int num_containers) { 
        this.num_containers = num_containers; 
    }
}
