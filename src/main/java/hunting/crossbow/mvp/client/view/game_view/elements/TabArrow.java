package hunting.crossbow.client.view.game_view.elements;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import java.util.Objects;


public class TabArrow {
    private Text text;
    private int y;
    private int x;
    private Pane pane;

    private static TabArrow lastTabArrow;

    /**
     * Constructor
     *
     * @param x    the x position
     * @param y    the y position
     * @param pane the pane to draw
     */
    public TabArrow(int x, int y, Pane pane) {
        if (lastTabArrow != null) {
            lastTabArrow.remove();
        }
        lastTabArrow = this;

        this.x = x;
        this.y = y;
        this.pane = pane;
        text = new Text();
        pane.getChildren().add(text);
    }

    /**
     * Draw the tab arrow
     *
     * @param context the context to draw
     * @param arrows  the amount of arrows
     */
    public void draw(GraphicsContext context, int arrows) {
        Image image = new Image(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("arrows_num.png")));
        context.drawImage(image, x - image.getWidth() / 2, y - image.getHeight() / 2);
        text.setText("" + arrows);
        text.setFill(Color.WHITE);
        Font font = Font.font("Verdana", FontWeight.NORMAL, 30);
        text.setFont(font);
        double size = text.getText().length() * 7.2;
        text.relocate(x - size + 5, y - 20);
    }

    /**
     * Remove the tab arrow
     */
    public void remove() {
        pane.getChildren().remove(text);
    }
}
