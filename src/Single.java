import javax.swing.*;
import java.util.ArrayList;

public class Single extends Tournament {
    int roundsRemaining;
    int numMatches; // for the current round
    int currMatch = 0; // in the current round
    boolean byes = false; // Are we handling byes right now
    private ArrayList<ImageAsset> nextRound;
    private ArrayList<ImageAsset> images;

    public Single(ArrayList<ImageAsset> images, AppFrame appFrame) {
        super(appFrame);
        this.images = images;
        nextRound = new ArrayList<>();
        setParameters(images.size());
    }

    protected void startTournament(JButton redButton, JButton blueButton, JLabel status) {
        clearListeners(redButton);
        clearListeners(blueButton);

        redButton.setText(null);
        blueButton.setText(null);

        redButton.addActionListener(e -> update(2 * currMatch, redButton, blueButton, status));

        blueButton.addActionListener(e -> update(2 * currMatch + 1, redButton, blueButton, status));

        imageHandler.updateButton(redButton, images.get(2 * currMatch));
        imageHandler.updateButton(blueButton,  images.get(2 * currMatch + 1));
        updateStatus(status);
    }

    private void update(int idx, JButton redButton, JButton blueButton, JLabel status) {
        if (roundsRemaining == 0) {
            return;
        }
        nextRound.add(images.get(idx));
        currMatch++;
        if (currMatch == numMatches) {
            roundsRemaining -= 1;
            byes = false;
            if (roundsRemaining == 0) {
                appFrame.displaySingleResults(nextRound.getFirst());
                return;
            }

            numMatches = (int) Math.pow(2, roundsRemaining - 1);
            currMatch = 0;

            images = nextRound;
            nextRound = new ArrayList<>();
        }

        imageHandler.updateButton(redButton, images.get(2 * currMatch));
        imageHandler.updateButton(blueButton, images.get(2 * currMatch + 1));
        updateStatus(status);
    }

    private void setParameters(int num) {
        int power2 = 1;
        int power = 0;
        while (power2 < num) {
            power2 *= 2;
            power++;
        }

        roundsRemaining = power;
        if (power2 == num) {
            numMatches = num / 2;
            return;
        }

        byes = true;

        // In each round two people are compared
        int numRem = (int) Math.pow(2, power - 1); // we want a power of 2 remaining after the first iteration;

        numMatches = num - numRem; // Get to power of 2 in the first iteration.

        int numByes = num - numMatches * 2;

        for (int i = 0; i < numByes; i++) {
            nextRound.add(images.get(num - 1 - i));
        }
    }

    protected void updateStatus(JLabel status) {
        if (byes) {
            status.setText("Handling byes: Match " + currMatch + " of " + numMatches);
        } else {
            status.setText("Round of " + (int) Math.pow(2, roundsRemaining) + ": Match " + currMatch + " of " + numMatches);
        }
    }

}
