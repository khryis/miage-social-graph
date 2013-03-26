
import domain.Graph;
import java.util.ArrayList;

public class Run {

    public static void main(String[] args) {
        String filePath = "testfiles/WellFormedFileWithAttr.txt";
        if (args.length != 0) {
            filePath = args[0];
        }

        Graph g = new Graph();
        g.includeFile(filePath);

        System.out.println(g.toString());

        //Test for the Search method so the DFS
        ArrayList<String> filters = new ArrayList<>();
        filters.add("friend");
        System.out.println(g.search(g.getNodes().get("barbara"), filters).display());
        //Supposed result : carol, dawn, elizabeth, anna
    }
}
