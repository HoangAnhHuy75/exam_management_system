package BUS;

import DAO.DiemThiDAO;
import DAO.ThiSinhDAO;
import DTO.DiemThiDTO;
import DTO.NganhDTO;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author HP
 */
public class DiemThiBUS {
    private DiemThiDAO diemThiDao = new DiemThiDAO();
    private ThiSinhDAO thiSinhDao = new ThiSinhDAO();
    private ArrayList<DiemThiDTO> diemThiList = new ArrayList<>();
//    cache
//    public ArrayList<DiemThiDTO> getList(){
//
//    if(diemThiList.isEmpty()){
//        diemThiList = diemThiDao.getAllDiem();
//    }
//
//    return diemThiList;
//}
    // lấy danh sách điểm thi
    public ArrayList<DiemThiDTO> getList(){
        diemThiList = diemThiDao.getAllDiem();
        return diemThiList;
    }
    
    // load cccd combobox
    public List<String> loadCbbCccd(){
        return thiSinhDao.getAllCCCD();
    }
    // load sbd khi chọn cccd
    public List<String> loadCbbSBD(String cccd) {
        return thiSinhDao.getSBDByCccd(cccd);
    }
    // thêm điểm
    public int insert(DiemThiDTO dt) {
        if(checkDup(dt.getCccd())) {
            return 0;
        }
        int success = diemThiDao.insert(dt);
        return success;
    }
    
    // sửa điểm
    public int update(DiemThiDTO dt) {
        int success = diemThiDao.update(dt);
        return success;
    }
    
    // xóa điểm
    public int delete(DiemThiDTO dt) {
        int success = diemThiDao.delete(dt);
        return success;
    }
    
    //kiểm tra trùng cccd
    public boolean checkDup(String cccd) {
        return diemThiDao.getAllDiem().stream()
                .anyMatch(n -> n.getCccd().equalsIgnoreCase(cccd));
    }
    
    //tìm kiếm theo cccd
    public ArrayList<DiemThiDTO> findBycccd(String cccd) {
        ArrayList<DiemThiDTO> rs = new ArrayList<>();
        String cancuoc = cccd != null ? cccd.toLowerCase() : "";
        for(DiemThiDTO dt : diemThiDao.getAllDiem()) {
            boolean matchCccd = cancuoc.isEmpty() || dt.getCccd().toLowerCase().contains(cancuoc);
            if(matchCccd) {
                rs.add(dt);
            }
        }
        return rs;
    }
    
