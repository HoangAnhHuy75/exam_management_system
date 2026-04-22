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
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/nguyenvong")
public class NguyenVongXetTuyenController {


    @Autowired
    private NguyenVongXetTuyenService service;
    @Autowired
    private NganhService nganhService;


    @PostMapping("/submit")
    public String submit(
            @RequestParam("maNganh[]") List<String> maNganh,
            @RequestParam("nvTt[]") List<Integer> nvTt,
            @RequestParam("ttPhuongThuc[]") List<String> ttPhuongThuc,
            HttpSession session
    ) {

        Student st = (Student) session.getAttribute("student");

        List<NguyenVongXetTuyen> list = new java.util.ArrayList<>();

        int size = Math.min(maNganh.size(),
                Math.min(nvTt.size(), ttPhuongThuc.size()));

        for (int i = 0; i < size; i++) {

            NguyenVongXetTuyen nv = new NguyenVongXetTuyen();

            nv.setMaNganh(maNganh.get(i));
            nv.setNvTt(nvTt.get(i));
            nv.setTtPhuongThuc(ttPhuongThuc.get(i));
            nv.setCccd(st.getCccd());
            list.add(nv);
        }

        service.createAll(list);

        return "redirect:/dashboard";
    }

}
