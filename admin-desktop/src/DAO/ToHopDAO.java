package DAO;

import DTO.ToHopDTO;
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

public class ToHopDAO {

    public int insert(ToHopDTO t) {
        String sql = "INSERT INTO xt_tohop_monthi (matohop, mon1, mon2, mon3, tentohop) VALUES (?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = ConnectedDatabase.getConnectedDB().prepareStatement(sql);
            ps.setString(1, t.getMatohop());
            ps.setString(2, t.getMon1());
            ps.setString(3, t.getMon2());
            ps.setString(4, t.getMon3());
            ps.setString(5, t.getTentohop());
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    // IMPORT EXCEL
    public List<ToHopDTO> importToHop(String filePath) {
        List<ToHopDTO> list = new ArrayList<>();
        try {
            FileInputStream fis = new FileInputStream(new File(filePath));
            Workbook workbook = new XSSFWorkbook(fis);
            Sheet sheet = workbook.getSheetAt(0);
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;
                ToHopDTO t = new ToHopDTO();
                if (row.getCell(0) != null) {
                    t.setMatohop(row.getCell(0).toString());
                }
                if (row.getCell(1) != null) {
                    t.setMon1(row.getCell(1).toString());
                }
                if (row.getCell(2) != null) {
                    t.setMon2(row.getCell(2).toString());
                }
                if (row.getCell(3) != null) {
                    t.setMon3(row.getCell(3).toString());
                }
                if (row.getCell(4) != null) {
                    t.setTentohop(row.getCell(4).toString());
                }
                list.add(t);
            }
            workbook.close();
            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // INSERT LIST
    public void insertList(List<ToHopDTO> list) {
        String sql = "INSERT INTO xt_tohop_monthi (matohop, mon1, mon2, mon3, tentohop) VALUES (?, ?, ?, ?, ?)";

        try {
            PreparedStatement ps = ConnectedDatabase.getConnectedDB().prepareStatement(sql);

            for (ToHopDTO t : list) {
                ps.setString(1, t.getMatohop());
                ps.setString(2, t.getMon1());
                ps.setString(3, t.getMon2());
                ps.setString(4, t.getMon3());
                ps.setString(5, t.getTentohop());
                ps.addBatch();
            }

            ps.executeBatch();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // GET ALL
    public ArrayList<ToHopDTO> getAllToHop() {
        ArrayList<ToHopDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM xt_tohop_monthi";

        try {
            PreparedStatement ps = ConnectedDatabase.getConnectedDB().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ToHopDTO t = new ToHopDTO();

                t.setIdtohop(rs.getInt("idtohop"));
                t.setMatohop(rs.getString("matohop"));
                t.setMon1(rs.getString("mon1"));
                t.setMon2(rs.getString("mon2"));
                t.setMon3(rs.getString("mon3"));
                t.setTentohop(rs.getString("tentohop"));

                list.add(t);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
    
    public HashMap<String, String> tohopMap(){
        HashMap<String, String> tohopMap = new HashMap<>();
        String sql = "SELECT matohop, tentohop FROM xt_tohop_monthi";
        PreparedStatement ps;
        ResultSet rs;
        try {
            ps = ConnectedDatabase.getConnectedDB().prepareStatement(sql);
            rs = ps.executeQuery();
            while(rs.next()){
                String maTH = rs.getString("matohop");
                String tenTH = rs.getString("tentohop");
                tohopMap.put(maTH, tenTH);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tohopMap;
    }
}