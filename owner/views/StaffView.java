package owner.views;

import owner.models.Staff;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class StaffView extends JPanel {
    private JTable staffTable;
    private DefaultTableModel tableModel;
    private JTextField searchField;
    private JComboBox<String> positionFilter;
    private JComboBox<String> statusFilter;
    private List<Staff> staffList;

    public StaffView() {
        setLayout(new BorderLayout(20, 20));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        setBackground(new Color(245, 247, 250)); // Light background for modern look

        initializeData();
        initComponents();
    }

    private void initializeData() {
        staffList = new ArrayList<>();
        staffList.add(new Staff("S001", "Arjun Kumar", "Receptionist", "555-0101", "arjun.kumar@miravelle.com", "On Duty", "2022-01-15", "2023-04-21"));
        staffList.add(new Staff("S002", "Sneha Patel", "Housekeeper", "555-0102", "sneha.patel@miravelle.com", "On Duty", "2022-02-10", "2023-04-21"));
        staffList.add(new Staff("S003", "Rahul Singh", "Maintenance", "555-0103", "rahul.singh@miravelle.com", "Off Duty", "2022-03-05", "2023-04-20"));
        staffList.add(new Staff("S004", "Anjali Sharma", "Manager", "555-0104", "anjali.sharma@miravelle.com", "On Duty", "2021-11-10", "2023-04-21"));
        staffList.add(new Staff("S005", "Vikram Joshi", "Chef", "555-0105", "vikram.joshi@miravelle.com", "On Duty", "2022-01-20", "2023-04-21"));
        staffList.add(new Staff("S006", "Pooja Reddy", "Receptionist", "555-0106", "pooja.reddy@miravelle.com", "Off Duty", "2022-04-15", "2023-04-19"));
        staffList.add(new Staff("S007", "Suresh Nair", "Security", "555-0107", "suresh.nair@miravelle.com", "On Duty", "2022-02-28", "2023-04-21"));
        staffList.add(new Staff("S008", "Divya Iyer", "Housekeeper", "555-0108", "divya.iyer@miravelle.com", "On Leave", "2022-03-10", "2023-04-18"));
    }

    private void initComponents() {
        // --- Title Panel with Rich Styling ---
        JPanel titlePanel = new JPanel(new BorderLayout(10, 10));
        titlePanel.setOpaque(true);
        titlePanel.setBackground(new Color(16, 46, 106));
        titlePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel("Staff Management");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);

        JLabel subtitleLabel = new JLabel("Manage your hotel staff, shifts, and performance.");
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        subtitleLabel.setForeground(new Color(200, 210, 255));

        JPanel titleTextPanel = new JPanel(new GridLayout(2, 1));
        titleTextPanel.setOpaque(false);
        titleTextPanel.add(titleLabel);
        titleTextPanel.add(subtitleLabel);

        JButton addButton = new JButton("Add New Staff");
        addButton.setBackground(new Color(179, 193, 214));
        addButton.setForeground(new Color(9, 28, 57));
        addButton.setFocusPainted(false);
        addButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        addButton.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
        addButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        addButton.addActionListener(e -> showAddStaffDialog());

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

        // --- Filters Panel with Cards ---
        JPanel filterContainer = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
        filterContainer.setOpaque(false);

        positionFilter = new JComboBox<>(new String[]{"All Positions", "Receptionist", "Housekeeper", "Maintenance", "Manager", "Chef", "Security"});
        positionFilter.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        positionFilter.addActionListener(e -> filterStaff());

        statusFilter = new JComboBox<>(new String[]{"All Statuses", "On Duty", "Off Duty", "On Leave"});
        statusFilter.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        statusFilter.addActionListener(e -> filterStaff());

        searchField = new JTextField(15);
        searchField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        searchField.addActionListener(e -> filterStaff());

        // Helper method to create cards for filters and search
        JPanel positionCard = createCardPanel("Filter by Position", positionFilter);
        JPanel statusCard = createCardPanel("Filter by Status", statusFilter);
        JPanel searchCard = createCardPanel("Search", searchField);

        filterContainer.add(positionCard);
        filterContainer.add(statusCard);
        filterContainer.add(searchCard);

        // --- Table Panel with Styling ---
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setOpaque(false);
        tablePanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220)),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        // Table model with columns
        tableModel = new DefaultTableModel(
                new Object[]{"ID", "Name", "Position", "Contact", "Status", "Last Shift", "Actions"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 6; // Only actions column editable
            }
        };

        staffTable = new JTable(tableModel) {
            // Alternate row colors
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

        staffTable.setRowHeight(45);
        staffTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        staffTable.getTableHeader().setReorderingAllowed(false);
        staffTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        staffTable.getTableHeader().setBackground(new Color(28, 60, 115));
        staffTable.getTableHeader().setForeground(Color.WHITE);
        staffTable.getTableHeader().setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(16, 46, 106)));

        // Set column widths
        TableColumnModel columnModel = staffTable.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(70);
        columnModel.getColumn(1).setPreferredWidth(180);
        columnModel.getColumn(2).setPreferredWidth(130);
        columnModel.getColumn(3).setPreferredWidth(130);
        columnModel.getColumn(4).setPreferredWidth(90);
        columnModel.getColumn(5).setPreferredWidth(110);
        columnModel.getColumn(6).setPreferredWidth(160);

        // Set custom renderer and editor for action buttons
        staffTable.getColumnModel().getColumn(6).setCellRenderer(new ButtonRenderer());
        // In StaffView class, change this line (around line 166)
        staffTable.getColumnModel().getColumn(6).setCellEditor(new ButtonEditor()); // Remove JCheckBox parameter


        JScrollPane scrollPane = new JScrollPane(staffTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        tablePanel.add(scrollPane, BorderLayout.CENTER);

        // Populate initial table data
        populateTable();

        // --- Add all to main panel ---
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
        card.setPreferredSize(new Dimension(230, 70));

        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        card.add(label, BorderLayout.NORTH);
        card.add(inputComponent, BorderLayout.CENTER);

        return card;
    }

    private void populateTable() {
        tableModel.setRowCount(0);

        for (Staff staff : staffList) {
            tableModel.addRow(new Object[]{
                    staff.getId(),
                    staff.getName(),
                    staff.getPosition(),
                    staff.getContactNumber(),
                    staff.getStatus(),
                    staff.getLastShift(),
                    "Actions"
            });
        }
    }

    private void filterStaff() {
        String searchText = searchField.getText().toLowerCase();
        String positionText = (String) positionFilter.getSelectedItem();
        String statusText = (String) statusFilter.getSelectedItem();

        tableModel.setRowCount(0);

        for (Staff staff : staffList) {
            boolean matchesSearch = staff.getId().toLowerCase().contains(searchText) ||
                    staff.getName().toLowerCase().contains(searchText) ||
                    staff.getPosition().toLowerCase().contains(searchText) ||
                    staff.getContactNumber().toLowerCase().contains(searchText);

            boolean matchesPosition = positionText.equals("All Positions") || staff.getPosition().equals(positionText);
            boolean matchesStatus = statusText.equals("All Statuses") || staff.getStatus().equals(statusText);

            if (matchesSearch && matchesPosition && matchesStatus) {
                tableModel.addRow(new Object[]{
                        staff.getId(),
                        staff.getName(),
                        staff.getPosition(),
                        staff.getContactNumber(),
                        staff.getStatus(),
                        staff.getLastShift(),
                        "Actions"
                });
            }
        }
    }

    private void showAddStaffDialog() {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Add New Staff", true);
        dialog.setSize(400, 400);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridLayout(7, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel nameLabel = new JLabel("Name:");
        JTextField nameField = new JTextField();

        JLabel positionLabel = new JLabel("Position:");
        JComboBox<String> positionCombo = new JComboBox<>(new String[]{"Receptionist", "Housekeeper", "Maintenance", "Manager", "Chef", "Security"});

        JLabel contactLabel = new JLabel("Contact Number:");
        JTextField contactField = new JTextField();

        JLabel emailLabel = new JLabel("Email:");
        JTextField emailField = new JTextField();

        JLabel statusLabel = new JLabel("Status:");
        JComboBox<String> statusCombo = new JComboBox<>(new String[]{"On Duty", "Off Duty", "On Leave"});

        JLabel joinDateLabel = new JLabel("Join Date:");
        JTextField joinDateField = new JTextField(new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date()));

        JLabel lastShiftLabel = new JLabel("Last Shift:");
        JTextField lastShiftField = new JTextField(new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date()));

        formPanel.add(nameLabel);
        formPanel.add(nameField);
        formPanel.add(positionLabel);
        formPanel.add(positionCombo);
        formPanel.add(contactLabel);
        formPanel.add(contactField);
        formPanel.add(emailLabel);
        formPanel.add(emailField);
        formPanel.add(statusLabel);
        formPanel.add(statusCombo);
        formPanel.add(joinDateLabel);
        formPanel.add(joinDateField);
        formPanel.add(lastShiftLabel);
        formPanel.add(lastShiftField);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> dialog.dispose());

        JButton addButton = new JButton("Add Staff");
        addButton.setBackground(new Color(10, 55, 128));
        addButton.setForeground(Color.WHITE);
        addButton.setFocusPainted(false);
        addButton.addActionListener(e -> {
            String name = nameField.getText().trim();
            String position = (String) positionCombo.getSelectedItem();
            String contact = contactField.getText().trim();
            String email = emailField.getText().trim();
            String status = (String) statusCombo.getSelectedItem();
            String joinDate = joinDateField.getText().trim();
            String lastShift = lastShiftField.getText().trim();

            if (name.isEmpty() || contact.isEmpty() || email.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Please fill in all required fields", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String id = "S" + String.format("%03d", staffList.size() + 1);
            Staff newStaff = new Staff(id, name, position, contact, email, status, joinDate, lastShift);
            staffList.add(newStaff);
            populateTable();
            dialog.dispose();
        });

        buttonPanel.add(cancelButton);
        buttonPanel.add(addButton);

        dialog.add(formPanel, BorderLayout.CENTER);
        dialog.add(buttonPanel, BorderLayout.SOUTH);

        dialog.setVisible(true);
    }

    // --- Custom Button Renderer for Actions Column ---
    // Update ButtonRenderer class

        // Update ButtonRenderer class
        private class ButtonRenderer extends JPanel implements TableCellRenderer {
            private final JButton viewButton;
            private final JButton editButton;
            private final JButton deleteButton;

            public ButtonRenderer() {
                setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
                setOpaque(true);
                setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));

                viewButton = createButton("View", new Color(59, 130, 246));
                editButton = createButton("Edit", new Color(16, 185, 129));
                deleteButton = createButton("Del", new Color(239, 68, 68));

                add(viewButton);
                add(Box.createRigidArea(new Dimension(5, 0)));
                add(editButton);
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



        // --- Custom Button Editor for Actions Column ---
    // Update ButtonEditor class
    private class ButtonEditor extends DefaultCellEditor {
        private final JPanel panel;
        private final JButton viewButton;
        private final JButton editButton;
        private final JButton deleteButton;
        private int currentRow;

        public ButtonEditor() {
            super(new JCheckBox());
            setClickCountToStart(1);

            panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
            panel.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));

            viewButton = createButton("View", new Color(10, 55, 128));
            editButton = createButton("Edit", new Color(41, 152, 115));
            deleteButton = createButton("Del", new Color(220, 39, 39));

            viewButton.addActionListener(e -> {
                fireEditingStopped();
                viewAction(currentRow);
            });

            editButton.addActionListener(e -> {
                fireEditingStopped();
                editAction(currentRow);
            });

            deleteButton.addActionListener(e -> {
                fireEditingStopped();
                deleteAction(currentRow);
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
            currentRow = row;
            panel.setBackground(isSelected ? table.getSelectionBackground() :
                    (row % 2 == 0 ? new Color(245, 247, 250) : Color.WHITE));
            return panel;
        }

        @Override
        public Object getCellEditorValue() {
            return "Actions";
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

        // Action methods
        private void viewAction(int row) {
            Staff staff = staffList.get(row);
            JOptionPane.showMessageDialog(StaffView.this,
                    "ID: " + staff.getId() + "\nName: " + staff.getName() +
                            "\nPosition: " + staff.getPosition() + "\nStatus: " + staff.getStatus(),
                    "Staff Details",
                    JOptionPane.INFORMATION_MESSAGE);
        }

        private void editAction(int row) {
            Staff staff = staffList.get(row);
            JDialog editDialog = createEditDialog(staff, row);
            editDialog.setVisible(true);
        }

        private void deleteAction(int row) {
            int confirm = JOptionPane.showConfirmDialog(StaffView.this,
                    "Delete " + staffList.get(row).getName() + "?",
                    "Confirm Delete",
                    JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                staffList.remove(row);
                populateTable();
            }
        }

    }
    // Add this method in StaffView class (not inside ButtonEditor)
    private JDialog createEditDialog(Staff staff, int row) {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Edit Staff", true);
        dialog.setSize(400, 400);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridLayout(7, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Create fields with existing data
        JTextField nameField = new JTextField(staff.getName());
        JComboBox<String> positionCombo = new JComboBox<>(
                new String[]{"Receptionist", "Housekeeper", "Maintenance", "Manager", "Chef", "Security"});
        positionCombo.setSelectedItem(staff.getPosition());

        JTextField contactField = new JTextField(staff.getContactNumber());
        JTextField emailField = new JTextField(staff.getEmail());

        JComboBox<String> statusCombo = new JComboBox<>(
                new String[]{"On Duty", "Off Duty", "On Leave"});
        statusCombo.setSelectedItem(staff.getStatus());

        JTextField joinDateField = new JTextField(staff.getJoinDate());
        JTextField lastShiftField = new JTextField(staff.getLastShift());

        // Add components to form
        formPanel.add(new JLabel("Name:"));
        formPanel.add(nameField);
        formPanel.add(new JLabel("Position:"));
        formPanel.add(positionCombo);
        formPanel.add(new JLabel("Contact:"));
        formPanel.add(contactField);
        formPanel.add(new JLabel("Email:"));
        formPanel.add(emailField);
        formPanel.add(new JLabel("Status:"));
        formPanel.add(statusCombo);
        formPanel.add(new JLabel("Join Date:"));
        formPanel.add(joinDateField);
        formPanel.add(new JLabel("Last Shift:"));
        formPanel.add(lastShiftField);

        JButton saveButton = new JButton("Save Changes");
        saveButton.setBackground(new Color(59, 130, 246));
        saveButton.setForeground(Color.WHITE);
        saveButton.addActionListener(e -> {
            staff.setName(nameField.getText());
            staff.setPosition((String) positionCombo.getSelectedItem());
            staff.setContactNumber(contactField.getText());
            staff.setEmail(emailField.getText());
            staff.setStatus((String) statusCombo.getSelectedItem());
            staff.setJoinDate(joinDateField.getText());
            staff.setLastShift(lastShiftField.getText());

            populateTable();
            dialog.dispose();
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(saveButton);

        dialog.add(formPanel, BorderLayout.CENTER);
        dialog.add(buttonPanel, BorderLayout.SOUTH);

        return dialog;
    }

}

