import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.awt.event.*;
import javax.swing.border.*;
import java.util.Date;

import javax.swing.plaf.basic.BasicComboBoxUI;

import com.toedter.calendar.JDateChooser;


public class CustomerDashboard extends JFrame {
    private JTabbedPane tabbedPane;
    private String currentUsername;

    // inside CustomerDashboard.java:

    public CustomerDashboard(String username) {
        this.currentUsername = username != null ? username : "Guest";
        initializeUI();
    }

    private void initializeUI() {
        setTitle("MiraVelle - Customer Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);

        BackgroundPanel mainPanel = new BackgroundPanel("images/background.jpg"); // Correct
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
        titleLabel.setFont(new Font("Georgia", Font.BOLD, 40));
        titleLabel.setForeground(HotelManagementSystem.LIGHT_COLOR);
        logoPanel.add(titleLabel);

        JPanel userPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        userPanel.setOpaque(false);

        JLabel userLabel = new JLabel("Welcome, " + currentUsername);
        userLabel.setFont(HotelManagementSystem.NORMAL_FONT);

        JButton profileButton = new StyledButton("Profile");
        profileButton.addActionListener(e -> openProfile());

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
        tabbedPane.setOpaque(false);
        tabbedPane.setBackground(new Color(0,0,0,0)); // transparent



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

        JPanel mainContent = new JPanel();
        mainContent.setLayout(new BoxLayout(mainContent, BoxLayout.Y_AXIS));
        mainContent.setOpaque(false);

        // ✅ Properly use welcome panel now:
        mainContent.add(createWelcomePanel());
        mainContent.add(Box.createVerticalStrut(15));
        mainContent.add(createQuickActionsPanel());
        mainContent.add(Box.createVerticalStrut(15));
        mainContent.add(createFeaturedRoomsPanel());

        JScrollPane scrollPane = new JScrollPane(mainContent);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }


    private RoundedPanel createWelcomePanel() {
        RoundedPanel panel = new RoundedPanel(20);
        panel.setBackground(new Color(
                HotelManagementSystem.PRIMARY_COLOR.getRed(),
                HotelManagementSystem.PRIMARY_COLOR.getGreen(),
                HotelManagementSystem.PRIMARY_COLOR.getBlue(),
                180
        )); // Semi-transparent

        panel.setLayout(new BorderLayout());
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel welcomeLabel = new JLabel("Welcome to MiraVelle, " + currentUsername + "!");
        welcomeLabel.setFont(HotelManagementSystem.HEADING_FONT);
        welcomeLabel.setForeground(Color.WHITE); // Light text on dark background

        JLabel subtitleLabel = new JLabel("Discover comfort and luxury at its finest.");
        subtitleLabel.setFont(HotelManagementSystem.NORMAL_FONT);
        subtitleLabel.setForeground(Color.WHITE);

        JPanel textPanel = new JPanel(new GridLayout(2, 1));
        textPanel.setOpaque(false);
        textPanel.add(welcomeLabel);
        textPanel.add(subtitleLabel);

        JButton bookNowButton = new StyledButton("Book");
        bookNowButton.addActionListener(e -> tabbedPane.setSelectedIndex(1));

        panel.add(textPanel, BorderLayout.CENTER);
        panel.add(bookNowButton, BorderLayout.EAST);

        return panel;
    }



    private RoundedPanel createQuickActionsPanel() {
        RoundedPanel panel = new RoundedPanel(15);
        panel.setBackground(new Color(
                HotelManagementSystem.LIGHT_COLOR.getRed(),
                HotelManagementSystem.LIGHT_COLOR.getGreen(),
                HotelManagementSystem.LIGHT_COLOR.getBlue(),
                200
        )); // semi-transparent light color
        panel.setLayout(new GridLayout(1, 3, 15, 0));
        panel.setBorder(new EmptyBorder(15, 15, 15, 15));
        panel.setOpaque(false);

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
        panel.setBackground(new Color(
                HotelManagementSystem.LIGHT_COLOR.getRed(),
                HotelManagementSystem.LIGHT_COLOR.getGreen(),
                HotelManagementSystem.LIGHT_COLOR.getBlue(),
                200
        ));
        panel.setLayout(new BorderLayout());
        panel.setBorder(new EmptyBorder(15, 15, 15, 15));
        panel.setOpaque(false);

        JLabel titleLabel = new JLabel("Featured Rooms");
        titleLabel.setFont(HotelManagementSystem.SUBHEADING_FONT);
        titleLabel.setForeground(HotelManagementSystem.TEXT_COLOR);

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
            URL imgUrl = getClass().getResource("/" + imagePath);
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
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        RoundedPanel formPanel = new RoundedPanel(25);
        formPanel.setBackground(new Color(255, 255, 255, 200)); // Even more translucent background
        formPanel.setLayout(new BorderLayout());
        formPanel.setBorder(new EmptyBorder(30, 30, 30, 30));

        JLabel titleLabel = new JLabel("Book Your Stay");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 26));
        titleLabel.setForeground(new Color(66, 103, 178));

