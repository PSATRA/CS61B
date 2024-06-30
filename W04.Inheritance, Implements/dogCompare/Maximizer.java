package dogCompare;

public class Maximizer {
    public static Comparable max(Comparable[] items) {
        int maxInd = 0;
        for (int i = 0; i < items.length; i++) {
            int cmp = items[i].compareTo(items[maxInd]);
            if (cmp > 0) {
                maxInd = i;
            }
        }
        return items[maxInd];
    }
}
