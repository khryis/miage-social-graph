package search;

import java.util.List;

/**
 * Interface IGraphParser
 */
public interface IGraphParser {

    /**
     * Default search which uses the Depth First Search method
     *
     * @param startingNode the id of the starting node
     * @param linkFilters the filters
     * @return an instance of <code>SearchResult</code>
     * @throws SearchException
     */
    public SearchResult search(String startingNode, List<String> linkFilters) throws SearchException;

    /**
     * Search with the givent search method
     *
     * @param startingNode the id of the starting node
     * @param linkFilters the filters
     * @param searchMethod the search method
     * @return an instance of <code>SearchResult</code>
     * @throws SearchException
     */
    public SearchResult search(String startingNode, List<String> linkFilters, SearchMethod searchMethod) throws SearchException;
}
