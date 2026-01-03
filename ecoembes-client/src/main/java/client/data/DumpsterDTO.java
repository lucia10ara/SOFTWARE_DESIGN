package client.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * DTO compatible con el Ecoembes Server.
 * - Para crear: el servidor actual necesita dumpster_id (no autogenerado).
 * - Para listar: se mapean también campos extra que devuelve la entidad.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class DumpsterDTO {

    @JsonProperty("dumpster_id")
    private int dumpsterId;

    @JsonProperty("address")
    private String address;

    @JsonProperty("pc")
    private int postalCode;

    @JsonProperty("city")
    private String city;

    @JsonProperty("country")
    private String country;

    @JsonProperty("initialCapacity")
    private double initialCapacity;

    @JsonProperty("num_containers")
    private int numContainers;

    @JsonProperty("currentFillLevel")
    private int currentFillLevel;

    @JsonProperty("currentStatus")
    private String currentStatus;

    public DumpsterDTO() {}

    public int getDumpsterId() { return dumpsterId; }
    public void setDumpsterId(int dumpsterId) { this.dumpsterId = dumpsterId; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public int getPostalCode() { return postalCode; }
    public void setPostalCode(int postalCode) { this.postalCode = postalCode; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }

    public double getInitialCapacity() { return initialCapacity; }
    public void setInitialCapacity(double initialCapacity) { this.initialCapacity = initialCapacity; }

    public int getNumContainers() { return numContainers; }
    public void setNumContainers(int numContainers) { this.numContainers = numContainers; }

    public int getCurrentFillLevel() { return currentFillLevel; }
    public void setCurrentFillLevel(int currentFillLevel) { this.currentFillLevel = currentFillLevel; }

    public String getCurrentStatus() { return currentStatus; }
    public void setCurrentStatus(String currentStatus) { this.currentStatus = currentStatus; }
}
