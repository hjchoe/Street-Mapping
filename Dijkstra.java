package Projects.Project3;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

public class Dijkstra {
    final double radius = 3958.8d;
    Graph g;
    Queue<Node> pq;
    Set<Node> S;

    Dijkstra(Graph graph, Node u) {
        this.g = graph;
        u.d = 0d;
        S = new HashSet<Node>();
        pq = new PriorityQueue<Node>();

        executeDijkstra(u);
    }

    void executeDijkstra(Node u) {
        // displayPQ();

        pq.add(u);

        while (!pq.isEmpty()) {
            Node curr = pq.poll();
            // System.out.println("\nPOLLED: "+curr+" | "+g.adjacencyList.get(curr));
            S.add(curr);
            for (Node n : g.adjacencyList.get(curr)) {
                // System.out.println(" check relax: "+n.label+" | d:"+n.d+" | pi: "+n.pi);
                relax(curr, n);
            }
            // displayPQ();
        }

        System.out.println("Set (" + S.size() + ")");
        for (Node n : S) {
            if (n.d == Double.POSITIVE_INFINITY)
                System.out.println(n.label + " | " + n.d);
        }

    }

    void relax(Node u, Node v) {
        // System.out.println(" COMPARING: "+v.label+" | <"+v.d+", "+v.pi+"> WITH "+(u.d
        // + weight(u, v)));
        if (v.d > (u.d + weight(u, v))) {
            pq.remove(v);
            v.d = u.d + weight(u, v);
            v.pi = u;
            pq.add(v);
            // System.out.println(" RELAXED " +v.label+" TO "+v.d+" FROM "+v.pi);
        }
    }

    void displayPQ() {
        System.out.println("\n----- PQ (" + pq.size() + ") -----");

        PriorityQueue<Node> temp = new PriorityQueue<>(pq);

        while (!temp.isEmpty()) {
            Node n = temp.poll();
            System.out.println(n.label + " | d: " + n.d + " | pi: " + n.pi);
        }
    }

    List<Node> getPath(Node u, Node v) {
        List<Node> path = new ArrayList<Node>();
        Node curr = v;

        while (curr != u) {
            path.add(curr);
            if (curr.pi == null) {
                System.out.println("NO POSSIBLE PATH");
                break;
            }
            curr = curr.pi;
        }
        path.add(u);

        g.start = u;
        g.end = v;

        return path;
    }

    double weight(Node u, Node v) {
        double ulat = u.latitude;
        double vlat = v.latitude;
        double ulong = u.longitude;
        double vlong = v.longitude;

        double latdist = Math.toRadians(vlat - ulat);
        double longdist = Math.toRadians(vlong - ulong);

        return 2 * radius * Math.asin(Math.sqrt(Math.pow(Math.sin((latdist) / 2d), 2d) + Math.cos(Math.toRadians(ulat))
                * Math.cos(Math.toRadians(vlat)) * Math.pow(Math.sin((longdist) / 2d), 2d)));
    }

    void test(Node u, Node v) {
        Double distance = weight(u, v);

        System.out.println("Distance: " + distance + " mi");
        System.out.println("From: " + u);
        System.out.println("To: " + v);

        System.out
                .println("TEST: " + weight(new Node("u", 51.510357, -0.116773), new Node("v", 38.889931, -77.009003)));
    }
}
