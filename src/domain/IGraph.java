package domain;

import java.util.ArrayList;

public interface IGraph {

    public void addNode(Node node);

    /**
     * Initialize the graph from the given filepath
     */
    public void includeFile(String filePath);

    /**
     * Search the graph with default method
     *
     * @param startingNode
     * @param linkFilter
     * @return
     */
    public Result search(Node startingNode, ArrayList<String> linkFilter);

    /**
     * Search the graph with a method set in parameters
     *
     * @param startingNode
     * @param linkFilter
     * @param method
     * @return
     */
    public Result search(Node startingNode, ArrayList<String> linkFilter, SearchMethod method);

    @Override
    public String toString();
}
