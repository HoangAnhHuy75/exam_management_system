/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import java.awt.Color;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author kiman
 */
public class JTextF_design {

    public void setUpJTF(JTextField jtf) {
        jtf.setFont(new Font("Segoe UI", Font.PLAIN, 15));

        // Màu chữ + nền
        jtf.setForeground(new Color(33, 33, 33));
        jtf.setBackground(new Color(245, 247, 250)); // xám rất nhạt

        // Caret (con trỏ)
        jtf.setCaretColor(new Color(0, 120, 215));

        // Border bo góc
        jtf.setBorder(new javax.swing.border.EmptyBorder(10, 12, 10, 12));

        // Tắt border mặc định
        jtf.setOpaque(true);

        // Hover + Focus effect
        jtf.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent e) {
                jtf.setBackground(Color.WHITE);
                jtf.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(0, 120, 215), 2),
                        new EmptyBorder(8, 10, 8, 10)
                ));
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                jtf.setBackground(new Color(245, 247, 250));
                jtf.setBorder(new javax.swing.border.EmptyBorder(10, 12, 10, 12));
            }
        });
    }
}
