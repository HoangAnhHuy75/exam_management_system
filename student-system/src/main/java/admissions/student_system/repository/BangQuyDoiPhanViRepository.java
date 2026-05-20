package admissions.student_system.repository;
import admissions.student_system.entity.BangQuyDoiPhanVi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BangQuyDoiPhanViRepository extends JpaRepository<BangQuyDoiPhanVi, Integer> {

    @Query("""
        SELECT b FROM BangQuyDoiPhanVi b
        WHERE b.phuongThuc = :pt
          AND b.mon = :mon
          AND b.toHop = :toHop
          AND :diem BETWEEN b.diemA AND b.diemB
    """)
    List<BangQuyDoiPhanVi> findKhoang(
            @Param("pt") String phuongThuc,
            @Param("mon") String mon,
            @Param("toHop") String toHop,
            @Param("diem") double diem
    );
}