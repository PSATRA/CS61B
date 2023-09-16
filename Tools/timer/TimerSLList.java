package timer;

/**
 * Created by hug.
 */
public class TimerSLList {
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
        // TODO: YOUR CODE HERE

        /** @NOTICE: Time the getLast() method after construction. */

        AList<Integer> Ns = new AList<>();
        AList<Double> times = new AList<>();
        AList<Integer> opCounts = new AList<>();
        SLList<Integer> tested = new SLList<>();
        for (int i = 0; i < 8; i++) {
            opCounts.addLast(10000);
        }

        for (int i = 0; i < 128000; i++) {
            tested.addLast(i);

            if (i == 999) {
                Ns.addLast(1000);
                Stopwatch sw = new Stopwatch();

                for (int j = 0; j < 10000; j++) {
                    tested.getLast();
                }

                double timeInSeconds = sw.elapsedTime();
                times.addLast(timeInSeconds);
            }
            if (i == 1999) {
                Ns.addLast(2000);
                Stopwatch sw = new Stopwatch();

                for (int j = 0; j < 10000; j++) {
                    tested.getLast();
                }

                double timeInSeconds = sw.elapsedTime();
                times.addLast(timeInSeconds);
            }
            if (i == 3999) {
                Ns.addLast(4000);
                Stopwatch sw = new Stopwatch();

                for (int j = 0; j < 10000; j++) {
                    tested.getLast();
                }

                double timeInSeconds = sw.elapsedTime();
                times.addLast(timeInSeconds);
            }
            if (i == 7999) {
                Ns.addLast(8000);
                Stopwatch sw = new Stopwatch();

                for (int j = 0; j < 10000; j++) {
                    tested.getLast();
                }

                double timeInSeconds = sw.elapsedTime();
                times.addLast(timeInSeconds);
            }
            if (i == 15999) {
                Ns.addLast(16000);
                Stopwatch sw = new Stopwatch();

                for (int j = 0; j < 10000; j++) {
                    tested.getLast();
                }

                double timeInSeconds = sw.elapsedTime();
                times.addLast(timeInSeconds);
            }
            if (i == 31999) {
                Ns.addLast(32000);
                Stopwatch sw = new Stopwatch();

                for (int j = 0; j < 10000; j++) {
                    tested.getLast();
                }

                double timeInSeconds = sw.elapsedTime();
                times.addLast(timeInSeconds);
            }
            if (i == 63999) {
                Ns.addLast(64000);
                Stopwatch sw = new Stopwatch();

                for (int j = 0; j < 10000; j++) {
                    tested.getLast();
                }

                double timeInSeconds = sw.elapsedTime();
                times.addLast(timeInSeconds);
            }
            if (i == 127999) {
                Ns.addLast(128000);
                Stopwatch sw = new Stopwatch();

                for (int j = 0; j < 10000; j++) {
                    tested.getLast();
                }

                double timeInSeconds = sw.elapsedTime();
                times.addLast(timeInSeconds);
            }
        }

        printTimingTable(Ns, times, opCounts);
    }

}
