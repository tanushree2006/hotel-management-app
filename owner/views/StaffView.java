package owner.views;

import owner.models.Staff;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        setBackground(Color.WHITE);

        initializeData();
        initComponents();
    }

    private void initializeData() {
        // Sample data
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
        // Title panel
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setOpaque(false);

        JLabel titleLabel = new JLabel("Staff Management");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));

        JLabel subtitleLabel = new JLabel("Manage your hotel staff, shifts, and performance.");
        subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        subtitleLabel.setForeground(new Color(107, 114, 128));

        JButton addButton = new JButton("Add New Staff");
        addButton.setBackground(new Color(59, 130, 246));
        addButton.setForeground(Color.WHITE);
        addButton.setFocusPainted(false);
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAddStaffDialog();
            }
        });

        titlePanel.add(titleLabel, BorderLayout.NORTH);
        titlePanel.add(subtitleLabel, BorderLayout.CENTER);
        titlePanel.add(addButton, BorderLayout.EAST);

        // Filter panel
        JPanel filterPanel = new JPanel(new GridLayout(1, 3, 10, 0));
        filterPanel.setOpaque(false);
        filterPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(229, 231, 235)),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        // Position filter
        JPanel positionPanel = new JPanel(new BorderLayout(0, 5));
        positionPanel.setOpaque(false);

        JLabel positionLabel = new JLabel("Filter by Position");
        positionLabel.setFont(new Font("Arial", Font.PLAIN, 12));

        positionFilter = new JComboBox<>(new String[]{"All Positions", "Receptionist", "Housekeeper", "Maintenance", "Manager", "Chef", "Security"});
        positionFilter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                filterStaff();
            }
        });

        positionPanel.add(positionLabel, BorderLayout.NORTH);
        positionPanel.add(positionFilter, BorderLayout.CENTER);

        // Status filter
        JPanel statusPanel = new JPanel(new BorderLayout(0, 5));
        statusPanel.setOpaque(false);

        JLabel statusLabel = new JLabel("Filter by Status");
        statusLabel.setFont(new Font("Arial", Font.PLAIN, 12));

        statusFilter = new JComboBox<>(new String[]{"All Statuses", "On Duty", "Off Duty", "On Leave"});
        statusFilter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                filterStaff();
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
                filterStaff();
            }
        });

        searchPanel.add(searchLabel, BorderLayout.NORTH);
        searchPanel.add(searchField, BorderLayout.CENTER);

        filterPanel.add(positionPanel);
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
                new Object[]{"ID", "Name", "Position", "Contact", "Status", "Last Shift", "Actions"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 6; // Only actions column is editable
            }
        };

        // Create table
        staffTable = new JTable(tableModel);
        staffTable.setRowHeight(40);
        staffTable.getTableHeader().setReorderingAllowed(false);
        staffTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        staffTable.getTableHeader().setBackground(new Color(243, 244, 246));
        staffTable.getTableHeader().setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(229, 231, 235)));

        // Set column widths
        staffTable.getColumnModel().getColumn(0).setPreferredWidth(60);
        staffTable.getColumnModel().getColumn(1).setPreferredWidth(150);
        staffTable.getColumnModel().getColumn(2).setPreferredWidth(100);
        staffTable.getColumnModel().getColumn(3).setPreferredWidth(100);
        staffTable.getColumnModel().getColumn(4).setPreferredWidth(80);
        staffTable.getColumnModel().getColumn(5).setPreferredWidth(100);
        staffTable.getColumnModel().getColumn(6).setPreferredWidth(100);

        // Add action buttons to the table
        staffTable.getColumnModel().getColumn(6).setCellRenderer(new ButtonRenderer());
        staffTable.getColumnModel().getColumn(6).setCellEditor(new ButtonEditor(new JCheckBox()));

        // Add table to scroll pane
        JScrollPane scrollPane = new JScrollPane(staffTable);
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
        String positionText = positionFilter.getSelectedItem().toString();
        String statusText = statusFilter.getSelectedItem().toString();

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
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
            }
        });

        JButton addButton = new JButton("Add Staff");
        addButton.setBackground(new Color(59, 130, 246));
        addButton.setForeground(Color.WHITE);
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String position = (String) positionCombo.getSelectedItem();
                String contact = contactField.getText();
                String email = emailField.getText();
                String status = (String) statusCombo.getSelectedItem();
                String joinDate = joinDateField.getText();
                String lastShift = lastShiftField.getText();

                if (name.isEmpty() || contact.isEmpty() || email.isEmpty()) {
                    JOptionPane.showMessageDialog(dialog, "Please fill in all required fields", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String id = "S" + String.format("%03d", staffList.size() + 1);
                Staff newStaff = new Staff(id, name, position, contact, email, status, joinDate, lastShift);
                staffList.add(newStaff);

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
        private JButton editButton;
        private JButton deleteButton;

        public ButtonRenderer() {
            setLayout(new FlowLayout(FlowLayout.CENTER, 2, 0));
            setOpaque(true);

            viewButton = new JButton("View");
            viewButton.setPreferredSize(new Dimension(50, 25));
            viewButton.setFocusPainted(false);

            editButton = new JButton("Edit");
            editButton.setPreferredSize(new Dimension(50, 25));
            editButton.setFocusPainted(false);

            deleteButton = new JButton("Del");
            deleteButton.setPreferredSize(new Dimension(50, 25));
            deleteButton.setFocusPainted(false);

            add(viewButton);
            add(editButton);
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
        private JButton editButton;
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

            editButton = new JButton("Edit");
            editButton.setPreferredSize(new Dimension(50, 25));
            editButton.setFocusPainted(false);
            editButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    action = "Edit";
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
            panel.add(editButton);
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
                showViewStaffDialog(staffList.get(currentRow));
            } else if (action.equals("Edit")) {
                showEditStaffDialog(staffList.get(currentRow));
            } else if (action.equals("Delete")) {
                showDeleteStaffDialog(staffList.get(currentRow));
            }

            action = "";
            return action;
        }
    }

    private void showViewStaffDialog(Staff staff) {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Staff Details", true);
        dialog.setSize(400, 350);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout());

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel idLabel = new JLabel("Staff ID: " + staff.getId());
        idLabel.setFont(new Font("Arial", Font.BOLD, 14));
        idLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel nameLabel = new JLabel("Name: " + staff.getName());
        nameLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        nameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel positionLabel = new JLabel("Position: " + staff.getPosition());
        positionLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        positionLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel contactLabel = new JLabel("Contact: " + staff.getContactNumber());
        contactLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        contactLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel emailLabel = new JLabel("Email: " + staff.getEmail());
        emailLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        emailLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel statusLabel = new JLabel("Status: " + staff.getStatus());
        statusLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        statusLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel joinDateLabel = new JLabel("Join Date: " + staff.getJoinDate());
        joinDateLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        joinDateLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lastShiftLabel = new JLabel("Last Shift: " + staff.getLastShift());
        lastShiftLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        lastShiftLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        contentPanel.add(idLabel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        contentPanel.add(nameLabel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        contentPanel.add(positionLabel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        contentPanel.add(contactLabel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        contentPanel.add(emailLabel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        contentPanel.add(statusLabel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        contentPanel.add(joinDateLabel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        contentPanel.add(lastShiftLabel);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
            }
        });

        JButton editButton = new JButton("Edit");
        editButton.setBackground(new Color(59, 130, 246));
        editButton.setForeground(Color.WHITE);
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
                showEditStaffDialog(staff);
            }
        });

        buttonPanel.add(closeButton);
        buttonPanel.add(editButton);

        dialog.add(contentPanel, BorderLayout.CENTER);
        dialog.add(buttonPanel, BorderLayout.SOUTH);

        dialog.setVisible(true);
    }

    private void showEditStaffDialog(Staff staff) {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Edit Staff", true);
        dialog.setSize(400, 400);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridLayout(7, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel nameLabel = new JLabel("Name:");
        JTextField nameField = new JTextField(staff.getName());

        JLabel positionLabel = new JLabel("Position:");
        JComboBox<String> positionCombo = new JComboBox<>(new String[]{"Receptionist", "Housekeeper", "Maintenance", "Manager", "Chef", "Security"});
        positionCombo.setSelectedItem(staff.getPosition());

        JLabel contactLabel = new JLabel("Contact Number:");
        JTextField contactField = new JTextField(staff.getContactNumber());

        JLabel emailLabel = new JLabel("Email:");
        JTextField emailField = new JTextField(staff.getEmail());

        JLabel statusLabel = new JLabel("Status:");
        JComboBox<String> statusCombo = new JComboBox<>(new String[]{"On Duty", "Off Duty", "On Leave"});
        statusCombo.setSelectedItem(staff.getStatus());

        JLabel joinDateLabel = new JLabel("Join Date:");
        JTextField joinDateField = new JTextField(staff.getJoinDate());

        JLabel lastShiftLabel = new JLabel("Last Shift:");
        JTextField lastShiftField = new JTextField(staff.getLastShift());

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
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
            }
        });

        JButton saveButton = new JButton("Save Changes");
        saveButton.setBackground(new Color(59, 130, 246));
        saveButton.setForeground(Color.WHITE);
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String position = (String) positionCombo.getSelectedItem();
                String contact = contactField.getText();
                String email = emailField.getText();
                String status = (String) statusCombo.getSelectedItem();
                String joinDate = joinDateField.getText();
                String lastShift = lastShiftField.getText();

                if (name.isEmpty() || contact.isEmpty() || email.isEmpty()) {
                    JOptionPane.showMessageDialog(dialog, "Please fill in all required fields", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                staff.setName(name);
                staff.setPosition(position);
                staff.setContactNumber(contact);
                staff.setEmail(email);
                staff.setStatus(status);
                staff.setJoinDate(joinDate);
                staff.setLastShift(lastShift);

                populateTable();
                dialog.dispose();
            }
        });

        buttonPanel.add(cancelButton);
        buttonPanel.add(saveButton);

        dialog.add(formPanel, BorderLayout.CENTER);
        dialog.add(buttonPanel, BorderLayout.SOUTH);

        dialog.setVisible(true);
    }

    private void showDeleteStaffDialog(Staff staff) {
        int option = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to delete staff member " + staff.getName() + "?",
                "Delete Staff",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (option == JOptionPane.YES_OPTION) {
            staffList.remove(staff);
            populateTable();
        }
    }
}