package domain;

import static domain.LinkFilter.Direction.BLIND;
import static domain.LinkFilter.Direction.BOTH;
import static domain.LinkFilter.Direction.IN;
import static domain.LinkFilter.Direction.OUT;
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
    private Map<String, List<Link>> links;

    public Node() {
        this.links = new HashMap<>();
    }

    public Node(String id) {
        this();
        this.id = id;
    }

    /**
     * Utility methods
     */
    public void addLink(Link link) {
        if (links.containsKey(link.getType())) {
            links.get(link.getType()).add(link);
        } else {
            List<Link> arrayListLinks = new ArrayList<>();
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
     * s
     * Gets all links of the node
     *
     * @return an instance of <code>List</code> of <code>Link</code>
     */
    public List<Link> getLinksList() {
        List<Link> linksList = new ArrayList<>();
        for (List<Link> currentLinks : links.values()) {
            linksList.addAll(currentLinks);
        }
        return linksList;
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
        for (Map.Entry<String, List<Link>> entry : links.entrySet()) {
            for (Iterator<Link> it = entry.getValue().iterator(); it.hasNext();) {
                Link link = it.next();
                if (this.equals(link.getFrom())) {
                    linkedNodes.add(link.getTo());
                } else {
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
     * @return linked nodes matching the given search criteria and null if no
     * nodes matche
     */
    public Set<Node> getLinkedNodes(LinkFilter filter) {
        Set<Node> linkedNodes = new HashSet<>();
        for (Link link : getLinkList(filter)) {
            switch (filter.getDirection()) {
                case IN:
                    if (this.equals(link.getTo())) {
                        linkedNodes.add(link.getFrom());
                    }
                    break;
                case OUT:
                    if (this.equals(link.getFrom())) {
                        linkedNodes.add(link.getTo());
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
        if (filters == null) {
            return getLinkedNodes();
        }
        switch (filters.size()) {
            case 0:
                return getLinkedNodes();
            case 1:
                return getLinkedNodes(filters.get(0));
            default:
                //Build a set of all nodes matching one of the filters
                Set<Node> result = new HashSet<>();
                for (Iterator<LinkFilter> it = filters.iterator(); it.hasNext();) {
                    LinkFilter lf = it.next();
                    Set<Link> linkSet = getLinkList(lf);
                    if (linkSet != null) {
                        Set<Node> nodeSet = getLinkedNodes(lf);
                        if (nodeSet != null) {
                            for (Iterator<Node> it1 = nodeSet.iterator(); it1.hasNext();) {
                                result.add(it1.next());
                            }
                        }
                    }
                }
                return result;
        }
    }

    public Set<Link> getLinkList() {
        Set<Link> list = new HashSet<>();
        for (Map.Entry<String, List<Link>> entry : links.entrySet()) {
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
    @SuppressWarnings("IncompatibleEquals")
    public Set<Link> getLinkList(LinkFilter filter) {
        Set<Link> list = new HashSet<>();
        List<Link> linkList = links.get(filter.getType());
        if (linkList != null) {
            for (Iterator<Link> it = linkList.iterator(); it.hasNext();) {
                Link link = it.next();
                if (link.equals(filter)) {
                    switch (filter.getDirection()) {
                        case IN:
                            if (this.equals(link.getTo())) {
                                list.add(link);
                            }
                            break;
                        case OUT:
                            if (this.equals(link.getFrom())) {
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
        }
        return list;
    }

    public Set<Link> getLinkList(List<LinkFilter> filters) {
        if (filters == null) {
            return getLinkList();
        }
        switch (filters.size()) {
            case 0:
                return getLinkList();
            case 1:
                return getLinkList(filters.get(0));
            default:
                //Build a set of all links matching one of the filters
                Set<Link> result = new HashSet<>();
                for (Iterator<LinkFilter> it = filters.iterator(); it.hasNext();) {
                    Set<Link> listSet = getLinkList(it.next());
                    if (listSet != null) {
                        for (Iterator<Link> it1 = listSet.iterator(); it1.hasNext();) {
                            result.add(it1.next());
                        }
                    }
                }
                return result;
        }
    }

    public Map<String, List<Link>> getLinks() {
        return links;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        StringBuilder display = new StringBuilder();
        display.append(id).append("\n");
        // Prints a list of links grouped by type
        StringBuilder in = new StringBuilder();
        StringBuilder out = new StringBuilder();
        for (Map.Entry<String, List<Link>> link : links.entrySet()) {
            for (Link linkDetail : link.getValue()) {
                if (linkDetail.getFrom().getId().equals(id)) {
                    out.append("   --").append(linkDetail).append("--> ").append(linkDetail.getTo().getId()).append("\n");
                } else {
                    in.append("   <--").append(linkDetail).append("-- ").append(linkDetail.getFrom().getId()).append("\n");
                }
            }
        }
        display.append(out);
        display.append(in);
        return display.toString();
    }

    @Override
    public int hashCode() {
        int hash = 31 + id.hashCode();
        for (Map.Entry<String, List<Link>> entry : links.entrySet()) {
            for (Iterator<Link> it = entry.getValue().iterator(); it.hasNext();) {
                hash = hash * 31 + it.next().hashCode();
            }
        }
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
        if (!Objects.equals(this.links, other.getLinks())) {
            return false;
        }
        return true;
    }
}
