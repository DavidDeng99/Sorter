import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Single extends Tournament {
    int byes; // if the number of images isn't a power of 2
    int iterationsRemaining;
    int numRounds; // for the current iteration
    int currRound = 0; // for the current iteration
    ArrayList<ImageAsset> nextRound;

    public Single(ArrayList<ImageAsset> images) {
        super(images);
        nextRound = new ArrayList<>();
        setParameters(images.size());
    }

    void startTournament(JButton redButton, JButton blueButton) {
        clearListeners(redButton);
        clearListeners(blueButton);

        redButton.setText(null);
        blueButton.setText(null);

        redButton.addActionListener(e -> {
            update(2 * currRound, redButton, blueButton);
            if (iterationsRemaining == 0) {
                return;
            }
        });

        blueButton.addActionListener(e -> {
            update(2 * currRound + 1, redButton, blueButton);
            if (iterationsRemaining == 0) {
                return;
            }
        });

        displayImages(redButton, blueButton);

    }

    private void clearListeners(JButton button) {
        for (ActionListener al : button.getActionListeners()) {
            button.removeActionListener(al);
        }
    }

    private void update(int idx, JButton redButton, JButton blueButton) {
        System.out.println("One person eliminated");
        if (iterationsRemaining == 0) {
            return;
        }
        nextRound.add(images.get(idx));
        currRound++;
        if (currRound == numRounds) {
            iterationsRemaining -= 1;
            if (iterationsRemaining == 0) {
                System.out.println("Successful");
                System.out.println(nextRound.size());
                return;
            }

            numRounds = (int) Math.pow(2, iterationsRemaining - 1);
            currRound = 0;

            images = nextRound;
            nextRound = new ArrayList<>();
        }

        displayImages(redButton, blueButton);
    }

    public void setParameters(int num) {
        int power2 = 1;
        int power = 0;
        while (power2 < num) {
            power2 *= 2;
            power++;
        }

        iterationsRemaining = power;
        if (power2 == num) {
            numRounds = num / 2;
            return;
        }

        // In each round two people are compared
        int numRem = (int) Math.pow(2, power - 1); // we want a power of 2 remaining after the first iteration;

        numRounds = num - numRem; // Get to power of 2 in the first iteration.

        int byes = num - numRounds * 2;

        for (int i = 0; i < byes; i++) {
            nextRound.add(images.get(num - 1 - i));
        }
    }

    private ImageIcon getScaledIcon(Image image, int width, int height) {
        Image scaledImg = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImg);
    }

    private void displayImages(JButton redButton, JButton blueButton) {
        Image blueImg = images.get(2 * currRound).getImage();
        Image redImg = images.get(2 * currRound + 1).getImage();

        blueButton.setIcon(getAutoScaledIcon(blueImg, blueButton));
        redButton.setIcon(getAutoScaledIcon(redImg, redButton));
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

}
