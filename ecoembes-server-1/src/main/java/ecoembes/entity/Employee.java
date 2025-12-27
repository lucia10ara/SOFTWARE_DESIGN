package ecoembes.entity;

import java.util.ArrayList;

import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Employee {
	@Id 
	private int employee_id;
    private String email;
    private String password;
    
    @OneToMany(mappedBy = "employee", fetch = FetchType.LAZY)
    @JsonIgnore 
    private List<Assignment> list_assignments;

    public Employee() {}
    
    
    public Employee(int employee_id, String email, String password) {
    	this.employee_id = employee_id;
        this.email = email;
        this.password = password;
        this.list_assignments = new ArrayList<Assignment>();
    }

    public boolean checkPassword(String pwd) {
        return this.password != null && this.password.equals(pwd);
    }
    
    
    
    public int getEmp_id() {
    	return employee_id;
    }

    public String getEmail() {
        return email;
    }


	public List<Assignment> getList_assignments() {
		return list_assignments;
	}

	public void setList_assignments(List<Assignment> list_assignments) {
		this.list_assignments = list_assignments;
	}
    
	public void setEmployee_id(int employee_id) {
        this.employee_id = employee_id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    

	@Override
	public int hashCode() {
		return Objects.hash(employee_id);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Employee other = (Employee) obj;
		return employee_id == other.employee_id;
	}

    
    
    
}
