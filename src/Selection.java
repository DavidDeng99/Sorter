import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class Selection extends JButton {
    private final Color backgroundColour;
    private final int cornerRadius = 50;

    // Initialise class
    public Selection(String label, Color colour) {
        super(label);
        this.backgroundColour = colour;

        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);
        setForeground(new Color(245, 245, 245));
        setFont(new Font("SansSerif", Font.PLAIN, 20));
    }

    // Display the button
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (getModel().isArmed()) {
            g2.setColor(getSubtleDarker(backgroundColour, 0.15f)); // Selected
        } else if (getModel().isRollover()) {
            g2.setColor(getSubtleDarker(backgroundColour, 0.05f)); // Hover
        } else {
            g2.setColor(backgroundColour);
        }

        g2.fillRoundRect(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius);
        g2.dispose();

        super.paintComponent(g);
    }

    // Mouse should be inside the rounded rectangle
    @Override
    public boolean contains(int x, int y) {
        Shape shape = new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius);
        return shape.contains(x, y);
    }

    // Make slightly darker for clicked on or for hover
    private Color getSubtleDarker(Color c, float pct) {
        float[] hsb = Color.RGBtoHSB(c.getRed(), c.getGreen(), c.getBlue(), null);
        float newBrightness = Math.max(0, hsb[2] - pct);
        return Color.getHSBColor(hsb[0], hsb[1], newBrightness);
    }
}


