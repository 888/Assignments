import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

/**
 * Your implementations of various graph algorithms.
 *
 * @author Alan Chiang
 * @version 1.0
 */
public class GraphAlgorithms {

    /**
     * Perform breadth first search on the given graph, starting at the start
     * Vertex.  You will return a List of the vertices in the order that
     * you visited them.  Make sure to include the starting vertex at the
     * beginning of the list.
     *
     * When exploring a Vertex, make sure you explore in the order that the
     * adjacency list returns the neighbors to you.  Failure to do so may
     * cause you to lose points.
     *
     * The graph passed in may be directed or undirected, but never both.
     *
     * You may import/use {@code java.util.Queue}, {@code java.util.Set},
     * {@code java.util.Map}, {@code java.util.List}, and any classes
     * that implement the aforementioned interfaces.
     *
     * @throws IllegalArgumentException if any input is null, or if
     *         {@code start} doesn't exist in the graph
     * @param start the Vertex you are starting at
     * @param graph the Graph we are searching
     * @param <T> the data type representing the vertices in the graph.
     * @return a List of vertices in the order that you visited them
     */
    public static <T> List<Vertex<T>> breadthFirstSearch(Vertex<T> start,
                                                         Graph<T> graph) {
        if ((graph == null) || (start == null)) {
            throw new IllegalArgumentException(
                    "Graph or starting vertex are null");
        }
        Map<Vertex<T>, List<VertexDistancePair<T>>> adjList
                = graph.getAdjacencyList();
        if (adjList.get(start) == null) {
            throw new IllegalArgumentException(
                    "Graph missing start vertex");
        }
        List<Vertex<T>> output = new LinkedList<>();
        Set<Vertex<T>> visited = new HashSet<>();
        Queue<Vertex<T>> queue = new LinkedList<>();
        queue.add(start);
        while (!queue.isEmpty()) {
            Vertex<T> current = queue.remove();
            if (!visited.contains(current)) {
                output.add(current);
                visited.add(current);
                for (VertexDistancePair<T> adjacent : adjList.get(current)) {
                    queue.add(adjacent.getVertex());
                }
            }
        }
        return output;
    }

    /**
     * Perform depth first search on the given graph, starting at the start
     * Vertex.  You will return a List of the vertices in the order that
     * you visited them.  Make sure to include the starting vertex at the
     * beginning of the list.
     *
     * When exploring a Vertex, make sure you explore in the order that the
     * adjacency list returns the neighbors to you.  Failure to do so may
     * cause you to lose points.
     *
     * The graph passed in may be directed or undirected, but never both.
     *
     * You MUST implement this method recursively.
     * Do not use any data structure as a stack to avoid recursion.
     * Implementing it any other way WILL cause you to lose points!
     *
     * You may import/use {@code java.util.Set}, {@code java.util.Map},
     * {@code java.util.List}, and any classes that implement the
     * aforementioned interfaces.
     *
     * @throws IllegalArgumentException if any input is null, or if
     *         {@code start} doesn't exist in the graph
     * @param start the Vertex you are starting at
     * @param graph the Graph we are searching
     * @param <T> the data type representing the vertices in the graph.
     * @return a List of vertices in the order that you visited them
     */
    public static <T> List<Vertex<T>> depthFirstSearch(Vertex<T> start,
                                                       Graph<T> graph) {
        if ((graph == null) || (start == null)) {
            throw new IllegalArgumentException(
                    "Graph or starting vertex are null");
        }
        Map<Vertex<T>, List<VertexDistancePair<T>>> adjList
                = graph.getAdjacencyList();
        if (adjList.get(start) == null) {
            throw new IllegalArgumentException(
                    "Graph missing start vertex");
        }
        List<Vertex<T>> output = new LinkedList<>();
        Set<Vertex<T>> visited = new HashSet<>();
        depthHelp(start, adjList, output, visited);
        return output;
    }

    /**
     * Recursive helper method for depth first search.
     *
     * @param current the node being checked
     * @param adjList the adjacency list representing the graph
     * @param output list containing the order that the nodes were visited
     * @param visited the set of visited nodes
     * @param <T> the data type representing the vertices in the graph.
     */
    private static <T> void depthHelp(Vertex<T> current, Map<Vertex<T>,
            List<VertexDistancePair<T>>> adjList,
                                      List<Vertex<T>> output,
                                      Set<Vertex<T>> visited) {
        if (!visited.contains(current)) {
            output.add(current);
            visited.add(current);
            if (adjList.get(current) != null) {
                for (VertexDistancePair<T> adjacent : adjList.get(current)) {
                    depthHelp(adjacent.getVertex(), adjList, output, visited);
                }
            }
        }
    }

