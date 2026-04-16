package DTO;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "xt_thisinhxettuyen25")
public class ThiSinhDTO implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idthisinh")
    private int idthisinh;

    @Column(name = "cccd")
    private String cccd;

    @Column(name = "sobaodanh")
    private String sobaodanh;

    @Column(name = "ho")
    private String ho;
    
    @Column(name = "ten")
    private String ten;

    @Temporal(TemporalType.DATE)
    @Column(name = "ngay_sinh")
    private Date ngaySinh;

    @Column(name = "dien_thoai")
    private String dienThoai;

    @Column(name = "password")
    private String password;

    @Column(name = "gioi_tinh")
    private String gioiTinh;

    @Column(name = "email")
    private String email;

    @Column(name = "noi_sinh")
    private String noiSinh;

    @Column(name = "doi_tuong")
    private String doiTuong;

    @Column(name = "khu_vuc")
    private String khuVuc;

    // ===== Constructor =====
    public ThiSinhDTO() {
    }

    public ThiSinhDTO(int idthisinh, String cccd, String sobaodanh, String ho,String ten,
            Date ngaySinh, String dienThoai, String password,
            String gioiTinh, String email, String noiSinh,
            String doiTuong, String khuVuc) {
        this.idthisinh = idthisinh;
        this.cccd = cccd;
        this.sobaodanh = sobaodanh;
        this.ho = ho;
        this.ten = ten;
        this.ngaySinh = ngaySinh;
        this.dienThoai = dienThoai;
        this.password = password;
        this.gioiTinh = gioiTinh;
        this.email = email;
        this.noiSinh = noiSinh;
        this.doiTuong = doiTuong;
        this.khuVuc = khuVuc;
    }

    // ===== Getter & Setter =====
    public int getIdthisinh() {
        return idthisinh;
    }

    public void setIdthisinh(int idthisinh) {
        this.idthisinh = idthisinh;
    }

    public String getCccd() {
        return cccd;
    }

    public void setCccd(String cccd) {
        this.cccd = cccd;
    }

    public String getSobaodanh() {
        return sobaodanh;
    }

    public void setSobaodanh(String sobaodanh) {
        this.sobaodanh = sobaodanh;
    }
    public String getHo() {
        return ho;
    }
    public void setHo(String ho) {
        this.ho= ho;
    }
    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public Date getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(Date ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public String getDienThoai() {
        return dienThoai;
    }

    public void setDienThoai(String dienThoai) {
        this.dienThoai = dienThoai;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNoiSinh() {
        return noiSinh;
    }

    public void setNoiSinh(String noiSinh) {
        this.noiSinh = noiSinh;
    }

    public String getDoiTuong() {
        return doiTuong;
    }

    public void setDoiTuong(String doiTuong) {
        this.doiTuong = doiTuong;
    }

    public String getKhuVuc() {
        return khuVuc;
    }

    public void setKhuVuc(String khuVuc) {
        this.khuVuc = khuVuc;
    }

}
