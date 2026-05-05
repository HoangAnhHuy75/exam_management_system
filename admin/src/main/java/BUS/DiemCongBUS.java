package BUS;

import DAO.DiemCongDAO;
import DTO.DiemCongDTO;
import DTO.DiemThiDTO;
import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.poi.ss.usermodel.Cell;
import static org.apache.poi.ss.usermodel.CellType.NUMERIC;
import static org.apache.poi.ss.usermodel.CellType.STRING;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author HP
 */
public class DiemCongBUS {
    private DiemCongDAO diemCongDao = new DiemCongDAO();
    private DiemThiBUS dtBus = new DiemThiBUS();
    private ArrayList<DiemCongDTO> diemCongList = new ArrayList<>();
    private NganhBUS ngB = new NganhBUS();
    private Map<String, BigDecimal> ccMap = new HashMap<>();
    // lấy danh sách điểm thi
    public ArrayList<DiemCongDTO> getList(){
        diemCongList = diemCongDao.getAllDiemCong();
        return diemCongList;
    }
    
    // load mã ngành vào cbb
    public List<String> loadMaNganh() {
        return diemCongDao.getAllMaNganh();
    }
    
    // load mã tổ hợp vào cbb
    public List<String> loadMaToHop() {
        return diemCongDao.getAllMaToHop();
    }
    
    // thêm điểm
    public int insert(DiemCongDTO dc) {
        if(checkDup(dc.getDc_keys())) {
            return 0;
        }
        int success = diemCongDao.insert(dc);
        return success;
    }
    
    // sửa điểm
    public int update(DiemCongDTO dc) {
        int success = diemCongDao.update(dc);
        return success;
    }
    
    // xóa điểm
    public int delete(DiemCongDTO dc) {
        int success = diemCongDao.delete(dc);
        return success;
    }
    
    // tìm 1 dc_keys
    public DiemCongDTO findOneByDcKeys(String dcKeys) {

    for(DiemCongDTO dc : diemCongDao.getAllDiemCong()) {
        if(dc.getDc_keys().equals(dcKeys)) {
            return dc;
        }
    }

    return null;
    }
    // kiểm tra trùng dc_keys
    public boolean checkDup(String dcKeys) {
        return diemCongDao.getAllDiemCong().stream()
                .anyMatch(n -> n.getDc_keys().equalsIgnoreCase(dcKeys));
    }
    
    // Tìm kiếm theo cccd
    public ArrayList<DiemCongDTO> findByCccd (String cccd) {
        ArrayList<DiemCongDTO> rs = new ArrayList<>();
        String cancuoc = cccd != null ? cccd.toLowerCase() : "";
        for(DiemCongDTO dc : diemCongDao.getAllDiemCong()) {
            boolean matchCccd = cancuoc.isEmpty() || dc.getTs_cccd().toLowerCase().contains(cancuoc);
            if(matchCccd) {
                rs.add(dc);
            }
        }
        return rs;
    }
    
    // filter tổng hợp
    public ArrayList<DiemCongDTO> filterAll(String tenNganh, String maToHop, String pt) {
        ArrayList<DiemCongDTO> list = new ArrayList<>();
        HashMap<String,String> map = ngB.nganhMap();

        String maNganh = map.get(tenNganh);
        String ptText = convertPhuongThuc(pt);

        for (DiemCongDTO dc : diemCongDao.getAllDiemCong()) {

            // lọc ngành
            if (tenNganh != null && !tenNganh.equals("Chọn tên ngành")) {
                if (maNganh == null || !dc.getManganh().equalsIgnoreCase(maNganh)) continue;
            }

            // lọc tổ hợp
            if (maToHop != null && !maToHop.equals("Chọn Mã Tổ Hợp")) {
                if (dc.getMatohop() == null || !dc.getMatohop().equalsIgnoreCase(maToHop)) continue;
            }

            // lọc phương thức
            if (pt != null && !pt.equals("Bộ lọc ptxt")) {
                if (dc.getPhuongthuc() == null || !dc.getPhuongthuc().equalsIgnoreCase(ptText)) continue;
            }

            list.add(dc);
        }

        return list;
    }
    
