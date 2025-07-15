package hunting.crossbow.client.view.game_view.elements;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import java.util.Objects;


public class Score {
    private Text text;
    private int y;
    private int x;
    private boolean adversaire;
    private Pane pane;

    private static Score lastAdversaire;
    private static Score lastPlayer;

    /**
     * Constructor
     *
     * @param x          the x position
     * @param y          the y position
     * @param pane       the pane to draw
     * @param adversaire true if current player, false if oponent
     */
    public Score(int x, int y, Pane pane, boolean adversaire) {
        if (adversaire) {
            if (lastAdversaire != null) {
                lastAdversaire.remove();
            }
            lastAdversaire = this;
        } else {
            if (lastPlayer != null) {
                lastPlayer.remove();
            }
            lastPlayer = this;
        }
        this.x = x;
        this.y = y;
        this.adversaire = adversaire;
        this.pane = pane;
        text = new Text();
        pane.getChildren().add(text);
    }

    /**
     * Draw the score
     *
     * @param context the context to draw
     * @param score   the new score
     */
    public void draw(GraphicsContext context, int score) {
        Image image = new Image(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("score.png")));
        context.drawImage(image, x - image.getWidth() / 2, y - image.getHeight() / 2);
        setText(score);
        text.setFill(Color.WHITE);
        Font font = Font.font("Verdana", FontWeight.BOLD, 25);
        text.setFont(font);
        double size = text.getText().length() * 7.2;
        text.relocate(x - size, y - 18);
        drawLogo(context, image.getWidth() / 4);
    }

    /**
     * Change score
     *
     * @param score the new score
     */
    private void setText(int score) {
        if (adversaire) {
            text.setFill(Color.RED);
            text.setText("Score adversaire : " + score);
        } else {
            text.setFill(Color.GREEN);
            text.setText("Score : " + score);
        }
    }

    /**
     * Draw the logo
     *
     * @param context the context to draw
     * @param size    the size of the logo
     */
    private void drawLogo(GraphicsContext context, double size) {
        Image image;
        if (adversaire) {
            image = new Image(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("enemy.png")));
        } else {
            image = new Image(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("player.png")));
        }
        context.drawImage(image, x - image.getWidth() / 2 - size, y - image.getHeight() / 8, 50, 50);
    }

    /**
     * Remove the score text
     */
    private void remove() {
        pane.getChildren().remove(text);
    }
}
