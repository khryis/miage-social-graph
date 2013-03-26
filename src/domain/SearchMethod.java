package domain;

public enum SearchMethod {

    DFS("Depth-first search (default)"),
    BFS("Breadth-first search");
    String description;

    SearchMethod(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return description;
    }
}
