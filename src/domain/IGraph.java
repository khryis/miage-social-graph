package domain;

public interface IGraph {

    public void addNode(Node node);

    /**
     * Initialize the graph from the given filepath
     */
    public void includeFile(String filePath);
}
