/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * @author kiman
 */
@Entity
@Table(name = "xt_nguyenvongxettuyen")
public class NguyenVongDTO implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idnv")
    private int idnv;

    @Column(name = "nv_cccd")  // Sửa thành nv_cccd
    private String nvCccd;

    @Column(name = "nv_manganh")
    private String nvManganh;

    @Column(name = "nv_tt")
    private int nvTt;

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

    // Constructor không tham số
    public NguyenVongDTO() {}

    // Constructor đầy đủ tham số
    public NguyenVongDTO(int idnv, String nvCccd, String nvManganh, int nvTt, 
                         BigDecimal diemThxt, BigDecimal diemUtqd, BigDecimal diemCong, 
                         BigDecimal diemXettuyen, String nvKetqua, String nvKeys, 
                         String ttPhuongthuc, String ttThm) {
        this.idnv = idnv;
        this.nvCccd = nvCccd;
        this.nvManganh = nvManganh;
        this.nvTt = nvTt;
        this.diemThxt = diemThxt;
        this.diemUtqd = diemUtqd;
        this.diemCong = diemCong;
        this.diemXettuyen = diemXettuyen;
        this.nvKetqua = nvKetqua;
        this.nvKeys = nvKeys;
        this.ttPhuongthuc = ttPhuongthuc;
        this.ttThm = ttThm;
    }

    // Getter và Setter
    public int getIdnv() {
        return idnv;
    }

    public void setIdnv(int idnv) {
        this.idnv = idnv;
    }

    public String getNvCccd() {
        return nvCccd;
    }

    public void setNvCccd(String nvCccd) {
        this.nvCccd = nvCccd;
    }

    public String getNvManganh() {
        return nvManganh;
    }

    public void setNvManganh(String nvManganh) {
        this.nvManganh = nvManganh;
    }

    public int getNvTt() {
        return nvTt;
    }

    public void setNvTt(int nvTt) {
        this.nvTt = nvTt;
    }

    public BigDecimal getDiemThxt() {
        return diemThxt;
    }

    public void setDiemThxt(BigDecimal diemThxt) {
        this.diemThxt = diemThxt;
    }

    public BigDecimal getDiemUtqd() {
        return diemUtqd;
    }

    public void setDiemUtqd(BigDecimal diemUtqd) {
        this.diemUtqd = diemUtqd;
    }

    public BigDecimal getDiemCong() {
        return diemCong;
    }

    public void setDiemCong(BigDecimal diemCong) {
        this.diemCong = diemCong;
    }

    public BigDecimal getDiemXettuyen() {
        return diemXettuyen;
    }

    public void setDiemXettuyen(BigDecimal diemXettuyen) {
        this.diemXettuyen = diemXettuyen;
    }

    public String getNvKetqua() {
        return nvKetqua;
    }

    public void setNvKetqua(String nvKetqua) {
        this.nvKetqua = nvKetqua;
    }

    public String getNvKeys() {
        return nvKeys;
    }

    public void setNvKeys(String nvKeys) {
        this.nvKeys = nvKeys;
    }

    public String getTtPhuongthuc() {
        return ttPhuongthuc;
    }

    public void setTtPhuongthuc(String ttPhuongthuc) {
        this.ttPhuongthuc = ttPhuongthuc;
    }

    public String getTtThm() {
        return ttThm;
    }

    public void setTtThm(String ttThm) {
        this.ttThm = ttThm;
    }

    @Override
    public String toString() {
        return "NguyenVongDTO{" +
                "idnv=" + idnv +
                ", nvCccd='" + nvCccd + '\'' +
                ", nvManganh='" + nvManganh + '\'' +
                ", nvTt=" + nvTt +
                ", diemThxt=" + diemThxt +
                ", diemUtqd=" + diemUtqd +
                ", diemCong=" + diemCong +
                ", diemXettuyen=" + diemXettuyen +
                ", nvKetqua='" + nvKetqua + '\'' +
                ", nvKeys='" + nvKeys + '\'' +
                ", ttPhuongthuc='" + ttPhuongthuc + '\'' +
                ", ttThm='" + ttThm + '\'' +
                '}';
    }
}