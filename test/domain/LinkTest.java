package domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
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
     * Test of addAttributes method, of class Link
     */
    @Test
    public void testAddAttributes() {
        System.out.println("addAttributes");
        AttributeSingleValue since = new AttributeSingleValue("2003");
        AttributeMultipleValues share = new AttributeMultipleValues();
        share.add("book");
        share.add("movie");
        AttributeSingleValue from = new AttributeSingleValue("school");
        AttributeMultipleValues meet = new AttributeMultipleValues();
        meet.add("2004");
        meet.add("2005");
        meet.add("2006");
        HashMap<String, IAttributeValue> expResult = new HashMap();
        expResult.put("since", since);
        expResult.put("share", share);
        expResult.put("from", from);
        expResult.put("meet", meet);
        Link instance = new Link("friends", new Node("Barbara"), new Node("Henri"));
        instance.addAttributes("since=2003,share=[book|movie],from=school,meet=[2004|2005|2006]");
        System.out.println(instance);
        HashMap<String, IAttributeValue> result = instance.getAttributes();
        assertEquals(expResult, result);
    }
}
