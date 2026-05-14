import javax.swing.*;
import java.awt.event.ActionListener;

public abstract class Tournament {
    protected ImageHandler imageHandler;
    protected AppFrame appFrame; // reference back to the frame

    public Tournament(AppFrame appFrame) {
        this.appFrame = appFrame;
        this.imageHandler = new ImageHandler();
    }

    abstract void startTournament(JButton redButton, JButton blueButton, JLabel status);

    protected void clearListeners(JButton button) {
        for (ActionListener al : button.getActionListeners()) {
            button.removeActionListener(al);
        }
    }

    abstract void updateStatus(JLabel status);

}
