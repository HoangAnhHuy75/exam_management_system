package GUI.Panel;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import util.RotatingIcon;
public class TrangChuPanel extends javax.swing.JPanel {

    private JLabel lblTime;
    private JLabel lblDate;

    public TrangChuPanel() {
        initUI();
        startClock();
    }

    private void initUI() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(Color.WHITE);

        // ===== ROW 1 =====
        JPanel row1 = new JPanel(new GridLayout(1, 2, 20, 0));
        row1.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        row1.setMaximumSize(new Dimension(Integer.MAX_VALUE, 140));
        row1.setBackground(Color.WHITE);

        // ===== LEFT CARD =====
        JPanel leftCard = createCard();
        leftCard.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 0, 10);
        gbc.anchor = GridBagConstraints.CENTER;

        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setBackground(new Color(245, 247, 250));

        JLabel welcome = new JLabel(
                "<html><div style='width:200px; font-size:16px; font-weight:bold;'>"
                + "Chào buổi sáng!"
                + "</div></html>"
        );

        JLabel desc = new JLabel(
                "<html>Chúc bạn có một ngày làm việc hiệu quả.<br>"
                + "Hệ thống hiện đang có 24 hồ sơ cần được phê duyệt.</html>"
        );

        textPanel.add(welcome);
        textPanel.add(Box.createVerticalStrut(5));
        textPanel.add(desc);

        gbc.gridx = 0;
        leftCard.add(textPanel, gbc);

        gbc.gridx = 1;
        FlatSVGIcon baseIcon = new FlatSVGIcon("resources/icon/hand_hello.svg", 0.1f);
        RotatingIcon rotatingIcon = new RotatingIcon(baseIcon);

        JLabel img = new JLabel(rotatingIcon);
        leftCard.add(img, gbc);
        // ===== ANIMATION =====
        Timer waveTimer = new Timer(100, new AbstractAction() {
            double angle = -10;
            boolean forward = true;

            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                if (forward) {
                    angle += 2;
                    if (angle >= 10) forward = false;
                } else {
                    angle -= 2;
                    if (angle <= -10) forward = true;
                }

                rotatingIcon.setAngle(angle);
                img.repaint();
            }
        });
        waveTimer.start();
        // ===== RIGHT CARD =====
        JPanel rightCard = createCard();
        rightCard.setLayout(new GridBagLayout());

        GridBagConstraints gbc2 = new GridBagConstraints();
        gbc2.gridx = 0;
        gbc2.gridy = 0;
        gbc2.anchor = GridBagConstraints.CENTER;

        JPanel clockBox = new JPanel();
        clockBox.setLayout(new BoxLayout(clockBox, BoxLayout.Y_AXIS));
        clockBox.setBackground(new Color(245, 247, 250));

        JLabel titleClock = new JLabel("  THỜI GIAN HỆ THỐNG");
        titleClock.setIcon(new FlatSVGIcon("resources/icon/clock.svg", 0.04f));
        titleClock.setAlignmentX(Component.CENTER_ALIGNMENT);

        lblTime = new JLabel("00:00:00");
        lblTime.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lblTime.setAlignmentX(Component.CENTER_ALIGNMENT);

        lblDate = new JLabel("");
        lblDate.setAlignmentX(Component.CENTER_ALIGNMENT);

        clockBox.add(titleClock);
        clockBox.add(Box.createVerticalStrut(5));
        clockBox.add(lblTime);
        clockBox.add(lblDate);

        rightCard.add(clockBox, gbc2);

        row1.add(leftCard);
        row1.add(rightCard);

        // ===== ROW 2 =====
        JPanel row2 = new JPanel();
        row2.setBackground(Color.WHITE);
        row2.setLayout(new BoxLayout(row2, BoxLayout.Y_AXIS));

        // ===== TITLE =====
        JLabel title = new JLabel("");
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setForeground(new Color(52, 152, 219));

        // ===== SUBTITLE =====
        JLabel subtitle = new JLabel("Giải pháp hỗ trợ quản lý thí sinh, ngành học và quy trình xét tuyển hiệu quả");
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        subtitle.setForeground(Color.GRAY);

        // ===== ADD =====
        row2.add(title);
        row2.add(Box.createVerticalStrut(5));
        row2.add(subtitle);

        // ===== DELAY 0.5s rồi mới chạy typing =====
        Timer delayTimer = new Timer(500, e -> {
            ((Timer) e.getSource()).stop(); // chạy 1 lần
            typeWriter(title, "— HỆ THỐNG QUẢN LÝ TUYỂN SINH —", 100);
        });
        delayTimer.start();


        // ===== ROW 3 =====
        JPanel row3 = new JPanel(new GridLayout(1, 4, 20, 0));
        row3.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        row3.setBackground(Color.WHITE);

        // ===== COMMON STYLE =====
        Font titleFont = new Font("Segoe UI", Font.BOLD, 16);
        Color primary = new Color(52, 152, 219); // xanh
        Color success = new Color(46, 204, 113); // xanh lá
        Color warning = new Color(241, 196, 15); // vàng

        // ===== Column 1: Nhóm phát triển =====
        JPanel col1 = createCardStyled(new Color(235, 245, 255));
        col1.setLayout(new BoxLayout(col1, BoxLayout.Y_AXIS));

        JLabel title1 = new JLabel(" Nhóm phát triển");
        title1.setFont(titleFont);
        title1.setIcon(new FlatSVGIcon("resources/icon/community.svg", 0.05f));
        title1.setHorizontalAlignment(SwingConstants.LEFT);
        title1.setAlignmentX(Component.LEFT_ALIGNMENT);

        col1.add(title1);
        col1.add(Box.createVerticalStrut(10));

        // thành viên
        col1.add(createUser("Hoàng Anh Huy", "Trưởng nhóm"));
        col1.add(createUser("Nguyễn Nhật Hào", "Thành viên"));
        col1.add(createUser("Võ Thành Huynh", "Thành viên"));
        col1.add(createUser("Phan Lập Thành", "Thành viên"));


        // ===== Column 2: Mục đích =====
        JPanel col2 = createCardStyled(new Color(235, 255, 240));
        col2.setLayout(new BoxLayout(col2, BoxLayout.Y_AXIS));

        JLabel title2 = new JLabel(" Mục đích dự án");
        title2.setFont(titleFont);
        title2.setIcon(new FlatSVGIcon("resources/icon/target.svg", 0.05f));

        JLabel content2 = new JLabel(
            "<html>"
            + "Hệ thống quản lý tuyển sinh được xây dựng nhằm hỗ trợ nhà trường "
            + "trong việc quản lý toàn bộ quy trình tuyển sinh một cách khoa học.<br><br>"
            + "Giúp giảm sai sót, tiết kiệm thời gian xử lý và nâng cao hiệu quả quản lý. "
            + "Ngoài ra, hệ thống còn hỗ trợ người dùng thao tác dễ dàng và trực quan hơn."
            + "</html>"
        );

        col2.add(title2);
        col2.add(Box.createVerticalStrut(10));
        col2.add(content2);


        // ===== Column 3: Chức năng =====
        JPanel col3 = createCardStyled(new Color(255, 250, 235));
        col3.setLayout(new BoxLayout(col3, BoxLayout.Y_AXIS));
        
        JLabel title3 = new JLabel(" Chức năng chính");
        title3.setFont(titleFont);
        title3.setIcon(new FlatSVGIcon("resources/icon/list_check.svg", 0.05f));
        title3.setHorizontalAlignment(SwingConstants.LEFT);
        title3.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        col3.add(title3);
        col3.add(Box.createVerticalStrut(5));

        // danh sách chức năng (có icon tick)
        col3.add(createFeature("Quản lý thí sinh", "resources/icon/tick.svg"));
        col3.add(createFeature("Quản lý ngành và tổ hợp", "resources/icon/tick.svg"));
        col3.add(createFeature("Nhập và xử lý điểm", "resources/icon/tick.svg"));
        col3.add(createFeature("Xét tuyển và nguyện vọng", "resources/icon/tick.svg"));
        col3.add(createFeature("Tính điểm cộng", "resources/icon/tick.svg"));
        col3.add(createFeature("Phân quyền người dùng", "resources/icon/tick.svg"));
        
        // ===== Column 4: Hướng dẫn =====
        JPanel col4 = createCardStyled(new Color(245, 240, 255));
        col4.setLayout(new BoxLayout(col4, BoxLayout.Y_AXIS));

        JLabel title4 = new JLabel(" Hướng dẫn sử dụng");
        title4.setFont(titleFont);
        title4.setIcon(new FlatSVGIcon("resources/icon/regulation.svg", 0.05f));

        JLabel guide = new JLabel(
            "<html>"
            + "• Chọn chức năng ở menu bên trái<br>"
            + "• Nhập thông tin cần thiết<br>"
            + "• Nhấn lưu để ghi dữ liệu<br>"
            + "• Kiểm tra và chỉnh sửa khi cần<br><br>"
            + "Hệ thống được thiết kế thân thiện, "
            + "giúp người dùng thao tác nhanh chóng và dễ dàng."
            + "</html>"
        );

        col4.add(title4);
        col4.add(Box.createVerticalStrut(10));
        col4.add(guide);
        
        row3.add(col1);
        row3.add(col2);
        row3.add(col3);  
        row3.add(col4);

        // ===== ADD =====
        mainPanel.add(row1);
        mainPanel.add(row2);
        mainPanel.add(row3);

        add(mainPanel, BorderLayout.CENTER);
    }

    private JPanel createCard() {
        JPanel panel = new JPanel();
        panel.setBackground(new Color(245, 247, 250));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        return panel;
    }

    private void startClock() {
        Timer timer = new Timer(1000, e -> {
            Date now = new Date();
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
            SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, dd/MM/yyyy");

            lblTime.setText(timeFormat.format(now));
            lblDate.setText(dateFormat.format(now));
        });
        timer.start();
    }
    private JPanel createCardStyled(Color bg) {
        JPanel panel = new JPanel();
        panel.setBackground(bg);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        // ===== HOVER EFFECT =====
        panel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                panel.setBackground(bg.darker());
                panel.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                panel.setBackground(bg);
                panel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        });

        return panel;
    }
    private JPanel createUser(String name, String role) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 2));
        panel.setOpaque(false);
        panel.setAlignmentX(Component.LEFT_ALIGNMENT); // ⭐ QUAN TRỌNG
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        JLabel icon = new JLabel(new FlatSVGIcon("resources/icon/user_profile.svg", 0.04f));

        JLabel text = new JLabel("<html><b>" + name + "</b> - " + role + "</html>");

        panel.add(icon);
        panel.add(text);

        return panel;
    }
    private JPanel createFeature(String text, String iconPath) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 2));
        panel.setOpaque(false);
        panel.setAlignmentX(Component.LEFT_ALIGNMENT); // ⭐
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        JLabel icon = new JLabel(new FlatSVGIcon(iconPath, 0.03f));
        JLabel label = new JLabel(text);

        panel.add(icon);
        panel.add(label);

        return panel;
    }
    private void typeWriter(JLabel label, String text, int delay) {
    Timer timer = new Timer(delay, null);

    timer.addActionListener(new java.awt.event.ActionListener() {
        int index = 0;
        boolean showCursor = true;

        @Override
        public void actionPerformed(java.awt.event.ActionEvent e) {
            if (index < text.length()) {
                label.setText(text.substring(0, index + 1) + (showCursor ? "|" : ""));
                index++;
                showCursor = !showCursor;
            } else {
                label.setText(text);
                timer.stop();
            }
        }
    });

    timer.start();
}
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1164, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 744, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
