package Projects.Project3;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.List;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class GraphPanel extends JPanel {
    Graph graph;
    List<Node> pathvertices;
    List<Edge> pathedges;
    private MouseSense ma;
    Node start;
    Node end;
    int startx, starty;
    int endx, endy;
    int r;
    int grabbed; // 0: didn't grab | 1: grabbed start | 2: grabbed end

    int width;
    int height;
    int margin;
    double minLatitude;
    double maxLatitude;
    double minLongitude;
    double maxLongitude;
    double latitudeRange;
    double longitudeRange;

    private class MouseSense extends MouseAdapter {
        private int x;
        private int y;

        @Override
        public void mousePressed(MouseEvent e) {
            x = e.getX();
            y = e.getY();

            if (hit(x, y, startx, starty)) {
                grabbed = 1;
            } else if (hit(x, y, endx, endy)) {
                grabbed = 2;
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            x = e.getX();
            y = e.getY();

            if (grabbed == 1) {
                graph.start = findClosestNode(x, y);
                start = graph.start;
                graph.initGraph();
                addPath(graph.directions(start.label, end.label));
            } else if (grabbed == 2) {
                graph.end = findClosestNode(x, y);
                end = graph.end;
                graph.newShortestPath(start, end);
                addPath(graph.shortestpath);
            }
            if (grabbed != 0) {
                grabbed = 0;
                repaint();
            }
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            x = e.getX();
            y = e.getY();

            if (grabbed == 1) {
                startx = x;
                starty = y;
            } else if (grabbed == 2) {
                endx = x;
                endy = y;
            }
            if (grabbed != 0) {
                repaint();
            }
        }
    }

    public GraphPanel(Graph graph) {
        this.ma = new MouseSense();
        this.addMouseMotionListener(this.ma);
        this.addMouseListener(this.ma);

        this.setOpaque(false);
        this.setBackground(new Color(255, 255, 255));
        this.setBorder(BorderFactory.createLineBorder(Color.RED));
        this.setFocusable(true);
        this.requestFocus();
        this.setLayout(null);
        this.graph = graph;
        pathedges = new ArrayList<Edge>();
        this.r = 4;
        this.grabbed = 0;
    }

    void addPath(List<Node> path) {
        this.pathvertices = path;
        pathedges = new ArrayList<Edge>();
        Edge temp;
        for (int i = path.size() - 1; i > 0; i--) {
            if ((temp = graph.findEdge(path.get(i), path.get(i - 1))) != null) {
                pathedges.add(temp);
            }
        }
    }

    Node findClosestNode(int x, int y) {
        Node closest = null;
        double closestDistance = Double.POSITIVE_INFINITY;
        double currx;
        double curry;

        // System.out.println("FINDING CLOSEST NODE TO: "+x+", "+y);

        for (Node u : graph.vertices) {
            currx = ((u.longitude - minLongitude) * (width - 2 * margin) / longitudeRange) + margin;
            curry = ((maxLatitude - u.latitude) * (height - 2 * margin) / latitudeRange) + margin;
            double distance = Math.hypot(Math.abs(x - currx), Math.abs(y - curry));
            // System.out.println("CHECKING DISTANCE FOR NODE: "+u.label+" <"+currx+",
            // "+curry+">"+" | distance: "+distance);
            if (distance < closestDistance) {
                // System.out.println(" NEW CLOSEST");
                closest = u;
                closestDistance = distance;
            }
        }
        return closest;
    }

    public boolean hit(float x, float y, float mousex, float mousey) {
        double distance = Math.hypot(mousex - x, mousey - y);
        // System.out.println("for point <"+x+", "+y+"> mouse: ("+mousex+", "+mousey+")
        // | distance: "+distance);
        return distance <= r;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        width = getWidth();
        height = getHeight();
        margin = 20;

        minLatitude = graph.vertices.stream().mapToDouble(n -> n.latitude).min().getAsDouble();
        maxLatitude = graph.vertices.stream().mapToDouble(n -> n.latitude).max().getAsDouble();
        minLongitude = graph.vertices.stream().mapToDouble(n -> n.longitude).min().getAsDouble();
        maxLongitude = graph.vertices.stream().mapToDouble(n -> n.longitude).max().getAsDouble();
        latitudeRange = maxLatitude - minLatitude;
        longitudeRange = maxLongitude - minLongitude;

        for (Edge e : graph.edges) {
            int x1 = (int) ((e.u.longitude - minLongitude) * (width - 2 * margin) / longitudeRange) + margin;
            int y1 = (int) ((maxLatitude - e.u.latitude) * (height - 2 * margin) / latitudeRange) + margin;
            int x2 = (int) ((e.v.longitude - minLongitude) * (width - 2 * margin) / longitudeRange) + margin;
            int y2 = (int) ((maxLatitude - e.v.latitude) * (height - 2 * margin) / latitudeRange) + margin;
            float linewidth = 1.0f;

            if (pathedges.contains(e)) {
                linewidth = 3.0f;
                g2.setColor(Color.BLUE);
            } else {
                g2.setColor(Color.BLACK);
            }

            g2.setStroke(new BasicStroke(linewidth, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            g2.drawLine(x1, y1, x2, y2);
        }

        // start node
        start = graph.start;
        if (grabbed == 0 && grabbed != 2) {
            startx = (int) ((start.longitude - minLongitude) * (width - 2 * margin) / longitudeRange) + margin;
            starty = (int) ((maxLatitude - start.latitude) * (height - 2 * margin) / latitudeRange) + margin;
        }

        g2.setColor(Color.GREEN);
        g2.fillOval(startx - r, starty - r, r * 2, r * 2);

        // end node
        end = graph.end;
        if (grabbed == 0 && grabbed != 1) {
            endx = (int) ((end.longitude - minLongitude) * (width - 2 * margin) / longitudeRange) + margin;
            endy = (int) ((maxLatitude - end.latitude) * (height - 2 * margin) / latitudeRange) + margin;
        }

        g2.setColor(Color.RED);
        g2.fillOval(endx - r, endy - r, r * 2, r * 2);
    }
}
