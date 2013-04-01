package factory;

import domain.Graph;
import domain.Link;
import domain.Node;
import factory.builder.GraphBuildingMethod;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class GraphFactoryTest {

    private IGraphFactory factory;

    public GraphFactoryTest() {
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
     * Test of getGraph method, of class GraphFactory, with strict method
     * building
     */
    @Test
    public void testGetGraph_File_STRICT() throws Exception {
        System.out.println("getGraph with a well formed file in parameter and "
                + "with the strict building method");

        String filePath = "testfiles/WellFormedFileJUnit.txt";
        Graph graph = factory.getGraph(new File(filePath), GraphBuildingMethod.STRICT);

        Graph expResult = new Graph();
        Node barbara = new Node("barbara");
        Node carol = new Node("carol");
        Node elizabeth = new Node("elizabeth");
        Node anna = new Node("anna");

        Link friend1 = new Link("friend", barbara, carol);
        friend1.addAttribute("since", "1999");
        barbara.addLink(friend1);
        carol.addLink(friend1);
        Link friend2 = new Link("friend", barbara, elizabeth);
        friend2.addAttribute("since", "1999");
        List<String> values2 = new ArrayList<>();
        values2.add("books");
        values2.add("movies");
        values2.add("tweets");
        friend2.addAttribute("share", values2);
        barbara.addLink(friend2);
        elizabeth.addLink(friend2);
        Link friend3 = new Link("friend", barbara, anna);
        friend3.addAttribute("since", "2011");
        barbara.addLink(friend3);
        anna.addLink(friend3);

        expResult.addNode(barbara);
        expResult.addNode(elizabeth);
        expResult.addNode(carol);
        expResult.addNode(anna);

        System.out.println("*************************");
        System.out.println(expResult);
        System.out.println("--------------------------");
        System.out.println(graph);
        System.out.println("***************************");

        assertEquals(expResult, graph);
    }

    /**
     * Test of getGraph method, of class GraphFactory, with the "with update"
     * method building
     */
    @Test
    public void testGetGraph_File_WITH_UPDATE() throws Exception {
        System.out.println("getGraph with a malformed file in parameter and "
                + "with the \"with update\" building method");

        String filePath = "testfiles/MalFormedFileJUnit.txt";
        Graph graph = factory.getGraph(new File(filePath), GraphBuildingMethod.WITH_UPDATE);

        Graph expResult = new Graph();
        Node barbara = new Node("barbara");
        Node carol = new Node("carol");
        Node dawn = new Node("dawn");
        Node simon = new Node("simon");

        Link friend1 = new Link("friend", barbara, carol);
        friend1.addAttribute("since", "2001");
        barbara.addLink(friend1);
        carol.addLink(friend1);
        Link friend2 = new Link("friend", carol, dawn);
        friend2.addAttribute("since", "2007");
        friend2.addAttribute("share", "movies");
        carol.addLink(friend2);
        dawn.addLink(friend2);
        Link friend3 = new Link("friend", simon, barbara);
        friend3.addAttribute("since", "2003");
        List<String> values3 = new ArrayList<>();
        values3.add("movies");
        values3.add("books");
        values3.add("tweets");
        friend3.addAttribute("share", values3);
        simon.addLink(friend3);
        barbara.addLink(friend3);

        expResult.addNode(barbara);
        expResult.addNode(carol);
        expResult.addNode(dawn);
        expResult.addNode(simon);

        System.out.println("*************************");
        System.out.println(expResult);
        System.out.println("--------------------------");
        System.out.println(graph);
        System.out.println("***************************");

        assertEquals(expResult, graph);
    }
}
