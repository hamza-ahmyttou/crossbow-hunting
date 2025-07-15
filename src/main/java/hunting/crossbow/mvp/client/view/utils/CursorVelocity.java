package hunting.crossbow.client.view.utils;

import javafx.application.Platform;
import javafx.scene.robot.Robot;

import java.awt.*;
import java.util.Random;

public class CursorVelocity {

    private static boolean stopped;
    private static double lastSize = 0;

    /**
     * Get the last size of the cursor velocity
     * @return the last size of the cursor velocity
     */
    public static double getLastSize() {
        return lastSize;
    }

    /**
     * Start the cursor effect
     * @param size the size of the effect
     */
    public static void launch(double size) {
        lastSize = size;
        final double fsize = size / 50;
        stopped = false;
        new Thread(() -> {
            int i = 0;
            double randX = 0;
            double randY = 0;
            Random random = new Random();
            while(!stopped) {
                if(i % 10 == 0) {
                    randY = random.nextDouble()*2-1;
                    randX = random.nextDouble()*2-1;
                }
                final double rX = randX;
                final double rY = randY;
                Platform.runLater(() -> {
                    Point p = MouseInfo.getPointerInfo().getLocation();
                    Robot robot = new Robot();
                    double posX = (p.getX()+rX*fsize);
                    double posY = (p.getY()+rY*fsize);
                    robot.mouseMove(posX, posY);
                });
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                i++;
            }
        }).start();
    }

    /**
     * Stop the effect
     */
    public static void stop() {
        stopped = true;
    }
}
