/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BUS;

import DAO.XetTuyenDAO;
import DTO.NganhDTO;
import DTO.NguyenVongDTO;
import DTO.XetTuyenDTO;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class XetTuyenBUS {

    private XetTuyenDAO xtDao = new XetTuyenDAO();
    private List<XetTuyenDTO> xtList = new ArrayList<>();
    NguyenVongBUS nvBus = new NguyenVongBUS();
    NganhBUS nganhBus = new NganhBUS();

    // Thêm
    public int insert(XetTuyenDTO xt) {
        int success = xtDao.insert(xt);
        return success;
    }

    // Xóa
    public int delete(XetTuyenDTO xt) {
        int success = xtDao.delete(xt);
        return success;
    }

    // Update
    public int update(XetTuyenDTO xt) {
        return xtDao.update(xt);
    }

    // Lấy danh sách
    public List<XetTuyenDTO> getList() {
        xtList = xtDao.getAllXetTuyen();
        return xtList;
    }

    // Tìm 1 xét tuyển theo CCCD + mã ngành
    public XetTuyenDTO findOne(String cccd, String maNganh) {

        for (XetTuyenDTO xt : xtDao.getAllXetTuyen()) {

            boolean matchCCCD = xt.getCccd() != null
                    && xt.getCccd().equalsIgnoreCase(cccd);

            boolean matchMaNganh = xt.getMaNganh() != null
                    && xt.getMaNganh().equalsIgnoreCase(maNganh);

            if (matchCCCD && matchMaNganh) {
                return xt;
            }
        }

        return null;
    }

    // Map CCCD -> XetTuyenDTO
    public HashMap<String, XetTuyenDTO> xetTuyenMap() {

        HashMap<String, XetTuyenDTO> map = new HashMap<>();

        for (XetTuyenDTO xt : xtDao.getAllXetTuyen()) {
            map.put(xt.getCccd(), xt);
        }

        return map;
    }

    // Tìm kiếm text
    public List<XetTuyenDTO> timKiemText(String text) {

        String textFind = text.toLowerCase();

        List<XetTuyenDTO> result = new ArrayList<>();

        for (XetTuyenDTO xt : xtDao.getAllXetTuyen()) {

            String cccd = xt.getCccd() != null
                    ? xt.getCccd().toLowerCase()
                    : "";

            String maNganh = xt.getMaNganh() != null
                    ? xt.getMaNganh().toLowerCase()
                    : "";

            String ketQua = xt.getKetQua() != null
                    ? xt.getKetQua().toLowerCase()
                    : "";

            String phuongThuc = xt.getPhuongThuc() != null
                    ? xt.getPhuongThuc().toLowerCase()
                    : "";

            if (cccd.contains(textFind)
                    || maNganh.contains(textFind)
                    || ketQua.contains(textFind)
                    || phuongThuc.contains(textFind)) {

                result.add(xt);
            }
        }

        return result;
    }

    // Lấy id theo index
    public int getIdByIndex(int index) {

        List<XetTuyenDTO> list = xtDao.getAllXetTuyen();

        if (index >= 0 && index < list.size()) {
            return list.get(index).getIdXetTuyen();
        }

        return -1;
    }
    
    public void tienHanhXetTuyen() {
        xtDao.deleteAll();
        List<NguyenVongDTO> listNV = nvBus.getList();

        // nhóm theo mã ngành
        HashMap<String, List<NguyenVongDTO>> mapByNVMaNganh = nvBus.mapByMaNganh();
        List<XetTuyenDTO> insertList = new ArrayList<>();

        // duyệt từng ngành
        for (String maNganh : mapByNVMaNganh.keySet()) {

            List<NguyenVongDTO> ds = mapByNVMaNganh.get(maNganh);

            // lấy ngành
            NganhDTO nganh = null;

            for (NganhDTO n : nganhBus.getListN()) {
                if (n.getMaNganh().equals(maNganh)) {
                    nganh = n;
                    break;
                }
            }

            if (nganh == null) {
                continue;
            }

            int chiTieu = nganh.getNChiTieu();

            // sort giảm dần theo điểm
            ds.sort((a, b) -> {

                int cmp = b.getDiemXettuyen()
                        .compareTo(a.getDiemXettuyen());

                if (cmp != 0) {
                    return cmp;
                }

                return Integer.compare(a.getNvTt(), b.getNvTt());
            });

            BigDecimal diemTrungTuyen = BigDecimal.ZERO;

            if (ds.size() >= chiTieu && chiTieu > 0) {

                diemTrungTuyen
                        = ds.get(chiTieu - 1).getDiemXettuyen();

            } else if (!ds.isEmpty()) {

                diemTrungTuyen
                        = ds.get(ds.size() - 1).getDiemXettuyen();
            }

            // cập nhật điểm trúng tuyển cho ngành
            nganh.setNDiemTrungTuyen(diemTrungTuyen);
            nganhBus.update(nganh);

            // xét từng người
            for (int i = 0; i < ds.size(); i++) {

                NguyenVongDTO nv = ds.get(i);

                XetTuyenDTO xt = new XetTuyenDTO();

                xt.setCccdTS(nv.getNvCccd());
                xt.setMaNganh(nv.getNvManganh());
                xt.setDiemXetTuyen(nv.getDiemXettuyen());
                xt.setPhuongThuc(nv.getTtPhuongthuc());

                if (i < chiTieu) {
                    xt.setKetQua("Đậu");
                } else {
                    xt.setKetQua("Trượt");
                }

                insertList.add(xt);
            }
        }
        xtDao.insertList(insertList);

    }
    
    public ArrayList<XetTuyenDTO> filter(String text, String tenNganh, String phuongThuc, String kq) {
        String t = text.toLowerCase().trim();
        ArrayList<XetTuyenDTO> result = new ArrayList<>();
        HashMap<String, String> mapTenNganh = nganhBus.getMapTenNganh();
        for (XetTuyenDTO xt : xtDao.getAllXetTuyen()) {

            boolean matchTenNganh = (tenNganh == null || tenNganh.equals("Tất cả"))
                    || mapTenNganh.get(xt.getMaNganh()).contains(tenNganh);

            boolean matchPT = (phuongThuc == null || phuongThuc.equals("Tất cả"))
                    || xt.getPhuongThuc().equals(phuongThuc);

            boolean matchKQ = (kq == null || kq.equals("Tất cả"))
                    || xt.getKetQua().equals(kq);
            boolean matchText = t.equals("") || xt.getCccd().toLowerCase().contains(t) || mapTenNganh.get(xt.getMaNganh()).toLowerCase().contains(t);
            if (matchTenNganh && matchPT && matchKQ && matchText) {
                result.add(xt);
            }
        }
        return result;
    }
    
    public List<XetTuyenDTO> timKiem(String text) {
        String t = text.toLowerCase();
        List<XetTuyenDTO> result = new ArrayList<>();
        HashMap<String, String> mapTenNganh = nganhBus.getMapTenNganh();

        for (XetTuyenDTO xt : xtDao.getAllXetTuyen()) {
            if ((xt.getCccd() != null && xt.getCccd().toLowerCase().contains(t))
                    || (xt.getMaNganh() != null && mapTenNganh.get(xt.getMaNganh()).toLowerCase().contains(t))) {
                result.add(xt);
            }
        }
        return result;
    }
}