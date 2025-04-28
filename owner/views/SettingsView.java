package owner.views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SettingsView extends JPanel {
    private JTabbedPane tabbedPane;

    public SettingsView() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        setBackground(Color.WHITE);

        initComponents();
    }

    private void initComponents() {
        // Title panel
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setOpaque(false);

        JLabel titleLabel = new JLabel("Settings");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));

        JLabel subtitleLabel = new JLabel("Configure your hotel management system settings.");
        subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        subtitleLabel.setForeground(new Color(107, 114, 128));

        titlePanel.add(titleLabel, BorderLayout.NORTH);
        titlePanel.add(subtitleLabel, BorderLayout.CENTER);

        // Create tabbed pane
        tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Arial", Font.PLAIN, 12));

        // Add tabs
        tabbedPane.addTab("General", createGeneralPanel());
        tabbedPane.addTab("User Profile", createUserProfilePanel());
        tabbedPane.addTab("Notifications", createNotificationsPanel());
        tabbedPane.addTab("System", createSystemPanel());
        tabbedPane.addTab("About", createAboutPanel());

        // Add components to main panel
        add(titlePanel, BorderLayout.NORTH);
        add(tabbedPane, BorderLayout.CENTER);
    }

    private JPanel createGeneralPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setOpaque(false);

        JLabel sectionLabel = new JLabel("General Settings");
        sectionLabel.setFont(new Font("Arial", Font.BOLD, 16));
        sectionLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Hotel Information
        JPanel hotelInfoPanel = createSettingsSection("Hotel Information");

        JPanel namePanel = createSettingItem("Hotel Name");
        JTextField nameField = new JTextField("MiraVelle Hotel");
        nameField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        namePanel.add(nameField);

        JPanel addressPanel = createSettingItem("Address");
        JTextField addressField = new JTextField("123 Main Street, Anytown, USA");
        addressField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        addressPanel.add(addressField);

        JPanel phonePanel = createSettingItem("Phone Number");
        JTextField phoneField = new JTextField("+1 (555) 123-4567");
        phoneField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        phonePanel.add(phoneField);

        JPanel emailPanel = createSettingItem("Email");
        JTextField emailField = new JTextField("info@miravelle.com");
        emailField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        emailPanel.add(emailField);

        hotelInfoPanel.add(namePanel);
        hotelInfoPanel.add(addressPanel);
        hotelInfoPanel.add(phonePanel);
        hotelInfoPanel.add(emailPanel);

        // Display Settings
        JPanel displayPanel = createSettingsSection("Display Settings");

        JPanel themePanel = createSettingItem("Theme");
        JComboBox<String> themeCombo = new JComboBox<>(new String[]{"Light", "Dark", "System Default"});
        themeCombo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        themePanel.add(themeCombo);

        JPanel dateFormatPanel = createSettingItem("Date Format");
        JComboBox<String> dateFormatCombo = new JComboBox<>(new String[]{"MM/DD/YYYY", "DD/MM/YYYY", "YYYY-MM-DD"});
        dateFormatCombo.setSelectedItem("YYYY-MM-DD");
        dateFormatCombo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        dateFormatPanel.add(dateFormatCombo);

        JPanel timeFormatPanel = createSettingItem("Time Format");
        JComboBox<String> timeFormatCombo = new JComboBox<>(new String[]{"12-hour (AM/PM)", "24-hour"});
        timeFormatCombo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        timeFormatPanel.add(timeFormatCombo);

        displayPanel.add(themePanel);
        displayPanel.add(dateFormatPanel);
        displayPanel.add(timeFormatPanel);

        // Currency Settings
        JPanel currencyPanel = createSettingsSection("Currency Settings");

        JPanel currencyTypePanel = createSettingItem("Currency");
        JComboBox<String> currencyCombo = new JComboBox<>(new String[]{"USD ($)", "EUR (€)", "GBP (£)", "JPY (¥)"});
        currencyCombo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        currencyTypePanel.add(currencyCombo);

        JPanel taxRatePanel = createSettingItem("Default Tax Rate (%)");
        JTextField taxRateField = new JTextField("8.5");
        taxRateField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        taxRatePanel.add(taxRateField);

        currencyPanel.add(currencyTypePanel);
        currencyPanel.add(taxRatePanel);

        // Save button
        JButton saveButton = new JButton("Save Changes");
        saveButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        saveButton.setBackground(new Color(59, 130, 246));
        saveButton.setForeground(Color.WHITE);
        saveButton.setFocusPainted(false);
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(SettingsView.this,
                        "Settings saved successfully.",
                        "Settings Saved",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });

        panel.add(sectionLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(hotelInfoPanel);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(displayPanel);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(currencyPanel);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(saveButton);

        return panel;
    }

    private JPanel createUserProfilePanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setOpaque(false);

        JLabel sectionLabel = new JLabel("User Profile");
        sectionLabel.setFont(new Font("Arial", Font.BOLD, 16));
        sectionLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Profile Information
        JPanel profilePanel = createSettingsSection("Profile Information");

        JPanel namePanel = createSettingItem("Full Name");
        JTextField nameField = new JTextField("Admin User");
        nameField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        namePanel.add(nameField);

        JPanel usernamePanel = createSettingItem("Username");
        JTextField usernameField = new JTextField("admin");
        usernameField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        usernamePanel.add(usernameField);

        JPanel emailPanel = createSettingItem("Email");
        JTextField emailField = new JTextField("admin@miravelle.com");
        emailField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        emailPanel.add(emailField);

        JPanel phonePanel = createSettingItem("Phone");
        JTextField phoneField = new JTextField("+1 (555) 987-6543");
        phoneField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        phonePanel.add(phoneField);

        profilePanel.add(namePanel);
        profilePanel.add(usernamePanel);
        profilePanel.add(emailPanel);
        profilePanel.add(phonePanel);

        // Password Settings
        JPanel passwordPanel = createSettingsSection("Change Password");

        JPanel currentPasswordPanel = createSettingItem("Current Password");
        JPasswordField currentPasswordField = new JPasswordField();
        currentPasswordField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        currentPasswordPanel.add(currentPasswordField);

        JPanel newPasswordPanel = createSettingItem("New Password");
        JPasswordField newPasswordField = new JPasswordField();
        newPasswordField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        newPasswordPanel.add(newPasswordField);

        JPanel confirmPasswordPanel = createSettingItem("Confirm New Password");
        JPasswordField confirmPasswordField = new JPasswordField();
        confirmPasswordField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        confirmPasswordPanel.add(confirmPasswordField);

        passwordPanel.add(currentPasswordPanel);
        passwordPanel.add(newPasswordPanel);
        passwordPanel.add(confirmPasswordPanel);

        // Save button
        JButton saveButton = new JButton("Save Profile");
        saveButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        saveButton.setBackground(new Color(59, 130, 246));
        saveButton.setForeground(Color.WHITE);
        saveButton.setFocusPainted(false);
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(SettingsView.this,
                        "Profile updated successfully.",
                        "Profile Updated",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });

        panel.add(sectionLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(profilePanel);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(passwordPanel);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(saveButton);

        return panel;
    }

    private JPanel createNotificationsPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setOpaque(false);

        JLabel sectionLabel = new JLabel("Notification Settings");
        sectionLabel.setFont(new Font("Arial", Font.BOLD, 16));
        sectionLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Email Notifications
        JPanel emailPanel = createSettingsSection("Email Notifications");

        JPanel bookingPanel = createSettingItem("New Bookings");
        JCheckBox bookingCheck = new JCheckBox();
        bookingCheck.setSelected(true);
        bookingPanel.add(bookingCheck);

        JPanel checkoutPanel = createSettingItem("Upcoming Checkouts");
        JCheckBox checkoutCheck = new JCheckBox();
        checkoutCheck.setSelected(true);
        checkoutPanel.add(checkoutCheck);

        JPanel maintenancePanel = createSettingItem("Maintenance Alerts");
        JCheckBox maintenanceCheck = new JCheckBox();
        maintenanceCheck.setSelected(true);
        maintenancePanel.add(maintenanceCheck);

        JPanel lostFoundPanel = createSettingItem("Lost & Found Claims");
        JCheckBox lostFoundCheck = new JCheckBox();
        lostFoundCheck.setSelected(true);
        lostFoundPanel.add(lostFoundCheck);

        emailPanel.add(bookingPanel);
        emailPanel.add(checkoutPanel);
        emailPanel.add(maintenancePanel);
        emailPanel.add(lostFoundPanel);

        // System Notifications
        JPanel systemPanel = createSettingsSection("System Notifications");

        JPanel desktopPanel = createSettingItem("Desktop Notifications");
        JCheckBox desktopCheck = new JCheckBox();
        desktopCheck.setSelected(true);
        desktopPanel.add(desktopCheck);

        JPanel soundPanel = createSettingItem("Sound Alerts");
        JCheckBox soundCheck = new JCheckBox();
        soundCheck.setSelected(false);
        soundPanel.add(soundCheck);

        JPanel reportPanel = createSettingItem("Daily Reports");
        JCheckBox reportCheck = new JCheckBox();
        reportCheck.setSelected(true);
        reportPanel.add(reportCheck);

        systemPanel.add(desktopPanel);
        systemPanel.add(soundPanel);
        systemPanel.add(reportPanel);

        // Save button
        JButton saveButton = new JButton("Save Notification Settings");
        saveButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        saveButton.setBackground(new Color(59, 130, 246));
        saveButton.setForeground(Color.WHITE);
        saveButton.setFocusPainted(false);
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(SettingsView.this,
                        "Notification settings saved successfully.",
                        "Settings Saved",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });

        panel.add(sectionLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(emailPanel);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(systemPanel);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(saveButton);

        return panel;
    }

    private JPanel createSystemPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setOpaque(false);

        JLabel sectionLabel = new JLabel("System Settings");
        sectionLabel.setFont(new Font("Arial", Font.BOLD, 16));
        sectionLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Backup Settings
        JPanel backupPanel = createSettingsSection("Backup Settings");

        JPanel autoBackupPanel = createSettingItem("Automatic Backups");
        JCheckBox autoBackupCheck = new JCheckBox();
        autoBackupCheck.setSelected(true);
        autoBackupPanel.add(autoBackupCheck);

        JPanel backupFrequencyPanel = createSettingItem("Backup Frequency");
        JComboBox<String> backupFrequencyCombo = new JComboBox<>(new String[]{"Daily", "Weekly", "Monthly"});
        backupFrequencyCombo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        backupFrequencyPanel.add(backupFrequencyCombo);

        JPanel backupLocationPanel = createSettingItem("Backup Location");
        JTextField backupLocationField = new JTextField("/backups");
        backupLocationField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        backupLocationPanel.add(backupLocationField);

        backupPanel.add(autoBackupPanel);
        backupPanel.add(backupFrequencyPanel);
        backupPanel.add(backupLocationPanel);

        // Database Settings
        JPanel databasePanel = createSettingsSection("Database Settings");

        JPanel hostPanel = createSettingItem("Database Host");
        JTextField hostField = new JTextField("localhost");
        hostField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        hostPanel.add(hostField);

        JPanel portPanel = createSettingItem("Database Port");
        JTextField portField = new JTextField("3306");
        portField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        portPanel.add(portField);

        JPanel namePanel = createSettingItem("Database Name");
        JTextField nameField = new JTextField("miravelle_hotel");
        nameField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        namePanel.add(nameField);

        JPanel userPanel = createSettingItem("Database User");
        JTextField userField = new JTextField("hotel_admin");
        userField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        userPanel.add(userField);

        databasePanel.add(hostPanel);
        databasePanel.add(portPanel);
        databasePanel.add(namePanel);
        databasePanel.add(userPanel);

        // Action buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.setOpaque(false);
        buttonPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        buttonPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));

        JButton saveButton = new JButton("Save Settings");
        saveButton.setBackground(new Color(59, 130, 246));
        saveButton.setForeground(Color.WHITE);
        saveButton.setFocusPainted(false);
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(SettingsView.this,
                        "System settings saved successfully.",
                        "Settings Saved",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });

        JButton backupNowButton = new JButton("Backup Now");
        backupNowButton.setFocusPainted(false);
        backupNowButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(SettingsView.this,
                        "Backup created successfully.",
                        "Backup Complete",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });

        JButton testConnectionButton = new JButton("Test Database Connection");
        testConnectionButton.setFocusPainted(false);
        testConnectionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(SettingsView.this,
                        "Database connection successful.",
                        "Connection Test",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });

        buttonPanel.add(saveButton);
        buttonPanel.add(backupNowButton);
        buttonPanel.add(testConnectionButton);

        panel.add(sectionLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(backupPanel);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(databasePanel);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(buttonPanel);

        return panel;
    }

    private JPanel createAboutPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setOpaque(false);

        JLabel sectionLabel = new JLabel("About MiraVelle Hotel Management System");
        sectionLabel.setFont(new Font("Arial", Font.BOLD, 16));
        sectionLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel versionPanel = new JPanel(new BorderLayout());
        versionPanel.setOpaque(false);
        versionPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
        versionPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(229, 231, 235)),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        JLabel versionLabel = new JLabel("Version 1.0.0");
        versionLabel.setFont(new Font("Arial", Font.BOLD, 14));

        JLabel buildLabel = new JLabel("Build: 2023.04.22.1");
        buildLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        buildLabel.setForeground(new Color(107, 114, 128));

        JButton checkUpdateButton = new JButton("Check for Updates");
        checkUpdateButton.setFocusPainted(false);
        checkUpdateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(SettingsView.this,
                        "Your software is up to date.",
                        "Update Check",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });

        JPanel versionInfoPanel = new JPanel();
        versionInfoPanel.setLayout(new BoxLayout(versionInfoPanel, BoxLayout.Y_AXIS));
        versionInfoPanel.setOpaque(false);
        versionLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        buildLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        versionInfoPanel.add(versionLabel);
        versionInfoPanel.add(buildLabel);

        versionPanel.add(versionInfoPanel, BorderLayout.WEST);
        versionPanel.add(checkUpdateButton, BorderLayout.EAST);

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setOpaque(false);
        infoPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        infoPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        JLabel descLabel = new JLabel("MiraVelle Hotel Management System is a comprehensive solution for hotel operations.");
        descLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel copyrightLabel = new JLabel("© 2023 MiraVelle Hotels. All rights reserved.");
        copyrightLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        copyrightLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        copyrightLabel.setForeground(new Color(107, 114, 128));

        infoPanel.add(descLabel);
        infoPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        infoPanel.add(copyrightLabel);

        JPanel licensePanel = createSettingsSection("License Information");

        JPanel licenseKeyPanel = createSettingItem("License Key");
        JTextField licenseKeyField = new JTextField("XXXX-XXXX-XXXX-XXXX");
        licenseKeyField.setEditable(false);
        licenseKeyField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        licenseKeyPanel.add(licenseKeyField);

        JPanel registeredToPanel = createSettingItem("Registered To");
        JTextField registeredToField = new JTextField("MiraVelle Hotels Inc.");
        registeredToField.setEditable(false);
        registeredToField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        registeredToPanel.add(registeredToField);

        JPanel expiryPanel = createSettingItem("License Expiry");
        JTextField expiryField = new JTextField("2024-04-22");
        expiryField.setEditable(false);
        expiryField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        expiryPanel.add(expiryField);

        licensePanel.add(licenseKeyPanel);
        licensePanel.add(registeredToPanel);
        licensePanel.add(expiryPanel);

        panel.add(sectionLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(versionPanel);
        panel.add(infoPanel);
        panel.add(licensePanel);

        return panel;
    }

    private JPanel createSettingsSection(String title) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(
                        BorderFactory.createLineBorder(new Color(229, 231, 235)),
                        title,
                        javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                        javax.swing.border.TitledBorder.DEFAULT_POSITION,
                        new Font("Arial", Font.BOLD, 14)
                ),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, panel.getMaximumSize().height));

        return panel;
    }

    private JPanel createSettingItem(String label) {
        JPanel panel = new JPanel(new BorderLayout(10, 0));
        panel.setOpaque(false);
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        panel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));

        JLabel itemLabel = new JLabel(label);
        itemLabel.setPreferredSize(new Dimension(200, itemLabel.getPreferredSize().height));

        panel.add(itemLabel, BorderLayout.WEST);

        return panel;
    }
}