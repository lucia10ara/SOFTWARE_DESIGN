package ecoembes.dao;


import ecoembes.entity.RecyclingPlant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecyclingPlantRepository extends JpaRepository<RecyclingPlant, Integer> {
    // Hereda save(), findById(), etc.
}

