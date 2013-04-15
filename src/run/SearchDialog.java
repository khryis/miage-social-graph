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
import javax.swing.ImageIcon;
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
    private boolean sendData;
    private JLabel nomLabel, startNodeLabel, cheveuxLabel, ageLabel, tailleLabel, taille2Label, icon;
    private JRadioButton tranche1, tranche2, tranche3, tranche4;
    private JComboBox startNode, cheveux;
    private JTextField nom, taille;
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
        this.sendData = false;
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
        JPanel panAge = new JPanel();
        panAge.setBackground(Color.white);
        panAge.setBorder(BorderFactory.createTitledBorder("Type de parcours"));
        panAge.setPreferredSize(new Dimension(440, 60));
        tranche1 = new JRadioButton("Profondeur");
        tranche1.setSelected(true);
        tranche2 = new JRadioButton("Largeur");
        ButtonGroup bg = new ButtonGroup();
        bg.add(tranche1);
        bg.add(tranche2);
        panAge.add(tranche1);
        panAge.add(tranche2);

        //La taille
        JPanel panTaille = new JPanel();
        panTaille.setBackground(Color.white);
        panTaille.setPreferredSize(new Dimension(220, 60));
        panTaille.setBorder(BorderFactory.createTitledBorder("Taille du personnage"));
        tailleLabel = new JLabel("Taille : ");
        taille2Label = new JLabel(" cm");
        taille = new JTextField("180");
        taille.setPreferredSize(new Dimension(90, 25));
        panTaille.add(tailleLabel);
        panTaille.add(taille);
        panTaille.add(taille2Label);

        //La couleur des cheveux
        JPanel panCheveux = new JPanel();
        panCheveux.setBackground(Color.white);
        panCheveux.setPreferredSize(new Dimension(220, 60));
        panCheveux.setBorder(BorderFactory.createTitledBorder("Couleur de cheveux du personnage"));
        cheveux = new JComboBox();
        cheveux.addItem("Blond");
        cheveux.addItem("Brun");
        cheveux.addItem("Roux");
        cheveux.addItem("Blanc");
        cheveuxLabel = new JLabel("Cheveux");
        panCheveux.add(cheveuxLabel);
        panCheveux.add(cheveux);

        JPanel content = new JPanel();
        content.setBackground(Color.white);
        //content.add(panNom);
        content.add(panStartNode);
        content.add(panAge);
        content.add(panTaille);
        content.add(panCheveux);

        JPanel control = new JPanel();
        JButton okBouton = new JButton("OK");

        okBouton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                zInfo = new SearchDialogInfo(nom.getText(), (String) startNode.getSelectedItem(), getAge(), (String) cheveux.getSelectedItem(), getTaille());
                setVisible(false);
            }

            public String getAge() {
                return (tranche1.isSelected()) ? tranche1.getText()
                        : (tranche2.isSelected()) ? tranche2.getText()
                        : (tranche3.isSelected()) ? tranche3.getText()
                        : (tranche4.isSelected()) ? tranche4.getText()
                        : tranche1.getText();
            }

            public String getTaille() {
                return (taille.getText().equals("")) ? "180" : taille.getText();
            }
        });

        JButton cancelBouton = new JButton("Annuler");
        cancelBouton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                setVisible(false);
            }
        });

        control.add(okBouton);
        control.add(cancelBouton);

        // this.getContentPane().add(panIcon, BorderLayout.WEST);
        this.getContentPane().add(content, BorderLayout.CENTER);
        this.getContentPane().add(control, BorderLayout.SOUTH);
    }
}
