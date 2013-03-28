
import domain.Graph;

public class Run {

    public static void main(String[] args) {
        String filePath = "testfiles/MalFormedFileWithAttr.txt";
        if (args.length != 0) {
            filePath = args[0];
        }

        Graph g = new Graph();
        g.includeFile(filePath);

        System.out.println(g.toString());
    }
}
