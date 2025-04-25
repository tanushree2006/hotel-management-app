import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HotelManagementSystem {
    // Define color scheme
    public static final Color PRIMARY_COLOR = new Color(225, 29, 72);  // Rose-500
    public static final Color SECONDARY_COLOR = new Color(249, 168, 212);  // Rose-300
    public static final Color LIGHT_COLOR = new Color(255, 241, 242);  // Rose-50
    public static final Color DARK_COLOR = new Color(159, 18, 57);  // Rose-700
    public static final Color ACCENT_COLOR = new Color(79, 70, 229);  // Indigo-600
    public static final Color BACKGROUND_COLOR = new Color(255, 255, 255);  // White
    public static final Color TEXT_COLOR = new Color(17, 24, 39);  // Gray-900
    public static final Color SUCCESS_COLOR = new Color(34, 197, 94);  // Green-500
    public static final Color WARNING_COLOR = new Color(245, 158, 11);  // Amber-500
    public static final Color INFO_COLOR = new Color(59, 130, 246);  // Blue-500

    // Font definitions
    public static final Font HEADING_FONT = new Font("Arial", Font.BOLD, 20);
    public static final Font SUBHEADING_FONT = new Font("Arial", Font.BOLD, 16);
    public static final Font NORMAL_FONT = new Font("Arial", Font.PLAIN, 14);
    public static final Font SMALL_FONT = new Font("Arial", Font.PLAIN, 12);

    public static void main(String[] args) {
        try {
            // Set system look and feel
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            
            // Customize UI defaults
            UIManager.put("Panel.background", BACKGROUND_COLOR);
            UIManager.put("OptionPane.background", BACKGROUND_COLOR);
            UIManager.put("TextField.background", BACKGROUND_COLOR);
            UIManager.put("TextField.foreground", TEXT_COLOR);
            UIManager.put("TextField.caretForeground", PRIMARY_COLOR);
            UIManager.put("TextField.selectionBackground", SECONDARY_COLOR);
            UIManager.put("TextField.selectionForeground", TEXT_COLOR);
            UIManager.put("Button.focus", new Color(0, 0, 0, 0));
            UIManager.put("TabbedPane.selected", LIGHT_COLOR);
            UIManager.put("TabbedPane.contentAreaColor", BACKGROUND_COLOR);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Launch the application
        SwingUtilities.invokeLater(() -> {
            LoginWindow loginWindow = new LoginWindow();
            loginWindow.setVisible(true);
        });
    }
}
