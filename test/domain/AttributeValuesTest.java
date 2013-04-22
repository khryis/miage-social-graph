package domain;

import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class AttributeValuesTest {

    private AttributeValues attributeValuesTest1;
    private AttributeValues attributeValuesTest2;

    public AttributeValuesTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        attributeValuesTest1 = new AttributeValues();
        attributeValuesTest2 = new AttributeValues();
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
        attributeValuesTest1.addValue("Tasty");
        int expected = attributeValuesTest1.hashCode();
        assertEquals(expected, attributeValuesTest1.hashCode());
    }

    /**
     * Test 2 of hashCode method, of class Graph
     */
    @Test
    public void testHashCodeOnEquals() {
        System.out.println("hashCode : if object equals true");
        attributeValuesTest1.addValue("Tasty");
        attributeValuesTest2.addValue("Tasty");
        int expected = attributeValuesTest1.hashCode();
        assertEquals(expected, attributeValuesTest2.hashCode());
    }

    /**
     * Test 1 of equals method, of class Graph
     */
    @Test
    public void testEqualsOnEquals() {
        System.out.println("equals : if objects are equals");
        attributeValuesTest1.addValue("Tasty");
        attributeValuesTest2.addValue("Tasty");
        assertTrue(attributeValuesTest1.equals(attributeValuesTest2));
    }

    /**
     * Test 2 of equals method, of class Graph
     */
    @Test
    public void testEqualsOnDifferent() {
        System.out.println("equals : if objects are different");
        attributeValuesTest1.addValue("Tasty");
        attributeValuesTest2.addValue("Disgusting");
        assertFalse(attributeValuesTest1.equals(attributeValuesTest2));
    }

    /**
     * Test 3 of equals method, of class Graph
     */
    @Test
    public void testEqualsOnNull() {
        System.out.println("equals : if target object is null");
        attributeValuesTest1.addValue("Tasty");
        assertFalse(attributeValuesTest1.equals(null));
    }
}
