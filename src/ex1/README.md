# ex1_oop
exercise 1 on OOP course.
This exerecise is about undirectional **weighted graphs**.
The task was to build an undirectional weighted graph with functions and algorithms.

# 1. List of the files:

node_info - Interface.

weighted_grpah - Interface.

weighted_graph_algorithms - Interface.

WGraph_DS - weighted_graph implementation + node_info as an internal class.

WGraph_Algo - graph_algorithms implementation.

WGraph_DSMyTest - Test for WGraph_DS class. 

WGraph_AlgoMyTest - Test for WGraph_Algo class.

README.md - This file.

# Data structures.

## 1.HashMap.

  This is the main Data structure i used. This Data structure have an easy way
  to save unique data for any kind of variables. HashMap has the best time comlexity
  from the Data structures i've looked.
  
  **A.** On class Wgraph_DS I used 3 HashMaps:
  
    a. to keep keys as ints and nodes as an objects.
    b. to keep edges as a String and weights as a double.
    c. to keep nodes as int and there neighbors as a linkedhashset.

  **B.** For Wgraph_Algo:
  
    I used HashMaps for any algorithm that i used.
  
  ### 2. Queue.(linkedlist)
  
    I used this Data structure for functions on WGraph_Algo. It was the best way
    to deal with list of data and check about every one on the list.
    

# Implementations.

## 1.WGraph_DS**
The functions on this class:

**Add**: add a new node to the graph and add it to "mygraph" HashMap.

**Remove**: remove one node from the graph and remove all his edges with other nodes.

**Connect:** connect between two nodes, and give a weight to the edge. If the nodes are already connected so the weight update.
"Weights" map saves edges for example: <"node1 to node2", weight>

**Has_edge**: check if there is an edge between two nodes.

**Get_edge:** returns the weight of edge between two nodes.

**Remove_edge:** remove the connection between two nodes.

**In addition** there is one more function that i wrote for the tests, it called: 

**Equals**.
This function is check if 2 graphs are equal. It checks number of nodes and edges, if all the values are equal.
This function "run over" the original Equals function.

## 2. "WGraph_Algo".** 

The functions on this class:

**Copy:** this function makes a deep copy of graph.

**Isconnected:** check if all the nodes are connected one to other in some way. On this function I used the BFS Algorithm and Dijkstra Algorithm.

**Shortest_Path_Dist:** return the path on double between two nodes. If there is no path- return -1. (Dijkstra Algorithm).
 
**Shortest_Path:** return a list of the nodes who take part to build the shortest path. For eample: 1-->3-->7-->8. (Dijkstra Algorithm).

**Save:** save the graph as a txt file. First line for example: node1key, node2key, weight. If the node alone so: node1key, null, -1.  

**Load:** load a saved graph to the program.

## Tests
There are two tests one for class. need Junit5. 
The tests checks the functions and algorithms and run time.

