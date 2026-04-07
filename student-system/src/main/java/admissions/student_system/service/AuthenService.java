package admissions.student_system.service;

import admissions.student_system.entity.Student;
import admissions.student_system.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenService {
    @Autowired
    private StudentRepository studentRepository;
    public Student login(String cccd,String password){
        Student student = studentRepository.findByCccd(cccd)
                .orElseThrow(()->new RuntimeException("CCCD Không tồn tại"));
        if (!student.getPassword().equals(password)) {
            throw new RuntimeException("Sai mật khẩu");
        }

        return student;
    }
}
