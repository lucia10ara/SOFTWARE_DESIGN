package ecoembes.dao;


import ecoembes.entity.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssignmentRepository extends JpaRepository<Assignment, Integer> {
    // El Integer es la clave primaria (assignment_id)
}