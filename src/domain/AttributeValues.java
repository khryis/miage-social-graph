package domain;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/**
 * Class AttributeValues
 */
public class AttributeValues {

    private List<String> values;

    public AttributeValues() {
        values = new ArrayList<>();
    }

    public AttributeValues(List<String> values) {
        this();
        this.values.addAll(values);
    }

    /**
     * Utility methods
     */
    public AttributeValues addValue(String value) {
        values.add(value);
        return this;
    }

    public List<String> getValues() {
        return values;
    }

    @Override
    public String toString() {
        StringBuilder display = new StringBuilder(values.get(0));
        for (int i = 1; i < values.size(); i++) {
            display.append(", ").append(values.get(i));
        }
        return display.toString();
    }

    @Override
    public int hashCode() {
        int hash = 1;
        int[] primes = new int[]{2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97, 101, 103, 107, 109, 113, 127, 131, 137, 139, 149, 151, 157, 163, 167, 173, 179, 181, 191, 193, 197, 199, 211, 223, 227, 229, 233, 239, 241, 251, 257, 263, 269, 271, 277, 281, 283};
        for (Iterator<String> it = values.iterator(); it.hasNext();) {
            hash = hash * primes[(int) (Math.random() * primes.length)] + it.next().hashCode();
        }
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
        final AttributeValues other = (AttributeValues) obj;
        if (!Objects.equals(this.values, other.values)) {
            return false;
        }
        return true;
    }

    public boolean isSupersetOf(AttributeValues other) {
        for (Iterator<String> it = other.getValues().iterator(); it.hasNext();) {
            if (!values.contains(it.next())) {
                return false;
            }
        }
        return true;
    }
}