        JPanel fieldsPanel = new JPanel(new GridLayout(0, 2, 20, 20));
        fieldsPanel.setOpaque(false);

        JDateChooser checkInChooser = new JDateChooser();
        JDateChooser checkOutChooser = new JDateChooser();
        JComboBox<Integer> adultsBox = new JComboBox<>(new Integer[]{1, 2, 3, 4});
        JComboBox<Integer> childrenBox = new JComboBox<>(new Integer[]{0, 1, 2, 3, 4});
        JComboBox<String> roomTypeBox = new JComboBox<>(new String[]{
                "Standard Room", "Deluxe Room", "Executive Suite", "Family Suite", "Presidential Suite"
        });
        JTextArea requestsArea = new JTextArea(3, 20);

        fieldsPanel.add(createInputField("Check-in Date", checkInChooser));
        fieldsPanel.add(createInputField("Check-out Date", checkOutChooser));
        fieldsPanel.add(createInputField("Adults", adultsBox));
        fieldsPanel.add(createInputField("Children", childrenBox));
        fieldsPanel.add(createInputField("Room Type", roomTypeBox));
        fieldsPanel.add(new JPanel()); // Empty filler

        requestsArea.setLineWrap(true);
        requestsArea.setFont(new Font("Segoe UI", Font.ITALIC, 14));
        requestsArea.setForeground(new Color(100, 100, 100));
        JPanel requestsPanel = createInputField("Special Requests", new JScrollPane(requestsArea));

        StyledButton viewRoomsButton = new StyledButton("Search");
        viewRoomsButton.setBackground(new Color(173, 216, 230)); // Light blue
        viewRoomsButton.setBorder(BorderFactory.createLineBorder(new Color(70, 130, 180)));
        viewRoomsButton.setFont(new Font("Segoe UI", Font.BOLD, 16));

// When clicked, call showAvailableRooms
        viewRoomsButton.addActionListener(e -> {
            showAvailableRooms(formPanel);  // 👈 MAGIC
        });


        // --- Submit Button (NEW Beautiful Popup) ---
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setOpaque(false);
        StyledButton submitButton = new StyledButton("Submit");
        submitButton.setBackground(new Color(255, 182, 193));
        submitButton.setBorder(BorderFactory.createLineBorder(new Color(255, 105, 180)));
        submitButton.setFont(new Font("Segoe UI", Font.BOLD, 16));

        // Submit Button Action
        submitButton.addActionListener(e -> {
            showReceiptPanel(
                    checkInChooser.getDate(),
                    checkOutChooser.getDate(),
                    (Integer) adultsBox.getSelectedItem(),
                    (Integer) childrenBox.getSelectedItem(),
                    (String) roomTypeBox.getSelectedItem(),
                    requestsArea.getText()
            );
        });

        buttonPanel.add(submitButton);
        buttonPanel.add(viewRoomsButton);


        JPanel formContent = new JPanel(new BorderLayout(0, 15));
        formContent.setOpaque(false);
        formContent.add(titleLabel, BorderLayout.NORTH);
        formContent.add(fieldsPanel, BorderLayout.CENTER);
        formContent.add(requestsPanel, BorderLayout.SOUTH);

        formPanel.add(formContent, BorderLayout.CENTER);
        formPanel.add(buttonPanel, BorderLayout.SOUTH);

