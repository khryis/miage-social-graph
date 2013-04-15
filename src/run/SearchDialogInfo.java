package run;

import domain.Node;

public class SearchDialogInfo {

    private Object node;
    private String searchMethod;
    private String links;

    public SearchDialogInfo() {
    }

    public SearchDialogInfo(Object node, String searchMethod, String links) {
        this.node = node;
        this.searchMethod = searchMethod;
        this.links = links;
    }

    public String toString() {
        String str = "toString";

        return str;
    }
}
