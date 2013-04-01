package search;

import domain.Graph;
import domain.Node;
import java.util.ArrayList;
import java.util.List;

/**
 * Class GraphParser
 */
public class GraphParser implements IGraphParser {

    private Graph workingGraph;

    public GraphParser(Graph workingGraph) {
        this.workingGraph = workingGraph;
    }

    @Override
    public SearchResult search(String startingNode, List<String> linkFilters) throws SearchException {
        return search(startingNode, linkFilters, SearchMethod.DFS);
    }

    @Override
    public SearchResult search(String startingNode, List<String> linkFilters, SearchMethod searchMethod) throws SearchException {
        Node node;
        if ((node = workingGraph.getNode(startingNode)) == null) {
            throw new SearchException("The node " + startingNode + " does not exist for this graph");
        }
        switch (searchMethod) {
            case BFS:
                return null;
            default:
                return DFS(node, linkFilters);
        }
    }

    /**
     * The Depth First Search method
     *
     * @param startingNode the starting node
     * @param linkFilters the filters
     * @return an instance of <code>SearchResult</code>
     */
    private SearchResult DFS(Node startingNode, List<String> linkFilters) {
        SearchResult result = new SearchResult();
        List<Node> exploredNodeList = new ArrayList<>();
        processDFS(startingNode, linkFilters, result, exploredNodeList);
        return result;
    }

    /**
     * Process the Depth First Search on the graph
     *
     * @param currentNode the current node
     * @param linkFilters the filters
     * @param result the result which is updated
     * @param exploredNodeList the explored nodes list
     */
    private void processDFS(Node currentNode, List<String> linkFilters, SearchResult result, List<Node> exploredNodesList) {
        exploredNodesList.add(currentNode);
        for (Node n : currentNode.getLinkedNodes(linkFilters)) {
            if (!exploredNodesList.contains(n)) {
                result.addNode(n);
                processDFS(n, linkFilters, result, exploredNodesList);
            }
        }
    }
}
