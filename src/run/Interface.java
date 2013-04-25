package run;

import domain.Graph;
import domain.LinkFilter;
import domain.LinkFilter.Direction;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import search.GraphParser;
import search.IGraphParser.Unicity;
import search.SearchException;
import search.SearchMethod;
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
        } else if (e.getSource() == searchButton) {
            SearchDialog zd = new SearchDialog(null, "Rechercher", true, g);
            SearchDialogInfo zInfo = zd.showZDialog();
            log.append("Search: \n" + zInfo.toString() + newline);
            String startNode = zInfo.getStartNode();
            String searchMethod = zInfo.getSearchMethod();
            int searchLevel = zInfo.getSearchLevel();
            String unicityStr = zInfo.getUnicity();
            Unicity unicity = null;
            if (unicityStr.equals("GLOBAL NODE")) {
                unicity = Unicity.GLOBALNODE;
            } else {
                unicity = Unicity.GLOBALRELATION;
            }
            String[] links = zInfo.getLinks();
            List<LinkFilter> filters = new ArrayList<>();
            for (String link : links) {
                if (!link.isEmpty()) {
                    LinkFilter linkFilter;
                    String linkType;
                    Map<String, List<String>> attributes = null;
                    int direction = 0;
                    // Has attributes on links
                    if (link.contains("[")) {
                        linkType = link.substring(0, link.indexOf("["));
                        String attributesLine = link.substring(link.indexOf("[") + 1, link.lastIndexOf("]"));
                        // Gets attributes values
                        String regex = "((\\w+=(\\[((\\w+)|\\|)+\\]|\\w+)))";
                        Pattern pattern = Pattern.compile(regex);
                        Matcher matcher = pattern.matcher(attributesLine);
                        String attribute, key;
                        List<String> values = new ArrayList<>();
                        attributes = new HashMap<>();
                        while (matcher.find()) {
                            // attribute : "since=1999" or "share=[book|movie]"
                            attribute = attributesLine.substring(matcher.start(), matcher.end());
                            // key = "since"
                            key = attribute.substring(0, attribute.indexOf("="));
                            if (attribute.contains("[")) {
                                //"[book|movie]"
                                //values = Arrays.asList(attribute.substring(attribute.indexOf("[") + 1, attribute.length() - 1).split("\\|"))
                                String tmp = attribute.substring(attribute.indexOf("[") + 1, attribute.length() - 1);
                                String[] tabTmp = tmp.split("|");
                                for (String att : tabTmp) {
                                    values.add(att);
                                }
                            } else {
                                //"1999"
                                values.add(attribute.substring(attribute.indexOf("=") + 1));
                            }
                            attributes.put(key, values);
                        }
                        if (link.contains(">") && link.contains(">")) {
                            direction = 3;
                        } else if (link.contains(">")) {
                            direction = 2;
                        } else if (link.contains("<")) {
                            direction = 1;

                        }
                    } else {
                        if (link.contains(">") && link.contains(">")) {
                            linkType = link.substring(0, link.indexOf(">"));
                            direction = 3;
                        } else if (link.contains(">")) {
                            linkType = link.substring(0, link.indexOf(">"));
                            direction = 2;
                        } else if (link.contains("<")) {
                            linkType = link.substring(0, link.indexOf("<"));
                            direction = 1;
                        } else {
                            linkType = link;
                        }
                    }
                    switch (direction) {
                        case 1:
                            linkFilter = new LinkFilter(linkType, Direction.IN);
                            break;
                        case 2:
                            linkFilter = new LinkFilter(linkType, Direction.OUT);
                            break;
                        case 3:
                            linkFilter = new LinkFilter(linkType, Direction.BOTH);
                            break;
                        default:
                            linkFilter = new LinkFilter(linkType);
                    }

                    if (attributes != null) {
                        for (String key : attributes.keySet()) {
                            linkFilter.addAttribute(key, attributes.get(key));
                        }
                    }

                    filters.add(linkFilter);
                }

            }
            GraphParser parser = new GraphParser(g);
            switch (searchMethod) {
                case "DFS":
                    try {
                        SearchResult result;
                        if (searchLevel > 0) {
                            result = parser.search(startNode, filters, SearchMethod.DFS, searchLevel, unicity);
                        } else {
                            result = parser.search(startNode, filters, SearchMethod.DFS, unicity);
                        }
                        log.append(result.toString() + newline);
                    } catch (SearchException ex) {
                        Logger.getLogger(Run.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    break;
                case "BFS":
                    try {
                        SearchResult result;
                        if (searchLevel > 0) {
                            result = parser.search(startNode, filters, SearchMethod.DFS, searchLevel, unicity);
                        } else {
                            result = parser.search(startNode, filters, SearchMethod.DFS, unicity);
                        }
                        log.append(result.toString() + newline);
                    } catch (SearchException ex) {
                        Logger.getLogger(Run.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    break;
            }
        } else if (e.getSource()
                == exportButton) {
        }
    }
}
