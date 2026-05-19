/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package GUI.Panel;

import BUS.ToHopBUS;
import DTO.ToHopDTO;
import GUIDialog.AddToHopDialog;
import GUIDialog.DetailToHopDialog;
import GUIDialog.UpdateToHopDialog;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Window;
import java.util.ArrayList;
import java.util.Set;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import org.jdesktop.swingx.prompt.PromptSupport;
import util.JButton_design;
import util.JTextF_design;
import util.Table_design;


/**
 *
 * @author kiman
 */
public class TohopPanel extends javax.swing.JPanel {
    JTextF_design jtf_design = new JTextF_design();
    ToHopBUS tohopBus = new ToHopBUS();
    Table_design table_design = new Table_design();
    JButton_design btn_design = new JButton_design();
    /**
     * Creates new form TohopPanel
     */
    Set<String> permissions ;
    public TohopPanel() {
        initComponents();
        khoiTao();
    }
    public TohopPanel(Set<String> permissions) {
        initComponents();
        khoiTao();
        this.permissions =permissions;
        applyPermissions();
    }
    public void applyPermissions() {
    boolean isAdmin = permissions.contains("ROLE_ADMIN");
    boolean canCreate = isAdmin || permissions.contains("tohop.create");
    boolean canUpdate = isAdmin || permissions.contains("tohop.update");
    boolean canDelete = isAdmin || permissions.contains("tohop.delete");

    if (!canCreate) {
        replaceActionWithDeny(combination_add);
        replaceActionWithDeny(combination_import);
    }
    if (!canUpdate) replaceActionWithDeny(btn_update);
    if (!canDelete) replaceActionWithDeny(btn_delete);
}

private void replaceActionWithDeny(javax.swing.JButton btn) {
    // Xóa tất cả listener cũ
    for (java.awt.event.ActionListener al : btn.getActionListeners()) {
        btn.removeActionListener(al);
    }
    
    // Đổi màu để báo hiệu không có quyền
    btn.setBackground(new java.awt.Color(180, 180, 180)); // xám
    btn.setForeground(new java.awt.Color(100, 100, 100)); // chữ xám
    btn.setToolTipText("Bạn không có quyền thực hiện chức năng này!");

    // Thêm listener thông báo
    btn.addActionListener(e ->
        JOptionPane.showMessageDialog(
            this,
            "Bạn không có quyền thực hiện chức năng này!",
            "Từ chối truy cập",
            JOptionPane.WARNING_MESSAGE
        )
    );
}
    public void khoiTao(){
        dataTable(tohopBus.getListTH());
        designJTF();
        setUpIcon();
        setUpBtn();
    }
    public void setUpIcon() {
        combination_import.setIcon(new FlatSVGIcon("./resources/icon/import.svg", 0.2f));
        combination_add.setIcon(new FlatSVGIcon("./resources/icon/add.svg", 0.2f));
        btn_update.setIcon(new FlatSVGIcon("./resources/icon/edit.svg", 0.2f));
        btn_timkiem.setIcon(new FlatSVGIcon("./resources/icon/look.svg", 0.25f));
        btn_refresh.setIcon(new FlatSVGIcon("./resources/icon/refresh.svg", 0.25f));
        btn_delete.setIcon(new FlatSVGIcon("./resources/icon/delete.svg", 0.2f));
        btn_chitiet.setIcon(new FlatSVGIcon("./resources/icon/view.svg", 0.2f));
    }

