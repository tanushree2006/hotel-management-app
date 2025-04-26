import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

public class RegistrationWindow extends JFrame {
    private JTextField fullNameField, emailField, phoneField, usernameField;
    private JPasswordField passwordField, confirmPasswordField;
    private JRadioButton customerRadio, ownerRadio;

    public RegistrationWindow() {
        setTitle("MiraVelle : A House of Distinction - Registration");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 700);
        setLocationRelativeTo(null);
        setResizable(false);

        // Create main panel with gradient background
        GradientPanel mainPanel = new GradientPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        setContentPane(mainPanel);

        // Header panel
        JPanel headerPanel = new JPanel();
        headerPanel.setOpaque(false);
        headerPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        // Hotel icon and title
        JLabel iconLabel = new JLabel(createHotelIcon());
        JLabel titleLabel = new JLabel("MiraVelle : Beauty in Every Stay");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(HotelManagementSystem.PRIMARY_COLOR);

        headerPanel.add(iconLabel);
        headerPanel.add(titleLabel);
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // Registration card
        RoundedPanel registrationCard = new RoundedPanel(15);
        registrationCard.setBackground(Color.WHITE);
        registrationCard.setLayout(new BorderLayout());
        registrationCard.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Tab buttons
        JPanel tabPanel = new JPanel(new GridLayout(1, 2));
        tabPanel.setOpaque(false);

        StyledButton loginButton = new StyledButton("Login");
        loginButton.setSelected(false);
        StyledButton registerButton = new StyledButton("Register");
        registerButton.setSelected(true);

        loginButton.addActionListener(e -> {
            LoginWindow loginWindow = new LoginWindow();
            loginWindow.setVisible(true);
            dispose();
        });

        tabPanel.add(loginButton);
        tabPanel.add(registerButton);

        // Registration form
        JPanel registrationFormPanel = new JPanel();
        registrationFormPanel.setLayout(new BoxLayout(registrationFormPanel, BoxLayout.Y_AXIS));
        registrationFormPanel.setOpaque(false);
        registrationFormPanel.setBorder(new EmptyBorder(20, 10, 10, 10));

        // Full Name field
        JPanel fullNamePanel = createFormField("Full Name", fullNameField = new StyledTextField());

        // Email field
        JPanel emailPanel = createFormField("Email", emailField = new StyledTextField());

        // Phone field
        JPanel phonePanel = createFormField("Phone Number", phoneField = new StyledTextField());

        // Username field
        JPanel usernamePanel = createFormField("Username", usernameField = new StyledTextField());

        // Password field
        JPanel passwordPanel = createFormField("Password", passwordField = new StyledPasswordField());

        // Confirm Password field
        JPanel confirmPasswordPanel = createFormField("Confirm Password", confirmPasswordField = new StyledPasswordField());

        // User type selection
        JPanel userTypePanel = new JPanel(new BorderLayout());
        userTypePanel.setOpaque(false);
        userTypePanel.setBorder(new EmptyBorder(0, 0, 15, 0));

        JLabel userTypeLabel = new JLabel("Register As");
        userTypeLabel.setFont(HotelManagementSystem.NORMAL_FONT);

        JPanel radioPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        radioPanel.setOpaque(false);

        ButtonGroup group = new ButtonGroup();
        customerRadio = new StyledRadioButton("Customer");
        customerRadio.setSelected(true);
        ownerRadio = new StyledRadioButton("Hotel Owner");

        group.add(customerRadio);
        group.add(ownerRadio);

        radioPanel.add(customerRadio);
        radioPanel.add(ownerRadio);

        userTypePanel.add(userTypeLabel, BorderLayout.NORTH);
        userTypePanel.add(radioPanel, BorderLayout.CENTER);

        // Register button
        JPanel registerButtonPanel = new JPanel(new BorderLayout());
        registerButtonPanel.setOpaque(false);
        registerButtonPanel.setBorder(new EmptyBorder(10, 0, 0, 0));

        StyledButton submitButton = new StyledButton("Register");
        submitButton.addActionListener(e -> handleRegistration());

        registerButtonPanel.add(submitButton, BorderLayout.CENTER);

        // Add components to registration form
        registrationFormPanel.add(fullNamePanel);
        registrationFormPanel.add(emailPanel);
        registrationFormPanel.add(phonePanel);
        registrationFormPanel.add(usernamePanel);
        registrationFormPanel.add(passwordPanel);
        registrationFormPanel.add(confirmPasswordPanel);
        registrationFormPanel.add(userTypePanel);
        registrationFormPanel.add(registerButtonPanel);

        // Add components to registration card
        registrationCard.add(tabPanel, BorderLayout.NORTH);
        registrationCard.add(registrationFormPanel, BorderLayout.CENTER);

        // Add registration card to main panel with scroll
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setOpaque(false);

        // Create a JScrollPane and put registrationCard inside it
        JScrollPane scrollPane = new JScrollPane(registrationCard);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(null);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16); // Smooth scrolling

        centerPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
    }

    private JPanel createFormField(String labelText, JComponent field) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);
        panel.setBorder(new EmptyBorder(0, 0, 15, 0));

        JLabel label = new JLabel(labelText);
        label.setFont(HotelManagementSystem.NORMAL_FONT);

        panel.add(label, BorderLayout.NORTH);
        panel.add(field, BorderLayout.CENTER);

        return panel;
    }

    private void handleRegistration() {
        String fullName = fullNameField.getText();
        String email = emailField.getText();
        String phone = phoneField.getText();
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        String confirmPassword = new String(confirmPasswordField.getPassword());

        // Validate fields
        if (fullName.isEmpty() || email.isEmpty() || phone.isEmpty() ||
                username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please fill in all fields.",
                    "Registration Failed",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(this,
                    "Passwords do not match.",
                    "Registration Failed",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Check if username already exists
        UserManager userManager = UserManager.getInstance();
        if (userManager.userExists(username)) {
            JOptionPane.showMessageDialog(this,
                    "Username already exists. Please choose a different username.",
                    "Registration Failed",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Create and register the new user
        boolean isOwner = ownerRadio.isSelected();
        User newUser = new User(username, password, fullName, email, phone, isOwner);
        userManager.addUser(newUser);

        JOptionPane.showMessageDialog(this,
                "Registration successful! You can now login.",
                "Registration Complete",
                JOptionPane.INFORMATION_MESSAGE);

        // Return to login window
        LoginWindow loginWindow = new LoginWindow();
        loginWindow.setVisible(true);
        dispose();
    }

    private ImageIcon createHotelIcon() {
        // Replace with your image path (make sure the image is placed in the correct directory)
        String imagePath = "images/logo.png"; // Adjust this path as per your project structure
        ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource(imagePath));

        // Optionally scale the image to fit nicely in the UI
        Image image = icon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        return new ImageIcon(image);
    }
}
