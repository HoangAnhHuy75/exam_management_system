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
        System.out.println("CCCD nhập vào: " + cccd);
        System.out.println("Mật khẩu nhập vào: " + password);

        // 1. Check đúng CCCD và Mật khẩu (Ngày sinh) hay không
        Student student = authenService.login(cccd, password);

        if (student == null) {
            System.out.println(">>> KẾT QUẢ: Đăng nhập THẤT BẠI (Sai CCCD hoặc mật khẩu)");
            model.addAttribute("error", "Số CCCD hoặc Ngày sinh không chính xác!");
            return "login";
        }

        System.out.println(">>> KẾT QUẢ: Đăng nhập THÀNH CÔNG cho thí sinh: " + student.getCccd());

        // 2. Lấy dữ liệu nguyện vọng
        List<NguyenVong> danhSachNv = nguyenVongService.getAllByCccd(cccd);
        model.addAttribute("danhSachNv", danhSachNv);

        if (danhSachNv == null || danhSachNv.isEmpty()) {
            System.out.println(">>> KẾT QUẢ: Không có dữ liệu nguyện vọng.");
            model.addAttribute("message", "Không tìm thấy dữ liệu đăng ký xét tuyển.");
        } else {
            // Log chi tiết từng nguyện vọng để kiểm tra trạng thái trong DB
            danhSachNv.forEach(nv -> {
                System.out.println("--- NV Thứ tự: " + nv.getNvTt() + " | Ngành: " + nv.getNvManganh() + " | Kết quả: " + nv.getNvKetqua());
            });

            // 3. Lọc kết quả trúng tuyển
            Optional<NguyenVong> trungTuyen = danhSachNv.stream()
                    .filter(nv -> "Trúng tuyển".equalsIgnoreCase(nv.getNvKetqua()))
                    .findFirst();

            if (trungTuyen.isPresent()) {
                NguyenVong nvHopLe = trungTuyen.get();
                System.out.println(">>> KẾT QUẢ: TRÚNG TUYỂN ngành " + nvHopLe.getNvManganh());
                model.addAttribute("ketQua", "TRUNG_TUYEN");
                model.addAttribute("nv", nvHopLe);
            } else {
                System.out.println(">>> KẾT QUẢ: KHÔNG TRÚNG TUYỂN (Tất cả NV đều không đạt)");
                model.addAttribute("ketQua", "KHONG_TRUNG_TUYEN");
            }
        }

        System.out.println("========== KẾT THÚC TRA CỨU ==========");
        return "ketquaxettuyen";
    }
}