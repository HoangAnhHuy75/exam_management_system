package DAO;

import DTO.ToHopNganhDTO;
import util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ToHopNganhDAO {

    // Lấy tất cả
    public ArrayList<ToHopNganhDTO> getAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<ToHopNganhDTO> query = session.createQuery("FROM ToHop_Nganh_DTO", ToHopNganhDTO.class);
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

    // Map manganh_matohop -> DTO (hoặc bạn có thể đổi tùy mục đích)
    public HashMap<String, ToHopNganhDTO> toHopNganhMap() {
        HashMap<String, ToHopNganhDTO> map = new HashMap<>();
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<ToHopNganhDTO> query = session.createQuery("FROM ToHop_Nganh_DTO", ToHopNganhDTO.class);
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
                    "FROM ToHop_Nganh_DTO WHERE manganh = :manganh",
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
                    "FROM ToHop_Nganh_DTO WHERE matohop = :matohop",
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
