package owner.views;

import owner.controllers.BookingController;
import owner.controllers.LostFoundController;
import owner.controllers.ReportController;
import owner.controllers.RoomController;
import owner.controllers.StaffController;
import owner.utils.CurrencyUtils;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.lang.reflect.Method;

public class OwnerDashboard extends JFrame {

    public OwnerDashboard() {
        initializeUI();
    }

    private void initializeUI() {
        setTitle("MiraVelle : A House of Distinction - Hotel Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 800);
        setLocationRelativeTo(null);

        // Initialize controllers
        RoomController roomController = new RoomController();
        BookingController bookingController = new BookingController();
        StaffController staffController = new StaffController();
        LostFoundController lostFoundController = new LostFoundController();
        ReportController reportController = new ReportController(
                roomController, bookingController, staffController, lostFoundController
        );

        // Create and set the dashboard view
        DashboardView dashboardView = new DashboardView(this);
        setContentPane(dashboardView);


        // Add window listener to handle logout
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                handleLogout();
            }
        });

    }
    private void handleLogout() {
        int option = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to logout?",
                "Logout Confirmation",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        if (option == JOptionPane.YES_OPTION) {
            dispose();
            SwingUtilities.invokeLater(() -> {
                try {
                    // Use reflection to create LoginWindow instance
                    Class<?> loginWindowClass = Class.forName("LoginWindow");
                    Object loginWindow = loginWindowClass.getDeclaredConstructor().newInstance();
                    Method setVisibleMethod = loginWindowClass.getMethod("setVisible", boolean.class);
                    setVisibleMethod.invoke(loginWindow, true);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(
                            null,
                            "Error returning to login screen. Please restart the application.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE
                    );
                }
            });
        }
    }


    public static void main(String[] args) {
        try {
            // Set system look and feel
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

            // Set better font rendering
            System.setProperty("awt.useSystemAAFontSettings", "on");
            System.setProperty("swing.aatext", "true");
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            OwnerDashboard dashboard = new OwnerDashboard();
            dashboard.setVisible(true);
        });
    }
}