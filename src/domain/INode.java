package domain;

public interface INode {

    public String getId();

    public void addLink(Link link);

    @Override
    public String toString();
}
