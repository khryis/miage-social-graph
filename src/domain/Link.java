package domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;

/**
 * Class Link
 */
public class Link {

    private String type;
    private Node from;
    private Node to;
    private Map<String, AttributeValues> attributes;

    public Link(String type, Node from, Node to) {
        attributes = new HashMap<>();
        this.type = type;
        this.from = from;
        this.to = to;
    }

    /**
     * Utility methods
     */
    public void addAttribute(String name, String value) {
        List<String> values = new ArrayList<>();
        values.add(value);
        addAttribute(name, values);
    }
    
    public void addAttribute(String name, List<String> values) {
        if (!attributes.containsKey(name)) {
            attributes.put(name, new AttributeValues(values));
        }
    }

    public String getType() {
        return type;
    }

    public Node getFrom() {
        return from;
    }

    public Node getTo() {
        return to;
    }

    public Map<String, AttributeValues> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, AttributeValues> attributes) {
        this.attributes = attributes;
    }

    @Override
    public String toString() {
        StringBuilder display = new StringBuilder();
        display.append("Source: ").append(from.getId());
        display.append(" | To: ").append(to.getId());
        // For each attribute, prints his name and his value(s)
        for (Entry<String, AttributeValues> attribute : attributes.entrySet()) {
            display.append(" | ").append(attribute.getKey()).append(" = ");
            AttributeValues value = attribute.getValue();
            display.append(value);
        }
        return display.toString();
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
        if (!Objects.equals(this.from.getId(), other.from.getId())) {
            return false;
        }
        if (!Objects.equals(this.to.getId(), other.to.getId())) {
            return false;
        }
        //TODO: test if to object with same content (content objects redefine equals) are equals


        if (!Objects.equals(this.attributes, other.attributes)) {
            return false;
        }
        return true;
    }
}
