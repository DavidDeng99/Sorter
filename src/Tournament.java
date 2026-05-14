import javax.swing.*;
import java.util.ArrayList;

public abstract class Tournament {
    protected ArrayList<ImageAsset> images;
    protected ImageHandler imageHandler;
    protected AppFrame appFrame; // reference back to the frame

    public Tournament(ArrayList<ImageAsset> images, AppFrame appFrame) {
        this.appFrame = appFrame;
        this.images = images;
        this.imageHandler = new ImageHandler();
    }

    abstract void startTournament(JButton redButton, JButton blueButton, JLabel status);
}
