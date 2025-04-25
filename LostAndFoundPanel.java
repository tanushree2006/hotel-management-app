import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import javax.swing.text.JTextComponent;

public class LostAndFoundPanel extends JPanel {
    private static List<LostItem> lostItems = new ArrayList<>();
    private static List<ItemClaim> itemClaims = new ArrayList<>();

    private JTable itemsTable;
    private DefaultTableModel tableModel;
    private JPanel detailsPanel;
    private boolean isOwnerView;
    private String currentUsername;
    private JTextField searchField;

    // Colors
    private final Color PRIMARY_COLOR = new Color(52, 152, 219);
    private final Color SECONDARY_COLOR = new Color(241, 243, 245);
    private final Color ACCENT_COLOR = new Color(46, 204, 113);
    private final Color DANGER_COLOR = new Color(231, 76, 60);

    public LostAndFoundPanel(boolean isOwnerView, String username) {
        this.isOwnerView = isOwnerView;
        this.currentUsername = username;
        setLayout(new BorderLayout(15, 15));
        setBorder(new EmptyBorder(20, 20, 20, 20));
        setBackground(SECONDARY_COLOR);

        if (lostItems.isEmpty()) {
            addSampleItems();
        }

        initializeUI();
    }

    private void initializeUI() {
        // Header Panel with Search
        JPanel headerPanel = createHeaderPanel();
        add(headerPanel, BorderLayout.NORTH);

        // Main Content Panel
        JPanel contentPanel = new JPanel(new BorderLayout(15, 15));
        contentPanel.setBackground(SECONDARY_COLOR);

        // Items Table Panel
        JPanel tablePanel = createTablePanel();
        contentPanel.add(tablePanel, BorderLayout.CENTER);

        // Details Panel (initially empty)
        detailsPanel = new JPanel();
        detailsPanel.setLayout(new BorderLayout());
        detailsPanel.setPreferredSize(new Dimension(350, 0));
        detailsPanel.setBorder(BorderFactory.createCompoundBorder(
                new MatteBorder(0, 1, 0, 0, new Color(220, 220, 220)),
                new EmptyBorder(15, 15, 15, 15)
        ));
        detailsPanel.setBackground(Color.WHITE);

        // Add placeholder for details panel
        JLabel placeholder = new JLabel(
                "<html><div style='text-align:center;width:300px;color:#7f8c8d;font-size:14px;'>" +
                        "Select an item to view details</div></html>",
                JLabel.CENTER
        );
        detailsPanel.add(placeholder, BorderLayout.CENTER);

        contentPanel.add(detailsPanel, BorderLayout.EAST);
        add(contentPanel, BorderLayout.CENTER);

        // Action Buttons Panel
        JPanel actionPanel = createActionPanel();
        add(actionPanel, BorderLayout.SOUTH);

        // Populate table with data
        populateTable();
    }

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout(15, 15));
        headerPanel.setBackground(SECONDARY_COLOR);
        headerPanel.setBorder(new EmptyBorder(0, 0, 15, 0));

        // Title
        JLabel titleLabel = new JLabel(
                "<html><div style='font-size:24px;color:#2c3e50;font-weight:bold;'>" +
                        "Lost & Found</div><div style='font-size:14px;color:#7f8c8d;'>" +
                        (isOwnerView ? "Manage lost items and claims" : "Report or claim lost items") +
                        "</div></html>"
        );

        // Search Panel
        JPanel searchPanel = new JPanel(new BorderLayout(10, 10));
        searchPanel.setBackground(SECONDARY_COLOR);

        searchField = new JTextField();
        searchField.setPreferredSize(new Dimension(250, 40));
        searchField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220)),
                BorderFactory.createEmptyBorder(5, 15, 5, 15)
        ));
        searchField.setFont(new Font("Arial", Font.PLAIN, 14));

        JButton searchButton = new JButton("Search");
        searchButton.setBackground(PRIMARY_COLOR);
        searchButton.setForeground(Color.WHITE);
        searchButton.setFont(new Font("Arial", Font.BOLD, 14));
        searchButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        searchButton.setFocusPainted(false);
        searchButton.addActionListener(e -> filterItems());

        searchPanel.add(searchField, BorderLayout.CENTER);
        searchPanel.add(searchButton, BorderLayout.EAST);

        headerPanel.add(titleLabel, BorderLayout.WEST);
        headerPanel.add(searchPanel, BorderLayout.EAST);

        return headerPanel;
    }

    private JPanel createTablePanel() {
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(SECONDARY_COLOR);

        // Table columns
        String[] columns = isOwnerView ?
                new String[]{"ID", "Item", "Found Location", "Date", "Status", "Actions"} :
                new String[]{"ID", "Item", "Found Location", "Date", "Status", "Actions"};

        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == columns.length - 1; // Only actions column is editable
            }
        };

        itemsTable = new JTable(tableModel);
        itemsTable.setRowHeight(60);
        itemsTable.setShowGrid(false);
        itemsTable.setIntercellSpacing(new Dimension(0, 5));
        itemsTable.setFont(new Font("Arial", Font.PLAIN, 14));
        itemsTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        itemsTable.getTableHeader().setBackground(new Color(240, 240, 240));
        itemsTable.getTableHeader().setReorderingAllowed(false);

        // Custom renderer for the actions column
        itemsTable.getColumnModel().getColumn(columns.length - 1).setCellRenderer(new ButtonRenderer());
        itemsTable.getColumnModel().getColumn(columns.length - 1).setCellEditor(new ButtonEditor(new JCheckBox()));

        JScrollPane scrollPane = new JScrollPane(itemsTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(Color.WHITE);

        tablePanel.add(scrollPane, BorderLayout.CENTER);
        return tablePanel;
    }

    private JPanel createActionPanel() {
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 15));
        actionPanel.setBackground(SECONDARY_COLOR);

        if (isOwnerView) {
            JButton addButton = createStyledButton("Add New Item", PRIMARY_COLOR);
            addButton.addActionListener(e -> showAddItemDialog());

            JButton claimsButton = createStyledButton("View Claims", new Color(155, 89, 182));
            claimsButton.addActionListener(e -> showClaimsDialog());

            actionPanel.add(addButton);
            actionPanel.add(claimsButton);
        } else {
            JButton reportButton = createStyledButton("Report Lost Item", DANGER_COLOR);
            reportButton.addActionListener(e -> showReportItemDialog());

            actionPanel.add(reportButton);
        }

        return actionPanel;
    }

    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBorder(BorderFactory.createEmptyBorder(12, 25, 12, 25));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }

    private void populateTable() {
        tableModel.setRowCount(0);

        for (LostItem item : lostItems) {
            if (!isOwnerView && !item.getStatus().equals("Unclaimed")) {
                continue;
            }

            Object[] rowData = new Object[]{
                    item.getId(),
                    item.getName(),
                    item.getRoomNumber(),
                    new SimpleDateFormat("MMM dd, yyyy").format(item.getDateFound()),
                    item.getStatus(),
                    "Details"
            };

            tableModel.addRow(rowData);
        }
    }

    private void filterItems() {
        String query = searchField.getText().toLowerCase();
        if (query.isEmpty()) {
            populateTable();
            return;
        }

        tableModel.setRowCount(0);
        for (LostItem item : lostItems) {
            if (item.getName().toLowerCase().contains(query) ||
                    item.getDescription().toLowerCase().contains(query) ||
                    item.getRoomNumber().toLowerCase().contains(query)) {

                Object[] rowData = new Object[]{
                        item.getId(),
                        item.getName(),
                        item.getRoomNumber(),
                        new SimpleDateFormat("MMM dd, yyyy").format(item.getDateFound()),
                        item.getStatus(),
                        "Details"
                };

                tableModel.addRow(rowData);
            }
        }
    }

    private void displayItemDetails(int row) {
        int itemId = (Integer) itemsTable.getValueAt(row, 0);
        LostItem item = lostItems.stream()
                .filter(i -> i.getId() == itemId)
                .findFirst()
                .orElse(null);

        if (item == null) return;

        detailsPanel.removeAll();

        // Main details container
        JPanel detailsContainer = new JPanel();
        detailsContainer.setLayout(new BoxLayout(detailsContainer, BoxLayout.Y_AXIS));
        detailsContainer.setBackground(Color.WHITE);

        // Item image
        JPanel imagePanel = new JPanel(new BorderLayout());
        imagePanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        imagePanel.setBorder(new EmptyBorder(0, 0, 20, 0));

        try {
            URL imgUrl = getClass().getResource("/images/" + item.getImageUrl());
            if (imgUrl != null) {
                ImageIcon icon = new ImageIcon(imgUrl);
                Image scaledImage = icon.getImage().getScaledInstance(300, 200, Image.SCALE_SMOOTH);
                JLabel imageLabel = new JLabel(new ImageIcon(scaledImage));
                imageLabel.setHorizontalAlignment(JLabel.CENTER);
                imagePanel.add(imageLabel, BorderLayout.CENTER);
            } else {
                JLabel noImageLabel = new JLabel("No Image Available");
                noImageLabel.setFont(new Font("Arial", Font.ITALIC, 14));
                noImageLabel.setForeground(Color.GRAY);
                noImageLabel.setHorizontalAlignment(JLabel.CENTER);
                imagePanel.add(noImageLabel, BorderLayout.CENTER);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Item details
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        infoPanel.setBorder(new EmptyBorder(0, 0, 20, 0));

        JLabel titleLabel = new JLabel(item.getName());
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setBorder(new EmptyBorder(0, 0, 10, 0));

        JTextArea descArea = new JTextArea(item.getDescription());
        descArea.setEditable(false);
        descArea.setLineWrap(true);
        descArea.setWrapStyleWord(true);
        descArea.setBackground(Color.WHITE);
        descArea.setFont(new Font("Arial", Font.PLAIN, 14));

        JPanel metaPanel = new JPanel(new GridLayout(0, 2, 10, 5));
        metaPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        addMetaItem(metaPanel, "Found Location:", item.getRoomNumber());
        addMetaItem(metaPanel, "Date Found:",
                new SimpleDateFormat("MMM dd, yyyy").format(item.getDateFound()));
        addMetaItem(metaPanel, "Status:", item.getStatus());

        infoPanel.add(titleLabel);
        infoPanel.add(descArea);
        infoPanel.add(Box.createVerticalStrut(15));
        infoPanel.add(metaPanel);

        // Action buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        buttonPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        if (!isOwnerView && item.getStatus().equals("Unclaimed")) {
            JButton claimButton = new JButton("Claim This Item");
            claimButton.setBackground(ACCENT_COLOR);
            claimButton.setForeground(Color.WHITE);
            claimButton.setFont(new Font("Arial", Font.BOLD, 14));
            claimButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
            claimButton.addActionListener(e -> showClaimDialog(item));
            buttonPanel.add(claimButton);
        }

        detailsContainer.add(imagePanel);
        detailsContainer.add(infoPanel);
        detailsContainer.add(buttonPanel);

        detailsPanel.add(new JScrollPane(detailsContainer), BorderLayout.CENTER);
        detailsPanel.revalidate();
        detailsPanel.repaint();
    }

    private void addMetaItem(JPanel panel, String label, String value) {
        JLabel lbl = new JLabel(label);
        lbl.setFont(new Font("Arial", Font.BOLD, 14));
        lbl.setForeground(new Color(100, 100, 100));

        JLabel val = new JLabel(value);
        val.setFont(new Font("Arial", Font.PLAIN, 14));

        panel.add(lbl);
        panel.add(val);
    }

    private void showAddItemDialog() {
        JDialog dialog = new JDialog();
        dialog.setTitle("Add New Lost Item");
        dialog.setModal(true);
        dialog.setSize(500, 600);
        dialog.setLocationRelativeTo(this);

        JPanel panel = new JPanel(new BorderLayout(15, 15));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Form fields
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));

        // Item name
        formPanel.add(createFormField("Item Name:", new JTextField(), "Enter item name"));

        // Description
        JTextArea descArea = new JTextArea(5, 20);
        descArea.setLineWrap(true);
        descArea.setWrapStyleWord(true);
        formPanel.add(createFormField("Description:", new JScrollPane(descArea), "Enter description"));

        // Location
        formPanel.add(createFormField("Found Location:", new JTextField(), "Where was it found?"));

        // Image upload
        JButton uploadButton = new JButton("Upload Image");
        uploadButton.setBackground(PRIMARY_COLOR);
        uploadButton.setForeground(Color.WHITE);
        formPanel.add(createFormField("Item Image:", uploadButton, ""));

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> dialog.dispose());

        JButton saveButton = new JButton("Save Item");
        saveButton.setBackground(ACCENT_COLOR);
        saveButton.setForeground(Color.WHITE);
        saveButton.addActionListener(e -> {
            // Save logic here
            JOptionPane.showMessageDialog(dialog, "Item saved successfully!");
            dialog.dispose();
            populateTable();
        });

        buttonPanel.add(cancelButton);
        buttonPanel.add(saveButton);

        panel.add(formPanel, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        dialog.add(panel);
        dialog.setVisible(true);
    }

    private JPanel createFormField(String label, JComponent field, String placeholder) {
        JPanel panel = new JPanel(new BorderLayout(5, 5));

        JLabel lbl = new JLabel(label);
        lbl.setFont(new Font("Arial", Font.BOLD, 14));

        if (field instanceof JTextComponent) {
            ((JTextComponent)field).setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(220, 220, 220)),
                    BorderFactory.createEmptyBorder(8, 12, 8, 12)
            ));
            ((JTextComponent)field).setFont(new Font("Arial", Font.PLAIN, 14));
            if (!placeholder.isEmpty()) {
                ((JTextComponent)field).setText(placeholder);
            }
        }

        panel.add(lbl, BorderLayout.NORTH);
        panel.add(field, BorderLayout.CENTER);

        return panel;
    }

    private void showReportItemDialog() {
        // Similar to add item dialog but for customers to report lost items
        showAddItemDialog(); // Reuse for now
    }

    private void showClaimDialog(LostItem item) {
        JDialog dialog = new JDialog();
        dialog.setTitle("Claim Item: " + item.getName());
        dialog.setModal(true);
        dialog.setSize(450, 400);
        dialog.setLocationRelativeTo(this);

        JPanel panel = new JPanel(new BorderLayout(15, 15));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Claim form
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));

        formPanel.add(new JLabel("<html><div style='font-size:16px;'>Please provide details to verify this is your item:</div></html>"));
        formPanel.add(Box.createVerticalStrut(20));

        // Description
        JTextArea descArea = new JTextArea(5, 20);
        descArea.setLineWrap(true);
        descArea.setWrapStyleWord(true);
        formPanel.add(createFormField("Description of Item:", new JScrollPane(descArea), "Describe the item to verify ownership"));

        // Contact info
        formPanel.add(createFormField("Contact Information:", new JTextField(), "Phone or email"));

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> dialog.dispose());

        JButton submitButton = new JButton("Submit Claim");
        submitButton.setBackground(ACCENT_COLOR);
        submitButton.setForeground(Color.WHITE);
        submitButton.addActionListener(e -> {
            // Claim submission logic
            JOptionPane.showMessageDialog(dialog, "Your claim has been submitted for review!");
            dialog.dispose();
            item.setStatus("Claimed");
            populateTable();
        });

        buttonPanel.add(cancelButton);
        buttonPanel.add(submitButton);

        panel.add(formPanel, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        dialog.add(panel);
        dialog.setVisible(true);
    }

    private void showClaimsDialog() {
        JDialog dialog = new JDialog();
        dialog.setTitle("Item Claims");
        dialog.setModal(true);
        dialog.setSize(800, 500);
        dialog.setLocationRelativeTo(this);

        JPanel panel = new JPanel(new BorderLayout(15, 15));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Claims table
        String[] columns = {"Claim ID", "Item", "Claimant", "Date", "Status", "Actions"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);

        JTable claimsTable = new JTable(model);
        // Configure table...

        // Populate table with claims data...

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));

        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> dialog.dispose());

        buttonPanel.add(closeButton);

        panel.add(new JScrollPane(claimsTable), BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        dialog.add(panel);
        dialog.setVisible(true);
    }

    private void addSampleItems() {
        lostItems.add(new LostItem(1, "Black Wallet", "Leather wallet with credit cards", "Lobby", new Date(), "wallet.jpg"));
        lostItems.add(new LostItem(2, "iPhone 12", "Blue case, no scratches", "Room 203", new Date(), "phone.jpg"));
        lostItems.add(new LostItem(3, "Gold Watch", "Rolex, serial number 12345", "Restaurant", new Date(), "watch.jpg"));
        lostItems.add(new LostItem(4, "Backpack", "Black Nike with laptop compartment", "Pool Area", new Date(), "backpack.jpg"));
    }

    // Custom cell renderer for buttons in table
    class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
            setBackground(PRIMARY_COLOR);
            setForeground(Color.WHITE);
            setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
            setFont(new Font("Arial", Font.BOLD, 12));
        }

        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus, int row, int column) {
            setText((value == null) ? "" : value.toString());
            return this;
        }
    }

    // Custom cell editor for buttons in table
    class ButtonEditor extends DefaultCellEditor {
        private String label;
        private JButton button;
        private boolean isPushed;

        public ButtonEditor(JCheckBox checkBox) {
            super(checkBox);
            button = new JButton();
            button.setOpaque(true);
            button.setBackground(PRIMARY_COLOR);
            button.setForeground(Color.WHITE);
            button.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
            button.setFont(new Font("Arial", Font.BOLD, 12));
            button.addActionListener(e -> fireEditingStopped());
        }

        public Component getTableCellEditorComponent(JTable table, Object value,
                                                     boolean isSelected, int row, int column) {
            label = (value == null) ? "" : value.toString();
            button.setText(label);
            isPushed = true;
            return button;
        }

        public Object getCellEditorValue() {
            if (isPushed) {
                // Handle button click
                displayItemDetails(itemsTable.getSelectedRow());
            }
            isPushed = false;
            return label;
        }
    }
}