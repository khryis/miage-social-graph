package domain;

import java.util.Map;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class GraphTest {

    private Graph graph;
    private Graph testGraph1;
    private Graph testGraph2;
    Node testNode1;
    Node testNode2;

    public GraphTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        graph = new Graph();
        testGraph1 = new Graph();
        testGraph2 = new Graph();
        testNode1 = new Node("Sylvie");
        testNode2 = new Node("Debbie");
    }

    @After
    public void tearDown() {
    }

    /**
     * Test 1 of addNode method, of class Graph.
     */
    @Test
    public void testAddNodeInKeySet() {
        System.out.println("addNode : in keyset");
        String expected = "Carol";
        graph.addNode(new Node("Carol"));
        Map<String, Node> nodes = graph.getNodes();
        assertTrue(nodes.containsKey(expected));
    }

    /**
     * Test 2 of addNode method, of class Graph.
     */
    @Test
    public void testAddNodeExistNode() {
        System.out.println("addNode : If node exist");
        Node expected = new Node("Carol");
        graph.addNode(new Node("Carol"));
        Map<String, Node> nodes = graph.getNodes();
        assertEquals(expected, nodes.get("Carol"));
    }

    /**
     * Test 1 of hashCode method, of class Graph
     */
    @Test
    public void testHashCodeIntegrity() {
        System.out.println("hashCode : same object, same integer");
        Graph testGraph = new Graph();
        int expected = testGraph.hashCode();
        assertEquals(expected, testGraph.hashCode());
    }

    /**
     * Test 2 of hashCode method, of class Graph
     */
    @Test
    public void testHashCodeOnEquals() {
        System.out.println("hashCode : if object equals true");
        testGraph1.addNode(testNode1);
        testGraph2.addNode(testNode1);
        int expected = testGraph1.hashCode();
        assertEquals(expected, testGraph2.hashCode());
    }

    /**
     * Test 1 of equals method, of class Graph
     */
    @Test
    public void testEqualsOnEquals() {
        System.out.println("equals : if objects are equals");
        testGraph1.addNode(testNode1);
        testGraph2.addNode(testNode1);
        assertTrue(testGraph1.equals(testGraph2));
    }

    /**
     * Test 2 of equals method, of class Graph
     */
    @Test
    public void testEqualsOnDifferent() {
        System.out.println("equals : if objects are different");
        testGraph1.addNode(testNode1);
        testGraph2.addNode(testNode2);
        assertFalse(testGraph1.equals(testGraph2));
    }

    /**
     * Test 3 of equals method, of class Graph
     */
    @Test
    public void testEqualsOnNull() {
        System.out.println("equals : if target object is null");
        assertFalse(testGraph1.equals(null));
    }
}
