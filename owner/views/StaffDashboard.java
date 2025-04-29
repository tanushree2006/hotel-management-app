package owner.views;

import owner.controllers.BookingController;
import owner.controllers.LostFoundController;
import owner.controllers.RoomController;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.lang.reflect.Method;

public class StaffDashboard extends JFrame {
    private JPanel contentPanel;
    private JPanel sidebarPanel;
    private CardLayout cardLayout;
    private String staffName;
    private String staffDepartment;

    // Dashboard components
    private JPanel dashboardPanel;
    private JPanel roomsPanel;
    private JPanel bookingsPanel;
    private JPanel lostFoundPanel;
    private JPanel tasksList;

    public StaffDashboard(String staffName, String staffDepartment) {
        this.staffName = staffName != null ? staffName : "Staff";
        this.staffDepartment = staffDepartment != null ? staffDepartment : "General";
        initializeUI();
    }

    private void initializeUI() {
        setTitle("MiraVelle : A House of Distinction - Staff Portal");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 800);
        setLocationRelativeTo(null);

        // Initialize controllers
        RoomController roomController = new RoomController();
        BookingController bookingController = new BookingController();
        LostFoundController lostFoundController = new LostFoundController();

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
        dashboardPanel = createDashboardPanel();
        roomsPanel = new RoomsView();
        bookingsPanel = new BookingsView();
        lostFoundPanel = new LostFoundView();

        // Add panels to card layout
        contentPanel.add(dashboardPanel, "dashboard");
        contentPanel.add(roomsPanel, "rooms");
        contentPanel.add(bookingsPanel, "bookings");
        contentPanel.add(lostFoundPanel, "lostfound");

        // Show dashboard by default
        cardLayout.show(contentPanel, "dashboard");

        // Add components to main panel
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(sidebarPanel, BorderLayout.WEST);
        mainPanel.add(contentPanel, BorderLayout.CENTER);

        // Set content pane
        setContentPane(mainPanel);

