
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class OwnerDashboard extends JFrame {
    private JTabbedPane tabbedPane;
    private List<Room> rooms;
    private List<Booking> bookings;
    private List<Staff> staff;
    private List<LostItem> lostItems;
    private JTable lostItemsTable; // Add this to store reference to the lost items table

    public OwnerDashboard() {
        setTitle("MiraVelle");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);

        // Initialize sample data
        initializeSampleData();

        // Create main panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);

        // Create header
        JPanel headerPanel = createHeader();
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // Create tabbed pane
        tabbedPane = new JTabbedPane();
        tabbedPane.setFont(HotelManagementSystem.SUBHEADING_FONT);
        tabbedPane.setBackground(Color.WHITE);
        tabbedPane.setForeground(HotelManagementSystem.TEXT_COLOR);

        // Create tabs
        JPanel roomsPanel = createRoomsPanel();
        JPanel bookingsPanel = createBookingsPanel();
        JPanel staffPanel = createStaffPanel();
        JPanel lostFoundPanel = createLostFoundPanel();

        // Add tabs with icons
        tabbedPane.addTab("Rooms Management", createTabIcon("bed"), roomsPanel);
        tabbedPane.addTab("Bookings", createTabIcon("booking"), bookingsPanel);
        tabbedPane.addTab("Staff Management", createTabIcon("staff"), staffPanel);
        tabbedPane.addTab("Lost & Found", createTabIcon("search"), lostFoundPanel);

        // Set custom tab renderer
        tabbedPane.setUI(new CustomTabbedPaneUI());

        mainPanel.add(tabbedPane, BorderLayout.CENTER);

        setContentPane(mainPanel);
    }

    private void initializeSampleData() {
        // Initialize rooms
        rooms = new ArrayList<>();
        rooms.add(new Room("R101", "101", "Standard", "Available", "$120/night", "2023-04-21"));
        rooms.add(new Room("R102", "102", "Deluxe", "Occupied", "$180/night", "2023-04-20"));
        rooms.add(new Room("R103", "201", "Suite", "Maintenance", "$250/night", "2023-04-19"));
        rooms.add(new Room("R104", "202", "Standard", "Available", "$120/night", "2023-04-21"));
        rooms.add(new Room("R105", "301", "Deluxe", "Occupied", "$180/night", "2023-04-20"));

        // Initialize bookings
        bookings = new ArrayList<>();
        bookings.add(new Booking("B001", "John Smith", "102", "2023-04-18", "2023-04-23", "Active", "$900"));
        bookings.add(new Booking("B002", "Jane Doe", "301", "2023-04-20", "2023-04-25", "Active", "$900"));
        bookings.add(new Booking("B003", "Robert Johnson", "201", "2023-04-15", "2023-04-19", "Completed", "$1000"));

        // Initialize staff
        staff = new ArrayList<>();
        staff.add(new Staff("S001", "Michael Brown", "Receptionist", "555-1234", "On Duty"));
        staff.add(new Staff("S002", "Sarah Wilson", "Housekeeper", "555-5678", "On Duty"));
        staff.add(new Staff("S003", "David Lee", "Maintenance", "555-9012", "Off Duty"));

        // Initialize lost items
        lostItems = new ArrayList<>();
        lostItems.add(new LostItem("L001", "Black leather wallet with initials JD", "102", "wallet.jpg"));
        lostItems.add(new LostItem("L002", "Silver watch with brown leather strap", "205", "watch.jpg"));
        lostItems.add(new LostItem("L003", "Blue iPhone charger", "301", "charger.jpg"));

        // Add a sample claim
        ItemClaim claim = new ItemClaim("C001", "L001", "John Doe", "Black leather wallet with my initials JD, contains my driver's license and two credit cards");
        lostItems.get(0).addClaim(claim);
    }

    private JPanel createHeader() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Color.WHITE);
        headerPanel.setBorder(new CompoundBorder(
                new MatteBorder(0, 0, 1, 0, new Color(229, 231, 235)), // Gray-200
                new EmptyBorder(10, 20, 10, 20)
        ));

        // Logo and title
        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        leftPanel.setOpaque(false);

        JLabel iconLabel = new JLabel(createBuildingIcon());
        JLabel titleLabel = new JLabel("Hotel Management");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(HotelManagementSystem.PRIMARY_COLOR);

        leftPanel.add(iconLabel);
        leftPanel.add(titleLabel);

        // User info and logout
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rightPanel.setOpaque(false);

        JPanel userInfoPanel = new JPanel(new BorderLayout());
        userInfoPanel.setOpaque(false);

        JLabel userNameLabel = new JLabel("Admin User");
        userNameLabel.setFont(new Font("Arial", Font.BOLD, 14));
        JLabel userRoleLabel = new JLabel("Hotel Owner");
        userRoleLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        userRoleLabel.setForeground(new Color(107, 114, 128)); // Gray-500

        userInfoPanel.add(userNameLabel, BorderLayout.NORTH);
        userInfoPanel.add(userRoleLabel, BorderLayout.CENTER);

        StyledButton logoutButton = new StyledButton("Logout");
        logoutButton.setPreferredSize(new Dimension(100, 30));
        logoutButton.addActionListener(e -> {
            LoginWindow loginWindow = new LoginWindow();
            loginWindow.setVisible(true);
            dispose();
        });

        rightPanel.add(userInfoPanel);
        rightPanel.add(Box.createHorizontalStrut(10));
        rightPanel.add(logoutButton);

        headerPanel.add(leftPanel, BorderLayout.WEST);
        headerPanel.add(rightPanel, BorderLayout.EAST);

        return headerPanel;
    }

    private JPanel createRoomsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Header with title and add button
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);
        headerPanel.setBorder(new EmptyBorder(0, 0, 20, 0));

        JLabel titleLabel = new JLabel("Rooms Management");
        titleLabel.setFont(HotelManagementSystem.HEADING_FONT);

        StyledButton addButton = new StyledButton("Add New Room");
        addButton.setPreferredSize(new Dimension(150, 35));
        addButton.addActionListener(e -> {
            // Show add room dialog
            JOptionPane.showMessageDialog(this,
                    "Add room functionality would be implemented here.",
                    "Add Room",
                    JOptionPane.INFORMATION_MESSAGE);
        });

        headerPanel.add(titleLabel, BorderLayout.WEST);
        headerPanel.add(addButton, BorderLayout.EAST);

        panel.add(headerPanel, BorderLayout.NORTH);

        // Filter panel
        RoundedPanel filterPanel = new RoundedPanel(10);
        filterPanel.setBackground(Color.WHITE);
        filterPanel.setBorder(new CompoundBorder(
                new LineBorder(HotelManagementSystem.SECONDARY_COLOR, 1),
                new EmptyBorder(15, 15, 15, 15)
        ));
        filterPanel.setLayout(new GridLayout(1, 3, 10, 0));

        // Room type filter
        JPanel roomTypePanel = new JPanel(new BorderLayout());
        roomTypePanel.setOpaque(false);

        JLabel roomTypeLabel = new JLabel("Filter by Type");
        roomTypeLabel.setFont(HotelManagementSystem.NORMAL_FONT);

        String[] roomTypes = {"All Types", "Standard", "Deluxe", "Suite"};
        JComboBox<String> roomTypeCombo = new JComboBox<>(roomTypes);
        roomTypeCombo.setFont(HotelManagementSystem.NORMAL_FONT);

        roomTypePanel.add(roomTypeLabel, BorderLayout.NORTH);
        roomTypePanel.add(roomTypeCombo, BorderLayout.CENTER);

        // Room status filter
        JPanel statusPanel = new JPanel(new BorderLayout());
        statusPanel.setOpaque(false);

        JLabel statusLabel = new JLabel("Filter by Status");
        statusLabel.setFont(HotelManagementSystem.NORMAL_FONT);

        String[] statuses = {"All Statuses", "Available", "Occupied", "Maintenance"};
        JComboBox<String> statusCombo = new JComboBox<>(statuses);
        statusCombo.setFont(HotelManagementSystem.NORMAL_FONT);

        statusPanel.add(statusLabel, BorderLayout.NORTH);
        statusPanel.add(statusCombo, BorderLayout.CENTER);

        // Search field
        JPanel searchPanel = new JPanel(new BorderLayout());
        searchPanel.setOpaque(false);

        JLabel searchLabel = new JLabel("Search");
        searchLabel.setFont(HotelManagementSystem.NORMAL_FONT);

        JTextField searchField = new StyledTextField();
        searchField.setFont(HotelManagementSystem.NORMAL_FONT);

        searchPanel.add(searchLabel, BorderLayout.NORTH);
        searchPanel.add(searchField, BorderLayout.CENTER);

        // Add components to filter panel
        filterPanel.add(roomTypePanel);
        filterPanel.add(statusPanel);
        filterPanel.add(searchPanel);

        // Rooms table
        String[] columnNames = {"Room No.", "Type", "Status", "Price", "Last Cleaned", "Actions"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 5; // Only actions column is editable
            }
        };

        for (Room room : rooms) {
            model.addRow(new Object[]{
                    room.getRoomNumber(),
                    room.getRoomType(),
                    room.getStatus(),
                    room.getPrice(),
                    room.getLastCleaned(),
                    "Actions"
            });
        }

        JTable table = new JTable(model);
        table.setRowHeight(40);
        table.setFont(HotelManagementSystem.NORMAL_FONT);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        table.getTableHeader().setBackground(Color.WHITE);
        table.getTableHeader().setBorder(new MatteBorder(0, 0, 1, 0, new Color(229, 231, 235)));

        // Custom renderer for status column
        table.getColumnModel().getColumn(2).setCellRenderer(new StatusCellRenderer());

        // Custom renderer and editor for actions column
        table.getColumnModel().getColumn(5).setCellRenderer(new ButtonRenderer());
        table.getColumnModel().getColumn(5).setCellEditor(new ButtonEditor(new JCheckBox()));

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(null);

        // Content panel
        JPanel contentPanel = new JPanel(new BorderLayout(0, 20));
        contentPanel.setOpaque(false);
        contentPanel.add(filterPanel, BorderLayout.NORTH);
        contentPanel.add(scrollPane, BorderLayout.CENTER);

        panel.add(contentPanel, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createBookingsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Title
        JLabel titleLabel = new JLabel("Bookings Management");
        titleLabel.setFont(HotelManagementSystem.HEADING_FONT);
        titleLabel.setBorder(new EmptyBorder(0, 0, 20, 0));
        panel.add(titleLabel, BorderLayout.NORTH);

        // Filter panel
        RoundedPanel filterPanel = new RoundedPanel(10);
        filterPanel.setBackground(Color.WHITE);
        filterPanel.setBorder(new CompoundBorder(
                new LineBorder(HotelManagementSystem.SECONDARY_COLOR, 1),
                new EmptyBorder(15, 15, 15, 15)
        ));
        filterPanel.setLayout(new GridLayout(1, 3, 10, 0));

        // Booking status filter
        JPanel statusPanel = new JPanel(new BorderLayout());
        statusPanel.setOpaque(false);

        JLabel statusLabel = new JLabel("Filter by Status");
        statusLabel.setFont(HotelManagementSystem.NORMAL_FONT);

        String[] statuses = {"All Statuses", "Active", "Completed", "Cancelled"};
        JComboBox<String> statusCombo = new JComboBox<>(statuses);
        statusCombo.setFont(HotelManagementSystem.NORMAL_FONT);

        statusPanel.add(statusLabel, BorderLayout.NORTH);
        statusPanel.add(statusCombo, BorderLayout.CENTER);

        // Date filter
        JPanel datePanel = new JPanel(new BorderLayout());
        datePanel.setOpaque(false);

        JLabel dateLabel = new JLabel("Filter by Date");
        dateLabel.setFont(HotelManagementSystem.NORMAL_FONT);

        JDateChooser dateChooser = new JDateChooser();
        dateChooser.setDateFormatString("yyyy-MM-dd");
        dateChooser.setFont(HotelManagementSystem.NORMAL_FONT);

        datePanel.add(dateLabel, BorderLayout.NORTH);
        datePanel.add(dateChooser, BorderLayout.CENTER);

        // Search field
        JPanel searchPanel = new JPanel(new BorderLayout());
        searchPanel.setOpaque(false);

        JLabel searchLabel = new JLabel("Search");
        searchLabel.setFont(HotelManagementSystem.NORMAL_FONT);

        JTextField searchField = new StyledTextField();
        searchField.setFont(HotelManagementSystem.NORMAL_FONT);

        searchPanel.add(searchLabel, BorderLayout.NORTH);
        searchPanel.add(searchField, BorderLayout.CENTER);

        // Add components to filter panel
        filterPanel.add(statusPanel);
        filterPanel.add(datePanel);
        filterPanel.add(searchPanel);

        // Bookings table
        String[] columnNames = {"Booking ID", "Customer", "Room", "Check-in", "Check-out", "Status", "Amount", "Actions"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 7; // Only actions column is editable
            }
        };

        for (Booking booking : bookings) {
            model.addRow(new Object[]{
                    booking.getId(),
                    booking.getCustomerName(),
                    booking.getRoomNumber(),
                    booking.getCheckIn(),
                    booking.getCheckOut(),
                    booking.getStatus(),
                    booking.getTotalAmount(),
                    "Actions"
            });
        }

        JTable table = new JTable(model);
        table.setRowHeight(40);
        table.setFont(HotelManagementSystem.NORMAL_FONT);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        table.getTableHeader().setBackground(Color.WHITE);
        table.getTableHeader().setBorder(new MatteBorder(0, 0, 1, 0, new Color(229, 231, 235)));

        // Custom renderer for status column
        table.getColumnModel().getColumn(5).setCellRenderer(new StatusCellRenderer());

        // Custom renderer and editor for actions column
        table.getColumnModel().getColumn(7).setCellRenderer(new ButtonRenderer());
        table.getColumnModel().getColumn(7).setCellEditor(new ButtonEditor(new JCheckBox()));

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(null);

        // Content panel
        JPanel contentPanel = new JPanel(new BorderLayout(0, 20));
        contentPanel.setOpaque(false);
        contentPanel.add(filterPanel, BorderLayout.NORTH);
        contentPanel.add(scrollPane, BorderLayout.CENTER);

        panel.add(contentPanel, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createStaffPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Header with title and add button
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);
        headerPanel.setBorder(new EmptyBorder(0, 0, 20, 0));

        JLabel titleLabel = new JLabel("Staff Management");
        titleLabel.setFont(HotelManagementSystem.HEADING_FONT);

        StyledButton addButton = new StyledButton("Add Staff Member");
        addButton.setPreferredSize(new Dimension(180, 35));
        addButton.addActionListener(e -> {
            // Show add staff dialog
            JOptionPane.showMessageDialog(this,
                    "Add staff functionality would be implemented here.",
                    "Add Staff",
                    JOptionPane.INFORMATION_MESSAGE);
        });

        headerPanel.add(titleLabel, BorderLayout.WEST);
        headerPanel.add(addButton, BorderLayout.EAST);

        panel.add(headerPanel, BorderLayout.NORTH);

        // Filter panel
        RoundedPanel filterPanel = new RoundedPanel(10);
        filterPanel.setBackground(Color.WHITE);
        filterPanel.setBorder(new CompoundBorder(
                new LineBorder(HotelManagementSystem.SECONDARY_COLOR, 1),
                new EmptyBorder(15, 15, 15, 15)
        ));
        filterPanel.setLayout(new GridLayout(1, 3, 10, 0));

        // Position filter
        JPanel positionPanel = new JPanel(new BorderLayout());
        positionPanel.setOpaque(false);

        JLabel positionLabel = new JLabel("Filter by Position");
        positionLabel.setFont(HotelManagementSystem.NORMAL_FONT);

        String[] positions = {"All Positions", "Receptionist", "Housekeeper", "Maintenance"};
        JComboBox<String> positionCombo = new JComboBox<>(positions);
        positionCombo.setFont(HotelManagementSystem.NORMAL_FONT);

        positionPanel.add(positionLabel, BorderLayout.NORTH);
        positionPanel.add(positionCombo, BorderLayout.CENTER);

        // Status filter
        JPanel statusPanel = new JPanel(new BorderLayout());
        statusPanel.setOpaque(false);

        JLabel statusLabel = new JLabel("Filter by Status");
        statusLabel.setFont(HotelManagementSystem.NORMAL_FONT);

        String[] statuses = {"All Statuses", "On Duty", "Off Duty"};
        JComboBox<String> statusCombo = new JComboBox<>(statuses);
        statusCombo.setFont(HotelManagementSystem.NORMAL_FONT);

        statusPanel.add(statusLabel, BorderLayout.NORTH);
        statusPanel.add(statusCombo, BorderLayout.CENTER);

        // Search field
        JPanel searchPanel = new JPanel(new BorderLayout());
        searchPanel.setOpaque(false);

        JLabel searchLabel = new JLabel("Search");
        searchLabel.setFont(HotelManagementSystem.NORMAL_FONT);

        JTextField searchField = new StyledTextField();
        searchField.setFont(HotelManagementSystem.NORMAL_FONT);

        searchPanel.add(searchLabel, BorderLayout.NORTH);
        searchPanel.add(searchField, BorderLayout.CENTER);

        // Add components to filter panel
        filterPanel.add(positionPanel);
        filterPanel.add(statusPanel);
        filterPanel.add(searchPanel);

        // Staff table
        String[] columnNames = {"Staff ID", "Name", "Position", "Contact", "Status", "Actions"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 5; // Only actions column is editable
            }
        };

        for (Staff member : staff) {
            model.addRow(new Object[]{
                    member.getId(),
                    member.getName(),
                    member.getPosition(),
                    member.getContact(),
                    member.getStatus(),
                    "Actions"
            });
        }

        JTable table = new JTable(model);
        table.setRowHeight(40);
        table.setFont(HotelManagementSystem.NORMAL_FONT);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        table.getTableHeader().setBackground(Color.WHITE);
        table.getTableHeader().setBorder(new MatteBorder(0, 0, 1, 0, new Color(229, 231, 235)));

        // Custom renderer for status column
        table.getColumnModel().getColumn(4).setCellRenderer(new StatusCellRenderer());

        // Custom renderer and editor for actions column
        table.getColumnModel().getColumn(5).setCellRenderer(new ButtonRenderer());
        table.getColumnModel().getColumn(5).setCellEditor(new ButtonEditor(new JCheckBox()));

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(null);

        // Content panel
        JPanel contentPanel = new JPanel(new BorderLayout(0, 20));
        contentPanel.setOpaque(false);
        contentPanel.add(filterPanel, BorderLayout.NORTH);
        contentPanel.add(scrollPane, BorderLayout.CENTER);

        panel.add(contentPanel, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createLostFoundPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Header with title and add button
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);
        headerPanel.setBorder(new EmptyBorder(0, 0, 20, 0));

        JLabel titleLabel = new JLabel("Lost & Found Management");
        titleLabel.setFont(HotelManagementSystem.HEADING_FONT);

        StyledButton addButton = new StyledButton("Add Found Item");
        addButton.setPreferredSize(new Dimension(180, 35));
        addButton.addActionListener(e -> showAddItemDialog());

        headerPanel.add(titleLabel, BorderLayout.WEST);
        headerPanel.add(addButton, BorderLayout.EAST);

        panel.add(headerPanel, BorderLayout.NORTH);

        // Filter panel
        RoundedPanel filterPanel = new RoundedPanel(10);
        filterPanel.setBackground(Color.WHITE);
        filterPanel.setBorder(new CompoundBorder(
                new LineBorder(HotelManagementSystem.SECONDARY_COLOR, 1),
                new EmptyBorder(15, 15, 15, 15)
        ));
        filterPanel.setLayout(new GridLayout(1, 3, 10, 0));

        // Status filter
        JPanel statusPanel = new JPanel(new BorderLayout());
        statusPanel.setOpaque(false);

        JLabel statusLabel = new JLabel("Filter by Status");
        statusLabel.setFont(HotelManagementSystem.NORMAL_FONT);

        String[] statuses = {"All Statuses", "Unclaimed", "Claimed (Pending)", "Returned", "Rejected"};
        JComboBox<String> statusCombo = new JComboBox<>(statuses);
        statusCombo.setFont(HotelManagementSystem.NORMAL_FONT);

        statusPanel.add(statusLabel, BorderLayout.NORTH);
        statusPanel.add(statusCombo, BorderLayout.CENTER);

        // Room filter
        JPanel roomPanel = new JPanel(new BorderLayout());
        roomPanel.setOpaque(false);

        JLabel roomLabel = new JLabel("Filter by Room");
        roomLabel.setFont(HotelManagementSystem.NORMAL_FONT);

        JTextField roomField = new StyledTextField();
        roomField.setFont(HotelManagementSystem.NORMAL_FONT);

        roomPanel.add(roomLabel, BorderLayout.NORTH);
        roomPanel.add(roomField, BorderLayout.CENTER);

        // Search field
        JPanel searchPanel = new JPanel(new BorderLayout());
        searchPanel.setOpaque(false);

        JLabel searchLabel = new JLabel("Search Description");
        searchLabel.setFont(HotelManagementSystem.NORMAL_FONT);

        JTextField searchField = new StyledTextField();
        searchField.setFont(HotelManagementSystem.NORMAL_FONT);

        searchPanel.add(searchLabel, BorderLayout.NORTH);
        searchPanel.add(searchField, BorderLayout.CENTER);

        // Add components to filter panel
        filterPanel.add(statusPanel);
        filterPanel.add(roomPanel);
        filterPanel.add(searchPanel);

        // Lost items table
        String[] columnNames = {"Item ID", "Description", "Room", "Found Date", "Status", "Claims", "Actions"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 6; // Only actions column is editable
            }
        };

        for (LostItem item : lostItems) {
            model.addRow(new Object[]{
                    item.getId(),
                    item.getDescription(),
                    item.getRoomNumber(),
                    item.getFoundDate(),
                    item.getStatus(),
                    item.getClaims().size(),
                    "Actions"
            });
        }

        lostItemsTable = new JTable(model); // Assign to class field
        lostItemsTable.setRowHeight(40);
        lostItemsTable.setFont(HotelManagementSystem.NORMAL_FONT);
        lostItemsTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        lostItemsTable.getTableHeader().setBackground(Color.WHITE);
        lostItemsTable.getTableHeader().setBorder(new MatteBorder(0, 0, 1, 0, new Color(229, 231, 235)));

        // Custom renderer for status column
        lostItemsTable.getColumnModel().getColumn(4).setCellRenderer(new StatusCellRenderer());

        // Custom renderer and editor for actions column
        lostItemsTable.getColumnModel().getColumn(6).setCellRenderer(new ButtonRenderer());
        lostItemsTable.getColumnModel().getColumn(6).setCellEditor(new ButtonEditor(new JCheckBox()) {
            @Override
            protected void handleActionClick() {
                fireEditingStopped();

                LostItem selectedItem = lostItems.get(clickedRow);
                if (selectedItem.getClaims().size() > 0) {
                    showClaimsDialog(selectedItem);
                } else {
                    JOptionPane.showMessageDialog(null,
                            "No claims have been made for this item yet.",
                            "No Claims",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            }

            @Override
            protected void handleEditClick() {
                fireEditingStopped();

                LostItem selectedItem = lostItems.get(clickedRow);
                showItemDetailsDialog(selectedItem);
            }
        });

        JScrollPane scrollPane = new JScrollPane(lostItemsTable);
        scrollPane.setBorder(null);

        // Content panel
        JPanel contentPanel = new JPanel(new BorderLayout(0, 20));
        contentPanel.setOpaque(false);
        contentPanel.add(filterPanel, BorderLayout.NORTH);
        contentPanel.add(scrollPane, BorderLayout.CENTER);

        panel.add(contentPanel, BorderLayout.CENTER);

        return panel;
    }

    private void showAddItemDialog() {
        JDialog dialog = new JDialog(this, "Add Found Item", true);
        dialog.setSize(500, 400);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout());

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        formPanel.setBackground(Color.WHITE);

        // Description field
        JPanel descPanel = new JPanel(new BorderLayout());
        descPanel.setOpaque(false);
        descPanel.setBorder(new EmptyBorder(0, 0, 15, 0));

        JLabel descLabel = new JLabel("Item Description");
        descLabel.setFont(HotelManagementSystem.NORMAL_FONT);

        JTextArea descField = new JTextArea();
        descField.setFont(HotelManagementSystem.NORMAL_FONT);
        descField.setLineWrap(true);
        descField.setWrapStyleWord(true);
        descField.setBorder(new CompoundBorder(
                new LineBorder(HotelManagementSystem.SECONDARY_COLOR, 1),
                new EmptyBorder(8, 10, 8, 10)
        ));

        JScrollPane descScroll = new JScrollPane(descField);
        descScroll.setPreferredSize(new Dimension(0, 100));

        descPanel.add(descLabel, BorderLayout.NORTH);
        descPanel.add(descScroll, BorderLayout.CENTER);

        // Room number field
        JPanel roomPanel = new JPanel(new BorderLayout());
        roomPanel.setOpaque(false);
        roomPanel.setBorder(new EmptyBorder(0, 0, 15, 0));

        JLabel roomLabel = new JLabel("Room Number");
        roomLabel.setFont(HotelManagementSystem.NORMAL_FONT);

        JTextField roomField = new StyledTextField();

        roomPanel.add(roomLabel, BorderLayout.NORTH);
        roomPanel.add(roomField, BorderLayout.CENTER);

        // Image upload
        JPanel imagePanel = new JPanel(new BorderLayout());
        imagePanel.setOpaque(false);
        imagePanel.setBorder(new EmptyBorder(0, 0, 15, 0));

        JLabel imageLabel = new JLabel("Item Photo");
        imageLabel.setFont(HotelManagementSystem.NORMAL_FONT);

        JPanel uploadPanel = new JPanel(new BorderLayout());
        uploadPanel.setOpaque(false);

        JTextField imagePathField = new JTextField();
        imagePathField.setEditable(false);

        JButton browseButton = new JButton("Browse...");
        browseButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileFilter(new FileNameExtensionFilter("Image files", "jpg", "jpeg", "png", "gif"));

            int result = fileChooser.showOpenDialog(dialog);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                imagePathField.setText(selectedFile.getAbsolutePath());
            }
        });

        uploadPanel.add(imagePathField, BorderLayout.CENTER);
        uploadPanel.add(browseButton, BorderLayout.EAST);

        imagePanel.add(imageLabel, BorderLayout.NORTH);
        imagePanel.add(uploadPanel, BorderLayout.CENTER);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setOpaque(false);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> dialog.dispose());

        StyledButton saveButton = new StyledButton("Save Item");
        saveButton.addActionListener(e -> {
            String description = descField.getText().trim();
            String roomNumber = roomField.getText().trim();
            String imagePath = imagePathField.getText().trim();

            if (description.isEmpty() || roomNumber.isEmpty()) {
                JOptionPane.showMessageDialog(dialog,
                        "Please fill in all required fields.",
                        "Missing Information",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            // In a real app, you would save the image to a server/database
            // For this example, we'll just use the filename
            String imageFileName = "item_image.jpg";
            if (!imagePath.isEmpty()) {
                File file = new File(imagePath);
                imageFileName = file.getName();
            }

            // Create new lost item
            String newId = "L00" + (lostItems.size() + 1);
            LostItem newItem = new LostItem(newId, description, roomNumber, imageFileName);
            lostItems.add(newItem);

            // Update table - use lostItemsTable instead of table
            DefaultTableModel model = (DefaultTableModel) lostItemsTable.getModel();
            model.addRow(new Object[]{
                    newItem.getId(),
                    newItem.getDescription(),
                    newItem.getRoomNumber(),
                    newItem.getFoundDate(),
                    newItem.getStatus(),
                    0,
                    "Actions"
            });

            JOptionPane.showMessageDialog(dialog,
                    "Item added successfully. Notifications will be sent to travelers.",
                    "Item Added",
                    JOptionPane.INFORMATION_MESSAGE);

            dialog.dispose();
        });

        buttonPanel.add(cancelButton);
        buttonPanel.add(saveButton);

        // Add components to form panel
        formPanel.add(descPanel);
        formPanel.add(roomPanel);
        formPanel.add(imagePanel);
        formPanel.add(Box.createVerticalStrut(10));
        formPanel.add(buttonPanel);

        dialog.add(formPanel, BorderLayout.CENTER);
        dialog.setVisible(true);
    }

    private void showItemDetailsDialog(LostItem item) {
        JDialog dialog = new JDialog(this, "Item Details", true);
        dialog.setSize(500, 500);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout());

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        contentPanel.setBackground(Color.WHITE);

        // Item image
        JPanel imagePanel = new JPanel(new BorderLayout());
        imagePanel.setOpaque(false);
        imagePanel.setBorder(new EmptyBorder(0, 0, 15, 0));

        // In a real app, you would load the actual image
        JLabel imageLabel = new JLabel(createItemIcon(150));
        imageLabel.setHorizontalAlignment(JLabel.CENTER);
        imageLabel.setBorder(new LineBorder(HotelManagementSystem.SECONDARY_COLOR, 1));

        imagePanel.add(imageLabel, BorderLayout.CENTER);

        // Item details
        JPanel detailsPanel = new JPanel();
        detailsPanel.setLayout(new BoxLayout(detailsPanel, BoxLayout.Y_AXIS));
        detailsPanel.setOpaque(false);
        detailsPanel.setBorder(new EmptyBorder(0, 0, 15, 0));

        JLabel idLabel = new JLabel("Item ID: " + item.getId());
        idLabel.setFont(HotelManagementSystem.NORMAL_FONT);

        JLabel descLabel = new JLabel("Description: " + item.getDescription());
        descLabel.setFont(HotelManagementSystem.NORMAL_FONT);

        JLabel roomLabel = new JLabel("Found in Room: " + item.getRoomNumber());
        roomLabel.setFont(HotelManagementSystem.NORMAL_FONT);

        JLabel dateLabel = new JLabel("Found on: " + item.getFoundDate());
        dateLabel.setFont(HotelManagementSystem.NORMAL_FONT);

        JLabel statusLabel = new JLabel("Status: " + item.getStatus());
        statusLabel.setFont(HotelManagementSystem.NORMAL_FONT);

        detailsPanel.add(idLabel);
        detailsPanel.add(Box.createVerticalStrut(10));
        detailsPanel.add(descLabel);
        detailsPanel.add(Box.createVerticalStrut(10));
        detailsPanel.add(roomLabel);
        detailsPanel.add(Box.createVerticalStrut(10));
        detailsPanel.add(dateLabel);
        detailsPanel.add(Box.createVerticalStrut(10));
        detailsPanel.add(statusLabel);

        // Claims section
        JPanel claimsPanel = new JPanel(new BorderLayout());
        claimsPanel.setOpaque(false);
        claimsPanel.setBorder(new EmptyBorder(15, 0, 0, 0));

        JLabel claimsLabel = new JLabel("Claims (" + item.getClaims().size() + ")");
        claimsLabel.setFont(HotelManagementSystem.SUBHEADING_FONT);

        claimsPanel.add(claimsLabel, BorderLayout.NORTH);

        if (item.getClaims().size() > 0) {
            JButton viewClaimsButton = new JButton("View Claims");
            viewClaimsButton.addActionListener(e -> {
                dialog.dispose();
                showClaimsDialog(item);
            });
            claimsPanel.add(viewClaimsButton, BorderLayout.EAST);
        } else {
            JLabel noClaimsLabel = new JLabel("No claims have been made for this item yet.");
            noClaimsLabel.setFont(HotelManagementSystem.NORMAL_FONT);
            claimsPanel.add(noClaimsLabel, BorderLayout.CENTER);
        }

        // Close button
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setOpaque(false);

        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> dialog.dispose());

        buttonPanel.add(closeButton);

        // Add components to content panel
        contentPanel.add(imagePanel);
        contentPanel.add(detailsPanel);
        contentPanel.add(claimsPanel);
        contentPanel.add(Box.createVerticalStrut(20));
        contentPanel.add(buttonPanel);

        dialog.add(contentPanel, BorderLayout.CENTER);
        dialog.setVisible(true);
    }

    private void showClaimsDialog(LostItem item) {
        JDialog dialog = new JDialog(this, "Item Claims", true);
        dialog.setSize(600, 500);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout());

        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        contentPanel.setBackground(Color.WHITE);

        // Item info header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);
        headerPanel.setBorder(new EmptyBorder(0, 0, 15, 0));

        JLabel titleLabel = new JLabel("Claims for Item: " + item.getId());
        titleLabel.setFont(HotelManagementSystem.HEADING_FONT);

        JLabel descLabel = new JLabel(item.getDescription());
        descLabel.setFont(HotelManagementSystem.NORMAL_FONT);

        headerPanel.add(titleLabel, BorderLayout.NORTH);
        headerPanel.add(descLabel, BorderLayout.CENTER);

        // Claims table
        String[] columnNames = {"Claim ID", "Traveler", "Date", "Status", "Actions"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 4; // Only actions column is editable
            }
        };

        for (ItemClaim claim : item.getClaims()) {
            model.addRow(new Object[]{
                    claim.getId(),
                    claim.getTravelerName(),
                    claim.getClaimDate(),
                    claim.getStatus(),
                    "Actions"
            });
        }

        JTable claimsTable = new JTable(model);
        claimsTable.setRowHeight(40);
        claimsTable.setFont(HotelManagementSystem.NORMAL_FONT);
        claimsTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        claimsTable.getTableHeader().setBackground(Color.WHITE);
        claimsTable.getTableHeader().setBorder(new MatteBorder(0, 0, 1, 0, new Color(229, 231, 235)));

        // Custom renderer for status column
        claimsTable.getColumnModel().getColumn(3).setCellRenderer(new StatusCellRenderer());

        // Custom renderer and editor for actions column
        claimsTable.getColumnModel().getColumn(4).setCellRenderer(new ButtonRenderer());
        claimsTable.getColumnModel().getColumn(4).setCellEditor(new ButtonEditor(new JCheckBox()) {
            @Override
            protected void handleActionClick() {
                fireEditingStopped();

                ItemClaim selectedClaim = item.getClaims().get(clickedRow);
                showClaimDetailsDialog(item, selectedClaim);
            }

            @Override
            protected void handleEditClick() {
                fireEditingStopped();

                ItemClaim selectedClaim = item.getClaims().get(clickedRow);
                showClaimDetailsDialog(item, selectedClaim);
            }
        });

        JScrollPane scrollPane = new JScrollPane(claimsTable);
        scrollPane.setBorder(null);

        // Close button
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setOpaque(false);

        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> dialog.dispose());

        buttonPanel.add(closeButton);

        // Add components to content panel
        contentPanel.add(headerPanel, BorderLayout.NORTH);
        contentPanel.add(scrollPane, BorderLayout.CENTER);
        contentPanel.add(buttonPanel, BorderLayout.SOUTH);

        dialog.add(contentPanel, BorderLayout.CENTER);
        dialog.setVisible(true);
    }

    private void showClaimDetailsDialog(LostItem item, ItemClaim claim) {
        JDialog dialog = new JDialog(this, "Claim Details", true);
        dialog.setSize(500, 500);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout());

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        contentPanel.setBackground(Color.WHITE);

        // Item details
        JPanel itemPanel = new JPanel();
        itemPanel.setLayout(new BoxLayout(itemPanel, BoxLayout.Y_AXIS));
        itemPanel.setOpaque(false);
        itemPanel.setBorder(new EmptyBorder(0, 0, 15, 0));

        JLabel itemTitleLabel = new JLabel("Item Information");
        itemTitleLabel.setFont(HotelManagementSystem.SUBHEADING_FONT);

        JLabel itemIdLabel = new JLabel("Item ID: " + item.getId());
        itemIdLabel.setFont(HotelManagementSystem.NORMAL_FONT);

        JLabel itemDescLabel = new JLabel("Description: " + item.getDescription());
        itemDescLabel.setFont(HotelManagementSystem.NORMAL_FONT);

        JLabel itemRoomLabel = new JLabel("Found in Room: " + item.getRoomNumber());
        itemRoomLabel.setFont(HotelManagementSystem.NORMAL_FONT);

        itemPanel.add(itemTitleLabel);
        itemPanel.add(Box.createVerticalStrut(10));
        itemPanel.add(itemIdLabel);
        itemPanel.add(Box.createVerticalStrut(5));
        itemPanel.add(itemDescLabel);
        itemPanel.add(Box.createVerticalStrut(5));
        itemPanel.add(itemRoomLabel);

        // Claim details
        JPanel claimPanel = new JPanel();
        claimPanel.setLayout(new BoxLayout(claimPanel, BoxLayout.Y_AXIS));
        claimPanel.setOpaque(false);
        claimPanel.setBorder(new EmptyBorder(15, 0, 15, 0));

        JLabel claimTitleLabel = new JLabel("Claim Information");
        claimTitleLabel.setFont(HotelManagementSystem.SUBHEADING_FONT);

        JLabel claimIdLabel = new JLabel("Claim ID: " + claim.getId());
        claimIdLabel.setFont(HotelManagementSystem.NORMAL_FONT);

        JLabel travelerLabel = new JLabel("Traveler: " + claim.getTravelerName());
        travelerLabel.setFont(HotelManagementSystem.NORMAL_FONT);

        JLabel dateLabel = new JLabel("Claim Date: " + claim.getClaimDate());
        dateLabel.setFont(HotelManagementSystem.NORMAL_FONT);

        JLabel statusLabel = new JLabel("Status: " + claim.getStatus());
        statusLabel.setFont(HotelManagementSystem.NORMAL_FONT);

        claimPanel.add(claimTitleLabel);
        claimPanel.add(Box.createVerticalStrut(10));
        claimPanel.add(claimIdLabel);
        claimPanel.add(Box.createVerticalStrut(5));
        claimPanel.add(travelerLabel);
        claimPanel.add(Box.createVerticalStrut(5));
        claimPanel.add(dateLabel);
        claimPanel.add(Box.createVerticalStrut(5));
        claimPanel.add(statusLabel);

        // Traveler description
        JPanel descPanel = new JPanel(new BorderLayout());
        descPanel.setOpaque(false);
        descPanel.setBorder(new EmptyBorder(0, 0, 15, 0));

        JLabel descTitleLabel = new JLabel("Traveler's Description");
        descTitleLabel.setFont(HotelManagementSystem.SUBHEADING_FONT);

        JTextArea descArea = new JTextArea(claim.getTravelerDescription());
        descArea.setFont(HotelManagementSystem.NORMAL_FONT);
        descArea.setLineWrap(true);
        descArea.setWrapStyleWord(true);
        descArea.setEditable(false);
        descArea.setBorder(new CompoundBorder(
                new LineBorder(HotelManagementSystem.SECONDARY_COLOR, 1),
                new EmptyBorder(8, 10, 8, 10)
        ));

        JScrollPane descScroll = new JScrollPane(descArea);
        descScroll.setPreferredSize(new Dimension(0, 100));

        descPanel.add(descTitleLabel, BorderLayout.NORTH);
        descPanel.add(Box.createVerticalStrut(5), BorderLayout.CENTER);
        descPanel.add(descScroll, BorderLayout.SOUTH);

        // Action buttons
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        actionPanel.setOpaque(false);

        if (claim.getStatus().equals("Pending")) {
            StyledButton approveButton = new StyledButton("Approve Claim");
            approveButton.addActionListener(e -> {
                int result = JOptionPane.showConfirmDialog(dialog,
                        "Are you sure you want to approve this claim? The item will be marked as returned.",
                        "Confirm Approval",
                        JOptionPane.YES_NO_OPTION);

                if (result == JOptionPane.YES_OPTION) {
                    claim.setStatus("Approved");
                    item.setStatus("Returned");

                    // Update the table
                    DefaultTableModel model = (DefaultTableModel) lostItemsTable.getModel();
                    for (int i = 0; i < model.getRowCount(); i++) {
                        if (model.getValueAt(i, 0).equals(item.getId())) {
                            model.setValueAt(item.getStatus(), i, 4);
                            break;
                        }
                    }

                    JOptionPane.showMessageDialog(dialog,
                            "Claim has been approved. The item is now marked as returned.",
                            "Claim Approved",
                            JOptionPane.INFORMATION_MESSAGE);

                    dialog.dispose();
                }
            });

            StyledButton rejectButton = new StyledButton("Reject Claim");
            rejectButton.setOutline(true);
            rejectButton.addActionListener(e -> {
                int result = JOptionPane.showConfirmDialog(dialog,
                        "Are you sure you want to reject this claim?",
                        "Confirm Rejection",
                        JOptionPane.YES_NO_OPTION);

                if (result == JOptionPane.YES_OPTION) {
                    claim.setStatus("Rejected");

                    // If all claims are rejected, set item status back to Unclaimed
                    boolean allRejected = true;
                    for (ItemClaim c : item.getClaims()) {
                        if (!c.getStatus().equals("Rejected")) {
                            allRejected = false;
                            break;
                        }
                    }

                    if (allRejected) {
                        item.setStatus("Unclaimed");

                        // Update the table
                        DefaultTableModel model = (DefaultTableModel) lostItemsTable.getModel();
                        for (int i = 0; i < model.getRowCount(); i++) {
                            if (model.getValueAt(i, 0).equals(item.getId())) {
                                model.setValueAt(item.getStatus(), i, 4);
                                break;
                            }
                        }
                    }

                    JOptionPane.showMessageDialog(dialog,
                            "Claim has been rejected.",
                            "Claim Rejected",
                            JOptionPane.INFORMATION_MESSAGE);

                    dialog.dispose();
                }
            });

            actionPanel.add(approveButton);
            actionPanel.add(rejectButton);
        }

        // Close button
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setOpaque(false);

        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> dialog.dispose());

        buttonPanel.add(closeButton);

        // Add components to content panel
        contentPanel.add(itemPanel);
        contentPanel.add(claimPanel);
        contentPanel.add(descPanel);

        if (claim.getStatus().equals("Pending")) {
            contentPanel.add(Box.createVerticalStrut(10));
            contentPanel.add(actionPanel);
        }

        contentPanel.add(Box.createVerticalStrut(20));
        contentPanel.add(buttonPanel);

        dialog.add(contentPanel, BorderLayout.CENTER);
        dialog.setVisible(true);
    }

    private ImageIcon createBuildingIcon() {
        // Create a simple building icon
        int size = 30;
        BufferedImage image = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(HotelManagementSystem.PRIMARY_COLOR);

        // Draw a simple building
        g2d.fillRect(5, 10, 20, 15);
        g2d.fillRect(8, 5, 14, 5);

        // Draw windows
        g2d.setColor(Color.WHITE);
        g2d.fillRect(8, 13, 4, 4);
        g2d.fillRect(18, 13, 4, 4);
        g2d.fillRect(8, 19, 4, 4);
        g2d.fillRect(18, 19, 4, 4);

        g2d.dispose();
        return new ImageIcon(image);
    }

    private ImageIcon createTabIcon(String type) {
        // Create a simple icon for tabs
        int size = 16;
        BufferedImage image = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(HotelManagementSystem.PRIMARY_COLOR);

        if (type.equals("bed")) {
            // Draw a simple bed
            g2d.fillRect(2, 10, 12, 4);
            g2d.fillRect(3, 7, 10, 3);
            g2d.fillRect(1, 8, 2, 6);
            g2d.fillRect(13, 8, 2, 6);
        } else if (type.equals("booking")) {
            // Draw a calendar
            g2d.drawRect(3, 3, 10, 10);
            g2d.drawLine(3, 6, 13, 6);
            g2d.drawLine(6, 6, 6, 13);
            g2d.drawLine(10, 6, 10, 13);
        } else if (type.equals("staff")) {
            // Draw a person
            g2d.fillOval(6, 3, 4, 4);
            g2d.fillRect(4, 8, 8, 5);
        } else if (type.equals("search")) {
            // Draw a magnifying glass
            g2d.drawOval(3, 3, 7, 7);
            g2d.setStroke(new BasicStroke(2));
            g2d.drawLine(9, 9, 13, 13);
        }

        g2d.dispose();
        return new ImageIcon(image);
    }

    private ImageIcon createItemIcon(int size) {
        BufferedImage image = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(new Color(243, 244, 246)); // Gray-100
        g2d.fillRect(0, 0, size, size);

        g2d.setColor(new Color(156, 163, 175)); // Gray-400
        int iconSize = size / 3;
        g2d.fillRect((size - iconSize) / 2, (size - iconSize) / 2, iconSize, iconSize);

        g2d.dispose();
        return new ImageIcon(image);
    }

    // Inner classes for data models
    class Room {
        private String id;
        private String roomNumber;
        private String roomType;
        private String status;
        private String price;
        private String lastCleaned;

        public Room(String id, String roomNumber, String roomType, String status, String price, String lastCleaned) {
            this.id = id;
            this.roomNumber = roomNumber;
            this.roomType = roomType;
            this.status = status;
            this.price = price;
            this.lastCleaned = lastCleaned;
        }

        public String getId() { return id; }
        public String getRoomNumber() { return roomNumber; }
        public String getRoomType() { return roomType; }
        public String getStatus() { return status; }
        public String getPrice() { return price; }
        public String getLastCleaned() { return lastCleaned; }

        public void setStatus(String status) { this.status = status; }
    }

    class Booking {
        private String id;
        private String customerName;
        private String roomNumber;
        private String checkIn;
        private String checkOut;
        private String status;
        private String totalAmount;

        public Booking(String id, String customerName, String roomNumber, String checkIn, String checkOut, String status, String totalAmount) {
            this.id = id;
            this.customerName = customerName;
            this.roomNumber = roomNumber;
            this.checkIn = checkIn;
            this.checkOut = checkOut;
            this.status = status;
            this.totalAmount = totalAmount;
        }

        public String getId() { return id; }
        public String getCustomerName() { return customerName; }
        public String getRoomNumber() { return roomNumber; }
        public String getCheckIn() { return checkIn; }
        public String getCheckOut() { return checkOut; }
        public String getStatus() { return status; }
        public String getTotalAmount() { return totalAmount; }

        public void setStatus(String status) { this.status = status; }
    }

    class Staff {
        private String id;
        private String name;
        private String position;
        private String contact;
        private String status;

        public Staff(String id, String name, String position, String contact, String status) {
            this.id = id;
            this.name = name;
            this.position = position;
            this.contact = contact;
            this.status = status;
        }

        public String getId() { return id; }
        public String getName() { return name; }
        public String getPosition() { return position; }
        public String getContact() { return contact; }
        public String getStatus() { return status; }
    }

    // Custom cell renderers and editors
    class StatusCellRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

            if (value != null) {
                StatusBadge badge = new StatusBadge(value.toString());
                badge.setHorizontalAlignment(JLabel.CENTER);
                return badge;
            }

            return c;
        }
    }

    class ButtonRenderer extends JPanel implements TableCellRenderer {
        private JButton editButton;
        private JButton actionButton;

        public ButtonRenderer() {
            setLayout(new FlowLayout(FlowLayout.CENTER, 5, 0));
            setOpaque(false);

            editButton = new JButton("Edit");
            editButton.setFont(new Font("Arial", Font.PLAIN, 12));
            editButton.setPreferredSize(new Dimension(60, 25));

            actionButton = new JButton("Action");
            actionButton.setFont(new Font("Arial", Font.PLAIN, 12));
            actionButton.setPreferredSize(new Dimension(80, 25));

            add(editButton);
            add(actionButton);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            return this;
        }
    }

    class ButtonEditor extends DefaultCellEditor {
        protected JButton editButton;
        protected JButton actionButton;
        protected JPanel panel;
        protected int clickedRow;

        public ButtonEditor(JCheckBox checkBox) {
            super(checkBox);

            panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
            panel.setOpaque(false);

            editButton = new JButton("Edit");
            editButton.setFont(new Font("Arial", Font.PLAIN, 12));
            editButton.setPreferredSize(new Dimension(60, 25));
            editButton.addActionListener(e -> handleEditClick());

            actionButton = new JButton("View");
            actionButton.setFont(new Font("Arial", Font.PLAIN, 12));
            actionButton.setPreferredSize(new Dimension(60, 25));
            actionButton.addActionListener(e -> handleActionClick());

            panel.add(editButton);
            panel.add(actionButton);
        }

        protected void handleEditClick() {
            fireEditingStopped();
            JOptionPane.showMessageDialog(null, "Edit button clicked on row " + clickedRow);
        }

        protected void handleActionClick() {
            fireEditingStopped();
            JOptionPane.showMessageDialog(null, "Action button clicked on row " + clickedRow);
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            clickedRow = row;
            return panel;
        }

        @Override
        public Object getCellEditorValue() {
            return "Actions";
        }
    }

    class StatusBadge extends JLabel {
        public StatusBadge(String status) {
            super(status);
            setOpaque(true);
            setHorizontalAlignment(CENTER);
            setVerticalAlignment(CENTER);
            setBorder(new EmptyBorder(2, 8, 2, 8));
            setFont(new Font("Arial", Font.BOLD, 12));

            switch (status.toLowerCase()) {
                case "available":
                case "active":
                case "on duty":
                case "returned":
                case "approved":
                    setBackground(new Color(209, 250, 229)); // Green-100
                    setForeground(new Color(6, 95, 70));     // Green-800
                    break;

                case "occupied":
                case "claimed (pending)":
                case "pending":
                    setBackground(new Color(254, 243, 199)); // Yellow-100
                    setForeground(new Color(146, 64, 14));   // Yellow-800
                    break;

                case "maintenance":
                case "off duty":
                case "rejected":
                case "cancelled":
                    setBackground(new Color(254, 226, 226)); // Red-100
                    setForeground(new Color(185, 28, 28));   // Red-800
                    break;

                case "completed":
                case "unclaimed":
                default:
                    setBackground(new Color(219, 234, 254)); // Blue-100
                    setForeground(new Color(30, 64, 175));   // Blue-800
                    break;
            }
        }
    }

    class LostItem {
        private String id;
        private String description;
        private String roomNumber;
        private String imageFileName;
        private String foundDate;
        private String status;
        private List<ItemClaim> claims;

        public LostItem(String id, String description, String roomNumber, String imageFileName) {
            this.id = id;
            this.description = description;
            this.roomNumber = roomNumber;
            this.imageFileName = imageFileName;

            // Set default values
            this.foundDate = java.time.LocalDate.now().toString();
            this.status = "Unclaimed";
            this.claims = new ArrayList<>();
        }

        public String getId() { return id; }
        public String getDescription() { return description; }
        public String getRoomNumber() { return roomNumber; }
        public String getImageFileName() { return imageFileName; }
        public String getFoundDate() { return foundDate; }
        public String getStatus() { return status; }
        public List<ItemClaim> getClaims() { return claims; }

        public void setStatus(String status) { this.status = status; }

        public void addClaim(ItemClaim claim) {
            claims.add(claim);
            if (status.equals("Unclaimed")) {
                status = "Claimed (Pending)";
            }
        }
    }

    class ItemClaim {
        private String id;
        private String itemId;
        private String travelerName;
        private String travelerDescription;
        private String claimDate;
        private String status;

        public ItemClaim(String id, String itemId, String travelerName, String travelerDescription) {
            this.id = id;
            this.itemId = itemId;
            this.travelerName = travelerName;
            this.travelerDescription = travelerDescription;

            // Set default values
            this.claimDate = java.time.LocalDate.now().toString();
            this.status = "Pending";
        }

        public String getId() { return id; }
        public String getItemId() { return itemId; }
        public String getTravelerName() { return travelerName; }
        public String getTravelerDescription() { return travelerDescription; }
        public String getClaimDate() { return claimDate; }
        public String getStatus() { return status; }

        public void setStatus(String status) { this.status = status; }
    }

    // Utility classes
    class RoundedPanel extends JPanel {
        private int radius;

        public RoundedPanel(int radius) {
            super();
            this.radius = radius;
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, radius, radius);
            g2.dispose();
        }
    }

    class StyledButton extends JButton {
        private boolean isOutline = false;

        public StyledButton(String text) {
            super(text);
            setupStyle();
        }

        public void setOutline(boolean outline) {
            this.isOutline = outline;
            setupStyle();
        }

        private void setupStyle() {
            setFocusPainted(false);
            setBorderPainted(false);
            setContentAreaFilled(false);
            setFont(new Font("Arial", Font.BOLD, 14));
            setMargin(new Insets(8, 16, 8, 16));

            if (isOutline) {
                setForeground(HotelManagementSystem.PRIMARY_COLOR);
                setBorder(new LineBorder(HotelManagementSystem.PRIMARY_COLOR, 1));
            } else {
                setForeground(Color.WHITE);
                setBorder(null);
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            if (!isOutline) {
                g2.setColor(HotelManagementSystem.PRIMARY_COLOR);
                g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 5, 5);
            }

            super.paintComponent(g);
            g2.dispose();
        }
    }

    class StyledTextField extends JTextField {
        public StyledTextField() {
            super();
            setBorder(new CompoundBorder(
                    new LineBorder(HotelManagementSystem.SECONDARY_COLOR, 1),
                    new EmptyBorder(8, 10, 8, 10)
            ));
            setFont(HotelManagementSystem.NORMAL_FONT);
        }
    }

    class CustomTabbedPaneUI extends javax.swing.plaf.basic.BasicTabbedPaneUI {
        @Override
        protected void installDefaults() {
            super.installDefaults();
            tabAreaInsets = new Insets(0, 0, 0, 0);
            contentBorderInsets = new Insets(10, 0, 0, 0);
            tabInsets = new Insets(10, 15, 10, 15);
        }

        @Override
        protected void paintTabBorder(Graphics g, int tabPlacement, int tabIndex, int x, int y, int w, int h, boolean isSelected) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            if (isSelected) {
                g2.setColor(HotelManagementSystem.PRIMARY_COLOR);
                g2.fillRect(x, y + h - 2, w, 2);
            }

            g2.dispose();
        }

        @Override
        protected void paintContentBorder(Graphics g, int tabPlacement, int selectedIndex) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setColor(new Color(229, 231, 235)); // Gray-200
            g2.drawLine(contentBorderInsets.left, 0, tabPane.getWidth() - contentBorderInsets.right, 0);
            g2.dispose();
        }
    }

    // JDateChooser class (simplified version for this example)
    class JDateChooser extends JPanel {
        private JTextField dateField;
        private JButton calendarButton;
        private String dateFormatString;

        public JDateChooser() {
            setLayout(new BorderLayout());

            dateField = new JTextField();
            calendarButton = new JButton("📅");
            calendarButton.setPreferredSize(new Dimension(30, 0));

            add(dateField, BorderLayout.CENTER);
            add(calendarButton, BorderLayout.EAST);

            calendarButton.addActionListener(e -> {
                // In a real app, this would show a calendar dialog
                JOptionPane.showMessageDialog(this,
                        "Calendar dialog would be shown here.",
                        "Select Date",
                        JOptionPane.INFORMATION_MESSAGE);
            });
        }

        public void setDateFormatString(String formatString) {
            this.dateFormatString = formatString;
        }

        public void setFont(Font font) {
            if (dateField != null) {
                dateField.setFont(font);
            }
        }
    }
}
