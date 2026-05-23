package BUS;

import DAO.ThiSinhDAO;
import DTO.ThiSinhDTO;
import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import static org.apache.poi.ss.usermodel.CellType.NUMERIC;
import static org.apache.poi.ss.usermodel.CellType.STRING;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ThiSinhBUS {

    private ThiSinhDAO thiSinhDAO = new ThiSinhDAO();
    private ArrayList<ThiSinhDTO> thiSinhList = new ArrayList<>();
    private HashSet<String> cccdCache = null ;
    

    // ===== Lấy tất cả =====
    public ArrayList<ThiSinhDTO> getAll() {
        thiSinhList = thiSinhDAO.getAll();
        return thiSinhList;
    }

    // ===== Thêm 1 (check trùng CCCD) =====
    public int insert(ThiSinhDTO t) {
        if (checkDupCCCD(t.getCccd())) {
            return 0; // trùng
        }
        return thiSinhDAO.insert(t);
    }

    // ===== Update =====
    public int update(ThiSinhDTO t) {
        return thiSinhDAO.update(t);
    }
    
     public int getIDbyIndex(int index) {
        ArrayList<ThiSinhDTO> list = thiSinhDAO.getAll(); // lấy danh sách điện thoại
        if (index >= 0 && index < list.size()) {
            return list.get(index).getIdthisinh();
        }
        return -1; 
    }

    public int delete(ThiSinhDTO t) {
        return thiSinhDAO.delete(t);
    }

    public ThiSinhDTO findByCCCD(String cccd) {
        if (cccd == null || cccd.trim().isEmpty()) {
            return null;
        }
        return thiSinhDAO.findByCCCD(cccd.trim());
    }
    
    public HashMap<String, ThiSinhDTO> thisinhMap(){
        HashMap<String, ThiSinhDTO> thisinhMap = new HashMap<>();
        for(ThiSinhDTO ts : thiSinhDAO.getAll())
            thisinhMap.put(ts.getCccd(),ts);
        return thisinhMap;
    }

    public String getPasswordByCCCD(String cccd) {
        if (cccd == null || cccd.trim().isEmpty()) {
            return null;
        }
        ThiSinhDTO t = thiSinhDAO.findByCCCD(cccd.trim());
        if (t != null) {
            return t.getPassword();
        }
        return null;
    }
    
    private int getColumnIndex(Row headerRow, String columnName) {
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

    private Date getDateCell(Row row, int index) {
        if (index == -1) {
            return null;
        }
        Cell cell = row.getCell(index);
        if (cell == null) {
            return null;
        }
        try {
            // Excel chuẩn (date dạng numeric)
            if (cell.getCellType() == CellType.NUMERIC) {
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue();
                }
            }
            // Nếu là string (user nhập tay)
            if (cell.getCellType() == CellType.STRING) {
                String value = cell.getStringCellValue().trim();

                if (value.isEmpty()) {
                    return null;
                }
                String[] formats = {
                    "dd/MM/yyyy",
                    "yyyy-MM-dd",
                    "MM/dd/yyyy"
                };
                for (String f : formats) {
                    try {
                        return new SimpleDateFormat(f).parse(value);
                    } catch (Exception ignored) {
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<ThiSinhDTO> readFile(String filePath) {
        List<ThiSinhDTO> list = new ArrayList<>();
        try (FileInputStream fis = new FileInputStream(new File(filePath)); Workbook workbook = new XSSFWorkbook(fis)) {
            Sheet sheet = workbook.getSheetAt(0);
            Row header = sheet.getRow(0);
            // map cột
            int cccdCol = getColumnIndex(header, "CCCD");
            int sbdCol = getColumnIndex(header, "sobaodanh");
            int tenCol = getColumnIndex(header, "Họ Tên");
            int nsCol = getColumnIndex(header, "Ngày sinh");
            int gtCol = getColumnIndex(header, "Giới tính");
            int sdtCol = getColumnIndex(header, "dien_thoai");
            int emailCol = getColumnIndex(header, "email");
            int noiSinhCol = getColumnIndex(header, "Nơi sinh");
            int doiTuongCol = getColumnIndex(header, "ĐTƯT");
            int khuVucCol = getColumnIndex(header, "KVƯT");

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) {
                    continue;
                }
                ThiSinhDTO t = new ThiSinhDTO();
                t.setCccd(getCell(row, cccdCol));
                t.setSobaodanh(getCell(row, sbdCol));
                t.setTen(getCell(row, tenCol));
                t.setNgaySinh(getDateCell(row, nsCol));
                t.setGioiTinh(getCell(row, gtCol));
                t.setDienThoai(getCell(row, sdtCol));
                t.setEmail(getCell(row, emailCol));
                t.setNoiSinh(getCell(row, noiSinhCol));
                t.setDoiTuong(getCell(row, doiTuongCol));
                t.setKhuVuc(getCell(row, khuVucCol));
                list.add(t);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public int importFromExcel(String filePath) {
        List<ThiSinhDTO> importList = readFile(filePath);
        if (importList == null || importList.isEmpty()) {
            return 0;
        }
        if (cccdCache == null) {
            cccdCache = new HashSet<>(thiSinhDAO.getAllCCCD());
        }
        List<ThiSinhDTO> newList = new ArrayList<>();
        for (ThiSinhDTO t : importList) {
            if (t.getCccd() == null || t.getCccd().trim().isEmpty()) {
                continue;
            }
            String cccd = t.getCccd().trim();
            if (!cccdCache.contains(cccd)) {
                newList.add(t);
                cccdCache.add(cccd);
            }
        }
        if (!newList.isEmpty()) {
            thiSinhDAO.insertList(newList);
        }
        return newList.size();
    }

    public boolean checkDupCCCD(String cccd) {
        if (cccd == null) {
            return false;
        }
        return thiSinhDAO.findByCCCD(cccd.trim()) != null;
    }
    
    public ArrayList<ThiSinhDTO> filterNoNganh(String text,String dt, String kv, String ns) {
        String t = text.toLowerCase().trim();
        ArrayList<ThiSinhDTO> result = new ArrayList<>();
        for (ThiSinhDTO ts : thiSinhDAO.getAll()) {
            boolean matchDT = dt.equals("Tất cả") || ts.getDoiTuong().equals(dt);
            boolean matchKV = kv.equals("Tất cả") || ts.getKhuVuc().equals(kv);
            boolean matchNS = ns.equals("Tất cả") || ts.getNoiSinh().toLowerCase().contains(ns.toLowerCase());
            boolean matchText = t.equals("") || ts.getCccd().toLowerCase().contains(t) || ts.getTen().toLowerCase().contains(t);
            if (matchDT && matchKV && matchNS && matchText) {
                result.add(ts);
            }
        }
        return result;
    }
    
    public ArrayList<ThiSinhDTO> filterHasNganh(String text, String dt, String kv, String maNganh, String ns) {
        String t = text.toLowerCase().trim();
        ArrayList<ThiSinhDTO> result = new ArrayList<>();
        List<ThiSinhDTO> listTSByNganh = thiSinhDAO.getThiSinhByMaNganh(maNganh);
        for (ThiSinhDTO ts : listTSByNganh) {
            boolean matchDT = dt.equals("Tất cả") || ts.getDoiTuong().equals(dt);
            boolean matchKV = kv.equals("Tất cả") || ts.getKhuVuc().equals(kv);
            boolean matchNS = ns.equals("Tất cả") || ts.getNoiSinh().toLowerCase().contains(ns.toLowerCase());
            boolean matchText = t.equals("") || ts.getCccd().toLowerCase().contains(t) || ts.getTen().toLowerCase().contains(t);
            if (matchDT && matchKV && matchNS && matchText) {
                result.add(ts);
            }
        }
        return result;
    }

    public ArrayList<ThiSinhDTO> searchText(String text) {
        ArrayList<ThiSinhDTO> result = new ArrayList<>();
        for (ThiSinhDTO ts : thiSinhDAO.getAll()) {
            if (ts.getCccd().toLowerCase().contains(text) || ts.getTen().toLowerCase().contains(text)) {
                result.add(ts);
            }
        }
        return result;
    }
}