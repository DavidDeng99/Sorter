import javax.swing.*;
import java.util.ArrayList;

public abstract class Tournament {
    protected ArrayList<ImageAsset> images;

    public Tournament(ArrayList<ImageAsset> images) {
        this.images = images;
    }

    abstract void startTournament(JButton redButton, JButton blueButton);
}
