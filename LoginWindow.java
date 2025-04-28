import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.net.URL;
import owner.views.OwnerDashboard;


public class LoginWindow extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JRadioButton customerRadio, ownerRadio, staffRadio;
    private StyledButton loginButton, registerButton;

    public LoginWindow() {
        initializeUI();
    }

    private void initializeUI() {
        setTitle("MiraVelle : A House of Distinction - Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 600);
        setLocationRelativeTo(null);
        setResizable(false);

        GradientPanel mainPanel = new GradientPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        setContentPane(mainPanel);

        setupHeader(mainPanel);
        setupLoginCard(mainPanel);
    }

    private void setupHeader(JPanel mainPanel) {
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        headerPanel.setOpaque(false);

        JLabel iconLabel = new JLabel(createHotelIcon());
        JLabel titleLabel = new JLabel("Miravelle : Beauty in Every Stay");
        titleLabel.setFont(new Font("Georgia", Font.BOLD, 24));
        titleLabel.setForeground(HotelManagementSystem.LIGHT_COLOR);

        headerPanel.add(iconLabel);
        headerPanel.add(titleLabel);
        mainPanel.add(headerPanel, BorderLayout.NORTH);
    }

    private void setupLoginCard(JPanel mainPanel) {
        RoundedPanel loginCard = new RoundedPanel(15);
        loginCard.setBackground(Color.WHITE);
        loginCard.setLayout(new BorderLayout());
        loginCard.setBorder(new EmptyBorder(20, 20, 20, 20));

        setupTabs(loginCard);
        setupLoginForm(loginCard);

        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setOpaque(false);
        centerPanel.add(loginCard);
        mainPanel.add(centerPanel, BorderLayout.CENTER);

        setupFooter(mainPanel);
    }

    private void setupTabs(RoundedPanel loginCard) {
        JPanel tabPanel = new JPanel(new GridLayout(1, 2));
        tabPanel.setOpaque(false);

        loginButton = new StyledButton("Login");
        loginButton.setSelected(true);
        registerButton = new StyledButton("Register");
        registerButton.setSelected(false);

        loginButton.addActionListener(e -> {
            loginButton.setSelected(true);
            registerButton.setSelected(false);
        });

        registerButton.addActionListener(e -> {
            RegistrationWindow registrationWindow = new RegistrationWindow();
            registrationWindow.setVisible(true);
            dispose();
        });

        tabPanel.add(loginButton);
        tabPanel.add(registerButton);
        loginCard.add(tabPanel, BorderLayout.NORTH);
    }

    private void setupLoginForm(RoundedPanel loginCard) {
        JPanel loginFormPanel = new JPanel();
        loginFormPanel.setLayout(new BoxLayout(loginFormPanel, BoxLayout.Y_AXIS));
        loginFormPanel.setOpaque(false);
        loginFormPanel.setBorder(new EmptyBorder(20, 10, 10, 10));

        loginFormPanel.add(createInputPanel("Username", usernameField = new StyledTextField()));
        loginFormPanel.add(createInputPanel("Password", passwordField = new StyledPasswordField()));
        loginFormPanel.add(createUserTypePanel());
        loginFormPanel.add(createSubmitButton());

        loginCard.add(loginFormPanel, BorderLayout.CENTER);
    }

    private JPanel createInputPanel(String label, JComponent field) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);
        panel.setBorder(new EmptyBorder(0, 0, 15, 0));

        JLabel jLabel = new JLabel(label);
        jLabel.setFont(HotelManagementSystem.NORMAL_FONT);

        panel.add(jLabel, BorderLayout.NORTH);
        panel.add(field, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createUserTypePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);
        panel.setBorder(new EmptyBorder(0, 0, 15, 0));


        JLabel label = new JLabel("Login As");
        label.setFont(HotelManagementSystem.NORMAL_FONT);

        JPanel radioPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        radioPanel.setOpaque(false);

        ButtonGroup group = new ButtonGroup();
        customerRadio = new StyledRadioButton("Customer");
        customerRadio.setSelected(true);
        ownerRadio = new StyledRadioButton("Hotel Owner");
        staffRadio = new StyledRadioButton("Staff");

        group.add(customerRadio);
        group.add(ownerRadio);
        group.add(staffRadio);

        radioPanel.add(customerRadio);
        radioPanel.add(ownerRadio);
        radioPanel.add(staffRadio);

        panel.add(label, BorderLayout.NORTH);
        panel.add(radioPanel, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createSubmitButton() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);
        panel.setBorder(new EmptyBorder(10, 0, 0, 0));

        StyledButton submitButton = new StyledButton("Login");
        submitButton.addActionListener(e -> handleLogin());

        panel.add(submitButton, BorderLayout.CENTER);
        return panel;
    }

    private void setupFooter(JPanel mainPanel) {
        JPanel notePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        notePanel.setOpaque(false);
        JLabel noteLabel = new JLabel("New users must register before logging in");
        noteLabel.setFont(HotelManagementSystem.SMALL_FONT);
        noteLabel.setForeground(HotelManagementSystem.ACCENT_COLOR);
        notePanel.add(noteLabel);
        mainPanel.add(notePanel, BorderLayout.SOUTH);
    }

    private void handleLogin() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();

        if (username.isEmpty() || password.isEmpty()) {
            showError("Please enter both username and password.");
            return;
        }

        try {
            UserManager userManager = UserManager.getInstance();

            if (ownerRadio.isSelected()) {
                // 👉 Hardcoded Owner Credentials
                String ownerUsername = "admin";
                String ownerPassword = "admin123"; // Change to whatever password you want

                if (username.equals(ownerUsername) && password.equals(ownerPassword)) {
                    openOwnerDashboard();
                } else {
                    showError("Invalid Hotel Owner credentials.");
                }
                return;
            } else if (staffRadio.isSelected()) {
                // 👉 Hardcoded Staff Credentials
                String staffUsername = "staff";
                String staffPassword = "staff123";

                if (username.equals(staffUsername) && password.equals(staffPassword)) {
                    openStaffDashboard();
                } else {
                    showError("Invalid Staff credentials.");
                }
                return;
            }

            // Customer Login
            User user = userManager.authenticateUser(username, password);

            if (user == null) {
                if (!userManager.userExists(username)) {
                    showError("User does not exist. Please register first.");
                    openRegistrationWindow();
                } else {
                    showError("Invalid username or password.");
                }
                return;
            }

            if (user.isOwner()) {
                showError("Invalid login method for owner.");
                return;
            }

            openDashboard(user);
        } catch (Exception e) {
            showError("An error occurred during login: " + e.getMessage());
        }
    }

    private void openOwnerDashboard() {
        SwingUtilities.invokeLater(() -> {
            try {
                // Use reflection to load our class from the package
                Class<?> ownerDashboardClass = Class.forName("owner.views.OwnerDashboard");
                Object ownerDashboard = ownerDashboardClass.getDeclaredConstructor().newInstance();
                java.lang.reflect.Method setVisibleMethod = ownerDashboardClass.getMethod("setVisible", boolean.class);
                setVisibleMethod.invoke(ownerDashboard, true);
                dispose();
            } catch (Exception ex) {
                ex.printStackTrace();
                showError("Error loading Owner Dashboard. Please check if the hotel management system is properly installed.");
            }
        });
    }

    private void openStaffDashboard() {
        SwingUtilities.invokeLater(() -> {
            try {
                // Use reflection to load our class from the package
                Class<?> staffDashboardClass = Class.forName("owner.views.StaffDashboard");
                Object staffDashboard = staffDashboardClass.getDeclaredConstructor(String.class, String.class)
                        .newInstance("Staff User", "Housekeeping");
                java.lang.reflect.Method setVisibleMethod = staffDashboardClass.getMethod("setVisible", boolean.class);
                setVisibleMethod.invoke(staffDashboard, true);
                dispose();
            } catch (Exception ex) {
                ex.printStackTrace();
                showError("Error loading Staff Dashboard. Please check if the hotel management system is properly installed.");
            }
        });
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Login Failed", JOptionPane.ERROR_MESSAGE);
    }

    private void openRegistrationWindow() {
        SwingUtilities.invokeLater(() -> {
            RegistrationWindow registrationWindow = new RegistrationWindow();
            registrationWindow.setVisible(true);
            dispose();
        });
    }

    private void openDashboard(User user) {
        SwingUtilities.invokeLater(() -> {
            if (user.isOwner()) {
                OwnerDashboard ownerDashboard = new OwnerDashboard();
                ownerDashboard.setVisible(true);
            } else {
                CustomerDashboard customerDashboard = new CustomerDashboard(user.getUsername());
                customerDashboard.setVisible(true);
            }
            dispose();
        });
    }

    private ImageIcon createHotelIcon() {
        try {
            URL imgUrl = getClass().getClassLoader().getResource("images/logo.png");
            if (imgUrl == null) {
                throw new Exception("Logo image not found");
            }
            ImageIcon icon = new ImageIcon(imgUrl);
            Image scaledImage = icon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
            return new ImageIcon(scaledImage);
        } catch (Exception e) {
            System.err.println("Error loading hotel icon: " + e.getMessage());
            return new ImageIcon(new BufferedImage(40, 40, BufferedImage.TYPE_INT_ARGB));
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LoginWindow loginWindow = new LoginWindow();
            loginWindow.setVisible(true);
        });
    }
}