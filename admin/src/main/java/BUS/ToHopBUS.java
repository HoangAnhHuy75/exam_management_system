package BUS;

import DAO.ToHopDAO;
import DTO.ToHopDTO;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ToHopBUS {

    private ToHopDAO tohopDao = new ToHopDAO();
    private ArrayList<ToHopDTO> tohopList = new ArrayList<>();


    // Lấy danh sách tất cả tổ hợp
    public ArrayList<ToHopDTO> getListTH() {
        tohopList = tohopDao.getAllToHop();
        return tohopList;
    }

    // Thêm 1 tổ hợp (nếu chưa tồn tại)
    public int insert(ToHopDTO th) {
        if (checkDup(th.getMatohop())) {
            return 0; // Tổ hợp đã tồn tại
        }
        int success = tohopDao.insert(th); // insert vào DB
        return success;
    }
    
    public int update(ToHopDTO t) {
        for (ToHopDTO th : tohopDao.getAllToHop()) {
            // bỏ qua chính nó
            if (th.getIdtohop() != t.getIdtohop() && th.getMatohop().equalsIgnoreCase(t.getMatohop())) {
                return 0; 
            }
        }

        return tohopDao.update(t);
    }
    
    public int delete(ToHopDTO t){
        int success = tohopDao.delete(t);
        return success;
    }
    
    // tìm 1 mã tổ hợp
    public ToHopDTO findOneByTH(String maTH) {

    for(ToHopDTO t : tohopDao.getAllToHop()) {
        if(t.getMatohop().equals(maTH)) {
            return t;
        }
    }
        return null;
    }
    
    public int getIdbyIndex(int index){
        ArrayList<ToHopDTO> list = tohopDao.getAllToHop();
        if(index >=0 && index < list.size())
            return list.get(index).getIdtohop();
        return -1;
    }
    // Lấy danh sách tên tổ hợp không trùng (nếu có tổ hợp trùng tên)
    public ArrayList<String> getListTenToHopDistinct() {
        ArrayList<String> listTen = new ArrayList<>();
        for (ToHopDTO th : tohopDao.getAllToHop()) {
            String ten = th.getTentohop();
            if (ten != null && !ten.isEmpty() && !listTen.contains(ten)) {
                listTen.add(ten);
            }
        }
        return listTen;
    }
    
    // Import Excel -> list ToHopDTO
    public List<ToHopDTO> readFile(String filePath) {
        List<ToHopDTO> list = new ArrayList<>();
        try (FileInputStream fis = new FileInputStream(new File(filePath)); Workbook workbook = new XSSFWorkbook(fis)) {
            Sheet sheet = workbook.getSheetAt(0);
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) {
                    continue;
                }
                ToHopDTO t = new ToHopDTO();
                t.setMatohop(row.getCell(0) != null ? row.getCell(0).toString() : "");
                t.setMon1(row.getCell(1) != null ? row.getCell(1).toString() : "");
                t.setMon2(row.getCell(2) != null ? row.getCell(2).toString() : "");
                t.setMon3(row.getCell(3) != null ? row.getCell(3).toString() : "");
                t.setTentohop(row.getCell(4) != null ? row.getCell(4).toString() : "");
                list.add(t);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // Import Excel
    public int importFromExcel(String filePath) {
        List<ToHopDTO> list = readFile(filePath);
        if (list == null || list.isEmpty()) {
            return 0;
        }
        HashMap<String, Boolean> existingMap = new HashMap<>();
        for (ToHopDTO th : tohopDao.getAllToHop()) {
            existingMap.put(th.getMatohop().toLowerCase(), true);
        }
        ArrayList<ToHopDTO> newList = new ArrayList<>();
        for (ToHopDTO th : list) {
            String key = th.getMatohop().toLowerCase();
            if (!existingMap.containsKey(key)) {
                newList.add(th);
                existingMap.put(key, true); // tránh trùng trong chính file Excel
            }
        }
        if (!newList.isEmpty()) {
            tohopDao.insertList(newList);
            tohopList.addAll(newList);
        }
        return newList.size();
    }

    // Lấy mã tổ hợp từ tên
    public String getMaTHByTenTH(String tenTH) {
        if (tenTH == null) {
            return null;
        }
        for (ToHopDTO th : tohopDao.getAllToHop()) {
            if (tenTH.equalsIgnoreCase(th.getTentohop())) {
                return th.getMatohop();
            }
        }
        return null;
    }

    public String getTenTHByMaTH(String maTH) {
        if (maTH == null) {
            return null;
        }
        for (ToHopDTO th : tohopDao.getAllToHop()) {
            if (maTH.equalsIgnoreCase(th.getMatohop())) {
                return th.getTentohop();
            }
        }
        return null;
    }

    // Map matohop -> tentohop
    public HashMap<String, String> tohopMap() {
        HashMap<String, String> map = new HashMap<>();
        for (ToHopDTO th : tohopDao.getAllToHop()) {
            map.put(th.getTentohop(), th.getMatohop());
        }
        return map;
    }

    // Tìm kiếm theo mã và tên
    public ArrayList<ToHopDTO> timkiem(String text) {
        ArrayList<ToHopDTO> result = new ArrayList<>();
        String textFind = text.toLowerCase();
        for (ToHopDTO th : tohopDao.getAllToHop()) {
            if (th.getMatohop().toLowerCase().contains(textFind) || th.getTentohop().toLowerCase().contains(textFind)) {
                result.add(th);
            }
        }
        return result;
    }

    // Kiểm tra trùng
    public boolean checkDup(String matohop) {
        if (matohop == null) {
            return false;
        }
        return tohopDao.getAllToHop().stream()
                .anyMatch(th -> th.getMatohop().equalsIgnoreCase(matohop));
    }
}
