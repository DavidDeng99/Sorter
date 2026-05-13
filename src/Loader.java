import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

public class Loader {

    public static ArrayList<Image> loadAssets() {
        ArrayList<Image> images = new ArrayList<>();
        Path dir = Paths.get("assets");

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {
            for (Path entry : stream) {
                try {
                    images.add(new Image(entry.toFile()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return images;
    }
}
