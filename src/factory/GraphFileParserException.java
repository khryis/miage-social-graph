package factory;

/**
 * Exception GraphFileParserException which it throws when there is a parsing
 * error
 */
public class GraphFileParserException extends Exception {

    private static final long serialVersionUID = 1L;

    /**
     * Creates a new instance of
     * <code>GraphFileParserException</code> without detail message.
     */
    public GraphFileParserException() {
    }

    /**
     * Constructs an instance of
     * <code>GraphFileParserException</code> with the specified detail message.
     *
     * @param msg the detail message.
     */
    public GraphFileParserException(String msg) {
        super(msg);
    }
}
