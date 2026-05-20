package DAO;

import DTO.NganhDTO;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    
    public int update(NganhDTO n) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.update(n); // update object
            tx.commit();
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            if (tx != null) {
                tx.rollback();
            }
            return 0;
        }
    }
    
    // Xóa ngành
    public int delete(NganhDTO n) {
        Transaction tx = null;
        try (Session se = HibernateUtil.getSessionFactory().openSession()) {
            tx = se.beginTransaction();
            se.delete(n);
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
    
    public int countThiSinhByMaNganh(String maNganh) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            String hql = "SELECT COUNT(DISTINCT nv.nvCccd) "
                    + "FROM NguyenVongDTO nv "
                    + "WHERE nv.nvManganh = :maNganh";

            Query<Long> query = session.createQuery(hql, Long.class);
            query.setParameter("maNganh", maNganh);

            Long count = query.uniqueResult();

            return count != null ? count.intValue() : 0;

        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
    
    public HashMap<String, Integer> countAllThiSinh() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "SELECT nv.nvManganh, COUNT(DISTINCT nv.nvCccd) "
                    + "FROM NguyenVongDTO nv "
                    + "GROUP BY nv.nvManganh";
            Query<Object[]> query = session.createQuery(hql, Object[].class);
            List<Object[]> list = query.list();
            HashMap<String, Integer> map = new HashMap<>();
            for (Object[] row : list) {
                String maNganh = (String) row[0];
                Long count = (Long) row[1];
                map.put(maNganh, count.intValue());
            }
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            return new HashMap<>();
        }
    }
}
