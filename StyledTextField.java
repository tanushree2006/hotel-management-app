import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;

public class StyledTextField extends JTextField {
    public StyledTextField() {
        setFont(HotelManagementSystem.NORMAL_FONT);
        setForeground(HotelManagementSystem.TEXT_COLOR);
        setBorder(new CompoundBorder(
            new LineBorder(HotelManagementSystem.SECONDARY_COLOR, 1),
            new EmptyBorder(8, 10, 8, 10)
        ));
        
        // Add focus effect
        addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                setBorder(new CompoundBorder(
                    new LineBorder(HotelManagementSystem.PRIMARY_COLOR, 2),
                    new EmptyBorder(7, 9, 7, 9)
                ));
            }
            
            @Override
            public void focusLost(FocusEvent e) {
                setBorder(new CompoundBorder(
                    new LineBorder(HotelManagementSystem.SECONDARY_COLOR, 1),
                    new EmptyBorder(8, 10, 8, 10)
                ));
            }
        });
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        g2d.setColor(getBackground());
        g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 5, 5);
        
        super.paintComponent(g);
        g2d.dispose();
    }
}
