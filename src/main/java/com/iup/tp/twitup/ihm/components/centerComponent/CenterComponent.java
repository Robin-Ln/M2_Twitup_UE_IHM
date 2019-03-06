package com.iup.tp.twitup.ihm.components.centerComponent;

import com.iup.tp.twitup.core.EntityManager;
import com.iup.tp.twitup.datamodel.IDatabase;
import com.iup.tp.twitup.datamodel.Twit;
import com.iup.tp.twitup.datamodel.User;
import com.iup.tp.twitup.ihm.components.listTwitComponent.IListTwitComponentObserver;
import com.iup.tp.twitup.ihm.components.listTwitComponent.ListTwitComponent;
import com.iup.tp.twitup.ihm.components.twitAdd.TwitAddComponent;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.HashSet;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;

public class CenterComponent extends JPanel implements ICenterComponent, IListTwitComponentObserver {

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

    /**
     * Nom de l'utilisateur
     */
    private User mUser;

    public CenterComponent(IDatabase database, EntityManager entityManager, Locale locale, User user) {
        super();
        this.mDatabase = database;
        this.mEntityManager = entityManager;
        this.mObservers = new HashSet<>();
        this.mBundle = ResourceBundle.getBundle("local", locale);
        this.mUser = user;
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
         * Liste des twits
         */
        Set<Twit> twits = this.mDatabase.getTwits();
        ListTwitComponent listTwitComponent = new ListTwitComponent(twits);
        this.add(listTwitComponent);
        listTwitComponent.addObserver(this);

        /**
         * ajouter un twit
         */
        TwitAddComponent twitAddComponent = new TwitAddComponent(this.mBundle, this.mUser);
        twitAddComponent.addObserver(listTwitComponent);
        this.add(twitAddComponent);
    }

    /**
     * Methode de IListTwitComponentObserver
     */
    @Override
    public void notifyTwitListUpdate() {
        this.revalidate();
        this.repaint();
    }
}
