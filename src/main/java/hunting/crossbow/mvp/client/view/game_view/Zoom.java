package hunting.crossbow.client.view.game_view;

import javafx.application.Platform;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;


import java.awt.*;
import java.util.Objects;



public class Zoom {
    private boolean stopped;
    private Canvas canvas;
    private int height;
    private int width;
    private int targetSize;

    /**
     * Constructor
     *
     * @param canvas     the canvas to draw
     * @param height     the zoom height
     * @param width      the zoom width
     * @param targetSize the size of the target
     */
    public Zoom(Canvas canvas, int height, int width, int targetSize) {
        this.canvas = canvas;
        this.height = height;
        this.width = width;
        this.targetSize = targetSize;
        this.stopped = false;
    }

    /**
     * Start the zoom thread
     */
    public void init() {
        new Thread(
                () -> {
                    while (!stopped) {
                        Platform.runLater(() -> {
                            Point p = MouseInfo.getPointerInfo().getLocation();
                            try {
                                update((int) p.getX(), (int) p.getY());
                            } catch (Exception e) {
                            }
                        });
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                }
        ).start();
    }

    /**
     * Update the view
     *
     * @param x the x position
     * @param y the y position
     */
    private void update(int x, int y) {
        SnapshotParameters params = new SnapshotParameters();
        params.setFill(Color.TRANSPARENT);
        WritableImage image = canvas.snapshot(params, null);
        PixelReader reader = image.getPixelReader();
        double zoom = 1.50;
        int sizeBox = 200;
        WritableImage newImage = new WritableImage(reader, (int) (x - 100 / zoom + 17), (int) (y - 100 / zoom + 17), (int) (200 / zoom), (int) (200 / zoom));
        canvas.getGraphicsContext2D().drawImage(newImage, width * 3 / 4 + sizeBox / 2, height / 2 - sizeBox / 2, sizeBox, sizeBox);
        drawPointer(width * 3 / 4 + 200, height / 2);
    }

    /**
     * Draw the zoom image
     *
     * @param x the x position
     * @param y the y position
     */
    private void drawPointer(int x, int y) {
        double zoom = 1.50;
        double pointerSize = 35 * zoom;
        javafx.scene.image.Image image = new Image(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("shooter.png")));
        canvas.getGraphicsContext2D().drawImage(image, x - pointerSize / 2, y - pointerSize / 2, pointerSize, pointerSize);
    }

    /**
     * Stop the zoom
     */
    public void stop() {
        this.stopped = true;
    }

}
