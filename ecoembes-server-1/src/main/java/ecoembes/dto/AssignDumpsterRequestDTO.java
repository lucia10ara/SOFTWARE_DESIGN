package ecoembes.dto;

import java.time.LocalDate;

import ecoembes.entity.*;

import java.util.List;


//solo con ids
public class AssignDumpsterRequestDTO {
    //private int emp_id; //lo borro pq no lo voy a pedir como argumento, simplemente el que se ha autorizado en la app, se registrara como employee que asigna el dumpster
    private int rp_id;
    private int dumpster_id;
    
    /*
    public int getEmp_id() { return emp_id; }
    public void setEmp_id(int emp_id) { this.emp_id = emp_id; }
*/
    
    public int getRp_id() { return rp_id; }
    public void setRp_id(int rp_id) { this.rp_id = rp_id; }

    public int getDumpster_id() { return dumpster_id; }
    public void setDumpster_id(int dumpster_id) { this.dumpster_id = dumpster_id; }
    
    
    @Override
    public String toString() {
        return "AssignDumpsterRequestDTO{" +
                "rp_id=" + rp_id +
                ", dumpster_id=" + dumpster_id +
                '}';
    }
}
