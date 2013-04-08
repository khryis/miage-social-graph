package search;

/**
 * Enum to help the graph parser to know what kind of search to do
 */
public enum SearchMethod {

    DFS("Depth-first search (default)", "DFS"),
    BFS("Breadth-first search", "BFS");
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
