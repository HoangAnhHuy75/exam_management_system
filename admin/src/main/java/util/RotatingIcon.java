package util;
import javax.swing.Icon;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;


public class RotatingIcon implements Icon {
    private Icon icon;
    private double angle = 0;

    public RotatingIcon(Icon icon) {
        this.icon = icon;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    @Override
    public int getIconWidth() {
        return icon.getIconWidth();
    }

    @Override
    public int getIconHeight() {
        return icon.getIconHeight();
    }

    @Override
    public void paintIcon(Component c, Graphics g, int x, int y) {
        Graphics2D g2 = (Graphics2D) g.create();

        int cx = x + icon.getIconWidth() / 2;
        int cy = y + icon.getIconHeight() / 2;

        g2.rotate(Math.toRadians(angle), cx, cy);
        icon.paintIcon(c, g2, x, y);

        g2.dispose();
    }
}
