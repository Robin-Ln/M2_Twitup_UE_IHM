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
        this.setLayout(new GridBagLayout());


        /**
         * Liste des twits
         */
        Set<Twit> twits = this.mDatabase.getTwits();
        ListTwitComponent listTwitComponent = new ListTwitComponent(twits);
        listTwitComponent.addObserver(this);

        /**
         * ajouter un twit
         */
        TwitAddComponent twitAddComponent = new TwitAddComponent(this.mBundle, this.mUser);
        twitAddComponent.addObserver(listTwitComponent);

        /**
         * Ajout des composents
         */


        this.add(twitAddComponent,
                new GridBagConstraints(0, 0, 1, 1, 1, 0,
                        GridBagConstraints.NORTH,
                        GridBagConstraints.BOTH,
                        new Insets(5, 5, 0, 5), 0, 0));

        this.add(listTwitComponent,
                new GridBagConstraints(1, 0, 1, 1, 1, 1,
                        GridBagConstraints.NORTH,
                        GridBagConstraints.BOTH,
                        new Insets(5, 5, 0, 5), 0, 0));

    }

    /**
     * Methode de IListTwitComponentObserver
     */
    @Override
    public void notifyTwitListUpdate() {

        for (ICenterComponentObserver observer : this.mObservers){
            observer.notifyViewChange();
        }
    }
}
