/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package GUI.Panel;

import BUS.NganhBUS;
import BUS.ThiSinhBUS;
import DTO.NganhDTO;
import DTO.ThiSinhDTO;
import GUIDialog.AddThiSinhDialog;
import GUIDialog.ChiTietFullDiem;
import GUIDialog.DetailThiSinhDialog;
import GUIDialog.UpdateThiSinhDialog;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Window;
import javax.swing.JLabel;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import javax.swing.BorderFactory;
import javax.swing.JDialog;
import java.io.File;
import java.util.Set;
import java.util.HashMap;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import org.jdesktop.swingx.prompt.PromptSupport;
import util.Combobox_design;
import util.JButton_design;
import util.JTextF_design;
import util.Table_design;
import util.TinhThanhUtil;

/**
 *
 * @author kiman
 */
public class ThiSinhPanel extends javax.swing.JPanel {

    Combobox_design cbb_design = new Combobox_design();
    ThiSinhBUS thisinhBus = new ThiSinhBUS();
    Table_design table_design = new Table_design();
    JButton_design btn_design = new JButton_design();
    JTextF_design jtf_design = new JTextF_design();
    Set<String> permissions;
    NganhBUS nganhBus = new NganhBUS();

    /**
     * Creates new form ThiSinhPanel
     */
    public ThiSinhPanel(Set<String> permissions) {
        initComponents();
        khoiTao();
        this.permissions =permissions;
        applyPermissions();
    }
    public ThiSinhPanel() {
        initComponents();
        khoiTao();
    }
    public void khoiTao() {
        setUpCombobox();
        setUpIcon();
        loadDataTable(thisinhBus.getAll());
        setUpPlaceholder();
        loadComboboxDT();
        loadComboboxKhuVuc();
        loadComboboxTinhThanh();
    }
    public void applyPermissions() {
        boolean canCreate = permissions.contains("thisinh.create");
        boolean canUpdate = permissions.contains("thisinh.update");
        boolean canDelete = permissions.contains("thisinh.delete");

        if (!canCreate) {
            replaceActionWithDeny(btn_add);
            replaceActionWithDeny(btn_import);
        }
        if (!canUpdate) {
            replaceActionWithDeny(btn_edit);
        }
        if (!canDelete) {
            replaceActionWithDeny(btn_delete);
        }
    }

    private void replaceActionWithDeny(javax.swing.JButton btn) {
        // Xóa tất cả listener cũ
        for (java.awt.event.ActionListener al : btn.getActionListeners()) {
            btn.removeActionListener(al);
        }
        // Thêm listener thông báo
        btn.addActionListener(e
                -> JOptionPane.showMessageDialog(
                        this,
                        "Bạn không có quyền thực hiện chức năng này!",
                        "Từ chối truy cập",
                        JOptionPane.WARNING_MESSAGE
                )
        );
    }
    public void loadComboboxKhuVuc() {
        jComboBox1.addItem("Tất cả");
        String[] areas = {"1", "2", "2NT", "3"};
        for (String a : areas) {
            jComboBox1.addItem(a);
        }
    }
    public void loadComboboxDT() {
        jComboBox2.addItem("Tất cả");
        String[] doituong = {"01", "06a","06b"};
        for (String dt : doituong) {
            jComboBox2.addItem(dt);
        }
    }
    public void loadComboboxTinhThanh(){
        jComboBox3.addItem("Tất cả");
        for (String tt : TinhThanhUtil.getAllTinhThanh()) {
            jComboBox3.addItem(tt);
        }
    }
    public void loadComboboxNganh() {
        jComboBox4.removeAllItems();
        jComboBox4.addItem("Tất cả");
        List<NganhDTO> listNganh = nganhBus.getListN();
        for (NganhDTO nganh : listNganh) {
            jComboBox4.addItem(nganh.getTenNganh());
        }
    }
    public void setUpCombobox(){
        cbb_design.setUpComBoBox(jComboBox1);
        cbb_design.setUpComBoBox(jComboBox2);
        cbb_design.setUpComBoBox(jComboBox3);
        cbb_design.setUpComBoBox(jComboBox4);
    }

