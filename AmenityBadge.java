import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class AmenityBadge extends JLabel {
    public AmenityBadge(String text) {
        super(text);
        setOpaque(true);
        setFont(new Font("Arial", Font.PLAIN, 11));
        setBorder(new EmptyBorder(3, 6, 3, 6));
        setBackground(HotelManagementSystem.LIGHT_COLOR);
        setForeground(HotelManagementSystem.PRIMARY_COLOR);
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        g2d.setColor(getBackground());
        g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
        
        super.paintComponent(g);
        g2d.dispose();
    }
}
