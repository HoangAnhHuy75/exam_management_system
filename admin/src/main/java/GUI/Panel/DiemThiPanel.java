package GUI.Panel;

import BUS.DiemThiBUS;
import DTO.DiemThiDTO;
import GUIDialog.AddDiemThiDialog;
import GUIDialog.EditDiemThiDialog;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Window;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import org.jdesktop.swingx.prompt.PromptSupport;
import util.Combobox_design;
import util.JTextF_design;
import util.Table_design;

/**
 *
 * @author HP
 */
public class DiemThiPanel extends javax.swing.JPanel {
    private DiemThiBUS diemThiB = new DiemThiBUS();
    JTextF_design jtf_design = new JTextF_design();
    Table_design table_design = new Table_design();
    Combobox_design cbb_design = new Combobox_design();
    public DiemThiPanel() {
        initComponents();
        khoiTao();
    }
    public void khoiTao() {
        dataTable(diemThiB.getList());
        designJTF();
        designCBB();
        setIcon();
        loadTenPhuongThucToComboBox();
        loadMonToComboBox();
        loadLoaiDiemToComboBox();
    }
    public void designCBB(){
        cbb_design.setUpComBoBox(cbb_phuongthuc);
        cbb_design.setUpComBoBox(cbb_mon);
        cbb_design.setUpComBoBox(cbb_loaidiem);
    }
    public void designJTF(){
        jtf_design.setUpJTF(field_cccd);
        PromptSupport.setPrompt("Tìm kiếm theo căn cước công dân", field_cccd);
        PromptSupport.setForeground(Color.GRAY, field_cccd);
        PromptSupport.setFocusBehavior(PromptSupport.FocusBehavior.SHOW_PROMPT, field_cccd);
    }
    public void setIcon() {
        btn_refresh.setIcon(new FlatSVGIcon("./resources/icon/refresh.svg", 0.25f));
        btn_import.setIcon(new FlatSVGIcon("./resources/icon/import.svg", 0.2f));
        btn_add.setIcon(new FlatSVGIcon("./resources/icon/add.svg", 0.2f));
        btn_sua.setIcon(new FlatSVGIcon("./resources/icon/edit.svg", 0.2f));
        btn_xoa.setIcon(new FlatSVGIcon("./resources/icon/delete.svg", 0.2f));
    }
    public void loadTenPhuongThucToComboBox() {
        cbb_phuongthuc.removeAllItems(); // xóa dữ liệu cũ
        cbb_phuongthuc.addItem("Bộ lọc PT");
        String[] ptxt = {"Tuyển thẳng", "ĐGNL", "THPT", "VSAT"};
        for(String pt : ptxt){
            cbb_phuongthuc.addItem(pt);
        }
    }
    public void loadMonToComboBox() {
        cbb_mon.removeAllItems(); // xóa dữ liệu cũ
        cbb_mon.addItem("Bộ lọc môn");
        String[] ptxt = {"Toán", "Lí", "Hóa", "Sinh", "Sử", "Địa", "Văn", "CNCN", "CNNN", "Tin", "KTPL",
        "NK1", "NK2", "NL1", "N1_THI", "N1_CC"};
        for(String pt : ptxt){
            cbb_mon.addItem(pt);
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
    public void loadLoaiDiemToComboBox() {
        cbb_loaidiem.removeAllItems(); // xóa dữ liệu cũ
        cbb_loaidiem.addItem("Bộ lọc điểm");
        String[] ptxt = {"Giỏi","Khá","Trung bình","Yếu","Kém"};
        for(String pt : ptxt){
            cbb_loaidiem.addItem(pt);
        }
    }
    public void dataTable(ArrayList<DiemThiDTO> listDiem) {
        DefaultTableModel model = new DefaultTableModel(
                new Object[][]{},
                new String[]{"cccd","SBD","Phương thức","TO","LI","HO","SI","SU","DI","VA","CNCN","CNNN","TI","KTPL","NK1","NK2","NL1","N1_THI","N1_CC"}
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        for (DiemThiDTO dt : listDiem) {
            model.addRow(new Object[]{
                dt.getCccd(),dt.getSobaodanh(), convertPhuongThucToText(dt.getD_phuongthuc()),
                dt.getTO(), dt.getLI(), dt.getHO(), dt.getSI(),
                dt.getSU(), dt.getDI(), dt.getVA(), dt.getCNCN(), dt.getCNNN(), dt.getTI(),
                dt.getKTPL(), dt.getNK1(), dt.getNK2(),dt.getNL1(),dt.getN1_THI(),dt.getN1_CC()
            });
        }
        table_diem.setModel(model);
        table_design.centerTable(table_diem);
        table_design.setUpTable(table_diem);
        TableColumnModel columnModel = table_diem.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(100);
        columnModel.getColumn(1).setPreferredWidth(200);
        columnModel.getColumn(2).setPreferredWidth(80);
        columnModel.getColumn(3).setPreferredWidth(80);
        columnModel.getColumn(4).setPreferredWidth(60);
        columnModel.getColumn(5).setPreferredWidth(60);
        columnModel.getColumn(6).setPreferredWidth(60);
        columnModel.getColumn(7).setPreferredWidth(60);
        columnModel.getColumn(8).setPreferredWidth(60);
        columnModel.getColumn(9).setPreferredWidth(60);
        columnModel.getColumn(10).setPreferredWidth(60);
        columnModel.getColumn(11).setPreferredWidth(60);
        columnModel.getColumn(12).setPreferredWidth(60);
        columnModel.getColumn(13).setPreferredWidth(60); 
        columnModel.getColumn(14).setPreferredWidth(60); 
        columnModel.getColumn(15).setPreferredWidth(60);
        columnModel.getColumn(16).setPreferredWidth(60);
        columnModel.getColumn(17).setPreferredWidth(60);
        columnModel.getColumn(18).setPreferredWidth(60);
    }
    public void dataThongKe(HashMap<String, Integer> map) {

        DefaultTableModel model = new DefaultTableModel(
                new Object[][]{},
                new String[]{"Loại điểm", "Số lượng"}
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        for (String key : map.keySet()) {
            model.addRow(new Object[]{
                key,
                map.get(key)
            });
        }

        table_diem.setModel(model);
        table_design.centerTable(table_diem);
        table_design.setUpTable(table_diem);
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        btn_import = new javax.swing.JButton();
        btn_add = new javax.swing.JButton();
        btn_sua = new javax.swing.JButton();
        btn_xoa = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        cbb_phuongthuc = new javax.swing.JComboBox<>();
        field_cccd = new javax.swing.JTextField();
        btn_loc = new javax.swing.JButton();
        cbb_mon = new javax.swing.JComboBox<>();
        cbb_loaidiem = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        btn_refresh = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        table_diem = new javax.swing.JTable();

        setBackground(new java.awt.Color(255, 255, 255));
        setPreferredSize(new java.awt.Dimension(1300, 1000));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(1300, 79));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel1.setText("HỆ THỐNG /");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(51, 204, 0));
        jLabel2.setText("ĐIỂM THI");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 22)); // NOI18N
        jLabel3.setText("Quản lý điểm thi tuyển sinh");

        btn_import.setBackground(new java.awt.Color(204, 204, 255));
        btn_import.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btn_import.setForeground(new java.awt.Color(51, 51, 255));
        btn_import.setText("Import Excel");
        btn_import.addActionListener(this::btn_importActionPerformed);

        btn_add.setBackground(new java.awt.Color(0, 153, 51));
        btn_add.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btn_add.setForeground(new java.awt.Color(255, 255, 255));
        btn_add.setText("Thêm điểm");
        btn_add.addActionListener(this::btn_addActionPerformed);

        btn_sua.setBackground(new java.awt.Color(52, 152, 219));
        btn_sua.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btn_sua.setForeground(new java.awt.Color(255, 255, 255));
        btn_sua.setText("Sửa điểm");
        btn_sua.addActionListener(this::btn_suaActionPerformed);

        btn_xoa.setBackground(new java.awt.Color(255, 0, 51));
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
                    .addComponent(jLabel3)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2)))
                .addGap(119, 119, 119)
                .addComponent(btn_import, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addComponent(btn_add, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36)
                .addComponent(btn_sua, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(btn_xoa, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(btn_import, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btn_add, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(btn_sua, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btn_xoa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(7, Short.MAX_VALUE))
        );

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel4.setText("CCCD");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel6.setText("Phương thức");

        cbb_phuongthuc.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        btn_loc.setBackground(new java.awt.Color(0, 0, 255));
        btn_loc.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btn_loc.setForeground(new java.awt.Color(255, 255, 255));
        btn_loc.setText("Lọc dữ liệu");
        btn_loc.addActionListener(this::btn_locActionPerformed);

        cbb_mon.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        cbb_loaidiem.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel5.setText("Chọn môn");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel7.setText("Loại điểm");

        btn_refresh.setBackground(new java.awt.Color(102, 255, 102));
        btn_refresh.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btn_refresh.setForeground(new java.awt.Color(255, 255, 255));
        btn_refresh.setText("Làm mới");
        btn_refresh.addActionListener(this::btn_refreshActionPerformed);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(field_cccd, javax.swing.GroupLayout.PREFERRED_SIZE, 368, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(55, 55, 55)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbb_phuongthuc, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addGap(52, 52, 52)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbb_mon, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addGap(37, 37, 37)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(cbb_loaidiem, 0, 143, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(btn_loc, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_refresh, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                            .addContainerGap()
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel4)
                                .addComponent(jLabel6)
                                .addComponent(jLabel5))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(cbb_phuongthuc, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(field_cccd, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addGap(33, 33, 33)
                            .addComponent(cbb_mon, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbb_loaidiem, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(btn_refresh, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btn_loc, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(11, Short.MAX_VALUE))
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
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 823, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btn_importActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_importActionPerformed
        try {
            javax.swing.JFileChooser fileChooser = new javax.swing.JFileChooser();
            fileChooser.setDialogTitle("Chọn file Excel");

            int result = fileChooser.showOpenDialog(this);

            if (result == javax.swing.JFileChooser.APPROVE_OPTION) {
                java.io.File file = fileChooser.getSelectedFile();
                
                 // hiện đang import
                 System.out.println("Đang import dữ liệu từ: " + file.getName());
                // GỌI BUS (đúng kiến trúc)
                int count = diemThiB.importFromExcel(file.getAbsolutePath());
                System.out.println("Đã thêm " + count + " điểm");
                if (count == 0) {
                    javax.swing.JOptionPane.showMessageDialog(this, "File không có dữ liệu!");
                    return;
                }

                // reload lại bảng
                dataTable(diemThiB.getList());

                javax.swing.JOptionPane.showMessageDialog(this,
                        "Import thành công " + count + " điểm!");
            }

        } catch (Exception e) {
            e.printStackTrace();
            javax.swing.JOptionPane.showMessageDialog(this,
                    "Lỗi khi import: " + e.getMessage());
        }
    }//GEN-LAST:event_btn_importActionPerformed

    private void btn_addActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_addActionPerformed
        Window parentWindow = SwingUtilities.getWindowAncestor(this);
        new AddDiemThiDialog((Frame) parentWindow, true, this).setVisible(true);
    }//GEN-LAST:event_btn_addActionPerformed

    private void btn_locActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_locActionPerformed
        // TODO add your handling code here:
        String cccd = field_cccd.getText();
        String mon = cbb_mon.getSelectedItem().toString();
        String ptxt = cbb_phuongthuc.getSelectedItem().toString();
        String loaidiem = cbb_loaidiem.getSelectedItem().toString();
        
        // lọc tìm kiếm cccd
        if(!cccd.isEmpty()){
            dataTable(diemThiB.findBycccd(cccd));
            return;
        }
        
        // lọc phương thức xét tuyển
        if(!ptxt.equals("Bộ lọc PT")) {
            dataTable(diemThiB.filterByPTXT(ptxt));
            return;
        }
        // lọc loaị điểm và môn -> bảng bình thường
        
        if(!mon.equals("Bộ lọc môn") && !loaidiem.equals("Bộ lọc điểm")) {
            dataTable(diemThiB.filterByLoaiDiemVaMon(mon,loaidiem));
            return;
        }
        
        // lọc theo môn -> bảng thống kê
        
        if(!mon.equals("Bộ lọc môn")) {
            HashMap<String,Integer> map = diemThiB.thongKetheoMon(mon);
            dataThongKe(map);
            return;
        }
    }//GEN-LAST:event_btn_locActionPerformed

    private void btn_suaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_suaActionPerformed
        int row = table_diem.getSelectedRow();
        if(row == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn dòng cần sửa");
            return;
        }

        String cccd = table_diem.getValueAt(row, 0).toString();

        DiemThiDTO dtDTO = diemThiB.findOneByCCCD(cccd);
        Window parentWindow = SwingUtilities.getWindowAncestor(this);
        new EditDiemThiDialog((Frame) parentWindow, true, this, dtDTO).setVisible(true);
    }//GEN-LAST:event_btn_suaActionPerformed

    private void btn_refreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_refreshActionPerformed
        field_cccd.setText("");

        // reset combobox
        cbb_mon.setSelectedIndex(0);
        cbb_loaidiem.setSelectedIndex(0);
        cbb_phuongthuc.setSelectedIndex(0);

        // load lại bảng điểm ban đầu
        dataTable(diemThiB.getList());
    }//GEN-LAST:event_btn_refreshActionPerformed

    private void btn_xoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_xoaActionPerformed
        int row = table_diem.getSelectedRow();
        if(row == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn dòng cần xóa");
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this, "Bạn có muốn xóa điểm thi này không?", "Xác nhận xóa",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE);
        if(confirm != JOptionPane.OK_OPTION) {
            return;
        }
        
        String cccd = table_diem.getValueAt(row, 0).toString();

        DiemThiDTO dt = diemThiB.findOneByCCCD(cccd);

        if(dt == null) {
            JOptionPane.showMessageDialog(this, 
                "Không tìm thấy dữ liệu");
            return;
        }
        if(diemThiB.delete(dt) == 1) {
            JOptionPane.showMessageDialog(this, "Xóa điểm thành công","Thông báo",JOptionPane.INFORMATION_MESSAGE);
            dataTable(diemThiB.getList());
        } else {
            JOptionPane.showMessageDialog(this, "Xóa thất bại","Lỗi",JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btn_xoaActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_add;
    private javax.swing.JButton btn_import;
    private javax.swing.JButton btn_loc;
    private javax.swing.JButton btn_refresh;
    private javax.swing.JButton btn_sua;
    private javax.swing.JButton btn_xoa;
    private javax.swing.JComboBox<String> cbb_loaidiem;
    private javax.swing.JComboBox<String> cbb_mon;
    private javax.swing.JComboBox<String> cbb_phuongthuc;
    private javax.swing.JTextField field_cccd;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable table_diem;
    // End of variables declaration//GEN-END:variables
}
