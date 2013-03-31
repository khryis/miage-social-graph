package domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Graph {

    private HashMap<String, Node> nodes;

    public Graph() {
        this.nodes = new HashMap();
    }

    /**
     * Utility methods
     */
    public void addNode(Node node) {
        nodes.put(node.getId(), node);
    }

    public Node getNode(String nodeId) {
        return nodes.get(nodeId.toLowerCase());
    }

    public HashMap<String, Node> getNodes() {
        return nodes;
    }

    /**
     * Search the graph with default method
     *
     * @param startingNode
     * @param linkFilter
     * @return
     */
    public Result search(Node startingNode, ArrayList<String> linkFilter) {

        return search(startingNode, linkFilter, SearchMethod.DFS);
    }

    /**
     * Search the graph with a method set in parameters
     *
     * @param startingNode
     * @param linkFilter
     * @param method
     * @return
     */
    public Result search(Node startingNode, ArrayList<String> linkFilter, SearchMethod method) {
        switch (method) {
            case DFS:
                return DFS(startingNode, linkFilter);
            default:
                return DFS(startingNode, linkFilter);
        }
    }

    private Result DFS(Node startingNode, ArrayList<String> linkFilter) {
        Result result = new Result();
        ArrayList<Node> exploredNodeList = new ArrayList<>();

        DFS(startingNode, linkFilter, result, exploredNodeList);

        return result;
    }

    /**
     * Process the Depth First Search of the graph
     *
     * @param currentNode
     * @param linkFilter
     * @param resultList
     * @param exploredNodeList
     */
    private void DFS(Node currentNode, ArrayList<String> linkFilter, Result result, ArrayList<Node> exploredNodeList) {
        exploredNodeList.add(currentNode);
        for (Node n : currentNode.getLinkedNodes(linkFilter)) {
            if (!exploredNodeList.contains(n)) {
                result.add(n);
                DFS(n, linkFilter, result, exploredNodeList);
            }
        }
    }

    @Override
    public String toString() {
        String display = "";
        // Prints each node of the graph
        for (Map.Entry<String, Node> node : nodes.entrySet()) {
            display += node.getValue().toString() + "\n";
        }
        return display;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Graph other = (Graph) obj;
        if (!Objects.equals(this.nodes, other.nodes)) {
            return false;
        }
        return true;
    }
}
