package admissions.student_system.controller;

import admissions.student_system.service.BangQuyDoiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class BangQuyDoiController {

    @Autowired
    private BangQuyDoiService bangQuyDoiService;



    @GetMapping("/api/quydoi")
    @ResponseBody
    public double tinhQuyDoi(
            @RequestParam String phuongThuc,
            @RequestParam double diem,
            @RequestParam String toHop,
            @RequestParam String toHopGoc

    ) {
        return bangQuyDoiService.tinhDiemQuyDoi(phuongThuc, diem, toHop,toHopGoc);
    }

    @GetMapping("/api/uutien")
    @ResponseBody
    public double tinhUuTien(
            @RequestParam String phuongThuc,
            @RequestParam double diem,
            @RequestParam String toHopThiSinh,
            @RequestParam String toHopGoc,
            @RequestParam double diemDoiTuong,
            @RequestParam double diemKhuVuc,
            @RequestParam(defaultValue = "0.0") double diemCongThanhTich) {

        return bangQuyDoiService.tinhDiemUuTien(
                phuongThuc,
                diem,
                toHopThiSinh,
                toHopGoc,
                diemDoiTuong,
                diemKhuVuc,
                diemCongThanhTich
        );
    }

    @GetMapping("/api/a")
    @ResponseBody
    public double diema(
            @RequestParam String phuongThuc,
            @RequestParam double diem,
            @RequestParam String toHopGoc

            ) {
        return bangQuyDoiService.diema(phuongThuc, diem, toHopGoc);
    }

    @GetMapping("/api/b")
    @ResponseBody
    public double diemb(
            @RequestParam String phuongThuc,
            @RequestParam String toHopGoc,
            @RequestParam double diem
    ) {
        return bangQuyDoiService.diemb(phuongThuc, diem, toHopGoc);
    }

    @GetMapping("/api/c")
    @ResponseBody
    public double diemc(
            @RequestParam String phuongThuc,
            @RequestParam String toHopGoc,
            @RequestParam double diem
    ) {
        return bangQuyDoiService.diemc(phuongThuc, diem,  toHopGoc);
    }

    @GetMapping("/api/d")
    @ResponseBody
    public double diemd(
            @RequestParam String phuongThuc,
            @RequestParam String toHopGoc,
            @RequestParam double diem
    ) {
        return bangQuyDoiService.diemd(phuongThuc, diem, toHopGoc);
    }

}