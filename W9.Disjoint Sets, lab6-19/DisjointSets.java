public interface DisjointSets {
    /**
     * We will discuss four iterations of a Disjoint Sets design before being satisfied:
     * Quick Find → Quick Union → Weighted Quick Union (WQU) → WQU with Path Compression.
     * We will see how design decisions greatly affect asymptotic runtime and code complexity.
     */

    /** connects two items P and Q */
    void connect(int p, int q);

    /** checks to see if two items are connected */
    boolean isConnected(int p, int q);
}