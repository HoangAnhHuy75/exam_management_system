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
public class EditDiemThiDialog extends javax.swing.JDialog {
    
    DiemThiPanel diemThiPanel;
    DiemThiBUS diemThiBus = new DiemThiBUS();
    Combobox_design cbb_design = new Combobox_design();
    private DiemThiDTO dtDTO;
    public EditDiemThiDialog(java.awt.Frame parent, boolean modal, DiemThiPanel diemThiPanel,DiemThiDTO dtDTO) {
        super(parent, modal);
        initComponents();
        this.diemThiPanel = diemThiPanel;
        this.setLocationRelativeTo(null);
        this.setTitle("Sửa điểm thi");
        this.dtDTO = dtDTO;
        khoiTao();
    }
    
    public void khoiTao(){
        setUpCombobox();
        loadPhuongThucToComboBox();
        loadCccdToComboBox();
        loadSbdToCombobox("");
        loadData();
    }
    public String getText(BigDecimal value) {
        return value == null ? "" : value.toString();
    }
    public void loadData() {
        cbb_phuongthuc.setSelectedItem(dtDTO.getD_phuongthuc());
        cbb_sbd.setSelectedItem(dtDTO.getSobaodanh());
        field_to.setText(getText(dtDTO.getTO()));
        field_li.setText(getText(dtDTO.getLI()));
        field_ho.setText(getText(dtDTO.getHO()));
        field_si.setText(getText(dtDTO.getSI()));
        field_su.setText(getText(dtDTO.getSU()));
        field_dia.setText(getText(dtDTO.getDI()));
        field_va.setText(getText(dtDTO.getVA()));
        field_gdcd.setText(getText(dtDTO.getGDCD()));
        field_cncn.setText(getText(dtDTO.getCNCN()));
        field_cnnn.setText(getText(dtDTO.getCNNN()));
        field_ti.setText(getText(dtDTO.getTI()));
        field_ktpl.setText(getText(dtDTO.getKTPL()));
        field_nk1.setText(getText(dtDTO.getNK1()));
        field_nk2.setText(getText(dtDTO.getNK2()));
        field_nk3.setText(getText(dtDTO.getNK3()));
        field_nk4.setText(getText(dtDTO.getNK4()));
        field_nk5.setText(getText(dtDTO.getNK5()));
        field_nk6.setText(getText(dtDTO.getNK6()));
        field_nl1.setText(getText(dtDTO.getNL1()));
        field_n1cc.setText(getText(dtDTO.getN1_CC()));
        field_n1thi.setText(getText(dtDTO.getN1_THI()));
        field_dotthi.setText(dtDTO.getDotthi());
        cbb_cccd.setEnabled(false);
        cbb_phuongthuc.setEnabled(false);
        cbb_sbd.setEnabled(false);
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

        List<String> list = diemThiBus.loadCbbCccd();
        for(String cccd : list) {
            cbb_cccd.addItem(cccd);
        }
        cbb_cccd.setSelectedItem(dtDTO.getCccd());
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
     public void loadSbdToCombobox(String cccd) {
        cbb_sbd.removeAllItems();
        cbb_sbd.addItem("");
        List<String> list = diemThiBus.loadCbbSBD(cccd);
        for(String sbd : list) {
            cbb_sbd.addItem(sbd);
        }
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
        jLabel26 = new javax.swing.JLabel();
        field_dotthi = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        cbb_phuongthuc = new javax.swing.JComboBox<>();
        cbb_cccd = new javax.swing.JComboBox<>();
        cbb_sbd = new javax.swing.JComboBox<>();
        btn_reset = new javax.swing.JButton();
        btn_huy = new javax.swing.JButton();
        btn_sua = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        field_nk1 = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        field_nk2 = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        field_nk3 = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        field_nk4 = new javax.swing.JTextField();
        field_nk5 = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        field_nk6 = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(224, 224, 224));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setText("Sửa điểm thi tuyển sinh");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel1)
                .addContainerGap(27, Short.MAX_VALUE))
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
                        .addComponent(jLabel21)
                        .addGap(9, 9, 9)
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
                .addGap(27, 27, 27)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(field_gdcd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(12, Short.MAX_VALUE))
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
                .addContainerGap()
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
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel19)
                        .addGap(26, 26, 26)
                        .addComponent(field_n1cc, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(field_nl1, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel26)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(field_dotthi, javax.swing.GroupLayout.DEFAULT_SIZE, 128, Short.MAX_VALUE)
                            .addComponent(field_n1thi, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(16, 16, 16))
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
                .addGap(27, 27, 27)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(field_nl1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(field_n1thi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel26)
                    .addComponent(field_dotthi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(31, 31, 31)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(field_n1cc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel20))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbb_sbd, 0, 175, Short.MAX_VALUE)
                            .addComponent(cbb_phuongthuc, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(cbb_cccd, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(18, 18, 18))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(cbb_cccd, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(cbb_sbd, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20)
                    .addComponent(cbb_phuongthuc, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(19, Short.MAX_VALUE))
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

        btn_sua.setBackground(new java.awt.Color(102, 255, 102));
        btn_sua.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btn_sua.setForeground(new java.awt.Color(255, 255, 255));
        btn_sua.setText("Lưu");
        btn_sua.addActionListener(this::btn_suaActionPerformed);

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Điểm năng khiếu", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N

        jLabel15.setText("NK1");

        jLabel22.setText("NK2");

        field_nk2.addActionListener(this::field_nk2ActionPerformed);

        jLabel23.setText("NK3");

        jLabel24.setText("NK4");

        jLabel16.setText("NK5");

        jLabel25.setText("NK6");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(field_nk1, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(35, 35, 35)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGap(30, 30, 30)
                                .addComponent(field_nk2, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(field_nk5, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(6, 6, 6)
                                .addComponent(field_nk3, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(35, 35, 35)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGap(30, 30, 30)
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(field_nk6, javax.swing.GroupLayout.DEFAULT_SIZE, 107, Short.MAX_VALUE)
                                    .addComponent(field_nk4))))))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jLabel15))
                    .addComponent(field_nk1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(field_nk2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel22))))
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(jLabel23))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(field_nk3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel24)
                    .addComponent(field_nk4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel16)
                    .addComponent(field_nk5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(field_nk6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel25))
                .addContainerGap(10, Short.MAX_VALUE))
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
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(39, 39, 39)
                        .addComponent(btn_reset, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btn_huy, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btn_sua, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 13, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(13, 13, 13)
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btn_huy, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
                            .addComponent(btn_reset, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btn_sua, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 23, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_resetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_resetActionPerformed
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
        cbb_sbd.setSelectedItem(dtDTO.getCccd());
        cbb_cccd.setSelectedItem(dtDTO.getCccd());
    }//GEN-LAST:event_btn_resetActionPerformed

    private void btn_huyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_huyActionPerformed
        this.dispose();
    }//GEN-LAST:event_btn_huyActionPerformed

    private void btn_suaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_suaActionPerformed

        String cccd = cbb_cccd.getSelectedItem() == null? "": cbb_cccd.getSelectedItem().toString();

