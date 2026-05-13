import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Image {
    private String name;
    private BufferedImage image;

    public Image(File file) throws IOException {
        String fileName = file.getName();
        this.name = fileName.split("\\.")[0];
        image = ImageIO.read(file);
    }

    public String getName() {
        return name;
    }

    public BufferedImage getImage() {
        return image;
    }
}
