public class ScoredImage {
    private final ImageAsset imageAsset;
    private double score;

    public ScoredImage(ImageAsset imageAsset, int score) {
        this.imageAsset = imageAsset;
        this.score = score;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public ImageAsset getImageAsset() {
        return imageAsset;
    }
}
