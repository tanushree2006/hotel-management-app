import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

// Model class for lost items
class LostItem {
    private int id;
    private String name;
    private String description;
    private String roomNumber;
    private Date dateFound;
    private String status;
    private String imageUrl;

    public LostItem(int id, String name, String description, String roomNumber, Date dateFound, String status, String imageUrl) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.roomNumber = roomNumber;
        this.dateFound = dateFound;
        this.status = status;
        this.imageUrl = imageUrl;
    }
    public int getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public String getRoomNumber() { return roomNumber; }
    public Date getDateFound() { return dateFound; }
    public String getStatus() { return status; }
    public String getImageUrl() { return imageUrl; }
    public void setStatus(String s) { this.status = s; }
}

// Model class for item claims
class ItemClaim {
    private int itemId;
    private String claimantName;
    private String claimDetails;
    public ItemClaim(int itemId, String claimantName, String claimDetails) {
        this.itemId = itemId;
        this.claimantName = claimantName;
        this.claimDetails = claimDetails;
    }
    public int getItemId() { return itemId; }
    public String getClaimantName() { return claimantName; }
    public String getClaimDetails() { return claimDetails; }
}

public class LostAndFoundPanel extends JPanel {
    private static List<LostItem> lostItems = new ArrayList<>();
    private static List<ItemClaim> itemClaims = new ArrayList<>();

    private boolean isOwnerView;
    private String currentUsername;
    private JTextField searchField;
    private JPanel galleryPanel;
    private JScrollPane galleryScrollPane;

    // Colors
    // Updated colors to match Customer Dashboard theme
    private final Color PRIMARY_COLOR = new Color(52, 152, 219);  // blue
    private final Color SECONDARY_COLOR = new Color(240, 248, 255); // AliceBlue (lighter background)
    private final Color ACCENT_COLOR = new Color(41, 128, 185); // darker blue for buttons
    private final Color DANGER_COLOR = new Color(231, 76, 60); // red for danger actions
    private final Color TEXT_COLOR = new Color(44, 62, 80); // dark text color (#2c3e50)

    @Override
    public void setBackground(Color bg) {
        super.setBackground(SECONDARY_COLOR);
    }

