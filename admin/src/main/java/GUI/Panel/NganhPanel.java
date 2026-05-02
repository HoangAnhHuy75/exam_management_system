/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package GUI.Panel;

import BUS.NganhBUS;
import DAO.NganhDAO;
import DTO.NganhDTO;
import GUIDialog.AddNganhDialog;
import GUIDialog.UpdateNganhDialog;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Window;
import java.util.ArrayList;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import org.jdesktop.swingx.prompt.PromptSupport;
import util.Combobox_design;
import util.JButton_design;
import util.JTextF_design;
import util.Table_design;

/**
 *
 * @author kiman
 */
public class NganhPanel extends javax.swing.JPanel {
        private NganhDAO nganhD;
    private NganhBUS nganhB = new NganhBUS();
    JTextF_design jtf_design = new JTextF_design();
    Table_design table_design = new Table_design();
    Combobox_design cbb_design = new Combobox_design();
    JButton_design btn_design = new JButton_design();
    public NganhPanel() {
        initComponents();
        khoiTao();
    }

    public NganhBUS getNganhBUS() {
        return this.nganhB;
    }
    public void khoiTao() {
        dataTable(nganhB.getListN());
        designJTF();
        designCBB();
        loadTenNganhToComboBox();
        loadToHopGocCombobox();
        setIcon();
        designButton();
    }
    public JTable getTableThiSinh(){
        return this.getTableThiSinh();
    }
    public void designCBB() {
        cbb_design.setUpComBoBox(jComboBox1);
        cbb_design.setUpComBoBox(jComboBox2);
        cbb_design.setUpComBoBox(jComboBox3);
    }

