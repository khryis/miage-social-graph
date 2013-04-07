package domain;

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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int hashCode() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    public enum Direction {

        FROM, //The node from which the search starts must be equivalent to the from contained in the link
        TO, //must be equivalent to the to of the link
        BOTH, //The link must exist in both directions (there is one link with from and another with to that contains the search start node)
        BLIND //The direction doesn't matter
    }
}
