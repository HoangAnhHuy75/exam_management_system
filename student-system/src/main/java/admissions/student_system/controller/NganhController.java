package admissions.student_system.controller;

import admissions.student_system.entity.Nganh;
import admissions.student_system.service.NganhService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class NganhController {

    private final NganhService nganhService;

    public NganhController(NganhService nganhService) {
        this.nganhService = nganhService;
    }

    @GetMapping("/api/nganh")
    public ResponseEntity<Nganh> getByMaNganh(@RequestParam String maNganh) {
        Nganh nganh = nganhService.getByMaNganh(maNganh);

        if (nganh == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(nganh);
    }
}