        JScrollPane scrollPane = new JScrollPane(formPanel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);

        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }




    private void showAvailableRooms(JPanel formPanel) {
        // Create a new panel to display available rooms
        JPanel roomsPanel = new JPanel();
        roomsPanel.setLayout(new BorderLayout());
        roomsPanel.setBackground(new Color(255, 255, 255, 180));

        // Close Button to go back to the booking form
        JButton closeButton = new JButton("Close");
        closeButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        closeButton.setBackground(new Color(255, 105, 180)); // Light pink
        closeButton.setForeground(Color.BLUE);
        closeButton.addActionListener(e -> {
            formPanel.removeAll();
            formPanel.add(createBookingPanel(), BorderLayout.CENTER);  // Go back to the booking form
            formPanel.revalidate();
            formPanel.repaint();
        });

        // Add the close button at the top
        JPanel closePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        closePanel.setOpaque(false);
        closePanel.add(closeButton);

        // Sample room data (replace with dynamic data)
        Room[] rooms = {
                new Room("101", "Standard Room", "A cozy room with all basic amenities", "images/FamilyS.jpg", "Available"),
                new Room("102", "Deluxe Room", "A spacious room with luxury features", "images/Deluxe.jpg", "Occupied"),
                new Room("103", "Executive Suite", "Perfect for business travelers", "images/Executive.jpg", "Available")
        };

        JPanel roomCardsPanel = new JPanel();
        roomCardsPanel.setLayout(new GridLayout(0, 3, 20, 20));

        for (Room room : rooms) {
            roomCardsPanel.add(createRoomCard(room));
        }

        roomsPanel.add(closePanel, BorderLayout.NORTH);  // Add close button to the top of the panel
        roomsPanel.add(roomCardsPanel, BorderLayout.CENTER);

        // Replace the form with the rooms panel
        formPanel.removeAll();
        formPanel.add(roomsPanel, BorderLayout.CENTER);
        formPanel.revalidate();
        formPanel.repaint();
    }


    private JPanel createRoomCard(Room room) {
        JPanel card = new JPanel();
        card.setLayout(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createLineBorder(new Color(240, 240, 240), 1));
        card.setPreferredSize(new Dimension(250, 300));

        // Room Image
        JLabel imageLabel = new JLabel(new ImageIcon(room.getImagePath()));
        imageLabel.setPreferredSize(new Dimension(200, 150));
        card.add(imageLabel, BorderLayout.CENTER);

        // Room Details
        JPanel detailsPanel = new JPanel();
        detailsPanel.setLayout(new BoxLayout(detailsPanel, BoxLayout.Y_AXIS));
        detailsPanel.setOpaque(false);

        JLabel roomNumberLabel = new JLabel("Room " + room.getRoomNumber());
        roomNumberLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        roomNumberLabel.setForeground(new Color(66, 103, 178));

        JLabel roomTypeLabel = new JLabel(room.getRoomType());
        roomTypeLabel.setFont(new Font("Segoe UI", Font.ITALIC, 14));

        JLabel roomDescriptionLabel = new JLabel("<html>" + room.getDescription() + "</html>");
        roomDescriptionLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));

        JLabel availabilityLabel = new JLabel("Availability: " + room.getAvailabilityStatus());
        availabilityLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        availabilityLabel.setForeground(room.getAvailabilityStatus().equals("Available") ? Color.GREEN : Color.RED);

        detailsPanel.add(roomNumberLabel);
        detailsPanel.add(roomTypeLabel);
        detailsPanel.add(roomDescriptionLabel);
        detailsPanel.add(availabilityLabel);

        card.add(detailsPanel, BorderLayout.SOUTH);

        return card;
    }

    private JPanel createInputField(String label, JComponent field) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);

        JLabel jLabel = new JLabel(label);
        jLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        jLabel.setForeground(new Color(66, 103, 178));
        jLabel.setBackground(new Color(255, 255, 255, 180));
        jLabel.setOpaque(true);
        panel.add(jLabel, BorderLayout.NORTH);
        panel.add(field, BorderLayout.CENTER);

        return panel;
    }

    private void showReceiptPanel(Date checkIn, Date checkOut, int adults, int children, String roomType, String specialRequests) {
        JPanel receiptPanel = new JPanel();
        receiptPanel.setLayout(new BoxLayout(receiptPanel, BoxLayout.Y_AXIS));
        receiptPanel.setBackground(new Color(255, 250, 250)); // Very soft pink-white

        JLabel successLabel = new JLabel("🎉 THANK YOU!");
        successLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        successLabel.setForeground(new Color(66, 103, 178));
        successLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel thankYouLabel = new JLabel("Enjoy your stay at Miravelle 🌸");
        thankYouLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        thankYouLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        receiptPanel.add(Box.createVerticalStrut(20));
        receiptPanel.add(successLabel);
        receiptPanel.add(thankYouLabel);
        receiptPanel.add(Box.createVerticalStrut(30));

        // Details
        receiptPanel.add(createReceiptLine("Check-in Date: ", checkIn != null ? checkIn.toString() : "Not selected"));
        receiptPanel.add(createReceiptLine("Check-out Date: ", checkOut != null ? checkOut.toString() : "Not selected"));
        receiptPanel.add(createReceiptLine("Adults: ", String.valueOf(adults)));
        receiptPanel.add(createReceiptLine("Children: ", String.valueOf(children)));
        receiptPanel.add(createReceiptLine("Room Type: ", roomType));
        receiptPanel.add(createReceiptLine("Special Requests: ", specialRequests.isEmpty() ? "None" : specialRequests));

        JScrollPane scrollPane = new JScrollPane(receiptPanel);
        scrollPane.setPreferredSize(new Dimension(400, 400));
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        JOptionPane.showMessageDialog(null, scrollPane, "Booking Summary", JOptionPane.PLAIN_MESSAGE);
    }

    private JPanel createReceiptLine(String label, String value) {
        JPanel line = new JPanel(new FlowLayout(FlowLayout.LEFT));
        line.setOpaque(false);
        JLabel lbl = new JLabel(label);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 14));
        JLabel val = new JLabel(value);
        val.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        line.add(lbl);
        line.add(val);
        return line;
    }


    // Room class for sample data (Use your own room data logic)
    class Room {
        private String roomNumber;
        private String roomType;
        private String description;
        private String imagePath;
        private String availabilityStatus;

        public Room(String roomNumber, String roomType, String description, String imagePath, String availabilityStatus) {
            this.roomNumber = roomNumber;
            this.roomType = roomType;
            this.description = description;
            this.imagePath = imagePath;
            this.availabilityStatus = availabilityStatus;
        }

        public String getRoomNumber() {
            return roomNumber;
        }

        public String getRoomType() {
            return roomType;
        }

        public String getDescription() {
            return description;
        }

        public String getImagePath() {
            return imagePath;
        }

        public String getAvailabilityStatus() {
            return availabilityStatus;
        }
    }







    // This is just the createReservationsPanel() method from CustomerDashboard.java
