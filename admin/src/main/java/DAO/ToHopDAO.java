package DAO;

import DTO.ToHopDTO;
import util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ToHopDAO {

    // Lấy tất cả tổ hợp
    public ArrayList<ToHopDTO> getAllToHop() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<ToHopDTO> query = session.createQuery("FROM ToHopDTO", ToHopDTO.class);
            return new ArrayList<>(query.list());
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    // Thêm 1 tổ hợp
    public int insert(ToHopDTO t) {
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

    // Thêm danh sách tổ hợp
    public void insertList(List<ToHopDTO> list) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            for (ToHopDTO t : list) {
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

    // Import Excel -> list ToHopDTO
    public List<ToHopDTO> importFromExcel(String filePath) {
        List<ToHopDTO> list = new ArrayList<>();
        try (FileInputStream fis = new FileInputStream(new File(filePath));
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0);
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;

                ToHopDTO t = new ToHopDTO();
                t.setMatohop(row.getCell(0) != null ? row.getCell(0).toString() : "");
                t.setMon1(row.getCell(1) != null ? row.getCell(1).toString() : "");
                t.setMon2(row.getCell(2) != null ? row.getCell(2).toString() : "");
                t.setMon3(row.getCell(3) != null ? row.getCell(3).toString() : "");
                t.setTentohop(row.getCell(4) != null ? row.getCell(4).toString() : "");

                list.add(t);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // Lấy map matohop -> tentohop
    public HashMap<String, String> tohopMap() {
        HashMap<String, String> map = new HashMap<>();
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<ToHopDTO> query = session.createQuery("FROM ToHopDTO", ToHopDTO.class);
            for (ToHopDTO t : query.list()) {
                map.put(t.getMatohop(), t.getTentohop());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }
}