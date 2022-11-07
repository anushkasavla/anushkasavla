package gh2;
import deque.ArrayDeque;


//Note: This file will not compile until you complete the Deque implementations
public class GuitarString  {
    /** Constants. Do not change. In case you're curious, the keyword final
     * means the values cannot be changed at runtime. We'll discuss this and
     * other topics in lecture on Friday. */
    private static final int SR = 44100;      // Sampling Rate
    private static final double DECAY = .996; // energy decay factor

    /* Buffer for storing sound data. */
    private ArrayDeque<Double> buffer = new ArrayDeque<Double>();


    /* Create a guitar string of the given frequency.  */
    public GuitarString(double frequency) {
        double capacity = Math.round(SR / frequency);
        int newCapacity = (int) capacity;
        for (int i = 0; i < newCapacity; i += 1) {
            buffer.addFirst(0.0);
        }
    }


    /* Pluck the guitar string by replacing the buffer with white noise. */
    public void pluck() {
        //       Make sure that your random numbers are different from each
        //       other. This does not mean that you need to check that the numbers
        //       are different from each other. It means you should repeatedly call
        //       Math.random() - 0.5 to generate new random numbers for each array index.
        for (int i = 0; i < buffer.size(); i += 1) {
            double r = Math.random() - 0.5;
            buffer.removeFirst();
            buffer.addFirst(r);
        }
    }

    /* Advance the simulation one time step by performing one iteration of
     * the Karplus-Strong algorithm.
     */
    public void tic() {
        double first = buffer.get(0);
        double second = buffer.get(1);
        buffer.removeFirst();
        double averageFrequency = DECAY * .5 * (first + second);
        buffer.addLast(averageFrequency);
    }

    /* Return the double at the front of the buffer. */
    public double sample() {
        double first = buffer.get(0);
        return first;
    }
}

