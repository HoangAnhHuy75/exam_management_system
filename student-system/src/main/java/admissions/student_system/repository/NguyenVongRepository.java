package admissions.student_system.repository;

import admissions.student_system.entity.NguyenVong;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface NguyenVongRepository extends JpaRepository<NguyenVong, Integer> {
    List<NguyenVong> findByNvCccdOrderByNvTtAsc(String nvCccd);
}