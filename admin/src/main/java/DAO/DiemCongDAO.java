package DAO;

import DTO.DiemCongDTO;
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
public class DiemCongDAO {
    // lấy danh sách xt_diemcong
    public ArrayList<DiemCongDTO> getAllDiemCong() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<DiemCongDTO> query = session.createQuery("FROM DiemCongDTO", DiemCongDTO.class);
            return new ArrayList<>(query.list());
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
    
    // thêm điểm
    public int insert(DiemCongDTO dc) {
        Transaction tr = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()){
            tr = session.beginTransaction();
            session.save(dc);
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
    public int update(DiemCongDTO dc) {
        Transaction tr = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()){
            tr = session.beginTransaction();
            session.update(dc);
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
    public int delete(DiemCongDTO dc) {
        Transaction tr = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()){
            tr = session.beginTransaction();
            session.delete(dc);
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
    
    // lấy mã ngành
    public List<String> getAllMaNganh() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<String> query = session.createQuery(
                    "SELECT n.maNganh FROM NganhDTO n", String.class
            );
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
    
    // lấy mã tổ hợp
    public List<String> getAllMaToHop() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<String> query = session.createQuery(
                    "SELECT matohop FROM ToHopDTO", String.class
            );
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
