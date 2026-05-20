/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package GUI.Panel;

import BUS.DiemThiBUS;
import BUS.NganhBUS;
import BUS.NguyenVongBUS;
import DTO.NganhDTO;
import DTO.NguyenVongDTO;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
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
public class NguyenVongPanel extends javax.swing.JPanel {

    NguyenVongBUS nvBus = new NguyenVongBUS();
    DiemThiBUS dtBus = new DiemThiBUS();
    NganhBUS nganhBus = new NganhBUS();
    JTextF_design jtf_design = new JTextF_design();
    Table_design table_design = new Table_design();
    Combobox_design cbb_design = new Combobox_design();
    JButton_design btn_design = new JButton_design();

    /**
     * Creates new form NguyenVongPanel
     */
    Set<String> permissions;

    public NguyenVongPanel(Set<String> permissions) {
        initComponents();
        khoiTao();
        this.permissions = permissions;
        applyPermissions();
    }

    public void applyPermissions() {
        boolean canCreate = permissions.contains("nguyenvong.create");

        if (!canCreate) {
            replaceActionWithDeny(btn_import);
        }
    }

    private void replaceActionWithDeny(javax.swing.JButton btn) {
        // Xóa tất cả listener cũ
        for (java.awt.event.ActionListener al : btn.getActionListeners()) {
            btn.removeActionListener(al);
        }
        // Thêm listener thông báo
        btn.addActionListener(e -> JOptionPane.showMessageDialog(
                this,
                "Bạn không có quyền thực hiện chức năng này!",
                "Từ chối truy cập",
                JOptionPane.WARNING_MESSAGE));
    }

    public void khoiTao() {
        dataTableNV(nvBus.getList());
        designJTF();
        designCBB();
        loadPTCombobox();
        setIcon();
        designButton();
    }

    public void designCBB() {
        cbb_design.setUpComBoBox(jComboBox10);
        cbb_design.setUpComBoBox(jComboBox11);
    }

    public void setIcon() {
        btn_refresh.setIcon(new FlatSVGIcon("./resources/icon/refresh.svg", 0.25f));
        btn_import.setIcon(new FlatSVGIcon("./resources/icon/import.svg", 0.2f));
        btn_filter.setIcon(new FlatSVGIcon("./resources/icon/filter.svg", 0.2f));
        btn_xettuyen.setIcon(new FlatSVGIcon("./resources/icon/agree.svg", 0.2f));
    }

    public void designButton() {
        btn_design.setUpBtn(btn_refresh, Color.WHITE, Color.WHITE);
    }

    public void designJTF() {
        jtf_design.setUpJTF(jtf_timkiem);
        PromptSupport.setPrompt("Tìm kiếm theo CCCD, tên ngành", jtf_timkiem);
        PromptSupport.setForeground(Color.GRAY, jtf_timkiem);
        PromptSupport.setFocusBehavior(PromptSupport.FocusBehavior.SHOW_PROMPT, jtf_timkiem);
    }

    public void dataTableNV(List<NguyenVongDTO> listNV) {
        DefaultTableModel model = new DefaultTableModel(
                new Object[][] {},
                new String[] {
                        "STT", "CCCD", "Tên ngành", "NV",
                        "Điểm THXT", "Điểm UT", "Điểm cộng", "Điểm XT",
                        "Kết quả", "Phương thức", "NV Key"
                }) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        HashMap<String, String> mapTenNganh = nganhBus.getTenNganhByMaNganhMap();
        int i = 1;
        for (NguyenVongDTO nv : listNV) {
            model.addRow(new Object[] {
                    i++,
                    nv.getNvCccd(),
                    mapTenNganh.get(nv.getNvManganh()),
                    nv.getNvTt(),
                    nv.getDiemThxt(),
                    nv.getDiemUtqd(),
                    nv.getDiemCong(),
                    nv.getDiemXettuyen(),
                    nv.getNvKetqua(),
                    nv.getTtPhuongthuc(),
                    nv.getNvKeys()
            });
        }

        nguyenvong_table.setModel(model);

        table_design.centerTable(nguyenvong_table);
        table_design.setUpTable(nguyenvong_table);

        TableColumnModel columnModel = nguyenvong_table.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(50); // CCCD
        columnModel.getColumn(1).setPreferredWidth(80); // CCCD
        columnModel.getColumn(2).setPreferredWidth(130); // Tên ngành
        columnModel.getColumn(3).setPreferredWidth(50); // NV
        columnModel.getColumn(4).setPreferredWidth(90); // Điểm THXT
        columnModel.getColumn(5).setPreferredWidth(90); // Điểm UT
        columnModel.getColumn(6).setPreferredWidth(90); // Điểm cộng
        columnModel.getColumn(7).setPreferredWidth(100); // Điểm XT
        columnModel.getColumn(8).setPreferredWidth(80); // Kết quả
        columnModel.getColumn(9).setPreferredWidth(100); // Phương thức
        columnModel.getColumn(10).setPreferredWidth(120); // NV Key
    }

