package factory;

import domain.Graph;
import factory.builder.AbstractGraphBuilder;
import factory.builder.DefaultGraphBuilder;
import factory.builder.GraphBuildingException;
import factory.builder.GraphBuildingMethod;
import factory.builder.WithUpdateGraphBuilder;
import factory.parser.GraphFileParser;
import factory.parser.GraphFileParserException;
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
        return getGraph(file, GraphBuildingMethod.DEFAULT);
    }

    @Override
    public Graph getGraph(File file, GraphBuildingMethod buildingMethod)
            throws GraphFileParserException, GraphBuildingException, IOException {
        GraphFileParser parser = new GraphFileParser(file);
        Graph graph = new Graph();
        AbstractGraphBuilder builder = initBuilder(graph, buildingMethod);
        String currentLine;
        while ((currentLine = parser.readNextLine()) != null) {
            builder.putLineData(currentLine);
        }
        parser.close();
        return graph;
    }

    /**
     * Initializes an extension of <code>AbstractGraphBuilder</code>
     * 
     * @param workingGraph the graph to work on
     * @param buildingMethod the building method
     * @return an extension of <code>AbstractGraphBuilder</code>
     */
    private AbstractGraphBuilder initBuilder(Graph workingGraph, GraphBuildingMethod buildingMethod) {
        if (buildingMethod.equals(GraphBuildingMethod.WITH_UPDATE)) {
            return new WithUpdateGraphBuilder(workingGraph);
        } else {
            return new DefaultGraphBuilder(workingGraph);
        }
    }
}
