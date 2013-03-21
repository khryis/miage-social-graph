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
    public void testGetTypeLinkArrayList() {
        from.addLink(new Link(linkType, from, to));
        ArrayList<Link> friendsLink = from.getTypeLinkArrayList(linkType);
        assertNotNull(friendsLink);
    }

    /**
     * Test 1 of addLink method, of class Node.
     */
    @Test
    public void testAddLink() {
        System.out.println("addLink : testAddLink");

        Link link = new Link("friends", new Node("Carol"), new Node("Barbara"), Direction.RIGHT);
        from.addLink(link);

        Link attendu;
        attendu = new Link(linkType, new Node("Carol"), new Node("Barbara"), Direction.RIGHT);
        ArrayList<Link> fromfriendsLinks = from.getTypeLinkArrayList(linkType);

        assertTrue(fromfriendsLinks.contains(attendu));
    }

    /**
     * Test 2 of addLink method, of class Node.
     */
    @Test
    public void testSizeOfLinkArrayListAfterAddLink() {
        System.out.println("addLink : testSizeOfLinkArrayListAfterAddLink");
        from.addLink(new Link(linkType, from, to));

        ArrayList<Link> friendlist = from.getTypeLinkArrayList(linkType);
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
        from.addLink(new Link(linkType, from, to));
        HashMap<String, ArrayList<Link>> links = from.getLinks();
        assertTrue(links.containsKey(linkType));
    }

    /**
     * Test of toString method, of class Node.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        Node instance = new Node("Barbara");
        instance.addLink(new Link("friend", new Node("Barbara"), new Node("Carol"), Direction.RIGHT));
        instance.addLink(new Link("employee_of", new Node("Barbara"), new Node("BigCo"), Direction.RIGHT));
        String expResult = "#### Noeud : Barbara\n";
        expResult += "## friend\n";
        expResult += "Source : Barbara";
        expResult += " | To : Carol";
        expResult += " | Direction : " + Direction.RIGHT.getChar() + "\n";
        expResult += "## employee_of\n";
        expResult += "Source : Barbara";
        expResult += " | To : BigCo";
        expResult += " | Direction : " + Direction.RIGHT.getChar() + "\n";
        String result = instance.toString();
        assertEquals(expResult, result);
    }
}
