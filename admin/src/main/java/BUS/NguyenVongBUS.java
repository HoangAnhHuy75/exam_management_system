package BUS;

import DAO.NguyenVongDAO;
import DTO.BangQuyDoiDTO;
import DTO.DiemCongDTO;
import DTO.DiemThiDTO;
import DTO.NganhDTO;
import DTO.NguyenVongDTO;
import DTO.ThiSinhDTO;
import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.apache.poi.ss.usermodel.Cell;
import static org.apache.poi.ss.usermodel.CellType.NUMERIC;
import static org.apache.poi.ss.usermodel.CellType.STRING;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class NguyenVongBUS {
    private DiemThiBUS dtBus = new DiemThiBUS();
    private ThiSinhBUS tsBus = new ThiSinhBUS();
    private DiemCongBUS dcBus = new DiemCongBUS();
    private NganhBUS nganhBus = new NganhBUS();
    private BangQuyDoiBUS bqdBus = new BangQuyDoiBUS();
    private NguyenVongDAO nvDAO = new NguyenVongDAO();
    private List<NguyenVongDTO> nvList = new ArrayList<>();
    // ================= INSERT =================
    public int insert(NguyenVongDTO nv) {

        // check trùng NV (1 thí sinh không được trùng thứ tự NV)
        if (checkDup(nv.getNvCccd(), nv.getNvTt())) {
            return 0;
        }

        return nvDAO.insert(nv);
    }

    // ================= UPDATE =================
    public int update(NguyenVongDTO nv) {

        for (NguyenVongDTO item : nvDAO.getAll()) {
            if (item.getIdnv() != nv.getIdnv()
                    && item.getNvCccd().equals(nv.getNvCccd())
                    && item.getNvTt() == nv.getNvTt()) {
                return 0; // trùng NV
            }
        }

        return nvDAO.update(nv);
    }

    // ================= DELETE =================
    public int delete(int id) {
        return nvDAO.delete(id);
    }

    // ================= GET LIST =================
    public List<NguyenVongDTO> getList() {
        nvList = nvDAO.getAll();
        return nvList;
    }

    // ================= GET BY CCCD =================
    public ArrayList<NguyenVongDTO> getByCccd(String cccd) {
        return new ArrayList<>(nvDAO.getByCccdOrderByNV(cccd));
    }

    // ================= CHECK TRÙNG NV =================
    public boolean checkDup(String cccd, int nvTt) {
        return nvDAO.getAll().stream()
                .anyMatch(nv ->
                        nv.getNvCccd().equals(cccd)
                        && nv.getNvTt() == nvTt
                );
    }

    // ================= MAP CCCD -> LIST NV =================
    public HashMap<String, List<NguyenVongDTO>> mapByCccd() {
        HashMap<String, List<NguyenVongDTO>> map = new HashMap<>();
        for (NguyenVongDTO nv : nvDAO.getAll()) {
            map.putIfAbsent(nv.getNvCccd(), new ArrayList<>());
            map.get(nv.getNvCccd()).add(nv);
        }
        return map;
    }
    
    public HashMap<String, List<NguyenVongDTO>> mapByMaNganh() {
        HashMap<String, List<NguyenVongDTO>> map = new HashMap<>();
        for (NguyenVongDTO nv : nvDAO.getAll()) {
            map.putIfAbsent(nv.getNvManganh(), new ArrayList<>());
            map.get(nv.getNvManganh()).add(nv);
        }
        return map;
    }
    
    public List<String> listMaNganh(){
        return nvDAO.getListMaNganh();
    }
    
    public List<Object[]> getToHopNganhByMaNganh(String maNganh) {
        return nvDAO.getToHopNganhByMaNganh(maNganh);
    }
    
    private BigDecimal getDiemMon(DiemThiDTO dt, String mon) {
        if (mon == null) {
            return null;
        }
        switch (mon.toUpperCase()) {
            case "TO":
                return dt.getTO();
            case "VA":
                return dt.getVA();
            case "LI":
                return dt.getLI();
            case "HO":
                return dt.getHO();
            case "SI":
                return dt.getSI();
            case "SU":
                return dt.getSU();
            case "DI":
                return dt.getDI();
            case "GDCD":
                return dt.getGDCD();
            case "N1":
                return dt.getN1_THI();
            case "TIN":
                return dt.getTI();
            case "KTPL":
                return dt.getKTPL();
            case "CNCN":
                return dt.getCNCN();
            case "CNNN":
                return dt.getCNNN();
            case "NK1":
                return dt.getNK1();
            case "NK2":
                return dt.getNK2();
            case "NK3":
                return dt.getNK3();
            case "NK4":
                return dt.getNK4();
            case "NK5":
                return dt.getNK5();
            case "NK6":
                return dt.getNK6();
            default:
                return null;
        }
    }

    public BigDecimal tinhDiemKhuVucDoiTuong(BigDecimal diemTruocUT, ThiSinhDTO ts, BigDecimal diemTHXT, BigDecimal diemCong) {
        BigDecimal MDUT = BigDecimal.ZERO;
        BigDecimal diemUT;
        BigDecimal he_so_giam = new BigDecimal("7.5");
        BigDecimal tongMax = new BigDecimal("30");
        BigDecimal nguong = new BigDecimal("22.5");
        switch (ts.getKhuVuc()) {
            case "1":
                MDUT = MDUT.add(new BigDecimal("0.75"));
                break;
            case "2NT":
                MDUT = MDUT.add(new BigDecimal("0.5"));
                break;
            case "2":
                MDUT = MDUT.add(new BigDecimal("0.25"));
                break;
            case "3":
                // không cộng
                break;
        }
        switch (ts.getDoiTuong()) {
            case "01":
                MDUT = MDUT.add(new BigDecimal("2.00"));
            case "06a":
            case "06b":
                MDUT = MDUT.add(new BigDecimal("1.00"));
                break;
        }
        if (diemTruocUT.compareTo(nguong) < 0) {
            diemUT = MDUT;
        } else {
            diemUT = tongMax.subtract(diemTHXT).subtract(diemCong).divide(he_so_giam, 4, RoundingMode.HALF_UP).multiply(MDUT);
        }
        return diemUT;
    }
    
    public boolean hasTiengAnh(Object[] row) {
        return "N1".equalsIgnoreCase((String) row[1])
                || "N1".equalsIgnoreCase((String) row[2])
                || "N1".equalsIgnoreCase((String) row[3]);
    }
    
    public BigDecimal tinhDiemDGNL(DiemThiDTO dt, List<BangQuyDoiDTO> listQD) {
        BigDecimal diemDGNL = dt.getNL1();
        if (diemDGNL == null) {
            return null;
        }
        for (BangQuyDoiDTO qd : listQD) {
            BigDecimal diemA = qd.getdDiemA();
            BigDecimal diemB = qd.getdDiemB();
            // nằm trong khoảng quy đổi
            if (diemDGNL.compareTo(diemA) >= 0 && diemDGNL.compareTo(diemB) <= 0) {
                // lấy trung bình điểm quy đổi THPT
                BigDecimal diemC = qd.getdDiemC();
                BigDecimal diemD = qd.getdDiemD();
                return noiSuy(diemDGNL, diemA, diemB, diemC, diemD);
            }
        }
        return null;
    }

    private BigDecimal tinhTHXT(Object[] row, DiemThiDTO dt, boolean hasTA,BigDecimal diemCCQuyDoi) {
        String mon1 = (String) row[1];
        String mon2 = (String) row[2];
        String mon3 = (String) row[3];
        int hs1 = (Integer) row[4];
        int hs2 = (Integer) row[5];
        int hs3 = (Integer) row[6];
//        BigDecimal doLech = (BigDecimal) row[7];
        BigDecimal d1, d2, d3;
        if (hasTA) {
            BigDecimal diemThiTA = getDiemMon(dt, "N1");
            BigDecimal diemTA = diemThiTA.max(diemCCQuyDoi);
            d1 = mon1.equalsIgnoreCase("N1") ? diemTA : getDiemMon(dt, mon1);
            d2 = mon2.equalsIgnoreCase("N1") ? diemTA : getDiemMon(dt, mon2);
            d3 = mon3.equalsIgnoreCase("N1") ? diemTA : getDiemMon(dt, mon3);
        } else {
            d1 = getDiemMon(dt, mon1);
            d2 = getDiemMon(dt, mon2);
            d3 = getDiemMon(dt, mon3);
        }
        if (d1 == null || d2 == null || d3 == null) {
            return null;
        }
        int w = hs1 + hs2 + hs3;
        if (w == 0) {
            return null;
        }
        return d1.multiply(BigDecimal.valueOf(hs1))
                .add(d2.multiply(BigDecimal.valueOf(hs2)))
                .add(d3.multiply(BigDecimal.valueOf(hs3)))
                .divide(BigDecimal.valueOf(w), 4, RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(3));
//                .subtract(doLech);
    }
    
    private List<Object[]> getDanhSachToHop(String maNganh, HashMap<String, List<Object[]>> cache) {
        List<Object[]> list = cache.get(maNganh);
        if (list == null) {
            list = nvDAO.getToHopNganhByMaNganh(maNganh);
            cache.put(maNganh, list);
        }
        return list;
    }
    
    private DiemCongDTO getDiemCong(String cccd,String maNganh,String matohop,HashMap<String, DiemCongDTO> diemCongMap) {
        String keyDCFull = cccd + "_" + matohop;
        String keyCC = cccd + "_CC";
        DiemCongDTO dc = diemCongMap.get(keyDCFull);
        if (dc == null) {
            dc = diemCongMap.get(keyCC);
        }
        return dc;
    }
    
    private BigDecimal getDiemCCQuyDoi(DiemThiDTO dt) {
        if (dt.getN1_CC() != null && dt.getN1_CC().compareTo(BigDecimal.ZERO) > 0) {
            return dt.getN1_CC();
        }
        return BigDecimal.ZERO;
    }
    
    private BigDecimal getDiemCC(DiemCongDTO dc) {
        return (dc != null && dc.getDiemCC() != null) ? dc.getDiemCC() : BigDecimal.ZERO;
    }

    private BigDecimal getDiemUTXT(DiemCongDTO dc) {
        return (dc != null && dc.getDiemUtxt() != null) ? dc.getDiemUtxt() : BigDecimal.ZERO;
    }
    
    private BigDecimal noiSuy(BigDecimal x, BigDecimal a, BigDecimal b,BigDecimal c, BigDecimal d) {
        if (x == null || a == null || b == null || c == null || d == null) {
            return null;
        }
        if (b.compareTo(a) == 0) {
            return c;
        }
        BigDecimal ratio = x.subtract(a)
                .divide(b.subtract(a), 6, RoundingMode.HALF_UP);

        return c.add(ratio.multiply(d.subtract(c)))
                .setScale(2, RoundingMode.HALF_UP);
    }
    
    private BigDecimal quyDoiMonVSAT(String mon, BigDecimal x,List<BangQuyDoiDTO> listQD) {
        if (x == null || listQD == null) {
            return null;
        }
        for (BangQuyDoiDTO qd : listQD) {
            BigDecimal a = qd.getdDiemA(); // VSAT min
            BigDecimal b = qd.getdDiemB(); // VSAT max
            BigDecimal c = qd.getdDiemC(); // THPT min
            BigDecimal d = qd.getdDiemD(); // THPT max
            if (x.compareTo(a) > 0 && x.compareTo(b) <= 0) {
                return noiSuy(x, a, b, c, d);
            }
        }
        return null;
    }

//    private BigDecimal layDiemVSAT(DiemThiDTO dt,String mon,List<BangQuyDoiDTO> listQD) {
//        if (mon == null) {
//            return null;
//        }
//        BigDecimal raw;
//        switch (mon.toUpperCase()) {
//            case "TO":
//                raw = dt.getTO();
//                break;
//            case "LI":
//                raw = dt.getLI();
//                break;
//            case "HO":
//                raw = dt.getHO();
//                break;
//            case "SI":
//                raw = dt.getSI();
//                break;
//            case "VA":
//                raw = dt.getVA();
//                break;
//            case "DI":
//                raw = dt.getDI();
//                break;
//            case "SU":
//                raw = dt.getSU();
//                break;
//            case "N1":
//                raw = dt.getN1_THI();
//                break;
//            default:
//                return null;
//        }
//        if (raw == null) {
//            return null;
//        }
//        return quyDoiMonVSAT(mon, raw, listQD);
//    }
    
    private BigDecimal layDiemVSAT(DiemThiDTO dt, String mon, List<BangQuyDoiDTO> listQD, BigDecimal diemCCQuyDoi) {
        if (mon == null) {
            return null;
        }

        BigDecimal raw;

        switch (mon.toUpperCase()) {
            case "TO":
                raw = dt.getTO();
                break;
            case "LI":
                raw = dt.getLI();
                break;
            case "HO":
                raw = dt.getHO();
                break;
            case "SI":
                raw = dt.getSI();
                break;
            case "VA":
                raw = dt.getVA();
                break;
            case "DI":
                raw = dt.getDI();
                break;
            case "SU":
                raw = dt.getSU();
                break;
            case "N1":
                raw = dt.getN1_THI();
                break;
            default:
                return null;
        }

        if (raw == null) {
            return null;
        }

        // nội suy VSAT -> thang 10
        BigDecimal diemQuyDoi = quyDoiMonVSAT(mon, raw, listQD);

        if (diemQuyDoi == null) {
            return null;
        }

        // riêng tiếng Anh thì lấy max với CC quy đổi
        if ("N1".equalsIgnoreCase(mon)) {
            return diemQuyDoi.max(diemCCQuyDoi);
        }

        return diemQuyDoi;
    }

    private BigDecimal tinhDiemVSAT(Object[] row, DiemThiDTO dt, List<BangQuyDoiDTO> listQD1, List<BangQuyDoiDTO> listQD2, List<BangQuyDoiDTO> listQD3, BigDecimal diemCCQuyDoi) {
        {
            String mon1 = (String) row[1];
            String mon2 = (String) row[2];
            String mon3 = (String) row[3];
            int hs1 = (Integer) row[4];
            int hs2 = (Integer) row[5];
            int hs3 = (Integer) row[6];
//        BigDecimal d1 = layDiemVSAT(dt, mon1, listQD1);
//        BigDecimal d2 = layDiemVSAT(dt, mon2, listQD2);
//        BigDecimal d3 = layDiemVSAT(dt, mon3, listQD3);
            BigDecimal d1 = layDiemVSAT(dt, mon1, listQD1, diemCCQuyDoi);
            BigDecimal d2 = layDiemVSAT(dt, mon2, listQD2, diemCCQuyDoi);
            BigDecimal d3 = layDiemVSAT(dt, mon3, listQD3, diemCCQuyDoi);
            if (d1 == null || d2 == null || d3 == null) {
                return null;
            }
            int w = hs1 + hs2 + hs3;
            if (w == 0) {
                return null;
            }
            return d1.multiply(BigDecimal.valueOf(hs1))
                    .add(d2.multiply(BigDecimal.valueOf(hs2)))
                    .add(d3.multiply(BigDecimal.valueOf(hs3)))
                    .divide(BigDecimal.valueOf(w), 4, RoundingMode.HALF_UP)
                    .multiply(BigDecimal.valueOf(3));
        }
    }

    public void xetTuyen() {
        List<NguyenVongDTO> listNV = nvDAO.getAll();
        HashMap<String, DiemThiDTO> thpt_dgnl_Map = dtBus.thpt_dgnl_Map();
        HashMap<String, HashMap<String, DiemThiDTO>> vsatMap = dtBus.vsatMap();
        HashMap<String, ThiSinhDTO> thisinhMap = tsBus.thisinhMap();
        HashMap<String, DiemCongDTO> diemCongMap = dcBus.diemcongMap();
        HashMap<String, List<BangQuyDoiDTO>> bqdĐGNLMap = bqdBus.bqdDGNLMap();
        HashMap<String, List<BangQuyDoiDTO>> bqdVSATMap = bqdBus.bqdVSATMap();
        HashMap<String, NganhDTO> nganhMap = nganhBus.nganhMap();
        HashMap<String, List<Object[]>> cache = new HashMap<>();
        for (NguyenVongDTO nv : listNV) {
            String maNganh = nv.getNvManganh();
            NganhDTO nganh = nganhMap.get(maNganh);
            String cccd = nv.getNvCccd();
            ThiSinhDTO ts = thisinhMap.get(cccd);
            DiemThiDTO dtTHPT = thpt_dgnl_Map.get(cccd + "_PT4");
            DiemThiDTO dtDGNL = thpt_dgnl_Map.get(cccd + "_PT2");
            HashMap<String, DiemThiDTO> vsatByDot = vsatMap.get(cccd);
            if (dtTHPT == null && dtDGNL == null && (vsatByDot == null || vsatByDot.isEmpty())) {
                continue;
            }
            List<Object[]> list = getDanhSachToHop(maNganh, cache);
            BigDecimal max = BigDecimal.ZERO;
            BigDecimal diem_cong = BigDecimal.ZERO;
            BigDecimal diem_utqd = BigDecimal.ZERO;
            BigDecimal bestDiemTHXT = BigDecimal.ZERO;
            String bestToHop = "";
            String bestPhuongThuc = "";
            for (Object[] row : list) {
                String matohop = (String) row[0];
                BigDecimal doLech = (BigDecimal) row[7];
                DiemCongDTO dc = getDiemCong(cccd, maNganh, matohop, diemCongMap);
                BigDecimal diemCC = getDiemCC(dc);
                BigDecimal diemCCQuyDoi = getDiemCCQuyDoi(dtTHPT);
                BigDecimal diemUTXT = getDiemUTXT(dc);
                boolean hasTA = hasTiengAnh(row);
                // ================= THPT =================
                if (dtTHPT != null) {
                    BigDecimal diemTHXT = tinhTHXT(row, dtTHPT, hasTA, diemCCQuyDoi);
                    if (diemTHXT != null) {
                        BigDecimal diemTHGXT = diemTHXT.subtract(doLech);
                        BigDecimal diemTruocUT = hasTA ? diemTHGXT.add(diemUTXT) : diemTHGXT.add(diemCC).add(diemUTXT);
                        BigDecimal diemCongTinhUT = hasTA  ? diemUTXT : diemUTXT.add(diemCC);
                        BigDecimal diemUT = tinhDiemKhuVucDoiTuong(diemTruocUT, ts, diemTHXT, diemCongTinhUT);
                        BigDecimal diemXetTuyen = diemTruocUT.add(diemUT);
                        if (diemXetTuyen.compareTo(max) > 0) {
                            max = diemXetTuyen;
                            diem_cong = hasTA ? diemUTXT : diemUTXT.add(diemCC);
                            diem_utqd = diemUT;
                            bestDiemTHXT = diemTHXT;
                            bestToHop = matohop;
                            bestPhuongThuc = "PT4";
                        }
                    }
                }

// ================= ĐGNL =================
                if (dtDGNL != null) {
                    String keyDGNL = "DGNL_" + nganh.getNToHopGoc().trim();
                    List<BangQuyDoiDTO> bqdList = bqdĐGNLMap.get(keyDGNL);
                    if (bqdList != null && !bqdList.isEmpty()) {
                        BigDecimal diemTHXT = tinhDiemDGNL(dtDGNL, bqdList);
                        if (diemTHXT != null) {
                            BigDecimal diemTruocUT = diemTHXT.add(diemUTXT).add(diemCC);
                            BigDecimal tongDiemCong = (diemUTXT).add(diemCC);
                            BigDecimal diemUT = tinhDiemKhuVucDoiTuong(diemTruocUT, ts, diemTHXT,tongDiemCong);
                            BigDecimal diemXetTuyen = diemTruocUT.add(diemUT);
                            if (diemXetTuyen.compareTo(max) > 0) {
                                max = diemXetTuyen;
                                diem_cong = diemUTXT.add(diemCC);
                                diem_utqd = diemUT;
                                bestDiemTHXT = diemTHXT;
                                bestToHop = nganh.getNToHopGoc();
                                bestPhuongThuc = "PT2";
                            }
                        }
                    }
                }

// ================= VSAT =================
                if (vsatByDot != null && !vsatByDot.isEmpty()) {
                    String mon1 = (String) row[1];
                    String mon2 = (String) row[2];
                    String mon3 = (String) row[3];
                    List<BangQuyDoiDTO> listQD1 = bqdVSATMap.get("VSAT_" + mon1);
                    List<BangQuyDoiDTO> listQD2 = bqdVSATMap.get("VSAT_" + mon2);
                    List<BangQuyDoiDTO> listQD3 = bqdVSATMap.get("VSAT_" + mon3);
                    if (listQD1 != null && !listQD1.isEmpty() && listQD2 != null && !listQD2.isEmpty() && listQD3 != null && !listQD3.isEmpty()) {
                        for (DiemThiDTO dtVSAT : vsatByDot.values()) {
//                            BigDecimal diemTHXT = tinhDiemVSAT(row, dtVSAT, listQD1, listQD2, listQD3);
                            BigDecimal diemTHXT = tinhDiemVSAT(row,dtVSAT,listQD1,listQD2,listQD3,diemCCQuyDoi);
                            if (diemTHXT == null) {
                                continue;
                            }
                            BigDecimal diemTHGXT = diemTHXT.subtract(doLech);
                            BigDecimal diemTruocUT = hasTA ? diemTHGXT.add(diemUTXT) : diemTHGXT.add(diemUTXT).add(diemCC);
                            BigDecimal diemCongTinhUT = hasTA ? diemUTXT : diemUTXT.add(diemCC);

                            BigDecimal diemUT = tinhDiemKhuVucDoiTuong(diemTruocUT, ts, diemTHXT,diemCongTinhUT);
                            BigDecimal diemXetTuyen = diemTruocUT.add(diemUT);
                            if (diemXetTuyen.compareTo(max) > 0) {
                                max = diemXetTuyen;
                                diem_cong = hasTA ? diemUTXT : diemUTXT.add(diemCC);
                                diem_utqd = diemUT;
                                bestDiemTHXT = diemTHXT;
                                bestToHop = matohop;
                                bestPhuongThuc = "PT3";
                            }
                        }
                    }
                }
            }
            if (max.compareTo(BigDecimal.valueOf(30)) > 0) {
                max = BigDecimal.valueOf(30);
            }
            nv.setDiemThxt(bestDiemTHXT);
            nv.setDiemXettuyen(max);
            nv.setTtThm(bestToHop);
            nv.setDiemCong(diem_cong);
            nv.setDiemUtqd(diem_utqd);
            nv.setTtPhuongthuc(bestPhuongThuc);
            if (nganh != null && nganh.getNDiemSan() != null) {
                if (max.compareTo(nganh.getNDiemSan()) >= 0) {
                    nv.setNvKetqua("Trên sàn");
                } else {
                    nv.setNvKetqua("Dưới sàn");
                }

            }
        }
        nvDAO.updateBatch(listNV);
    }

    // ================= SEARCH =================
    public List<NguyenVongDTO> filter(String text,String tenNganh, String phuongThuc) {
        String t = text.toLowerCase().trim();
        List<NguyenVongDTO> result = new ArrayList<>();
        HashMap<String,String> mapTenNganh = nganhBus.getTenNganhByMaNganhMap();
        for (NguyenVongDTO nv : nvDAO.getAll()) {

            boolean matchTenNganh = (tenNganh == null || tenNganh.equals("Tất cả"))
                    || mapTenNganh.get(nv.getNvManganh()).equals(tenNganh);

            boolean matchPT = (phuongThuc == null || phuongThuc.equals("Tất cả"))
                    || nv.getTtPhuongthuc().equals(phuongThuc);
            boolean matchText = t.equals("") || nv.getNvCccd().toLowerCase().contains(t) || mapTenNganh.get(nv.getNvManganh()).toLowerCase().contains(t);
            if (matchTenNganh && matchPT && matchText ) {
                result.add(nv);
            }
        }

        return result;
    }

    // ================= SEARCH TEXT =================
    public List<NguyenVongDTO> timKiemText(String text) {
        String t = text.toLowerCase();
        ArrayList<NguyenVongDTO> result = new ArrayList<>();
        HashMap<String,String> mapTenNganh = nganhBus.getTenNganhByMaNganhMap();

        for (NguyenVongDTO nv : nvDAO.getAll()) {
            if ((nv.getNvCccd() != null && nv.getNvCccd().toLowerCase().contains(t))
                    || (nv.getNvManganh() != null && mapTenNganh.get(nv.getNvManganh()).toLowerCase().contains(t))) {
                result.add(nv);
            }
        }

        return result;
    }
    
    public int getColumnIndex(Row headerRow, String columnName) {
        for (Cell cell : headerRow) {
            if (cell.getStringCellValue().trim().equalsIgnoreCase(columnName)) {
                return cell.getColumnIndex();
            }
        }
        return -1;
    }

    private String getCell(Row row, int index) {
        if (index == -1) {
            return null;
        }
        Cell cell = row.getCell(index);
        if (cell == null) {
            return null;
        }
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue().trim();
            case NUMERIC:
                return String.valueOf(cell.getNumericCellValue());
            default:
                return cell.toString().trim();
        }
    }

    public List<NguyenVongDTO> readFile(String filePath) {
        List<NguyenVongDTO> list = new ArrayList<>();
        try {
            FileInputStream fis = new FileInputStream(new File(filePath));
            Workbook workbook = new XSSFWorkbook(fis);
            for (int s = 1; s <= 2; s++) {
                Sheet sheet = workbook.getSheetAt(s);
                Row header = sheet.getRow(4);
                int cccdCol = getColumnIndex(header, "CCCD");
                int nvCol = getColumnIndex(header, "Thứ tự NV");
                int maNganhCol = getColumnIndex(header, "Mã xét tuyển");
                for (int i = 5; i <= sheet.getLastRowNum(); i++) {
                    Row row = sheet.getRow(i);
                    if (row == null) {
                        continue;
                    }
                    String cccd = getCell(row, cccdCol);
                    String maNganh = getCell(row, maNganhCol);
                    String nvStr = getCell(row, nvCol);
                    if (cccd == null || maNganh == null || cccd.trim().isEmpty() || maNganh.trim().isEmpty() || nvStr == null || nvStr.trim().isEmpty()) {
                        continue;
                    }
                    NguyenVongDTO nv = new NguyenVongDTO();
                    nv.setNvCccd(cccd);
                    nv.setNvTt((int) Double.parseDouble(nvStr));
                    nv.setNvManganh(maNganh);
                    nv.setNvKeys(cccd + "_" + nv.getNvManganh() + "_" + nv.getNvTt());
                    list.add(nv);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // ================= IMPORT EXCEL =================
    public int importFromExcel(String filePath) {
        List<NguyenVongDTO> importList = readFile(filePath);
        if (importList == null || importList.isEmpty()) {
            return 0;
        }
        HashMap<String, Boolean> map = new HashMap<>();
        for (NguyenVongDTO nv : nvDAO.getAll()) {
            String key = nv.getNvCccd() + "_" + nv.getNvTt();
            map.put(key, true);
        }
        List<NguyenVongDTO> newList = new ArrayList<>();
        for (NguyenVongDTO nv : importList) {
            if (nv.getNvCccd() == null) {
                continue;
            }
            String key = nv.getNvCccd() + "_" + nv.getNvTt();
            if (!map.containsKey(key)) {
                newList.add(nv);
                map.put(key, true);
            }
        }
        if (!newList.isEmpty()) {
            nvDAO.insertList(newList);
            nvList.addAll(newList);
        }
        return newList.size();
    }

}