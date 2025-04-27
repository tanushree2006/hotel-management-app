import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;

public class StyledButton extends JButton {
    private boolean isHovered = false;
    private boolean isPressed = false;
    private Color buttonColor;
    private Color hoverColor;
    private Color pressedColor;
    private Color textColor;
    private int cornerRadius = 10;

    public StyledButton(String text) {
        super(text);

        // Default colors
        this.buttonColor = HotelManagementSystem.PRIMARY_COLOR;
        this.hoverColor = new Color(
                Math.max((int)(buttonColor.getRed() * 0.8), 0),
                Math.max((int)(buttonColor.getGreen() * 0.8), 0),
                Math.max((int)(buttonColor.getBlue() * 0.8), 0)
        );
        this.pressedColor = new Color(
                Math.max((int)(buttonColor.getRed() * 0.7), 0),
                Math.max((int)(buttonColor.getGreen() * 0.7), 0),
                Math.max((int)(buttonColor.getBlue() * 0.7), 0)
        );
        this.textColor = Color.WHITE;

        setup();
    }

    public StyledButton(String text, Color buttonColor) {
        super(text);

        this.buttonColor = buttonColor;
        this.hoverColor = new Color(
                Math.max((int)(buttonColor.getRed() * 0.8), 0),
                Math.max((int)(buttonColor.getGreen() * 0.8), 0),
                Math.max((int)(buttonColor.getBlue() * 0.8), 0)
        );
        this.pressedColor = new Color(
                Math.max((int)(buttonColor.getRed() * 0.7), 0),
                Math.max((int)(buttonColor.getGreen() * 0.7), 0),
                Math.max((int)(buttonColor.getBlue() * 0.7), 0)
        );
        this.textColor = Color.WHITE;

        setup();
    }

    private void setup() {
        setFont(HotelManagementSystem.BUTTON_FONT);
        setForeground(textColor);
        setFocusPainted(false);
        setBorderPainted(false);
        setContentAreaFilled(false);
        setOpaque(false);

        // Set preferred size
        setPreferredSize(new Dimension(120, 40));

        // Add mouse listeners for hover and press effects
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                isHovered = true;
                repaint();
                setCursor(new Cursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                isHovered = false;
                repaint();
                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }

            @Override
            public void mousePressed(MouseEvent e) {
                isPressed = true;
                repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                isPressed = false;
                repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Determine button color based on state
        if (isPressed) {
            g2d.setColor(pressedColor);
        } else if (isHovered) {
            g2d.setColor(hoverColor);
        } else {
            g2d.setColor(buttonColor);
        }

        // Draw rounded rectangle button
        g2d.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius));

        // Add subtle gradient
        if (!isPressed) {
            GradientPaint gradient = new GradientPaint(
                    0, 0, new Color(255, 255, 255, 50),
                    0, getHeight(), new Color(255, 255, 255, 0)
            );
            g2d.setPaint(gradient);
            g2d.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight()/2, cornerRadius, cornerRadius));
        }

        // Add subtle shadow
        if (!isPressed) {
            g2d.setColor(new Color(0, 0, 0, 30));
            g2d.fill(new RoundRectangle2D.Double(0, getHeight()-3, getWidth(), 3, cornerRadius, cornerRadius));
        }

        g2d.dispose();

        // Paint text and icon
        super.paintComponent(g);
    }

    @Override
    public void setBackground(Color bg) {
        this.buttonColor = bg;
        this.hoverColor = new Color(
                Math.max((int)(bg.getRed() * 0.8), 0),
                Math.max((int)(bg.getGreen() * 0.8), 0),
                Math.max((int)(bg.getBlue() * 0.8), 0)
        );
        this.pressedColor = new Color(
                Math.max((int)(bg.getRed() * 0.7), 0),
                Math.max((int)(bg.getGreen() * 0.7), 0),
                Math.max((int)(bg.getBlue() * 0.7), 0)
        );
        repaint();
    }

    public void setCornerRadius(int radius) {
        this.cornerRadius = radius;
        repaint();
    }
}