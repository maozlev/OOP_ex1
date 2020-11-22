package ex1.tests;

import ex1.src.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class WGraph_Algo_MyTest {
    weighted_graph g;
    weighted_graph_algorithms _g;
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

    public static weighted_graph creat_rnd_connected_graph(int V, int E){ // creat connected graph
        if(V>E){
            System.out.println("it cant be a connected graph because number of vertex is large than number of edges!");
            weighted_graph g=new WGraph_DS();
            for (int i = 0; i <V ; i++) {
                g.addNode(i);
            }
            for (int i = 0; i <E ; i++) {
                int from=(int)(Math.random()*V-1);
                int to=(int)(Math.random()*V-1);
                double weight=Math.random()*20;
                g.connect(from,to,weight);
            }
            return g;
        }
        weighted_graph g=new WGraph_DS();
        for (int i = 0; i <V ; i++) {
            g.addNode(i);
        }
        for (int i=0,j=0,k=1,count=1; i <=E ; i++,j++,k++) {
            double weight=Math.random()*20;
            if(j>=V-1) { j=0;k=V-1-count;count++;}
            g.connect(j,k,weight);
        }
        return g;
    }

    @BeforeEach
    public void declareGraph() {
        g = new WGraph_DS();
        _g= new WGraph_Algo();
    }

    /**
     * check if all nodes in the graph connected.
     */
    @Test
    void isConnected(){
        assertTrue(_g.isConnected()); // empty graph should back true
        g= creat_rnd_connected_graph(5,6);
        _g.init(g);
        assertTrue(_g.isConnected()); //this graph connected
        g=graph_creator(5,2,4);
        _g.init(g);
        assertFalse(_g.isConnected()); // this graph isnt connected.
        }

    /**
     * this test ex1.tests the two function of calculating shortest distance between nodes and
     * return order list of those nodes.
     */
        @Test
        void ShortestPath_and_Dist() {
            _g.init(g);
            for (int i = 0; i < 8; i++) {
                g.addNode(i);
            }
            g.connect(0, 1, 0.15);
            g.connect(0, 2, 1.5);
            g.connect(0, 6, 2);
            g.connect(0, 7, 3);
            g.connect(1, 2, 1.5);
            g.connect(1, 7, 4);
            g.connect(2, 6, 0.1);
            g.connect(2, 5, 1.5);
            g.connect(2, 4, 0.1);
            g.connect(2, 3, 6);
            g.connect(3, 4, 0.6);
            g.connect(4, 5, 0.5);
            g.connect(5, 6, 0.5);
            g.connect(5, 7, 10);
            g.connect(6, 7, 1.1);
            boolean flag = false;
            if (_g.shortestPathDist(0, 5) == 2.1) flag = true; // the distance between 0 to 5 should be 2.1
            assertTrue(flag);
            System.out.println("shortest distance between ex1.src = 0 to dest = 5 is: 2.1");
            int[] key = {0, 2, 6, 5}; // the path should be 0,2,6,5
            int i = 0;
            for (node_info p : _g.shortestPath(0, 5)) {
                assertEquals(p.getKey(), key[i]); // check the order of the right path
                i++;
            }
            System.out.println("shortest distance node collection is: 0,2,6,5");
            g.removeEdge(3,4);
            g.removeEdge(3,2);
            _g.init(g);
            assertEquals(-1.0,_g.shortestPathDist(7,3) ); // should give -1. no path between them
            assertEquals(null, _g.shortestPath(0,3)); // should give null. no path between them to
            System.out.println("there is no path between ex1.src = 7 to dest = 3");
            System.out.println("there is no path between ex1.src = 0 to dest = 3");
        }

    /** this test ex1.tests 3 functions:
     * copy, save, load
     */
        @Test
            public void copy_save_load() {
            g = graph_creator(7, 9, 9);
            _g.init(g);
            weighted_graph_algorithms _g1 = new WGraph_Algo(); //check the copy function by creat new graph and let him to be copy
            weighted_graph g1 = new WGraph_DS();
            g1 = _g.copy();
            assertEquals(g, g1); //check if the copy equals to original
            _g1 = new WGraph_Algo();
            g1 = new WGraph_DS();
            _g1.init(g1);
            String file = System.getProperty("user.home") + "/Desktop/Mygraph.txt";
            assertTrue(_g.save(file)); //check if the graph saved
            assertTrue(_g1.load(file));//check if the graph can be loaded
            g1 = _g1.copy();
            assertEquals(g, g1); //check if the loded graph equals to graph who saved
            g = new WGraph_DS(); // null the graph
            _g.init(g);
            assertFalse(_g.save(file)); //save null graph should return false
        }
    /** this test ex1.tests times functions on big graph with 100,000 nodes and 1,000,000 edges:
     * isConnected, shortestPathDist, shortestPath, copy, save, load
     * in less then 18 second. (3 seconds for each function).
     * (usually the functions runs less then 12 seconds)
     */
        @Test
        public void time(){
            System.out.println("this test build graph with 100,000 nodes and 1,000,000 edges and checks 6 functions,\n" +
                    "it might take up then 10 sec..");
            System.out.print("..");System.out.print("..");System.out.print("..");
            System.out.println("...");
            boolean flag=true;
            int V=100000, E=1000000;
            Random rnd = new Random(1);
            for (int i = 0; i < V; i++) {
                g.addNode(i);
            }
            int n1=0,n2=0;
            for (int i=0;i<E;i++){
                double w = Math.random()*30;
                n1 = rnd.nextInt((V-1));
                n2 = rnd.nextInt((V-1));
                g.connect(n1,n2, w);
            }
            _g.init(g);
            long start1 = System.currentTimeMillis();
            boolean flag1=_g.isConnected();
            long end1 = System.currentTimeMillis();
            System.out.println("time to isConnected function: "+(end1-start1)/1000.0+" sec. and result is: "+flag1);
            n1 = 1+rnd.nextInt((V-1));
            long start2 = System.currentTimeMillis();
            double e= _g.shortestPathDist(n1,n1-1);
            long end2 = System.currentTimeMillis();
            System.out.println("time to shortestPathDist: "+(end2-start2)/1000.0+" sec. and result is: "+e);
            LinkedList<node_info> lst=new LinkedList<>();
            long start3 = System.currentTimeMillis();
            lst= (LinkedList<node_info>) _g.shortestPath(n1,n1-1);
            long end3 = System.currentTimeMillis();
            System.out.println("time to shortestPath: "+(end3-start3)/1000.0+" sec. and result is: "+lst.toString());
            long start4 = System.currentTimeMillis();
            _g.copy();
            long end4 = System.currentTimeMillis();
            System.out.println("time to copy function: "+(end4-start4)/1000.0+" sec");
            long start5 = System.currentTimeMillis();
            String file = System.getProperty("user.home") + "/Desktop/Mygraph";
            _g.save(file);
            long end5 = System.currentTimeMillis();
            System.out.println("time to save function: "+(end5-start5)/1000.0+" sec");
            long start6 = System.currentTimeMillis();
            _g.load(file);
            long end6 = System.currentTimeMillis();
            System.out.println("time to load function: "+(end6-start6)/1000.0+" sec");
            double sum=(end1-start1)/1000.0+(end2-start2)/1000.0+(end3-start3)/1000.0+(end4-start4)/1000.0+
                    (end5-start5)/1000.0+(end6-start6)/1000.0;
            if(sum>18) flag=false;
            assertTrue(flag);
    }

}















