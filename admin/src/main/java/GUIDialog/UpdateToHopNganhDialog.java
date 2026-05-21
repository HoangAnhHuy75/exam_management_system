/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package GUIDialog;

import BUS.NganhBUS;
import BUS.ToHopBUS;
import BUS.ToHopNganhBUS;
import java.math.BigDecimal;
import DTO.ToHopNganhDTO;
import GUI.Panel.ToHopNganhPanel;
import java.util.HashMap;
import util.Combobox_design;

/**
 *
 * @author kiman
 */
public class UpdateToHopNganhDialog extends javax.swing.JDialog {

    ToHopNganhPanel toh_ng_Panel;
    Combobox_design cbb_design = new Combobox_design();
    NganhBUS nganhBus = new NganhBUS();
    ToHopBUS tohopBus = new ToHopBUS();
    ToHopNganhBUS th_ng_bus = new ToHopNganhBUS();
    ToHopNganhDTO toHopNganhDTO;

    /**
     * Creates new form UpdateToHopNganhDialog
     */
    public UpdateToHopNganhDialog(java.awt.Frame parent, boolean modal, ToHopNganhPanel tohopnganhPanel, ToHopNganhDTO toHopNganhDTO) {
        super(parent, modal);
        initComponents();
        this.setTitle("Cập nhật Ngành - Tổ Hợp");
        this.setLocationRelativeTo(null);
        this.toHopNganhDTO = toHopNganhDTO;
        this.toh_ng_Panel = tohopnganhPanel;
        khoiTao();
    }

    public void khoiTao() {
        setUpCBB();
        loadCombobox();
        tudong_check();
        disableAllCheckbox();
        setData();
    }

    public void setData() {
        String tenNganh = nganhBus.getTenNganhByMaNganh(toHopNganhDTO.getManganh());
        major_cbb.setSelectedItem(tenNganh);
        String tenTH = tohopBus.getTenTHByMaTH(toHopNganhDTO.getMatohop());
        combination_cbb.setSelectedItem(tenTH);
        jComboBox1.setSelectedItem(toHopNganhDTO.getTh_mon1());
        jComboBox2.setSelectedItem(toHopNganhDTO.getTh_mon2());
        jComboBox3.setSelectedItem(toHopNganhDTO.getTh_mon3());

        jtf_hs_mon1.setText(String.valueOf(toHopNganhDTO.getHsmon1()));
        jtf_hs_mon2.setText(String.valueOf(toHopNganhDTO.getHsmon2()));
        jtf_hs_mon3.setText(String.valueOf(toHopNganhDTO.getHsmon3()));
    }

    public void loadCombobox() {
        HashMap<String, String> maNganhByTenNganhMap = nganhBus.getMaNganhByTenNganhMap();
        HashMap<String, String> maToHopByTenToHopMap = tohopBus.tohopMap();
        for (String tenNganh : maNganhByTenNganhMap.keySet()) {
            major_cbb.addItem(tenNganh);
        }
        for (String tenTH : maToHopByTenToHopMap.keySet()) {
            combination_cbb.addItem(tenTH);
        }
        jComboBox1.addItem("Chọn môn 1");
        jComboBox2.addItem("Chọn môn 2");
        jComboBox3.addItem("Chọn môn 3");
        String[] subjects = {"N1", "TO", "LI", "HO", "SI", "VA", "SU", "DI", "TI", "KTPL", "CNCN", "CNNN", "NK1", "NK2", "NK3", "NK4", "NK5", "NK6"};
        for (String s : subjects) {
            jComboBox1.addItem(s);
            jComboBox2.addItem(s);
            jComboBox3.addItem(s);
        }
    }

    private void resetCheckbox() {
        jcb_toan.setSelected(false);
        jcb_li.setSelected(false);
        jcb_hoa.setSelected(false);
        jcb_sinh.setSelected(false);
        jcb_van.setSelected(false);
        jcb_anh.setSelected(false);
        jcb_su.setSelected(false);
        jcb_dia.setSelected(false);
        jcb_tin.setSelected(false);
        jcb_ktpl.setSelected(false);
        jcb_khac.setSelected(false);
    }

