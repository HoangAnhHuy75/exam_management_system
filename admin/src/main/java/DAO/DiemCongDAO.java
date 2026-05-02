package DAO;

import DTO.DiemCongDTO;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.poi.ss.usermodel.Cell;
import static org.apache.poi.ss.usermodel.CellType.NUMERIC;
import static org.apache.poi.ss.usermodel.CellType.STRING;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import util.HibernateUtil;

/**
 *
 * @author HP
 */
public class DiemCongDAO {
    // lấy danh sách xt_diemcong
    public ArrayList<DiemCongDTO> getAllDiemCong() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<DiemCongDTO> query = session.createQuery("FROM DiemCongDTO", DiemCongDTO.class);
            return new ArrayList<>(query.list());
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
    
    // thêm điểm
    public int insert(DiemCongDTO dc) {
        Transaction tr = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()){
            tr = session.beginTransaction();
            session.save(dc);
            tr.commit();
            return 1;
        } catch (Exception e) {
            if(tr != null) {
                tr.rollback();
            }
            e.printStackTrace();
            return 0;
        }
    }
    
    public void insertList(List<DiemCongDTO> list) {
        Transaction tx = null;
        int batchSize = 50;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();

            for (int i = 0; i < list.size(); i++) {
                session.save(list.get(i));

                // 🔥 batch insert
                if (i > 0 && i % batchSize == 0) {
                    session.flush();   // đẩy xuống DB
                    session.clear();   // giải phóng cache
                }
            }

            tx.commit();

        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        }
    }
    
    // sửa điểm
    public int update(DiemCongDTO dc) {
        Transaction tr = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()){
            tr = session.beginTransaction();
            session.update(dc);
            tr.commit();
            return 1;
        } catch (Exception e) {
            if(tr != null) {
                tr.rollback();
            }
            e.printStackTrace();
            return 0;
        }
    }
    
    // xóa điểm
    public int delete(DiemCongDTO dc) {
        Transaction tr = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()){
            tr = session.beginTransaction();
            session.delete(dc);
            tr.commit();
            return 1;
        } catch (Exception e) {
            if(tr != null) {
                tr.rollback();
            }
            e.printStackTrace();
            return 0;
        }
    }
    
    // lấy mã ngành
    public List<String> getAllMaNganh() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<String> query = session.createQuery(
                    "SELECT n.maNganh FROM NganhDTO n", String.class
            );
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
    
    // lấy mã tổ hợp
    public List<String> getAllMaToHop() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<String> query = session.createQuery(
                    "SELECT matohop FROM ToHopDTO", String.class
            );
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
    
    public List<Object[]> getListMaToHopAndMaNganhByTenNganh(String tenNganh) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String sql = "SELECT thn.matohop, thn.manganh, thn.th_mon1, thn.th_mon2, thn.th_mon3 "
                    + "FROM xt_tohop_nganh thn "
                    + "INNER JOIN xt_nganh nganh "
                    + "ON thn.manganh = nganh.manganh "
                    + "WHERE nganh.tennganh = :tennganh";
            Query<Object[]> query = session.createNativeQuery(sql);
            query.setParameter("tennganh", tenNganh);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
    
    
    public int getColumnIndex(Row headerRow, String columnName) {
        for (Cell cell : headerRow) {
            if (cell.getStringCellValue().trim().equalsIgnoreCase(columnName)) {
                return cell.getColumnIndex();
            }
        }
        return -1;
    }

    private String getCell(Row row, int index) {
        if (index == -1) {
            return null;
        }
        Cell cell = row.getCell(index);
        if (cell == null) {
            return null;
        }
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue().trim();
            case NUMERIC:
                return String.valueOf((long) cell.getNumericCellValue());
            default:
                return cell.toString().trim();
        }
    }
    
    private String normalizeMon(String mon) {
        if (mon == null) {
            return "";
        }
        mon = mon.trim().toLowerCase();
        switch (mon) {
            case "tiếng anh":
                return "N1";
            case "lịch sử":
                return "SU";
            case "địa lí":
            case "địa lý":
                return "DI";
            case "toán học":
                return "TO";
            case "ngữ văn":
                return "VA";
            case "vật lí":
            case "vật lý":
                return "LI";
            case "hóa học":
                return "HO";
            case "khoa học xã hội và hành vi":
                return "KHAC";
            default:
                return mon.toUpperCase();
        }
    }
    
    public void upsertDiemCC(Map<String, BigDecimal> ccMap) {

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();

            int batchSize = 50;
            int i = 0;

            // 🔥 LOAD ALL 1 LẦN (KHÔNG query từng CCCD)
            List<DiemCongDTO> all = session.createQuery("FROM DiemCongDTO", DiemCongDTO.class).list();

            Map<String, List<DiemCongDTO>> mapDB = new HashMap<>();

            for (DiemCongDTO dc : all) {
                mapDB.computeIfAbsent(dc.getTs_cccd(), k -> new ArrayList<>()).add(dc);
            }

            for (Map.Entry<String, BigDecimal> e : ccMap.entrySet()) {

                String cccd = e.getKey().trim();
                BigDecimal diem = e.getValue();

                List<DiemCongDTO> list = mapDB.get(cccd);

                if (list == null || list.isEmpty()) {

                    DiemCongDTO dc = new DiemCongDTO();
                    dc.setTs_cccd(cccd);
                    dc.setDiemCC(diem);
                    dc.setDiemUtxt(BigDecimal.ZERO);
                    dc.setDiemTong(diem);
                    dc.setPhuongthuc("THPT");
                    dc.setDc_keys(cccd + "_CC");

                    session.save(dc);

                } else {

                    for (DiemCongDTO dc : list) {

                        dc.setDiemCC(diem);

                        BigDecimal utxt = dc.getDiemUtxt() == null
                                ? BigDecimal.ZERO
                                : dc.getDiemUtxt();

                        dc.setDiemTong(diem.add(utxt));

                        session.update(dc);
                    }
                }

                // 🔥 batch flush
                if (i > 0 && i % batchSize == 0) {
                    session.flush();
                    session.clear();
                }

                i++;
            }

            tx.commit();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public Map<String, BigDecimal> importCC(String filePath) {
        Map<String, BigDecimal> map = new HashMap<>();
        try (FileInputStream fis = new FileInputStream(filePath); Workbook wb = new XSSFWorkbook(fis)) {
            Sheet sheet = wb.getSheetAt(0);
            Row header = sheet.getRow(0);
            int cccdCol = getColumnIndex(header, "CCCD");
            int diemCol = getColumnIndex(header, "Điểm cộng");
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) {
                    continue;
                }
                String cccd = getCell(row, cccdCol);
                String diemStr = getCell(row, diemCol);
                if (cccd == null || cccd.trim().isEmpty()) {
                    continue;
                }
                BigDecimal diem = BigDecimal.ZERO;
                try {
                    if (diemStr != null && !diemStr.isEmpty()) {
                        diem = new BigDecimal(diemStr);
                    }
                } catch (Exception e) {
                    diem = BigDecimal.ZERO;
                }
                map.put(cccd, diem);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    public List<DiemCongDTO> importExcel(String filePath, Map<String, BigDecimal> ccMap) {
        List<DiemCongDTO> diemCongList = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(new File(filePath)); Workbook work = new XSSFWorkbook(fis)) {

            Sheet sheet = work.getSheetAt(1);
            Row headerRow = sheet.getRow(0);

            int cccdCol = getColumnIndex(headerRow, "CCCD");
            int tenNganhCol = getColumnIndex(headerRow, "Tên ngành");
            int monDatGiaiCol = getColumnIndex(headerRow, "Môn đạt giải");
            int diemCol = getColumnIndex(headerRow, "Điểm cộng cho môn đạt giải");
            int diemKhongMonCol = getColumnIndex(headerRow, "Điểm cộng cho THXT ko có môn đạt giải");

            // 🔥 CACHE NGÀNH
            Map<String, List<Object[]>> cacheNganh = new HashMap<>();

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) {
                    continue;
                }

                String cccd = getCell(row, cccdCol);
                String tenNganh = getCell(row, tenNganhCol);

                if (cccd == null || cccd.trim().isEmpty() || tenNganh == null) {
                    continue;
                }

                // 🔥 lấy từ cache (KHÔNG query lại DB)
                List<Object[]> list = cacheNganh.get(tenNganh);
                if (list == null) {
                    list = getListMaToHopAndMaNganhByTenNganh(tenNganh);
                    cacheNganh.put(tenNganh, list);
                }

                String monDatGiai = getCell(row, monDatGiaiCol);

                double diem = row.getCell(diemCol) != null ? row.getCell(diemCol).getNumericCellValue() : 0;
                double diemKhongMon = row.getCell(diemKhongMonCol) != null ? row.getCell(diemKhongMonCol).getNumericCellValue() : 0;

                for (Object[] obj : list) {

                    String maToHop = obj[0].toString();
                    String maNganh = obj[1].toString();

                    String mon1 = obj[2] != null ? obj[2].toString() : "";
                    String mon2 = obj[3] != null ? obj[3].toString() : "";
                    String mon3 = obj[4] != null ? obj[4].toString() : "";

                    boolean trungMon = false;

                    if (monDatGiai != null) {
                        String m = normalizeMon(monDatGiai);
                        if (m.equals(mon1) || m.equals(mon2) || m.equals(mon3)) {
                            trungMon = true;
                        }
                    }

                    BigDecimal utxt = trungMon
                            ? BigDecimal.valueOf(diem)
                            : BigDecimal.valueOf(diemKhongMon);

                    BigDecimal diemCC = ccMap.getOrDefault(cccd, BigDecimal.ZERO);

                    DiemCongDTO dc = new DiemCongDTO();
                    dc.setTs_cccd(cccd);
                    dc.setManganh(maNganh);
                    dc.setMatohop(maToHop);
                    dc.setDiemUtxt(utxt);
                    dc.setDiemCC(diemCC);
                    dc.setDiemTong(diemCC.add(utxt));
                    dc.setPhuongthuc("THPT");

                    // 🔥 KEY CHUẨN
                    dc.setDc_keys(cccd + "_" + maNganh + "_" + maToHop);

                    diemCongList.add(dc);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return diemCongList;
    }
}
