package ecoembes.dao;


import ecoembes.entity.Dumpster;
import ecoembes.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface DumpsterRepository extends JpaRepository<Dumpster, Integer> {
    
	//needed methods
    List<Dumpster> findByCurrentStatus(Status status); 

    List<Dumpster> findByPostalCode(int postalCode);
}