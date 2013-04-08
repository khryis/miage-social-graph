package search;

import domain.Graph;
import domain.LinkFilter;
import domain.Node;
import factory.GraphBuildingMethod;
import factory.GraphFactory;
import factory.IGraphFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(value = Parameterized.class)
public class GraphParserTest {

    private IGraphFactory factory;
    private String filePath;
    private String[] expResultNodeTab;
    private LinkFilter[] filtersTab;
    private String startNode;
    private SearchMethod searchMethod;
    private int level;
    private IGraphParser.Unicity unicity;

    @Parameters
    public static Collection<Object[]> data() {
        Object[][] data = new Object[][]{
            {
                "testfiles/JUnitTestSearch2.txt",
                new String[]{"1"},
                new LinkFilter[]{new LinkFilter("f", LinkFilter.Direction.IN)},
                "5",
                SearchMethod.DFS,
                1,
                IGraphParser.Unicity.GLOBALNODE
            },
            {
                "testfiles/JUnitTestSearch2.txt",
                new String[]{"5", "1"},
                new LinkFilter[]{new LinkFilter("f", LinkFilter.Direction.OUT), new LinkFilter("f", LinkFilter.Direction.IN)},
                "1",
                SearchMethod.DFS,
                Integer.MAX_VALUE,
                IGraphParser.Unicity.GLOBALNODE
            },
            {
                "testfiles/JUnitTestSearch2.txt",
                new String[]{"5", "1", "2", "3", "4"},
                new LinkFilter[]{
                    new LinkFilter("f", LinkFilter.Direction.OUT),
                    new LinkFilter("f", LinkFilter.Direction.IN),
                    new LinkFilter("l", LinkFilter.Direction.OUT),
                    new LinkFilter("e", LinkFilter.Direction.OUT)
                },
                "1",
                SearchMethod.DFS,
                3,
                IGraphParser.Unicity.GLOBALNODE
            },
            {
                "testfiles/JUnitTestSearch2.txt",
                new String[]{"2", "7", "3", "8", "4", "13", "12", "9"},
                new LinkFilter[]{
                    new LinkFilter("l", LinkFilter.Direction.OUT),
                    new LinkFilter("f", LinkFilter.Direction.BLIND),},
                "1",
                SearchMethod.DFS,
                Integer.MAX_VALUE,
                IGraphParser.Unicity.GLOBALNODE
            },
            {
                "testfiles/JUnitTestSearch2.txt",
                new String[]{"2", "6", "7", "11", "3", "8", "12", "13", "9", "4", "10"},
                new LinkFilter[]{
                    new LinkFilter("l", LinkFilter.Direction.OUT),
                    new LinkFilter("f", LinkFilter.Direction.BLIND),
                    new LinkFilter("e", LinkFilter.Direction.BLIND)},
                "1",
                SearchMethod.DFS,
                Integer.MAX_VALUE,
                IGraphParser.Unicity.GLOBALNODE
            },
            {
                "testfiles/JUnitTestSearch2.txt",
                new String[]{},
                new LinkFilter[]{
                    new LinkFilter("e", LinkFilter.Direction.OUT),
                    new LinkFilter("f", LinkFilter.Direction.BLIND),},
                "11",
                SearchMethod.DFS,
                Integer.MAX_VALUE,
                IGraphParser.Unicity.GLOBALNODE
            },
            {
                "testfiles/JUnitTestSearch.txt",
                new String[]{"carol", "elizabeth", "anna", "julie"},
                new LinkFilter[]{
                    new LinkFilter("friend", LinkFilter.Direction.OUT),
                    new LinkFilter("friend", LinkFilter.Direction.OUT),},
                "barbara",
                SearchMethod.DFS,
                Integer.MAX_VALUE,
                IGraphParser.Unicity.GLOBALNODE
            },
            {
                "testfiles/JUnitTestSearch2.txt",
                new String[]{"1"},
                new LinkFilter[]{
                    new LinkFilter("f", LinkFilter.Direction.IN)},
                "5",
                SearchMethod.DFS,
                1,
                IGraphParser.Unicity.GLOBALRELATION
            },
            {
                "testfiles/JUnitTestSearch2.txt",
                new String[]{"9", "13", "4", "1", "2", "3"},
                new LinkFilter[]{
                    new LinkFilter("f", LinkFilter.Direction.OUT),
                    new LinkFilter("l", LinkFilter.Direction.BLIND),
                    new LinkFilter("f", LinkFilter.Direction.IN)},
                "12",
                SearchMethod.DFS,
                Integer.MAX_VALUE,
                IGraphParser.Unicity.GLOBALRELATION
            },
            {
                "testfiles/JUnitTestSearch2.txt",
                new String[]{"2", "3", "4"},
                new LinkFilter[]{
                    new LinkFilter("l", LinkFilter.Direction.OUT),
                    new LinkFilter("l", LinkFilter.Direction.IN),},
                "1",
                SearchMethod.DFS,
                Integer.MAX_VALUE,
                IGraphParser.Unicity.GLOBALRELATION
            },
            {
                "testfiles/JUnitTestSearch2.txt",
                new String[]{},
                new LinkFilter[]{
                    new LinkFilter("e", LinkFilter.Direction.OUT),
                    new LinkFilter("f", LinkFilter.Direction.BLIND),},
                "11",
                SearchMethod.DFS,
                Integer.MAX_VALUE,
                IGraphParser.Unicity.GLOBALRELATION
            },
            {
                "testfiles/JUnitTestSearch2.txt",
                new String[]{},
                new LinkFilter[]{
                    new LinkFilter("e", LinkFilter.Direction.OUT),
                    new LinkFilter("f", LinkFilter.Direction.BLIND),},
                "11",
                SearchMethod.DFS,
                Integer.MAX_VALUE,
                IGraphParser.Unicity.GLOBALRELATION
            },
            {
                "testfiles/JUnitTestSearch.txt",
                new String[]{"carol", "barbara", "elizabeth", "anna", "julie"},
                new LinkFilter[]{
                    new LinkFilter("friend", LinkFilter.Direction.OUT)
                },
                "barbara",
                SearchMethod.BFS,
                Integer.MAX_VALUE,
                IGraphParser.Unicity.GLOBALNODE
            },
            {
                "testfiles/JUnitTestSearch.txt",
                new String[]{"barbara"},
                new LinkFilter[]{
                    new LinkFilter("employee_of", LinkFilter.Direction.OUT)
                },
                "anna",
                SearchMethod.BFS,
                1,
                IGraphParser.Unicity.GLOBALNODE
            }
        };
        return Arrays.asList(data);
    }

