package Tests;

import api.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DWGraph_DSTest {
    directed_weighted_graph g = TestMethods.smallGraph();


    @Test
    void getNode() {
        assertNotNull(g.getNode(0));
        assertNull(g.getNode(5));
    }

    @Test
    void getEdge() {
        edge_data w1 = g.getEdge(0, 2);
        assertEquals(2, w1.getWeight());
        assertNull(g.getEdge(3, 2));
    }

    @Test
    void connect() {
        g.connect(2, 3, 1);
        assertEquals(1, g.getEdge(2, 3).getWeight());
        g.connect(0, 1, 3);
        assertEquals(3, g.getEdge(0, 1).getWeight());
        g.connect(0, 3, -1);
        assertEquals(3, g.getEdge(0, 3).getWeight());
    }

    @Test
    void getV() {
        assertFalse(g.getV().isEmpty());
        assertTrue(g.getV().contains(g.getNode(1)));
        assertFalse(g.getV().contains(g.getNode(10)));
    }

    @Test
    void getE() {
        assertFalse(g.getE(0).isEmpty());
        assertTrue(g.getE(0).contains(g.getEdge(0, 3)));
        assertFalse(g.getE(1).contains(g.getEdge(1, 2)));
    }

    @Test
    void removeNode() {
        g.removeNode(0);
        assertNull(g.getNode(0));
        assertNull(g.getEdge(0, 1));
    }

    @Test
    void removeEdge() {
        g.removeEdge(0, 1);
        assertNull(g.getEdge(0, 1));
        g.connect(0, 1, 8.3);
        assertEquals(8.3, g.getEdge(0, 1).getWeight());
    }

    @Test
    void nodeSize() {
        assertEquals(6, g.nodeSize());
        assertNotEquals(2, g.nodeSize());
    }

    @Test
    void edgeSize() {
        assertEquals(4, g.edgeSize());
        assertNotEquals(2, g.edgeSize());
    }

    @Test
    void getMC() {
        g.removeEdge(0, 1);
        g.connect(3, 2, 1);
        g.connect(2, 0, 2);
        g.addNode(new NodeData(4));
        assertEquals(5, g.edgeSize());
        assertEquals(7, g.nodeSize());
        g.removeNode(4);
        assertEquals(6, g.nodeSize());
        assertEquals(15, g.getMC());
    }
}