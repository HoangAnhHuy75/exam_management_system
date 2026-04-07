package BUS;

import DAO.ToHopDAO;
import DTO.ToHopDTO;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

    // Import Excel
    public int importFromExcel(String filePath) {
        List<ToHopDTO> list = tohopDao.importFromExcel(filePath);

        if (list == null || list.isEmpty()) {
            return 0;
        }

        // map chứa dữ liệu đã có trong DB
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

        return newList.size(); // số record thực sự insert
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

    // Map matohop -> tentohop
    public HashMap<String, String> tohopMap() {
        HashMap<String, String> map = new HashMap<>();
        for (ToHopDTO th : tohopDao.getAllToHop()) {
            map.put(th.getMatohop(), th.getTentohop());
        }
        return map;
    }

    // Tìm kiếm theo mã và tên
    public ArrayList<ToHopDTO> timkiem(String maTH, String tenTH) {
        ArrayList<ToHopDTO> result = new ArrayList<>();
        String ma = maTH != null ? maTH.toLowerCase() : "";
        String ten = tenTH != null ? tenTH.toLowerCase() : "";
        for (ToHopDTO th : tohopDao.getAllToHop()) {
            boolean matchMa = ma.isEmpty() || th.getMatohop().toLowerCase().contains(ma);
            boolean matchTen = ten.isEmpty() || th.getTentohop().toLowerCase().contains(ten);
            if (matchMa && matchTen) {
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
