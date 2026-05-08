/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package GUI.Panel;

import BUS.DiemThiBUS;
import BUS.NguyenVongBUS;
import DTO.DiemThiDTO;
import DTO.NguyenVongDTO;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import java.io.File;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
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
    JTextF_design jtf_design = new JTextF_design();
    Table_design table_design = new Table_design();
    Combobox_design cbb_design = new Combobox_design();
    JButton_design btn_design = new JButton_design();

    /**
     * Creates new form NguyenVongPanel
     */
    public NguyenVongPanel() {
        initComponents();
        khoiTao();
    }

    public void khoiTao() {
        dataTableNV(nvBus.getList());
        designJTF();
        designCBB();
//        loadTenNganhToComboBox();
//        loadToHopGocCombobox();
        setIcon();
        designButton();
    }

    public void designCBB() {
        cbb_design.setUpComBoBox(jComboBox10);
        cbb_design.setUpComBoBox(jComboBox11);
        cbb_design.setUpComBoBox(jComboBox12);
    }

    public void setIcon() {
        btn_timkiem.setIcon(new FlatSVGIcon("./resources/icon/look.svg", 0.25f));
        btn_refresh.setIcon(new FlatSVGIcon("./resources/icon/refresh.svg", 0.25f));
        btn_import.setIcon(new FlatSVGIcon("./resources/icon/import.svg", 0.2f));
//        major_add.setIcon(new FlatSVGIcon("./resources/icon/add.svg", 0.2f));
//        major_update.setIcon(new FlatSVGIcon("./resources/icon/edit.svg", 0.2f));
        btn_filter.setIcon(new FlatSVGIcon("./resources/icon/filter.svg", 0.2f));
//        btn_delete.setIcon(new FlatSVGIcon("./resources/icon/delete.svg", 0.2f));
        btn_xettuyen.setIcon(new FlatSVGIcon("./resources/icon/agree.svg", 0.2f));
    }

    public void designButton() {
        btn_design.setUpBtn(btn_timkiem, Color.WHITE, Color.WHITE);
        btn_design.setUpBtn(btn_refresh, Color.WHITE, Color.WHITE);
    }

    public void designJTF() {
        jtf_design.setUpJTF(jtf_timkiem);
        PromptSupport.setPrompt("Tìm kiếm theo mã ngành, tên ngành", jtf_timkiem);
        PromptSupport.setForeground(Color.GRAY, jtf_timkiem);
        PromptSupport.setFocusBehavior(PromptSupport.FocusBehavior.SHOW_PROMPT, jtf_timkiem);
    }

    public void dataTableNV(List<NguyenVongDTO> listNV) {
        DefaultTableModel model = new DefaultTableModel(
                new Object[][]{},
                new String[]{
                    "CCCD", "Mã ngành", "NV",
                    "Điểm THXT", "Điểm UT", "Điểm cộng", "Điểm XT",
                    "Kết quả", "Phương thức", "NV Key"
                }
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        for (NguyenVongDTO nv : listNV) {
            model.addRow(new Object[]{
                nv.getNvCccd(),
                nv.getNvManganh(),
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

        columnModel.getColumn(0).setPreferredWidth(120); // CCCD
        columnModel.getColumn(1).setPreferredWidth(120); // Mã ngành
        columnModel.getColumn(2).setPreferredWidth(50);  // NV
        columnModel.getColumn(3).setPreferredWidth(90);  // Điểm THXT
        columnModel.getColumn(4).setPreferredWidth(90);  // Điểm UT
        columnModel.getColumn(5).setPreferredWidth(90);  // Điểm cộng
        columnModel.getColumn(6).setPreferredWidth(100); // Điểm XT
        columnModel.getColumn(7).setPreferredWidth(80);  // Kết quả
        columnModel.getColumn(8).setPreferredWidth(100); // Phương thức
        columnModel.getColumn(9).setPreferredWidth(120); // NV Key
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        major_search3 = new javax.swing.JPanel();
        jComboBox10 = new javax.swing.JComboBox<>();
        jComboBox11 = new javax.swing.JComboBox<>();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        btn_filter = new javax.swing.JButton();
        jComboBox12 = new javax.swing.JComboBox<>();
        jLabel15 = new javax.swing.JLabel();
        major_header = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        nguyenvong_table = new javax.swing.JTable();
        btn_import = new javax.swing.JButton();
        jtf_timkiem = new javax.swing.JTextField();
        btn_timkiem = new javax.swing.JButton();
        btn_xettuyen = new javax.swing.JButton();
        btn_refresh = new javax.swing.JButton();

        major_search3.setBackground(new java.awt.Color(255, 255, 255));
        major_search3.setPreferredSize(new java.awt.Dimension(981, 90));

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel13.setText("Phương thức");

        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel14.setText("Tổ hợp gốc");

        btn_filter.setBackground(new java.awt.Color(96, 125, 139));
        btn_filter.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btn_filter.setForeground(new java.awt.Color(255, 255, 255));
        btn_filter.setText("Áp dụng bộ lọc");
        btn_filter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_filterActionPerformed(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel15.setText("Sắp xếp theo chỉ tiêu");

        javax.swing.GroupLayout major_search3Layout = new javax.swing.GroupLayout(major_search3);
        major_search3.setLayout(major_search3Layout);
        major_search3Layout.setHorizontalGroup(
            major_search3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(major_search3Layout.createSequentialGroup()
                .addGap(48, 48, 48)
                .addGroup(major_search3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jComboBox10, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13))
                .addGap(58, 58, 58)
                .addGroup(major_search3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jComboBox11, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14))
                .addGap(94, 94, 94)
                .addGroup(major_search3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel15)
                    .addGroup(major_search3Layout.createSequentialGroup()
                        .addComponent(jComboBox12, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(64, 64, 64)
                        .addComponent(btn_filter, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(118, Short.MAX_VALUE))
        );
        major_search3Layout.setVerticalGroup(
            major_search3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, major_search3Layout.createSequentialGroup()
                .addContainerGap(9, Short.MAX_VALUE)
                .addGroup(major_search3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(jLabel14)
                    .addComponent(jLabel15))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(major_search3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(major_search3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jComboBox12)
                        .addComponent(btn_filter, javax.swing.GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE))
                    .addComponent(jComboBox11)
                    .addComponent(jComboBox10))
                .addGap(20, 20, 20))
        );

        major_header.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 22)); // NOI18N
        jLabel1.setText("Quản lý nguyện vọng tuyển sinh");

        jLabel11.setText("HỆ THỐNG /");

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(51, 204, 0));
        jLabel12.setText("NGUYỆN VỌNG");

        javax.swing.GroupLayout major_headerLayout = new javax.swing.GroupLayout(major_header);
        major_header.setLayout(major_headerLayout);
        major_headerLayout.setHorizontalGroup(
            major_headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(major_headerLayout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(major_headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(major_headerLayout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel12))
                    .addComponent(jLabel1))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        major_headerLayout.setVerticalGroup(
            major_headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(major_headerLayout.createSequentialGroup()
                .addContainerGap(34, Short.MAX_VALUE)
                .addGroup(major_headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(jLabel12))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jScrollPane1.setPreferredSize(new java.awt.Dimension(0, 0));

        nguyenvong_table.setModel(new javax.swing.table.DefaultTableModel(
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
        nguyenvong_table.setMinimumSize(new java.awt.Dimension(0, 0));
        jScrollPane1.setViewportView(nguyenvong_table);

        btn_import.setBackground(new java.awt.Color(204, 204, 255));
        btn_import.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btn_import.setForeground(new java.awt.Color(51, 51, 255));
        btn_import.setText("Import Excel");
        btn_import.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_importActionPerformed(evt);
            }
        });

        btn_timkiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_timkiemActionPerformed(evt);
            }
        });

        btn_xettuyen.setBackground(new java.awt.Color(0, 0, 255));
        btn_xettuyen.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btn_xettuyen.setForeground(new java.awt.Color(255, 255, 255));
        btn_xettuyen.setText("Tiến hành xét tuyển");
        btn_xettuyen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_xettuyenActionPerformed(evt);
            }
        });

        btn_refresh.setText("Làm mới");
        btn_refresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_refreshActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jtf_timkiem, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(btn_timkiem, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(btn_refresh, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40)
                .addComponent(btn_import, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btn_xettuyen, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(major_search3, javax.swing.GroupLayout.DEFAULT_SIZE, 1040, Short.MAX_VALUE)
            .addComponent(major_header, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(major_header, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jtf_timkiem, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_timkiem, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_refresh, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btn_import, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_xettuyen, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(20, 20, 20)
                .addComponent(major_search3, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 374, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btn_filterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_filterActionPerformed
//        String ptxt = (String) jComboBox1.getSelectedItem();
//        String thg = (String) jComboBox2.getSelectedItem();
//        dataTable(nganhB.timKiem(ptxt,thg));
    }//GEN-LAST:event_btn_filterActionPerformed

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
    }//GEN-LAST:event_btn_importActionPerformed

    private void btn_timkiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_timkiemActionPerformed
        String text = jtf_timkiem.getText();
//        dataTable(nganhB.timkiemText(text));
    }//GEN-LAST:event_btn_timkiemActionPerformed

    private void btn_refreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_refreshActionPerformed
//        dataTable(nganhB.getListN());
        jtf_timkiem.setText("");
    }//GEN-LAST:event_btn_refreshActionPerformed

    private void btn_xettuyenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_xettuyenActionPerformed
        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Bạn có chắc muốn tính điểm xét tuyển không?",
                "Xác nhận",
                JOptionPane.YES_NO_OPTION
        );

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
    }//GEN-LAST:event_btn_xettuyenActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_filter;
    private javax.swing.JButton btn_import;
    private javax.swing.JButton btn_refresh;
    private javax.swing.JButton btn_timkiem;
    private javax.swing.JButton btn_xettuyen;
    private javax.swing.JComboBox<String> jComboBox10;
    private javax.swing.JComboBox<String> jComboBox11;
    private javax.swing.JComboBox<String> jComboBox12;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jtf_timkiem;
    private javax.swing.JPanel major_header;
    private javax.swing.JPanel major_search3;
    private javax.swing.JTable nguyenvong_table;
    // End of variables declaration//GEN-END:variables
}
