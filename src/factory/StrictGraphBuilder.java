package factory;

import domain.AttributeValues;
import domain.Graph;
import domain.Link;
import domain.Node;
import java.util.HashMap;
import java.util.Map;

/**
 * The default extension of
 * <code>GraphBuilder</code>
 */
class StrictGraphBuilder extends GraphBuilder {

    public StrictGraphBuilder(Graph workingGraph) {
        super(workingGraph);
    }

    @Override
    public void putLineData(String line) throws GraphBuildingException {
        Map<String, Object> lineData = getLineData(line);
        if (lineData.size() != 4 && lineData.size() != 3) {
            throw new GraphBuildingException("Line has not the required data");
        }
        Map<String, Node> nodes = workingGraph.getNodes();
        Node from = nodes.get((String) lineData.get("from"));
        if (from == null) {
            from = new Node((String) lineData.get("from"));
            workingGraph.addNode(from);
        }
        Node to = nodes.get((String) lineData.get("to"));
        if (to == null) {
            to = new Node((String) lineData.get("to"));
            workingGraph.addNode(to);
        }
        Link link = new Link((String) lineData.get("linkType"), from, to);
        if (from.contains(link) || to.contains(link)) {
            throw new GraphBuildingException("The link already exists:\n" + link);
        } else if (lineData.size() == 4) {
            link.setAttributes((Map) lineData.get("attributes"));
            from.addLink(link);
            to.addLink(link);
        } else {
            link.setAttributes(new HashMap<String, AttributeValues>());
            from.addLink(link);
            to.addLink(link);
        }
    }
}
