package search;

/**
 * Exception SearchException which it throws when there is a search error
 */
public class SearchException extends Exception {

    /**
     * Creates a new instance of
     * <code>SearchException</code> without detail message.
     */
    public SearchException() {
    }

    /**
     * Constructs an instance of
     * <code>SearchException</code> with the specified detail message.
     *
     * @param msg the detail message.
     */
    public SearchException(String msg) {
        super(msg);
    }
}
