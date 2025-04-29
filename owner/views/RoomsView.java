package owner.views;

import owner.models.Room;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class RoomsView extends JPanel {
    private JTable roomsTable;
    private DefaultTableModel tableModel;
    private JTextField searchField;
    private JComboBox<String> typeFilter;
    private JComboBox<String> statusFilter;
    private List<Room> rooms;

    public RoomsView() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        setBackground(new Color(249, 250, 251)); // Soft pastel background

        initializeData();
        initComponents();
    }

    private void initializeData() {
        rooms = new ArrayList<>();
        rooms.add(new Room("R101", "101", "Standard", "Available", "₹9840/night", "2023-04-21"));
        rooms.add(new Room("R102", "102", "Deluxe", "Occupied", "₹14760/night", "2023-04-20"));
        rooms.add(new Room("R103", "201", "Suite", "Maintenance", "₹20500/night", "2023-04-19"));
        rooms.add(new Room("R109", "401", "Presidential Suite", "Available", "₹41000/night", "2023-04-22"));
    }

    private void initComponents() {
        // Title Panel
        JPanel titleCard = new JPanel(new BorderLayout(10, 10));
        titleCard.setBackground(new Color(12, 42, 97));
        titleCard.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(238, 240, 243)),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        titleCard.setPreferredSize(new Dimension(1000, 120)); // give it bigger height for luxurious feel
        titleCard.setOpaque(true);

        JLabel titleLabel = new JLabel("Rooms Management");
        titleLabel.setFont(new Font("Serif", Font.BOLD, 34));
        titleLabel.setForeground(new Color(231, 236, 243));

        JLabel subtitleLabel = new JLabel("Manage your rooms, statuses, pricing, and maintenance with ease.");
        subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        subtitleLabel.setForeground(new Color(189, 195, 205));

        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setOpaque(false);
        textPanel.add(titleLabel);
        textPanel.add(Box.createRigidArea(new Dimension(0, 8)));
        textPanel.add(subtitleLabel);

        JButton addButton = new JButton("➕ Add New Room");
        addButton.setBackground(new Color(59, 130, 246));
        addButton.setForeground(new Color(233, 240, 250));
        addButton.setFont(new Font("Arial", Font.BOLD, 14));
        addButton.setFocusPainted(false);
        addButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        addButton.setBorder(BorderFactory.createLineBorder(new Color(59, 130, 246), 1, true));
        addButton.setPreferredSize(new Dimension(180, 40));
        addButton.setContentAreaFilled(true);
        addButton.addActionListener(e -> showAddRoomDialog());

        titleCard.add(textPanel, BorderLayout.CENTER);
        titleCard.add(addButton, BorderLayout.EAST);


        // Filter Panel
        JPanel filterPanel = new JPanel(new GridLayout(1, 3, 20, 0));
        filterPanel.setOpaque(false);
        filterPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        filterPanel.add(createFilterCard("Filter by Type", typeFilter = new JComboBox<>(new String[]{"All Types", "Standard", "Deluxe", "Suite", "Presidential Suite"})));
        filterPanel.add(createFilterCard("Filter by Status", statusFilter = new JComboBox<>(new String[]{"All Statuses", "Available", "Occupied", "Maintenance"})));
        filterPanel.add(createFilterCard("Search", searchField = new JTextField()));

        typeFilter.addActionListener(e -> filterRooms());
        statusFilter.addActionListener(e -> filterRooms());
        searchField.addActionListener(e -> filterRooms());

        // Table
        tableModel = new DefaultTableModel(new Object[]{"Room No.", "Type", "Status", "Price", "Last Cleaned", "Actions"}, 0) {
            public boolean isCellEditable(int row, int column) {
                return column == 5;
            }
        };

        roomsTable = new JTable(tableModel);
        roomsTable.setRowHeight(48);
        roomsTable.setFont(new Font("Arial", Font.PLAIN, 13));
        roomsTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        roomsTable.getTableHeader().setBackground(new Color(240, 242, 245));
        roomsTable.setGridColor(new Color(229, 231, 235));

        for (int i = 0; i < 6; i++) {
            roomsTable.getColumnModel().getColumn(i).setCellRenderer(new CenterRenderer());
        }

        roomsTable.getColumnModel().getColumn(5).setCellRenderer(new ButtonRenderer());
        roomsTable.getColumnModel().getColumn(5).setCellEditor(new ButtonEditor(new JCheckBox()));

        JScrollPane scrollPane = new JScrollPane(roomsTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(Color.WHITE);
        tablePanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(229, 231, 235)),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        // Add to main layout
        add(titleCard, BorderLayout.NORTH);
        add(filterPanel, BorderLayout.CENTER);
        add(tablePanel, BorderLayout.SOUTH);

        populateTable();
    }

    private void showAddRoomDialog() {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Add New Room", true);
        dialog.setSize(400, 350);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout(10, 10));
        dialog.getContentPane().setBackground(new Color (12, 21, 35));

        JPanel formPanel = new JPanel(new GridLayout(5, 2, 15, 15));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        formPanel.setBackground(Color.WHITE);

        JLabel roomNumberLabel = new JLabel("Room Number:");
        JTextField roomNumberField = new JTextField();

        JLabel typeLabel = new JLabel("Room Type:");
        JComboBox<String> typeCombo = new JComboBox<>(new String[]{"Standard", "Deluxe", "Suite", "Presidential Suite"});

        JLabel statusLabel = new JLabel("Status:");
        JComboBox<String> statusCombo = new JComboBox<>(new String[]{"Available", "Occupied", "Maintenance"});

        JLabel priceLabel = new JLabel("Price:");
        JTextField priceField = new JTextField();

        JLabel lastCleanedLabel = new JLabel("Last Cleaned:");
        JTextField lastCleanedField = new JTextField(new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date()));

        formPanel.add(roomNumberLabel);
        formPanel.add(roomNumberField);
        formPanel.add(typeLabel);
        formPanel.add(typeCombo);
        formPanel.add(statusLabel);
        formPanel.add(statusCombo);
        formPanel.add(priceLabel);
        formPanel.add(priceField);
        formPanel.add(lastCleanedLabel);
        formPanel.add(lastCleanedField);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(Color.WHITE);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> dialog.dispose());

        JButton saveButton = new JButton("Save Room");
        saveButton.setBackground(new Color(59, 130, 246));
        saveButton.setForeground(new Color(6, 14, 27));
        saveButton.setFocusPainted(false);
        saveButton.addActionListener(e -> {
            String roomNumber = roomNumberField.getText();
            String type = (String) typeCombo.getSelectedItem();
            String status = (String) statusCombo.getSelectedItem();
            String price = priceField.getText();
            String lastCleaned = lastCleanedField.getText();

            if (roomNumber.isEmpty() || price.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Please fill all fields.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Room newRoom = new Room("R" + (rooms.size() + 111), roomNumber, type, status, price, lastCleaned);
            rooms.add(newRoom);
            populateTable();
            dialog.dispose();
        });

        buttonPanel.add(cancelButton);
        buttonPanel.add(saveButton);

        dialog.add(formPanel, BorderLayout.CENTER);
        dialog.add(buttonPanel, BorderLayout.SOUTH);

        dialog.setVisible(true);
    }

    private void showEditRoomDialog(Room room) {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Edit Room", true);
        dialog.setSize(400, 350);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout(10, 10));
        dialog.getContentPane().setBackground(Color.WHITE);

        JPanel formPanel = new JPanel(new GridLayout(5, 2, 15, 15));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        formPanel.setBackground(Color.WHITE);

        JLabel roomNumberLabel = new JLabel("Room Number:");
        JTextField roomNumberField = new JTextField(room.getRoomNumber());

        JLabel typeLabel = new JLabel("Room Type:");
        JComboBox<String> typeCombo = new JComboBox<>(new String[]{"Standard", "Deluxe", "Suite", "Presidential Suite"});
        typeCombo.setSelectedItem(room.getType());

        JLabel statusLabel = new JLabel("Status:");
        JComboBox<String> statusCombo = new JComboBox<>(new String[]{"Available", "Occupied", "Maintenance"});
        statusCombo.setSelectedItem(room.getStatus());

        JLabel priceLabel = new JLabel("Price:");
        JTextField priceField = new JTextField(room.getPrice());

        JLabel lastCleanedLabel = new JLabel("Last Cleaned:");
        JTextField lastCleanedField = new JTextField(room.getLastCleaned());

        formPanel.add(roomNumberLabel);
        formPanel.add(roomNumberField);
        formPanel.add(typeLabel);
        formPanel.add(typeCombo);
        formPanel.add(statusLabel);
        formPanel.add(statusCombo);
        formPanel.add(priceLabel);
        formPanel.add(priceField);
        formPanel.add(lastCleanedLabel);
        formPanel.add(lastCleanedField);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(Color.WHITE);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> dialog.dispose());

        JButton saveButton = new JButton("Save Changes");
        saveButton.setBackground(new Color(59, 130, 246));
        saveButton.setForeground(Color.WHITE);
        saveButton.setFocusPainted(false);
        saveButton.addActionListener(e -> {
            room.setRoomNumber(roomNumberField.getText());
            room.setType((String) typeCombo.getSelectedItem());
            room.setStatus((String) statusCombo.getSelectedItem());
            room.setPrice(priceField.getText());
            room.setLastCleaned(lastCleanedField.getText());
            populateTable();
            dialog.dispose();
        });

        buttonPanel.add(cancelButton);
        buttonPanel.add(saveButton);

        dialog.add(formPanel, BorderLayout.CENTER);
        dialog.add(buttonPanel, BorderLayout.SOUTH);

        dialog.setVisible(true);
    }

    private void showDeleteRoomDialog(Room room) {
        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to delete room " + room.getRoomNumber() + "?",
                "Delete Confirmation",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (confirm == JOptionPane.YES_OPTION) {
            rooms.remove(room);
            populateTable();
        }
    }



    private JPanel createFilterCard(String label, JComponent component) {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(229, 231, 235)),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        JLabel lbl = new JLabel(label);
        lbl.setFont(new Font("Arial", Font.BOLD, 12));
        lbl.setForeground(new Color(107, 114, 128));

        panel.add(lbl, BorderLayout.NORTH);
        panel.add(component, BorderLayout.CENTER);

        return panel;
    }

    private void populateTable() {
        tableModel.setRowCount(0);
        for (Room room : rooms) {
            tableModel.addRow(new Object[]{
                    room.getRoomNumber(), room.getType(), room.getStatus(), room.getPrice(), room.getLastCleaned(), "Actions"
            });
        }
    }

    private void filterRooms() {
        String search = searchField.getText().toLowerCase();
        String type = typeFilter.getSelectedItem().toString();
        String status = statusFilter.getSelectedItem().toString();
        tableModel.setRowCount(0);

        for (Room room : rooms) {
            boolean matches = (room.getRoomNumber().toLowerCase().contains(search) || room.getType().toLowerCase().contains(search))
                    && (type.equals("All Types") || room.getType().equals(type))
                    && (status.equals("All Statuses") || room.getStatus().equals(status));

            if (matches) {
                tableModel.addRow(new Object[]{
                        room.getRoomNumber(), room.getType(), room.getStatus(), room.getPrice(), room.getLastCleaned(), "Actions"
                });
            }
        }
    }

    // Style primary buttons
    private void stylePrimaryButton(JButton button) {
        button.setBackground(new Color(59, 130, 246));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorder(BorderFactory.createLineBorder(new Color(59, 130, 246), 1, true));
    }

    // Center align table cells
    private static class CenterRenderer extends DefaultTableCellRenderer {
        CenterRenderer() {
            setHorizontalAlignment(SwingConstants.CENTER);
        }
    }

    // Custom ButtonRenderer
    private class ButtonRenderer extends JPanel implements javax.swing.table.TableCellRenderer {
        private final JButton editButton;
        private final JButton deleteButton;

        public ButtonRenderer() {
            setLayout(new FlowLayout(FlowLayout.CENTER, 5, 0));
            setOpaque(true);
            setBackground(Color.WHITE);

            editButton = createStyledButton("Edit", new Color(59, 130, 246));
            deleteButton = createStyledButton("Delete", new Color(239, 68, 68));

            // Set fixed size
            editButton.setPreferredSize(new Dimension(70, 30));
            deleteButton.setPreferredSize(new Dimension(70, 30));

            add(editButton);
            add(deleteButton);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            return this;
        }

        private JButton createStyledButton(String text, Color borderColor) {
            JButton button = new JButton(text);
            button.setFont(new Font("Arial", Font.BOLD, 12));
            button.setForeground(borderColor);
            button.setBackground(Color.WHITE);
            button.setBorder(BorderFactory.createLineBorder(borderColor, 1, true));
            button.setFocusPainted(false);
            button.setCursor(new Cursor(Cursor.HAND_CURSOR));
            button.setContentAreaFilled(false);
            return button;
        }
    }



    // ButtonEditor for Edit/Delete
    private class ButtonEditor extends DefaultCellEditor {
        private JPanel panel;
        private JButton editButton, deleteButton;
        private String action = "";
        private int currentRow;

        public ButtonEditor(JCheckBox checkBox) {
            super(checkBox);
            panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
            panel.setBackground(Color.WHITE);

            editButton = createStyledButton("Edit", new Color(59, 130, 246));
            deleteButton = createStyledButton("Delete", new Color(239, 68, 68));

            editButton.setPreferredSize(new Dimension(70, 30));
            deleteButton.setPreferredSize(new Dimension(70, 30));

            editButton.addActionListener(e -> {
                action = "Edit";
                fireEditingStopped();
            });

            deleteButton.addActionListener(e -> {
                action = "Delete";
                fireEditingStopped();
            });

            panel.add(editButton);
            panel.add(deleteButton);
        }

        private JButton createStyledButton(String text, Color borderColor) {
            JButton button = new JButton(text);
            button.setFont(new Font("Arial", Font.BOLD, 12));
            button.setForeground(borderColor);
            button.setBackground(Color.WHITE);
            button.setBorder(BorderFactory.createLineBorder(borderColor, 1, true));
            button.setFocusPainted(false);
            button.setCursor(new Cursor(Cursor.HAND_CURSOR));
            button.setContentAreaFilled(false);
            return button;
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            currentRow = row;
            return panel;
        }

        @Override
        public Object getCellEditorValue() {
            if ("Edit".equals(action)) {
                showEditRoomDialog(rooms.get(currentRow));
            } else if ("Delete".equals(action)) {
                showDeleteRoomDialog(rooms.get(currentRow));
            }
            return action;
        }
    }


}
