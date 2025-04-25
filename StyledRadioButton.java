import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class StyledRadioButton extends JRadioButton {
    public StyledRadioButton(String text) {
        super(text);
        setFont(HotelManagementSystem.NORMAL_FONT);
        setForeground(HotelManagementSystem.TEXT_COLOR);
        setBackground(Color.WHITE);
        setFocusPainted(false);
        
        // Custom icon
        setIcon(createRadioIcon(false));
        setSelectedIcon(createRadioIcon(true));
        
        // Add hover effect
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
        });
    }
    
    private ImageIcon createRadioIcon(boolean selected) {
        int size = 16;
        BufferedImage image = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();
        
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        if (selected) {
            // Outer circle
            g2d.setColor(HotelManagementSystem.PRIMARY_COLOR);
            g2d.fillOval(0, 0, size, size);
            
            // Inner circle
            g2d.setColor(Color.WHITE);
            g2d.fillOval(4, 4, size - 8, size - 8);
            
            // Center dot
            g2d.setColor(HotelManagementSystem.PRIMARY_COLOR);
            g2d.fillOval(5, 5, size - 10, size - 10);
        } else {
            // Outer circle
            g2d.setColor(new Color(209, 213, 219)); // Gray-300
            g2d.drawOval(0, 0, size - 1, size - 1);
            
            // Inner circle
            g2d.setColor(Color.WHITE);
            g2d.fillOval(1, 1, size - 2, size - 2);
        }
        
        g2d.dispose();
        return new ImageIcon(image);
    }
}
