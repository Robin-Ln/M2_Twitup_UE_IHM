package com.iup.tp.twitup.ihm.components.centerComponent;

import com.iup.tp.twitup.core.EntityManager;
import com.iup.tp.twitup.datamodel.IDatabase;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;

public class CenterComponent extends JPanel implements ICenterComponent {

    /**
     * Liste des observateurs de modifications de la base.
     */
    private final Set<ICenterComponentObserver> mObservers;

    /**
     * Base de donénes de l'application.
     */
    private IDatabase mDatabase;

    /**
     * Gestionnaire de bdd et de fichier.
     */
    private EntityManager mEntityManager;

    /**
     * Configurer la langue de l'aplication
     */
    private ResourceBundle mBundle;

    public CenterComponent(IDatabase database, EntityManager entityManager, Locale locale) {
        super();
        this.mDatabase = database;
        this.mEntityManager = entityManager;
        this.mObservers = new HashSet<>();
        this.mBundle = ResourceBundle.getBundle("local", locale);
        this.init();
    }

    @Override
    public void addObserver(ICenterComponentObserver observer) {
        this.mObservers.add(observer);
    }


    @Override
    public void deleteObserver(ICenterComponentObserver observer) {
        this.mObservers.remove(observer);
    }

    /**
     * Initialisation du composant
     */
    private void init(){
        this.setOpaque(true);
        this.setBackground(new Color(50,150,200,70));
        this.setBorder(new LineBorder(Color.BLUE, 4,true));

        /**
         * Label connexion OK
         */
        JLabel label = new JLabel("OK");
        this.add(label);
    }
}
