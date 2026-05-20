package DTO;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "xt_xettuyen") // Tên bảng trong DB
public class XetTuyenDTO {

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

    // Constructor rỗng
    public XetTuyenDTO() {
    }

    // Getter & Setter
    public int getIdXetTuyen() {
        return idXetTuyen;
    }

    public void setIdXetTuyen(int idXetTuyen) {
        this.idXetTuyen = idXetTuyen;
    }

    public String getCccd() {
        return cccd;
    }

    public void setCccdTS(String cccd) {
        this.cccd = cccd;
    }

    public String getMaNganh() {
        return maNganh;
    }

    public void setMaNganh(String maNganh) {
        this.maNganh = maNganh;
    }

    public String getKetQua() {
        return ketQua;
    }

    public void setKetQua(String ketQua) {
        this.ketQua = ketQua;
    }

    public BigDecimal getDiemXetTuyen() {
        return diemXetTuyen;
    }

    public void setDiemXetTuyen(BigDecimal diemXetTuyen) {
        this.diemXetTuyen = diemXetTuyen;
    }

    public String getPhuongThuc() {
        return phuongThuc;
    }

    public void setPhuongThuc(String phuongThuc) {
        this.phuongThuc = phuongThuc;
    }
}