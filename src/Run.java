
import domain.Graph;
import factory.GraphFactory;
import factory.IGraphFactory;
import factory.builder.GraphBuildingException;
import factory.builder.GraphBuildingMethod;
import factory.parser.GraphFileParserException;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Run {

    public static void main(String[] args) {
        String filePath = "testfiles/MalFormedFileWithAttr.txt";
        if (args.length != 0) {
            filePath = args[0];
        }
        IGraphFactory factory = new GraphFactory();
        Graph g;
        try {
            g = factory.getGraph(new File(filePath), GraphBuildingMethod.WITH_UPDATE);
            System.out.println(g.toString());
        } catch (GraphFileParserException | GraphBuildingException | IOException ex) {
            Logger.getLogger(Run.class.getName()).log(Level.SEVERE, null, ex);
        }

        

        //Test for the Search method so the DFS
        //ArrayList<String> filters = new ArrayList<>();
        //filters.add("friend");
        //System.out.println(g.search(g.getNodes().get("barbara"), filters).display());
        //Supposed result : carol, dawn, elizabeth, anna
    }
}
