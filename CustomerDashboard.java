import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.URL;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.DefaultTableCellRenderer;
import java.util.ArrayList;
import java.util.List;

public class CustomerDashboard extends JFrame {
    private JTabbedPane tabbedPane;
    private String currentUsername;

    public CustomerDashboard(String username) {
        this.currentUsername = username != null ? username : "Guest";
        initializeUI();
    }

    private void initializeUI() {
        setTitle("MiraVelle - Customer Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);

        GradientPanel mainPanel = new GradientPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
        setContentPane(mainPanel);

        mainPanel.add(createHeaderPanel(), BorderLayout.NORTH);
        setupTabbedPane(mainPanel);
    }

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);
        headerPanel.setBorder(new EmptyBorder(0, 0, 15, 0));

        JPanel logoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        logoPanel.setOpaque(false);
        logoPanel.add(new JLabel(createHotelIcon()));

        JLabel titleLabel = new JLabel("MiraVelle");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(HotelManagementSystem.PRIMARY_COLOR);
        logoPanel.add(titleLabel);

        JPanel userPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        userPanel.setOpaque(false);

        JLabel userLabel = new JLabel("Welcome, " + currentUsername);
        userLabel.setFont(HotelManagementSystem.NORMAL_FONT);

        JButton profileButton = new StyledButton("My Profile");
        JButton logoutButton = new StyledButton("Logout");
        logoutButton.addActionListener(e -> logout());

        userPanel.add(userLabel);
        userPanel.add(profileButton);
        userPanel.add(logoutButton);

        headerPanel.add(logoPanel, BorderLayout.WEST);
        headerPanel.add(userPanel, BorderLayout.EAST);

