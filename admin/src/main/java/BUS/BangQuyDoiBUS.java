package BUS;

import DAO.BangQuyDoiDAO;
import DTO.BangQuyDoiDTO;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BangQuyDoiBUS {

    private BangQuyDoiDAO bqdDAO = new BangQuyDoiDAO();

    // ================= GET ALL =================
    public ArrayList<BangQuyDoiDTO> getList() {
        return bqdDAO.getAll();
    }

    // ================= MAP BANG QUY DOI DGNL PHUONG THUC =================
    public HashMap<String, List<BangQuyDoiDTO>> bqdDGNLMap() {
        HashMap<String, List<BangQuyDoiDTO>> map = new HashMap<>();
        for (BangQuyDoiDTO qd : bqdDAO.getAll()) {
            String key = qd.getdPhuongThuc() + "_" + qd.getdToHop();
            map.putIfAbsent(key, new ArrayList<>());
            map.get(key).add(qd);
        }
        return map;
    }
    
    // ================= MAP BANG QUY DOI VSAT PHUONG THUC =================
    public HashMap<String, List<BangQuyDoiDTO>> bqdVSATMap() {
        HashMap<String, List<BangQuyDoiDTO>> map = new HashMap<>();
        for (BangQuyDoiDTO qd : bqdDAO.getAll()) {
            String key = qd.getdPhuongThuc() + "_" + qd.getdMon();
            map.putIfAbsent(key, new ArrayList<>());
            map.get(key).add(qd);
        }
        return map;
    }

}