    public void loadPTCombobox() {
        jComboBox11.addItem("Tất cả");
        String[] arrPhuongThuc = { "ĐGNL", "VSAT", "THPT" };
        for (String pt : arrPhuongThuc) {
            jComboBox11.addItem(pt);
        }
    }
    public void loadTenNganhCombobox(){
        List<NganhDTO> listN = nganhBus.getListN();
        jComboBox10.removeAllItems();
        jComboBox10.addItem("Tất cả");
        for (NganhDTO nganh : listN) {
            jComboBox10.addItem(nganh.getTenNganh());
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated
    // Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        major_header = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        btn_xettuyen = new javax.swing.JButton();
        btn_import = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        nguyenvong_table = new javax.swing.JTable();
        jtf_timkiem = new javax.swing.JTextField();
        btn_refresh = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        jComboBox10 = new javax.swing.JComboBox<>();
        jLabel14 = new javax.swing.JLabel();
        jComboBox11 = new javax.swing.JComboBox<>();
        btn_filter = new javax.swing.JButton();

        major_header.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 22)); // NOI18N
        jLabel1.setText("Quản lý nguyện vọng tuyển sinh");

        jLabel11.setText("HỆ THỐNG /");

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(51, 204, 0));
        jLabel12.setText("NGUYỆN VỌNG");

        btn_xettuyen.setBackground(new java.awt.Color(0, 0, 255));
        btn_xettuyen.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btn_xettuyen.setForeground(new java.awt.Color(255, 255, 255));
        btn_xettuyen.setText("Tính điểm xét tuyển");
        btn_xettuyen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_xettuyenActionPerformed(evt);
            }
        });

        btn_import.setBackground(new java.awt.Color(204, 204, 255));
        btn_import.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btn_import.setForeground(new java.awt.Color(51, 51, 255));
        btn_import.setText("Import Excel");
        btn_import.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_importActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout major_headerLayout = new javax.swing.GroupLayout(major_header);
        major_header.setLayout(major_headerLayout);
        major_headerLayout.setHorizontalGroup(
                major_headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(major_headerLayout.createSequentialGroup()
                                .addGap(27, 27, 27)
                                .addGroup(major_headerLayout
                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(major_headerLayout.createSequentialGroup()
                                                .addComponent(jLabel11)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(jLabel12))
                                        .addGroup(major_headerLayout.createSequentialGroup()
                                                .addComponent(jLabel1)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                                        javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(btn_import, javax.swing.GroupLayout.PREFERRED_SIZE, 130,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btn_xettuyen, javax.swing.GroupLayout.PREFERRED_SIZE, 170,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)));
        major_headerLayout.setVerticalGroup(
                major_headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(major_headerLayout.createSequentialGroup()
                                .addContainerGap(22, Short.MAX_VALUE)
                                .addGroup(major_headerLayout
                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, major_headerLayout
                                                .createSequentialGroup()
                                                .addGroup(major_headerLayout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                        .addComponent(jLabel11)
                                                        .addComponent(jLabel12))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 31,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, major_headerLayout
                                                .createSequentialGroup()
                                                .addGroup(major_headerLayout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(btn_xettuyen,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE, 36,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(btn_import,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE, 36,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(17, 17, 17)))));

        jScrollPane1.setPreferredSize(new java.awt.Dimension(0, 0));

        nguyenvong_table.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][] {
                        { null, null, null, null },
                        { null, null, null, null },
                        { null, null, null, null },
                        { null, null, null, null }
                },
                new String[] {
                        "Title 1", "Title 2", "Title 3", "Title 4"
                }));
        nguyenvong_table.setMinimumSize(new java.awt.Dimension(0, 0));
        jScrollPane1.setViewportView(nguyenvong_table);

        btn_refresh.setText("Làm mới");
        btn_refresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_refreshActionPerformed(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel13.setText("Tên ngành");

        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel14.setText("Phương thức");

        btn_filter.setBackground(new java.awt.Color(96, 125, 139));
        btn_filter.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btn_filter.setForeground(new java.awt.Color(255, 255, 255));
        btn_filter.setText("Áp dụng bộ lọc");
        btn_filter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_filterActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE,
                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(major_header, javax.swing.GroupLayout.DEFAULT_SIZE,
                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jtf_timkiem, javax.swing.GroupLayout.PREFERRED_SIZE, 295,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jComboBox10, javax.swing.GroupLayout.PREFERRED_SIZE, 180,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel13))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(jComboBox11, javax.swing.GroupLayout.PREFERRED_SIZE, 139,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(32, 32, 32)
                                                .addComponent(btn_filter, javax.swing.GroupLayout.PREFERRED_SIZE, 154,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(btn_refresh, javax.swing.GroupLayout.PREFERRED_SIZE, 130,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 83,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(62, Short.MAX_VALUE)));
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(major_header, javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel13)
                                        .addComponent(jLabel14))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(jtf_timkiem, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(btn_refresh, javax.swing.GroupLayout.Alignment.LEADING,
                                                javax.swing.GroupLayout.PREFERRED_SIZE, 37,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jComboBox10, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jComboBox11, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(btn_filter, javax.swing.GroupLayout.Alignment.LEADING,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 466,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)));
    }// </editor-fold>//GEN-END:initComponents

    private void btn_filterActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btn_filterActionPerformed
        String text = jtf_timkiem.getText();
        String tenNganh = (String) jComboBox10.getSelectedItem();
        String ptxt = (String) jComboBox11.getSelectedItem();
        dataTableNV(nvBus.filter(text, tenNganh, ptxt));
    }// GEN-LAST:event_btn_filterActionPerformed

    private void btn_importActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btn_importActionPerformed
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

        JLabel text = new JLabel("Đang import nguyện vọng...", JLabel.CENTER);
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
                    count = nvBus.importFromExcel(filePath);
                    if (count == 0) {
                        message = "File không có dữ liệu!";
                    } else if (count < 0) {
                        message = "Lỗi khi import!";
                    } else {
                        message = "Import thành công " + count + " nguyện vọng!";
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    message = "Lỗi khi import file!";
                }
                return null;
            }

            @Override
            protected void done() {
                loadingDialog.dispose();
                JOptionPane.showMessageDialog(null, message);
                dataTableNV(nvBus.getList()); // hoặc getList() tuỳ bạn đặt tên
            }
        };

        worker.execute();
        loadingDialog.setVisible(true);
    }// GEN-LAST:event_btn_importActionPerformed

    private void btn_refreshActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btn_refreshActionPerformed
        dataTableNV(nvBus.getList());
        jtf_timkiem.setText("");
        jComboBox10.setSelectedItem("Tất cả");
        jComboBox11.setSelectedItem("Tất cả");
    }// GEN-LAST:event_btn_refreshActionPerformed

    private void btn_xettuyenActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btn_xettuyenActionPerformed
        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Bạn có chắc muốn tính điểm xét tuyển không?",
                "Xác nhận",
                JOptionPane.YES_NO_OPTION);

        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        // ===== LOADING DIALOG =====
        JDialog loadingDialog = new JDialog();
        loadingDialog.setTitle("Đang xử lý...");
        loadingDialog.setSize(300, 120);
        loadingDialog.setLocationRelativeTo(this);
        loadingDialog.setLayout(new BorderLayout());

        JLabel text = new JLabel("Đang xét tuyển, vui lòng chờ...", JLabel.CENTER);
        text.setFont(new Font("Segoe UI", Font.BOLD, 14));
        text.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        loadingDialog.add(text, BorderLayout.CENTER);
        loadingDialog.pack();
        loadingDialog.setLocationRelativeTo(this);
        loadingDialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);

        // ===== BACKGROUND TASK =====
        SwingWorker<Void, Void> worker = new SwingWorker<>() {

            String message = "";

            @Override
            protected Void doInBackground() {
                try {
                    nvBus.xetTuyen();
                    message = "Xét tuyển hoàn tất!";
                } catch (Exception e) {
                    e.printStackTrace();
                    message = "Lỗi khi xét tuyển!";
                }
                return null;
            }

            @Override
            protected void done() {
                loadingDialog.dispose();
                JOptionPane.showMessageDialog(null, message);
                dataTableNV(nvBus.getList());
            }
        };

        worker.execute();
        loadingDialog.setVisible(true);
    }// GEN-LAST:event_btn_xettuyenActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_filter;
    private javax.swing.JButton btn_import;
    private javax.swing.JButton btn_refresh;
    private javax.swing.JButton btn_xettuyen;
    private javax.swing.JComboBox<String> jComboBox10;
    private javax.swing.JComboBox<String> jComboBox11;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jtf_timkiem;
    private javax.swing.JPanel major_header;
    private javax.swing.JTable nguyenvong_table;
    // End of variables declaration//GEN-END:variables
}
