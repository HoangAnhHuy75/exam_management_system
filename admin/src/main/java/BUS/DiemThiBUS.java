package BUS;

import DAO.DiemThiDAO;
import DAO.ThiSinhDAO;
import DTO.DiemThiDTO;
import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
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
public class DiemThiBUS {
    private DiemThiDAO diemThiDao = new DiemThiDAO();
    private ThiSinhDAO thiSinhDao = new ThiSinhDAO();
    private HashSet<String> cccdCache = null;
    private ArrayList<DiemThiDTO> diemThiList = new ArrayList<>();
//    cache
//    public ArrayList<DiemThiDTO> getList(){
//
//    if(diemThiList.isEmpty()){
//        diemThiList = diemThiDao.getAllDiem();
//    }
//
//    return diemThiList;
//}
    // lấy danh sách điểm thi
    public ArrayList<DiemThiDTO> getList(){
        diemThiList = diemThiDao.getAllDiem();
        return diemThiList;
    }
    
    // load cccd combobox
    public List<String> loadCbbCccd(){
        return thiSinhDao.getAllCCCD();
    }
    // load sbd khi chọn cccd
    public List<String> loadCbbSBD(String cccd) {
        return thiSinhDao.getSBDByCccd(cccd);
    }
    // thêm điểm
    public int insert(DiemThiDTO dt) {
        if(checkDup(dt.getCccd())) {
            return 0;
        }
        int success = diemThiDao.insert(dt);
        return success;
    }
    
    // sửa điểm
    public int update(DiemThiDTO dt) {
        int success = diemThiDao.update(dt);
        return success;
    }
    
    // xóa điểm
    public int delete(DiemThiDTO dt) {
        int success = diemThiDao.delete(dt);
        return success;
    }
    
    //kiểm tra trùng cccd
    public boolean checkDup(String cccd) {
        return diemThiDao.getAllDiem().stream()
                .anyMatch(n -> n.getCccd().equalsIgnoreCase(cccd));
    }
    
    //tìm kiếm theo cccd
    public ArrayList<DiemThiDTO> findBycccd(String cccd) {
        ArrayList<DiemThiDTO> rs = new ArrayList<>();
        String cancuoc = cccd != null ? cccd.toLowerCase() : "";
        for(DiemThiDTO dt : diemThiDao.getAllDiem()) {
            boolean matchCccd = cancuoc.isEmpty() || dt.getCccd().toLowerCase().contains(cancuoc);
            if(matchCccd) {
                rs.add(dt);
            }
        }
        return rs;
    }
    
    // tìm 1 cccd
    public DiemThiDTO findOneByCCCD(String cccd) {
        for (DiemThiDTO dt : diemThiDao.getAllDiem()) {
            if (dt.getCccd().equals(cccd)) {
                return dt;
            }
        }
        return null;
    }
    
    //filter theo phương thức xét tuyển
    public ArrayList<DiemThiDTO> filterByPTXT(String pt) {
        String ptText = convertPhuongThuc(pt);
        ArrayList<DiemThiDTO> list = new ArrayList<>();
        
        for(DiemThiDTO dt: diemThiDao.getAllDiem()) {
            if(dt.getD_phuongthuc() == null) continue;
            if(dt.getD_phuongthuc().equalsIgnoreCase(ptText)) {
                list.add(dt);
            }
        }
        return list;
    }
    //filter nâng cao (theo loại điểm và môn)
    public ArrayList<DiemThiDTO> filterByLoaiDiemVaMon(String mon,String loai){
        ArrayList<DiemThiDTO> list = new ArrayList<>();
        for(DiemThiDTO dt : diemThiDao.getAllDiem()){
            BigDecimal diem = layDiemTheoMon(dt, mon); 
            if(diem == null) continue;
            if(loai.equals("Giỏi") && diem.compareTo(new BigDecimal("8")) >= 0)
                list.add(dt);
            else if(loai.equals("Khá") && diem.compareTo(new BigDecimal("6.5")) >= 0 && diem.compareTo(new BigDecimal("8")) < 0)
                list.add(dt);
            else if(loai.equals("Trung bình") && diem.compareTo(new BigDecimal("5")) >= 0 && diem.compareTo(new BigDecimal("6.5")) < 0 )
                list.add(dt);
            else if(loai.equals("Yếu") && diem.compareTo(new BigDecimal("3.5")) >= 0 && diem.compareTo(new BigDecimal("5")) < 0)
                list.add(dt);
            else if(loai.equals("Kém") && diem.compareTo(new BigDecimal("3.5")) < 0)
                list.add(dt);
        }
        return list;
    }
    
