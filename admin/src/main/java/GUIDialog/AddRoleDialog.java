/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUIDialog;

/**
 *
 * @author Hao Nguyen
 */

import BUS.PermissionBUS;
import GUI.Panel.PermissionPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Dialog thêm role mới
 */
public class AddRoleDialog extends JDialog {

    private final PermissionBUS permissionBUS = new PermissionBUS();
    private final PermissionPanel parentPanel;

    private JTextField jtf_roleName;
    private JButton btn_save;
    private JButton btn_cancel;

    public AddRoleDialog(Frame parent, boolean modal, PermissionPanel parentPanel) {
        super(parent, modal);
        this.parentPanel = parentPanel;

        setTitle("Thêm Role mới");
        setSize(420, 220);
        setLocationRelativeTo(parent);
        setResizable(false);

        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout(0, 0));

        // ---- Header ----
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 15));
        headerPanel.setBackground(new Color(0, 135, 51));
        JLabel titleLabel = new JLabel("Thêm Role mới");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 15));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel);
        add(headerPanel, BorderLayout.NORTH);

        // ---- Form ----
        JPanel formPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 20));
        formPanel.setBackground(Color.WHITE);

        JLabel lbl = new JLabel("Tên Role:");
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lbl.setPreferredSize(new Dimension(80, 30));

        jtf_roleName = new JTextField();
        jtf_roleName.setPreferredSize(new Dimension(260, 36));
        jtf_roleName.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        jtf_roleName.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 180, 180)),
                BorderFactory.createEmptyBorder(4, 8, 4, 8)
        ));

        formPanel.add(lbl);
        formPanel.add(jtf_roleName);
        add(formPanel, BorderLayout.CENTER);

        // ---- Footer ----
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        footerPanel.setBackground(new Color(245, 245, 245));
        footerPanel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(220, 220, 220)));

        btn_cancel = new JButton("Hủy");
        btn_cancel.setPreferredSize(new Dimension(90, 36));
        btn_cancel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        btn_cancel.setFocusPainted(false);
        btn_cancel.addActionListener(e -> dispose());

        btn_save = new JButton("Thêm");
        btn_save.setPreferredSize(new Dimension(90, 36));
        btn_save.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn_save.setBackground(new Color(0, 135, 51));
        btn_save.setForeground(Color.WHITE);
        btn_save.setFocusPainted(false);
        btn_save.addActionListener(e -> saveRole());

        footerPanel.add(btn_cancel);
        footerPanel.add(btn_save);
        add(footerPanel, BorderLayout.SOUTH);
    }

    private void saveRole() {
        String roleName = jtf_roleName.getText().trim();

        if (roleName.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Tên role không được để trống!",
                    "Lỗi",
                    JOptionPane.WARNING_MESSAGE);
            jtf_roleName.requestFocus();
            return;
        }

        boolean success = permissionBUS.insertRole(roleName);

        if (success) {
            JOptionPane.showMessageDialog(this,
                    "Thêm role \"" + roleName + "\" thành công!",
                    "Thành công",
                    JOptionPane.INFORMATION_MESSAGE);
            parentPanel.loadData();
            dispose();
        } else {
            JOptionPane.showMessageDialog(this,
                    "Thêm role thất bại! Vui lòng thử lại.",
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}