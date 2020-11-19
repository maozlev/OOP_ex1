# ex1_oop
exercise 1 on OOP course.

This exerecise is about undirectional weighted graphs.

The task was to build an undirectional weighted graph with functions and algorithms.
## The first class called "WGraph_DS" there all the simple techniques. 
There is one class who added to this class as a private calss, 
it called **"node_info"**- an implemintation to node_info interface. 
This class impliments to interfaces: "node_info" and "weighted_graph".
On this class I used 3 HashMaps: **first**, to keep keys as ints and nodes as an objects. **Second**, to keep edges as a String and weights as a double.
**third**, to keep nodes as int and there neighbors as a linkedhashset.

## The functions on this class:
**Add**: add a new node to the graph and add it to "mygraph" HashMap. 
**Remove**: remove one node from the graph and remove all his edges with other nodes. 
**Connect:** connect between two nodes, and give a weight to the edge. If the nodes are already connected so the weight update. 
"Weights" map saves edges for example: <"node1 to node2", weight>
**Has_edge**: check if there is an edge between two nodes.
**Get_edge:** returns the weight of edge between two nodes.
**Remove_edge:** remove the connection between two nodes.

**In addition** there is one more function that i wrote for the tests, it called: Equals.
This function is check if 2 graphs are equal. It checks number of nodes and edges, if all the values are equal.
This function "run over" the original Equals function.

## Second class called "WGraph_Algo". The functions on this class:
 **Copy:** this function makes a deep copy of graph.
 **Isconnected:** check if all the nodes are connected one to other in some way. On this function I used the BFS Algorithm and Dijkstra Algorithm.
 **Shortest_Path_Dist:** return the path on double between two nodes. If there is no path- return -1. (Dijkstra Algorithm).
 **Shortest_Path:** return a list of the nodes who take part to build the shortest path. For eample: 1-->3-->7-->8. (Dijkstra Algorithm).
 **Save:** save the graph as a txt file. First line for example: node1key, node2key, weight. If the node alone so: node1key, null, -1.  
 **Load:** load a saved graph to the program.

## Tests
There are two tests one for class. need Junit5. 
The tests checks the functions and algorithms and run time.

