package GUI.Panel;

import BUS.ThongKeBUS;
import javax.swing.*;
import javax.swing.border.AbstractBorder;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;

public class ThongKePanel extends JPanel {

    private final ThongKeBUS thongKeBUS;

    // ================= COLORS =================
    private static final Color BACKGROUND  = new Color(245, 247, 250);
    private static final Color CARD_BG     = Color.WHITE;
    private static final Color PRIMARY     = new Color(37,  99,  235);
    private static final Color SUCCESS     = new Color(16,  185, 129);
    private static final Color WARNING     = new Color(245, 158, 11);
    private static final Color PURPLE      = new Color(139, 92,  246);
    private static final Color TEXT_DARK   = new Color(30,  41,  59);
    private static final Color TEXT_LIGHT  = new Color(100, 116, 139);

    // ================= DATA =================
    private int tongThiSinh;
    private int tongNguyenVong;
    private int tongNganh;
    private int tongToHop;
    private int tongTrungTuyen;

    /*
     * Object[] gồm:
     * [0] -> String  : Tên ngành
     * [1] -> Integer : Chỉ tiêu
     * [2] -> Long    : Số lượng nguyện vọng
     */
    private ArrayList<Object[]> top5Nganh;

    public ThongKePanel() {

        thongKeBUS = new ThongKeBUS();

        loadData();

        initUI();
    }

    private void loadData() {

        tongThiSinh    = thongKeBUS.getSoLuongThiSinh();
        tongNguyenVong = thongKeBUS.getSoLuongNguyenVong();
        tongNganh      = thongKeBUS.getTongSoNganh();
        tongToHop      = thongKeBUS.getSoLuongToHop();
        tongTrungTuyen = thongKeBUS.getSoLuongThiSinhTrungTuyen();
        top5Nganh      = thongKeBUS.getTop5NganhTyLeChoiCaoNhat();
    }

    private void initUI() {

        setLayout(null);
        setBackground(BACKGROUND);

        // Toàn bộ nội dung vừa trong 1040x640, không cần scroll
        setPreferredSize(new Dimension(1040, 640));

        // ================= HEADER =================
        JLabel title = new JLabel("THỐNG KÊ TUYỂN SINH");
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setForeground(TEXT_DARK);
        title.setBounds(20, 10, 400, 34);
        add(title);

        JLabel sub = new JLabel("Dashboard thống kê hệ thống xét tuyển");
        sub.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        sub.setForeground(TEXT_LIGHT);
        sub.setBounds(20, 44, 400, 18);
        add(sub);

        // ================= CARDS (4 cards, mỗi card 232px, gap 18px) =================
        // x: 20 | 270 | 520 | 770   →   last right edge = 770+232 = 1002 ✓
        add(createStatCard("Tổng Thí Sinh",    formatNumber(tongThiSinh),    "👨‍🎓", PRIMARY, 20,  90));
        add(createStatCard("Tổng Nguyện Vọng", formatNumber(tongNguyenVong), "📝",   SUCCESS, 270, 90));
        add(createStatCard("Tổng Ngành",       formatNumber(tongNganh),      "🎓",   WARNING, 520, 90));
        add(createStatCard("Tổng Tổ Hợp",      formatNumber(tongToHop),      "📚",   PURPLE,  770, 90));

        // ================= ROW DƯỚI: Top5 (trái) + Tỷ lệ (phải) =================
        // Top5:  x=20,  w=680, h=425, y=200
        // TyLe:  x=715, w=305, h=425, y=200
        // Bottom: 200+425=625, padding dưới 15px → tổng 640 ✓

        JPanel chartPanel = createTopNganhPanel();
        chartPanel.setBounds(20, 200, 680, 425);
        add(chartPanel);

        JPanel ratePanel = createTyLePanel();
        ratePanel.setBounds(715, 200, 305, 425);
        add(ratePanel);
    }

