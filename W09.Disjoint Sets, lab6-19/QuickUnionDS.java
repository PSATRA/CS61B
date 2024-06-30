public class QuickUnionDS implements DisjointSets {
    private final int[] _parent;     // the address is final, not the value!

    public QuickUnionDS(int num) {
        _parent = new int[num];
        for (int i = 0; i < num; i++) {
            _parent[i] = -1;
        }
    }

    /* Find the first generation. */
    private int find(int p) {
        while (_parent[p] >= 0) {
            p = _parent[p];
        }
        return p;
    }

    @Override
    public void connect(int p, int q) {
        int i = find(p);
        int j = find(q);
        _parent[i] = j;
    }

    @Override
    public boolean isConnected(int p, int q) {
        return find(p) == find(q);
    }
}