package ecoembes.dto;

public class CreateDumpsterDTO {
    
    //solo los campos necesarios para CREAR un contenedor
    private String address;
    private int pc;
    private String city;
    private String country;
    private double initialCapacity;
    private int num_containers;
    
    // Constructors
    public CreateDumpsterDTO() {}
    
    public CreateDumpsterDTO(String address, int pc, String city, String country, 
                            double initialCapacity, int num_containers) {
        this.address = address;
        this.pc = pc;
        this.city = city;
        this.country = country;
        this.initialCapacity = initialCapacity;
        this.num_containers = num_containers;
    }
    
    // Getters y Setters
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    
    public int getPc() { return pc; }
    public void setPc(int pc) { this.pc = pc; }
    
    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }
    
    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }
    
    public double getInitialCapacity() { return initialCapacity; }
    public void setInitialCapacity(double initialCapacity) { this.initialCapacity = initialCapacity; }
    
    public int getNum_containers() { return num_containers; }
    public void setNum_containers(int num_containers) { this.num_containers = num_containers; }
}