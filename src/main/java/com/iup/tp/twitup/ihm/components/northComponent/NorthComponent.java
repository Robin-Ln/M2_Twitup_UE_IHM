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
     * JTextField searchTextField
     */
    private JTextField searchTextField;

    /**
     * JButton searchButton
     */
    private JButton searchButton;

    /**
     * JButton siginButton
     */
    private JButton siginButton;

    /**
     * JButton connexionButton
     */
    private JButton connexionButton;

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
        this.searchTextField = new JTextField();
        this.searchTextField.setVisible(false);

        /**
         * Bouton de recherche
         */
        this.searchButton = new JButton(this.mBundle.getString("button.rechercher.libelle"));
        this.searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });
        this.searchButton.setVisible(false);

        /**
         * Bouton de s'inscrire
         */
        this.siginButton = new JButton(this.mBundle.getString("button.inscription.libelle"));
        this.siginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });

        /**
         * Bouton de connexion
         */
        this.connexionButton = new JButton(this.mBundle.getString("button.connexion.libelle"));
        this.connexionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (INorthComponentObserver observer : NorthComponent.this.mObservers) {
                    observer.notifyRequestConnexion();
                }
            }
        });


        /**
         * Ajout dans le layout
         */
        JPanel contenue = new JPanel();
        contenue.setLayout(new GridBagLayout());

        contenue.add(this.searchTextField,
                new GridBagConstraints(0, 0, 1, 1, 1, 0,
                        GridBagConstraints.NORTH,
                        GridBagConstraints.HORIZONTAL,
                        new Insets(5, 5, 0, 5), 0, 0));

        contenue.add(this.searchButton,
                new GridBagConstraints(1, 0, 1, 1, 0, 0,
                        GridBagConstraints.NORTH,
                        GridBagConstraints.NONE,
                        new Insets(5, 5, 0, 5), 0, 0));

        contenue.add(this.siginButton,
                new GridBagConstraints(2, 0, 1, 1, 0, 0,
                        GridBagConstraints.NORTH,
                        GridBagConstraints.NONE,
                        new Insets(5, 5, 0, 5), 0, 0));

        contenue.add(this.connexionButton,
                new GridBagConstraints(3, 0, 1, 1, 0, 0,
                        GridBagConstraints.NORTH,
                        GridBagConstraints.NONE,
                        new Insets(5, 5, 0, 5), 0, 0));

        /**
         * Ajout du contenue
         */
        this.add(contenue,
                new GridBagConstraints(0, 0, 1, 1, 1, 0,
                        GridBagConstraints.NORTH,
                        GridBagConstraints.HORIZONTAL,
                        new Insets(0, 0, 0, 0), 0, 0));
    }

    /**
     * Méthodes de l'interface ITwitupMainViewObserver
     */
    @Override
    public void notifyEchangeDirectoryChange(File file) {
    }

    @Override
    public void notifyWindowClosing(ITwitupMainView observable) {
    }

    @Override
    public void notifySuccessConnexion(User user) {
        this.searchTextField.setVisible(true);
        this.searchButton.setVisible(true);
        this.connexionButton.setVisible(false);
        this.siginButton.setVisible(false);
    }
}
