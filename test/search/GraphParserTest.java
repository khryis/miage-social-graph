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
                "testfiles/JUnitTestSearch2.txt",//filepath
                new String[]{"1"},//expResult
                new LinkFilter[]{//filters
                    new LinkFilter("f", LinkFilter.Direction.IN)
                },
                "5",//startNode
                SearchMethod.DFS,//searchMethod
                1,//level
                IGraphParser.Unicity.GLOBALNODE//unicity
            },
            {
                "testfiles/JUnitTestSearch2.txt",//filepath
                new String[]{"5", "1"},//expResult
                new LinkFilter[]{//filters
                    new LinkFilter("f", LinkFilter.Direction.OUT),
                    new LinkFilter("f", LinkFilter.Direction.IN)
                },
                "1",//startNode
                SearchMethod.DFS,//searchMethod
                Integer.MAX_VALUE,//level
                IGraphParser.Unicity.GLOBALNODE//unicity
            },
            {
                "testfiles/JUnitTestSearch2.txt",//filepath
                new String[]{"5", "1", "2", "3", "4"},//expResult
                new LinkFilter[]{//filters
                    new LinkFilter("f", LinkFilter.Direction.OUT),
                    new LinkFilter("f", LinkFilter.Direction.IN),
                    new LinkFilter("l", LinkFilter.Direction.OUT),
                    new LinkFilter("e", LinkFilter.Direction.OUT)
                },
                "1",//startNode
                SearchMethod.DFS,//searchMethod
                Integer.MAX_VALUE,//level
                IGraphParser.Unicity.GLOBALNODE//unicity
            },
            {
                "testfiles/JUnitTestSearch2.txt",//filepath
                new String[]{"2", "7", "3", "8", "4", "13", "12", "9"},//expResult
                new LinkFilter[]{//filters
                    new LinkFilter("l", LinkFilter.Direction.OUT),
                    new LinkFilter("f", LinkFilter.Direction.BLIND),},
                "1",//startNode
                SearchMethod.DFS,//searchMethod
                Integer.MAX_VALUE,//level
                IGraphParser.Unicity.GLOBALNODE//unicity
            },
            {
                "testfiles/JUnitTestSearch2.txt",//filepath
                new String[]{"2", "6", "7", "11", "3", "8", "12", "13", "9", "4", "10"},//expResult
                new LinkFilter[]{//filters
                    new LinkFilter("l", LinkFilter.Direction.OUT),
                    new LinkFilter("f", LinkFilter.Direction.BLIND),
                    new LinkFilter("e", LinkFilter.Direction.BLIND)},
                "1",//startNode
                SearchMethod.DFS,//searchMethod
                Integer.MAX_VALUE,//level
                IGraphParser.Unicity.GLOBALNODE//unicity
            },
            {
                "testfiles/JUnitTestSearch2.txt",//filepath
                new String[]{},//expResult
                new LinkFilter[]{//filters
                    new LinkFilter("e", LinkFilter.Direction.OUT),
                    new LinkFilter("f", LinkFilter.Direction.BLIND),},
                "11",//startNode
                SearchMethod.DFS,//searchMethod
                Integer.MAX_VALUE,//level
                IGraphParser.Unicity.GLOBALNODE//unicity
            },
            {
                "testfiles/JUnitTestSearch.txt",//filepath
                new String[]{"carol", "elizabeth", "anna", "julie"},//expResult
                new LinkFilter[]{//filters
                    new LinkFilter("friend", LinkFilter.Direction.OUT),
                    new LinkFilter("friend", LinkFilter.Direction.OUT),},
                "barbara",//startNode
                SearchMethod.DFS,//searchMethod
                Integer.MAX_VALUE,//level
                IGraphParser.Unicity.GLOBALNODE//unicity
            },
            {
                "testfiles/JUnitTestSearch2.txt",//filepath
                new String[]{"1"},//expResult
                new LinkFilter[]{//filters
                    new LinkFilter("f", LinkFilter.Direction.IN)},
                "5",//startNode
                SearchMethod.DFS,//searchMethod
                1,//level
                IGraphParser.Unicity.GLOBALRELATION//unicity
            },
            {
                "testfiles/JUnitTestSearch2.txt",//filepath
                new String[]{"9", "13", "4", "1", "2", "3"},//expResult
                new LinkFilter[]{//filters
                    new LinkFilter("f", LinkFilter.Direction.OUT),
                    new LinkFilter("l", LinkFilter.Direction.BLIND),
                    new LinkFilter("f", LinkFilter.Direction.IN)},
                "12",//startNode
                SearchMethod.DFS,//searchMethod
                Integer.MAX_VALUE,//level
                IGraphParser.Unicity.GLOBALRELATION//unicity
            },
            {
                "testfiles/JUnitTestSearch2.txt",//filepath
                new String[]{"2", "3", "4"},//expResult
                new LinkFilter[]{//filters
                    new LinkFilter("l", LinkFilter.Direction.OUT),
                    new LinkFilter("l", LinkFilter.Direction.IN),},
                "1",//startNode
                SearchMethod.DFS,//searchMethod
                Integer.MAX_VALUE,//level
                IGraphParser.Unicity.GLOBALRELATION//unicity
            },
            {
                "testfiles/JUnitTestSearch2.txt",//filepath
                new String[]{},//expResult
                new LinkFilter[]{//filters
                    new LinkFilter("e", LinkFilter.Direction.OUT),
                    new LinkFilter("f", LinkFilter.Direction.BLIND),},
                "11",//startNode
                SearchMethod.DFS,//searchMethod
                Integer.MAX_VALUE,//level
                IGraphParser.Unicity.GLOBALRELATION//unicity
            },
            {
                "testfiles/JUnitTestSearch2.txt",//filepath
                new String[]{},//expResult
                new LinkFilter[]{//filters
                    new LinkFilter("e", LinkFilter.Direction.OUT),
                    new LinkFilter("f", LinkFilter.Direction.BLIND),},
                "11",//startNode
                SearchMethod.DFS,//searchMethod
                Integer.MAX_VALUE,//level
                IGraphParser.Unicity.GLOBALRELATION//unicity
            },
            {
                "testfiles/JUnitTestSearch.txt",//filepath
                new String[]{"carol", "barbara", "elizabeth", "anna", "julie"},//expResult
                new LinkFilter[]{//filters
                    new LinkFilter("friend", LinkFilter.Direction.OUT)
                },
                "barbara",//startNode
                SearchMethod.BFS,//searchMethode
                Integer.MAX_VALUE,//level
                IGraphParser.Unicity.GLOBALNODE//unicity
            },
            {
                "testfiles/JUnitTestSearch.txt",//filepath
                new String[]{"barbara", "anna"},//expResult
                new LinkFilter[]{//filters
                    new LinkFilter("employee_of", LinkFilter.Direction.OUT),
                    new LinkFilter("employee_of", LinkFilter.Direction.IN)
                },
                "anna",//startNode
                SearchMethod.BFS,//searchMethode
                Integer.MAX_VALUE,//level
                IGraphParser.Unicity.GLOBALNODE//unicity
            },
            {
                "testfiles/JUnitTestSearch.txt",//filepath
                new String[]{"barbara"},//expResult
                new LinkFilter[]{//filters
                    new LinkFilter("employee_of", LinkFilter.Direction.OUT),
                    new LinkFilter("employee_of", LinkFilter.Direction.OUT)
                },
                "anna",//startNode
                SearchMethod.BFS,//searchMethode
                Integer.MAX_VALUE,//level
                IGraphParser.Unicity.GLOBALNODE//unicity
            },
            {
                "testfiles/JUnitTestSearch.txt",//filepath
                new String[]{},//expResult
                new LinkFilter[]{//filters
                    new LinkFilter("employee_of", LinkFilter.Direction.IN)
                },
                "anna",//startNode
                SearchMethod.BFS,//searchMethode
                Integer.MAX_VALUE,//level
                IGraphParser.Unicity.GLOBALNODE//unicity
            },
            {
                "testfiles/JUnitTestSearch.txt",//filepath
                new String[]{"barbara", "carol", "elizabeth", "anna", "julie"},//expResult
                new LinkFilter[]{//filters
                    new LinkFilter("employee_of", LinkFilter.Direction.OUT)
                },
                "anna",//startNode
                SearchMethod.BFS,//searchMethode
                Integer.MAX_VALUE,//level
                IGraphParser.Unicity.GLOBALNODE//unicity
            },
            {
                "testfiles/JUnitTestSearch2.txt",//filepath
                new String[]{"2", "3", "4", "7", "8", "13"},//expResult
                new LinkFilter[]{//filters
                    new LinkFilter("l", LinkFilter.Direction.OUT),
                    new LinkFilter("f", LinkFilter.Direction.OUT)
                },
                "1",//startNode
                SearchMethod.BFS,//searchMethode
                Integer.MAX_VALUE,//level
                IGraphParser.Unicity.GLOBALNODE//unicity
            },
            {
                "testfiles/JUnitTestSearch2.txt",//filepath
                new String[]{},//expResult
                new LinkFilter[]{//filters
                    new LinkFilter("f", LinkFilter.Direction.IN),
                    new LinkFilter("f", LinkFilter.Direction.OUT)
                },
                "1",//startNode
                SearchMethod.BFS,//searchMethode
                Integer.MAX_VALUE,//level
                IGraphParser.Unicity.GLOBALRELATION//unicity
            },
            {
                "testfiles/JUnitTestSearch2.txt",//filepath
                new String[]{"1"},//expResult
                new LinkFilter[]{//filters
                    new LinkFilter("f", LinkFilter.Direction.IN),
                    new LinkFilter("f", LinkFilter.Direction.OUT)
                },
                "5",//startNode
                SearchMethod.BFS,//searchMethode
                Integer.MAX_VALUE,//level
                IGraphParser.Unicity.GLOBALRELATION//unicity
            },
            {
                "testfiles/JUnitTestSearch2.txt",//filepath
                new String[]{"1"},//expResult
                new LinkFilter[]{//filters
                    new LinkFilter("f", LinkFilter.Direction.IN),
                    new LinkFilter("f", LinkFilter.Direction.OUT)
                },
                "5",//startNode
                SearchMethod.BFS,//searchMethode
                Integer.MAX_VALUE,//level
                IGraphParser.Unicity.GLOBALRELATION//unicity
            },
            {
                "testfiles/JUnitTestSearch2.txt",//filepath
                new String[]{"2", "3", "4"},//expResult
                new LinkFilter[]{//filters
                    new LinkFilter("l", LinkFilter.Direction.OUT),
                    new LinkFilter("l", LinkFilter.Direction.BLIND)
                },
                "1",//startNode
                SearchMethod.BFS,//searchMethode//searchMethode
                Integer.MAX_VALUE,//level
                IGraphParser.Unicity.GLOBALRELATION//unicity
            },
            {
                "testfiles/JUnitTestSearch2.txt",//filepath
                new String[]{"2", "3", "4", "6", "7", "8", "9", "13", "11", "12", "10"},//expResult
                new LinkFilter[]{//filters
                    new LinkFilter("l", LinkFilter.Direction.OUT),
                    new LinkFilter("f", LinkFilter.Direction.BLIND),
                    new LinkFilter("e", LinkFilter.Direction.BLIND)
                },
                "1",//startNode
                SearchMethod.BFS,//searchMethode//searchMethode
                Integer.MAX_VALUE,
                IGraphParser.Unicity.GLOBALRELATION//unicity
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

        System.out.println("search : " + searchMethod.getShortName() + "\n"
                           + "Unicity : " + unicity.toString() + "\n"
                           + "LinkFilters : " + filters.toString() + "\n"
                           + "StartNode : " + startNode + "\n"
                           + "level : " + (level == Integer.MAX_VALUE ? "MAX" : level) + "\n\n");

        SearchResult result = graph.parser.search(startNode, filters, searchMethod,
                                                  level, unicity);
        Set<Node> resultNodes = result.getResultNodes();

        assertEquals(expResult, resultNodes);
        assertEquals(resultNodes.size(), expResult.size());
        assertTrue(resultNodes.containsAll(expResult));
    }
}
