import java.util.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.BufferedReader;
import java.nio.charset.Charset;
import java.io.IOException;

/* A mutable and finite Graph object. Edge labels are stored via a HashMap
   where labels are mapped to a key calculated by the following. The graph is
   undirected (whenever an Edge is added, the dual Edge is also added). Vertices
   are numbered starting from 0. */
public class Graph {

    /* Maps vertices to a list of its neighboring vertices. */
    private HashMap<Integer, Set<Integer>> neighbors = new HashMap<>();
    /* Maps vertices to a list of its connected edges. */
    private HashMap<Integer, Set<Edge>> edges = new HashMap<>();
    /* A sorted set of all edges. */
    private TreeSet<Edge> allEdges = new TreeSet<>();

    /* Returns the vertices that neighbor V. */
    public TreeSet<Integer> getNeighbors(int v) {
        return new TreeSet<Integer>(neighbors.get(v));
    }

    /* Returns all edges adjacent to V. */
    public TreeSet<Edge> getEdges(int v) {
        return new TreeSet<Edge>(edges.get(v));
    }

    /* Returns a sorted list of all vertices. */
    public TreeSet<Integer> getAllVertices() {
        return new TreeSet<Integer>(neighbors.keySet());
    }

    /* Returns a sorted list of all edges. */
    public TreeSet<Edge> getAllEdges() {
        return new TreeSet<Edge>(allEdges);
    }

    /* Adds vertex V to the graph. */
    public void addVertex(Integer v) {
        if (neighbors.get(v) == null) {
            neighbors.put(v, new HashSet<Integer>());
            edges.put(v, new HashSet<Edge>());
        }
    }

    /* Adds Edge E to the graph. */
    public void addEdge(Edge e) {
        addEdgeHelper(e.getSource(), e.getDest(), e.getWeight());
    }

    /* Creates an Edge between V1 and V2 with no weight. */
    public void addEdge(int v1, int v2) {
        addEdgeHelper(v1, v2, 0);
    }

    /* Creates an Edge between V1 and V2 with weight WEIGHT. */
    public void addEdge(int v1, int v2, int weight) {
        addEdgeHelper(v1, v2, weight);
    }

    /* Returns true if V1 and V2 are connected by an edge. */
    public boolean isNeighbor(int v1, int v2) {
        return neighbors.get(v1).contains(v2) && neighbors.get(v2).contains(v1);
    }

    /* Returns true if the graph contains V as a vertex. */
    public boolean containsVertex(int v) {
        return neighbors.get(v) != null;
    }

    /* Returns true if the graph contains the edge E. */
    public boolean containsEdge(Edge e) {
        return allEdges.contains(e);
    }

    /* Returns if this graph spans G. */
    public boolean spans(Graph g) {
        TreeSet<Integer> all = getAllVertices();
        if (all.size() != g.getAllVertices().size()) {
            return false;
        }
        Set<Integer> visited = new HashSet<>();
        Queue<Integer> vertices = new ArrayDeque<>();
        Integer curr;

        vertices.add(all.first());
        while ((curr = vertices.poll()) != null) {
            if (!visited.contains(curr)) {
                visited.add(curr);
                for (int n : getNeighbors(curr)) {
                    vertices.add(n);
                }
            }
        }
        return visited.size() == g.getAllVertices().size();
    }

    /* Overrides objects equals method. */
    public boolean equals(Object o) {
        if (!(o instanceof Graph)) {
            return false;
        }
        Graph other = (Graph) o;
        return neighbors.equals(other.neighbors) && edges.equals(other.edges);
    }

    /* A helper function that adds a new edge from V1 to V2 with WEIGHT as the
       label. */
    private void addEdgeHelper(int v1, int v2, int weight) {
        addVertex(v1);
        addVertex(v2);

        neighbors.get(v1).add(v2);
        neighbors.get(v2).add(v1);

        Edge e1 = new Edge(v1, v2, weight);
        Edge e2 = new Edge(v2, v1, weight);
        edges.get(v1).add(e1);
        edges.get(v2).add(e2);
        allEdges.add(e1);
    }

    /* ************************************************** */
    /* ****** Minimum Spanning Tree Implementation ****** */
    /* ************************************************** */

    /* each node in priority queue is a vector [vertex, distToTree] */
    private static class PQNode implements Comparable<PQNode> {
        int vertex, dist;

        public PQNode(int vertex, int dist) {
            this.vertex = vertex;
            this.dist = dist;
        }

        @Override
        public int compareTo(PQNode o) {
            return dist - o.dist;
        }
    }

    public Graph prims(int start) {
        Graph span = new Graph();

        int vertexNum = getAllVertices().size();
        // the dist from node to spanning tree
        int[] distToTree = new int[vertexNum];
        // if a node has been visited
        boolean[] visited = new boolean[vertexNum];
        // fringe, compared by distToTree
        PriorityQueue<PQNode> fringe = new PriorityQueue<>();
        // which node does it come from, in spanning tree
        int[] nodeFrom = new int[vertexNum];

        // set other nodes' dist to INF
        final int INF = 999999;
        TreeSet<Integer> allVertices = getAllVertices();
        for(int v : allVertices) {
            distToTree[v] = INF;
        }

        // process start node
        visited[start] = true;
        distToTree[start] = 0;
        fringe.add(new PQNode(start, 0));

        while(!fringe.isEmpty()) {
            // pop the nearest node
            PQNode current = fringe.poll();
            int currentNode = current.vertex;
            int currentDis = current.dist;
            visited[currentNode] = true;

            // iterate all neighbours
            TreeSet<Edge> edges = getEdges(currentNode);
            for(Edge e : edges) {
                int to = e.getDest();
                int w = e.getWeight();
                // if visited, do nothing
                if(visited[to]) continue;
                // else, perform relaxing
                if(w < distToTree[to]) {
                    distToTree[to] = w;
                    nodeFrom[to] = currentNode;
                    fringe.add(new PQNode(to, distToTree[to]));
                }
            }

        }

        // add edges to form a spanning tree
        TreeSet<Integer> vertices = getAllVertices();
        for(int v : vertices) {
            // add to graph only if it's reachable form start
            if(v != start && distToTree[v] != INF) {
                span.addEdge(nodeFrom[v], v, distToTree[v]);
            }
        }
        return span;
    }

