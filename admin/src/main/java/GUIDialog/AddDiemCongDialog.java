package GUIDialog;

import BUS.DiemCongBUS;
import BUS.DiemThiBUS;
import BUS.NganhBUS;
import DTO.DiemCongDTO;
import GUI.Panel.DiemCongPanel;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import util.Combobox_design;

/**
 *
 * @author HP
 */
public class AddDiemCongDialog extends javax.swing.JDialog {
    
    DiemCongPanel diemCongPanel;
    DiemCongBUS diemCongB = new DiemCongBUS();
    DiemThiBUS diemThiB = new DiemThiBUS();
    NganhBUS ngB = new NganhBUS();
    Combobox_design cbb_design = new Combobox_design();
    public AddDiemCongDialog(java.awt.Frame parent, boolean modal, DiemCongPanel diemCongPanel) {
        super(parent, modal);
        initComponents();
        this.diemCongPanel = diemCongPanel;
        this.setLocationRelativeTo(null);
        this.setTitle("Thêm điểm cộng");
        khoiTao();
    }
    public void khoiTao(){
        setUpCombobox();
        loadPhuongThucToComboBox();
        loadCccdToComboBox();
        loadMaNganhComboBox();
        loadMaToHopComboBox();
    }
    public void setUpCombobox(){
        cbb_design.setUpComBoBox(cbb_manganh);
        cbb_design.setUpComBoBox(cbb_cccd);
        cbb_design.setUpComBoBox(cbb_matohop);
        cbb_design.setUpComBoBox(cbb_phuongthuc);
    }
    public void loadPhuongThucToComboBox() {
        cbb_phuongthuc.removeAllItems(); // xóa dữ liệu cũ
        cbb_phuongthuc.addItem("THPT");
        String[] ptxt = {"Tuyển thẳng", "ĐGNL", "VSAT"};
        for(String pt : ptxt){
            cbb_phuongthuc.addItem(pt);
        }
    }
    public void loadCccdToComboBox() {
        cbb_cccd.removeAllItems();
        cbb_cccd.addItem("Chọn CCCD");
        List<String> list = diemThiB.loadCbbCccd();
        for(String cccd : list) {
            cbb_cccd.addItem(cccd);
        }
    }
    public void loadMaNganhComboBox() {
        cbb_manganh.removeAllItems();
        cbb_manganh.addItem("Chọn tên ngành");
        List<String> list = diemCongB.loadMaNganh();
        HashMap<String, String> mapNganh = ngB.getTenNganhByMaNganhMap();
        for(String mn : list) {    
            String tenNganh = mapNganh.get(mn);
            cbb_manganh.addItem(tenNganh);
        }
    }
    public void loadMaToHopComboBox() {
        cbb_matohop.removeAllItems();
        cbb_matohop.addItem("Chọn Mã Tổ Hợp");
        List<String> list = diemCongB.loadMaToHop();
        for(String mth : list) {
            cbb_matohop.addItem(mth);
        }
    }
    public BigDecimal getBigDecimal(JTextField field) {
    String text = field.getText().trim();
    
    if(text.isEmpty()) {
        return BigDecimal.ZERO;
    }
    
    try {
        return new BigDecimal(text);
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Dữ liệu số không hợp lệ tại: " + field.getName());
        return null;
        }
    }
    private void checkInputMode() {

        BigDecimal cc = getBigDecimal(field_cc);
        BigDecimal utxt = getBigDecimal(field_utxt);

        if(cc == null) cc = BigDecimal.ZERO;
        if(utxt == null) utxt = BigDecimal.ZERO;

        // CHỈ CÓ CC
        if(cc.compareTo(BigDecimal.ZERO) > 0
                && utxt.compareTo(BigDecimal.ZERO) == 0){

            // reset combobox
            cbb_manganh.setSelectedIndex(0);
            cbb_matohop.setSelectedIndex(0);

            // khóa
            cbb_manganh.setEnabled(false);
            cbb_matohop.setEnabled(false);

        } else {

            // mở lại
            cbb_manganh.setEnabled(true);
            cbb_matohop.setEnabled(true);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        cbb_cccd = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        cbb_manganh = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        cbb_matohop = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        cbb_phuongthuc = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        textArea_ghichu = new javax.swing.JTextArea();
        jLabel7 = new javax.swing.JLabel();
        field_dckeys = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        field_cc = new javax.swing.JTextField();
        field_utxt = new javax.swing.JTextField();
        field_diemtong = new javax.swing.JTextField();
        btn_tinhdiem = new javax.swing.JButton();
        btn_lammoi = new javax.swing.JButton();
        btn_huy = new javax.swing.JButton();
        btn_luu = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(224, 224, 224));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setText("Thểm điểm cộng tuyển sinh");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jLabel1)
                .addContainerGap(26, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "thông tin tổ hợp ngành", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N

        cbb_cccd.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel2.setText("Căn cước công dân");

        jLabel3.setText("Mã ngành");

        cbb_manganh.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel4.setText("Mã tổ hợp");

        cbb_matohop.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel5.setText("Phương thức");

        cbb_phuongthuc.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel6.setText("Ghi chú");

        textArea_ghichu.setColumns(20);
        textArea_ghichu.setRows(5);
        jScrollPane1.setViewportView(textArea_ghichu);

        jLabel7.setText("dc_keys");

        field_dckeys.setEditable(false);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(cbb_cccd, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(cbb_manganh, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(cbb_matohop, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(cbb_phuongthuc, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane1)
                            .addComponent(field_dckeys))))
                .addContainerGap(36, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbb_cccd, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(19, 19, 19)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(cbb_manganh, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(19, 19, 19)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(cbb_matohop, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(19, 19, 19)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(cbb_phuongthuc, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addComponent(jLabel6))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(field_dckeys, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Thông tin điểm", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N

        jLabel8.setText("Điểm chứng chỉ");

        jLabel9.setText("Điểm ưu tiên xét tuyển");

        jLabel10.setText("Điểm tổng");

        field_cc.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                field_ccKeyReleased(evt);
            }
        });

        field_utxt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                field_utxtKeyReleased(evt);
            }
        });

        field_diemtong.setEditable(false);

        btn_tinhdiem.setBackground(new java.awt.Color(153, 153, 255));
        btn_tinhdiem.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btn_tinhdiem.setForeground(new java.awt.Color(255, 255, 255));
        btn_tinhdiem.setText("Tính điểm tổng");
        btn_tinhdiem.addActionListener(this::btn_tinhdiemActionPerformed);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(field_diemtong, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(field_cc, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addGap(18, 18, 18)
                        .addComponent(field_utxt, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(26, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btn_tinhdiem, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(76, 76, 76))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(3, 3, 3)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(field_cc, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(field_utxt, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 36, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(field_diemtong, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addComponent(btn_tinhdiem, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        btn_lammoi.setBackground(new java.awt.Color(51, 153, 255));
        btn_lammoi.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btn_lammoi.setForeground(new java.awt.Color(255, 255, 255));
        btn_lammoi.setText("Làm mới");
        btn_lammoi.addActionListener(this::btn_lammoiActionPerformed);

        btn_huy.setBackground(new java.awt.Color(255, 51, 51));
        btn_huy.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btn_huy.setForeground(new java.awt.Color(255, 255, 255));
        btn_huy.setText("Hủy");
        btn_huy.addActionListener(this::btn_huyActionPerformed);

        btn_luu.setBackground(new java.awt.Color(102, 255, 102));
        btn_luu.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btn_luu.setForeground(new java.awt.Color(255, 255, 255));
        btn_luu.setText("Lưu");
        btn_luu.addActionListener(this::btn_luuActionPerformed);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(btn_lammoi, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(33, 33, 33)
                        .addComponent(btn_huy, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btn_luu, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btn_huy, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_lammoi, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_luu, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_huyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_huyActionPerformed
        this.dispose();
    }//GEN-LAST:event_btn_huyActionPerformed

    private void btn_lammoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_lammoiActionPerformed
        cbb_cccd.setSelectedIndex(0);
        cbb_manganh.setSelectedIndex(0);
        cbb_matohop.setSelectedIndex(0);
        cbb_phuongthuc.setSelectedIndex(0);

        field_cc.setText("");
        field_dckeys.setText("");
        field_diemtong.setText("");
        field_utxt.setText("");
        textArea_ghichu.setText("");
        cbb_cccd.requestFocus();
        cbb_manganh.setEnabled(true);
        cbb_matohop.setEnabled(true);
    }//GEN-LAST:event_btn_lammoiActionPerformed

    private void btn_luuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_luuActionPerformed
        String Cccd = cbb_cccd.getSelectedItem().toString();
        String tenNganh = cbb_manganh.getSelectedItem().toString();
        HashMap<String,String> mapNganh = ngB.getMaNganhByTenNganhMap();
        String maNganh = mapNganh.get(tenNganh);
        String maToHop = cbb_matohop.getSelectedItem().toString();
        String phuongthucText = cbb_phuongthuc.getSelectedItem().toString();
        String phuongthuc = phuongthucText;
        String ghiChu = textArea_ghichu.getText();
        String dcKeys = field_dckeys.getText();
        BigDecimal cc = getBigDecimal(field_cc);
        BigDecimal utxt = getBigDecimal(field_utxt);
        BigDecimal tong = getBigDecimal(field_diemtong);
        String expectedKey = "";
        if(maToHop.equals("Chọn Mã Tổ Hợp")){
            maToHop = null;
        }

        if(cc.compareTo(BigDecimal.ZERO) > 0
                && utxt.compareTo(BigDecimal.ZERO) == 0){

            expectedKey = Cccd + "_CC";

        } else {

            expectedKey = Cccd + "_" + maNganh + "_" + maToHop;
        }
        
        // phải chọn CCCD
        if(Cccd.equals("Chọn CCCD") || phuongthuc == null){
            JOptionPane.showMessageDialog(this,
                    "Vui lòng nhập đầy đủ thông tin");
            return;
        }

        // nếu có UTXT thì bắt buộc chọn ngành + tổ hợp
        if(utxt.compareTo(BigDecimal.ZERO) > 0){

            if(tenNganh.equals("Chọn tên ngành")
                    || maToHop.equals("Chọn Mã Tổ Hợp")){

                JOptionPane.showMessageDialog(this,
                        "Vui lòng chọn ngành và tổ hợp");
                return;
            }
        }
        
        List<BigDecimal> diemList = Arrays.asList(cc,utxt);
        // check âm
        for (BigDecimal diem : diemList) {
            if (diem.compareTo(BigDecimal.ZERO) < 0) {
                JOptionPane.showMessageDialog(this, "Điểm không được âm");
                return;
            }
        }

        // check > 2
        BigDecimal max = new BigDecimal("2");

        for (BigDecimal diem : diemList) {
            if (diem.compareTo(max) > 0) {
                JOptionPane.showMessageDialog(this, "Điểm phải từ 0 đến 2");
                return;
            }
        }
        
        // check tổng điểm
        BigDecimal tongCheck = cc.add(utxt);

        if(tong.compareTo(tongCheck) != 0){
            JOptionPane.showMessageDialog(this,
                    "Điểm tổng không khớp với CC + UTXT");
            return;
        }
        
        // check phải trùng dc_keys
        if(!expectedKey.equals(dcKeys)) {
            JOptionPane.showMessageDialog(this, "dc_key không khớp vui lòng tính điểm tổng lại","Thông báo",JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        // check k nhập chữ
        if(cc == null || utxt == null){
            return;
        }
        // thêm dữ liệu
        DiemCongDTO dc = new DiemCongDTO();
        dc.setTs_cccd(Cccd);
        dc.setManganh(maNganh);
        dc.setMatohop(maToHop);
        dc.setPhuongthuc(phuongthuc);
        dc.setGhichu(ghiChu);
        dc.setDc_keys(dcKeys);
        dc.setDiemCC(cc);
        dc.setDiemUtxt(utxt);
        dc.setDiemTong(tong);
        
        if(diemCongB.insert(dc) == 1) {
            JOptionPane.showMessageDialog(this, "Thêm điểm cộng thành công","Thông báo",JOptionPane.INFORMATION_MESSAGE);
            diemCongPanel.dataTable(diemCongB.getList());
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "dc_keys đã tồn tại","Thông báo",JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_btn_luuActionPerformed

    private void btn_tinhdiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_tinhdiemActionPerformed
        String cccd = cbb_cccd.getSelectedItem().toString();
        String tenNganh = cbb_manganh.getSelectedItem().toString();
        HashMap<String,String> mapNganh = ngB.getMaNganhByTenNganhMap();
        String maNganh = mapNganh.get(tenNganh);
        String maToHop = cbb_matohop.getSelectedItem().toString();
        BigDecimal cc = getBigDecimal(field_cc);
        BigDecimal utxt = getBigDecimal(field_utxt);
        // kiểm tra chọn
        if(cc == null) cc = BigDecimal.ZERO;
        if(utxt == null) utxt = BigDecimal.ZERO;
        // phải chọn CCCD
        if(cccd.equals("Chọn CCCD")){
            JOptionPane.showMessageDialog(this,
                    "Vui lòng chọn CCCD");
            return;
        }

        String dckeys = "";
        // =========================
        // TRƯỜNG HỢP CHỈ CÓ CC
        // =========================
        if(cc.compareTo(BigDecimal.ZERO) > 0
                && utxt.compareTo(BigDecimal.ZERO) == 0){

            dckeys = cccd + "_CC";

            // khóa combobox

        } else {

            // =========================
            // CÓ UTXT hoặc CC + UTXT
            // =========================

            if(tenNganh.equals("Chọn tên ngành")
                    || maToHop.equals("Chọn Mã Tổ Hợp")){

                JOptionPane.showMessageDialog(this,
                        "Vui lòng chọn mã ngành và mã tổ hợp");
                return;
            }

            dckeys = cccd + "_" + maNganh + "_" + maToHop;
        }
        BigDecimal tong = cc.add(utxt);
        field_diemtong.setText(tong.toString());
        field_dckeys.setText(dckeys);
    }//GEN-LAST:event_btn_tinhdiemActionPerformed

    private void field_ccKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_field_ccKeyReleased
        checkInputMode();
    }//GEN-LAST:event_field_ccKeyReleased

    private void field_utxtKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_field_utxtKeyReleased
        checkInputMode();
    }//GEN-LAST:event_field_utxtKeyReleased

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_huy;
    private javax.swing.JButton btn_lammoi;
    private javax.swing.JButton btn_luu;
    private javax.swing.JButton btn_tinhdiem;
    private javax.swing.JComboBox<String> cbb_cccd;
    private javax.swing.JComboBox<String> cbb_manganh;
    private javax.swing.JComboBox<String> cbb_matohop;
    private javax.swing.JComboBox<String> cbb_phuongthuc;
    private javax.swing.JTextField field_cc;
    private javax.swing.JTextField field_dckeys;
    private javax.swing.JTextField field_diemtong;
    private javax.swing.JTextField field_utxt;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea textArea_ghichu;
    // End of variables declaration//GEN-END:variables
}
