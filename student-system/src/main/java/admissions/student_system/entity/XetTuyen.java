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
@Table(name = "xt_xettuyen")
public class XetTuyen {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idxettuyen")
    private int idXetTuyen;

    @Column(name = "cccd")
    private String cccd;

    @Column(name = "manganh")
    private String maNganh;

    @Column(name = "ketqua")
    private String ketQua;

    @Column(name = "diemxettuyen")
    private BigDecimal diemXetTuyen;

    @Column(name = "phuongthuc")
    private String phuongThuc;
}