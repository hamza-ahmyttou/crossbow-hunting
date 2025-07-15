package hunting.crossbow.server.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class GameModelTest {
    private GameModel gameModel;

    @Before
    public void initialize() {
        this.gameModel = new GameModel();
        this.gameModel.initPlayers(11111, 22222);
        this.gameModel.setIdPlayer("1");
    }

    @Test
    public void testSetScore() {
        gameModel.setScore(12345);
        Assert.assertEquals(12345, gameModel.getPlayer().getScore());
    }

    @Test
    public void testSetPositionAfterWind() {
        double[] a = new double[]{1, 1, 30};
        gameModel.setPositionAfterWind(a);
        Assert.assertArrayEquals(new double[]{1, 1, 30}, gameModel.get_positionAfterWind(), 0);
    }
}