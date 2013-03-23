package domain;

import java.util.ArrayList;
import java.util.HashMap;

public interface INode {

    public String getId();

    public HashMap<String, ArrayList<Link>> getLinks();

    public ArrayList<Link> getTypeLinkArrayList(String type);

    /**
     *
     * @param linkFilter
     * @return
     */
    public ArrayList<Node> getLinkedNodes(ArrayList<String> linkFilter);

    public void addLink(Link link);

    @Override
    public String toString();
}
