package DAO;

import DTO.DiemThiDTO;
import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import util.HibernateUtil;

/**
 *
 * @author HP
 */
public class DiemThiDAO {
    public ArrayList<DiemThiDTO> getAllDiem() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<DiemThiDTO> query = session.createQuery("FROM DiemThiDTO", DiemThiDTO.class);
            return new ArrayList<>(query.list());
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
    
    // thêm điểm
    public int insert(DiemThiDTO dt) {
        Transaction tr = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()){
            tr = session.beginTransaction();
            session.save(dt);
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
    public int update(DiemThiDTO dt) {
        Transaction tr = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()){
            tr = session.beginTransaction();
            session.update(dt);
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
    public int delete(DiemThiDTO dt) {
        Transaction tr = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()){
            tr = session.beginTransaction();
            session.delete(dt);
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
    // convert number
    private BigDecimal getDecimal(Cell cell) {
    if (cell == null) return BigDecimal.ZERO;
    try {
        switch (cell.getCellType()) {
            case NUMERIC:
                return BigDecimal.valueOf(cell.getNumericCellValue());
            case STRING:
                String val = cell.getStringCellValue().trim().replace(",", ".");
                if(val.isEmpty()) return BigDecimal.ZERO;
                return new BigDecimal(val);
            default:
                return BigDecimal.ZERO;
        }
    } catch (Exception e) {
        return BigDecimal.ZERO;
        }
    }
    
    // hàm import 
    public List<DiemThiDTO> importFromExcel(String filePath) {
        List<DiemThiDTO> list = new ArrayList<>();
        try (FileInputStream files = new FileInputStream(new File(filePath));
        Workbook wb = new XSSFWorkbook(files)) {
            Sheet sh = wb.getSheetAt(0);
            for (int i = 1; i <= sh.getLastRowNum(); i++) {
                Row row = sh.getRow(i);
                if(row == null) {
                    continue;
                }
                
                DiemThiDTO dt = new DiemThiDTO();
                dt.setCccd(row.getCell(1) != null ? row.getCell(1).toString() : null);
                dt.setD_phuongthuc("PT4");
                dt.setTO(getDecimal(row.getCell(6)));
                dt.setVA(getDecimal(row.getCell(7)));
                dt.setLI(getDecimal(row.getCell(8)));
                dt.setHO(getDecimal(row.getCell(9)));
                dt.setSI(getDecimal(row.getCell(10)));
                dt.setSU(getDecimal(row.getCell(11)));
                dt.setDI(getDecimal(row.getCell(12)));
                dt.setKTPL(getDecimal(row.getCell(16)));
                dt.setTI(getDecimal(row.getCell(17)));
                dt.setCNCN(getDecimal(row.getCell(18)));
                dt.setCNNN(getDecimal(row.getCell(19)));
                dt.setNK1(getDecimal(row.getCell(22)));
                dt.setNK2(getDecimal(row.getCell(23)));
                
                list.add(dt);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    // lưu ds điểm vào dtb
    public void saveAll(List<DiemThiDTO> list) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();

            for (DiemThiDTO dt : list) {
                session.save(dt);
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
