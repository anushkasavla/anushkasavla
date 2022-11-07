package deque;
import java.util.Comparator;

public class MaxArrayDeque<T> extends ArrayDeque<T> {
    private Comparator<T> comparator;
    public MaxArrayDeque(Comparator<T> c) {
        comparator = c;
    }
    public T max() {
        return max(comparator);

    }
    public T max(Comparator<T> c) {
        T maximum = this.get(0);
        for (int i = 1; i < this.size(); i += 1) {
            int a = c.compare(this.get(i), maximum);
            if (a > 0) {
                maximum = this.get(i);
            }
        }
        return maximum;
    }

}

