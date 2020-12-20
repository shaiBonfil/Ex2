# Ex2
Author: Omer Shalom and Shai Bonfil.


Our project has two main packages. The first implementing data structure of an directed weighted graph
and operates a set of algorithms on the graph. The second part //game
The first package built from four classes implementing 4 interfaces: 
class NodeData - implements node_data interface.
class EdgeData - implements edge_data interface.
class DWGraph_DS implements directed_weighted_graph interface.
class DWGraph_Algo implements dw_graph_algorithms interface.

In NodeData class, every node has int key(id), int tag and double weight(used for algorithms), String info(node info) and
a geo_location that is the location of the node at the graph, representing by 3D(x,y,z).

In  EdgeData class, every edge has int src and dest of the current edge, int tag and double weight(used for algorithms),
and String info(edge info). 

In DWGraph_DS class, the graph has a static int node_id (every new node get his unique key), int esize(amount of edges in the graph), int mc(for counting changes in the graph) and three different HashMap because this is a directed graph:
1) storing its nodes.
2) storing all the edges that get out from every node
3) storing all the edges that get in to every node

We choose to use HashMap data structure beacuse its methods complexity are very efficient (O(1) for most methods) .

In DWGraph_Algo we implement set of algorithms operates on the graph. For implementing the algorithms on the
graph we used the Kosaraju algorithm which visiting all the connected nodes in a graph. 


NodeData class methods:
1. getKey() – returns the key (id) of the node. complexity: O(1).
2. getLocation() - returns the location of this node. complexity: O(1).
3. setLocation(geo_location p) - set the location of this node. complexity: O(1).
4. getWeight() - returns the weight of this node. complexity: O(1).
5. setWeight(double w) - set the weight of this node. complexity: O(1).
6. getInfo()  – returns the info of this node. complexity: O(1).
7. setInfo(String s) – set the info of this node. complexity: O(1).
8. getTag() – returns the tag of this node. complexity: O(1).
9. setTag(int t) - set the tag of this node. complexity: O(1).
10. constructors.

*****************************************************************************************

EdgeData class methods:
1. getSrc() – returns the src node of the edge. complexity: O(1).
2. getDest() - returns the dest node of the edge. complexity: O(1).
3. getWeight() - returns the weight of this edge. complexity: O(1).
4. getInfo()  – returns the info of this edge. complexity: O(1).
5. setInfo(String s) – set the info of this edge. complexity: O(1).
6. getTag() – returns the tag of this edge. complexity: O(1).
7. setTag(int t) - set the tag of this edge. complexity: O(1).
8. constructors.

*****************************************************************************************

DWGraph_DS class methods:
1. getNode(int key) – returns the node by his id. complexity: O(1).
2. getEdge(int src, int dest) - returns the data of the edge. complexity: O(1).
3. addNode(node_data n) - adds a new node to the graph with the given node_data. complexity: O(1).
4. connect(int src, int dest, double w) - connects an edge with weight w between node src to node dest. complexity: O(1).
5. getV() - returns a collection of all the nodes in the graph. complexity: O(1).
6. getE(int node_id) - returns a collection of all the edges getting out of the given node. complexity: O(k) k being the collection size.
7. removeNode(int key) -  returns and remove given node from the graph and removes all edges starts or ends at this node. complexity: O(k), V.degree=k.
8. removeEdge(int src, int dest) – removes the edge between 2 given nodes from the graph. complexity: O(1).
9. nodeSize() – returns the amount of nodes in the graph O(1)
10. edgeSize() – returns the amount of edges in the graph. complexity: O(1).
11. getMC() – returns the amount of changes made in the graph. complexity: O(1).
12. constructors.

*****************************************************************************************

WGraph_Algo class methods:
1. init(directed_weighted_graph g) - initiates the g graph on which this set of algorithms operates on. complexity: O(1).
2. directed_weighted_graph getGraph() - returns the weighted graph initiated at this class.
3. copy() - perform a deep copy of the graph. complexity: O(V+E).

4. is connected() - 
return a boolean type. checking if is a valid path from each node to each other node.
Starts at a random node (of a graph), and travers by DFS visiting all the nodes connected to him, and counts them.
The number of nodes visited need to be equal to this graph number of nodes. Then transpose the gragh and travers by DFS again,
and count the nodes. The number of nodes visited need to be equal to this graph number of nodes. Just if the real graph and the
transpose graph they both connected, return true. 
complexity: O(V+E).

5. shortestPathDist(int src, int dest) - 
returns the length of the shortest path between src to dest.
Assuming there is a way from src to dest - starting by src node (of this graph), visiting all the nodes connected to him (by bfs) - and set the tag of each node to be the dist from source. The algorithm is setting the weight of each node to be the sum of the edges weight on the way from src node to this node.
If there is no way from src to dest returns -1.
complexity: O(V+E).

6. shortestPath(int src, int dest) - 
returns the the shortest path between src to dest - as an ordered List of nodes.
Assuming there is a way from src to dest - starting by src node (of this graph), visiting all the nodes connected to him (by bfs) - and set the tag of each node to be the dist from source. The algorithm is setting the weight of each node to be the sum of the edges weight on the way from src node to this node.
Then the algorithm adds to a list all the nodes in the path.
If there is no way from src to dest returns null.
complexity: O(V+E).

7. save(String file) - saves this directed weighted graph to the given file name in JSON format.
8. load(String file) - load a graph to this graph algorithm, from given file name of JSON file.

