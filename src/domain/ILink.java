package domain;

public interface ILink {

    public String getType();

    public Node getFrom();

    public Node getTo();

    /**
     *
     * @return
     */
    @Override
    public String toString();
}
