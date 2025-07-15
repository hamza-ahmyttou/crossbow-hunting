package hunting.crossbow.server.model;
import hunting.crossbow.server.model.events.FinalPositionEvent;
import hunting.crossbow.server.presenter.PresenterListener;

/*
 * This class models a bolt and takes charge of it's movement
 */
public class Bolt {
    /**
     * "w"  the weight of the bolt
     * "v0"  the initial speed
     * "coordinates"  an array containing the coordinates
     * of the bolt in the time of calling it
     */
    private double w;
    private double v0;
    private double[] coordinates;
    private PresenterListener _presenter;

    public void setPresenter(PresenterListener presenter) {
        _presenter = presenter;
    }

    public Bolt(double w, double v0) {
        this.w = w; // w?
        this.v0 = v0; // v0?
        this.coordinates = new double[3];
        this.coordinates[0] = 0;
        this.coordinates[1] = 0;
        this.coordinates[2] = 0;
    }

    public static double round(double value, int places) { // A quoi sert ?
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }


    /**
     * takes charge of the movement of the bolts
     * provides the final coordinates of the bolt
     * takes 4 parameters:
     *
     * @param x0   hoped first coordinate
     * @param y0   hoped second coordinate
     * @param z0   distance between the bolt and the target
     * @param wind the intensity of wind
     */
    public void fly(double x0, double y0, double z0, double wind) {
        double g = 10;

        double P[] = {wind, wind - this.w * g, wind};

        double vv0[] = {
                v0 * Math.cos(Math.atan(y0 / Math.sqrt(Math.pow(z0, 2)
                        + Math.pow(x0, 2)))) * Math.sin(Math.atan(x0 / z0)),
                v0 * Math.cos(Math.atan(x0 / Math.sqrt(Math.pow(z0, 2)
                        + Math.pow(y0, 2)))) * Math.sin(Math.atan(y0 / z0)),
                v0 * Math.cos(Math.atan(y0 / Math.sqrt(Math.pow(z0, 2)
                        + Math.pow(x0, 2)))) * Math.cos(Math.atan(x0 / z0))
        };

        double tf = Math.sqrt(2 * this.w / P[2])
                * (Math.sqrt(z0 + Math.pow(vv0[2], 2) * this.w / 2 / P[2])
                - Math.sqrt(2 * this.w / P[2]) * vv0[2] / 2);

        for (int i = 0; i < 3; i++) {
            this.coordinates[i] = round(P[i] * Math.pow(tf, 2) / this.w / 2 + vv0[i] * tf, 1);
        }

        FinalPositionEvent event = new FinalPositionEvent(this, this.coordinates);
        _presenter.updateFinalPosition(event);

    }

}