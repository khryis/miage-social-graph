package domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Class Node
 */
public class Node {

    private String id;
    private HashMap<String, ArrayList<Link>> links;

    public Node(String id) {
        this.id = id;
        this.links = new HashMap();
    }

    /**
     * Utility methods
     */
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
     * Returns the link if it exists in the node
     *
     * @param type the link type
     * @param from the starting node
     * @param to the end node
     * @return the link or null
     */
    public Link getLink(String type, Node from, Node to) {
        boolean found = false;
        Link link = null;
        if (links.containsKey(type)) {
            List<Link> linksList = links.get(type);
            for (int i = 0; i < linksList.size() && !found; i++) {
                Link currentLink = linksList.get(i);
                if (currentLink.getFrom().equals(from)
                        && currentLink.getTo().equals(to)) {
                    link = currentLink;
                    found = true;
                }
            }
        }
        return link;
    }

    /**
     * Checks if the link exists in the node
     *
     * @param link the link to check
     * @return <tt>true</tt> if the node contains the link
     */
    public boolean contains(Link link) {
        boolean contains = false;
        if (links.containsKey(link.getType())) {
            List<Link> linksList = links.get(link.getType());
            for (int i = 0; i < linksList.size() && !contains; i++) {
                Link currentLink = linksList.get(i);
                if (currentLink.getFrom().equals(link.getFrom())
                        && currentLink.getTo().equals(link.getTo())) {
                    contains = true;
                }
            }
        }
        return contains;
    }

    public List<Link> getLinkTypeArrayList(String type) {
        if (this.links.get(type) != null) {
            return this.links.get(type);
        } else {
            return new ArrayList<>();
        }
    }

    /**
     * Returns the nodes linked to this one by the filters in the parameter
     *
     * @param linkFilters the filters
     * @return an instance of <code>List</code> of nodes
     */
    public List<Node> getLinkedNodes(List<String> linkFilters) {
        ArrayList<Node> linkedNodes = new ArrayList();
        //For each type of link selected as filter in the parameter of the search
        for (String linkType : linkFilters) {
            //We get the links linked to the node
            for (Link link : getLinkTypeArrayList(linkType)) {
                //If the actual node is the source of the link, we add the destination node
                if (this.equals(link.getFrom())) {
                    linkedNodes.add(link.getTo());
                    //If it's not we add the source of the link
                } else {
                    linkedNodes.add(link.getFrom());
                }
            }
        }
        return linkedNodes;
    }

    public List<Node> getLinkedNodes(HashMap<String, IsSource> linkFilters) {
        for (Map.Entry<String, IsSource> entry : linkFilters.entrySet()) {
            String string = entry.getKey();
            IsSource isSource = entry.getValue();

        }

        return null;
    }

    public String getId() {
        return id;
    }

    public Map<String, ArrayList<Link>> getLinks() {
        return links;
    }

    @Override
    public String toString() {
        StringBuilder display = new StringBuilder();
        display.append("#### Noeud : ").append(id).append("\n");
        // Prints a list of links grouped by type
        for (Map.Entry<String, ArrayList<Link>> link : links.entrySet()) {
            display.append("##").append(link.getKey()).append("\n");
            for (Link linkDetail : link.getValue()) {
                display.append(linkDetail).append("\n");
            }
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
        final Node other = (Node) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.links, other.links)) {
            return false;
        }
        return true;
    }

    public enum IsSource {

        TRUE,
        FALSE
    }
}
