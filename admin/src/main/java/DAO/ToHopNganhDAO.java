package DAO;

import DTO.ToHopNganhDTO;
import java.io.File;
import java.io.FileInputStream;
import util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import java.math.BigDecimal; 
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ToHopNganhDAO {

    // Lấy tất cả
    public ArrayList<ToHopNganhDTO> getAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<ToHopNganhDTO> query = session.createQuery("FROM ToHopNganhDTO", ToHopNganhDTO.class);
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
    
    public int update(ToHopNganhDTO t) {
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
    
    // Xóa ngành
    public int delete(ToHopNganhDTO t) {
        Transaction tx = null;
        try (Session se = HibernateUtil.getSessionFactory().openSession()) {
            tx = se.beginTransaction();
            se.delete(t);
            tx.commit();
            return 1;
        } catch (Exception e) {
            if(tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
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
    
    
    public int getColumnIndex(Row headerRow, String columnName) {
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
                double num = cell.getNumericCellValue();

                // tránh 1.0 -> 1
                if (num == (long) num) {
                    return String.valueOf((long) num);
                } else {
                    return String.valueOf(num);
                }

            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());

            default:
                return cell.toString().trim();
        }
    }

    public Object[] parseMonAndHeSo(String matohop) {

        if (matohop == null || !matohop.contains("(")) {
            return new Object[]{
                new String[]{"", "", ""},
                new Integer[]{0, 0, 0}
            };
        }

        try {
            String inside = matohop.substring(
                    matohop.indexOf("(") + 1,
                    matohop.indexOf(")")
            );

            String[] parts = inside.split(",");

            String[] mons = new String[3];
            Integer[] hs = new Integer[3];

            for (int i = 0; i < 3; i++) {

                if (i < parts.length) {
                    String[] p = parts[i].split("-");

                    mons[i] = p.length > 0 ? p[0] : "";

                    try {
                        hs[i] = p.length > 1 ? Integer.parseInt(p[1]) : 0;
                    } catch (Exception e) {
                        hs[i] = 0;
                    }

                } else {
                    mons[i] = "";
                    hs[i] = 0;
                }
            }

            return new Object[]{mons, hs};

        } catch (Exception e) {
            return new Object[]{
                new String[]{"", "", ""},
                new Integer[]{0, 0, 0}
            };
        }
    }
    
    
    public List<ToHopNganhDTO> importFromExcel(String filePath) {

        List<ToHopNganhDTO> list = new ArrayList<>();

        try {
            FileInputStream fis = new FileInputStream(new File(filePath));
            Workbook workbook = new XSSFWorkbook(fis);
            Sheet sheet = workbook.getSheetAt(0);
            Row headerRow = sheet.getRow(0);

            int maNganhCol = getColumnIndex(headerRow, "MANGANH");
            int maToHopCol = getColumnIndex(headerRow, "MA_TO_HOP");
            int dolechCol = getColumnIndex(headerRow, "Độ lệch");

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {

                Row row = sheet.getRow(i);
                if (row == null) {
                    continue;
                }

                String maNganh = getCell(row, maNganhCol);
                String maToHop = getCell(row, maToHopCol);

                if (maNganh == null || maToHop == null) {
                    continue;
                }

                ToHopNganhDTO dto = new ToHopNganhDTO();

                // ===== ONLY DB FIELDS =====
                dto.setManganh(maNganh);
                dto.setMatohop(maToHop);
                // tạo tb_keys từ 2 field
                String tbKeys = maNganh + "_" + maToHop;
                dto.setTb_keys(tbKeys);

                // ===== PARSE MÔN + HỆ SỐ =====
                Object[] result = parseMonAndHeSo(maToHop);

                String[] mons = (String[]) result[0];
                Integer[] hs = (Integer[]) result[1];

                dto.setTh_mon1(mons[0]);
                dto.setHsmon1(hs[0]);

                dto.setTh_mon2(mons[1]);
                dto.setHsmon2(hs[1]);

                dto.setTh_mon3(mons[2]);
                dto.setHsmon3(hs[2]);

                // ===== ĐỘ LỆCH =====
                String dl = getCell(row, dolechCol);
                if (dl != null && !dl.isEmpty()) {
                    try {
                        dto.setDolech(new BigDecimal(dl));
                    } catch (Exception e) {
                        dto.setDolech(BigDecimal.ZERO);
                    }
                }

                list.add(dto);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
    
    
    
   
    
    

    // Map manganh_matohop -> DTO (hoặc bạn có thể đổi tùy mục đích)
    public HashMap<String, ToHopNganhDTO> toHopNganhMap() {
        HashMap<String, ToHopNganhDTO> map = new HashMap<>();
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<ToHopNganhDTO> query = session.createQuery("FROM ToHopNganhDTO", ToHopNganhDTO.class);
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
                    "FROM ToHopNganhDTO WHERE manganh = :manganh",
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
                    "FROM ToHopNganhDTO WHERE matohop = :matohop",
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
