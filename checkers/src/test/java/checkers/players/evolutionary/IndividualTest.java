package checkers.players.evolutionary;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class IndividualTest {

    @Test
    public void testIndividualCreation() {
        Individual ind = new Individual();
        Assert.assertNotNull("Expected individual creation to be successful",
                ind);
    }
}

