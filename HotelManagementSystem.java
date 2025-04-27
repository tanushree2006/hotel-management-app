import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HotelManagementSystem {
    // Define luxurious color scheme
    public static final Color PRIMARY_COLOR = new Color(25, 55, 109);  // Rich navy blue
    public static final Color SECONDARY_COLOR = new Color(87, 108, 188);  // Royal blue
    public static final Color LIGHT_COLOR = new Color(240, 245, 255);  // Light blue-white
    public static final Color DARK_COLOR = new Color(15, 32, 67);  // Deep navy
    public static final Color ACCENT_COLOR = new Color(212, 175, 55);  // Gold
    public static final Color ACCENT_SECONDARY = new Color(180, 122, 73);  // Copper/bronze
    public static final Color BACKGROUND_COLOR = new Color(255, 255, 255);  // White
    public static final Color TEXT_COLOR = new Color(33, 33, 33);  // Near black
    public static final Color SUCCESS_COLOR = new Color(39, 174, 96);  // Emerald green
    public static final Color WARNING_COLOR = new Color(230, 126, 34);  // Burnt orange
    public static final Color ERROR_COLOR = new Color(192, 57, 43);  // Deep red
    public static final Color INFO_COLOR = new Color(52, 152, 219);  // Bright blue

    // Elegant font definitions
    public static final Font HEADING_FONT = new Font("Garamond", Font.BOLD, 24);
    public static final Font SUBHEADING_FONT = new Font("Garamond", Font.BOLD, 20);
    public static final Font NORMAL_FONT = new Font("Palatino", Font.PLAIN, 16);
    public static final Font SMALL_FONT = new Font("Palatino", Font.PLAIN, 14);
    public static final Font BUTTON_FONT = new Font("Palatino", Font.BOLD, 16);

    public static void main(String[] args) {
        try {
            // Set system look and feel
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

            // Customize UI defaults
            UIManager.put("Panel.background", BACKGROUND_COLOR);
            UIManager.put("OptionPane.background", BACKGROUND_COLOR);
            UIManager.put("TextField.background", LIGHT_COLOR);
            UIManager.put("TextField.foreground", TEXT_COLOR);
            UIManager.put("TextField.caretForeground", PRIMARY_COLOR);
            UIManager.put("TextField.selectionBackground", SECONDARY_COLOR);
            UIManager.put("TextField.selectionForeground", LIGHT_COLOR);
            UIManager.put("Button.focus", new Color(0, 0, 0, 0));
            UIManager.put("TabbedPane.selected", LIGHT_COLOR);
            UIManager.put("TabbedPane.contentAreaColor", BACKGROUND_COLOR);
            UIManager.put("Table.selectionBackground", SECONDARY_COLOR);
            UIManager.put("Table.selectionForeground", LIGHT_COLOR);
            UIManager.put("Table.gridColor", new Color(240, 240, 240));
            UIManager.put("ScrollPane.background", BACKGROUND_COLOR);
            UIManager.put("ComboBox.selectionBackground", SECONDARY_COLOR);
            UIManager.put("ComboBox.selectionForeground", LIGHT_COLOR);
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