    public GraphParserTest(String filePath, String[] expResultNodeTab, LinkFilter[] filtersTab, String startNode, SearchMethod searchMethod, int level, IGraphParser.Unicity unicity) {
        this.filePath = filePath;
        this.expResultNodeTab = expResultNodeTab;
        this.filtersTab = filtersTab;
        this.startNode = startNode;
        this.searchMethod = searchMethod;
        this.level = level;
        this.unicity = unicity;
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

    @Test
    public void testSearch() throws Exception {

        Graph graph = factory.getGraph(new File(filePath), GraphBuildingMethod.STRICT);

        Set<Node> expResult = new HashSet<>();
        for (int i = 0; i < expResultNodeTab.length; i++) {
            String string = expResultNodeTab[i];
            expResult.add(graph.getNode(string));
        }

        List<LinkFilter> filters = new ArrayList<>();
        for (int i = 0; i < filtersTab.length; i++) {
            LinkFilter filter = filtersTab[i];
            filters.add(filter);
        }
        SearchResult result = graph.parser.search(startNode, filters, searchMethod,
                                                  level, unicity);
        Set<Node> resultNodes = result.getResultNodes();

        System.out.println("search : " + searchMethod.getShortName() + "\n"
                           + "Unicity : " + unicity.toString() + "\n"
                           + "LinkFilters : " + filters.toString() + "\n"
                           + "StartNode : " + startNode + "\n"
                           + "level : " + level + "\n\n");

        assertEquals(expResult, resultNodes);
        assertEquals(resultNodes.size(), expResult.size());
        assertTrue(resultNodes.containsAll(expResult));
    }
}
