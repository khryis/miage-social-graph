package domain;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import search.GraphParser;

/**
 * Class Graph
 */
public class Graph {

    public GraphParser parser;
    private HashMap<String, Node> nodes;

    public Graph() {
        parser = new GraphParser(this);
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

    public Map<String, Node> getNodes() {
        return nodes;
    }

    @Override
    public String toString() {
        StringBuilder display = new StringBuilder();
        // Prints each node of the graph
        for (Map.Entry<String, Node> node : nodes.entrySet()) {
            display.append(node.getValue()).append("\n");
        }
        return display.toString();
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
