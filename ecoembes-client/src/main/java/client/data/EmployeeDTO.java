package client.data;

import java.io.Serializable;



import java.io.Serializable;


public class EmployeeDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private int employee_id; 
    private String email;   
    private String password; 
    private long token;      // Timestamp generado por el servidor

    public EmployeeDTO() {}

    // Getters y Setters
    public int getEmployee_id() { return employee_id; }
    public void setEmployee_id(int employee_id) { this.employee_id = employee_id; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public long getToken() { return token; }
    public void setToken(long token) { this.token = token; }
}