        // Add window listener to handle logout
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                handleLogout();
            }
        });
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

        JButton profileButton = new JButton("👤 " + staffName + " (" + staffDepartment + ")");
        profileButton.setFocusPainted(false);
        profileButton.setBorderPainted(false);
        profileButton.setContentAreaFilled(false);

        JButton logoutButton = new JButton("Logout");
        logoutButton.setFocusPainted(false);
        logoutButton.setBackground(new Color(137, 173, 243));
        logoutButton.setForeground(new  Color(0, 2, 5));
        logoutButton.addActionListener(e -> handleLogout());

        rightPanel.add(notificationsButton);
        rightPanel.add(profileButton);
        rightPanel.add(logoutButton);

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
        JPanel lostFoundItem = createMenuItem("Lost & Found", "lostfound", false);

        // Add some spacing at the top
        sidebarPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Add menu items to sidebar
        sidebarPanel.add(dashboardItem);
        sidebarPanel.add(roomsItem);
        sidebarPanel.add(bookingsItem);
        sidebarPanel.add(lostFoundItem);

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

    private JPanel createDashboardPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Title panel
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setOpaque(false);

        JLabel titleLabel = new JLabel("Staff Dashboard");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));

        JLabel subtitleLabel = new JLabel("Welcome Members ! Here's your daily overview.");
        subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        subtitleLabel.setForeground(new Color(107, 114, 128));

        titlePanel.add(titleLabel, BorderLayout.NORTH);
        titlePanel.add(subtitleLabel, BorderLayout.CENTER);

        // Metrics panel
        JPanel metricsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 15));
        metricsPanel.setOpaque(false);
        metricsPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        metricsPanel.add(createMetricCard("Rooms to Clean", "12", "+2 from yesterday", false));
        metricsPanel.add(createMetricCard("Check-ins Today", "8", "3 completed", true));
        metricsPanel.add(createMetricCard("Check-outs Today", "5", "2 completed", true));

        // Tasks panel
        JPanel tasksPanel = new JPanel(new BorderLayout());
        tasksPanel.setBackground(Color.WHITE);
        tasksPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(229, 231, 235)),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        JLabel tasksTitle = new JLabel("Today's Tasks");
        tasksTitle.setFont(new Font("Arial", Font.BOLD, 18));

        tasksList = new JPanel();
        tasksList.setLayout(new BoxLayout(tasksList, BoxLayout.Y_AXIS));
        tasksList.setOpaque(false);

        // Add initial dummy tasks
        tasksList.add(createTaskItem("Clean room 302", "High", "9:00 AM"));
        tasksList.add(Box.createRigidArea(new Dimension(0, 10)));
        tasksList.add(createTaskItem("Restock amenities in rooms 201-210", "Medium", "10:30 AM"));
        tasksList.add(Box.createRigidArea(new Dimension(0, 10)));
        tasksList.add(createTaskItem("Assist with check-in for VIP guest", "High", "2:00 PM"));
        tasksList.add(Box.createRigidArea(new Dimension(0, 10)));
        tasksList.add(createTaskItem("Prepare meeting room for corporate event", "Medium", "4:00 PM"));

        JScrollPane scrollPane = new JScrollPane(tasksList);
        scrollPane.setBorder(null);
        scrollPane.setPreferredSize(new Dimension(0, 200)); // Optional to control task list height

        // Buttons panel
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonsPanel.setOpaque(false);

        JButton submitButton = new JButton("Submit");
        JButton addTaskButton = new JButton("Add Task");

        submitButton.addActionListener(e -> {
            Component[] components = tasksList.getComponents();
            for (int i = components.length - 1; i >= 0; i--) {
                Component comp = components[i];
                if (comp instanceof JPanel) {
                    JPanel taskItem = (JPanel) comp;
                    for (Component child : taskItem.getComponents()) {
                        if (child instanceof JCheckBox checkBox) {
                            if (checkBox.isSelected()) {
                                tasksList.remove(taskItem);
                                if (i - 1 >= 0 && tasksList.getComponent(i - 1) instanceof Box.Filler) {
                                    tasksList.remove(i - 1); // Remove spacing too
                                }
                                break;
                            }
                        }
                    }
                }
            }
            tasksList.revalidate();
            tasksList.repaint();
        });

        addTaskButton.addActionListener(e -> {
            String taskName = JOptionPane.showInputDialog(null, "Enter Task Name:");
            if (taskName != null && !taskName.trim().isEmpty()) {
                tasksList.add(createTaskItem(taskName, "Medium", "TBD"));
                tasksList.add(Box.createRigidArea(new Dimension(0, 10)));
                tasksList.revalidate();
                tasksList.repaint();
            }
        });

        buttonsPanel.add(addTaskButton);
        buttonsPanel.add(submitButton);

        tasksPanel.add(tasksTitle, BorderLayout.NORTH);
        tasksPanel.add(scrollPane, BorderLayout.CENTER);
        tasksPanel.add(buttonsPanel, BorderLayout.SOUTH);

        // New: Center panel to align metrics and tasks vertically
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setOpaque(false);

        centerPanel.add(metricsPanel);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        centerPanel.add(tasksPanel);

        panel.add(titlePanel, BorderLayout.NORTH);
        panel.add(centerPanel, BorderLayout.CENTER);

        return panel;
    }



    private JPanel createMetricCard(String title, String value, String change, boolean isPositive) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);

        // Set fixed smaller size
        Dimension cardSize = new Dimension(220, 120); // <<< Shrunk width and height
        card.setPreferredSize(cardSize);
        card.setMinimumSize(cardSize);
        card.setMaximumSize(cardSize);

        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(229, 231, 235)),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);

        JLabel iconLabel = new JLabel("📊");

        JLabel changeLabel = new JLabel(change);
        changeLabel.setFont(new Font("Arial", Font.PLAIN, 11)); // slightly smaller font
        changeLabel.setForeground(isPositive ? new Color(34, 197, 94) : new Color(239, 68, 68));

        topPanel.add(iconLabel, BorderLayout.WEST);
        topPanel.add(changeLabel, BorderLayout.EAST);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setOpaque(false);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(8, 0, 0, 0));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.PLAIN, 11));
        titleLabel.setForeground(new Color(107, 114, 128));

        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("Arial", Font.BOLD, 18)); // Slightly smaller

        bottomPanel.add(titleLabel, BorderLayout.NORTH);
        bottomPanel.add(valueLabel, BorderLayout.SOUTH);

        card.add(topPanel, BorderLayout.NORTH);
        card.add(bottomPanel, BorderLayout.SOUTH);

        return card;
    }


    private JPanel createTaskItem(String task, String priority, String time) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(229, 231, 235)),
                BorderFactory.createEmptyBorder(10, 0, 10, 0)
        ));

        JCheckBox checkBox = new JCheckBox(task);
        checkBox.setFont(new Font("Arial", Font.PLAIN, 14));
        checkBox.setOpaque(false);

        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        rightPanel.setOpaque(false);

        JLabel priorityLabel = new JLabel(priority);
        priorityLabel.setFont(new Font("Arial", Font.BOLD, 12));
        priorityLabel.setForeground(priority.equals("High") ? new Color(239, 68, 68)
                : priority.equals("Medium") ? new Color(234, 179, 8)
                : new Color(34, 197, 94));

        JLabel timeLabel = new JLabel(time);
        timeLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        timeLabel.setForeground(new Color(107, 114, 128));

        rightPanel.add(priorityLabel);
        rightPanel.add(timeLabel);

        panel.add(checkBox, BorderLayout.WEST);
        panel.add(rightPanel, BorderLayout.EAST);

        return panel;
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
            StaffDashboard dashboard = new StaffDashboard("Members", "all");
            dashboard.setVisible(true);
        });
    }
}
