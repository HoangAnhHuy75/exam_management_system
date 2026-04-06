package edu.poly.exammanagement.dto.response;

import edu.poly.exammanagement.enums.GioiTinh;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UserResponse {
    private int maTS;
    private String cccd;
    private String sbd;
    private String name;
    private LocalDate birthday;
    private String phone;
    private String password;
    private GioiTinh gioiTinh;
    private String email;
    private String khu_vuc;
    private String doi_tuong;
    private LocalDateTime update_at;
}
