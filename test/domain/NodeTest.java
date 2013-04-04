package domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
        List<Link> friendsLink = from.getLinkList(new LinkFilter(linkType, LinkFilter.Direction.FROM));
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

        Link attendu;
        attendu = new Link(linkType, new Node("Carol"), new Node("Barbara"));
        List<Link> fromfriendsLinks = from.getLinkList(new LinkFilter(linkType, LinkFilter.Direction.FROM));

        assertTrue(fromfriendsLinks.contains(attendu));
    }

    /**
     * Test 2 of addLink method, of class Node.
     */
    @Test
    public void testSizeOfLinkListAfterAddLink() {
        System.out.println("addLink : testSizeOfLinkArrayListAfterAddLink");
        from.addLink(new Link(linkType, from, to));

        List<Link> friendlist = from.getLinkList(new LinkFilter(linkType, LinkFilter.Direction.FROM));
        long attendu = 1;
        assertEquals(attendu, friendlist.size());
    }

    /**
     * <p><strong>Test 3 of addLink method, of class Node.</strong></p> <p>When
     * add a new type of link, new entry (key) in HashMap.</p>
     */
    @Test
    public void testLinkTypeExistAfterAddLink() {
        System.out.println("addLink : testKeyExistAfterAddLink");
        from.addLink(new Link(linkType, from, to));
        Map<String, ArrayList<Link>> links = from.getLinks();
        assertTrue(links.containsKey(linkType));
    }
}
