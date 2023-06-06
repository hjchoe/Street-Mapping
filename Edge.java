package Projects.Project3;

public class Edge {
    String label;
    Node u;
    Node v;

    Edge(String label, Node u, Node v) {
        this.label = label;
        this.u = u;
        this.v = v;
    }

    @Override
    public boolean equals(Object o) {
        Edge n = (Edge) o;
        return this.label.equals(n.label);
    }

    @Override
    public String toString() {
        return "<" + label + ", " + u.label + "->" + v.label + ">";
    }
}