    public void setIcon() {
        btn_timkiem.setIcon(new FlatSVGIcon("./resources/icon/look.svg", 0.25f));
        btn_refresh.setIcon(new FlatSVGIcon("./resources/icon/refresh.svg", 0.25f));
        major_import.setIcon(new FlatSVGIcon("./resources/icon/import.svg", 0.2f));
        major_add.setIcon(new FlatSVGIcon("./resources/icon/add.svg", 0.2f));
        major_update.setIcon(new FlatSVGIcon("./resources/icon/edit.svg", 0.2f));
        major_filter.setIcon(new FlatSVGIcon("./resources/icon/filter.svg", 0.2f));
        btn_delete.setIcon(new FlatSVGIcon("./resources/icon/delete.svg", 0.2f));
        btn_chitiet.setIcon(new FlatSVGIcon("./resources/icon/view.svg", 0.2f));
    }
    public void designButton(){
        btn_design.setUpBtn(btn_timkiem, Color.WHITE, Color.WHITE);
        btn_design.setUpBtn(btn_refresh, Color.WHITE, Color.WHITE);
    }
    public void designJTF() {
        jtf_design.setUpJTF(jtf_timkiem);
        PromptSupport.setPrompt("Tìm kiếm theo mã ngành, tên ngành", jtf_timkiem);
        PromptSupport.setForeground(Color.GRAY, jtf_timkiem);
        PromptSupport.setFocusBehavior(PromptSupport.FocusBehavior.SHOW_PROMPT, jtf_timkiem);
    }
    public void dataTable(ArrayList<NganhDTO> listNganh) {
        DefaultTableModel model = new DefaultTableModel(
                new Object[][]{},
                new String[]{"Mã ngành", "Tên ngành", "TH gốc", "Chỉ tiêu", "Điểm sàn", "ĐTT", "XTT", "ĐGNL", "THPT", "VSAT", "SL_XTT", "SL_ĐGNL", "SL_THPT", "SL_VSAT"}
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        for (NganhDTO ng : listNganh) {
            model.addRow(new Object[]{
                ng.getMaNganh(), ng.getTenNganh(), ng.getNToHopGoc(), ng.getNChiTieu(), ng.getNDiemSan(), ng.getNDiemTrungTuyen(), ng.getNTuyenThang(), ng.getNDGNL(), ng.getNTHPT(), ng.getNVSAT(), ng.getSlXTT(), ng.getSlDGNL(), ng.getSlTHPT(), ng.getSlVSAT()
            });
        }
        major_table.setModel(model);
        table_design.centerTable(major_table);
        table_design.setUpTable(major_table);
        TableColumnModel columnModel = major_table.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(100);
        columnModel.getColumn(1).setPreferredWidth(200);
        columnModel.getColumn(2).setPreferredWidth(80);
        columnModel.getColumn(3).setPreferredWidth(80);
        columnModel.getColumn(4).setPreferredWidth(100);
        columnModel.getColumn(5).setPreferredWidth(120);
        columnModel.getColumn(6).setPreferredWidth(60);
        columnModel.getColumn(7).setPreferredWidth(60);
        columnModel.getColumn(8).setPreferredWidth(60);
        columnModel.getColumn(9).setPreferredWidth(60);
        columnModel.getColumn(10).setPreferredWidth(80);
        columnModel.getColumn(11).setPreferredWidth(80);
        columnModel.getColumn(12).setPreferredWidth(80);
        columnModel.getColumn(13).setPreferredWidth(80);
    }
    public void loadTenNganhToComboBox() {
        jComboBox1.removeAllItems(); // xóa dữ liệu cũ
        jComboBox1.addItem("Tất cả");
        String[] ptxt = {"Tuyển thẳng", "ĐGNL", "THPT", "VSAT"};
        for(String pt : ptxt){
            jComboBox1.addItem(pt);
        }
        jComboBox3.addItem("Tất cả");
        String[] sortsChitieu = {"Chỉ tiêu giảm dần", "Chỉ tiêu tăng dần"};
        for(String sort : sortsChitieu){
            jComboBox3.addItem(sort);
        }
    }
    public void loadToHopGocCombobox(){
        jComboBox2.removeAllItems();
        jComboBox2.addItem("Tất cả");
        ArrayList<String> toHopGocList = nganhB.getListToHopGoc();
        for(String thg : toHopGocList)
            jComboBox2.addItem(thg);
    }
     private String getSafeString(int row, int col) {
        Object obj = major_table.getValueAt(row, col);
        return (obj != null) ? obj.toString() : "";
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        major_header = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        major_search = new javax.swing.JPanel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jComboBox2 = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        major_filter = new javax.swing.JButton();
        jComboBox3 = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        major_table = new javax.swing.JTable();
        major_add = new javax.swing.JButton();
        major_import = new javax.swing.JButton();
        jtf_timkiem = new javax.swing.JTextField();
        btn_timkiem = new javax.swing.JButton();
        major_update = new javax.swing.JButton();
        btn_refresh = new javax.swing.JButton();
        btn_delete = new javax.swing.JButton();
        btn_chitiet = new javax.swing.JButton();

        setBackground(new java.awt.Color(204, 204, 204));
        setPreferredSize(new java.awt.Dimension(1300, 800));

        major_header.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 22)); // NOI18N
        jLabel1.setText("Quản lý ngành tuyển sinh");

