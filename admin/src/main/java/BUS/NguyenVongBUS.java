package BUS;

import DAO.NguyenVongDAO;
import DTO.NguyenVongDTO;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class NguyenVongBUS {

    private NguyenVongDAO nvDAO = new NguyenVongDAO();
    private ArrayList<NguyenVongDTO> nvList = new ArrayList<>();

    // ================= INSERT =================
    public int insert(NguyenVongDTO nv) {

        // check trùng NV (1 thí sinh không được trùng thứ tự NV)
        if (checkDup(nv.getNvCccd(), nv.getNvTt())) {
            return 0;
        }

        return nvDAO.insert(nv);
    }

    // ================= UPDATE =================
    public int update(NguyenVongDTO nv) {

        for (NguyenVongDTO item : nvDAO.getAll()) {
            if (item.getIdnv() != nv.getIdnv()
                    && item.getNvCccd().equals(nv.getNvCccd())
                    && item.getNvTt() == nv.getNvTt()) {
                return 0; // trùng NV
            }
        }

        return nvDAO.update(nv);
    }

    // ================= DELETE =================
    public int delete(int id) {
        return nvDAO.delete(id);
    }

    // ================= GET LIST =================
    public ArrayList<NguyenVongDTO> getList() {
        nvList = nvDAO.getAll();
        return nvList;
    }

    // ================= GET BY CCCD =================
    public ArrayList<NguyenVongDTO> getByCccd(String cccd) {
        return new ArrayList<>(nvDAO.getByCccdOrderByNV(cccd));
    }

    // ================= CHECK TRÙNG NV =================
    public boolean checkDup(String cccd, int nvTt) {
        return nvDAO.getAll().stream()
                .anyMatch(nv ->
                        nv.getNvCccd().equals(cccd)
                        && nv.getNvTt() == nvTt
                );
    }

    // ================= MAP CCCD -> LIST NV =================
    public HashMap<String, List<NguyenVongDTO>> mapByCccd() {
        HashMap<String, List<NguyenVongDTO>> map = new HashMap<>();

        for (NguyenVongDTO nv : nvDAO.getAll()) {
            map.putIfAbsent(nv.getNvCccd(), new ArrayList<>());
            map.get(nv.getNvCccd()).add(nv);
        }

        return map;
    }

    // ================= TÍNH ĐIỂM XÉT =================
    public BigDecimal tinhDiemXet(NguyenVongDTO nv) {
        BigDecimal tong = BigDecimal.ZERO;

        if (nv.getDiemThxt() != null)
            tong = tong.add(nv.getDiemThxt());

        if (nv.getDiemUtqd() != null)
            tong = tong.add(nv.getDiemUtqd());

        if (nv.getDiemCong() != null)
            tong = tong.add(nv.getDiemCong());

        return tong;
    }

    // ================= UPDATE ĐIỂM XÉT =================
    public void updateDiemXet() {
        for (NguyenVongDTO nv : nvDAO.getAll()) {
            BigDecimal diem = tinhDiemXet(nv);
            nv.setDiemXettuyen(diem);
            nvDAO.update(nv);
        }
    }

    // ================= XÉT ĐẬU THEO NGÀNH =================
    public void xetKetQuaTheoNganh(String manganh, BigDecimal diemChuan) {
        nvDAO.xetKetQua(manganh, diemChuan);
    }

    // ================= XÉT THEO THỨ TỰ NV =================
    public void xetTheoNV(String cccd) {
        nvDAO.xetTheoThuTuNguyenVong(cccd);
    }

    // ================= XÉT TOÀN BỘ =================
    public void xetToanBo() {

        // 1. Tính điểm
        updateDiemXet();

        // 2. Lấy danh sách CCCD
        HashMap<String, List<NguyenVongDTO>> map = mapByCccd();

        // 3. Xét từng thí sinh
        for (String cccd : map.keySet()) {
            xetTheoNV(cccd);
        }
    }

    // ================= SEARCH =================
    public ArrayList<NguyenVongDTO> timKiem(String cccd, String manganh, String ketqua) {
        ArrayList<NguyenVongDTO> result = new ArrayList<>();

        for (NguyenVongDTO nv : nvDAO.getAll()) {

            boolean matchCccd = (cccd == null || cccd.isEmpty())
                    || nv.getNvCccd().contains(cccd);

            boolean matchNganh = (manganh == null || manganh.equals("Tất cả"))
                    || nv.getNvManganh().equals(manganh);

            boolean matchKQ = (ketqua == null || ketqua.equals("Tất cả"))
                    || (nv.getNvKetqua() != null && nv.getNvKetqua().equals(ketqua));

            if (matchCccd && matchNganh && matchKQ) {
                result.add(nv);
            }
        }

        return result;
    }

    // ================= SEARCH TEXT =================
    public ArrayList<NguyenVongDTO> timKiemText(String text) {
        String t = text.toLowerCase();
        ArrayList<NguyenVongDTO> result = new ArrayList<>();

        for (NguyenVongDTO nv : nvDAO.getAll()) {
            if ((nv.getNvCccd() != null && nv.getNvCccd().toLowerCase().contains(t))
                    || (nv.getNvManganh() != null && nv.getNvManganh().toLowerCase().contains(t))) {
                result.add(nv);
            }
        }

        return result;
    }

    // ================= IMPORT EXCEL =================
    public int importFromExcel(String filePath) {

        List<NguyenVongDTO> importList = nvDAO.importFromExcel(filePath);

        if (importList == null || importList.isEmpty()) {
            return 0;
        }

        // map check trùng
        HashMap<String, Boolean> map = new HashMap<>();

        for (NguyenVongDTO nv : nvDAO.getAll()) {
            String key = nv.getNvCccd() + "_" + nv.getNvTt();
            map.put(key, true);
        }

        List<NguyenVongDTO> newList = new ArrayList<>();

        for (NguyenVongDTO nv : importList) {

            if (nv.getNvCccd() == null) continue;

            String key = nv.getNvCccd() + "_" + nv.getNvTt();

            if (!map.containsKey(key)) {
                newList.add(nv);
                map.put(key, true);
            }
        }

        if (!newList.isEmpty()) {
            nvDAO.insertList(newList);
            nvList.addAll(newList);
        }

        return newList.size();
    }

}