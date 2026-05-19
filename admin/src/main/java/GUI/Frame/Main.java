/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package GUI.Frame;

import BUS.DiemThiBUS;
import DTO.PermissionDTO;
import DTO.RoleDTO;
import DTO.UserDTO;
import GUI.Panel.DiemCongPanel;
import GUI.Panel.DiemThiPanel;
import GUI.Panel.NganhPanel;
import GUI.Panel.NguyenVongPanel;
import GUI.Panel.PermissionPanel;
import GUI.Panel.ThiSinhPanel;
import GUI.Panel.ToHopNganhPanel;
import GUI.Panel.TohopPanel;
import GUI.Panel.TrangChuPanel;
import GUI.Panel.UserPanel;
import GUI.Panel.ThongKePanel;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashSet;
import java.util.Set;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

/**
 *
 * @author kiman
 */
public class Main extends javax.swing.JFrame {

    /**
     * Creates new form Main
     */
    ThongKePanel thongKePanel;
    NganhPanel nganhPanel;
    TohopPanel toHopPanel;
    UserPanel userPanel;
    ToHopNganhPanel tohop_nganh_panel;
    DiemCongPanel diemCongPanel;
    DiemThiPanel diemThiPanel;
    ThiSinhPanel thisinhPanel;
    NguyenVongPanel nguyenvongPanel;
    PermissionPanel permissionPanel;
    
    JButton[] btns ;
    JButton currentActiveBtn = null;
    Border etchedBorder = BorderFactory.createEtchedBorder();
    private String role;
    DiemThiBUS dtBus = new DiemThiBUS();
    

    public Main(UserDTO user) {
        initComponents();
        this.setTitle("Quản lý xét tuyển thí sinh");
        init(user);
    }
    // ===== Permission state =====
    private UserDTO currentUser;
    private Set<String> permissions = new HashSet<>();

    private void init(UserDTO user) {
        this.currentUser = user;
        buildPermissionSet(user);
        initPanels();
        addPanels();
        khoiTao();
        applyRole();
    }
    public void khoiTao() {
        khoiTaoBtns();
        actionJButtonMenu();
        styleAllButtonMenu();
        setIcon();
        setBorder();
    }
    private void initPanels() {
        thongKePanel    = new ThongKePanel();
        nganhPanel       = new NganhPanel(permissions);
        toHopPanel       = new TohopPanel(permissions);
        userPanel        = new UserPanel(permissions);
        tohop_nganh_panel= new ToHopNganhPanel(permissions);
        diemCongPanel    = new DiemCongPanel(permissions);
        diemThiPanel     = new DiemThiPanel(permissions);
        thisinhPanel     = new ThiSinhPanel(permissions);
        nguyenvongPanel  = new NguyenVongPanel(permissions);
        permissionPanel  = new PermissionPanel(permissions);
    }
    private void addPanels() {
        main.add(thongKePanel, "TrangChu");
        main.add(nganhPanel, "Nganh");
        main.add(toHopPanel, "ToHop");
        main.add(tohop_nganh_panel, "ToHop_Nganh");
        main.add(diemCongPanel, "DiemCong");
        main.add(diemThiPanel, "DiemThi");
        main.add(thisinhPanel, "ThiSinh");
        main.add(userPanel, "User");
        main.add(nguyenvongPanel, "NguyenVong");
        main.add(permissionPanel, "PhanQuyen");

        hideAllPanels();
        thongKePanel.setVisible(true);
    }

    private void buildPermissionSet(UserDTO user) {
        permissions.clear();

        if (user.getRoles() == null) {
            return;
        }

        for (RoleDTO role : user.getRoles()) {
            // Lưu tên role dạng "ROLE_ADMIN", "ROLE_STAFF", ...
            if (role.getRoleName() != null) {
                permissions.add("ROLE_" + role.getRoleName().toUpperCase());
            }

            // Lưu từng permission name
            if (role.getPermissions() != null) {
                for (PermissionDTO p : role.getPermissions()) {
                    if (p.getPermissionName() != null) {
                        permissions.add(p.getPermissionName());
                    }
                }
            }
        }
    }

