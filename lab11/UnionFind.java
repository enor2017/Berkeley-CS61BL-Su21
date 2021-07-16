public class UnionFind {

    private int[] parent;

    /* Creates a UnionFind data structure holding N items. Initially, all
       items are in disjoint sets. */
    public UnionFind(int N) {
        parent = new int[N];
        for(int i = 0; i < N; ++i) {
            parent[i] = -1;
        }
    }

    /**
     * helper function to check whether argument is legal
     * if illegal, throw exception
     */
    private void checkLegalArgument(int v) {
        if (v >= parent.length || v < 0) {
            throw new IllegalArgumentException();
        }
    }

    /* Returns the size of the set V belongs to. */
    public int sizeOf(int v) {
        checkLegalArgument(v);

        if (parent[v] < 0) {
            return -parent[v];
        } else {
            return -parent[find(v)];
        }
    }

    /* Returns the parent of V. If V is the root of a tree, returns the
       negative size of the tree for which V is the root. */
    public int parent(int v) {
        checkLegalArgument(v);

        return parent[v];
    }

    /* Returns true if nodes V1 and V2 are connected. */
    public boolean connected(int v1, int v2) {
        checkLegalArgument(v1);
        checkLegalArgument(v2);

        return find(v1) == find(v2);
    }

    /* Returns the root of the set V belongs to. Path-compression is employed
       allowing for fast search-time. If invalid items are passed into this
       function, throw an IllegalArgumentException. */
    public int find(int v) {
        checkLegalArgument(v);

        /**
         * if a root node, return itself
         * otherwise, recursively find its parent's root,
         * use parent[v] = find(parent[v]) for path compression
         */
        return parent[v] < 0 ? v : (parent[v] = find(parent[v]));
    }

    /* Connects two items V1 and V2 together by connecting their respective
       sets. V1 and V2 can be any element, and a union-by-size heuristic is
       used. If the sizes of the sets are equal, tie break by connecting V1's
       root to V2's root. Union-ing a item with itself or items that are
       already connected should not change the structure. */
    public void union(int v1, int v2) {
        int parent1 = find(v1);
        int parent2 = find(v2);
        if (parent1 != parent2) {
            if (sizeOf(parent1) > sizeOf(parent2)) {
                // connect v2's root to v1
                parent[parent1] -= sizeOf(parent2);
                parent[parent2] = parent1;
            } else {
                parent[parent2] -= sizeOf(parent1);
                parent[parent1] = parent2;
            }
        }
    }
}
