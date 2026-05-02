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
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import util.HibernateUtil;

public class NguyenVongDAO {

    // ================= CRUD =================
    public int insert(NguyenVongDTO nv) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.save(nv);
            tx.commit();
            return 1;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            return 0;
        }
    }
    
    public void insertList(List<NguyenVongDTO> list) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
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

    public int update(NguyenVongDTO nv) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.update(nv);
            tx.commit();
            return 1;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            return 0;
        }
    }

    public int delete(int id) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
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
    public ArrayList<NguyenVongDTO> getAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return new ArrayList<>(
                session.createQuery("FROM NguyenVongDTO", NguyenVongDTO.class).list()
            );
        }
    }

    public List<NguyenVongDTO> getByCccdOrderByNV(String cccd) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<NguyenVongDTO> query = session.createQuery(
                "FROM NguyenVongDTO WHERE nvCccd = :cccd ORDER BY nvTt ASC",
                NguyenVongDTO.class
            );
            query.setParameter("cccd", cccd);
            return query.list();
        }
    }

    public void deleteByCccd(String cccd) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
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

    public List<NguyenVongDTO> importFromExcel(String filePath) {
        List<NguyenVongDTO> list = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(new File(filePath)); Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0);

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) {
                    continue;
                }

                NguyenVongDTO nv = new NguyenVongDTO();

                nv.setNvCccd(getString(row.getCell(1)));
                nv.setNvTt(getInt(row.getCell(2)));
                nv.setNvManganh(getString(row.getCell(5)));

                // các cột điểm (nếu có)
                nv.setDiemThxt(getDecimal(row.getCell(6)));
                nv.setDiemUtqd(getDecimal(row.getCell(7)));
                nv.setDiemCong(getDecimal(row.getCell(8)));
                nv.setDiemXettuyen(getDecimal(row.getCell(9)));

                list.add(nv);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
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