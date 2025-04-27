import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ProfileWindow extends JFrame {

    public ProfileWindow(String username) {
        UserManager userManager = UserManager.getInstance();
        User user = userManager.getUser(username);

        if (user == null) {
            JOptionPane.showMessageDialog(this, "User not found!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        setTitle("My Profile - MiraVelle");
        setSize(600, 750);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setUndecorated(true); // removes default ugly borders for full floating effect

        JPanel backgroundPanel = new GradientPanel();
        backgroundPanel.setLayout(new GridBagLayout());
        backgroundPanel.setBorder(new EmptyBorder(30, 30, 30, 30));
        setContentPane(backgroundPanel);

        // Create the floating card
        JPanel cardPanel = new RoundedPanel(40);
        cardPanel.setBackground(new Color(255, 255, 255, 120)); // transparent glass
        cardPanel.setLayout(new BoxLayout(cardPanel, BoxLayout.Y_AXIS));
        cardPanel.setBorder(new EmptyBorder(40, 40, 40, 40));
        cardPanel.setMaximumSize(new Dimension(400, 600));
        cardPanel.setOpaque(true);

        // Profile Picture
        JLabel profilePicLabel = new JLabel(loadCircularProfileImage());
        profilePicLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        profilePicLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        // Full Name
        JLabel nameLabel = new JLabel(user.getFullName());
        nameLabel.setFont(new Font("Playfair Display", Font.BOLD, 26));
        nameLabel.setForeground(new Color(90, 30, 90));
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // User Type
        JLabel userTypeLabel = new JLabel(user.isOwner() ? "Hotel Owner" : "Customer");
        userTypeLabel.setFont(new Font("Georgia", Font.ITALIC, 18));
        userTypeLabel.setForeground(Color.GRAY);
        userTypeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Information Panel
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setOpaque(false);
        infoPanel.setBorder(new EmptyBorder(30, 20, 30, 20));

        infoPanel.add(createInfoLabel("Username: " + user.getUsername()));
        infoPanel.add(Box.createVerticalStrut(15));
        infoPanel.add(createInfoLabel("Email: " + user.getEmail()));
        infoPanel.add(Box.createVerticalStrut(15));
        infoPanel.add(createInfoLabel("Phone: " + user.getPhoneNumber()));
        infoPanel.add(Box.createVerticalStrut(15));
        infoPanel.add(createInfoLabel("Registered As: " + (user.isOwner() ? "Hotel Owner" : "Customer")));

        // Close Button
        JButton closeButton = new JButton("Close");
        closeButton.setFont(new Font("Georgia", Font.BOLD, 16));
        closeButton.setBackground(new Color(90, 30, 90));
        closeButton.setForeground(Color.WHITE);
        closeButton.setFocusPainted(false);
        closeButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        closeButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        closeButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        closeButton.addActionListener(e -> dispose());

        // Add components to card
        cardPanel.add(profilePicLabel);
        cardPanel.add(nameLabel);
        cardPanel.add(userTypeLabel);
        cardPanel.add(Box.createVerticalStrut(30));
        cardPanel.add(infoPanel);
        cardPanel.add(Box.createVerticalStrut(20));
        cardPanel.add(closeButton);

        // Center card in background
        backgroundPanel.add(cardPanel);

        // Fade-in animation
        setOpacity(0f);
        Timer timer = new Timer(10, null);
        timer.addActionListener(e -> {
            float opacity = getOpacity();
            if (opacity < 1f) {
                setOpacity(opacity + 0.05f);
            } else {
                timer.stop();
            }
        });
        timer.start();

        setVisible(true);
    }

    private JLabel createInfoLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Georgia", Font.PLAIN, 16));
        label.setForeground(Color.BLACK);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        return label;
    }

    private ImageIcon loadCircularProfileImage() {
        try {
            File file = new File("images/default.png"); // or adjust based on your setup
            BufferedImage originalImage = ImageIO.read(file);
            Image scaledImage = originalImage.getScaledInstance(140, 140, Image.SCALE_SMOOTH);

            BufferedImage circleBuffer = new BufferedImage(140, 140, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = circleBuffer.createGraphics();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            Ellipse2D.Double circle = new Ellipse2D.Double(0, 0, 140, 140);
            g2.setClip(circle);
            g2.drawImage(scaledImage, 0, 0, null);
            g2.dispose();

            return new ImageIcon(circleBuffer);
        } catch (IOException e) {
            System.err.println("Error loading image: " + e.getMessage());
            return new ImageIcon(new BufferedImage(140, 140, BufferedImage.TYPE_INT_ARGB));
        }
    }
}
