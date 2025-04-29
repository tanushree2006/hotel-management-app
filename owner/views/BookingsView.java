package owner.views;

import owner.models.Booking;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class BookingsView extends JPanel {
    private JTable bookingsTable;
    private DefaultTableModel tableModel;
    private JTextField searchField;
    private JComboBox<String> statusFilter;
    private List<Booking> bookings;

    public BookingsView() {
        setLayout(new BorderLayout(20, 20));
        setBorder(new EmptyBorder(20, 20, 20, 20));
        setBackground(new Color(245, 247, 250)); // Modern light background

        initializeData();
        initComponents();
    }

    private void initializeData() {
        bookings = new ArrayList<>();
        bookings.add(new Booking("B001", "Aarav Sharma", "102", "2023-04-18", "2023-04-23", "Active", "₹10000"));
        bookings.add(new Booking("B002", "Priya Patel", "301", "2023-04-20", "2023-04-25", "Active", "₹25000"));
        bookings.add(new Booking("B003", "Rohan Mehra", "201", "2023-04-15", "2023-04-19", "Completed", "₹8000"));
        bookings.add(new Booking("B004", "Ananya Gupta", "105", "2023-04-22", "2023-04-24", "Upcoming", "₹65000"));
        bookings.add(new Booking("B005", "Vikram Singh", "302", "2023-04-10", "2023-04-15", "Completed", "₹12500"));
        bookings.add(new Booking("B006", "Kavya Nair", "401", "2023-04-25", "2023-04-30", "Upcoming", "₹25000"));
        bookings.add(new Booking("B007", "Saurabh Banerjee", "202", "2023-04-12", "2023-04-14", "Cancelled", "₹22240"));
        bookings.add(new Booking("B008", "Isha Reddy", "303", "2023-04-19", "2023-04-22", "Active", "₹45000"));
    }

    private void initComponents() {
        // --- Header Panel ---
        JPanel headerPanel = new JPanel(new BorderLayout(10, 10));
        headerPanel.setOpaque(true);
        headerPanel.setBackground(new Color(16, 46, 106));
        headerPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel("Bookings Management");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);

        JLabel subtitleLabel = new JLabel("Manage hotel bookings with style.");
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        subtitleLabel.setForeground(new Color(200, 210, 255));

        JPanel titleTextPanel = new JPanel(new GridLayout(2, 1));
        titleTextPanel.setOpaque(false);
        titleTextPanel.add(titleLabel);
        titleTextPanel.add(subtitleLabel);

        JButton addButton = new JButton("Add New Booking");
        addButton.setBackground(new Color(59, 130, 246));
        addButton.setForeground(Color.WHITE);
        addButton.setFocusPainted(false);
        addButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        addButton.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
        addButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        addButton.addActionListener(e -> showAddBookingDialog());

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

        headerPanel.add(titleTextPanel, BorderLayout.CENTER);
        headerPanel.add(addButton, BorderLayout.EAST);

        // --- Filters Panel (as cards) ---
        JPanel filterContainer = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
        filterContainer.setOpaque(false);

        statusFilter = new JComboBox<>(new String[]{"All Statuses", "Upcoming", "Active", "Completed", "Cancelled"});
        statusFilter.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        statusFilter.addActionListener(e -> filterBookings());

        searchField = new JTextField(15);
        searchField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        searchField.addActionListener(e -> filterBookings());

        JPanel statusCard = createCardPanel("Booking Status", statusFilter);
        JPanel searchCard = createCardPanel("Search Booking", searchField);

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
                new Object[]{"Booking ID", "Customer", "Room", "Check-in", "Check-out", "Status", "Amount", "Actions"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 7;
            }
            @Override
            public Class<?> getColumnClass(int column) {
                return column == 7 ? Object.class : String.class;
            }
        };

        bookingsTable = new JTable(tableModel) {
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
        bookingsTable.setRowHeight(45);
        bookingsTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        bookingsTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        bookingsTable.getTableHeader().setBackground(new Color(10, 55, 128));
        bookingsTable.getTableHeader().setForeground(Color.WHITE);
        bookingsTable.getTableHeader().setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(40, 90, 180)));
        bookingsTable.setSelectionBackground(new Color(220, 230, 250));

        bookingsTable.getColumnModel().getColumn(7).setCellRenderer(new ButtonRenderer());
        bookingsTable.getColumnModel().getColumn(7).setCellEditor(new ButtonEditor());

        JScrollPane scrollPane = new JScrollPane(bookingsTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        populateTable();

        // --- Layout ---
        add(headerPanel, BorderLayout.NORTH);
        add(filterContainer, BorderLayout.CENTER);
        add(tablePanel, BorderLayout.SOUTH);
        tablePanel.add(scrollPane, BorderLayout.CENTER);
    }

    private JPanel createCardPanel(String labelText, JComponent inputComponent) {
        JPanel card = new JPanel(new BorderLayout(0, 5));
        card.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(220, 220, 220), 1, true),
                new EmptyBorder(10, 15, 10, 15)
        ));
        card.setBackground(Color.WHITE);
        card.setPreferredSize(new Dimension(240, 70));

        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        card.add(label, BorderLayout.NORTH);
        card.add(inputComponent, BorderLayout.CENTER);

        return card;
    }

    private void populateTable() {
        tableModel.setRowCount(0);
        for (Booking booking : bookings) {
            tableModel.addRow(new Object[]{
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
    }

    // --- Action Buttons Renderer ---
    private class ButtonRenderer extends JPanel implements TableCellRenderer {
        public ButtonRenderer() {
            setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
            setOpaque(true);
            setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus, int row, int column) {
            removeAll();

            JButton viewButton = createStyledButton("View", new Color(59, 130, 246));
            JButton editButton = createStyledButton("Edit", new Color(16, 185, 129));
            JButton deleteButton = createStyledButton("Delete", new Color(239, 68, 68));

            add(viewButton);
            add(Box.createRigidArea(new Dimension(5, 0)));
            add(editButton);
            add(Box.createRigidArea(new Dimension(5, 0)));
            add(deleteButton);

            if (isSelected) {
                setBackground(table.getSelectionBackground());
            } else {
                setBackground(row % 2 == 0 ? new Color(245, 247, 250) : Color.WHITE);
            }

            return this;
        }
    }

    // --- Action Buttons Editor ---
    private class ButtonEditor extends DefaultCellEditor {
        private JPanel panel;
        private JButton viewButton, editButton, deleteButton;
        private int selectedRow;
        private String lastAction = null;

        public ButtonEditor() {
            super(new JTextField());
            panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
            panel.setBackground(Color.WHITE);

            viewButton = createStyledButton("View", new Color(102, 178, 255));
            editButton = createStyledButton("Edit", new Color(51, 153, 255));
            deleteButton = createStyledButton("Delete", new Color(255, 99, 71));

            viewButton.addActionListener(e -> {
                lastAction = "View";
                fireEditingStopped();
            });
            editButton.addActionListener(e -> {
                lastAction = "Edit";
                fireEditingStopped();
            });
            deleteButton.addActionListener(e -> {
                lastAction = "Delete";
                fireEditingStopped();
            });

            panel.add(viewButton);
            panel.add(Box.createRigidArea(new Dimension(5, 0)));
            panel.add(editButton);
            panel.add(Box.createRigidArea(new Dimension(5, 0)));
            panel.add(deleteButton);
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value,
                                                     boolean isSelected, int row, int column) {
            selectedRow = row;
            return panel;
        }

        @Override
        public Object getCellEditorValue() {
            // Perform the action here
            if (lastAction != null && selectedRow >= 0 && selectedRow < bookings.size()) {
                Booking booking = bookings.get(selectedRow);
                switch (lastAction) {
                    case "View":
                        showViewBookingDialog(booking);
                        break;
                    case "Edit":
                        showEditBookingDialog(booking);
                        break;
                    case "Delete":
                        int confirm = JOptionPane.showConfirmDialog(BookingsView.this,
                                "Are you sure you want to delete booking " + booking.getId() + "?", "Delete Confirmation",
                                JOptionPane.YES_NO_OPTION);
                        if (confirm == JOptionPane.YES_OPTION) {
                            bookings.remove(booking);
                            populateTable();
                        }
                        break;
                }
            }
            lastAction = null; // Reset for next edit
            return "";
        }
    }


    private JButton createStyledButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFocusPainted(false);
        button.setBackground(color);
        button.setForeground(Color.BLACK);
        button.setFont(new Font("Segoe UI", Font.BOLD, 12));
        button.setBorder(BorderFactory.createEmptyBorder(4, 12, 4, 12));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(70, 28));
        button.setMaximumSize(new Dimension(70, 28));
        return button;
    }

    private void performAction(String action, int row) {
        if (row < 0 || row >= bookings.size()) return;
        Booking booking = bookings.get(row);

        switch (action) {
            case "View":
                showViewBookingDialog(booking);
                break;
            case "Edit":
                showEditBookingDialog(booking);
                break;
            case "Delete":
                int confirm = JOptionPane.showConfirmDialog(this,
                        "Are you sure you want to delete booking " + booking.getId() + "?",
                        "Delete Confirmation", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    bookings.remove(booking);
                    populateTable();
                }
                break;
        }
    }

    private void showViewBookingDialog(Booking booking) {
        JDialog dialog = createDialog("Booking Details");

        JTextArea details = new JTextArea(
                "Booking ID: " + booking.getId() + "\n" +
                        "Customer Name: " + booking.getCustomerName() + "\n" +
                        "Room: " + booking.getRoomNumber() + "\n" +
                        "Check-in: " + booking.getCheckIn() + "\n" +
                        "Check-out: " + booking.getCheckOut() + "\n" +
                        "Status: " + booking.getStatus() + "\n" +
                        "Total Amount: " + booking.getTotalAmount()
        );
        details.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        details.setEditable(false);
        details.setBackground(Color.WHITE);

        dialog.add(new JScrollPane(details), BorderLayout.CENTER);

        JButton closeButton = createPrimaryButton("Close");
        closeButton.addActionListener(e -> dialog.dispose());

        dialog.add(closeButton, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }

    private void showEditBookingDialog(Booking booking) {
        JDialog dialog = createDialog("Edit Booking");

        JTextField customerNameField = new JTextField(booking.getCustomerName());
        JTextField roomNumberField = new JTextField(booking.getRoomNumber());
        JTextField checkInField = new JTextField(booking.getCheckIn());
        JTextField checkOutField = new JTextField(booking.getCheckOut());
        JComboBox<String> statusCombo = new JComboBox<>(new String[]{"Upcoming", "Active", "Completed", "Cancelled"});
        statusCombo.setSelectedItem(booking.getStatus());
        JTextField totalAmountField = new JTextField(booking.getTotalAmount());

        JPanel formPanel = createFormPanel(new String[]{
                "Customer Name:", "Room Number:", "Check-in Date:", "Check-out Date:", "Status:", "Total Amount:"
        }, new JComponent[]{
                customerNameField, roomNumberField, checkInField, checkOutField, statusCombo, totalAmountField
        });

        JButton saveButton = createPrimaryButton("Save Changes");
        saveButton.addActionListener(e -> {
            booking.setCustomerName(customerNameField.getText());
            booking.setRoomNumber(roomNumberField.getText());
            booking.setCheckIn(checkInField.getText());
            booking.setCheckOut(checkOutField.getText());
            booking.setStatus((String) statusCombo.getSelectedItem());
            booking.setTotalAmount(totalAmountField.getText());

            populateTable();
            dialog.dispose();
        });

        dialog.add(formPanel, BorderLayout.CENTER);
        dialog.add(saveButton, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }

    private JPanel createFormPanel(String[] labels, JComponent[] fields) {
        JPanel panel = new JPanel(new GridLayout(labels.length, 2, 10, 10));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        panel.setBackground(Color.WHITE);
        for (int i = 0; i < labels.length; i++) {
            JLabel label = new JLabel(labels[i]);
            label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            panel.add(label);
            panel.add(fields[i]);
        }
        return panel;
    }

    private JButton createPrimaryButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(new Color(59, 130, 246));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }

    private JDialog createDialog(String title) {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), title, true);
        dialog.setSize(400, 400);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout());
        dialog.getContentPane().setBackground(Color.WHITE);
        return dialog;
    }

    private void filterBookings() {
        String searchText = searchField.getText().toLowerCase();
        String statusText = statusFilter.getSelectedItem().toString();

        tableModel.setRowCount(0);

        for (Booking booking : bookings) {
            boolean matchesSearch = booking.getId().toLowerCase().contains(searchText) ||
                    booking.getCustomerName().toLowerCase().contains(searchText) ||
                    booking.getRoomNumber().toLowerCase().contains(searchText);

            boolean matchesStatus = statusText.equals("All Statuses") || booking.getStatus().equals(statusText);

            if (matchesSearch && matchesStatus) {
                tableModel.addRow(new Object[]{
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
        }
    }

    private void showAddBookingDialog() {
        JDialog dialog = createDialog("Add New Booking");

        JTextField customerNameField = new JTextField();
        JTextField roomNumberField = new JTextField();
        JTextField checkInField = new JTextField();
        JTextField checkOutField = new JTextField();
        JComboBox<String> statusCombo = new JComboBox<>(new String[]{"Upcoming", "Active", "Completed", "Cancelled"});
        JTextField totalAmountField = new JTextField();

        JPanel formPanel = createFormPanel(new String[]{
                "Customer Name:", "Room Number:", "Check-in Date:", "Check-out Date:", "Status:", "Total Amount:"
        }, new JComponent[]{customerNameField, roomNumberField, checkInField, checkOutField, statusCombo, totalAmountField});

        JButton addButton = createPrimaryButton("Add Booking");
        addButton.addActionListener(e -> {
            if (customerNameField.getText().isEmpty() || roomNumberField.getText().isEmpty() ||
                    checkInField.getText().isEmpty() || checkOutField.getText().isEmpty() ||
                    totalAmountField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Please fill all fields!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            String id = "B" + String.format("%03d", bookings.size() + 1);
            Booking booking = new Booking(
                    id,
                    customerNameField.getText(),
                    roomNumberField.getText(),
                    checkInField.getText(),
                    checkOutField.getText(),
                    (String) statusCombo.getSelectedItem(),
                    totalAmountField.getText()
            );
            bookings.add(booking);
            populateTable();
            dialog.dispose();
        });

        dialog.add(formPanel, BorderLayout.CENTER);
        dialog.add(addButton, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }
}
