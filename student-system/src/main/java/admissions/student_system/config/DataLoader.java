package admissions.student_system.config;

import admissions.student_system.entity.Student;
import admissions.student_system.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private StudentRepository studentRepository;

    @Override
    public void run(String... args) throws Exception {
        if(studentRepository.count() == 0) {
            Student s = new Student();
            s.setCccd("123456789");
            s.setSoBaoDanh("2026A001");
            s.setHo("Độ");
            s.setTen("Mi xi");
            s.setNgaySinh("2005-05-20");
            s.setDienThoai("0987654321");
            s.setPassword("123456");
            s.setGioiTinh("Nam");
            s.setEmail("domixi@example.com");
            s.setNoiSinh("Ha Noi");
            s.setUpdatedAt(new Date());
            s.setDoiTuong("Chinh sach");
            s.setKhuVuc("KV1");

            studentRepository.save(s);
        }
    }
}