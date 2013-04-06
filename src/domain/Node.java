package domain;

import static domain.LinkFilter.Direction.BLIND;
import static domain.LinkFilter.Direction.BOTH;
import static domain.LinkFilter.Direction.FROM;
import static domain.LinkFilter.Direction.TO;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

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

    /**
     * @return A list of all directly linked nodes
     */
    public Set<Node> getLinkedNodes() {
        Set<Node> linkedNodes = new HashSet<>();
        for (Map.Entry<String, ArrayList<Link>> entry : links.entrySet()) {
            for (Iterator<Link> it = entry.getValue().iterator(); it.hasNext();) {
                Link link = it.next();
                System.out.println(link);
                if (this.equals(link.getFrom())) {
                    linkedNodes.add(link.getTo());
                }
                if (this.equals(link.getTo())) {
                    linkedNodes.add(link.getFrom());
                }
            }

        }
        return linkedNodes;
    }

    /**
     * Returns the linked nodes list to the current node according to the given
     * filter
     *
     * @param filter Criteria of link acceptation
     *
     * @return linked nodes matching the given search criteria
     */
    public Set<Node> getLinkedNodes(LinkFilter filter) {
        Set<Node> linkedNodes = new HashSet<>();
        for (Link link : getLinkList(filter)) {
            switch (filter.getDirection()) {
                case FROM:
                    if (this.equals(link.getFrom())) {
                        linkedNodes.add(link.getTo());
                    }
                    break;
                case TO:
                    if (this.equals(link.getTo())) {
                        linkedNodes.add(link.getFrom());
                    }
                    break;
                case BOTH:
                    //TODO
                    break;
                case BLIND:
                    if (this.equals(link.getFrom())) {
                        linkedNodes.add(link.getTo());
                    }
                    if (this.equals(link.getTo())) {
                        linkedNodes.add(link.getFrom());
                    }
                    break;
            }
        }

        return linkedNodes;
    }

    public Set<Node> getLinkedNodes(List<LinkFilter> filters) {
        if (filters != null) {
            switch (filters.size()) {
                case 0:
                    return getLinkedNodes();
                case 1:
                    return getLinkedNodes(filters.get(0));
                default:
                    //Build a set of all nodes matching one of the filters
                    Set<Node> result = new HashSet<>();
                    for (Iterator<LinkFilter> it = filters.iterator(); it.hasNext();) {
                        for (Iterator<Node> it1 = getLinkedNodes(it.next()).iterator(); it1.hasNext();) {
                            result.add(it1.next());
                        }
                    }
                    return result;
            }
        } else {
            return getLinkedNodes();
        }
    }

    public Set<Link> getLinkList() {
        Set<Link> list = new HashSet<>();
        for (Map.Entry<String, ArrayList<Link>> entry : links.entrySet()) {
            for (Iterator<Link> it = entry.getValue().iterator(); it.hasNext();) {
                list.add(it.next());
            }
        }
        return list;
    }

    /**
     * Returns the list of links of the current node that satisfy the given
     * search criterias
     *
     * @param filter define the link exclusion criteria
     * @return list of links according to the given search criteria
     */
    public Set<Link> getLinkList(LinkFilter filter) {
        Set<Link> list = new HashSet<>();
        System.out.println(filter);
        for (Iterator<Link> it = (links.get(filter.getType())).iterator(); it.hasNext();) {
            Link link = it.next();
            if (link.equals(filter)) {
                switch (filter.getDirection()) {
                    case FROM:
                        if (this.equals(link.getFrom())) {
                            list.add(link);
                        }
                        break;
                    case TO:
                        if (this.equals(link.getTo())) {
                            list.add(link);
                        }
                        break;
                    case BOTH:
                        //TODO
                        break;
                    case BLIND:
                        if (this.equals(link.getFrom())) {
                            list.add(link);
                        } else if (this.equals(link.getTo())) {
                            list.add(link);
                        }
                        break;
                }
            }
        }
        return list;
    }

    public Set<Link> getLinkList(List<LinkFilter> filters) {
        if (filters != null) {
            switch (filters.size()) {
                case 0:
                    return getLinkList();
                case 1:
                    return getLinkList(filters.get(0));
                default:
                    //Build a set of all links matching one of the filters
                    Set<Link> result = new HashSet<>();
                    for (Iterator<LinkFilter> it = filters.iterator(); it.hasNext();) {
                        for (Iterator<Link> it1 = getLinkList(it.next()).iterator(); it1.hasNext();) {
                            result.add(it1.next());
                        }
                    }
                    return result;
            }
        } else {
            return getLinkList();
        }
    }

    public Map<String, ArrayList<Link>> getLinks() {
        return links;
    }

    public String getId() {
        return id;
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
}
