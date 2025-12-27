package ecoembes.facade;

import java.time.LocalDate;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.format.annotation.DateTimeFormat;

import ecoembes.dao.DumpsterRepository;
import ecoembes.dto.DumpsterDetailsDTO;
import ecoembes.dto.DumpsterUpdateDTO;
import ecoembes.dto.DumpsterUsageDTO;
import ecoembes.entity.Dumpster;
import ecoembes.service.DumpsterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;


import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("/dumpsters")
@Tag(name = "Dumpsters Controller", description = "Dumpster management operations")
public class DumpsterController {
    
    private final DumpsterService dumpsterService;
    private final DumpsterRepository dumpsterRepository;
    
    @Autowired
    public DumpsterController(DumpsterService dumpsterService, DumpsterRepository dumpsterRepository) {
        this.dumpsterService = dumpsterService;
        this.dumpsterRepository = dumpsterRepository;
    }
    
    @Operation(summary = "Get all dumpsters")
    @GetMapping
    public ResponseEntity<List<Dumpster>> getAllDumpsters() {
        List<Dumpster> dumpsters = dumpsterRepository.findAll();
        return ResponseEntity.ok(dumpsters);
    }
    
    @Operation(summary = "Get dumpster by ID")
    @GetMapping("/{id}")
    public ResponseEntity<Dumpster> getDumpsterById(@PathVariable("id") int id) {
        Dumpster dumpster = dumpsterService.findById(id);
        if (dumpster == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(dumpster);
    }
    
    @Operation(summary = "Create new dumpster")
    @PostMapping
    public ResponseEntity<Dumpster> createDumpster(@RequestBody DumpsterDetailsDTO dto) {
        Dumpster d = dumpsterService.createDumpster(dto);
        return new ResponseEntity<>(d, HttpStatus.CREATED);
    }
    
    
    @Operation(summary = "Update dumpster")
    @PutMapping("/{id}")
    public ResponseEntity<Dumpster> updateDumpster(
            @PathVariable int id,
            @RequestBody DumpsterUpdateDTO dto) {  // ← Sin parámetro date
        
        return dumpsterService.updateDumpsterInfo(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @Operation(summary = "Query dumpster usage between two dates")
    @GetMapping("/{id}/usage")
    public ResponseEntity<List<DumpsterUsageDTO>> getDumpsterUsage(
            @PathVariable("id") int id,
            @RequestParam("from") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam("to") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {
        
        try {
            Dumpster dumpster = dumpsterService.findById(id);
            if (dumpster == null) {
                return ResponseEntity.notFound().build();
            }
            List<DumpsterUsageDTO> list = dumpsterService.getUsage(dumpster, from, to);
            return ResponseEntity.ok(list);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