    // Updated button style for better contrast
    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBorder(BorderFactory.createEmptyBorder(12, 25, 12, 25));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setOpaque(true);
        button.setBorder(new LineBorder(bgColor.darker(), 1, true));
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor.darker());
                button.setBorder(new LineBorder(bgColor.darker().darker(), 1, true));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor);
                button.setBorder(new LineBorder(bgColor.darker(), 1, true));
            }
        });
        return button;
    }


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
        JPanel headerPanel = createHeaderPanel();
        add(headerPanel, BorderLayout.NORTH);

        galleryPanel = new JPanel(new WrapLayout(FlowLayout.LEFT, 20, 20));
        galleryPanel.setBackground(SECONDARY_COLOR);
        galleryScrollPane = new JScrollPane(galleryPanel);
        galleryScrollPane.setBorder(BorderFactory.createEmptyBorder());
        add(galleryScrollPane, BorderLayout.CENTER);

        JPanel actionPanel = createActionPanel();
        add(actionPanel, BorderLayout.SOUTH);

        refreshGallery();
    }

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout(15, 15));
        headerPanel.setBackground(SECONDARY_COLOR);
        headerPanel.setBorder(new EmptyBorder(0, 0, 15, 0));

        JLabel titleLabel = new JLabel(
                "<html><div style='font-size:24px;color:#2c3e50;font-weight:bold;'>" +
                        "Lost & Found</div><div style='font-size:14px;color:#7f8c8d;'>" +
                        (isOwnerView ? "Manage lost items and claims" : "Report or claim lost items") +
                        "</div></html>"
        );
        titleLabel.setForeground(TEXT_COLOR);

        JPanel searchPanel = new JPanel(new BorderLayout(10, 10));
        searchPanel.setBackground(SECONDARY_COLOR);

        searchField = new JTextField();
        searchField.setPreferredSize(new Dimension(250, 40));
        searchField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220)),
                BorderFactory.createEmptyBorder(5, 15, 5, 15)
        ));
        searchField.setFont(new Font("Arial", Font.PLAIN, 14));

        JButton searchButton = createStyledButton("Search", PRIMARY_COLOR);
        searchButton.addActionListener(e -> refreshGallery());

        searchPanel.add(searchField, BorderLayout.CENTER);
        searchPanel.add(searchButton, BorderLayout.EAST);

        headerPanel.add(titleLabel, BorderLayout.WEST);
        headerPanel.add(searchPanel, BorderLayout.EAST);

        return headerPanel;
    }

    private JPanel createActionPanel() {
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 15));
        actionPanel.setBackground(SECONDARY_COLOR);

        if (isOwnerView) {
            JButton addButton = createStyledButton("Add New Item", PRIMARY_COLOR);
            addButton.addActionListener(e -> showAddItemDialog());
            actionPanel.add(addButton);
        } else {
            JButton reportButton = createStyledButton("Report Lost Item", DANGER_COLOR);
            reportButton.addActionListener(e -> showReportItemDialog());
            actionPanel.add(reportButton);
        }
        return actionPanel;
    }


    private void refreshGallery() {
        galleryPanel.removeAll();
        String query = searchField.getText().trim().toLowerCase();

        for (LostItem item : lostItems) {
            if (!isOwnerView && !item.getStatus().equals("Unclaimed")) continue;
            if (!query.isEmpty()) {
                if (!(item.getName().toLowerCase().contains(query)
                        || item.getDescription().toLowerCase().contains(query)
                        || item.getRoomNumber().toLowerCase().contains(query))) {
                    continue;
                }
            }
            galleryPanel.add(createItemCard(item));
        }

        galleryPanel.revalidate();
        galleryPanel.repaint();
    }

    private JPanel createItemCard(LostItem item) {
        JPanel card = new JPanel();
        card.setPreferredSize(new Dimension(220, 320));
        card.setLayout(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(new LineBorder(PRIMARY_COLOR, 2, true));
        card.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Image
        JLabel imgLabel = new JLabel();
        imgLabel.setHorizontalAlignment(JLabel.CENTER);
        imgLabel.setPreferredSize(new Dimension(220, 180));
        try {
            URL imgUrl = getClass().getResource("/images/" + item.getImageUrl());
            if (imgUrl != null) {
                ImageIcon icon = new ImageIcon(imgUrl);
                imgLabel.setIcon(new ImageIcon(icon.getImage().getScaledInstance(200, 160, Image.SCALE_SMOOTH)));
            } else {
                imgLabel.setText("No Image");
            }
        } catch (Exception e) {
            imgLabel.setText("No Image");
        }
        card.add(imgLabel, BorderLayout.NORTH);

        // Name & quick info
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBackground(Color.WHITE);
        JLabel nameLabel = new JLabel(item.getName(), JLabel.CENTER);
        nameLabel.setFont(new Font("Arial", Font.BOLD, 16));
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        nameLabel.setForeground(TEXT_COLOR);

        JLabel statusLabel = new JLabel(item.getStatus(), JLabel.CENTER);
        statusLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        statusLabel.setForeground(item.getStatus().equals("Unclaimed") ? ACCENT_COLOR : DANGER_COLOR);
        statusLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        infoPanel.add(Box.createVerticalStrut(8));
        infoPanel.add(nameLabel);
        infoPanel.add(statusLabel);

        card.add(infoPanel, BorderLayout.CENTER);

        // Action Button
        JButton detailsBtn = createStyledButton("View Details", ACCENT_COLOR);
        detailsBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        detailsBtn.addActionListener(e -> showItemDetailDialog(item));
        JPanel btnPanel = new JPanel();
        btnPanel.setBackground(Color.WHITE);
        btnPanel.add(detailsBtn);
        card.add(btnPanel, BorderLayout.SOUTH);

        // Hover effect
        card.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                card.setBorder(new LineBorder(ACCENT_COLOR, 3, true));
            }
            public void mouseExited(MouseEvent e) {
                card.setBorder(new LineBorder(PRIMARY_COLOR, 2, true));
            }
        });

        return card;
    }

    private void showItemDetailDialog(LostItem item) {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Item Details", true);
        dialog.setSize(500, 650);
        dialog.setLocationRelativeTo(this);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Image
        JLabel imgLabel = new JLabel();
        imgLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        imgLabel.setPreferredSize(new Dimension(400, 220));
        try {
            URL imgUrl = getClass().getResource("/images/" + item.getImageUrl());
            if (imgUrl != null) {
                ImageIcon icon = new ImageIcon(imgUrl);
                imgLabel.setIcon(new ImageIcon(icon.getImage().getScaledInstance(400, 200, Image.SCALE_SMOOTH)));
            } else {
                imgLabel.setText("No Image");
            }
        } catch (Exception e) {
            imgLabel.setText("No Image");
        }
        panel.add(imgLabel);

        // Details
        panel.add(Box.createVerticalStrut(10));
        JLabel nameLabel = new JLabel(item.getName());
        nameLabel.setFont(new Font("Arial", Font.BOLD, 20));
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        nameLabel.setForeground(TEXT_COLOR);
        panel.add(nameLabel);

        JTextArea desc = new JTextArea(item.getDescription());
        desc.setEditable(false);
        desc.setLineWrap(true);
        desc.setWrapStyleWord(true);
        desc.setBackground(Color.WHITE);
        desc.setFont(new Font("Arial", Font.PLAIN, 14));
        desc.setBorder(new EmptyBorder(10, 0, 10, 0));
        panel.add(desc);

        // Meta info
        JLabel locationLabel = new JLabel("Found Location: " + item.getRoomNumber());
        locationLabel.setForeground(TEXT_COLOR);
        panel.add(locationLabel);

        JLabel dateLabel = new JLabel("Date Found: " + new SimpleDateFormat("MMM dd, yyyy").format(item.getDateFound()));
        dateLabel.setForeground(TEXT_COLOR);
        panel.add(dateLabel);

        JLabel statusLabel = new JLabel("Status: " + item.getStatus());
        statusLabel.setForeground(TEXT_COLOR);
        panel.add(statusLabel);

        // Claim form (if unclaimed)
        if (!isOwnerView && item.getStatus().equals("Unclaimed")) {
            panel.add(Box.createVerticalStrut(20));
            JLabel claimLabel = new JLabel("To claim, provide details:");
            claimLabel.setForeground(TEXT_COLOR);
            panel.add(claimLabel);

            JTextField yourName = new JTextField();
            yourName.setBorder(BorderFactory.createTitledBorder("Your Name"));
            JTextArea claimDetails = new JTextArea(3, 20);
            claimDetails.setBorder(BorderFactory.createTitledBorder("Describe the item or proof of ownership"));
            panel.add(yourName);
            panel.add(claimDetails);

            JButton claimBtn = createStyledButton("Submit Claim", ACCENT_COLOR);
            claimBtn.addActionListener(ev -> {
                if (yourName.getText().trim().isEmpty() || claimDetails.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(dialog, "Please fill all required fields.", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    itemClaims.add(new ItemClaim(item.getId(), yourName.getText().trim(), claimDetails.getText().trim()));
                    item.setStatus("Claimed");
                    JOptionPane.showMessageDialog(dialog, "Claim submitted! You will be contacted if matched.");
                    dialog.dispose();
                    refreshGallery();
                }
            });
            panel.add(Box.createVerticalStrut(10));
            panel.add(claimBtn);
        }

        dialog.add(new JScrollPane(panel));
        dialog.setVisible(true);
    }

    private void showAddItemDialog() {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Add New Lost Item", true);
        dialog.setSize(400, 500);
        dialog.setLocationRelativeTo(this);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        panel.setBackground(Color.WHITE);

        JTextField nameField = new JTextField();
        nameField.setBorder(BorderFactory.createTitledBorder("Item Name"));
        JTextArea descArea = new JTextArea(3, 20);
        descArea.setBorder(BorderFactory.createTitledBorder("Description"));
        JTextField locField = new JTextField();
        locField.setBorder(BorderFactory.createTitledBorder("Found Location"));
        JTextField imgField = new JTextField();
        imgField.setBorder(BorderFactory.createTitledBorder("Image File Name (in /images/)"));

        panel.add(nameField);
        panel.add(descArea);
        panel.add(locField);
        panel.add(imgField);

        JButton saveBtn = createStyledButton("Save Item", ACCENT_COLOR);
        saveBtn.addActionListener(e -> {
            if (nameField.getText().trim().isEmpty() || descArea.getText().trim().isEmpty() || locField.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Please fill all fields.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                lostItems.add(new LostItem(
                        lostItems.size() + 1,
                        nameField.getText().trim(),
                        descArea.getText().trim(),
                        locField.getText().trim(),
                        new Date(),
                        "Unclaimed",
                        imgField.getText().trim().isEmpty() ? null : imgField.getText().trim()
                ));
                dialog.dispose();
                refreshGallery();
            }
        });
        panel.add(Box.createVerticalStrut(10));
        panel.add(saveBtn);

        dialog.add(panel);
        dialog.setVisible(true);
    }

    private void showReportItemDialog() {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Report Lost Item", true);
        dialog.setSize(400, 400);
        dialog.setLocationRelativeTo(this);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        panel.setBackground(Color.WHITE);

        JTextField nameField = new JTextField();
        nameField.setBorder(BorderFactory.createTitledBorder("Your Name"));
        JTextArea descArea = new JTextArea(3, 20);
        descArea.setBorder(BorderFactory.createTitledBorder("Describe your lost item"));

        panel.add(nameField);
        panel.add(descArea);

        JButton submitBtn = createStyledButton("Submit Report", DANGER_COLOR);
        submitBtn.addActionListener(e -> {
            if (nameField.getText().trim().isEmpty() || descArea.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Please fill all required fields.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(dialog, "Your lost item report has been submitted.");
                dialog.dispose();
            }
        });
        panel.add(Box.createVerticalStrut(10));
        panel.add(submitBtn);

        dialog.add(panel);
        dialog.setVisible(true);
    }

    // Add some sample items
    private void addSampleItems() {
        lostItems.add(new LostItem(1, "Wallet", "A leather wallet with cards.", "Lobby", new Date(), "Unclaimed", "wallet.jpg"));
        lostItems.add(new LostItem(2, "Red Umbrella", "Red umbrella with white dots.", "Cafeteria", new Date(), "Unclaimed", "umbrella.jpg"));
        lostItems.add(new LostItem(3, "Blue Water Bottle", "Blue bottle, brand 'HydroFlask'.", "Gym", new Date(), "Claimed", "bottle.jpg"));
        lostItems.add(new LostItem(4, "Watch", "Analog  wristwatch.", "Room 101", new Date(), "Unclaimed", "watch.jpg"));
        lostItems.add(new LostItem(5, "Laptop Charger", "Dell laptop charger.", "Library", new Date(), "Unclaimed", "charger.jpg"));
        lostItems.add(new LostItem(6, "Pair of Glasses", "Black frame glasses.", "Reception", new Date(), "Unclaimed", "glasses.jpg"));
        lostItems.add(new LostItem(7, "Keys", "Set of keys with a keychain.", "Parking Lot", new Date(), "Unclaimed", "keys.jpg"));
        lostItems.add(new LostItem(8, "Tablet", "Samsung tablet.", "Room 205", new Date(), "Unclaimed", "tablet.jpg"));
    }

    // For testing/demo
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Lost & Found Demo");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1000, 700);
            frame.setLocationRelativeTo(null);
            frame.setContentPane(new LostAndFoundPanel(false, "customer1"));
            frame.setVisible(true);
        });
    }
}

