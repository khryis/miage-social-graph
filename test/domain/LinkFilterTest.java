package domain;

import domain.LinkFilter.Direction;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class LinkFilterTest {

    private LinkFilter linkFilterTest;
    private LinkFilter linkFilterSame;
    private LinkFilter linkFilterDifferent;

    public LinkFilterTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        linkFilterTest = new LinkFilter("friend");
        linkFilterSame = new LinkFilter("friend");
        linkFilterDifferent = new LinkFilter("friend", Direction.IN);
    }

    @After
    public void tearDown() {
    }

    /**
     * Test 1 of hashCode method, of class Graph
     */
    @Test
    public void testHashCodeIntegrity() {
        System.out.println("hashCode : same object, same integer");
        int expected = linkFilterTest.hashCode();
        assertEquals(expected, linkFilterTest.hashCode());
    }

    /**
     * Test 2 of hashCode method, of class Graph
     */
    @Test
    public void testHashCodeOnEquals() {
        System.out.println("hashCode : if object equals true");
        int expected = linkFilterTest.hashCode();
        assertEquals(expected, linkFilterSame.hashCode());
    }

    /**
     * Test 1 of equals method, of class Graph
     */
    @Test
    public void testEqualsOnEquals() {
        System.out.println("equals : if objects are equals");
        assertTrue(linkFilterTest.equals(linkFilterSame));
    }

    /**
     * Test 2 of equals method, of class Graph
     */
    @Test
    public void testEqualsOnDifferent() {
        System.out.println("equals : if objects are different");
        assertFalse(linkFilterTest.equals(linkFilterDifferent));
    }

    /**
     * Test 3 of equals method, of class Graph
     */
    @Test
    public void testEqualsOnNull() {
        System.out.println("equals : if target object is null");
        assertFalse(linkFilterTest.equals(null));
    }
}