        jLabel6.setText("HỆ THỐNG /");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(51, 204, 0));
        jLabel7.setText("NGÀNH");

        javax.swing.GroupLayout major_headerLayout = new javax.swing.GroupLayout(major_header);
        major_header.setLayout(major_headerLayout);
        major_headerLayout.setHorizontalGroup(
            major_headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(major_headerLayout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(major_headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(major_headerLayout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel7))
                    .addComponent(jLabel1))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        major_headerLayout.setVerticalGroup(
            major_headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(major_headerLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(major_headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        major_search.setBackground(new java.awt.Color(255, 255, 255));
        major_search.setPreferredSize(new java.awt.Dimension(981, 90));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setText("Phương thức");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel4.setText("Tổ hợp gốc");

        major_filter.setBackground(new java.awt.Color(96, 125, 139));
        major_filter.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        major_filter.setForeground(new java.awt.Color(255, 255, 255));
        major_filter.setText("Áp dụng bộ lọc");
        major_filter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                major_filterActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setText("Sắp xếp theo chỉ tiêu");

        javax.swing.GroupLayout major_searchLayout = new javax.swing.GroupLayout(major_search);
        major_search.setLayout(major_searchLayout);
        major_searchLayout.setHorizontalGroup(
            major_searchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(major_searchLayout.createSequentialGroup()
                .addGap(48, 48, 48)
                .addGroup(major_searchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(58, 58, 58)
                .addGroup(major_searchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addGap(94, 94, 94)
                .addGroup(major_searchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addGroup(major_searchLayout.createSequentialGroup()
                        .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(117, 117, 117)
                        .addComponent(major_filter, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(313, Short.MAX_VALUE))
        );
        major_searchLayout.setVerticalGroup(
            major_searchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, major_searchLayout.createSequentialGroup()
                .addContainerGap(9, Short.MAX_VALUE)
                .addGroup(major_searchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(major_searchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jComboBox3, javax.swing.GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE)
                    .addComponent(major_filter, javax.swing.GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE)
                    .addComponent(jComboBox2)
                    .addComponent(jComboBox1))
                .addGap(20, 20, 20))
        );

        jScrollPane1.setPreferredSize(new java.awt.Dimension(0, 0));

        major_table.setModel(new javax.swing.table.DefaultTableModel(
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
        major_table.setMinimumSize(new java.awt.Dimension(0, 0));
        jScrollPane1.setViewportView(major_table);

        major_add.setBackground(new java.awt.Color(0, 153, 51));
        major_add.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        major_add.setForeground(new java.awt.Color(255, 255, 255));
        major_add.setText("Thêm ");
        major_add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                major_addActionPerformed(evt);
            }
        });

        major_import.setBackground(new java.awt.Color(204, 204, 255));
        major_import.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        major_import.setForeground(new java.awt.Color(51, 51, 255));
        major_import.setText("Import Excel");
        major_import.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                major_importActionPerformed(evt);
            }
        });

        btn_timkiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_timkiemActionPerformed(evt);
            }
        });

        major_update.setBackground(new java.awt.Color(52, 152, 219));
        major_update.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        major_update.setForeground(new java.awt.Color(255, 255, 255));
        major_update.setText("Sửa ");
        major_update.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                major_updateActionPerformed(evt);
            }
        });

        btn_refresh.setText("Làm mới");
        btn_refresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_refreshActionPerformed(evt);
            }
        });

        btn_delete.setBackground(new java.awt.Color(255, 0, 0));
        btn_delete.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btn_delete.setForeground(new java.awt.Color(255, 255, 255));
        btn_delete.setText("Xóa");

        btn_chitiet.setBackground(new java.awt.Color(0, 0, 255));
        btn_chitiet.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btn_chitiet.setForeground(new java.awt.Color(255, 255, 255));
        btn_chitiet.setText("Chi tiết");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(major_header, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(major_search, javax.swing.GroupLayout.DEFAULT_SIZE, 1288, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jtf_timkiem, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_timkiem, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_refresh, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(major_import, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(major_add, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(major_update, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_delete, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_chitiet, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(major_header, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_timkiem, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtf_timkiem, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_refresh, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(major_import, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(major_add, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(major_update, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btn_delete, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btn_chitiet, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(major_search, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 568, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void major_addActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_major_addActionPerformed
        Window parentWindow = SwingUtilities.getWindowAncestor(this);
        new AddNganhDialog((Frame) parentWindow, true,this).setVisible(true);
    }//GEN-LAST:event_major_addActionPerformed

    private void major_filterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_major_filterActionPerformed
        String ptxt = (String) jComboBox1.getSelectedItem();
        String thg = (String) jComboBox2.getSelectedItem();
        dataTable(nganhB.timKiem(ptxt,thg));
    }//GEN-LAST:event_major_filterActionPerformed

    private void major_importActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_major_importActionPerformed
        try {
            javax.swing.JFileChooser fileChooser = new javax.swing.JFileChooser();
            fileChooser.setDialogTitle("Chọn file Excel");

            int result = fileChooser.showOpenDialog(this);

            if (result == javax.swing.JFileChooser.APPROVE_OPTION) {
                java.io.File file = fileChooser.getSelectedFile();

                // GỌI BUS (đúng kiến trúc)
                int count = nganhB.importFromExcel(file.getAbsolutePath());

                if (count == 0) {
                    javax.swing.JOptionPane.showMessageDialog(this, "File không có dữ liệu!");
                    return;
                }

                // reload lại bảng
                dataTable(nganhB.getListN());

                javax.swing.JOptionPane.showMessageDialog(this,
                        "Import thành công " + count + " ngành!");
            }

        } catch (Exception e) {
            e.printStackTrace();
            javax.swing.JOptionPane.showMessageDialog(this,
                    "Lỗi khi import: " + e.getMessage());
        }
    }//GEN-LAST:event_major_importActionPerformed

    private void btn_timkiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_timkiemActionPerformed
        String text = jtf_timkiem.getText();
        dataTable(nganhB.timkiemText(text));
    }//GEN-LAST:event_btn_timkiemActionPerformed

    private void btn_refreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_refreshActionPerformed
        dataTable(nganhB.getListN());
        jtf_timkiem.setText("");
    }//GEN-LAST:event_btn_refreshActionPerformed

    private void major_updateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_major_updateActionPerformed
        int vitriRow = major_table.getSelectedRow();

        if (vitriRow == -1) {
            javax.swing.JOptionPane.showMessageDialog(this, "Vui lòng chọn ngành để chỉnh sửa!");
            return;
        }

        // Lấy dữ liệu từ table
        
        String maNganh = major_table.getValueAt(vitriRow, 0).toString();
        String tenNganh = major_table.getValueAt(vitriRow, 1).toString();
        String toHopGoc = major_table.getValueAt(vitriRow, 2).toString();
        int chiTieu = Integer.parseInt(major_table.getValueAt(vitriRow, 3).toString());

        java.math.BigDecimal diemSan = new java.math.BigDecimal(major_table.getValueAt(vitriRow, 4).toString());
        Object value = major_table.getValueAt(vitriRow, 5);
        java.math.BigDecimal diemTrungTuyen = null;

        if (value != null && !value.toString().trim().isEmpty()) {
            diemTrungTuyen = new java.math.BigDecimal(value.toString());
        }
        String xtt = major_table.getValueAt(vitriRow, 6).toString();
        String dgnl = major_table.getValueAt(vitriRow, 7).toString();
        String thpt = major_table.getValueAt(vitriRow, 8).toString();
        String vsat = major_table.getValueAt(vitriRow, 9).toString();

        Integer slXTT = (Integer) major_table.getValueAt(vitriRow, 10);
        Integer slDGNL = (Integer) major_table.getValueAt(vitriRow, 11);
        Integer slTHPT = (Integer) major_table.getValueAt(vitriRow, 12);
        Integer slVSAT = (Integer) major_table.getValueAt(vitriRow, 13);

        // Tạo DTO
        NganhDTO nganh = new NganhDTO();
        nganh.setIdNganh(nganhB.getIdbyIndex(vitriRow));
        nganh.setMaNganh(maNganh);
        nganh.setTenNganh(tenNganh);
        nganh.setNToHopGoc(toHopGoc);
        nganh.setNChiTieu(chiTieu);
        nganh.setNDiemSan(diemSan);
        nganh.setNDiemTrungTuyen(diemTrungTuyen);
        nganh.setNTuyenThang(xtt);
        nganh.setNDGNL(dgnl);
        nganh.setNTHPT(thpt);
        nganh.setNVSAT(vsat);
        nganh.setSlXTT(slXTT);
        nganh.setSlDGNL(slDGNL);
        nganh.setSlTHPT(slTHPT);
        nganh.setSlVSAT(slVSAT);

        // Mở dialog
        Window parentWindow = SwingUtilities.getWindowAncestor(this);
        new UpdateNganhDialog((Frame) parentWindow, true, this, nganh).setVisible(true);
    }//GEN-LAST:event_major_updateActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_chitiet;
    private javax.swing.JButton btn_delete;
    private javax.swing.JButton btn_refresh;
    private javax.swing.JButton btn_timkiem;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JComboBox<String> jComboBox3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jtf_timkiem;
    private javax.swing.JButton major_add;
    private javax.swing.JButton major_filter;
    private javax.swing.JPanel major_header;
    private javax.swing.JButton major_import;
    private javax.swing.JPanel major_search;
    private javax.swing.JTable major_table;
    private javax.swing.JButton major_update;
    // End of variables declaration//GEN-END:variables
}
