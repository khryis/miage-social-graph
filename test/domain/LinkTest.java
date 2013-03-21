package domain;

import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

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
        Link instance = new Link("friend", new Node("Barbara"), new Node("Carol"));
        String expResult = "Source: Barbara";
        expResult += " | To: Carol";
        String result = instance.toString();
        assertEquals(expResult, result);
    }
}
