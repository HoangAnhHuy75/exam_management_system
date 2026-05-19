package GUIDialog;

import BUS.DiemThiBUS;
import DTO.DiemThiDTO;
import GUI.Panel.DiemThiPanel;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import util.Combobox_design;

/**
 *
 * @author HP
 */
public class AddDiemThiDialog extends javax.swing.JDialog {
    
    DiemThiPanel diemThiPanel;
    DiemThiBUS diemThiBus = new DiemThiBUS();
    Combobox_design cbb_design = new Combobox_design();
    public AddDiemThiDialog(java.awt.Frame parent, boolean modal, DiemThiPanel diemThiPanel) {
        super(parent, modal);
        initComponents();
        this.diemThiPanel = diemThiPanel;
        this.setLocationRelativeTo(null);
        this.setTitle("Thêm điểm thi");
        khoiTao();
    }
    
    public void khoiTao(){
        setUpCombobox();
        loadPhuongThucToComboBox();
        loadCccdToComboBox();
        loadSbdToCombobox("");
    }
    public void setUpCombobox(){
        cbb_design.setUpComBoBox(cbb_phuongthuc);
        cbb_design.setUpComBoBox(cbb_cccd);
        cbb_design.setUpComBoBox(cbb_sbd);
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
        List<String> list = diemThiBus.loadCbbCccd();
        for(String cccd : list) {
            cbb_cccd.addItem(cccd);
        }
    }
    public void loadSbdToCombobox(String cccd) {
        cbb_sbd.removeAllItems();
        cbb_sbd.addItem("");
        List<String> list = diemThiBus.loadCbbSBD(cccd);
        for(String sbd : list) {
            cbb_sbd.addItem(sbd);
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
    public void setEnableFields(
        boolean to, boolean li, boolean ho,
        boolean si, boolean su, boolean dia,
        boolean va, boolean gdcd,
        boolean cncn, boolean cnnn,
        boolean ti, boolean ktpl,
        boolean nk1, boolean nk2,
        boolean nk3, boolean nk4,
        boolean nk5, boolean nk6,
        boolean nl1,
        boolean n1_thi,
        boolean dotthi
    ) {
        field_to.setEnabled(to);
        field_li.setEnabled(li);
        field_ho.setEnabled(ho);
        field_si.setEnabled(si);
        field_su.setEnabled(su);
        field_dia.setEnabled(dia);
        field_va.setEnabled(va);
        field_gdcd.setEnabled(gdcd);
        field_cncn.setEnabled(cncn);
        field_cnnn.setEnabled(cnnn);
        field_ti.setEnabled(ti);
        field_ktpl.setEnabled(ktpl);

        field_nk1.setEnabled(nk1);
        field_nk2.setEnabled(nk2);
        field_nk3.setEnabled(nk3);
        field_nk4.setEnabled(nk4);
        field_nk5.setEnabled(nk5);
        field_nk6.setEnabled(nk6);

        field_nl1.setEnabled(nl1);

        field_n1thi.setEnabled(n1_thi);
        field_dotthi.setEnabled(dotthi);
    }
    public void clearFields() {
        field_to.setText("");
        field_li.setText("");
        field_ho.setText("");
        field_si.setText("");
        field_su.setText("");
        field_dia.setText("");
        field_va.setText("");
        field_gdcd.setText("");
        field_cncn.setText("");
        field_cnnn.setText("");
        field_ti.setText("");
        field_ktpl.setText("");
        field_nk1.setText("");
        field_nk2.setText("");
        field_nk3.setText("");
        field_nk4.setText("");
        field_nk5.setText("");
        field_nk6.setText("");
        field_nl1.setText("");
        field_n1thi.setText("");
        field_dotthi.setText("");
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        field_to = new javax.swing.JTextField();
        field_ho = new javax.swing.JTextField();
        field_si = new javax.swing.JTextField();
        field_su = new javax.swing.JTextField();
        field_li = new javax.swing.JTextField();
        field_va = new javax.swing.JTextField();
        field_dia = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        field_gdcd = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        field_cnnn = new javax.swing.JTextField();
        field_ti = new javax.swing.JTextField();
        field_cncn = new javax.swing.JTextField();
        field_ktpl = new javax.swing.JTextField();
        field_nl1 = new javax.swing.JTextField();
        field_n1thi = new javax.swing.JTextField();
        field_n1cc = new javax.swing.JTextField();
        field_dotthi = new javax.swing.JTextField();
        jLabel26 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        cbb_phuongthuc = new javax.swing.JComboBox<>();
        cbb_cccd = new javax.swing.JComboBox<>();
        cbb_sbd = new javax.swing.JComboBox<>();
        btn_reset = new javax.swing.JButton();
        btn_huy = new javax.swing.JButton();
        btn_them = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        field_nk1 = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        field_nk2 = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        field_nk3 = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        field_nk4 = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        field_nk5 = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        field_nk6 = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));

        jPanel1.setBackground(new java.awt.Color(224, 224, 224));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setText("Thêm điểm thi tuyển sinh");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(284, 284, 284))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel1)
                .addContainerGap(24, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Điểm môn học chính & VSAT", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N

        jLabel4.setText("TO");

        jLabel5.setText("LI");

        jLabel6.setText("HO");

        jLabel8.setText("SU");

        jLabel7.setText("SI");

        jLabel9.setText("DIA");

        jLabel10.setText("VA");

        jLabel21.setText("GDCD");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(field_to, javax.swing.GroupLayout.DEFAULT_SIZE, 128, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(field_li))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(field_ho))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(field_si))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(field_su))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(field_va, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(field_dia))))
                        .addGap(14, 14, 14))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(field_gdcd)
                        .addGap(15, 15, 15))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(field_to, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))
                        .addGap(22, 22, 22)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(field_li, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(26, 26, 26)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(field_ho, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(27, 27, 27)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(field_si, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7))
                        .addGap(56, 56, 56))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(field_su, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel8)))
                .addGap(29, 29, 29)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(field_dia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(field_va, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(field_gdcd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel21))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Điêm môn học khác", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N

        jLabel11.setText("CNCN");

        jLabel12.setText("CNNN");

        jLabel13.setText("TI");

        jLabel14.setText("KTPL");

        jLabel17.setText("NL1");

        jLabel18.setText("N1_THI");

        jLabel19.setText("N1_CC");

        field_n1cc.setEditable(false);

        jLabel26.setText("Đợt thi");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(field_cncn, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(field_ti, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(field_cnnn, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(field_ktpl, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(jPanel4Layout.createSequentialGroup()
                            .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(field_n1thi, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel4Layout.createSequentialGroup()
                            .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(field_nl1, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel4Layout.createSequentialGroup()
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(jPanel4Layout.createSequentialGroup()
                                    .addComponent(jLabel19)
                                    .addGap(26, 26, 26))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                                    .addComponent(jLabel26)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(field_dotthi, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 127, Short.MAX_VALUE)
                                .addComponent(field_n1cc, javax.swing.GroupLayout.Alignment.TRAILING)))))
                .addContainerGap(11, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(field_cncn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(17, 17, 17)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(field_cnnn))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(field_ti, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(field_ktpl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14))
                .addGap(23, 23, 23)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(field_nl1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(field_n1thi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(field_n1cc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(field_dotthi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel26))
                .addGap(55, 55, 55))
        );

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Thông tin thí sinh", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N

        jLabel2.setText("Căn cước công dân");

        jLabel3.setText("Số báo danh");

        jLabel20.setText("Phương thức");

        cbb_phuongthuc.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbb_phuongthuc.addActionListener(this::cbb_phuongthucActionPerformed);

        cbb_cccd.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbb_cccd.addActionListener(this::cbb_cccdActionPerformed);

        cbb_sbd.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel20))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbb_sbd, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cbb_phuongthuc, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbb_cccd, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(15, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbb_cccd, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbb_sbd, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbb_phuongthuc, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel20))
                .addContainerGap(23, Short.MAX_VALUE))
        );

        btn_reset.setBackground(new java.awt.Color(51, 153, 255));
        btn_reset.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btn_reset.setForeground(new java.awt.Color(255, 255, 255));
        btn_reset.setText("Làm mới");
        btn_reset.addActionListener(this::btn_resetActionPerformed);

        btn_huy.setBackground(new java.awt.Color(255, 51, 51));
        btn_huy.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btn_huy.setForeground(new java.awt.Color(255, 255, 255));
        btn_huy.setText("Hủy");
        btn_huy.addActionListener(this::btn_huyActionPerformed);

        btn_them.setBackground(new java.awt.Color(102, 255, 102));
        btn_them.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btn_them.setForeground(new java.awt.Color(255, 255, 255));
        btn_them.setText("Lưu");
        btn_them.addActionListener(this::btn_themActionPerformed);

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Điểm năng khiếu", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N

        jLabel15.setText("NK1");

        jLabel16.setText("NK2");

        jLabel22.setText("NK3");

        jLabel23.setText("NK4");

        jLabel24.setText("NK5");

        jLabel25.setText("NK6");

        field_nk6.addActionListener(this::field_nk6ActionPerformed);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel24, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(field_nk1, javax.swing.GroupLayout.DEFAULT_SIZE, 105, Short.MAX_VALUE)
                    .addComponent(field_nk3)
                    .addComponent(field_nk5))
                .addGap(38, 38, 38)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel23, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel25, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(field_nk2, javax.swing.GroupLayout.DEFAULT_SIZE, 114, Short.MAX_VALUE)
                    .addComponent(field_nk4)
                    .addComponent(field_nk6))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(field_nk1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16)
                    .addComponent(field_nk2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel22)
                    .addComponent(field_nk3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel23)
                    .addComponent(field_nk4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel24)
                    .addComponent(field_nk5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel25)
                    .addComponent(field_nk6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(17, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(37, 37, 37)
                        .addComponent(btn_reset, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btn_huy, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btn_them, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(11, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(34, 34, 34)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btn_reset, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_huy, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_them, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(34, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_resetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_resetActionPerformed
        cbb_cccd.setSelectedIndex(0);
        cbb_sbd.setSelectedIndex(0);
        cbb_phuongthuc.setSelectedIndex(0);

        field_to.setText("");
        field_li.setText("");
        field_ho.setText("");
        field_si.setText("");
        field_su.setText("");
        field_dia.setText("");
        field_va.setText("");
        field_gdcd.setText("");
        field_cncn.setText("");
        field_cnnn.setText("");
        field_ti.setText("");
        field_ktpl.setText("");
        field_nk1.setText("");
        field_nk2.setText("");
        field_nk3.setText("");
        field_nk4.setText("");
        field_nk5.setText("");
        field_nk6.setText("");
        field_nl1.setText("");
        field_n1thi.setText("");
        field_n1cc.setText("");
        field_dotthi.setText("");
        cbb_cccd.requestFocus();
    }//GEN-LAST:event_btn_resetActionPerformed

    private void btn_huyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_huyActionPerformed
        this.dispose();
    }//GEN-LAST:event_btn_huyActionPerformed

    private void btn_themActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_themActionPerformed
//        String Cccd = cbb_cccd.getSelectedItem().toString();
//        String Sbd = cbb_sbd.getSelectedItem().toString();
//        String phuongthucText = cbb_phuongthuc.getSelectedItem().toString();
//        String phuongthuc = phuongthucText;
//        BigDecimal to = getBigDecimal(field_to);
//        BigDecimal li = getBigDecimal(field_li);
//        BigDecimal ho = getBigDecimal(field_ho);
//        BigDecimal si = getBigDecimal(field_si);
//        BigDecimal su = getBigDecimal(field_su);
//        BigDecimal dia = getBigDecimal(field_dia);
//        BigDecimal va  = getBigDecimal(field_va);
//        BigDecimal gdcd = getBigDecimal(field_gdcd);
//        BigDecimal cncn = getBigDecimal(field_cncn);
//        BigDecimal cnnn = getBigDecimal(field_cnnn);
//        BigDecimal ti = getBigDecimal(field_ti);
//        BigDecimal ktpl = getBigDecimal(field_ktpl);
//        BigDecimal nk1 = getBigDecimal(field_nk1);
//        BigDecimal nk2 = getBigDecimal(field_nk2);
//        BigDecimal nk3 = getBigDecimal(field_nk3);
//        BigDecimal nk4 = getBigDecimal(field_nk4);
//        BigDecimal nk5 = getBigDecimal(field_nk5);
//        BigDecimal nk6 = getBigDecimal(field_nk6);
//        BigDecimal nl1 = getBigDecimal(field_nl1);
//        BigDecimal n1_thi = getBigDecimal(field_n1thi);
//        BigDecimal n1_cc = getBigDecimal(field_n1cc);
//        Integer dotThi = null;
//
//        if (!field_dotthi.getText().trim().isEmpty()) {
//            dotThi = Integer.parseInt(field_dotthi.getText().trim());
//        }
//        // check rỗng
//        if (Cccd.equals("Chọn CCCD") || phuongthuc == null) {
//            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin thí sinh");
//            return;
//        }
//        if (!phuongthuc.equals("THPT")) {
//
//            if (dotThi <= 0) {
//                JOptionPane.showMessageDialog(this,
//                        "Đợt thi phải lớn hơn 0");
//                return;
//            }
//        }
//        if (phuongthuc.equals("ĐGNL")) {
//
//            if (dotThi > 2) {
//                JOptionPane.showMessageDialog(this,
//                        "ĐGNL chỉ có đợt 1 hoặc đợt 2");
//                return;
//            }
//        }
//        if (!phuongthuc.equals("THPT") && field_dotthi.getText().trim().isEmpty()) {
//
//            JOptionPane.showMessageDialog(this,
//                    "Vui lòng nhập đợt thi");
//            return;
//        }
//
        ////        if (!Cccd.matches("\\d{12}")) {
////        JOptionPane.showMessageDialog(this, "CCCD phải gồm 12 chữ số");
////        return;
////        }
//        List<BigDecimal> diemList = Arrays.asList(
//                to, li, ho, si, su, dia, va,gdcd,
//                 cncn, cnnn, ti, ktpl, nk1, nk2, nk3, nk4, nk5, nk6, n1_thi, n1_cc
//        );
//
//        // check âm
//        for (BigDecimal diem : diemList) {
//            if (diem == null) {
//                continue;
//            }
//            if (diem.compareTo(BigDecimal.ZERO) < 0) {
//                JOptionPane.showMessageDialog(this, "Điểm không được âm");
//                return;
//            } else if (diem == null) {
//                return;
//            }
//        }
//
//        // check >10
//        BigDecimal max = new BigDecimal("10");
//        BigDecimal max150 = new BigDecimal("150");
//        BigDecimal max1200 = new BigDecimal("1200");
//
//        if (phuongthuc.equals("THPT")) {
//
//            for (BigDecimal diem : diemList) {
//                if (diem.compareTo(max) > 0) {
//                    JOptionPane.showMessageDialog(this,
//                            "Điểm THPT phải từ 0 đến 10");
//                    return;
//                }
//            }
//        } else if (phuongthuc.equals("VSAT")) {
//
//            List<BigDecimal> vsatList = Arrays.asList(
//                    to, li, ho, si, su, dia, va, n1_thi
//            );
//
//            for (BigDecimal diem : vsatList) {
//                if (diem.compareTo(max150) > 0) {
//                    JOptionPane.showMessageDialog(this,
//                            "Điểm VSAT phải từ 0 đến 150");
//                    return;
//                }
//            }
//        } else if (phuongthuc.equals("ĐGNL")) {
//
//            if (nl1.compareTo(max1200) > 0) {
//                JOptionPane.showMessageDialog(this,
//                        "Điểm ĐGNL phải từ 0 đến 1200");
//                return;
//            }
//        }
//
//        // thêm dữ liệu
//        DiemThiDTO dt = new DiemThiDTO();
//        dt.setCccd(Cccd);
//        dt.setSobaodanh(Sbd);
//        dt.setD_phuongthuc(phuongthuc);
//        dt.setTO(to);
//        dt.setLI(li);
//        dt.setHO(ho);
//        dt.setSI(si);
//        dt.setSU(su);
//        dt.setDI(dia);
//        dt.setVA(va);
//        dt.setGDCD(gdcd);
//        dt.setCNCN(cncn);
//        dt.setCNNN(cnnn);
//        dt.setTI(ti);
//        dt.setKTPL(ktpl);
//        dt.setNK1(nk1);
//        dt.setNK2(nk2);
//        dt.setNK3(nk3);
//        dt.setNK4(nk4);
//        dt.setNK5(nk5);
//        dt.setNK6(nk6);
//        dt.setNL1(nl1);
//        dt.setN1_THI(n1_thi);
//        // THPT không có đợt thi
//        if (phuongthuc.equals("THPT")) {
//            dt.setDotthi(null);
//        } else if (phuongthuc.equals("ĐGNL")) {
//            dt.setDotthi(null);
//        } else {
//            dt.setDotthi(String.valueOf(dotThi));
//        }
//        if (diemThiBus.insert(dt) == 1) {
//            JOptionPane.showMessageDialog(this, "Thêm điểm thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
//            diemThiPanel.dataTable(diemThiBus.getList());
//            this.dispose();
//        } else {
//            JOptionPane.showMessageDialog(this, "Thí sinh đã có điểm thi", "Lỗi", JOptionPane.WARNING_MESSAGE);
//        }
        String Cccd = cbb_cccd.getSelectedItem().toString();
        String Sbd = cbb_sbd.getSelectedItem().toString();
        String phuongthuc = cbb_phuongthuc.getSelectedItem().toString();

        BigDecimal to = getBigDecimal(field_to);
        BigDecimal li = getBigDecimal(field_li);
        BigDecimal ho = getBigDecimal(field_ho);
        BigDecimal si = getBigDecimal(field_si);
        BigDecimal su = getBigDecimal(field_su);
        BigDecimal dia = getBigDecimal(field_dia);
        BigDecimal va  = getBigDecimal(field_va);
        BigDecimal gdcd = getBigDecimal(field_gdcd);

        BigDecimal cncn = getBigDecimal(field_cncn);
        BigDecimal cnnn = getBigDecimal(field_cnnn);
        BigDecimal ti = getBigDecimal(field_ti);
        BigDecimal ktpl = getBigDecimal(field_ktpl);

        BigDecimal nk1 = getBigDecimal(field_nk1);
        BigDecimal nk2 = getBigDecimal(field_nk2);
        BigDecimal nk3 = getBigDecimal(field_nk3);
        BigDecimal nk4 = getBigDecimal(field_nk4);
        BigDecimal nk5 = getBigDecimal(field_nk5);
        BigDecimal nk6 = getBigDecimal(field_nk6);

        BigDecimal nl1 = getBigDecimal(field_nl1);

        BigDecimal n1_thi = getBigDecimal(field_n1thi);
        BigDecimal n1_cc = getBigDecimal(field_n1cc);

        String dotThi = field_dotthi.getText().trim();
        if (phuongthuc.equals("VSAT")) {
            if (dotThi.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đợt thi");
                return;
            }
        }

        // check thông tin cơ bản
        if (Cccd.equals("Chọn CCCD")) {
            JOptionPane.showMessageDialog(this,
                    "Vui lòng chọn CCCD");
            return;
        }


        List<BigDecimal> diemList = Arrays.asList(to, li, ho, si, su, dia, va, gdcd,cncn, cnnn, ti, ktpl,nk1, nk2, nk3, nk4, nk5, nk6,nl1, n1_thi, n1_cc);

        // check điểm âm
        for (BigDecimal diem : diemList) {

            if (diem == null) {
                return;
            }

            if (diem.compareTo(BigDecimal.ZERO) < 0) {
                JOptionPane.showMessageDialog(this,
                        "Điểm không được âm");
                return;
            }
        }

        BigDecimal max10 = new BigDecimal("10");
        BigDecimal max150 = new BigDecimal("150");
        BigDecimal max1200 = new BigDecimal("1200");

        // THPT
        if (phuongthuc.equals("THPT")) {

            for (BigDecimal diem : diemList) {

                if (diem.compareTo(max10) > 0) {
                    JOptionPane.showMessageDialog(this,
                            "Điểm THPT phải từ 0 đến 10");
                    return;
                }
            }
        } // VSAT
        else if (phuongthuc.equals("VSAT")) {

            List<BigDecimal> vsatList = Arrays.asList(
                    to, li, ho, si, su, dia, va, n1_thi
            );

            for (BigDecimal diem : vsatList) {

                if (diem.compareTo(max150) > 0) {
                    JOptionPane.showMessageDialog(this,
                            "Điểm VSAT phải từ 0 đến 150");
                    return;
                }
            }
        } // ĐGNL
        else if (phuongthuc.equals("ĐGNL")) {

            if (nl1.compareTo(max1200) > 0) {
                JOptionPane.showMessageDialog(this,
                        "Điểm ĐGNL phải từ 0 đến 1200");
                return;
            }
        }

        // tạo DTO
        DiemThiDTO dt = new DiemThiDTO();

        dt.setCccd(Cccd);
        dt.setSobaodanh(Sbd);
        dt.setD_phuongthuc(phuongthuc);

        dt.setTO(to);
        dt.setLI(li);
        dt.setHO(ho);
        dt.setSI(si);
        dt.setSU(su);
        dt.setDI(dia);
        dt.setVA(va);
        dt.setGDCD(gdcd);
        dt.setN1_THI(n1_thi);
        dt.setCNCN(cncn);
        dt.setCNNN(cnnn);
        dt.setTI(ti);
        dt.setKTPL(ktpl);

        dt.setNK1(nk1);
        dt.setNK2(nk2);
        dt.setNK3(nk3);
        dt.setNK4(nk4);
        dt.setNK5(nk5);
        dt.setNK6(nk6);

        if (phuongthuc.equals("ĐGNL")) {
            dt.setNL1(nl1);
            dt.setNL1(nl1);
            dt.setTO(null);
            dt.setLI(null);
            dt.setHO(null);
            dt.setSI(null);
            dt.setSU(null);
            dt.setDI(null);
            dt.setVA(null);
            dt.setGDCD(null);
            dt.setN1_THI(null);

            dt.setCNCN(null);
            dt.setCNNN(null);
            dt.setTI(null);
            dt.setKTPL(null);

            dt.setNK1(null);
            dt.setNK2(null);
            dt.setNK3(null);
            dt.setNK4(null);
            dt.setNK5(null);
            dt.setNK6(null);
        } else {
            dt.setNL1(null);
        }

        // THPT: nếu có nhập N1_CC thì mới set
        if (phuongthuc.equals("THPT")) {
            if (!field_n1cc.getText().trim().isEmpty()) {
                dt.setN1_CC(n1_cc);
            } else {
                dt.setN1_CC(null);
            }
        } else {
            dt.setN1_CC(null);
        }

        // chỉ VSAT có đợt thi
        if (phuongthuc.equals("VSAT")) {
             dt.setDotthi(dotThi);
            dt.setGDCD(null);
            
            dt.setCNCN(null);
            dt.setCNNN(null);
            dt.setTI(null);
            dt.setKTPL(null);
            
            dt.setNK1(null);
            dt.setNK2(null);
            dt.setNK3(null);
            dt.setNK4(null);
            dt.setNK5(null);
            dt.setNK6(null);
            dt.setNL1(null);
            dt.setN1_CC(null);
        } else {
            dt.setDotthi(null);
        }

        // insert
        if (diemThiBus.insert(dt) == 1) {

            JOptionPane.showMessageDialog(this,
                    "Thêm điểm thành công",
                    "Thông báo",
                    JOptionPane.INFORMATION_MESSAGE);

            diemThiPanel.dataTable(diemThiBus.getList());

            this.dispose();

        } else {

            JOptionPane.showMessageDialog(this,
                    "Thí sinh đã có điểm thi",
                    "Lỗi",
                    JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_btn_themActionPerformed

    private void cbb_cccdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbb_cccdActionPerformed
        if (cbb_cccd.getSelectedItem() == null) {
            return;
        }
        String cccd = cbb_cccd.getSelectedItem().toString();
        if (cccd.equals("Chọn CCCD")) {
            cbb_sbd.removeAllItems();
            cbb_sbd.addItem("Chọn SBD");
            return;
        }
        loadSbdToCombobox(cccd);
    }//GEN-LAST:event_cbb_cccdActionPerformed

    private void cbb_phuongthucActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbb_phuongthucActionPerformed
        clearFields();
        if (cbb_phuongthuc.getSelectedItem() == null) {
            return;
        }
        String pt = cbb_phuongthuc.getSelectedItem().toString();
        setEnableFields(
                false, false, false,
                false, false, false,
                false, false,
                false, false,
                false, false,
                false, false,
                false, false,
                false, false,
                false,
                false,
                false
        );

        switch(pt) {
            case "THPT":
                setEnableFields(
                    true,true,true,
                    true,true,true,
                    true,true,
                    true,true,
                    true,true,
                    true,true,
                    true,true,
                    true,true,
                    false,
                    true,
                    false
                );
                break;

            case "Tuyển thẳng":
                break;

            case "VSAT":
                field_n1cc.setEnabled(false);
                field_n1cc.setText("");
                setEnableFields(
                        true, true, true,
                        true, true, true,
                        true, false,
                        false, false,
                        false, false,
                        false, false,
                        false, false,
                        false, false,
                        false,
                        true,
                        true
                );
                break;

            case "ĐGNL":
                field_n1cc.setEnabled(false);
                field_n1cc.setText("");
                setEnableFields(
                        false, false, false,
                        false, false, false,
                        false, false,
                        false, false,
                        false, false,
                        false, false,
                        false, false,
                        false, false,
                        true,
                        false,
                        false
                );

                break;
        }
    }//GEN-LAST:event_cbb_phuongthucActionPerformed

    private void field_nk6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_field_nk6ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_field_nk6ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_huy;
    private javax.swing.JButton btn_reset;
    private javax.swing.JButton btn_them;
    private javax.swing.JComboBox<String> cbb_cccd;
    private javax.swing.JComboBox<String> cbb_phuongthuc;
    private javax.swing.JComboBox<String> cbb_sbd;
    private javax.swing.JTextField field_cncn;
    private javax.swing.JTextField field_cnnn;
    private javax.swing.JTextField field_dia;
    private javax.swing.JTextField field_dotthi;
    private javax.swing.JTextField field_gdcd;
    private javax.swing.JTextField field_ho;
    private javax.swing.JTextField field_ktpl;
    private javax.swing.JTextField field_li;
    private javax.swing.JTextField field_n1cc;
    private javax.swing.JTextField field_n1thi;
    private javax.swing.JTextField field_nk1;
    private javax.swing.JTextField field_nk2;
    private javax.swing.JTextField field_nk3;
    private javax.swing.JTextField field_nk4;
    private javax.swing.JTextField field_nk5;
    private javax.swing.JTextField field_nk6;
    private javax.swing.JTextField field_nl1;
    private javax.swing.JTextField field_si;
    private javax.swing.JTextField field_su;
    private javax.swing.JTextField field_ti;
    private javax.swing.JTextField field_to;
    private javax.swing.JTextField field_va;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
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
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    // End of variables declaration//GEN-END:variables
}
