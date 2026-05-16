/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */package GUI.Panel;

import BUS.PermissionBUS;
import DTO.RoleDTO;
import GUIDialog.AddRoleDialog;
import GUIDialog.UpdatePermissionDialog;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Window;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import org.jdesktop.swingx.prompt.PromptSupport;
import util.JButton_design;
import util.JTextF_design;
import util.Table_design;

/**
 * Panel quản lý phân quyền
 */
public class PermissionPanel extends javax.swing.JPanel {

    private PermissionBUS permissionBUS = new PermissionBUS();
    private Table_design table_design = new Table_design();
    private JTextF_design jtf_design = new JTextF_design();
    private JButton_design btn_design = new JButton_design();

    public PermissionPanel() {
        initComponents();
        khoiTao();
    }

    public void khoiTao() {
        dataTable(permissionBUS.getAllRole());
        setUpJtf();
        setIcon();
    }

    // ===== Setup UI =====
    public void setUpJtf() {
        jtf_design.setUpJTF(jtf_timkiem);
        btn_design.setUpBtn(btn_timkiem, Color.WHITE, Color.WHITE);
        PromptSupport.setPrompt("Tìm kiếm tên role...", jtf_timkiem);
        PromptSupport.setForeground(Color.GRAY, jtf_timkiem);
    }

    public void setIcon() {
        btn_timkiem.setIcon(new FlatSVGIcon("./resources/icon/look.svg", 0.25f));
        btn_add.setIcon(new FlatSVGIcon("./resources/icon/add.svg", 0.2f));
        btn_update.setIcon(new FlatSVGIcon("./resources/icon/edit.svg", 0.2f));
        btn_delete.setIcon(new FlatSVGIcon("./resources/icon/delete.svg", 0.2f));
    }

    // ===== Load table =====
    public void dataTable(ArrayList<RoleDTO> list) {
        list.sort((r1, r2) ->
            Integer.compare(r1.getId(), r2.getId()));
        DefaultTableModel model = new DefaultTableModel(
                new Object[][]{},
                new String[]{"ID", "Tên Role"}
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        for (RoleDTO r : list) {
            model.addRow(new Object[]{
                r.getId(),
                r.getRoleName()
            });
        }

        role_table.setModel(model);
        table_design.centerTable(role_table);
        table_design.setUpTable(role_table);

        TableColumnModel columnModel = role_table.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(60);
        columnModel.getColumn(1).setPreferredWidth(300);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        role_table = new javax.swing.JTable();
        jtf_timkiem = new javax.swing.JTextField();
        btn_delete = new javax.swing.JButton();
        btn_add = new javax.swing.JButton();
        btn_update = new javax.swing.JButton();
        btn_timkiem = new javax.swing.JButton();

        setBackground(new java.awt.Color(204, 204, 204));
        setPreferredSize(new java.awt.Dimension(1008, 1008));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 18));
        jLabel3.setText("Quản lý phân quyền");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel3)
                .addContainerGap(817, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addComponent(jLabel3)
                .addContainerGap(13, Short.MAX_VALUE))
        );

        role_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object[][]{
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String[]{"ID", "Tên Role"}
        ));
        jScrollPane1.setViewportView(role_table);

        btn_delete.setBackground(new java.awt.Color(255, 0, 0));
        btn_delete.setFont(new java.awt.Font("Segoe UI", 1, 12));
        btn_delete.setForeground(new java.awt.Color(255, 255, 255));
        btn_delete.setText("Xóa");
        btn_delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_deleteActionPerformed(evt);
            }
        });

        btn_add.setBackground(new java.awt.Color(0, 135, 51));
        btn_add.setFont(new java.awt.Font("Segoe UI", 1, 12));
        btn_add.setForeground(new java.awt.Color(255, 255, 255));
        btn_add.setText("Thêm Role");
        btn_add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_addActionPerformed(evt);
            }
        });

        btn_update.setBackground(new java.awt.Color(52, 152, 219));
        btn_update.setFont(new java.awt.Font("Segoe UI", 1, 12));
        btn_update.setForeground(new java.awt.Color(255, 255, 255));
        btn_update.setText("Phân quyền");
        btn_update.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_updateActionPerformed(evt);
            }
        });

        btn_timkiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_timkiemActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jtf_timkiem, javax.swing.GroupLayout.DEFAULT_SIZE, 293, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_timkiem, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(298, 298, 298)
                .addComponent(btn_delete, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_add, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_update, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(41, 41, 41))
            .addComponent(jScrollPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jtf_timkiem, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_timkiem, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btn_add, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btn_update, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btn_delete, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(22, 22, 22)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 838, Short.MAX_VALUE))
        );
    }// </editor-fold>

    private void btn_addActionPerformed(java.awt.event.ActionEvent evt) {
        Window parent = SwingUtilities.getWindowAncestor(this);
        new AddRoleDialog((Frame) parent, true, this).setVisible(true);
    }

    private void btn_deleteActionPerformed(java.awt.event.ActionEvent evt) {
        int row = role_table.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Chọn role cần xóa!");
            return;
        }

        int id = (int) role_table.getValueAt(row, 0);
        String roleName = (String) role_table.getValueAt(row, 1);

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Bạn có chắc muốn xóa role \"" + roleName + "\"?\nThao tác này có thể ảnh hưởng đến người dùng đang sử dụng role này.",
                "Xác nhận xóa",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (confirm != JOptionPane.YES_OPTION) return;

        String result =
        permissionBUS.deleteRole(id);

if (result.equals("OK")) {

    JOptionPane.showMessageDialog(
            this,
            "Xóa role thành công!"
    );

    loadData();

} else {

    JOptionPane.showMessageDialog(
            this,
            result
    );
}
    }

    private void btn_updateActionPerformed(java.awt.event.ActionEvent evt) {
        int row = role_table.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Chọn role cần phân quyền!");
            return;
        }

        int id = (int) role_table.getValueAt(row, 0);
        String roleName = (String) role_table.getValueAt(row, 1);

        Window parent = SwingUtilities.getWindowAncestor(this);
        new UpdatePermissionDialog((Frame) parent, true, this, id, roleName).setVisible(true);
    }

    private void btn_timkiemActionPerformed(java.awt.event.ActionEvent evt) {
        String keyword = jtf_timkiem.getText().trim().toLowerCase();
        ArrayList<RoleDTO> allRoles = permissionBUS.getAllRole();
        ArrayList<RoleDTO> filtered = new ArrayList<>();

        for (RoleDTO r : allRoles) {
            if (r.getRoleName().toLowerCase().contains(keyword)) {
                filtered.add(r);
            }
        }

        dataTable(filtered);
    }

    // ===== Public để dialog gọi lại =====
    public void loadData() {
        dataTable(permissionBUS.getAllRole());
    }

    // Variables declaration - do not modify
    private javax.swing.JButton btn_add;
    private javax.swing.JButton btn_delete;
    private javax.swing.JButton btn_timkiem;
    private javax.swing.JButton btn_update;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jtf_timkiem;
    private javax.swing.JTable role_table;
    // End of variables declaration
}