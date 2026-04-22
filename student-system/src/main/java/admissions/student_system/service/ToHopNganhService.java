package admissions.student_system.service;

import admissions.student_system.entity.ToHopNganh;
import admissions.student_system.repository.ToHopNganhRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ToHopNganhService {

    private final ToHopNganhRepository repo;

    public ToHopNganhService(ToHopNganhRepository repo) {
        this.repo = repo;
    }

    public List<ToHopNganh> getByMaNganh(String maNganh) {
        return repo.findByMaNganh(maNganh);
    }
}