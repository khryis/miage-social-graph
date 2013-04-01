package search;

import domain.Node;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Class SearchResult
 */
public class SearchResult {

    private List<Node> resultNodes;

    public SearchResult() {
        resultNodes = new ArrayList<>();
    }

    /**
     * Utility methods
     */
    public void addNode(Node node) {
        resultNodes.add(node);
    }

    public List<Node> getResultNodes() {
        return resultNodes;
    }

    public Node[] getResultNodesAsArray() {
        Node[] result = new Node[resultNodes.size()];
        for (int i = 0; i < result.length; i++) {
            result[i] = resultNodes.get(i);
        }
        return result;
    }

    @Override
    public String toString() {
        StringBuilder display = new StringBuilder("Result : ");
        if (resultNodes.isEmpty()) {
            display.append("0 node found");
        } else {
            display.append(resultNodes.get(0));
            for (int i = 1; i < resultNodes.size(); i++) {
                display.append(", ").append(resultNodes.get(i));
            }
        }
        return display.toString();
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 23 * hash + Objects.hashCode(this.resultNodes);
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
        final SearchResult other = (SearchResult) obj;
        if (!Objects.equals(this.resultNodes, other.resultNodes)) {
            return false;
        }
        return true;
    }
}
