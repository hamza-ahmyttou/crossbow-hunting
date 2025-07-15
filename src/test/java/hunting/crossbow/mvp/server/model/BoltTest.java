package hunting.crossbow.server.model;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BoltTest {
    private Bolt bolt;


    @Before
    public void initialize() {
        this.bolt = new Bolt(20, 4);

    }

    @Test
    public void roundTest() {
        Assert.assertEquals(14, bolt.round(14, 5), 0.001);
    }


}
