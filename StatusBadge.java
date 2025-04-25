import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class StatusBadge extends JLabel {
    public StatusBadge(String text) {
        super(text);
        setOpaque(true);
        setFont(new Font("Arial", Font.BOLD, 12));
        setBorder(new EmptyBorder(4, 8, 4, 8));
        
        switch (text.toLowerCase()) {
            case "available":
            case "active":
            case "confirmed":
            case "on duty":
                setBackground(HotelManagementSystem.SUCCESS_COLOR);
                setForeground(Color.WHITE);
                break;
            case "occupied":
            case "pending":
                setBackground(HotelManagementSystem.INFO_COLOR);
                setForeground(Color.WHITE);
                break;
            case "maintenance":
            case "off duty":
                setBackground(HotelManagementSystem.WARNING_COLOR);
                setForeground(Color.WHITE);
                break;
            case "completed":
                setBackground(new Color(16, 185, 129)); // Green-600
                setForeground(Color.WHITE);
                break;
            default:
                setBackground(new Color(156, 163, 175)); // Gray-400
                setForeground(Color.WHITE);
        }
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        g2d.setColor(getBackground());
        g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
        
        super.paintComponent(g);
        g2d.dispose();
    }
}
