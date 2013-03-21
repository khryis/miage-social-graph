package domain;

import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class NodeTest {

    private Node From;
    private Node To;

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
        this.From = new Node("Carol");
        this.To = new Node("Barbara");
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of addLink method, of class Node.
     */
    @Test
    public void testAddLink() {
        System.out.println("addLink");

        Link link = new Link("friend", new Node("Carol"), new Node("Barbara"), Direction.RIGHT);
        Link[] attendu = {new Link("friend", From, To, Direction.LEFT)};
        ArrayList<Link> fromLink = this.From.getTypeLinkArrayList(null);
        Link[] links = (Link[]) fromLink.toArray();
        this.From.addLink(link);
        //assertEquals(this.Attendu, this.From);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    @Test
    public void testSizeLinkArrayListAfterAddLink() {
        System.out.println("addLink");

        Link link = new Link("friend", new Node("Carol"), new Node("Barbara"), Direction.RIGHT);
        Link[] attendu = {new Link("friend", From, To, Direction.LEFT)};
        ArrayList<Link> fromLink = this.From.getTypeLinkArrayList(null);
        Link[] links = (Link[]) fromLink.toArray();
        this.From.addLink(link);
        //assertEquals(this.Attendu, this.From);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of toString method, of class Node.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        Node instance = null;
        String expResult = "";
        String result = instance.toString();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}
