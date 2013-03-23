
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

        //Test for the getLinkedNodes and the displayResult methods
        ArrayList<String> filters = new ArrayList<>();
        filters.add("friend");
        filters.add("employee_of");
        System.out.println(g.displayResult(g.getNodes().get("barbara").getLinkedNodes(filters)));
        //Supposed result : carol, elizabeth, anna, bigco
    }
}
