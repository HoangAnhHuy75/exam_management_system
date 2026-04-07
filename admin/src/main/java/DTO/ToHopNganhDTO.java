package DTO;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "xt_tohop_nganh") // đổi theo tên bảng DB của bạn
public class ToHopNganhDTO implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "manganh")
    private String manganh;

    @Column(name = "matohop")
    private String matohop;

    // thứ tự môn + hệ số
    @Column(name = "th_mon1")
    private String th_mon1;

    @Column(name = "hsmon1")
    private Integer hsmon1;

    @Column(name = "th_mon2")
    private String th_mon2;

    @Column(name = "hsmon2")
    private Integer hsmon2;

    @Column(name = "th_mon3")
    private String th_mon3;

    @Column(name = "hsmon3")
    private Integer hsmon3;

    @Column(name = "tb_keys")
    private String tb_keys;

    // đánh dấu môn
    @Column(name = "N1")
    private Integer N1;

    @Column(name = "TO")
    private Integer TO;

    @Column(name = "LI")
    private Integer LI;

    @Column(name = "HO")
    private Integer HO;

    @Column(name = "SI")
    private Integer SI;

    @Column(name = "VA")
    private Integer VA;

    @Column(name = "SU")
    private Integer SU;

    @Column(name = "DI")
    private Integer DI;

    @Column(name = "TI")
    private Integer TI;

    @Column(name = "KHAC")
    private Integer KHAC;

    @Column(name = "KTPL")
    private Integer KTPL;

    @Column(name = "dolech")
    private BigDecimal dolech;

    // Constructor rỗng
    public ToHopNganhDTO() {}

    // Getter & Setter giữ nguyên (copy lại y chang của bạn)
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getManganh() { return manganh; }
    public void setManganh(String manganh) { this.manganh = manganh; }

    public String getMatohop() { return matohop; }
    public void setMatohop(String matohop) { this.matohop = matohop; }

    public String getTh_mon1() { return th_mon1; }
    public void setTh_mon1(String th_mon1) { this.th_mon1 = th_mon1; }

    public Integer getHsmon1() { return hsmon1; }
    public void setHsmon1(Integer hsmon1) { this.hsmon1 = hsmon1; }

    public String getTh_mon2() { return th_mon2; }
    public void setTh_mon2(String th_mon2) { this.th_mon2 = th_mon2; }

    public Integer getHsmon2() { return hsmon2; }
    public void setHsmon2(Integer hsmon2) { this.hsmon2 = hsmon2; }

    public String getTh_mon3() { return th_mon3; }
    public void setTh_mon3(String th_mon3) { this.th_mon3 = th_mon3; }

    public Integer getHsmon3() { return hsmon3; }
    public void setHsmon3(Integer hsmon3) { this.hsmon3 = hsmon3; }

    public String getTb_keys() { return tb_keys; }
    public void setTb_keys(String tb_keys) { this.tb_keys = tb_keys; }

    public Integer getN1() { return N1; }
    public void setN1(Integer N1) { this.N1 = N1; }

    public Integer getTO() { return TO; }
    public void setTO(Integer TO) { this.TO = TO; }

    public Integer getLI() { return LI; }
    public void setLI(Integer LI) { this.LI = LI; }

    public Integer getHO() { return HO; }
    public void setHO(Integer HO) { this.HO = HO; }

    public Integer getSI() { return SI; }
    public void setSI(Integer SI) { this.SI = SI; }

    public Integer getVA() { return VA; }
    public void setVA(Integer VA) { this.VA = VA; }

    public Integer getSU() { return SU; }
    public void setSU(Integer SU) { this.SU = SU; }

    public Integer getDI() { return DI; }
    public void setDI(Integer DI) { this.DI = DI; }

    public Integer getTI() { return TI; }
    public void setTI(Integer TI) { this.TI = TI; }

    public Integer getKHAC() { return KHAC; }
    public void setKHAC(Integer KHAC) { this.KHAC = KHAC; }

    public Integer getKTPL() { return KTPL; }
    public void setKTPL(Integer KTPL) { this.KTPL = KTPL; }

    public BigDecimal getDolech() { return dolech; }
    public void setDolech(BigDecimal dolech) { this.dolech = dolech; }

    // helper
    public String getDisplay() {
        return manganh + " - " + matohop;
    }
}