package admissions.student_system.controller;

import admissions.student_system.entity.NguyenVongXetTuyen;
import admissions.student_system.entity.Student;
import admissions.student_system.entity.Nganh;
import admissions.student_system.entity.ToHopNganh;
import admissions.student_system.service.NganhService;

import admissions.student_system.service.NguyenVongXetTuyenService;
import admissions.student_system.service.ToHopNganhService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class NguyenVongController {

    private final NganhService nganhService;
    private final ToHopNganhService toHopNganhService;
    public NguyenVongController(NganhService nganhService,ToHopNganhService toHopNganhService) {
        this.nganhService = nganhService;
        this.toHopNganhService =  toHopNganhService;
    }
    @Autowired
    private NguyenVongXetTuyenService service;


    @GetMapping("/dangkynguyenvong")
    public String nguyenVong(HttpSession session, Model model) {

        System.out.println("1. ENTER /dangky");

        Object sessObj = session.getAttribute("student");
        System.out.println("2. SESSION RAW student = " + sessObj);

        Student student = (Student) sessObj;

        System.out.println("3. AFTER CAST student = " + student);

        if (student == null) {
            System.out.println("4. student == NULL -> REDIRECT LOGIN");
            return "redirect:/login";
        }

        System.out.println("5. student OK -> cccd = " + student.getCccd());

        List<Nganh> listNganh = nganhService.getAll();
        System.out.println("6. loaded listNganh size = " + listNganh.size());

        List<NguyenVongXetTuyen> listNV =
                service.findByCccd(student.getCccd());

        System.out.println("7. loaded listNV size = " +
                (listNV == null ? "null" : listNV.size()));

        model.addAttribute("student", student);
        model.addAttribute("listNganh", listNganh);
        model.addAttribute("listNV", listNV);

        System.out.println("8. MODEL ADDED DONE");

        return "dangkynguyenvong";
    }
    @GetMapping("/api/tohop/{maNganh}")
    @ResponseBody
    public List<ToHopNganh> getToHop(@PathVariable String maNganh) {
        return toHopNganhService.getByMaNganh(maNganh);
    }
}