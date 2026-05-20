/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI.Frame;

/**
 *
 * @author Hao Nguyen
 */
import BUS.UserBUS;
import DTO.UserDTO;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.geom.RoundRectangle2D;
import java.io.File;
public class LoginFrame extends JFrame {
 
    private JTextField txtUser;
    private JPasswordField txtPass;
    private boolean passwordVisible = false;
    private UserBUS userBUS = new UserBUS();
    public LoginFrame() {
        setTitle("Academic Ledger - Login");
        setSize(480, 420);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
 
        // ── Content pane: chính là form, nền trắng ────────────────────────
        JPanel form = new JPanel();
        form.setBackground(Color.WHITE);
        form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));
        form.setBorder(new EmptyBorder(36, 44, 36, 44));
 
        // ── Logo row ──────────────────────────────────────────────────────
        JPanel logoRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
         logoRow.setOpaque(false);
        logoRow.setMaximumSize(new Dimension(Integer.MAX_VALUE, 56));
 
        JLabel iconLabel = loadSchoolIcon("./resources/icon/school.svg", 46, 46);
        iconLabel.setBorder(new EmptyBorder(0, 0, 0, 12));
 
        JLabel titleLabel = new JLabel("ACADEMIC LEDGER");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titleLabel.setForeground(new Color(18, 30, 60));
        titleLabel.setAlignmentY(Component.CENTER_ALIGNMENT);
        iconLabel.setAlignmentY(Component.CENTER_ALIGNMENT);
        logoRow.add(iconLabel);
        logoRow.add(titleLabel);
 
        // ── Subtitle — sát ngay dưới logo ─────────────────────────────────
        JLabel subtitle = new JLabel("Admin Management Portal");
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        subtitle.setForeground(new Color(140, 150, 165));
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);
 
        // ── Username ──────────────────────────────────────────────────────
        txtUser = new JTextField();
        JPanel userPanel = buildInputPanel("person", txtUser, null);
 
        // ── Password ──────────────────────────────────────────────────────
        txtPass = new JPasswordField();
        txtPass.setEchoChar('●');
        JButton eyeBtn = buildEyeButton();
        JPanel passPanel = buildInputPanel("lock", txtPass, eyeBtn);
 
 
        // ── Login button — full width, tall ───────────────────────────────
        JButton btnLogin = new JButton("Đăng nhập") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                Color base = getModel().isPressed()  ? new Color(0, 150, 10)
                           : getModel().isRollover() ? new Color(20, 200, 40)
                           : new Color(0, 185, 20);
                g2.setColor(base);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                g2.setColor(Color.WHITE);
                g2.setFont(new Font("Segoe UI", Font.BOLD, 15));
                FontMetrics fm = g2.getFontMetrics();
                int x = (getWidth() - fm.stringWidth(getText())) / 2;
                int y = (getHeight() + fm.getAscent() - fm.getDescent()) / 2;
                g2.drawString(getText(), x, y);
                g2.dispose();
            }
        };
        btnLogin.setOpaque(false);
        btnLogin.setContentAreaFilled(false);
        btnLogin.setBorderPainted(false);
        btnLogin.setFocusPainted(false);
        btnLogin.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnLogin.setMaximumSize(new Dimension(Integer.MAX_VALUE, 48));
        btnLogin.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnLogin.addActionListener(e -> handleLogin());
 
        // ── Assemble ──────────────────────────────────────────────────────
        form.add(logoRow);
        form.add(Box.createVerticalStrut(2));
        form.add(subtitle);
        form.add(Box.createVerticalStrut(28));
        form.add(userPanel);
        form.add(Box.createVerticalStrut(14));
        form.add(passPanel);
        form.add(Box.createVerticalStrut(8));
        form.add(Box.createVerticalStrut(16));
        form.add(btnLogin);
 
        setContentPane(form);
        setVisible(true);
        form.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
    .put(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_ENTER, 0), "login");
