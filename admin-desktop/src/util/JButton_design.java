/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.JButton;

/**
 *
 * @author kiman
 */
public class JButton_design {

    public void setUpBtn(JButton btn, Color colorExit, Color colorEntered) {
        btn.setFocusPainted(false);
        btn.setBackground(Color.WHITE);
        btn.setForeground(Color.BLACK);
        btn.setFont(new Font("Tahoma", Font.BOLD, 16));
        btn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        // Tạo bo góc
        btn.setContentAreaFilled(false);
        btn.setOpaque(true);
        btn.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2, true));
        // Hiệu ứng hover
        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                btn.setBackground(colorEntered);
            }

            public void mouseExited(MouseEvent evt) {
                btn.setBackground(colorExit);
            }
        });
    }
    
    public void setUpBtnTwo(JButton btn,Color ColorStart,Color colorExit, Color colorEntered,int size) {
        btn.setFocusPainted(false);
        btn.setBackground(ColorStart);
        btn.setForeground(Color.BLACK);
        btn.setFont(new Font("Tahoma", Font.BOLD,size));
        btn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        // Tạo bo góc
        btn.setContentAreaFilled(false);
        btn.setOpaque(true);
        btn.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2, true));
        // Hiệu ứng hover
        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                btn.setBackground(colorEntered);
            }
            public void mouseExited(MouseEvent evt) {
                btn.setBackground(colorExit);
            }
        });
    }
}
