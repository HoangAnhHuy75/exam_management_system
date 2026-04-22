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
@Table(name = "xt_tohop_nganh")
public class ToHopNganh {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "manganh")
    private String maNganh;

    @Column(name = "matohop")
    private String maToHop;

    @Column(name = "th_mon1")
    private String mon1;

    @Column(name = "hsmon1")
    private Integer hsMon1;

    @Column(name = "th_mon2")
    private String mon2;

    @Column(name = "hsmon2")
    private Integer hsMon2;

    @Column(name = "th_mon3")
    private String mon3;

    @Column(name = "hsmon3")
    private Integer hsMon3;

    @Column(name = "dolech")
    private BigDecimal doLech;

    // getter setter
}