    // tìm 1 cccd
    public DiemThiDTO findOneByCCCD(String cccd) {

    for(DiemThiDTO dt : diemThiDao.getAllDiem()) {
        if(dt.getCccd().equals(cccd)) {
            return dt;
        }
    }

    return null;
    }
    //filter theo phương thức xét tuyển
    public ArrayList<DiemThiDTO> filterByPTXT(String pt) {
        String ptText = convertPhuongThuc(pt);
        ArrayList<DiemThiDTO> list = new ArrayList<>();
        
        for(DiemThiDTO dt: diemThiDao.getAllDiem()) {
            if(dt.getD_phuongthuc() == null) continue;
            if(dt.getD_phuongthuc().equalsIgnoreCase(ptText)) {
                list.add(dt);
            }
        }
        return list;
    }
    //filter nâng cao (theo loại điểm và môn)
    public ArrayList<DiemThiDTO> filterByLoaiDiemVaMon(String mon,String loai){

        ArrayList<DiemThiDTO> list = new ArrayList<>();

        for(DiemThiDTO dt : diemThiDao.getAllDiem()){

            BigDecimal diem = layDiemTheoMon(dt, mon); 
            if(diem == null) continue;
            
            if(loai.equals("Giỏi") && diem.compareTo(new BigDecimal("8")) >= 0)
                list.add(dt);

            else if(loai.equals("Khá") && diem.compareTo(new BigDecimal("6.5")) >= 0 && diem.compareTo(new BigDecimal("8")) < 0)
                list.add(dt);

            else if(loai.equals("Trung bình") && diem.compareTo(new BigDecimal("5")) >= 0 && diem.compareTo(new BigDecimal("6.5")) < 0 )
                list.add(dt);

            else if(loai.equals("Yếu") && diem.compareTo(new BigDecimal("3.5")) >= 0 && diem.compareTo(new BigDecimal("5")) < 0)
                list.add(dt);

            else if(loai.equals("Kém") && diem.compareTo(new BigDecimal("3.5")) < 0)
                list.add(dt);
        }

        return list;
    }
    // Thống kê theo môn
    public HashMap<String, Integer> thongKetheoMon(String mon) {
        HashMap<String,Integer> map = new HashMap<>();
        map.put("Giỏi", 0);
        map.put("Khá", 0);
        map.put("Trung bình", 0);
        map.put("Yếu", 0);
        map.put("Kém", 0);
        
        for(DiemThiDTO dt : diemThiDao.getAllDiem()) {
            BigDecimal diem = layDiemTheoMon(dt, mon);
            
            if(diem == null) continue;
            
            if(diem.compareTo(new BigDecimal("8")) >= 0 ){
                map.put("Giỏi", map.get("Giỏi") + 1);
            }
            else if(diem.compareTo(new BigDecimal("6.5")) >= 0 ){
                map.put("Khá", map.get("Khá") + 1);
            }
            else if(diem.compareTo(new BigDecimal("5")) >= 0 ){
                map.put("Trung bình", map.get("Trung bình") + 1);
            }
            else if(diem.compareTo(new BigDecimal("3.5")) >= 0 ){
                map.put("Yếu", map.get("Yếu") + 1);
            }
            else {
                map.put("Kém", map.get("Kém") + 1);
            }
        }
        return map;
    }
    // lấy điểm theo môn
    public BigDecimal layDiemTheoMon(DiemThiDTO dt,String mon) {
        switch (mon) {
            case "Toán":
                return dt.getTO();
            case "Lí":
                return dt.getLI();
            case "Hóa":
                return dt.getHO();
            case "Sinh":
                return dt.getSI();
            case "Sử":
                return dt.getSU();
            case "Địa":
                return dt.getDI();
            case "Văn":
                return dt.getVA();
            case "CNCN":
                return dt.getCNCN();
            case "CNNN":
                return dt.getCNNN();
            case "Tin":
                return dt.getTI();
            case "KTPL":
                return dt.getKTPL();
            case "NK1":
                return dt.getNK1();
            case "NK2":
                return dt.getNK2();
            case "NL1":
                return dt.getNL1();
            case "N1_THI":
                return dt.getN1_THI();
            case "N1_CC":
                return dt.getN1_CC();
        }
        return null;
    }
    // import điểm thi
    public int importFromExcel(String filePath) {
        List<DiemThiDTO> importList = diemThiDao.importFromExcel(filePath);
        
        if (importList == null || importList.isEmpty()) {
            return 0;
        }
        
        // 2. Tạo map để check trùng
        HashMap<String, Boolean> existingMap = new HashMap<>();
        for (DiemThiDTO n : diemThiDao.getAllDiem()) {
            if (n.getCccd() != null) {
                existingMap.put(n.getCccd(), true);
            }
        }
        List<DiemThiDTO> newList = new ArrayList<>();
        for (DiemThiDTO dt : importList) {
            if (dt.getCccd() == null){
                continue;
            }
            
            String key = dt.getCccd();
            
            if (!existingMap.containsKey(key)) {
                newList.add(dt);
                existingMap.put(key, true); // tránh trùng trong file
            } 
        }
        diemThiDao.saveAll(newList);
        
        return newList.size();
    }
    
    public String convertPhuongThuc(String pt) {

    switch (pt) {
        case "Tuyển thẳng": return "PT1";
        case "ĐGNL": return "PT2";
        case "VSAT": return "PT3";
        case "THPT": return "PT4";
        default: return "";
        }
    }
}
