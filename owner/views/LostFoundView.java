package owner.views;

import owner.models.LostItem;
import owner.models.Claim;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class LostFoundView extends JPanel {
    private JTable lostItemsTable;
    private DefaultTableModel tableModel;
    private JTextField searchField;
    private JComboBox<String> statusFilter;
    private List<LostItem> lostItems;

    public LostFoundView() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        setBackground(Color.WHITE);

        initializeData();
        initComponents();
    }

    private void initializeData() {
        // Sample data
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
        // Title panel
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setOpaque(false);

        JLabel titleLabel = new JLabel("Lost & Found Management");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));

        JLabel subtitleLabel = new JLabel("Manage lost items found in the hotel and their claims.");
        subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        subtitleLabel.setForeground(new Color(107, 114, 128));

        JButton addButton = new JButton("Add New Item");
        addButton.setBackground(new Color(59, 130, 246));
        addButton.setForeground(Color.WHITE);
        addButton.setFocusPainted(false);
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAddItemDialog();
            }
        });

        titlePanel.add(titleLabel, BorderLayout.NORTH);
        titlePanel.add(subtitleLabel, BorderLayout.CENTER);
        titlePanel.add(addButton, BorderLayout.EAST);

        // Filter panel
        JPanel filterPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        filterPanel.setOpaque(false);
        filterPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(229, 231, 235)),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        // Status filter
        JPanel statusPanel = new JPanel(new BorderLayout(0, 5));
        statusPanel.setOpaque(false);

        JLabel statusLabel = new JLabel("Filter by Status");
        statusLabel.setFont(new Font("Arial", Font.PLAIN, 12));

        statusFilter = new JComboBox<>(new String[]{"All Statuses", "Unclaimed", "Claimed (Pending)", "Claimed"});
        statusFilter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                filterItems();
            }
        });

        statusPanel.add(statusLabel, BorderLayout.NORTH);
        statusPanel.add(statusFilter, BorderLayout.CENTER);

        // Search panel
        JPanel searchPanel = new JPanel(new BorderLayout(0, 5));
        searchPanel.setOpaque(false);

        JLabel searchLabel = new JLabel("Search");
        searchLabel.setFont(new Font("Arial", Font.PLAIN, 12));

        searchField = new JTextField();
        searchField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                filterItems();
            }
        });

        searchPanel.add(searchLabel, BorderLayout.NORTH);
        searchPanel.add(searchField, BorderLayout.CENTER);

        filterPanel.add(statusPanel);
        filterPanel.add(searchPanel);

        // Table panel
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setOpaque(false);
        tablePanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(229, 231, 235)),
                BorderFactory.createEmptyBorder(0, 0, 0, 0)
        ));

        // Create table model
        tableModel = new DefaultTableModel(
                new Object[]{"ID", "Description", "Room", "Found Date", "Status", "Claims", "Actions"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 6; // Only actions column is editable
            }
        };

        // Create table
        lostItemsTable = new JTable(tableModel);
        lostItemsTable.setRowHeight(40);
        lostItemsTable.getTableHeader().setReorderingAllowed(false);
        lostItemsTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        lostItemsTable.getTableHeader().setBackground(new Color(243, 244, 246));
        lostItemsTable.getTableHeader().setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(229, 231, 235)));

        // Set column widths
        lostItemsTable.getColumnModel().getColumn(0).setPreferredWidth(60);
        lostItemsTable.getColumnModel().getColumn(1).setPreferredWidth(200);
        lostItemsTable.getColumnModel().getColumn(2).setPreferredWidth(60);
        lostItemsTable.getColumnModel().getColumn(3).setPreferredWidth(100);
        lostItemsTable.getColumnModel().getColumn(4).setPreferredWidth(120);
        lostItemsTable.getColumnModel().getColumn(5).setPreferredWidth(60);
        lostItemsTable.getColumnModel().getColumn(6).setPreferredWidth(100);

        // Add action buttons to the table
        lostItemsTable.getColumnModel().getColumn(6).setCellRenderer(new ButtonRenderer());
        lostItemsTable.getColumnModel().getColumn(6).setCellEditor(new ButtonEditor(new JCheckBox()));

        // Add table to scroll pane
        JScrollPane scrollPane = new JScrollPane(lostItemsTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        tablePanel.add(scrollPane, BorderLayout.CENTER);

        // Populate table
        populateTable();

        // Add components to main panel
        add(titlePanel, BorderLayout.NORTH);
        add(filterPanel, BorderLayout.CENTER);
        add(tablePanel, BorderLayout.SOUTH);
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
        String statusText = statusFilter.getSelectedItem().toString();

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
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
            }
        });

        JButton addButton = new JButton("Add Item");
        addButton.setBackground(new Color(59, 130, 246));
        addButton.setForeground(Color.WHITE);
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String description = descriptionField.getText();
                String room = roomField.getText();
                String image = imageField.getText();
                String date = dateField.getText();
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
            }
        });

        buttonPanel.add(cancelButton);
        buttonPanel.add(addButton);

        dialog.add(formPanel, BorderLayout.CENTER);
        dialog.add(buttonPanel, BorderLayout.SOUTH);

        dialog.setVisible(true);
    }

    // Custom button renderer for the actions column
    private class ButtonRenderer extends JPanel implements TableCellRenderer {
        private JButton viewButton;
        private JButton claimButton;
        private JButton deleteButton;

        public ButtonRenderer() {
            setLayout(new FlowLayout(FlowLayout.CENTER, 2, 0));
            setOpaque(true);

            viewButton = new JButton("View");
            viewButton.setPreferredSize(new Dimension(50, 25));
            viewButton.setFocusPainted(false);

            claimButton = new JButton("Claim");
            claimButton.setPreferredSize(new Dimension(50, 25));
            claimButton.setFocusPainted(false);

            deleteButton = new JButton("Del");
            deleteButton.setPreferredSize(new Dimension(50, 25));
            deleteButton.setFocusPainted(false);

            add(viewButton);
            add(claimButton);
            add(deleteButton);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            if (isSelected) {
                setBackground(table.getSelectionBackground());
            } else {
                setBackground(table.getBackground());
            }

            return this;
        }
    }

    // Custom button editor for the actions column
    private class ButtonEditor extends DefaultCellEditor {
        private JPanel panel;
        private JButton viewButton;
        private JButton claimButton;
        private JButton deleteButton;
        private String action = "";
        private int currentRow;

        public ButtonEditor(JCheckBox checkBox) {
            super(checkBox);

            panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 2, 0));

            viewButton = new JButton("View");
            viewButton.setPreferredSize(new Dimension(50, 25));
            viewButton.setFocusPainted(false);
            viewButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    action = "View";
                    fireEditingStopped();
                }
            });

            claimButton = new JButton("Claim");
            claimButton.setPreferredSize(new Dimension(50, 25));
            claimButton.setFocusPainted(false);
            claimButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    action = "Claim";
                    fireEditingStopped();
                }
            });

            deleteButton = new JButton("Del");
            deleteButton.setPreferredSize(new Dimension(50, 25));
            deleteButton.setFocusPainted(false);
            deleteButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    action = "Delete";
                    fireEditingStopped();
                }
            });

            panel.add(viewButton);
            panel.add(claimButton);
            panel.add(deleteButton);
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            currentRow = row;
            panel.setBackground(table.getSelectionBackground());
            return panel;
        }

        @Override
        public Object getCellEditorValue() {
            if (action.equals("View")) {
                showViewItemDialog(lostItems.get(currentRow));
            } else if (action.equals("Claim")) {
                showAddClaimDialog(lostItems.get(currentRow));
            } else if (action.equals("Delete")) {
                showDeleteItemDialog(lostItems.get(currentRow));
            }

            action = "";
            return action;
        }
    }

    private void showViewItemDialog(LostItem item) {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Lost Item Details", true);
        dialog.setSize(500, 400);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout());

        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Item details panel
        JPanel detailsPanel = new JPanel();
        detailsPanel.setLayout(new BoxLayout(detailsPanel, BoxLayout.Y_AXIS));
        detailsPanel.setOpaque(false);

        JLabel idLabel = new JLabel("Item ID: " + item.getId());
        idLabel.setFont(new Font("Arial", Font.BOLD, 14));
        idLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel descriptionLabel = new JLabel("Description: " + item.getDescription());
        descriptionLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        descriptionLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel roomLabel = new JLabel("Found in Room: " + item.getRoomNumber());
        roomLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        roomLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel dateLabel = new JLabel("Found Date: " + item.getFoundDate());
        dateLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        dateLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel statusLabel = new JLabel("Status: " + item.getStatus());
        statusLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        statusLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel imageLabel = new JLabel("Image: " + item.getImageFileName());
        imageLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        imageLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        detailsPanel.add(idLabel);
        detailsPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        detailsPanel.add(descriptionLabel);
        detailsPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        detailsPanel.add(roomLabel);
        detailsPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        detailsPanel.add(dateLabel);
        detailsPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        detailsPanel.add(statusLabel);
        detailsPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        detailsPanel.add(imageLabel);
        detailsPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Claims panel
        JPanel claimsPanel = new JPanel(new BorderLayout());
        claimsPanel.setOpaque(false);

        JLabel claimsLabel = new JLabel("Claims (" + item.getClaims().size() + ")");
        claimsLabel.setFont(new Font("Arial", Font.BOLD, 14));

        JPanel claimsListPanel = new JPanel();
        claimsListPanel.setLayout(new BoxLayout(claimsListPanel, BoxLayout.Y_AXIS));
        claimsListPanel.setOpaque(false);

        if (item.getClaims().isEmpty()) {
            JLabel noClaimsLabel = new JLabel("No claims have been made for this item.");
            noClaimsLabel.setFont(new Font("Arial", Font.ITALIC, 12));
            noClaimsLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            claimsListPanel.add(noClaimsLabel);
        } else {
            for (Claim claim : item.getClaims()) {
                JPanel claimPanel = new JPanel(new BorderLayout());
                claimPanel.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(229, 231, 235)),
                        BorderFactory.createEmptyBorder(10, 0, 10, 0)
                ));
                claimPanel.setOpaque(false);

                JPanel claimInfoPanel = new JPanel();
                claimInfoPanel.setLayout(new BoxLayout(claimInfoPanel, BoxLayout.Y_AXIS));
                claimInfoPanel.setOpaque(false);

                JLabel claimNameLabel = new JLabel("Traveler: " + claim.getTravelerName());
                claimNameLabel.setFont(new Font("Arial", Font.BOLD, 12));
                claimNameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

                JLabel claimDateLabel = new JLabel("Claim Date: " + claim.getClaimDate());
                claimDateLabel.setFont(new Font("Arial", Font.PLAIN, 11));
                claimDateLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

                JLabel claimDescLabel = new JLabel("Description: " + claim.getTravelerDescription());
                claimDescLabel.setFont(new Font("Arial", Font.PLAIN, 11));
                claimDescLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

                claimInfoPanel.add(claimNameLabel);
                claimInfoPanel.add(claimDateLabel);
                claimInfoPanel.add(claimDescLabel);

                JLabel claimStatusLabel = new JLabel(claim.getStatus());
                claimStatusLabel.setFont(new Font("Arial", Font.PLAIN, 11));

                if (claim.getStatus().equals("Pending")) {
                    claimStatusLabel.setForeground(new Color(234, 179, 8));
                    claimStatusLabel.setBorder(BorderFactory.createCompoundBorder(
                            BorderFactory.createLineBorder(new Color(234, 179, 8)),
                            BorderFactory.createEmptyBorder(2, 5, 2, 5)
                    ));
                } else if (claim.getStatus().equals("Approved")) {
                    claimStatusLabel.setForeground(new Color(34, 197, 94));
                    claimStatusLabel.setBorder(BorderFactory.createCompoundBorder(
                            BorderFactory.createLineBorder(new Color(34, 197, 94)),
                            BorderFactory.createEmptyBorder(2, 5, 2, 5)
                    ));
                } else {
                    claimStatusLabel.setForeground(new Color(239, 68, 68));
                    claimStatusLabel.setBorder(BorderFactory.createCompoundBorder(
                            BorderFactory.createLineBorder(new Color(239, 68, 68)),
                            BorderFactory.createEmptyBorder(2, 5, 2, 5)
                    ));
                }

                claimPanel.add(claimInfoPanel, BorderLayout.CENTER);
                claimPanel.add(claimStatusLabel, BorderLayout.EAST);

                claimsListPanel.add(claimPanel);
            }
        }

        JScrollPane claimsScrollPane = new JScrollPane(claimsListPanel);
        claimsScrollPane.setBorder(BorderFactory.createEmptyBorder());

        claimsPanel.add(claimsLabel, BorderLayout.NORTH);
        claimsPanel.add(claimsScrollPane, BorderLayout.CENTER);

        contentPanel.add(detailsPanel, BorderLayout.NORTH);
        contentPanel.add(claimsPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
            }
        });

        JButton addClaimButton = new JButton("Add Claim");
        addClaimButton.setBackground(new Color(59, 130, 246));
        addClaimButton.setForeground(Color.WHITE);
        addClaimButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
                showAddClaimDialog(item);
            }
        });

        buttonPanel.add(closeButton);
        buttonPanel.add(addClaimButton);

        dialog.add(contentPanel, BorderLayout.CENTER);
        dialog.add(buttonPanel, BorderLayout.SOUTH);

        dialog.setVisible(true);
    }

    private void showAddClaimDialog(LostItem item) {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Add Claim for " + item.getId(), true);
        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel nameLabel = new JLabel("Traveler Name:");
        JTextField nameField = new JTextField();

        JLabel descriptionLabel = new JLabel("Description:");
        JTextArea descriptionArea = new JTextArea();
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        JScrollPane descriptionScrollPane = new JScrollPane(descriptionArea);

        JLabel dateLabel = new JLabel("Claim Date:");
        JTextField dateField = new JTextField(new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date()));

        formPanel.add(nameLabel);
        formPanel.add(nameField);
        formPanel.add(descriptionLabel);
        formPanel.add(descriptionScrollPane);
        formPanel.add(dateLabel);
        formPanel.add(dateField);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
            }
        });

        JButton addButton = new JButton("Submit Claim");
        addButton.setBackground(new Color(59, 130, 246));
        addButton.setForeground(Color.WHITE);
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String description = descriptionArea.getText();
                String date = dateField.getText();

                if (name.isEmpty() || description.isEmpty()) {
                    JOptionPane.showMessageDialog(dialog, "Please fill in all required fields", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String id = "C" + String.format("%03d", getTotalClaimsCount() + 1);
                Claim newClaim = new Claim(id, item.getId(), name, description, date, "Pending");
                item.addClaim(newClaim);

                // Update item status if it was unclaimed
                if (item.getStatus().equals("Unclaimed")) {
                    item.setStatus("Claimed (Pending)");
                }

                populateTable();
                dialog.dispose();

                JOptionPane.showMessageDialog(dialog, "Claim submitted successfully. It will be reviewed by the staff.", "Claim Submitted", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        buttonPanel.add(cancelButton);
        buttonPanel.add(addButton);

        dialog.add(formPanel, BorderLayout.CENTER);
        dialog.add(buttonPanel, BorderLayout.SOUTH);

        dialog.setVisible(true);
    }

    private int getTotalClaimsCount() {
        int count = 0;
        for (LostItem item : lostItems) {
            count += item.getClaims().size();
        }
        return count;
    }

    private void showDeleteItemDialog(LostItem item) {
        int option = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to delete item " + item.getId() + "?",
                "Delete Item",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (option == JOptionPane.YES_OPTION) {
            lostItems.remove(item);
            populateTable();
        }
    }
}