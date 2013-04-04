package search;

import domain.Graph;
import domain.LinkFilter;
import domain.Node;
import factory.GraphBuildingMethod;
import factory.GraphFactory;
import factory.IGraphFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
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

        Node[] expResult = new Node[4];
        expResult[0] = graph.getNode("carol");
        expResult[1] = graph.getNode("elizabeth");
        expResult[2] = graph.getNode("anna");
        expResult[3] = graph.getNode("julie");

        GraphParser parser = new GraphParser(graph);
        List<LinkFilter> filters = new ArrayList<>();
        LinkFilter f1 = new LinkFilter("friend", LinkFilter.Direction.FROM);
        filters.add(f1);
        SearchResult result = parser.search("barbara", filters, SearchMethod.DFS, Integer.MAX_VALUE, GraphParser.Unicity.GLOBALNODE);
        Node[] resultNodes = result.getResultNodesAsArray();

        System.out.println("*************************");
        for (int i = 0; i < expResult.length; i++) {
            System.out.println(expResult[i]);
        }
        System.out.println("--------------------------");
        for (int i = 0; i < resultNodes.length; i++) {
            System.out.println(resultNodes[i]);
        }
        System.out.println("***************************");

        assertArrayEquals(expResult, resultNodes);
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

        GraphParser parser = new GraphParser(graph);
        List<LinkFilter> filters = new ArrayList<>();
        LinkFilter f1 = new LinkFilter("employee_of", LinkFilter.Direction.FROM);
        filters.add(f1);
        SearchResult result = parser.search("anna", filters, SearchMethod.DFS, Integer.MAX_VALUE, GraphParser.Unicity.GLOBALNODE);
        Node[] resultNodes = result.getResultNodesAsArray();

        System.out.println("*************************");
        for (int i = 0; i < expResult.length; i++) {
            System.out.println(expResult[i]);
        }
        System.out.println("--------------------------");
        for (int i = 0; i < resultNodes.length; i++) {
            System.out.println(resultNodes[i]);
        }
        System.out.println("***************************");

        assertArrayEquals(expResult, resultNodes);
    }
}
