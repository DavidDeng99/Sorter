import java.util.ArrayList;

public class Sorter {
    private ArrayList<ImageAsset> images;

    public Sorter() {
        this.images = Loader.loadAssets();
    }

    public static void main(String[] args) {
        Sorter sorter = new Sorter();
        AppFrame frame = new AppFrame(sorter);
        frame.show();
    }

    public ArrayList<ImageAsset> getImages() {
        return images;
    }
}