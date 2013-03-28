package domain;

import java.util.ArrayList;
import java.util.HashMap;
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


        //TODO change the test to be correct. nodeArray must be build with all the values contained in the ArrayList and must be the size of the ArrayList
        HashMap<String, Node> nodes = graph.getNodes();
        Node[] nodeArray = new Node[3];
        nodeArray[0] = nodes.get("barbara");
        nodeArray[1] = nodes.get("carol");
        nodeArray[2] = nodes.get("elizabeth");

        Node[] expected = {new Node("barbara"),
            new Node("carol"),
            new Node("elizabeth")};

        assertArrayEquals(expected, nodeArray);

        Graph expResult = new Graph();
        Node barbara = new Node("barbara");
        Node carol = new Node("carol");
        Node elizabeth = new Node("elizabeth");
        Node anna = new Node("anna");
        Link friend1 = new Link("friend", barbara, carol);
        friend1.addAttributes("since=1999");
        barbara.addLink(friend1);
        carol.addLink(friend1);
        Link friend2 = new Link("friend", barbara, elizabeth);
        friend1.addAttributes("since=1999,share=[books|movies|tweets]");
        barbara.addLink(friend2);
        elizabeth.addLink(friend2);
        Link friend3 = new Link("friend", barbara, anna);
        friend3.addAttributes("since=2011");
        barbara.addLink(friend3);
        anna.addLink(friend3);
        expResult.addNode(barbara);
        expResult.addNode(elizabeth);
        expResult.addNode(carol);
        expResult.addNode(anna);
        assertEquals(expResult, graph);

    }
    // TODO review the generated test code and remove the default call to fail.
    //fail("The test case is a prototype.");

    @Test
    public void testDFSFriend() {
        System.out.println("DFS friend");

        String filePath = "testfiles/JUnitTestDFS.txt";
        graph.includeFile(filePath);
        Node[] expected = new Node[4];
        expected[0] = graph.getNode("carol");
        expected[1] = graph.getNode("elizabeth");
        expected[2] = graph.getNode("anna");
        expected[3] = graph.getNode("julie");

        ArrayList<String> criteria = new ArrayList<>();
        criteria.add("friend");
        Node[] result = graph.search(graph.getNode("barbara"), criteria, SearchMethod.DFS).toNodeArray();

        for (Node n : result) {
            System.out.println(n);
        }

        assertArrayEquals(expected, result);
    }

    @Test
    public void testDFSEmployeeOf() {
        System.out.println("DFS employee_of");

        String filePath = "testfiles/JUnitTestDFS.txt";
        graph.includeFile(filePath);
        Node[] expected = new Node[1];
        expected[0] = graph.getNode("barbara");

        ArrayList<String> criteria = new ArrayList<>();
        criteria.add("employee_of");
        Node[] result = graph.search(graph.getNode("anna"), criteria, SearchMethod.DFS).toNodeArray();

        for (Node n : result) {
            System.out.println(n);
        }

        assertArrayEquals(expected, result);
    }

    /**
     * Test of includeFile method, of class Graph.
     */
    @Test
    public void testIncludeMalFormedFile() {
        System.out.println("includeMalFormedFile");
        Graph expResult = new Graph();
        Node barbara = new Node("barbara");
        Node carol = new Node("carol");
        Node elizabeth = new Node("elizabeth");
        Node anna = new Node("anna");
        Node bigCo = new Node("bigco");
        Node dawn = new Node("dawn");
        expResult.addNode(barbara);
        expResult.addNode(carol);
        expResult.addNode(elizabeth);
        expResult.addNode(anna);
        expResult.addNode(bigCo);
        expResult.addNode(dawn);
        Link friend1 = new Link("friend", barbara, carol);
        friend1.addAttributes("since=2000");
        barbara.addLink(friend1);
        carol.addLink(friend1);
        Link friend2 = new Link("friend", barbara, elizabeth);
        friend2.addAttributes("since=1999,share=[books|movies|tweets]");
        barbara.addLink(friend2);
        elizabeth.addLink(friend2);
        Link friend3 = new Link("friend", barbara, anna);
        friend3.addAttributes("since=2011");
        barbara.addLink(friend3);
        anna.addLink(friend3);
        Link employee1 = new Link("employee_of", barbara, bigCo);
        employee1.addAttributes("role=architect,hired=feb04");
        barbara.addLink(employee1);
        bigCo.addLink(employee1);
        Link employee2 = new Link("employee_of", anna, bigCo);
        employee2.addAttributes("role=developer,hired=mars06");
        anna.addLink(employee2);
        bigCo.addLink(employee2);
        Link employee3 = new Link("employee_of", carol, bigCo);
        employee3.addAttributes("role=research,hired=oct08");
        carol.addLink(employee3);
        bigCo.addLink(employee3);
        Link friend4 = new Link("friend", carol, dawn);
        friend4.addAttributes("since=2007,share=[books|tweets|movies]");
        carol.addLink(friend4);
        dawn.addLink(friend4);
        String filePath = "testfiles/MalFormedFileWithAttrJUnit.txt";
        graph.includeFile(filePath);
        assertEquals(expResult, graph);
    }
}