    public void setUpIcon() {
        btn_add.setIcon(new FlatSVGIcon("./resources/icon/add.svg", 0.2f));
        btn_edit.setIcon(new FlatSVGIcon("./resources/icon/edit.svg", 0.2f));
        btn_delete.setIcon(new FlatSVGIcon("./resources/icon/delete.svg", 0.2f));
        btn_filter.setIcon(new FlatSVGIcon("./resources/icon/filter.svg", 0.2f));
        btn_import.setIcon(new FlatSVGIcon("./resources/icon/import.svg", 0.2f));
        btn_refresh.setIcon(new FlatSVGIcon("./resources/icon/refresh.svg", 0.25f));
        btn_chitiet.setIcon(new FlatSVGIcon("./resources/icon/view.svg", 0.2f));
        btn_xem_chitietDiem.setIcon(new FlatSVGIcon("./resources/icon/view.svg", 0.2f));
        jtf_design.setUpJTF(jtf_timkiem);
        btn_design.setUpBtn(btn_refresh, Color.white, Color.white);
    }

    public void setUpPlaceholder() {
        PromptSupport.setPrompt("Tìm kiếm theo CCCD, họ tên", jtf_timkiem);
        PromptSupport.setForeground(Color.GRAY, jtf_timkiem);
        PromptSupport.setFocusBehavior(PromptSupport.FocusBehavior.SHOW_PROMPT, jtf_timkiem);
    }

    public JTable getTableThiSinh() {
        return this.getTableThiSinh();
    }

