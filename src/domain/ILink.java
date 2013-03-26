package domain;

public interface ILink {

    public String getType();

    /**
     * <p>Take a line of attributes like : "since=1999,share=[book|movies|tweets]"</p>
     * <p>Extract in line keys and corresponding values to put into the attributes Structure</p>
     *
     * @param attributesLine
     */
    public void addAttributes(String lineAttributes);

    /**
     *
     * @return
     */
    @Override
    public String toString();
}
