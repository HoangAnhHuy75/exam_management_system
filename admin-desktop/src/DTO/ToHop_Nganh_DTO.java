package DTO;

import java.math.BigDecimal;

public class ToHop_Nganh_DTO {

    private int id;

    private String manganh;
    private String matohop;

    // thứ tự môn + hệ số
    private String th_mon1;
    private Integer hsmon1;

    private String th_mon2;
    private Integer hsmon2;

    private String th_mon3;
    private Integer hsmon3;

    private String tb_keys;

    // đánh dấu môn (0/1)
    private Integer N1;
    private Integer TO;
    private Integer LI;
    private Integer HO;
    private Integer SI;
    private Integer VA;
    private Integer SU;
    private Integer DI;
    private Integer TI;
    private Integer KHAC;
    private Integer KTPL;

    private BigDecimal dolech;

    // Constructor rỗng
    public ToHop_Nganh_DTO() {
    }

    // Constructor đầy đủ
    public ToHop_Nganh_DTO(int id, String manganh, String matohop,
            String th_mon1, Integer hsmon1,
            String th_mon2, Integer hsmon2,
            String th_mon3, Integer hsmon3,
            String tb_keys,
            Integer N1, Integer TO, Integer LI, Integer HO,
            Integer SI, Integer VA, Integer SU, Integer DI,
            Integer TI, Integer KHAC, Integer KTPL,
            BigDecimal dolech) {

        this.id = id;
        this.manganh = manganh;
        this.matohop = matohop;
        this.th_mon1 = th_mon1;
        this.hsmon1 = hsmon1;
        this.th_mon2 = th_mon2;
        this.hsmon2 = hsmon2;
        this.th_mon3 = th_mon3;
        this.hsmon3 = hsmon3;
        this.tb_keys = tb_keys;
        this.N1 = N1;
        this.TO = TO;
        this.LI = LI;
        this.HO = HO;
        this.SI = SI;
        this.VA = VA;
        this.SU = SU;
        this.DI = DI;
        this.TI = TI;
        this.KHAC = KHAC;
        this.KTPL = KTPL;
        this.dolech = dolech;
    }

    // ================= GETTER & SETTER =================
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getTh_mon1() {
        return th_mon1;
    }

    public void setTh_mon1(String th_mon1) {
        this.th_mon1 = th_mon1;
    }

    public Integer getHsmon1() {
        return hsmon1;
    }

    public void setHsmon1(Integer hsmon1) {
        this.hsmon1 = hsmon1;
    }

    public String getTh_mon2() {
        return th_mon2;
    }

    public void setTh_mon2(String th_mon2) {
        this.th_mon2 = th_mon2;
    }

    public Integer getHsmon2() {
        return hsmon2;
    }

    public void setHsmon2(Integer hsmon2) {
        this.hsmon2 = hsmon2;
    }

    public String getTh_mon3() {
        return th_mon3;
    }

    public void setTh_mon3(String th_mon3) {
        this.th_mon3 = th_mon3;
    }

    public Integer getHsmon3() {
        return hsmon3;
    }

    public void setHsmon3(Integer hsmon3) {
        this.hsmon3 = hsmon3;
    }

    public String getTb_keys() {
        return tb_keys;
    }

    public void setTb_keys(String tb_keys) {
        this.tb_keys = tb_keys;
    }

    public Integer getN1() {
        return N1;
    }

    public void setN1(Integer N1) {
        this.N1 = N1;
    }

    public Integer getTO() {
        return TO;
    }

    public void setTO(Integer TO) {
        this.TO = TO;
    }

    public Integer getLI() {
        return LI;
    }

    public void setLI(Integer LI) {
        this.LI = LI;
    }

    public Integer getHO() {
        return HO;
    }

    public void setHO(Integer HO) {
        this.HO = HO;
    }

    public Integer getSI() {
        return SI;
    }

    public void setSI(Integer SI) {
        this.SI = SI;
    }

    public Integer getVA() {
        return VA;
    }

    public void setVA(Integer VA) {
        this.VA = VA;
    }

    public Integer getSU() {
        return SU;
    }

    public void setSU(Integer SU) {
        this.SU = SU;
    }

    public Integer getDI() {
        return DI;
    }

    public void setDI(Integer DI) {
        this.DI = DI;
    }

    public Integer getTI() {
        return TI;
    }

    public void setTI(Integer TI) {
        this.TI = TI;
    }

    public Integer getKHAC() {
        return KHAC;
    }

    public void setKHAC(Integer KHAC) {
        this.KHAC = KHAC;
    }

    public Integer getKTPL() {
        return KTPL;
    }

    public void setKTPL(Integer KTPL) {
        this.KTPL = KTPL;
    }

    public BigDecimal getDolech() {
        return dolech;
    }

    public void setDolech(BigDecimal dolech) {
        this.dolech = dolech;
    }

    // ================= HELPER =================
    public String getDisplay() {
        return manganh + " - " + matohop;
    }
}
