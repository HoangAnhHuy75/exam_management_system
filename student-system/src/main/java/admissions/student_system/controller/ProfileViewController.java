package admissions.student_system.controller;

import admissions.student_system.entity.Student;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class ProfileViewController {
    @GetMapping("/dashboard")
    public String home(HttpSession session , Model model) {

        Student student = (Student) session.getAttribute("student");

        if (student == null) {
            return "redirect:/login";
        }
        model.addAttribute("activePage", "profile");
        model.addAttribute("student", student);
        return "profile";
    }
}
