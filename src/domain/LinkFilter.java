package domain;

public class LinkFilter extends AbstractLink {

    private Node.IsSource isSource;

    public LinkFilter() {
        super();
    }

    public LinkFilter(String type, Node.IsSource isSource) {
        super(type);
        this.isSource = isSource;
    }

    public Node.IsSource getIsSource() {
        return isSource;
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
