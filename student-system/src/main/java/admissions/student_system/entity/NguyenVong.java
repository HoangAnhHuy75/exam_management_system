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
public class NguyenVong {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idnv")
    private Integer idnv;

    @Column(name = "nv_cccd")
    private String nvCccd;

    @Column(name = "nv_manganh")
    private String nvManganh;

    @Column(name = "nv_tt")
    private Integer nvTt;

    @Column(name = "diem_thxt")
    private BigDecimal diemThxt;

    @Column(name = "diem_utqd")
    private BigDecimal diemUtqd;

    @Column(name = "diem_cong")
    private BigDecimal diemCong;

    @Column(name = "diem_xettuyen")
    private BigDecimal diemXettuyen;

    @Column(name = "nv_ketqua")
    private String nvKetqua;

    @Column(name = "nv_keys")
    private String nvKeys;

    @Column(name = "tt_phuongthuc")
    private String ttPhuongthuc;

    @Column(name = "tt_thm")
    private String ttThm;
}
