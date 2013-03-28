package domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AttributeMultipleValues implements IAttributeValue<List<String>> {

    private List<String> values;

    public AttributeMultipleValues() {
        values = new ArrayList<>();
    }

    public AttributeMultipleValues(List<String> values) {
        this();
        this.values.addAll(values);
    }

    public AttributeMultipleValues add(String value) {
        values.add(value);
        return this;
    }

    /**
     * Utility methods
     */
    @Override
    public List<String> getValue() {
        return values;
    }

    @Override
    public IAttributeValue update(List<String> values) {
        for (String currentValue : values) {
            if (!this.values.contains(currentValue)) {
                add(currentValue);
            }
        }
        return this;
    }

    @Override
    public String toString() {
        String display = values.get(0);
        for (int i = 1; i < values.size(); i++) {
            display += ", " + values.get(i);
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
        final AttributeMultipleValues other = (AttributeMultipleValues) obj;
        if (!Objects.equals(this.values, other.values)) {
            return false;
        }
        return true;
    }
}
