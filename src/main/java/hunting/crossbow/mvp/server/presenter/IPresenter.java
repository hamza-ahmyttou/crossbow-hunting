package hunting.crossbow.server.presenter;

import hunting.crossbow.client.view.game_view.IView;
import hunting.crossbow.server.model.GameModel;

public interface IPresenter {

    void setModel( GameModel model );
    void addView( IView view );
}
