package admissions.student_system.service;

import admissions.student_system.entity.NguyenVongXetTuyen;
import admissions.student_system.repository.NguyenVongXetTuyenRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NguyenVongXetTuyenService {

    @Autowired
    private NguyenVongXetTuyenRepository repository;

    @Transactional
    public List<NguyenVongXetTuyen> createAll(List<NguyenVongXetTuyen> list) {

        if (list == null || list.isEmpty()) {
            return new java.util.ArrayList<>();
        }

        String cccd = list.get(0).getCccd();

        // ❗ 1. XÓA NGUYỆN VỌNG CŨ
        repository.deleteByCccd(cccd);

        List<Integer> thuTuList = new java.util.ArrayList<>();
        List<NguyenVongXetTuyen> result = new java.util.ArrayList<>();

        for (NguyenVongXetTuyen nv : list) {

            // ❗ 2. CHECK TRÙNG THỨ TỰ
            if (thuTuList.contains(nv.getNvTt())) {
                throw new RuntimeException("Trùng thứ tự nguyện vọng: " + nv.getNvTt());
            }
            thuTuList.add(nv.getNvTt());

            NguyenVongXetTuyen entity = new NguyenVongXetTuyen();

            entity.setCccd(nv.getCccd());
            entity.setMaNganh(nv.getMaNganh());
            entity.setNvTt(nv.getNvTt());
            entity.setTtPhuongThuc(nv.getTtPhuongThuc());

            entity.setDiemThxt(null);
            entity.setDiemUtqd(null);
            entity.setDiemCong(null);
            entity.setDiemXetTuyen(null);
            entity.setNvKetQua(null);
            entity.setNvKeys(null);
            entity.setTtthm(null);

            result.add(repository.save(entity));
        }

        return result;
    }
    public List<NguyenVongXetTuyen> findByCccd(String cccd) {
        System.out.println("CALL findByCccd with: " + cccd);
        return repository.findByCccd(cccd);
    }
}
