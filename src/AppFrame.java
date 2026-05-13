import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class AppFrame {
    Sorter sorter; // maintain a reference to the main class
    Tournament tournament;

    private JFrame frame;

    public AppFrame(Sorter sorter) {
        this.sorter = sorter;
        frame = new JFrame("Sorter");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setExtendedState(Frame.MAXIMIZED_BOTH);
        frame.setMinimumSize(new java.awt.Dimension(1067, 600));

        JPanel masterPanel = new JPanel(new BorderLayout());
        masterPanel.setBorder(new EmptyBorder(30, 30, 30, 30));
        masterPanel.setBackground(Color.DARK_GRAY);

        JLabel headerLabel = new JLabel("Select one option:", SwingConstants.CENTER);
        headerLabel.setFont(new Font("SansSerif", Font.BOLD, 28));
        headerLabel.setForeground(new Color(235, 235, 235));
        headerLabel.setBorder(new EmptyBorder(0, 0, 0, 0));

        JPanel container = getjPanel(sorter);
        masterPanel.add(headerLabel, BorderLayout.NORTH);
        masterPanel.add(container, BorderLayout.CENTER);
        frame.add(masterPanel);
    }

    private JPanel getjPanel(Sorter sorter) {
        JPanel container = new JPanel(new GridLayout(1, 2, 50, 0));
        container.setOpaque(false);
        container.setBorder(new EmptyBorder(50, 30, 30, 30));

        Selection redButton = new Selection("Single elimination tournament (one winner)", new Color(231, 76, 60));
        Selection blueButton = new Selection("General tournament (exact ranking)", new Color(52, 152, 219));

        redButton.addActionListener(e -> {
            tournament = new Single(sorter.getImages());
        });

        blueButton.addActionListener(e -> {
            tournament = new Full(sorter.getImages());
        });

        container.add(redButton);
        container.add(blueButton);
        return container;
    }

    public void show() {
        frame.setVisible(true);
    }
}