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
    }

    @After
    public void tearDown() {
    }

    /**
     * Test 1 of search method, of class GraphParser.
     */
    @Test
    public void testSearch_DFS_Friend() throws Exception {
        System.out.println("search DFS friend");

        String filePath = "testfiles/JUnitTestDFS.txt";
        Graph graph = factory.getGraph(new File(filePath), GraphBuildingMethod.STRICT);
        
        Set<Node> expResult = new HashSet<>();
        expResult.add(graph.getNode("carol"));
        expResult.add(graph.getNode("elizabeth"));
        expResult.add(graph.getNode("anna"));
        expResult.add(graph.getNode("julie"));

        List<LinkFilter> filters = new ArrayList<>();
        LinkFilter f1 = new LinkFilter("friend", LinkFilter.Direction.BLIND);
        filters.add(f1);
        SearchResult result = graph.parser.search("barbara", filters, SearchMethod.DFS, Integer.MAX_VALUE, GraphParser.Unicity.GLOBALNODE);
        Set<Node> resultNodes = result.getResultNodes();

        assertEquals(expResult, resultNodes);
    }

    /**
     * Test 2 of search method, of class GraphParser.
     */
    @Test
    public void testSearch_DFS_EmployeeOf() throws Exception {
        System.out.println("search DFS employee of");

        String filePath = "testfiles/JUnitTestDFS.txt";
        Graph graph = factory.getGraph(new File(filePath), GraphBuildingMethod.STRICT);

        Node[] expResult = new Node[1];
        expResult[0] = graph.getNode("barbara");

        List<LinkFilter> filters = new ArrayList<>();
        LinkFilter f1 = new LinkFilter("employee_of", LinkFilter.Direction.BLIND);
        filters.add(f1);
        SearchResult result = graph.parser.search("anna", filters, SearchMethod.DFS, Integer.MAX_VALUE, GraphParser.Unicity.GLOBALNODE);
        Node[] resultNodes = result.getResultNodesAsArray();

        assertArrayEquals(expResult, resultNodes);
    }
}
