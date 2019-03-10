package com.iup.tp.twitup.ihm.components.northComponent;

import com.iup.tp.twitup.core.EntityManager;
import com.iup.tp.twitup.datamodel.IDatabase;
import com.iup.tp.twitup.datamodel.User;
import com.iup.tp.twitup.ihm.components.northLogedComponent.NorthLogedComponent;
import com.iup.tp.twitup.ihm.components.northLogedComponent.NorthLogedComponentAdapter;
import com.iup.tp.twitup.ihm.components.northLogoutComponent.NorthLogoutComponent;
import com.iup.tp.twitup.ihm.components.northLogoutComponent.NorthLogoutComponentAdapter;

import javax.swing.*;
import java.awt.*;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

public class NorthComponent extends JPanel implements INorthComponent {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

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

    private Boolean mRemember;

    private User mUser;

    /**
     * Configurer la langue de l'aplication
     */
    private ResourceBundle mBundle;

    public NorthComponent(IDatabase database,
                          EntityManager entityManager,
                          ResourceBundle bundle,
                          Boolean remember,
                          User user
    ) {
        super();
        this.mDatabase = database;
        this.mEntityManager = entityManager;
        this.mObservers = new HashSet<>();
        this.mBundle = bundle;
        this.mRemember = remember;
        this.mUser = user;
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
    private void init() {

        this.setLayout(new GridBagLayout());
        this.setBackground(new Color(144,193,217));

        if (this.mRemember) {
            this.handlerLogin(this.mUser);
        } else {
            this.handlerLogout();
        }
    }

    /**
     * handler logout
     */
    private void handlerLogout() {
        this.removeAll();

        NorthLogoutComponent northLogoutComponent = new NorthLogoutComponent(this.mDatabase, this.mEntityManager, this.mBundle);
        northLogoutComponent.addObserver(new NorthLogoutComponentAdapter() {
            @Override
            public void notifySuccessConnexion(User user, Boolean remember) {
                NorthComponent.this.handlerLogin(user);
                NorthComponent.this.mUser = user;

                for (INorthComponentObserver observer : NorthComponent.this.mObservers) {
                    observer.notifySuccessConnexion(user, remember);
                }
            }
        });

        this.add(northLogoutComponent,
                new GridBagConstraints(0, 0, 1, 1, 1, 0,
                        GridBagConstraints.EAST,
                        GridBagConstraints.NONE,
                        new Insets(0, 0, 0, 0), 0, 0));

        this.revalidate();
        this.repaint();
    }

    private void handlerLogin(User user) {
        this.removeAll();
        NorthLogedComponent northLogedComponent = new NorthLogedComponent(this.mBundle);
        northLogedComponent.addObserver(new NorthLogedComponentAdapter() {
            @Override
            public void notifyRequestLogout() {
                NorthComponent.this.handlerLogout();
                for (INorthComponentObserver observer : NorthComponent.this.mObservers) {
                    observer.notifyRequestLogout();
                }
            }

            @Override
            public void notifySearchRequest(String search) {
                for (INorthComponentObserver observer : NorthComponent.this.mObservers) {
                    observer.notifySearchRequest(search);
                }
            }
        });
        this.add(northLogedComponent,
                new GridBagConstraints(0, 0, 1, 1, 1, 0,
                        GridBagConstraints.NORTHWEST,
                        GridBagConstraints.HORIZONTAL,
                        new Insets(0, 0, 0, 0), 0, 0));
        this.revalidate();
        this.repaint();
    }
}