// Replace your existing method with this one

    private JPanel createReservationsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);
        panel.setBorder(new EmptyBorder(15, 15, 15, 15));

        // Elegant header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);
        headerPanel.setBorder(new EmptyBorder(0, 0, 20, 0));

        JLabel titleLabel = new JLabel("My Reservations");
        titleLabel.setFont(HotelManagementSystem.HEADING_FONT.deriveFont(Font.BOLD, 26f));
        titleLabel.setForeground(HotelManagementSystem.ACCENT_COLOR);

        // Gold underline decoration
        JPanel decorPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(HotelManagementSystem.ACCENT_COLOR);
                g2d.fillRoundRect(0, getHeight() - 4, 120, 4, 5, 5);
            }
        };
        decorPanel.setPreferredSize(new Dimension(150, 30));
        decorPanel.setOpaque(false);

        JPanel titleWrapper = new JPanel(new BorderLayout());
        titleWrapper.setOpaque(false);
        titleWrapper.add(titleLabel, BorderLayout.CENTER);
        titleWrapper.add(decorPanel, BorderLayout.SOUTH);

        // Action buttons
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        actionPanel.setOpaque(false);

        JButton refreshButton = createIconButton("Refresh", "images/refresh.png");
        JButton filterButton = createIconButton("Filter", "images/filter.png");
        JButton exportButton = createIconButton("Export", "images/export.png");

        actionPanel.add(filterButton);
        actionPanel.add(refreshButton);
        actionPanel.add(exportButton);

        headerPanel.add(titleWrapper, BorderLayout.WEST);
        headerPanel.add(actionPanel, BorderLayout.EAST);

        // Main content panel
        JPanel cardsPanel = new JPanel();
        cardsPanel.setLayout(new BoxLayout(cardsPanel, BoxLayout.Y_AXIS));
        cardsPanel.setOpaque(false);

        // Sample reservation data
        String[][] reservations = {
                {"RES-001", "Deluxe Suite", "2023-06-15", "2023-06-20", "Confirmed", "images/deluxe.jpg"},
                {"RES-002", "Executive Room", "2023-07-10", "2023-07-15", "Pending", "images/executive.jpg"},
                {"RES-003", "Family Suite", "2023-08-05", "2023-08-12", "Cancelled", "images/FamilyS.jpg"},
                {"RES-004", "Presidential Suite", "2023-09-20", "2023-09-25", "Confirmed", "images/presidential.jpeg"}
        };

        for (String[] reservation : reservations) {
            cardsPanel.add(createReservationCard(
                    reservation[0], reservation[1], reservation[2],
                    reservation[3], reservation[4], reservation[5]
            ));
            cardsPanel.add(Box.createVerticalStrut(15));
        }

        JScrollPane scrollPane = new JScrollPane(cardsPanel);
        scrollPane.setBorder(null);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        panel.add(headerPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createReservationCard(String id, String roomType, String checkIn, String checkOut, String status, String imagePath) {
        JPanel card = new JPanel(new BorderLayout(15, 0)) {
            private boolean isHovered = false;

            {
                addMouseListener(new java.awt.event.MouseAdapter() {
                    public void mouseEntered(java.awt.event.MouseEvent evt) {
                        isHovered = true;
                        setCursor(new Cursor(Cursor.HAND_CURSOR));
                        repaint();
                    }
                    public void mouseExited(java.awt.event.MouseEvent evt) {
                        isHovered = false;
                        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                        repaint();
                    }
                });
            }

            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                Color baseColor = new Color(
                        HotelManagementSystem.LIGHT_COLOR.getRed(),
                        HotelManagementSystem.LIGHT_COLOR.getGreen(),
                        HotelManagementSystem.LIGHT_COLOR.getBlue(),
                        220
                );
                g2d.setColor(isHovered ? baseColor.brighter() : baseColor);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);

                super.paintComponent(g);
            }
        };

        card.setOpaque(false);
        card.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Image
        JPanel imagePanel = new JPanel(new BorderLayout());
        imagePanel.setOpaque(false);
        imagePanel.setPreferredSize(new Dimension(120, 90));

        try {
            ImageIcon icon = new ImageIcon(imagePath);
            Image img = icon.getImage().getScaledInstance(120, 90, Image.SCALE_SMOOTH);
            JLabel imageLabel = new JLabel(new ImageIcon(img));
            imageLabel.setBorder(new RoundedBorder(10, new Color(230, 230, 230)));
            imagePanel.add(imageLabel, BorderLayout.CENTER);
        } catch (Exception e) {
            JPanel placeholderPanel = new JPanel();
            placeholderPanel.setBackground(HotelManagementSystem.LIGHT_COLOR);
            placeholderPanel.setBorder(new RoundedBorder(10, new Color(230, 230, 230)));
            imagePanel.add(placeholderPanel, BorderLayout.CENTER);
        }

        // Details
        JPanel detailsPanel = new JPanel();
        detailsPanel.setLayout(new BoxLayout(detailsPanel, BoxLayout.Y_AXIS));
        detailsPanel.setOpaque(false);

        JLabel roomLabel = new JLabel(roomType);
        roomLabel.setFont(HotelManagementSystem.SUBHEADING_FONT.deriveFont(Font.BOLD, 18f));
        roomLabel.setForeground(HotelManagementSystem.PRIMARY_COLOR);

        JLabel idLabel = new JLabel("Reservation ID: " + id);
        idLabel.setFont(HotelManagementSystem.NORMAL_FONT);
        idLabel.setForeground(HotelManagementSystem.TEXT_COLOR);

        JLabel dateLabel = new JLabel("Check-in: " + checkIn + " | Check-out: " + checkOut);
        dateLabel.setFont(HotelManagementSystem.SMALL_FONT);
        dateLabel.setForeground(HotelManagementSystem.TEXT_COLOR);

        detailsPanel.add(roomLabel);
        detailsPanel.add(Box.createVerticalStrut(5));
        detailsPanel.add(idLabel);
        detailsPanel.add(Box.createVerticalStrut(5));
        detailsPanel.add(dateLabel);

        // Status and Actions
        JPanel statusPanel = new JPanel(new BorderLayout(0, 10));
        statusPanel.setOpaque(false);

        JLabel statusLabel = new JLabel(status);
        statusLabel.setFont(HotelManagementSystem.NORMAL_FONT.deriveFont(Font.BOLD));
        if ("Confirmed".equals(status)) {
            statusLabel.setForeground(HotelManagementSystem.SUCCESS_COLOR);
        } else if ("Pending".equals(status)) {
            statusLabel.setForeground(HotelManagementSystem.WARNING_COLOR);
        } else if ("Cancelled".equals(status)) {
            statusLabel.setForeground(HotelManagementSystem.ERROR_COLOR);
        }

        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 0));
        buttonsPanel.setOpaque(false);

        JButton viewButton = new StyledButton("Details");
        viewButton.setFont(HotelManagementSystem.SMALL_FONT);
        viewButton.setPreferredSize(new Dimension(110, 30));
        viewButton.addActionListener(e -> showReservationPopup(id, roomType, checkIn, checkOut, status, imagePath));



        buttonsPanel.add(viewButton);

        statusPanel.add(statusLabel, BorderLayout.NORTH);
        statusPanel.add(buttonsPanel, BorderLayout.SOUTH);

        card.add(imagePanel, BorderLayout.WEST);
        card.add(detailsPanel, BorderLayout.CENTER);
        card.add(statusPanel, BorderLayout.EAST);

        return card;
    }

    // Cute Aesthetic Popup for Reservation Details
    private void showReservationPopup(String id, String roomType, String checkIn, String checkOut, String status, String imagePath) {
        JPanel popup = new JPanel();
        popup.setLayout(new BoxLayout(popup, BoxLayout.Y_AXIS));
        popup.setBackground(Color.WHITE);
        popup.setBorder(new EmptyBorder(15, 15, 15, 15));

        JLabel heading = new JLabel(roomType);
        heading.setFont(new Font("SansSerif", Font.BOLD, 20));
        heading.setForeground(HotelManagementSystem.PRIMARY_COLOR);
        heading.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel idText = new JLabel("Reservation ID: " + id);
        idText.setFont(new Font("SansSerif", Font.PLAIN, 16));
        idText.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel dateText = new JLabel("Stay: " + checkIn + " → " + checkOut);
        dateText.setFont(new Font("SansSerif", Font.PLAIN, 16));
        dateText.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel statusText = new JLabel("Status: " + status);
        statusText.setFont(new Font("SansSerif", Font.BOLD, 16));
        statusText.setAlignmentX(Component.CENTER_ALIGNMENT);
        if ("Confirmed".equals(status)) {
            statusText.setForeground(HotelManagementSystem.SUCCESS_COLOR);
        } else if ("Pending".equals(status)) {
            statusText.setForeground(HotelManagementSystem.WARNING_COLOR);
        } else if ("Cancelled".equals(status)) {
            statusText.setForeground(HotelManagementSystem.ERROR_COLOR);
        }

        popup.add(heading);
        popup.add(Box.createVerticalStrut(10));
        popup.add(idText);
        popup.add(dateText);
        popup.add(statusText);

        JOptionPane.showMessageDialog(null, popup, "Reservation Details", JOptionPane.PLAIN_MESSAGE);
    }


    // Helper method to create icon buttons
    private JButton createIconButton(String text, String iconPath) {
        JButton button = new JButton(text);
        try {
            ImageIcon icon = new ImageIcon(iconPath);
            Image img = icon.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH);
            button.setIcon(new ImageIcon(img));
        } catch (Exception e) {
            // Just use text if icon fails to load
        }

        button.setFont(HotelManagementSystem.SMALL_FONT);
        button.setBackground(HotelManagementSystem.PRIMARY_COLOR);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(HotelManagementSystem.PRIMARY_COLOR.darker());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(HotelManagementSystem.PRIMARY_COLOR);
            }
        });

        return button;
    }

    // Custom rounded border for images
    class RoundedBorder extends AbstractBorder {
        private int radius;
        private Color color;

        public RoundedBorder(int radius, Color color) {
            this.radius = radius;
            this.color = color;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setColor(color);
            g2d.drawRoundRect(x, y, width-1, height-1, radius, radius);
            g2d.dispose();
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(this.radius, this.radius, this.radius, this.radius);
        }

        @Override
        public boolean isBorderOpaque() {
            return false;
        }
    }

    private JPanel createServicesPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);
        panel.setBorder(new EmptyBorder(15, 15, 15, 15));

        panel.add(new JLabel("Hotel Services") {{
            setFont(HotelManagementSystem.HEADING_FONT);
            setForeground(HotelManagementSystem.ACCENT_COLOR);
        }}, BorderLayout.NORTH);

        JPanel servicesGrid = new JPanel(new GridLayout(3, 2, 20, 20));
        servicesGrid.setOpaque(false);

        // Create all buttons
        JButton orderFoodButton = createServiceButton("Order Food", "Order food and beverages", "images/RoomS.jpg");
        JButton requestCleaningButton = createServiceButton("Request Cleaning", "Request room cleaning services", "images/HouseK.jpg");
        JButton bookSpaButton = createServiceButton("Book Spa", "Book spa and wellness treatments", "images/Spa.jpg");
        JButton requestTaxiButton = createServiceButton("Request Taxi", "Book a taxi for local travel", "images/taxi.jpg");
        JButton wakeUpCallButton = createServiceButton("Wake-Up Call", "Schedule a wake-up call", "images/wakeup.jpg");
        JButton otherServiceButton = createServiceButton("Other Services", "Request other hotel services", "images/other.jpg");

        // --- Actions (from your second code) ---

        // 1. Order Food
        orderFoodButton.addActionListener(e -> {
            JDialog dialog = new JDialog((JFrame) SwingUtilities.getWindowAncestor(panel), "Order Food", true);
            dialog.setSize(400, 250);
            dialog.setLayout(new GridLayout(4, 2, 10, 10));
            dialog.setLocationRelativeTo(panel);

            JLabel foodTypeLabel = new JLabel("Select Food Type:");
            JComboBox<String> foodTypeBox = new JComboBox<>(new String[]{"Veg", "Non-Veg"});

            JLabel dishLabel = new JLabel("Select Dish:");
            JComboBox<String> dishBox = new JComboBox<>();

            foodTypeBox.addActionListener(ev -> {
                dishBox.removeAllItems();
                if (foodTypeBox.getSelectedItem().equals("Veg")) {
                    dishBox.addItem("Paneer Butter Masala");
                    dishBox.addItem("Veg Biryani");
                    dishBox.addItem("Dal Tadka");
                    dishBox.addItem("Mix Veg Curry");
                } else {
                    dishBox.addItem("Chicken Curry");
                    dishBox.addItem("Fish Fry");
                    dishBox.addItem("Mutton Biryani");
                    dishBox.addItem("Prawns Masala");
                }
            });
            foodTypeBox.setSelectedIndex(0);

            JButton confirmButton = new JButton("Confirm Order");
            confirmButton.addActionListener(ev -> {
                String foodType = (String) foodTypeBox.getSelectedItem();
                String dish = (String) dishBox.getSelectedItem();
                JOptionPane.showMessageDialog(panel, "Order placed for: " + dish + " (" + foodType + ")");
                dialog.dispose();
            });

            dialog.add(foodTypeLabel);
            dialog.add(foodTypeBox);
            dialog.add(dishLabel);
            dialog.add(dishBox);
            dialog.add(new JLabel());
            dialog.add(confirmButton);

            dialog.setVisible(true);
        });

        // 2. Request Room Cleaning
        requestCleaningButton.addActionListener(e -> {
            JDialog dialog = new JDialog((JFrame) SwingUtilities.getWindowAncestor(panel), "Request Room Cleaning", true);
            dialog.setSize(400, 200);
            dialog.setLayout(new GridLayout(3, 1, 10, 10));
            dialog.setLocationRelativeTo(panel);

            JLabel noteLabel = new JLabel("Enter any additional notes:");
            JTextField noteField = new JTextField();

            JButton confirmButton = new JButton("Request Cleaning");
            confirmButton.addActionListener(ev -> {
                String note = noteField.getText();
                JOptionPane.showMessageDialog(panel, "Cleaning requested with note: " + note);
                dialog.dispose();
            });

            dialog.add(noteLabel);
            dialog.add(noteField);
            dialog.add(confirmButton);

            dialog.setVisible(true);
        });

        // 3. Book Spa
        bookSpaButton.addActionListener(e -> {
            JDialog dialog = new JDialog((JFrame) SwingUtilities.getWindowAncestor(panel), "Book Spa Appointment", true);
            dialog.setSize(400, 200);
            dialog.setLayout(new GridLayout(3, 1, 10, 10));
            dialog.setLocationRelativeTo(panel);

            JLabel serviceLabel = new JLabel("Select Spa Service:");
            JComboBox<String> spaBox = new JComboBox<>(new String[]{"Head Massage", "Foot Massage", "Full Body Massage", "Facial"});

            JButton confirmButton = new JButton("Book Appointment");
            confirmButton.addActionListener(ev -> {
                String service = (String) spaBox.getSelectedItem();
                JOptionPane.showMessageDialog(panel, "Spa appointment booked for: " + service);
                dialog.dispose();
            });

            dialog.add(serviceLabel);
            dialog.add(spaBox);
            dialog.add(confirmButton);

            dialog.setVisible(true);
        });

        // 4. Request Taxi
        requestTaxiButton.addActionListener(e -> {
            JDialog dialog = new JDialog((JFrame) SwingUtilities.getWindowAncestor(panel), "Request Taxi", true);
            dialog.setSize(400, 200);
            dialog.setLayout(new GridLayout(3, 1, 10, 10));
            dialog.setLocationRelativeTo(panel);

            JLabel destinationLabel = new JLabel("Enter Destination Address:");
            JTextField destinationField = new JTextField();

            JButton confirmButton = new JButton("Book Taxi");
            confirmButton.addActionListener(ev -> {
                String destination = destinationField.getText();
                JOptionPane.showMessageDialog(panel, "Taxi booked to: " + destination);
                dialog.dispose();
            });

            dialog.add(destinationLabel);
            dialog.add(destinationField);
            dialog.add(confirmButton);

            dialog.setVisible(true);
        });

        // 5. Schedule Wake-Up Call
        wakeUpCallButton.addActionListener(e -> {
            JDialog dialog = new JDialog((JFrame) SwingUtilities.getWindowAncestor(panel), "Schedule Wake-Up Call", true);
            dialog.setSize(400, 200);
            dialog.setLayout(new GridLayout(3, 1, 10, 10));
            dialog.setLocationRelativeTo(panel);

            JLabel timeLabel = new JLabel("Enter Wake-Up Time (e.g., 6:30 AM):");
            JTextField timeField = new JTextField();

            JButton confirmButton = new JButton("Set Wake-Up Call");
            confirmButton.addActionListener(ev -> {
                String time = timeField.getText();
                JOptionPane.showMessageDialog(panel, "Wake-up call scheduled at: " + time);
                dialog.dispose();
            });

            dialog.add(timeLabel);
            dialog.add(timeField);
            dialog.add(confirmButton);

            dialog.setVisible(true);
        });

        // 6. Other Services
        otherServiceButton.addActionListener(e -> {
            JDialog dialog = new JDialog((JFrame) SwingUtilities.getWindowAncestor(panel), "Other Services", true);
            dialog.setSize(400, 200);
            dialog.setLayout(new GridLayout(3, 1, 10, 10));
            dialog.setLocationRelativeTo(panel);

            JLabel serviceLabel = new JLabel("Select Other Service:");
            JComboBox<String> otherServicesBox = new JComboBox<>(new String[]{"Laundry Service", "Luggage Handling", "Shoe Polishing", "Newspaper Delivery"});

            JButton confirmButton = new JButton("Request Service");
            confirmButton.addActionListener(ev -> {
                String service = (String) otherServicesBox.getSelectedItem();
                JOptionPane.showMessageDialog(panel, service + " requested successfully!");
                dialog.dispose();
            });

            dialog.add(serviceLabel);
            dialog.add(otherServicesBox);
            dialog.add(confirmButton);

            dialog.setVisible(true);
        });

        // Add all buttons
        servicesGrid.add(orderFoodButton);
        servicesGrid.add(requestCleaningButton);
        servicesGrid.add(bookSpaButton);
        servicesGrid.add(requestTaxiButton);
        servicesGrid.add(wakeUpCallButton);
        servicesGrid.add(otherServiceButton);

        JScrollPane scrollPane = new JScrollPane(servicesGrid);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private JButton createServiceButton(String title, String description, String imagePath) {
        // Load and resize the image
        ImageIcon originalIcon = new ImageIcon(imagePath);
        Image scaledImage = originalIcon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH); // Bigger size for clarity
        ImageIcon resizedIcon = new ImageIcon(scaledImage);

        JButton button = new JButton() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Draw the image
                g.drawImage(resizedIcon.getImage(), 0, 0, getWidth(), getHeight(), this);

                // Set text properties
                g.setColor(Color.WHITE); // Text color
                g.setFont(new Font("Arial", Font.BOLD, 16));
                FontMetrics fm = g.getFontMetrics();

                // Calculate center position for title
                int titleWidth = fm.stringWidth(title);
                int titleX = (getWidth() - titleWidth) / 2;
                int titleY = getHeight() / 2 - 10;

                // Calculate center position for description (smaller text)
                g.setFont(new Font("Arial", Font.PLAIN, 12));
                FontMetrics fmSmall = g.getFontMetrics();
                int descWidth = fmSmall.stringWidth(description);
                int descX = (getWidth() - descWidth) / 2;
                int descY = getHeight() / 2 + 10;

                // Draw title and description
                g.setFont(new Font("Arial", Font.BOLD, 16));
                g.drawString(title, titleX, titleY);
                g.setFont(new Font("Arial", Font.PLAIN, 12));
                g.drawString(description, descX, descY);
            }
        };

        button.setPreferredSize(new Dimension(150, 300));
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setOpaque(false);

        return button;
    }

    private JPanel createLostAndFoundPanel() {
        return new LostAndFoundPanel(false, currentUsername);
    }

    private void logout() {
        // Implement logout functionality here
        JOptionPane.showMessageDialog(this, "Logout successful!");
    }



    static class GradientPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            Color color1 = new Color(240, 248, 255); // AliceBlue
            Color color2 = new Color(220, 220, 220); // LightGray
            GradientPaint gp = new GradientPaint(0, 0, color1, 0, getHeight(), color2);
            g2d.setPaint(gp);
            g2d.fillRect(0, 0, getWidth(), getHeight());
        }
    }

    class StyledButton extends JButton {
        private Color normalBackground = HotelManagementSystem.ACCENT_COLOR;
        private Color hoverBackground = HotelManagementSystem.ACCENT_COLOR.brighter();
        private boolean isHovered = false;

        public StyledButton(String text) {
            super(text);
            setFont(HotelManagementSystem.BUTTON_FONT);
            setForeground(Color.WHITE);
            setFocusPainted(false);
            setBorderPainted(false);
            setContentAreaFilled(false); // important!
            setCursor(new Cursor(Cursor.HAND_CURSOR));
            setPreferredSize(new Dimension(120, 40));

            addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent e) {
                    isHovered = true;
                    repaint();
                }

                public void mouseExited(MouseEvent e) {
                    isHovered = false;
                    repaint();
                }
            });
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Draw rounded rectangle with color
            g2.setColor(isHovered ? hoverBackground : normalBackground);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);

            super.paintComponent(g);
            g2.dispose();
        }
    }





    static class RoundedPanel extends JPanel {
        private int radius;

        public RoundedPanel(int radius) {
            this.radius = radius;
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            g2d.setColor(getBackground());
            g2d.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, radius, radius);

            g2d.setColor(getForeground());
            g2d.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, radius, radius);

            g2d.dispose();
            super.paintComponent(g);
        }
    }

    static class HotelManagementSystem {


        public static final Color LIGHT_COLOR = new Color(240, 248, 255);
        public static final Font HEADING_FONT = new Font("Arial", Font.BOLD, 20);
        public static final Font SUBHEADING_FONT = new Font("Arial", Font.BOLD, 16);
        public static final Font NORMAL_FONT = new Font("Arial", Font.PLAIN, 14);
        public static final Font SMALL_FONT = new Font("Arial", Font.PLAIN, 12);

        public static final Color ACCENT_COLOR = new Color(212, 175, 55);
        public static final Color SECONDARY_COLOR = new Color(190, 173, 226);
        public static final Color SUCCESS_COLOR = new Color(76, 175, 80);
        public static final Color WARNING_COLOR = new Color(255, 152, 0);
        public static final Color TEXT_COLOR = new Color(33, 33, 33);
        public static final Color ERROR_COLOR = new Color(192, 57, 43);
        public static final Color PRIMARY_COLOR = new Color(25, 55, 109);

        public static final Font BUTTON_FONT = new Font("Palatino", Font.BOLD, 16);
    }
    private ImageIcon createHotelIcon() {
        try {
            URL imgUrl = getClass().getClassLoader().getResource("images/logo.png");
            if (imgUrl == null) {
                throw new Exception("Logo image not found");
            }
            ImageIcon icon = new ImageIcon(imgUrl);
            Image scaledImage = icon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
            return new ImageIcon(scaledImage);
        } catch (Exception e) {
            System.err.println("Error loading hotel icon: " + e.getMessage());
            return new ImageIcon(new BufferedImage(40, 40, BufferedImage.TYPE_INT_ARGB));
        }
    }
    private void openProfile() {
        new ProfileWindow(currentUsername);
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CustomerDashboard dashboard = new CustomerDashboard("JohnDoe");
            dashboard.setVisible(true);
        });
    }
}






