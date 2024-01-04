package lab6_2019;

//import edu.princeton.cs.algs4.WeightedQuickUnionUF;
//extends WeightedQuickUnionUF

public class WeightedQuickUnionDS {

    /* https://www.cs.usfca.edu/~galles/visualization/DisjointSets.html */

    private final int[] _parent;

    /* Creates a UnionFind data structure holding n vertices.
    Initially, all vertices are in disjoint sets. */
    public WeightedQuickUnionDS(int n) {
        _parent = new int[n];
        for (int i = 0; i < n; i++) {
            _parent[i] = -1;    // essential initialization!!!
        }
    }

    /* Throws an exception if v1 is not a valid index. */
    public void validate(int v1) {
        if (v1 >= _parent.length || v1 < 0) {
            throw new IllegalArgumentException("Invalid vertex is passed into the method.");
        }
    }

    /* Returns the size of the set v1 belongs to. */
    public int sizeOf(int v1) {
        return _parent[parent(v1)];
        // return -parent[find(v1)]; ???
    }

    /* Returns the parent of v1. If v1 is the root of a tree, returns
    the negative size of the tree for which v1 is the root. */
    public int parent(int v1) {
        validate(v1);
        return _parent[v1];
    }

    /* Returns the root of the set v1 belongs to. Optional: Path-compression
    is employed allowing for fast search-time. */
    public int find(int v1) {
        validate(v1);
        // pass by value !!!
        while (_parent[v1] >= 0) {
            v1 = _parent[v1]; // same as parent(v1), but doesn't need validate(v1);
        }
        return v1;
    }

    /**
     * If the sizes of the sets are equal, break the tie by connecting
     * v1’s root to v2’s root.
     * Unioning a vertex with itself or vertices that are already
     * connected should not change the sets, but it may alter the
     * internal structure of the data structure.
     */
    public void connect(int p, int q) {
        int i = find(p);
        int j = find(q);
        if (_parent[i] > _parent[j]) {
            // j has bigger wight or the same
            _parent[j] += _parent[i]; // this must come before parent[i] = j;!!! or it's meaningless!!!
            _parent[i] = j;
        } else {
            // i has bigger wight
            _parent[i] += _parent[j];
            _parent[j] = i;
        }
    }

    public boolean isConnected(int p, int q) {
        return find(p) == find(q);
    }
}
