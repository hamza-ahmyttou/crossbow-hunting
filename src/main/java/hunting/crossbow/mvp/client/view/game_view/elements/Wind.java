package hunting.crossbow.client.view.game_view.elements;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import java.util.Objects;

public class Wind {
    private Text text;
    private int y;
    private int x;
    private double degrees;
    private Pane pane;

    /**
     * Constructor
     *
     * @param x       the x position
     * @param y       the y position
     * @param pane    the pane to draw
     * @param degrees the degrees of the wind
     */
    public Wind(int x, int y, Pane pane, double degrees) {
        this.x = x;
        this.y = y;
        text = new Text();
        this.pane = pane;
        pane.getChildren().add(text);
        this.degrees = degrees;
    }

    /**
     * Draw the wind
     *
     * @param context the context to draw
     * @param speed   the speed wind
     */
    public void draw(GraphicsContext context, int speed) {
        Image image = new Image(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("wind.png")));
        context.drawImage(image, x - image.getWidth() / 2, y - image.getHeight() / 2);
        Image indicator = new Image(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("indicator.png")));
        ImageView imageView = new ImageView(indicator);
        imageView.setX(x - indicator.getWidth() / 2 - 125);
        imageView.setY(y - indicator.getHeight() / 2);
        imageView.setRotate(degrees);
        pane.getChildren().add(imageView);
        text.setText("Vent : " + speed);
        text.setFill(Color.WHITE);
        Font font = Font.font("Verdana", FontWeight.BOLD, 25);
        text.setFont(font);
        double size = text.getText().length() * 7.2;
        text.relocate(x - size, y - 18);
    }
}
