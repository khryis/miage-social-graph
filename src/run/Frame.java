package run;

import domain.Graph;
import factory.GraphBuildingException;
import factory.GraphBuildingMethod;
import factory.GraphFactory;
import factory.GraphFileParserException;
import factory.IGraphFactory;
import java.awt.BorderLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class Frame extends JPanel implements ActionListener {

    static private final String newline = "\n";
    JButton importButton, exportButton, searchButton;
    JTextArea log;
    JFileChooser fc;
    private static File file;
    IGraphFactory factory;
    Graph g;

    public Frame() {
        super(new BorderLayout());

        log = new JTextArea(5, 20);
        log.setMargin(new Insets(5, 5, 5, 5));
        log.setEditable(false);
        JScrollPane logScrollPane = new JScrollPane(log);
        fc = new JFileChooser();
        importButton = new JButton("Importer un fichier");
        importButton.addActionListener(this);
        searchButton = new JButton("Rechercher");
        searchButton.addActionListener(this);
        searchButton.disable();
        exportButton = new JButton("Exporter un fichier");
        exportButton.addActionListener(this);
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(importButton);
        buttonPanel.add(searchButton);
        buttonPanel.add(exportButton);
        add(buttonPanel, BorderLayout.PAGE_START);
        add(logScrollPane, BorderLayout.CENTER);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == importButton) {
            int returnVal = fc.showOpenDialog(Frame.this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                file = fc.getSelectedFile();
                log.append("Opening: " + file.getName() + "." + newline);
                searchButton.enable();
            } else {
                log.append("Open command cancelled by user." + newline);
            }
            log.setCaretPosition(log.getDocument().getLength());
            File file = null;
            while (file == null) {
                file = Frame.getFile();
            }
            factory = new GraphFactory();
            try {
                g = factory.getGraph(file, GraphBuildingMethod.STRICT);
                System.out.println(g);
            } catch (GraphFileParserException | GraphBuildingException | IOException ex) {
                Logger.getLogger(Run.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else if (e.getSource() == searchButton) {
            SearchDialog zd = new SearchDialog(null, "Rechercher", true, g);
            SearchDialogInfo zInfo = zd.showZDialog();
            JOptionPane jop = new JOptionPane();
            jop.showMessageDialog(null, zInfo.toString(), "Informations personnage", JOptionPane.INFORMATION_MESSAGE);
        } else if (e.getSource() == exportButton) {
            int returnVal = fc.showSaveDialog(Frame.this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();
                // This is where a real application would save the file.
                log.append("Saving: " + file.getName() + "." + newline);
            } else {
                log.append("Save command cancelled by user." + newline);
            }
            log.setCaretPosition(log.getDocument().getLength());
        }
    }

    public static void createAndShowFrame() {
        JFrame frame = new JFrame("SocialGraph");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new Frame());
        frame.pack();
        frame.setVisible(true);
    }

    public static File getFile() {
        return file;
    }
}
