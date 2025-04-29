package owner.views;

import owner.models.LostItem;
import owner.models.Claim;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class LostFoundView extends JPanel {
    private JTable lostItemsTable;
    private DefaultTableModel tableModel;
    private JTextField searchField;
    private JComboBox<String> statusFilter;
    private List<LostItem> lostItems;

    public LostFoundView() {
        setLayout(new BorderLayout(20, 20));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        setBackground(new Color(245, 247, 250)); // Light background for modern look

        initializeData();
        initComponents();
    }

    private void initializeData() {
        lostItems = new ArrayList<>();

        LostItem item1 = new LostItem("L001", "Black leather wallet with initials JD", "102", "wallet.jpg", "2023-04-15", "Claimed (Pending)");
        item1.addClaim(new Claim("C001", "L001", "John Doe", "Lost my wallet during checkout. It has my ID and credit cards.", "2023-04-16", "Pending"));

        LostItem item2 = new LostItem("L002", "Silver watch with brown leather strap", "205", "watch.jpg", "2023-04-17", "Unclaimed");

        LostItem item3 = new LostItem("L003", "Blue iPhone charger", "301", "charger.jpg", "2023-04-18", "Unclaimed");

        LostItem item4 = new LostItem("L004", "Gold earrings with pearl", "402", "earrings.jpg", "2023-04-14", "Claimed");
        item4.addClaim(new Claim("C002", "L004", "Emily Wilson", "I left my earrings in the bathroom. They're gold with pearls.", "2023-04-15", "Approved"));

        LostItem item5 = new LostItem("L005", "Prescription glasses with black frame", "103", "glasses.jpg", "2023-04-19", "Unclaimed");

        lostItems.add(item1);
        lostItems.add(item2);
        lostItems.add(item3);
        lostItems.add(item4);
        lostItems.add(item5);
    }

    private void initComponents() {
        // --- Title Panel ---
        JPanel titlePanel = new JPanel(new BorderLayout(10, 10));
        titlePanel.setOpaque(true);
        titlePanel.setBackground(new Color(16, 46, 106));
        titlePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel("Lost & Found Management");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);

        JLabel subtitleLabel = new JLabel("Manage lost items found in the hotel and their claims.");
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        subtitleLabel.setForeground(new Color(200, 210, 255));

        JPanel titleTextPanel = new JPanel(new GridLayout(2, 1));
        titleTextPanel.setOpaque(false);
        titleTextPanel.add(titleLabel);
        titleTextPanel.add(subtitleLabel);

        JButton addButton = new JButton("Add New Item");
        addButton.setBackground(new Color(16, 46, 106));
        addButton.setForeground(Color.WHITE);
        addButton.setFocusPainted(false);
        addButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        addButton.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
        addButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        addButton.addActionListener(e -> showAddItemDialog());

        // Rounded corners for button
        addButton.setUI(new javax.swing.plaf.basic.BasicButtonUI() {
            @Override
            public void paint(Graphics g, JComponent c) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(addButton.getBackground());
                g2.fillRoundRect(0, 0, c.getWidth(), c.getHeight(), 20, 20);
                super.paint(g, c);
                g2.dispose();
            }
        });

        titlePanel.add(titleTextPanel, BorderLayout.CENTER);
        titlePanel.add(addButton, BorderLayout.EAST);

        // --- Filters Panel ---
        JPanel filterContainer = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
        filterContainer.setOpaque(false);

        statusFilter = new JComboBox<>(new String[]{"All Statuses", "Unclaimed", "Claimed (Pending)", "Claimed"});
        statusFilter.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        statusFilter.addActionListener(e -> filterItems());

        searchField = new JTextField(15);
        searchField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        searchField.addActionListener(e -> filterItems());

        JPanel statusCard = createCardPanel("Filter by Status", statusFilter);
        JPanel searchCard = createCardPanel("Search", searchField);

        filterContainer.add(statusCard);
        filterContainer.add(searchCard);

        // --- Table Panel ---
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setOpaque(false);
        tablePanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220)),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        tableModel = new DefaultTableModel(
                new Object[]{"ID", "Description", "Room", "Found Date", "Status", "Claims", "Actions"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 6;
            }

            @Override
            public Class<?> getColumnClass(int column) {
                return column == 6 ? Object.class : String.class;
            }
        };

        lostItemsTable = new JTable(tableModel) {
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);
                if (!isRowSelected(row)) {
                    c.setBackground(row % 2 == 0 ? new Color(245, 247, 250) : Color.WHITE);
                } else {
                    c.setBackground(new Color(184, 207, 229));
                }
                return c;
            }
        };

        lostItemsTable.setRowHeight(45);
        lostItemsTable.getTableHeader().setReorderingAllowed(false);
        lostItemsTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        lostItemsTable.getTableHeader().setBackground(new Color(16, 46, 106));
        lostItemsTable.getTableHeader().setForeground(Color.WHITE);
        lostItemsTable.getTableHeader().setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(10, 55, 128)));

        TableColumnModel columnModel = lostItemsTable.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(60);
        columnModel.getColumn(1).setPreferredWidth(200);
        columnModel.getColumn(2).setPreferredWidth(60);
        columnModel.getColumn(3).setPreferredWidth(100);
        columnModel.getColumn(4).setPreferredWidth(120);
        columnModel.getColumn(5).setPreferredWidth(60);
        columnModel.getColumn(6).setPreferredWidth(150);

        lostItemsTable.getColumnModel().getColumn(6).setCellRenderer(new ButtonRenderer());
        lostItemsTable.getColumnModel().getColumn(6).setCellEditor(new ButtonEditor());

        JScrollPane scrollPane = new JScrollPane(lostItemsTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        tablePanel.add(scrollPane, BorderLayout.CENTER);

        populateTable();

        // --- Add to main panel ---
        add(titlePanel, BorderLayout.NORTH);
        add(filterContainer, BorderLayout.CENTER);
        add(tablePanel, BorderLayout.SOUTH);
    }

    private JPanel createCardPanel(String labelText, JComponent inputComponent) {
        JPanel card = new JPanel(new BorderLayout(0, 5));
        card.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(220, 220, 220), 1, true),
                BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));
        card.setBackground(Color.WHITE);
        card.setPreferredSize(new Dimension(250, 70));

        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        card.add(label, BorderLayout.NORTH);
        card.add(inputComponent, BorderLayout.CENTER);

        return card;
    }

    private void populateTable() {
        tableModel.setRowCount(0);

        for (LostItem item : lostItems) {
            tableModel.addRow(new Object[]{
                    item.getId(),
                    item.getDescription(),
                    item.getRoomNumber(),
                    item.getFoundDate(),
                    item.getStatus(),
                    item.getClaims().size(),
                    "Actions"
            });
        }
    }

    private void filterItems() {
        String searchText = searchField.getText().toLowerCase();
        String statusText = (String) statusFilter.getSelectedItem();

        tableModel.setRowCount(0);

        for (LostItem item : lostItems) {
            boolean matchesSearch = item.getId().toLowerCase().contains(searchText) ||
                    item.getDescription().toLowerCase().contains(searchText) ||
                    item.getRoomNumber().toLowerCase().contains(searchText);

            boolean matchesStatus = statusText.equals("All Statuses") || item.getStatus().equals(statusText);

            if (matchesSearch && matchesStatus) {
                tableModel.addRow(new Object[]{
                        item.getId(),
                        item.getDescription(),
                        item.getRoomNumber(),
                        item.getFoundDate(),
                        item.getStatus(),
                        item.getClaims().size(),
                        "Actions"
                });
            }
        }
    }

    private void showAddItemDialog() {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Add New Lost Item", true);
        dialog.setSize(400, 350);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel descriptionLabel = new JLabel("Description:");
        JTextField descriptionField = new JTextField();

        JLabel roomLabel = new JLabel("Room Number:");
        JTextField roomField = new JTextField();

        JLabel imageLabel = new JLabel("Image File:");
        JPanel imagePanel = new JPanel(new BorderLayout());
        JTextField imageField = new JTextField();
        JButton browseButton = new JButton("Browse");
        imagePanel.add(imageField, BorderLayout.CENTER);
        imagePanel.add(browseButton, BorderLayout.EAST);

        JLabel dateLabel = new JLabel("Found Date:");
        JTextField dateField = new JTextField(new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date()));

        JLabel statusLabel = new JLabel("Status:");
        JComboBox<String> statusCombo = new JComboBox<>(new String[]{"Unclaimed", "Claimed (Pending)", "Claimed"});

        formPanel.add(descriptionLabel);
        formPanel.add(descriptionField);
        formPanel.add(roomLabel);
        formPanel.add(roomField);
        formPanel.add(imageLabel);
        formPanel.add(imagePanel);
        formPanel.add(dateLabel);
        formPanel.add(dateField);
        formPanel.add(statusLabel);
        formPanel.add(statusCombo);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> dialog.dispose());

        JButton addButton = new JButton("Add Item");
        addButton.setBackground(new Color(10, 55, 128));
        addButton.setForeground(Color.WHITE);
        addButton.setFocusPainted(false);
        addButton.addActionListener(e -> {
            String description = descriptionField.getText().trim();
            String room = roomField.getText().trim();
            String image = imageField.getText().trim();
            String date = dateField.getText().trim();
            String status = (String) statusCombo.getSelectedItem();

            if (description.isEmpty() || room.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Please fill in all required fields", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String id = "L" + String.format("%03d", lostItems.size() + 1);
            LostItem newItem = new LostItem(id, description, room, image.isEmpty() ? "no_image.jpg" : image, date, status);
            lostItems.add(newItem);

            populateTable();
            dialog.dispose();
        });

        buttonPanel.add(cancelButton);
        buttonPanel.add(addButton);

        dialog.add(formPanel, BorderLayout.CENTER);
        dialog.add(buttonPanel, BorderLayout.SOUTH);

        dialog.setVisible(true);
    }

    // --- Button Renderer ---
    private class ButtonRenderer extends JPanel implements TableCellRenderer {
        private final JButton viewButton;
        private final JButton claimButton;
        private final JButton deleteButton;

        public ButtonRenderer() {
            setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
            setOpaque(true);
            setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));

            viewButton = createButton("View", new Color(59, 130, 246));
            claimButton = createButton("Claim", new Color(16, 185, 129));
            deleteButton = createButton("Del", new Color(239, 68, 68));

            add(viewButton);
            add(Box.createRigidArea(new Dimension(5, 0)));
            add(claimButton);
            add(Box.createRigidArea(new Dimension(5, 0)));
            add(deleteButton);
        }

        private JButton createButton(String text, Color bgColor) {
            JButton btn = new JButton(text);
            btn.setPreferredSize(new Dimension(70, 28));
            btn.setMaximumSize(new Dimension(70, 28));
            btn.setFocusPainted(false);
            btn.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            btn.setBackground(bgColor);
            btn.setForeground(Color.BLACK);
            btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            return btn;
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus, int row, int column) {
            if (isSelected) {
                setBackground(table.getSelectionBackground());
            } else {
                setBackground(row % 2 == 0 ? new Color(245, 247, 250) : Color.WHITE);
            }
            return this;
        }
    }

    // --- Button Editor ---
    private class ButtonEditor extends DefaultCellEditor {
        private final JPanel panel;
        private final JButton viewButton;
        private final JButton claimButton;
        private final JButton deleteButton;
        private int currentRow;
        private String action = "";

        public ButtonEditor() {
            super(new JCheckBox());
            setClickCountToStart(1);

            panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
            panel.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));

            viewButton = createButton("View", new Color(59, 130, 246));
            claimButton = createButton("Claim", new Color(16, 185, 129));
            deleteButton = createButton("Del", new Color(239, 68, 68));

            viewButton.addActionListener(e -> {
                fireEditingStopped();
                viewAction(currentRow);
            });

            claimButton.addActionListener(e -> {
                fireEditingStopped();
                claimAction(currentRow);
            });

            deleteButton.addActionListener(e -> {
                fireEditingStopped();
                deleteAction(currentRow);
            });

            panel.add(viewButton);
            panel.add(Box.createRigidArea(new Dimension(5, 0)));
            panel.add(claimButton);
            panel.add(Box.createRigidArea(new Dimension(5, 0)));
            panel.add(deleteButton);
        }

        private JButton createButton(String text, Color bgColor) {
            JButton btn = new JButton(text);
            btn.setPreferredSize(new Dimension(70, 28));
            btn.setFocusPainted(false);
            btn.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            btn.setBackground(bgColor);
            btn.setForeground(Color.WHITE);
            btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            return btn;
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value,
                                                     boolean isSelected, int row, int column) {
            currentRow = row;
            panel.setBackground(isSelected ? table.getSelectionBackground() :
                    (row % 2 == 0 ? new Color(245, 247, 250) : Color.WHITE));
            return panel;
        }

        @Override
        public Object getCellEditorValue() {
            return "Actions";
        }
    }

    // --- Action Methods ---
    private void viewAction(int row) {
        LostItem item = lostItems.get(row);
        StringBuilder claimsInfo = new StringBuilder();
        if (item.getClaims().isEmpty()) {
            claimsInfo.append("No claims yet.");
        } else {
            for (Claim claim : item.getClaims()) {
                claimsInfo.append("Claim ID: ").append(claim.getId()).append("\n")
                        .append("Name: ").append(claim.getTravelerName()).append("\n")
                        .append("Description: ").append(claim.getTravelerDescription()).append("\n")
                        .append("Date: ").append(claim.getClaimDate()).append("\n")
                        .append("Status: ").append(claim.getStatus()).append("\n\n");
            }
        }
        JOptionPane.showMessageDialog(this,
                "ID: " + item.getId() + "\nDescription: " + item.getDescription() +
                        "\nRoom: " + item.getRoomNumber() + "\nFound Date: " + item.getFoundDate() +
                        "\nStatus: " + item.getStatus() + "\n\nClaims:\n" + claimsInfo.toString(),
                "Lost Item Details",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void claimAction(int row) {
        LostItem item = lostItems.get(row);
        if ("Claimed".equals(item.getStatus())) {
            JOptionPane.showMessageDialog(this, "This item has already been claimed.", "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        // For demo, just show a message. You can implement claim dialog here.
        JOptionPane.showMessageDialog(this,
                "Claim request for item ID: " + item.getId(),
                "Claim Item",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void deleteAction(int row) {
        int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to delete item: " + lostItems.get(row).getDescription() + "?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            lostItems.remove(row);
            populateTable();
        }
    }
}
