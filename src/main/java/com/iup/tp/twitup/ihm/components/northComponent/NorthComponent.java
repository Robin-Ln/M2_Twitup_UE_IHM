package com.iup.tp.twitup.ihm.components.northComponent;

import com.iup.tp.twitup.core.EntityManager;
import com.iup.tp.twitup.datamodel.IDatabase;
import com.iup.tp.twitup.datamodel.Twit;
import com.iup.tp.twitup.datamodel.User;
import com.iup.tp.twitup.ihm.ITwitupMainView;
import com.iup.tp.twitup.ihm.ITwitupMainViewObserver;
import com.iup.tp.twitup.ihm.components.northLogedComponent.INorthLogedComponentObserver;
import com.iup.tp.twitup.ihm.components.northLogedComponent.NorthLogedComponent;
import com.iup.tp.twitup.ihm.components.northLogoutComponent.INorthLogoutComponent;
import com.iup.tp.twitup.ihm.components.northLogoutComponent.INorthLogoutComponentObserver;
import com.iup.tp.twitup.ihm.components.northLogoutComponent.NorthLogoutComponent;
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

public class NorthComponent extends JPanel implements INorthComponent, ITwitupMainViewObserver, INorthLogoutComponentObserver, INorthLogedComponentObserver {

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
     * Pannel deconnecter
     */

    /**
     * Configurer la langue de l'aplication
     */
    private ResourceBundle mBundle;

    public NorthComponent(IDatabase database, EntityManager entityManager, ResourceBundle bundle) {
        super();
        this.mDatabase = database;
        this.mEntityManager = entityManager;
        this.mObservers = new HashSet<>();
        this.mBundle = bundle;
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

        this.handlerLogout();
    }

    /**
     * handler logout
     */
    private void handlerLogout(){
        this.removeAll();

        NorthLogoutComponent northLogoutComponent = new NorthLogoutComponent(this.mBundle);
        northLogoutComponent.addObserver(this);

        this.add(northLogoutComponent,
                new GridBagConstraints(0, 0, 1, 1, 1, 0,
                        GridBagConstraints.EAST,
                        GridBagConstraints.NONE,
                        new Insets(0, 0, 0, 0), 0, 0));

        this.revalidate();
    }

    /**
     * Méthodes de l'interface ITwitupMainViewObserver
     */
    @Override
    public void notifyEchangeDirectoryChange(File file) {
    }


    @Override
    public void notifySuccessConnexion(User user) {
        this.removeAll();

        NorthLogedComponent northLogedComponent = new NorthLogedComponent(this.mBundle);
        northLogedComponent.addObserver(this);

        this.add(northLogedComponent,
                new GridBagConstraints(0, 0, 1, 1, 1, 0,
                        GridBagConstraints.NORTH,
                        GridBagConstraints.HORIZONTAL,
                        new Insets(0, 0, 0, 0), 0, 0));

        this.revalidate();
    }

    /**
     * Methode de INorthLogoutComponentObserver, INorthLogedComponentObserver
     */

    @Override
    public void notifyRequestLogout() {
        this.handlerLogout();
        for (INorthComponentObserver observer : this.mObservers) {
            observer.notifyRequestLogout();
        }
    }

    @Override
    public void notifySearchRequest(String search) {
        for (INorthComponentObserver observer : this.mObservers) {
            observer.notifySearchRequest(search);
        }
    }

    @Override
    public void notifyRequestConnexion() {
        for (INorthComponentObserver observer : this.mObservers) {
            observer.notifyRequestConnexion();
        }
    }

    @Override
    public void notifyRequestInscription() {
        for (INorthComponentObserver observer : this.mObservers) {
            observer.notifyRequestInscription();
        }
    }
}
