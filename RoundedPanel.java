import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class RoundedPanel extends JPanel {
    private int radius;
    private Color shadowColor = new Color(0, 0, 0, 50);
    private int shadowSize = 3;
    private boolean drawShadow = true;

    public RoundedPanel(int radius) {
        this.radius = radius;
        setOpaque(false);
    }

    public RoundedPanel(int radius, boolean drawShadow) {
        this.radius = radius;
        this.drawShadow = drawShadow;
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Draw shadow if enabled
        if (drawShadow) {
            for (int i = 0; i < shadowSize; i++) {
                g2d.setColor(new Color(
                        shadowColor.getRed(),
                        shadowColor.getGreen(),
                        shadowColor.getBlue(),
                        shadowColor.getAlpha() / (i + 1)
                ));
                g2d.fill(new RoundRectangle2D.Double(
                        i, i,
                        getWidth() - i * 2,
                        getHeight() - i * 2,
                        radius, radius
                ));
            }
        }

        // Draw panel background
        g2d.setColor(getBackground());
        g2d.fill(new RoundRectangle2D.Double(
                shadowSize, shadowSize,
                getWidth() - shadowSize * 2,
                getHeight() - shadowSize * 2,
                radius, radius
        ));

        g2d.dispose();

        super.paintComponent(g);
    }

    public void setShadowColor(Color color) {
        this.shadowColor = color;
        repaint();
    }

    public void setShadowSize(int size) {
        this.shadowSize = size;
        repaint();
    }

    public void setDrawShadow(boolean draw) {
        this.drawShadow = draw;
        repaint();
    }
}