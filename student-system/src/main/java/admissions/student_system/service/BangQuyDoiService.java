package admissions.student_system.service;

import admissions.student_system.entity.BangQuyDoi;
import admissions.student_system.repository.BangQuyDoiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class BangQuyDoiService {

    @Autowired
    private BangQuyDoiRepository repo;
    @Autowired
    private BangChenhLechService chenhLechService;

    public double tinhDiemUuTien(String phuongThuc,
                                 double diem,
                                 String toHopThiSinh,
                                 String toHopGoc,
                                 double diemDoiTuong,
                                 double diemKhuVuc,
                                 double diemCongThanhTich) {

        double diemQuyDoi = tinhDiemQuyDoi(phuongThuc, diem, toHopThiSinh, toHopGoc);

        double MĐUT = diemDoiTuong + diemKhuVuc;

        if (MĐUT < 0) MĐUT = 0;

        double tongDiem = diemQuyDoi + diemCongThanhTich;

        double diemUuTien;

        if (tongDiem < 22.5) {
            diemUuTien = MĐUT;
        } else {
            diemUuTien = ((30.0 - diemQuyDoi - diemCongThanhTich) / 7.5) * MĐUT;
        }

        if (diemUuTien < 0) {
            diemUuTien = 0.0;
        }

        return Math.round(diemUuTien * 100.0) / 100.0;
    }

    public double tinhDiemMonVSAT(
            String phuongThuc,
            String monInput,
            double diem
    ) {

        String monKey = monInput.toUpperCase();

        String mon;
        switch (monKey) {
            case "TOAN", "TO" -> mon = "TO";
            case "VAN", "VA" -> mon = "VA";
            case "LY", "LI" -> mon = "LI";
            case "HOA", "HO" -> mon = "HO";
            case "SINH", "SI" -> mon = "SI";
            case "SU" -> mon = "SU";
            case "DIA", "DI" -> mon = "DI";
            case "ANH", "N1" -> mon = "N1";
            default -> {
                return 0.0;
            }
        }

        List<BangQuyDoi> list = repo.findKhoangChuaDiemTheoMon(phuongThuc, mon, diem);

        if (list == null || list.isEmpty()) {
            return 0.0;
        }

        BangQuyDoi row = list.get(0);

        BigDecimal a = row.getDDiemA();
        BigDecimal b = row.getDDiemB();
        BigDecimal c = row.getDDiemC();
        BigDecimal d = row.getDDiemD();

        if (a == null || b == null || c == null || d == null) {
            return 0.0;
        }

        BigDecimal x = BigDecimal.valueOf(diem);

        BigDecimal ratio = (b.compareTo(a) == 0)
                ? BigDecimal.ZERO
                : x.subtract(a)
                .divide(b.subtract(a), 10, RoundingMode.HALF_UP);

        BigDecimal result = c.add(ratio.multiply(d.subtract(c)));

        return result.setScale(15, RoundingMode.HALF_UP).doubleValue();
    }
    public double tinhDiemQuyDoi(
            String phuongThuc,
            double diem,
            String toHopThiSinh,
            String toHopGoc
    ) {

        // 1. nội suy điểm theo phương thức
        BangQuyDoi row = repo.findKhoangChuaDiem(phuongThuc, diem, toHopGoc)
                .stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException(
                        "Không tìm thấy khoảng cho phương thức: " + phuongThuc + " và điểm: " + diem
                ));

        BigDecimal a = row.getDDiemA();
        BigDecimal b = row.getDDiemB();
        BigDecimal c = row.getDDiemC();
        BigDecimal d = row.getDDiemD();

        if (a == null || b == null || c == null || d == null) {
            throw new RuntimeException("Dữ liệu quy đổi bị null");
        }

        BigDecimal x = BigDecimal.valueOf(diem);

        BigDecimal ratio = (b.compareTo(a) == 0)
                ? BigDecimal.ZERO
                : x.subtract(a).divide(b.subtract(a), 10, RoundingMode.HALF_UP);

        BigDecimal diemQuyDoi = c.add(ratio.multiply(d.subtract(c)));

        // =========================
        // CHỈ THPT MỚI CÓ CHÊNH LỆCH
        // =========================
        BigDecimal ketQua;

        if ("DGNL".equalsIgnoreCase(phuongThuc)) {
            ketQua = diemQuyDoi;
        } else {
            BigDecimal chenhLech = chenhLechService.findChenhLech(
                    toHopGoc,
                    toHopThiSinh
            );

            ketQua = diemQuyDoi.subtract(chenhLech);
        }

        return ketQua.setScale(2, RoundingMode.HALF_UP).doubleValue();
    }    public double diema(String phuongThuc, double diem,String toHopGoc) {
        BangQuyDoi row = repo.findKhoangChuaDiem(phuongThuc, diem,toHopGoc)
                .stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException(
                        "Không tìm thấy khoảng cho phương thức: " + phuongThuc + " và điểm: " + diem
                ));

        if (row.getDDiemA() == null) {
            throw new RuntimeException("diemA bị null");
        }

        return row.getDDiemA().doubleValue();
    }

    public double diemb(String phuongThuc, double diem,String toHopGoc) {
        BangQuyDoi row = repo.findKhoangChuaDiem(phuongThuc, diem,toHopGoc)
                .stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException(
                        "Không tìm thấy khoảng cho phương thức: " + phuongThuc + " và điểm: " + diem
                ));

        if (row.getDDiemB() == null) {
            throw new RuntimeException("diemB bị null");
        }

        return row.getDDiemB().doubleValue();
    }

    public double diemc(String phuongThuc, double diem,String toHopGoc) {
        BangQuyDoi row = repo.findKhoangChuaDiem(phuongThuc, diem,  toHopGoc)
                .stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException(
                        "Không tìm thấy khoảng cho phương thức: " + phuongThuc + " và điểm: " + diem
                ));

        if (row.getDDiemC() == null) {
            throw new RuntimeException("diemC bị null");
        }

        return row.getDDiemC().doubleValue();
    }

    public double diemd(String phuongThuc, double diem,String toHopGoc) {
        BangQuyDoi row = repo.findKhoangChuaDiem(phuongThuc, diem,toHopGoc)
                .stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException(
                        "Không tìm thấy khoảng cho phương thức: " + phuongThuc + " và điểm: " + diem
                ));

        if (row.getDDiemD() == null) {
            throw new RuntimeException("diemD bị null");
        }

        return row.getDDiemD().doubleValue();
    }
    public double tinhDiemABCDVSAT(String phuongThuc, String mon, double diem, String value) {

        BangQuyDoi row = repo.findKhoangVSAT(phuongThuc, mon, diem)
                .stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException(
                        "Không tìm thấy khoảng VSAT cho môn: " + mon + " với điểm: " + diem
                ));

        BigDecimal result;

        switch (value.toLowerCase()) {
            case "a":
                result = row.getDDiemA();
                break;
            case "b":
                result = row.getDDiemB();
                break;
            case "c":
                result = row.getDDiemC();
                break;
            case "d":
                result = row.getDDiemD();
                break;
            default:
                throw new RuntimeException("Value phải là a, b, c hoặc d");
        }

        if (result == null) {
            throw new RuntimeException("Dữ liệu bị null");
        }

        return result.setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

}
