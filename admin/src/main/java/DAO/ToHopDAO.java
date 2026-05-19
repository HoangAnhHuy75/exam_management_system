package DAO;

import DTO.ToHopDTO;
import util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ToHopDAO {

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
    
    public int update(ToHopDTO t) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            session.update(t);

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
    
    // Xóa tổ hợp
    public int delete(ToHopDTO t) {
        Transaction tx = null;
        try (Session se = HibernateUtil.getSessionFactory().openSession()) {
            tx = se.beginTransaction();
            se.delete(t);
            tx.commit();
            return 1;
        } catch (Exception e) {
            if(tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
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
}