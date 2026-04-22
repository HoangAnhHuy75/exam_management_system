package admissions.student_system.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "xt_nganh")
public class Nganh {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idnganh")
    private Integer id;

    @Column(name = "manganh")
    private String maNganh;

    @Column(name = "tennganh")
    private String tenNganh;

    @Column(name = "n_tohopgoc")
    private String nToHopGoc;

    @Column(name = "n_chitieu")
    private Integer nChiTieu;

    @Column(name = "n_diemsan")
    private BigDecimal nDiemSan;

    @Column(name = "n_diemtrungtuyen")
    private BigDecimal nDiemTrungTuyen;

    @Column(name = "n_tuyenthang")
    private String nTuyenThang;

    @Column(name = "n_dgnl")
    private String nDGNL;

    @Column(name = "n_thpt")
    private String nTHPT;

    @Column(name = "n_vsat")
    private String nVSAT;

    // getter setter
}