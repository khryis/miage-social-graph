package run;

import domain.Graph;
import domain.Node;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import search.IGraphParser;
import search.SearchMethod;

public class SearchDialog extends JDialog {

    private SearchDialogInfo zInfo = new SearchDialogInfo();
    private JLabel startNodeLabel, linkLabel, link2Label, searchMethodLabel, unicityLabel, level2Label, levelLabel;
    private JRadioButton profondeur, largeur;
    private JComboBox startNode, searchMethod, unicity;
    private JTextArea link;
    private Graph graph;
    private JSpinner level;

    public SearchDialog(JFrame parent, String title, boolean modal, Graph g) {
        super(parent, title, modal);
        graph = g;
        this.setSize(550, 550);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        this.initComponent();
    }

    public SearchDialogInfo showZDialog() {
        this.setVisible(true);
        return this.zInfo;
    }

    private void initComponent() {
        //Noeud de depart
        JPanel panStartNode = new JPanel();
        panStartNode.setBackground(Color.white);
        panStartNode.setPreferredSize(new Dimension(440, 60));
        panStartNode.setBorder(BorderFactory.createTitledBorder("Noeud de depart"));
        startNode = new JComboBox();
        for (Map.Entry<String, Node> node : graph.getNodes().entrySet()) {
            startNode.addItem(node.getKey());
        }
        startNodeLabel = new JLabel("Noeud : ");
        panStartNode.add(startNodeLabel);
        panStartNode.add(startNode);


        //Type de parcours
        JPanel panSearchMethod = new JPanel();
        panSearchMethod.setBackground(Color.white);
        panSearchMethod.setBorder(BorderFactory.createTitledBorder("Type de parcours"));
        panSearchMethod.setPreferredSize(new Dimension(440, 60));
        searchMethod = new JComboBox();
        searchMethod.addItem("");
        for (SearchMethod sm : SearchMethod.values()) {
            searchMethod.addItem(sm.getShortName());
        }
        searchMethodLabel = new JLabel("Parcours : ");
        panSearchMethod.add(searchMethodLabel);
        panSearchMethod.add(searchMethod);

        //Niveau de parcours
        JPanel panSearchLevel = new JPanel();
        panSearchLevel.setBackground(Color.white);
        panSearchLevel.setPreferredSize(new Dimension(440, 60));
        panSearchLevel.setBorder(BorderFactory.createTitledBorder("Niveau de parcours"));
        levelLabel = new JLabel("Niveau : ");
        level2Label = new JLabel(" 0 pour tout le graph");
        SpinnerModel spinnerModel = new SpinnerNumberModel(0, 0, 100, 1);
        level = new JSpinner(spinnerModel);
        panSearchLevel.add(levelLabel);
        panSearchLevel.add(level);
        panSearchLevel.add(level2Label);


        //Unicité
        JPanel panUnicity = new JPanel();
        panUnicity.setBackground(Color.white);
        panUnicity.setBorder(BorderFactory.createTitledBorder("Unicité"));
        panUnicity.setPreferredSize(new Dimension(440, 60));
        unicity = new JComboBox();
        unicity.addItem("");
        for (IGraphParser.Unicity u : IGraphParser.Unicity.values()) {
            unicity.addItem(u);
        }
        unicityLabel = new JLabel("Unicité : ");
        panUnicity.add(unicityLabel);
        panUnicity.add(unicity);


        //Les liens
        JPanel panLink = new JPanel();
        panLink.setBackground(Color.white);
        panLink.setPreferredSize(new Dimension(440, 200));
        panLink.setBorder(BorderFactory.createTitledBorder("Les liens à rechercher"));
        link = new JTextArea();
        link.setPreferredSize(new Dimension(400, 150));
        JScrollPane scrollPaneLink = new JScrollPane(link, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        link.setLineWrap(true);
        panLink.add(scrollPaneLink);

        // Création du panel
        JPanel content = new JPanel();
        content.setBackground(Color.white);
        content.add(panStartNode);
        content.add(panSearchMethod);
        content.add(panSearchLevel);
        content.add(panUnicity);
        content.add(panLink);
        JPanel control = new JPanel();
        control.setBackground(Color.white);
        JButton okBouton = new JButton("OK");
        okBouton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                zInfo = new SearchDialogInfo(startNode.getSelectedItem().toString(),
                        searchMethod.getSelectedItem().toString(), Integer.parseInt(level.getValue().toString()),
                        unicity.getSelectedItem().toString(), getLinks());
                setVisible(false);
            }

            public String getLinks() {
                return link.getText();
            }
        });

        JButton cancelBouton = new JButton("Annuler");
        cancelBouton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                zInfo = null;
                setVisible(false);
            }
        });
        control.add(okBouton);
        control.add(cancelBouton);

        this.getContentPane().add(content, BorderLayout.CENTER);
        this.getContentPane().add(control, BorderLayout.SOUTH);
    }
}
