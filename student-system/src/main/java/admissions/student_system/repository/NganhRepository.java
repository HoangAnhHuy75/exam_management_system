package admissions.student_system.repository;

import admissions.student_system.entity.Nganh;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NganhRepository extends JpaRepository<Nganh, Integer> {
}