package domain;

import java.util.Map;

public class LinkFilter extends AbstractLink {

    private Direction direction;

    public LinkFilter() {
        super();
    }

    /**
     * Default direction is BLIND
     *
     * @param type Type of link
     */
    public LinkFilter(String type) {
        this(type, Direction.BLIND);
    }

    public LinkFilter(String type, Direction direction) {
        super(type);
        this.direction = direction;
    }

    public Direction getDirection() {
        return direction;
    }

    @Override
    public String toString() {
        StringBuilder display = new StringBuilder();
        display.append("\"").append(getType()).append("\"->").append(direction);
        // For each attribute, prints his name and his value(s)
        for (Map.Entry<String, AttributeValues> attribute : getAttributes().entrySet()) {
            display.append(" | ").append(attribute.getKey()).append(" = ");
            AttributeValues value = attribute.getValue();
            display.append(value);
        }
        return display.toString();
    }

    @Override
    public int hashCode() {
        int hash = super.hashCode();
        hash = hash * 31 + direction.toString().hashCode();
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    public enum Direction {

        OUT, //The node from which the search starts must be equivalent to the from contained in the link
        IN, //must be equivalent to the to of the link
        BOTH, //The link must exist in both directions (there is one link with from and another with to that contains the search start node)
        BLIND //The direction doesn't matter
    }
}
