package search;

/**
 * Enum to help the graph parser to know what kind of search to do
 */
public enum SearchMethod {

    DFS("Profondeur d'abord", "DFS"), //Depth-first search (default)
    BFS("Largeur d'abord", "BFS"); //Breadth-first search
    String description;
    String shortName;

    SearchMethod(String description, String shortName) {
        this.description = description;
        this.shortName = shortName;
    }

    @Override
    public String toString() {
        return description;
    }

    public String getShortName() {
        return shortName;
    }
}
