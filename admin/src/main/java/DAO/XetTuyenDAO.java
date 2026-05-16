package DAO;

import DTO.XetTuyenDTO;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import java.util.ArrayList;
import java.util.List;
import util.HibernateUtil;

public class XetTuyenDAO {

    // Thêm 1 kết quả xét tuyển
    public int insert(XetTuyenDTO xt) {

        Transaction tx = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            tx = session.beginTransaction();

            session.save(xt);

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

    // Cập nhật
    public int update(XetTuyenDTO xt) {

        Transaction tx = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            tx = session.beginTransaction();

            session.update(xt);

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

    // Xóa
    public int delete(XetTuyenDTO xt) {

        Transaction tx = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            tx = session.beginTransaction();

            session.delete(xt);

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

    // Lấy tất cả danh sách xét tuyển
    public List<XetTuyenDTO> getAllXetTuyen() {

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            Query<XetTuyenDTO> query
                    = session.createQuery(
                            "FROM XetTuyenDTO",
                            XetTuyenDTO.class
                    );

            return query.list();

        } catch (Exception e) {

            e.printStackTrace();

            return new ArrayList<>();
        }
    }

    // Insert danh sách
    // Insert danh sách
public void insertList(List<XetTuyenDTO> list) {

    Transaction tx = null;

    try (Session session = HibernateUtil.getSessionFactory().openSession()) {

        tx = session.beginTransaction();

        for (int i = 0; i < list.size(); i++) {

            session.save(list.get(i));

            // batch mỗi 50 dòng
            if (i % 50 == 0) {

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

    // Xóa toàn bộ dữ liệu
    public int deleteAll() {

        Transaction tx = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            tx = session.beginTransaction();

            Query query = session.createQuery("DELETE FROM XetTuyenDTO");

            query.executeUpdate();

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
}