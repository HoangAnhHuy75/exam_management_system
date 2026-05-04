package DAO;

import DTO.DiemThiDTO;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import util.HibernateUtil;

/**
 *
 * @author HP
 */
public class DiemThiDAO {
    public ArrayList<DiemThiDTO> getAllDiem() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<DiemThiDTO> query = session.createQuery("FROM DiemThiDTO", DiemThiDTO.class);
            return new ArrayList<>(query.list());
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
    
    // thêm điểm
    public int insert(DiemThiDTO dt) {
        Transaction tr = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()){
            tr = session.beginTransaction();
            session.save(dt);
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
    
    // sửa điểm
    public int update(DiemThiDTO dt) {
        Transaction tr = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()){
            tr = session.beginTransaction();
            session.update(dt);
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
    public int delete(DiemThiDTO dt) {
        Transaction tr = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()){
            tr = session.beginTransaction();
            session.delete(dt);
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
    
    // lưu ds điểm vào dtb
    public void saveAll(List<DiemThiDTO> list) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            int batchSize = 50; // có thể chỉnh 30–100
            for (int i = 0; i < list.size(); i++) {
                session.save(list.get(i));
                if (i > 0 && i % batchSize == 0) {
                    session.flush();
                    session.clear();
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
    
    public List<String> getAllCCCD() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<String> query = session.createQuery(
                    "SELECT cccd FROM DiemThiDTO", String.class
            );
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
