import java.util.*;

public class Graph implements Iterable<Integer> {

    private LinkedList<Edge>[] adjLists;
    private int vertexCount;

    /* Initializes a graph with NUMVERTICES vertices and no Edges. */
    public Graph(int numVertices) {
        adjLists = (LinkedList<Edge>[]) new LinkedList[numVertices];
        for (int k = 0; k < numVertices; k++) {
            adjLists[k] = new LinkedList<Edge>();
        }
        vertexCount = numVertices;
    }

    /* Adds a directed Edge (V1, V2) to the graph. That is, adds an edge
       in ONE directions, from v1 to v2. */
    public void addEdge(int v1, int v2) {
        addEdge(v1, v2, 0);
    }

    /* Adds an undirected Edge (V1, V2) to the graph. That is, adds an edge
       in BOTH directions, from v1 to v2 and from v2 to v1. */
    public void addUndirectedEdge(int v1, int v2) {
        addUndirectedEdge(v1, v2, 0);
    }

    /* Adds a directed Edge (V1, V2) to the graph with weight WEIGHT. If the
       Edge already exists, replaces the current Edge with a new Edge with
       weight WEIGHT. */
    public void addEdge(int v1, int v2, int weight) {
        for(Edge e : adjLists[v1]) {
            // Edge already exists
            if(e.to == v2) {
                e.weight = weight;
                return;
            }
        }
        // no edge, add one
        adjLists[v1].add(new Edge(v1, v2, weight));
    }

    /* Adds an undirected Edge (V1, V2) to the graph with weight WEIGHT. If the
       Edge already exists, replaces the current Edge with a new Edge with
       weight WEIGHT. */
    public void addUndirectedEdge(int v1, int v2, int weight) {
        addEdge(v1, v2, weight);
        addEdge(v2, v1, weight);
    }

    /* Returns true if there exists an Edge from vertex FROM to vertex TO.
       Returns false otherwise. */
    public boolean isAdjacent(int from, int to) {
        for(Edge e : adjLists[from]) {
            if(e.to == to) {
                return true;
            }
        }
        return false;
    }

    /* Returns a list of all the vertices u such that the Edge (V, u)
       exists in the graph. */
    public List<Integer> neighbors(int v) {
        List<Integer> list = new LinkedList<>();
        // Avoid replicate: after adding u to list, set visited[u] = true;
        boolean[] visited = new boolean[vertexCount];
        for(Edge e : adjLists[v]) {
            int to = e.to;
            if(!visited[to]) {
                list.add(to);
                visited[to] = true;
            }
        }
        return list;
    }
    /* Returns the number of incoming Edges for vertex V. */
    public int inDegree(int v) {
        int count = 0;
        for(int i = 0; i < vertexCount; ++i) {
            LinkedList<Edge> currentList = adjLists[i];
            for(Edge e : currentList) {
                if(e.to == v) {
                    count++;
                }
            }
        }
        return count;
    }

    /* Returns an Iterator that outputs the vertices of the graph in topological
       sorted order. */
    public Iterator<Integer> iterator() {
        return new TopologicalIterator();
    }

    /**
     *  A class that iterates through the vertices of this graph,
     *  starting with a given vertex. Does not necessarily iterate
     *  through all vertices in the graph: if the iteration starts
     *  at a vertex v, and there is no path from v to a vertex w,
     *  then the iteration will not include w.
     */
    private class DFSIterator implements Iterator<Integer> {

        private Stack<Integer> fringe;
        private HashSet<Integer> visited;

        public DFSIterator(Integer start) {
            fringe = new Stack<>();
            visited = new HashSet<>();
            fringe.push(start);
        }

        public boolean hasNext() {
            if (!fringe.isEmpty()) {
                int i = fringe.pop();
                while (visited.contains(i)) {
                    if (fringe.isEmpty()) {
                        return false;
                    }
                    i = fringe.pop();
                }
                fringe.push(i);
                return true;
            }
            return false;
        }

