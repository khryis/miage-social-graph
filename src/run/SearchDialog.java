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
import javax.swing.ButtonGroup;
import javax.swing.JTextField;

public class SearchDialog extends JDialog {

    private SearchDialogInfo zInfo = new SearchDialogInfo();
    private JLabel startNodeLabel, linkLabel, link2Label;
    private JRadioButton profondeur, largeur;
    private JComboBox startNode;
    private JTextField link;
    private Graph graph;

    public SearchDialog(JFrame parent, String title, boolean modal, Graph g) {
        super(parent, title, modal);
        graph = g;
        this.setSize(550, 270);
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
        profondeur = new JRadioButton("Profondeur");
        profondeur.setSelected(true);
        largeur = new JRadioButton("Largeur");
        ButtonGroup bg = new ButtonGroup();
        bg.add(profondeur);
        bg.add(largeur);
        panSearchMethod.add(profondeur);
        panSearchMethod.add(largeur);

        //Les liens
        JPanel panLink = new JPanel();
        panLink.setBackground(Color.white);
        panLink.setPreferredSize(new Dimension(440, 60));
        panLink.setBorder(BorderFactory.createTitledBorder("Les liens à rechercher"));
        linkLabel = new JLabel("liens : ");
        link2Label = new JLabel(" les séparer par des ;");
        link = new JTextField("");
        link.setPreferredSize(new Dimension(190, 25));
        panLink.add(linkLabel);
        panLink.add(link);
        panLink.add(link2Label);

        // Création du panel
        JPanel content = new JPanel();
        content.setBackground(Color.white);
        content.add(panStartNode);
        content.add(panSearchMethod);
        content.add(panLink);
        JPanel control = new JPanel();
        JButton okBouton = new JButton("OK");

        okBouton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                zInfo = new SearchDialogInfo((String) startNode.getSelectedItem(), getSearchMethod(), getLinks());
                setVisible(false);
            }

            public String getSearchMethod() {
                return (profondeur.isSelected()) ? profondeur.getText()
                        : (largeur.isSelected()) ? largeur.getText()
                        : profondeur.getText();
            }

            public String getLinks() {
                return link.getText();
            }
        });

        JButton cancelBouton = new JButton("Annuler");
        cancelBouton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                setVisible(false);
            }
        });
        control.add(okBouton);
        control.add(cancelBouton);

        this.getContentPane().add(content, BorderLayout.CENTER);
        this.getContentPane().add(control, BorderLayout.SOUTH);
    }
}
