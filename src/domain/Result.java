package domain;

import java.util.ArrayList;

/**
 *
 * @author Cach
 */
public class Result {

    private ArrayList<Node> resultList;

    public Result() {
        this.resultList = new ArrayList();
    }

    public Result(ArrayList<Node> nodes) {
        resultList = nodes;
    }

    public void add(Node n) {
        resultList.add(n);
    }

    public String display() {
        String display = "Resultat : ";
        if (resultList.isEmpty()) {
            display += "Aucun noeud trouvé.";
        } else {
            display += resultList.get(0).getId();
            for (int i = 1; i < resultList.size(); i++) {
                display += ", " + resultList.get(i).getId();
            }
        }
        return display;
    }
}
