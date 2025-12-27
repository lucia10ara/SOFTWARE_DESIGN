package ecoembes.service;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ecoembes.entity.Employee;
import jakarta.annotation.PostConstruct;
import ecoembes.dao.EmployeeRepository;
import ecoembes.dto.*;

@Service
public class AuthService {

    // Comentamos el mapa y usamos el Repositorio (TW2)
    /* private final Map<String, Employee> employeeRepo = new ConcurrentHashMap<>(); */
    @Autowired
    private EmployeeRepository employeeRepository;

    private final Map<String, Employee> tokenStore = new ConcurrentHashMap<>(); 
    
    
    public Optional<String> login(String email, String password) {
        
        //we use the dao to find the employee in the data base
        Optional<Employee> employeeOpt = employeeRepository.findByEmailAndPassword(email, password);

        if (employeeOpt.isPresent()) {
            Employee e = employeeOpt.get();
            String token = generateToken();
            tokenStore.put(token, e);
            return Optional.of(token);
        }
        return Optional.empty();
    }

    public Optional<Boolean> logout(String token) {
        if (tokenStore.containsKey(token)) {
            tokenStore.remove(token);
            return Optional.of(true);
        }
        return Optional.empty();
    }

    
    public Employee getEmployeeByToken(String token) {
        return tokenStore.get(token);
    }

    
    public void saveEmployee(Employee employee) {
        //we save it using the dao (repository)
        if (employee != null) {
            employeeRepository.save(employee);
        }
    }
    
    public Employee findById(int empId) {
        return employeeRepository.findById(empId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
    }


    //from auctions
    private static synchronized String generateToken() {
        return Long.toHexString(System.currentTimeMillis());
    }
}