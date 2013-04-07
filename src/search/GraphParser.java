package search;

import domain.Graph;
import domain.Link;
import domain.LinkFilter;
import domain.Node;
import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import static search.IGraphParser.Unicity.GLOBALRELATION;
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
    public SearchResult search(String startingNode, List<LinkFilter> filters, int maxDepth) throws SearchException {
        return search(startingNode, filters, SearchMethod.DFS, maxDepth, Unicity.GLOBALNODE);
    }

    @Override
    public SearchResult search(String startingNode, List<LinkFilter> filters, Unicity unicity) throws SearchException {
        return search(startingNode, filters, SearchMethod.DFS, 1, unicity);
    }

    @Override
    public SearchResult search(String startingNode, List<LinkFilter> filters, int maxDepth, Unicity unicity) throws SearchException {
        return search(startingNode, filters, SearchMethod.DFS, maxDepth, Unicity.GLOBALNODE);
    }

    @Override
    public SearchResult search(String startingNode, List<LinkFilter> filters, SearchMethod searchMethod) throws SearchException {
        return search(startingNode, filters, searchMethod, 1, Unicity.GLOBALNODE);
    }

    @Override
    public SearchResult search(String startingNode, List<LinkFilter> filters, SearchMethod searchMethod, int maxDepth) throws SearchException {
        return search(startingNode, filters, searchMethod, maxDepth, Unicity.GLOBALNODE);
    }

    @Override
    public SearchResult search(String startingNode, List<LinkFilter> filters, SearchMethod searchMethod, Unicity unicity) throws SearchException {
        return search(startingNode, filters, searchMethod, 1, unicity);
    }

    @Override
    public SearchResult search(String startingNode, List<LinkFilter> filters, SearchMethod searchMethod, int maxDepth, Unicity unicity) throws SearchException {
        Node node;
        if ((node = workingGraph.getNode(startingNode)) == null) {
            throw new SearchException("The node " + startingNode + " does not exist for this graph");
        }
        if (maxDepth < 1) {
            throw new SearchException("The depth of search must a least be 1");
        }
        switch (searchMethod) {
            case BFS:
                return BFS(node, filters, maxDepth, unicity);
            case DFS:
            default:
                return DFS(node, filters, maxDepth, unicity);
        }
    }

    /**
     * The Depth First Search method
     *
     * @param startingNode the starting node
     * @param filters search criteria
     * @param maxDepth set the depth of search (default = 1)
     * @param unicity set the parsing rule (default = GLOBALNODE)
     * @return an instance of <code>SearchResult</code>
     */
    private SearchResult DFS(Node startingNode, List<LinkFilter> filters, int maxDepth, Unicity unicity) {
        SearchResult result;
        switch (unicity) {
            case GLOBALRELATION:
                return globalRelationDFSStep1(startingNode, filters, maxDepth);
            case GLOBALNODE:
            default:
                return globalNodeDFSStep1(startingNode, filters, maxDepth);
        }
    }

    /**
     * Perform the first step of research. Select the first set of Nodes that
     * will be used to perform the second step of research
     */
    private SearchResult globalNodeDFSStep1(Node startNode, List<LinkFilter> filters, int maxDepth) {
        //get the matching nodes
        Set<Node> toVisit = new HashSet<>();
        if (filters != null) {
            if (filters.isEmpty()) {
                filters = null;
            } else {
                toVisit = startNode.getLinkedNodes(filters.remove(0));
            }
        } else {
            toVisit = startNode.getLinkedNodes();
        }

        //visit the matching nodes
        SearchResult result = new SearchResult();
        if (filters == null) {
            for (Iterator<Node> it = toVisit.iterator(); it.hasNext();) {
                globalNodeDFSStep2(it.next(), result, 1, maxDepth);
            }
        } else {
            for (Iterator<Node> it = toVisit.iterator(); it.hasNext();) {
                globalNodeDFSStep2(it.next(), filters, result, 1, maxDepth);
            }
        }

        return result;
    }

    /**
     * Perform the second step of research. Add recursivly all linked nodes to
     * the result until the given maxDepth is reached
     */
    private void globalNodeDFSStep2(Node currentNode, SearchResult result, int currentDepth, int maxDepth) {
        if (result.addNode(currentNode)) {
            if (currentDepth < maxDepth) {
                for (Node n : currentNode.getLinkedNodes()) {
                    globalNodeDFSStep2(n, result, currentDepth + 1, maxDepth);
                }
            }
        }
    }

    /**
     * Perform the second step of research. Add recursivly the nodes matchink
     * the given filters until the given maxDepth is reached
     */
    private void globalNodeDFSStep2(Node currentNode, List<LinkFilter> filters, SearchResult result, int currentDepth, int maxDepth) {
        if (result.addNode(currentNode)) {
            if (currentDepth < maxDepth) {
                Set<Node> nodeSet = currentNode.getLinkedNodes(filters);
                if (!(nodeSet == null)) {
                    for (Node n : nodeSet) {
                        globalNodeDFSStep2(n, filters, result, currentDepth + 1, maxDepth);
                    }
                }
            }
        }
    }

    private SearchResult globalRelationDFSStep1(Node startNode, List<LinkFilter> filters, int maxDepth) {
        //get the matching nodes
        Set<Link> toVisit = new HashSet<>();
        if (filters != null) {
            if (filters.isEmpty()) {
                filters = null;
            } else {
                toVisit = startNode.getLinkList(filters.remove(0));
            }
        } else {
            toVisit = startNode.getLinkList();
        }

        //visit the matching nodes
        Set<Link> visited = new HashSet<>();
        SearchResult result = new SearchResult();
        if (filters == null) {
            for (Iterator<Link> it = toVisit.iterator(); it.hasNext();) {
                globalRelationDFSStep2(startNode, it.next(), visited, result, 0, maxDepth);
            }
        } else {
            for (Iterator<Link> it = toVisit.iterator(); it.hasNext();) {
                globalRelationDFSStep2(startNode, it.next(), filters, visited, result, 0, maxDepth);
            }
        }
        return result;
    }

    private void globalRelationDFSStep2(Node currentNode, Link currentLink, Set<Link> visited, SearchResult result, int currentDepth, int maxDepth) {
        if (visited.add(currentLink)) {
            if (currentDepth < maxDepth) {
                Node target = (currentLink.getTo().getId().equals(currentNode.getId()) ? currentLink.getFrom() : currentLink.getTo());
                result.addNode(target);
                for (Link l : target.getLinkList()) {
                    globalRelationDFSStep2(target, l, visited, result, currentDepth + 1, maxDepth);
                }
            }
        }
    }

    private void globalRelationDFSStep2(Node currentNode, Link currentLink, List<LinkFilter> filters, Set<Link> visited, SearchResult result, int currentDepth, int maxDepth) {
        if (visited.add(currentLink)) {
            if (currentDepth < maxDepth) {
                Node target = (currentLink.getTo().getId().equals(currentNode.getId()) ? currentLink.getFrom() : currentLink.getTo());
                result.addNode(target);
                for (Link l : target.getLinkList(filters)) {
                    globalRelationDFSStep2(target, l, filters, visited, result, currentDepth + 1, maxDepth);
                }
            }
        }
    }

    /**
     * The Breadth First Search method
     *
     * @param startingNode the starting node
     * @param filters search criteria
     * @param level set the depth of search (default = 1)
     * @param unicity set the parsing rule (default = GLOBALNODE)
     * @return an instance of <code>SearchResult</code>
     */
    private SearchResult BFS(Node startingNode, List<LinkFilter> filters, int level, Unicity unicity) {
        SearchResult result = new SearchResult();
        switch (unicity) {
            case GLOBALRELATION:
                Set<Link> exploredLinksList = new HashSet<>();
                globalRelationBFS(startingNode, filters, result, exploredLinksList, 0, level);
                break;
            default:
                Set<Node> exploredNodesList = new HashSet<>();
                globalNodeBFS(startingNode, filters, result, exploredNodesList, 0, level);
                break;
        }
        return result;
    }

    private void globalNodeBFS(Node startingNode, List<LinkFilter> filters, SearchResult result, Set<Node> exploredNodes, int currentLevel, int maxLevel) {
        exploredNodes.add(startingNode);
        ArrayDeque<Node> nodesQueue = new ArrayDeque();
        nodesQueue.add(startingNode);
        Node currentNode;
        while (!nodesQueue.isEmpty()) {
            currentNode = nodesQueue.pollFirst();
            result.addNode(currentNode);
            for (Iterator<Node> it = currentNode.getLinkedNodes(filters.get(currentLevel)).iterator(); it.hasNext();) {
                Node n = it.next();
                if (!exploredNodes.contains(n)) {
                    exploredNodes.add(n);
                    nodesQueue.add(n);
                }
            }
        }
    }

    private void globalRelationBFS(Node currentNode, List<LinkFilter> filters, SearchResult result, Set<Link> exploredLinks, int currentLevel, int maxLevel) {
        //TODO add this kind of parsing
    }
}
