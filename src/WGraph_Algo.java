package ex1.src;

public class WGraph_Algo implements weighted_graph_algorithms {

    weighted_graph mygraph;

    public WGraph_Algo() {
        mygraph = new WGraph_DS();
    }

    public WGraph_Algo(weighted_graph g) {
        g = new WGraph_DS();
    }

    @Override
    public void init(weighted_graph g) {
        mygraph = g;
    }

    @Override
    public weighted_graph getGraph() {
        return mygraph;
    }

    /**
     * Compute a deep copy of this weighted graph.
     * @return
     */

    @Override
    public weighted_graph copy() {
        weighted_graph g = new WGraph_DS();
        if (mygraph != null) {
            for (node_info old : mygraph.getV()) { // make new nodes for the new graph and add them
                node_info p = old;
                g.addNode(p.getKey());
            }
            for (node_info old : mygraph.getV()) { // pass all over nodes of the graph
                for (node_info copynode : mygraph.getV(old.getKey())) { // pass all over the neighbors
                    double oldweight = mygraph.getEdge(copynode.getKey(), old.getKey()); // save the weight of the edge
                    g.connect(copynode.getKey(), old.getKey(), oldweight); // connect the new nodes with same weight
                }
            }
        }
        return g;
    }

    /**
     * Returns true if and only if (iff) there is a valid path from EVREY node to each
     * other node. NOTE: assume ubdirectional graph.
     *
     * @return
     */

    @Override
    public boolean isConnected() { //based on Dijkstra Algorithm/ BFS (both)
        if (mygraph.nodeSize() == 0) return true; // if the graph is empty return true because he is connected
        if (mygraph == null) return true; // if the graph is empty return true because he is connected
        Queue<node_info> q = new LinkedList<node_info>(); // this queue will contain nodes to check all the neighbors.
        for (node_info cheacktag : mygraph.getV()) {
            cheacktag.setInfo(""); //make all the info to ""
            cheacktag.setTag(Double.MAX_VALUE); // set all the tags to MAX_VALUE
        }
        node_info p = null;
        for (node_info node : mygraph.getV()) { // catch the first node of the graph. it could be some rnd..
            p = node;
            break;
        }
        p.setTag(0.0); // make the first node with zero at his tag, his parent/info still ""
        q.add(p);
        while (!q.isEmpty()) {
            p = q.remove(); // remove the first node from the queue
            for (node_info node : mygraph.getV(p.getKey())) { // check all his neighbors
                if (p.getInfo() == "") { // if his info is "" it seems that we didn't check all the neighbors
                    double t = p.getTag(); // t= the distance took to get the neighbor
                    double e = mygraph.getEdge(p.getKey(), node.getKey());// the weight between node to the neighbor
                    if (e + t < node.getTag()) {// if this path is shorter from what is in his neighbor's tag
                        node.setTag(e + t);// replace the distance to the shorter one
                        q.add(node); // adding this node to the queue because we have connection
                    }
                }
            }
            p.setInfo("1"); // after reaching the all neighbors, mark this node at his tag with 1
        }
        for (node_info checktag : mygraph.getV()) {
            // if there is one node that his tag is MAX_VALUE:
            // this graph is not connected!
            if (checktag.getTag() == Double.MAX_VALUE) return false;
        }
        return true;
    }

    /**
     * returns the length of the shortest path between ex1.ex1.src to dest
     * Note: if no such path --> returns -1
     *
     * @param src  - start node
     * @param dest - end (target) node
     * @return
     */

    @Override