//    String sbd = cbb_sbd.getSelectedItem() == null
//            ? ""
//            : cbb_sbd.getSelectedItem().toString();

    String phuongThuc = cbb_phuongthuc.getSelectedItem() == null ? "": cbb_phuongthuc.getSelectedItem().toString();

    // ===== VALIDATE THÔNG TIN =====
    if (cccd.isEmpty() || phuongThuc.isEmpty()) {
        JOptionPane.showMessageDialog(this,"Vui lòng chọn đầy đủ thông tin thí sinh");
        return;
    }

    // ===== LẤY ĐIỂM =====
    BigDecimal to = getBigDecimal(field_to);
    BigDecimal li = getBigDecimal(field_li);
    BigDecimal ho = getBigDecimal(field_ho);
    BigDecimal si = getBigDecimal(field_si);
    BigDecimal su = getBigDecimal(field_su);
    BigDecimal dia = getBigDecimal(field_dia);
    BigDecimal va = getBigDecimal(field_va);
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

    BigDecimal nl1 = field_nl1.getText().trim().isEmpty()
            ? BigDecimal.ZERO
            : getBigDecimal(field_nl1);

        BigDecimal n1Thi = field_n1thi.getText().trim().isEmpty()
                ? BigDecimal.ZERO
                : getBigDecimal(field_n1thi);

        BigDecimal n1Cc = field_n1cc.getText().trim().isEmpty()
                ? BigDecimal.ZERO
                : getBigDecimal(field_n1cc);

        // ===== CHECK NULL =====
        List<BigDecimal> diemList = Arrays.asList(to, li, ho, si, su, dia, va, gdcd,cncn, cnnn, ti, ktpl, nk1, nk2, nk3, nk4, nk5, nk6,nl1, n1Thi, n1Cc);

        for (BigDecimal diem : diemList) {
            if (diem == null) {
                return;
            }
            if (diem.compareTo(BigDecimal.ZERO) < 0) {
                JOptionPane.showMessageDialog(this,"Điểm không được âm");
                return;
            }
        }

        // ===== VALIDATE ĐỢT THI =====
        String dotThi = field_dotthi.getText().trim();
        if (phuongThuc.equals("VSAT")) {
            if (dotThi.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đợt thi");
                return;
            }
        }

        // ===== VALIDATE THEO PHƯƠNG THỨC =====
        BigDecimal max10 = new BigDecimal("10");
        BigDecimal max150 = new BigDecimal("150");
        BigDecimal max1200 = new BigDecimal("1200");

        // ---------- THPT ----------
        if (phuongThuc.equals("THPT")) {
            for (BigDecimal diem : diemList) {
                if (diem.compareTo(max10) > 0) {
                    JOptionPane.showMessageDialog(this,"Điểm THPT phải từ 0 đến 10");
                    return;
                }
            }
        } // ---------- VSAT ----------
        else if (phuongThuc.equals("VSAT")) {
            List<BigDecimal> vsatList = Arrays.asList(to, li, ho, si, su, dia, va, n1Thi);
            for (BigDecimal diem : vsatList) {
                if (diem.compareTo(max150) > 0) {
                    JOptionPane.showMessageDialog(this,"Điểm VSAT phải từ 0 đến 150");
                    return;
                }
            }
        } // ---------- ĐGNL ----------
        else if (phuongThuc.equals("ĐGNL")) {
            if (nl1.compareTo(max1200) > 0) {
                JOptionPane.showMessageDialog(this,"Điểm ĐGNL phải từ 0 đến 1200");
                return;
            }
        }

        // ===== CẬP NHẬT DTO =====
        DiemThiDTO dt = dtDTO;
        dt.setIddiemthi(dtDTO.getIddiemthi());
        
        dt.setCccd(cccd);
