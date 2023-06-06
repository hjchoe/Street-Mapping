James Choe
CSC 172-Lab 2
hchoe4
32242362
hchoe4@u.rochester.edu

<===== STEPS =====>
    * steps taken when working on project
command line arguments
take in file data inputs
create graph
dijkstra's algorithm + shortest path
graphics
make it work with monroe.txt
make it work with nys.txt
extra credit graphics (drag and drop start and end positions)

<===== CLASSES =====>
proj3.java
    contains main class
    takes in input to graph and contains graph
Node.java
    graph node object that contains its label, latitude, longitude, d, and pi
Edge.java
    graph edge object that contains its label and two node objects it connects to
Graph.java
    graph object that contains nodes and edges lists, adjacency list, Dijkstra object, GraphPanel object, and shortest path
Dijkstra.java
    Dijkstra's Algorithm and helper functions
GraphPanel.java
    contains MouseSense class that extends MouseAdapter
    contains all functions required to draw graphics of graph and shortest path
    includes extra credit drag and drop feature

<===== INPUT =====>
java proj3.java map.txt [ -- show ] [-- directions startIntersection endIntersection ]

<===== OUTPUT =====>
    * how final output looks
----- Output -----
Path:
* includes path from start node to finish node represented by '->'

Distance: *total distance of path in miles* mi

<===== NOTES =====>
The program uses the haversine formula as the weight function to calculate distances between latitude and longitude degree points
The program uses a linear scaling method for converting latitude and longitude coordinates to 2D coordinates for the graph representation
The program implements a drag and drop feature for start and end nodes on graph representation

<===== EXTRA CREDIT =====>
The map highlights the shortest path from start and finish nodes using a thicker blue line.
The start node is highlighted as green circle on the map.
The finish node is highlighted as a red circle on the map.
The start and finish nodes are both able to be clicked and dragged to a new location.
    The program then finds the closest node and replaces that node with the new one and shows the new shortest path with an updated output.
    * note: for the large nys.txt map, the start and finish nodes may need to be held while it calculates until the map updates. (make take awhile)