    public double shortestPathDist(int src, int dest) { //based on Dijkstra Algorithm
        if (mygraph == null) return -1; // empty graph dosnt have those nodes return -1
        if (src == dest) return 0; // there is no need to find a path from node to himself- return 0
        Queue<node_info> q = new LinkedList<node_info>(); // in this queue every node enter and check his neighbors
        HashMap<node_info, node_info> daddy = new HashMap<node_info, node_info>(); // data structure that save the parent of node
        for (node_info cheacktag : mygraph.getV()) {
            cheacktag.setInfo(""); // make all the info to ""
            cheacktag.setTag(Double.MAX_VALUE); // MAX_VALUE all tags of nodes
            daddy.put(cheacktag, null); // make the daddy map to save all nodes fathers
        }
        node_info _src = mygraph.getNode(src); // catch the first node- ex1.ex1.src- source
        node_info _dest = mygraph.getNode(dest); // catch the last node- dest- destination
        if (_dest == null || _src == null) return -1; // if those nodes are not exist return -1
        _src.setTag(0); // tag of first become 0
        q.add(_src); // the first node enter to the queue to start the check
        while (!q.isEmpty()) {
            node_info p = q.remove(); // remove the first node
            for (node_info node : mygraph.getV(p.getKey())) { // and start check his neighbors
                if (p.getInfo() == "") { // if the info is "" it tells that are still neighbors that has not been checked
                    double t = p.getTag(); // the shortest distance saved in the tag
                    double e = mygraph.getEdge(p.getKey(), node.getKey()); // the weight of the edge between 2 nodes
                    //that are compared
                    if (e + t < node.getTag()) { // if the new distance is shorter from the distance in the node tag
                        node.setTag(e + t); // replace with the shorter one
                        daddy.replace(node, p); // and replace with the "shorter daddy"
                        q.add(node); // add this neighbor node to the queue for continue the checking
                    }
                }
            }
            p.setInfo("1"); // after all neighbors comparisons mark this node. this node is not necessary anymore
        }
        if (daddy.get(_dest) == null || _dest.getTag() == Double.MAX_VALUE) return -1;
        // if the destination node dosnt have a daddy
        // or its tag still MAX_VALUE it looks like there is no path. return -1
        return _dest.getTag(); // return destination tag- the shortest path.
    }

    /**
     * returns the the shortest path between ex1.ex1.src to dest - as an ordered List of nodes:
     * ex1.ex1.src--> n1-->n2-->...dest
     * see: https://en.wikipedia.org/wiki/Shortest_path_problem
     * Note if no such path --> returns null;
     *
     * @param src  - start node
     * @param dest - end (target) node
     * @return
     */

    @Override
    public List<node_info> shortestPath(int src, int dest) { //based on Dijkstra Algorithm
        if (mygraph == null) return null; // there is no path with empty graph
        HashMap<node_info, node_info> daddy = new HashMap<node_info, node_info>();  // data structure that save the parent of node
        Queue<node_info> q = new LinkedList<node_info>(); // in this queue every node enter and check his neighbors
        LinkedList<node_info> path = new LinkedList<>(); // that's the list will returned with the wanted path
        node_info _src = mygraph.getNode(src); // catch the first node- ex1.ex1.src- source
        node_info _dest = mygraph.getNode(dest); // catch the last node- dest- destination
        if (_dest == null || _src == null) return null;
        if (src == dest) { // if ex1.ex1.src is equal to destination return this one node
            path.add(_dest);
            return path;
        }
        for (node_info cheacktag : mygraph.getV()) {
            cheacktag.setInfo(""); // make all the info to ""
            cheacktag.setTag(Double.MAX_VALUE); // MAX_VALUE all tags of nodes
            daddy.put(cheacktag, null); // make the daddy map to save all nodes fathers
        }
        _src.setTag(0); // tag of first become 0
        q.add(_src); // the first node enter to the queue to start the check
        while (!q.isEmpty()) {
            node_info p = q.remove(); // remove the first node
            for (node_info node : mygraph.getV(p.getKey())) { // and start check his neighbors
                if (p.getInfo() == "") { // if the info is "" it tells that are still neighbors that has not been checked
                    double t = p.getTag(); // the shortest distance saved in the tag
                    double e = mygraph.getEdge(p.getKey(), node.getKey()); // the weight of the edge between 2 nodes
                    //that are compared
                    if (e + t < node.getTag()) { // if the new distance is shorter from the distance in the node tag
                        node.setTag(e + t); // replace with the shorter one
                        daddy.replace(node, p); // and replace with the "shorter daddy"
                        q.add(node); // add this neighbor node to the queue for continue the checking
                    }
                }
            }
            p.setInfo("1"); // after all neighbors comparisons mark this node. this node is not necessary anymore
        }
        if (daddy.get(_dest) == null || _dest.getTag() == Double.MAX_VALUE) return null;
        //if destination have no daddy or his tag is MAX_VALUE it seems the algorithm
        // didnt reached to this node- there is no path-  return null
        node_info p = _dest;
        while (daddy.get(p) != null) { // build a path from destination to source in reverse
            path.add(p);
            p = daddy.get(p);
        }
        path.add(_src);
        Stack<node_info> path1 = new Stack<>(); // the path is reversed but the demand is ordered list
        p = path.getFirst(); // so the reverse path get into stack
        path.removeFirst();
        while (p != null) {
            path1.push(p);
            p = path.poll();
        }
        while (!path1.empty()) { //then pop from the stack to the list
            path.add(path1.pop()); // now the list in the right order
        }
        return path;
    }

