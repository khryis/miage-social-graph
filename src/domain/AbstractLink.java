package domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public abstract class AbstractLink {

    private String type;
    private Map<String, AttributeValues> attributes;

    public AbstractLink() {
        attributes = new HashMap<>();
    }

    public AbstractLink(String type) {
        this();
        this.type = type;
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

    public Map<String, AttributeValues> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, AttributeValues> attributes) {
        this.attributes = attributes;
    }

    @Override
    public abstract String toString();

    @Override
    public abstract int hashCode();

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass().getSuperclass() != obj.getClass().getSuperclass()) {
            return false;
        }
        final AbstractLink other = (AbstractLink) obj;
        if (!getType().equals(other.getType())) {
            return false;
        }
        if (!Objects.equals(getAttributes(), other.getAttributes())) {
            return false;
        }
        return true;
    }
}