    /* A comparator for Edge priority queue used in kruskal */
    private class edgeComp implements Comparator<Edge> {
        @Override
        public int compare(Edge o1, Edge o2) {
            return o1.getWeight() - o2.getWeight();
        }
    }

    public Graph kruskals() {
        Graph g = new Graph();

        // build a disjoint set
        TreeSet<Integer> vertices = getAllVertices();
        int vertexNum = vertices.size();
        DisjointSet disjoint = new DisjointSet(vertices.size());

        // add all edges to priority queue, compared using edgeComp
        PriorityQueue<Edge> pq = new PriorityQueue<>(new edgeComp());
        TreeSet<Edge> edges = getAllEdges();
        for(Edge e : edges) {
            pq.add(e);
        }

        // iterate all edges while ensuring spanning tree's size < vertexNum - 1
        while(!pq.isEmpty()) {
            // spanning tree's size = vertexNum - 1, stop
            if(g.getAllEdges().size() == vertexNum - 1) {
                break;
            }
            Edge e = pq.poll();
            int from = e.getSource();
            int to = e.getDest();
            if(!disjoint.isConnected(from, to)) {
                disjoint.Union(from, to);
                g.addEdge(from, to, e.getWeight());
            }
        }

        return g;
    }

    /* Returns a randomly generated graph with VERTICES number of vertices and
       EDGES number of edges with max weight WEIGHT. */
    public static Graph randomGraph(int vertices, int edges, int weight) {
        Graph g = new Graph();
        Random rng = new Random();
        for (int i = 0; i < vertices; i += 1) {
            g.addVertex(i);
        }
        for (int i = 0; i < edges; i += 1) {
            Edge e = new Edge(rng.nextInt(vertices), rng.nextInt(vertices), rng.nextInt(weight));
            g.addEdge(e);
        }
        return g;
    }

    /* Returns a Graph object with integer edge weights as parsed from
       FILENAME. Talk about the setup of this file. */
    public static Graph loadFromText(String filename) {
        Charset cs = Charset.forName("US-ASCII");
        try (BufferedReader r = Files.newBufferedReader(Paths.get(filename), cs)) {
            Graph g = new Graph();
            String line;
            while ((line = r.readLine()) != null) {
                String[] fields = line.split(", ");
                if (fields.length == 3) {
                    int from = Integer.parseInt(fields[0]);
                    int to = Integer.parseInt(fields[1]);
                    int weight = Integer.parseInt(fields[2]);
                    g.addEdge(from, to, weight);
                } else if (fields.length == 1) {
                    g.addVertex(Integer.parseInt(fields[0]));
                } else {
                    throw new IllegalArgumentException("Bad input file!");
                }
            }
            return g;
        } catch (IOException e) {
            System.err.println("Caught IOException: " + e.getMessage());
            System.exit(1);
            return null;
        }
    }

    // main method for simple tests
    public static void main(String[] args) {
        // Prim algorithm
        System.out.println("\n=== Prim : graphTestNormal.in ===");
        Graph g = loadFromText("inputs/graphTestNormal.in");
        Graph res = g.prims(0);
        TreeSet<Edge> allEdges = res.getAllEdges();
        for(Edge e : allEdges) {
            System.out.println(e);
        }

        System.out.println("\n=== Prim : graphTestSomeDisjoint.in ===");
        g = loadFromText("inputs/graphTestSomeDisjoint.in");
        res = g.prims(0);
        allEdges = res.getAllEdges();
        for(Edge e : allEdges) {
            System.out.println(e);
        }

        System.out.println("\n=== Prim : graphTestMultiEdge.in ===");
        g = loadFromText("inputs/graphTestMultiEdge.in");
        res = g.prims(0);
        allEdges = res.getAllEdges();
        for(Edge e : allEdges) {
            System.out.println(e);
        }

        // Kruskal algorithm
        System.out.println("\n=== Kruskal : graphTestNormal.in ===");
        g = loadFromText("inputs/graphTestNormal.in");
        res = g.kruskals();
        allEdges = res.getAllEdges();
        for(Edge e : allEdges) {
            System.out.println(e);
        }

        System.out.println("\n=== Kruskal : graphTestSomeDisjoint.in ===");
        g = loadFromText("inputs/graphTestSomeDisjoint.in");
        res = g.kruskals();
        allEdges = res.getAllEdges();
        for(Edge e : allEdges) {
            System.out.println(e);
        }

        System.out.println("\n=== Kruskal : graphTestMultiEdge.in ===");
        g = loadFromText("inputs/graphTestMultiEdge.in");
        res = g.kruskals();
        allEdges = res.getAllEdges();
        for(Edge e : allEdges) {
            System.out.println(e);
        }

        System.out.println("\n=== Kruskal : graphTestAllDisjoint.in ===");
        g = loadFromText("inputs/graphTestAllDisjoint.in");
        res = g.kruskals();
        allEdges = res.getAllEdges();
        for(Edge e : allEdges) {
            System.out.println(e);
        }
    }
}