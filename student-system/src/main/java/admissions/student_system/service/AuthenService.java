package admissions.student_system.service;

import admissions.student_system.entity.Student;
import admissions.student_system.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

@Service
public class AuthenService {
    @Autowired
    private StudentRepository studentRepository;
    public Student login(String cccd, String password) {
        Student student = studentRepository.findByCccd(cccd.trim())
                .orElseThrow(() -> new RuntimeException("Số CCCD không tồn tại"));

        String rawPw = password.trim(); // Giả sử nhập: 20070127
        String dbBirthday = student.getNgaySinh().trim(); // DB: 2007-01-27
        String formattedPw = "";

        if (rawPw.length() == 8 && !rawPw.contains("-")) {
            // Cắt theo định dạng yyyyMMdd (Năm - Tháng - Ngày)
            String year = rawPw.substring(0, 4);  // 2007
            String month = rawPw.substring(4, 6); // 01
            String day = rawPw.substring(6, 8);   // 27

            formattedPw = year + "-" + month + "-" + day; // Kết quả: 2007-01-27
        } else {
            formattedPw = rawPw;
        }

        // So sánh sau khi đã quy đổi đúng
        if (!dbBirthday.equals(formattedPw)) {
            throw new RuntimeException("Ngày sinh (Mật khẩu) không chính xác");
        }

        return student;
    }
}
