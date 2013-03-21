package domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Node implements INode {

    private String id;
    private HashMap<String, ArrayList<Link>> links;

    public Node(String id) {
        this.id = id;
        this.links = new HashMap();
    }

    @Override
    public String getId() {
        return id;
    }

    public HashMap<String, ArrayList<Link>> getLinks() {
        return links;
    }

    @Override
    public void addLink(Link link) {
        if (links.containsKey(link.getType())) {
            links.get(link.getType()).add(link);
        } else {
            ArrayList<Link> arrayListLinks = new ArrayList();
            arrayListLinks.add(link);
            links.put(link.getType(), arrayListLinks);
        }
    }

    @Override
    public String toString() {
        String display = "";

        display += "#### Noeud : " + this.id + "\n";
        //On affiche la liste des liens regroupés par type
        for (Map.Entry<String, ArrayList<Link>> link : links.entrySet()) {
            display += "## " + link.getKey() + "\n";
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
