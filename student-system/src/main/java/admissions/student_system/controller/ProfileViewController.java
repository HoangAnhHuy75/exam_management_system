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
        System.out.println("SESSION STUDENT1111222111111222221111111111111122211111111111111111111 = " + session.getAttribute("student"));

        if (student == null) {
            return "redirect:/login";
        }
        System.out.println("SESSION STUDENT111111111233311111111111113333111111111111111111111 = " + session.getAttribute("student"));

        model.addAttribute("activePage", "profile");
        model.addAttribute("student", student);
        return "profile";
    }
}
