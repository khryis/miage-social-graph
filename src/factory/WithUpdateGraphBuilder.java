package factory;

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
 * <code>GraphBuilder</code>
 */
class WithUpdateGraphBuilder extends GraphBuilder {

    public WithUpdateGraphBuilder(Graph workingGraph) {
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
        Link link;
        Map<String, AttributeValues> attributes = (Map) lineData.get("attributes");
        String linkType = lineData.get("linkType").toString();
        if ((link = from.getLink(linkType, from, to)) != null && attributes != null) {
            updateLink(link, attributes);
            Link toLink = to.getLink(linkType, from, to);
            toLink.setAttributes(link.getAttributes());
        } else {
            link = new Link(linkType, from, to);
            if (attributes != null) {
                link.setAttributes(attributes);
            }
            from.addLink(link);
            to.addLink(link);
        }
    }

    /**
     * Utility methods
     */
    private void updateLink(Link link, Map<String, AttributeValues> attributes) {
        Set<String> attributesName = attributes.keySet();
        for (Iterator<String> it = attributesName.iterator(); it.hasNext();) {
            String attributeName = it.next();
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
