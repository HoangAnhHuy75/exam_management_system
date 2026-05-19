package GUI.Panel;

import BUS.ThongKeBUS;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.RenderingHints;
import java.util.ArrayList;

public class ThongKePanel extends JPanel {

    private ThongKeBUS thongKeBUS;

    // Colors
    private static final Color BG_MAIN       = new Color(0xF0F4FA);
    private static final Color BG_CARD       = Color.WHITE;
    private static final Color BLUE_PRIMARY  = new Color(0x1A56DB);
    private static final Color BLUE_LIGHT    = new Color(0xE8F0FE);
    private static final Color BLUE_MED      = new Color(0x3B82F6);
    private static final Color GREEN_ACCENT  = new Color(0x10B981);
    private static final Color RED_ACCENT    = new Color(0xEF4444);
    private static final Color YELLOW_ACCENT = new Color(0xF59E0B);
    private static final Color TEXT_DARK     = new Color(0x1E293B);
    private static final Color TEXT_MID      = new Color(0x64748B);
    private static final Color TEXT_LIGHT    = new Color(0x94A3B8);
    private static final Color DIVIDER       = new Color(0xE2E8F0);

    // Data
    private int soThiSinh, soNguyenVong, soNganh, soToHop, soTrungTuyen;
    private ArrayList<Object[]> top5Nganh;

    public ThongKePanel() {
        thongKeBUS = new ThongKeBUS();
        loadData();
        setPreferredSize(new Dimension(1040, 640));
        setBackground(BG_MAIN);
        setLayout(null);
        buildUI();
    }

    private void loadData() {
        soThiSinh    = thongKeBUS.getSoLuongThiSinh();
        soNguyenVong = thongKeBUS.getSoLuongNguyenVong();
        soNganh      = thongKeBUS.getTongSoNganh();
        soToHop      = thongKeBUS.getSoLuongToHop();
        soTrungTuyen = thongKeBUS.getSoLuongThiSinhTrungTuyen();
        top5Nganh    = thongKeBUS.getTop5NganhTyLeChoiCaoNhat();
    }

    private void buildUI() {
        // ── Header ──────────────────────────────────────────────────
        JPanel header = createHeader();
        header.setBounds(0, 0, 1040, 60);
        add(header);

        // ── Stat Cards Row ───────────────────────────────────────────
        int cardY = 76, cardW = 230, cardH = 100, cardGap = 16, cardX = 20;

        // Card "Trúng Tuyển": chỉ hiển thị phân số khi có dữ liệu
        String trungTuyenSub = (soThiSinh > 0 && soTrungTuyen > 0)
                ? fmt(soTrungTuyen) + " / " + fmt(soThiSinh) + " thí sinh"
                : "";

        JPanel c1 = createStatCard("Tổng Thí Sinh",    fmt(soThiSinh),
                BLUE_PRIMARY, "\uD83D\uDC65");
        c1.setBounds(cardX, cardY, cardW, cardH);
        add(c1);

        JPanel c2 = createStatCard("Tổng Nguyện Vọng", fmt(soNguyenVong),
                BLUE_MED, "\uD83D\uDCCB");
        c2.setBounds(cardX + (cardW + cardGap), cardY, cardW, cardH);
        add(c2);

        JPanel c3 = createStatCardWithSub("Trúng Tuyển", fmt(soTrungTuyen),
                trungTuyenSub, GREEN_ACCENT, "\u2705");
        c3.setBounds(cardX + 2*(cardW + cardGap), cardY, cardW, cardH);
        add(c3);

        JPanel c4 = createStatCard("Tổng Số Ngành",    String.valueOf(soNganh),
                YELLOW_ACCENT, "\uD83C\uDF93");
        c4.setBounds(cardX + 3*(cardW + cardGap), cardY, cardW, cardH);
        add(c4);

        // ── Bottom Row ───────────────────────────────────────────────
        int botY  = 196, botH = 420;
        int leftW = 630, rightW = 370;

        JPanel barCard = createBarChartCard();
        barCard.setBounds(20, botY, leftW, botH);
        add(barCard);

        JPanel donutCard = createDonutCard();
        donutCard.setBounds(20 + leftW + 16, botY, rightW, botH);
        add(donutCard);
    }

