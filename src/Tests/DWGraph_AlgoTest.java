package Tests;

import api.*;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class DWGraph_AlgoTest {
    directed_weighted_graph g;
    dw_graph_algorithms ga = new DWGraph_Algo();


    @Test
    void copy() {
        g = TestMethods.smallGraph();
        directed_weighted_graph gcopy;
        dw_graph_algorithms ga1 = new DWGraph_Algo();

        ga.init(g);
        gcopy = ga.copy();
        ga1.init(gcopy);
        assertTrue(TestMethods.GraphEqualizer(ga.getGraph(), ga1.getGraph()));

        gcopy.removeEdge(0, 3);
        gcopy.connect(0, 3, 3);
        assertTrue(TestMethods.GraphEqualizer(ga.getGraph(), ga1.getGraph()));

        gcopy.removeNode(2);
        assertFalse(TestMethods.GraphEqualizer(ga.getGraph(), ga1.getGraph()));


    }

    @Test
    void isConnected() {
        g = new DWGraph_DS();
        ga.init(g);
        assertTrue(ga.isConnected());

        g.addNode(new NodeData(0));
        assertTrue(ga.isConnected());

        g.addNode(new NodeData(1));
        assertFalse(ga.isConnected());

        g.connect(0, 1, 0.45);
        g.connect(1, 0, 4.73);
        assertTrue(ga.isConnected());

        g = TestMethods.connectedGraph();
        ga.init(g);
        assertTrue(ga.isConnected());

        g.removeEdge(3, 0);
        assertFalse(ga.isConnected());

        g.connect(1, 0, 4.3);
        assertTrue(ga.isConnected());

        g.removeNode(2);
        assertFalse(ga.isConnected());

        g.connect(3, 1, 0.2);
        assertTrue(ga.isConnected());


    }

    @Test
    void shortestPathDist() {
        g = TestMethods.connectedGraph();
        ga.init(g);
        assertEquals(3, ga.shortestPathDist(0, 3));

        g.connect(0, 3, 6);
        assertEquals(3, ga.shortestPathDist(0, 3));

        g.addNode(new NodeData(4));
        g.connect(0, 4, 12.7);
        g.connect(3, 4, 4.2);
        assertEquals(7.2, ga.shortestPathDist(0, 4));
        assertEquals(0, ga.shortestPathDist(4, 4));

        g.connect(0, 3, 1.5);
        assertEquals(1.5, ga.shortestPathDist(0, 3));

        g.connect(0, 4, 1.7);
        assertEquals(1.7, ga.shortestPathDist(0, 4));


    }

    @Test
    void shortestPath() {
        g = TestMethods.connectedGraph();
        ga.init(g);

        LinkedList<node_data> path = new LinkedList<>();
        path.addLast(g.getNode(0));

        assertEquals(path, ga.shortestPath(0, 0));

        g.addNode(new NodeData(4));
        g.connect(0, 4, 12.8);
        g.connect(3, 4, 4.2);
        path.addLast(g.getNode(1));
        assertEquals(path, ga.shortestPath(0, 1));

        path.addLast(g.getNode(3));
        assertEquals(path, ga.shortestPath(0, 3));

        path.addLast(g.getNode(4));
        assertEquals(path, ga.shortestPath(0, 4));

        path.remove(g.getNode(4));
        assertEquals(path, ga.shortestPath(0, 3));

    }

    @Test
    void save_load() throws FileNotFoundException {
        g = TestMethods.connectedGraph();
        ga.init(g);
        directed_weighted_graph g1 = ga.copy();
        ga.save("C:\\Users\\omer2\\Desktop\\g0");
        ga.load("C:\\Users\\omer2\\Desktop\\g0");
        assertTrue(TestMethods.GraphEqualizer(ga.getGraph(), g1));


        dw_graph_algorithms ga1 = new DWGraph_Algo();
        ga1.load("C:\\Users\\omer2\\Desktop\\g0");
        assertTrue(TestMethods.GraphEqualizer(ga.getGraph(), ga1.getGraph()));

    }

}