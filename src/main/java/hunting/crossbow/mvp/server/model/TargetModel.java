package hunting.crossbow.server.model;



public class TargetModel {
    private int size;
    private int x;
    private int y;
    private  CircleModel[] CircleModels = new CircleModel[11] ;

    public TargetModel(int size, int x, int y) {
        this.size = size;
        this.x = x - size/2;
        this.y = y - size/2;
        for (int i=1;i<12;i++)
            CircleModels[i-1]=new CircleModel(i,i,i*15);

    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public CircleModel[] getCircleModels() {
        return CircleModels;
    }




}
