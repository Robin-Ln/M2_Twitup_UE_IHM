package com.iup.tp.twitup.ihm;

import com.iup.tp.twitup.core.EntityManager;
import com.iup.tp.twitup.datamodel.IDatabase;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

/**
 * Classe de la vue principale de l'application.
 */
public class TwitupMainView {

    /**
     * Fenetre du bouchon
     */
    protected JFrame mFrame;

    /**
     * Base de donénes de l'application.
     */
    protected IDatabase mDatabase;

    /**
     * Gestionnaire de bdd et de fichier.
     */
    protected EntityManager mEntityManager;

    /**
     * Constructeur.
     *
     * @param database , Base de données de l'application.
     */
    public TwitupMainView(IDatabase database, EntityManager entityManager) {
        this.mDatabase = database;
        this.mEntityManager = entityManager;
    }

    /**
     * Lance l'afficahge de l'IHM.
     */
    public void showGUI() {
        // Init auto de l'IHM au cas ou ;)
        if (mFrame == null) {
            this.initGUI();
        }

        // Affichage dans l'EDT
        SwingUtilities.invokeLater(() -> {
            // Custom de l'affichage
            JFrame frame = TwitupMainView.this.mFrame;
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            frame.setSize(screenSize);

            TwitupMainView.this.mFrame.setVisible(true);
        });
    }

    /**
     * Initialisation de l'IHM
     */
    protected void initGUI() {
        // Création de la fenetre principale
        this.mFrame = new JFrame("ma fenetre");

        mFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                TwitupMainView.this.HandlerQuitter();
            }
        });

        /**
         * Menu
         */

        JMenuBar menuBar = new JMenuBar();
        mFrame.setJMenuBar(menuBar);

        JMenu menu = new JMenu("Fichier");
        menuBar.add(menu);

        JMenuItem itemEditer = new JMenuItem("Editier");
        ImageIcon iconEditer = new ImageIcon(getClass().getResource("/images/editIcon_20.png"));
        itemEditer.setIcon(iconEditer);
        itemEditer.addActionListener(e -> TwitupMainView.this.HandlerFileChooser());
        menu.add(itemEditer);

        JMenuItem itemQutter = new JMenuItem("Quitter");
        ImageIcon iconQuitter = new ImageIcon(getClass().getResource("/images/exitIcon_20.png"));
        itemQutter.setIcon(iconQuitter);
        itemQutter.addActionListener(e -> TwitupMainView.this.HandlerQuitter());
        menu.add(itemQutter);

        JMenuItem itemInfo = new JMenuItem("?");
        itemInfo.addActionListener(e -> TwitupMainView.this.HandlerDialogInfo());
        menuBar.add(itemInfo);


    }

    /**
     * Methodes handler
     */

    private void HandlerQuitter() {
        System.exit(0);
    }

    private void HandlerDialogInfo() {
        JOptionPane jOptionPane = new JOptionPane();
        ImageIcon iconIUP = new ImageIcon(getClass().getResource("/images/logoIUP_50.jpg"));
        jOptionPane.showMessageDialog(this.mFrame,
                "UBO M2-TIIL \nDépartement informatique",
                "A propos",
                JOptionPane.INFORMATION_MESSAGE,
                iconIUP);
    }

    private File HandlerFileChooser() {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int returnVal = chooser.showOpenDialog(this.mFrame);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            return chooser.getSelectedFile();
        }
        return null;
    }
}
