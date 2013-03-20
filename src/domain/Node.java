package domain;

import java.util.ArrayList;
import java.util.HashMap;

public class Node implements INode {

    private String id;
    private HashMap<String, ArrayList<Link>> links;

    public Node(String id) {
        this.id = id;
        this.links = new HashMap<String, ArrayList<Link>>();
    }

    public String getId() {
        return id;
    }

    public void addLink(Link link) {
        if (links.containsKey(link.getType())) {
            links.get(link.getType()).add(link);
        } else {
            ArrayList<Link> arrayListLinks = new ArrayList<Link>();
            arrayListLinks.add(link);
            links.put(link.getType(), arrayListLinks);
        }
    }
}
