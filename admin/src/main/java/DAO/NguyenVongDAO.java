package DAO;

import DTO.NguyenVongDTO;
import DTO.DiemThiDTO;
import java.io.File;
import java.io.FileInputStream;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.apache.poi.ss.usermodel.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.apache.poi.ss.usermodel.CellType;
import static org.apache.poi.ss.usermodel.CellType.NUMERIC;
import static org.apache.poi.ss.usermodel.CellType.STRING;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import util.HibernateUtil;

public class NguyenVongDAO {

    // ================= CRUD =================
    public int insert(NguyenVongDTO nv) {
        Transaction tx = null;
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            session.save(nv);
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

    public int update(NguyenVongDTO nv) {
        Transaction tx = null;
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            session.update(nv);
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

    public int delete(int id) {
        Transaction tx = null;
        try{
            Session session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            NguyenVongDTO nv = session.get(NguyenVongDTO.class, id);
            if (nv != null) session.delete(nv);
            tx.commit();
            return 1;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            return 0;
        }
    }

    // ================= GET =================
    public List<NguyenVongDTO> getAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM NguyenVongDTO", NguyenVongDTO.class)
                    .getResultList();
        }
    }

    public List<NguyenVongDTO> getByCccdOrderByNV(String cccd) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Query<NguyenVongDTO> query = session.createQuery(
                "FROM NguyenVongDTO WHERE nvCccd = :cccd ORDER BY nvTt ASC",
                NguyenVongDTO.class
        );
        query.setParameter("cccd", cccd);
        return query.list();
    }

    public void deleteByCccd(String cccd) {
        Transaction tx = null;
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            Query q = session.createQuery("DELETE FROM NguyenVongDTO WHERE nvCccd = :cccd");
            q.setParameter("cccd", cccd);
            q.executeUpdate();
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        }
    }
    
    public List<String> getListMaNganh(){
        List<String> result = new ArrayList<>();
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            String hql = "SELECT nv_manganh FROM NguyenVongDTO";
            Query<String> query = session.createQuery(hql, String.class);
            result = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    
    public List<Object[]> getToHopNganhByMaNganh(String maNganh) {
        List<Object[]> result = new ArrayList<>();
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            String hql = "SELECT thn.matohop, thn.th_mon1, thn.th_mon2, thn.th_mon3, "
                    + "thn.hsmon1, thn.hsmon2, thn.hsmon3, thn.dolech "
                    + "FROM ToHopNganhDTO thn "
                    + "WHERE thn.manganh = :maNganh";
            Query<Object[]> query = session.createQuery(hql, Object[].class);
            query.setParameter("maNganh", maNganh);
            result = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    
    public void updateBatch(List<NguyenVongDTO> list) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            int batchSize = 50;
            for (int i = 0; i < list.size(); i++) {
                session.update(list.get(i));
                if (i % batchSize == 0) {
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

    public void insertList(List<NguyenVongDTO> list) {
        Transaction tx = null;
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            for (NguyenVongDTO nv : list) {
                session.save(nv);
            }
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        }
    }

    
    
    
    

    // ===== HELPER =====
    private String getString(Cell cell) {
        if (cell == null) {
            return null;
        }
        return cell.toString().trim();
    }

    private int getInt(Cell cell) {
        if (cell == null) {
            return 0;
        }
        try {
            return (int) cell.getNumericCellValue();
        } catch (Exception e) {
            return 0;
        }
    }

    private BigDecimal getDecimal(Cell cell) {
        if (cell == null) {
            return null;
        }
        try {
            if (cell.getCellType() == CellType.NUMERIC) {
                return BigDecimal.valueOf(cell.getNumericCellValue());
            }
            String val = cell.toString().trim();
            if (val.isEmpty()) {
                return null;
            }
            return new BigDecimal(val);
        } catch (Exception e) {
            return null;
        }
    }

    // ================= TÍNH ĐIỂM =================
    public BigDecimal tinhDiemTHPT(DiemThiDTO d, String toHop) {
        if (d == null || toHop == null) return BigDecimal.ZERO;

        try {
            switch (toHop) {
                case "A00":
                    return d.getTO().add(d.getLI()).add(d.getHO());
                case "A01":
                    return d.getTO().add(d.getLI()).add(d.getN1_THI());
                case "D01":
                    return d.getTO().add(d.getVA()).add(d.getN1_THI());
                case "C00":
                    return d.getVA().add(d.getSU()).add(d.getDI());
                default:
                    return BigDecimal.ZERO;
            }
        } catch (Exception e) {
            return BigDecimal.ZERO;
        }
    }

    // ================= TÍNH ĐIỂM XÉT TUYỂN =================
    public BigDecimal tinhDiemXetTuyen(NguyenVongDTO nv) {
        BigDecimal tong = BigDecimal.ZERO;

        if (nv.getDiemThxt() != null)
            tong = tong.add(nv.getDiemThxt());

        if (nv.getDiemUtqd() != null)
            tong = tong.add(nv.getDiemUtqd());

        if (nv.getDiemCong() != null)
            tong = tong.add(nv.getDiemCong());

        return tong;
    }

    // ================= XÉT ĐẬU / RỚT =================
    public void xetKetQua(String manganh, BigDecimal diemChuan) {
        Transaction tx = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();

            Query<NguyenVongDTO> query = session.createQuery(
                "FROM NguyenVongDTO WHERE nvManganh = :manganh ORDER BY nvTt ASC",
                NguyenVongDTO.class
            );
            query.setParameter("manganh", manganh);

            List<NguyenVongDTO> list = query.list();

            for (NguyenVongDTO nv : list) {
                BigDecimal diem = tinhDiemXetTuyen(nv);
                nv.setDiemXettuyen(diem);

                if (diem.compareTo(diemChuan) >= 0) {
                    nv.setNvKetqua("Đậu");
                } else {
                    nv.setNvKetqua("Rớt");
                }

                session.update(nv);
            }

            tx.commit();

        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }

    // ================= XÉT TUYỂN THEO NV (QUAN TRỌNG) =================
    public void xetTheoThuTuNguyenVong(String cccd) {
        Transaction tx = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();

            List<NguyenVongDTO> list = getByCccdOrderByNV(cccd);

            boolean daDau = false;

            for (NguyenVongDTO nv : list) {

                if (daDau) {
                    nv.setNvKetqua("Rớt");
                } else {
                    if ("Đậu".equals(nv.getNvKetqua())) {
                        daDau = true;
                    }
                }

                session.update(nv);
            }

            tx.commit();

        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }

}