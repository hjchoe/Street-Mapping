package Projects.Project3;

import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class proj3 {
    String path;
    Graph g;
    Boolean show;
    Boolean directions;
    String startID;
    String finishID;

    proj3(String path) {
        this.path = path;
        this.g = new Graph();
        this.show = false;
        this.directions = false;
    }

    void initGraph() throws FileNotFoundException {
        Scanner s = new Scanner(new FileReader(path));
        while (s.hasNextLine()) {
            String[] args = s.nextLine().split("\t");
            if (args[0].equals("i")) {
                g.addNode(args[1], Double.parseDouble(args[2]), Double.parseDouble(args[3]));
            } else if (args[0].equals("r")) {
                g.addEdge(args[1], args[2], args[3]);
            }
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        proj3 map = new proj3(args[0]);

        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("--show")) {
                map.show = true;
            } else if (args[i].equals("--directions")) {
                map.directions = true;
                map.startID = args[++i];
                map.finishID = args[++i];
            }
        }

        System.out.println("Initializing Graph...");
        map.initGraph();
        System.out.println("    FINISHED");

        if (map.directions) {
            map.g.directions(map.startID, map.finishID);
        }
        if (map.show) {
            if (map.directions)
                map.g.gp.addPath(map.g.shortestpath);
            map.g.show();
        }
    }
}
