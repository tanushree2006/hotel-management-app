package owner.utils;

import javax.swing.*;
import java.awt.*;

public class UIUtils {

    /**
     * Creates a rounded border for components
     * @param component The component to apply the border to
     * @param radius The corner radius
     * @param color The border color
     */
    public static void setRoundedBorder(JComponent component, int radius, Color color) {
        component.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(color, 1, true),
                BorderFactory.createEmptyBorder(radius/2, radius, radius/2, radius)
        ));
    }

    /**
     * Creates a panel with a gradient background
     * @param startColor The start color of the gradient
     * @param endColor The end color of the gradient
     * @return A panel with the specified gradient background
     */
    public static JPanel createGradientPanel(Color startColor, Color endColor) {
        return new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gp = new GradientPaint(0, 0, startColor,
                        getWidth(), getHeight(), endColor);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
    }

    /**
     * Styles a button with modern look and feel
     * @param button The button to style
     * @param bgColor The background color
     * @param fgColor The foreground (text) color
     */
    public static void styleButton(JButton button, Color bgColor, Color fgColor) {
        button.setBackground(bgColor);
        button.setForeground(fgColor);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(true);
        button.setOpaque(true);
        button.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    /**
     * Creates a status label with appropriate styling based on status
     * @param status The status text
     * @return A styled JLabel for the status
     */
    public static JLabel createStatusLabel(String status) {
        JLabel label = new JLabel(status);
        label.setFont(new Font("Arial", Font.PLAIN, 11));
        label.setOpaque(true);
        label.setBorder(BorderFactory.createEmptyBorder(2, 5, 2, 5));

        if (status.equalsIgnoreCase("Active") || status.equalsIgnoreCase("On Duty") ||
                status.equalsIgnoreCase("Available") || status.equalsIgnoreCase("Completed")) {
            label.setBackground(new Color(209, 250, 229));
            label.setForeground(new Color(6, 95, 70));
        } else if (status.equalsIgnoreCase("Pending") || status.equalsIgnoreCase("Claimed (Pending)") ||
                status.equalsIgnoreCase("Upcoming") || status.equalsIgnoreCase("In Progress")) {
            label.setBackground(new Color(254, 243, 199));
            label.setForeground(new Color(146, 64, 14));
        } else if (status.equalsIgnoreCase("Cancelled") || status.equalsIgnoreCase("Off Duty") ||
                status.equalsIgnoreCase("Maintenance") || status.equalsIgnoreCase("Rejected")) {
            label.setBackground(new Color(254, 226, 226));
            label.setForeground(new Color(153, 27, 27));
        } else {
            label.setBackground(new Color(229, 231, 235));
            label.setForeground(new Color(75, 85, 99));
        }

        return label;
    }

    /**
     * Centers a window on the screen
     * @param window The window to center
     */
    public static void centerOnScreen(Window window) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screenSize.width - window.getWidth()) / 2;
        int y = (screenSize.height - window.getHeight()) / 2;
        window.setLocation(x, y);
    }
}