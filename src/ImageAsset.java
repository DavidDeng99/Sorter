import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class ImageAsset {
    private final String name;
    private final Image image;

    public ImageAsset(File file) throws IOException {
        String fileName = file.getName();
        this.name = fileName.split("\\.")[0];
        image = ImageIO.read(file);
    }

    public String getName() {
        return name;
    }

    public Image getImage() {
        return image;
    }
}
