package domain;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Chris
 */
public class GraphTest {

    public GraphTest() {
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
     * Test of addNode method, of class Graph.
     */
    @Test
    public void testAddNode() {
        System.out.println("addNode");
        Node node = null;
        Graph instance = new Graph();
        instance.addNode(node);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of includeFile method, of class Graph.
     */
    @Test
    public void testIncludeFile() {
        System.out.println("includeFile");
        String filePath = "";
        Graph instance = new Graph();
        instance.includeFile(filePath);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addLine method, of class Graph.
     */
    @Test
    public void testAddLine() {
        System.out.println("addLine");
        String line = "";
        Graph instance = new Graph();
        instance.addLine(line);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}
