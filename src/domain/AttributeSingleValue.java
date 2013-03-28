package domain;

import java.util.Objects;

public class AttributeSingleValue implements IAttributeValue<String> {

    private String value;

    public AttributeSingleValue(String value) {
        this.value = value;
    }

    /**
     * Utility methods
     */
    @Override
    public String getValue() {
        return value;
    }

    @Override
    public IAttributeValue update(String value) {
        this.value = value;
        return this;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public int hashCode() {
        int hash = 5;
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
        final AttributeSingleValue other = (AttributeSingleValue) obj;
        if (!Objects.equals(this.value, other.value)) {
            return false;
        }
        return true;
    }
}
