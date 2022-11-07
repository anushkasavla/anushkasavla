package deque;

import org.junit.Test;

import java.util.Iterator;

import static org.junit.Assert.*;


/** Performs some basic Array tests. */
public class ArrayDequeTest {

    @Test
    public void generic() {
        ArrayDeque<Integer> ArrayDeque = new ArrayDeque<Integer>();
        ArrayDeque.addFirst(0);
        ArrayDeque.removeFirst();
        ArrayDeque.addLast(2);
        ArrayDeque.addLast(3);
        ArrayDeque.get(1);
        System.out.println(ArrayDeque.get(1));
    }

    @Test
    /** Adds a few things to the list, checking isEmpty() and size() are correct,
     * finally printing the results.
     *
     * && is the "and" operation. */
    public void addIsEmptySizeTest() {
        ArrayDeque<Integer> ad1 = new ArrayDeque<Integer>();

        assertTrue("A newly initialized ArrayDeque should be empty", ad1.isEmpty());
        ad1.addFirst(10);

        // The && operator is the same as "and" in Python.
        // It's a binary operator that returns true if both arguments true, and false otherwise.
        assertEquals(1, ad1.size());
        assertFalse("ad1 should now contain 1 item", ad1.isEmpty());

        ad1.addLast(20);
        assertEquals(2, ad1.size());

        ad1.addLast(30);
        assertEquals(3, ad1.size());

        System.out.println("Printing out deque: ");
        ad1.printDeque();
    }

    @Test
    public void addRemoveTest() {
        ArrayDeque<Integer> ad1 = new ArrayDeque<Integer>();
        // should be empty
        assertTrue("ad1 should be empty upon initialization", ad1.isEmpty());

        ad1.addFirst(10);
        // should not be empty
        assertFalse("ad1 should contain 1 item", ad1.isEmpty());

        ad1.addLast(20);
        assertEquals(2, ad1.size());

        ad1.addLast(30);
        assertEquals(3, ad1.size());

        ad1.addFirst(40);
        assertEquals(4, ad1.size());

        ad1.removeFirst();
        ad1.removeLast();
    }
    @Test
    public void addTestResize() {
        ArrayDeque<Integer> ad1 = new ArrayDeque<Integer>();

        assertTrue("A newly initialized ArrayDeque should be empty", ad1.isEmpty());

        for (int i = 0; i < 20; i++) {
            ad1.addLast(i);
        }

        assertEquals(20, ad1.size());
        assertFalse("ad1 should now contain 20 items", ad1.isEmpty());

        for (int i = 0; i < 14; i++) {
            ad1.removeFirst();
        }

        assertEquals(6, ad1.size());
        assertFalse("ad1 should now contain 2 items", ad1.isEmpty());

        System.out.println("Printing out deque: ");
        ad1.printDeque();
    }

    @Test
    public void getTest() {
        ArrayDeque<Integer> ad1 = new ArrayDeque<Integer>();
        for (int i = 0; i < 17; i++) {
            ad1.addFirst(i);
        }
        for (int i = 0; i < 17; i++) {
            ad1.removeFirst();
        }
        for (int i = 0; i < 100; i++) {
            ad1.addFirst(i);
        }

        System.out.println(ad1.get(0));
    }
    @Test
    public void filledUp() {
        ArrayDeque<Integer> ad1 = new ArrayDeque<Integer>();
        for (int i = 0; i < 8; i++) {
            ad1.addLast(i);
        }
        for (int i = 0; i < 8; i++) {
            ad1.removeFirst();
        }
        for (int i = 0; i < 8; i++) {
            ad1.addFirst(i);
        }
    }

    @Test
    public void iter() {
        ArrayDeque<Integer> a1 = new ArrayDeque<>();
        Iterator<Integer> iter = a1.iterator();
        assertFalse(iter.hasNext());
    }
}

