package run;

import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;

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
        JFrame frame = new JFrame("SocialGraph");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(800, 450));
        frame.add(new Interface());
        frame.pack();
        frame.setVisible(true);
    }
}
