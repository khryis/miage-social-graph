package domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Node {

    private String id;
    private HashMap<String, ArrayList<Link>> links;

    public Node(String id) {
        this.id = id;
        this.links = new HashMap();
    }

    public void addLink(Link link) {
        if (links.containsKey(link.getType())) {
            links.get(link.getType()).add(link);
        } else {
            ArrayList<Link> arrayListLinks = new ArrayList();
            arrayListLinks.add(link);
            links.put(link.getType(), arrayListLinks);
        }
    }

    /**
     * Checks if the link is already define for the node. If true, updates the
     * link in the node.
     *
     * @param link the link to check
     * @return true if the link has been updated
     */
    public boolean checkLink(Link link) {
        boolean hasBeenUpdated = false;
        if (links.containsKey(link.getType())) {
            List<Link> linksToCheck = links.get(link.getType());
            for (int i = 0; i < linksToCheck.size() && !hasBeenUpdated; i++) {
                Link currentLink = linksToCheck.get(i);
                if (currentLink.getFrom().equals(link.getFrom())
                        && currentLink.getTo().equals(link.getTo())) {
                    currentLink.update(link);
                    hasBeenUpdated = true;
                }
            }
        }
        return hasBeenUpdated;
    }

    /**
     * Utility methods
     */
    public String getId() {
        return id;
    }

    public HashMap<String, ArrayList<Link>> getLinks() {
        return links;
    }

    public ArrayList<Link> getTypeLinkArrayList(String type) {
        return this.links.get(type);
    }

    @Override
    public String toString() {
        String display = "";

        display += "#### Noeud : " + this.id + "\n";
        // Prints a list of links grouped by type
        for (Map.Entry<String, ArrayList<Link>> link : links.entrySet()) {
            display += "##" + link.getKey() + "\n";
            for (Link linkDetail : link.getValue()) {
                display += linkDetail.toString() + "\n";
            }
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
        final Node other = (Node) obj;
        if (!Objects.equals(this.id, other.getId())) {
            return false;
        }
        return true;
    }
}
