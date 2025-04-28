package owner.views;

import owner.models.Booking;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
        setBackground(new Color(245, 247, 250));

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
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);

        JLabel titleLabel = new JLabel("\u2728 Bookings Management");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 26));
        titleLabel.setForeground(new Color(33, 37, 41));

        JLabel subtitleLabel = new JLabel("Manage hotel bookings with style.");
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitleLabel.setForeground(new Color(108, 117, 125));

        JPanel titleTextPanel = new JPanel();
        titleTextPanel.setLayout(new BoxLayout(titleTextPanel, BoxLayout.Y_AXIS));
        titleTextPanel.setOpaque(false);
        titleTextPanel.add(titleLabel);
        titleTextPanel.add(subtitleLabel);

        JButton addButton = new JButton("+ Add New Booking");
        addButton.setBackground(new Color(0, 123, 255));
        addButton.setForeground(new Color(13, 20, 28));
        addButton.setFocusPainted(false);
        addButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        addButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        addButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        addButton.addActionListener(e -> showAddBookingDialog());

        headerPanel.add(titleTextPanel, BorderLayout.WEST);
        headerPanel.add(addButton, BorderLayout.EAST);

        JPanel filterPanel = new JPanel(new GridLayout(1, 3, 15, 0));
        filterPanel.setBorder(new EmptyBorder(10, 0, 10, 0));
        filterPanel.setBackground(new Color(255, 255, 255));

        statusFilter = new JComboBox<>(new String[]{"All Statuses", "Upcoming", "Active", "Completed", "Cancelled"});
        statusFilter.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        statusFilter.addActionListener(e -> filterBookings());

        searchField = new JTextField();
        searchField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        searchField.setBorder(BorderFactory.createTitledBorder("Search"));
        searchField.addActionListener(e -> filterBookings());

        JPanel dummyDateFilter = new JPanel();
        dummyDateFilter.setOpaque(false);

        filterPanel.add(statusFilter);
        filterPanel.add(dummyDateFilter);
        filterPanel.add(searchField);

        tableModel = new DefaultTableModel(new Object[]{"Booking ID", "Customer", "Room", "Check-in", "Check-out", "Status", "Amount", "Actions"}, 0) {
            public boolean isCellEditable(int row, int column) {
                return column == 7;
            }
        };

        bookingsTable = new JTable(tableModel);
        bookingsTable.setRowHeight(45);
        bookingsTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        bookingsTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        bookingsTable.getTableHeader().setBackground(new Color(230, 230, 250));
        bookingsTable.getColumnModel().getColumn(7).setCellRenderer(new ButtonRenderer());
        bookingsTable.getColumnModel().getColumn(7).setCellEditor(new ButtonEditor());

        bookingsTable.getTableHeader().setFont(new Font("Poppins", Font.BOLD, 14));
        bookingsTable.getTableHeader().setBackground(new Color(240, 240, 250));
        bookingsTable.setSelectionBackground(new Color(220, 230, 250));


        JScrollPane scrollPane = new JScrollPane(bookingsTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        populateTable();

        add(headerPanel, BorderLayout.NORTH);
        add(filterPanel, BorderLayout.CENTER);
        add(scrollPane, BorderLayout.SOUTH);
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

    // inside BookingsView.java

    private class ButtonRenderer extends JPanel implements TableCellRenderer {
        public ButtonRenderer() {
            setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
            setBackground(Color.WHITE);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus, int row, int column) {
            removeAll();

            JButton viewButton = createStyledButton("View", new Color(102, 178, 255));
            JButton editButton = createStyledButton("Edit", new Color(51, 153, 255));
            JButton deleteButton = createStyledButton("Delete", new Color(255, 99, 71));

            add(viewButton);
            add(Box.createRigidArea(new Dimension(5, 0)));
            add(editButton);
            add(Box.createRigidArea(new Dimension(5, 0)));
            add(deleteButton);

            if (isSelected) {
                setBackground(table.getSelectionBackground());
            } else {
                setBackground(Color.WHITE);
            }

            return this;
        }
    }






    private class ButtonEditor extends DefaultCellEditor {
        private JPanel panel;
        private JButton viewButton, editButton, deleteButton;
        private int selectedRow;

        public ButtonEditor() {
            super(new JTextField());
            panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
            panel.setBackground(Color.WHITE);

            viewButton = createStyledButton("View", new Color(102, 178, 255));
            editButton = createStyledButton("Edit", new Color(51, 153, 255));
            deleteButton = createStyledButton("Delete", new Color(255, 99, 71));

            viewButton.addActionListener(e -> handleAction("View"));
            editButton.addActionListener(e -> handleAction("Edit"));
            deleteButton.addActionListener(e -> handleAction("Delete"));

            panel.add(viewButton);
            panel.add(Box.createRigidArea(new Dimension(5, 0)));
            panel.add(editButton);
            panel.add(Box.createRigidArea(new Dimension(5, 0)));
            panel.add(deleteButton);
        }

        private void handleAction(String actionType) {
            Booking booking = bookings.get(selectedRow);

            if ("View".equals(actionType)) {
                showViewBookingDialog(booking);
            } else if ("Edit".equals(actionType)) {
                showEditBookingDialog(booking);
            } else if ("Delete".equals(actionType)) {
                int confirm = JOptionPane.showConfirmDialog(BookingsView.this,
                        "Are you sure you want to delete booking " + booking.getId() + "?", "Delete Confirmation",
                        JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    bookings.remove(booking);
                    populateTable();
                }
            }
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value,
                                                     boolean isSelected, int row, int column) {
            selectedRow = row;
            return panel;
        }

        @Override
        public Object getCellEditorValue() {
            return "";
        }
    }



    private JButton createStyledButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFocusPainted(false);
        button.setBackground(Color.WHITE);
        button.setForeground(color);
        button.setFont(new Font("Poppins", Font.BOLD, 12));
        button.setBorder(BorderFactory.createLineBorder(color, 2, true));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(60, 25));
        return button;
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

        JPanel formPanel = createFormPanel(new String[]{"Customer Name:", "Room Number:", "Check-in Date:", "Check-out Date:", "Status:", "Total Amount:"},
                new JComponent[]{customerNameField, roomNumberField, checkInField, checkOutField, statusCombo, totalAmountField});

        JButton addButton = createPrimaryButton("Add Booking");
        addButton.addActionListener(e -> {
            if (customerNameField.getText().isEmpty() || roomNumberField.getText().isEmpty() || checkInField.getText().isEmpty() ||
                    checkOutField.getText().isEmpty() || totalAmountField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Please fill all fields", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            String id = "B" + (bookings.size() + 9);
            bookings.add(new Booking(id, customerNameField.getText(), roomNumberField.getText(), checkInField.getText(), checkOutField.getText(), (String) statusCombo.getSelectedItem(), totalAmountField.getText()));
            populateTable();
            dialog.dispose();
        });

        dialog.add(formPanel, BorderLayout.CENTER);
        dialog.add(addButton, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }

    private JPanel createFormPanel(String[] labels, JComponent[] fields) {
        JPanel panel = new JPanel(new GridLayout(labels.length, 2, 10, 10));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        panel.setBackground(Color.WHITE);
        for (int i = 0; i < labels.length; i++) {
            panel.add(new JLabel(labels[i]));
            panel.add(fields[i]);
        }
        return panel;
    }

    private JButton createPrimaryButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(new Color(40, 167, 69));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        return button;
    }

    private JDialog createDialog(String title) {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), title, true);
        dialog.setSize(400, 500);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout());
        dialog.getContentPane().setBackground(new Color(245, 247, 250));
        return dialog;
    }
}