    private void autoTickSubject(ToHopNganhDTO t, String... mons) {
        for (String mon : mons) {
            if (mon == null) {
                continue;
            }

            switch (mon) {
                case "TO":
                    t.setTO(1);
                    jcb_toan.setSelected(true);
                    break;
                case "LI":
                    t.setLI(1);
                    jcb_li.setSelected(true);
                    break;
                case "HO":
                    t.setHO(1);
                    jcb_hoa.setSelected(true);
                    break;
                case "SI":
                    t.setSI(1);
                    jcb_sinh.setSelected(true);
                    break;
                case "VA":
                    t.setVA(1);
                    jcb_van.setSelected(true);
                    break;
                case "N1":
                    t.setN1(1);
                    jcb_anh.setSelected(true);
                    break;
                case "SU":
                    t.setSU(1);
                    jcb_su.setSelected(true);
                    break;
                case "DI":
                    t.setDI(1);
                    jcb_dia.setSelected(true);
                    break;
                case "TI":
                    t.setTI(1);
                    jcb_tin.setSelected(true);
                    break;
                case "KTPL":
                    t.setKTPL(1);
                    jcb_ktpl.setSelected(true);
                    break;
                case "NK1":
                case "NK2":
                case "NK3":
                case "NK4":
                case "NK5":
                case "NK6":
                    t.setKHAC(1);
                    jcb_khac.setSelected(true);
                    break;
            }
        }
    }

    private void disableAllCheckbox() {
        jcb_toan.setEnabled(false);
        jcb_li.setEnabled(false);
        jcb_hoa.setEnabled(false);
        jcb_sinh.setEnabled(false);
        jcb_van.setEnabled(false);
        jcb_anh.setEnabled(false);
        jcb_su.setEnabled(false);
        jcb_dia.setEnabled(false);
        jcb_tin.setEnabled(false);
        jcb_ktpl.setEnabled(false);
        jcb_khac.setEnabled(false);
    }

    private void updateCheckboxUI() {
        resetCheckbox();

        String mon1 = (String) jComboBox1.getSelectedItem();
        String mon2 = (String) jComboBox2.getSelectedItem();
        String mon3 = (String) jComboBox3.getSelectedItem();

        // chỉ để update UI nên truyền DTO rỗng
        autoTickSubject(new ToHopNganhDTO(), mon1, mon2, mon3);
    }

    public void tudong_check() {
        jComboBox1.addActionListener(e -> updateCheckboxUI());
        jComboBox2.addActionListener(e -> updateCheckboxUI());
        jComboBox3.addActionListener(e -> updateCheckboxUI());
    }

