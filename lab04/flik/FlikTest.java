package flik;

import static flik.Flik.isSameNumber;
import static org.junit.Assert.*;
import org.junit.Test;


public class FlikTest {

    @Test
    public void testNines() {
        Integer i = 9;
        Integer j = 9;
        boolean newtest = isSameNumber(i, j);
        assertTrue(newtest);
    }

}
