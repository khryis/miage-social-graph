package domain;

import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Julien Neuhart
 */
public class NodeTest {
    
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
    }
    
    @After
    public void tearDown() {
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
