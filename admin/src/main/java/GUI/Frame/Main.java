/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package GUI.Frame;

import GUI.Panel.DiemCongPanel;
import GUI.Panel.DiemThiPanel;
import GUI.Panel.NganhPanel;
import GUI.Panel.ToHopNganhPanel;
//import GUI.Panel.ToHop_Nganh_Panel;
import GUI.Panel.TohopPanel;
import GUI.Panel.TrangChuPanel;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
    TrangChuPanel trangChuPanel = new TrangChuPanel();
    NganhPanel nganhPanel = new NganhPanel();
    TohopPanel toHopPanel = new TohopPanel();
    ToHopNganhPanel tohop_nganh_panel = new ToHopNganhPanel();
    DiemCongPanel diemCongPanel = new DiemCongPanel();
    DiemThiPanel diemThiPanel = new DiemThiPanel();
    JButton[] btns = new JButton[8];
    JButton currentActiveBtn = null;
    Border etchedBorder = BorderFactory.createEtchedBorder();
    public Main() {
        initComponents();
        khoiTao();
        main.add(trangChuPanel, "TrangChu");
        main.add(nganhPanel, "Nganh");
        main.add(toHopPanel, "ToHop");
        main.add(tohop_nganh_panel, "ToHop_Nganh");
        main.add(diemCongPanel, "DiemCong");
        main.add(diemThiPanel, "DiemThi");
        nganhPanel.setVisible(false);
        toHopPanel.setVisible(false);
        trangChuPanel.setVisible(false);
        tohop_nganh_panel.setVisible(false);
    }

    public void khoiTao() {
        khoiTaoBtns();
        actionJButtonMenu();
        styleAllButtonMenu();
        setIcon();
        setBorder();
    }

    public void khoiTaoBtns() {
        btns[0] = btn_home;
        btns[1] = btn_major;
        btns[2] = btn_combination;
        btns[3] = btn_combination_major;
        btns[4] = btn_thisinh;
        btns[5] = btn_diemthi;
        btns[6] = btn_diemcong;
        btns[7] = btn_nvxt;
    }
    public void setIcon(){
        btn_home.setIcon(new FlatSVGIcon("./resources/icon/home.svg", 0.35f));
        btn_major.setIcon(new FlatSVGIcon("./resources/icon/major.svg",0.4f));
        btn_combination.setIcon(new FlatSVGIcon("./resources/icon/combination.svg",0.4f));
        logo_school.setIcon(new FlatSVGIcon("./resources/icon/school.svg",0.7f));
        btn_combination_major.setIcon(new FlatSVGIcon("./resources/icon/combination_major.svg",0.33f));
        btn_thisinh.setIcon(new FlatSVGIcon("./resources/icon/student.svg",0.33f));
        btn_diemthi.setIcon(new FlatSVGIcon("./resources/icon/diemthi.svg",0.33f));
        btn_diemcong.setIcon(new FlatSVGIcon("./resources/icon/addpoint.svg",0.33f));
    }
    public void setBorder() {
        jPanel2.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 1, Color.GRAY)); // border dưới
        panel_bottom_menu.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 1, Color.GRAY)); // border trên
    }
    
    public void setBackgroundJButton(JButton btn) {
        if (currentActiveBtn != null && currentActiveBtn != btn) {
            currentActiveBtn.setBackground(null);
            currentActiveBtn.setOpaque(false);
        }
        btn.setBackground(new Color(100, 149, 237));
        btn.setOpaque(true);
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
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 10));
        btn.setBackground(Color.WHITE);
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setIconTextGap(10);
        btn.setContentAreaFilled(false);

        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) {
                if (btn == currentActiveBtn) {
                    btn.setBackground(new Color(100, 149, 237));
                    return;
                }
                btn.setOpaque(true);
                btn.setBackground(new Color(173, 216, 230));
            }

            public void mouseExited(java.awt.event.MouseEvent e) {
                if (btn == currentActiveBtn) {
                    btn.setBackground(new Color(100, 149, 237));
                    return;
                }
                btn.setOpaque(false);
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
                    .addComponent(btn_nvxt, javax.swing.GroupLayout.DEFAULT_SIZE, 195, Short.MAX_VALUE))
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
                .addContainerGap(364, Short.MAX_VALUE))
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

        main.setLayout(new java.awt.CardLayout());
        getContentPane().add(main, java.awt.BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btn_majorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_majorActionPerformed
        nganhPanel.setVisible(true);
        toHopPanel.setVisible(false);
        trangChuPanel.setVisible(false);
        diemCongPanel.setVisible(false);
        diemThiPanel.setVisible(false);
        tohop_nganh_panel.setVisible(false);
    }//GEN-LAST:event_btn_majorActionPerformed

    private void btn_homeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_homeActionPerformed
        nganhPanel.setVisible(false);
        toHopPanel.setVisible(false);
        tohop_nganh_panel.setVisible(false);
        diemCongPanel.setVisible(false);
        diemThiPanel.setVisible(false);
        trangChuPanel.setVisible(true);
    }//GEN-LAST:event_btn_homeActionPerformed

    private void btn_combinationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_combinationActionPerformed
        nganhPanel.setVisible(false);
        toHopPanel.setVisible(true);
        trangChuPanel.setVisible(false);
        tohop_nganh_panel.setVisible(false);
        diemCongPanel.setVisible(false);
        diemThiPanel.setVisible(false);
    }//GEN-LAST:event_btn_combinationActionPerformed

    private void btn_combination_majorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_combination_majorActionPerformed
        nganhPanel.setVisible(false);
        toHopPanel.setVisible(false);
        trangChuPanel.setVisible(false);
        diemCongPanel.setVisible(false);
        diemThiPanel.setVisible(false);
        tohop_nganh_panel.setVisible(true);
    }//GEN-LAST:event_btn_combination_majorActionPerformed

    private void btn_diemthiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_diemthiActionPerformed
        nganhPanel.setVisible(false);
        toHopPanel.setVisible(false);
        trangChuPanel.setVisible(false);
        tohop_nganh_panel.setVisible(false);
        diemCongPanel.setVisible(false);
        diemThiPanel.setVisible(true);
    }//GEN-LAST:event_btn_diemthiActionPerformed

    private void btn_diemcongActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_diemcongActionPerformed
        nganhPanel.setVisible(false);
        toHopPanel.setVisible(false);
        trangChuPanel.setVisible(false);
        tohop_nganh_panel.setVisible(false);
        diemCongPanel.setVisible(true);
        diemThiPanel.setVisible(false);
    }//GEN-LAST:event_btn_diemcongActionPerformed

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
                new Main().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_combination;
    private javax.swing.JButton btn_combination_major;
    private javax.swing.JButton btn_diemcong;
    private javax.swing.JButton btn_diemthi;
    private javax.swing.JButton btn_home;
    private javax.swing.JButton btn_major;
    private javax.swing.JButton btn_nvxt;
    private javax.swing.JButton btn_thisinh;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel logo_school;
    private javax.swing.JLayeredPane main;
    private javax.swing.JPanel panel_bottom_menu;
    // End of variables declaration//GEN-END:variables
}
