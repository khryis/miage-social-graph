package domain;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Graph implements IGraph {

    private HashMap<String, Node> nodes;

    public Graph() {
        this.nodes = new HashMap();
    }

    public HashMap<String, Node> getNodes() {
        return nodes;
    }

    @Override
    public void addNode(Node node) {
        nodes.put(node.getId(), node);
    }

    @Override
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
        //TODO: Sprint 2 check line validity and continue if false (ex: regex)
        line = line.toLowerCase();
        int fromNbr = line.indexOf("--") - 1, toNbr = line.indexOf("-->") + 4;
        String fromStr = line.substring(0, fromNbr), toStr = line.substring(toNbr);
        line = line.substring(fromNbr, toNbr);
        String linkType = line.substring(3, line.indexOf("["));

        buildLine(toStr, fromStr, linkType);
    }

    /**
     * Line Builder
     */
    private void buildLine(String toStr, String fromStr, String linkType) {
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
        to.addLink(link);
        from.addLink(link);
    }

    @Override
    public Result search(Node startingNode, ArrayList<String> linkFilter) {

        return search(startingNode, linkFilter, SearchMethod.DFS);
    }

    @Override
    public Result search(Node startingNode, ArrayList<String> linkFilter, SearchMethod method) {
        Result result = new Result();
        ArrayList<Node> exploredNodeList = new ArrayList<>();

        switch (method) {
            case DFS:
                DFS(startingNode, linkFilter, result, exploredNodeList);
                break;
        }

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
        //On boucle sur la liste des Nodes sur laquelle on appelle la méthode d'affichage
        for (Map.Entry<String, Node> node : nodes.entrySet()) {
            display += node.getValue().toString() + "\n";
        }
        return display;
    }
}