    public void setUpCBB() {
        cbb_design.setUpComBoBox(major_cbb);
        cbb_design.setUpComBoBox(combination_cbb);
        cbb_design.setUpComBoBox(jComboBox1);
        cbb_design.setUpComBoBox(jComboBox2);
        cbb_design.setUpComBoBox(jComboBox3);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        major_cbb = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        combination_cbb = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        jtf_hs_mon1 = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jtf_hs_mon2 = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jtf_hs_mon3 = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jcb_anh = new javax.swing.JCheckBox();
        jPanel4 = new javax.swing.JPanel();
        jcb_khac = new javax.swing.JCheckBox();
        jPanel5 = new javax.swing.JPanel();
        jcb_li = new javax.swing.JCheckBox();
        jPanel6 = new javax.swing.JPanel();
        jcb_sinh = new javax.swing.JCheckBox();
        jPanel7 = new javax.swing.JPanel();
        jcb_hoa = new javax.swing.JCheckBox();
        jPanel8 = new javax.swing.JPanel();
        jcb_toan = new javax.swing.JCheckBox();
        jPanel9 = new javax.swing.JPanel();
        jcb_su = new javax.swing.JCheckBox();
        jPanel10 = new javax.swing.JPanel();
        jcb_dia = new javax.swing.JCheckBox();
        jPanel11 = new javax.swing.JPanel();
        jcb_tin = new javax.swing.JCheckBox();
        jPanel12 = new javax.swing.JPanel();
        jcb_ktpl = new javax.swing.JCheckBox();
        jPanel13 = new javax.swing.JPanel();
        jcb_van = new javax.swing.JCheckBox();
        jComboBox1 = new javax.swing.JComboBox<>();
        jComboBox2 = new javax.swing.JComboBox<>();
        jComboBox3 = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(245, 245, 245));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setText("Thêm Ngành - Tổ Hợp");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(229, 229, 229))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabel1)
                .addContainerGap(24, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(null);

        jLabel3.setText("Ngành");
        jPanel2.add(jLabel3);
        jLabel3.setBounds(60, 20, 36, 16);

        jPanel2.add(major_cbb);
        major_cbb.setBounds(60, 40, 240, 30);

        jLabel4.setText("Tổ hợp");
        jPanel2.add(jLabel4);
        jLabel4.setBounds(380, 20, 50, 16);
        jPanel2.add(combination_cbb);
        combination_cbb.setBounds(380, 40, 230, 30);

        jLabel5.setText("Môn 1");
        jPanel2.add(jLabel5);
        jLabel5.setBounds(60, 100, 40, 16);
        jPanel2.add(jtf_hs_mon1);
        jtf_hs_mon1.setBounds(380, 120, 230, 30);

        jLabel6.setText("Hệ số môn 1");
        jPanel2.add(jLabel6);
        jLabel6.setBounds(380, 100, 80, 16);

        jLabel7.setText("Môn 2");
        jPanel2.add(jLabel7);
        jLabel7.setBounds(60, 180, 40, 16);
        jPanel2.add(jtf_hs_mon2);
        jtf_hs_mon2.setBounds(380, 200, 230, 30);

        jLabel8.setText("Hệ số môn 2");
        jPanel2.add(jLabel8);
        jLabel8.setBounds(380, 180, 80, 16);

        jLabel9.setText("Môn 3");
        jPanel2.add(jLabel9);
        jLabel9.setBounds(60, 260, 40, 16);
        jPanel2.add(jtf_hs_mon3);
        jtf_hs_mon3.setBounds(380, 280, 230, 30);

        jLabel10.setText("Hệ số môn 3");
        jPanel2.add(jLabel10);
        jLabel10.setBounds(380, 260, 70, 16);

        jLabel11.setText("Môn trong tổ hợp");
        jPanel2.add(jLabel11);
        jLabel11.setBounds(20, 340, 100, 16);

        jcb_anh.setText("N1 ( ANH )");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(14, Short.MAX_VALUE)
                .addComponent(jcb_anh, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jcb_anh, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel2.add(jPanel3);
        jPanel3.setBounds(580, 370, 110, 40);

        jcb_khac.setText("KHÁC");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(20, Short.MAX_VALUE)
                .addComponent(jcb_khac, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jcb_khac, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel2.add(jPanel4);
        jPanel4.setBounds(490, 420, 110, 40);

        jcb_li.setText("LI ( LÍ )");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jcb_li, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(10, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jcb_li, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel2.add(jPanel5);
        jPanel5.setBounds(130, 370, 100, 40);

        jcb_sinh.setText("SI ( SINH )");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(10, Short.MAX_VALUE)
                .addComponent(jcb_sinh, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jcb_sinh, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel2.add(jPanel6);
        jPanel6.setBounds(350, 370, 100, 40);

        jcb_hoa.setText("HO ( HÓA )");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap(10, Short.MAX_VALUE)
                .addComponent(jcb_hoa)
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jcb_hoa, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel2.add(jPanel7);
        jPanel7.setBounds(240, 370, 100, 40);

        jcb_toan.setText("TO ( TOÁN )");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addComponent(jcb_toan)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jcb_toan, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel2.add(jPanel8);
        jPanel8.setBounds(10, 370, 110, 40);

        jcb_su.setText("SU ( SỬ )");

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jcb_su, javax.swing.GroupLayout.DEFAULT_SIZE, 98, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jcb_su, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel2.add(jPanel9);
        jPanel9.setBounds(40, 420, 110, 40);

        jcb_dia.setText("DI ( ĐỊA )");

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jcb_dia, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(10, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jcb_dia, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel2.add(jPanel10);
        jPanel10.setBounds(160, 420, 100, 40);

        jcb_tin.setText("TI ( TIN )");

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                .addContainerGap(10, Short.MAX_VALUE)
                .addComponent(jcb_tin, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jcb_tin, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel2.add(jPanel11);
        jPanel11.setBounds(270, 420, 100, 40);

        jcb_ktpl.setText("KTPL");

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                .addContainerGap(14, Short.MAX_VALUE)
                .addComponent(jcb_ktpl, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jcb_ktpl, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel2.add(jPanel12);
        jPanel12.setBounds(380, 420, 100, 40);

        jcb_van.setText("VA ( VĂN )");

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                .addContainerGap(14, Short.MAX_VALUE)
                .addComponent(jcb_van, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jcb_van, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel2.add(jPanel13);
        jPanel13.setBounds(460, 370, 110, 40);

        jPanel2.add(jComboBox1);
        jComboBox1.setBounds(60, 120, 240, 30);

        jPanel2.add(jComboBox2);
        jComboBox2.setBounds(60, 200, 240, 30);

        jPanel2.add(jComboBox3);
        jComboBox3.setBounds(60, 280, 240, 30);

        jButton1.setBackground(new java.awt.Color(255, 0, 0));
        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Hủy");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setBackground(new java.awt.Color(0, 204, 0));
        jButton2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("Cập nhật");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(210, 210, 210)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(43, 43, 43)
                .addComponent(jButton2)
                .addGap(0, 243, Short.MAX_VALUE))
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(70, 70, 70)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 470, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, 42, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        try {
            // ===== 1. KIỂM TRA DỮ LIỆU NHẬP =====

            // Kiểm tra chọn ngành
            if (major_cbb.getSelectedItem() == null || major_cbb.getSelectedIndex() == -1) {
                javax.swing.JOptionPane.showMessageDialog(this, "Vui lòng chọn Ngành!", "Cảnh báo", javax.swing.JOptionPane.WARNING_MESSAGE);
                major_cbb.requestFocus();
                return;
            }

            // Kiểm tra chọn tổ hợp
            if (combination_cbb.getSelectedItem() == null || combination_cbb.getSelectedIndex() == -1) {
                javax.swing.JOptionPane.showMessageDialog(this, "Vui lòng chọn Tổ hợp!", "Cảnh báo", javax.swing.JOptionPane.WARNING_MESSAGE);
                combination_cbb.requestFocus();
                return;
            }

            // Kiểm tra chọn môn 1
            // Kiểm tra 3 môn không được trùng nhau
            String mon1 = (String) jComboBox1.getSelectedItem();
            String mon2 = (String) jComboBox2.getSelectedItem();
            String mon3 = (String) jComboBox3.getSelectedItem();
            if (jComboBox1.getSelectedItem() == null || mon1.equals("Chọn môn 1")) {
                javax.swing.JOptionPane.showMessageDialog(this, "Vui lòng chọn Môn 1!", "Cảnh báo", javax.swing.JOptionPane.WARNING_MESSAGE);
                jComboBox1.requestFocus();
                return;
            }

            // Kiểm tra chọn môn 2
            if (jComboBox2.getSelectedItem() == null || mon2.equals("Chọn môn 2")) {
                javax.swing.JOptionPane.showMessageDialog(this, "Vui lòng chọn Môn 2!", "Cảnh báo", javax.swing.JOptionPane.WARNING_MESSAGE);
                jComboBox2.requestFocus();
                return;
            }

            // Kiểm tra chọn môn 3
            if (jComboBox3.getSelectedItem() == null || mon3.equals("Chọn môn 3")) {
                javax.swing.JOptionPane.showMessageDialog(this, "Vui lòng chọn Môn 3!", "Cảnh báo", javax.swing.JOptionPane.WARNING_MESSAGE);
                jComboBox3.requestFocus();
                return;
            }

            if (mon1.equals(mon2) || mon1.equals(mon3) || mon2.equals(mon3)) {
                javax.swing.JOptionPane.showMessageDialog(this, "Các môn trong tổ hợp không được trùng nhau!", "Lỗi", javax.swing.JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Kiểm tra hệ số môn 1 (phải là số nguyên dương)
            int hsmon1 = 0, hsmon2 = 0, hsmon3 = 0;
            try {
                hsmon1 = Integer.parseInt(jtf_hs_mon1.getText().trim());
                if (hsmon1 <= 0 || hsmon1 > 10) {
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException e) {
                javax.swing.JOptionPane.showMessageDialog(this, "Hệ số môn 1 phải là số nguyên dương (1-10)!", "Lỗi", javax.swing.JOptionPane.ERROR_MESSAGE);
                jtf_hs_mon1.requestFocus();
                jtf_hs_mon1.selectAll();
                return;
            }

            // Kiểm tra hệ số môn 2
            try {
                hsmon2 = Integer.parseInt(jtf_hs_mon2.getText().trim());
                if (hsmon2 <= 0 || hsmon2 > 10) {
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException e) {
                javax.swing.JOptionPane.showMessageDialog(this, "Hệ số môn 2 phải là số nguyên dương (1-10)!", "Lỗi", javax.swing.JOptionPane.ERROR_MESSAGE);
                jtf_hs_mon2.requestFocus();
                jtf_hs_mon2.selectAll();
                return;
            }

            // Kiểm tra hệ số môn 3
            try {
                hsmon3 = Integer.parseInt(jtf_hs_mon3.getText().trim());
                if (hsmon3 <= 0 || hsmon3 > 10) {
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException e) {
                javax.swing.JOptionPane.showMessageDialog(this, "Hệ số môn 3 phải là số nguyên dương (1-10)!", "Lỗi", javax.swing.JOptionPane.ERROR_MESSAGE);
                jtf_hs_mon3.requestFocus();
                jtf_hs_mon3.selectAll();
                return;
            }
            HashMap<String, String> maNganhByTenNganhMap = nganhBus.getMaNganhByTenNganhMap();
            HashMap<String, String> maToHopByTenToHopMap = tohopBus.tohopMap();

            // ===== 2. KIỂM TRA TRÙNG LẶP =====
            String tennganh = (String) major_cbb.getSelectedItem();
            String manganh = maNganhByTenNganhMap.get(tennganh);

            String tenTH = (String) combination_cbb.getSelectedItem();
            String maTH = maToHopByTenToHopMap.get(tenTH);

            // ===== 3. TẠO DTO =====
            toHopNganhDTO.setManganh(manganh);
            toHopNganhDTO.setMatohop(maTH);

            toHopNganhDTO.setTh_mon1(mon1);
            toHopNganhDTO.setHsmon1(hsmon1);

            toHopNganhDTO.setTh_mon2(mon2);
            toHopNganhDTO.setHsmon2(hsmon2);

            toHopNganhDTO.setTh_mon3(mon3);
            toHopNganhDTO.setHsmon3(hsmon3);

            // ===== AUTO KEY =====
            toHopNganhDTO.setTb_keys(manganh + "_" + maTH);

            // ===== CHECKBOX (chỉ tick những môn có trong tổ hợp) =====
            // Reset tất cả về 0 trước
            // reset DTO
            toHopNganhDTO.setTO(0);
            toHopNganhDTO.setLI(0);
            toHopNganhDTO.setHO(0);
            toHopNganhDTO.setSI(0);
            toHopNganhDTO.setVA(0);
            toHopNganhDTO.setN1(0);
            toHopNganhDTO.setSU(0);
            toHopNganhDTO.setDI(0);
            toHopNganhDTO.setTI(0);
            toHopNganhDTO.setKTPL(0);
            toHopNganhDTO.setKHAC(0);

            // reset UI
            resetCheckbox();

            // auto tick theo môn đã chọn
            autoTickSubject(toHopNganhDTO, mon1, mon2, mon3);

            // HOẶC nếu bạn muốn dùng checkbox do người dùng chọn (bỏ comment đoạn dưới và comment đoạn autoTickSubject)
            /*
            t.setTO(jcb_toan.isSelected() ? 1 : 0);
            t.setLI(jcb_li.isSelected() ? 1 : 0);
            t.setHO(jcb_hoa.isSelected() ? 1 : 0);
            t.setSI(jcb_sinh.isSelected() ? 1 : 0);
            t.setVA(jcb_van.isSelected() ? 1 : 0);
            t.setN1(jcb_anh.isSelected() ? 1 : 0);
            t.setSU(jcb_su.isSelected() ? 1 : 0);
            t.setDI(jcb_dia.isSelected() ? 1 : 0);
            t.setTI(jcb_tin.isSelected() ? 1 : 0);
            t.setKTPL(jcb_ktpl.isSelected() ? 1 : 0);
            t.setKHAC(jcb_khac.isSelected() ? 1 : 0);
             */
            // ===== DOLECH (mặc định 0) =====
            toHopNganhDTO.setDolech(BigDecimal.ZERO);

            // ===== INSERT =====
            int result = th_ng_bus.update(toHopNganhDTO);

            if (result > 0) {
                javax.swing.JOptionPane.showMessageDialog(this, "Cập nhật thành công!");
                toh_ng_Panel.dataTable(th_ng_bus.getAll());
                this.dispose();
            } else {
                javax.swing.JOptionPane.showMessageDialog(this,
                        "Cặp Ngành \"" + tennganh + "\" và Tổ hợp \"" + tenTH + "\" đã tồn tại!\nVui lòng chọn cặp khác.",
                        "Trùng lặp", javax.swing.JOptionPane.ERROR_MESSAGE);
            }

        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this, "Lỗi dữ liệu: " + e.getMessage(), "Lỗi", javax.swing.JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> combination_cbb;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JComboBox<String> jComboBox3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JCheckBox jcb_anh;
    private javax.swing.JCheckBox jcb_dia;
    private javax.swing.JCheckBox jcb_hoa;
    private javax.swing.JCheckBox jcb_khac;
    private javax.swing.JCheckBox jcb_ktpl;
    private javax.swing.JCheckBox jcb_li;
    private javax.swing.JCheckBox jcb_sinh;
    private javax.swing.JCheckBox jcb_su;
    private javax.swing.JCheckBox jcb_tin;
    private javax.swing.JCheckBox jcb_toan;
    private javax.swing.JCheckBox jcb_van;
    private javax.swing.JTextField jtf_hs_mon1;
    private javax.swing.JTextField jtf_hs_mon2;
    private javax.swing.JTextField jtf_hs_mon3;
    private javax.swing.JComboBox<String> major_cbb;
    // End of variables declaration//GEN-END:variables
}
