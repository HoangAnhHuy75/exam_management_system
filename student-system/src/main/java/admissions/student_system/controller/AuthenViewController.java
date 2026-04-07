package admissions.student_system.controller;

import admissions.student_system.entity.Student;
import admissions.student_system.service.AuthenService;
import org.springframework.ui.Model;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthenViewController {
    @Autowired
    AuthenService authenService;
    @GetMapping("/login")
    public String loginPage() {
        return "login"; // login.html
    }
    @PostMapping("/login")
    public String processLogin(
            @RequestParam String cccd,
            @RequestParam String password,
            Model model,
            HttpSession session) {

        try {
            Student student = authenService.login(cccd, password);
            if (student == null) {
                return "redirect:/login";
            }
            // Lưu student vào session để dùng cho các trang khác
            session.setAttribute("student", student);

            return "redirect:/dashboard";
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            return "login"; // login thất bại -> hiện lại form với lỗi
        }
    }
    @GetMapping("/logout")
    public String logout(HttpSession session) {

        session.invalidate();

        return "redirect:/login";
    }
}
