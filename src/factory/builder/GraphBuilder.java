package factory.builder;

import domain.AttributeValues;
import domain.Graph;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Abstact class GraphBuilder
 */
public abstract class GraphBuilder {

    protected Graph workingGraph;

    protected GraphBuilder(Graph workingGraph) {
        this.workingGraph = workingGraph;
    }

    /**
     * Put line data into the working graph
     *
     * @param line the line to analyze
     * @throws GraphBuildingException
     */
    public abstract void putLineData(String line) throws GraphBuildingException;

    /**
     * Gets the line data
     *
     * @param line the line to analyze
     * @return an instance of <code>Map</code> wich contains the data of the
     * line
     */
    protected Map<String, Object> getLineData(String line) {
        line = line.toLowerCase();
        int fromIndex = line.indexOf("--") - 1;
        int toIndex = line.indexOf("-->") + 4;
        String fromStr = line.substring(0, fromIndex);
        String toStr = line.substring(toIndex);
        line = line.substring(fromIndex + 3, toIndex);
        String linkType = line.substring(0, line.indexOf("["));
        String attributesLine = line.substring(line.indexOf("[") + 1, line.lastIndexOf("]"));
        // Gets attributes values
        String regex = "((\\w+=(\\[((\\w+)|\\|)+\\]|\\w+)))";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(attributesLine);
        String attribute, key;
        AttributeValues values;
        Map<String, AttributeValues> attributes = new HashMap<>();
        while (matcher.find()) {
            // attribute : "since=1999" or "share=[book|movie]"
            attribute = attributesLine.substring(matcher.start(), matcher.end());
            // key = "since"
            key = attribute.substring(0, attribute.indexOf("="));
            if (attribute.contains("[")) {
                //"[book|movie]"
                values = new AttributeValues(
                        Arrays.asList(
                        attribute.substring(attribute.indexOf("[") + 1, attribute.length() - 1).split("\\|")));
            } else {
                //"1999"
                values = new AttributeValues();
                values.addValue(attribute.substring(attribute.indexOf("=") + 1));
            }
            attributes.put(key, values);
        }
        // Fills the returned map
        Map<String, Object> lineData = new HashMap<>();
        lineData.put("from", fromStr);
        lineData.put("to", toStr);
        lineData.put("linkType", linkType);
        lineData.put("attributes", attributes);
        return lineData;
    }
}
