package admissions.student_system.repository;

import admissions.student_system.entity.BangQuyDoi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BangQuyDoiRepository extends JpaRepository<BangQuyDoi, Integer> {

    @Query("""
    SELECT b FROM BangQuyDoi b
    WHERE b.dPhuongThuc = :pt
    AND b.dToHop = :toHop
    AND :score BETWEEN b.dDiemA AND b.dDiemB
    ORDER BY (b.dDiemB - b.dDiemA) ASC
""")
    List<BangQuyDoi> findKhoangChuaDiem(
            @Param("pt") String phuongThuc,
            @Param("score") Double score,
            @Param("toHop") String toHop
    );

    @Query("""
SELECT b FROM BangQuyDoi b
WHERE b.dPhuongThuc = :phuongThuc
  AND b.dMon = :mon
  AND :diem > b.dDiemA
  AND :diem <= b.dDiemB
ORDER BY b.dDiemA DESC
""")
    List<BangQuyDoi> findKhoangVSAT(
            String phuongThuc,
            String mon,
            double diem
    );

    @Query("""
    SELECT b FROM BangQuyDoi b
    WHERE b.dPhuongThuc = :phuongThuc
      AND b.dMon = :mon
      AND :diem BETWEEN b.dDiemA AND b.dDiemB
""")
    List<BangQuyDoi> findKhoangChuaDiemTheoMon(
            String phuongThuc,
            String mon,
            double diem
    );
}
