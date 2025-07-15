package hunting.crossbow.client.view.utils;

import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

public class CustomButton extends Button{
    private String imgUrl;
    private String imgUrlHover;
    private int x;
    private  int y;

    /**
     * Constructor
     * @param imgUrl the name of the image to draw (from res folder)
     * @param x the x position
     * @param y the y position
     * @param onClick the action to launch by clicking on the button
     */
    public CustomButton(String imgUrl, int x, int y, Runnable onClick) {
        super("");
        this.imgUrl = imgUrl;
        this.x = x;
        this.y = y;
        formate(imgUrl);
        this.setStyle("-fx-background-color:transparent");
        this.setOnAction(event -> onClick.run());
        this.setCursor(Cursor.HAND);
    }

    /**
     * Constructor
     * @param imgUrl the name of the image to draw (from res folder)
     * @param x the x position
     * @param y the y position
     * @param onClick the action to launch by clicking on the button
     * @param imgUrlHover the name of the image to draw while hovering (from res folder)
     */
    public CustomButton(String imgUrl, int x, int y, Runnable onClick, String imgUrlHover) {
        this(imgUrl,x, y, onClick);
        this.imgUrlHover = imgUrlHover;
        this.setOnMouseEntered(event -> onHover());
        this.setOnMouseExited(event -> onStopHover());
    }

    /**
     * Action when the button is hovering
     */
    private void onHover() {
        CursorVelocity.stop();
        formate(imgUrlHover);
    }

    /**
     * Action when the button is no longer hovering
     */
    private void onStopHover() {
        CursorVelocity.launch(CursorVelocity.getLastSize());
        formate(imgUrl);
    }

    /**
     * Change the button image
     * @param imgUrlHover the name of the image to draw (from res folder)
     */
    private void formate(String imgUrlHover) {
        Image image = new Image(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(imgUrlHover)));
        ImageView view = new ImageView(image);
        this.setLayoutX(x - image.getWidth()/2);
        this.setLayoutY(y- image.getHeight()/2);
        this.setGraphic(view);
    }
}
