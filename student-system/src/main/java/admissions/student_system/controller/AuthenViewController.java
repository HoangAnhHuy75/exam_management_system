package admissions.student_system.controller;

import admissions.student_system.entity.NguyenVong;
import admissions.student_system.entity.Student;
import admissions.student_system.entity.XetTuyen;
import admissions.student_system.repository.XetTuyenRepository;
import admissions.student_system.service.AuthenService;
import admissions.student_system.service.NguyenVongService;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
@Controller
public class AuthenViewController {

    @Autowired
    private AuthenService authenService;

    @Autowired
    private NguyenVongService nguyenVongService;
    @Autowired
    private XetTuyenRepository xetTuyenRepository;

    @GetMapping("/login")
    public String dm() {
        return "login";
    }
    @GetMapping("/ketquaxettuyen")
    public String keta() {
        return "ketquaxettuyen";
    }

    @PostMapping("/tracuuketqua")
    public String processTraCuu(@RequestParam String cccd,
                                @RequestParam String password,
                                Model model) {

        System.out.println("========== BẮT ĐẦU TRA CỨU ==========");

        try {
            // 1. Thực hiện login và định dạng ngày sinh trong Service
            Student student = authenService.login(cccd, password);

// 2. Lấy dữ liệu nguyện vọng và xét tuyển
            List<NguyenVong> danhSachNv = nguyenVongService.getAllByCccd(student.getCccd());
            List<XetTuyen> danhSachXt = xetTuyenRepository.findByCccd(student.getCccd());

// 3. CẬP NHẬT KẾT QUẢ CHO TỪNG NGUYỆN VỌNG THEO KẾT QUẢ XÉT TUYỂN
            if (danhSachNv != null && danhSachXt != null) {
                for (NguyenVong nv : danhSachNv) {
                    // Tìm dòng xét tuyển có cùng mã ngành với nguyện vọng này
                    Optional<XetTuyen> xtTuongUng = danhSachXt.stream()
                            .filter(xt -> xt.getMaNganh() != null && xt.getMaNganh().equalsIgnoreCase(nv.getNvManganh()))
                            .findFirst();

                    if (xtTuongUng.isPresent()) {
                        String ketQuaXt = xtTuongUng.get().getKetQua();
                        // Nếu bảng xét tuyển ghi là "Đậu", ta chuẩn hóa thành "Trúng tuyển" để đồng bộ với View HTML
                        if ("Đậu".equalsIgnoreCase(ketQuaXt) || "Trúng tuyển".equalsIgnoreCase(ketQuaXt)) {
                            nv.setNvKetqua("Trúng tuyển");
                        } else {
                            nv.setNvKetqua(ketQuaXt); // Ví dụ: "Trượt", "Tạch",...
                        }
                    } else {
                        nv.setNvKetqua("Chưa có kết quả");
                    }
                }
            }

// Gửi dữ liệu thông tin thí sinh ra View
            model.addAttribute("student", student);
            model.addAttribute("danhSachNv", danhSachNv);
            model.addAttribute("studentName", student.getTen());
            model.addAttribute("cccd", student.getCccd());

// 4. XỬ LÝ KHỐI BANNER TRÚNG TUYỂN CHÍNH
            if (danhSachNv == null || danhSachNv.isEmpty()) {
                model.addAttribute("message", "Thí sinh chưa có dữ liệu nguyện vọng.");
                model.addAttribute("ketQua", "KHONG_TRUNG_TUYEN");
            } else {
                // Tìm xem trong danh sách Nguyện Vọng của thí sinh, có nguyện vọng nào đã được cập nhật thành "Trúng tuyển" không
                // Đồng thời sắp xếp hoặc chọn nguyện vọng có thứ tự ưu tiên nhỏ nhất (nv_tt) nếu trúng tuyển nhiều ngành
                Optional<NguyenVong> nvTrungTuyen = danhSachNv.stream()
                        .filter(nv -> "Trúng tuyển".equals(nv.getNvKetqua()))
                        .min(Comparator.comparingInt(NguyenVong::getNvTt)); // Lấy nguyện vọng số 1, số 2 cao nhất

                if (nvTrungTuyen.isPresent()) {
                    model.addAttribute("ketQua", "TRUNG_TUYEN");
                    model.addAttribute("nv", nvTrungTuyen.get()); // ĐẨY ĐÚNG ĐỐI TƯỢNG NGUYỆN VỌNG VỚI KEY "nv" RA VIEW
                } else {
                    model.addAttribute("ketQua", "KHONG_TRUNG_TUYEN");
                }
            }

            System.out.println(">>> Đăng nhập THÀNH CÔNG cho: " + student.getHo() + " " + student.getTen());
            return "ketquaxettuyen";

        } catch (RuntimeException e) {
            // Xử lý khi sai CCCD hoặc sai định dạng ngày sinh
            System.out.println(">>> LỖI: " + e.getMessage());
            model.addAttribute("error", e.getMessage());
            return "login"; // Quay lại trang login và hiển thị lỗi
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Lỗi hệ thống! Vui lòng thử lại sau.");
            return "login";
        }
    }
}