    public String convertPhuongThuc(String pt) {
        switch (pt) {
            case "Tuyển thẳng":
                return "PT1";
            case "ĐGNL":
                return "PT2";
            case "VSAT":
                return "PT3";
            case "THPT":
                return "PT4";
            default:
                return "";
        }
    }
    
    public HashMap<String, DiemCongDTO> diemcongMap(){
        HashMap<String,DiemCongDTO> diemcongMap = new HashMap<>();
        for(DiemCongDTO dc : diemCongDao.getAllDiemCong()){
            String key = dc.getTs_cccd() + "_" + dc.getManganh() + "_" + dc.getMatohop() + "_" + dc.getPhuongthuc();
            diemcongMap.put(key, dc);
        }
        return diemcongMap;
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
                return String.valueOf((long) cell.getNumericCellValue());
            default:
                return cell.toString().trim();
        }
    }
    
    public HashMap<String, BigDecimal> diemchungchiMap() {
        HashMap<String, BigDecimal> diemchungchiMap = new HashMap<>();
        for (DiemCongDTO dc : diemCongDao.getAllDiemCong()) {
            diemchungchiMap.put(dc.getTs_cccd(), dc.getDiemCC());
        }
        return diemchungchiMap;
    }

    public List<DiemCongDTO> readFileCC(String filePath) {
        List<DiemCongDTO> listCongN1 = new ArrayList<>();
        List<DiemThiDTO> listQuyDoiN1 = new ArrayList<>();
        try {
            FileInputStream fis = new FileInputStream(filePath);
            Workbook wb = new XSSFWorkbook(fis);
            Sheet sheet = wb.getSheetAt(0);
            Row header = sheet.getRow(0);
            int cccdCol = getColumnIndex(header, "CCCD");
            int diemCol = getColumnIndex(header, "Điểm cộng");
            int diemquydoiCol = getColumnIndex(header, "Điểm quy đổi");
            HashMap<String, DiemThiDTO> diemthiMap = dtBus.diemthiMap();
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) {
                    continue;
                }
                String cccd = getCell(row, cccdCol);
                String diemStr = getCell(row, diemCol);
                String diemquydoiStr = getCell(row, diemquydoiCol);
                if (cccd == null || cccd.trim().isEmpty()) {
                    continue;
                }
                BigDecimal diemCC = BigDecimal.ZERO;
                try {
                    if (diemStr != null && !diemStr.isEmpty()) {
                        diemCC = new BigDecimal(diemStr);
                    }
                } catch (Exception e) {
                    diemCC = BigDecimal.ZERO;
                }
                BigDecimal diemquydoi = new BigDecimal(diemquydoiStr);
                DiemThiDTO dt = diemthiMap.get(cccd);
                if (dt != null) {
                    dt.setN1_CC(diemquydoi);
                    listQuyDoiN1.add(dt);
                }
                DiemCongDTO dc = new DiemCongDTO();
                dc.setTs_cccd(cccd);
                dc.setDiemCC(diemCC);
                dc.setManganh(null);
                dc.setMatohop(null);
                dc.setDiemUtxt(BigDecimal.ZERO);
                dc.setDiemTong(diemCC);
                dc.setPhuongthuc("THPT"); // phân biệt với UTXT
                dc.setDc_keys(cccd); // chỉ cần CCCD

                listCongN1.add(dc);
            }
            dtBus.updateList(listQuyDoiN1);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listCongN1;
    }

    public int importCC(String filePath) {
        try {
            List<DiemCongDTO> list = readFileCC(filePath);
            if (list == null || list.isEmpty()) {
                return 0;
            }
            diemCongDao.upsertDiemCC(list); // hoặc upsert nếu bạn cần
            return list.size();
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    private String normalizeMon(String mon) {
        if (mon == null) {
            return "";
        }
        mon = mon.trim().toLowerCase();
        switch (mon) {
            case "tiếng anh":
                return "N1";
            case "lịch sử":
                return "SU";
            case "địa lí":
            case "địa lý":
                return "DI";
            case "toán học":
                return "TO";
            case "ngữ văn":
                return "VA";
            case "vật lí":
            case "vật lý":
                return "LI";
            case "hóa học":
                return "HO";
            case "khoa học xã hội và hành vi":
                return "KHAC";
            default:
                return mon.toUpperCase();
        }
    }

    public List<DiemCongDTO> readFileUTXT(String filePath, Map<String, BigDecimal> ccMap) {
        List<DiemCongDTO> diemCongList = new ArrayList<>();
        try {
            FileInputStream fis = new FileInputStream(new File(filePath));
            Workbook work = new XSSFWorkbook(fis);
            Sheet sheet = work.getSheetAt(1);
            Row headerRow = sheet.getRow(0);
            int cccdCol = getColumnIndex(headerRow, "CCCD");
            int tenNganhCol = getColumnIndex(headerRow, "Tên ngành");
            int monDatGiaiCol = getColumnIndex(headerRow, "Môn đạt giải");
            int diemCol = getColumnIndex(headerRow, "Điểm cộng cho môn đạt giải");
            int diemKhongMonCol = getColumnIndex(headerRow, "Điểm cộng cho THXT ko có môn đạt giải");
            HashMap<String, List<Object[]>> cacheNganh = new HashMap<>();
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) {
                    continue;
                }
                String cccd = getCell(row, cccdCol);
                String tenNganh = getCell(row, tenNganhCol);
                if (cccd == null || cccd.trim().isEmpty() || tenNganh == null) {
                    continue;
                }
                List<Object[]> list = cacheNganh.get(tenNganh);
                if (list == null) {
                    list = diemCongDao.buildDiemCongFromExcel(tenNganh);
                    cacheNganh.put(tenNganh, list);
                }
                String monDatGiai = getCell(row, monDatGiaiCol);
                double diem = row.getCell(diemCol) != null ? row.getCell(diemCol).getNumericCellValue() : 0;
                double diemKhongMon = row.getCell(diemKhongMonCol) != null ? row.getCell(diemKhongMonCol).getNumericCellValue() : 0;
                HashMap<String, BigDecimal> diemchungchiMap = diemchungchiMap();
                for (Object[] obj : list) {
                    String maToHop = obj[0].toString();
                    String maNganh = obj[1].toString();
                    String mon1 = obj[2] != null ? obj[2].toString() : "";
                    String mon2 = obj[3] != null ? obj[3].toString() : "";
                    String mon3 = obj[4] != null ? obj[4].toString() : "";
                    boolean trungMon = false;
                    if (monDatGiai != null) {
                        String m = normalizeMon(monDatGiai);
                        if (m.equals(mon1) || m.equals(mon2) || m.equals(mon3)) {
                            trungMon = true;
                        }
                    }
                    BigDecimal utxt = trungMon ? BigDecimal.valueOf(diem) : BigDecimal.valueOf(diemKhongMon);
                    BigDecimal diemCC = diemchungchiMap.getOrDefault(cccd, BigDecimal.ZERO);
                    DiemCongDTO dc = new DiemCongDTO();
                    dc.setTs_cccd(cccd);
                    dc.setManganh(maNganh);
                    dc.setMatohop(maToHop);
                    dc.setDiemUtxt(utxt);
                    dc.setDiemCC(diemCC);
                    dc.setDiemTong(diemCC.add(utxt));
                    dc.setPhuongthuc("THPT");
                    dc.setDc_keys(cccd + "_" + maNganh + "_" + maToHop);
                    diemCongList.add(dc);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return diemCongList;
    }

    public int importUTXT(String filePath) {
        try {
            List<DiemCongDTO> list = readFileUTXT(filePath, ccMap);
            if (list == null || list.isEmpty()) {
                return 0;
            }
            diemCongDao.insertList(list);
            return list.size();
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
    
    
}
