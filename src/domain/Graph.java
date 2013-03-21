package domain;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Graph implements IGraph {

    private HashMap<String, Node> nodes;

    public Graph() {
        this.nodes = new HashMap<>();
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

            br.close();
            fr.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Graph.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Graph.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                br.close();
                fr.close();
            } catch (IOException ex) {
                Logger.getLogger(Graph.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void addLine(String line) {
        //TODO: Sprint 2 check line validity and continue if false (ex: regex)
        line = line.toLowerCase();
        int fromNbr = line.indexOf("--"), toNbr = line.indexOf("-->") + 3;
        String fromStr = line.substring(0, fromNbr), toStr = line.substring(toNbr);
        line = line.substring(fromNbr, toNbr);
        String linkType = line.substring(0, line.indexOf("["));

        Node to = nodes.get(toStr);
        if (to == null) {
            to = new Node(toStr);
        }
        Node from = nodes.get(fromStr);
        if (from == null) {
            from = new Node(fromStr);
        }
        Link link = new Link(linkType, from, to);
        to.addLink(link);
        from.addLink(link);
    }
}
