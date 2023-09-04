package timingtest;
import edu.princeton.cs.algs4.Stopwatch;
import org.checkerframework.checker.units.qual.A;

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
        AList<Integer> Ns = new AList<>();
        AList<Double> times = new AList<>();
        AList<Integer> tested = new AList<>();
        Stopwatch sw = new Stopwatch();

        for (int i = 0; i < 128000; i++) {
            tested.addLast(i);
            double timeInSeconds = sw.elapsedTime();

            if (i == 999) {
                Ns.addLast(1000);
                times.addLast(timeInSeconds);
            }
            if (i == 1999) {
                Ns.addLast(2000);
                times.addLast(timeInSeconds);
            }
            if (i == 3999) {
                Ns.addLast(4000);
                times.addLast(timeInSeconds);
            }
            if (i == 7999) {
                Ns.addLast(8000);
                times.addLast(timeInSeconds);
            }
            if (i == 15999) {
                Ns.addLast(16000);
                times.addLast(timeInSeconds);
            }
            if (i == 31999) {
                Ns.addLast(32000);
                times.addLast(timeInSeconds);
            }
            if (i == 63999) {
                Ns.addLast(64000);
                times.addLast(timeInSeconds);
            }
            if (i == 127999) {
                Ns.addLast(128000);
                times.addLast(timeInSeconds);
            }
        }

        printTimingTable(Ns, times, Ns);
    }
}
