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
     * Perform the first step of research. Select the first set of Nodes that will be used to perform the second step of research
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
     * Perform the second step of research. Add recursivly all linked nodes to the result until the given maxDepth is reached
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
     * Perform the second step of research. Add recursivly the nodes matchink the given filters until the given maxDepth is reached
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
     * The Breadth First Search method (BFS)
     *
     * @param startingNode the starting node
     * @param filters search criteria
     * @param level set the depth of search (default = 1)
     * @param unicity set the parsing rule (default = GLOBALNODE)
     * @return an instance of <code>SearchResult</code>
     */
    private SearchResult BFS(Node startingNode, List<LinkFilter> filters, int maxDepth, Unicity unicity) {
        switch (unicity) {
            case GLOBALRELATION:
                return globalRelationBFSStep1(startingNode, filters, maxDepth);
            default:
                return globalNodeBFSStep1(startingNode, filters, maxDepth);
        }
    }

    /**
     * Perform the first step of BFS research for global node. Select the first set of Nodes that will be used to perform the second step of research
     */
    private SearchResult globalNodeBFSStep1(Node startNode, List<LinkFilter> filters, int maxDepth) {
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
        ArrayDeque<Node> nodesQueue = new ArrayDeque();
        int nbNodesInDepth = 0;
        for (Iterator<Node> it = toVisit.iterator(); it.hasNext();) {
            nodesQueue.add(it.next());
            nbNodesInDepth++;
        }
        if (filters == null) {
            globalNodeBFSStep2(nodesQueue, result, nbNodesInDepth, maxDepth);
        } else {
            globalNodeBFSStep2(nodesQueue, filters, result, nbNodesInDepth, maxDepth);
        }

        return result;
    }

    //TODO handle the depth
    /**
     * Perform the second step of BFS research without filters for global node.
     */
    private void globalNodeBFSStep2(ArrayDeque<Node> nodesQueue, SearchResult result, int nbNodesInDepth, int maxDepth) {
        Node currentNode;
        int currentDepth = 1, maxNodesInDepth = nbNodesInDepth, numCurrentNode = 0;
        nbNodesInDepth = 0;
        while (!nodesQueue.isEmpty()) {
            if (currentDepth <= maxDepth) {
                if (numCurrentNode < maxNodesInDepth) {
                    currentNode = nodesQueue.pollFirst();
                    if (result.addNode(currentNode)) {
                        for (Node n : currentNode.getLinkedNodes()) {
                            nodesQueue.add(n);
                            nbNodesInDepth++;
                        }
                    }
                    numCurrentNode++;
                } else {
                    currentDepth++;
                    maxNodesInDepth = nbNodesInDepth;
                    nbNodesInDepth = 0;
                    numCurrentNode = 0;
                }
            } else {
                nodesQueue.clear();
            }
        }
    }

    //TODO handle the depth
    /**
     * Perform the second step of BFS research with filters for global node.
     */
    private void globalNodeBFSStep2(ArrayDeque<Node> nodesQueue, List<LinkFilter> filters, SearchResult result, int nbNodesInDepth, int maxDepth) {
        Node currentNode;
        int currentDepth = 1, maxNodesInDepth = nbNodesInDepth, numCurrentNode = 0;
        nbNodesInDepth = 0;
        while (!nodesQueue.isEmpty()) {
            if (currentDepth <= maxDepth) {
                if (numCurrentNode < maxNodesInDepth) {
                    currentNode = nodesQueue.pollFirst();
                    if (result.addNode(currentNode)) {
                        for (Node n : currentNode.getLinkedNodes(filters)) {
                            nodesQueue.add(n);
                            nbNodesInDepth++;
                        }
                    }
                    numCurrentNode++;
                } else {
                    currentDepth++;
                    maxNodesInDepth = nbNodesInDepth;
                    nbNodesInDepth = 0;
                    numCurrentNode = 0;
                }
            } else {
                nodesQueue.clear();
            }
        }
    }

    /**
     * Perform the first step of BFS research for global relation. Select the first set of Nodes that will be used to perform the second step of research
     */
    private SearchResult globalRelationBFSStep1(Node startNode, List<LinkFilter> filters, int maxDepth) {
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
        SearchResult result = new SearchResult();
        Set<Link> visited = new HashSet<>();
        ArrayDeque<Node> nodesQueue = new ArrayDeque();
        int nbNodesInDepth = 0;
        for (Iterator<Link> it = toVisit.iterator(); it.hasNext();) {
            Link currentLink = it.next();
            if (visited.add(currentLink)) {
                nodesQueue.add((currentLink.getTo().getId().equals(startNode.getId()) ? currentLink.getFrom() : currentLink.getTo()));
                nbNodesInDepth++;
            }
        }
        if (filters == null) {
            globalRelationBFSStep2(nodesQueue, visited, result, nbNodesInDepth, maxDepth);
        } else {
            globalRelationBFSStep2(nodesQueue, visited, filters, result, nbNodesInDepth, maxDepth);
        }

        return result;
    }

    //TODO handle the depth
    /**
     * Perform the second step of BFS research without filters for global relation.
     */
    private void globalRelationBFSStep2(ArrayDeque<Node> nodesQueue, Set<Link> visited, SearchResult result, int nbNodesInDepth, int maxDepth) {
        Node currentNode;
        int currentDepth = 1, maxNodesInDepth = nbNodesInDepth, numCurrentNode = 0;
        nbNodesInDepth = 0;
        while (!nodesQueue.isEmpty()) {
            if (currentDepth <= maxDepth) {
                if (numCurrentNode < maxNodesInDepth) {
                    currentNode = nodesQueue.pollFirst();
                    result.addNode(currentNode);
                    Set<Link> toVisit = currentNode.getLinkList();
                    for (Iterator<Link> it = toVisit.iterator(); it.hasNext();) {
                        Link currentLink = it.next();
                        if (visited.add(currentLink)) {
                            nodesQueue.add((currentLink.getTo().getId().equals(currentNode.getId()) ? currentLink.getFrom() : currentLink.getTo()));
                            nbNodesInDepth++;
                        }
                    }
                    numCurrentNode++;
                } else {
                    currentDepth++;
                    maxNodesInDepth = nbNodesInDepth;
                    nbNodesInDepth = 0;
                    numCurrentNode = 0;
                }
            } else {
                nodesQueue.clear();
            }
        }
    }

    //TODO handle the depth
    /**
     * Perform the second step of BFS research with filters for global relation.
     */
    private void globalRelationBFSStep2(ArrayDeque<Node> nodesQueue, Set<Link> visited, List<LinkFilter> filters, SearchResult result, int nbNodesInDepth, int maxDepth) {
        Node currentNode;
        int currentDepth = 1, maxNodesInDepth = nbNodesInDepth, numCurrentNode = 0;
        nbNodesInDepth = 0;
        while (!nodesQueue.isEmpty()) {
            if (currentDepth <= maxDepth) {
                if (numCurrentNode < maxNodesInDepth) {
                    currentNode = nodesQueue.pollFirst();
                    result.addNode(currentNode);
                    Set<Link> toVisit = currentNode.getLinkList(filters);
                    for (Iterator<Link> it = toVisit.iterator(); it.hasNext();) {
                        Link currentLink = it.next();
                        if (visited.add(currentLink)) {
                            nodesQueue.add((currentLink.getTo().getId().equals(currentNode.getId()) ? currentLink.getFrom() : currentLink.getTo()));
                            nbNodesInDepth++;
                        }
                    }
                    numCurrentNode++;
                } else {
                    currentDepth++;
                    maxNodesInDepth = nbNodesInDepth;
                    nbNodesInDepth = 0;
                    numCurrentNode = 0;
                }
            } else {
                nodesQueue.clear();
            }
        }
    }
}
