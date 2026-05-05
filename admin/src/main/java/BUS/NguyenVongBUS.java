package BUS;

import DAO.DiemCongDAO;
import DAO.NguyenVongDAO;
import DTO.DiemCongDTO;
import DTO.DiemThiDTO;
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

    public BigDecimal tinhDiemTHXT(DiemThiDTO diemThi, List<Object[]> list) {
        BigDecimal maxDiem = BigDecimal.ZERO;
        for (Object[] row : list) {
            String mon1 = (String) row[1];
            String mon2 = (String) row[2];
            String mon3 = (String) row[3];
            int hs1 = (Integer) row[4];
            int hs2 = (Integer) row[5];
            int hs3 = (Integer) row[6];
            BigDecimal doLech = (BigDecimal) row[7];
            BigDecimal d1 = getDiemMon(diemThi, mon1);
            BigDecimal d2 = getDiemMon(diemThi, mon2);
            BigDecimal d3 = getDiemMon(diemThi, mon3);
            if (d1 == null || d2 == null || d3 == null) {
                continue;
            }
            int w = hs1 + hs2 + hs3;
            if (w == 0) {
                continue;
            }
            BigDecimal tong = d1.multiply(BigDecimal.valueOf(hs1))
                    .add(d2.multiply(BigDecimal.valueOf(hs2)))
                    .add(d3.multiply(BigDecimal.valueOf(hs3)));

            BigDecimal diem = tong
                    .divide(BigDecimal.valueOf(w), 4, RoundingMode.HALF_UP)
                    .multiply(BigDecimal.valueOf(3));
            diem = diem.subtract(doLech);

            if (diem.compareTo(maxDiem) > 0) {
                maxDiem = diem;
            }
        }
        return maxDiem;
    }

    public BigDecimal tinhDiemKhuVucDoiTuong(BigDecimal diemTong, ThiSinhDTO ts) {
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
        if (diemTong.compareTo(nguong) < 0) {
            diemUT = MDUT;
        } else {
            diemUT = tongMax.subtract(diemTong).divide(he_so_giam, 4, RoundingMode.HALF_UP).multiply(MDUT);
        }
        return diemUT;
    }

    public void xetTuyen() {
        List<NguyenVongDTO> listNV = nvDAO.getAll();
        HashMap<String, DiemThiDTO> diemThiMap = dtBus.diemthiMap();
        HashMap<String, ThiSinhDTO> thisinhMap = tsBus.thisinhMap();
        HashMap<String, DiemCongDTO> diemCongMap = dcBus.diemcongMap();
        HashMap<String, List<Object[]>> cache = new HashMap<>();

        for (NguyenVongDTO nv : listNV) {
            String maNganh = nv.getNvManganh();
            String cccd = nv.getNvCccd();
            DiemThiDTO dt = diemThiMap.get(cccd);
            ThiSinhDTO ts = thisinhMap.get(cccd);
            if (dt == null) {
                continue;
            }
            List<Object[]> list = cache.get(maNganh);
            if (list == null) {
                list = nvDAO.getToHopNganhByMaNganh(maNganh);
                cache.put(maNganh, list);
            }

            // ===== 1. tính THXT =====
            BigDecimal thxt = tinhDiemTHXT(dt, list);
            nv.setDiemThxt(thxt);

            // ===== 2. lấy điểm cộng theo từng tổ hợp =====
            BigDecimal max = BigDecimal.ZERO;
            BigDecimal diem_cong = BigDecimal.ZERO;
            BigDecimal diem_utqd = BigDecimal.ZERO;
            for (Object[] row : list) {
                String matohop = (String) row[0];
                String key = cccd + "_" + maNganh + "_" + matohop + "_" + "THPT";
                DiemCongDTO dc = diemCongMap.get(key);
                BigDecimal diem_Cong_Tong = (dc != null && dc.getDiemTong() != null) ? dc.getDiemTong() : BigDecimal.ZERO;

                BigDecimal tong = thxt.add(diem_Cong_Tong);
                BigDecimal diemUT = tinhDiemKhuVucDoiTuong(tong, ts);
                tong = tong.add(diemUT);
                if (tong.compareTo(max) > 0) {
                    max = tong;
                    diem_cong = diem_Cong_Tong;
                    diem_utqd = diemUT;
                }
            }

            // ===== 3. set điểm xét =====
            nv.setDiemCong(diem_cong);
            nv.setDiemXettuyen(max);
            nv.setDiemUtqd(diem_utqd);
        }
        nvDAO.updateBatch(listNV);
    }

    // ================= SEARCH =================
    public ArrayList<NguyenVongDTO> timKiem(String cccd, String manganh, String ketqua) {
        ArrayList<NguyenVongDTO> result = new ArrayList<>();

        for (NguyenVongDTO nv : nvDAO.getAll()) {

            boolean matchCccd = (cccd == null || cccd.isEmpty())
                    || nv.getNvCccd().contains(cccd);

            boolean matchNganh = (manganh == null || manganh.equals("Tất cả"))
                    || nv.getNvManganh().equals(manganh);

            boolean matchKQ = (ketqua == null || ketqua.equals("Tất cả"))
                    || (nv.getNvKetqua() != null && nv.getNvKetqua().equals(ketqua));

            if (matchCccd && matchNganh && matchKQ) {
                result.add(nv);
            }
        }

        return result;
    }

    // ================= SEARCH TEXT =================
    public ArrayList<NguyenVongDTO> timKiemText(String text) {
        String t = text.toLowerCase();
        ArrayList<NguyenVongDTO> result = new ArrayList<>();

        for (NguyenVongDTO nv : nvDAO.getAll()) {
            if ((nv.getNvCccd() != null && nv.getNvCccd().toLowerCase().contains(t))
                    || (nv.getNvManganh() != null && nv.getNvManganh().toLowerCase().contains(t))) {
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