package hunting.crossbow.client.view.events;

import java.util.EventObject;

public class ClickEvent extends EventObject {

    private final double coordonnesArrowX;

    public double getCoordonnesArrowX() {
        return coordonnesArrowX;
    }

    public double getCoordonnesArrowY() {
        return coordonnesArrowY;
    }

    public int getSize() {
        return size;
    }

    public double getSpeedWind() {
        return speedWind;
    }

    public double getDistanceFromTargetZ() {
        return distanceFromTargetZ;
    }

    public int getId() {
        return id;
    }

    private final double coordonnesArrowY;
    private final int size;
    private final double speedWind;
    private final double distanceFromTargetZ;
    private final int id;


    /**
     * Creates a new instance
     *
     * @param source              the event's source
     * @param coordonnesArrowX    coordinate of the arrow on X
     * @param coordonnesArrowY    Coordinate of the arrox on Y
     * @param size                the size of the target
     * @param speedWind           the speed of wind
     * @param distanceFromTargetZ the distance from the target on Z
     * @param id                  the id of the player
     */

    public ClickEvent(Object source, double coordonnesArrowX, double coordonnesArrowY, int size, double speedWind, double distanceFromTargetZ, int id) {

        super(source);

        this.coordonnesArrowX = coordonnesArrowX;
        this.coordonnesArrowY = coordonnesArrowY;
        this.size = size;
        this.speedWind = speedWind;
        this.distanceFromTargetZ = distanceFromTargetZ;
        this.id = id;
    }
}
