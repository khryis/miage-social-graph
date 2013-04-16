package run;

public class SearchDialogInfo {

    private String startNode;
    private String searchMethod;
    private String links;

    public SearchDialogInfo() {
    }

    public SearchDialogInfo(String startNode, String searchMethod, String links) {
        this.startNode = startNode;
        this.searchMethod = searchMethod;
        this.links = links;
    }

    public String getStartNode() {
        return startNode;
    }

    public String getSearchMethod() {
        return searchMethod;
    }

    public String[] getLinks() {
        return links.split(";");
    }

    public String toString() {
        String str = "     Noeud de départ: " + startNode + "\n";
        str += "     Méthode de recherche: " + searchMethod + "\n";
        str += "     Liens: ";
        String[] tabLinks = links.split(";");
        for (String link : tabLinks) {
            if (!link.isEmpty()) {
                str += link + ", ";
            }
        }
        return str;
    }
}
