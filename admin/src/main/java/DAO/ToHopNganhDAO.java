package DAO;

import DTO.ToHopNganhDTO;
import java.io.File;
import java.io.FileInputStream;
import util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import java.math.BigDecimal; 
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ToHopNganhDAO {

    // Lấy tất cả
    public ArrayList<ToHopNganhDTO> getAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<ToHopNganhDTO> query = session.createQuery("FROM ToHopNganhDTO", ToHopNganhDTO.class);
            return new ArrayList<>(query.list());
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    // Thêm 1
    public int insert(ToHopNganhDTO t) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(t);
            transaction.commit();
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
            return 0;
        }
    }

    // Thêm list
    public void insertList(List<ToHopNganhDTO> list) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            for (ToHopNganhDTO t : list) {
                session.save(t);
            }
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }
    
    public List<ToHopNganhDTO> importFromExcel(String filePath) {
        List<ToHopNganhDTO> list = new ArrayList<>();
        try (FileInputStream fis = new FileInputStream(new File(filePath)); Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0);
            for (int i = 1; i <= sheet.getLastRowNum(); i++) { // giả sử dòng 0 là header
                Row row = sheet.getRow(i);
                if (row == null) {
                    continue;
                }

                ToHopNganhDTO t = new ToHopNganhDTO();

                t.setManganh(row.getCell(0) != null ? row.getCell(0).toString().trim() : "");
                t.setMatohop(row.getCell(1) != null ? row.getCell(1).toString().trim() : "");

                t.setTh_mon1(row.getCell(2) != null ? row.getCell(2).toString().trim() : "");
                t.setHsmon1(row.getCell(3) != null ? (int) row.getCell(3).getNumericCellValue() : null);

                t.setTh_mon2(row.getCell(4) != null ? row.getCell(4).toString().trim() : "");
                t.setHsmon2(row.getCell(5) != null ? (int) row.getCell(5).getNumericCellValue() : null);

                t.setTh_mon3(row.getCell(6) != null ? row.getCell(6).toString().trim() : "");
                t.setHsmon3(row.getCell(7) != null ? (int) row.getCell(7).getNumericCellValue() : null);

                t.setTb_keys(row.getCell(8) != null ? row.getCell(8).toString().trim() : "");

                t.setN1(row.getCell(9) != null ? (int) row.getCell(9).getNumericCellValue() : null);
                t.setTO(row.getCell(10) != null ? (int) row.getCell(10).getNumericCellValue() : null);
                t.setLI(row.getCell(11) != null ? (int) row.getCell(11).getNumericCellValue() : null);
                t.setHO(row.getCell(12) != null ? (int) row.getCell(12).getNumericCellValue() : null);
                t.setSI(row.getCell(13) != null ? (int) row.getCell(13).getNumericCellValue() : null);
                t.setVA(row.getCell(14) != null ? (int) row.getCell(14).getNumericCellValue() : null);
                t.setSU(row.getCell(15) != null ? (int) row.getCell(15).getNumericCellValue() : null);
                t.setDI(row.getCell(16) != null ? (int) row.getCell(16).getNumericCellValue() : null);
                t.setTI(row.getCell(17) != null ? (int) row.getCell(17).getNumericCellValue() : null);
                t.setKHAC(row.getCell(18) != null ? (int) row.getCell(18).getNumericCellValue() : null);
                t.setKTPL(row.getCell(19) != null ? (int) row.getCell(19).getNumericCellValue() : null);
                t.setDolech(row.getCell(20) != null ? BigDecimal.valueOf(row.getCell(20).getNumericCellValue()) : null);
                list.add(t);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // Map manganh_matohop -> DTO (hoặc bạn có thể đổi tùy mục đích)
    public HashMap<String, ToHopNganhDTO> toHopNganhMap() {
        HashMap<String, ToHopNganhDTO> map = new HashMap<>();
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<ToHopNganhDTO> query = session.createQuery("FROM ToHopNganhDTO", ToHopNganhDTO.class);
            for (ToHopNganhDTO t : query.list()) {
                String key = t.getManganh() + "_" + t.getMatohop();
                map.put(key, t);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    // Tìm theo mã ngành
    public List<ToHopNganhDTO> findByMaNganh(String maNganh) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<ToHopNganhDTO> query = session.createQuery(
                    "FROM ToHopNganhDTO WHERE manganh = :manganh",
                    ToHopNganhDTO.class
            );
            query.setParameter("manganh", maNganh);
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    // Tìm theo mã tổ hợp
    public List<ToHopNganhDTO> findByMaToHop(String maToHop) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<ToHopNganhDTO> query = session.createQuery(
                    "FROM ToHopNganhDTO WHERE matohop = :matohop",
                    ToHopNganhDTO.class
            );
            query.setParameter("matohop", maToHop);
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
