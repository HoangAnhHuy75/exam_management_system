package edu.poly.exammanagement.entity;

import edu.poly.exammanagement.enums.GioiTinh;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int maTS;
    private String cccd;
    private String sbd;
    private String name;
    private LocalDate birthday;
    private String phone;
    private String password;
    @Enumerated(EnumType.STRING)
    private GioiTinh gioiTinh;
    private String email;
    private String khu_vuc;
    private String doi_tuong;
    @UpdateTimestamp
    private LocalDateTime update_at;
}
