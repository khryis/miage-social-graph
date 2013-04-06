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
    public void testSearch_DFS_GlobalNode_1() throws Exception {
        System.out.println("search DFS global node filter f(from) start node = 5 level 1");

        Graph graph = factory.getGraph(new File(filePath), GraphBuildingMethod.STRICT);

        Set<Node> expResult = new HashSet<>();
        expResult.add(graph.getNode("5"));
        expResult.add(graph.getNode("1"));
        expResult.add(graph.getNode("2"));
        expResult.add(graph.getNode("3"));
        expResult.add(graph.getNode("4"));

        List<LinkFilter> filters = new ArrayList<>();
        LinkFilter f1 = new LinkFilter("f", LinkFilter.Direction.FROM);
        filters.add(f1);
        SearchResult result = graph.parser.search("5", filters, SearchMethod.DFS,
                                                  1, GraphParser.Unicity.GLOBALNODE);
        Set<Node> resultNodes = result.getResultNodes();

        assertEquals(expResult, resultNodes);
    }

    /**
     * Test 2 of search method, of class GraphParser.
     */
    @Test
    public void testSearch_DFS_GlobalNode_2() throws Exception {
        System.out.println("search DFS global node filter f(to) then l(from) start node = 1 level = Max");

        Graph graph = factory.getGraph(new File(filePath), GraphBuildingMethod.STRICT);

        Set<Node> expResult = new HashSet<>();
        expResult.add(graph.getNode("5"));
        expResult.add(graph.getNode("1"));

        List<LinkFilter> filters = new ArrayList<>();
        LinkFilter f1 = new LinkFilter("f", LinkFilter.Direction.TO);
        LinkFilter f2 = new LinkFilter("f", LinkFilter.Direction.FROM);
        filters.add(f1);
        filters.add(f2);
        SearchResult result = graph.parser.search("1", filters, SearchMethod.DFS,
                                                  Integer.MAX_VALUE, GraphParser.Unicity.GLOBALNODE);
        Set<Node> resultNodes = result.getResultNodes();

        assertEquals(expResult, resultNodes);
        assertTrue(resultNodes.containsAll(expResult));
    }

    @Test
    public void testSearch_DFS_GlobalNode_3() throws Exception {
        System.out.println("search DFS global node filter f(to) then f(from),l(to),e(to) start node = 1 level = 3");

        Graph graph = factory.getGraph(new File(filePath), GraphBuildingMethod.STRICT);

        Set<Node> expResult = new HashSet<>();
        expResult.add(graph.getNode("5"));
        expResult.add(graph.getNode("1"));
        expResult.add(graph.getNode("2"));
        expResult.add(graph.getNode("3"));
        expResult.add(graph.getNode("4"));
        expResult.add(graph.getNode("6"));
        expResult.add(graph.getNode("9"));

        List<LinkFilter> filters = new ArrayList<>();
        LinkFilter f1 = new LinkFilter("f", LinkFilter.Direction.TO);
        LinkFilter f2 = new LinkFilter("f", LinkFilter.Direction.FROM);
        LinkFilter f3 = new LinkFilter("l", LinkFilter.Direction.TO);
        LinkFilter f4 = new LinkFilter("e", LinkFilter.Direction.TO);
        filters.add(f1);
        filters.add(f2);
        filters.add(f3);
        filters.add(f4);
        SearchResult result = graph.parser.search("1", filters, SearchMethod.DFS,
                                                  3, GraphParser.Unicity.GLOBALNODE);
        Set<Node> resultNodes = result.getResultNodes();

        assertEquals(expResult, resultNodes);
    }

    @Test
    public void testSearch_DFS_GlobalNode_4() throws Exception {
        System.out.println("search DFS global node filter l(to) then f(blind) start node = 1 level = Max");

        Graph graph = factory.getGraph(new File(filePath), GraphBuildingMethod.STRICT);

        Set<Node> expResult = new HashSet<>();
        expResult.add(graph.getNode("2"));
        expResult.add(graph.getNode("7"));
        expResult.add(graph.getNode("3"));
        expResult.add(graph.getNode("8"));
        expResult.add(graph.getNode("4"));
        expResult.add(graph.getNode("13"));
        expResult.add(graph.getNode("12"));
        expResult.add(graph.getNode("9"));

        List<LinkFilter> filters = new ArrayList<>();
        LinkFilter f1 = new LinkFilter("l", LinkFilter.Direction.TO);
        LinkFilter f2 = new LinkFilter("f", LinkFilter.Direction.BLIND);
        filters.add(f1);
        filters.add(f2);
        SearchResult result = graph.parser.search("1", filters, SearchMethod.DFS,
                                                  Integer.MAX_VALUE, GraphParser.Unicity.GLOBALNODE);
        Set<Node> resultNodes = result.getResultNodes();

        assertEquals(expResult, resultNodes);
        assertTrue(resultNodes.containsAll(expResult));
    }

    @Test
    public void testSearch_DFS_GlobalNode_5() throws Exception {
        System.out.println("search DFS global node filter l(to) then f(blind),e(blind) start node = 1 level = Max");

        Graph graph = factory.getGraph(new File(filePath), GraphBuildingMethod.STRICT);

        Set<Node> expResult = new HashSet<>();
        expResult.add(graph.getNode("2"));
        expResult.add(graph.getNode("6"));
        expResult.add(graph.getNode("7"));
        expResult.add(graph.getNode("11"));
        expResult.add(graph.getNode("3"));
        expResult.add(graph.getNode("8"));
        expResult.add(graph.getNode("12"));
        expResult.add(graph.getNode("13"));
        expResult.add(graph.getNode("9"));
        expResult.add(graph.getNode("4"));
        expResult.add(graph.getNode("10"));

        List<LinkFilter> filters = new ArrayList<>();
        LinkFilter f1 = new LinkFilter("l", LinkFilter.Direction.TO);
        LinkFilter f2 = new LinkFilter("f", LinkFilter.Direction.BLIND);
        LinkFilter f3 = new LinkFilter("e", LinkFilter.Direction.BLIND);
        filters.add(f1);
        filters.add(f2);
        filters.add(f3);
        SearchResult result = graph.parser.search("1", filters, SearchMethod.DFS,
                                                  Integer.MAX_VALUE, GraphParser.Unicity.GLOBALNODE);
        Set<Node> resultNodes = result.getResultNodes();

        assertEquals(expResult, resultNodes);
        assertTrue(resultNodes.containsAll(expResult));
    }

    /**
     * Test 3 of search method, of class GraphParser.
     */
    //@Test
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
    //@Test
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
    //@Test
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
