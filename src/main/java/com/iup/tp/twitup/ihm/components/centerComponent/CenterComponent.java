package com.iup.tp.twitup.ihm.components.centerComponent;

import com.iup.tp.twitup.core.EntityManager;
import com.iup.tp.twitup.datamodel.IDatabase;
import com.iup.tp.twitup.datamodel.Twit;
import com.iup.tp.twitup.datamodel.User;
import com.iup.tp.twitup.ihm.components.listTwitComponent.ListTwitComponent;
import com.iup.tp.twitup.ihm.components.listTwitComponent.ListTwitComponentAdapter;
import com.iup.tp.twitup.ihm.components.profileComponent.ProfileComponent;
import com.iup.tp.twitup.ihm.components.twitAdd.TwitAddComponent;

import javax.swing.*;
import java.awt.*;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

public class CenterComponent extends JPanel implements ICenterComponent {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

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

    /**
     *
     */
    private ListTwitComponent listTwitComponent;

    private TwitAddComponent twitAddComponent;

    private ProfileComponent profileComponent;

    public CenterComponent(IDatabase database, EntityManager entityManager, ResourceBundle bundle, User user) {
        super();
        this.mDatabase = database;
        this.mEntityManager = entityManager;
        this.mObservers = new HashSet<>();
        this.mBundle = bundle;
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
    public void init() {

        this.setLayout(new GridBagLayout());
        this.setBackground(new Color(144,193,217));


        /**
         * Liste des twits
         */
        this.listTwitComponent = new ListTwitComponent(this.mDatabase, this.mEntityManager, this.mBundle, this.mUser);
        this.listTwitComponent.addObserver(new ListTwitComponentAdapter() {
            @Override
            public void notifyViewChange() {
                for (ICenterComponentObserver observer : CenterComponent.this.mObservers) {
                    observer.notifyViewChange();
                }
            }
        });

        /**
         * ajouter un twit
         */
        this.twitAddComponent = new TwitAddComponent(this.mBundle, this.mUser, this.mEntityManager);

        /**
         * Profile Component
         */
        this.profileComponent = new ProfileComponent(this.mBundle, this.mUser, this.mEntityManager, this.mDatabase);

        /**
         * Ajout des composents
         */

        this.add(this.profileComponent,
                new GridBagConstraints(0, 0, 1, 2, 1, 0,
                        GridBagConstraints.NORTH,
                        GridBagConstraints.BOTH,
                        new Insets(5, 5, 5, 5), 0, 0));


        this.add(this.twitAddComponent,
                new GridBagConstraints(1, 0, 1, 1, 1, 0,
                        GridBagConstraints.NORTH,
                        GridBagConstraints.BOTH,
                        new Insets(5, 5, 5, 5), 0, 0));

        this.add(this.listTwitComponent,
                new GridBagConstraints(1, 1, 1, 1, 1, 1,
                        GridBagConstraints.NORTH,
                        GridBagConstraints.BOTH,
                        new Insets(5, 5, 5, 5), 0, 0));

    }

    /**
     * Handlers
     */

    public void handlerSreachTwit(String serach) {
        this.listTwitComponent.handlerSreachTwit(serach);
    }


    public void setmUser(User mUser) {
        this.mUser = mUser;
    }
}
