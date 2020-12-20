package api;

import com.google.gson.*;
import gameClient.util.Point3D;

import java.io.*;
import java.util.*;

public class DWGraph_Algo implements dw_graph_algorithms {

    private directed_weighted_graph dwg;

    /**
     * Init the graph on which this set of algorithms operates on.
     *
     * @param g
     */
    @Override
    public void init(directed_weighted_graph g) {
        this.dwg = g;
    }

    /**
     * Return the underlying graph of which this class works.
     *
     * @return
     */
    @Override
    public directed_weighted_graph getGraph() {
        return this.dwg;
    }

    /**
     * Compute a deep copy of this weighted graph.
     *
     * @return
     */
    @Override
    public directed_weighted_graph copy() {
        directed_weighted_graph g1 = new DWGraph_DS(this.dwg);
        return g1;
    }

    private void TagInit(directed_weighted_graph g) {  //set the nodes tag to be 0
        for (node_data gNode : g.getV()) {
            gNode.setTag(0);
        }
    }

    private void TagInitMax() { //set the nodes tag to be max integer
        for (node_data gNode : this.dwg.getV()) {
            gNode.setTag(Integer.MAX_VALUE);
        }
    }

    private node_data getRandomNode() {
        for (node_data i : dwg.getV()) {
            return i;
        }
        return null;
    }

    private boolean DFS(directed_weighted_graph g) {
        Stack<node_data> s = new Stack<node_data>();
        int count = 0;
        TagInit(g);
        node_data src = getRandomNode();
        s.push(src);
        while (!s.empty()) {
            node_data tmp = g.getNode(s.pop().getKey());
            if (tmp.getTag() == 0) {
                tmp.setTag(1);
                count++;
                for (edge_data e : g.getE(tmp.getKey())) {
                    if (g.getNode(e.getDest()).getTag() == 0) {
                        s.push(g.getNode(e.getDest()));
                    }
                }
            }
        }
        if (count == g.nodeSize())
            return true;
        return false;
    }

    private directed_weighted_graph reverse() {
        directed_weighted_graph g = new DWGraph_DS();
        for (node_data n : this.dwg.getV())
            g.addNode(n);
        for (node_data nodes : this.dwg.getV()){
            for (edge_data edges : this.dwg.getE(nodes.getKey())){
                g.connect(edges.getDest(), edges.getSrc(), 1);
            }
        }
        return g;
    }

    /**
     * Returns true if and only if (iff) there is a valid path from each node to each
     * other node. NOTE: assume directional graph (all n*(n-1) ordered pairs).
     *
     * @return
     */
    @Override
    public boolean isConnected() {
        if (dwg.nodeSize() == 0 || dwg.nodeSize() == 1) {
            return true; // if the graph is empty or has one node
        }
        boolean flag = false;
        flag = DFS(this.dwg);
        directed_weighted_graph tranG = reverse();
        flag = flag && DFS(tranG);
        return flag;
    }

    /**
     * returns the length of the shortest path between src to dest
     * Note: if no such path --> returns -1
     *
     * @param src  - start node
     * @param dest - end (target) node
     * @return
     */
    @Override
    public double shortestPathDist(int src, int dest) {
        if (!this.dwg.getV().contains(this.dwg.getNode(src)) || !this.dwg.getV().contains(this.dwg.getNode(dest)))
            return -1;
        if (src == dest)
            return 0; // if src and dest are the same node
        Queue<node_data> q = new LinkedList<>();
        node_data s = this.dwg.getNode(src);
        node_data d = this.dwg.getNode(dest);
        node_data p;
        HashMap<Integer, Double> weight = new HashMap<Integer, Double>();
        for (node_data n : this.dwg.getV()) {
            weight.put(n.getKey(), Double.MAX_VALUE);
        }
        weight.put(s.getKey(), 0.0);
        q.add(s);
        while (!q.isEmpty()) {
            p = q.poll();
            for (edge_data edge : this.dwg.getE(p.getKey())) {
                //go through all the edges which get out from p(=src of this edge) - and finally by all the edges in the graph connected to src node (by bfs)
                if (weight.get(edge.getDest()) > weight.get(p.getKey()) + edge.getWeight()) {
                    weight.put(edge.getDest(), weight.get(p.getKey()) + edge.getWeight()); //set the weight of dest node of this edge in weight hashmap to be the weight distance of this node relative to src
                    q.add(this.dwg.getNode(edge.getDest())); //add this node to the queue

                }
            }
        }
        if (weight.get(d.getKey()) == Double.MAX_VALUE)
            return -1; // if dest node unvisited then there is not a valid path
        return weight.get(d.getKey()); // return dist of dest from src
    }


