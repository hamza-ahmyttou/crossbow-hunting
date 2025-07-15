package hunting.crossbow.client.view.events;

import java.util.EventObject;

public class PauseEvent extends EventObject {

    private final boolean pause;


    /**
     * Creates a new instance
     *
     * @param source the event's source
     * @param pause  true for pause and false if not
     */
    public PauseEvent(Object source, Boolean pause) {

        super(source);
        this.pause = pause;
    }


    public boolean isPause() {
        return pause;
    }
}