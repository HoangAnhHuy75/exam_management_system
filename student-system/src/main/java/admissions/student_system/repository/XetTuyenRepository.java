package admissions.student_system.repository;

import admissions.student_system.entity.XetTuyen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface XetTuyenRepository extends JpaRepository<XetTuyen, Integer> {

    List<XetTuyen> findByCccd(String cccd);}