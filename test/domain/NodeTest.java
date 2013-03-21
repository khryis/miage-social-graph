package domain;

import java.util.ArrayList;
import java.util.HashMap;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class NodeTest {

    private Node From;
    private Node To;
    private String LinkType;

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
        From = new Node("Carol");
        To = new Node("Barbara");
        LinkType = "friends";
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getTypeLinkArrayList method, of class Node
     */
    @Test
    public void testGetTypeLinkArrayList() {
        From.addLink(new Link(LinkType, From, To));
        ArrayList<Link> friendsLink = From.getTypeLinkArrayList(LinkType);
        assertNotNull(friendsLink);
    }

    /**
     * Test 1 of addLink method, of class Node.
     */
    @Test
    public void testAddLink() {
        System.out.println("addLink : testAddLink");

        Link link = new Link("friends", new Node("Carol"), new Node("Barbara"));
        From.addLink(link);

        Link attendu;
        attendu = new Link(LinkType, new Node("Carol"), new Node("Barbara"));
        ArrayList<Link> fromfriendsLinks = From.getTypeLinkArrayList(LinkType);

        assertTrue(fromfriendsLinks.contains(attendu));
    }

    /**
     * Test 2 of addLink method, of class Node.
     */
    @Test
    public void testSizeOfLinkArrayListAfterAddLink() {
        System.out.println("addLink : testSizeOfLinkArrayListAfterAddLink");
        From.addLink(new Link(LinkType, From, To));

        ArrayList<Link> friendlist = From.getTypeLinkArrayList(LinkType);
        long attendu = 1;
        assertEquals(attendu, friendlist.size());
    }

    /**
     * <p><strong>Test 3 of addLink method, of class Node.</strong></p>
     * <p>When add a new type of link, new entry (key) in HashMap.</p>
     */
    @Test
    public void testLinkTypeExistAfterAddLink() {
        System.out.println("addLink : testKeyExistAfterAddLink");
        From.addLink(new Link(LinkType, From, To));
        HashMap<String, ArrayList<Link>> links = From.getLinks();
        assertTrue(links.containsKey(LinkType));
    }

    /**
     * Test of toString method, of class Node.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        Node instance = new Node("Barbara");
        instance.addLink(new Link("friend", new Node("Barbara"), new Node("Carol")));
        instance.addLink(new Link("employee_of", new Node("Barbara"), new Node("BigCo")));
        String expResult = "#### Noeud : Barbara\n";
        expResult += "## friend\n";
        expResult += "Source: Barbara";
        expResult += " | To: Carol";
        expResult += "## employee_of\n";
        expResult += "Source: Barbara";
        expResult += " | To: BigCo";
        String result = instance.toString();
        assertEquals(expResult, result);
    }
}
