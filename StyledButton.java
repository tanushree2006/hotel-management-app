import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;

public class StyledButton extends JButton {
    private boolean isSelected = false;
    private boolean isOutline = false;
    
    public StyledButton(String text) {
        super(text);
        
        setFont(HotelManagementSystem.NORMAL_FONT);
        setFocusPainted(false);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setBorder(new EmptyBorder(10, 15, 10, 15));
        
        updateStyle();
        
        // Add hover effect
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (isEnabled()) {
                    if (isOutline) {
                        setBackground(new Color(254, 226, 226)); // Rose-100
                    } else {
                        setBackground(HotelManagementSystem.DARK_COLOR);
                    }
                }
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                updateStyle();
            }
        });
    }
    
    public void setSelected(boolean selected) {
        isSelected = selected;
        updateStyle();
    }
    
    public void setOutline(boolean outline) {
        isOutline = outline;
        updateStyle();
    }
    
    private void updateStyle() {
        if (isSelected) {
            setBackground(HotelManagementSystem.PRIMARY_COLOR);
            setForeground(Color.WHITE);
            setBorderPainted(false);
        } else if (isOutline) {
            setBackground(Color.WHITE);
            setForeground(HotelManagementSystem.PRIMARY_COLOR);
            setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(HotelManagementSystem.SECONDARY_COLOR, 1),
                BorderFactory.createEmptyBorder(9, 14, 9, 14)
            ));
            setBorderPainted(true);
        } else {
            setBackground(HotelManagementSystem.PRIMARY_COLOR);
            setForeground(Color.WHITE);
            setBorderPainted(false);
        }
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
