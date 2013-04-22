package domain;

import factory.GraphBuildingMethod;
import factory.GraphFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
    private Node testNode;
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
        testNode = new Node("Carol");
        linkType = "friends";
        filePath = "testfiles/JUnitTestSearch2.txt";
        factory = new GraphFactory();
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getTypeLinkList method, of class Node
     */
    @Test
    public void testGetLinkList() {
        from.addLink(new Link(linkType, from, to));
        Set<Link> friendsLink = from.getLinkList(new LinkFilter(linkType, LinkFilter.Direction.OUT));
        assertNotNull(friendsLink);
    }

    /**
     * Test 2 of getTypeLinkList method, of class Node
     */
    @Test
    public void testGetLinkList_2() throws Exception {
        System.out.println("addLink : testLinkGetLinkList_2");

        Graph graph = factory.getGraph(new File(filePath), GraphBuildingMethod.STRICT);
        from = graph.getNode("1");

        Set<Link> expResult = new HashSet<>();
        expResult.add(new Link("l", graph.getNode("1"), graph.getNode("2")));
        expResult.add(new Link("l", graph.getNode("1"), graph.getNode("3")));
        expResult.add(new Link("l", graph.getNode("1"), graph.getNode("4")));
        expResult.add(new Link("f", graph.getNode("1"), graph.getNode("5")));

        List<LinkFilter> linkFilters = new ArrayList<>();
        LinkFilter f1 = new LinkFilter("l", LinkFilter.Direction.BLIND);
        LinkFilter f2 = new LinkFilter("f", LinkFilter.Direction.BLIND);
        linkFilters.add(f1);
        linkFilters.add(f2);
        Node startNode = graph.getNode("1");

        Set<Link> resultLinks = startNode.getLinkList(linkFilters);

        assertTrue(expResult.containsAll(resultLinks));
    }

    /**
     * Test 1 of addLink method, of class Node.
     */
    @Test
    public void testAddLink() {
        System.out.println("addLink : testAddLink");

        Link link = new Link("friends", from, to);
        from.addLink(link);

        Link expResult;
        expResult = new Link(linkType, from, to);

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

        Set<Link> friendlist = from.getLinkList(new LinkFilter(linkType, LinkFilter.Direction.OUT));
        long expResult = 1;
        assertEquals(expResult, friendlist.size());
    }

    /**
     * <p><strong>Test 3 of addLink method, of class Node.</strong></p> <p>When
     * add a new type of link, new entry (key) in HashMap.</p>
     */
    @Test
    public void testLinkTypeExistAfterAddLink() {
        System.out.println("addLink : testKeyExistAfterAddLink");
        from.addLink(new Link(linkType, from, to));
        Map<String, List<Link>> links = from.getLinks();
        assertTrue(links.containsKey(linkType));
    }

    @Test
    public void testLinkGetLinkList_Filter() throws Exception {
        System.out.println("addLink : testLinkGetLinkList_Filter");

        Graph graph = factory.getGraph(new File(filePath), GraphBuildingMethod.STRICT);
        from = graph.getNode("1");

        Set<Link> expResult = new HashSet<>();
        expResult.add(new Link("l", graph.getNode("1"), graph.getNode("2")));
        expResult.add(new Link("l", graph.getNode("1"), graph.getNode("3")));
        expResult.add(new Link("l", graph.getNode("1"), graph.getNode("4")));

        LinkFilter f1 = new LinkFilter("l", LinkFilter.Direction.BLIND);
        Node startNode = graph.getNode("1");

        Set<Link> resultLinks = startNode.getLinkList(f1);

        assertTrue(resultLinks.containsAll(expResult));
    }

    @Test
    public void testLinkGetLinkList_Filters() throws Exception {
        System.out.println("addLink : testLinkGetLinkList_Filters");

        Graph graph = factory.getGraph(new File(filePath), GraphBuildingMethod.STRICT);
        from = graph.getNode("1");

        Set<Link> expResult = new HashSet<>();
        expResult.add(new Link("l", graph.getNode("1"), graph.getNode("2")));
        expResult.add(new Link("l", graph.getNode("1"), graph.getNode("3")));
        expResult.add(new Link("l", graph.getNode("1"), graph.getNode("4")));
        expResult.add(new Link("f", graph.getNode("1"), graph.getNode("5")));

        List<LinkFilter> linkFilters = new ArrayList<>();
        LinkFilter f1 = new LinkFilter("l", LinkFilter.Direction.BLIND);
        LinkFilter f2 = new LinkFilter("f", LinkFilter.Direction.BLIND);
        linkFilters.add(f1);
        linkFilters.add(f2);
        Node startNode = graph.getNode("1");

        Set<Link> resultLinks = startNode.getLinkList(linkFilters);

        assertEquals(expResult, resultLinks);
        assertTrue(resultLinks.containsAll(expResult));
    }

    /**
     * Test 1 of hashCode method, of class Node
     */
    @Test
    public void testHashCodeIntegrity() {
        System.out.println("hashCode : same object, same integer");
        int expected = from.hashCode();
        assertEquals(expected, from.hashCode());
    }

    /**
     * Test 2 of hashCode method, of class Node
     */
    @Test
    public void testHashCodeOnEquals() {
        System.out.println("hashCode : if object equals true");
        from.addLink(new Link(linkType, from, to));
        testNode.addLink(new Link(linkType, from, to));
        int expected = from.hashCode();
        assertEquals(expected, testNode.hashCode());
    }

    /**
     * Test 1 of equals method, of class Graph
     */
    @Test
    public void testEqualsOnEquals() {
        System.out.println("equals : if objects are equals");
        from.addLink(new Link(linkType, from, to));
        testNode.addLink(new Link(linkType, from, to));
        assertTrue(from.equals(testNode));
    }

    /**
     * Test 2 of equals method, of class Graph
     */
    @Test
    public void testEqualsOnDifferent() {
        System.out.println("equals : if objects are different");
        assertFalse(from.equals(to));
    }

    /**
     * Test 3 of equals method, of class Node
     */
    @Test
    public void testEqualsOnNull() {
        System.out.println("equals : if target object is null");
        assertFalse(from.equals(null));
    }
}