        public Integer next() {
            int curr = fringe.pop();
            ArrayList<Integer> lst = new ArrayList<>();
            for (int i : neighbors(curr)) {
                lst.add(i);
            }
            // This will sort neighbors in descending order
            lst.sort((Integer i1, Integer i2) -> -(i1 - i2));
            // System.out.println("lst:" + lst);
            for (Integer e : lst) {
                fringe.push(e);
            }
            visited.add(curr);
            return curr;
        }

        //ignore this method
        public void remove() {
            throw new UnsupportedOperationException(
                    "vertex removal not implemented");
        }

    }

    /* Returns the collected result of performing a depth-first search on this
       graph's vertices starting from V. */
    public List<Integer> dfs(int v) {
        ArrayList<Integer> result = new ArrayList<Integer>();
        Iterator<Integer> iter = new DFSIterator(v);

        while (iter.hasNext()) {
            result.add(iter.next());
        }
        return result;
    }

    /* Finding paths: (adapted from: algs4.cs.princeton.edu/41graph/ )
     *
     * we remember the edge u-v that takes us to v for the FIRST time,
     * by storing edgeTo[v] = u, which means, u-v is the LAST(reversed) EDGE
     * on the path from start to v.
     *
     * Then we traverse from end to start, pick each edgeTo[], a path forms.
     *
     */
    // if there is an start-v path
    private boolean[] hasEdge;
    // record the last edge on start-v path
    private int[] edgeTo;

    /* initialize arrays that we will use */
    private void initPath() {
        hasEdge = new boolean[vertexCount];
        edgeTo = new int[vertexCount];
    }

    /* Perform DFS to get hasEdge[] and edgeTo[] */
    private void runDFS(int u) {
        hasEdge[u] = true;
        List<Integer> neighbours = neighbors(u);
        for(int v : neighbours) {
            // if we has not updated v, do that!
            if(!hasEdge[v]) {
                edgeTo[v] = u;
                runDFS(v);
            }
        }
    }

    /* Returns true iff there exists a path from START to STOP. Assumes both
       START and STOP are in this graph. If START == STOP, returns true. */
    public boolean pathExists(int start, int stop) {
        /* If path returned is an empty list, no path exists.
         * Notice that we haven't run DFS before, otherwise,
         * we can directly check hasEdge[stop] to determine, which
         * greatly reduce time complexity.
         */
        return !path(start, stop).isEmpty();
    }

    /* Returns the path from START to STOP. If no path exists, returns an empty
       List. If START == STOP, returns a List with START. */
    public List<Integer> path(int start, int stop) {
        initPath();     // don't forget
        runDFS(start);
        // if we hasn't reached stop node while running DFS, no path exists.
        if(!hasEdge[stop]) {
            return new LinkedList<>();
        }
        Stack<Integer> path = new Stack<>();
        // from end back to start, push previous node
        // which is achieved by recording 'reversed' edge in edgeTo[]
        for(int x = stop; x != start; x = edgeTo[x]) {
            path.add(x);
        }
        // don't forget to push start node
        path.add(start);

        // convert stack into list
        List<Integer> pathList = new LinkedList<>();
        while(!path.isEmpty()) {
            pathList.add(path.pop());
        }
        return pathList;
    }

    public List<Integer> topologicalSort() {
        ArrayList<Integer> result = new ArrayList<Integer>();
        Iterator<Integer> iter = new TopologicalIterator();
        while (iter.hasNext()) {
            result.add(iter.next());
        }
        return result;
    }

    private class TopologicalIterator implements Iterator<Integer> {

        private Stack<Integer> fringe;
        private int[] inDegree;
        private boolean[] visited;

