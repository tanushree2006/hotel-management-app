package owner.views;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SettingsView extends JPanel {
    private JTabbedPane tabbedPane;

    public SettingsView() {
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(20, 20, 20, 20));
        setBackground(Color.WHITE);

        initComponents();
    }

    private void initComponents() {
        // Title panel
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setOpaque(true);
        titlePanel.setBackground(Color.WHITE);
        titlePanel.setBorder(new EmptyBorder(0, 0, 20, 0));

        JLabel titleLabel = new JLabel("Settings");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(new Color(30, 64, 175));

        JLabel subtitleLabel = new JLabel("Configure your hotel management system settings.");
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        subtitleLabel.setForeground(new Color(90, 90, 90));

        JPanel titleTextPanel = new JPanel(new GridLayout(2, 1));
        titleTextPanel.setOpaque(false);
        titleTextPanel.add(titleLabel);
        titleTextPanel.add(subtitleLabel);

        titlePanel.add(titleTextPanel, BorderLayout.WEST);

        // Tabbed pane with bigger font and padding
        tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tabbedPane.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Add tabs wrapped in scroll panes for better visibility on small screens
        tabbedPane.addTab("General", wrapInScrollPane(createGeneralPanel()));
        tabbedPane.addTab("User Profile", wrapInScrollPane(createUserProfilePanel()));
        tabbedPane.addTab("Notifications", wrapInScrollPane(createNotificationsPanel()));
        tabbedPane.addTab("System", wrapInScrollPane(createSystemPanel()));
        tabbedPane.addTab("About", wrapInScrollPane(createAboutPanel()));

        add(titlePanel, BorderLayout.NORTH);
        add(tabbedPane, BorderLayout.CENTER);
    }

    private JScrollPane wrapInScrollPane(JPanel panel) {
        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        return scrollPane;
    }

    // Helper to create section panels with white background and border
    private JPanel createSettingsSection(String title) {
        JPanel sectionPanel = new JPanel();
        sectionPanel.setLayout(new BoxLayout(sectionPanel, BoxLayout.Y_AXIS));
        sectionPanel.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(220, 220, 220), 1, true),
                new EmptyBorder(15, 15, 15, 15)
        ));
        sectionPanel.setBackground(Color.WHITE);

        JLabel sectionLabel = new JLabel(title);
        sectionLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        sectionLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        sectionLabel.setBorder(new EmptyBorder(0, 0, 10, 0));

        sectionPanel.add(sectionLabel);
        return sectionPanel;
    }

    // Helper to create individual setting items with label and container for input
    private JPanel createSettingItem(String labelText) {
        JPanel itemPanel = new JPanel(new BorderLayout(10, 5));
        itemPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        itemPanel.setOpaque(false);
        itemPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));

        JLabel label = new JLabel(labelText + ":");
        label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        label.setPreferredSize(new Dimension(150, 30));

        itemPanel.add(label, BorderLayout.WEST);
        return itemPanel;
    }

    private JPanel createGeneralPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        panel.setBackground(Color.WHITE);

        // Hotel Information Section
        JPanel hotelInfoPanel = createSettingsSection("Hotel Information");

        JPanel namePanel = createSettingItem("Hotel Name");
        JTextField nameField = new JTextField("MiraVelle Hotel");
        nameField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        namePanel.add(nameField, BorderLayout.CENTER);

        JPanel addressPanel = createSettingItem("Address");
        JTextField addressField = new JTextField("123 Main Street, Anytown, USA");
        addressField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        addressPanel.add(addressField, BorderLayout.CENTER);

        JPanel phonePanel = createSettingItem("Phone Number");
        JTextField phoneField = new JTextField("+1 (555) 123-4567");
        phoneField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        phonePanel.add(phoneField, BorderLayout.CENTER);

        JPanel emailPanel = createSettingItem("Email");
        JTextField emailField = new JTextField("info@miravelle.com");
        emailField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        emailPanel.add(emailField, BorderLayout.CENTER);

        hotelInfoPanel.add(namePanel);
        hotelInfoPanel.add(addressPanel);
        hotelInfoPanel.add(phonePanel);
        hotelInfoPanel.add(emailPanel);

        // Display Settings Section
        JPanel displayPanel = createSettingsSection("Display Settings");

        JPanel themePanel = createSettingItem("Theme");
        JComboBox<String> themeCombo = new JComboBox<>(new String[]{"Light", "Dark", "System Default"});
        themeCombo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        themePanel.add(themeCombo, BorderLayout.CENTER);

        JPanel dateFormatPanel = createSettingItem("Date Format");
        JComboBox<String> dateFormatCombo = new JComboBox<>(new String[]{"MM/DD/YYYY", "DD/MM/YYYY", "YYYY-MM-DD"});
        dateFormatCombo.setSelectedItem("YYYY-MM-DD");
        dateFormatCombo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        dateFormatPanel.add(dateFormatCombo, BorderLayout.CENTER);

        JPanel timeFormatPanel = createSettingItem("Time Format");
        JComboBox<String> timeFormatCombo = new JComboBox<>(new String[]{"12-hour (AM/PM)", "24-hour"});
        timeFormatCombo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        timeFormatPanel.add(timeFormatCombo, BorderLayout.CENTER);

        displayPanel.add(themePanel);
        displayPanel.add(dateFormatPanel);
        displayPanel.add(timeFormatPanel);

        // Currency Settings Section
        JPanel currencyPanel = createSettingsSection("Currency Settings");

        JPanel currencyTypePanel = createSettingItem("Currency");
        JComboBox<String> currencyCombo = new JComboBox<>(new String[]{"USD ($)", "EUR (€)", "GBP (£)", "JPY (¥)"});
        currencyCombo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        currencyTypePanel.add(currencyCombo, BorderLayout.CENTER);

        JPanel taxRatePanel = createSettingItem("Default Tax Rate (%)");
        JTextField taxRateField = new JTextField("8.5");
        taxRateField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        taxRatePanel.add(taxRateField, BorderLayout.CENTER);

        currencyPanel.add(currencyTypePanel);
        currencyPanel.add(taxRatePanel);

        // Save button
        JButton saveButton = new JButton("Save Changes");
        saveButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        saveButton.setBackground(new Color(59, 130, 246));
        saveButton.setForeground(new Color(12, 22, 37));
        saveButton.setFocusPainted(false);
        saveButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        saveButton.setMaximumSize(new Dimension(200, 40));
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(SettingsView.this,
                        "Settings saved successfully.",
                        "Settings Saved",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });

        panel.add(hotelInfoPanel);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(displayPanel);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(currencyPanel);
        panel.add(Box.createRigidArea(new Dimension(0, 30)));
        panel.add(saveButton);

        return panel;
    }

    private JPanel createUserProfilePanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        panel.setBackground(Color.WHITE);

        JPanel profilePanel = createSettingsSection("Profile Information");

        JPanel namePanel = createSettingItem("Full Name");
        JTextField nameField = new JTextField("Admin User");
        nameField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        namePanel.add(nameField, BorderLayout.CENTER);

        JPanel usernamePanel = createSettingItem("Username");
        JTextField usernameField = new JTextField("admin");
        usernameField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        usernamePanel.add(usernameField, BorderLayout.CENTER);

        JPanel emailPanel = createSettingItem("Email");
        JTextField emailField = new JTextField("admin@miravelle.com");
        emailField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        emailPanel.add(emailField, BorderLayout.CENTER);

        JPanel phonePanel = createSettingItem("Phone");
        JTextField phoneField = new JTextField("+1 (555) 987-6543");
        phoneField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        phonePanel.add(phoneField, BorderLayout.CENTER);

        profilePanel.add(namePanel);
        profilePanel.add(usernamePanel);
        profilePanel.add(emailPanel);
        profilePanel.add(phonePanel);

        JPanel passwordPanel = createSettingsSection("Change Password");

        JPanel currentPasswordPanel = createSettingItem("Current Password");
        JPasswordField currentPasswordField = new JPasswordField();
        currentPasswordField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        currentPasswordPanel.add(currentPasswordField, BorderLayout.CENTER);

        JPanel newPasswordPanel = createSettingItem("New Password");
        JPasswordField newPasswordField = new JPasswordField();
        newPasswordField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        newPasswordPanel.add(newPasswordField, BorderLayout.CENTER);

        JPanel confirmPasswordPanel = createSettingItem("Confirm New Password");
        JPasswordField confirmPasswordField = new JPasswordField();
        confirmPasswordField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        confirmPasswordPanel.add(confirmPasswordField, BorderLayout.CENTER);

        passwordPanel.add(currentPasswordPanel);
        passwordPanel.add(newPasswordPanel);
        passwordPanel.add(confirmPasswordPanel);

        JButton saveButton = new JButton("Save Profile");
        saveButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        saveButton.setBackground(new Color(59, 130, 246));
        saveButton.setForeground(new Color(13, 20, 28));
        saveButton.setFocusPainted(false);
        saveButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        saveButton.setMaximumSize(new Dimension(200, 40));
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(SettingsView.this,
                        "Profile updated successfully.",
                        "Profile Updated",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });

        panel.add(profilePanel);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(passwordPanel);
        panel.add(Box.createRigidArea(new Dimension(0, 30)));
        panel.add(saveButton);

        return panel;
    }

    private JPanel createNotificationsPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        panel.setBackground(Color.WHITE);

        JPanel emailPanel = createSettingsSection("Email Notifications");

        JPanel bookingPanel = createSettingItem("New Bookings");
        JCheckBox bookingCheck = new JCheckBox();
        bookingCheck.setSelected(true);
        bookingPanel.add(bookingCheck, BorderLayout.CENTER);

        JPanel checkoutPanel = createSettingItem("Upcoming Checkouts");
        JCheckBox checkoutCheck = new JCheckBox();
        checkoutCheck.setSelected(true);
        checkoutPanel.add(checkoutCheck, BorderLayout.CENTER);

        JPanel maintenancePanel = createSettingItem("Maintenance Alerts");
        JCheckBox maintenanceCheck = new JCheckBox();
        maintenanceCheck.setSelected(true);
        maintenancePanel.add(maintenanceCheck, BorderLayout.CENTER);

        JPanel lostFoundPanel = createSettingItem("Lost & Found Claims");
        JCheckBox lostFoundCheck = new JCheckBox();
        lostFoundCheck.setSelected(true);
        lostFoundPanel.add(lostFoundCheck, BorderLayout.CENTER);

        emailPanel.add(bookingPanel);
        emailPanel.add(checkoutPanel);
        emailPanel.add(maintenancePanel);
        emailPanel.add(lostFoundPanel);

        JPanel systemPanel = createSettingsSection("System Notifications");

        JPanel desktopPanel = createSettingItem("Desktop Notifications");
        JCheckBox desktopCheck = new JCheckBox();
        desktopCheck.setSelected(true);
        desktopPanel.add(desktopCheck, BorderLayout.CENTER);

        JPanel soundPanel = createSettingItem("Sound Alerts");
        JCheckBox soundCheck = new JCheckBox();
        soundCheck.setSelected(false);
        soundPanel.add(soundCheck, BorderLayout.CENTER);

        JPanel reportPanel = createSettingItem("Daily Reports");
        JCheckBox reportCheck = new JCheckBox();
        reportCheck.setSelected(true);
        reportPanel.add(reportCheck, BorderLayout.CENTER);

        systemPanel.add(desktopPanel);
        systemPanel.add(soundPanel);
        systemPanel.add(reportPanel);

        JButton saveButton = new JButton("Save Notification Settings");
        saveButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        saveButton.setBackground(new Color(59, 130, 246));
        saveButton.setForeground(new Color(12, 21, 35));
        saveButton.setFocusPainted(false);
        saveButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        saveButton.setMaximumSize(new Dimension(250, 40));
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(SettingsView.this,
                        "Notification settings saved successfully.",
                        "Settings Saved",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });

        panel.add(emailPanel);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(systemPanel);
        panel.add(Box.createRigidArea(new Dimension(0, 30)));
        panel.add(saveButton);

        return panel;
    }

    private JPanel createSystemPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        panel.setBackground(Color.WHITE);

        JPanel backupPanel = createSettingsSection("Backup Settings");

        JPanel autoBackupPanel = createSettingItem("Automatic Backups");
        JCheckBox autoBackupCheck = new JCheckBox();
        autoBackupCheck.setSelected(true);
        autoBackupPanel.add(autoBackupCheck, BorderLayout.CENTER);

        JPanel backupFrequencyPanel = createSettingItem("Backup Frequency");
        JComboBox<String> backupFrequencyCombo = new JComboBox<>(new String[]{"Daily", "Weekly", "Monthly"});
        backupFrequencyCombo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        backupFrequencyPanel.add(backupFrequencyCombo, BorderLayout.CENTER);

        JPanel backupLocationPanel = createSettingItem("Backup Location");
        JTextField backupLocationField = new JTextField("/backups");
        backupLocationField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        backupLocationPanel.add(backupLocationField, BorderLayout.CENTER);

        backupPanel.add(autoBackupPanel);
        backupPanel.add(backupFrequencyPanel);
        backupPanel.add(backupLocationPanel);

        JPanel databasePanel = createSettingsSection("Database Settings");

        JPanel hostPanel = createSettingItem("Database Host");
        JTextField hostField = new JTextField("localhost");
        hostField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        hostPanel.add(hostField, BorderLayout.CENTER);

        JPanel portPanel = createSettingItem("Database Port");
        JTextField portField = new JTextField("3306");
        portField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        portPanel.add(portField, BorderLayout.CENTER);

        JPanel namePanel = createSettingItem("Database Name");
        JTextField nameField = new JTextField("miravelle_hotel");
        nameField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        namePanel.add(nameField, BorderLayout.CENTER);

        databasePanel.add(hostPanel);
        databasePanel.add(portPanel);
        databasePanel.add(namePanel);

        JButton saveButton = new JButton("Save System Settings");
        saveButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        saveButton.setBackground(new Color(59, 130, 246));
        saveButton.setForeground(new Color(6, 14, 27));
        saveButton.setFocusPainted(false);
        saveButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        saveButton.setMaximumSize(new Dimension(250, 40));
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(SettingsView.this,
                        "System settings saved successfully.",
                        "Settings Saved",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });

        panel.add(backupPanel);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(databasePanel);
        panel.add(Box.createRigidArea(new Dimension(0, 30)));
        panel.add(saveButton);

        return panel;
    }

    private JPanel createAboutPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(new EmptyBorder(40, 40, 40, 40));

        JLabel aboutLabel = new JLabel("<html><center>" +
                "<h1>MiraVelle Hotel Management System</h1>" +
                "<p>Version 1.0.0</p>" +
                "<p>© 2025 MiraVelle Inc.</p>" +
                "<p>All rights reserved.</p>" +
                "</center></html>");
        aboutLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        aboutLabel.setHorizontalAlignment(SwingConstants.CENTER);

        panel.add(aboutLabel, BorderLayout.CENTER);

        return panel;
    }
}