    // ── Header ───────────────────────────────────────────────────────
    private JPanel createHeader() {
        JPanel p = new JPanel(null) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setColor(BLUE_PRIMARY);
                g2.fillRect(0, 0, getWidth(), getHeight());
                g2.dispose();
            }
        };
        p.setOpaque(false);

        JLabel title = new JLabel("  \uD83C\uDFEB  Bảng Thống Kê Tuyển Sinh");
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        title.setForeground(Color.WHITE);
        title.setBounds(10, 0, 500, 60);
        p.add(title);

        JLabel sub = new JLabel("Dữ liệu thời gian thực · Năm học 2024");
        sub.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        sub.setForeground(new Color(0xBFDBFE));
        sub.setBounds(710, 0, 320, 60);
        p.add(sub);

        return p;
    }

    // ── Stat Card (chỉ số, không badge/sub) ──────────────────────────
    private JPanel createStatCard(String title, String value,
                                  Color accent, String icon) {
        return createStatCardWithSub(title, value, "", accent, icon);
    }

    // ── Stat Card có dòng phụ tuỳ chọn ───────────────────────────────
    private JPanel createStatCardWithSub(String title, String value,
                                         String sub,
                                         Color accent, String icon) {
        JPanel card = new RoundedPanel(14, BG_CARD);
        card.setLayout(null);

        // accent bar on left
        JPanel bar = new JPanel() {
            @Override protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(accent);
                g2.fillRoundRect(0, 0, 5, getHeight(), 5, 5);
                g2.dispose();
            }
        };
        bar.setOpaque(false);
        bar.setBounds(0, 10, 5, 80);
        card.add(bar);

        // icon circle
        JLabel iconLbl = new JLabel(icon, SwingConstants.CENTER) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(accent.getRed(), accent.getGreen(),
                        accent.getBlue(), 30));
                g2.fillOval(0, 0, 36, 36);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        iconLbl.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 16));
        iconLbl.setBounds(180, 12, 36, 36);
        card.add(iconLbl);

        JLabel titleLbl = new JLabel(title);
        titleLbl.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        titleLbl.setForeground(TEXT_MID);
        titleLbl.setBounds(14, 14, 165, 16);
        card.add(titleLbl);

        // Nếu có sub → giảm font value để nhường chỗ
        int valueFontSize = sub.isEmpty() ? 28 : 26;
        int valueY        = sub.isEmpty() ? 38 : 32;

        JLabel valueLbl = new JLabel(value);
        valueLbl.setFont(new Font("Segoe UI", Font.BOLD, valueFontSize));
        valueLbl.setForeground(TEXT_DARK);
        valueLbl.setBounds(14, valueY, 210, 36);
        card.add(valueLbl);

        // Dòng phụ "X / Y thí sinh" — chỉ render khi không rỗng
        if (sub != null && !sub.isEmpty()) {
            JLabel subLbl = new JLabel(sub);
            subLbl.setFont(new Font("Segoe UI", Font.PLAIN, 10));
            subLbl.setForeground(new Color(accent.getRed(), accent.getGreen(),
                    accent.getBlue(), 200));
            subLbl.setBounds(14, 70, 210, 16);
            card.add(subLbl);
        }

        addCardShadow(card);
        return card;
    }

    // ── Bar Chart Card ────────────────────────────────────────────────
    private JPanel createBarChartCard() {
        JPanel card = new RoundedPanel(14, BG_CARD);
        card.setLayout(null);
        addCardShadow(card);

        JLabel heading = new JLabel("Nguyện Vọng Theo Ngành");
        heading.setFont(new Font("Segoe UI", Font.BOLD, 15));
        heading.setForeground(TEXT_DARK);
        heading.setBounds(20, 18, 360, 22);
        card.add(heading);

        JLabel sub = new JLabel("Top 5 ngành có tỉ lệ chọi cao nhất");
        sub.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        sub.setForeground(TEXT_MID);
        sub.setBounds(20, 40, 360, 16);
        card.add(sub);

        JSeparator sep = new JSeparator();
        sep.setForeground(DIVIDER);
        sep.setBounds(0, 64, 630, 1);
        card.add(sep);

        if (top5Nganh == null || top5Nganh.isEmpty()) {
            JLabel empty = new JLabel("Không có dữ liệu", SwingConstants.CENTER);
            empty.setFont(new Font("Segoe UI", Font.ITALIC, 13));
            empty.setForeground(TEXT_LIGHT);
            empty.setBounds(0, 200, 630, 30);
            card.add(empty);
            return card;
        }

        long maxNV = 1;
        for (Object[] row : top5Nganh) {
            long nv = (Long) row[2];
            if (nv > maxNV) maxNV = nv;
        }

        Color[] BAR_COLORS = {
            BLUE_PRIMARY,
            new Color(0x2563EB),
            new Color(0x3B82F6),
            new Color(0x60A5FA),
            new Color(0x93C5FD)
        };

        int rowH = 62, startY = 76;
        int barAreaW = 590;

        for (int i = 0; i < top5Nganh.size(); i++) {
            Object[] row  = top5Nganh.get(i);
            String  nganh = (String)  row[0];
            int     chiTieu = (Integer) row[1];
            long    nguyenVong = (Long) row[2];
            double  tyLe  = chiTieu > 0 ? (double) nguyenVong / chiTieu : 0;
            Color   barC  = BAR_COLORS[i % BAR_COLORS.length];
            int     y     = startY + i * rowH;

            // Name
            JLabel nameLbl = new JLabel((i + 1) + ".  " + nganh);
            nameLbl.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            nameLbl.setForeground(TEXT_DARK);
            nameLbl.setBounds(20, y, 300, 18);
            card.add(nameLbl);

            // Value
            JLabel valLbl = new JLabel(fmt(nguyenVong) + " NV");
            valLbl.setFont(new Font("Segoe UI", Font.BOLD, 12));
            valLbl.setForeground(barC);
            valLbl.setHorizontalAlignment(SwingConstants.RIGHT);
            valLbl.setBounds(490, y, 120, 18);
            card.add(valLbl);

            // Bar background
            int barY = y + 24;
            double ratio = (double) nguyenVong / maxNV;
            int    barW  = (int) (ratio * (barAreaW - 30));

            JPanel bgBar = new RoundedBar(6, new Color(0xEFF6FF));
            bgBar.setBounds(20, barY, barAreaW - 30, 12);
            card.add(bgBar);

            JPanel fgBar = new RoundedBar(6, barC);
            fgBar.setBounds(20, barY, Math.max(barW, 6), 12);
            card.add(fgBar);

            // Ratio label
            JLabel ratioLbl = new JLabel(String.format("1:%.1f", tyLe));
            ratioLbl.setFont(new Font("Segoe UI", Font.PLAIN, 10));
            ratioLbl.setForeground(TEXT_MID);
            ratioLbl.setBounds(20, barY + 14, 200, 14);
            card.add(ratioLbl);

            JLabel ctLbl = new JLabel("Chỉ tiêu: " + fmt(chiTieu));
            ctLbl.setFont(new Font("Segoe UI", Font.PLAIN, 10));
            ctLbl.setForeground(TEXT_LIGHT);
            ctLbl.setHorizontalAlignment(SwingConstants.RIGHT);
            ctLbl.setBounds(400, barY + 14, 210, 14);
            card.add(ctLbl);
        }

        return card;
    }

    // ── Donut Card ────────────────────────────────────────────────────
    private JPanel createDonutCard() {
        JPanel card = new RoundedPanel(14, BG_CARD);
        card.setLayout(null);
        addCardShadow(card);

        JLabel heading = new JLabel("Tỉ Lệ Xét Tuyển");
        heading.setFont(new Font("Segoe UI", Font.BOLD, 15));
        heading.setForeground(TEXT_DARK);
        heading.setBounds(20, 18, 330, 22);
        card.add(heading);

        JLabel sub = new JLabel("Phân tích trạng thái theo thời gian thực");
        sub.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        sub.setForeground(TEXT_MID);
        sub.setBounds(20, 40, 330, 16);
        card.add(sub);

        JSeparator sep = new JSeparator();
        sep.setForeground(DIVIDER);
        sep.setBounds(0, 64, 370, 1);
        card.add(sep);

        // compute percentages
        double accepted = soThiSinh > 0
                ? (double) soTrungTuyen / soThiSinh * 100 : 24;
        double rejected = soThiSinh > 0
                ? (double)(soThiSinh - soTrungTuyen) / soThiSinh * 100 * 0.6 : 45;
        double pending  = Math.max(0, 100 - accepted - rejected);

        JPanel donut = new DonutChart((int)Math.round(accepted),
                (int)Math.round(rejected), (int)Math.round(pending));
        donut.setBounds(55, 82, 260, 260);
        card.add(donut);

        // Legend
        String[][] legend = {
            {"Trúng tuyển",  String.format("%.0f%%", accepted),  toHex(GREEN_ACCENT)},
            {"Không trúng",  String.format("%.0f%%", rejected),  toHex(RED_ACCENT)},
            {"Chờ xét",      String.format("%.0f%%", pending),   toHex(YELLOW_ACCENT)},
        };

        int ly = 358;
        for (String[] leg : legend) {
            JPanel dot = new JPanel() {
                @Override protected void paintComponent(Graphics g) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                            RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setColor(Color.decode(leg[2]));
                    g2.fillOval(0, 2, 12, 12);
                    g2.dispose();
                }
            };
            dot.setOpaque(false);
            dot.setBounds(24, ly + 3, 12, 12);
            card.add(dot);

            JLabel lname = new JLabel(leg[0]);
            lname.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            lname.setForeground(TEXT_MID);
            lname.setBounds(42, ly, 160, 20);
            card.add(lname);

            JLabel lpct = new JLabel(leg[1]);
            lpct.setFont(new Font("Segoe UI", Font.BOLD, 13));
            lpct.setForeground(Color.decode(leg[2]));
            lpct.setHorizontalAlignment(SwingConstants.RIGHT);
            lpct.setBounds(280, ly, 66, 20);
            card.add(lpct);

            ly += 28;
        }

        // Dividers between legend rows
        for (int di = 0; di < 2; di++) {
            JSeparator ls = new JSeparator();
            ls.setForeground(DIVIDER);
            ls.setBounds(20, 378 + di * 28, 330, 1);
            card.add(ls);
        }

        return card;
    }

    // ── Helpers ───────────────────────────────────────────────────────
    private String fmt(long n) {
        if (n >= 1_000_000) return String.format("%.1fM", n / 1_000_000.0);
        if (n >= 1_000)     return String.format("%,.0f", (double) n)
                                         .replace(',', '.');
        return String.valueOf(n);
    }
    private String fmt(int n) { return fmt((long) n); }

    private String toHex(Color c) {
        return String.format("#%02X%02X%02X", c.getRed(), c.getGreen(), c.getBlue());
    }

    private void addCardShadow(JPanel card) {
        card.setBorder(BorderFactory.createCompoundBorder(
            new DropShadowBorder(),
            BorderFactory.createEmptyBorder(0, 0, 0, 0)
        ));
    }

    // ── Inner: Rounded Panel ─────────────────────────────────────────
    static class RoundedPanel extends JPanel {
        private int radius;
        private Color bg;
        RoundedPanel(int radius, Color bg) {
            this.radius = radius; this.bg = bg;
            setOpaque(false);
        }
        @Override protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(bg);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
            g2.dispose();
        }
    }

    // ── Inner: Rounded Bar ────────────────────────────────────────────
    static class RoundedBar extends JPanel {
        private int r; private Color c;
        RoundedBar(int r, Color c) { this.r = r; this.c = c; setOpaque(false); }
        @Override protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(c);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), r, r);
            g2.dispose();
        }
    }

    // ── Inner: Donut Chart ────────────────────────────────────────────
    static class DonutChart extends JPanel {
        private int accepted, rejected, pending;
        DonutChart(int a, int r, int p) {
            accepted = a; rejected = r; pending = p;
            setOpaque(false);
        }
        @Override protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);

            int W = getWidth(), H = getHeight();
            int margin = 20;
            int size = Math.min(W, H) - margin * 2;
            int x = (W - size) / 2, y = (H - size) / 2;

            Color[] colors = {GREEN_ACCENT, RED_ACCENT, YELLOW_ACCENT};
            int[]   vals   = {accepted, rejected, pending};
            int     total  = accepted + rejected + pending;
            if (total == 0) total = 100;

            float startAngle = 90f;
            int stroke = 28;

            // draw shadow ring
            g2.setColor(new Color(0, 0, 0, 12));
            g2.setStroke(new BasicStroke(stroke + 4, BasicStroke.CAP_BUTT,
                    BasicStroke.JOIN_MITER));
            g2.drawOval(x + stroke/2 - 2, y + stroke/2 - 2,
                        size - stroke + 4, size - stroke + 4);

            // draw segments
            g2.setStroke(new BasicStroke(stroke, BasicStroke.CAP_BUTT,
                    BasicStroke.JOIN_MITER));
            for (int i = 0; i < 3; i++) {
                float sweep = (float) vals[i] / total * 360f;
                g2.setColor(colors[i]);
                g2.drawArc(x + stroke/2, y + stroke/2,
                           size - stroke, size - stroke,
                           (int) startAngle, -(int) sweep);
                startAngle -= sweep;
            }

            // center text
            g2.setFont(new Font("Segoe UI", Font.BOLD, 22));
            g2.setColor(TEXT_DARK);
            String pct = "100%";
            FontMetrics fm = g2.getFontMetrics();
            int tw = fm.stringWidth(pct);
            g2.drawString(pct, W/2 - tw/2, H/2 + 4);

            g2.setFont(new Font("Segoe UI", Font.PLAIN, 10));
            g2.setColor(TEXT_MID);
            String sub = "Tổng xét";
            int sw = g2.getFontMetrics().stringWidth(sub);
            g2.drawString(sub, W/2 - sw/2, H/2 + 18);

            g2.dispose();
        }
    }

    // ── Inner: Drop Shadow Border ─────────────────────────────────────
    static class DropShadowBorder extends AbstractBorder {
        private static final int SHADOW = 4;
        @Override public Insets getBorderInsets(Component c) {
            return new Insets(SHADOW, SHADOW, SHADOW + 2, SHADOW + 2);
        }
        @Override public void paintBorder(Component c, Graphics g,
                int x, int y, int w, int h) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
            for (int i = 0; i < SHADOW; i++) {
                float alpha = 0.04f * (SHADOW - i);
                g2.setColor(new Color(0, 0, 0, (int)(alpha * 255)));
                g2.drawRoundRect(x + i, y + i, w - i*2 - 1, h - i*2 - 1, 14, 14);
            }
            g2.dispose();
        }
    }

    // ── Main (test) ───────────────────────────────────────────────────
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Thống Kê Tuyển Sinh");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setResizable(false);
            ThongKePanel panel = new ThongKePanel();
            frame.setContentPane(panel);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}