package run;

import domain.AttributeValues;
import domain.LinkFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SearchDialogInfo {

    private String startNode;
    private String searchMethod;
    private int searchLevel;
    private String unicity;
    private String links;

    public SearchDialogInfo() {
    }

    public SearchDialogInfo(String startNode, String searchMethod, int searchLevel, String unicity, String links) {
        this.startNode = startNode;
        this.searchMethod = searchMethod;
        this.searchLevel = searchLevel;
        this.unicity = unicity;
        this.links = links;
    }

    public int getSearchLevel() {
        return searchLevel;
    }

    public String getUnicity() {
        return unicity;
    }

    public String getStartNode() {
        return startNode;
    }

    public String getSearchMethod() {
        return searchMethod;
    }

    public String[] getLinks() {
        return links.split("\n");
    }

    public List<LinkFilter> getFilters() {
        String[] links = getLinks();
        List<LinkFilter> filters = new ArrayList<>();
        //Build the filters list
        for (String line : links) {
            if (!line.isEmpty()) {
                String linkType = line;
                LinkFilter.Direction linkDirection = null;
                if (line.indexOf("[") == -1) {
                    //no attributes
                    if (line.indexOf(" ") != -1) {
                        //direction is specified
                        linkType = line.substring(0, line.indexOf(" "));
                        if (line.substring(line.indexOf(" ") + 1).contains("<>")) {
                            //TODO: not supported yet
                            linkDirection = LinkFilter.Direction.BOTH;
                        } else if (line.substring(line.indexOf(" ") + 1).contains(">")) {
                            linkDirection = LinkFilter.Direction.OUT;
                        } else if (line.substring(line.indexOf(" ") + 1).contains("<")) {
                            linkDirection = LinkFilter.Direction.IN;
                        }
                    }
                    //no direction either
                } else {
                    //has attribute(s)
                    linkType = line.substring(0, line.indexOf("["));
                    if (line.indexOf(" ") != -1) {
                        //direction is specified
                        if (line.substring(line.indexOf(" ") + 1).contains("<>")) {
                            //TODO: not supported yet
                            linkDirection = LinkFilter.Direction.BOTH;
                        } else if (line.substring(line.indexOf(" ") + 1).contains(">")) {
                            linkDirection = LinkFilter.Direction.OUT;
                        } else if (line.substring(line.indexOf(" ") + 1).contains("<")) {
                            linkDirection = LinkFilter.Direction.IN;
                        }
                    }
                    //no direction
                }

                //Compute attributes
                Map<String, AttributeValues> attributes = null;
                if (line.contains("[")) {
                    String attributesLine = line.substring(line.indexOf("[") + 1, line.lastIndexOf("]"));
                    // Gets attributes values
                    Matcher matcher = Pattern.compile("((\\w+=(\\[((\\w+)|\\|)+\\]|\\w+)))").matcher(attributesLine);
                    String attribute, key;
                    AttributeValues values;
                    attributes = new HashMap<>();
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
                            values = new AttributeValues(attribute.substring(attribute.indexOf("=") + 1));
                        }
                        attributes.put(key, values);
                    }
                }

                LinkFilter filter;
                if (linkDirection == null) {
                    filter = new LinkFilter(linkType);
                } else {
                    filter = new LinkFilter(linkType, linkDirection);
                }
                if (attributes != null && !attributes.isEmpty()) {
                    filter.setAttributes(attributes);
                }

                filters.add(filter);
            }
        }

        return filters;
    }

    @Override
    public String toString() {
        String str = "\tNoeud de départ : " + startNode + "\n";
        str += "\tMéthode de recherche : " + searchMethod + "\n";
        str += "\tNiveau de recherche : " + searchLevel + "\n";
        str += "\tUnicité : " + unicity + "\n";
        str += "\tLiens : \n" + links;
        return str;
    }
}
