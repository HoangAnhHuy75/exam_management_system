package admissions.student_system.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "xt_nguyenvongxettuyen")
public class NguyenVongXetTuyen {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idnv")
    private Integer idNv;

    @Column(name = "cccd")
    private String cccd;

    @Column(name = "manganh")
    private String maNganh;

    @Column(name = "nvtt")
    private Integer nvTt;

    @Column(name = "diemthxt")
    private BigDecimal diemThxt;

    @Column(name = "diemutqd")
    private BigDecimal diemUtqd;

    @Column(name = "diemcong")
    private BigDecimal diemCong;

    @Column(name = "diemxettuyen")
    private BigDecimal diemXetTuyen;

    @Column(name = "nvketqua")
    private String nvKetQua;

    @Column(name = "nvkeys")
    private String nvKeys;

    @Column(name = "ttphuongthuc")
    private String ttPhuongThuc;

    @Column(name = "ttthm")
    private String ttthm;
}