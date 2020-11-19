package ex1.src;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;

public class WGraph_DS implements weighted_graph {

    private static class NodeInfo implements node_info {
        int key;
        String meta_data;
        double Tag;
        private static int keycounter;


        public NodeInfo() {
            key=keycounter=0;
            keycounter++;
            Tag=0;
            meta_data=null;
        }
        public NodeInfo(int key) {
            this.key=key;
            Tag=0;
            meta_data=null;
           }

        @Override
        public int getKey() {
            return key;
        }

        @Override
        public String getInfo() {
            return meta_data;
        }

        @Override
        public void setInfo(String s) {
            meta_data=s;
        }

        @Override
        public double getTag() {
            return Tag;
        }

        @Override
        public void setTag(double t) {
            this.Tag=t;
        }

        @Override
        public String toString() {
            return " key= " + key;
        }
    }
    private HashMap<Integer, node_info> mygraph;
    private HashMap<String, Double> Weights;
    private HashMap<Integer, LinkedHashSet> information;
    private int MC;

    public WGraph_DS(){
        mygraph=new HashMap<Integer, node_info>();
        Weights=new HashMap<String, Double>();
        information=new HashMap<Integer, LinkedHashSet>();
        MC=0;
    }

    /**
     * return the node_data by the node_id,
     * @param key - the node_id
     * @return the node_data by the node_id, null if none.
     */

    @Override
    public node_info getNode(int key) {
        return mygraph.get(key);
    }

    /**
     * return true iff (if and only if) there is an edge between node1 and node2
     * This method run in O(1) time.
     * @param node1
     * @param node2
     * @return
     */

    @Override
    public boolean hasEdge(int node1, int node2) {
        if ((!mygraph.containsKey(node1))||!mygraph.containsKey(node2)) return false;
        if (information.get(node1).contains(getNode(node2))) return true;
        return false;
       }

    /**
     * return the weight if the edge (node1, node1). In case
     * there is no such edge - return -1
     * This method run in O(1) time.
     * @param node1
     * @param node2
     * @return
     */

    @Override
    public double getEdge(int node1, int node2) {
            if(node1>node2){
                int p=node2;
                node2=node1;
                node1=p;
            }
            if(!Weights.containsKey(""+node1+" to "+node2))return -1;
            return Weights.get(""+node1+" to "+node2);
    }

    /**
     * add a new node to the graph with the given key.
     * This method run in O(1) time.
     * If there is already a node with such a key -> no action performed.
     * @param key
     */

    @Override
    public void addNode(int key) {
        if(!mygraph.containsKey(key)){
            if(!information.containsKey(key)) information.put(key, new LinkedHashSet<node_info>());
            node_info p= new NodeInfo(key);
            mygraph.put(key, p);
            MC++;
       }
    }

    /**
     * Connect an edge between node1 and node2, with an edge with weight >=0.
     * This method run in O(1) time.
     * If the edge node1-node2 already exists - the method updates the weight of the edge.
     */

    @Override
    public void connect(int node1, int node2, double w) {
        if ((!mygraph.containsKey(node1)) || !mygraph.containsKey(node2)) return;
        if (node1 == node2) return; // all edges saves as node1<node2
        if(node1>node2){
            int p=node2;
            node2=node1;
            node1=p;
        }
        if(!hasEdge(node1,node2)){
            Weights.put(""+node1+" to "+node2, w); // <"node1 to node2", weight>
            information.get(node1).add(getNode(node2));
            information.get(node2).add(getNode(node1));
            MC++;
        return;
        }
        Weights.replace(""+node1+" to "+node2, w);
        MC++;
        }

    /**
     * This method return a pointer (shallow copy) for a
     * Collection representing all the nodes in the graph.
     * This method should run in O(1) tim
     * @return Collection<node_data>
     */

    @Override
    public Collection<node_info> getV() {
        HashMap<Integer, node_info> TmpHashMap= new HashMap<Integer, node_info>();
        TmpHashMap=mygraph;
        return TmpHashMap.values();
    }

    /**
     * This method returns a Collection containing all the
     * nodes connected to node_id
     * This method run in O(1) time.
     * @return Collection<node_data>
     */

    @Override
    public Collection<node_info> getV(int node_id) {
    return information.get(node_id);
    }

    /**
     * Delete the node (with the given ID) from the graph -
     * and removes all edges which starts or ends at this node.
     * This method run in O(n), |V|=n, as all the edges removed.
     * @return the data of the removed node (null if none).
     * @param key
     */

    @Override
    public node_info removeNode(int key) {
        if(!mygraph.containsKey(key)) return null;
        node_info tmp= new NodeInfo(key);
        for (node_info node: getV(key)){
            information.get(node.getKey()).remove(getNode(key));
            if(key>node.getKey()){
                Weights.remove("" + node.getKey() + " to " + key);
                MC++;
            }
            if(key<node.getKey()){
                Weights.remove("" + key + " to " + node.getKey());
                MC++;
            }

        }
        mygraph.remove(key);
        MC++;
        return tmp;
    }

    /**
     * Delete the edge from the graph,
     * This method run in O(1) time.
     * @param node1
     * @param node2
     */

    @Override
    public void removeEdge(int node1, int node2) {
        if (node1 == node2) return;
        if ((!mygraph.containsKey(node1) || !mygraph.containsKey(node2))) return;
        if (!hasEdge(node1, node2)) return;
        if(node1>node2){
            int p=node2;
            node2=node1;
            node1=p;
        }
            Weights.remove("" + node1 + " to " + node2);
            information.get(node1).remove(getNode(node2));
            information.get(node2).remove(getNode(node1));
            MC++;
        }



    @Override
    public int nodeSize() {
        return mygraph.size();
    }

    @Override
    public int edgeSize() {
        return Weights.size();
    }

    @Override
    public int getMC() {
        return MC;
    }

   public boolean equals(Object g){
       if(!(g instanceof WGraph_DS)) return false; // g need to be WGraph_DS object
       WGraph_DS g1 = (WGraph_DS)g;
       boolean flag=true;
       if(g1.nodeSize()!= mygraph.size()) return false; // first of all check sizes
       if(g1.edgeSize()!= Weights.size()) return false;
       for (int i = 0; i <mygraph.size() ; i++) { //check if all the nodes are equal
           if (g1.getNode(i).getKey()!=getNode(i).getKey()) flag=false;
       }
       //check connections and weights
       for (node_info p:g1.getV()) {
           for (node_info q:getV(p.getKey())) {
               if(!g1.hasEdge(p.getKey(),q.getKey())||!hasEdge(p.getKey(),q.getKey())) flag=false;
           }
       }
       return flag;
   }

    @Override
    public String toString() {
        return
                "mygraph= " + getV() ;
    }
}

