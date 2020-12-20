package api;

import java.util.*;

public class DWGraph_DS implements directed_weighted_graph {

    private HashMap<Integer, node_data> nodes;
    private HashMap<Integer, HashMap<Integer, edge_data>> edges;
    private HashMap<Integer, HashSet<Integer>> edgesIn;
    private int eSize, mc;

    public DWGraph_DS() {
        this.nodes = new HashMap<Integer, node_data>();
        this.edges = new HashMap<Integer, HashMap<Integer, edge_data>>();
        this.edgesIn = new HashMap<Integer, HashSet<Integer>>();
        this.eSize = 0;
        this.mc = 0;
    }

    public DWGraph_DS(directed_weighted_graph other) { // copy constructor
        this.nodes = new HashMap<Integer, node_data>();
        this.edges = new HashMap<Integer, HashMap<Integer, edge_data>>();
        this.edgesIn = new HashMap<Integer, HashSet<Integer>>();
        for (node_data original : other.getV()) {
            this.nodes.put(original.getKey(), new NodeData(original));
            this.edges.put(original.getKey(), new HashMap<Integer, edge_data>());
            this.edgesIn.put(original.getKey(), new HashSet<Integer>());
        }
        for (node_data original : other.getV()) {
            for (edge_data edge : other.getE(original.getKey())) {
                connect(original.getKey(), edge.getDest(), edge.getWeight());
            }
        }
        this.mc = other.getMC();
    }

    /**
     * returns the node_data by the node_id,
     *
     * @param key - the node_id
     * @return the node_data by the node_id, null if none.
     */
    @Override
    public node_data getNode(int key) {
        if (!nodes.containsKey(key))
            return null;
        return this.nodes.get(key);
    }

    /**
     * returns the data of the edge (src,dest), null if none.
     * Note: this method should run in O(1) time.
     *
     * @param src
     * @param dest
     * @return
     */
    @Override
    public edge_data getEdge(int src, int dest) {
        if (src == dest)
            return null;
        if (!nodes.containsKey(src) || !nodes.containsKey(dest))
            return null;
        if (!this.edges.get(src).containsKey(dest))
            return null;
        return edges.get(src).get(dest);
    }

    /**
     * adds a new node to the graph with the given node_data.
     * Note: this method should run in O(1) time.
     *
     * @param n
     */
    @Override
    public void addNode(node_data n) {
        if (!this.nodes.containsKey(n.getKey())) {
            this.nodes.put(n.getKey(), n);
            this.edges.put(n.getKey(),new HashMap<Integer,edge_data>());
            this.edgesIn.put(n.getKey(), new HashSet<Integer>());
            mc++;
        }
    }

    /**
     * Connects an edge with weight w between node src to node dest.
     * * Note: this method should run in O(1) time.
     *
     * @param src  - the source of the edge.
     * @param dest - the destination of the edge.
     * @param w    - positive weight representing the cost (aka time, price, etc) between src-->dest.
     */
    @Override
    public void connect(int src, int dest, double w) {
        if (this.nodes.containsKey(src) && this.nodes.containsKey(dest) && src != dest && w > 0) {
            if (this.getEdge(src, dest) == null) {
                edge_data edge = new EdgeData(src, dest, w);
                this.edges.get(src).put(dest, edge);
                this.edgesIn.get(dest).add(src);
                eSize++;
                mc++;
            } else {
                edge_data edge = new EdgeData(src, dest, w);
                this.edges.get(src).put(dest, edge);
            }
        }
    }

    /**
     * This method returns a pointer (shallow copy) for the
     * collection representing all the nodes in the graph.
     * Note: this method should run in O(1) time.
     *
     * @return Collection<node_data>
     */
    @Override
    public Collection<node_data> getV() {
        return this.nodes.values();
    }

    /**
     * This method returns a pointer (shallow copy) for the
     * collection representing all the edges getting out of
     * the given node (all the edges starting (source) at the given node).
     * Note: this method should run in O(k) time, k being the collection size.
     *
     * @param node_id
     * @return Collection<edge_data>
     */
    @Override
    public Collection<edge_data> getE(int node_id) {
        return this.edges.get(node_id).values();
    }

    /**
     * Deletes the node (with the given ID) from the graph -
     * and removes all edges which starts or ends at this node.
     * This method should run in O(k), V.degree=k, as all the edges should be removed.
     *
     * @param key
     * @return the data of the removed node (null if none).
     */
    @Override
    public node_data removeNode(int key) {
        if (this.nodes.containsKey(key)) {
            Collection<edge_data> col = this.getE(key);
            for (edge_data edge: col){
                this.edgesIn.get(edge.getDest()).remove(key);
                eSize--;
            }
            HashSet<Integer> hashSet = this.edgesIn.get(key);
            for (Integer edgeIn: hashSet) {
                this.edges.get(edgeIn).remove(key);
                eSize--;
            }
            this.edgesIn.remove(key);
            this.edges.remove(key);
            mc++;
        }
        return this.nodes.remove(key);
    }

    /**
     * Deletes the edge from the graph,
     * Note: this method should run in O(1) time.
     *
     * @param src
     * @param dest
     * @return the data of the removed edge (null if none).
     */
    @Override
    public edge_data removeEdge(int src, int dest) {
        if ( src == dest || !nodes.containsKey(src) || !nodes.containsKey(dest) || !edges.get(src).containsKey(dest))  return null;
        mc++;
        eSize--;
        return edges.get(src).remove(dest);
    }

    /**
     * Returns the number of vertices (nodes) in the graph.
     * Note: this method should run in O(1) time.
     *
     * @return
     */
    @Override
    public int nodeSize() {
        return nodes.size();
    }

    /**
     * Returns the number of edges (assume directional graph).
     * Note: this method should run in O(1) time.
     *
     * @return
     */
    @Override
    public int edgeSize() {
        return this.eSize;
    }

    /**
     * Returns the Mode Count - for testing changes in the graph.
     *
     * @return
     */
    @Override
    public int getMC() {
        return this.mc;
    }
}