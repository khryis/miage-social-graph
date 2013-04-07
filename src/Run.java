
import domain.Graph;
import factory.GraphBuildingException;
import factory.GraphBuildingMethod;
import factory.GraphFactory;
import factory.GraphFileParserException;
import factory.IGraphFactory;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import search.GraphParser;

public class Run {

    static BufferedReader buffer = new BufferedReader(new InputStreamReader(System.in));

    public static int nextIntNotBlank(String notBlank, String exception, String message) {
        boolean correct = false;
        int valeur = 0;
        String line;
        while (!correct) {
            try {
                System.out.println(message);
                line = buffer.readLine();
                while (line.isEmpty()) {
                    System.out.println(notBlank);
                    line = buffer.readLine();
                }
                valeur = Integer.valueOf(line);
                if (valeur < 0) {
                    throw new NumberFormatException();
                }
                // Si on ne lance pas l'exception on va forcément renvoyer true, tout s'est bien déroulé
                correct = true;
            } catch (NumberFormatException numE) {
                correct = false;
                valeur = 0;
                System.out.println(exception);
            } catch (IOException ex) {
                Logger.getLogger(Run.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return valeur;
    }

    public static int nextIntRangedNotBlank(int min, int max, String notBlank, String exception, String message) {
        int valeur = -1;
        boolean correct = false;
        String line;
        while (!correct) {
            try {
                System.out.println(message);
                line = buffer.readLine();
                if (!line.isEmpty()) {
                    valeur = Integer.valueOf(line);
                    if ((valeur >= min) && (valeur <= max)) {
                        correct = true;
                    } else {
                        correct = false;
                        System.out.println("La valeur doit-être comprise entre " + min + " et " + max);
                    }
                } else {
                    valeur = -1;
                    correct = false;
                    System.out.println(notBlank);
                }
            } catch (NumberFormatException numE) {
                correct = false;
                System.out.println(exception);
            } catch (IOException ex) {
                Logger.getLogger(Run.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return valeur;
    }

    public static String nextLineNotBlank() {
        String line = "";
        try {
            line = buffer.readLine();
            while (line.isEmpty()) {
                System.out.println("Ecrivez quelque chose !");
                line = buffer.readLine();
            }
        } catch (IOException ex) {
            Logger.getLogger(Run.class.getName()).log(Level.SEVERE, null, ex);
        }

        return line;
    }

    public static void main(String[] args) {
        String filePath = "testfiles/WellFormedFileWithAttr.txt";
        if (args.length != 0) {
            filePath = args[0];
        }
        IGraphFactory factory = new GraphFactory();
        Graph g = factory.getGraph();

        String message = "", strFilePath = "", strStartNode = "", strSearchMethod = "", strFilter = "", strLevel = "", strUnicity = "";
        int choice = 0;
        List<String> listFilter = new ArrayList<String>();
        boolean quit = false;

        System.out.println("Ecrire le nom du fichier à parser (ne pas oublier l'extension) : ");
        strFilePath = nextLineNotBlank();

        try {
            g = factory.getGraph(new File("testfiles/" + strFilePath), GraphBuildingMethod.STRICT);
            System.out.println(g);
        } catch (GraphFileParserException | GraphBuildingException | IOException ex) {
            Logger.getLogger(Run.class.getName()).log(Level.SEVERE, null, ex);
        }

        while (!quit) {
            System.out.println("--------- Voici le graph -----------");
            System.out.println(g);
            System.out.println("------------------------------------");

            System.out.println("Nouvelle recherche...");
            System.out.println("Choississez votre noeud de départ pour la recherche : ");
            strStartNode = nextLineNotBlank();

            message = "Choisissez un type de parcours : \n"
                    + "1 - Profondeur\n"
                    + "2 - Largeur\n";
            choice = nextIntRangedNotBlank(1, 2, "Faite un choix ! ", "Erreur de saisie", message);
            if (choice == 1) {
                List<String> filters = new ArrayList<>();
                boolean endFilterChoice = false;
                while (!endFilterChoice) {
                    System.out.println("Donner un nom de type de lien pour filtrer les noeuds\n"
                            + "(stop pour arrêter d'ajouter des filtres)");
                    strFilter = nextLineNotBlank();
                    if (strFilter.compareToIgnoreCase("stop") == 0) {
                        endFilterChoice = true;
                    } else {
                        filters.add(strFilter);
                    }
                }
                GraphParser parser = new GraphParser(g);
                /*try {
                 SearchResult result = parser.search(strStartNode, filters);
                 System.out.println("--------- Voici le resultat -----------");
                 System.out.println(result);
                 System.out.println("---------------------------------------");
                 // Supposed result : carol, dawn, elizabeth, anna
                 } catch (SearchException ex) {
                 Logger.getLogger(Run.class.getName()).log(Level.SEVERE, null, ex);
                 }*/
            } else if (choice == 2) {
                System.out.println("Not implemented");
            }
        }
    }
}
