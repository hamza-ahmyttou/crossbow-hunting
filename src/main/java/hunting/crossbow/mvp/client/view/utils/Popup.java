package hunting.crossbow.client.view.utils;

import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class Popup {
    private static Popup current;


    private Text text;
    private int time;
    private int x;
    private int y;
    private Pane pane;
    private boolean running;

    /**
     * Constructor
     * @param text the text to draw
     * @param time the time popup is displayed
     * @param x the x position
     * @param y the y position
     * @param pane the pane to draw the popup
     */
    public Popup(String text, int time, int x, int y, Pane pane) {
        this.text = new Text(text);
        this.pane = pane;
        this.time = time;
        this.x = x;
        this.y = y;
        this.running = true;
    }

    /**
     * Constructor
     * @param text the text to draw
     * @param x the x position
     * @param y the y position
     * @param pane the pane to draw the popup
     */
    public Popup(String text, int x, int y, Pane pane) {
        this(text, -1, x, y, pane);
    }

    /**
     * Launch the popup
     */
    public void launch() {
        if(current != null) {
            current.remove();
        }
        pane.getChildren().add(text);
        text.setFill(Color.WHITE);
        Font font = Font.font("Verdana", FontWeight.BOLD, 15);
        text.setFont(font);
        double size = text.getText().length() * 7.2;
        text.relocate(x - size/2, y );
        current = this;
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(2), text);
        fadeTransition.setFromValue(1.0);
        fadeTransition.setToValue(0.0);
        fadeTransition.setCycleCount(Animation.INDEFINITE);
        fadeTransition.play();
        launchThread();
    }

    /**
     * Launch the popup thread
     */
    private void launchThread() {
        new Thread(() -> {
            while(time != 0 && running) {
                try {
                    Thread.sleep(1000);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                time--;
            }
            Platform.runLater(() -> {
                remove();
            });
        }).start();
    }

    /**
     * Stop the popup
     */
    public void remove() {
        running = false;
        pane.getChildren().remove(text);
    }

}
