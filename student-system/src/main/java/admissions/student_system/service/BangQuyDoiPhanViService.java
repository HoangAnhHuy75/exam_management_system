package admissions.student_system.service;

import admissions.student_system.entity.BangQuyDoiPhanVi;
import admissions.student_system.repository.BangQuyDoiPhanViRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Comparator;
import java.util.List;

@Service
public class BangQuyDoiPhanViService {

    private final BangQuyDoiPhanViRepository repo;

    public BangQuyDoiPhanViService(BangQuyDoiPhanViRepository repo) {
        this.repo = repo;
    }

    public double tinhDiemVSAT(String mon, double diem, String phuongThuc, String toHop) {

        List<BangQuyDoiPhanVi> list =
                repo.findKhoang(phuongThuc, mon, toHop, diem);

        if (list == null || list.isEmpty()) {
            throw new RuntimeException("Không tìm thấy khoảng VSAT");
        }

        BangQuyDoiPhanVi row = list.get(0);

        BigDecimal a = row.getDiemA();
        BigDecimal b = row.getDiemB();
        BigDecimal c = row.getDiemC();
        BigDecimal d = row.getDiemD();

        BigDecimal x = BigDecimal.valueOf(diem);

        BigDecimal ratio = (b.compareTo(a) == 0)
                ? BigDecimal.ZERO
                : x.subtract(a)
                .divide(b.subtract(a), 10, RoundingMode.HALF_UP);

        BigDecimal result = c.add(ratio.multiply(d.subtract(c)));

        return result.setScale(2, RoundingMode.HALF_UP).doubleValue();
    }
}