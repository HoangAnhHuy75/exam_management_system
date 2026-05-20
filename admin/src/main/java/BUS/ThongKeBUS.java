package BUS;

import DAO.ThongKeDAO;
import java.util.ArrayList;

public class ThongKeBUS {

    // ================= SINGLETON =================
    private static final ThongKeBUS instance =
            new ThongKeBUS();

    public static ThongKeBUS getInstance() {
        return instance;
    }

    // ================= DAO =================
    private final ThongKeDAO thongKeDAO;

    // ================= CACHE =================
    private Integer tongThiSinhCache;

    private Integer tongNguyenVongCache;

    private Integer tongNganhCache;

    private Integer tongToHopCache;

    private Integer tongTrungTuyenCache;

    private ArrayList<Object[]> top5NganhCache;

    // ================= CACHE TIME =================
    private long lastLoadTime = 0;

    // Cache 30 giây
    private static final long CACHE_DURATION =
            30 * 1000;

    // ================= CONSTRUCTOR =================
    public ThongKeBUS() {

        thongKeDAO = new ThongKeDAO();
    }

    // ================= CHECK CACHE =================
    private boolean isCacheExpired() {

        long now = System.currentTimeMillis();

        return now - lastLoadTime > CACHE_DURATION;
    }

    // ================= CLEAR CACHE =================
    public void clearCache() {

        tongThiSinhCache = null;

        tongNguyenVongCache = null;

        tongNganhCache = null;

        tongToHopCache = null;

        tongTrungTuyenCache = null;

        top5NganhCache = null;

        System.out.println("ThongKe cache cleared");
    }

    // ================= RELOAD CACHE =================
    private void reloadIfNeeded() {

        if (tongThiSinhCache == null || isCacheExpired()) {

            System.out.println("Reload thong ke cache...");

            tongThiSinhCache =
                    thongKeDAO.getSoLuongThiSinh();

            tongNguyenVongCache =
                    thongKeDAO.getSoLuongNguyenVong();

            tongNganhCache =
                    thongKeDAO.getTongSoNganh();

            tongToHopCache =
                    thongKeDAO.getSoLuongToHop();

            tongTrungTuyenCache =
                    thongKeDAO.getSoLuongThiSinhTrungTuyen();

            top5NganhCache =
                    thongKeDAO.getTop5NganhTyLeChoiCaoNhat();

            lastLoadTime = System.currentTimeMillis();
        }
    }

    // ================= API =================

    // Tổng số thí sinh
    public int getSoLuongThiSinh() {

        reloadIfNeeded();

        return tongThiSinhCache;
    }

    // Tổng số nguyện vọng
    public int getSoLuongNguyenVong() {

        reloadIfNeeded();

        return tongNguyenVongCache;
    }

    // Tổng số ngành
    public int getTongSoNganh() {

        reloadIfNeeded();

        return tongNganhCache;
    }

    // Tổng số tổ hợp
    public int getSoLuongToHop() {

        reloadIfNeeded();

        return tongToHopCache;
    }

    // Tổng số thí sinh trúng tuyển
    public int getSoLuongThiSinhTrungTuyen() {

        reloadIfNeeded();

        return tongTrungTuyenCache;
    }

    /*
     * Object[] gồm:
     * [0] -> String  : Tên ngành
     * [1] -> Integer : Chỉ tiêu
     * [2] -> Long    : Số lượng nguyện vọng
     */
    public ArrayList<Object[]> getTop5NganhTyLeChoiCaoNhat() {

        reloadIfNeeded();

        return top5NganhCache;
    }
}