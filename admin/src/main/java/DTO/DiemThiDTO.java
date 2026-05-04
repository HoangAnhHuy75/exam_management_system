package DTO;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.*;
@Entity
@Table(name = "xt_diemthixetuyen")
public class DiemThiDTO implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "iddiemthi")
    private Integer iddiemthi;
    
    @Column(name = "cccd", unique = true, nullable = false)
    private String cccd;
    
    @Column(name = "sobaodanh")
    private String sobaodanh;
    
    @Column(name = "d_phuongthuc")
    private String d_phuongthuc;
    
    @Column(name = "`TO`", precision = 8, scale = 2)
    private BigDecimal TO;
    
    @Column(name = "LI", precision = 8, scale = 2)
    private BigDecimal LI;
    
    @Column(name = "HO", precision = 8, scale = 2)
    private BigDecimal HO;
    
    @Column(name = "SI", precision = 8, scale = 2)
    private BigDecimal SI;
    
    @Column(name = "SU", precision = 8, scale = 2)
    private BigDecimal SU;
    
    @Column(name = "DI", precision = 8, scale = 2)
    private BigDecimal DI;
    
     @Column(name = "GDCD", precision = 8, scale = 2)
    private BigDecimal GDCD;
    
    @Column(name = "VA", precision = 8, scale = 2)
    private BigDecimal VA;
    
    @Column(name = "N1_THI", precision = 8, scale = 2)
    private BigDecimal N1_THI;
    
    @Column(name = "N1_CC", precision = 8, scale = 2)
    private BigDecimal N1_CC;
    
    @Column(name = "CNCN", precision = 8, scale = 2)
    private BigDecimal CNCN;
    
    @Column(name = "CNNN", precision = 8, scale = 2)
    private BigDecimal CNNN;
    
    @Column(name = "TI", precision = 8, scale = 2)
    private BigDecimal TI;
    
    @Column(name = "KTPL", precision = 8, scale = 2)
    private BigDecimal KTPL;
    
    @Column(name = "NL1", precision = 8, scale = 2)
    private BigDecimal NL1;
    
    @Column(name = "NK1", precision = 8, scale = 2)
    private BigDecimal NK1;
    
    @Column(name = "NK2", precision = 8, scale = 2)
    private BigDecimal NK2;
    
    @Column(name = "NK3", precision = 8, scale = 2)
    private BigDecimal NK3;
    
    @Column(name = "NK4", precision = 8, scale = 2)
    private BigDecimal NK4;
    
    @Column(name = "NK5", precision = 8, scale = 2)
    private BigDecimal NK5;
    
    @Column(name = "NK6", precision = 8, scale = 2)
    private BigDecimal NK6;

    public DiemThiDTO(Integer iddiemthi, String cccd, String sobaodanh, String d_phuongthuc
    , BigDecimal TO, BigDecimal LI, BigDecimal HO, BigDecimal SI, BigDecimal SU
    , BigDecimal DI, BigDecimal VA,BigDecimal GDCD, BigDecimal N1_THI, BigDecimal N1_CC, BigDecimal CNCN
    , BigDecimal CNNN, BigDecimal TI, BigDecimal KTPL, BigDecimal NL1, BigDecimal NK1, BigDecimal NK2,
    BigDecimal NK3,BigDecimal NK4,BigDecimal NK5,BigDecimal NK6) {
        this.iddiemthi = iddiemthi;
        this.cccd = cccd;
        this.sobaodanh = sobaodanh;
        this.d_phuongthuc = d_phuongthuc;
        this.TO = TO;
        this.LI = LI;
        this.HO = HO;
        this.SI = SI;
        this.SU = SU;
        this.DI = DI;
        this.VA = VA;
        this.GDCD = GDCD;
        this.N1_THI = N1_THI;
        this.N1_CC = N1_CC;
        this.CNCN = CNCN;
        this.CNNN = CNNN;
        this.TI = TI;
        this.KTPL = KTPL;
        this.NL1 = NL1;
        this.NK1 = NK1;
        this.NK2 = NK2;
        this.NK3 = NK3;
        this.NK4 = NK4;
        this.NK5 = NK5;
        this.NK6 = NK6;
    }

    public DiemThiDTO() {
    }

    public Integer getIddiemthi() {
        return iddiemthi;
    }

    public void setIddiemthi(Integer iddiemthi) {
        this.iddiemthi = iddiemthi;
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

    public String getD_phuongthuc() {
        return d_phuongthuc;
    }

    public void setD_phuongthuc(String d_phuongthuc) {
        this.d_phuongthuc = d_phuongthuc;
    }

    public BigDecimal getTO() {
        return TO;
    }

    public void setTO(BigDecimal TO) {
        this.TO = TO;
    }

    public BigDecimal getLI() {
        return LI;
    }

    public void setLI(BigDecimal LI) {
        this.LI = LI;
    }

    public BigDecimal getHO() {
        return HO;
    }

    public void setHO(BigDecimal HO) {
        this.HO = HO;
    }

    public BigDecimal getSI() {
        return SI;
    }

    public void setSI(BigDecimal SI) {
        this.SI = SI;
    }

    public BigDecimal getSU() {
        return SU;
    }

    public void setSU(BigDecimal SU) {
        this.SU = SU;
    }

    public BigDecimal getDI() {
        return DI;
    }

    public void setDI(BigDecimal DI) {
        this.DI = DI;
    }

    public BigDecimal getVA() {
        return VA;
    }

    public void setVA(BigDecimal VA) {
        this.VA = VA;
    }
    
    public BigDecimal getGDCD() {
        return GDCD;
    }

    public void setGDCD(BigDecimal GDCD) {
        this.GDCD = GDCD;
    }
    

    public BigDecimal getN1_THI() {
        return N1_THI;
    }

    public void setN1_THI(BigDecimal N1_THI) {
        this.N1_THI = N1_THI;
    }

    public BigDecimal getN1_CC() {
        return N1_CC;
    }

    public void setN1_CC(BigDecimal N1_CC) {
        this.N1_CC = N1_CC;
    }

    public BigDecimal getCNCN() {
        return CNCN;
    }

    public void setCNCN(BigDecimal CNCN) {
        this.CNCN = CNCN;
    }

    public BigDecimal getCNNN() {
        return CNNN;
    }

    public void setCNNN(BigDecimal CNNN) {
        this.CNNN = CNNN;
    }

    public BigDecimal getTI() {
        return TI;
    }

    public void setTI(BigDecimal TI) {
        this.TI = TI;
    }

    public BigDecimal getKTPL() {
        return KTPL;
    }

    public void setKTPL(BigDecimal KTPL) {
        this.KTPL = KTPL;
    }

    public BigDecimal getNL1() {
        return NL1;
    }

    public void setNL1(BigDecimal NL1) {
        this.NL1 = NL1;
    }

    public BigDecimal getNK1() {
        return NK1;
    }

    public void setNK1(BigDecimal NK1) {
        this.NK1 = NK1;
    }

    public BigDecimal getNK2() {
        return NK2;
    }

    public void setNK2(BigDecimal NK2) {
        this.NK2 = NK2;
    }
    
    public BigDecimal getNK3() {
        return NK3;
    }

    public void setNK3(BigDecimal NK3) {
        this.NK3 = NK3;
    }
    
    public BigDecimal getNK4() {
        return NK4;
    }

    public void setNK4(BigDecimal NK4) {
        this.NK4 = NK4;
    }
    
    public BigDecimal getNK5() {
        return NK5;
    }

    public void setNK5(BigDecimal NK5) {
        this.NK5 = NK5;
    }
    
    public BigDecimal getNK6() {
        return NK6;
    }

    public void setNK6(BigDecimal NK6) {
        this.NK6 = NK6;
    }
    
    
}