    private void applyRole() {
    // Luôn enable
    setMenuBtnState(btn_home,             true);
    setMenuBtnState(btn_logout,           true);

    // Theo permission
    setMenuBtnState(btn_major,            permissions.contains("nganh.read"));
    setMenuBtnState(btn_combination,      permissions.contains("tohop.read"));
    setMenuBtnState(btn_combination_major,permissions.contains("nganh_tohop.read"));
    setMenuBtnState(btn_thisinh,          permissions.contains("thisinh.read"));
    setMenuBtnState(btn_diemthi,          permissions.contains("diemthi.read"));
    setMenuBtnState(btn_diemcong,         permissions.contains("diemcong.read"));
    setMenuBtnState(btn_nvxt,             permissions.contains("nguyenvong.read"));
    setMenuBtnState(btn_user,             permissions.contains("user.read"));
    setMenuBtnState(btn_permission,       permissions.contains("ROLE_ADMIN"));
}
    private void setMenuBtnState(JButton btn, boolean hasPermission) {
    btn.setVisible(true); // luôn hiện
    btn.setEnabled(hasPermission);

    if (hasPermission) {
        btn.setBackground(Color.WHITE);
        btn.setForeground(Color.BLACK);
        btn.setToolTipText(null);
    } else {
        // Giao diện xám — không bấm được
        btn.setBackground(new Color(240, 240, 240));
        btn.setForeground(new Color(180, 180, 180));
        btn.setToolTipText("Bạn không có quyền truy cập");
    }
}
    

    public void khoiTaoBtns() {
    btns = new JButton[11];
    btns[0]  = btn_home;
    btns[1]  = btn_major;
    btns[2]  = btn_combination;
    btns[3]  = btn_combination_major;
    btns[4]  = btn_thisinh;
    btns[5]  = btn_diemthi;
    btns[6]  = btn_diemcong;
    btns[7]  = btn_nvxt;
    btns[8]  = btn_user;
    btns[9]  = btn_permission;
    btns[10] = btn_logout;
}

    private void hideAllPanels() {
        for (Component c : main.getComponents()) {
            c.setVisible(false);
        }
    }

    public void setIcon() {
        btn_home.setIcon(new FlatSVGIcon("./resources/icon/home.svg", 0.35f));
        btn_major.setIcon(new FlatSVGIcon("./resources/icon/major.svg", 0.4f));
        btn_combination.setIcon(new FlatSVGIcon("./resources/icon/combination.svg", 0.4f));
        logo_school.setIcon(new FlatSVGIcon("./resources/icon/school.svg", 0.7f));
        btn_combination_major.setIcon(new FlatSVGIcon("./resources/icon/combination_major.svg", 0.33f));
        btn_thisinh.setIcon(new FlatSVGIcon("./resources/icon/student.svg", 0.33f));
        btn_diemthi.setIcon(new FlatSVGIcon("./resources/icon/diemthi.svg", 0.33f));
        btn_diemcong.setIcon(new FlatSVGIcon("./resources/icon/addpoint.svg", 0.33f));
        btn_nvxt.setIcon(new FlatSVGIcon("./resources/icon/checklist.svg", 0.33f));
        btn_user.setIcon(new FlatSVGIcon("./resources/icon/key.svg", 0.33f));
    }

