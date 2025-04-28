package owner.views;

import owner.utils.UIUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginView extends JFrame {
    private JTabbedPane tabbedPane;
    private JTextField ownerUsernameField;
    private JPasswordField ownerPasswordField;
    private JTextField staffIdField;
    private JPasswordField staffPasswordField;
    private JButton ownerLoginButton;
    private JButton staffLoginButton;

    public LoginView() {
        setTitle("MiraVelle Hotel Management System - Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 400);
        setLocationRelativeTo(null);

        initComponents();
    }

    private void initComponents() {
        // Main panel with gradient background
        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gp = new GradientPaint(0, 0, new Color(240, 249, 255),
                        getWidth(), getHeight(), new Color(224, 231, 255));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        mainPanel.setLayout(new BorderLayout(20, 20));

        // Logo and title panel
        JPanel logoPanel = new JPanel(new BorderLayout());
        logoPanel.setOpaque(false);

        JLabel logoLabel = new JLabel("MiraVelle", SwingConstants.CENTER);
        logoLabel.setFont(new Font("Arial", Font.BOLD, 28));
        logoLabel.setForeground(new Color(59, 130, 246));

        JLabel subtitleLabel = new JLabel("Hotel Management System", SwingConstants.CENTER);
        subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        subtitleLabel.setForeground(new Color(107, 114, 128));

        logoPanel.add(logoLabel, BorderLayout.CENTER);
        logoPanel.add(subtitleLabel, BorderLayout.SOUTH);

        // Login form
        tabbedPane = new JTabbedPane();

        // Owner tab
        JPanel ownerPanel = new JPanel();
        ownerPanel.setLayout(new BoxLayout(ownerPanel, BoxLayout.Y_AXIS));
        ownerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        ownerPanel.setOpaque(false);

        JLabel ownerTitleLabel = new JLabel("Owner Login");
        ownerTitleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        ownerTitleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel ownerSubtitleLabel = new JLabel("Access your hotel management dashboard");
        ownerSubtitleLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        ownerSubtitleLabel.setForeground(new Color(107, 114, 128));
        ownerSubtitleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel usernameLabel = new JLabel("Username");
        usernameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        ownerUsernameField = new JTextField(20);
        ownerUsernameField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        ownerUsernameField.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        ownerPasswordField = new JPasswordField(20);
        ownerPasswordField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        ownerPasswordField.setAlignmentX(Component.LEFT_ALIGNMENT);

        ownerLoginButton = new JButton("Login");
        ownerLoginButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        ownerLoginButton.setBackground(new Color(59, 130, 246));
        ownerLoginButton.setForeground(Color.WHITE);
        ownerLoginButton.setFocusPainted(false);

        ownerPanel.add(ownerTitleLabel);
        ownerPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        ownerPanel.add(ownerSubtitleLabel);
        ownerPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        ownerPanel.add(usernameLabel);
        ownerPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        ownerPanel.add(ownerUsernameField);
        ownerPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        ownerPanel.add(passwordLabel);
        ownerPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        ownerPanel.add(ownerPasswordField);
        ownerPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        ownerPanel.add(ownerLoginButton);

        // Staff tab
        JPanel staffPanel = new JPanel();
        staffPanel.setLayout(new BoxLayout(staffPanel, BoxLayout.Y_AXIS));
        staffPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        staffPanel.setOpaque(false);

        JLabel staffTitleLabel = new JLabel("Staff Login");
        staffTitleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        staffTitleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel staffSubtitleLabel = new JLabel("Access your staff portal");
        staffSubtitleLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        staffSubtitleLabel.setForeground(new Color(107, 114, 128));
        staffSubtitleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel staffIdLabel = new JLabel("Staff ID");
        staffIdLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        staffIdField = new JTextField(20);
        staffIdField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        staffIdField.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel staffPasswordLabel = new JLabel("Password");
        staffPasswordLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        staffPasswordField = new JPasswordField(20);
        staffPasswordField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        staffPasswordField.setAlignmentX(Component.LEFT_ALIGNMENT);

        staffLoginButton = new JButton("Login");
        staffLoginButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        staffLoginButton.setBackground(new Color(59, 130, 246));
        staffLoginButton.setForeground(Color.WHITE);
        staffLoginButton.setFocusPainted(false);

        staffPanel.add(staffTitleLabel);
        staffPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        staffPanel.add(staffSubtitleLabel);
        staffPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        staffPanel.add(staffIdLabel);
        staffPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        staffPanel.add(staffIdField);
        staffPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        staffPanel.add(staffPasswordLabel);
        staffPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        staffPanel.add(staffPasswordField);
        staffPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        staffPanel.add(staffLoginButton);

        // Add tabs
        tabbedPane.addTab("Owner", ownerPanel);
        tabbedPane.addTab("Staff", staffPanel);

        // Add components to main panel
        mainPanel.add(logoPanel, BorderLayout.NORTH);
        mainPanel.add(tabbedPane, BorderLayout.CENTER);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        // Add main panel to frame
        setContentPane(mainPanel);

        // Add action listeners
        ownerLoginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleLogin();
            }
        });

        staffLoginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleLogin();
            }
        });
    }

    private void handleLogin() {
        ownerLoginButton.setText("Logging in...");
        ownerLoginButton.setEnabled(false);
        staffLoginButton.setEnabled(false);

        Timer timer = new Timer(1500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close login window

                SwingUtilities.invokeLater(() -> {
                    OwnerDashboard ownerDashboard = new OwnerDashboard();
                    DashboardView dashboardView = new DashboardView(ownerDashboard);
                    ownerDashboard.setContentPane(dashboardView);
                    ownerDashboard.setVisible(true);
                });
            }
        });
        timer.setRepeats(false);
        timer.start();
    }
}