    // Thống kê theo môn
    public HashMap<String, Integer> thongKetheoMon(String mon) {
        HashMap<String,Integer> map = new HashMap<>();
        map.put("Giỏi", 0);
        map.put("Khá", 0);
        map.put("Trung bình", 0);
        map.put("Yếu", 0);
        map.put("Kém", 0);
        
        for(DiemThiDTO dt : diemThiDao.getAllDiem()) {
            BigDecimal diem = layDiemTheoMon(dt, mon);
           
            if(diem == null) continue;
            
            if(diem.compareTo(new BigDecimal("8")) >= 0 ){
                map.put("Giỏi", map.get("Giỏi") + 1);
            }
            else if(diem.compareTo(new BigDecimal("6.5")) >= 0 ){
                map.put("Khá", map.get("Khá") + 1);
            }
            else if(diem.compareTo(new BigDecimal("5")) >= 0 ){
                map.put("Trung bình", map.get("Trung bình") + 1);
            }
            else if(diem.compareTo(new BigDecimal("3.5")) >= 0 ){
                map.put("Yếu", map.get("Yếu") + 1);
            }
            else {
                map.put("Kém", map.get("Kém") + 1);
            }
        }
        return map;
    }
    
    public int updateList(List<DiemThiDTO> list) {
        try {
            if (list == null || list.isEmpty()) {
                return 0;
            }
            diemThiDao.updateAll(list);
            return list.size();
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
    
    public HashMap<String, DiemThiDTO> diemthiMap(){
        HashMap<String, DiemThiDTO> diemthiMap = new HashMap<>();
        for(DiemThiDTO dt : diemThiDao.getAllDiem())
            diemthiMap.put(dt.getCccd(), dt);
        return diemthiMap;
    }
    
    // lấy điểm theo môn
    public BigDecimal layDiemTheoMon(DiemThiDTO dt,String mon) {
        switch (mon) {
            case "Toán":
                return dt.getTO();
            case "Lí":
                return dt.getLI();
            case "Hóa":
                return dt.getHO();
            case "Sinh":
                return dt.getSI();
            case "Sử":
                return dt.getSU();
            case "Địa":
                return dt.getDI();
            case "Văn":
                return dt.getVA();
            case "CNCN":
                return dt.getCNCN();
            case "CNNN":
                return dt.getCNNN();
            case "Tin":
                return dt.getTI();
            case "KTPL":
                return dt.getKTPL();
            case "NK1":
                return dt.getNK1();
            case "NK2":
                return dt.getNK2();
            case "NL1":
                return dt.getNL1();
            case "N1_THI":
                return dt.getN1_THI();
            case "N1_CC":
                return dt.getN1_CC();
        }
        return null;
    }
    
    
    public int getColumnIndex(Row header, String columnName) {
        for (Cell cell : header) {
            if (cell.getStringCellValue().trim().equalsIgnoreCase(columnName)) {
                return cell.getColumnIndex();
            }
        }
        return -1;
    }
    
    public String getCell(Row row, int index){
        if(index==-1)
            return null;
        Cell cell = row.getCell(index);
        if(cell == null)
            return null;
         switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue().trim();
            case NUMERIC:
                return String.valueOf(cell.getNumericCellValue());
            default:
                return cell.toString().trim();
        }
    }
    
    private BigDecimal parseBigDecimal(String value) {
        try {
            if (value == null || value.trim().isEmpty()) {
                return BigDecimal.ZERO.setScale(2);
            }
            return new BigDecimal(value.trim());
        } catch (Exception e) {
            return null;
        }
    }
    
    // đọc file
    public List<DiemThiDTO> readFile(String filePath) {
        List<DiemThiDTO> list = new ArrayList<>();
        try {
            FileInputStream fis = new FileInputStream(new File(filePath));
            Workbook work = new XSSFWorkbook(fis);
            Sheet sheet = work.getSheetAt(0);
            Row headerRow = sheet.getRow(0);
            int cccdCol = getColumnIndex(headerRow, "CCCD");
            int toanCol = getColumnIndex(headerRow, "TO");
            int vaCol = getColumnIndex(headerRow, "VA");
            int liCol = getColumnIndex(headerRow, "LI");
            int hoCol = getColumnIndex(headerRow, "HO");
            int siCol = getColumnIndex(headerRow, "SI");
            int suCol = getColumnIndex(headerRow, "SU");
            int diCol = getColumnIndex(headerRow, "DI");
            int gdcdCol = getColumnIndex(headerRow, "GDCD");
            int n1Col = getColumnIndex(headerRow, "NN");
            int ktplCol = getColumnIndex(headerRow, "KTPL");
            int tiCol = getColumnIndex(headerRow, "TIN");
            int cncnCol = getColumnIndex(headerRow, "CNCN");
            int cnnnCol = getColumnIndex(headerRow, "CNNN");
            int nk1Col = getColumnIndex(headerRow, "NK1");
            int nk2Col = getColumnIndex(headerRow, "NK2");
            int nk3Col = getColumnIndex(headerRow, "NK3");
            int nk4Col = getColumnIndex(headerRow, "NK4");
            int nk5Col = getColumnIndex(headerRow, "NK5");
            int nk6Col = getColumnIndex(headerRow, "NK6");
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) {
                    continue;
                }

                String cccd = getCell(row, cccdCol);
                if (cccd == null || cccd.trim().isEmpty()) {
                    continue;
                }

                DiemThiDTO dto = new DiemThiDTO();
                dto.setCccd(cccd);
                dto.setD_phuongthuc("THPT");
                dto.setTO(parseBigDecimal(getCell(row, toanCol)));
                dto.setVA(parseBigDecimal(getCell(row, vaCol)));
                dto.setLI(parseBigDecimal(getCell(row, liCol)));
                dto.setHO(parseBigDecimal(getCell(row, hoCol)));
                dto.setSI(parseBigDecimal(getCell(row, siCol)));
                dto.setSU(parseBigDecimal(getCell(row, suCol)));
                dto.setDI(parseBigDecimal(getCell(row, diCol)));
                dto.setGDCD(parseBigDecimal(getCell(row, gdcdCol)));
                dto.setN1_THI(parseBigDecimal(getCell(row, n1Col)));
                dto.setKTPL(parseBigDecimal(getCell(row, ktplCol)));
                dto.setTI(parseBigDecimal(getCell(row, tiCol)));
                dto.setCNCN(parseBigDecimal(getCell(row, cncnCol)));
                dto.setCNNN(parseBigDecimal(getCell(row, cnnnCol)));
                dto.setNK1(parseBigDecimal(getCell(row, nk1Col)));
                dto.setNK2(parseBigDecimal(getCell(row, nk2Col)));
                dto.setNK3(parseBigDecimal(getCell(row, nk3Col)));
                dto.setNK4(parseBigDecimal(getCell(row, nk4Col)));
                dto.setNK5(parseBigDecimal(getCell(row, nk5Col)));
                dto.setNK6(parseBigDecimal(getCell(row, nk6Col)));
                list.add(dto);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    // import điểm thi
    public int importFromExcel(String filePath) {
        List<DiemThiDTO> importList = readFile(filePath);
        if (importList == null || importList.isEmpty()) {
            return 0;
        }
        if (cccdCache == null) {
            cccdCache = new HashSet<>(diemThiDao.getAllCCCD());
        }
        List<DiemThiDTO> newList = new ArrayList<>();
        for (DiemThiDTO dt : importList) {
            if (dt.getCccd() == null || dt.getCccd().trim().isEmpty()) {
                continue;
            }
            String cccd = dt.getCccd().trim();
            if (!cccdCache.contains(cccd)) {
                newList.add(dt);
                cccdCache.add(cccd); // tránh trùng DB + file
            }
        }
        if (!newList.isEmpty()) {
            diemThiDao.saveAll(newList);
        }
        return newList.size();
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
}
