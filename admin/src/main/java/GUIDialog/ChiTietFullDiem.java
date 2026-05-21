/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package GUIDialog;

import BUS.DiemThiBUS;
import DTO.DiemThiDTO;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import javax.swing.Box;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Color;
import java.awt.GridLayout;
import java.math.BigDecimal;
import java.util.HashMap;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
/**
 *
 * @author kiman
 */
public class ChiTietFullDiem extends javax.swing.JDialog {

    private DiemThiBUS diemThiBUS = new DiemThiBUS();
    private String cccd;
    /**
     * Creates new form ChiTietFullDiem
     */
    public ChiTietFullDiem(java.awt.Frame parent, boolean modal,String cccd) {
        super(parent, modal);
        initComponents();
        this.cccd = cccd;
        setTitle("Chi tiết đầy đủ điểm thi");
        setSize(900, 600);
        loadData();
        setLocationRelativeTo(null);
    }

    public void loadData() {

        getContentPane().removeAll();
        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("BẢNG ĐIỂM THÍ SINH");
        title.setFont(new Font("Arial", Font.BOLD, 28));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setBorder(BorderFactory.createEmptyBorder(20, 0, 30, 0));

        mainPanel.add(title);

        DiemThiBUS bus = new DiemThiBUS();

        HashMap<String, DiemThiDTO> mapPT = bus.thpt_dgnl_Map();
        HashMap<String, HashMap<String, DiemThiDTO>> vsatMap = bus.vsatMap();

        // ================= PT2 =================
        DiemThiDTO dgnl = mapPT.get(cccd + "_PT2");

        if (dgnl != null) {
            mainPanel.add(createSectionTitle("ĐÁNH GIÁ NĂNG LỰC (PT2)"));

            JPanel p = new JPanel(new GridLayout(1, 2, 20, 15));
            p.setBackground(Color.WHITE);
            p.setBorder(BorderFactory.createEmptyBorder(10, 40, 20, 40));

            p.add(createItem("CCCD", dgnl.getCccd()));
            p.add(createItem("Điểm cao nhất", String.valueOf(dgnl.getNL1())));

            mainPanel.add(p);
        }

        // ================= PT3 =================
        HashMap<String, DiemThiDTO> dotMap = vsatMap.get(cccd);

        if (dotMap != null && !dotMap.isEmpty()) {

            mainPanel.add(createSectionTitle("VSAT (PT3)"));

            for (String dot : dotMap.keySet()) {

                DiemThiDTO dt = dotMap.get(dot);

                JPanel card = new JPanel();
                card.setLayout(new GridLayout(0, 4, 15, 15));
                card.setBackground(new Color(248, 249, 250));
                card.setBorder(BorderFactory.createTitledBorder(
                        BorderFactory.createLineBorder(new Color(220, 220, 220)),
                        "Đợt thi: " + dot
                ));

                card.add(createItem("Toán", value(dt.getTO())));
                card.add(createItem("Văn", value(dt.getVA())));
                card.add(createItem("Lý", value(dt.getLI())));
                card.add(createItem("Hóa", value(dt.getHO())));

                card.add(createItem("Sinh", value(dt.getSI())));
                card.add(createItem("Sử", value(dt.getSU())));
                card.add(createItem("Địa", value(dt.getDI())));
                card.add(createItem("Ngoại ngữ", value(dt.getN1_THI())));

                card.setMaximumSize(new Dimension(1000, 180));

                mainPanel.add(card);
                mainPanel.add(Box.createVerticalStrut(8));
            }
        }

        // ================= PT4 =================
        DiemThiDTO thpt = mapPT.get(cccd + "_PT4");

        if (thpt != null) {

            mainPanel.add(createSectionTitle("THPT (PT4)"));

            JPanel p = new JPanel(new GridLayout(0, 4, 8, 8));
            p.setBackground(Color.WHITE);
            p.setBorder(BorderFactory.createEmptyBorder(10, 40, 20, 40));

            p.add(createItem("Toán", value(thpt.getTO())));
            p.add(createItem("Văn", value(thpt.getVA())));
            p.add(createItem("Anh", value(thpt.getN1_THI())));
            p.add(createItem("Lý", value(thpt.getLI())));

            p.add(createItem("Hóa", value(thpt.getHO())));
            p.add(createItem("Sinh", value(thpt.getSI())));
            p.add(createItem("Sử", value(thpt.getSU())));
            p.add(createItem("Địa", value(thpt.getDI())));

            p.add(createItem("GDCD", value(thpt.getGDCD())));
            p.add(createItem("Tin", value(thpt.getTI())));
            p.add(createItem("CNCN", value(thpt.getCNCN())));
            p.add(createItem("CNNN", value(thpt.getCNNN())));

            p.add(createItem("NK1", value(thpt.getNK1())));
            p.add(createItem("NK2", value(thpt.getNK2())));
            p.add(createItem("NK3", value(thpt.getNK3())));
            p.add(createItem("NK4", value(thpt.getNK4())));

            p.add(createItem("NK5", value(thpt.getNK5())));
            p.add(createItem("NK6", value(thpt.getNK6())));

            mainPanel.add(p);
        }

        JButton btnClose = new JButton("Đóng");
        btnClose.setFocusPainted(false);
        btnClose.setBackground(new Color(231, 76, 60));
        btnClose.setForeground(Color.WHITE);
        btnClose.setFont(new Font("Arial", Font.BOLD, 18));
        btnClose.setPreferredSize(new Dimension(180, 45));

        btnClose.addActionListener(e -> dispose());

        JPanel bottom = new JPanel();
        bottom.setBackground(Color.WHITE);
        bottom.add(btnClose);

        JScrollPane scroll = new JScrollPane(mainPanel);
        scroll.setBorder(null);

        add(scroll, BorderLayout.CENTER);
        add(bottom, BorderLayout.SOUTH);

        revalidate();
        repaint();
    }
    private JPanel createItem(String label, String value) {

        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.setBackground(Color.WHITE);

        JLabel lb = new JLabel(label + " : ");
        lb.setFont(new Font("Arial", Font.BOLD, 18));

        JLabel val = new JLabel(value);
        val.setFont(new Font("Arial", Font.PLAIN, 18));

        panel.add(lb);
        panel.add(val);

        return panel;
    }

    private JLabel createSectionTitle(String text) {

        JLabel lb = new JLabel(text);
        lb.setAlignmentX(Component.CENTER_ALIGNMENT);
        lb.setFont(new Font("Arial", Font.BOLD, 18));
        lb.setForeground(new Color(0, 102, 204));

        lb.setBorder(BorderFactory.createEmptyBorder(20, 30, 15, 0));

        return lb;
    }

    private String value(BigDecimal d) {
        return d == null ? "0.00" : d.toString();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
