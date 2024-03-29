package domain;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/**
 * Class AttributeValues
 */
public class AttributeValues {

    private List<String> values;

    public AttributeValues() {
        values = new ArrayList<>();
    }

    public AttributeValues(String value) {
        this();
        values.add(value);
    }

    public AttributeValues(List<String> values) {
        this();
        this.values.addAll(values);
    }

    /**
     * Utility methods
     */
    public AttributeValues addValue(String value) {
        values.add(value);
        return this;
    }

    public List<String> getValues() {
        return values;
    }

    @Override
    public String toString() {
        StringBuilder display = new StringBuilder(values.get(0));
        for (int i = 1; i < values.size(); i++) {
            display.append("|").append(values.get(i));
        }
        if (values.size() > 1) {
            display.insert(0, "[").append("]");
        }
        return display.toString();
    }

    @Override
    public int hashCode() {
        int hash = 1;
        if (values.isEmpty()) {
            hash = 17;
        } else {
            for (Iterator<String> it = values.iterator(); it.hasNext();) {
                hash = hash * 31 + it.next().hashCode();
            }
        }
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
        final AttributeValues other = (AttributeValues) obj;
        if (!Objects.equals(this.values, other.getValues())) {
            return false;
        }
        return true;
    }

    public boolean isSupersetOf(AttributeValues other) {
        for (Iterator<String> it = other.getValues().iterator(); it.hasNext();) {
            if (!values.contains(it.next())) {
                return false;
            }
        }
        return true;
    }
}
