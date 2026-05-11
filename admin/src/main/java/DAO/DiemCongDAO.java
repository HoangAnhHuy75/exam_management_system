package DAO;

import DTO.DiemCongDTO;
import java.util.ArrayList;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tr = session.beginTransaction();
            session.save(dc);
            tr.commit();
            return 1;
        } catch (Exception e) {
            if (tr != null) {
                tr.rollback();
            }
            e.printStackTrace();
            return 0;
        }
    }

    // sửa điểm
    public int update(DiemCongDTO dc) {
        Transaction tr = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tr = session.beginTransaction();
            session.update(dc);
            tr.commit();
            return 1;
        } catch (Exception e) {
            if (tr != null) {
                tr.rollback();
            }
            e.printStackTrace();
            return 0;
        }
    }

    // xóa điểm
    public int delete(DiemCongDTO dc) {
        Transaction tr = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tr = session.beginTransaction();
            session.delete(dc);
            tr.commit();
            return 1;
        } catch (Exception e) {
            if (tr != null) {
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

    public void upsertDiemCC(List<DiemCongDTO> listCC) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            int batchSize = 50;
            int count = 0;
            List<DiemCongDTO> all = session.createQuery("FROM DiemCongDTO", DiemCongDTO.class).list();
            Map<String, List<DiemCongDTO>> mapDB = new HashMap<>();
            for (DiemCongDTO dc : all) {
                mapDB.computeIfAbsent(dc.getTs_cccd(), k -> new ArrayList<>()).add(dc);
            }
            for (DiemCongDTO input : listCC) {
                String cccd = input.getTs_cccd().trim();
                BigDecimal diem = input.getDiemCC();
                List<DiemCongDTO> list = mapDB.get(cccd);
                if (list == null || list.isEmpty()) {
                    DiemCongDTO dc = new DiemCongDTO();
                    dc.setTs_cccd(cccd);
                    dc.setDiemCC(diem);
                    dc.setDiemUtxt(BigDecimal.ZERO);
                    dc.setDiemTong(diem);
                    dc.setPhuongthuc("THPT");
                    dc.setDc_keys(cccd + "_CC");
                    session.save(dc);
                } else {
                    for (DiemCongDTO dc : list) {
                        BigDecimal utxt = dc.getDiemUtxt();
                        if (utxt == null) {
                            utxt = BigDecimal.ZERO;
                        }
                        dc.setDiemCC(diem);
                        dc.setDiemTong(diem.add(utxt));
                        session.update(dc);
                    }
                }
                count++;
                if (count % batchSize == 0) {
                    session.flush();
                    session.clear();
                }
            }
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void insertList(List<DiemCongDTO> list) {
        Transaction tx = null;
        int batchSize = 50;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            for (int i = 0; i < list.size(); i++) {
                session.save(list.get(i));
                if (i > 0 && i % batchSize == 0) {
                    session.flush();   // đẩy xuống DB
                    session.clear();   // giải phóng cache
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
    
    public List<Object[]> buildDiemCongFromExcel(String tenNganh) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String sql = "SELECT thn.matohop, thn.manganh, thn.th_mon1, thn.th_mon2, thn.th_mon3 "
                    + "FROM xt_tohop_nganh thn "
                    + "INNER JOIN xt_nganh nganh "
                    + "ON thn.manganh = nganh.manganh "
                    + "WHERE nganh.tennganh = :tennganh";
            Query<Object[]> query = session.createNativeQuery(sql);
            query.setParameter("tennganh", tenNganh);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
