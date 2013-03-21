package domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

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
}
