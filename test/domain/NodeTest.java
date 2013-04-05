package domain;

import factory.GraphBuildingMethod;
import factory.GraphFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class NodeTest {

    private Node from;
    private Node to;
    private String linkType;
    private String filePath;
    private GraphFactory factory;

    public NodeTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        from = new Node("Carol");
        to = new Node("Barbara");
        linkType = "friends";
        filePath = "testfiles/JUnitTestSearch2.txt";
        factory = new GraphFactory();
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getTypeLinkArrayList method, of class Node
     */
    @Test
    public void testGetLinkList() {
        from.addLink(new Link(linkType, from, to));
        Set<Link> friendsLink = from.getLinkList(new LinkFilter(linkType, LinkFilter.Direction.FROM));
        assertNotNull(friendsLink);
    }

    /**
     * Test 1 of addLink method, of class Node.
     */
    @Test
    public void testAddLink() {
        System.out.println("addLink : testAddLink");

        Link link = new Link("friends", new Node("Carol"), new Node("Barbara"));
        from.addLink(link);

        Link expResult;
        expResult = new Link(linkType, new Node("Carol"), new Node("Barbara"));

        Set<Link> fromfriendsLinks = from.getLinkList(new LinkFilter(linkType, LinkFilter.Direction.BLIND));

        assertTrue(fromfriendsLinks.contains(expResult));
    }

    /**
     * Test 2 of addLink method, of class Node.
     */
    @Test
    public void testSizeOfLinkListAfterAddLink() {
        System.out.println("addLink : testSizeOfLinkArrayListAfterAddLink");
        from.addLink(new Link(linkType, from, to));

        Set<Link> friendlist = from.getLinkList(new LinkFilter(linkType, LinkFilter.Direction.FROM));
        long expResult = 1;
        assertEquals(expResult, friendlist.size());
    }

    /**
     * <p><strong>Test 3 of addLink method, of class Node.</strong></p> <p>When add a new type of link, new entry (key) in HashMap.</p>
     */
    @Test
    public void testLinkTypeExistAfterAddLink() {
        System.out.println("addLink : testKeyExistAfterAddLink");
        from.addLink(new Link(linkType, from, to));
        Map<String, ArrayList<Link>> links = from.getLinks();
        assertTrue(links.containsKey(linkType));
    }

    @Test
    public void testLinkGetLinkList_Filter() throws Exception {
        System.out.println("addLink : testLinkGetLinkList_Filter");

        Graph graph = factory.getGraph(new File(filePath), GraphBuildingMethod.STRICT);

        Set<Link> expResult = new HashSet<>();
        expResult.add(new Link("f", graph.getNode("1"), graph.getNode("2")));
        expResult.add(new Link("f", graph.getNode("1"), graph.getNode("3")));
        expResult.add(new Link("f", graph.getNode("1"), graph.getNode("4")));

        LinkFilter f1 = new LinkFilter("l", LinkFilter.Direction.BLIND);
        Node startNode = graph.getNode("1");

        Set<Link> resultLinks = startNode.getLinkList(f1);

        assertThat(factory, null);
//        assertNotNull(expResult);
//        assertNotNull(resultLinks);
//        assertEquals(expResult.size(), resultLinks.size());
//        assertTrue(expResult.containsAll(resultLinks));
//        assertTrue(expResult.containsAll(resultLinks));
        assertSame(expResult, resultLinks);

    }
}