// Helper class for responsive wrapping layout
class WrapLayout extends FlowLayout {
    public WrapLayout() { super(); }
    public WrapLayout(int align) { super(align); }
    public WrapLayout(int align, int hgap, int vgap) { super(align, hgap, vgap); }
    @Override
    public Dimension preferredLayoutSize(Container target) {
        return layoutSize(target, true);
    }
    @Override
    public Dimension minimumLayoutSize(Container target) {
        Dimension minimum = layoutSize(target, false);
        minimum.width -= (getHgap() + 1);
        return minimum;
    }
    private Dimension layoutSize(Container target, boolean preferred) {
        synchronized (target.getTreeLock()) {
            int targetWidth = target.getWidth();
            if (targetWidth == 0) targetWidth = Integer.MAX_VALUE;
            int hgap = getHgap(), vgap = getVgap(), maxWidth = targetWidth - getHgap() * 2;
            int x = 0, y = vgap, rowHeight = 0;
            int nmembers = target.getComponentCount();
            for (int i = 0; i < nmembers; i++) {
                Component m = target.getComponent(i);
                if (!m.isVisible()) continue;
                Dimension d = preferred ? m.getPreferredSize() : m.getMinimumSize();
                if ((x == 0) || ((x + d.width) <= maxWidth)) {
                    if (x > 0) x += hgap;
                    x += d.width;
                    rowHeight = Math.max(rowHeight, d.height);
                } else {
                    x = d.width;
                    y += vgap + rowHeight;
                    rowHeight = d.height;
                }
            }
            y += rowHeight;
            Insets insets = target.getInsets();
            y += insets.top + insets.bottom;
            return new Dimension(targetWidth, y);
        }
    }
}
