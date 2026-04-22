package admissions.student_system.repository;

import admissions.student_system.entity.NguyenVongXetTuyen;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NguyenVongXetTuyenRepository extends JpaRepository<NguyenVongXetTuyen, Integer> {
    List<NguyenVongXetTuyen> findByCccd(String cccd);
    void deleteByCccd(String cccd);
}