        return headerPanel;
    }

    private void setupTabbedPane(JPanel mainPanel) {
        tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Dashboard", createDashboardPanel());
        tabbedPane.addTab("Room Booking", createBookingPanel());
        tabbedPane.addTab("My Reservations", createReservationsPanel());
        tabbedPane.addTab("Services", createServicesPanel());
        tabbedPane.addTab("Lost and Found", createLostAndFoundPanel());

        mainPanel.add(tabbedPane, BorderLayout.CENTER);
    }

    private JPanel createDashboardPanel() {
        JPanel panel = new JPanel(new BorderLayout(15, 15));
        panel.setOpaque(false);
        panel.setBorder(new EmptyBorder(15, 15, 15, 15));

        RoundedPanel welcomePanel = createWelcomePanel();
        RoundedPanel actionsPanel = createQuickActionsPanel();
        RoundedPanel featuredPanel = createFeaturedRoomsPanel();

        JPanel mainContent = new JPanel();
        mainContent.setLayout(new BoxLayout(mainContent, BoxLayout.Y_AXIS));
        mainContent.setOpaque(false);
        mainContent.add(welcomePanel);
        mainContent.add(Box.createVerticalStrut(15));
        mainContent.add(actionsPanel);
        mainContent.add(Box.createVerticalStrut(15));
        mainContent.add(featuredPanel);

        panel.add(mainContent, BorderLayout.CENTER);
        return panel;
    }

    private RoundedPanel createWelcomePanel() {
        RoundedPanel panel = new RoundedPanel(15);
        panel.setBackground(Color.WHITE);
        panel.setLayout(new BorderLayout());
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel welcomeLabel = new JLabel("Welcome to MiraVelle, " + currentUsername + "!");
        welcomeLabel.setFont(HotelManagementSystem.HEADING_FONT);
        welcomeLabel.setForeground(HotelManagementSystem.PRIMARY_COLOR);

        JLabel subtitleLabel = new JLabel("Discover comfort and luxury at its finest.");
        subtitleLabel.setFont(HotelManagementSystem.NORMAL_FONT);

        JPanel textPanel = new JPanel(new GridLayout(2, 1));
        textPanel.setOpaque(false);
        textPanel.add(welcomeLabel);
        textPanel.add(subtitleLabel);

        JButton bookNowButton = new StyledButton("Book Now");
        bookNowButton.addActionListener(e -> tabbedPane.setSelectedIndex(1));

        panel.add(textPanel, BorderLayout.CENTER);
        panel.add(bookNowButton, BorderLayout.EAST);

        return panel;
    }

    private RoundedPanel createQuickActionsPanel() {
        RoundedPanel panel = new RoundedPanel(15);
        panel.setBackground(Color.WHITE);
        panel.setLayout(new GridLayout(1, 3, 15, 0));
        panel.setBorder(new EmptyBorder(15, 15, 15, 15));

        panel.add(createQuickAction("View Reservations", "Check your upcoming stays", 2));
        panel.add(createQuickAction("Room Service", "Order food and services", 3));
        panel.add(createQuickAction("Lost & Found", "Report or claim lost items", 4));

        return panel;
    }

    private JPanel createQuickAction(String title, String description, int tabIndex) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(HotelManagementSystem.SUBHEADING_FONT);
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel descLabel = new JLabel(description);
        descLabel.setFont(HotelManagementSystem.SMALL_FONT);
        descLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JButton actionButton = new StyledButton("Go");
        actionButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        actionButton.addActionListener(e -> tabbedPane.setSelectedIndex(tabIndex));

        panel.add(titleLabel);
        panel.add(Box.createVerticalStrut(5));
        panel.add(descLabel);
        panel.add(Box.createVerticalStrut(10));
        panel.add(actionButton);

        return panel;
    }

    private RoundedPanel createFeaturedRoomsPanel() {
        RoundedPanel panel = new RoundedPanel(15);
        panel.setBackground(Color.WHITE);
        panel.setLayout(new BorderLayout());
        panel.setBorder(new EmptyBorder(15, 15, 15, 15));

        JLabel titleLabel = new JLabel("Featured Rooms");
        titleLabel.setFont(HotelManagementSystem.SUBHEADING_FONT);

        JPanel roomsPanel = new JPanel(new GridLayout(1, 3, 15, 0));
        roomsPanel.setOpaque(false);
        roomsPanel.add(createRoomCard("Deluxe Suite", "$299/night", "Spacious suite with ocean view", "images/Deluxe.jpg"));
        roomsPanel.add(createRoomCard("Executive Room", "$199/night", "Perfect for business travelers", "images/Executive.jpg"));
        roomsPanel.add(createRoomCard("Family Suite", "$349/night", "Ideal for families with children", "images/FamilyS.jpg"));

        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(roomsPanel, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createRoomCard(String type, String price, String desc, String imagePath) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));

        try {
            URL imgUrl = getClass().getClassLoader().getResource(imagePath);
            if (imgUrl != null) {
                ImageIcon icon = new ImageIcon(imgUrl);
                Image scaledImage = icon.getImage().getScaledInstance(250, 150, Image.SCALE_SMOOTH);
                panel.add(new JLabel(new ImageIcon(scaledImage)));
            } else {
                panel.add(new JLabel("[Image not found]"));
            }
        } catch (Exception e) {
            panel.add(new JLabel("[Image error]"));
        }

        panel.add(Box.createVerticalStrut(10));
        panel.add(new JLabel(type) {{
            setFont(HotelManagementSystem.NORMAL_FONT);
            setAlignmentX(Component.LEFT_ALIGNMENT);
        }});
        panel.add(new JLabel(price) {{
            setFont(HotelManagementSystem.NORMAL_FONT);
            setForeground(HotelManagementSystem.PRIMARY_COLOR);
            setAlignmentX(Component.LEFT_ALIGNMENT);
        }});
        panel.add(Box.createVerticalStrut(5));
        panel.add(new JLabel(desc) {{
            setFont(HotelManagementSystem.SMALL_FONT);
            setAlignmentX(Component.LEFT_ALIGNMENT);
        }});
        panel.add(Box.createVerticalStrut(10));

        JButton bookButton = new StyledButton("Book");
        bookButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        bookButton.addActionListener(e -> tabbedPane.setSelectedIndex(1));
        panel.add(bookButton);

        return panel;
    }

    private JPanel createBookingPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);
        panel.setBorder(new EmptyBorder(15, 15, 15, 15));

        RoundedPanel formPanel = new RoundedPanel(15);
        formPanel.setBackground(Color.WHITE);
        formPanel.setLayout(new BorderLayout());
        formPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel("Book Your Stay");
        titleLabel.setFont(HotelManagementSystem.HEADING_FONT);
        titleLabel.setForeground(HotelManagementSystem.PRIMARY_COLOR);

        JPanel fieldsPanel = new JPanel(new GridLayout(0, 2, 15, 15));
        fieldsPanel.setOpaque(false);
        fieldsPanel.add(createInputField("Check-in Date", new JDateChooser()));
        fieldsPanel.add(createInputField("Check-out Date", new JDateChooser()));
        fieldsPanel.add(createInputField("Adults", new JComboBox<>(new Integer[]{1, 2, 3, 4})));
        fieldsPanel.add(createInputField("Children", new JComboBox<>(new Integer[]{0, 1, 2, 3, 4})));
        fieldsPanel.add(createInputField("Room Type", new JComboBox<>(new String[]{
                "Standard Room", "Deluxe Room", "Executive Suite", "Family Suite", "Presidential Suite"
        })));
        fieldsPanel.add(new JPanel());

        JTextArea requestsArea = new JTextArea(3, 20);
        requestsArea.setLineWrap(true);
        JPanel requestsPanel = createInputField("Special Requests", new JScrollPane(requestsArea));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setOpaque(false);
        buttonPanel.add(new StyledButton("Search Available Rooms"));

        JPanel formContent = new JPanel(new BorderLayout(0, 15));
        formContent.setOpaque(false);
        formContent.add(titleLabel, BorderLayout.NORTH);
        formContent.add(fieldsPanel, BorderLayout.CENTER);
        formContent.add(requestsPanel, BorderLayout.SOUTH);

        formPanel.add(formContent, BorderLayout.CENTER);
        formPanel.add(buttonPanel, BorderLayout.SOUTH);
        panel.add(formPanel, BorderLayout.NORTH);

        return panel;
    }

    private JPanel createInputField(String label, JComponent field) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);
        panel.add(new JLabel(label) {{
            setFont(HotelManagementSystem.NORMAL_FONT);
        }}, BorderLayout.NORTH);
        panel.add(field, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createReservationsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);
        panel.setBorder(new EmptyBorder(15, 15, 15, 15));

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);
        headerPanel.add(new JLabel("My Reservations") {{
            setFont(HotelManagementSystem.HEADING_FONT);
            setForeground(HotelManagementSystem.PRIMARY_COLOR);
        }}, BorderLayout.WEST);
        headerPanel.add(new StyledButton("Refresh"), BorderLayout.EAST);

        String[] columns = {"Reservation ID", "Room Type", "Check-in", "Check-out", "Status", "Actions"};
        Object[][] data = {
                {"RES-001", "Deluxe Suite", "2023-06-15", "2023-06-20", "Confirmed", "View"},
                {"RES-002", "Executive Room", "2023-07-10", "2023-07-15", "Pending", "View"},
                {"RES-003", "Family Suite", "2023-08-05", "2023-08-12", "Cancelled", "View"}
        };

        JTable table = new JTable(data, columns);
        table.setRowHeight(40);
        table.getTableHeader().setReorderingAllowed(false);
        table.getTableHeader().setBackground(HotelManagementSystem.LIGHT_COLOR);

        panel.add(headerPanel, BorderLayout.NORTH);
        panel.add(new JScrollPane(table), BorderLayout.CENTER);

        return panel;
    }

    private JPanel createServicesPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);
        panel.setBorder(new EmptyBorder(15, 15, 15, 15));

        panel.add(new JLabel("Hotel Services") {{
            setFont(HotelManagementSystem.HEADING_FONT);
            setForeground(HotelManagementSystem.PRIMARY_COLOR);
        }}, BorderLayout.NORTH);

        JPanel servicesGrid = new JPanel(new GridLayout(2, 2, 15, 15));
        servicesGrid.setOpaque(false);
        servicesGrid.add(createServiceCard("Room Service", "Order food and beverages", "images/RoomS.jpg"));
        servicesGrid.add(createServiceCard("Housekeeping", "Request cleaning services", "images/HouseK.jpg"));
        servicesGrid.add(createServiceCard("Concierge", "Get local assistance", "images/Concierge.jpg"));
        servicesGrid.add(createServiceCard("Spa & Wellness", "Book spa treatments", "images/Spa.jpg"));

        panel.add(servicesGrid, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createServiceCard(String title, String desc, String imagePath) {
        RoundedPanel panel = new RoundedPanel(15);
        panel.setBackground(Color.WHITE);
        panel.setLayout(new BorderLayout(0, 10));
        panel.setBorder(new EmptyBorder(15, 15, 15, 15));

        // Try to load image from resources
        ImageIcon icon = null;
        try {
            URL imgUrl = getClass().getResource("/" + imagePath);
            if (imgUrl != null) {
                icon = new ImageIcon(imgUrl);
            } else {
                System.err.println("Image not found: " + imagePath);
            }
        } catch (Exception e) {
            System.err.println("Error loading image: " + e.getMessage());
        }

        JLabel imageLabel = new JLabel();
        if (icon != null) {
            Image scaledImage = icon.getImage().getScaledInstance(250, 150, Image.SCALE_SMOOTH);
            imageLabel.setIcon(new ImageIcon(scaledImage));
        } else {
            // Create a placeholder if image fails to load
            imageLabel.setText("[Image]");
            imageLabel.setHorizontalAlignment(JLabel.CENTER);
            imageLabel.setForeground(Color.GRAY);
        }
        imageLabel.setHorizontalAlignment(JLabel.CENTER);
        panel.add(imageLabel, BorderLayout.CENTER);

        // Rest of the card content...
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(HotelManagementSystem.SUBHEADING_FONT);
        titleLabel.setForeground(HotelManagementSystem.PRIMARY_COLOR);
        titleLabel.setHorizontalAlignment(JLabel.CENTER);

        JLabel descLabel = new JLabel("<html><div style='width:200px;text-align:center'>" + desc + "</div></html>");
        descLabel.setFont(HotelManagementSystem.NORMAL_FONT);
        descLabel.setHorizontalAlignment(JLabel.CENTER);

        JButton orderButton = new StyledButton("Order Now");

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setOpaque(false);
        contentPanel.add(titleLabel);
        contentPanel.add(Box.createVerticalStrut(5));
        contentPanel.add(descLabel);

        panel.add(contentPanel, BorderLayout.CENTER);
        panel.add(orderButton, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createLostAndFoundPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);
        panel.setBorder(new EmptyBorder(15, 15, 15, 15));

        // Header panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);

        JLabel titleLabel = new JLabel("Lost and Found");
        titleLabel.setFont(HotelManagementSystem.HEADING_FONT);
        titleLabel.setForeground(HotelManagementSystem.PRIMARY_COLOR);

        JButton reportButton = new StyledButton("Report Lost Item");
        reportButton.addActionListener(e -> showReportLostItemDialog());

        headerPanel.add(titleLabel, BorderLayout.WEST);
        headerPanel.add(reportButton, BorderLayout.EAST);

        // Table data
        String[] columns = {"Image", "Item ID", "Description", "Found Location", "Status", "Action"};
        Object[][] data = {
                {"images/wallet.jpg", "LF-001", "Black leather wallet", "Room 201", "Unclaimed", "Claim"},
                {"images/watch.jpg", "LF-002", "Gold wrist watch", "Lobby", "Unclaimed", "Claim"},
                {"images/charger.jpg", "LF-003", "Phone charger", "Restaurant", "Claimed", "View"}
        };

        // Create the table
        JTable table = new JTable(data, columns) {
            @Override
            public Class<?> getColumnClass(int column) {
                return column == 0 ? ImageIcon.class : Object.class;
            }
        };
        table.setRowHeight(60);

        // Set custom renderer for image column
        table.getColumnModel().getColumn(0).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel label = (JLabel) super.getTableCellRendererComponent(table, value,
                        isSelected, hasFocus, row, column);
                label.setHorizontalAlignment(JLabel.CENTER);

                if (value instanceof String) {
                    String imagePath = (String) value;
                    try {
                        URL imgUrl = getClass().getClassLoader().getResource(imagePath);
                        if (imgUrl != null) {
                            ImageIcon icon = new ImageIcon(imgUrl);
                            Image scaledImage = icon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
                            label.setIcon(new ImageIcon(scaledImage));
                        } else {
                            label.setIcon(null);
                            label.setText("[Image]");
                        }
                    } catch (Exception e) {
                        label.setIcon(null);
                        label.setText("[Error]");
                    }
                }
                return label;
            }
        });

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        panel.add(headerPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private void showReportLostItemDialog() {
        JOptionPane.showMessageDialog(this,
                "Report lost item functionality would be implemented here",
                "Report Lost Item",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private JPanel createLostItemCard(LostItem item) {
        RoundedPanel card = new RoundedPanel(15);
        card.setBackground(Color.WHITE);
        card.setLayout(new BorderLayout());
        card.setBorder(new EmptyBorder(15, 15, 15, 15));

        try {
            URL imgUrl = getClass().getClassLoader().getResource(item.getImagePath());
            if (imgUrl != null) {
                ImageIcon icon = new ImageIcon(imgUrl);
                Image scaledImage = icon.getImage().getScaledInstance(200, 150, Image.SCALE_SMOOTH);
                card.add(new JLabel(new ImageIcon(scaledImage)) {{
                    setHorizontalAlignment(JLabel.CENTER);
                }}, BorderLayout.NORTH);
            }
        } catch (Exception e) {
            card.add(new JLabel("[Item image]"), BorderLayout.NORTH);
        }

        JPanel detailsPanel = new JPanel();
        detailsPanel.setLayout(new BoxLayout(detailsPanel, BoxLayout.Y_AXIS));
        detailsPanel.setOpaque(false);

        detailsPanel.add(new JLabel("ID: " + item.getId()));
        detailsPanel.add(Box.createVerticalStrut(5));
        detailsPanel.add(new JLabel("Desc: " + item.getDescription()));
        detailsPanel.add(Box.createVerticalStrut(5));
        detailsPanel.add(new JLabel("Found: " + item.getRoomNumber()));
        detailsPanel.add(Box.createVerticalStrut(5));
        detailsPanel.add(new JLabel("Date: " + item.getFoundDate()));
        detailsPanel.add(Box.createVerticalStrut(10));
        detailsPanel.add(new StatusBadge(item.getStatus()));

        card.add(detailsPanel, BorderLayout.CENTER);
        card.add(new StyledButton("Claim Item"), BorderLayout.SOUTH);

        return card;
    }

    private void logout() {
        SwingUtilities.invokeLater(() -> {
            LoginWindow loginWindow = new LoginWindow();
            loginWindow.setVisible(true);
            dispose();
        });
    }

    private ImageIcon createHotelIcon() {
        try {
            URL imgUrl = getClass().getClassLoader().getResource("images/logo.png");
            if (imgUrl != null) {
                ImageIcon icon = new ImageIcon(imgUrl);
                Image scaledImage = icon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
                return new ImageIcon(scaledImage);
            }
        } catch (Exception e) {
            System.err.println("Error loading logo: " + e.getMessage());
        }
        return new ImageIcon(new BufferedImage(40, 40, BufferedImage.TYPE_INT_ARGB));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CustomerDashboard dashboard = new CustomerDashboard("TestUser");
            dashboard.setVisible(true);
        });
    }

    class LostItem {
        private String id, description, roomNumber, imagePath, foundDate, status;

        public LostItem(String id, String desc, String room, String image) {
            this.id = id;
            this.description = desc;
            this.roomNumber = room;
            this.imagePath = image;
            this.foundDate = "2023-11-15";
            this.status = "Unclaimed";
        }

        public String getId() { return id; }
        public String getDescription() { return description; }
        public String getRoomNumber() { return roomNumber; }
        public String getImagePath() { return imagePath; }
        public String getFoundDate() { return foundDate; }
        public String getStatus() { return status; }
    }
}