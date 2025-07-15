package hunting.crossbow.client.view.game_view.elements;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.Objects;

public class Arrow {
    private double x;
    private double y;
    private ArrowColor color;
    private int size;

    /**
     * Constructor
     *
     * @param x     the x position
     * @param y     the y position
     * @param color the color of the arrow
     * @param size  the size of the arrow
     */
    public Arrow(double x, double y, ArrowColor color, int size) {
        this.x = x;
        this.y = y;
        this.color = color;
        this.size = size;
    }

    /**
     * Constructor
     *
     * @param color the arrow color
     */
    public Arrow(ArrowColor color) {
        this.color = color;
    }

    /**
     * Get the arrow color
     *
     * @return the arrow color
     */
    public ArrowColor getColor() {
        return color;
    }

    /**
     * Draw the arrow
     *
     * @param context the contect to draw
     */
    public void draw(GraphicsContext context) {
        Image image = new Image(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(color.getPath())));
        context.drawImage(image, x - size / 1.62, y - size / 1.9, size, size);
    }

}
