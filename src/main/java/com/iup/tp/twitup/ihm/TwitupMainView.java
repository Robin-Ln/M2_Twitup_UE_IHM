package com.iup.tp.twitup.ihm;

import com.iup.tp.twitup.core.EntityManager;
import com.iup.tp.twitup.datamodel.IDatabase;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashSet;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;

/**
 * Classe de la vue principale de l'application.
 */
public class TwitupMainView implements IIhmObservable {

    /**
     * Fenetre du bouchon
     */
    private JFrame mFrame;

    /**
     * Base de donénes de l'application.
     */
    private IDatabase mDatabase;

    /**
     * Gestionnaire de bdd et de fichier.
     */
    private EntityManager mEntityManager;

    /**
     * Liste des observateurs de modifications de la base.
     */
    protected final Set<ITwitupMainViewObserver> mObservers;

    /**
     * Configurer la langue de l'aplication
     */
    ResourceBundle mBundle;

    /**
     * Constructeur.
     *
     * @param database , Base de données de l'application.
     */
    public TwitupMainView(IDatabase database, EntityManager entityManager, Locale locale) {
        this.mDatabase = database;
        this.mEntityManager = entityManager;
        this.mObservers = new HashSet<>();
        this.mBundle = ResourceBundle.getBundle("local", locale);
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
            screenSize.height /=  2;
            screenSize.width /= 2;
            frame.setSize(screenSize);

            TwitupMainView.this.mFrame.setVisible(true);
        });
    }

    /**
     * Initialisation de l'IHM
     */
    protected void initGUI() {
        // Création de la fenetre principale
        this.mFrame = new JFrame(this.mBundle.getString("titre"));

        mFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                TwitupMainView.this.handlerQuitter();
            }
        });

        /**
         * Menu
         */

        JMenuBar menuBar = new JMenuBar();
        mFrame.setJMenuBar(menuBar);

        JMenu menu = new JMenu(this.mBundle.getString("menu.fichier"));
        menuBar.add(menu);

        /**
         * Menu Configurer
         */

        JMenuItem itemConfigurer = new JMenuItem(this.mBundle.getString("menu.configurer"));
        ImageIcon iconEditer = new ImageIcon(getClass().getResource("/images/editIcon_20.png"));
        itemConfigurer.setIcon(iconEditer);
        itemConfigurer.addActionListener(e -> TwitupMainView.this.handlerFileChooser());
        menu.add(itemConfigurer);

        /**
         * Menu Connection
         */

        JMenuItem itemConnection = new JMenuItem(this.mBundle.getString("menu.connection"));
        itemConnection.setIcon(iconEditer);
        itemConnection.addActionListener(e -> TwitupMainView.this.handlerConnection(0));
        menu.add(itemConnection);

        /**
         * Menu Quitter
         */

        JMenuItem itemQutter = new JMenuItem(this.mBundle.getString("menu.quitter"));
        ImageIcon iconQuitter = new ImageIcon(getClass().getResource("/images/exitIcon_20.png"));
        itemQutter.setIcon(iconQuitter);
        itemQutter.addActionListener(e -> TwitupMainView.this.handlerQuitter());
        menu.add(itemQutter);

        JMenuItem itemInfo = new JMenuItem("?");
        itemInfo.addActionListener(e -> TwitupMainView.this.handlerDialogInfo());
        menuBar.add(itemInfo);


    }



    /**
     * Methodes handler
     */
    public void handlerConnection(Integer nbConnexion) {


        JTextField nameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        JPanel panel = new JPanel(new GridLayout(0, 2));

        if(nbConnexion > 0){
            JLabel echecLabel = new JLabel(this.mBundle.getString("dialog.connexion.label.echec"));
            echecLabel.setForeground(Color.RED);
            panel.add(echecLabel);

            JLabel nbConnexionLabel = new JLabel(nbConnexion.toString());
            nbConnexionLabel.setForeground(Color.RED);
            panel.add(nbConnexionLabel);
        }


        panel.add(new JLabel(this.mBundle.getString("dialog.connexion.label.name")));
        panel.add(nameField);
        panel.add(new JLabel(this.mBundle.getString("dialog.connexion.label.password")));
        panel.add(passwordField);


        int result = JOptionPane.showConfirmDialog(this.mFrame, panel, this.mBundle.getString("dialog.connexion.label.title"),
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            for (ITwitupMainViewObserver observer : this.mObservers) {
                observer.notifyRequestUserConnexion(nameField.getText(), passwordField.getPassword(), nbConnexion);
            }
        }
    }

    private void handlerQuitter() {

        // notifier les observer que l'application va se fermer
        for (ITwitupMainViewObserver observer : this.mObservers) {
            observer.notifyWindowClosing(this);
        }

        System.exit(0);
    }

    private void handlerDialogInfo() {
        JOptionPane jOptionPane = new JOptionPane();
        ImageIcon iconIUP = new ImageIcon(getClass().getResource("/images/logoIUP_50.jpg"));
        jOptionPane.showMessageDialog(this.mFrame,
                this.mBundle.getString("dialog.info.contenu"),
                this.mBundle.getString("dialog.info.title"),
                JOptionPane.INFORMATION_MESSAGE,
                iconIUP);
    }

    public void handlerFileChooser() {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle(this.mBundle.getString("dialog.file.choser.tilte"));
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int returnVal = chooser.showOpenDialog(this.mFrame);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            // notifier les observer qu'un fichier à été selectionné
            for (ITwitupMainViewObserver observer : this.mObservers) {
                observer.notifyEchangeDirectoryChange(chooser.getSelectedFile());
            }
        } else {
            throw new RuntimeException("handlerFileChooser Fail");
        }
    }


    @Override
    public void addObserver(ITwitupMainViewObserver observer) {
        this.mObservers.add(observer);
    }


    @Override
    public void deleteObserver(ITwitupMainViewObserver observer) {
        this.mObservers.remove(observer);
    }
}
