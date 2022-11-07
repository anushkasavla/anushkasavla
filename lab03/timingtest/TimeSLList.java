package timingtest;
import edu.princeton.cs.algs4.Stopwatch;

/**
 * Created by hug.
 */
public class TimeSLList {
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
        timeGetLast();
    }

    public static void timeGetLast() {
        int N = 1000;
        SLList slList = new SLList();
        AList count_times = new AList();
        AList op_count = new AList();
        AList n_value = new AList();
        int ops = 10000;
        while (N <= 64000) {
            for (int i = 0; i <= N; i+= 1) {
                slList.addLast(i);
            }
            Stopwatch counter = new Stopwatch();
            for (int i = 0; i <= N; i+= 1) {
                slList.getLast();
            }
            double timeInSeconds = counter.elapsedTime();
            n_value.addLast(N);
            count_times.addLast(timeInSeconds);
            op_count.addLast(ops);
            N *= 2;
        }
        printTimingTable(n_value, count_times,op_count);

        return;
    }

}
