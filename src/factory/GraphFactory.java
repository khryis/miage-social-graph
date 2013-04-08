package factory;

import domain.Graph;
import java.io.File;
import java.io.IOException;

/**
 * Class GraphFactory
 */
public class GraphFactory implements IGraphFactory {

    public GraphFactory() {
        super();
    }

    @Override
    public Graph getGraph() {
        return new Graph();
    }

    @Override
    public Graph getGraph(File file)
            throws GraphFileParserException, GraphBuildingException, IOException {
        return getGraph(file, new Graph(), GraphBuildingMethod.STRICT);
    }

    @Override
    public Graph getGraph(File file, Graph graph)
            throws GraphFileParserException, GraphBuildingException, IOException {
        return getGraph(file, graph, GraphBuildingMethod.WITH_UPDATE);
    }

    @Override
    public Graph getGraph(File file, GraphBuildingMethod buildingMethod)
            throws GraphFileParserException, GraphBuildingException, IOException {
        return getGraph(file, new Graph(), buildingMethod);
    }

    private Graph getGraph(File file, Graph graph, GraphBuildingMethod buildingMethod)
            throws GraphFileParserException, GraphBuildingException, IOException {
        GraphFileParser parser = new GraphFileParser(file);
        GraphBuilder builder = initBuilder(graph, buildingMethod);
        String currentLine;
        while ((currentLine = parser.readNextLine()) != null) {
            builder.putLineData(currentLine);
        }
        parser.close();
        return graph;
    }

    /**
     * Initializes an extension of
     * <code>GraphBuilder</code>
     *
     * @param workingGraph the graph to work on
     * @param buildingMethod the building method
     * @return an extension of <code>GraphBuilder</code>
     */
    private GraphBuilder initBuilder(Graph workingGraph, GraphBuildingMethod buildingMethod) {
        switch (buildingMethod) {
            case WITH_UPDATE:
                return new WithUpdateGraphBuilder(workingGraph);
            default:
                return new StrictGraphBuilder(workingGraph);
        }
    }
}
