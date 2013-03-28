package domain;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Graph {

    private HashMap<String, Node> nodes;

    public Graph() {
        this.nodes = new HashMap();
    }

    public void addNode(Node node) {
        nodes.put(node.getId(), node);
    }

    public Node getNode(String nodeId) {
        return nodes.get(nodeId.toLowerCase());
    }

    /**
     * Initialize the graph from the given filepath
     */
    public void includeFile(String filePath) {
        FileReader fr = null;
        BufferedReader br = null;
        try {
            fr = new FileReader(new File(filePath));
            br = new BufferedReader(fr);

            String line;
            while ((line = br.readLine()) != null) {
                addLine(line);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Graph.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Graph.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                br.close();
                fr.close();
            } catch (NullPointerException ex) {
            } catch (IOException ex) {
                Logger.getLogger(Graph.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Add the line content in graph. Line analyzer
     */
    private void addLine(String line) {
        // Check line validity and skip add if false
        if (lineIsValid(line)) {
            line = line.toLowerCase();
            int fromNbr = line.indexOf("--") - 1, toNbr = line.indexOf("-->") + 4;
            String fromStr = line.substring(0, fromNbr), toStr = line.substring(toNbr);
            line = line.substring(fromNbr + 3, toNbr);
            String linkType = line.substring(0, line.indexOf("["));
            String attributes = line.substring(line.indexOf("[") + 1, line.lastIndexOf("]"));
            buildLine(toStr, fromStr, linkType, attributes);
        }
    }

    private boolean lineIsValid(String line) {
        //TODO improve the flexibility of the regexp
        String motif = "\\w+\\s--\\w+\\[((((\\w+=(\\[((\\w+)|\\|)+\\]|\\w+)),)*)((\\w+=(\\[((\\w+)|\\|)+\\]|\\w+))))\\]-->\\s\\w+";
        Pattern p = Pattern.compile(motif);
        Matcher m = p.matcher(line);
        return m.matches();
    }

    /**
     * Line Builder
     */
    private void buildLine(String toStr, String fromStr, String linkType, String attributes) {
        Node to = nodes.get(toStr);
        if (to == null) {
            to = new Node(toStr);
            addNode(to);
        }
        Node from = nodes.get(fromStr);
        if (from == null) {
            from = new Node(fromStr);
            addNode(from);
        }
        Link link = new Link(linkType, from, to);
        link.addAttributes(attributes);
        if (!to.checkLink(link)) {
            to.addLink(link);
        }
        if (!from.checkLink(link)) {
            from.addLink(link);
        }
    }

    /**
     * Utility methods
     */
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
