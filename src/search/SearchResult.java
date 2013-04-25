package search;

import domain.Node;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

/**
 * Class SearchResult
 */
public class SearchResult {

    private Set<Node> resultNodes;

    public SearchResult() {
        resultNodes = new HashSet<>();
    }

    /**
     * Add a node if it isn't already present
     */
    public boolean addNode(Node node) {
        return resultNodes.add(node);
    }

    public boolean containsNode(Node node) {
        return resultNodes.contains(node);
    }

    public boolean removeNode(Node node) {
        return resultNodes.remove(node);
    }

    public Set<Node> getResultNodes() {
        return resultNodes;
    }

    public Node[] getResultNodesAsArray() {
        Node[] result = new Node[resultNodes.size()];

        resultNodes.toArray(result);

        return result;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("Resultat : ");
        StringBuilder display = new StringBuilder();
        if (resultNodes.isEmpty()) {
            display.append("0 node found");
        } else {
            for (Iterator<Node> it = resultNodes.iterator(); it.hasNext();) {
                Node n = it.next();
                result.append(n.getId()).append(", ");
                display.append(n);
            }
            result.replace(result.length() - 2, result.length(), "\n");
        }
        return result.append(display).toString();
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