    public void setUpBtn(){
        btn_design.setUpBtn(btn_timkiem, Color.WHITE, Color.WHITE);
        btn_design.setUpBtn(btn_refresh, Color.WHITE, Color.WHITE);
    }
    public void designJTF(){
        jtf_design.setUpJTF(jTextField1);
        PromptSupport.setPrompt("Tìm kiếm theo mã tổ hợp, tên tổ hợp", jTextField1);
        PromptSupport.setForeground(Color.GRAY, jTextField1);
        PromptSupport.setFocusBehavior(PromptSupport.FocusBehavior.SHOW_PROMPT, jTextField1);
    }
    public void dataTable(ArrayList<ToHopDTO> listToHop) {
        DefaultTableModel model = new DefaultTableModel(
                new Object[][]{},
                new String[]{"Mã tổ hợp", "Môn 1", "Môn 2", "Môn 3", "Tên tổ hợp"}
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        for (ToHopDTO t : listToHop) {
            model.addRow(new Object[]{
                t.getMatohop(),
                t.getMon1(),
                t.getMon2(),
                t.getMon3(),
                t.getTentohop()
            });
        }

        combination_table.setModel(model);

        //format giống bảng ngành
        table_design.centerTable(combination_table);
        table_design.setUpTable(combination_table);

        TableColumnModel columnModel = combination_table.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(100); // mã
        columnModel.getColumn(4).setPreferredWidth(200); // tên tổ hợp
        //Ẩn các cột Môn 1, 2, 3 (Index 1, 2, 3)
        for (int i = 1; i <= 3; i++) {
            columnModel.getColumn(i).setMinWidth(0);
            columnModel.getColumn(i).setMaxWidth(0);
            columnModel.getColumn(i).setPreferredWidth(0);
        }
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
        jScrollPane1 = new javax.swing.JScrollPane();
        combination_table = new javax.swing.JTable();
        jTextField1 = new javax.swing.JTextField();
        combination_import = new javax.swing.JButton();
        combination_add = new javax.swing.JButton();
        btn_timkiem = new javax.swing.JButton();
        btn_refresh = new javax.swing.JButton();
        btn_update = new javax.swing.JButton();
        btn_delete = new javax.swing.JButton();
        btn_chitiet = new javax.swing.JButton();

        setBackground(new java.awt.Color(204, 204, 204));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setText("HỆ THỐNG /");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 204, 51));
        jLabel2.setText(" TỔ HỢP MÔN");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 22)); // NOI18N
        jLabel3.setText("Quản lý Tổ hợp môn");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel3)
                .addGap(8, 8, 8))
        );

        combination_table.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(combination_table);

        combination_import.setBackground(new java.awt.Color(204, 204, 255));
        combination_import.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        combination_import.setForeground(new java.awt.Color(51, 51, 255));
        combination_import.setText("Import Excel");
        combination_import.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combination_importActionPerformed(evt);
            }
        });

        combination_add.setBackground(new java.awt.Color(0, 153, 51));
        combination_add.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        combination_add.setForeground(new java.awt.Color(255, 255, 255));
        combination_add.setText("Thêm ");
        combination_add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combination_addActionPerformed(evt);
            }
        });

        btn_timkiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_timkiemActionPerformed(evt);
            }
        });

        btn_refresh.setText("Làm mới");
        btn_refresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_refreshActionPerformed(evt);
            }
        });

        btn_update.setBackground(new java.awt.Color(52, 152, 219));
        btn_update.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btn_update.setForeground(new java.awt.Color(255, 255, 255));
        btn_update.setText("Sửa ");
        btn_update.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_updateActionPerformed(evt);
            }
        });

        btn_delete.setBackground(new java.awt.Color(255, 0, 0));
        btn_delete.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btn_delete.setForeground(new java.awt.Color(255, 255, 255));
        btn_delete.setText("Xóa");
        btn_delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_deleteActionPerformed(evt);
            }
        });

        btn_chitiet.setBackground(new java.awt.Color(0, 51, 204));
        btn_chitiet.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btn_chitiet.setForeground(new java.awt.Color(255, 255, 255));
        btn_chitiet.setText("Chi tiết");
        btn_chitiet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_chitietActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(btn_timkiem, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(btn_refresh, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(combination_import, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(combination_add, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_update, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_delete, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_chitiet, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(14, Short.MAX_VALUE))
            .addComponent(jScrollPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_timkiem, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btn_refresh, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(combination_import, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(combination_add, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btn_update, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btn_delete, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btn_chitiet, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(15, 15, 15)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 384, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void combination_importActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combination_importActionPerformed
        try {
            javax.swing.JFileChooser fileChooser = new javax.swing.JFileChooser();
            fileChooser.setDialogTitle("Chọn file Excel");

            int result = fileChooser.showOpenDialog(this);

            if (result == javax.swing.JFileChooser.APPROVE_OPTION) {
                java.io.File file = fileChooser.getSelectedFile();

                //  GỌI BUS
                int count = tohopBus.importFromExcel(file.getAbsolutePath());

                if (count == 0) {
                    javax.swing.JOptionPane.showMessageDialog(this, "File không có dữ liệu!");
                    return;
                }

                // reload table
                dataTable(tohopBus.getListTH()); 

                javax.swing.JOptionPane.showMessageDialog(this,
                        "Import thành công " + count + " tổ hợp!");
            }

        } catch (Exception e) {
            e.printStackTrace();
            javax.swing.JOptionPane.showMessageDialog(this,
                    "Lỗi khi import: " + e.getMessage());
        }
    }//GEN-LAST:event_combination_importActionPerformed

    private void combination_addActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combination_addActionPerformed
        Window parentWindow = SwingUtilities.getWindowAncestor(this);
        new AddToHopDialog((Frame) parentWindow, true,this).setVisible(true);
    }//GEN-LAST:event_combination_addActionPerformed

    private void btn_timkiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_timkiemActionPerformed
        String text = jTextField1.getText();
        dataTable(tohopBus.timkiem(text));
    }//GEN-LAST:event_btn_timkiemActionPerformed

    private void btn_updateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_updateActionPerformed
        int vitriRow = combination_table.getSelectedRow();
        if (vitriRow == -1) {
            javax.swing.JOptionPane.showMessageDialog(this, "Vui lòng chọn tổ hợp");
            return;
        }
        String maTH = combination_table.getValueAt(vitriRow, 0).toString();
        String mon1 = combination_table.getValueAt(vitriRow, 1).toString();
        String mon2 = combination_table.getValueAt(vitriRow, 2).toString();
        String mon3 = combination_table.getValueAt(vitriRow, 3).toString();
            String tenTH = combination_table.getValueAt(vitriRow, 4).toString();
        ToHopDTO tohopDto = new ToHopDTO();
        tohopDto.setIdtohop(tohopBus.getIdbyIndex(vitriRow));
        tohopDto.setMatohop(maTH);
        tohopDto.setTentohop(tenTH);
        tohopDto.setMon1(mon1);
        tohopDto.setMon2(mon2);
        tohopDto.setMon3(mon3);
        Window parentWindow = SwingUtilities.getWindowAncestor(this);
        new UpdateToHopDialog((Frame) parentWindow, true,this,tohopDto).setVisible(true);
    }//GEN-LAST:event_btn_updateActionPerformed

    private void btn_refreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_refreshActionPerformed
        dataTable(tohopBus.getListTH());
        jTextField1.setText("");
    }//GEN-LAST:event_btn_refreshActionPerformed

    private void btn_deleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_deleteActionPerformed
        int row = combination_table.getSelectedRow();
        if(row == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn dòng cần xóa");
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this, "Bạn có muốn xóa tổ hợp này không?", "Xác nhận xóa",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE);
        if(confirm != JOptionPane.OK_OPTION) {
            return;
        }
        
        String maTH = combination_table.getValueAt(row,0).toString();

        ToHopDTO t = tohopBus.findOneByTH(maTH);

        if(t == null) {
            JOptionPane.showMessageDialog(this, 
                "Không tìm thấy dữ liệu");
            return;
        }
        if(tohopBus.delete(t) == 1) {
            JOptionPane.showMessageDialog(this, "Xóa tổ hợp thành công","Thông báo",JOptionPane.INFORMATION_MESSAGE);
            dataTable(tohopBus.getListTH());
        } else {
            JOptionPane.showMessageDialog(this, "Xóa thất bại","Lỗi",JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btn_deleteActionPerformed

    private void btn_chitietActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_chitietActionPerformed
        int vitriRow = combination_table.getSelectedRow();
        if (vitriRow == -1) {
            javax.swing.JOptionPane.showMessageDialog(this, "Vui lòng chọn tổ hợp");
            return;
        }
        String maTH = combination_table.getValueAt(vitriRow, 0).toString();
        String mon1 = combination_table.getValueAt(vitriRow, 1).toString();
        String mon2 = combination_table.getValueAt(vitriRow, 2).toString();
        String mon3 = combination_table.getValueAt(vitriRow, 3).toString();
        String tenTH = combination_table.getValueAt(vitriRow, 4).toString();
        ToHopDTO tohopDto = new ToHopDTO();
        tohopDto.setIdtohop(tohopBus.getIdbyIndex(vitriRow));
        tohopDto.setMatohop(maTH);
        tohopDto.setTentohop(tenTH);
        tohopDto.setMon1(mon1);
        tohopDto.setMon2(mon2);
        tohopDto.setMon3(mon3);
        Window parentWindow = SwingUtilities.getWindowAncestor(this);
        new DetailToHopDialog((Frame) parentWindow, true,this,tohopDto).setVisible(true);
    }//GEN-LAST:event_btn_chitietActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_chitiet;
    private javax.swing.JButton btn_delete;
    private javax.swing.JButton btn_refresh;
    private javax.swing.JButton btn_timkiem;
    private javax.swing.JButton btn_update;
    private javax.swing.JButton combination_add;
    private javax.swing.JButton combination_import;
    private javax.swing.JTable combination_table;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
