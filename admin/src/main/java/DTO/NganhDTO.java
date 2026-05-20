package DTO;

import javax.persistence.*;
import java.math.BigDecimal;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "xt_nganh") // Tên bảng trong DB
public class NganhDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idnganh")
    private int idNganh;

    @Column(name = "manganh")
    private String maNganh;

    @Column(name = "tennganh")
    private String tenNganh;

    @Column(name = "n_tohopgoc")
    private String nToHopGoc;

    @Column(name = "n_chitieu")
    private int nChiTieu;

    @Column(name = "n_diemsan")
    private BigDecimal nDiemSan;

    @Column(name = "n_diemtrungtuyen")
    private BigDecimal nDiemTrungTuyen;

    @Column(name = "n_diemtrungtuyenvsat")
    private BigDecimal nDiemTrungTuyenVSAT;

    @Column(name = "n_diemtrungtuyendgnl")
    private BigDecimal nDiemTrungTuyenDGNL;
    
    @Column(name = "n_tuyenthang")
    private String nTuyenThang;

    @Column(name = "n_dgnl")
    private String nDGNL;

    @Column(name = "n_thpt")
    private String nTHPT;

    @Column(name = "n_vsat")
    private String nVSAT;

    @Column(name = "sl_xtt")
    private Integer slXTT;

    @Column(name = "sl_dgnl")
    private Integer slDGNL;

    @Column(name = "sl_vsat")
    private Integer slVSAT;

    @Column(name = "sl_thpt")
    private Integer slTHPT;

    // Constructor rỗng
    public NganhDTO() {
    }

    // Getter & Setter
    public int getIdNganh() {
        return idNganh;
    }

    public void setIdNganh(int idNganh) {
        this.idNganh = idNganh;
    }

    public String getMaNganh() {
        return maNganh;
    }

    public void setMaNganh(String maNganh) {
        this.maNganh = maNganh;
    }

    public String getTenNganh() {
        return tenNganh;
    }

    public void setTenNganh(String tenNganh) {
        this.tenNganh = tenNganh;
    }

    public String getNToHopGoc() {
        return nToHopGoc;
    }

    public void setNToHopGoc(String nToHopGoc) {
        this.nToHopGoc = nToHopGoc;
    }

    public int getNChiTieu() {
        return nChiTieu;
    }

    public void setNChiTieu(int nChiTieu) {
        this.nChiTieu = nChiTieu;
    }

    public BigDecimal getNDiemSan() {
        return nDiemSan;
    }

    public void setNDiemSan(BigDecimal nDiemSan) {
        this.nDiemSan = nDiemSan;
    }

    public BigDecimal getNDiemTrungTuyen() {
        return nDiemTrungTuyen;
    }

    public void setNDiemTrungTuyen(BigDecimal nDiemTrungTuyen) {
        this.nDiemTrungTuyen = nDiemTrungTuyen;
    }

    public BigDecimal getNDiemTrungTuyenVSAT() {
        return nDiemTrungTuyenVSAT;
    }

    public void setNDiemTrungTuyenVSAT(BigDecimal nDiemTrungTuyenVSAT) {
        this.nDiemTrungTuyenVSAT = nDiemTrungTuyenVSAT;
    }

    public BigDecimal getNDiemTrungTuyenDGNL() {
        return nDiemTrungTuyenDGNL;
    }

    public void setNDiemTrungTuyenDGNL(BigDecimal nDiemTrungTuyenDGNL) {
        this.nDiemTrungTuyenDGNL = nDiemTrungTuyenDGNL;
    }

    public String getNTuyenThang() {
        return nTuyenThang;
    }

    public void setNTuyenThang(String nTuyenThang) {
        this.nTuyenThang = nTuyenThang;
    }

    public String getNDGNL() {
        return nDGNL;
    }

    public void setNDGNL(String nDGNL) {
        this.nDGNL = nDGNL;
    }

    public String getNTHPT() {
        return nTHPT;
    }

    public void setNTHPT(String nTHPT) {
        this.nTHPT = nTHPT;
    }

    public String getNVSAT() {
        return nVSAT;
    }

    public void setNVSAT(String nVSAT) {
        this.nVSAT = nVSAT;
    }

    public Integer getSlXTT() {
        return slXTT;
    }

    public void setSlXTT(Integer slXTT) {
        this.slXTT = slXTT;
    }

    public Integer getSlDGNL() {
        return slDGNL;
    }

    public void setSlDGNL(Integer slDGNL) {
        this.slDGNL = slDGNL;
    }

    public Integer getSlVSAT() {
        return slVSAT;
    }

    public void setSlVSAT(Integer slVSAT) {
        this.slVSAT = slVSAT;
    }

    public Integer getSlTHPT() {
        return slTHPT;
    }

    public void setSlTHPT(Integer slTHPT) {
        this.slTHPT = slTHPT;
    }
}