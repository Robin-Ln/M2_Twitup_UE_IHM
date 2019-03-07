package com.iup.tp.twitup.ihm.components.northComponent;

import com.iup.tp.twitup.core.EntityManager;
import com.iup.tp.twitup.datamodel.IDatabase;
import com.iup.tp.twitup.datamodel.Twit;
import com.iup.tp.twitup.datamodel.User;
import com.iup.tp.twitup.ihm.ITwitupMainView;
import com.iup.tp.twitup.ihm.ITwitupMainViewObserver;
import com.iup.tp.twitup.ihm.components.twitComponent.TwitComponent;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.HashSet;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;

public class NorthComponent extends JPanel implements INorthComponent, ITwitupMainViewObserver {

    /**
     * Liste des observateurs de modifications de la base.
     */
    private final Set<INorthComponentObserver> mObservers;

    /**
     * Base de donénes de l'application.
     */
    private IDatabase mDatabase;

    /**
     * Gestionnaire de bdd et de fichier.
     */
    private EntityManager mEntityManager;

    /**
     * Pannel connecter
     */
    private JPanel logedPanel;

    /**
     * Pannel deonnecter
     */
    private JPanel logoutPanel;

    /**
     * Pannel deonnecter
     */
    private JPanel contenue;



    /**
     * Pannel deconnecter
     */

    /**
     * Configurer la langue de l'aplication
     */
    private ResourceBundle mBundle;

    public NorthComponent(IDatabase database, EntityManager entityManager, Locale locale) {
        super();
        this.mDatabase = database;
        this.mEntityManager = entityManager;
        this.mObservers = new HashSet<>();
        this.mBundle = ResourceBundle.getBundle("local", locale);
        this.init();
    }

    @Override
    public void addObserver(INorthComponentObserver observer) {
        this.mObservers.add(observer);
    }


    @Override
    public void deleteObserver(INorthComponentObserver observer) {
        this.mObservers.remove(observer);
    }

    /**
     * Initialisation du composant
     */
    private void init(){
        this.setOpaque(true);
        this.setBackground(new Color(50,150,200,70));
        this.setBorder(new LineBorder(Color.BLUE, 4,true));
        this.setLayout(new GridBagLayout());


        /**
         * Barre de recherche
         */
        JTextField searchTextField = new JTextField();

        /**
         * Bouton de recherche
         */
        JButton searchButton = new JButton(this.mBundle.getString("button.rechercher.libelle"));
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (INorthComponentObserver observer : NorthComponent.this.mObservers) {
                    observer.notifySearchRequest(searchTextField.getText());
                }
            }
        });

        /**
         * Bouton de s'inscrire
         */
        JButton siginButton = new JButton(this.mBundle.getString("button.inscription.libelle"));
        siginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                NorthComponent.this.handlerLogout();
                for (INorthComponentObserver observer : NorthComponent.this.mObservers) {
                    observer.notifyRequestLogout();
                }
            }
        });

        /**
         * Bouton de connexion
         */
        JButton connexionButton = new JButton(this.mBundle.getString("button.connexion.libelle"));
        connexionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (INorthComponentObserver observer : NorthComponent.this.mObservers) {
                    observer.notifyRequestConnexion();
                }
            }
        });

        /**
         * Bouton de deconnexion
         */
        JButton logoutButton = new JButton(this.mBundle.getString("button.logout.libelle"));
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (INorthComponentObserver observer : NorthComponent.this.mObservers) {
                }
            }
        });


        /**
         * Ajout dans le layout
         */
        this.contenue = new JPanel();
        this.contenue.setLayout(new GridBagLayout());

        this.logedPanel = new JPanel();
        this.logedPanel.setLayout(new GridBagLayout());

        this.logoutPanel = new JPanel();
        logoutPanel.setLayout(new GridBagLayout());

        this.logedPanel.add(searchTextField,
                new GridBagConstraints(0, 0, 1, 1, 1, 0,
                        GridBagConstraints.NORTH,
                        GridBagConstraints.HORIZONTAL,
                        new Insets(5, 5, 0, 5), 0, 0));

        this.logedPanel.add(searchButton,
                new GridBagConstraints(1, 0, 1, 1, 0, 0,
                        GridBagConstraints.NORTH,
                        GridBagConstraints.NONE,
                        new Insets(5, 5, 0, 5), 0, 0));

        this.logedPanel.add(logoutButton,
                new GridBagConstraints(2, 0, 1, 1, 0, 0,
                        GridBagConstraints.NORTH,
                        GridBagConstraints.NONE,
                        new Insets(5, 5, 0, 5), 0, 0));

        this.logoutPanel.add(siginButton,
                new GridBagConstraints(0, 0, 1, 1, 0, 0,
                        GridBagConstraints.NORTHEAST,
                        GridBagConstraints.NONE,
                        new Insets(5, 5, 0, 5), 0, 0));

        this.logoutPanel.add(connexionButton,
                new GridBagConstraints(1, 0, 1, 1, 0, 0,
                        GridBagConstraints.NORTHEAST,
                        GridBagConstraints.NONE,
                        new Insets(5, 5, 0, 5), 0, 0));

        /**
         * Ajout du contenue
         */
        this.handlerLogout();
    }

    /**
     * handler logout
     */
    private void handlerLogout(){
        this.contenue.add(this.logoutPanel,
                new GridBagConstraints(0, 0, 1, 1, 0, 0,
                        GridBagConstraints.EAST,
                        GridBagConstraints.NONE,
                        new Insets(0, 0, 0, 0), 0, 0));

        this.add(this.contenue,
                new GridBagConstraints(0, 0, 1, 1, 1, 0,
                        GridBagConstraints.EAST,
                        GridBagConstraints.NONE,
                        new Insets(0, 0, 0, 0), 0, 0));
    }

    /**
     * Méthodes de l'interface ITwitupMainViewObserver
     */
    @Override
    public void notifyEchangeDirectoryChange(File file) {
    }


    @Override
    public void notifySuccessConnexion(User user) {
        this.contenue.removeAll();
        this.removeAll();

        this.contenue.add(this.logedPanel,
                new GridBagConstraints(0, 0, 1, 1, 1, 0,
                        GridBagConstraints.NORTH,
                        GridBagConstraints.HORIZONTAL,
                        new Insets(0, 0, 0, 0), 0, 0));

        this.add(this.contenue,
                new GridBagConstraints(0, 0, 1, 1, 1, 0,
                        GridBagConstraints.NORTH,
                        GridBagConstraints.HORIZONTAL,
                        new Insets(0, 0, 0, 0), 0, 0));
    }
}
