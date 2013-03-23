package domain;

import java.util.ArrayList;

public interface IGraph {

    public void addNode(Node node);

    /**
     * Initialize the graph from the given filepath
     */
    public void includeFile(String filePath);

    /**
     *
     * @param startingNode
     * @param linkFilter
     * @return
     */
    public ArrayList<Node> search(Node startingNode, ArrayList<String> linkFilter);

    /**
     *
     * @param nodeList
     * @return
     */
    public String displayResult(ArrayList<Node> nodeList);

    @Override
    public String toString();
}
