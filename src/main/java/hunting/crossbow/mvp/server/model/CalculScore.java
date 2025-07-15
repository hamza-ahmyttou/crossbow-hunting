
package hunting.crossbow.server.model;


import hunting.crossbow.server.model.events.ScoreEvent;
import hunting.crossbow.server.presenter.PresenterListener;
import static java.lang.Math.pow;
import static java.lang.Math.sqrt;


public class CalculScore {

    private PresenterListener _presenter;

    public void setPresenter( PresenterListener presenter ) {
        _presenter = presenter;
    }

    public  void calculScore(double x, double y, int targetSize, int graphicX,int graphicY){

        TargetModel target =  new TargetModel( targetSize, graphicX/2,graphicY/2);
        int point=0;
        double d=sqrt(pow(graphicX/2-x,2)+pow(graphicY/2-y,2));

        if(d>=0 && d<=targetSize/20){
            point=target.getCircleModels()[10].getPoints();
        }
        for(int i=0;i<11;i++){
            if(d>(targetSize/20)*(i-1) && d<=(targetSize/20)*i){
                point=target.getCircleModels()[10-i].getPoints();
            }
        }

        ScoreEvent event = new ScoreEvent(this, point);
        _presenter.updateScore(event);
    }
}