//    dt.setSobaodanh(sbd);
        dt.setD_phuongthuc(diemThiBus.convertPhuongThuc(phuongThuc));
        
        dt.setTO(to);
        dt.setLI(li);
        dt.setHO(ho);
        dt.setSI(si);
        dt.setSU(su);
        dt.setDI(dia);
        dt.setVA(va);
        dt.setGDCD(gdcd);
        dt.setN1_THI(n1Thi);
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
        
        if (phuongThuc.equals("ĐGNL")) {
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
        
        if (phuongThuc.equals("THPT")) {
            
            if (!field_n1cc.getText().trim().isEmpty()) {
                dt.setN1_CC(n1Cc);
            } else {
                dt.setN1_CC(null);
            }
            
        } else {
            dt.setN1_CC(null);
        }

        // ===== ĐỢT THI =====
        if (phuongThuc.equals("VSAT")) {
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

        // ===== UPDATE =====
        int result = diemThiBus.update(dt);
        
        if (result == 1) {
            
            JOptionPane.showMessageDialog(this,
                    "Sửa điểm thành công",
                    "Thông báo",
                    JOptionPane.INFORMATION_MESSAGE);
            
            diemThiPanel.dataTable(diemThiBus.getList());
            
            this.dispose();
            
        } else {
            
            JOptionPane.showMessageDialog(this,
                    "Dữ liệu đã tồn tại",
                    "Thông báo",
                    JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_btn_suaActionPerformed

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

        switch (pt) {

            // ================= THPT =================
            case "THPT":

                setEnableFields(
                        true, true, true,
                        true, true, true,
                        true, true,
                        true, true,
                        true, true,
                        true, true,
                        true, true,
                        true, true,
                        false, // NL1
                        true, // N1_THI
                        false // DOTTHI
                );

                field_dotthi.setText("");
                field_nl1.setText("");

                break;

            // ================= ĐGNL =================
            case "ĐGNL":

                setEnableFields(
                        false, false, false,
                        false, false, false,
                        false, false,
                        false, false,
                        false, false,
                        false, false,
                        false, false,
                        false, false,
                        true, // NL1
                        false, // N1_THI
                        false // DOTTHI
                );

                break;

            // ================= VSAT =================
            case "VSAT":

                setEnableFields(
                        true, true, true,
                        true, true, true,
                        true, false, // VA true, GDCD false

                        false, false,
                        false, false,
                        false, false,
                        false, false,
                        false, false,
                        false, // NL1
                        true, // N1_THI
                        true // DOTTHI
                );

                field_nl1.setText("");

                break;

            // ================= Tuyển thẳng =================
            case "Tuyển thẳng":
                break;
        }
    }//GEN-LAST:event_cbb_phuongthucActionPerformed

    private void cbb_cccdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbb_cccdActionPerformed
        if(cbb_cccd.getSelectedItem() == null) return;

        String cccd = cbb_cccd.getSelectedItem().toString();

        if(cccd.equals("Chọn CCCD"))
        {
            cbb_sbd.removeAllItems();
            cbb_sbd.addItem("Chọn SBD");
            return;
        }

        loadSbdToCombobox(cccd);
    }//GEN-LAST:event_cbb_cccdActionPerformed

    private void field_nk2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_field_nk2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_field_nk2ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_huy;
    private javax.swing.JButton btn_reset;
    private javax.swing.JButton btn_sua;
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
