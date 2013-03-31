package factory.builder;

/**
 *
 * @author Julien Neuhart
 */
public class GraphBuildingException extends Exception {

    /**
     * Creates a new instance of
     * <code>GraphBuildingException</code> without detail message.
     */
    public GraphBuildingException() {
    }

    /**
     * Constructs an instance of
     * <code>GraphBuildingException</code> with the specified detail message.
     *
     * @param msg the detail message.
     */
    public GraphBuildingException(String msg) {
        super(msg);
    }
}
