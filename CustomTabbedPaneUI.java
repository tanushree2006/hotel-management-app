import javax.swing.*;
import javax.swing.plaf.basic.BasicTabbedPaneUI;
import java.awt.*;

public class CustomTabbedPaneUI extends BasicTabbedPaneUI {
    @Override
    protected void installDefaults() {
        super.installDefaults();
        lightHighlight = Color.WHITE;
        shadow = Color.WHITE;
        darkShadow = Color.WHITE;
        focus = HotelManagementSystem.PRIMARY_COLOR;
        
        tabInsets = new Insets(10, 15, 10, 15);
        selectedTabPadInsets = new Insets(0, 0, 0, 0);
        contentBorderInsets = new Insets(10, 0, 0, 0);
    }
    
    @Override
    protected void paintTabBorder(Graphics g, int tabPlacement, int tabIndex, int x, int y, int w, int h, boolean isSelected) {
        if (isSelected) {
            g.setColor(HotelManagementSystem.PRIMARY_COLOR);
            g.fillRect(x, y + h - 3, w, 3);
        }
    }
    
    @Override
    protected void paintTabBackground(Graphics g, int tabPlacement, int tabIndex, int x, int y, int w, int h, boolean isSelected) {
        Graphics2D g2d = (Graphics2D) g;
        if (isSelected) {
            g2d.setColor(Color.WHITE);
        } else {
            g2d.setColor(new Color(243, 244, 246)); // Gray-100
        }
        g2d.fillRect(x, y, w, h);
    }
    
    @Override
    protected void paintContentBorder(Graphics g, int tabPlacement, int selectedIndex) {
        int width = tabPane.getWidth();
        int height = tabPane.getHeight();
        Insets insets = tabPane.getInsets();
        
        int x = insets.left;
        int y = insets.top;
        int w = width - insets.right - insets.left;
        int h = height - insets.top - insets.bottom;
        
        switch (tabPlacement) {
            case TOP:
                y += calculateTabAreaHeight(tabPlacement, runCount, maxTabHeight);
                h -= y - insets.top;
                break;
            case BOTTOM:
                h -= calculateTabAreaHeight(tabPlacement, runCount, maxTabHeight);
                break;
            case LEFT:
                x += calculateTabAreaWidth(tabPlacement, runCount, maxTabWidth);
                w -= x - insets.left;
                break;
            case RIGHT:
                w -= calculateTabAreaWidth(tabPlacement, runCount, maxTabWidth);
                break;
        }
        
        g.setColor(new Color(229, 231, 235)); // Gray-200
        g.drawLine(x, y, x + w, y);
    }
    
    @Override
    protected void paintFocusIndicator(Graphics g, int tabPlacement, Rectangle[] rects, int tabIndex, Rectangle iconRect, Rectangle textRect, boolean isSelected) {
        // Do not paint focus indicator
    }
}
