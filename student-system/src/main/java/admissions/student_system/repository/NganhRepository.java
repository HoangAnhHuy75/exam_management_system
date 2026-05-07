package admissions.student_system.repository;

import admissions.student_system.entity.Nganh;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NganhRepository extends JpaRepository<Nganh, Integer> {
    Optional<Nganh> findByMaNganh(String ma);

}