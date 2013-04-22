package domain;

import java.util.ArrayList;
import java.util.List;
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
     * Test 1 of equals method, of class Link.
     */
    @Test
    public void testEqualsLinkFilter_True() {
        System.out.println("equals link filter true");
        LinkFilter linkFilter = new LinkFilter("friend", LinkFilter.Direction.BLIND);
        linkFilter.addAttribute("since", "1999");
        Link link = new Link("friend", new Node("barbara"), new Node("sophie"));
        link.addAttribute("since", "1999");
        link.addAttribute("share", "books");
        boolean expResult = true;
        boolean result = link.equals(linkFilter);
        assertEquals(expResult, result);
    }

    /**
     * Test 2 of equals method, of class Link.
     */
    @Test
    public void testEqualsLinkFilter_False() {
        System.out.println("equals link filter false");
        LinkFilter linkFilter = new LinkFilter("friend", LinkFilter.Direction.BLIND);
        linkFilter.addAttribute("since", "2000");
        Link link = new Link("friend", new Node("barbara"), new Node("sophie"));
        link.addAttribute("since", "1999");
        link.addAttribute("share", "books");
        boolean expResult = false;
        boolean result = link.equals(linkFilter);
        assertEquals(expResult, result);
    }

    /**
     * Test 3 of equals method, of class Link.
     */
    @Test
    public void testEqualsLinkFilterSameAttributes_True() {
        System.out.println("equals link filter same attributes true");
        LinkFilter linkFilter = new LinkFilter("friend", LinkFilter.Direction.BLIND);
        linkFilter.addAttribute("since", "1999");
        linkFilter.addAttribute("share", "books");
        Link link = new Link("friend", new Node("barbara"), new Node("sophie"));
        link.addAttribute("since", "1999");
        link.addAttribute("share", "books");
        boolean expResult = true;
        boolean result = link.equals(linkFilter);
        assertEquals(expResult, result);
    }

    /**
     * Test 4 of equals method, of class Link.
     */
    @Test
    public void testEqualsLinkFilterSameAttributes_False() {
        System.out.println("equals link filter same attributes false");
        LinkFilter linkFilter = new LinkFilter("friend", LinkFilter.Direction.BLIND);
        linkFilter.addAttribute("since", "1999");
        List<String> values1 = new ArrayList<>();
        values1.add("tweets");
        values1.add("books");
        linkFilter.addAttribute("share", values1);
        Link link = new Link("friend", new Node("barbara"), new Node("sophie"));
        link.addAttribute("since", "1999");
        List<String> values2 = new ArrayList<>();
        values2.add("movies");
        values2.add("books");
        link.addAttribute("share", values2);
        boolean expResult = false;
        boolean result = link.equals(linkFilter);
        assertEquals(expResult, result);
    }

    /**
     * Test 5 of equals method, of class Link.
     */
    @Test
    public void testEqualsLink_True() {
        System.out.println("equals link true");
        Link link1 = new Link("friend", new Node("barbara"), new Node("sophie"));
        link1.addAttribute("since", "1999");
        link1.addAttribute("share", "books");
        Link link2 = new Link("friend", new Node("barbara"), new Node("sophie"));
        link2.addAttribute("since", "1999");
        link2.addAttribute("share", "books");
        boolean expResult = true;
        boolean result = link1.equals(link2);
        assertEquals(expResult, result);
    }

    /**
     * Test 6 of equals method, of class Link.
     */
    @Test
    public void testEqualsLink_False() {
        System.out.println("equals link false");
        Link link1 = new Link("friend", new Node("barbara"), new Node("sophie"));
        link1.addAttribute("since", "1999");
        List<String> values1 = new ArrayList<>();
        values1.add("tweets");
        values1.add("books");
        link1.addAttribute("share", values1);
        Link link2 = new Link("friend", new Node("barbara"), new Node("sophie"));
        link2.addAttribute("since", "1999");
        link2.addAttribute("share", "books");
        boolean expResult = false;
        boolean result = link1.equals(link2);
        assertEquals(expResult, result);
    }

    /**
     * Test 1 of hashCode method, of class Link
     */
    @Test
    public void testHashCodeIntegrity() {
        System.out.println("hashCode : same object, same integer");
        Link testLink = new Link("friend", new Node("barbara"), new Node("sophie"));
        int expected = testLink.hashCode();
        assertEquals(expected, testLink.hashCode());
    }

    /**
     * Test 2 of hashCode method, of class Link
     */
    @Test
    public void testHashCodeOnEquals() {
        System.out.println("hashCode : if object equals true");
        Link link1 = new Link("friend", new Node("barbara"), new Node("sophie"));
        Link link2 = new Link("friend", new Node("barbara"), new Node("sophie"));
        int expected = link1.hashCode();
        assertEquals(expected, link2.hashCode());
    }
}