        TopologicalIterator() {
            fringe = new Stack<Integer>();
            inDegree = new int[vertexCount];
            visited = new boolean[vertexCount];
            for(int i = 0; i < vertexCount; ++i) {
                inDegree[i] = inDegree(i);
                // if in-deg is 0, push and visited
                if(inDegree[i] == 0) {
                    fringe.push(i);
                    visited[i] = true;
                }
            }
        }

        public boolean hasNext() {
            return !fringe.isEmpty();
        }

        public Integer next() {
            int toReturn = fringe.pop();
            List<Integer> neighbours = neighbors(toReturn);
            for(int v : neighbours) {
                // minus 1 for neighbor whose in-deg is not 0
                if(inDegree[v] != 0) {
                    inDegree[v]--;
                    // if -1 cause in-deg = 0, push into fringe
                    if(inDegree[v] == 0) {
                        fringe.push(v);
                        visited[v] = true;
                    }
                }
            }
            return toReturn;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

    }

    private class Edge {

        private int from;
        private int to;
        private int weight;

        Edge(int from, int to, int weight) {
            this.from = from;
            this.to = to;
            this.weight = weight;
        }

        public String toString() {
            return "(" + from + ", " + to + ", weight = " + weight + ")";
        }

    }

    private void generateG1() {
        addEdge(0, 1);
        addEdge(0, 2);
        addEdge(0, 4);
        addEdge(1, 2);
        addEdge(2, 0);
        addEdge(2, 3);
        addEdge(4, 3);
    }

    private void generateG2() {
        addEdge(0, 1);
        addEdge(0, 2);
        addEdge(0, 4);
        addEdge(1, 2);
        addEdge(2, 3);
        addEdge(4, 3);
    }

    private void generateG3() {
        addUndirectedEdge(0, 2);
        addUndirectedEdge(0, 3);
        addUndirectedEdge(1, 4);
        addUndirectedEdge(1, 5);
        addUndirectedEdge(2, 3);
        addUndirectedEdge(2, 6);
        addUndirectedEdge(4, 5);
    }

    private void generateG4() {
        addEdge(0, 1);
        addEdge(1, 2);
        addEdge(2, 0);
        addEdge(2, 3);
        addEdge(4, 2);
    }

    /* generate the graph in spec "Exercise: Practice Graph Traversal" */
    private void generateG5() {
        addEdge(1, 3);
        addEdge(2, 5);addEdge(2, 4);addEdge(3, 4);
        addEdge(3, 7);addEdge(3, 9);addEdge(3, 6);addEdge(3, 1);
        addEdge(4, 2);addEdge(4, 8);addEdge(4, 7);addEdge(4, 9);addEdge(4, 6);addEdge(4, 3);
        addEdge(5, 8);addEdge(5, 7);addEdge(5, 2);
        addEdge(6, 3);addEdge(6, 4);addEdge(6, 7);addEdge(6, 9);
        addEdge(7, 4);addEdge(7, 5);addEdge(7, 8);addEdge(7, 10);addEdge(7, 9);addEdge(7, 6);addEdge(7, 3);
        addEdge(8, 5);addEdge(8, 4);addEdge(8, 7);
        addEdge(9, 3);addEdge(9, 4);addEdge(9, 7);addEdge(9, 6);
        addEdge(10, 7);
    }

    private void printDFS(int start) {
        System.out.println("DFS traversal starting at " + start);
        List<Integer> result = dfs(start);
        Iterator<Integer> iter = result.iterator();
        while (iter.hasNext()) {
            System.out.println(iter.next() + " ");
        }
        System.out.println();
        System.out.println();
    }

    private void printPath(int start, int end) {
        System.out.println("Path from " + start + " to " + end);
        List<Integer> result = path(start, end);
        if (result.size() == 0) {
            System.out.println("No path from " + start + " to " + end);
            return;
        }
        Iterator<Integer> iter = result.iterator();
        while (iter.hasNext()) {
            System.out.println(iter.next() + " ");
        }
        System.out.println();
        System.out.println();
    }

