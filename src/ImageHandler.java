import javax.swing.*;
import java.awt.*;

public class ImageHandler {
    public void updateButton(JButton button, ImageAsset image) {
        button.removeAll(); // Clear any previous labels
        button.setLayout(new BorderLayout());
        button.setText("");

        // Create label and pin to bottom
        JLabel label = new JLabel(image.getName(), SwingConstants.CENTER);
        label.setFont(new Font("SansSerif", Font.PLAIN, 28));
        label.setForeground(new Color(245, 245, 245));
        button.add(label, BorderLayout.SOUTH);

        // Set the centered icon
        button.setIcon(getAutoScaledIcon(image.getImage(), button));

        button.revalidate(); // Refresh the layout
    }

    private ImageIcon getAutoScaledIcon(Image image, JButton button) {
        int padding = 100;

        int availableWidth = button.getWidth() - padding;
        int availableHeight = button.getHeight() - padding;

        int imageWidth = image.getWidth(null);
        int imageHeight = image.getHeight(null);

        double ratio = Math.min((double) availableWidth / imageWidth, (double) availableHeight / imageHeight);
        int finalWidth = (int) (imageWidth * ratio);
        int finalHeight = (int) (imageHeight * ratio);

        return getScaledIcon(image, finalWidth, finalHeight);
    }

    private ImageIcon getScaledIcon(Image image, int width, int height) {
        Image scaledImg = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImg);
    }

    public ImageIcon resultImage(Image image, int frameWidth) {
        int targetWidth = frameWidth * 4 / 10;

        int imageWidth = image.getWidth(null);
        int imageHeight = image.getHeight(null);
        double ratio = (double) targetWidth / imageWidth;
        int targetHeight = (int) (imageHeight * ratio);

        return getScaledIcon(image, targetWidth, targetHeight);
    }
}