    public void loadDataTable(ArrayList<ThiSinhDTO> listThiSinh) {
        DefaultTableModel model = new DefaultTableModel(
                new Object[][]{},
                new String[]{
                    "STT","CCCD", "SBD", "Họ", "Tên", "Ngày sinh",
                    "Giới tính", "SĐT", "Email", "Nơi sinh",
                    "Đối tượng", "Khu vực"
                }
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        int i=1;
        for (ThiSinhDTO t : listThiSinh) {
            model.addRow(new Object[]{
                i++,
                t.getCccd(),
                t.getSobaodanh(),
                t.getHo(),
                t.getTen(),
                t.getNgaySinh(),
                t.getGioiTinh(),
                t.getDienThoai(),
                t.getEmail(),
                t.getNoiSinh(),
                t.getDoiTuong(),
                t.getKhuVuc()
            });
        }

        thisinh_table.setModel(model);

        // format giống bảng tổ hợp
        table_design.centerTable(thisinh_table);
        table_design.setUpTable(thisinh_table);
        
        TableColumnModel columnModel = thisinh_table.getColumnModel();
        // 1. Định nghĩa danh sách các index cần ẩn
        int[] columnsToHide = {2, 3, 7, 8}; // SBD, Họ, SĐT, Email, Nơi sinh

        // 2. Chạy vòng lặp để ẩn
        for (int index : columnsToHide) {
            columnModel.getColumn(index).setMinWidth(0);
            columnModel.getColumn(index).setMaxWidth(0);
            columnModel.getColumn(index).setPreferredWidth(0);
        }
        columnModel.getColumn(0).setPreferredWidth(50); // STT
        columnModel.getColumn(1).setPreferredWidth(120); // CCCD
        columnModel.getColumn(4).setPreferredWidth(130); // Họ tên
        columnModel.getColumn(5).setPreferredWidth(120); // Ngày sinh
        columnModel.getColumn(6).setPreferredWidth(80); // Giới tính
        columnModel.getColumn(9).setPreferredWidth(120); // Nơi sinh
        columnModel.getColumn(10).setPreferredWidth(60); // Đối tượng
        columnModel.getColumn(11).setPreferredWidth(60); // Khu vực
    }

    private String getSafeString(int row, int col) {
        Object obj = thisinh_table.getValueAt(row, col);
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

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        thisinh_table = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        btn_add = new javax.swing.JButton();
        btn_edit = new javax.swing.JButton();
        btn_delete = new javax.swing.JButton();
        btn_chitiet = new javax.swing.JButton();
        btn_import = new javax.swing.JButton();
        btn_xem_chitietDiem = new javax.swing.JButton();
        jtf_timkiem = new javax.swing.JTextField();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        jComboBox3 = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        btn_refresh = new javax.swing.JButton();
        btn_filter = new javax.swing.JButton();
        jComboBox4 = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(204, 204, 204));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setText("HỆ THỐNG /");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(51, 204, 0));
        jLabel2.setText("THÍ SINH");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel3.setText("Quản lý thí sinh");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(jLabel3)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addContainerGap(8, Short.MAX_VALUE))
        );

        thisinh_table.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(thisinh_table);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        btn_add.setBackground(new java.awt.Color(46, 204, 113));
        btn_add.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btn_add.setForeground(new java.awt.Color(255, 255, 255));
        btn_add.setText("Thêm thí sinh");
        btn_add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_addActionPerformed(evt);
            }
        });

        btn_edit.setBackground(new java.awt.Color(52, 152, 219));
        btn_edit.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btn_edit.setForeground(new java.awt.Color(255, 255, 255));
        btn_edit.setText("Cập nhật thí sinh");
        btn_edit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_editActionPerformed(evt);
            }
        });

        btn_delete.setBackground(new java.awt.Color(231, 76, 60));
        btn_delete.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btn_delete.setForeground(new java.awt.Color(255, 255, 255));
        btn_delete.setText("Xóa thí sinh");
        btn_delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_deleteActionPerformed(evt);
            }
        });

        btn_chitiet.setBackground(new java.awt.Color(0, 0, 255));
        btn_chitiet.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btn_chitiet.setForeground(new java.awt.Color(255, 255, 255));
        btn_chitiet.setText("Xem chi tiết");
        btn_chitiet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_chitietActionPerformed(evt);
            }
        });

        btn_import.setBackground(new java.awt.Color(52, 152, 180));
        btn_import.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btn_import.setForeground(new java.awt.Color(255, 255, 255));
        btn_import.setText("Import Excel");
        btn_import.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_importActionPerformed(evt);
            }
        });

        btn_xem_chitietDiem.setBackground(new java.awt.Color(0, 255, 204));
        btn_xem_chitietDiem.setText("Chi tiết điểm");
        btn_xem_chitietDiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_xem_chitietDiemActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btn_import, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_add, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btn_edit, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_delete, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_chitiet, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_xem_chitietDiem, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(54, 54, 54))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btn_xem_chitietDiem, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btn_import, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btn_add, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btn_edit, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btn_delete, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btn_chitiet, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(22, Short.MAX_VALUE))
        );

        jtf_timkiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtf_timkiemActionPerformed(evt);
            }
        });

        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        jLabel5.setText("Khu vực");

        jLabel6.setText("Đối tượng");

        jLabel8.setText("Nơi sinh");

        btn_refresh.setText("Làm mới");
        btn_refresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_refreshActionPerformed(evt);
            }
        });

        btn_filter.setBackground(new java.awt.Color(96, 125, 139));
        btn_filter.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btn_filter.setForeground(new java.awt.Color(255, 255, 255));
        btn_filter.setText("Áp dụng");
        btn_filter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_filterActionPerformed(evt);
            }
        });

        jLabel9.setText("Tên ngành");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2)
            .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jtf_timkiem, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_filter, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_refresh, javax.swing.GroupLayout.DEFAULT_SIZE, 111, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jLabel5)
                    .addComponent(jLabel8)
                    .addComponent(jLabel9))
                .addGap(4, 4, 4)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jtf_timkiem, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btn_filter, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btn_refresh, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(19, 19, 19)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 379, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btn_importActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_importActionPerformed
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn file Excel");
        int result = fileChooser.showOpenDialog(this);
        if (result != JFileChooser.APPROVE_OPTION) {
            return;
        }

        File file = fileChooser.getSelectedFile();

        // ===== LOADING DIALOG =====
        JDialog loadingDialog = new JDialog();
        loadingDialog.setTitle("Đang xử lý...");
        loadingDialog.setSize(300, 120);
        loadingDialog.setLocationRelativeTo(this);
        loadingDialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        JLabel text = new JLabel("Đang import thí sinh...", JLabel.CENTER);
        text.setFont(new Font("Segoe UI", Font.BOLD, 14));
        text.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        loadingDialog.add(text, BorderLayout.CENTER);
        loadingDialog.pack();

        // ===== BACKGROUND TASK =====
        SwingWorker<Void, Void> worker = new SwingWorker<>() {
            int count = 0;
            String message = "";
            @Override
            protected Void doInBackground() {
                try {
                    count = thisinhBus.importFromExcel(file.getAbsolutePath());
                    if (count == 0) {
                        message = "File không có dữ liệu hoặc tất cả bị trùng!";
                    } else {
                        message = "Import thành công " + count + " thí sinh!";
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    message = "Lỗi khi import: " + e.getMessage();
                }
                return null;
            }
            @Override
            protected void done() {
                loadingDialog.dispose();
                JOptionPane.showMessageDialog(null, message);
                loadDataTable(thisinhBus.getAll());
            }
        };
        worker.execute();
        loadingDialog.setVisible(true);
    }//GEN-LAST:event_btn_importActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed

    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void btn_filterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_filterActionPerformed
        String text = (String) jtf_timkiem.getText();
        String dt = (String) jComboBox2.getSelectedItem();
        String kv = (String) jComboBox1.getSelectedItem();
        String ns = (String) jComboBox3.getSelectedItem();
        String tenNganh = (String) jComboBox4.getSelectedItem();
        String maNganh = null;
        if (!tenNganh.equals("Tất cả")) {
            HashMap<String, String> nganhMap = nganhBus.getMaNganhByTenNganhMap();
            maNganh = nganhMap.get(tenNganh);
        }
        if (maNganh != null) {
            loadDataTable(thisinhBus.filterHasNganh(text, dt, kv, maNganh, ns));
        } else {
            loadDataTable(thisinhBus.filterNoNganh(text, dt, kv, ns));
        }
    }//GEN-LAST:event_btn_filterActionPerformed

    private void jtf_timkiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtf_timkiemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtf_timkiemActionPerformed

    private void btn_editActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_editActionPerformed
        int vitriRow = thisinh_table.getSelectedRow();
        if (vitriRow == -1) {
            javax.swing.JOptionPane.showMessageDialog(this, "Vui lòng chọn thí sinh!");
            return;
        }
        int idthisinh = thisinhBus.getIDbyIndex(vitriRow);
        String cccd = getSafeString(vitriRow, 1);
        String sbd = getSafeString(vitriRow, 2);
        String ho = getSafeString(vitriRow, 3);
        String ten = getSafeString(vitriRow, 4);
        String password = thisinhBus.getPasswordByCCCD(cccd);

        Object objDate = thisinh_table.getValueAt(vitriRow, 5);
        Date ngaysinh = null;

        if (objDate instanceof Date) {
            ngaysinh = (Date) objDate;
        } else if (objDate instanceof String) {
            try {
                java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
                ngaysinh = sdf.parse((String) objDate);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        String gioitinh = getSafeString(vitriRow, 6);
        String dt = getSafeString(vitriRow, 7);
        String email = getSafeString(vitriRow, 8);
        String noisinh = getSafeString(vitriRow, 9);
        String doituong = getSafeString(vitriRow, 10);
        String khuvuc = getSafeString(vitriRow, 11);
        ThiSinhDTO thisinh = new ThiSinhDTO();
        thisinh.setIdthisinh(idthisinh);
        thisinh.setCccd(cccd);
        thisinh.setHo(ho);
        thisinh.setTen(ten);
        thisinh.setSobaodanh(sbd);
        thisinh.setPassword(password);
        thisinh.setNgaySinh(ngaysinh);
        thisinh.setGioiTinh(gioitinh);
        thisinh.setKhuVuc(khuvuc);
        thisinh.setDoiTuong(doituong);
        thisinh.setEmail(email);
        thisinh.setDienThoai(dt);
        thisinh.setNoiSinh(noisinh);
        Window parentWindow = SwingUtilities.getWindowAncestor(this);
        new UpdateThiSinhDialog((Frame) parentWindow, true, this, thisinh).setVisible(true);
    }//GEN-LAST:event_btn_editActionPerformed

    private void btn_addActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_addActionPerformed
        Window parentWindow = SwingUtilities.getWindowAncestor(this);
        new AddThiSinhDialog((Frame) parentWindow, true, this).setVisible(true);
    }//GEN-LAST:event_btn_addActionPerformed

    private void btn_refreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_refreshActionPerformed
        loadDataTable(thisinhBus.getAll());
        jtf_timkiem.setText("");
        jComboBox1.setSelectedItem("Tất cả");
        jComboBox2.setSelectedItem("Tất cả");
        jComboBox4.setSelectedItem("Tất cả");
        jComboBox3.setSelectedItem("Tất cả");
    }//GEN-LAST:event_btn_refreshActionPerformed

    private void btn_deleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_deleteActionPerformed
        int row = thisinh_table.getSelectedRow();
        if(row == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn dòng cần xóa");
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this, "Bạn có muốn xóa thí sinh này không?", "Xác nhận xóa",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE);
        if(confirm != JOptionPane.OK_OPTION) {
            return;
        }
        
        String cccd = thisinh_table.getValueAt(row,1).toString();

        ThiSinhDTO ts = thisinhBus.findByCCCD(cccd);

        if(ts == null) {
            JOptionPane.showMessageDialog(this, 
                "Không tìm thấy dữ liệu");
            return;
        }
        if(thisinhBus.delete(ts) == 1) {
            JOptionPane.showMessageDialog(this, "Xóa thí sinh thành công","Thông báo",JOptionPane.INFORMATION_MESSAGE);
            loadDataTable(thisinhBus.getAll());
        } else {
            JOptionPane.showMessageDialog(this, "Xóa thất bại","Lỗi",JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btn_deleteActionPerformed

    private void btn_chitietActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_chitietActionPerformed
        int vitriRow = thisinh_table.getSelectedRow();
        if (vitriRow == -1) {
            javax.swing.JOptionPane.showMessageDialog(this, "Vui lòng chọn thí sinh!");
            return;
        }
        int idthisinh = thisinhBus.getIDbyIndex(vitriRow);
        String cccd = getSafeString(vitriRow, 1);
        String sbd = getSafeString(vitriRow, 2);
        String ho = getSafeString(vitriRow, 3);
        String ten = getSafeString(vitriRow, 4);
        String password = thisinhBus.getPasswordByCCCD(cccd);

        Object objDate = thisinh_table.getValueAt(vitriRow, 5);
        Date ngaysinh = null;

        if (objDate instanceof Date) {
            ngaysinh = (Date) objDate;
        } else if (objDate instanceof String) {
            try {
                java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
                ngaysinh = sdf.parse((String) objDate);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        String gioitinh = getSafeString(vitriRow, 6);
        String dt = getSafeString(vitriRow, 7);
        String email = getSafeString(vitriRow, 8);
        String noisinh = getSafeString(vitriRow, 9);
        String doituong = getSafeString(vitriRow, 10);
        String khuvuc = getSafeString(vitriRow, 11);
        ThiSinhDTO thisinh = new ThiSinhDTO();
        thisinh.setIdthisinh(idthisinh);
        thisinh.setCccd(cccd);
        thisinh.setHo(ho);
        thisinh.setTen(ten);
        thisinh.setSobaodanh(sbd);
        thisinh.setPassword(password);
        thisinh.setNgaySinh(ngaysinh);
        thisinh.setGioiTinh(gioitinh);
        thisinh.setKhuVuc(khuvuc);
        thisinh.setDoiTuong(doituong);
        thisinh.setEmail(email);
        thisinh.setDienThoai(dt);
        thisinh.setNoiSinh(noisinh);
        Window parentWindow = SwingUtilities.getWindowAncestor(this);
        new DetailThiSinhDialog((Frame) parentWindow, true, this, thisinh).setVisible(true);
    }//GEN-LAST:event_btn_chitietActionPerformed

    private void btn_xem_chitietDiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_xem_chitietDiemActionPerformed
        int row = thisinh_table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn thí sinh!");
            return;
        }
        String cccd = thisinh_table.getValueAt(row, 1).toString();
        Window parentWindow = SwingUtilities.getWindowAncestor(this);
        new ChiTietFullDiem((Frame) parentWindow, true, cccd).setVisible(true);
    }//GEN-LAST:event_btn_xem_chitietDiemActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_add;
    private javax.swing.JButton btn_chitiet;
    private javax.swing.JButton btn_delete;
    private javax.swing.JButton btn_edit;
    private javax.swing.JButton btn_filter;
    private javax.swing.JButton btn_import;
    private javax.swing.JButton btn_refresh;
    private javax.swing.JButton btn_xem_chitietDiem;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JComboBox<String> jComboBox3;
    private javax.swing.JComboBox<String> jComboBox4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField jtf_timkiem;
    private javax.swing.JTable thisinh_table;
    // End of variables declaration//GEN-END:variables
}
