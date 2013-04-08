package domain;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import search.GraphParser;

/**
 * Class Graph
 */
public class Graph {

    @SuppressWarnings("PublicField")
    public GraphParser parser;
    private Map<String, Node> nodes;

    public Graph() {
        parser = new GraphParser(this);
        this.nodes = new HashMap<>();
    }

    /**
     * Exports the graph into a file
     *
     * @param fileName the file name
     * @return an instance of <code>File</code>
     * @throws IOException
     */
    public File export(String fileName) throws IOException {
        File file = new File("./" + fileName + ".txt");
        try (FileWriter writer = new FileWriter(file); BufferedWriter bw = new BufferedWriter(writer)) {
            List<Link> visitedLinks = new ArrayList<>();
            // Parses nodes
            for (Node node : nodes.values()) {
                List<Link> currentLinks = node.getLinksList();
                // Parses links of the current node
                for (Link link : currentLinks) {
                    // Checks if the current link has not been visited yet and if the from node is the current node
                    String nodeFromId = link.getFrom().getId();
                    if (!visitedLinks.contains(link) && nodeFromId.equals(node.getId())) {
                        visitedLinks.add(link);
                        String nodeToId = link.getTo().getId();
                        String linkType = link.getType();
                        bw.write(nodeFromId + " --" + linkType + '[');
                        Map<String, AttributeValues> attributes = link.getAttributes();
                        int attributesNumber = attributes.size();
                        int currentAttributeIndex = 1;
                        // Parses attributes of the current link
                        for (Map.Entry<String, AttributeValues> attribute : attributes.entrySet()) {
                            String attributeName = attribute.getKey();
                            bw.write(attributeName + '=');
                            // If one value
                            if (attribute.getValue().getValues().size() == 1) {
                                bw.write(attribute.getValue().getValues().get(0));
                                // If more
                            } else {
                                List<String> values = attribute.getValue().getValues();
                                bw.write('[');
                                // Parses values of the current attribute
                                for (int i = 0; i < values.size(); i++) {
                                    bw.write(values.get(i));
                                    // Checks if it is not the last value
                                    if (i != values.size() - 1) {
                                        bw.write('|');
                                    }
                                }
                                bw.write(']');
                            }
                            // Checks if it is not the last attribute
                            if (currentAttributeIndex != attributesNumber) {
                                bw.write(',');
                            }
                            currentAttributeIndex++;
                        }
                        bw.write("]--> " + nodeToId);
                        bw.newLine();
                        bw.flush();
                    }
                }
            }
        }
        return file;
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
