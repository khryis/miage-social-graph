
import domain.Graph;
import factory.GraphBuildingException;
import factory.GraphBuildingMethod;
import factory.GraphFactory;
import factory.GraphFileParserException;
import factory.IGraphFactory;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import search.GraphParser;

public class Run {

    public static void main(String[] args) {
        String filePath = "testfiles/WellFormedFileWithoutAttr.txt";
        if (args.length != 0) {
            filePath = args[0];
        }
        IGraphFactory factory = new GraphFactory();
        Graph g = factory.getGraph();
        try {
            g = factory.getGraph(new File(filePath), GraphBuildingMethod.STRICT);
            System.out.println(g);
        } catch (GraphFileParserException | GraphBuildingException | IOException ex) {
            Logger.getLogger(Run.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Test for DFS
        List<String> filters = new ArrayList<>();
        filters.add("friend");
        GraphParser parser = new GraphParser(g);
        //TODO uncomment search
        /*try {
         SearchResult result = parser.search("barbara", filters);
         System.out.println(result);
         // Supposed result : carol, dawn, elizabeth, anna
         } catch (SearchException ex) {
         Logger.getLogger(Run.class.getName()).log(Level.SEVERE, null, ex);
         }
         */
    }
}
