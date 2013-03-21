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
public class LinkTest {
    
    public LinkTest() {
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
     * Test of toString method, of class Link.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        Link instance = new Link("friend", new Node("Barbara"), new Node("Carol"), Direction.RIGHT);
        String expResult = "Source : Barbara";
        expResult += " | To : Carol";
        expResult += " | Direction : " + Direction.RIGHT.getChar();
        String result = instance.toString();
        assertEquals(expResult, result);
    }
}
