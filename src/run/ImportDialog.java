package run;

import domain.Graph;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class ImportDialog extends JDialog {

    private ImportDialogInfo zInfo = new ImportDialogInfo();
    private Graph graph;
    private JRadioButton strict, noStrict, ecrase, update;

    public ImportDialog(JFrame parent, String title, boolean modal, Graph g) {
        super(parent, title, modal);
        graph = g;
        this.setSize(550, 210);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        this.initComponent();
    }

    public ImportDialogInfo showZDialog() {
        this.setVisible(true);
        return this.zInfo;
    }

    private void initComponent() {
        //Paramètre d'import
        JPanel panParam1 = new JPanel();
        panParam1.setBackground(Color.white);
        panParam1.setPreferredSize(new Dimension(440, 60));
        panParam1.setBorder(BorderFactory.createTitledBorder("Mode de lecture"));
        ButtonGroup bg1 = new ButtonGroup();
        strict = new JRadioButton("Strict", false);
        bg1.add(strict);
        noStrict = new JRadioButton("Intelligent", true);
        bg1.add(noStrict);
        panParam1.add(strict);
        panParam1.add(noStrict);
        JPanel panParam2 = new JPanel();
        panParam2.setBackground(Color.white);
        panParam2.setPreferredSize(new Dimension(440, 60));
        panParam2.setBorder(BorderFactory.createTitledBorder("Sortie"));
        ButtonGroup bg2 = new ButtonGroup();
        ecrase = new JRadioButton("Nouveau graphe", true);
        bg2.add(ecrase);
        update = new JRadioButton("Ajout au graphe courant", false);
        bg2.add(update);
        panParam2.add(ecrase);
        panParam2.add(update);

        // Création du panel
        JPanel content = new JPanel();
        content.setBackground(Color.white);
        content.add(panParam1);
        content.add(panParam2);

        JPanel control = new JPanel();
        control.setBackground(Color.white);
        JButton okBouton = new JButton("OK");
        okBouton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                zInfo = new ImportDialogInfo(strict.isSelected(), noStrict.isSelected(), ecrase.isSelected(), update.isSelected());
                setVisible(false);
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
