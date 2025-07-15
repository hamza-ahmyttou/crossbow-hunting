package hunting.crossbow.server.model.events;

import java.util.EventObject;

public class ScoreEvent extends EventObject {


    private final int score;

    public ScoreEvent(Object source, int score) {

        super(source);
        this.score = score;
    }


    public int getScore() {
        return score;
    }

}
