package ecoembes.dao; 

import ecoembes.entity.DumpsterUsageRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface DumpsterUsageRecordRepository extends JpaRepository<DumpsterUsageRecord, Integer> { //añadimos a la entidad un id para identificar
    
	@Query("SELECT r FROM DumpsterUsageRecord r WHERE r.dumpster.dumpster_id = :dumpster_id AND r.start_date BETWEEN :startDate AND :endDate")
	List<DumpsterUsageRecord> findUsageByDumpsterIdAndDates(@Param("dumpster_id") int dumpster_id, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
	
}