    /**
     * Find the shortest distance between the start vertex and all other
     * vertices given a weighted graph where the edges only have positive
     * weights.
     *
     * Return a map of the shortest distances such that the key of each entry is
     * a node in the graph and the value for the key is the shortest distance
     * to that node from start, or Integer.MAX_VALUE (representing infinity)
     * if no path exists. You may assume that going from a vertex to itself
     * has a distance of 0.
     *
     * There are guaranteed to be no negative edge weights in the graph.
     * The graph passed in may be directed or undirected, but never both.
     *
     * You may import/use {@code java.util.PriorityQueue},
     * {@code java.util.Map}, and any class that implements the aforementioned
     * interface.
     *
     * @throws IllegalArgumentException if any input is null, or if
     *         {@code start} doesn't exist in the graph
     * @param start the Vertex you are starting at
     * @param graph the Graph we are searching
     * @param <T> the data type representing the vertices in the graph.
     * @return a map of the shortest distances from start to every other node
     *         in the graph.
     */
    public static <T> Map<Vertex<T>, Integer> dijkstras(Vertex<T> start,
                                                        Graph<T> graph) {
        if ((graph == null) || (start == null)) {
            throw new IllegalArgumentException(
                    "Graph or starting vertex are null");
        }
        Map<Vertex<T>, List<VertexDistancePair<T>>> adjList
                = graph.getAdjacencyList();
        if (adjList.get(start) == null) {
            throw new IllegalArgumentException(
                    "Graph missing start vertex");
        }
        Map<Vertex<T>, Integer> output = new HashMap<>();
        PriorityQueue<Vertex<T>> pq = new PriorityQueue<>(
                adjList.size(), (a, b) -> output.get(a) - output.get(b));
        for (Vertex<T> vertex : adjList.keySet()) {
            output.put(vertex, Integer.MAX_VALUE);
        }
        output.put(start, 0);
        pq.add(start);
        while (!pq.isEmpty()) {
            Vertex<T> current = pq.remove();
            for (VertexDistancePair<T> adjacent : adjList.get(current)) {
                int outCurr = output.get(current);
                Vertex<T> adjV = adjacent.getVertex();
                int adjD = adjacent.getDistance();
                if ((outCurr + adjD) < output.get(adjV)) {
                    output.put(adjV, outCurr + adjD);
                    pq.add(adjV);
                }
            }
        }
        return output;
    }

    /**
     * Run Kruskal's algorithm on the given graph and return the minimum
     * spanning tree in the form of a set of Edges. If the graph is
     * disconnected, and therefore there is no valid MST, return null.
     *
     * You may assume that there will only be one valid MST that can be formed.
     * In addition, only an undirected graph will be passed in.
     *
     * You may import/use {@code java.util.PriorityQueue},
     * {@code java.util.Set}, {@code java.util.Map} and any class from java.util
     * that implements the aforementioned interfaces.
     *
     * @throws IllegalArgumentException if graph is null
     * @param graph the Graph we are searching
     * @param <T> the data type representing the vertices in the graph.
     * @return the MST of the graph; null if no valid MST exists.
     */
    public static <T> Set<Edge<T>> kruskals(Graph<T> graph) {
        if (graph == null) {
            throw new IllegalArgumentException("Graph is null");
        }
        int mstSize = (graph.getAdjacencyList().size()) - 1;
        Set<Edge<T>> output = new HashSet<>();
        Map<Vertex, DisjointSet> map = new HashMap<>();
        for (Vertex<T> current : graph.getAdjacencyList().keySet()) {
            map.put(current, new DisjointSet());
        }
        PriorityQueue<Edge<T>> pq = new PriorityQueue<>(graph.getEdgeList());
        while (!(pq.isEmpty()) && (output.size() < mstSize)) {
            Edge<T> e = pq.poll();
            Vertex<T> v = e.getV();
            Vertex<T> u = e.getU();
            DisjointSet j = map.get(v).find();
            DisjointSet k = map.get(u).find();
            if (j != k) {
                j.union(k);
                output.add(e);
            }
        }
        if (output.size() != mstSize) {
            return null;
        }
        return output;
    }
}