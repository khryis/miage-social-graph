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
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import search.IGraphParser.Unicity;
import search.SearchException;
import search.SearchMethod;
import search.SearchResult;

public class Interface extends JPanel implements ActionListener {

    private static final String newline = "\n";
    private JButton importButton, exportButton, searchButton, showGraph, clear;
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
        clear = new JButton("Vider affichage");
        clear.addActionListener(this);
        exportButton = new JButton("Exporter un fichier");
        exportButton.addActionListener(this);
        exportButton.setEnabled(false);
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(importButton);
        buttonPanel.add(showGraph);
        buttonPanel.add(searchButton);
        buttonPanel.add(clear);
        buttonPanel.add(exportButton);
        add(buttonPanel, BorderLayout.PAGE_START);
        add(logScrollPane, BorderLayout.CENTER);
    }

    @Override
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
        } else if (e.getSource() == clear) {
            log.setText("");
        } else if (e.getSource() == searchButton) {
            SearchDialogInfo zInfo = (new SearchDialog(null, "Rechercher", true, g)).showZDialog();
            log.append("Search: \n" + zInfo.toString() + newline);

            //Get search parameters
            String startNode = zInfo.getStartNode();
            List<LinkFilter> filters = zInfo.getFilters();
            String searchMethod = zInfo.getSearchMethod();
            String unicity = zInfo.getUnicity();
            int searchLevel = zInfo.getSearchLevel();
            //Call of the search method
            try {
                SearchResult result;
                if (searchMethod.equals(SearchMethod.DFS.toString())) {
                    if (unicity.equals(Unicity.GLOBALNODE.toString())) {
                        if (searchLevel == 0) {
                            result = g.parser.search(startNode, filters, SearchMethod.DFS, Unicity.GLOBALNODE);
                        } else {
                            result = g.parser.search(startNode, filters, SearchMethod.DFS, searchLevel, Unicity.GLOBALNODE);
                        }
                    } else if (unicity.equals(Unicity.GLOBALRELATION.toString())) {
                        if (searchLevel == 0) {
                            result = g.parser.search(startNode, filters, SearchMethod.DFS, Unicity.GLOBALRELATION);
                        } else {
                            result = g.parser.search(startNode, filters, SearchMethod.DFS, searchLevel, Unicity.GLOBALRELATION);
                        }
                    } else {
                        if (searchLevel == 0) {
                            result = g.parser.search(startNode, filters, SearchMethod.DFS);
                        } else {
                            result = g.parser.search(startNode, filters, SearchMethod.DFS, searchLevel);
                        }
                    }
                } else if (searchMethod.equals(SearchMethod.BFS.toString())) {
                    if (unicity.equals(Unicity.GLOBALNODE.toString())) {
                        if (searchLevel == 0) {
                            result = g.parser.search(startNode, filters, SearchMethod.BFS, Unicity.GLOBALNODE);
                        } else {
                            result = g.parser.search(startNode, filters, SearchMethod.BFS, searchLevel, Unicity.GLOBALNODE);
                        }
                    } else if (unicity.equals(Unicity.GLOBALRELATION.toString())) {
                        if (searchLevel == 0) {
                            result = g.parser.search(startNode, filters, SearchMethod.BFS, Unicity.GLOBALRELATION);
                        } else {
                            result = g.parser.search(startNode, filters, SearchMethod.BFS, searchLevel, Unicity.GLOBALRELATION);
                        }
                    } else {
                        if (searchLevel == 0) {
                            result = g.parser.search(startNode, filters, SearchMethod.BFS);
                        } else {
                            result = g.parser.search(startNode, filters, SearchMethod.BFS, searchLevel);
                        }
                    }
                } else {
                    if (unicity.equals(Unicity.GLOBALNODE.toString())) {
                        if (searchLevel == 0) {
                            result = g.parser.search(startNode, filters, Unicity.GLOBALNODE);
                        } else {
                            result = g.parser.search(startNode, filters, searchLevel, Unicity.GLOBALNODE);
                        }
                    } else if (unicity.equals(Unicity.GLOBALRELATION.toString())) {
                        if (searchLevel == 0) {
                            result = g.parser.search(startNode, filters, Unicity.GLOBALRELATION);
                        } else {
                            result = g.parser.search(startNode, filters, searchLevel, Unicity.GLOBALRELATION);
                        }
                    } else {
                        if (searchLevel == 0) {
                            result = g.parser.search(startNode, filters);
                        } else {
                            result = g.parser.search(startNode, filters, searchLevel);
                        }
                    }
                }
                log.append(result.toString() + newline);
            } catch (SearchException ex) {
                log.append("Une erreur est survenue pendant la recherche" + newline);
            }

        } else if (e.getSource() == exportButton) {
        }
    }
}
