package plasSB.dao;


import plasSB.entity.CapacityRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.Optional;


@Repository
public interface CapacityRepository extends JpaRepository<CapacityRecord, LocalDate> {
    
    Optional<CapacityRecord> findByDate(LocalDate date);

}


