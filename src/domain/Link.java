package domain;

import java.util.ArrayList;
import java.util.HashMap;

public class Link implements ILink {

    private String type;
    private Node nodeSource;
    private Node nodeTo;
    private HashMap<String, ArrayList<String>> attributes;
    private Direction direction;

    public Link(String type, Node nodeSource, Node nodeTo, Direction direction) {
        this.type = type;
        this.nodeSource = nodeSource;
        this.nodeTo = nodeTo;
        this.direction = direction;
        this.attributes = new HashMap<String, ArrayList<String>>();
    }

    public String getType() {
        return type;
    }
}
