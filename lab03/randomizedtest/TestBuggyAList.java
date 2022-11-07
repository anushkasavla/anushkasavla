package randomizedtest;

import edu.princeton.cs.algs4.StdRandom;
import org.junit.Assert;
import org.junit.Test;
import timingtest.AList;

import static org.junit.Assert.*;

/**
 * Created by hug.
 */
public class TestBuggyAList {

    @Test
    public void testThreeAddThreeRemove() {
        AListNoResizing<Integer> nobug = new AListNoResizing<>();
        BuggyAList<Integer> bug = new BuggyAList<>();

        nobug.addLast(4);
        nobug.addLast(5);
        nobug.addLast(6);

        bug.addLast(4);
        bug.addLast(5);
        bug.addLast(6);

        assertEquals(nobug.removeLast(), bug.removeLast());
        assertEquals(nobug.removeLast(), bug.removeLast());
        assertEquals(nobug.removeLast(), bug.removeLast());
    }

    @Test
    public void randomizedTest() {
        AListNoResizing<Integer> nobug = new AListNoResizing<>();
        BuggyAList<Integer> bug = new BuggyAList<>();

        int N = 5000;
        for (int i = 0; i < N; i += 1) {
            int operationNumber = StdRandom.uniform(0, 4);
            if (operationNumber == 0) {
                // addLast
                int randVal = StdRandom.uniform(0, 100);
                nobug.addLast(randVal);
                bug.addLast(randVal);
                System.out.println("nobug: addLast(" + randVal + ")");
                System.out.println("bug: addLast(" + randVal + ")");
            } else if (operationNumber == 1) {
                // size
                int bugsize = bug.size();
                int nobugsize = nobug.size();
                assertEquals(bugsize, nobugsize);
            } else if (operationNumber == 2) {
                int size = bug.size();
                if (size > 0) {
                    int getbug = bug.getLast();
                    int getnobug = nobug.getLast();
                    assertEquals(getbug, getnobug);
                }
            } else if (operationNumber == 3) {
                int size = bug.size();
                if (size > 0) {
                    int removebug = bug.removeLast();
                    int removenobug = nobug.removeLast();
                    assertEquals(removebug,removenobug);
                }

            }
        }
    }
}
