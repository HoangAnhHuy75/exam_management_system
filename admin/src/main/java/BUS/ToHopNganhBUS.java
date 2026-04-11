package BUS;

import DAO.ToHopNganhDAO;
import DTO.ToHopNganhDTO;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ToHopNganhBUS {

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

    // Import Excel (nếu sau này bạn có)
    public int importList(List<ToHopNganhDTO> importList) {
        if (importList == null || importList.isEmpty()) {
            return 0;
        }

        // map dữ liệu đã có
        HashMap<String, Boolean> existingMap = new HashMap<>();
        for (ToHopNganhDTO t : tohopnganhDao.getAll()) {
            if (t.getManganh() != null && t.getMatohop() != null) {
                String key = (t.getManganh() + "_" + t.getMatohop()).toLowerCase().trim();
                existingMap.put(key, true);
            }
        }

        ArrayList<ToHopNganhDTO> newList = new ArrayList<>();

        for (ToHopNganhDTO t : importList) {
            if (t.getManganh() == null || t.getMatohop() == null) {
                continue;
            }

            String key = (t.getManganh().trim() + "_" + t.getMatohop().trim()).toLowerCase();

            if (!existingMap.containsKey(key)) {
                // chuẩn hóa
                t.setManganh(t.getManganh().trim());
                t.setMatohop(t.getMatohop().trim());

                newList.add(t);
                existingMap.put(key, true);
            }
        }

        if (!newList.isEmpty()) {
            tohopnganhDao.insertList(newList);
            tohopnganhList.addAll(newList);
        }

        return newList.size();
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

    // Map manganh_matohop -> DTO
    public HashMap<String, ToHopNganhDTO> map() {
        return tohopnganhDao.toHopNganhMap();
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

    public int importFromExcel(String filePath) {
        // B1: đọc dữ liệu từ Excel (DAO)
        List<ToHopNganhDTO> importList = tohopnganhDao.importFromExcel(filePath);

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
