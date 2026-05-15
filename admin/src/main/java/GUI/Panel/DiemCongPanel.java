package GUI.Panel;

import BUS.DiemCongBUS;
import BUS.NganhBUS;
import DTO.DiemCongDTO;
import GUIDialog.AddDiemCongDialog;
import GUIDialog.EditDiemCongDialog;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Window;
import java.io.File;
import java.io.FileInputStream;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.jdesktop.swingx.prompt.PromptSupport;
import util.Combobox_design;
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
        loadMaToHopToComboBox();
    }
    public void designCBB(){
        cbb_design.setUpComBoBox(cbb_maNganh);
        cbb_design.setUpComBoBox(cbb_maToHop);
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

    public void loadTenNganhToComboBox() {
        cbb_maNganh.removeAllItems();
        cbb_maNganh.addItem("Tất cả");
        List<String> list = diemCongB.loadMaNganh();
        HashMap<String,String> map = ngBus.getMapTenNganh();
        for(String mn : list) {
            String tenNganh = map.get(mn);
            cbb_maNganh.addItem(tenNganh);
        }
    }
    public void loadMaToHopToComboBox() {
        cbb_maToHop.removeAllItems();
        cbb_maToHop.addItem("Tất cả");
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
                new String[]{"Ts_cccd","Mã ngành","Mã tổ hợp","Phương thức","ĐiểmCC","ĐiểmƯtxt","Điểm tổng","Ghi chú","dc_keys"}
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

        jScrollPane1 = new javax.swing.JScrollPane();
        table_diem = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        cbb_maNganh = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        cbb_maToHop = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        btn_loc = new javax.swing.JButton();
        btn_xoa = new javax.swing.JButton();
        btn_sua = new javax.swing.JButton();
        btn_them = new javax.swing.JButton();
        btn_import = new javax.swing.JButton();
        btn_lammoi = new javax.swing.JButton();
        field_cccd = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(204, 204, 204));
        setPreferredSize(new java.awt.Dimension(1300, 800));

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

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel5.setText("Tên ngành");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel8.setText("Tổ hợp môn");

        btn_loc.setBackground(new java.awt.Color(0, 0, 255));
        btn_loc.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btn_loc.setForeground(new java.awt.Color(255, 255, 255));
        btn_loc.setText("Áp dụng bộ lọc");
        btn_loc.addActionListener(this::btn_locActionPerformed);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbb_maNganh, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(134, 134, 134)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(cbb_maToHop, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(200, 200, 200)
                        .addComponent(btn_loc, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel8))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE, false)
                    .addComponent(cbb_maToHop, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_loc, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbb_maNganh, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        btn_xoa.setBackground(new java.awt.Color(255, 51, 51));
        btn_xoa.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btn_xoa.setForeground(new java.awt.Color(255, 255, 255));
        btn_xoa.setText("Xóa điểm");
        btn_xoa.addActionListener(this::btn_xoaActionPerformed);

        btn_sua.setBackground(new java.awt.Color(52, 152, 219));
        btn_sua.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btn_sua.setForeground(new java.awt.Color(255, 255, 255));
        btn_sua.setText("Sửa điểm");
        btn_sua.addActionListener(this::btn_suaActionPerformed);

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

        btn_lammoi.setBackground(new java.awt.Color(102, 255, 102));
        btn_lammoi.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btn_lammoi.setForeground(new java.awt.Color(255, 255, 255));
        btn_lammoi.setText("Làm mới");
        btn_lammoi.addActionListener(this::btn_lammoiActionPerformed);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel2.setText("Quản lý điểm cộng");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addComponent(jLabel2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jLabel2)
                .addContainerGap(17, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
            .addGroup(layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(field_cccd, javax.swing.GroupLayout.PREFERRED_SIZE, 288, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(btn_lammoi, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19)
                .addComponent(btn_import, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4)
                .addComponent(btn_them, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(btn_sua, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(11, 11, 11)
                .addComponent(btn_xoa, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(311, Short.MAX_VALUE))
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(field_cccd, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_lammoi, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btn_import, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_them, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_sua, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_xoa, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 563, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btn_themActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_themActionPerformed
        Window parentWindow = SwingUtilities.getWindowAncestor(this);
        new AddDiemCongDialog((Frame) parentWindow, true, this).setVisible(true);
    }//GEN-LAST:event_btn_themActionPerformed


    private void btn_importActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_importActionPerformed
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn file Excel");
        int result = fileChooser.showOpenDialog(this);
        if (result != JFileChooser.APPROVE_OPTION) {
            return;
        }
        File file = fileChooser.getSelectedFile();
        String filePath = file.getAbsolutePath();

        // ===== LOADING DIALOG =====
        JDialog loadingDialog = new JDialog();
        loadingDialog.setTitle("Đang xử lý...");
        loadingDialog.setSize(300, 120);
        loadingDialog.setLocationRelativeTo(this);
        loadingDialog.setLayout(new BorderLayout());
        JLabel text = new JLabel("Đang import dữ liệu...", JLabel.CENTER);
        text.setFont(new Font("Segoe UI", Font.BOLD, 14));
        text.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        loadingDialog.add(text, BorderLayout.CENTER);
        loadingDialog.pack();
        loadingDialog.setLocationRelativeTo(this);
        loadingDialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);

        // ===== BACKGROUND TASK =====
        SwingWorker<Void, Void> worker = new SwingWorker<>() {
            int count = 0;
            String message = "";
            @Override
            protected Void doInBackground() {
                try {
                    DiemCongBUS bus = new DiemCongBUS();
                    try (FileInputStream fis = new FileInputStream(file); Workbook workbook = new XSSFWorkbook(fis)) {
                        Sheet sheet = workbook.getSheetAt(0);
                        Row header = sheet.getRow(0);
                        int cccdCol = bus.getColumnIndex(header, "CCCD");
                        int loaiGiaiCol = bus.getColumnIndex(header, "Loại giải");
                        int diemCongCol = bus.getColumnIndex(header, "Điểm cộng");
                        boolean isCCFile = (cccdCol != -1 && diemCongCol != -1);
                        boolean isUTXTFile = (loaiGiaiCol != -1);
                        if (isCCFile) {
                            count = bus.importCC(filePath);
                            message = "Import chứng chỉ thành công " + count + " dòng";
                        } else if (isUTXTFile) {
                            count = bus.importUTXT(filePath);
                            message = "Import UTXT thành công " + count + " dòng!";

                        } else {
                            message = "Không xác định được loại file!";
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    message = "Lỗi khi import file!";
                }
                return null;
            }
            @Override
            protected void done() {
                loadingDialog.dispose(); // tắt loading
                JOptionPane.showMessageDialog(null, message);
                dataTable(diemCongB.getList());
            }
        };
        worker.execute();
        loadingDialog.setVisible(true);
    }//GEN-LAST:event_btn_importActionPerformed

    private void btn_locActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_locActionPerformed
        String cccd = field_cccd.getText();
        String mn = cbb_maNganh.getSelectedItem().toString();
        String mth = cbb_maToHop.getSelectedItem().toString();
        
        // lọc tìm kiếm cccd
        if(!cccd.isEmpty()){
            dataTable(diemCongB.findByCccd(cccd));
            return;
        }
        
        // filter tổng
        dataTable(diemCongB.filterAll(mn, mth));
        
    }//GEN-LAST:event_btn_locActionPerformed

    private void btn_lammoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_lammoiActionPerformed
        field_cccd.setText("");
        cbb_maNganh.setSelectedIndex(0);
        cbb_maToHop.setSelectedIndex(0);
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
    private javax.swing.JComboBox<String> cbb_maNganh;
    private javax.swing.JComboBox<String> cbb_maToHop;
    private javax.swing.JTextField field_cccd;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable table_diem;
    // End of variables declaration//GEN-END:variables
}
