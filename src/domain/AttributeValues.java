package domain;

import java.util.ArrayList;
import java.util.Objects;

public class AttributeValues {

    private ArrayList<String> values;

    public AttributeValues() {
        values = new ArrayList<>();
    }

    public AttributeValues add(String value) {
        values.add(value);
        return this;
    }

    public AttributeValues add(String[] tabValues) {
        for (int i = 0; i < tabValues.length; i++) {
            values.add(tabValues[i]);
        }
        return this;
    }

    @Override
    public String toString() {
        //L'attribut est minimum de type : "since = 1999"
        String display = values.get(0);
        //Si l'attribut a plusieurs valeurs, par exemple : "allergic to = lactose, kiwi"
        for (int i = 1; i < values.size(); i++) {
            display += ", " + values.get(i);
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
        final Attributes other = (Attributes) obj;
        if (!Objects.equals(this.values, other.values)) {
            return false;
        }
        return true;
    }
}
