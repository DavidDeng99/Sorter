import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class AppFrame {
    Sorter sorter; // maintain a reference to the main class
    Tournament tournament;
    private JFrame frame;
    private JPanel masterPanel;
    private JLabel headerLabel;
    ImageHandler imageHandler;

    public AppFrame(Sorter sorter) {
        this.sorter = sorter;
        this.imageHandler = new ImageHandler();
        frame = new JFrame("Sorter");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setExtendedState(Frame.MAXIMIZED_BOTH);
        frame.setMinimumSize(new java.awt.Dimension(1067, 600));

        masterPanel = new JPanel(new BorderLayout());
        masterPanel.setBorder(new EmptyBorder(30, 30, 30, 30));
        masterPanel.setBackground(Color.DARK_GRAY);

        headerLabel = new JLabel("Select one option:", SwingConstants.CENTER);
        headerLabel.setFont(new Font("SansSerif", Font.BOLD, 28));
        headerLabel.setForeground(new Color(235, 235, 235));
        headerLabel.setBorder(new EmptyBorder(0, 0, 0, 0));

        JPanel container = getjPanel();
        masterPanel.add(headerLabel, BorderLayout.NORTH);
        masterPanel.add(container, BorderLayout.CENTER);
        frame.add(masterPanel);
    }

    private JPanel getjPanel() {
        JPanel container = new JPanel(new BorderLayout());
        container.setOpaque(false);
        container.setBorder(new EmptyBorder(50, 30, 30, 30));

        JLabel status = new JLabel("", SwingConstants.CENTER);
        status.setFont(new Font("SansSerif", Font.PLAIN, 16));
        status.setForeground(new Color(235, 235, 235));
        container.add(status, BorderLayout.NORTH);

        JPanel pairPanel = new JPanel(new GridLayout(1, 2, 50, 50));
        pairPanel.setOpaque(false);

        Selection redButton = new Selection("Single elimination tournament (one winner)", new Color(231, 76, 60));
        Selection blueButton = new Selection("General tournament (exact ranking)", new Color(52, 152, 219));

        redButton.addActionListener(e -> {
            tournament = new Single(sorter.getImages(), this);
            tournament.startTournament(redButton, blueButton, status);
            headerLabel.setText("Select the better image:");
        });

        blueButton.addActionListener(e -> {
            // tournament = new Full(sorter.getImages());
        });

        pairPanel.add(redButton);
        pairPanel.add(blueButton);
        container.add(pairPanel);
        return container;
    }

    public void show() {
        frame.setVisible(true);
    }

    public void displaySingleResults(ImageAsset winner) {
        // clear the middle part
        BorderLayout layout = (BorderLayout) masterPanel.getLayout();
        Component centerComp = layout.getLayoutComponent(BorderLayout.CENTER);
        masterPanel.remove(centerComp);

        headerLabel.setText("The winner is " + winner.getName());
        JPanel resultsContainer = new JPanel();
        resultsContainer.setLayout(new BoxLayout(resultsContainer, BoxLayout.Y_AXIS));
        resultsContainer.setOpaque(false);
        resultsContainer.setBorder(new EmptyBorder(40, 0, 0, 0));

        JLabel imageLabel = new JLabel(imageHandler.resultImage(winner.getImage(), frame.getWidth()));
        imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        resultsContainer.add(imageLabel);
        masterPanel.add(resultsContainer, BorderLayout.CENTER);

        // 6. Refresh the UI
        masterPanel.revalidate();
        masterPanel.repaint();
    }
}