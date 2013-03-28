package domain;

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
}
