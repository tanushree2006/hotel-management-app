import javax.swing.*;
import java.awt.*;

public class GradientPanel extends JPanel {
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        
        // Create a gradient from rose-50 to indigo-50
        Color color1 = new Color(255, 241, 242);  // Rose-50
        Color color2 = new Color(238, 242, 255);  // Indigo-50
        
        GradientPaint gp = new GradientPaint(
            0, 0, color1,
            getWidth(), getHeight(), color2
        );
        
        g2d.setPaint(gp);
        g2d.fillRect(0, 0, getWidth(), getHeight());
    }
}
