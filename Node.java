package Projects.Project3;

public class Node implements Comparable<Node> {
    String label;
    Double latitude;
    Double longitude;
    Double d;
    Node pi;

    Node(String label, Double latitude, Double longitude) {
        this.label = label;
        this.latitude = latitude;
        this.longitude = longitude;
        this.d = Double.POSITIVE_INFINITY;
        this.pi = null;
    }

    void replace(Node o) {
        this.label = o.label;
        this.latitude = o.latitude;
        this.longitude = o.longitude;
        this.d = o.d;
        this.pi = o.pi;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Node n = (Node) o;
        return this.label.equals(n.label);
    }

    @Override
    public String toString() {
        return "{" + this.label + " | (" + this.latitude + ", " + this.longitude + ")}";
    }

    @Override
    public int compareTo(Node u) {
        return Double.compare(this.d, u.d);
    }
}
