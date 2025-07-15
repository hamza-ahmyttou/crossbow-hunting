package hunting.crossbow.client.view.events;

import java.util.EventObject;

public class UpdateScoreAdvEvent extends EventObject {


    private final int scoreAdv;

    public int getScoreAdv() {
        return scoreAdv;
    }


    public UpdateScoreAdvEvent(Object source, int scoreAdv) {

        super(source);
        this.scoreAdv = scoreAdv;
    }
}
