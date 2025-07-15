package hunting.crossbow.server.presenter;

import hunting.crossbow.client.view.events.ClickEvent;
import hunting.crossbow.client.view.events.PauseEvent;
import hunting.crossbow.server.model.events.FinalPositionEvent;
import hunting.crossbow.server.model.events.ScoreEvent;
import hunting.crossbow.server.model.events.WinEvent;
import org.w3c.dom.events.EventListener;

public interface PresenterListener extends EventListener {


     void sayScore(ClickEvent event);

     void updateScore(ScoreEvent event);

     void updateFinalPosition(FinalPositionEvent event);

     void updateWin(WinEvent event);

     void sayPause(PauseEvent event);



}
