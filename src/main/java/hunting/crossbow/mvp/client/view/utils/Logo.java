package hunting.crossbow.client.view.utils;


import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import java.util.Objects;

public class Logo {
    private int x;
    private int y;

    /**
     * Constructor
     * @param x the x position
     * @param y the y position
     */
    public Logo(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Draw the logo
     * @param context the context to draw
     */
    public void draw(GraphicsContext context) {
        int width = 300;
        int height = 150;
        Image image = new Image(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("logo.png")));
        context.drawImage(image, x -width/2, y - height/2, width, height);
    }

}
