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
import org.apache.poi.ss.usermodel.WorkbookFactory;

/**
 *
 * @author HP
 */
public class DiemThiBUS {

    private DiemThiDAO diemThiDao = new DiemThiDAO();
    private ThiSinhDAO thiSinhDao = new ThiSinhDAO();
    private HashSet<String> keyCache = null;
    private ArrayList<DiemThiDTO> diemThiList = new ArrayList<>();
    private enum FileType {
        THPT,
        DGNL,
        VSAT,
        UNKNOWN
    }

    public ArrayList<DiemThiDTO> getList() {
        diemThiList = diemThiDao.getAllDiem();
        return diemThiList;
    }

    // load cccd combobox
    public List<String> loadCbbCccd() {
        return thiSinhDao.getAllCCCD();
    }

    // load sbd khi chọn cccd
    public List<String> loadCbbSBD(String cccd) {
        return thiSinhDao.getSBDByCccd(cccd);
    }

    // thêm điểm
    public int insert(DiemThiDTO dt) {
        if (checkDup(dt)) {
            return 0;
        }
        int success = diemThiDao.insert(dt);
        if (success > 0) {
            keyCache.add(buildKey(dt));
        }
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
    public boolean checkDup(DiemThiDTO dt) {
        if (keyCache == null) {
            keyCache = new HashSet<>();
            for (DiemThiDTO item : diemThiDao.getAllDiem()) {
                keyCache.add(buildKey(item));
            }
        }
        return keyCache.contains(buildKey(dt));
    }

    public int importExcel(String filePath) {
        FileType type = detectFileType(filePath);
        switch (type) {
            case THPT:
                return importTHPT(filePath);
            case VSAT:
                int vsat = importVSAT(filePath);
                int dgnl = importDGNL(filePath);
                // nếu cả 2 đều trùng
                if (vsat == -1 && dgnl == -1) {
                    return -1;
                }
                // cộng nhưng bỏ qua số âm
                return Math.max(vsat, 0) + Math.max(dgnl, 0);
            case DGNL:
                return importDGNL(filePath);
            default:
                return 0;
        }
    }

    private FileType detectFileType(String filePath) {
        try (FileInputStream fis = new FileInputStream(new File(filePath)); Workbook work = WorkbookFactory.create(fis)) {
            Sheet sheet0 = work.getSheetAt(0);
            Row headerRow0 = sheet0.getRow(0);
            boolean hasVSAT = false;
            boolean hasTHPT = false;
            if (headerRow0 != null) {
                boolean hasCMND = getColumnIndex(headerRow0, "CMND") != -1;
                boolean hasMAMONTHI = getColumnIndex(headerRow0, "MAMONTHI") != -1;
                if (hasCMND && hasMAMONTHI) {
                    hasVSAT = true;
                }
                boolean hasTO = getColumnIndex(headerRow0, "TO") != -1;
                boolean hasVA = getColumnIndex(headerRow0, "VA") != -1;
                if (hasTO && hasVA) {
                    hasTHPT = true;
                }
            }
            boolean hasDGNL = false;
            if (work.getNumberOfSheets() > 1) {
                Sheet sheet1 = work.getSheetAt(1);
                Row headerRow1 = sheet1.getRow(0);
                if (headerRow1 != null) {
                    boolean hasCMND = getColumnIndex(headerRow1, "CMND") != -1;
                    boolean hasDIEM = getColumnIndex(headerRow1, "DIEM") != -1;
                    if (hasCMND && hasDIEM) {
                        hasDGNL = true;
                    }
                }
            }
            if (hasVSAT) {
                return FileType.VSAT;
            }
            if (hasTHPT) {
                return FileType.THPT;
            }
            if (hasDGNL) {
                return FileType.DGNL;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return FileType.UNKNOWN;
    }

    //tìm kiếm theo cccd
    public ArrayList<DiemThiDTO> findBycccd(String cccd) {
        ArrayList<DiemThiDTO> rs = new ArrayList<>();
        String cancuoc = cccd != null ? cccd.toLowerCase() : "";
        for (DiemThiDTO dt : diemThiDao.getAllDiem()) {
            boolean matchCccd = cancuoc.isEmpty() || dt.getCccd().toLowerCase().contains(cancuoc);
            if (matchCccd) {
                rs.add(dt);
            }
        }
        return rs;
    }

    // tìm 1 cccd
    public DiemThiDTO findById(int idDiemThi) {
        return diemThiDao.findById(idDiemThi);
    }

    //filter theo phương thức xét tuyển
    public ArrayList<DiemThiDTO> filterByPTXT(String pt) {
//        String ptText = convertPhuongThuc(pt);
        ArrayList<DiemThiDTO> list = new ArrayList<>();

        for (DiemThiDTO dt : diemThiDao.getAllDiem()) {
            if (dt.getD_phuongthuc() == null) {
                continue;
            }
            if (dt.getD_phuongthuc().equalsIgnoreCase(pt)) {
                list.add(dt);
            }
        }
        return list;
    }

    //filter nâng cao (theo loại điểm và môn)
    public ArrayList<DiemThiDTO> filterByLoaiDiemVaMon(String mon, String loai) {
        ArrayList<DiemThiDTO> list = new ArrayList<>();
        for (DiemThiDTO dt : diemThiDao.getAllDiem()) {
            BigDecimal diem = layDiemTheoMon(dt, mon);
            if (diem == null) {
                continue;
            }
            if (loai.equals("Giỏi") && diem.compareTo(new BigDecimal("8")) >= 0) {
                list.add(dt);
            } else if (loai.equals("Khá") && diem.compareTo(new BigDecimal("6.5")) >= 0 && diem.compareTo(new BigDecimal("8")) < 0) {
                list.add(dt);
            } else if (loai.equals("Trung bình") && diem.compareTo(new BigDecimal("5")) >= 0 && diem.compareTo(new BigDecimal("6.5")) < 0) {
                list.add(dt);
            } else if (loai.equals("Yếu") && diem.compareTo(new BigDecimal("3.5")) >= 0 && diem.compareTo(new BigDecimal("5")) < 0) {
                list.add(dt);
            } else if (loai.equals("Kém") && diem.compareTo(new BigDecimal("3.5")) < 0) {
                list.add(dt);
            }
        }
        return list;
    }

    // Thống kê theo môn
    public HashMap<String, Integer> thongKetheoMon(String mon) {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("Giỏi", 0);
        map.put("Khá", 0);
        map.put("Trung bình", 0);
        map.put("Yếu", 0);
        map.put("Kém", 0);
        for (DiemThiDTO dt : diemThiDao.getAllDiem()) {
            BigDecimal diem = layDiemTheoMon(dt, mon);
            if (diem == null) {
                continue;
            }
            if (diem.compareTo(new BigDecimal("8")) >= 0) {
                map.put("Giỏi", map.get("Giỏi") + 1);
            } else if (diem.compareTo(new BigDecimal("6.5")) >= 0) {
                map.put("Khá", map.get("Khá") + 1);
            } else if (diem.compareTo(new BigDecimal("5")) >= 0) {
                map.put("Trung bình", map.get("Trung bình") + 1);
            } else if (diem.compareTo(new BigDecimal("3.5")) >= 0) {
                map.put("Yếu", map.get("Yếu") + 1);
            } else {
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

    private String buildKeyVSAT(DiemThiDTO dt) {
        return dt.getCccd().trim() + "_VSAT_" + (dt.getDotthi() == null ? "" : dt.getDotthi().trim());
    }

    public HashMap<String, HashMap<String, DiemThiDTO>> vsatMap() {
        HashMap<String, HashMap<String, DiemThiDTO>> map = new HashMap<>();
        for (DiemThiDTO dt : diemThiDao.getAllDiem()) {
            if (!"VSAT".equals(dt.getD_phuongthuc())) {
                continue;
            }
            String cccd = dt.getCccd();
            String dotThi = dt.getDotthi();
            if (cccd == null || dotThi == null) {
                continue;
            }
            map.putIfAbsent(cccd, new HashMap<>());
            map.get(cccd).put(dotThi, dt);
        }
        return map;
    }

    private String buildKey(DiemThiDTO dt) {
        return dt.getCccd().trim() + "_" + dt.getD_phuongthuc().trim() + "_" + dt.getDotthi();
    }

    public HashMap<String, DiemThiDTO> thpt_dgnl_Map() {
        HashMap<String, DiemThiDTO> diemthiMap = new HashMap<>();
        for (DiemThiDTO dt : diemThiDao.getAllDiem()) {
            // bỏ qua VSAT
            if ("VSAT".equals(dt.getD_phuongthuc())) {
                continue;
            }
            diemthiMap.put(dt.getCccd() + "_" + dt.getD_phuongthuc(),dt);
        }

        return diemthiMap;
    }

    public HashMap<String, DiemThiDTO> diemthiMap2() {
        HashMap<String, DiemThiDTO> diemthiMap = new HashMap<>();
        for (DiemThiDTO dt : diemThiDao.getAllDiem()) {
            diemthiMap.put(buildKey(dt),dt);
        }
        return diemthiMap;
    }

    // lấy điểm theo môn
    public BigDecimal layDiemTheoMon(DiemThiDTO dt, String mon) {
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
            case "GDCD":
                return dt.getGDCD();
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
            case "NK3":
                return dt.getNK3();
            case "NK4":
                return dt.getNK4();
            case "NK5":
                return dt.getNK5();
            case "NK6":
                return dt.getNK6();
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

    public String getCell(Row row, int index) {
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
    
    public HashMap<String, String> getDotThiTotNhat(List<DiemThiDTO> list) {
        HashMap<String, HashMap<String, BigDecimal>> sum = new HashMap<>();
        for (DiemThiDTO dt : list) {
            String cccd = dt.getCccd();
            String dot = dt.getDotthi();
            if (cccd == null || dot == null) {
                continue;
            }
            sum.putIfAbsent(cccd, new HashMap<>());
            HashMap<String, BigDecimal> mapDot = sum.get(cccd);
            BigDecimal total = BigDecimal.ZERO;
            if (dt.getTO() != null) {
                total = total.add(dt.getTO());
            }
            if (dt.getLI() != null) {
                total = total.add(dt.getLI());
            }
            if (dt.getHO() != null) {
                total = total.add(dt.getHO());
            }
            if (dt.getVA() != null) {
                total = total.add(dt.getVA());
            }
            if (dt.getSI() != null) {
                total = total.add(dt.getSI());
            }
            if (dt.getSU() != null) {
                total = total.add(dt.getSU());
            }
            if (dt.getDI() != null) {
                total = total.add(dt.getDI());
            }
            if (dt.getN1_THI() != null) {
                total = total.add(dt.getN1_THI());
            }
            mapDot.put(dot, mapDot.getOrDefault(dot, BigDecimal.ZERO).add(total));
        }
        HashMap<String, String> result = new HashMap<>();
        for (String cccd : sum.keySet()) {
            String bestDot = null;
            BigDecimal max = BigDecimal.valueOf(-1);
            for (var entry : sum.get(cccd).entrySet()) {
                if (entry.getValue().compareTo(max) > 0) {
                    max = entry.getValue();
                    bestDot = entry.getKey();
                }
            }
            result.put(cccd, bestDot);
        }
        return result;
    }

    private void setVSATScore(DiemThiDTO dto, String maMon, BigDecimal diem) {
        if(maMon == null) return;
        switch (maMon.trim().toUpperCase()) {
            case "TO_VS":
            case "M1":
                dto.setTO(diem);
                break;
            case "LI_VS":
            case "M2":
                dto.setLI(diem);
                break;
            case "HO_VS":
            case "M3":
                dto.setHO(diem);
                break;
            case "VA_VS":
                dto.setVA(diem);
                break;
            case "SI_VS":
            case "M4":
                dto.setSI(diem);
                break;
            case "SU_VS":
            case "M6":
                dto.setSU(diem);
                break;
            case "DI_VS":
            case "M7":
                dto.setDI(diem);
                break;
            case "N1_VS":
            case "M8":
                dto.setN1_THI(diem);
                break;
            default:
                break;
        }
    }
    
    // đọc file VSAT
    public List<DiemThiDTO> readFileVSAT(String filePath) {
        HashMap<String, DiemThiDTO> map = new HashMap<>();
        try {
            FileInputStream fis = new FileInputStream(new File(filePath));
            Workbook work = WorkbookFactory.create(fis);
            Sheet sheet = work.getSheetAt(0);
            Row headerRow = sheet.getRow(0);
            int cccdCol = getColumnIndex(headerRow, "CMND");
            int maMonCol = getColumnIndex(headerRow, "MAMONTHI");
            int dotthiCol = getColumnIndex(headerRow, "MADOTTHI");
            int diemCol = getColumnIndex(headerRow, "DIEM");
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) {
                    continue;
                }
                String cccd = getCell(row, cccdCol);
                String maMon = getCell(row, maMonCol);
                String diemStr = getCell(row, diemCol);
                String dotthi = getCell(row, dotthiCol);
                BigDecimal diem = parseBigDecimal(diemStr);
                if (cccd == null || cccd.trim().isEmpty() || maMon == null || maMon.trim().isEmpty() || diemStr == null || diemStr.trim().isEmpty()|| diem == null) {
                    continue;
                }
                String key = cccd + "_" + dotthi;
                DiemThiDTO dto = map.get(key);
                if (dto == null) {
                    dto = new DiemThiDTO();
                    dto.setCccd(cccd.trim());
                    dto.setD_phuongthuc("VSAT");
                    dto.setDotthi(dotthi);
                    map.put(key, dto);
                }
                setVSATScore(dto, maMon, diem);
            }
            work.close();
            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>(map.values());
    }
    
    public int importVSAT(String filePath) {
        List<DiemThiDTO> importList = readFileVSAT(filePath);
        if (importList == null || importList.isEmpty()) {
            return 0;
        }
//        if (keyCache == null) {
            keyCache = new HashSet<>();
            for (DiemThiDTO item : diemThiDao.getAllDiem()) {
                if (!"VSAT".equals(item.getD_phuongthuc())) {
                    continue;
                }
                keyCache.add(buildKey(item));
            }
//        }
        List<DiemThiDTO> newList = new ArrayList<>();
        for (DiemThiDTO dt : importList) {
            int soMon = 0;
            if (dt.getTO() != null) {
                soMon++;
            }
            if (dt.getLI() != null) {
                soMon++;
            }
            if (dt.getHO() != null) {
                soMon++;
            }
            if (dt.getSI() != null) {
                soMon++;
            }
            if (dt.getSU() != null) {
                soMon++;
            }
            if (dt.getDI() != null) {
                soMon++;
            }
            if (dt.getVA() != null) {
                soMon++;
            }
            if (dt.getN1_THI() != null) {
                soMon++;
            }
            if (soMon < 3) {
                continue;
            }
            String key = buildKeyVSAT(dt);
            if (keyCache.contains(key)) {
                continue;
            }
            newList.add(dt);
            keyCache.add(key);
        }
        if (newList.isEmpty()) {
            return -1;
        }
        diemThiDao.saveAll(newList);
        return newList.size();
    }

    // đọc file ĐGNL
    public List<DiemThiDTO> readFileDGNL(String filePath) {
        HashMap<String, DiemThiDTO> map = new HashMap<>();
        try {
            FileInputStream fis = new FileInputStream(new File(filePath));
            Workbook work = WorkbookFactory.create(fis);
            Sheet sheet = work.getSheetAt(1);
            Row headerRow = sheet.getRow(0);
            int cccdCol = getColumnIndex(headerRow, "CMND");
            int dotthi = getColumnIndex(headerRow, "DOTTHI");
            int diemCol = getColumnIndex(headerRow, "DIEM");
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) {
                    continue;
                }
                String cccd = getCell(row, cccdCol);
                if (cccd == null || cccd.trim().isEmpty()) {
                    continue;
                }
                String diemStr = getCell(row, diemCol);
                if (diemStr == null || diemStr.trim().isEmpty()) {
                    continue;
                }
                BigDecimal diem = parseBigDecimal(diemStr);
                if (diem == null) {
                    continue;
                }
                DiemThiDTO old = map.get(cccd);
                if (old == null || diem.compareTo(old.getNL1()) > 0) {
                    DiemThiDTO dto = new DiemThiDTO();
                    dto.setCccd(cccd.trim());
                    dto.setD_phuongthuc("ĐGNL");
                    dto.setNL1(diem);
                    map.put(cccd, dto);
                }
            }
            work.close();
            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>(map.values());
    }

    // import điểm ĐGNL
    public int importDGNL(String filePath) {
        List<DiemThiDTO> importList = readFileDGNL(filePath);
        if (importList == null || importList.isEmpty()) {
            return 0;
        }
//        if (keyCache == null) {
            keyCache = new HashSet<>();
            for (DiemThiDTO item : diemThiDao.getAllDiem()) {
                keyCache.add(buildKey(item));
            }
//        }
        List<DiemThiDTO> newList = new ArrayList<>();
        for (DiemThiDTO dt : importList) {
            if (dt.getCccd() == null || dt.getCccd().trim().isEmpty()) {
                continue;
            }
            String key = buildKey(dt);
            if (!keyCache.contains(key)) {
                newList.add(dt);
                keyCache.add(key);
            }
        }
        if (newList.isEmpty()) {
            return -1;
        }
        diemThiDao.saveAll(newList);
        return newList.size();
    }

    // đọc file
    public List<DiemThiDTO> readFileTHPT(String filePath) {
        List<DiemThiDTO> list = new ArrayList<>();
        try {
            FileInputStream fis = new FileInputStream(new File(filePath));
            Workbook work = WorkbookFactory.create(fis);
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
    public int importTHPT(String filePath) {
        List<DiemThiDTO> importList = readFileTHPT(filePath);
        if (importList == null || importList.isEmpty()) {
            return 0;
        }
//        if (keyCache == null) {
            keyCache = new HashSet<>();
            for (DiemThiDTO item : diemThiDao.getAllDiem()) {
                keyCache.add(buildKey(item));
            }
//        }
        List<DiemThiDTO> newList = new ArrayList<>();
        for (DiemThiDTO dt : importList) {
            if (dt.getCccd() == null || dt.getCccd().trim().isEmpty()) {
                continue;
            }
            String key = buildKey(dt);

            if (!keyCache.contains(key)) {
                newList.add(dt);
                keyCache.add(key);
            }
        }
        if (newList.isEmpty()) {
            return -1;
        }
        diemThiDao.saveAll(newList);
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