    /* **************************************** */
    /* *******  ShortestPath Algorithm  ******* */
    /* **************************************** */

    /* A class for a node in dijkstra's fringe: (vertex, distance) */
    private static class DijkNode implements Comparable<DijkNode>{
        private int vertex, dist;

        public DijkNode(int vertex, int dist) {
            this.vertex = vertex;
            this.dist = dist;
        }

        @Override
        public int compareTo(DijkNode o) {
            return this.dist - o.dist;
        }
    }
    public List<Integer> shortestPath(int start, int stop) {
        boolean[] visited = new boolean[vertexCount];
        PriorityQueue<DijkNode> fringe = new PriorityQueue<>();
        // store the shortest dist from start to this vertex, init to inf
        int[] shortestDist = new int[vertexCount];
        final int INF = 99999999;
        for(int i = 0; i < vertexCount; ++i) {
            shortestDist[i] = INF;
        }

        // for each node, record which node does the shortest path come from
        int[] edgeFrom = new int[vertexCount];

        // push start node into fringe
        fringe.add(new DijkNode(start, 0));
        visited[start] = true;
        shortestDist[start] = 0;

        while(!fringe.isEmpty()) {
            // pop the top node from fringe
            DijkNode curr = fringe.poll();
            int currVertex = curr.vertex;
            int currDist = curr.dist;

            // if reaching stop node
            if(currVertex == stop) {
                break;
            }
            // else, mark it as visited
            visited[currVertex] = true;

            // process each neighbour v of current node
            LinkedList<Edge> neighbours = adjLists[currVertex];
            for(Edge e : neighbours) {
                int toVertex = e.to;
                int weight = e.weight;
                // if v has already been visited, skip
                if(visited[toVertex]) {
                    continue;
                }
                // else, perform relaxing
                if(shortestDist[toVertex] > shortestDist[currVertex] + weight) {
                    shortestDist[toVertex] = shortestDist[currVertex] + weight;
                    fringe.add(new DijkNode(toVertex, shortestDist[toVertex]));
                    edgeFrom[toVertex] = currVertex;
                }
            }
        }

        // construct shortest path from end to start
        Stack<Integer> reversedPath = new Stack<>();
        for(int i = stop; i != start; i = edgeFrom[i]) {
            reversedPath.add(i);
        }
        // don't forget to add start
        reversedPath.add(start);

        // convert stack to list
        LinkedList<Integer> shortestPath = new LinkedList<>();
        while(!reversedPath.isEmpty()) {
            shortestPath.add(reversedPath.pop());
        }
        return shortestPath;
    }

    /* return the Edge object from u to v */
    private Edge getEdge(int u, int v) {
        LinkedList<Edge> edges = adjLists[u];
        for(Edge e : edges) {
            if(e.to == v) {
                return e;
            }
        }
        return null;
    }


    private void printTopologicalSort() {
        System.out.println("Topological sort");
        List<Integer> result = topologicalSort();
        Iterator<Integer> iter = result.iterator();
        while (iter.hasNext()) {
            System.out.println(iter.next() + " ");
        }
    }

    public static void main(String[] args) {
        Graph g1 = new Graph(5);
        g1.generateG1();
        g1.printDFS(0);
        g1.printDFS(2);
        g1.printDFS(3);
        g1.printDFS(4);

        g1.printPath(0, 3);
        g1.printPath(0, 4);
        g1.printPath(1, 3);
        g1.printPath(1, 4);
        g1.printPath(4, 0);

        Graph g2 = new Graph(5);
        g2.generateG2();
        g2.printTopologicalSort();

        System.out.println("===== My Tests =====");
        Graph g3 = new Graph(11);
        g3.generateG5();
        // g3.printDFS(1);
        // System.out.println(g3.neighbors(2));
        System.out.println(g3.path(1, 10));
        System.out.println(g3.pathExists(1, 10));
    }
}