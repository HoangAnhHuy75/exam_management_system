package admissions.student_system.repository;

import admissions.student_system.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
public interface StudentRepository extends JpaRepository<Student, Integer> {
    Optional<Student> findByCccd(String cccd);
}
