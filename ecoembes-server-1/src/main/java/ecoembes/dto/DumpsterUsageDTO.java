package ecoembes.dto;

import java.time.LocalDate;

import ecoembes.entity.Status;

public class DumpsterUsageDTO {
    
	private LocalDate start_date; 
    private LocalDate end_date; 
    private int fillLevel; 
    private int num_containers; 
    private Status status;  

    public DumpsterUsageDTO(LocalDate start_date, LocalDate end_date, int fillLevel, int num_containers, Status status) {
        this.start_date = start_date;
        this.end_date = end_date;
        this.fillLevel = fillLevel;
        this.num_containers = num_containers;
        this.status = status;
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
}