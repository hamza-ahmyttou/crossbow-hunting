package hunting.crossbow.server.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CrossbowTest {

    private Crossbow crossbow;


    @Before
    public void initialize() {
        this.crossbow = new Crossbow();

    }

    @Test
    public void moveTest() {
        Assert.assertArrayEquals(new double[]{1, 1}, crossbow.move(1, 1), 0);
    }

}
