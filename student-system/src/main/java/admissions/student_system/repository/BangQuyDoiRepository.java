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
    // Tìm hàng có dDiemB lớn nhất nhưng vẫn nhỏ hơn hoặc bằng điểm của thí sinh
    @Query("""
    SELECT b FROM BangQuyDoi b 
    WHERE b.dPhuongThuc = :pt AND b.dToHop = :th AND b.dDiemB <= :score 
    ORDER BY b.dDiemB DESC LIMIT 1
""")
    BangQuyDoi findTiemCanDuoi(@Param("pt") String phuongThuc, @Param("th") String toHop, @Param("score") Double score);

    // Tìm hàng có dDiemB cao nhất (dành cho trường hợp điểm quá cao)
    @Query("SELECT b FROM BangQuyDoi b WHERE b.dPhuongThuc = :pt AND b.dToHop = :th ORDER BY b.dDiemB DESC LIMIT 1")
    BangQuyDoi findMaxAbsolute(@Param("pt") String phuongThuc, @Param("th") String toHop);
    // Tìm hàng tiệm cận dưới cho từng môn
    @Query("""
    SELECT b FROM BangQuyDoi b 
    WHERE b.dPhuongThuc = :pt AND b.dMon = :mon AND b.dDiemB <= :score 
    ORDER BY b.dDiemB DESC LIMIT 1
""")
    BangQuyDoi findTiemCanDuoiTheoMon(@Param("pt") String pt, @Param("mon") String mon, @Param("score") Double score);

    // Tìm hàng có điểm cao nhất của môn đó
    @Query("SELECT b FROM BangQuyDoi b WHERE b.dPhuongThuc = :pt AND b.dMon = :mon ORDER BY b.dDiemB DESC LIMIT 1")
    BangQuyDoi findMaxTheoMon(@Param("pt") String pt, @Param("mon") String mon);
}
