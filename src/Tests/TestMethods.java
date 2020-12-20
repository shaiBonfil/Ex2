package Tests;


import api.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.Random;

public class TestMethods {


    public static void main(String[] args) {

    }

    public static directed_weighted_graph smallGraph() {
        directed_weighted_graph g = new DWGraph_DS();
        g.addNode(new NodeData(0));
        g.addNode(new NodeData(1));
        g.addNode(new NodeData(2));
        g.addNode(new NodeData(3));
        g.addNode(new NodeData(6));
        g.addNode(new NodeData(8));
        g.connect(0, 1, 1);
        g.connect(0, 2, 2);
        g.connect(0, 3, 3);
        g.connect(1, 3, 2);
        return g;

    }

    public static directed_weighted_graph connectedGraph() {
        directed_weighted_graph g = new DWGraph_DS();
        g.addNode(new NodeData(0));
        g.addNode(new NodeData(1));
        g.addNode(new NodeData(2));
        g.addNode(new NodeData(3));
        g.connect(0, 1, 1);
        g.connect(0, 2, 2.1);
        g.connect(0, 3, 4);
        g.connect(1, 3, 2);
        g.connect(3, 2, 5);
        g.connect(2, 1, 2.7);
        g.connect(3, 0, 5.2);
        return g;


    }


    public static void PrintGraph(directed_weighted_graph g) {
        for (node_data node : g.getV()) {
            System.out.println("node" + node.getKey() + " -> ");
            System.out.print("[");
            for (edge_data ni : g.getE(node.getKey())) {
                System.out.print("{" + g.getNode(ni.getDest()).getKey() + "," + g.getEdge(ni.getSrc(), ni.getDest()).getWeight() + "},");
            }
            System.out.println("]");
            System.out.println();
        }
    }

    public static boolean GraphEqualizer(directed_weighted_graph g1, directed_weighted_graph g2) {
        if (g1.edgeSize() != g2.edgeSize() || g1.nodeSize() != g2.nodeSize()) return false;
        boolean v = false;
        boolean e = false;
        for (node_data nodes1 : g1.getV()) {
            for (node_data nodes2 : g2.getV()) {
                if (nodes1.getKey() == nodes2.getKey()) v = true;
            }
            if (!v) return false;
            for (edge_data edge1 : g1.getE(nodes1.getKey())) {
                for (node_data edge2 : g2.getV()) {
                    if (edge1.getSrc() == edge2.getKey()) e = true;

                }
                if (!e) return false;

            }
        }
        return true;

    }
}
