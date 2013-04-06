package search;

import domain.Graph;
import domain.LinkFilter;
import domain.Node;
import factory.GraphBuildingMethod;
import factory.GraphFactory;
import factory.IGraphFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class GraphParserTest {

    private IGraphFactory factory;
    private String filePath;

    public GraphParserTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        factory = new GraphFactory();
        filePath = "testfiles/JUnitTestSearch2.txt";
    }

    @After
    public void tearDown() {
    }

    /**
     * Test 1 of search method, of class GraphParser.
     */
    @Test
    public void testSearch_DFS_GlobalNode_F() throws Exception {
        System.out.println("search DFS global node f");

        Graph graph = factory.getGraph(new File(filePath), GraphBuildingMethod.STRICT);

        Set<Node> expResult = new HashSet<>();
        expResult.add(graph.getNode("5"));
        expResult.add(graph.getNode("1"));

        List<LinkFilter> filters = new ArrayList<>();
        LinkFilter f1 = new LinkFilter("f", LinkFilter.Direction.TO);
        filters.add(f1);
        SearchResult result = graph.parser.search("1", filters, SearchMethod.DFS,
                                                  5, GraphParser.Unicity.GLOBALNODE);
        Set<Node> resultNodes = result.getResultNodes();

        assertTrue(expResult.containsAll(resultNodes));
    }

    /**
     * Test 2 of search method, of class GraphParser.
     */
    @Test
    public void testSearch_DFS_GlobalNode_EmployeeOf() throws Exception {
        System.out.println("search DFS global node employee of");

        Graph graph = factory.getGraph(new File(filePath), GraphBuildingMethod.STRICT);

        Node[] expResult = new Node[1];
        expResult[0] = graph.getNode("barbara");

        List<LinkFilter> filters = new ArrayList<>();
        LinkFilter f1 = new LinkFilter("employee_of", LinkFilter.Direction.BLIND);
        filters.add(f1);
        SearchResult result = graph.parser.search("anna", filters, SearchMethod.DFS,
                                                  Integer.MAX_VALUE, GraphParser.Unicity.GLOBALNODE);
        Node[] resultNodes = result.getResultNodesAsArray();

        assertArrayEquals(expResult, resultNodes);
    }

    /**
     * Test 3 of search method, of class GraphParser.
     */
    @Test
    public void testSearch_DFS_GlobalNode_Employee_Of_Without_Attr() throws Exception {
        System.out.println("search DFS global node employee_of without attributes(level 1)");

        Graph graph = factory.getGraph(new File(filePath), GraphBuildingMethod.STRICT);

        Set<Node> expResult = new HashSet<Node>(3);
        expResult.add(graph.getNode("anna"));
        expResult.add(graph.getNode("barbara"));
        expResult.add(graph.getNode("carol"));

        List<LinkFilter> filters = new ArrayList<>();
        LinkFilter f1 = new LinkFilter("employee_of", LinkFilter.Direction.BLIND);
        filters.add(f1);
        SearchResult result = graph.parser.search("BigCo", filters, SearchMethod.DFS,
                                                  1, GraphParser.Unicity.GLOBALNODE);
        Set<Node> resultNodes = result.getResultNodes();

        assertSame(expResult, resultNodes);
    }

    /**
     * Test 4 of search method, of class GraphParser.
     */
    @Test
    public void testSearch_BFS_GlobalNode_Friend() throws Exception {
        System.out.println("search BFS global node friend");

        Graph graph = factory.getGraph(new File(filePath), GraphBuildingMethod.STRICT);

        Set<Node> expResult = new HashSet<>();
        expResult.add(graph.getNode("carol"));
        expResult.add(graph.getNode("elizabeth"));
        expResult.add(graph.getNode("anna"));
        expResult.add(graph.getNode("julie"));

        List<LinkFilter> filters = new ArrayList<>();
        LinkFilter f1 = new LinkFilter("friend", LinkFilter.Direction.BLIND);
        filters.add(f1);
        SearchResult result = graph.parser.search("barbara", filters, SearchMethod.BFS,
                                                  Integer.MAX_VALUE, GraphParser.Unicity.GLOBALNODE);
        Set<Node> resultNodes = result.getResultNodes();

        assertEquals(expResult, resultNodes);
    }

    /**
     * Test 5 of search method, of class GraphParser.
     */
    @Test
    public void testSearch_BFS_GlobalNode_EmployeeOf() throws Exception {
        System.out.println("search BFS global node employee of");

        Graph graph = factory.getGraph(new File(filePath), GraphBuildingMethod.STRICT);

        Node[] expResult = new Node[1];
        expResult[0] = graph.getNode("barbara");

        List<LinkFilter> filters = new ArrayList<>();
        LinkFilter f1 = new LinkFilter("employee_of", LinkFilter.Direction.BLIND);
        filters.add(f1);
        SearchResult result = graph.parser.search("anna", filters, SearchMethod.BFS,
                                                  Integer.MAX_VALUE, GraphParser.Unicity.GLOBALNODE);
        Node[] resultNodes = result.getResultNodesAsArray();

        assertArrayEquals(expResult, resultNodes);
    }
}
