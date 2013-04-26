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
        String fromStr = lineData.get("from").toString();
        Node from = nodes.get(fromStr);
        if (from == null) {
            from = new Node(fromStr);
            workingGraph.addNode(from);
        }
        String toStr = lineData.get("to").toString();
        Node to = nodes.get(toStr);
        if (to == null) {
            to = new Node(toStr);
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
