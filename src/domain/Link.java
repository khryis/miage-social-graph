package domain;

import java.util.Map.Entry;
import java.util.Objects;

/**
 * Class Link
 */
public class Link extends AbstractLink {

    private Node from;
    private Node to;

    public Link() {
        super();
    }

    public Link(String type, Node from, Node to) {
        super(type);
        this.from = from;
        this.to = to;
    }

    public Node getFrom() {
        return from;
    }

    public Node getTo() {
        return to;
    }

    @Override
    public String toString() {
        StringBuilder display = new StringBuilder();
        display.append("Source: ").append(from.getId());
        display.append(" | To: ").append(to.getId());
        // For each attribute, prints his name and his value(s)
        for (Entry<String, AttributeValues> attribute : getAttributes().entrySet()) {
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
        if (!super.equals(obj)) {
            return false;
        }

        if (getClass() == obj.getClass()) {
            final Link other = (Link) obj;
            if (!Objects.equals(this.from.getId(), other.from.getId())) {
                return false;
            }
            if (!Objects.equals(this.to.getId(), other.to.getId())) {
                return false;
            }
        } else if (obj.getClass() != LinkFilter.class) {
            return false;
        }

        return true;
    }
}
