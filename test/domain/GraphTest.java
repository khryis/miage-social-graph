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
}
