package ecoembes.service;

import java.time.LocalDate;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ecoembes.dto.DumpsterDetailsDTO;
import ecoembes.dto.DumpsterUpdateDTO;
import ecoembes.dto.DumpsterUsageDTO;
import ecoembes.entity.Assignment;
import ecoembes.entity.Dumpster;
import ecoembes.entity.DumpsterUsageRecord;
import ecoembes.entity.Status;

import ecoembes.dao.*; //we import our dao


@Service
public class DumpsterService {

    //We eliminate our maps from TW1
    /*
     * private static Map<Integer, Dumpster> dumpsters = new HashMap<>(); 
     * private static List<DumpsterUsageRecord> usageRecords = new ArrayList<>(); 
     * private static Map<Integer, Assignment> assignmentsData = new HashMap<>();
     */
	

    @Autowired
    private DumpsterRepository dumpsterRepository;

    @Autowired
    private DumpsterUsageRecordRepository usageRecordRepository;

    @Autowired
    private AssignmentRepository assignmentRepository;

    
    public void saveDumpster(Dumpster dumpster) {
        if (dumpster != null && dumpster.getDumpster_id() > 0) {
            dumpsterRepository.save(dumpster);
        }
    }
    
    public Dumpster findById(int dumpsterId) {
        return dumpsterRepository.findById(dumpsterId)
                .orElseThrow(() -> new RuntimeException("Dumpster not found"));
    }

    
    public void saveAssignment(Assignment assignment) {
        if (assignment != null && assignment.getAssignmentId() > 0) {
            assignmentRepository.save(assignment);
            Dumpster d = assignment.getDumpster();
            if (d != null) {
                if (d.getList_assignments() == null) {
                    d.setList_assignments(new ArrayList<>());
                }
                d.getList_assignments().add(assignment);
            }
        }
    }

/* cambio para no pedirlo por parametro
    public Dumpster createDumpster(DumpsterDetailsDTO dto) {
        Dumpster d = new Dumpster(
            dto.getDumpster_id(),
            dto.getAddress(),
            dto.getPc(),
            dto.getInitialCapacity(),
            dto.getFillLevel()
        );

        return dumpsterRepository.save(d);
    }*/
    
    
    public Dumpster createDumpster(DumpsterDetailsDTO dto) {
        Dumpster d = new Dumpster();
        
        // Datos del DTO
        d.setDumpster_id(dto.getDumpster_id());  // ← AÑADIR manualmente
        d.setAddress(dto.getAddress());
        d.setPostalCode(dto.getPc());
        d.setCity(dto.getCity());
        d.setCountry(dto.getCountry());
        d.setInitialCapacity((int) dto.getInitialCapacity()); // cast de double a int
        d.setNum_containers(dto.getNum_containers());
        
        d.setCurrentFillLevel(0);
        d.setCurrentStatus(Status.GREEN);
        
        return dumpsterRepository.save(d);
    }
    
    public Optional<Dumpster> updateDumpsterInfo(int dumpsterId, DumpsterUpdateDTO dto) {  // ← Sin parámetro date
        Optional<Dumpster> dumpsterOpt = dumpsterRepository.findById(dumpsterId);
        
        if (dumpsterOpt.isEmpty()) {
            return Optional.empty();
        }
        
        Dumpster d = dumpsterOpt.get();
        
        // Fecha actual automática
        LocalDate today = LocalDate.now();  // ← Se genera aquí

        // 1. Calcular el nuevo status
        Status newStatus = dto.calculateSaturationStatus();

        // 2. Actualizar el Dumpster (estado ACTUAL)
        d.setCurrentFillLevel(dto.getFillLevel());
        d.setNum_containers(dto.getNum_containers());
        d.setCurrentStatus(newStatus);

        // 3. Crear un nuevo registro histórico
        DumpsterUsageRecord newRecord = new DumpsterUsageRecord(
            d,
            today.minusDays(1),  // start_date: ayer
            today,               // end_date: hoy
            dto.getFillLevel(),
            dto.getNum_containers(),
            newStatus
        );
        
        // 4. Guardar ambos
        usageRecordRepository.save(newRecord);
        dumpsterRepository.save(d);

        return Optional.of(d);
    }
    

    public List<DumpsterUsageDTO> getUsage(Dumpster dumpster, LocalDate from, LocalDate to) {
        
    	// we use the query method of our dao
    	List<DumpsterUsageRecord> records = usageRecordRepository.findUsageByDumpsterIdAndDates(
                dumpster.getDumpster_id(), 
                from, 
                to
            );
        
        return records.stream()
             .map(r -> new DumpsterUsageDTO(
                 r.getStart_date(),
                 r.getEnd_date(),
                 r.getFillLevel(),
                 r.getNum_containers(),
                 r.getStatus()
             ))
             .collect(Collectors.toList());
    }

    public List<Dumpster> getStatusByPostalCodeAndDate(String postalCode, LocalDate date) {

        int pc = Integer.parseInt(postalCode);
        List<Dumpster> dumpstersInArea = dumpsterRepository.findByPostalCode(pc);
        
        return dumpstersInArea;
    }

    public Map<Integer, Dumpster> getAllDumpsters() {
        return dumpsterRepository.findAll().stream()
            .collect(Collectors.toMap(Dumpster::getDumpster_id, d -> d));
    }

    public int calculateTotalLoad(List<Dumpster> dumpsters) {
        return dumpsters.stream()
            .mapToInt(Dumpster::getCurrentFillLevel)
            .sum();
    }
    
    private Status calculateSaturationStatus(int fillPercentage) {
        if (fillPercentage >= 80) {
            return Status.RED;
        } else if (fillPercentage >= 50) {
            return Status.ORANGE;
        } else {
            return Status.GREEN;
        }
    }
}