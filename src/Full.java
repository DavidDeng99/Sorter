import javax.swing.*;
import java.util.ArrayList;
import java.util.Comparator;

public class Full extends Tournament {
    private final ArrayList<ScoredImage> scoredImages = new ArrayList<>();
    private int rounds = 0;
    public record IntPair(int first, int second) {}
    private final int min_rounds;
    private boolean seeding_phase = true;
    private final int instability_threshold;
    private IntPair currPair;

    public Full(ArrayList<ImageAsset> images, AppFrame appFrame) {
        super(appFrame);
        for (ImageAsset i : images) {
            this.scoredImages.add(new ScoredImage(i, 0));
        }
        this.min_rounds = (int) (scoredImages.size() * Math.log(scoredImages.size()));
        this.instability_threshold = (int) (0.05 * scoredImages.size());
    }

    protected void startTournament(JButton redButton, JButton blueButton, JLabel status) {
        clearListeners(redButton);
        clearListeners(blueButton);

        redButton.setText(null);
        blueButton.setText(null);

        redButton.addActionListener(e -> update(true, redButton, blueButton, status));

        blueButton.addActionListener(e -> update(false, redButton, blueButton, status));

        updateCurrPair();
        imageHandler.updateButton(redButton, scoredImages.get(currPair.first()).getImageAsset());
        imageHandler.updateButton(blueButton, scoredImages.get(currPair.second()).getImageAsset());
        updateStatus(status);
    }

    private void update(boolean redWin, JButton redButton, JButton blueButton, JLabel status) {
        if (stoppingCondition()) {
            scoredImages.sort(Comparator.comparing(ScoredImage::getScore));
            System.out.println("Rounds played: " + rounds);
            appFrame.displayFullResults(scoredImages);
            return;
        }

        rounds += 1;
        if (rounds >= (scoredImages.size() + 1) / 2) {
            seeding_phase = false;
        }

        double p = calculate_p(currPair.first(), currPair.second());

        int outcome = redWin ? 1 : 0;
        ScoredImage image1 = scoredImages.get(currPair.first());
        ScoredImage image2 = scoredImages.get(currPair.second());
        double learning_rate = 2;
        image1.setScore(image1.getScore() + learning_rate * (outcome - p));
        image2.setScore(image2.getScore() - learning_rate * (outcome - p));

        updateCurrPair();
        imageHandler.updateButton(redButton, scoredImages.get(currPair.first()).getImageAsset());
        imageHandler.updateButton(blueButton, scoredImages.get(currPair.second()).getImageAsset());
        updateStatus(status);
    }

    private void updateCurrPair() {
        if (seeding_phase) {
            int first = rounds * 2;
            int second = rounds * 2 + 1;
            if (second > scoredImages.size() - 1) {
                second = 0;
            }

            currPair = new IntPair(first, second);
            return;
        }

        if (Math.random() < 0.2) {
            int first = (int) Math.floor(Math.random() * scoredImages.size());
            int second = (int) Math.floor(Math.random() * scoredImages.size());
            while (second == first) {
                second = (int) Math.floor(Math.random() * scoredImages.size());
            }

            currPair = new IntPair(first, second);
            return;
        }

        ArrayList<ScoredImage> sorted = new ArrayList<>(scoredImages);
        sorted.sort(Comparator.comparing(ScoredImage::getScore));

        int sortedIdx1 = (int) (Math.random() * sorted.size());

        int sortedIdx2 = sortedIdx1 == 0 ? 1 :
                sortedIdx1 == sorted.size() - 1 ? sorted.size() - 2 :
                        (Math.random() < 0.5 ? sortedIdx1 - 1 : sortedIdx1 + 1);

        int firstIndex = scoredImages.indexOf(sorted.get(sortedIdx1));
        int secondIndex = scoredImages.indexOf(sorted.get(sortedIdx2));

        currPair = new IntPair(firstIndex, secondIndex);
    }

    protected void updateStatus(JLabel status) {
        status.setText("Rounds played: " + (rounds + 1));
    }

    protected double uncertainty(int i, int j) {
        double p = calculate_p(i, j);
        return 1 - Math.abs(p - 0.5) * 2;
    }

    private boolean stoppingCondition() {
        if (rounds < min_rounds) {
            return false;
        }

        ArrayList<ScoredImage> sortedCopy = new ArrayList<>(scoredImages);
        sortedCopy.sort(Comparator.comparing(ScoredImage::getScore));

        int instability = 0;
        double p;
        for (int i = 0; i < sortedCopy.size() - 1; i++) {
            p = Math.exp(sortedCopy.get(i).getScore()) /
                    (Math.exp(sortedCopy.get(i).getScore()) + Math.exp(sortedCopy.get(i + 1).getScore()));
            if (0.4 < p && p < 0.6) {
                instability += 1;
            }
        }

        return instability <= instability_threshold;
    }

    private double calculate_p(int idx1, int idx2) {
        return Math.exp(scoredImages.get(idx1).getScore()) /
                (Math.exp(scoredImages.get(idx1).getScore()) + Math.exp(scoredImages.get(idx2).getScore()));
    }
}
