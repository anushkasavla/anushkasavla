package deque;
import java.util.Iterator;

public class ArrayDeque<T> implements Deque<T>, Iterable<T> {
    private int size;
    private int first;
    private int last;
    private T[] items;
    private static final int CAPACITY = 8;
    private static final int MIN = 4;
    private static final int MAX = 16;
    public ArrayDeque() {
        size = 0;
        first = 0;
        last = 0;
        items = (T[]) new Object[CAPACITY];
    }

    public void printDeque() {
        int printer = nextIndex(first);
        for (int i = printer; i < printer + size; i += 1) {
            System.out.print(items[i]);
            System.out.print(' ');
        }
        System.out.println();
    }

    public int size() {
        return size;
    }

    public void addFirst(T item) {
        resizer();
        if (size == 0) {
            items[first] = item;
            first = prevIndex(first);
            last = nextIndex(last);
        } else {
            items[first] = item;
            first = prevIndex(first);
        }
        size = size + 1;
    }

    public void addLast(T item) {
        resizer();
        if (size == 0) {
            items[first] = item;
            first = prevIndex(first);
        } else {
            items[last] = item;
        }
        last = nextIndex(last);
        size = size + 1;
    }

    public T removeFirst() {
        if (size == 0) {
            return null;
        } else if (size == 1) {
            last = prevIndex(last);
            first = nextIndex(first);
            T returnfirstItem = items[last];
            items[last] = null;
            size -= 1;
            resizer();
            return returnfirstItem;
        } else {
            first = nextIndex(first);
            T returnfirstItem = items[first];
            items[first] = null;
            size -= 1;
            resizer();
            return returnfirstItem;
        }
    }

    public T removeLast() {
        if (size == 0) {
            return null;
        } else if (size == 1) {
            last = prevIndex(last);
            first = nextIndex(first);
            T returnlastItem = items[last];
            items[last] = null;
            size -= 1;
            resizer();
            return returnlastItem;
        } else {
            last = prevIndex(last);
            T returnlastItem = items[last];
            items[last] = null;
            size -= 1;
            resizer();
            return returnlastItem;
        }
    }

    private void resizer() {
        if ((size < items.length / MIN) && (items.length > MAX)) {
            T[] newlist = (T[]) new Object[items.length / 2];
            System.arraycopy(items, nextIndex(first), newlist, 0, size);
            last = size;
            items = newlist;
            first = items.length - 1;
        } else if (size == items.length) {
            T[] newlist = (T[]) new Object[items.length * 2];
            int pointer = nextIndex(first);
            int index = 0;
            for (int i = pointer; i != first; i = (pointer) % items.length) {
                newlist[index] = items[i];
                index += 1;
                pointer += 1;
            }
            newlist[index] = items[first];
            last = size;
            items = newlist;
            first = items.length - 1;
        }
    }

    private int prevIndex(int prev) {
        prev = (prev - 1) % items.length;
        if (prev < 0) {
            prev = (prev) + items.length;
        }
        return prev;
    }

    private int nextIndex(int next) {
        next = (1 + next) % items.length;
        return next;
    }

    public T get(int index) {
        if (index >= size || index < 0) {
            return null;
        } else {
            return items[(index + first + 1) % items.length];
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
        return new ArrayDequeIter();
    }
    private class ArrayDequeIter implements Iterator<T> {
        int tempFirst;
        public ArrayDequeIter() {
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


