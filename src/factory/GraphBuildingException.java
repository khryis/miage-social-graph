package factory;

public class GraphBuildingException extends Exception {

    private static final long serialVersionUID = 1L;

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
