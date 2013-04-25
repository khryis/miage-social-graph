package run;

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
        return links.split("[ \n]");
    }

    @Override
    public String toString() {
        String str = "     Noeud de départ: " + startNode + "\n";
        str += "     Méthode de recherche: " + searchMethod + "\n";
        str += "     Niveau de recherche: " + searchLevel + "\n";
        str += "     Unicité: " + unicity + "\n";
        str += "     Liens: ";
        String[] tabLinks = links.split("[ \n]");
        for (String link : tabLinks) {
            if (!link.isEmpty()) {
                str += link + ", ";
            }
        }
        return str;
    }
}
