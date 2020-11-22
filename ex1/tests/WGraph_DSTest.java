package ex1.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ex1.src.WGraph_DS;
import ex1.src.node_info;
import ex1.src.weighted_graph;

import java.util.Arrays;
import java.util.Collection;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class WGraph_DS_MyTest {
    weighted_graph g;
    private static Random _rnd = null;
    /**
     fuctions to creat graph from the cours git
     */
    public static weighted_graph graph_creator(int v_size, int e_size, int seed) {
        weighted_graph g = new WGraph_DS();
        _rnd = new Random(seed);
        for(int i=0;i<v_size;i++) {
            g.addNode(i);
        }
        int[] nodes = nodes(g);
        while(g.edgeSize() < e_size) {
            int a = nextRnd(0,v_size);
            int b = nextRnd(0,v_size);
            int i = nodes[a];
            int j = nodes[b];
            double w = _rnd.nextDouble();
            g.connect(i,j, w);
        }
        return g;
    }
    private static int nextRnd(int min, int max) {
        double v = nextRnd(0.0+min, (double)max);
        int ans = (int)v;
        return ans;
    }
    private static double nextRnd(double min, double max) {
        double d = _rnd.nextDouble();
        double dx = max-min;
        double ans = d*dx+min;
        return ans;
    }
    private static int[] nodes(weighted_graph g) {
        int size = g.nodeSize();
        Collection<node_info> V = g.getV();
        node_info[] nodes = new node_info[size];
        V.toArray(nodes); // O(n) operation
        int[] ans = new int[size];
        for(int i=0;i<size;i++) {ans[i] = nodes[i].getKey();}
        Arrays.sort(ans);
        return ans;
    }

    /** ////////////////////////////////////|\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\ **/

    @BeforeEach
    public void declareGraph() {
        g = new WGraph_DS();
    }

    @Test
    void nodesize(){
        for (int i = 0; i <10 ; i++) {
            g.addNode(i);
        }
        g.addNode(0);
        g.removeNode(1);
        g.removeNode(1);
        g.removeNode(2);
        assertEquals(8,g.nodeSize());
    }

    @Test
    void edgeSize(){
        for (int i = 0; i <10 ; i++) {
            g.addNode(i);
        }
        g.connect(0,1,1);
        g.connect(0,2,1);
        g.connect(0,3,1);
        g.connect(0,4,1);
        g.connect(0,1,2);
        g.removeEdge(0,2);
        assertEquals(3,g.edgeSize());
        assertEquals(2,g.getEdge(0,1));
    }

    /**
     * this function need to return boolean value,
     * true- if there is an esge between nodes.
     * if there is not edge between chosen nodes- false.
     */

    @Test
    void hasEdge(){
        for (int i = 0; i <4 ; i++) {
            g.addNode(i);
        }
        g.connect(0,1,1);
        g.connect(0,2,1);
        assertTrue(g.hasEdge(0,1)&&g.hasEdge(0,2));
        assertFalse(g.hasEdge(0,3)); // no connection- return false
        assertFalse(g.hasEdge(0,4)); // node 4 dont exist in the graph- return false
    }

    /**
     * this function need to return double who is the
     * weight of the edge.
     * if there is not edge between chosen nodes
     * return -1.
     */
    @Test
    void getEdge(){
        for (int i = 0; i <3 ; i++) {
            g.addNode(i);
        }
        g.connect(0,1,1);
        double e1=g.getEdge(0,1); //check the order of nodes
        double e2=g.getEdge(1,0);
        assertEquals(1,e1);
        assertEquals(1,e2);
        assertEquals(-1.0,g.getEdge(0,2)); // no connection return -1
    }

    /**
     * this test checks the remove node function.
     * it remove node who connected to 4 neighbors.
     * it check if those edges are removed to.
     * it remove the same node once more to check
     * the code to not remove node who dose not exist.
     */
    @Test
    void removeNode(){
        for (int i = 0; i <10 ; i++) {
            g.addNode(i);
        }
        g.connect(0,1,1);
        g.connect(0,2,1);
        g.connect(0,3,1);
        g.connect(0,4,1);
        g.removeNode(0); // if this node remove all the edges of the graph gone
        g.removeNode(1); // remove some node who not connected nobody
        assertEquals(null,g.removeNode(0) );
        assertEquals(0,g.edgeSize());
        assertEquals(8,g.nodeSize());
    }

    /** build a graph with 1,000,000 nodes and 10,000,000 edges.
     * in less then 10 sec.
     */

    @Test
    public void creat_huge_graph(){
        System.out.println("build big graph with 1,000,000 nodes and 10,000,000 edges.");
        System.out.println("it might be take some time...");
        boolean flag=false;
        long start = System.currentTimeMillis();
        int V=1000000,E=10000000;
        for(int i=0;i<V;i++) {
            g.addNode(i);
        }
        long end1 = System.currentTimeMillis();
        double sum1=((end1-start)/1000.0);
        if(sum1<1.0) flag=true;
        System.out.println("Time after build "+V+" nodes: " + (end1-start)/1000.0);
        assertTrue(flag);
        flag=false;
        start = System.currentTimeMillis();
        for (int i=0;i<1000000;i++){
            double temp = Math.random()*20;
            int first = (int)(Math.random()*1000000);
            int second = (int)(Math.random()*1000000);
            g.connect(first,second,temp);
        }
        long end2 = System.currentTimeMillis();
        double sum2=((end2-start)/1000.0);
        if(sum2<8.0) flag=true;
        double sum=sum1+sum2;
        if(sum<10.0) flag=true;
        System.out.println("Time after build and connect " +E + " edges: " + (sum));
        assertTrue(flag);
    }
}
