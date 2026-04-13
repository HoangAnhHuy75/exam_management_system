package admissions.student_system.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "xt_thisinhxettuyen25")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idthisinh")
    private Integer id;

    @Column(name = "cccd", unique = true, length = 20)
    private String cccd;

    @Column(name = "sobaodanh", length = 45)
    private String soBaoDanh;

    @Column(name = "ho", length = 100)
    private String ho;

    @Column(name = "ten", length = 100)
    private String ten;

    @Column(name = "ngay_sinh", length = 45)
    private String ngaySinh; // nên dùng LocalDate nếu có thể

    @Column(name = "dien_thoai", length = 20)
    private String dienThoai;

    @Column(name = "password", length = 100)
    private String password;

    @Column(name = "gioi_tinh", length = 10)
    private String gioiTinh;

    @Column(name = "email", length = 100)
    private String email;

    @Column(name = "noi_sinh", length = 45)
    private String noiSinh;

    @Column(name = "updated_at")
    @Temporal(TemporalType.DATE)
    private Date updatedAt;

    @Column(name = "doi_tuong", length = 45)
    private String doiTuong;

    @Column(name = "khu_vuc", length = 45)
    private String khuVuc;
}

