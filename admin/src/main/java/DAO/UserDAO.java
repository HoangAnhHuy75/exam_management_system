/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import DTO.UserDTO;
import java.util.ArrayList;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import util.HibernateUtil;

/**
 *
 * @author Hao Nguyen
 */
public class UserDAO {
    // ===== Lấy tất cả =====
        public ArrayList<UserDTO> getAll() {
            try (Session session = HibernateUtil.getSessionFactory().openSession()) {
                Query<UserDTO> query = session.createQuery("FROM UserDTO", UserDTO.class);
                return new ArrayList<>(query.list());
            } catch (Exception e) {
                e.printStackTrace();
                return new ArrayList<>();
            }
        }
    // ===== Tìm theo username =====
    public UserDTO findByUsername(String username) {
    if (username == null || username.trim().isEmpty()) {
        return null;
    }

    try (Session session = HibernateUtil.getSessionFactory().openSession()) {

        String hql = "FROM UserDTO u WHERE u.username = :username";

        Query<UserDTO> query = session.createQuery(hql, UserDTO.class);

        query.setParameter("username", username.trim());

        return query.uniqueResult();

    } catch (Exception e) {
        e.printStackTrace();
        return null;
    }
}
    public int insert(UserDTO u) {
    Transaction transaction = null;
    try (Session session = HibernateUtil.getSessionFactory().openSession()) {
        transaction = session.beginTransaction();

        session.save(u);

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
    
    public int update(UserDTO u) {
    Transaction transaction = null;
    try (Session session = HibernateUtil.getSessionFactory().openSession()) {
        transaction = session.beginTransaction();

        session.update(u);

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
    public int banUser(int userId) {
    Transaction transaction = null;
    try (Session session = HibernateUtil.getSessionFactory().openSession()) {
        transaction = session.beginTransaction();

        UserDTO user = session.get(UserDTO.class, userId);
        if (user != null) {
            user.setEnabled(false);
            session.update(user);
        }

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
    public UserDTO getById(int id) {
    try (Session session = HibernateUtil.getSessionFactory().openSession()) {
        return session.get(UserDTO.class, id);
    }
}
    public int unbanUser(int userId) {
    Transaction transaction = null;
    try (Session session = HibernateUtil.getSessionFactory().openSession()) {
        transaction = session.beginTransaction();

        UserDTO user = session.get(UserDTO.class, userId);
        if (user != null) {
            user.setEnabled(true);
            session.update(user);
        }

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
    public ArrayList<UserDTO> searchByUsername(String keyword) {
    try (Session session = HibernateUtil.getSessionFactory().openSession()) {

        String hql = "FROM UserDTO u WHERE u.username LIKE :kw";

        Query<UserDTO> query = session.createQuery(hql, UserDTO.class);
        query.setParameter("kw", "%" + keyword + "%");

        return new ArrayList<>(query.list());

    } catch (Exception e) {
        e.printStackTrace();
        return new ArrayList<>();
    }
}
    public int delete(int id) {
    Transaction tx = null;

    try (Session session = HibernateUtil.getSessionFactory().openSession()) {

        tx = session.beginTransaction();

        UserDTO user = session.get(UserDTO.class, id);

        if (user != null) {
            session.delete(user);
            tx.commit();
            return 1;
        }

        return 0;

    } catch (Exception e) {
        if (tx != null) tx.rollback();
        e.printStackTrace();
        return 0;
    }
}
    
}
