package DAO;

import DTO.NganhDTO;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.File;
import java.io.FileInputStream;
import util.HibernateUtil;

public class NganhDAO {

    // Thêm 1 ngành
    public int insert(NganhDTO n) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.save(n);
            tx.commit();
            return 1;
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
            return 0;
        }
    }

    // Lấy tất cả ngành
    public ArrayList<NganhDTO> getAllNganh() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<NganhDTO> query = session.createQuery("FROM NganhDTO", NganhDTO.class);
            return new ArrayList<>(query.list());
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    // Lưu danh sách ngành vào DB
    public void insertList(List<NganhDTO> list) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            for (NganhDTO n : list) {
                session.save(n);
            }
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        }
    }

    // Import Excel sang List<NganhDTO>
    private BigDecimal getDecimal(Cell cell) {
        if (cell == null) {
            return null;
        }
        try {
            if (cell.getCellType() == CellType.NUMERIC) {
                return BigDecimal.valueOf(cell.getNumericCellValue());
            }
            String val = cell.toString().trim();
            if (val.isEmpty() || val.equalsIgnoreCase("null")) {
                return null;
            }
            return new BigDecimal(val);
        } catch (Exception e) {
            return null;
        }
    }

    public List<NganhDTO> importFromExcel(String filePath) {
        List<NganhDTO> list = new ArrayList<>();
        try (FileInputStream fis = new FileInputStream(new File(filePath)); Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0);
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) {
                    continue;
                }

                NganhDTO n = new NganhDTO();
                n.setMaNganh(row.getCell(1) != null ? row.getCell(1).toString() : null);
                n.setTenNganh(row.getCell(2) != null ? row.getCell(2).toString() : null);
                n.setNToHopGoc(row.getCell(3) != null ? row.getCell(3).toString() : null);
                n.setNChiTieu(row.getCell(4) != null ? (int) row.getCell(4).getNumericCellValue() : 0);
                n.setNDiemSan(getDecimal(row.getCell(5)));
                n.setNDiemTrungTuyen(getDecimal(row.getCell(6)));
                n.setNTuyenThang(row.getCell(7) != null ? row.getCell(7).toString() : null);
                n.setNDGNL(row.getCell(8) != null ? row.getCell(8).toString() : null);
                n.setNTHPT(row.getCell(9) != null ? row.getCell(9).toString() : null);
                n.setNVSAT(row.getCell(10) != null ? row.getCell(10).toString() : null);
                n.setSlXTT(row.getCell(11) != null ? (int) row.getCell(11).getNumericCellValue() : null);
                n.setSlDGNL(row.getCell(12) != null ? (int) row.getCell(12).getNumericCellValue() : null);
                n.setSlVSAT(row.getCell(13) != null ? (int) row.getCell(13).getNumericCellValue() : null);
                n.setSlTHPT(row.getCell(14) != null ? (int) row.getCell(14).getNumericCellValue() : null);

                list.add(n);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

}
