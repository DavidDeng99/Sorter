import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Collections;

public class Loader {

    public static ArrayList<ImageAsset> loadAssets() {
        ArrayList<ImageAsset> images = new ArrayList<>();
        Path dir = Paths.get("assets");

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {
            for (Path entry : stream) {
                try {
                    images.add(new ImageAsset(entry.toFile()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        Collections.shuffle(images);
        return images;
    }
}
