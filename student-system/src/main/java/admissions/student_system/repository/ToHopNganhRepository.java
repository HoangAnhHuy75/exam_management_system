package admissions.student_system.repository;

import admissions.student_system.entity.ToHopNganh;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ToHopNganhRepository extends JpaRepository<ToHopNganh, Integer> {

    List<ToHopNganh> findByMaNganh(String maNganh);
}