    // ================= STAT CARD (232x100) =================
    private JPanel createStatCard(
            String title, String value, String icon, Color color, int x, int y) {

        JPanel panel = new RoundedPanel(16);
        panel.setLayout(null);
        panel.setBackground(CARD_BG);
        panel.setBounds(x, y, 232, 100);
        panel.setBorder(new ShadowBorder());

        JLabel iconLabel = new JLabel(icon);
        iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 26));
        iconLabel.setBounds(16, 14, 46, 36);
        panel.add(iconLabel);

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        titleLabel.setForeground(TEXT_LIGHT);
        titleLabel.setBounds(16, 56, 200, 18);
        panel.add(titleLabel);

        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 26));
        valueLabel.setForeground(TEXT_DARK);
        valueLabel.setBounds(72, 16, 148, 36);
        panel.add(valueLabel);

        // Thanh màu bên trái
        JPanel accent = new JPanel();
        accent.setBackground(color);
        accent.setBounds(0, 0, 6, 100);
        panel.add(accent);

        return panel;
    }

    // ================= TOP 5 NGÀNH (680x425) =================
    private JPanel createTopNganhPanel() {

        JPanel panel = new RoundedPanel(16);
        panel.setLayout(null);
        panel.setBackground(CARD_BG);
        panel.setBorder(new ShadowBorder());

        JLabel title = new JLabel("TOP 5 NGÀNH CÓ NHIỀU THÍ SINH ĐĂNG KÝ NHẤT");
        title.setFont(new Font("Segoe UI", Font.BOLD, 15));
        title.setForeground(TEXT_DARK);
        title.setBounds(20, 16, 640, 26);
        panel.add(title);

        JLabel sub = new JLabel("Số lượng nguyện vọng, chỉ tiêu và tỷ lệ chọi");
        sub.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        sub.setForeground(TEXT_LIGHT);
        sub.setBounds(20, 44, 500, 18);
        panel.add(sub);

        // Scale bar theo tổng nguyện vọng toàn hệ thống
        long tongNV = tongNguyenVong > 0 ? tongNguyenVong : 1;

        // Bar tối đa = 680 - left(30) - right(30) - label(80) - gap(8) = 532px
        final int BAR_MAX   = 532;
        final int BAR_LEFT  = 30;
        final int LABEL_X   = BAR_LEFT + BAR_MAX + 8;   // 570
        final int LABEL_W   = 680 - LABEL_X - 10;       // 100

        int y = 75;

        for (int i = 0; i < top5Nganh.size(); i++) {

            Object[] row    = top5Nganh.get(i);
            String tenNganh = (String) row[0];
            int chiTieu     = ((Number) row[1]).intValue();
            long soNV       = ((Number) row[2]).longValue();
            double tyLe     = chiTieu > 0 ? (double) soNV / chiTieu : 0;

            // Phần trăm so với tổng nguyện vọng
            double phanTram = (double) soNV / tongNV * 100;

            // Tên ngành
            JLabel ten = new JLabel((i + 1) + ". " + tenNganh);
            ten.setFont(new Font("Segoe UI", Font.BOLD, 13));
            ten.setForeground(TEXT_DARK);
            ten.setBounds(BAR_LEFT, y, 560, 18);
            panel.add(ten);

            // Thông tin chi tiết
            JLabel info = new JLabel(
                "NV: " + formatNumber(soNV)
                + "   Chỉ tiêu: " + formatNumber(chiTieu)
                + "   Tỷ lệ chọi: 1:" + String.format("%.1f", tyLe)
            );
            info.setFont(new Font("Segoe UI", Font.PLAIN, 11));
            info.setForeground(TEXT_LIGHT);
            info.setBounds(BAR_LEFT, y + 20, 560, 16);
            panel.add(info);

            // Bar nền (đại diện 100% tổng nguyện vọng)
            JPanel bgBar = new RoundedBar(new Color(226, 232, 240));
            bgBar.setBounds(BAR_LEFT, y + 40, BAR_MAX, 12);
            panel.add(bgBar);

            // Bar giá trị: chiều dài = soNV / tongNV * BAR_MAX
            int barW = (int) ((double) soNV / tongNV * BAR_MAX);
            JPanel valueBar = new RoundedBar(PRIMARY);
            valueBar.setBounds(BAR_LEFT, y + 40, Math.max(barW, 4), 12);
            panel.add(valueBar);

            // Label "soNV / tongNV (xx.x%)" bên phải bar
            JLabel valueLbl = new JLabel(
                formatNumber(soNV) + " (" + String.format("%.1f%%", phanTram) + ")"
            );
            valueLbl.setFont(new Font("Segoe UI", Font.BOLD, 11));
            valueLbl.setForeground(PRIMARY);
            valueLbl.setBounds(LABEL_X, y + 36, LABEL_W, 18);
            panel.add(valueLbl);

            y += 65;
        }

        return panel;
    }

    // ================= TỶ LỆ TRÚNG TUYỂN (305x425) =================
    private JPanel createTyLePanel() {

        double tyLe = tongThiSinh > 0
                ? (double) tongTrungTuyen / tongThiSinh * 100
                : 0;

        // Custom panel vẽ circular progress arc
        JPanel panel = new RoundedPanel(16) {

            @Override
            protected void paintComponent(Graphics g) {

                super.paintComponent(g);

                Graphics2D g2 = (Graphics2D) g.create();

                g2.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
                );
                g2.setRenderingHint(
                    RenderingHints.KEY_TEXT_ANTIALIASING,
                    RenderingHints.VALUE_TEXT_ANTIALIAS_ON
                );

                int cx     = getWidth() / 2;
                int cy     = 190;
                int radius = 90;
                int stroke = 14;
                int d      = radius * 2;
                int arcX   = cx - radius;
                int arcY   = cy - radius;

                // Vòng ngoài xám
                g2.setColor(new Color(226, 232, 240));
                g2.setStroke(new BasicStroke(stroke, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                g2.drawOval(arcX, arcY, d, d);

                // Arc xanh (SUCCESS) theo tỷ lệ, clockwise từ trên
                g2.setColor(SUCCESS);
                int arcAngle = (int) Math.round(tyLe / 100.0 * 360);
                g2.drawArc(arcX, arcY, d, d, 90, -arcAngle);

                // Text % to ở giữa vòng
                String pctText = String.format("%.1f%%", tyLe);
                g2.setFont(new Font("Segoe UI", Font.BOLD, 32));
                g2.setColor(TEXT_DARK);
                FontMetrics fm = g2.getFontMetrics();
                int tx = cx - fm.stringWidth(pctText) / 2;
                g2.drawString(pctText, tx, cy + fm.getAscent() / 2 - 4);

                g2.dispose();
            }
        };

        panel.setLayout(null);
        panel.setBackground(CARD_BG);
        panel.setBorder(new ShadowBorder());

        // Tiêu đề
        JLabel title = new JLabel("TỶ LỆ TRÚNG TUYỂN");
        title.setFont(new Font("Segoe UI", Font.BOLD, 15));
        title.setForeground(TEXT_DARK);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setBounds(0, 15, 305, 22);
        panel.add(title);

        // Mô tả bên dưới vòng tròn
        JLabel desc = new JLabel(
            "<html><center>"
            + tongTrungTuyen + " / " + tongThiSinh
            + "<br>thí sinh trúng tuyển"
            + "</center></html>"
        );
        desc.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        desc.setForeground(TEXT_LIGHT);
        desc.setHorizontalAlignment(SwingConstants.CENTER);
        desc.setBounds(20, 295, 265, 44);
        panel.add(desc);

        // Divider
        JSeparator sep = new JSeparator();
        sep.setForeground(new Color(226, 232, 240));
        sep.setBounds(20, 355, 265, 2);
        panel.add(sep);

        // Stat nhỏ: Trúng tuyển
        addStatRow(panel, "✅ Trúng tuyển",  formatNumber(tongTrungTuyen), SUCCESS, 370);

        // Stat nhỏ: Không trúng
        int khongTrung = tongThiSinh - tongTrungTuyen;
        addStatRow(panel, "❌ Không trúng", formatNumber(khongTrung),      WARNING, 405);

        return panel;
    }

    private void addStatRow(JPanel parent, String label, String value, Color valueColor, int y) {

        JLabel lbl = new JLabel(label);
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lbl.setForeground(TEXT_LIGHT);
        lbl.setBounds(25, y, 170, 20);
        parent.add(lbl);

        JLabel val = new JLabel(value);
        val.setFont(new Font("Segoe UI", Font.BOLD, 13));
        val.setForeground(valueColor);
        val.setHorizontalAlignment(SwingConstants.RIGHT);
        val.setBounds(180, y, 100, 20);
        parent.add(val);
    }

    // ================= FORMAT =================
    private String formatNumber(long n) {
        return String.format("%,d", n).replace(",", ".");
    }

    // ================= ROUNDED PANEL =================
    static class RoundedPanel extends JPanel {

        private final int radius;

        public RoundedPanel(int radius) {
            this.radius = radius;
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {

            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getBackground());
            g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), radius, radius));
            g2.dispose();
            super.paintComponent(g);
        }
    }

    // ================= ROUNDED BAR =================
    static class RoundedBar extends JPanel {

        private final Color color;

        public RoundedBar(Color color) {
            this.color = color;
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {

            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(color);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
            g2.dispose();
        }
    }

    // ================= SHADOW BORDER =================
    static class ShadowBorder extends AbstractBorder {

        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(4, 4, 4, 4);
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {

            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(new Color(0, 0, 0, 18));
            g2.drawRoundRect(x, y, width - 1, height - 1, 16, 16);
            g2.dispose();
        }
    }

    // ================= MAIN =================
    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            JFrame frame = new JFrame("Thống Kê Tuyển Sinh");
            frame.setSize(1040, 640);
            frame.setMinimumSize(new Dimension(1040, 640));
            frame.setResizable(false);
            frame.setLocationRelativeTo(null);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setContentPane(new ThongKePanel());
            frame.setVisible(true);
        });
    }
}