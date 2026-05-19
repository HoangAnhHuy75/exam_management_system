package BUS;

import DAO.ThongKeDAO;
import java.util.ArrayList;

public class ThongKeBUS {

    private ThongKeDAO thongKeDAO;

    public ThongKeBUS() {
        thongKeDAO = new ThongKeDAO();
    }

    // Tổng số thí sinh
    public int getSoLuongThiSinh() {
        return thongKeDAO.getSoLuongThiSinh();
    }

    // Tổng số nguyện vọng
    public int getSoLuongNguyenVong() {
        return thongKeDAO.getSoLuongNguyenVong();
    }

    // Tổng số ngành
    public int getTongSoNganh() {
        return thongKeDAO.getTongSoNganh();
    }

    // Tổng số tổ hợp
    public int getSoLuongToHop() {
        return thongKeDAO.getSoLuongToHop();
    }

    // Số lượng thí sinh trúng tuyển
    public int getSoLuongThiSinhTrungTuyen() {
        return thongKeDAO.getSoLuongThiSinhTrungTuyen();
    }

    // Top 5 ngành có tỷ lệ chọi cao nhất
// Object[] trả về gồm:
// [0] -> String : Tên ngành
// [1] -> Integer : Chỉ tiêu
// [2] -> Long : Số lượng nguyện vọng đăng ký
    public ArrayList<Object[]> getTop5NganhTyLeChoiCaoNhat() {
        return thongKeDAO.getTop5NganhTyLeChoiCaoNhat();
    }
}