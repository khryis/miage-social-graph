package domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Link {

    private String type;
    private Node from;
    private Node to;
    private HashMap<String, IAttributeValue> attributes;

    private Link() {
        attributes = new HashMap<>();
    }

    public Link(String type, Node from, Node to) {
        this();
        this.type = type;
        this.from = from;
        this.to = to;
    }

    /**
     * <p>Take a line of attributes like :
     * "since=1999,share=[book|movies|tweets]"</p>
     * <p>Extract in line keys and corresponding values to put into the
     * attributes Structure</p>
     *
     * @param attributesLine
     */
    public void addAttributes(String attributesLine) {
        String attribute, key;
        IAttributeValue value;

        String motif = "((\\w+=(\\[((\\w+)|\\|)+\\]|\\w+)))";
        Pattern p = Pattern.compile(motif);
        Matcher m = p.matcher(attributesLine);

        while (m.find()) {
            // attribute : "since=1999" or "share=[book|movie]"
            attribute = attributesLine.substring(m.start(), m.end());
            // key = "since"
            key = attribute.substring(0, attribute.indexOf("="));
            if (attribute.contains("[")) {
                //"[book|movie]"
                value = new AttributeMultipleValues(
                        Arrays.asList(
                        attribute.substring(attribute.indexOf("[") + 1, attribute.length() - 1).split("\\|")));
            } else {
                //"1999"
                value = new AttributeSingleValue(attribute.substring(attribute.indexOf("=") + 1));
            }
            attributes.put(key, value);
        }
    }

    /**
     * Updates the current link with the information of the attributes in
     * parameters
     *
     * @param attributes the attributes
     */
    public void update(HashMap<String, IAttributeValue> attributes) {
        Set<String> attributesName = attributes.keySet();
        Iterator iterator = attributesName.iterator();
        while (iterator.hasNext()) {
            String attributeName = (String) iterator.next();
            IAttributeValue tmpAttributeValue = attributes.get(attributeName);
            IAttributeValue attributeValue = this.attributes.get(attributeName);
            // if the current link has not this attribute
            if (attributeValue == null) {
                this.attributes.put(attributeName, tmpAttributeValue);
            } else {
                // if the attributes are instance of the same class
                if (tmpAttributeValue.getClass().equals(attributeValue.getClass())) {
                    attributeValue.update(tmpAttributeValue.getValue());
                    // if tmpAttributeValue like share=[books, movies] and attributeValue like share=tweets
                } else if (tmpAttributeValue instanceof AttributeMultipleValues) {
                    this.attributes.remove(attributeName);
                    AttributeMultipleValues tmpMultipleValues = (AttributeMultipleValues) tmpAttributeValue;
                    List<String> value = new ArrayList<>();
                    value.add((String) attributeValue.getValue());
                    tmpMultipleValues.update(value);
                    this.attributes.put(attributeName, tmpMultipleValues);
                    //if tmpAttributeValue like share=movies and attributeValue like share=[movies,tweets]
                } else {
                    List<String> value = new ArrayList<>();
                    value.add((String) tmpAttributeValue.getValue());
                    attributeValue.update(value);
                }
            }
        }
    }

    /**
     * Utility methods
     */
    public String getType() {
        return type;
    }

    public Node getFrom() {
        return from;
    }

    public Node getTo() {
        return to;
    }

    public HashMap<String, IAttributeValue> getAttributes() {
        return attributes;
    }

    @Override
    public String toString() {
        String display = "";

        display += "Source: " + this.from.getId();
        display += " | To: " + this.to.getId();
        // For each attribute, prints his name and his value(s)
        for (Entry<String, IAttributeValue> attribute : attributes.entrySet()) {
            display += " | " + attribute.getKey() + " = ";
            IAttributeValue value = attribute.getValue();
            display += value.toString();
        }

        return display;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Link other = (Link) obj;
        if (!Objects.equals(this.type, other.type)) {
            return false;
        }
        if (!Objects.equals(this.from.getId(), other.from.getId())) {
            return false;
        }
        if (!Objects.equals(this.to.getId(), other.to.getId())) {
            return false;
        }
        //TODO: test if to object with same content (content objects redefine equals) are equals


        if (!Objects.equals(this.attributes, other.attributes)) {
            return false;
        }
        return true;
    }
}
