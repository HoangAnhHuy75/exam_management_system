package DAO;

import DTO.BangQuyDoiDTO;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import util.HibernateUtil;

public class BangQuyDoiDAO {

    // ================= INSERT =================
    public int insert(BangQuyDoiDTO qd) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.save(qd);
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

    // ================= UPDATE =================
    public int update(BangQuyDoiDTO qd) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.update(qd);
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

    // ================= DELETE =================
    public int delete(BangQuyDoiDTO qd) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.delete(qd);
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

    // ================= GET ALL =================
    public ArrayList<BangQuyDoiDTO> getAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<BangQuyDoiDTO> query =
                    session.createQuery(
                            "FROM BangQuyDoiDTO",
                            BangQuyDoiDTO.class
                    );
            return new ArrayList<>(query.list());
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    // ================= INSERT LIST =================
    public void insertList(List<BangQuyDoiDTO> list) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            for (BangQuyDoiDTO qd : list) {
                session.save(qd);
            }
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        }
    }
}