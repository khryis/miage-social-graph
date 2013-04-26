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
        this.setSize(550, 150);
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
        JPanel panParam = new JPanel();
        panParam.setBackground(Color.white);
        panParam.setPreferredSize(new Dimension(440, 60));
        panParam.setBorder(BorderFactory.createTitledBorder("Paramètres d'importation"));
        ButtonGroup bg1 = new ButtonGroup();
        strict = new JRadioButton("Strict", true);
        bg1.add(strict);
        noStrict = new JRadioButton("Pas strict", false);
        bg1.add(noStrict);
        panParam.add(strict);
        panParam.add(noStrict);
        ButtonGroup bg2 = new ButtonGroup();
        ecrase = new JRadioButton("Ecrase", true);
        bg2.add(ecrase);
        update = new JRadioButton("Update", false);
        bg2.add(update);
        panParam.add(ecrase);
        panParam.add(update);

        // Création du panel
        JPanel content = new JPanel();
        content.setBackground(Color.white);
        content.add(panParam);

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
