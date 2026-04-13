package BUS;

import DAO.DiemCongDAO;
import DTO.DiemCongDTO;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author HP
 */
public class DiemCongBUS {
    private DiemCongDAO diemCongDao = new DiemCongDAO();
    private ArrayList<DiemCongDTO> diemCongList = new ArrayList<>();
    private NganhBUS ngB = new NganhBUS();
    // lấy danh sách điểm thi
    public ArrayList<DiemCongDTO> getList(){
        diemCongList = diemCongDao.getAllDiemCong();
        return diemCongList;
    }
    
    // load mã ngành vào cbb
    public List<String> loadMaNganh() {
        return diemCongDao.getAllMaNganh();
    }
    
    // load mã tổ hợp vào cbb
    public List<String> loadMaToHop() {
        return diemCongDao.getAllMaToHop();
    }
    
    // thêm điểm
    public int insert(DiemCongDTO dc) {
        if(checkDup(dc.getDc_keys())) {
            return 0;
        }
        int success = diemCongDao.insert(dc);
        return success;
    }
    
    // sửa điểm
    public int update(DiemCongDTO dc) {
        int success = diemCongDao.update(dc);
        return success;
    }
    
    // xóa điểm
    public int delete(DiemCongDTO dc) {
        int success = diemCongDao.delete(dc);
        return success;
    }
    
    // tìm 1 dc_keys
    public DiemCongDTO findOneByDcKeys(String dcKeys) {

    for(DiemCongDTO dc : diemCongDao.getAllDiemCong()) {
        if(dc.getDc_keys().equals(dcKeys)) {
            return dc;
        }
    }

    return null;
    }
    // kiểm tra trùng dc_keys
    public boolean checkDup(String dcKeys) {
        return diemCongDao.getAllDiemCong().stream()
                .anyMatch(n -> n.getDc_keys().equalsIgnoreCase(dcKeys));
    }
    
    // Tìm kiếm theo cccd
    public ArrayList<DiemCongDTO> findByCccd (String cccd) {
        ArrayList<DiemCongDTO> rs = new ArrayList<>();
        String cancuoc = cccd != null ? cccd.toLowerCase() : "";
        for(DiemCongDTO dc : diemCongDao.getAllDiemCong()) {
            boolean matchCccd = cancuoc.isEmpty() || dc.getTs_cccd().toLowerCase().contains(cancuoc);
            if(matchCccd) {
                rs.add(dc);
            }
        }
        return rs;
    }
    
    // filter tổng hợp
    public ArrayList<DiemCongDTO> filterAll(String tenNganh, String maToHop, String pt) {
        ArrayList<DiemCongDTO> list = new ArrayList<>();
        HashMap<String,String> map = ngB.nganhMap();

        String maNganh = map.get(tenNganh);
        String ptText = convertPhuongThuc(pt);

        for (DiemCongDTO dc : diemCongDao.getAllDiemCong()) {

            // lọc ngành
            if (tenNganh != null && !tenNganh.equals("Chọn tên ngành")) {
                if (maNganh == null || !dc.getManganh().equalsIgnoreCase(maNganh)) continue;
            }

            // lọc tổ hợp
            if (maToHop != null && !maToHop.equals("Chọn Mã Tổ Hợp")) {
                if (dc.getMatohop() == null || !dc.getMatohop().equalsIgnoreCase(maToHop)) continue;
            }

            // lọc phương thức
            if (pt != null && !pt.equals("Bộ lọc ptxt")) {
                if (dc.getPhuongthuc() == null || !dc.getPhuongthuc().equalsIgnoreCase(ptText)) continue;
            }

            list.add(dc);
        }

        return list;
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
