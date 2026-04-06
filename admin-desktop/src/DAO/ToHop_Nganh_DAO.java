package DAO;

import DTO.ToHop_Nganh_DTO;
import config.ConnectedDatabase;
import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ToHop_Nganh_DAO {

    public int insert(ToHop_Nganh_DTO t) {
        String sql = "INSERT INTO xt_nganh_tohop ("
                + "manganh, matohop, "
                + "th_mon1, hsmon1, "
                + "th_mon2, hsmon2, "
                + "th_mon3, hsmon3, "
                + "tb_keys, "
                + "`TO`, `LI`, `HO`, `SI`, `VA`, `SU`, `DI`, `TI`, `KTPL`, `KHAC`, "
                + "dolech"
                + ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = ConnectedDatabase.getConnectedDB().prepareStatement(sql);
            ps.setString(1, t.getManganh());
            ps.setString(2, t.getMatohop());
            ps.setString(3, t.getTh_mon1());
            ps.setObject(4, t.getHsmon1());
            ps.setString(5, t.getTh_mon2());
            ps.setObject(6, t.getHsmon2());
            ps.setString(7, t.getTh_mon3());
            ps.setObject(8, t.getHsmon3());
            ps.setString(9, t.getTb_keys());
            ps.setObject(10, t.getTO());
            ps.setObject(11, t.getLI());
            ps.setObject(12, t.getHO());
            ps.setObject(13, t.getSI());
            ps.setObject(14, t.getVA());
            ps.setObject(15, t.getSU());
            ps.setObject(16, t.getDI());
            ps.setObject(17, t.getTI());
            ps.setObject(18, t.getKTPL());
            ps.setObject(19, t.getKHAC());
            ps.setBigDecimal(20, t.getDolech());
            return ps.executeUpdate(); // trả về số dòng insert (1 = thành công)

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }
    
    // ================= HELPER =================
    private BigDecimal getDecimal(Cell cell) {
        if (cell == null) {
            return null;
        }
        try {
            if (cell.getCellType() == CellType.BLANK) {
                return null;
            }
            if (cell.getCellType() == CellType.NUMERIC) {
                return BigDecimal.valueOf(cell.getNumericCellValue());
            }
            String value = cell.toString().trim();
            if (value.isEmpty() || value.equalsIgnoreCase("null")) {
                return null;
            }
            return new BigDecimal(value);
        } catch (Exception e) {
            return null;
        }
    }

    private Integer getInt(Cell cell) {
        if (cell == null) {
            return null;
        }
        try {
            if (cell.getCellType() == CellType.NUMERIC) {
                return (int) cell.getNumericCellValue();
            }
            String value = cell.toString().trim();
            if (value.isEmpty()) {
                return null;
            }
            return Integer.parseInt(value);
        } catch (Exception e) {
            return null;
        }
    }

    // ================= IMPORT EXCEL =================
    public List<ToHop_Nganh_DTO> importExcel(String filePath) {
        List<ToHop_Nganh_DTO> list = new ArrayList<>();

        try {
            FileInputStream fis = new FileInputStream(new File(filePath));
            Workbook workbook = new XSSFWorkbook(fis);
            Sheet sheet = workbook.getSheetAt(0);

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) {
                    continue;
                }

                ToHop_Nganh_DTO t = new ToHop_Nganh_DTO();

                t.setManganh(row.getCell(0).toString());
                t.setMatohop(row.getCell(1).toString());

                t.setTh_mon1(row.getCell(2).toString());
                t.setHsmon1(getInt(row.getCell(3)));

                t.setTh_mon2(row.getCell(4).toString());
                t.setHsmon2(getInt(row.getCell(5)));

                t.setTh_mon3(row.getCell(6).toString());
                t.setHsmon3(getInt(row.getCell(7)));

                // auto generate key
                t.setTb_keys(t.getManganh() + "_" + t.getMatohop());

                // flags môn
                t.setTO(getInt(row.getCell(8)));
                t.setLI(getInt(row.getCell(9)));
                t.setHO(getInt(row.getCell(10)));
                t.setSI(getInt(row.getCell(11)));
                t.setVA(getInt(row.getCell(12)));
                t.setSU(getInt(row.getCell(13)));
                t.setDI(getInt(row.getCell(14)));
                t.setTI(getInt(row.getCell(15)));
                t.setKTPL(getInt(row.getCell(16)));
                t.setKHAC(getInt(row.getCell(17)));

                t.setDolech(getDecimal(row.getCell(18)));

                list.add(t);
            }

            workbook.close();
            fis.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // ================= INSERT =================
    public void insertList(List<ToHop_Nganh_DTO> list) {

        String sql = "INSERT INTO xt_nganh_tohop (\n"
                + "    manganh, matohop,\n"
                + "    th_mon1, hsmon1,\n"
                + "    th_mon2, hsmon2,\n"
                + "    th_mon3, hsmon3,\n"
                + "    tb_keys,\n"
                + "    `TO`, `LI`, `HO`, `SI`, `VA`, `SU`, `DI`, `TI`, `KTPL`, `KHAC`,\n"
                + "    dolech\n"
                + ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement ps = ConnectedDatabase.getConnectedDB().prepareStatement(sql);

            for (ToHop_Nganh_DTO t : list) {
                ps.setString(1, t.getManganh());
                ps.setString(2, t.getMatohop());

                ps.setString(3, t.getTh_mon1());
                ps.setObject(4, t.getHsmon1());

                ps.setString(5, t.getTh_mon2());
                ps.setObject(6, t.getHsmon2());

                ps.setString(7, t.getTh_mon3());
                ps.setObject(8, t.getHsmon3());

                ps.setString(9, t.getTb_keys());

                ps.setObject(10, t.getTO());
                ps.setObject(11, t.getLI());
                ps.setObject(12, t.getHO());
                ps.setObject(13, t.getSI());
                ps.setObject(14, t.getVA());
                ps.setObject(15, t.getSU());
                ps.setObject(16, t.getDI());
                ps.setObject(17, t.getTI());
                ps.setObject(18, t.getKTPL());
                ps.setObject(19, t.getKHAC());

                ps.setBigDecimal(20, t.getDolech());

                ps.addBatch();
            }

            ps.executeBatch();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ================= GET ALL =================
    public ArrayList<ToHop_Nganh_DTO> getAll() {
        ArrayList<ToHop_Nganh_DTO> list = new ArrayList<>();

        String sql = "SELECT * FROM xt_nganh_tohop";

        try {
            PreparedStatement ps = ConnectedDatabase.getConnectedDB().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ToHop_Nganh_DTO t = new ToHop_Nganh_DTO();

                t.setId(rs.getInt("id"));
                t.setManganh(rs.getString("manganh"));
                t.setMatohop(rs.getString("matohop"));

                t.setTh_mon1(rs.getString("th_mon1"));
                t.setHsmon1(rs.getInt("hsmon1"));

                t.setTh_mon2(rs.getString("th_mon2"));
                t.setHsmon2(rs.getInt("hsmon2"));

                t.setTh_mon3(rs.getString("th_mon3"));
                t.setHsmon3(rs.getInt("hsmon3"));

                t.setTb_keys(rs.getString("tb_keys"));

                t.setTO(rs.getInt("TO"));
                t.setLI(rs.getInt("LI"));
                t.setHO(rs.getInt("HO"));
                t.setSI(rs.getInt("SI"));
                t.setVA(rs.getInt("VA"));
                t.setSU(rs.getInt("SU"));
                t.setDI(rs.getInt("DI"));
                t.setTI(rs.getInt("TI"));
                t.setKTPL(rs.getInt("KTPL"));
                t.setKHAC(rs.getInt("KHAC"));

                t.setDolech(rs.getBigDecimal("dolech"));

                list.add(t);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}
