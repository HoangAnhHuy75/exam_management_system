/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;

/**
 *
 * @author kiman
 */
public class Combobox_design {
    public void setUpComBoBox(JComboBox<?> comboBox) {
        comboBox.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        comboBox.setPreferredSize(new Dimension(150, 30));
        comboBox.setBackground(new Color(240, 240, 240));
        comboBox.setForeground(Color.DARK_GRAY);
        // Renderer tùy chỉnh giao diện từng item
        comboBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                    boolean isSelected, boolean cellHasFocus) {
                JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
                label.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
                if (isSelected) {
                    label.setBackground(new Color(0, 120, 215));
                    label.setForeground(Color.WHITE);
                } else {
                    label.setBackground(Color.WHITE);
                    label.setForeground(Color.DARK_GRAY);
                }
                return label;
            }
        });
    }
}
