package nam.tran.repository;

import nam.tran.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IStudentRepository extends JpaRepository<Student,Long> {
Boolean existsByName(String name);
List<Student> findAllByNameContaining(String name);
}
