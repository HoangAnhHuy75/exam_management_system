/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BUS;

import DAO.NganhDAO;
import DTO.NganhDTO;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author kiman
 */
public class NganhBUS {

    NganhDAO nganhDao = new NganhDAO();
    ArrayList<NganhDTO> nganhList = new ArrayList<>();

    public NganhBUS() {
    }
    
    public int insert(NganhDTO n){
        if (checkDup(n.getManganh())) {
            return 0;
        }
        return nganhDao.insert(n);
    }

    public ArrayList<NganhDTO> getListN() {
        nganhList = nganhDao.getAllNganh();
        return nganhList;
    }

    public int importFromExcel(String filePath) {
        List<NganhDTO> list = nganhDao.importNganh(filePath);
        if (list == null || list.isEmpty()) {
            return 0;
        }
        ArrayList<NganhDTO> currentList = nganhDao.getAllNganh();
        HashMap<String, Boolean> map = new HashMap<>();
        for (NganhDTO n : currentList) {
            map.put(n.getManganh().toLowerCase(), true);
        }
        ArrayList<NganhDTO> newList = new ArrayList<>();
        for (NganhDTO n : list) {
            String key = n.getManganh().toLowerCase();
            if (!map.containsKey(key)) {
                newList.add(n);
                map.put(key, true);
            }
        }
        if (newList.isEmpty()) {
            return 0;
        }
        nganhDao.insertList(newList);
        return newList.size();
    }
    
    public String getMaNganhByTenNganh(String tennganh) {
        String manganh = null;
        for (NganhDTO nganh : nganhDao.getAllNganh()) {
            if (tennganh.equals(nganh.getTennganh())) {
                manganh = nganh.getManganh();
                break;
            }
        }
        return manganh;
    }
    
    public String getTenNganhByMaNganh(String manganh) {
        String tennganh = null;
        for (NganhDTO nganh : nganhDao.getAllNganh()) {
            if (manganh.equals(nganh.getManganh())) {
                tennganh = nganh.getTennganh();
                break;
            }
        }
        return tennganh;
    }

   
    public HashMap<String,String> nganhMap(){
        HashMap<String,String> nganhMap = nganhDao.nganhMap();
        return nganhMap;
    }

    public ArrayList<String> getListToHopGoc() {
        ArrayList<String> listToHopGoc = new ArrayList<>();
        for (NganhDTO n : nganhDao.getAllNganh()) {
            if (!listToHopGoc.contains(n.getN_tohopgoc())) {
                listToHopGoc.add(n.getN_tohopgoc());
            }
        }
        return listToHopGoc;
    }

    public ArrayList<NganhDTO> timKiem(String ptxt,String thg, String maNganh, String tenNganh) {
        ArrayList<NganhDTO> timkiemList = new ArrayList<>();
        for (NganhDTO n : nganhDao.getAllNganh()) {
            // 1. check phương thức
            boolean matchPT = false;

            if (ptxt.equals("Tất cả")) {
                matchPT = true;
            } else if (ptxt.equals("Tuyển thẳng") && "Y".equalsIgnoreCase(n.getN_tuyenthang())) {
                matchPT = true;
            } else if (ptxt.equals("ĐGNL") && "Y".equalsIgnoreCase(n.getN_dgnl())) {
                matchPT = true;
            } else if (ptxt.equals("THPT") && "Y".equalsIgnoreCase(n.getN_thpt())) {
                matchPT = true;
            } else if (ptxt.equals("VSAT") && "Y".equalsIgnoreCase(n.getN_vsat())) {
                matchPT = true;
            }
            
            // 2. check tổ hợp gốc
            boolean matchTHG = false;
            if(thg.equals("Tất cả"))
                matchTHG = true;
            else if(n.getN_tohopgoc().equals(thg))
                matchTHG = true;

            // 3. check mã ngành
            boolean matchMa = (maNganh == null || maNganh.isEmpty())
                    || n.getManganh().toLowerCase().contains(maNganh.toLowerCase());

            // 4. check tên ngành
            boolean matchTen = (tenNganh == null || tenNganh.isEmpty())
                    || n.getTennganh().toLowerCase().contains(tenNganh.toLowerCase());

            // 4. nếu thỏa tất cả thì add
            if (matchPT && matchMa && matchTHG && matchTen) {
                timkiemList.add(n);
            }
        }

        return timkiemList;
    }
    
    public boolean checkDup(String manganh) {
        HashMap<String, Boolean> map = new HashMap<>();
        for (NganhDTO th : nganhDao.getAllNganh()) {
            map.put(th.getManganh().toLowerCase(), true);
        }
        return map.containsKey(manganh.toLowerCase());
    }
}
