package domain;

import java.util.HashMap;
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
     * Test of addAttributes method, of class Link.
     */
    @Test
    public void testAddAttributes() {
        System.out.println("addAttributes");
        Attributes role = new Attributes(new String[]{"Research"});
        Attributes hired = new Attributes(new String[]{"Oct08"});
        HashMap<String, Attributes> expResult = new HashMap();
        expResult.put("role", role);
        expResult.put("hired", hired);
        Link instance = new Link("employee_of", new Node("Barbara"), new Node("BigCo"));
        instance.addAttributes("role=Research,hired=Oct08");
        HashMap<String, Attributes> result = instance.getAttributes();
        assertEquals(expResult, result);
    }
}
