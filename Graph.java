package Projects.Project3;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;

public class Graph {
    List<Node> vertices;
    List<Edge> edges;
    Map<Node, List<Node>> adjacencyList;
    JFrame frame;
    GraphPanel gp;
    Node start;
    Node end;
    Dijkstra d;
    List<Node> shortestpath;

    Graph() {
        this.vertices = new ArrayList<Node>();
        this.edges = new ArrayList<Edge>();
        this.adjacencyList = new HashMap<Node, List<Node>>();
        this.gp = new GraphPanel(this);
    }

    void initGraph() {
        for (Node u : vertices) {
            u.d = Double.POSITIVE_INFINITY;
            u.pi = null;
        }
    }

    void addNode(String ID, double latitude, double longitude) {
        vertices.add(new Node(ID, latitude, longitude));
    }

    void addEdge(String ID, String uID, String vID) {
        Node u = vertices.get(vertices.indexOf(new Node(uID, null, null)));
        Node v = vertices.get(vertices.indexOf(new Node(vID, null, null)));
        edges.add(new Edge(ID, u, v));
        if (adjacencyList.containsKey(u)) {
            adjacencyList.get(u).add(v);
        } else {
            List<Node> temp = new ArrayList<Node>();
            temp.add(v);
            adjacencyList.put(u, temp);
        }
        if (adjacencyList.containsKey(v)) {
            adjacencyList.get(v).add(u);
        } else {
            List<Node> temp = new ArrayList<Node>();
            temp.add(u);
            adjacencyList.put(v, temp);
        }
    }

    Node getNode(String ID) {
        return vertices.get(vertices.indexOf(new Node(ID, null, null)));
    }

    Edge getEdge(String ID) {
        return edges.get(edges.indexOf(new Edge(ID, null, null)));
    }

    Edge findEdge(Node u, Node v) {
        for (Edge e : edges) {
            if (e.u == u && e.v == v) {
                return e;
            }
        }
        for (Edge e : edges) {
            if (e.u == v && e.v == u) {
                return e;
            }
        }
        System.out.println("FAILED EDGE SEARCH: " + u + " -- " + v);
        return null;
    }

    void show() {
        System.out.println("\nSHOW MAP");
        frame = new JFrame("Graph");
        frame.setPreferredSize(new Dimension(700, 700));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(gp, BorderLayout.CENTER);
        frame.getContentPane().setBackground(Color.LIGHT_GRAY);
        frame.pack();
        frame.setVisible(true);
        frame.setFocusable(false);
    }

    List<Node> directions(String startID, String finishID) {
        initGraph();
        System.out.println("\nDIRECTIONS FROM " + startID + " TO " + finishID + ":");

        Node u = getNode(startID);
        Node v = getNode(finishID);
        System.out.println("Running Dijkstra's Algorithm...");
        d = new Dijkstra(this, u);
        System.out.println("    FINISHED");

        return newShortestPath(u, v);
    }

    List<Node> newShortestPath(Node u, Node v) {
        end = v;
        System.out.println("\nGetting Shortest Path...");
        shortestpath = d.getPath(u, v);
        System.out.println("    FINISHED");
        System.out.println("\n----- Output -----\nPath:");
        StringBuilder sb = new StringBuilder();
        sb.append(shortestpath.get(shortestpath.size() - 1).label);
        for (int i = shortestpath.size() - 2; i >= 0; i--) {
            sb.append(" -> " + shortestpath.get(i).label);
        }
        sb.append("\n\nDistance: " + v.d + " mi");

        System.out.println(sb.toString());
        return shortestpath;
    }

    void TEMPDISPLAY() {
        System.out.println("\nVERTICES");
        for (Node n : vertices) {
            System.out.println(n);
        }
        System.out.println("\nEDGES");
        for (Edge e : edges) {
            System.out.printf("[%s | (%s, %s)]%n", e.label, e.u, e.v);
        }
        System.out.println("\nADJACENCY LIST");
        int size = 0;
        for (Node n : adjacencyList.keySet()) {
            System.out.printf("%s: %s%n", n, adjacencyList.get(n));
            size += adjacencyList.get(n).size();
        }
        System.out.println("\nSTATS");
        System.out.printf("# of vertices: %d%n", vertices.size());
        System.out.printf("# of edges: %d%n", edges.size());
        System.out.printf("# of both: %d%n", vertices.size() + edges.size());
        System.out.printf("# of keys in adjacency list: %d%n", adjacencyList.size());
        System.out.printf("# of values in adjacency list: %d%n", size);
    }
}
