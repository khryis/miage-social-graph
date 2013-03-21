package domain;

import java.util.ArrayList;
import java.util.HashMap;

public interface INode {

    public String getId();

    public HashMap<String, ArrayList<Link>> getLinks();

    public void addLink(Link link);

    @Override
    public String toString();
}
