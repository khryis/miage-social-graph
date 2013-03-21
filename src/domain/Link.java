package domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Objects;

public class Link implements ILink {

    private String type;
    private Node nodeSource;
    private Node nodeTo;
    private HashMap<String, ArrayList<String>> attributes;
    private Direction direction;

    private Link() {
        attributes = new HashMap<>();
    }

    public Link(String type, Node from, Node to) {
        this();
        this.type = type;
        nodeSource = from;
        nodeTo = to;
    }

    public Link(String type, Node nodeSource, Node nodeTo, Direction direction) {
        this.type = type;
        this.nodeSource = nodeSource;
        this.nodeTo = nodeTo;
        this.direction = direction;
        this.attributes = new HashMap();
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        String display = "";

        display += "Source : " + this.nodeSource.getId();
        display += " | To : " + this.nodeTo.getId();
        display += " | Direction : " + this.direction.getChar();
        //Pour chaque attribut on affiche le nom et sa ou ses valeurs
        for (Entry<String, ArrayList<String>> attribute : attributes.entrySet()) {
            display += " | " + attribute.getKey() + " = ";
            ArrayList<String> attributeValues = attribute.getValue();
            //L'attribut est minimum de type : "since = 1999"
            display += attributeValues.get(0);
            //Si l'attribut a plusieurs valeurs, par exemple : "allergic to = lactose, kiwi"
            for (int i = 1; i < attributeValues.size(); i++) {
                display += ", " + attributeValues.get(i);
            }
        }

        return display;
    }

    @Override
    public int hashCode() {
        int hash = 3;
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
        final Link other = (Link) obj;
        if (!Objects.equals(this.type, other.type)) {
            return false;
        }
        if (!Objects.equals(this.nodeSource, other.nodeSource)) {
            return false;
        }
        if (!Objects.equals(this.nodeTo, other.nodeTo)) {
            return false;
        }
        if (!Objects.equals(this.attributes, other.attributes)) {
            return false;
        }
        if (this.direction != other.direction) {
            return false;
        }
        return true;
    }
}
