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
    public double tinhDiemUuTien(
            double diem,
            double diemTHG,
            double diemDoiTuong,
            double diemKhuVuc,
            double diemCongThanhTich) {




        double MĐUT = diemDoiTuong + diemKhuVuc;

        if (MĐUT < 0) MĐUT = 0;

        double tongDiem = diemTHG + diemCongThanhTich;

        double diemUuTien;

        if (tongDiem < 22.5) {
            diemUuTien = MĐUT;
        } else {
            diemUuTien = ((30.0 - diem - diemCongThanhTich) / 7.5) * MĐUT;
        }

        if (diemUuTien < 0) {
            diemUuTien = 0.0;
        }

        return Math.round(diemUuTien * 100.0) / 100.0;
    }

    /**
     * Hàm tính điểm quy đổi cho từng môn V-SAT (thang 10)
     * Đảm bảo chặn trên, chặn dưới và nội suy chính xác.
     */
    public double tinhDiemMonVSAT(String phuongThuc, String monInput, double diem) {
        // 1. Chặn tuyệt đối đầu vào (Điểm âm hoặc bằng 0 trả về 0)
        if (diem <= 0) return 0.0;

        // 2. Chuẩn hóa mã môn học
        String monKey = monInput.toUpperCase();
        String mon;
        switch (monKey) {
            case "TOAN", "TO" -> mon = "TO";
            case "VAN", "VA"  -> mon = "VA";
            case "LY", "LI"   -> mon = "LI";
            case "HOA", "HO"  -> mon = "HO";
            case "SINH", "SI" -> mon = "SI";
            case "SU"         -> mon = "SU";
            case "DIA", "DI"  -> mon = "DI";
            case "ANH", "N1"  -> mon = "N1";
            default -> { return 0.0; }
        }

        // 3. Tìm khoảng chính xác (A <= diem <= B)
        BangQuyDoi row = repo.findKhoangChuaDiemTheoMon(phuongThuc, mon, diem)
                .stream().findFirst().orElse(null);

        // 4. Nếu không thấy khoảng khớp, thực hiện cơ chế chặn biên
        if (row == null) {
            // Tìm hàng "tiệm cận dưới" (Hàng có dDiemB lớn nhất nhưng vẫn <= điểm nhập vào)
            row = repo.findTiemCanDuoiTheoMon(phuongThuc, mon, diem);

            if (row == null) {
                // Kiểm tra trường hợp điểm quá cao (vượt trần cao nhất trong DB)
                row = repo.findMaxTheoMon(phuongThuc, mon);

                // Nếu điểm vẫn nhỏ hơn cả mức sàn thấp nhất (dDiemA) của hàng thấp nhất
                if (row != null && diem < row.getDDiemA().doubleValue()) {
                    return 0.0;
                }
            }
        }

        // Nếu DB hoàn toàn không có dữ liệu cho môn này
        if (row == null) return 0.0;

        // 5. Lấy các giá trị A, B, C, D từ hàng đã chọn
        BigDecimal a = row.getDDiemA();
        BigDecimal b = row.getDDiemB();
        BigDecimal c = row.getDDiemC();
        BigDecimal d = row.getDDiemD();

        // Kiểm tra dữ liệu DB tránh lỗi Null
        if (a == null || b == null || c == null || d == null) return 0.0;

        // 6. Ràng buộc giá trị x để nội suy (x không được nằm ngoài [a, b])
        BigDecimal x = BigDecimal.valueOf(diem);
        if (x.compareTo(a) < 0) x = a;
        if (x.compareTo(b) > 0) x = b;

        // 7. Tính tỷ lệ nội suy (Ratio)
        // Nếu a == b thì ratio = 1 (lấy mức điểm cao nhất d)
        BigDecimal ratio = (b.compareTo(a) == 0)
                ? BigDecimal.ONE
                : x.subtract(a).divide(b.subtract(a), 10, RoundingMode.HALF_UP);

        // 8. Công thức quy đổi: c + ratio * (d - c)
        BigDecimal result = c.add(ratio.multiply(d.subtract(c)));

        // 9. Chốt chặn cuối cùng (Kết quả phải nằm trong [0, 10])
        if (result.compareTo(BigDecimal.valueOf(10.0)) > 0) {
            result = BigDecimal.valueOf(10.0);
        }
        if (result.compareTo(BigDecimal.ZERO) < 0) {
            result = BigDecimal.ZERO;
        }

        return result.setScale(2, RoundingMode.HALF_UP).doubleValue();
    }
    public double tinhDiemQuyDoi(String phuongThuc, double diem, String toHopThiSinh, String toHopGoc) {

        if (diem <= 0) return 0.0;

        // 2. Tìm khoảng chính xác (A <= diem <= B)
        BangQuyDoi row = repo.findKhoangChuaDiem(phuongThuc, diem , toHopGoc)
                .stream().findFirst().orElse(null);

        // 3. Nếu không thấy khoảng chính xác
        if (row == null) {
            // Tìm thằng lớn nhất mà vẫn nhỏ hơn điểm thí sinh (Tiệm cận dưới)
            row = repo.findTiemCanDuoi(phuongThuc, toHopGoc, diem);

            // Nếu vẫn không thấy (nghĩa là điểm thí sinh còn nhỏ hơn cả mức thấp nhất của DB)
            if (row == null) {
                // Có thể trả về 0 hoặc lấy hàng thấp nhất tùy chính sách
                return 0.0;
            }

            // Nếu điểm thí sinh lớn hơn cả mức cao nhất trong DB
            BangQuyDoi maxRow = repo.findMaxAbsolute(phuongThuc, toHopGoc);
            if (diem > maxRow.getDDiemB().doubleValue()) {
                row = maxRow;
            }
        }
        BigDecimal a = row.getDDiemA();
        BigDecimal b = row.getDDiemB();
        if (a.compareTo(b) > 0) {
            a = b;
        }
        BigDecimal c = row.getDDiemC();
        BigDecimal d = row.getDDiemD();

        // Ràng buộc giá trị x để không vượt quá biên sau khi đã chọn được hàng
        // Ví dụ: Nhập 1200 nhưng hàng max chỉ đến 1122, thì x sẽ là 1122
        BigDecimal x = BigDecimal.valueOf(diem);
        if (x.compareTo(a) < 0) x = a;
        if (x.compareTo(b) > 0) x = b;

        // Tính tỷ lệ nội suy (Ratio)
        BigDecimal ratio = (b.compareTo(a) == 0)
                ? BigDecimal.ZERO
                : x.subtract(a).divide(b.subtract(a), 10, RoundingMode.HALF_UP);

        // Tính điểm quy đổi: c + ratio * (d - c)
        BigDecimal diemQuyDoi = c.add(ratio.multiply(d.subtract(c)));

        // Xử lý chênh lệch tổ hợp
        BigDecimal ketQua;
        if ("DGNL".equalsIgnoreCase(phuongThuc)) {
            ketQua = diemQuyDoi;
        } else {
            BigDecimal chenhLech = chenhLechService.findChenhLech(toHopGoc, toHopThiSinh);
            ketQua = diemQuyDoi.subtract(chenhLech);
            if (ketQua.compareTo(BigDecimal.ZERO) < 0) {
                ketQua = BigDecimal.ZERO;
            }
        }

        BigDecimal maxAllowed = new BigDecimal("30.0");
        if (ketQua.compareTo(maxAllowed) > 0) {
            ketQua = maxAllowed;
        }

        return ketQua.setScale(2, RoundingMode.HALF_UP).doubleValue();
    }
    public double diema(String phuongThuc, double diem, String toHopGoc) {
        if (diem <= 0) return 0.0;
        // Tìm khoảng chính xác, nếu không thấy thì tìm thằng tiệm cận dưới hoặc trên cùng
        BangQuyDoi row = repo.findKhoangChuaDiem(phuongThuc, diem, toHopGoc)
                .stream().findFirst()
                .orElseGet(() -> {
                    BangQuyDoi nearest = repo.findTiemCanDuoi(phuongThuc, toHopGoc, diem);
                    return (nearest != null) ? nearest : repo.findMaxAbsolute(phuongThuc, toHopGoc);
                });

        return (row != null && row.getDDiemA() != null) ? row.getDDiemA().doubleValue() : 0.0;
    }

    public double diemb(String phuongThuc, double diem, String toHopGoc) {
        if (diem <= 0) return 0.0;
        BangQuyDoi row = repo.findKhoangChuaDiem(phuongThuc, diem, toHopGoc)
                .stream().findFirst()
                .orElseGet(() -> {
                    BangQuyDoi nearest = repo.findTiemCanDuoi(phuongThuc, toHopGoc, diem);
                    return (nearest != null) ? nearest : repo.findMaxAbsolute(phuongThuc, toHopGoc);
                });

        return (row != null && row.getDDiemB() != null) ? row.getDDiemB().doubleValue() : 0.0;
    }

    public double diemc(String phuongThuc, double diem, String toHopGoc) {
        if (diem <= 0) return 0.0;
        BangQuyDoi row = repo.findKhoangChuaDiem(phuongThuc, diem, toHopGoc)
                .stream().findFirst()
                .orElseGet(() -> {
                    BangQuyDoi nearest = repo.findTiemCanDuoi(phuongThuc, toHopGoc, diem);
                    return (nearest != null) ? nearest : repo.findMaxAbsolute(phuongThuc, toHopGoc);
                });

        return (row != null && row.getDDiemC() != null) ? row.getDDiemC().doubleValue() : 0.0;
    }

    public double diemd(String phuongThuc, double diem, String toHopGoc) {
        if (diem <= 0) return 0.0;
        BangQuyDoi row = repo.findKhoangChuaDiem(phuongThuc, diem, "A01")
                .stream().findFirst()
                .orElseGet(() -> {
                    BangQuyDoi nearest = repo.findTiemCanDuoi(phuongThuc, toHopGoc, diem);
                    return (nearest != null) ? nearest : repo.findMaxAbsolute(phuongThuc, toHopGoc);
                });

        return (row != null && row.getDDiemD() != null) ? row.getDDiemD().doubleValue() : 0.0;
    }
    public double tinhDiemABCDVSAT(String phuongThuc, String mon, double diem, String value) {
        // 1. Chặn dưới tuyệt đối: Nếu điểm <= 0, trả về 0 luôn
        if (diem <= 0) return 0.0;

        // 2. Tìm khoảng chính xác (A < diem <= B)
        BangQuyDoi row = repo.findKhoangVSAT(phuongThuc, mon, diem)
                .stream()
                .findFirst()
                .orElse(null);

        // 3. Cơ chế chặn biên nếu không tìm thấy khoảng chính xác
        if (row == null) {
            // Tìm hàng có dDiemB lớn nhất nhưng vẫn nhỏ hơn điểm nhập vào
            row = repo.findTiemCanDuoiTheoMon(phuongThuc, mon, diem);

            // Nếu vẫn không thấy, tìm hàng có điểm cao nhất (chặn trên)
            if (row == null) {
                row = repo.findMaxTheoMon(phuongThuc, mon);

                // Nếu điểm nhập vào thậm chí còn nhỏ hơn cả mức sàn thấp nhất của môn đó
                if (row != null && diem < row.getDDiemA().doubleValue()) {
                    return 0.0;
                }
            }
        }

        // 4. Nếu DB hoàn toàn không có dữ liệu cho môn này
        if (row == null) return 0.0;

        BigDecimal result;
        switch (value.toLowerCase()) {
            case "a" -> result = row.getDDiemA();
            case "b" -> result = row.getDDiemB();
            case "c" -> result = row.getDDiemC();
            case "d" -> result = row.getDDiemD();
            default -> throw new IllegalArgumentException("Value phải là a, b, c hoặc d");
        }

        return (result != null) ? result.setScale(2, RoundingMode.HALF_UP).doubleValue() : 0.0;
    }

}
