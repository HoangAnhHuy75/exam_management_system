package admissions.student_system.service;


import admissions.student_system.entity.Nganh;
import admissions.student_system.repository.NganhRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NganhService {

    private final NganhRepository repo;

    public NganhService(NganhRepository repo) {
        this.repo = repo;
    }

    public List<Nganh> getAll() {
        return repo.findAll();
    }

    public Nganh getById(Integer id) {
        return repo.findById(id).orElse(null);
    }
}