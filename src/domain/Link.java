package domain;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Link implements ILink {

    private String type;
    private Node from;
    private Node to;
    private HashMap<String, AttributeValues> attributes;

    private Link() {
        attributes = new HashMap<>();
    }

    public Link(String type, Node from, Node to) {
        this();
        this.type = type;
        this.from = from;
        this.to = to;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public void addAttributes(String attributesLine) {
        String attribute, key;
        AttributeValues value;

        String motif = "((\\w+=(\\[((\\w+)|\\|)+\\]|\\w+)))";
        Pattern p = Pattern.compile(motif);
        Matcher m = p.matcher(attributesLine);

        while (m.find()) {
            // attribute : "since=1999" or "share=[book|movie]"
            attribute = attributesLine.substring(m.start(), m.end());
            // key = "since"
            key = attribute.substring(0, attribute.indexOf("="));
            value = new AttributeValues();
            if (attribute.contains("[")) {
                //"[book|movie]"
                value.add(attribute.substring(attribute.indexOf("[") + 1, attribute.length() - 1).split("\\|"));
            } else {
                //"1999"
                value.add(attribute.substring(attribute.indexOf("=") + 1));
            }
            attributes.put(key, value);
        }
    }

    @Override
    public String toString() {
        String display = "";

        display += "Source: " + this.from.getId();
        display += " | To: " + this.to.getId();
        //Pour chaque attribut on affiche le nom et sa ou ses valeurs
        for (Entry<String, AttributeValues> attribute : attributes.entrySet()) {
            display += " | " + attribute.getKey() + " = ";
            AttributeValues attributeValues = attribute.getValue();
            //L'attribut est minimum de type : "since = 1999"
            display += attributeValues.toString();
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
        if (!Objects.equals(this.from, other.from)) {
            return false;
        }
        if (!Objects.equals(this.to, other.to)) {
            return false;
        }
        //TODO: test if to object with same content (content objects redefine equals) are equals
        if (!Objects.equals(this.attributes, other.attributes)) {
            return false;
        }
        return true;
    }
}
