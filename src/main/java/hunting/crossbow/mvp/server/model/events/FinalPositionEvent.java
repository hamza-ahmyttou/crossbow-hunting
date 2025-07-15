package hunting.crossbow.server.model.events;

import java.util.EventObject;

public class FinalPositionEvent extends EventObject {


    private double[] coordinates;


    public FinalPositionEvent(Object source, double[] coordinates) {

        super(source);
        this.coordinates = coordinates;
    }


    public double[] getCoordinates() {
        return coordinates;
    }

}
