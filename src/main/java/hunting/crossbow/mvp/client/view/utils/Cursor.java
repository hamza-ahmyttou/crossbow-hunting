package hunting.crossbow.client.view.utils;

import javafx.scene.ImageCursor;
import javafx.scene.image.Image;

import java.util.Objects;

public class Cursor {

    private static Cursor instance = new Cursor();

    /**
     * Get the instance of cursor
     * @return the instance of cursor
     */
    public static Cursor getInstance() {
        return instance;
    }

    /**
     * Constructor
     */
    private Cursor() {}

    /**
     * Get the shooter image
     * @return the imageCursor of the shooter image
     */
    public ImageCursor getShooter() {
        Image image = new Image(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("shooter.png")));
        return  new ImageCursor(image);
    }
}
