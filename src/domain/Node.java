package domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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

    public ArrayList<Link> getTypeLinkArrayList(String type) {
        return this.links.get(type);
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
}
