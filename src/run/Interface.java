package run;

import domain.Graph;
import domain.LinkFilter;
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
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import search.GraphParser;
import search.SearchException;
import search.SearchResult;

public class Interface extends JPanel implements ActionListener {

    private static final String newline = "\n";
    private JButton importButton, exportButton, searchButton, showGraph;
    private JTextArea log;
    private JFileChooser fc;
    private File file;
    private IGraphFactory factory;
    private Graph g;

    public Interface() {
        super(new BorderLayout());
        log = new JTextArea(5, 20);
        log.setMargin(new Insets(5, 5, 5, 5));
        log.setEditable(false);
        JScrollPane logScrollPane = new JScrollPane(log);
        fc = new JFileChooser();
        importButton = new JButton("Importer un fichier");
        importButton.addActionListener(this);
        showGraph = new JButton("Afficher le gaphe");
        showGraph.addActionListener(this);
        showGraph.setEnabled(false);
        searchButton = new JButton("Rechercher");
        searchButton.addActionListener(this);
        searchButton.setEnabled(false);
        exportButton = new JButton("Exporter un fichier");
        exportButton.addActionListener(this);
        exportButton.setEnabled(false);
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(importButton);
        buttonPanel.add(showGraph);
        buttonPanel.add(searchButton);
        buttonPanel.add(exportButton);
        add(buttonPanel, BorderLayout.PAGE_START);
        add(logScrollPane, BorderLayout.CENTER);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == importButton) {
            int returnVal = fc.showOpenDialog(Interface.this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                file = fc.getSelectedFile();
                log.append("Opening: " + file.getName() + "." + newline);
                showGraph.setEnabled(true);
                searchButton.setEnabled(true);
            } else {
                log.append("Open command cancelled by user." + newline);
            }
            log.setCaretPosition(log.getDocument().getLength());
            factory = new GraphFactory();
            try {
                g = factory.getGraph(file, GraphBuildingMethod.STRICT);
                System.out.println(g);
            } catch (GraphFileParserException | GraphBuildingException | IOException ex) {
                Logger.getLogger(Run.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (e.getSource() == showGraph) {
            log.append("Graph: \n" + g.toString() + newline);
        } else if (e.getSource() == searchButton) {
            SearchDialog zd = new SearchDialog(null, "Rechercher", true, g);
            SearchDialogInfo zInfo = zd.showZDialog();
            log.append("Search: \n" + zInfo.toString() + newline);
            String startNode = zInfo.getStartNode();
            String searchMethod = zInfo.getSearchMethod();
            String[] links = zInfo.getLinks();
            switch (searchMethod) {
                case "Profondeur":
                    List<LinkFilter> filters = new ArrayList<>();
                    for (String link : links) {
                        if (!link.isEmpty()) {
                            filters.add(new LinkFilter(link));
                        }
                    }
                    GraphParser parser = new GraphParser(g);
                    try {
                        SearchResult result = parser.search(startNode, filters);
                        log.append(result.toString() + newline);
                    } catch (SearchException ex) {
                        Logger.getLogger(Run.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    break;
                case "Largeur":
                    System.out.println("Not implemented");
                    break;
            }
        } else if (e.getSource() == exportButton) {
        }
    }
}
