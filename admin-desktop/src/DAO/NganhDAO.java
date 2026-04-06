package DAO;

import DTO.NganhDTO;
import config.ConnectedDatabase;
import java.io.File;
import java.io.FileInputStream;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;
import java.util.HashMap;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class NganhDAO {

    public int insert(NganhDTO n) {
        String sql = "INSERT INTO xt_nganh (manganh, tennganh, n_tohopgoc, n_chitieu, "
                + "n_diemsan, n_diemtrungtuyen, n_tuyenthang, n_dgnl, n_thpt, n_vsat, "
                + "sl_xtt, sl_dgnl, sl_vsat, sl_thpt) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = ConnectedDatabase.getConnectedDB().prepareStatement(sql);
            ps.setString(1, n.getManganh());
            ps.setString(2, n.getTennganh());
            ps.setString(3, n.getN_tohopgoc());
            ps.setInt(4, n.getN_chitieu());
            ps.setBigDecimal(5, n.getN_diemsan());
            ps.setBigDecimal(6, n.getN_diemtrungtuyen());
            ps.setString(7, n.getN_tuyenthang());
            ps.setString(8, n.getN_dgnl());
            ps.setString(9, n.getN_thpt());
            ps.setString(10, n.getN_vsat());
            ps.setInt(11, n.getSl_xtt());
            ps.setInt(12, n.getSl_dgnl());
            ps.setInt(13, n.getSl_vsat());
            ps.setInt(14, n.getSl_thpt());
            return ps.executeUpdate() ;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    private java.math.BigDecimal getDecimal(Cell cell) {
        if (cell == null) {
            return null;
        }
        try {
            if (cell.getCellType() == CellType.BLANK) {
                return null;
            }
            if (cell.getCellType() == CellType.NUMERIC) {
                return java.math.BigDecimal.valueOf(cell.getNumericCellValue());
            }
            String value = cell.toString().trim();
            if (value.isEmpty() || value.equalsIgnoreCase("null")) {
                return null;
            }
            return new java.math.BigDecimal(value);
        } catch (Exception e) {
            return null;
        }
    }
    
    public List<NganhDTO> importNganh(String filePath) {
        List<NganhDTO> list = new ArrayList<>();

        try {
            FileInputStream fis = new FileInputStream(new File(filePath));
            Workbook workbook = new XSSFWorkbook(fis);
            Sheet sheet = workbook.getSheetAt(0);

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) {
                    continue;
                }
                NganhDTO n = new NganhDTO();
                if (row.getCell(1) != null) {
                    n.setManganh(row.getCell(1).toString());
                }
                if (row.getCell(2) != null) {
                    n.setTennganh(row.getCell(2).toString());
                }
                if (row.getCell(3) != null) {
                    n.setN_tohopgoc(row.getCell(3).toString());
                }
                if (row.getCell(4) != null) {
                    n.setN_chitieu((int) row.getCell(4).getNumericCellValue());
                }
                n.setN_diemsan(getDecimal(row.getCell(5)));
                n.setN_diemtrungtuyen(getDecimal(row.getCell(6)));
                if (row.getCell(7) != null) {
                    n.setN_tuyenthang(row.getCell(7).toString());
                }
                if (row.getCell(8) != null) {
                    n.setN_dgnl(row.getCell(8).toString());
                }
                if (row.getCell(9) != null) {
                    n.setN_thpt(row.getCell(9).toString());
                }
                if (row.getCell(10) != null) {
                    n.setN_vsat(row.getCell(10).toString());
                }
                if (row.getCell(11) != null) {
                    n.setSl_xtt((int) row.getCell(11).getNumericCellValue());
                }
                if (row.getCell(12) != null) {
                    n.setSl_dgnl((int) row.getCell(12).getNumericCellValue());
                }
                if (row.getCell(13) != null) {
                    n.setSl_vsat((int) row.getCell(13).getNumericCellValue());
                }
                if (row.getCell(14) != null) {
                    n.setSl_thpt((int) row.getCell(14).getNumericCellValue());
                }
                list.add(n);
            }
            workbook.close();
            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // Insert DB
    public void insertList(List<NganhDTO> list) {
        String sql = "INSERT INTO xt_nganh (manganh, tennganh, n_tohopgoc, n_chitieu, "
                + "n_diemsan, n_diemtrungtuyen, n_tuyenthang, n_dgnl, n_thpt, n_vsat, "
                + "sl_xtt, sl_dgnl, sl_vsat, sl_thpt) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = ConnectedDatabase.getConnectedDB().prepareStatement(sql);
            for (NganhDTO n : list) {
                ps.setString(1, n.getManganh());
                ps.setString(2, n.getTennganh());
                ps.setString(3, n.getN_tohopgoc());
                ps.setInt(4, n.getN_chitieu());
                ps.setBigDecimal(5, n.getN_diemsan());
                ps.setBigDecimal(6, n.getN_diemtrungtuyen());
                ps.setString(7, n.getN_tuyenthang());
                ps.setString(8, n.getN_dgnl());
                ps.setString(9, n.getN_thpt());
                ps.setString(10, n.getN_vsat());
                ps.setInt(11, n.getSl_xtt());
                ps.setInt(12, n.getSl_dgnl());
                ps.setInt(13, n.getSl_vsat());
                ps.setInt(14, n.getSl_thpt());
                ps.addBatch();
            }
            ps.executeBatch();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public ArrayList<NganhDTO> getAllNganh() {
        ArrayList<NganhDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM xt_nganh";
        try {
            PreparedStatement ps = ConnectedDatabase.getConnectedDB().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                NganhDTO n = new NganhDTO();
                n.setIdnganh(rs.getInt("idnganh"));
                n.setManganh(rs.getString("manganh"));
                n.setTennganh(rs.getString("tennganh"));
                n.setN_tohopgoc(rs.getString("n_tohopgoc"));
                n.setN_chitieu(rs.getInt("n_chitieu"));
                n.setN_diemsan(rs.getBigDecimal("n_diemsan"));
                n.setN_diemtrungtuyen(rs.getBigDecimal("n_diemtrungtuyen"));
                n.setN_tuyenthang(rs.getString("n_tuyenthang"));
                n.setN_dgnl(rs.getString("n_dgnl"));
                n.setN_thpt(rs.getString("n_thpt"));
                n.setN_vsat(rs.getString("n_vsat"));
                n.setSl_xtt(rs.getInt("sl_xtt"));
                n.setSl_dgnl(rs.getInt("sl_dgnl"));
                n.setSl_vsat(rs.getInt("sl_vsat"));
                n.setSl_thpt(rs.getInt("sl_thpt"));
                list.add(n);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public HashMap<String, String> nganhMap(){
        HashMap<String, String> nganhMap = new HashMap<>();
        String sql = "SELECT manganh, tennganh FROM xt_nganh";
        PreparedStatement ps;
        ResultSet rs;
        try {
            ps = ConnectedDatabase.getConnectedDB().prepareStatement(sql);
            rs = ps.executeQuery();
            while(rs.next()){
                String manganh = rs.getString("manganh");
                String tennganh = rs.getString("tennganh");
                nganhMap.put(manganh,tennganh);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return nganhMap;
    }
}
