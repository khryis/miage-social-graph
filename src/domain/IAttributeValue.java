package domain;

public interface IAttributeValue<V> {

    /**
     * Gets the value(s) of the attribute
     *
     * @return the value(s)
     */
    public V getValue();

    /**
     * Updates the value(s) of the attribute
     *
     * @param value the new value(s)
     * @return a IAttributeValue
     */
    public IAttributeValue update(V value);
}
