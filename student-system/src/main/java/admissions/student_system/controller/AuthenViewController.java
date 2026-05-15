package admissions.student_system.controller;

import admissions.student_system.entity.NguyenVong;
import admissions.student_system.entity.Student;
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

            // 2. Lấy dữ liệu nguyện vọng (Sử dụng cccd đã trim từ đối tượng student)
            List<NguyenVong> danhSachNv = nguyenVongService.getAllByCccd(student.getCccd());

            // Gửi dữ liệu ra View
            model.addAttribute("student", student);
            model.addAttribute("danhSachNv", danhSachNv);
            model.addAttribute("studentName", student.getTen());            model.addAttribute("cccd", student.getCccd());



            if (danhSachNv == null || danhSachNv.isEmpty()) {
                model.addAttribute("message", "Thí sinh chưa đăng ký nguyện vọng xét tuyển.");
            } else {
                // 3. Tìm nguyện vọng trúng tuyển (Ưu tiên nguyện vọng có số thứ tự NvTt nhỏ nhất)
                Optional<NguyenVong> trungTuyen = danhSachNv.stream()
                        .filter(nv -> "Trúng tuyển".equalsIgnoreCase(nv.getNvKetqua()))
                        .min(Comparator.comparingInt(NguyenVong::getNvTt));

                if (trungTuyen.isPresent()) {
                    model.addAttribute("ketQua", "TRUNG_TUYEN");
                    model.addAttribute("nv", trungTuyen.get());
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