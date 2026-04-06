/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BUS;

import DAO.ToHop_Nganh_DAO;
import DTO.ToHop_Nganh_DTO;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author kiman
 */
public class ToHop_Nganh_BUS {
    ArrayList<ToHop_Nganh_DTO> toh_ng_List = new ArrayList<>();
    ToHop_Nganh_DAO toH_ng_Dao = new ToHop_Nganh_DAO();
    
    public ToHop_Nganh_BUS(){}
    
    public ArrayList<ToHop_Nganh_DTO> getListToHop_Nganh(){
        toh_ng_List = toH_ng_Dao.getAll();
        return toh_ng_List;
    }
    
    public int importFromExcel(String filePath){
        List<ToHop_Nganh_DTO> importList = toH_ng_Dao.importExcel(filePath);
        if(importList.isEmpty() || importList == null){
            return 0;
        }
        toH_ng_Dao.insertList(importList);
        return 1;
    }
    
    public int insert(ToHop_Nganh_DTO t){
        int check = toH_ng_Dao.insert(t);
        return check;
    }
}
