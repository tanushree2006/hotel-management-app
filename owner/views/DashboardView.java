package owner.views;

import owner.utils.UIUtils;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import owner.utils.CurrencyUtils;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.lang.reflect.Method;

public class DashboardView extends JPanel {
    private JPanel contentPanel;
    private JPanel sidebarPanel;
    private CardLayout cardLayout;

    // Dashboard components
    private JPanel dashboardPanel;
    private JPanel roomsPanel;
    private JPanel bookingsPanel;
    private JPanel staffPanel;
    private JPanel lostFoundPanel;
    private JPanel reportsPanel;
    private JPanel settingsPanel;

    private OwnerDashboard ownerDashboard;

    public DashboardView(OwnerDashboard ownerDashboard) {
        this.ownerDashboard = ownerDashboard;
        initComponents();
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
            ownerDashboard.dispose(); // This will close the actual window
            // Show login window as before
            SwingUtilities.invokeLater(() -> {
                try {
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


    private void initComponents() {
        // Main panel
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Header panel
        JPanel headerPanel = createHeaderPanel();


        // Sidebar panel
        sidebarPanel = createSidebarPanel();

        // Content panel with card layout
        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);

        // Create content panels
        dashboardPanel = new DashboardOverviewPanel();
        roomsPanel = new RoomsView();
        bookingsPanel = new BookingsView();
        staffPanel = new StaffView();
        lostFoundPanel = new LostFoundView();
        reportsPanel = new ReportsView();
        settingsPanel = new SettingsView();

        // Add panels to card layout
        contentPanel.add(dashboardPanel, "dashboard");
        contentPanel.add(roomsPanel, "rooms");
        contentPanel.add(bookingsPanel, "bookings");
        contentPanel.add(staffPanel, "staff");
        contentPanel.add(lostFoundPanel, "lostfound");
        contentPanel.add(reportsPanel, "reports");
        contentPanel.add(settingsPanel, "settings");

        // Show dashboard by default
        cardLayout.show(contentPanel, "dashboard");

        // Add components to main panel
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(sidebarPanel, BorderLayout.WEST);
        mainPanel.add(contentPanel, BorderLayout.CENTER);

        this.setLayout(new BorderLayout());
        this.add(mainPanel, BorderLayout.CENTER);


        // Set content pane



    }

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Color.WHITE);
        headerPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(229, 231, 235)),
                new EmptyBorder(10, 20, 10, 20)
        ));

        // Logo and search panel
        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        leftPanel.setOpaque(false);

        JLabel logoLabel = new JLabel("MiraVelle");
        logoLabel.setFont(new Font("Arial", Font.BOLD, 18));
        logoLabel.setForeground(new Color(59, 130, 246));

        JTextField searchField = new JTextField(20);
        searchField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(229, 231, 235)),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));

        leftPanel.add(logoLabel);
        leftPanel.add(searchField);

        // User profile panel
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        rightPanel.setOpaque(false);

        JButton notificationsButton = new JButton("🔔");
        notificationsButton.setFocusPainted(false);
        notificationsButton.setBorderPainted(false);
        notificationsButton.setContentAreaFilled(false);

        JButton themeButton = new JButton("🌙");
        themeButton.setFocusPainted(false);
        themeButton.setBorderPainted(false);
        themeButton.setContentAreaFilled(false);

        JButton profileButton = new JButton("👤 Admin");
        profileButton.setFocusPainted(false);
        profileButton.setBorderPainted(false);
        profileButton.setContentAreaFilled(false);

        JButton logoutButton = new JButton("Logout");
        logoutButton.setFocusPainted(false);
        logoutButton.setBackground(new Color(137, 173, 243));
        logoutButton.setForeground(new  Color(0, 2, 5));
        logoutButton.addActionListener(e -> handleLogout());
        rightPanel.add(logoutButton);



        rightPanel.add(notificationsButton);
        rightPanel.add(themeButton);
        rightPanel.add(profileButton);

        headerPanel.add(leftPanel, BorderLayout.WEST);
        headerPanel.add(rightPanel, BorderLayout.EAST);

        return headerPanel;
    }

    private JPanel createSidebarPanel() {
        JPanel sidebarPanel = new JPanel();
        sidebarPanel.setLayout(new BoxLayout(sidebarPanel, BoxLayout.Y_AXIS));
        sidebarPanel.setBackground(new Color(249, 250, 251));
        sidebarPanel.setPreferredSize(new Dimension(220, 0));
        sidebarPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, new Color(229, 231, 235)));

        // Create menu items
        JPanel dashboardItem = createMenuItem("Dashboard", "dashboard", true);
        JPanel roomsItem = createMenuItem("Rooms Management", "rooms", false);
        JPanel bookingsItem = createMenuItem("Bookings", "bookings", false);
        JPanel staffItem = createMenuItem("Staff Management", "staff", false);
        JPanel lostFoundItem = createMenuItem("Lost & Found", "lostfound", false);
        JPanel reportsItem = createMenuItem("Reports & Analytics", "reports", false);
        JPanel settingsItem = createMenuItem("Settings", "settings", false);

        // Add some spacing at the top
        sidebarPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Add menu items to sidebar
        sidebarPanel.add(dashboardItem);
        sidebarPanel.add(roomsItem);
        sidebarPanel.add(bookingsItem);
        sidebarPanel.add(staffItem);
        sidebarPanel.add(lostFoundItem);
        sidebarPanel.add(reportsItem);
        sidebarPanel.add(settingsItem);

        // Add filler to push everything to the top
        sidebarPanel.add(Box.createVerticalGlue());

        return sidebarPanel;
    }

    private JPanel createMenuItem(String text, String cardName, boolean isSelected) {
        JPanel menuItem = new JPanel(new BorderLayout());
        menuItem.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        menuItem.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        menuItem.setOpaque(true);

        if (isSelected) {
            menuItem.setBackground(new Color(243, 244, 246));
        } else {
            menuItem.setBackground(new Color(249, 250, 251));
        }

        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", isSelected ? Font.BOLD : Font.PLAIN, 14));
        label.setForeground(isSelected ? new Color(59, 130, 246) : new Color(107, 114, 128));

        menuItem.add(label, BorderLayout.CENTER);

        // Add hover effect and click action
        menuItem.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                if (!isSelected) {
                    menuItem.setBackground(new Color(243, 244, 246));
                }
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                if (!isSelected) {
                    menuItem.setBackground(new Color(249, 250, 251));
                }
            }

            public void mouseClicked(java.awt.event.MouseEvent evt) {
                // Update all menu items
                for (Component comp : sidebarPanel.getComponents()) {
                    if (comp instanceof JPanel) {
                        comp.setBackground(new Color(249, 250, 251));
                        for (Component child : ((JPanel) comp).getComponents()) {
                            if (child instanceof JLabel) {
                                ((JLabel) child).setFont(new Font("Arial", Font.PLAIN, 14));
                                ((JLabel) child).setForeground(new Color(107, 114, 128));
                            }
                        }
                    }
                }

                // Update selected item
                menuItem.setBackground(new Color(243, 244, 246));
                label.setFont(new Font("Arial", Font.BOLD, 14));
                label.setForeground(new Color(59, 130, 246));

                // Show the selected card
                cardLayout.show(contentPanel, cardName);
            }
        });

        return menuItem;
    }

    // Inner class for Dashboard Overview Panel
    private static class DashboardOverviewPanel extends JPanel {
        public DashboardOverviewPanel() {
            setLayout(new BorderLayout());
            setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
            setBackground(Color.WHITE);

            // Title panel
            JPanel titlePanel = new JPanel(new BorderLayout());
            titlePanel.setOpaque(false);

            JLabel titleLabel = new JLabel("Dashboard");
            titleLabel.setFont(new Font("Arial", Font.BOLD, 24));

            JLabel subtitleLabel = new JLabel("Welcome back, Admin! Here's what's happening with your hotel today.");
            subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 14));
            subtitleLabel.setForeground(new Color(107, 114, 128));

            titlePanel.add(titleLabel, BorderLayout.NORTH);
            titlePanel.add(subtitleLabel, BorderLayout.CENTER);

            // Metrics panel
            JPanel metricsPanel = new JPanel(new GridLayout(1, 4, 15, 0));
            metricsPanel.setOpaque(false);
            metricsPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

            metricsPanel.add(createMetricCard("Total Revenue", "12,628", "+12.5%", true));
            metricsPanel.add(createMetricCard("Occupancy Rate", "78%", "+5.2%", true));
            metricsPanel.add(createMetricCard("New Bookings", "24", "-3.1%", false));
            metricsPanel.add(createMetricCard("Average Rating", "4.8", "+0.3", true));

            // Main content panel
            JPanel contentPanel = new JPanel(new GridLayout(2, 1, 0, 20));
            contentPanel.setOpaque(false);

            // Top row - Occupancy and Bookings
            JPanel topRowPanel = new JPanel(new GridLayout(1, 2, 20, 0));
            topRowPanel.setOpaque(false);

            // Occupancy overview card
            JPanel occupancyCard = createCard("Occupancy Overview", "View All");
            JPanel occupancyContent = new JPanel(new BorderLayout());
            occupancyContent.setOpaque(false);

            // Room type stats
            JPanel roomStatsPanel = new JPanel(new GridLayout(4, 1, 0, 10));
            roomStatsPanel.setOpaque(false);
            roomStatsPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

            roomStatsPanel.add(createProgressBar("Standard Rooms", 85));
            roomStatsPanel.add(createProgressBar("Deluxe Rooms", 72));
            roomStatsPanel.add(createProgressBar("Suites", 64));
            roomStatsPanel.add(createProgressBar("Presidential Suites", 50));

            occupancyContent.add(roomStatsPanel, BorderLayout.CENTER);
            occupancyCard.add(occupancyContent, BorderLayout.CENTER);

            // Recent bookings card
            JPanel bookingsCard = createCard("Recent Bookings", "View All");
            JPanel bookingsContent = new JPanel();
            bookingsContent.setLayout(new BoxLayout(bookingsContent, BoxLayout.Y_AXIS));
            bookingsContent.setOpaque(false);
            bookingsContent.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

            bookingsContent.add(createBookingItem("Tanvi Joshi", "102 - Deluxe Room", "Apr 18 - Apr 23", "Active"));
            bookingsContent.add(Box.createRigidArea(new Dimension(0, 10)));
            bookingsContent.add(createBookingItem("Gaurav Bhatnagar", "301 - Deluxe Room", "Apr 20 - Apr 25", "Active"));
            bookingsContent.add(Box.createRigidArea(new Dimension(0, 10)));
            bookingsContent.add(createBookingItem("Neha Mukherjee", "201 - Suite", "Apr 15 - Apr 19", "Completed"));

            bookingsCard.add(bookingsContent, BorderLayout.CENTER);

            topRowPanel.add(occupancyCard);
            topRowPanel.add(bookingsCard);

            // Bottom row - Staff, Lost & Found, Rooms
            JPanel bottomRowPanel = new JPanel(new GridLayout(1, 3, 20, 0));
            bottomRowPanel.setOpaque(false);

            // Staff card
            JPanel staffCard = createCard("Staff On Duty", "View All");
            JPanel staffContent = new JPanel();
            staffContent.setLayout(new BoxLayout(staffContent, BoxLayout.Y_AXIS));
            staffContent.setOpaque(false);
            staffContent.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

            staffContent.add(createStaffItem("Yash Thakur", "Receptionist", "On Duty"));
            staffContent.add(Box.createRigidArea(new Dimension(0, 10)));
            staffContent.add(createStaffItem("Aditi Rao", "Housekeeper", "On Duty"));
            staffContent.add(Box.createRigidArea(new Dimension(0, 10)));
            staffContent.add(createStaffItem("Manish Shah", "Maintenance", "Off Duty"));

            staffCard.add(staffContent, BorderLayout.CENTER);

            // Lost & Found card
            JPanel lostFoundCard = createCard("Lost & Found Items", "View All");
            JPanel lostFoundContent = new JPanel();
            lostFoundContent.setLayout(new BoxLayout(lostFoundContent, BoxLayout.Y_AXIS));
            lostFoundContent.setOpaque(false);
            lostFoundContent.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

            lostFoundContent.add(createLostFoundItem("Black leather wallet with initials JD", "102", "Claimed (Pending)"));
            lostFoundContent.add(Box.createRigidArea(new Dimension(0, 10)));
            lostFoundContent.add(createLostFoundItem("Silver watch with brown leather strap", "205", "Unclaimed"));
            lostFoundContent.add(Box.createRigidArea(new Dimension(0, 10)));
            lostFoundContent.add(createLostFoundItem("Blue iPhone charger", "301", "Unclaimed"));

            lostFoundCard.add(lostFoundContent, BorderLayout.CENTER);

            // Rooms card
            JPanel roomsCard = createCard("Rooms Needing Attention", "View All");
            JPanel roomsContent = new JPanel();
            roomsContent.setLayout(new BoxLayout(roomsContent, BoxLayout.Y_AXIS));
            roomsContent.setOpaque(false);
            roomsContent.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

            roomsContent.add(createRoomItem("103", "Suite", "Maintenance", "AC not working properly"));
            roomsContent.add(Box.createRigidArea(new Dimension(0, 10)));
            roomsContent.add(createRoomItem("215", "Deluxe", "Cleaning", "Needs deep cleaning"));
            roomsContent.add(Box.createRigidArea(new Dimension(0, 10)));
            roomsContent.add(createRoomItem("308", "Standard", "Inspection", "Pre-arrival inspection needed"));

            roomsCard.add(roomsContent, BorderLayout.CENTER);

            bottomRowPanel.add(staffCard);
            bottomRowPanel.add(lostFoundCard);
            bottomRowPanel.add(roomsCard);

            contentPanel.add(topRowPanel);
            contentPanel.add(bottomRowPanel);

            // Add components to main panel
            add(titlePanel, BorderLayout.NORTH);
            add(metricsPanel, BorderLayout.CENTER);
            JScrollPane scrollPane = new JScrollPane(contentPanel);
            scrollPane.setBorder(null); // Optional: removes default border
            scrollPane.getVerticalScrollBar().setUnitIncrement(16); // Smooth scrolling
            add(scrollPane, BorderLayout.SOUTH);

        }

        private JPanel createMetricCard(String title, String value, String change, boolean isPositive) {
            JPanel card = new JPanel(new BorderLayout());
            card.setBackground(Color.WHITE);
            card.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(229, 231, 235)),
                    BorderFactory.createEmptyBorder(15, 15, 15, 15)
            ));

            JPanel topPanel = new JPanel(new BorderLayout());
            topPanel.setOpaque(false);

            JLabel iconLabel = new JLabel("📊");

            JLabel changeLabel = new JLabel(change);
            changeLabel.setFont(new Font("Arial", Font.PLAIN, 12));
            changeLabel.setForeground(isPositive ? new Color(34, 197, 94) : new Color(239, 68, 68));

            topPanel.add(iconLabel, BorderLayout.WEST);
            topPanel.add(changeLabel, BorderLayout.EAST);

            JPanel bottomPanel = new JPanel(new BorderLayout());
            bottomPanel.setOpaque(false);
            bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

            JLabel titleLabel = new JLabel(title);
            titleLabel.setFont(new Font("Arial", Font.PLAIN, 12));
            titleLabel.setForeground(new Color(107, 114, 128));

            JLabel valueLabel = new JLabel(value);
            valueLabel.setFont(new Font("Arial", Font.BOLD, 20));

            bottomPanel.add(titleLabel, BorderLayout.NORTH);
            bottomPanel.add(valueLabel, BorderLayout.SOUTH);

            card.add(topPanel, BorderLayout.NORTH);
            card.add(bottomPanel, BorderLayout.SOUTH);

            return card;
        }

        private JPanel createCard(String title, String actionText) {
            JPanel card = new JPanel(new BorderLayout());
            card.setBackground(Color.WHITE);
            card.setBorder(BorderFactory.createLineBorder(new Color(229, 231, 235)));

            JPanel headerPanel = new JPanel(new BorderLayout());
            headerPanel.setBackground(Color.WHITE);
            headerPanel.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(229, 231, 235)),
                    BorderFactory.createEmptyBorder(10, 15, 10, 15)
            ));

            JLabel titleLabel = new JLabel(title);
            titleLabel.setFont(new Font("Arial", Font.BOLD, 14));

            JButton actionButton = new JButton(actionText + " →");
            actionButton.setBorderPainted(false);
            actionButton.setContentAreaFilled(false);
            actionButton.setFocusPainted(false);
            actionButton.setFont(new Font("Arial", Font.PLAIN, 12));
            actionButton.setForeground(new Color(59, 130, 246));

            headerPanel.add(titleLabel, BorderLayout.WEST);
            headerPanel.add(actionButton, BorderLayout.EAST);

            card.add(headerPanel, BorderLayout.NORTH);

            return card;
        }

        private JPanel createProgressBar(String label, int percentage) {
            JPanel panel = new JPanel(new BorderLayout(0, 5));
            panel.setOpaque(false);

            JPanel labelPanel = new JPanel(new BorderLayout());
            labelPanel.setOpaque(false);

            JLabel nameLabel = new JLabel(label);
            nameLabel.setFont(new Font("Arial", Font.PLAIN, 12));

            JLabel percentLabel = new JLabel(percentage + "%");
            percentLabel.setFont(new Font("Arial", Font.PLAIN, 12));

            labelPanel.add(nameLabel, BorderLayout.WEST);
            labelPanel.add(percentLabel, BorderLayout.EAST);

            JProgressBar progressBar = new JProgressBar(0, 100);
            progressBar.setValue(percentage);
            progressBar.setStringPainted(false);
            progressBar.setForeground(new Color(59, 130, 246));
            progressBar.setBackground(new Color(229, 231, 235));

            panel.add(labelPanel, BorderLayout.NORTH);
            panel.add(progressBar, BorderLayout.SOUTH);

            return panel;
        }

        private JPanel createBookingItem(String name, String room, String dates, String status) {
            JPanel panel = new JPanel(new BorderLayout());
            panel.setOpaque(false);
            panel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));

            JPanel leftPanel = new JPanel(new BorderLayout(5, 0));
            leftPanel.setOpaque(false);

            JLabel avatarLabel = new JLabel("👤");

            JPanel infoPanel = new JPanel();
            infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
            infoPanel.setOpaque(false);

            JLabel nameLabel = new JLabel(name);
            nameLabel.setFont(new Font("Arial", Font.BOLD, 12));
            nameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

            JLabel roomLabel = new JLabel(room);
            roomLabel.setFont(new Font("Arial", Font.PLAIN, 11));
            roomLabel.setForeground(new Color(107, 114, 128));
            roomLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

            JLabel datesLabel = new JLabel(dates);
            datesLabel.setFont(new Font("Arial", Font.PLAIN, 11));
            datesLabel.setForeground(new Color(107, 114, 128));
            datesLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

            infoPanel.add(nameLabel);
            infoPanel.add(roomLabel);
            infoPanel.add(datesLabel);

            leftPanel.add(avatarLabel, BorderLayout.WEST);
            leftPanel.add(infoPanel, BorderLayout.CENTER);

            JLabel statusLabel = new JLabel(status);
            statusLabel.setFont(new Font("Arial", Font.PLAIN, 11));

            if (status.equals("Active")) {
                statusLabel.setForeground(new Color(59, 130, 246));
                statusLabel.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(59, 130, 246)),
                        BorderFactory.createEmptyBorder(2, 5, 2, 5)
                ));
            } else if (status.equals("Completed")) {
                statusLabel.setForeground(new Color(34, 197, 94));
                statusLabel.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(34, 197, 94)),
                        BorderFactory.createEmptyBorder(2, 5, 2, 5)
                ));
            } else {
                statusLabel.setForeground(new Color(107, 114, 128));
                statusLabel.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(107, 114, 128)),
                        BorderFactory.createEmptyBorder(2, 5, 2, 5)
                ));
            }

            panel.add(leftPanel, BorderLayout.WEST);
            panel.add(statusLabel, BorderLayout.EAST);

            return panel;
        }

        private JPanel createStaffItem(String name, String position, String status) {
            JPanel panel = new JPanel(new BorderLayout());
            panel.setOpaque(false);
            panel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));

            JPanel leftPanel = new JPanel(new BorderLayout(5, 0));
            leftPanel.setOpaque(false);

            JLabel avatarLabel = new JLabel("👤");

            JPanel infoPanel = new JPanel();
            infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
            infoPanel.setOpaque(false);

            JLabel nameLabel = new JLabel(name);
            nameLabel.setFont(new Font("Arial", Font.BOLD, 12));
            nameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

            JLabel positionLabel = new JLabel(position);
            positionLabel.setFont(new Font("Arial", Font.PLAIN, 11));
            positionLabel.setForeground(new Color(107, 114, 128));
            positionLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

            infoPanel.add(nameLabel);
            infoPanel.add(positionLabel);

            leftPanel.add(avatarLabel, BorderLayout.WEST);
            leftPanel.add(infoPanel, BorderLayout.CENTER);

            JLabel statusLabel = new JLabel(status);
            statusLabel.setFont(new Font("Arial", Font.PLAIN, 11));

            if (status.equals("On Duty")) {
                statusLabel.setForeground(new Color(59, 130, 246));
                statusLabel.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(59, 130, 246)),
                        BorderFactory.createEmptyBorder(2, 5, 2, 5)
                ));
            } else {
                statusLabel.setForeground(new Color(107, 114, 128));
                statusLabel.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(107, 114, 128)),
                        BorderFactory.createEmptyBorder(2, 5, 2, 5)
                ));
            }

            panel.add(leftPanel, BorderLayout.WEST);
            panel.add(statusLabel, BorderLayout.EAST);

            return panel;
        }

        private JPanel createLostFoundItem(String description, String room, String status) {
            JPanel panel = new JPanel(new BorderLayout(0, 5));
            panel.setOpaque(false);
            panel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));

            JPanel topPanel = new JPanel(new BorderLayout());
            topPanel.setOpaque(false);

            JLabel descLabel = new JLabel(description);
            descLabel.setFont(new Font("Arial", Font.BOLD, 12));

            JLabel statusLabel = new JLabel(status);
            statusLabel.setFont(new Font("Arial", Font.PLAIN, 11));

            if (status.equals("Claimed (Pending)")) {
                statusLabel.setForeground(new Color(234, 179, 8));
                statusLabel.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(234, 179, 8)),
                        BorderFactory.createEmptyBorder(2, 5, 2, 5)
                ));
            } else if (status.equals("Unclaimed")) {
                statusLabel.setForeground(new Color(107, 114, 128));
                statusLabel.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(107, 114, 128)),
                        BorderFactory.createEmptyBorder(2, 5, 2, 5)
                ));
            } else {
                statusLabel.setForeground(new Color(34, 197, 94));
                statusLabel.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(34, 197, 94)),
                        BorderFactory.createEmptyBorder(2, 5, 2, 5)
                ));
            }

            topPanel.add(descLabel, BorderLayout.WEST);
            topPanel.add(statusLabel, BorderLayout.EAST);

            JLabel roomLabel = new JLabel("Room: " + room);
            roomLabel.setFont(new Font("Arial", Font.PLAIN, 11));
            roomLabel.setForeground(new Color(107, 114, 128));

            panel.add(topPanel, BorderLayout.NORTH);
            panel.add(roomLabel, BorderLayout.SOUTH);

            return panel;
        }

        private JPanel createRoomItem(String room, String type, String issue, String description) {
            JPanel panel = new JPanel(new BorderLayout(0, 5));
            panel.setOpaque(false);
            panel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));

            JPanel topPanel = new JPanel(new BorderLayout());
            topPanel.setOpaque(false);

            JLabel roomLabel = new JLabel("Room " + room + " - " + type);
            roomLabel.setFont(new Font("Arial", Font.BOLD, 12));

            JLabel issueLabel = new JLabel(issue);
            issueLabel.setFont(new Font("Arial", Font.PLAIN, 11));

            if (issue.equals("Maintenance")) {
                issueLabel.setForeground(new Color(239, 68, 68));
                issueLabel.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(239, 68, 68)),
                        BorderFactory.createEmptyBorder(2, 5, 2, 5)
                ));
            } else if (issue.equals("Cleaning")) {
                issueLabel.setForeground(new Color(234, 179, 8));
                issueLabel.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(234, 179, 8)),
                        BorderFactory.createEmptyBorder(2, 5, 2, 5)
                ));
            } else {
                issueLabel.setForeground(new Color(107, 114, 128));
                issueLabel.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(107, 114, 128)),
                        BorderFactory.createEmptyBorder(2, 5, 2, 5)
                ));
            }

            topPanel.add(roomLabel, BorderLayout.WEST);
            topPanel.add(issueLabel, BorderLayout.EAST);

            JLabel descLabel = new JLabel(description);
            descLabel.setFont(new Font("Arial", Font.PLAIN, 11));
            descLabel.setForeground(new Color(107, 114, 128));

            panel.add(topPanel, BorderLayout.NORTH);


            panel.add(topPanel, BorderLayout.NORTH);
            panel.add(descLabel, BorderLayout.SOUTH);

            return panel;
        }

    }
}