    /**
     * returns the the shortest path between src to dest - as an ordered List of nodes:
     * src--> n1-->n2-->...dest
     * see: https://en.wikipedia.org/wiki/Shortest_path_problem
     * Note if no such path --> returns null;
     *
     * @param src  - start node
     * @param dest - end (target) node
     * @return
     */
    @Override
    public List<node_data> shortestPath(int src, int dest) {
        if (!this.dwg.getV().contains(this.dwg.getNode(src)) || !this.dwg.getV().contains(this.dwg.getNode(dest)))
            return null;
        LinkedList<node_data> path = new LinkedList<>();
        if (src == dest) { // if src and dest are the same node
            path.addFirst(this.dwg.getNode(src));
            return path; //return list with that node only
        }
        TagInitMax();
        Queue<node_data> q = new LinkedList<>();
        node_data s = this.dwg.getNode(src);
        node_data d = this.dwg.getNode(dest);
        node_data p;
        HashMap<Integer, Double> weight = new HashMap<Integer, Double>();
        for (node_data n : this.dwg.getV()) {
            weight.put(n.getKey(), Double.MAX_VALUE);
        }
        weight.put(s.getKey(), 0.0);
        q.add(s);
        while (!q.isEmpty()) {
            p = q.poll();
            for (edge_data edge : this.dwg.getE(p.getKey())) {
                //go through all the edges which get out from p(=src of this edge) - and finally by all the edges in the graph connected to src node (by bfs)
                if (weight.get(edge.getDest()) > weight.get(p.getKey()) + edge.getWeight()) {
                    weight.put(edge.getDest(), weight.get(p.getKey()) + edge.getWeight()); //set the weight of dest node of this edge in weight hashmap to be the weight distance of this node relative to src
                    q.add(this.dwg.getNode(edge.getDest())); //add this node to the queue
                    this.dwg.getNode(edge.getDest()).setTag(p.getKey());
                }
            }
        }
        if (weight.get(d.getKey()) == Integer.MAX_VALUE) return null;
        node_data tmp = d;
        while (tmp != s) {
            path.addFirst(this.dwg.getNode(tmp.getKey()));
            tmp = this.dwg.getNode(tmp.getTag());
        }
        path.addFirst(s);
        return path;
    }

    /**
     * Saves this weighted (directed) graph to the given
     * file name - in JSON format
     *
     * @param file - the file name (may include a relative path).
     * @return true - iff the file was successfully saved
     */
    @Override
    public boolean save(String file) {
        try {
            Gson json = new Gson();
            JsonObject graph = new JsonObject();
            JsonArray edges = new JsonArray();
            JsonArray nodes = new JsonArray();
            for (node_data n : this.dwg.getV()) {
                JsonObject v = new JsonObject();
                v.addProperty("pos", n.getLocation().x() + "," + n.getLocation().y() + "," + n.getLocation().z());
                v.addProperty("id", n.getKey());
                nodes.add(v);
                for (edge_data edge : this.dwg.getE(n.getKey())) {
                    JsonObject e = new JsonObject();
                    e.addProperty("src", edge.getSrc());
                    e.addProperty("w", edge.getWeight());
                    e.addProperty("dest", edge.getDest());
                    edges.add(e);

                }
            }
            graph.add("Edges", edges);
            graph.add("Nodes", nodes);

            File myObj = new File(file);
            FileWriter writer = new FileWriter(file);
            writer.write(json.toJson(graph));
            writer.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    /**
     * This method load a graph to this graph algorithm.
     * if the file was successfully loaded - the underlying graph
     * of this class will be changed (to the loaded one), in case the
     * graph was not loaded the original graph should remain "as is".
     *
     * @param file - file name of JSON file
     * @return true - iff the graph was successfully loaded.
     */
    @Override
    public boolean load(String file) {
        try {
            JsonObject graph = new JsonParser().parse(new FileReader(file)).getAsJsonObject();
            JsonArray nodes = graph.getAsJsonArray("Nodes");
            JsonArray edges = graph.getAsJsonArray("Edges");
            directed_weighted_graph g = new DWGraph_DS();
            System.out.println(graph);
            System.out.println(nodes);
            for (JsonElement n : nodes) {
                node_data v = new NodeData(((JsonObject) n).get("id").getAsInt());
                v.setLocation(new Point3D((Double.parseDouble(((JsonObject) n).get("pos").getAsString().split(",")[0])), (Double.parseDouble(((JsonObject) n).get("pos").getAsString().split(",")[1])), (Double.parseDouble(((JsonObject) n).get("pos").getAsString().split(",")[2]))));
                g.addNode(v);
            }
            for (JsonElement edge : edges) {
                g.connect(((JsonObject) edge).get("src").getAsInt(), ((JsonObject) edge).get("dest").getAsInt(), ((JsonObject) edge).get("w").getAsDouble());
            }
            this.dwg = g;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }
}