package com.iup.tp.twitup.ihm;

import com.iup.tp.twitup.core.EntityManager;
import com.iup.tp.twitup.datamodel.IDatabase;
import com.iup.tp.twitup.datamodel.User;
import com.iup.tp.twitup.ihm.components.centerComponent.CenterComponent;
import com.iup.tp.twitup.ihm.components.centerComponent.CenterComponentAdapter;
import com.iup.tp.twitup.ihm.components.northComponent.NorthComponent;
import com.iup.tp.twitup.ihm.components.northComponent.NorthComponentAdapter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

/**
 * Classe de la vue principale de l'application.
 */
public class TwitupMainView extends JFrame implements ITwitupMainView {

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
    private ResourceBundle mBundle;

    /**
     * Center panel
     */
    private CenterComponent centerComponent;

    /**
     * north panel
     */
    private NorthComponent northComponent;

    /**
     * Constructeur.
     *
     * @param database , Base de données de l'application.
     */
    public TwitupMainView(IDatabase database, EntityManager entityManager, ResourceBundle bundle) {
        this.mDatabase = database;
        this.mEntityManager = entityManager;
        this.mObservers = new HashSet<>();
        this.mBundle = bundle;
    }

    public void showGUI() {
        // dimenssion
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        screenSize.height /= 2;
        screenSize.width /= 2;
        this.setSize(screenSize);
        this.setLocation(this.getWidth() * 2,
                screenSize.height / 4);

        // Affichage dans l'EDT
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {

                // Affichage
                TwitupMainView.this.setVisible(true);
            }
        });
    }

    /**
     * Initialisation de l'IHM
     */
    public void initGUI() {
        // Création de la fenetre principale
        this.setTitle(this.mBundle.getString("titre"));

        /**
         * Création du composant nord
         */
        this.northComponent = new NorthComponent(this.mDatabase, this.mEntityManager, this.mBundle);
        this.northComponent.addObserver(new NorthComponentAdapter() {
            @Override
            public void notifySuccessConnexion(User user,Boolean remember) {
                TwitupMainView.this.handlerSuccessConnexion(user,remember);
            }

            @Override
            public void notifyRequestLogout() {
                TwitupMainView.this.handlerLogout();
            }

            @Override
            public void notifySearchRequest(String search) {
                TwitupMainView.this.centerComponent.handlerSreachTwit(search);
            }
        });
        this.add(this.northComponent, BorderLayout.NORTH);

        /**
         * TODO: notifier Twithup
         */
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                TwitupMainView.this.handlerQuitter();
            }
        });


        /**
         * Menu
         */

        JMenuBar menuBar = new JMenuBar();
        this.setJMenuBar(menuBar);

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

    private void handlerLogout() {
        this.centerComponent.removeAll();
        this.centerComponent.revalidate();
        this.centerComponent.repaint();
    }

    /**
     * Methodes handler
     */

    private void handlerQuitter() {
        System.exit(0);
    }

    private void handlerDialogInfo() {
        JOptionPane jOptionPane = new JOptionPane();
        ImageIcon iconIUP = new ImageIcon(getClass().getResource("/images/logoIUP_50.jpg"));
        jOptionPane.showMessageDialog(this,
                this.mBundle.getString("dialog.info.contenu"),
                this.mBundle.getString("dialog.info.title"),
                JOptionPane.INFORMATION_MESSAGE,
                iconIUP);
    }

    public void handlerFileChooser() {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle(this.mBundle.getString("dialog.file.choser.tilte"));
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int returnVal = chooser.showOpenDialog(this);
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


    /**
     * handler
     */

    private void handlerSuccessConnexion(User user,Boolean remember){
        if(this.centerComponent == null){
            this.centerComponent = new CenterComponent(this.mDatabase,this.mEntityManager,this.mBundle, user);
            this.centerComponent.addObserver(new CenterComponentAdapter() {});
            this.add(this.centerComponent, BorderLayout.CENTER);
        }else {
            this.centerComponent.removeAll();
            this.centerComponent.init();
        }


        for (ITwitupMainViewObserver observer : this.mObservers) {
            observer.notifyRememberUser(user,remember);
        }

    }
}
