package factory.parser;

/**
 * Exception GraphFileParserException which it throws when there is a parsing
 * error
 */
public class GraphFileParserException extends Exception {

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
