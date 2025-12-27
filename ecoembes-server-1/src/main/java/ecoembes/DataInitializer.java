package ecoembes;

import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import ecoembes.entity.*;
import ecoembes.entity.Employee;
import ecoembes.entity.RecyclingPlant;
import ecoembes.entity.Status;

import ecoembes.dao.*;

import ecoembes.service.DumpsterService;


@Configuration
public class DataInitializer {

    private static final Logger logger = LoggerFactory.getLogger(DataInitializer.class);

    @Bean
    CommandLineRunner initData(
        EmployeeRepository employeeRepository,
        RecyclingPlantRepository recyclingPlantRepository,
        DumpsterRepository dumpsterRepository,
        AssignmentRepository assignmentRepository,
        DumpsterUsageRecordRepository dumpsterUsageRecordRepository,
        DumpsterService dumpsterService) {
            
        return args -> {
            logger.info("--- Inicializando datos en DB para Prototipo TW2 ---");

            // 1. EMPLOYEES
            Employee emp1 = new Employee(101, "ane_idoiaga@ecoembes.com", "aneido66");
            Employee emp2 = new Employee(102, "lucia_baranano@ecoembes.com", "lubaranano77");
            Employee emp3 = new Employee(103, "lucia_diez@ecoembes.com", "ludiez005");
            
            // Persistir todos los empleados y usar el resultado del save()
            Employee savedEmp1 = employeeRepository.save(emp1);
            employeeRepository.save(emp2);
            employeeRepository.save(emp3);
            
            logger.info("Employees saved");
            
            
            // 2. RECYCLING PLANTS
            //RecyclingPlant plantPlas = new RecyclingPlant(5001, 1000, "http://localhost:8082/api", null, 0); 
            RecyclingPlant plantPlas = new RecyclingPlant(5001, 1000, "http://localhost:8081", null, 0);
            RecyclingPlant plantCont = new RecyclingPlant(5002, 500, null, "127.0.0.1", 6000);
            
            plantPlas.setCapacity(1000);
            plantCont.setCapacity(500);
            
            RecyclingPlant savedPlantPlas = recyclingPlantRepository.save(plantPlas); 
            recyclingPlantRepository.save(plantCont);
            logger.info("RecyclingPlants saved");


            // 3. DUMPSTERS 
            Dumpster d001 = new Dumpster(1, "Calle Ledesma 1", "Bilbao", 48001, "España", 100, 3, 10);
            Dumpster d002 = new Dumpster(2, "Gran Vía 50", "Madrid", 28013, "España", 50, 5, 25);
            Dumpster d003 = new Dumpster(3, "Plaza Nueva", "Sevilla", 41004, "España", 90, 4, 85);

            
            d001.setCurrentStatus(Status.GREEN);
            d002.setCurrentStatus(Status.ORANGE);
            d003.setCurrentStatus(Status.RED);
            
            Dumpster savedD001 = dumpsterRepository.save(d001);
            dumpsterRepository.save(d002);
            dumpsterRepository.save(d003);
            logger.info("Dumpster saved");
            
            //USAGE RECORDS para D001

            DumpsterUsageRecord u1 = new DumpsterUsageRecord(
                savedD001,
                LocalDate.of(2025, 1, 1),
                LocalDate.of(2025, 1, 10),
                35,
                savedD001.getNum_containers(),
                Status.GREEN
            );

            DumpsterUsageRecord u2 = new DumpsterUsageRecord(
                savedD001,
                LocalDate.of(2025, 1, 10),
                LocalDate.of(2025, 1, 20),
                72,
                savedD001.getNum_containers(),
                Status.ORANGE
            );

            DumpsterUsageRecord u3 = new DumpsterUsageRecord(
                savedD001,
                LocalDate.of(2025, 1, 20),
                LocalDate.of(2025, 1, 30),
                90,
                savedD001.getNum_containers(),
                Status.RED
            );

            dumpsterUsageRecordRepository.save(u1);
            dumpsterUsageRecordRepository.save(u2);
            dumpsterUsageRecordRepository.save(u3);

            
            // 4. ASSIGNMENT (Usar los objetos que retornó save())
            Assignment assignment001 = new Assignment(
                100,
                LocalDate.now(), //para hoy
                savedEmp1, // Usar el objeto retornado por save()
                savedPlantPlas, // Usar el objeto retornado por save()
                savedD001 // Usar el objeto retornado por save()
            );

            // Esto llama a assignmentRepository.save(a), y Hibernate maneja las relaciones
            dumpsterService.saveAssignment(assignment001);
            
            // ⬇️ LÍNEAS ELIMINADAS: La modificación manual causa el error de estado ⬇️
            // emp1.getList_assignments().add(assignment001);
            // plantPlas.getList_assignments().add(assignment001);

            logger.info("Assignment saved");
            logger.info("--- Initialization finished ---");
        };
    }
}