package search;

import domain.Graph;
import domain.Link;
import domain.LinkFilter;
import domain.Node;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import static search.SearchMethod.BFS;

/**
 * Class GraphParser
 */
public class GraphParser implements IGraphParser {

    private Graph workingGraph;

    public GraphParser(Graph workingGraph) {
        this.workingGraph = workingGraph;
    }

    @Override
    public SearchResult search(String startingNode, List<LinkFilter> filters) throws SearchException {
        return search(startingNode, filters, SearchMethod.DFS, 1, Unicity.GLOBALNODE);
    }

    @Override
    public SearchResult search(String startingNode, List<LinkFilter> filters, int level) throws SearchException {
        return search(startingNode, filters, SearchMethod.DFS, level, Unicity.GLOBALNODE);
    }

    @Override
    public SearchResult search(String startingNode, List<LinkFilter> filters, Unicity unicity) throws SearchException {
        return search(startingNode, filters, SearchMethod.DFS, 1, unicity);
    }

    @Override
    public SearchResult search(String startingNode, List<LinkFilter> filters, int level, Unicity unicity) throws SearchException {
        return search(startingNode, filters, SearchMethod.DFS, level, Unicity.GLOBALNODE);
    }

    @Override
    public SearchResult search(String startingNode, List<LinkFilter> filters, SearchMethod searchMethod) throws SearchException {
        return search(startingNode, filters, searchMethod, 1, Unicity.GLOBALNODE);
    }

    @Override
    public SearchResult search(String startingNode, List<LinkFilter> filters, SearchMethod searchMethod, int level) throws SearchException {
        return search(startingNode, filters, searchMethod, level, Unicity.GLOBALNODE);
    }

    @Override
    public SearchResult search(String startingNode, List<LinkFilter> filters, SearchMethod searchMethod, Unicity unicity) throws SearchException {
        return search(startingNode, filters, searchMethod, 1, unicity);
    }

    @Override
    public SearchResult search(String startingNode, List<LinkFilter> filters, SearchMethod searchMethod, int level, Unicity unicity) throws SearchException {
        Node node;
        if ((node = workingGraph.getNode(startingNode)) == null) {
            throw new SearchException("The node " + startingNode + " does not exist for this graph");
        }
        if (level < 1) {
            throw new SearchException("The level of search must a least be 1");
        }
        switch (searchMethod) {
            case BFS:
                return null;
            case DFS:
            default:
                return DFS(node, filters, level, unicity);
        }
    }

    /**
     * Process the Depth First Search on the graph
     *
     * @param currentNode the current node
     * @param linkFilters the filters
     * @param result the result which is updated
     * @param exploredNodeList the explored nodes list
     */
    /*@Deprecated
     private void recursiveDFS(Node currentNode, List<String> linkFilters, SearchResult result, List<Node> exploredNodesList) {
     exploredNodesList.add(currentNode);
     for (Node n : currentNode.getLinkedNodes(linkFilters)) {
     if (!exploredNodesList.contains(n)) {
     result.addNode(n);
     recursiveDFS(n, linkFilters, result, exploredNodesList);
     }
     }
     }*/
    /**
     * The Depth First Search method
     *
     * @param startingNode the starting node
     * @param filters search criteria
     * @param level set the depth of search (default = 1)
     * @param unicity set the parsing rule (default = GLOBALNODE)
     * @return an instance of <code>SearchResult</code>
     */
    private SearchResult DFS(Node startingNode, List<LinkFilter> filters, int level, Unicity unicity) {
        SearchResult result = new SearchResult();
        switch (unicity) {
            case GLOBALRELATION:
                Set<Link> exploredLinksList = new HashSet<>();
                recursiveGlobalRelationDFS(startingNode, filters, result, exploredLinksList, 0, level);
                break;
            case GLOBALNODE:
            default:
                Set<Node> exploredNodesList = new HashSet<>();
                recursiveGlobalNodeDFS(startingNode, filters, result, exploredNodesList, 0, level);
                break;
        }
        return result;
    }

    private void recursiveGlobalNodeDFS(Node currentNode, List<LinkFilter> filters, SearchResult result, Set<Node> exploredNodes, int currentLevel, int maxLevel) {
        exploredNodes.add(currentNode);
        currentLevel++;
        for (Node n : currentNode.getLinkedNodes(filters.get(currentLevel >= filters.size() ? filters.size() - 1 : currentLevel))) {
            if (!exploredNodes.contains(n)) {
                int delta = currentLevel - filters.size();
                if (delta >= 0) {
                    result.addNode(n);
                }
                if (delta < maxLevel) {
                    recursiveGlobalNodeDFS(n, filters, result, exploredNodes, currentLevel, maxLevel);
                }
            }
        }
    }

    private void recursiveGlobalRelationDFS(Node currentNode, List<LinkFilter> filters, SearchResult result, Set<Link> exploredLinks, int currentLevel, int maxLevel) {
        //TODO add this kind of parsing
    }
}
