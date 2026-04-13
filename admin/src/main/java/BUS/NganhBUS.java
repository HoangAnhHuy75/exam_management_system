package BUS;

import DAO.NganhDAO;
import DTO.NganhDTO;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.apache.poi.ss.usermodel.*;

public class NganhBUS {

    private NganhDAO nganhDao = new NganhDAO();
    private ArrayList<NganhDTO> nganhList = new ArrayList<>();

    public int insert(NganhDTO n) {
        if (checkDup(n.getMaNganh())) {
            return 0;
        }
        int success = nganhDao.insert(n);
        return success;
    }

    // Lấy danh sách ngành
    public ArrayList<NganhDTO> getListN() {
        nganhList = nganhDao.getAllNganh();
        return nganhList;
    }
    
    public int update(NganhDTO n) {
        for (NganhDTO ng : nganhDao.getAllNganh()) {
            // bỏ qua chính nó (so sánh id)
            if (ng.getIdNganh() != n.getIdNganh()
                    && ng.getMaNganh().equalsIgnoreCase(n.getMaNganh())) {
                return 0; // trùng mã ngành
            }
        }

        return nganhDao.update(n);
    }

    // Kiểm tra trùng mã ngành
    public boolean checkDup(String maNganh) {
        return nganhDao.getAllNganh().stream()
                .anyMatch(n -> n.getMaNganh().equalsIgnoreCase(maNganh));
    }

    // Map manganh -> tennganh
    public HashMap<String, String> nganhMap() {
        HashMap<String, String> nganhMap = new HashMap<>();
        for (NganhDTO ng : nganhDao.getAllNganh()) {
            nganhMap.put(ng.getTenNganh(), ng.getMaNganh());
        }
        return nganhMap;
    }
    
    // Map tennganh -> manganh(thành)
    public HashMap<String,String> getMapTenNganh() {
        HashMap<String,String> getMapTenNganh = new HashMap<>();
        for(NganhDTO ng : nganhDao.getAllNganh()) {
            getMapTenNganh.put(ng.getMaNganh(), ng.getTenNganh());
        }
        return getMapTenNganh;
    }
    // Tìm mã ngành theo tên ngành
    public String getMaNganhByTenNganh(String tenNganh) {
        return nganhDao.getAllNganh().stream()
                .filter(n -> n.getTenNganh().equals(tenNganh))
                .map(NganhDTO::getMaNganh)
                .findFirst()
                .orElse(null);
    }

    // Tìm tên ngành theo mã ngành
    public String getTenNganhByMaNganh(String maNganh) {
        return nganhDao.getAllNganh().stream()
                .filter(n -> n.getMaNganh().equals(maNganh))
                .map(NganhDTO::getTenNganh)
                .findFirst()
                .orElse(null);
    }

    // Hàm import Excel và lưu vào DB
    public int importFromExcel(String filePath) {

        // 1. Lấy list từ DAO (DAO đọc Excel)
        List<NganhDTO> importList = nganhDao.importFromExcel(filePath);

        if (importList == null || importList.isEmpty()) {
            return 0;
        }

        // 2. Tạo map để check trùng
        HashMap<String, Boolean> existingMap = new HashMap<>();
        for (NganhDTO n : nganhDao.getAllNganh()) {
            if (n.getMaNganh() != null) {
                existingMap.put(n.getMaNganh().toLowerCase(), true);
            }
        }

        // 3. Lọc dữ liệu mới
        List<NganhDTO> newList = new ArrayList<>();
        for (NganhDTO n : importList) {
            if (n.getMaNganh() == null) {
                continue;
            }

            String key = n.getMaNganh().toLowerCase();

            if (!existingMap.containsKey(key)) {
                newList.add(n);
                existingMap.put(key, true); // tránh trùng trong file
            }
        }

        // 4. Insert DB
        if (!newList.isEmpty()) {
            nganhDao.insertList(newList);
            nganhList.addAll(newList);
        }

        return newList.size();
    }

    private BigDecimal getDecimal(Cell cell) {
        if (cell == null) {
            return null;
        }
        try {
            if (cell.getCellType() == CellType.NUMERIC) {
                return BigDecimal.valueOf(cell.getNumericCellValue());
            }
            String val = cell.toString().trim();
            if (val.isEmpty() || val.equalsIgnoreCase("null")) {
                return null;
            }
            return new BigDecimal(val);
        } catch (Exception e) {
            return null;
        }
    }
    public int getIdbyIndex(int index){
        ArrayList<NganhDTO> list = nganhDao.getAllNganh();
        if(index >=0 && index < list.size())
            return list.get(index).getIdNganh();
        return -1;
    }
    public ArrayList<String> getListToHopGoc() {
        ArrayList<String> listToHopGoc = new ArrayList<>();
        for (NganhDTO n : nganhDao.getAllNganh()) {
            String thGoc = n.getNToHopGoc();
            if (thGoc != null && !thGoc.isEmpty() && !listToHopGoc.contains(thGoc)) {
                listToHopGoc.add(thGoc);
            }
        }
        return listToHopGoc;
    }
    // Tìm kiếm nâng cao
    public ArrayList<NganhDTO> timKiem(String ptxt, String thg) {
        ArrayList<NganhDTO> result = new ArrayList<>();
        for (NganhDTO n : nganhDao.getAllNganh()) {
            boolean matchPT = ptxt.equals("Tất cả")
                    || (ptxt.equals("Tuyển thẳng") && "Y".equalsIgnoreCase(n.getNTuyenThang()))
                    || (ptxt.equals("ĐGNL") && "Y".equalsIgnoreCase(n.getNDGNL()))
                    || (ptxt.equals("THPT") && "Y".equalsIgnoreCase(n.getNTHPT()))
                    || (ptxt.equals("VSAT") && "Y".equalsIgnoreCase(n.getNVSAT()));

            boolean matchTHG = thg.equals("Tất cả") || thg.equals(n.getNToHopGoc());
            if (matchPT && matchTHG) {
                result.add(n);
            }
        }
        return result;
    }
    
    public ArrayList<NganhDTO> timkiemText(String text){
        String textFind = text.toLowerCase();
        ArrayList<NganhDTO> result = new ArrayList<>();
        for(NganhDTO ng : nganhDao.getAllNganh()){
            if(ng.getMaNganh().toLowerCase().contains(textFind) || ng.getTenNganh().toLowerCase().contains(textFind)){
                result.add(ng);
            }
        }
        return result;
    }
}