form.getActionMap().put("login", new javax.swing.AbstractAction() {
    @Override
    public void actionPerformed(java.awt.event.ActionEvent e) {
        handleLogin();
    }
});
    }
 
    // ── Input panel ───────────────────────────────────────────────────────
    private JPanel buildInputPanel(String iconType, JComponent field, JButton trailing) {
        JPanel panel = new JPanel(new BorderLayout(10, 0)) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Color.WHITE);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                boolean focused = field.isFocusOwner();
                g2.setStroke(new BasicStroke(focused ? 1.8f : 1.2f));
                g2.setColor(focused ? new Color(60, 140, 240) : new Color(200, 210, 222));
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 10, 10);
                g2.dispose();
            }
        };
        panel.setOpaque(false);
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        panel.setPreferredSize(new Dimension(Integer.MAX_VALUE, 50));
        panel.setBorder(new EmptyBorder(6, 14, 6, 14));
 
        field.setOpaque(false);
        field.setBorder(BorderFactory.createEmptyBorder());
        field.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        field.setForeground(new Color(50, 60, 75));
 
        if (!(field instanceof JPasswordField) && field instanceof JTextField) {
    JTextField tf = (JTextField) field;

    String ph = "Tên đăng nhập";
    tf.setForeground(new Color(160, 170, 185));
    tf.setText(ph);

    tf.addFocusListener(new FocusAdapter() {
        @Override public void focusGained(FocusEvent e) {
            if (tf.getText().equals(ph)) {
                tf.setText("");
                tf.setForeground(new Color(50, 60, 75));
            }
        }

        @Override public void focusLost(FocusEvent e) {
            if (tf.getText().isEmpty()) {
                tf.setText(ph);
                tf.setForeground(new Color(160, 170, 185));
            }
        }
    });
}
 
        field.addFocusListener(new FocusAdapter() {
            @Override public void focusGained(FocusEvent e) { panel.repaint(); }
            @Override public void focusLost(FocusEvent e)   { panel.repaint(); }
        });
 
        panel.add(buildShapeIcon(iconType), BorderLayout.WEST);
        panel.add(field, BorderLayout.CENTER);
        if (trailing != null) panel.add(trailing, BorderLayout.EAST);
        return panel;
    }
 
    // ── Shape icon ────────────────────────────────────────────────────────
    private JLabel buildShapeIcon(String type) {
        return new JLabel() {
            { setPreferredSize(new Dimension(20, 20)); }
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(110, 120, 140));
                int cx = getWidth() / 2, cy = getHeight() / 2;
                if ("person".equals(type)) {
                    g2.fillOval(cx - 5, cy - 9, 10, 10);
                    g2.fillArc(cx - 8, cy + 1, 16, 12, 0, 180);
                } else {
                    g2.setStroke(new BasicStroke(2f));
                    g2.drawArc(cx - 5, cy - 11, 10, 11, 0, 180);
                    g2.setStroke(new BasicStroke(1f));
                    g2.fillRoundRect(cx - 7, cy - 2, 14, 11, 4, 4);
                    g2.setColor(Color.WHITE);
                    g2.fillOval(cx - 2, cy + 1, 4, 5);
                }
                g2.dispose();
            }
        };
    }
 
    // ── Load school icon ─────────────────────────────────────────────────
    private JLabel loadSchoolIcon(String path, int w, int h) {
        try {
        FlatSVGIcon icon = new FlatSVGIcon(path, w, h);
        return new JLabel(icon);
    } catch (Exception e) {
        e.printStackTrace();
        return new JLabel("No Icon");
    }
    }
 
    // ── Eye toggle ────────────────────────────────────────────────────────
    private JButton buildEyeButton() {
        return new JButton() {
            {
                setOpaque(false); setContentAreaFilled(false);
                setBorderPainted(false); setFocusPainted(false);
                setPreferredSize(new Dimension(24, 24));
                setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                addActionListener(e -> {
                    passwordVisible = !passwordVisible;
                    txtPass.setEchoChar(passwordVisible ? (char) 0 : '●');
                    repaint();
                });
            }
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(150, 160, 175));
                int cx = getWidth() / 2, cy = getHeight() / 2;
                g2.setStroke(new BasicStroke(1.6f));
                g2.drawArc(cx - 7, cy - 5, 14, 10, 0, 180);
                g2.drawArc(cx - 7, cy - 5, 14, 10, 180, 180);
                g2.fillOval(cx - 3, cy - 3, 6, 6);
                if (!passwordVisible) {
                    g2.setColor(new Color(236, 239, 244));
                    g2.setStroke(new BasicStroke(2f));
                    g2.drawLine(cx - 9, cy + 7, cx + 9, cy - 7);
                }
                g2.dispose();
            }
        };
    }
 
    // ── Login handler ─────────────────────────────────────────────────────
    private void handleLogin() {

    String username = txtUser.getText().trim();
    String password = new String(txtPass.getPassword()).trim();

    try {

        UserDTO user =
                userBUS.login(username, password);

        new Main(user).setVisible(true);

        this.dispose();

    } catch (Exception e) {

        JOptionPane.showMessageDialog(
                this,
                e.getMessage(),
                "Thông báo",
                JOptionPane.WARNING_MESSAGE
        );
    }
}
 
    public static void main(String[] args) {
        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); }
        catch (Exception ignored) {}
        SwingUtilities.invokeLater(LoginFrame::new);
    }
}