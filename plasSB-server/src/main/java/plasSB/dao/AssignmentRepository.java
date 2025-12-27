package plasSB.dao;

import plasSB.entity.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface AssignmentRepository extends JpaRepository<Assignment, Long> {
    
    List<Assignment> findByDate(LocalDate date);
    
 // ✅ CORRECTO - Sumar de Assignment
    @Query("SELECT COALESCE(SUM(a.loadInTons), 0) FROM Assignment a WHERE a.date = :date")
    int getTotalLoadByDate(@Param("date") LocalDate date);


}