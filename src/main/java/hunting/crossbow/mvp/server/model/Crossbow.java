
package hunting.crossbow.server.model;

/*
 * This class is to be used as a pattern of a real crossbow
 */
public class Crossbow {

    /*
     * "coordinates"  an array of crossbow's coordinates in
     * a space of n dimensions
     */
    private double coordinates[];

    public Crossbow() {
        this.coordinates = new double[2];
        this.coordinates[0] = 0;
        this.coordinates[1] = 0;
    }


    /**
     * allows a crossbow to move to a specific point in a space of 2 dimensions
     *
     * @param dx intial position on X - final position on X
     * @param dy intial position on Y - final position on Y
     * @return the modified coordinates : an array of the final position
     */
    public double[] move(double dx, double dy) {
        this.coordinates[0] += dx;
        this.coordinates[1] += dy;
        return this.coordinates;
    }
}
