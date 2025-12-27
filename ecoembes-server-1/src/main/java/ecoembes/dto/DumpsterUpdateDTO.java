package ecoembes.dto;
import ecoembes.entity.Status;

public class DumpsterUpdateDTO {
    
    private int fillLevel; //percentage of fill 
    private int num_containers;
    
    public DumpsterUpdateDTO() {}
    
    public int getFillLevel() {
        return fillLevel;
    }
    
    public int getNum_containers() {
        return num_containers;
    }
    
    public void setFillLevel(int fillLevel) {
        this.fillLevel = fillLevel;
    }
    
    public void setNum_containers(int num_containers) {
        this.num_containers = num_containers;
    }
    
    public Status calculateSaturationStatus() { 
        if (this.fillLevel >= 80) {
            return Status.RED;
        } else if (this.fillLevel >= 50) {
            return Status.ORANGE;
        } else {
            return Status.GREEN;
        }
    }
}