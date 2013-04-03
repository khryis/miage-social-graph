package factory;

import domain.Graph;
import java.io.File;
import java.io.IOException;

/**
 * Interface IGraphFactory
 */
public interface IGraphFactory {

    /**
     * Constructs an instance of
     * <code>Graph</code> which is empty
     *
     * @return an instance of <code>Graph</code>
     */
    public Graph getGraph();

    /**
     * Constructs an instance of
     * <code>Graph</code> with the information of a file and the default
     * building method
     *
     * @param file the file to parse
     * @return an instance of <code>Graph</code>
     * @throws GraphFileParserException
     * @throws GraphBuildingException
     * @throws IOException
     */
    public Graph getGraph(File file)
            throws GraphFileParserException, GraphBuildingException, IOException;

    /**
     * Constructs an instance of
     * <code>Graph</code> with the information of a file
     *
     * @param file the file to parse
     * @param buildingMethod the building method
     * @return an instance of <code>Graph</code>
     * @throws GraphFileParserException
     * @throws GraphBuildingException
     * @throws IOException
     */
    public Graph getGraph(File file, GraphBuildingMethod buildingMethod)
            throws GraphFileParserException, GraphBuildingException, IOException;
}
