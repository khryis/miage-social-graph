package search;

import domain.LinkFilter;
import java.util.List;

/**
 * Interface IGraphParser
 */
public interface IGraphParser {

    public enum Unicity {

        GLOBALNODE("Noeud global", "GLOBAL NODE"),
        GLOBALRELATION("Relation global", "GLOBAL RELATION");
        private String description;
        private String shortName;

        Unicity(String description, String shortName) {
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

    /**
     * Default search which uses the Depth First Search method
     *
     * @param startingNode the id of the starting node
     * @param filters the filters
     * @return an instance of <code>SearchResult</code>
     * @throws SearchException
     */
    public SearchResult search(String startingNode, List<LinkFilter> filters) throws SearchException;

    public SearchResult search(String startingNode, List<LinkFilter> filters, int maxDepth) throws SearchException;

    public SearchResult search(String startingNode, List<LinkFilter> filters, Unicity unicity) throws SearchException;

    public SearchResult search(String startingNode, List<LinkFilter> filters, int maxDepth, Unicity unicity) throws SearchException;

    /**
     * Search with the given search method
     *
     * @param startingNode the id of the starting node
     * @param filters the filters
     * @param searchMethod the search method
     * @return an instance of <code>SearchResult</code>
     * @throws SearchException
     */
    public SearchResult search(String startingNode, List<LinkFilter> filters, SearchMethod searchMethod) throws SearchException;

    public SearchResult search(String startingNode, List<LinkFilter> filters, SearchMethod searchMethod, int maxDepth) throws SearchException;

    public SearchResult search(String startingNode, List<LinkFilter> filters, SearchMethod searchMethod, Unicity unicity) throws SearchException;

    public SearchResult search(String startingNode, List<LinkFilter> filters, SearchMethod searchMethod, int maxDepth, Unicity unicity) throws SearchException;
}
