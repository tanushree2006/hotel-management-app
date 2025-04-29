package owner.views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class ReportsView extends JPanel {
    private JComboBox<String> reportTypeCombo;
    private JComboBox<String> timeRangeCombo;
    private JPanel reportPanel;
    private CardLayout cardLayout;

    public ReportsView() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        setBackground(Color.WHITE);

        initComponents();
    }

    private void initComponents() {
        // Title panel
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setOpaque(false);

        JLabel titleLabel = new JLabel("Reports & Analytics");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));

        JLabel subtitleLabel = new JLabel("View and generate reports for your hotel operations.");
        subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        subtitleLabel.setForeground(new Color(107, 114, 128));

        JButton exportButton = new JButton("Export Report");
        exportButton.setBackground(new Color(59, 130, 246));
        exportButton.setForeground(new Color(6, 13, 23));
        exportButton.setFocusPainted(false);
        exportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(ReportsView.this,
                        "Report exported successfully.",
                        "Export Complete",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });

        titlePanel.add(titleLabel, BorderLayout.NORTH);
        titlePanel.add(subtitleLabel, BorderLayout.CENTER);
        titlePanel.add(exportButton, BorderLayout.EAST);

        // Filter panel
        JPanel filterPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        filterPanel.setOpaque(false);
        filterPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(229, 231, 235)),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        // Report type filter
        JPanel typePanel = new JPanel(new BorderLayout(0, 5));
        typePanel.setOpaque(false);

        JLabel typeLabel = new JLabel("Report Type");
        typeLabel.setFont(new Font("Arial", Font.PLAIN, 12));

        reportTypeCombo = new JComboBox<>(new String[]{"Revenue Report", "Occupancy Report", "Staff Performance", "Lost & Found Report"});
        reportTypeCombo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateReport();
            }
        });

        typePanel.add(typeLabel, BorderLayout.NORTH);
        typePanel.add(reportTypeCombo, BorderLayout.CENTER);

        // Time range filter
        JPanel timePanel = new JPanel(new BorderLayout(0, 5));
        timePanel.setOpaque(false);

        JLabel timeLabel = new JLabel("Time Range");
        timeLabel.setFont(new Font("Arial", Font.PLAIN, 12));

        timeRangeCombo = new JComboBox<>(new String[]{"Last 7 Days", "Last 30 Days", "Last 90 Days", "Last Year", "Custom Range"});
        timeRangeCombo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (timeRangeCombo.getSelectedItem().equals("Custom Range")) {
                    showDateRangeDialog();
                } else {
                    updateReport();
                }
            }
        });

        timePanel.add(timeLabel, BorderLayout.NORTH);
        timePanel.add(timeRangeCombo, BorderLayout.CENTER);

        filterPanel.add(typePanel);
        filterPanel.add(timePanel);

        // Report panel with card layout
        cardLayout = new CardLayout();
        reportPanel = new JPanel(cardLayout);
        reportPanel.setOpaque(false);
        reportPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(229, 231, 235)),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        // Create report panels
        JPanel revenuePanel = createRevenueReportPanel();
        JPanel occupancyPanel = createOccupancyReportPanel();
        JPanel staffPanel = createStaffReportPanel();
        JPanel lostFoundPanel = createLostFoundReportPanel();

        reportPanel.add(revenuePanel, "Revenue Report");
        reportPanel.add(occupancyPanel, "Occupancy Report");
        reportPanel.add(staffPanel, "Staff Performance");
        reportPanel.add(lostFoundPanel, "Lost & Found Report");

        // Add components to main panel
        add(titlePanel, BorderLayout.NORTH);
        add(filterPanel, BorderLayout.CENTER);
        add(reportPanel, BorderLayout.SOUTH);
    }

    private void updateReport() {
        String reportType = (String) reportTypeCombo.getSelectedItem();
        cardLayout.show(reportPanel, reportType);
    }

    private void showDateRangeDialog() {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Select Date Range", true);
        dialog.setSize(300, 200);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel startLabel = new JLabel("Start Date:");
        JTextField startField = new JTextField(new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date()));

        JLabel endLabel = new JLabel("End Date:");
        JTextField endField = new JTextField(new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date()));

        formPanel.add(startLabel);
        formPanel.add(startField);
        formPanel.add(endLabel);
        formPanel.add(endField);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
                timeRangeCombo.setSelectedItem("Last 30 Days");
            }
        });

        JButton applyButton = new JButton("Apply");
        applyButton.setBackground(new Color(59, 130, 246));
        applyButton.setForeground(Color.WHITE);
        applyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateReport();
                dialog.dispose();
            }
        });

        buttonPanel.add(cancelButton);
        buttonPanel.add(applyButton);

        dialog.add(formPanel, BorderLayout.CENTER);
        dialog.add(buttonPanel, BorderLayout.SOUTH);

        dialog.setVisible(true);
    }

    private JPanel createRevenueReportPanel() {
        JPanel panel = new JPanel(new BorderLayout(0, 20));
        panel.setOpaque(false);

        // Summary panel
        JPanel summaryPanel = new JPanel(new GridLayout(1, 4, 15, 0));
        summaryPanel.setOpaque(false);

        summaryPanel.add(createMetricCard("Total Revenue", "₹34,10,240", "+12.5%", true));
        summaryPanel.add(createMetricCard("Room Revenue", "₹29,16,800", "+10.2%", true));
        summaryPanel.add(createMetricCard("F&B Revenue", "₹3,88,000", "+15.3%", true));
        summaryPanel.add(createMetricCard("Other Revenue", "₹1,22,640", "+8.7%", true));

        // Chart panel
        JPanel chartPanel = new JPanel(new BorderLayout());
        chartPanel.setOpaque(false);
        chartPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(229, 231, 235)),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        JLabel chartTitle = new JLabel("Revenue Trend");
        chartTitle.setFont(new Font("Arial", Font.BOLD, 14));

        JPanel chartContent = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                int width = getWidth();
                int height = getHeight();
                int padding = 40;
                int chartWidth = width - 2 * padding;
                int chartHeight = height - 2 * padding;

                // Draw axes
                g2d.setColor(Color.BLACK);
                g2d.drawLine(padding, height - padding, width - padding, height - padding); // x-axis
                g2d.drawLine(padding, padding, padding, height - padding); // y-axis

                // Draw data
                int numPoints = 12;
                int pointWidth = chartWidth / numPoints;

                // Generate random data for the chart
                Random random = new Random(123); // Fixed seed for consistent results
                int[] data = new int[numPoints];
                for (int i = 0; i < numPoints; i++) {
                    data[i] = random.nextInt(chartHeight - 50) + 50;
                }

                // Draw line chart
                g2d.setColor(new Color(59, 130, 246));
                g2d.setStroke(new BasicStroke(2));

                for (int i = 0; i < numPoints - 1; i++) {
                    int x1 = padding + i * pointWidth;
                    int y1 = height - padding - data[i];
                    int x2 = padding + (i + 1) * pointWidth;
                    int y2 = height - padding - data[i + 1];
                    g2d.drawLine(x1, y1, x2, y2);
                }

                // Draw points
                for (int i = 0; i < numPoints; i++) {
                    int x = padding + i * pointWidth;
                    int y = height - padding - data[i];
                    g2d.fillOval(x - 4, y - 4, 8, 8);
                }

                // Draw x-axis labels (months)
                g2d.setColor(Color.BLACK);
                String[] months = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
                for (int i = 0; i < numPoints; i++) {
                    int x = padding + i * pointWidth;
                    g2d.drawString(months[i], x - 10, height - padding + 20);
                }

                // Draw y-axis labels (revenue)
                for (int i = 0; i <= 5; i++) {
                    int y = height - padding - (i * chartHeight / 5);
                    g2d.drawString("$" + (i * 10) + "K", padding - 35, y + 5);
                    g2d.setColor(new Color(229, 231, 235));
                    g2d.drawLine(padding, y, width - padding, y);
                    g2d.setColor(Color.BLACK);
                }
            }
        };
        chartContent.setPreferredSize(new Dimension(0, 300));

        chartPanel.add(chartTitle, BorderLayout.NORTH);
        chartPanel.add(chartContent, BorderLayout.CENTER);

        panel.add(summaryPanel, BorderLayout.NORTH);
        panel.add(chartPanel, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createOccupancyReportPanel() {
        JPanel panel = new JPanel(new BorderLayout(0, 20));
        panel.setOpaque(false);

        // Summary panel
        JPanel summaryPanel = new JPanel(new GridLayout(1, 4, 15, 0));
        summaryPanel.setOpaque(false);

        summaryPanel.add(createMetricCard("Occupancy Rate", "78%", "+5.2%", true));
        summaryPanel.add(createMetricCard("Average Daily Rate", "14,400", "+3.8%", true));
        summaryPanel.add(createMetricCard("RevPAR", "14,400", "+9.1%", true));
        summaryPanel.add(createMetricCard("Average Stay", "2.8 days", "-0.3 days", false));

        // Chart panel
        JPanel chartPanel = new JPanel(new BorderLayout());
        chartPanel.setOpaque(false);
        chartPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(229, 231, 235)),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        JLabel chartTitle = new JLabel("Occupancy by Room Type");
        chartTitle.setFont(new Font("Arial", Font.BOLD, 14));

        JPanel chartContent = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                int width = getWidth();
                int height = getHeight();
                int padding = 40;
                int barWidth = 60;
                int spacing = 40;

                // Draw axes
                g2d.setColor(Color.BLACK);
                g2d.drawLine(padding, height - padding, width - padding, height - padding); // x-axis
                g2d.drawLine(padding, padding, padding, height - padding); // y-axis

                // Draw data
                String[] roomTypes = {"Standard", "Deluxe", "Suite", "Presidential"};
                int[] occupancy = {85, 72, 64, 50};
                Color[] colors = {
                        new Color(59, 130, 246),
                        new Color(16, 185, 129),
                        new Color(245, 158, 11),
                        new Color(239, 68, 68)
                };

                int startX = padding + 50;

                for (int i = 0; i < roomTypes.length; i++) {
                    int x = startX + i * (barWidth + spacing);
                    int barHeight = (int) ((occupancy[i] / 100.0) * (height - 2 * padding));
                    int y = height - padding - barHeight;

                    // Draw bar
                    g2d.setColor(colors[i]);
                    g2d.fillRect(x, y, barWidth, barHeight);

                    // Draw border
                    g2d.setColor(colors[i].darker());
                    g2d.drawRect(x, y, barWidth, barHeight);

                    // Draw label
                    g2d.setColor(Color.BLACK);
                    g2d.drawString(roomTypes[i], x, height - padding + 20);

                    // Draw percentage
                    g2d.drawString(occupancy[i] + "%", x + 15, y - 5);
                }

                // Draw y-axis labels
                for (int i = 0; i <= 5; i++) {
                    int y = height - padding - (i * (height - 2 * padding) / 5);
                    g2d.drawString((i * 20) + "%", padding - 35, y + 5);
                    g2d.setColor(new Color(229, 231, 235));
                    g2d.drawLine(padding, y, width - padding, y);
                    g2d.setColor(Color.BLACK);
                }
            }
        };
        chartContent.setPreferredSize(new Dimension(0, 300));

        chartPanel.add(chartTitle, BorderLayout.NORTH);
        chartPanel.add(chartContent, BorderLayout.CENTER);

        panel.add(summaryPanel, BorderLayout.NORTH);
        panel.add(chartPanel, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createStaffReportPanel() {
        JPanel panel = new JPanel(new BorderLayout(0, 20));
        panel.setOpaque(false);

        // Summary panel
        JPanel summaryPanel = new JPanel(new GridLayout(1, 4, 15, 0));
        summaryPanel.setOpaque(false);

        summaryPanel.add(createMetricCard("Total Staff", "24", "+2", true));
        summaryPanel.add(createMetricCard("Avg. Performance", "4.2/5", "+0.3", true));
        summaryPanel.add(createMetricCard("Staff Turnover", "8%", "-2.5%", true));
        summaryPanel.add(createMetricCard("Training Hours", "120", "+15", true));

        // Table panel
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setOpaque(false);
        tablePanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(229, 231, 235)),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        JLabel tableTitle = new JLabel("Staff Performance Overview");
        tableTitle.setFont(new Font("Arial", Font.BOLD, 14));

        // Create table model
        String[] columnNames = {"Staff ID", "Name", "Position", "Performance", "Attendance", "Training"};
        Object[][] data = {
                {"S001", "Sneha Pillai", "Receptionist", "4.5/5", "98%", "Completed"},
                {"S002", "Aditya Jain", "Housekeeper", "4.2/5", "95%", "In Progress"},
                {"S003", "Meera Das", "Maintenance", "3.8/5", "92%", "Completed"},
                {"S004", "Karan Kapoor", "Manager", "4.7/5", "99%", "Completed"},
                {"S005", "Divya Chatterjee", "Chef", "4.4/5", "96%", "Completed"},
                {"S006", "Mohit Verma", "Receptionist", "3.9/5", "94%", "Not Started"},
                {"S007", "Shreya Menon", "Security", "4.1/5", "97%", "In Progress"},
                {"S008", "Arjun Sinha", "Housekeeper", "4.0/5", "93%", "Completed"}
        };

        JTable table = new JTable(data, columnNames);
        table.setRowHeight(30);
        table.getTableHeader().setReorderingAllowed(false);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        table.getTableHeader().setBackground(new Color(243, 244, 246));
        table.getTableHeader().setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(229, 231, 235)));

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        tablePanel.add(tableTitle, BorderLayout.NORTH);
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        panel.add(summaryPanel, BorderLayout.NORTH);
        panel.add(tablePanel, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createLostFoundReportPanel() {
        JPanel panel = new JPanel(new BorderLayout(0, 20));
        panel.setOpaque(false);

        // Summary panel
        JPanel summaryPanel = new JPanel(new GridLayout(1, 4, 15, 0));
        summaryPanel.setOpaque(false);

        summaryPanel.add(createMetricCard("Total Items", "42", "+8", true));
        summaryPanel.add(createMetricCard("Claimed Items", "28", "+5", true));
        summaryPanel.add(createMetricCard("Unclaimed Items", "14", "+3", false));
        summaryPanel.add(createMetricCard("Claim Rate", "67%", "+2%", true));

        // Chart panel
        JPanel chartPanel = new JPanel(new BorderLayout());
        chartPanel.setOpaque(false);
        chartPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(229, 231, 235)),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        JLabel chartTitle = new JLabel("Lost Items by Category");
        chartTitle.setFont(new Font("Arial", Font.BOLD, 14));

        JPanel chartContent = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                int width = getWidth();
                int height = getHeight();
                int centerX = width / 2;
                int centerY = height / 2;
                int radius = Math.min(width, height) / 3;

                // Data for pie chart
                String[] categories = {"Electronics", "Jewelry", "Clothing", "Documents", "Other"};
                int[] values = {35, 20, 15, 10, 20};
                Color[] colors = {
                        new Color(59, 130, 246),
                        new Color(16, 185, 129),
                        new Color(245, 158, 11),
                        new Color(239, 68, 68),
                        new Color(139, 92, 246)
                };

                // Calculate total
                int total = 0;
                for (int value : values) {
                    total += value;
                }

                // Draw pie chart
                int startAngle = 0;
                for (int i = 0; i < values.length; i++) {
                    int arcAngle = (int) Math.round(360.0 * values[i] / total);

                    g2d.setColor(colors[i]);
                    g2d.fillArc(centerX - radius, centerY - radius, 2 * radius, 2 * radius, startAngle, arcAngle);

                    // Draw label line and text
                    double middleAngle = Math.toRadians(startAngle + arcAngle / 2);
                    int labelX = (int) (centerX + (radius * 1.2) * Math.cos(middleAngle));
                    int labelY = (int) (centerY - (radius * 1.2) * Math.sin(middleAngle));

                    g2d.setColor(Color.BLACK);
                    g2d.drawLine(
                            (int) (centerX + radius * 0.8 * Math.cos(middleAngle)),
                            (int) (centerY - radius * 0.8 * Math.sin(middleAngle)),
                            labelX, labelY
                    );

                    String label = categories[i] + " (" + values[i] + "%)";
                    g2d.drawString(label, labelX - 40, labelY);

                    startAngle += arcAngle;
                }
            }
        };
        chartContent.setPreferredSize(new Dimension(0, 300));

        chartPanel.add(chartTitle, BorderLayout.NORTH);
        chartPanel.add(chartContent, BorderLayout.CENTER);

        panel.add(summaryPanel, BorderLayout.NORTH);
        panel.add(chartPanel, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createMetricCard(String title, String value, String change, boolean isPositive) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(229, 231, 235)),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);

        JLabel iconLabel = new JLabel("📊");

        JLabel changeLabel = new JLabel(change);
        changeLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        changeLabel.setForeground(isPositive ? new Color(34, 197, 94) : new Color(239, 68, 68));

        topPanel.add(iconLabel, BorderLayout.WEST);
        topPanel.add(changeLabel, BorderLayout.EAST);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setOpaque(false);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        titleLabel.setForeground(new Color(107, 114, 128));

        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("Arial", Font.BOLD, 20));

        bottomPanel.add(titleLabel, BorderLayout.NORTH);
        bottomPanel.add(valueLabel, BorderLayout.SOUTH);

        card.add(topPanel, BorderLayout.NORTH);
        card.add(bottomPanel, BorderLayout.SOUTH);

        return card;
    }
}