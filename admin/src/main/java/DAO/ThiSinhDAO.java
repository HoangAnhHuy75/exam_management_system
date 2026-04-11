    package DAO;

import DTO.ThiSinhDTO;
import util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import java.util.Date;
import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

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

    private int getColumnIndex(Row headerRow, String columnName) {
        for (Cell cell : headerRow) {
            if (cell.getStringCellValue().trim().equalsIgnoreCase(columnName)) {
                return cell.getColumnIndex();
            }
        }
        return -1;
    }

    private String getCell(Row row, int index) {
        if (index == -1) {
            return null;
        }

        Cell cell = row.getCell(index);
        if (cell == null) {
            return null;
        }

        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue().trim();
            case NUMERIC:
                return String.valueOf((long) cell.getNumericCellValue());
            default:
                return cell.toString().trim();
        }
    }
    
    private Date getDateCell(Row row, int index) {
        if (index == -1) {
            return null;
        }

        Cell cell = row.getCell(index);
        if (cell == null) {
            return null;
        }

        try {
            // Excel chuẩn (date dạng numeric)
            if (cell.getCellType() == CellType.NUMERIC) {
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue();
                }
            }

            // Nếu là string (user nhập tay)
            if (cell.getCellType() == CellType.STRING) {
                String value = cell.getStringCellValue().trim();

                if (value.isEmpty()) {
                    return null;
                }

                // thử parse nhiều format
                String[] formats = {
                    "dd/MM/yyyy",
                    "yyyy-MM-dd",
                    "MM/dd/yyyy"
                };

                for (String f : formats) {
                    try {
                        return new SimpleDateFormat(f).parse(value);
                    } catch (Exception ignored) {
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
    // ===== Import Excel =====

    public List<ThiSinhDTO> importFromExcel(String filePath) {
        List<ThiSinhDTO> list = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(new File(filePath)); Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0);
            Row header = sheet.getRow(0);

            // map cột
            int cccdCol = getColumnIndex(header, "CCCD");
            int sbdCol = getColumnIndex(header, "sobaodanh");
            int tenCol = getColumnIndex(header, "Họ Tên");
            int nsCol = getColumnIndex(header, "Ngày sinh");
            int gtCol = getColumnIndex(header, "Giới tính");
            int sdtCol = getColumnIndex(header, "dien_thoai");
            int emailCol = getColumnIndex(header, "email");
            int noiSinhCol = getColumnIndex(header, "Nơi sinh");
            int doiTuongCol = getColumnIndex(header, "ĐTƯT");
            int khuVucCol = getColumnIndex(header, "KVƯT");

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) {
                    continue;
                }

                ThiSinhDTO t = new ThiSinhDTO();

                t.setCccd(getCell(row, cccdCol));
                t.setSobaodanh(getCell(row, sbdCol));
                t.setTen(getCell(row, tenCol));
                t.setNgaySinh(getDateCell(row, nsCol));
                t.setGioiTinh(getCell(row, gtCol));
                t.setDienThoai(getCell(row, sdtCol));
                t.setEmail(getCell(row, emailCol));
                t.setNoiSinh(getCell(row, noiSinhCol));
                t.setDoiTuong(getCell(row, doiTuongCol));
                t.setKhuVuc(getCell(row, khuVucCol));
                list.add(t);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
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
}
