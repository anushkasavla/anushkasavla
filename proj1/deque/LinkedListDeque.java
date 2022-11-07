package deque;
import java.util.Iterator;
public class LinkedListDeque<T> implements Deque<T>, Iterable<T> {
    private int size;
    private Node sentinel;

    private class Node {
        private T item;
        private Node next;
        private Node prev;

        public Node(T i, Node n, Node p) {
            item = i;
            next = n;
            prev = p;
        }
    }

    /**
     * Went to office hours for help on approaching circular approach
     */
    public LinkedListDeque() {
        /** got help on Ed with null error*/
        size = 0;
        sentinel = new Node(null, null, null);
        sentinel.prev = sentinel;
        sentinel.next = sentinel;
    }

    public void addFirst(T item) {
        size = size + 1;
        Node first = new Node(item, sentinel.next, sentinel);
        sentinel.next.prev = first;
        sentinel.next = first;
    }

    public void addLast(T item) {
        size = size + 1;
        Node last = new Node(item, sentinel, sentinel.prev);
        sentinel.prev.next = last;
        sentinel.prev = last;
    }

    public int size() {
        return size;
    }

    public void printDeque() {
        Node printer = sentinel.next;
        while (printer != sentinel) {
            System.out.print(printer.item);
            System.out.print(' ');
            printer = printer.next;
        }
        System.out.println();
    }

    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }
        size = size - 1;
        Node first = sentinel.next;
        sentinel.next = first.next;
        first.next.prev = sentinel;
        return first.item;
    }

    public T removeLast() {
        if (isEmpty()) {
            return null;
        }
        size = size - 1;
        Node last = sentinel.prev;
        sentinel.prev = last.prev;
        last.prev.next = sentinel;
        return last.item;
    }

    public T get(int index) {
        if (isEmpty()) {
            return null;
        }
        Node got = sentinel.next;
        if (index > size) {
            return null;
        } else {
            for (int i = 0; (i < index); i += 1) {
                got = got.next;
            }
            return got.item;
        }
    }

    private T recursiveHelper(int index, Node sentinelCopy) {
        if (index == 0) {
            return sentinelCopy.item;
        } else {
            return recursiveHelper(index - 1, sentinelCopy.next);
        }
    }

    public T getRecursive(int index) {
        if (index > size) {
            return null;
        } else {
            return recursiveHelper(index, sentinel.next);
        }
    }

    public boolean equals(Object o) {
        if ((o != null) && (o instanceof Deque)) {
            Deque other = (Deque) o;
            if (other == this) {
                return true;
            }
            int otherSize = other.size();
            int thisSize = this.size();
            if (otherSize != thisSize) {
                return false;
            }
            for (int index = 0; index < this.size; index += 1) {
                if ((other.get(index)).equals(this.get(index))) {
                    return true;
                }
            }
        }
        return false;
    }
    public Iterator<T> iterator() {
        return new LinkedListDeque.LLDequeIter();
    }
    private class LLDequeIter implements Iterator<T> {
        int tempFirst;
        public LLDequeIter() {
            tempFirst = 0;
        }
        @Override
        public boolean hasNext() {
            return tempFirst < size;
        }
        @Override
        public T next() {
            T nextDigit = get(tempFirst);
            tempFirst++;
            return nextDigit;
        }

    }
}


