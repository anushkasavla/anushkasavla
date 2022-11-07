package timingtest;
import edu.princeton.cs.algs4.Stopwatch;

import java.lang.reflect.Array;

/**
 * Created by hug.
 */
public class TimeAList {
    private static void printTimingTable(AList<Integer> Ns, AList<Double> times, AList<Integer> opCounts) {
        System.out.printf("%12s %12s %12s %12s\n", "N", "time (s)", "# ops", "microsec/op");
        System.out.printf("------------------------------------------------------------\n");
        for (int i = 0; i < Ns.size(); i += 1) {
            int N = Ns.get(i);
            double time = times.get(i);
            int opCount = opCounts.get(i);
            double timePerOp = time / opCount * 1e6;
            System.out.printf("%12d %12.2f %12d %12.2f\n", N, time, opCount, timePerOp);
        }
    }

    public static void main(String[] args) {
        timeAListConstruction();
    }

    public static void timeAListConstruction() {
        // TODO: YOUR CODE HERE
        int N = 1000;
        int op = -1;
        AList iterate = new AList();
        AList n_value = new AList();
        AList count_times = new AList();
        AList op_count = new AList();
        while (N <= 128000) {
            Stopwatch counter = new Stopwatch();
            for (int i = 0; i <= N; i+= 1) {
                iterate.addLast(i);
                op += 1;
            }
            double timeInSeconds = counter.elapsedTime();
            count_times.addLast(timeInSeconds);
            op_count.addLast(op);
            n_value.addLast(N);
            op = -1;
            N *= 2;
        }

        printTimingTable(n_value, count_times,op_count);

        return;
    }
}
