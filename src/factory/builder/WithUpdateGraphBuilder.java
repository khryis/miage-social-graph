package factory.builder;

import domain.AttributeValues;
import domain.Graph;
import domain.Link;
import domain.Node;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The "with update" extension of
 * <code>AbstractGraphBuilder</code>
 */
public class WithUpdateGraphBuilder extends AbstractGraphBuilder {

    public WithUpdateGraphBuilder(Graph workingGraph) {
        super(workingGraph);
    }

    @Override
    public void putLineData(String line) throws GraphBuildingException {
        Map<String, Object> lineData = getLineData(line);
        if (lineData.size() != 4) {
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
        Link link;
        if ((link = from.getLink((String) lineData.get("linkType"), from, to)) != null) {
            updateLink(link, (Map) lineData.get("attributes"));
            Link toLink = to.getLink((String) lineData.get("linkType"), from, to);
            toLink.setAttributes(link.getAttributes());
        } else {
            link = new Link((String) lineData.get("linkType"), from, to);
            link.setAttributes((Map) lineData.get("attributes"));
            from.addLink(link);
            to.addLink(link);
        }
    }

    /**
     * Utility methods
     */
    private void updateLink(Link link, Map<String, AttributeValues> attributes) {
        Set<String> attributesName = attributes.keySet();
        Iterator iterator = attributesName.iterator();
        while (iterator.hasNext()) {
            String attributeName = (String) iterator.next();
            AttributeValues tmpAttributeValues = attributes.get(attributeName);
            AttributeValues attributeValues = link.getAttributes().get(attributeName);
            // if the current link has not this attribute
            if (attributeValues == null) {
                link.getAttributes().put(attributeName, tmpAttributeValues);
            } else {
                // if the attributes are both like since=2008
                if (tmpAttributeValues.getValues().size() == 1 && attributeValues.getValues().size() == 1) {
                    attributeValues.getValues().set(0, tmpAttributeValues.getValues().get(0));
                    // if tmpAttributeValue like share=[books, movies] and attributeValue like share=tweets (and vice versa)
                    // or tmpAttributeValue and attributeValue both like share=[books, movies]
                } else {
                    updateAttributeValues(attributeValues, tmpAttributeValues.getValues());
                }
            }
        }
    }

    private void updateAttributeValues(AttributeValues values, List<String> newValues) {
        for (String currentValue : newValues) {
            if (!values.getValues().contains(currentValue)) {
                values.addValue(currentValue);
            }
        }
    }
}
