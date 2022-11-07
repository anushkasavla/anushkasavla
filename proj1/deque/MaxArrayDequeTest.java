package deque;
import org.junit.Test;

import java.util.Comparator;
import java.util.Iterator;
import static org.junit.Assert.*;

public class MaxArrayDequeTest {
    @Test
    public void generic() {
        MaxArrayDeque<Integer> ArrayDeque = new MaxArrayDeque<Integer>(Comparator.naturalOrder());
        ArrayDeque.addFirst(0);
        ArrayDeque.addFirst(10);
        ArrayDeque.addFirst(12);
        ArrayDeque.addFirst(3);
        ArrayDeque.addFirst(14);

        ArrayDeque.max();

        System.out.println(ArrayDeque.max());
    }


}
