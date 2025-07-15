package hunting.crossbow.client.view.game_view;

import hunting.crossbow.server.presenter.IPresenter;
import hunting.crossbow.server.presenter.PresenterListener;

public interface IView {






    void setPresenter(PresenterListener presenter);


    void setScore(String score);
    void setNumberOfArrowsLeft(String arrows);
    void setPositionAfterWind(String[] coordinates);
    void setIdPlayer(String id);
    void setWinPlayer(String winPlayer);

    void sendData();
    void sendPause();
    void setPause(String pause);

}
