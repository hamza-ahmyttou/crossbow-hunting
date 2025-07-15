package hunting.crossbow.server.model.events;

import java.util.EventObject;

public class WinEvent extends EventObject {


    private final boolean winPlayer;
    private final boolean winOtherPlayer;


    public WinEvent(Object source, boolean winPlayer, boolean winOtherPlayer) {

        super(source);
        this.winPlayer = winPlayer;
        this.winOtherPlayer = winOtherPlayer;

    }

    public boolean isWinPlayer() {
        return winPlayer;
    }

    public boolean isWinOtherPlayer() {
        return winOtherPlayer;
    }
}
