package admissions.student_system.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "xt_bangquydoi")
public class BangQuyDoi {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idqd")
    private int idqd;

    @Column(name = "d_phuongthuc")
    private String dPhuongThuc;

    @Column(name = "d_tohop")
    private String dToHop;

    @Column(name = "d_mon")
    private String dMon;

    @Column(name = "d_diema")
    private BigDecimal dDiemA;

    @Column(name = "d_diemb")
    private BigDecimal dDiemB;

    @Column(name = "d_diemc")
    private BigDecimal dDiemC;

    @Column(name = "d_diemd")
    private BigDecimal dDiemD;

    @Column(name = "d_maquydoi")
    private String dMaQuyDoi;

    @Column(name = "d_phanvi")
    private String dPhanVi;
}