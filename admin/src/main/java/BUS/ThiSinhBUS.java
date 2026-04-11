package BUS;

import DAO.ThiSinhDAO;
import DTO.ThiSinhDTO;
import java.lang.reflect.Array;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class ThiSinhBUS {

    private ThiSinhDAO thiSinhDAO = new ThiSinhDAO();
    private ArrayList<ThiSinhDTO> thiSinhList = new ArrayList<>();

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

    // ===== Delete =====
    public int delete(ThiSinhDTO t) {
        return thiSinhDAO.delete(t);
    }

    // ===== Tìm theo CCCD =====
    public ThiSinhDTO findByCCCD(String cccd) {
        if (cccd == null || cccd.trim().isEmpty()) return null;
        return thiSinhDAO.findByCCCD(cccd.trim());
    }

    // ===== Import Excel =====
    public int importFromExcel(String filePath) {
        List<ThiSinhDTO> importList = thiSinhDAO.importFromExcel(filePath);

        if (importList == null || importList.isEmpty()) {
            return 0;
        }

        // map để check trùng CCCD
        HashSet<String> existingSet = new HashSet<>(thiSinhDAO.getAllCCCD());

        ArrayList<ThiSinhDTO> newList = new ArrayList<>();

        for (ThiSinhDTO t : importList) {
            if (t.getCccd() == null) {
                continue;
            }

            String cccd = t.getCccd().trim();

            if (!existingSet.contains(cccd)) {
                t.setCccd(cccd);

                newList.add(t);
                existingSet.add(cccd);
            }
        }

        if (!newList.isEmpty()) {
            thiSinhDAO.insertList(newList);
            thiSinhList.addAll(newList);
        }

        return newList.size();
    }

    // ===== Tìm kiếm =====
    public ArrayList<ThiSinhDTO> search(String keyword) {
        ArrayList<ThiSinhDTO> result = new ArrayList<>();

        if (keyword == null) keyword = "";
        String key = keyword.toLowerCase();

        for (ThiSinhDTO t : thiSinhDAO.getAll()) {
            boolean matchCCCD = t.getCccd() != null && t.getCccd().toLowerCase().contains(key);
            boolean matchName = t.getHoTen() != null && t.getHoTen().toLowerCase().contains(key);

            if (matchCCCD || matchName) {
                result.add(t);
            }
        }

        return result;
    }

    // ===== Check trùng CCCD =====
    public boolean checkDupCCCD(String cccd) {
        if (cccd == null) {
            return false;
        }
        return thiSinhDAO.findByCCCD(cccd.trim()) != null;
    }
    
    public ArrayList<ThiSinhDTO> timKiem(String gt, String kv, String ns) {
        ArrayList<ThiSinhDTO> result = new ArrayList<>();
        for (ThiSinhDTO ts : thiSinhDAO.getAll()) {
            boolean matchGT = gt.equals("Tất cả") || ts.getGioiTinh().equals(gt);
            boolean matchKV = kv.equals("Tất cả") || ts.getKhuVuc().equals(kv);
            boolean matchNS = ns.equals("Tất cả") || ts.getNoiSinh().toLowerCase().contains(ns.toLowerCase());
            if (matchGT && matchKV && matchNS) {
                result.add(ts);
            }
        }
        return result;
    }

    public ArrayList<ThiSinhDTO> timKiem2(String text) {
        ArrayList<ThiSinhDTO> result = new ArrayList<>();
        for (ThiSinhDTO ts : thiSinhDAO.getAll()) {
            if (ts.getCccd().toLowerCase().contains(text) || ts.getHoTen().toLowerCase().contains(text)) {
                result.add(ts);
            }
        }
        return result;
    }
}