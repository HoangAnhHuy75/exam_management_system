package GUI.Panel;

import BUS.DiemCongBUS;
import BUS.NganhBUS;
import DTO.DiemCongDTO;
import GUIDialog.AddDiemCongDialog;
import GUIDialog.EditDiemCongDialog;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Window;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import org.jdesktop.swingx.prompt.PromptSupport;
import util.Combobox_design;
import util.JButton_design;
import util.JTextF_design;
import util.Table_design;

public class DiemCongPanel extends javax.swing.JPanel {

    JTextF_design jtf_design = new JTextF_design();
    Table_design table_design = new Table_design();
    Combobox_design cbb_design = new Combobox_design();
    NganhBUS ngBus = new NganhBUS();
    DiemCongBUS diemCongB = new DiemCongBUS();
    public DiemCongPanel() {
        initComponents();
        khoiTao();
    }

    public void khoiTao() {
        dataTable(diemCongB.getList());
        designJTF();
        designCBB();
        setIcon();
        loadTenNganhToComboBox();
        loadTenPhuongThucToComboBox();
        loadMaToHopToComboBox();
    }
    public void designCBB(){
        cbb_design.setUpComBoBox(cbb_maNganh);
        cbb_design.setUpComBoBox(cbb_maToHop);
        cbb_design.setUpComBoBox(cbb_PhuongThuc);
    }
    public void designJTF(){
        jtf_design.setUpJTF(field_cccd);
        PromptSupport.setPrompt("Tìm kiếm theo căn cước công dân", field_cccd);
        PromptSupport.setForeground(Color.GRAY, field_cccd);
        PromptSupport.setFocusBehavior(PromptSupport.FocusBehavior.SHOW_PROMPT, field_cccd);
    }
    public void setIcon() {
        btn_lammoi.setIcon(new FlatSVGIcon("./resources/icon/refresh.svg", 0.25f));
        btn_import.setIcon(new FlatSVGIcon("./resources/icon/import.svg", 0.2f));
        btn_them.setIcon(new FlatSVGIcon("./resources/icon/add.svg", 0.2f));
        btn_sua.setIcon(new FlatSVGIcon("./resources/icon/edit.svg", 0.2f));
        btn_xoa.setIcon(new FlatSVGIcon("./resources/icon/delete.svg", 0.2f));
    }
    public void loadTenPhuongThucToComboBox() {
        cbb_PhuongThuc.removeAllItems(); // xóa dữ liệu cũ
        cbb_PhuongThuc.addItem("Bộ lọc ptxt");
        String[] ptxt = {"Tuyển thẳng", "ĐGNL", "THPT", "VSAT"};
        for(String pt : ptxt){
            cbb_PhuongThuc.addItem(pt);
        }
    }
    public void loadTenNganhToComboBox() {
        cbb_maNganh.removeAllItems();
        cbb_maNganh.addItem("Chọn tên ngành");
        List<String> list = diemCongB.loadMaNganh();
        HashMap<String,String> map = ngBus.getMapTenNganh();
        for(String mn : list) {
            String tenNganh = map.get(mn);
            cbb_maNganh.addItem(tenNganh);
        }
    }
    public void loadMaToHopToComboBox() {
        cbb_maToHop.removeAllItems();
        cbb_maToHop.addItem("Chọn Mã Tổ Hợp");
        List<String> list = diemCongB.loadMaToHop();
        for(String mth : list) {
            cbb_maToHop.addItem(mth);
        }
    }
    public String convertPhuongThucToText(String ptCode) {
    switch (ptCode) {
        case "PT1":
            return "Tuyển thẳng";
        case "PT2":
            return "ĐGNL";
        case "PT3":
            return "VSAT";
        case "PT4":
            return "THPT";
        default:
            return ptCode;
        }
    }
    public void dataTable(ArrayList<DiemCongDTO> listDiem) {
        HashMap<String, String> mapNganh = ngBus.getMapTenNganh();
        DefaultTableModel model = new DefaultTableModel(
                new Object[][]{},
                new String[]{"ts_cccd","mã ngành","mã tổ hợp","phương thức","điểmCC","điểmƯtxt","điểm Tổng","ghi chú","dc_keys"}
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        for (DiemCongDTO dc : listDiem) {
            String tenNganh = mapNganh.get(dc.getManganh());
            if (tenNganh == null) {
            tenNganh = dc.getManganh();
            }
            model.addRow(new Object[]{
                dc.getTs_cccd(),tenNganh,dc.getMatohop(),convertPhuongThucToText(dc.getPhuongthuc())
                ,dc.getDiemCC(),dc.getDiemUtxt(),dc.getDiemTong(),dc.getGhichu(),dc.getDc_keys()
            });
        }
        table_diem.setModel(model);
        table_design.centerTable(table_diem);
        table_design.setUpTable(table_diem);
        TableColumnModel columnModel = table_diem.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(130);
        columnModel.getColumn(1).setPreferredWidth(100);
        columnModel.getColumn(2).setPreferredWidth(100);
        columnModel.getColumn(3).setPreferredWidth(80);
        columnModel.getColumn(4).setPreferredWidth(60);
        columnModel.getColumn(5).setPreferredWidth(60);
        columnModel.getColumn(6).setPreferredWidth(60);
        columnModel.getColumn(7).setPreferredWidth(100);
        columnModel.getColumn(8).setPreferredWidth(150);
    } 
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        btn_them = new javax.swing.JButton();
        btn_import = new javax.swing.JButton();
        btn_sua = new javax.swing.JButton();
        btn_xoa = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        field_cccd = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        cbb_maNganh = new javax.swing.JComboBox<>();
        cbb_maToHop = new javax.swing.JComboBox<>();
        cbb_PhuongThuc = new javax.swing.JComboBox<>();
        btn_loc = new javax.swing.JButton();
        btn_lammoi = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        table_diem = new javax.swing.JTable();

        setBackground(new java.awt.Color(255, 255, 255));
        setPreferredSize(new java.awt.Dimension(1300, 800));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel6.setBackground(new java.awt.Color(255, 255, 255));
        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel6.setText("HỆ THỐNG /");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(51, 204, 0));
        jLabel7.setText("ĐIỂM CỘNG");

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 22)); // NOI18N
        jLabel1.setText("Quản lý điểm cộng tuyển sinh");

        btn_them.setBackground(new java.awt.Color(0, 153, 51));
        btn_them.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btn_them.setForeground(new java.awt.Color(255, 255, 255));
        btn_them.setText("Thêm điểm cộng");
        btn_them.addActionListener(this::btn_themActionPerformed);

        btn_import.setBackground(new java.awt.Color(204, 204, 255));
        btn_import.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btn_import.setForeground(new java.awt.Color(51, 51, 255));
        btn_import.setText("Import Excel");
        btn_import.addActionListener(this::btn_importActionPerformed);

        btn_sua.setBackground(new java.awt.Color(52, 152, 219));
        btn_sua.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btn_sua.setForeground(new java.awt.Color(255, 255, 255));
        btn_sua.setText("Sửa điểm");
        btn_sua.addActionListener(this::btn_suaActionPerformed);

        btn_xoa.setBackground(new java.awt.Color(255, 51, 51));
        btn_xoa.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btn_xoa.setForeground(new java.awt.Color(255, 255, 255));
        btn_xoa.setText("Xóa điểm");
        btn_xoa.addActionListener(this::btn_xoaActionPerformed);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel7)))
                .addGap(119, 119, 119)
                .addComponent(btn_import, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(41, 41, 41)
                .addComponent(btn_them)
                .addGap(46, 46, 46)
                .addComponent(btn_sua, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(54, 54, 54)
                .addComponent(btn_xoa, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(208, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btn_them, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_sua, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_xoa, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_import, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(24, Short.MAX_VALUE))
        );

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel4.setText("CCCD");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel5.setText("tenNganh");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel8.setText("maTohop");

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel9.setText("Phương thức");

        cbb_maNganh.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        cbb_maToHop.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        cbb_PhuongThuc.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        btn_loc.setBackground(new java.awt.Color(0, 0, 255));
        btn_loc.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btn_loc.setForeground(new java.awt.Color(255, 255, 255));
        btn_loc.setText("Lọc dữ liệu");
        btn_loc.addActionListener(this::btn_locActionPerformed);

        btn_lammoi.setBackground(new java.awt.Color(102, 255, 102));
        btn_lammoi.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btn_lammoi.setForeground(new java.awt.Color(255, 255, 255));
        btn_lammoi.setText("Làm mới");
        btn_lammoi.addActionListener(this::btn_lammoiActionPerformed);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(field_cccd, javax.swing.GroupLayout.PREFERRED_SIZE, 288, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(39, 39, 39)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbb_maNganh, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addGap(37, 37, 37)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbb_maToHop, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addGap(36, 36, 36)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(cbb_PhuongThuc, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(35, 35, 35)
                        .addComponent(btn_loc, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btn_lammoi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(17, 17, 17))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(jLabel8)
                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(field_cccd, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cbb_PhuongThuc, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btn_loc, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btn_lammoi, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(cbb_maNganh)
                    .addComponent(cbb_maToHop))
                .addContainerGap(12, Short.MAX_VALUE))
        );

        table_diem.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(table_diem);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 602, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btn_themActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_themActionPerformed
        Window parentWindow = SwingUtilities.getWindowAncestor(this);
        new AddDiemCongDialog((Frame) parentWindow, true, this).setVisible(true);
    }//GEN-LAST:event_btn_themActionPerformed

    private void btn_importActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_importActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_importActionPerformed

    private void btn_locActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_locActionPerformed
        String cccd = field_cccd.getText();
        String mn = cbb_maNganh.getSelectedItem().toString();
        String mth = cbb_maToHop.getSelectedItem().toString();
        String ptxt = cbb_PhuongThuc.getSelectedItem().toString();
        
        // lọc tìm kiếm cccd
        if(!cccd.isEmpty()){
            dataTable(diemCongB.findByCccd(cccd));
            return;
        }
        
        // filter tổng
        dataTable(diemCongB.filterAll(mn, mth, ptxt));
        
    }//GEN-LAST:event_btn_locActionPerformed

    private void btn_lammoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_lammoiActionPerformed
        field_cccd.setText("");
        cbb_maNganh.setSelectedIndex(0);
        cbb_maToHop.setSelectedIndex(0);
        cbb_PhuongThuc.setSelectedIndex(0);
        dataTable(diemCongB.getList());
    }//GEN-LAST:event_btn_lammoiActionPerformed

    private void btn_xoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_xoaActionPerformed
        int row = table_diem.getSelectedRow();
        if(row == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn dòng cần xóa");
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this, "Bạn có muốn xóa điểm cộng này không?", "Xác nhận xóa",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE);
        if(confirm != JOptionPane.OK_OPTION) {
            return;
        }
        
        String dckeys = table_diem.getValueAt(row, 8).toString();

        DiemCongDTO dc = diemCongB.findOneByDcKeys(dckeys);

        if(dc == null) {
            JOptionPane.showMessageDialog(this, 
                "Không tìm thấy dữ liệu");
            return;
        }
        if(diemCongB.delete(dc) == 1) {
            JOptionPane.showMessageDialog(this, "Xóa điểm cộng thành công","Thông báo",JOptionPane.INFORMATION_MESSAGE);
            dataTable(diemCongB.getList());
        } else {
            JOptionPane.showMessageDialog(this, "Xóa thất bại","Lỗi",JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btn_xoaActionPerformed

    private void btn_suaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_suaActionPerformed
        int row = table_diem.getSelectedRow();
        if(row == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn dòng cần sửa");
            return;
        }

        String dckeys = table_diem.getValueAt(row, 8).toString();

        DiemCongDTO dcDTO = diemCongB.findOneByDcKeys(dckeys);
        Window parentWindow = SwingUtilities.getWindowAncestor(this);
        new EditDiemCongDialog((Frame) parentWindow, true, this, dcDTO).setVisible(true);
    }//GEN-LAST:event_btn_suaActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_import;
    private javax.swing.JButton btn_lammoi;
    private javax.swing.JButton btn_loc;
    private javax.swing.JButton btn_sua;
    private javax.swing.JButton btn_them;
    private javax.swing.JButton btn_xoa;
    private javax.swing.JComboBox<String> cbb_PhuongThuc;
    private javax.swing.JComboBox<String> cbb_maNganh;
    private javax.swing.JComboBox<String> cbb_maToHop;
    private javax.swing.JTextField field_cccd;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable table_diem;
    // End of variables declaration//GEN-END:variables
}
