package hunting.crossbow.server.model;



public class CircleModel {
    private final int id;
    private final int points;
    private final int ray;



    public CircleModel(int id, int points, int ray){

        this.id=id;
        this.points=points;
        this.ray=ray;

    }


    public int getId() {
        return id;
    }

    public int getPoints() {
        return points;
    }

    public int getRay() { return ray;  }

}

