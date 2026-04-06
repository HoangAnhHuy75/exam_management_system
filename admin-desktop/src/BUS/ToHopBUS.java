/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BUS;

import DAO.ToHopDAO;
import DTO.ToHopDTO;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author kiman
 */
public class ToHopBUS {
    ToHopDAO tohopDao = new ToHopDAO();
    ArrayList<ToHopDTO> tohopList = new ArrayList<>();
    
    public ToHopBUS(){}
    
    public ArrayList<ToHopDTO> getListTH(){
        tohopList = tohopDao.getAllToHop();
        return tohopList;
    }
    
    public int insert(ToHopDTO th) {
        if (checkDup(th.getMatohop())) {
            return 0;
        }
        return tohopDao.insert(th);
    }
    
    public int importFromExcel(String filePath) {
        List<ToHopDTO> list = tohopDao.importToHop(filePath);

        if (list == null || list.isEmpty()) {
            return 0;
        }

        // lấy dữ liệu đã có trong DB
        ArrayList<ToHopDTO> currentList = tohopDao.getAllToHop();
        HashMap<String, Boolean> map = new HashMap<>();

        for (ToHopDTO th : currentList) {
            map.put(th.getMatohop().toLowerCase(), true);
        }

        ArrayList<ToHopDTO> newList = new ArrayList<>();

        for (ToHopDTO th : list) {
            String key = th.getMatohop().toLowerCase();

            // nếu chưa tồn tại thì thêm
            if (!map.containsKey(key)) {
                newList.add(th);
                map.put(key, true); // tránh trùng trong chính file Excel
            }
        }

        if (newList.isEmpty()) {
            return 0;
        }

        tohopDao.insertList(newList);
        return newList.size(); // trả về số record thực sự insert
    }
    
    public String getMaTHByTenTH(String tenTH) {
        String maTH = null;
        for (ToHopDTO tohop : tohopDao.getAllToHop()) {
            if (tenTH.equals(tohop.getTentohop())) {
                maTH = tohop.getMatohop();
                break;
            }
        }
        return maTH;
    }
    
    public HashMap<String, String> tohopMap(){
        HashMap<String, String> tohopMap = tohopDao.tohopMap();
        return tohopMap;
    }
    
    public ArrayList<ToHopDTO> timkiem(String maTH, String tenTH){
        ArrayList<ToHopDTO> timkiemList = new ArrayList<>();
        for(ToHopDTO th : tohopDao.getAllToHop()){
            boolean mathMa = (maTH == null || maTH.isEmpty()) || th.getMatohop().toLowerCase().contains(maTH);
            boolean mathTen = (tenTH == null || tenTH.isEmpty()) || th.getTentohop().toLowerCase().contains(tenTH);
            if(mathMa && mathTen)
                timkiemList.add(th);
        }
        return timkiemList;
    }
    
    public boolean checkDup(String matohop) {
        HashMap<String, Boolean> map = new HashMap<>();
        for (ToHopDTO th : tohopDao.getAllToHop()) {
            map.put(th.getMatohop().toLowerCase(), true);
        }
        return map.containsKey(matohop.toLowerCase());
    }
    
}
