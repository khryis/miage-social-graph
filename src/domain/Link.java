package domain;

import java.util.Map;
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
        StringBuilder display = new StringBuilder(getType());
        if (getAttributes().size() > 0) {
            display.append("[");
        }
        StringBuilder attr = new StringBuilder();
        for (Entry<String, AttributeValues> attribute : getAttributes().entrySet()) {
            attr.append(attribute.getKey()).append("=").append(attribute.getValue()).append(", ");
        }
        if (attr.length() > 2) {
            display.append(attr, 0, attr.length() - 2);
        }
        if (getAttributes().size() > 0) {
            display.append("]");
        }
        return display.toString();
    }

    @Override
    public int hashCode() {
        int hash = super.hashCode();
        hash = hash * 17 + from.getId().hashCode();
        hash = hash * 31 + to.getId().hashCode();
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass().getSuperclass() != obj.getClass().getSuperclass()) {
            return false;
        }
        if (!getType().equals(((AbstractLink) obj).getType())) {
            return false;
        }

        if (getClass() == obj.getClass()) {
            //Same instance, we want both object to be exactly the same
            final Link other = (Link) obj;
            if (!Objects.equals(this.from.getId(), other.from.getId())) {
                return false;
            }
            if (!Objects.equals(this.to.getId(), other.to.getId())) {
                return false;
            }
            if (!Objects.equals(getAttributes(), other.getAttributes())) {
                return false;
            }
        } else if (obj.getClass() == LinkFilter.class) {
            //the given object is a linkfilter, we want it to be a subset of this
            final LinkFilter other = (LinkFilter) obj;
            for (Map.Entry<String, AttributeValues> en : other.getAttributes().entrySet()) {
                if (!getAttributes().get(en.getKey()).isSupersetOf(en.getValue())) {
                    return false;
                }
            }
        } else {
            return false;
        }

        return true;
    }
}
