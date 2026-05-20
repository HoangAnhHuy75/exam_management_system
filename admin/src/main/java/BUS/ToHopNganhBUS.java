package BUS;

import DAO.ToHopNganhDAO;
import DTO.ToHopNganhDTO;
import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.apache.poi.ss.usermodel.Cell;
import static org.apache.poi.ss.usermodel.CellType.BOOLEAN;
import static org.apache.poi.ss.usermodel.CellType.NUMERIC;
import static org.apache.poi.ss.usermodel.CellType.STRING;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ToHopNganhBUS {
    private NganhBUS nganhBus = new NganhBUS();
    private ToHopNganhDAO tohopnganhDao = new ToHopNganhDAO();
    private ArrayList<ToHopNganhDTO> tohopnganhList = new ArrayList<>();

    // Lấy tất cả
    public ArrayList<ToHopNganhDTO> getAll() {
        tohopnganhList = tohopnganhDao.getAll();
        return tohopnganhList;
    }

    // Thêm 1 (check trùng manganh + matohop)
    public int insert(ToHopNganhDTO t) {
        if (checkDup(t.getManganh(), t.getMatohop())) {
            return 0;
        }
        int success = tohopnganhDao.insert(t);
        return success;
    }
    
    public int update(ToHopNganhDTO tohopnganh) {
        for (ToHopNganhDTO thn : tohopnganhDao.getAll()) {
            // bỏ qua chính nó
            if (thn.getId() != tohopnganh.getId() && thn.getTb_keys().equalsIgnoreCase(tohopnganh.getTb_keys())) {
                return 0;
            }
        }
        return tohopnganhDao.update(tohopnganh);
    }
    
    public int delete(ToHopNganhDTO t){
        int success = tohopnganhDao.delete(t);
        return success;
    }
    
    // tìm 1 ngành
    public ToHopNganhDTO findOneByTHNganh(String tb_key) {
        for (ToHopNganhDTO t : tohopnganhDao.getAll()) {
            if (t.getTb_keys().equals(tb_key)) {
                return t;
            }
        }
        return null;
    }

    public int getIdbyIndex(int index) {
        ArrayList<ToHopNganhDTO> list = tohopnganhDao.getAll();
        if (index >= 0 && index < list.size()) {
            return list.get(index).getId();
        }
        return -1;
    }

    // Tìm theo mã ngành
    public List<ToHopNganhDTO> findByMaNganh(String maNganh) {
        if (maNganh == null) {
            return new ArrayList<>();
        }
        return tohopnganhDao.findByMaNganh(maNganh);
    }

    // Tìm theo mã tổ hợp
    public List<ToHopNganhDTO> findByMaToHop(String maToHop) {
        if (maToHop == null) {
            return new ArrayList<>();
        }
        return tohopnganhDao.findByMaToHop(maToHop);
    }

    // Tìm kiếm
    public ArrayList<ToHopNganhDTO> timkiem(String maNganh, String maToHop) {
        ArrayList<ToHopNganhDTO> result = new ArrayList<>();
        String mn = maNganh != null ? maNganh.toLowerCase() : "";
        String th = maToHop != null ? maToHop.toLowerCase() : "";
        for (ToHopNganhDTO t : tohopnganhDao.getAll()) {
            boolean matchMN = mn.isEmpty() || t.getManganh().toLowerCase().contains(mn);
            boolean matchTH = th.isEmpty() || t.getMatohop().toLowerCase().contains(th);
            if (matchMN && matchTH) {
                result.add(t);
            }
        }
        return result;
    }
    
    public ArrayList<ToHopNganhDTO> timkiemText(String text) {
        String textFind = text.toLowerCase().trim();
        ArrayList<ToHopNganhDTO> result = new ArrayList<>();
        HashMap<String,String> tenNganhByMaNganhMap = nganhBus.getTenNganhByMaNganhMap();
        for (ToHopNganhDTO t : tohopnganhDao.getAll()) {
            String tenNganh = tenNganhByMaNganhMap.get(t.getManganh());
            if (t.getManganh().toLowerCase().contains(textFind) || t.getMatohop().toLowerCase().contains(textFind) || tenNganh.toLowerCase().contains(textFind)) {
                result.add(t);
            }
        }
        return result;
    }

    // Kiểm tra trùng (combo manganh + matohop)
    public boolean checkDup(String manganh, String matohop) {
        if (manganh == null || matohop == null) {
            return false;
        }
        String key = (manganh.trim() + "_" + matohop.trim()).toLowerCase();
        return tohopnganhDao.getAll().stream().anyMatch(t
                -> t.getManganh() != null && t.getMatohop() != null
                && (t.getManganh().trim() + "_" + t.getMatohop().trim())
                        .toLowerCase().equals(key)
        );
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
                double num = cell.getNumericCellValue();

                // tránh 1.0 -> 1
                if (num == (long) num) {
                    return String.valueOf((long) num);
                } else {
                    return String.valueOf(num);
                }
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            default:
                return cell.toString().trim();
        }
    }
    
    public int getColumnIndex(Row headerRow, String columnName) {
        for (Cell cell : headerRow) {
            if (cell.getStringCellValue().trim().equalsIgnoreCase(columnName)) {
                return cell.getColumnIndex();
            }
        }
        return -1;
    }
    

    public Object[] parseMonAndHeSo(String matohop) {
        if (matohop == null || !matohop.contains("(")) {
            return new Object[]{
                new String[]{"", "", ""},
                new Integer[]{0, 0, 0}
            };
        }
        try {
            String inside = matohop.substring(
                    matohop.indexOf("(") + 1,
                    matohop.indexOf(")")
            );
            String[] parts = inside.split(",");
            String[] mons = new String[3];
            Integer[] hs = new Integer[3];
            for (int i = 0; i < 3; i++) {
                if (i < parts.length) {
                    String[] p = parts[i].split("-");
                    mons[i] = p.length > 0 ? p[0] : "";
                    try {
                        hs[i] = p.length > 1 ? Integer.parseInt(p[1]) : 0;
                    } catch (Exception e) {
                        hs[i] = 0;
                    }
                } else {
                    mons[i] = "";
                    hs[i] = 0;
                }
            }
            return new Object[]{mons, hs};
        } catch (Exception e) {
            return new Object[]{
                new String[]{"", "", ""},
                new Integer[]{0, 0, 0}
            };
        }
    }
    
    public List<ToHopNganhDTO> readFile(String filePath) {
        List<ToHopNganhDTO> list = new ArrayList<>();
        try {
            FileInputStream fis = new FileInputStream(new File(filePath));
            Workbook workbook = new XSSFWorkbook(fis);
            Sheet sheet = workbook.getSheetAt(0);
            Row headerRow = sheet.getRow(0);
            int maNganhCol = getColumnIndex(headerRow, "MANGANH");
            int maToHopCol = getColumnIndex(headerRow, "MA_TO_HOP");
            int dolechCol = getColumnIndex(headerRow, "Độ lệch");
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) {
                    continue;
                }
                String maNganh = getCell(row, maNganhCol);
                String maToHop = getCell(row, maToHopCol);
                if (maNganh == null || maToHop == null) {
                    continue;
                }
                ToHopNganhDTO dto = new ToHopNganhDTO();
                dto.setManganh(maNganh);
                dto.setMatohop(maToHop.substring(0,3));
                // tạo tb_keys từ 2 field
                String tbKeys = maNganh + "_" + maToHop.substring(0,3);
                dto.setTb_keys(tbKeys);

                // ===== PARSE MÔN + HỆ SỐ =====
                Object[] result = parseMonAndHeSo(maToHop);
                String[] mons = (String[]) result[0];
                Integer[] hs = (Integer[]) result[1];
                dto.setTh_mon1(mons[0]);
                dto.setHsmon1(hs[0]);
                dto.setTh_mon2(mons[1]);
                dto.setHsmon2(hs[1]);
                dto.setTh_mon3(mons[2]);
                dto.setHsmon3(hs[2]);
                // ===== ĐỘ LỆCH =====
                String dl = getCell(row, dolechCol);
                if (dl != null && !dl.isEmpty()) {
                    try {
                        dto.setDolech(new BigDecimal(dl));
                    } catch (Exception e) {
                        dto.setDolech(BigDecimal.ZERO);
                    }
                }
                list.add(dto);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public int importFromExcel(String filePath) {
        // B1: đọc dữ liệu từ Excel (DAO)
        List<ToHopNganhDTO> importList = readFile(filePath);

        // B2: kiểm tra list
        if (importList == null || importList.isEmpty()) {
            return 0;
        }

        // B3: tạo map để check trùng (manganh_matohop)
        HashMap<String, Boolean> existingMap = new HashMap<>();

        for (ToHopNganhDTO t : tohopnganhDao.getAll()) {
            if (t.getManganh() != null && t.getMatohop() != null) {
                String key = (t.getManganh().trim() + "_" + t.getMatohop().trim()).toLowerCase();
                existingMap.put(key, true);
            }
        }

        // B4: lọc dữ liệu mới
        ArrayList<ToHopNganhDTO> newList = new ArrayList<>();

        for (ToHopNganhDTO t : importList) {
            if (t.getManganh() == null || t.getMatohop() == null) {
                continue;
            }

            String key = (t.getManganh().trim() + "_" + t.getMatohop().trim()).toLowerCase();

            if (!existingMap.containsKey(key)) {
                // chuẩn hóa dữ liệu
                t.setManganh(t.getManganh().trim());
                t.setMatohop(t.getMatohop().trim());

                newList.add(t);
                existingMap.put(key, true);
            }
        }

        // B5: insert DB
        if (!newList.isEmpty()) {
            tohopnganhDao.insertList(newList);
            tohopnganhList.addAll(newList);
        }

        // B6: trả về số dòng import thành công
        return newList.size();
    }
}