    /**
     * Saves this weighted (undirected) graph to the given
     * file name
     *
     * @param file - the file name (may include a relative path).
     * @return true - iff the file was successfully saved
     */

    @Override
    public boolean save(String file) {
        boolean flag = false; // flag to return
        boolean flag_lone = false; // flag to figure out if node without neighbors
        if (mygraph.nodeSize() == 0) {
            System.out.println("the graph is empty");
            return false;
        }
        for (node_info cheacktag : mygraph.getV()) { // zero all tag for marking
            cheacktag.setTag(0);
        }
        try {
            PrintWriter pw = new PrintWriter(new File(file));
            StringBuilder sb = new StringBuilder();
            for (node_info node : mygraph.getV()) {
                flag_lone = false;
                if (mygraph.getV(node.getKey()) != null) {
                    for (node_info Ni : mygraph.getV(node.getKey())) {
                        flag_lone = true; // if this flag is true it seems the node have neighbors and its not print alone
                        if (Ni.getTag() == 0) { // build String that looks like: node1, node2, weight
                            sb.append(node.getKey());
                            sb.append(",");
                            sb.append(Ni.getKey());
                            sb.append(",");
                            sb.append(mygraph.getEdge(node.getKey(), Ni.getKey()));
                            sb.append("\n");
                            pw.write(sb.toString());
                            sb.setLength(0);
                        }
                    }
                    if (flag_lone == false) { // if the node dosnt have neighbors it will be written
                        // like this: node1, null, -1 (no edges)
                        sb.append(node.getKey());
                        sb.append(",");
                        sb.append("null");
                        sb.append(",");
                        sb.append(-1);
                        sb.append("\n");
                        pw.write(sb.toString());
                        sb.setLength(0);
                    }
                }
                node.setTag(1); // mark the node then it not will be written more then one time
            }
            System.out.println("File created:  " + file + ".txt"); // making text file
            flag = true; // flag to return if the process succeeded
            pw.close();
            return flag;

        } catch(FileNotFoundException e) {
        System.out.println("An error occurred.");
        System.out.println("the graph is empty");
    }
        return flag;
    }

        /**
     * This method load a graph to this graph algorithm.
     * if the file was successfully loaded - the underlying graph
     * of this class will be changed (to the loaded one), in case the
     * graph was not loaded the original graph should remain "as is".
     * @param file - file name
     * @return true - iff the graph was successfully loaded.
     */

    @Override
    public boolean load(String file) {
        Boolean flag=false;
        weighted_graph_algorithms _g=new WGraph_Algo(); // creat a new graph to load him the wanted graph
        weighted_graph g=new WGraph_DS();
        _g.init(g);
        try {
            FileReader f =new FileReader(file); // file should be written with .txt
            BufferedReader br = new BufferedReader(f);
            String s="";
            while((s=br.readLine())!=null){ // read line after line
                String[] info= s.split(","); // the line looks like: node1, node2, weight with split function
                g.addNode(Integer.parseInt(info[0])); // every part from this array going to the right place
                if(!info[1].contains("null")) g.addNode(Integer.parseInt(info[1]));
                if(!info[2].contains("-1")) g.connect(Integer.parseInt(info[0]),Integer.parseInt(info[1]),Double.parseDouble(info[2]));
            // if node2 is null and the edge is -1 this node is without neighbors and written alone.
            }
            flag=true;
        } catch (IOException e) {
            flag=false;
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        mygraph=g; // let the graph be the loaded one
        return flag;
    }

}