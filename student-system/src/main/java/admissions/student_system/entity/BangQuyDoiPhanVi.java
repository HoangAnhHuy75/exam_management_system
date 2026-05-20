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
@Table(name = "xt_bangquydoiphanvi")
public class BangQuyDoiPhanVi {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idqd")
    private Integer idQd;

    @Column(name = "d_phuongthuc")
    private String phuongThuc;

    @Column(name = "d_mon")
    private String mon;

    @Column(name = "d_phanvi")
    private String phanVi;

    @Column(name = "d_diema")
    private BigDecimal diemA;

    @Column(name = "d_diemb")
    private BigDecimal diemB;

    @Column(name = "d_diemc")
    private BigDecimal diemC;

    @Column(name = "d_diemd")
    private BigDecimal diemD;

    @Column(name = "d_tohop")
    private String toHop;
}