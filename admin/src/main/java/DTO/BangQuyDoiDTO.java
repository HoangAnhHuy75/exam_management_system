package DTO;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "xt_bangquydoi") // tên bảng trong database
public class BangQuyDoiDTO implements Serializable {

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

    // Constructor không tham số
    public BangQuyDoiDTO() {}

    // Constructor đầy đủ tham số
    public BangQuyDoiDTO(int idqd, String dPhuongThuc, String dToHop, String dMon, 
                         BigDecimal dDiemA, BigDecimal dDiemB, BigDecimal dDiemC, 
                         BigDecimal dDiemD, String dMaQuyDoi, String dPhanVi) {
        this.idqd = idqd;
        this.dPhuongThuc = dPhuongThuc;
        this.dToHop = dToHop;
        this.dMon = dMon;
        this.dDiemA = dDiemA;
        this.dDiemB = dDiemB;
        this.dDiemC = dDiemC;
        this.dDiemD = dDiemD;
        this.dMaQuyDoi = dMaQuyDoi;
        this.dPhanVi = dPhanVi;
    }

    // Getter và Setter
    public int getIdqd() {
        return idqd;
    }

    public void setIdqd(int idqd) {
        this.idqd = idqd;
    }

    public String getdPhuongThuc() {
        return dPhuongThuc;
    }

    public void setdPhuongThuc(String dPhuongThuc) {
        this.dPhuongThuc = dPhuongThuc;
    }

    public String getdToHop() {
        return dToHop;
    }

    public void setdToHop(String dToHop) {
        this.dToHop = dToHop;
    }

    public String getdMon() {
        return dMon;
    }

    public void setdMon(String dMon) {
        this.dMon = dMon;
    }

    public BigDecimal getdDiemA() {
        return dDiemA;
    }

    public void setdDiemA(BigDecimal dDiemA) {
        this.dDiemA = dDiemA;
    }

    public BigDecimal getdDiemB() {
        return dDiemB;
    }

    public void setdDiemB(BigDecimal dDiemB) {
        this.dDiemB = dDiemB;
    }

    public BigDecimal getdDiemC() {
        return dDiemC;
    }

    public void setdDiemC(BigDecimal dDiemC) {
        this.dDiemC = dDiemC;
    }

    public BigDecimal getdDiemD() {
        return dDiemD;
    }

    public void setdDiemD(BigDecimal dDiemD) {
        this.dDiemD = dDiemD;
    }

    public String getdMaQuyDoi() {
        return dMaQuyDoi;
    }

    public void setdMaQuyDoi(String dMaQuyDoi) {
        this.dMaQuyDoi = dMaQuyDoi;
    }

    public String getdPhanVi() {
        return dPhanVi;
    }

    public void setdPhanVi(String dPhanVi) {
        this.dPhanVi = dPhanVi;
    }

    // Phương thức toString() để dễ dàng in thông tin
    @Override
    public String toString() {
        return "BangQuyDoiDTO{" +
                "idqd=" + idqd +
                ", dPhuongThuc='" + dPhuongThuc + '\'' +
                ", dToHop='" + dToHop + '\'' +
                ", dMon='" + dMon + '\'' +
                ", dDiemA=" + dDiemA +
                ", dDiemB=" + dDiemB +
                ", dDiemC=" + dDiemC +
                ", dDiemD=" + dDiemD +
                ", dMaQuyDoi='" + dMaQuyDoi + '\'' +
                ", dPhanVi='" + dPhanVi + '\'' +
                '}';
    }
}