package admissions.student_system.service;

import admissions.student_system.entity.NguyenVong;
import admissions.student_system.repository.NguyenVongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class NguyenVongService {

    @Autowired
    private NguyenVongRepository repo;

    public Optional<NguyenVong> getKetQuaTrungTuyen(String cccd) {
        List<NguyenVong> list = repo.findByNvCccdOrderByNvTtAsc(cccd);
        return list.stream()
                .filter(nv -> "Trúng tuyển".equalsIgnoreCase(nv.getNvKetqua()))
                .findFirst();
    }

    public List<NguyenVong> getAllByCccd(String cccd) {
        return repo.findByNvCccdOrderByNvTtAsc(cccd);
    }
}