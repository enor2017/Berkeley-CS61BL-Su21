
/* A class for disjoint set, used in kruskal */
public class DisjointSet {
    private int num;
    private int[] parent;

    public DisjointSet(int num) {
        this.num = num;
        this.parent = new int[num];
        for(int i = 0; i < num; ++i) {
            parent[i] = i;
        }
    }

    public int find(int x) {
        return x == parent[x] ? x : (parent[x] = find(parent[x]));
    }

    public boolean isConnected(int x, int y) {
        return find(x) == find(y);
    }

    public void Union(int x, int y) {
        int parentX = find(x);
        int parentY = find(y);
        if(parentX != parentY) {
            parent[parentX] = parentY;
        }
    }
}
