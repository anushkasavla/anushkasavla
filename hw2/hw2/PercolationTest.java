package hw2;

import org.junit.jupiter.api.Test;
import static org.junit.Assert.*;

public class PercolationTest {

    @Test
    public void xtoYtst() {
        Percolation trial = new Percolation(2);
        trial.open(0,0);
        trial.open(1,1);
        assertTrue(trial.percolates());
    }
}