    public void setBorder() {
        jPanel2.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 1, Color.GRAY)); // border dưới
        panel_bottom_menu.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 1, Color.GRAY)); // border trên
    }

    public void setBackgroundJButton(JButton btn) {
    if (currentActiveBtn != null && currentActiveBtn != btn) {
        currentActiveBtn.setBackground(Color.WHITE);
        currentActiveBtn.repaint();
    }
    btn.setBackground(new Color(100, 149, 237));
    btn.repaint();
    currentActiveBtn = btn;
}

    public void actionJButtonMenu() {
        Component[] cpns = panel_bottom_menu.getComponents();
        for (Component cpn : cpns) {
            if (cpn instanceof JButton) {
                JButton button = (JButton) cpn;
                button.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        setBackgroundJButton(button);
                    }
                });
            }
        }
    }

    public void styleButtonMenu(JButton btn) {
    // Bypass FlatLaf hoàn toàn
    btn.setUI(new javax.swing.plaf.basic.BasicButtonUI());

    btn.setFocusPainted(false);
    btn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 10));
    btn.setHorizontalAlignment(SwingConstants.LEFT);
    btn.setIconTextGap(10);
    btn.setOpaque(true);
    btn.setContentAreaFilled(true);
    btn.setBackground(Color.WHITE);
    btn.setForeground(Color.BLACK);

    btn.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseEntered(java.awt.event.MouseEvent e) {
            if (btn == currentActiveBtn) return;
            if (!btn.isEnabled()) return;
            btn.setBackground(new Color(173, 216, 230));
        }
        public void mouseExited(java.awt.event.MouseEvent e) {
            if (btn == currentActiveBtn) return;
            if (!btn.isEnabled()) return;
            btn.setBackground(Color.WHITE);
        }
    });
}

    public void setBtnMenu() {
        for (JButton btn : btns) {
            btn.setFocusPainted(false);
            btn.setBorder(null);
            btn.setBackground(new Color(211, 218, 211));
        }
        panel_bottom_menu.setBorder(etchedBorder);
    }

    public void styleAllButtonMenu() {
        for (JButton btn : btns) {
            styleButtonMenu(btn);
        }
        setBackgroundJButton(btn_home);
    }

    public void switchPanel(javax.swing.JPanel panel) {
        Component[] components = main.getComponents();
        for (Component c : components) {
            c.setVisible(false);
        }
        panel.setVisible(true);
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
        panel_bottom_menu = new javax.swing.JPanel();
        btn_combination = new javax.swing.JButton();
        btn_major = new javax.swing.JButton();
        btn_home = new javax.swing.JButton();
        btn_combination_major = new javax.swing.JButton();
        btn_thisinh = new javax.swing.JButton();
        btn_diemthi = new javax.swing.JButton();
        btn_diemcong = new javax.swing.JButton();
        btn_nvxt = new javax.swing.JButton();
        btn_user = new javax.swing.JButton();
        btn_logout = new javax.swing.JButton();
        btn_permission = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        logo_school = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        main = new javax.swing.JLayeredPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(240, 470));

        panel_bottom_menu.setBackground(new java.awt.Color(255, 255, 255));

        btn_combination.setText("Tổ hợp");
        btn_combination.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_combinationActionPerformed(evt);
            }
        });

        btn_major.setText("Ngành");
        btn_major.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_majorActionPerformed(evt);
            }
        });

        btn_home.setText("Trang Chủ");
        btn_home.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_homeActionPerformed(evt);
            }
        });

        btn_combination_major.setText("Ngành - Tổ Hợp");
        btn_combination_major.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_combination_majorActionPerformed(evt);
            }
        });

        btn_thisinh.setText("Thí Sinh");
        btn_thisinh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_thisinhActionPerformed(evt);
            }
        });

        btn_diemthi.setText("Điểm Thi");
        btn_diemthi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_diemthiActionPerformed(evt);
            }
        });

        btn_diemcong.setText("Điểm Cộng");
        btn_diemcong.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_diemcongActionPerformed(evt);
            }
        });

        btn_nvxt.setText("Nguyện vọng xét tuyển");
        btn_nvxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_nvxtActionPerformed(evt);
            }
        });

        btn_user.setText("Người dùng");
        btn_user.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_userActionPerformed(evt);
            }
        });

        btn_logout.setText("Đăng xuất");
        btn_logout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_logoutActionPerformed(evt);
            }
        });

        btn_permission.setText("Phân quyền");
        btn_permission.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_permissionActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panel_bottom_menuLayout = new javax.swing.GroupLayout(panel_bottom_menu);
        panel_bottom_menu.setLayout(panel_bottom_menuLayout);
        panel_bottom_menuLayout.setHorizontalGroup(
            panel_bottom_menuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_bottom_menuLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(panel_bottom_menuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btn_home, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_combination, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_major, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_combination_major, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_thisinh, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_diemthi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_diemcong, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_nvxt, javax.swing.GroupLayout.DEFAULT_SIZE, 195, Short.MAX_VALUE)
                    .addComponent(btn_user, javax.swing.GroupLayout.DEFAULT_SIZE, 195, Short.MAX_VALUE)
                    .addComponent(btn_logout, javax.swing.GroupLayout.DEFAULT_SIZE, 195, Short.MAX_VALUE)
                    .addComponent(btn_permission, javax.swing.GroupLayout.DEFAULT_SIZE, 195, Short.MAX_VALUE))
                .addContainerGap(25, Short.MAX_VALUE))
        );
        panel_bottom_menuLayout.setVerticalGroup(
            panel_bottom_menuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_bottom_menuLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(btn_home, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btn_major, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btn_combination, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btn_combination_major, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btn_thisinh, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btn_diemthi, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btn_diemcong, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btn_nvxt, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btn_user, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btn_permission, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btn_logout, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(217, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel1.setText("ACADEMIC LEDGER");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 2, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(153, 153, 153));
        jLabel2.setText("Admin Management");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addComponent(logo_school, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 149, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel2))
                    .addComponent(logo_school, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(41, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel_bottom_menu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(131, 131, 131)
                .addComponent(panel_bottom_menu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        getContentPane().add(jPanel1, java.awt.BorderLayout.LINE_START);

        main.setPreferredSize(new java.awt.Dimension(1300, 800));
        main.setLayout(new java.awt.CardLayout());
        getContentPane().add(main, java.awt.BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btn_majorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_majorActionPerformed
        hideAllPanels();
        nganhPanel.setVisible(true);
    }//GEN-LAST:event_btn_majorActionPerformed

    private void btn_homeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_homeActionPerformed
        hideAllPanels();
        thongKePanel.setVisible(true);
    }//GEN-LAST:event_btn_homeActionPerformed

    private void btn_combinationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_combinationActionPerformed
        hideAllPanels();
        toHopPanel.setVisible(true);
    }//GEN-LAST:event_btn_combinationActionPerformed

    private void btn_combination_majorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_combination_majorActionPerformed
        hideAllPanels();
        tohop_nganh_panel.setVisible(true);
    }//GEN-LAST:event_btn_combination_majorActionPerformed

    private void btn_diemthiActionPerformed(java.awt.event.ActionEvent evt) {
        hideAllPanels();
        diemThiPanel.setVisible(true);
        diemThiPanel.dataTable(dtBus.getList());
    }

    private void btn_diemcongActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_diemcongActionPerformed
        hideAllPanels();
        diemCongPanel.setVisible(true);
    }//GEN-LAST:event_btn_diemcongActionPerformed

    private void btn_userActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_userActionPerformed
        hideAllPanels();
        userPanel.setVisible(true);
    }//GEN-LAST:event_btn_userActionPerformed

    private void btn_logoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_logoutActionPerformed
        int confirm = javax.swing.JOptionPane.showConfirmDialog(
                this,
                "Bạn có chắc chắn muốn đăng xuất?",
                "Xác nhận đăng xuất",
                javax.swing.JOptionPane.YES_NO_OPTION
        );

        if (confirm == javax.swing.JOptionPane.YES_OPTION) {
            this.dispose(); // đóng màn hình Main

            // mở lại màn hình đăng nhập
            new LoginFrame().setVisible(true);
        }
    }//GEN-LAST:event_btn_logoutActionPerformed

    private void btn_nvxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_nvxtActionPerformed
        hideAllPanels();
        nguyenvongPanel.setVisible(true);
    }//GEN-LAST:event_btn_nvxtActionPerformed

    private void btn_permissionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_permissionActionPerformed
        hideAllPanels();
        permissionPanel.setVisible(true);
        permissionPanel.loadData();
    }//GEN-LAST:event_btn_permissionActionPerformed
    private void btn_thisinhActionPerformed(java.awt.event.ActionEvent evt) {
        hideAllPanels();
        thisinhPanel.setVisible(true);

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                UserDTO testUser = new UserDTO();
                RoleDTO adminRole = new RoleDTO("ADMIN");
                testUser.getRoles().add(adminRole);
                new Main(testUser).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_combination;
    private javax.swing.JButton btn_combination_major;
    private javax.swing.JButton btn_diemcong;
    private javax.swing.JButton btn_diemthi;
    private javax.swing.JButton btn_home;
    private javax.swing.JButton btn_logout;
    private javax.swing.JButton btn_major;
    private javax.swing.JButton btn_nvxt;
    private javax.swing.JButton btn_permission;
    private javax.swing.JButton btn_thisinh;
    private javax.swing.JButton btn_user;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel logo_school;
    private javax.swing.JLayeredPane main;
    private javax.swing.JPanel panel_bottom_menu;
    // End of variables declaration//GEN-END:variables
}
