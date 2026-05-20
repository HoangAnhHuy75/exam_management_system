    package DAO;

import DTO.ThiSinhDTO;
import util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import java.util.ArrayList;
import java.util.List;

public class ThiSinhDAO {

    // ===== Lấy tất cả =====
    public ArrayList<ThiSinhDTO> getAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<ThiSinhDTO> query = session.createQuery("FROM ThiSinhDTO", ThiSinhDTO.class);
            return new ArrayList<>(query.list());
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    // ===== Thêm 1 =====
    public int insert(ThiSinhDTO t) {
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

    // ===== Thêm list =====
    public void insertList(List<ThiSinhDTO> list) {
        Transaction tx = null;
        int batchSize = 50;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();

            for (int i = 0; i < list.size(); i++) {
                session.save(list.get(i));

                if (i % batchSize == 0) {
                    session.flush();  // đẩy xuống DB
                    session.clear();  // clear cache
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

    // ===== Update =====
    public int update(ThiSinhDTO t) {
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

    // ===== Delete =====
    public int delete(ThiSinhDTO t) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.delete(t);
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

    // ===== Tìm theo CCCD =====
    public ThiSinhDTO findByCCCD(String cccd) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<ThiSinhDTO> query = session.createQuery(
                    "FROM ThiSinhDTO WHERE cccd = :cccd",
                    ThiSinhDTO.class
            );
            query.setParameter("cccd", cccd);
            return query.uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<String> getAllCCCD() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<String> query = session.createQuery(
                    "SELECT cccd FROM ThiSinhDTO", String.class
            );
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
    
    // dùng để hiển thị khi chọn cccd (thành)
    public List<String> getSBDByCccd(String cccd) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
        Query<String> query = session.createQuery(
            "SELECT t.sobaodanh FROM ThiSinhDTO t WHERE t.cccd = :cccd",
            String.class
        );

        query.setParameter("cccd", cccd);

        return query.getResultList();

        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
    
    public List<ThiSinhDTO> getThiSinhByMaNganh(String maNganh) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<ThiSinhDTO> query = session.createQuery(
                    "SELECT ts "
                    + "FROM ThiSinhDTO ts "
                    + "JOIN NguyenVongDTO nv "
                    + "ON ts.cccd = nv.nvCccd "
                    + "WHERE nv.nvManganh = :maNganh",
                    ThiSinhDTO.class
            );
            query.setParameter("maNganh", maNganh);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
