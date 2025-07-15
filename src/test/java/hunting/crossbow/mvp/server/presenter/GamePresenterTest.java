package hunting.crossbow.server.presenter;

import hunting.crossbow.client.view.game_view.GameView;
import hunting.crossbow.client.view.game_view.IView;
import hunting.crossbow.server.model.GameModel;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public final class GamePresenterTest {
    private GamePresenter gamePresenter;

    @Before
    public void initilize() {
        this.gamePresenter = new GamePresenter();
        this.gamePresenter.setModel(new GameModel());
        this.gamePresenter.get_model().initPlayers(1, 2);
    }

    @Test
    public void testUpdateScoreAdversaire() {
        this.gamePresenter.updateScoreAdversaire("12345");
        Assert.assertEquals(12345, this.gamePresenter.get_model().getotherPlayer().getScore());
    }

}
