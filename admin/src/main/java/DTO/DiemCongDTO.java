package DTO;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.*;
@Entity
@Table(name = "xt_diemcongxettuyen")
public class DiemCongDTO implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "iddiemcong")
    private Integer iddiemcong;
    
    @Column(name = "ts_cccd", nullable = false)
    private String ts_cccd;
    
    @Column(name = "manganh")
    private String manganh;
    
    @Column(name = "matohop")
    private String matohop;
    
    @Column(name = "phuongthuc")
    private String phuongthuc;
    
    @Column(name = "diemCC", precision = 6, scale = 2)
    private BigDecimal diemCC;
    
    @Column(name = "diemUtxt", precision = 6, scale = 2)
    private BigDecimal diemUtxt;
    
    @Column(name = "diemTong", precision = 6, scale = 2)
    private BigDecimal diemTong;
    
    @Column(name = "ghichu")
    private String ghichu;
    
    @Column(name = "dc_keys", nullable = false)
    private String dc_keys;

    public DiemCongDTO(Integer iddiemcong, String ts_cccd, String manganh, String matohop
            , String phuongthuc, BigDecimal diemCC, BigDecimal diemUtxt, BigDecimal diemTong
            , String ghichu, String dc_keys) {
        this.iddiemcong = iddiemcong;
        this.ts_cccd = ts_cccd;
        this.manganh = manganh;
        this.matohop = matohop;
        this.phuongthuc = phuongthuc;
        this.diemCC = diemCC;
        this.diemUtxt = diemUtxt;
        this.diemTong = diemTong;
        this.ghichu = ghichu;
        this.dc_keys = dc_keys;
    }

    public DiemCongDTO() {
    }

    public Integer getIddiemcong() {
        return iddiemcong;
    }

    public void setIddiemcong(Integer iddiemcong) {
        this.iddiemcong = iddiemcong;
    }

    public String getTs_cccd() {
        return ts_cccd;
    }

    public void setTs_cccd(String ts_cccd) {
        this.ts_cccd = ts_cccd;
    }

    public String getManganh() {
        return manganh;
    }

    public void setManganh(String manganh) {
        this.manganh = manganh;
    }

    public String getMatohop() {
        return matohop;
    }

    public void setMatohop(String matohop) {
        this.matohop = matohop;
    }

    public String getPhuongthuc() {
        return phuongthuc;
    }

    public void setPhuongthuc(String phuongthuc) {
        this.phuongthuc = phuongthuc;
    }

    public BigDecimal getDiemCC() {
        return diemCC;
    }

    public void setDiemCC(BigDecimal diemCC) {
        this.diemCC = diemCC;
    }

    public BigDecimal getDiemUtxt() {
        return diemUtxt;
    }

    public void setDiemUtxt(BigDecimal dienUtxt) {
        this.diemUtxt = dienUtxt;
    }

    public BigDecimal getDiemTong() {
        return diemTong;
    }

    public void setDiemTong(BigDecimal diemTong) {
        this.diemTong = diemTong;
    }

    public String getGhichu() {
        return ghichu;
    }

    public void setGhichu(String ghichu) {
        this.ghichu = ghichu;
    }

    public String getDc_keys() {
        return dc_keys;
    }

    public void setDc_keys(String dc_keys) {
        this.dc_keys = dc_keys;
    }
    
    
}
