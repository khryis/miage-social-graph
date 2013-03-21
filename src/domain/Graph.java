package domain;

import java.util.HashSet;

public class Graph implements IGraph {

    private HashSet<Node> nodes;

    public Graph() {
        this.nodes = new HashSet();
    }

    @Override
    public void addNode(Node node) {
        nodes.add(node);
    }
}
