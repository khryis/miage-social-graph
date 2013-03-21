package domain;

import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Chris
 */
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
        HashMap<String, Node> nodes = graph.getNodes();
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
        HashMap<String, Node> nodes = graph.getNodes();
        assertEquals(expected, nodes.get("Carol"));
    }

    /**
     * Test of includeFile method, of class Graph.
     */
    @Test
    public void testIncludeFile() {
        System.out.println("includeFile");

        String filePath = "testfiles/WellFormedFileJUnit.txt";
        graph.includeFile(filePath);

        HashMap<String, Node> nodes = graph.getNodes();
        Node[] nodeArray = new Node[3];
        nodeArray[0] = nodes.get("barbara");
        nodeArray[1] = nodes.get("carol");
        nodeArray[2] = nodes.get("elizabeth");

        Node[] expected = {new Node("barbara"),
                           new Node("carol"),
                           new Node("elizabeth")};

        assertArrayEquals(expected, nodeArray);
    }
    // TODO review the generated test code and remove the default call to fail.
    //fail("The test case is a prototype.");
}
