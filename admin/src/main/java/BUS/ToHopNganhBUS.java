package BUS;

import DAO.ToHopNganhDAO;
import DTO.ToHopNganhDTO;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ToHopNganhBUS {

    private ToHopNganhDAO dao = new ToHopNganhDAO();
    private ArrayList<ToHopNganhDTO> list = new ArrayList<>();

    // Lấy tất cả
    public ArrayList<ToHopNganhDTO> getAll() {
        list = dao.getAll();
        return list;
    }

    // Thêm 1 (check trùng manganh + matohop)
    public int insert(ToHopNganhDTO t) {
        if (t == null || t.getManganh() == null || t.getMatohop() == null) {
            return 0;
        }

        // reload để tránh list cũ
        list = dao.getAll();

        if (checkDup(t.getManganh(), t.getMatohop())) {
            return 0;
        }

        int success = dao.insert(t);
        if (success == 1) {
            list.add(t);
            return 1;
        }
        return 0;
    }

    // Import Excel (nếu sau này bạn có)
    public int importList(List<ToHopNganhDTO> importList) {
        if (importList == null || importList.isEmpty()) {
            return 0;
        }

        // map dữ liệu đã có
        HashMap<String, Boolean> existingMap = new HashMap<>();
        for (ToHopNganhDTO t : dao.getAll()) {
            if (t.getManganh() != null && t.getMatohop() != null) {
                String key = (t.getManganh() + "_" + t.getMatohop()).toLowerCase().trim();
                existingMap.put(key, true);
            }
        }

        ArrayList<ToHopNganhDTO> newList = new ArrayList<>();

        for (ToHopNganhDTO t : importList) {
            if (t.getManganh() == null || t.getMatohop() == null) continue;

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
            dao.insertList(newList);
            list.addAll(newList);
        }

        return newList.size();
    }

    // Tìm theo mã ngành
    public List<ToHopNganhDTO> findByMaNganh(String maNganh) {
        if (maNganh == null) return new ArrayList<>();
        return dao.findByMaNganh(maNganh);
    }

    // Tìm theo mã tổ hợp
    public List<ToHopNganhDTO> findByMaToHop(String maToHop) {
        if (maToHop == null) return new ArrayList<>();
        return dao.findByMaToHop(maToHop);
    }

    // Map manganh_matohop -> DTO
    public HashMap<String, ToHopNganhDTO> map() {
        return dao.toHopNganhMap();
    }

    // Tìm kiếm
    public ArrayList<ToHopNganhDTO> timkiem(String maNganh, String maToHop) {
        ArrayList<ToHopNganhDTO> result = new ArrayList<>();

        String mn = maNganh != null ? maNganh.toLowerCase() : "";
        String th = maToHop != null ? maToHop.toLowerCase() : "";

        for (ToHopNganhDTO t : list) {
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
        if (manganh == null || matohop == null) return false;

        String key = (manganh.trim() + "_" + matohop.trim()).toLowerCase();

        return list.stream().anyMatch(t ->
                t.getManganh() != null && t.getMatohop() != null &&
                (t.getManganh().trim() + "_" + t.getMatohop().trim())
                        .toLowerCase().equals(key)
        );
    }
}