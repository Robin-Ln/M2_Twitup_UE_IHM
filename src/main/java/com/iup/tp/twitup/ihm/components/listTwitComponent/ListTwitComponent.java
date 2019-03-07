package com.iup.tp.twitup.ihm.components.listTwitComponent;

import com.iup.tp.twitup.core.EntityManager;
import com.iup.tp.twitup.datamodel.IDatabase;
import com.iup.tp.twitup.datamodel.Twit;
import com.iup.tp.twitup.ihm.components.twitAdd.ITwitAddComponentObserver;
import com.iup.tp.twitup.ihm.components.twitComponent.TwitComponent;

import javax.swing.*;
import java.awt.*;
import java.util.HashSet;
import java.util.Set;

public class ListTwitComponent extends JPanel implements IListTwitComponent, ITwitAddComponentObserver {

    /**
     * Liste des observateurs de modifications de la base.
     */
    private final Set<IListTwitComponentObserver> mObservers;

    /**
     * Nonbre de twit
     */
    private Integer nbTwit;


    /**
     * Contenue de la liste
     */
    JPanel contenu;

    /**
     * Gestionnaire de bdd et de fichier.
     */
    private EntityManager mEntityManager;

    /**
     * Data base
     */
    /**
     * Base de donénes de l'application.
     */
    private IDatabase mDatabase;

    public ListTwitComponent(Set<Twit> twits, IDatabase database, EntityManager entityManager) {
        this.mObservers = new HashSet<>();
        this.nbTwit = 0;
        this.mDatabase = database;
        this.mEntityManager = entityManager;
        this.init(twits);

    }

    private void init(Set<Twit> twits){
        this.setLayout(new GridBagLayout());
        JScrollPane scrollPane = new JScrollPane();

        this.contenu = new JPanel(new GridBagLayout());
        scrollPane.getViewport().add(this.contenu);

        this.add(scrollPane, new GridBagConstraints(0, 0, 1, 1, 1, 1,
                        GridBagConstraints.NORTH,
                        GridBagConstraints.BOTH,
                        new Insets(5, 5, 0, 5), 0, 0));


        for (Twit twit : twits) {
            this.handlerAddTwit(twit);
        }
    }

    /**
     * Handler
     */

    public void handlerSreachTwit(String search){

        // TODO: si vide get tous les twits

        Set<Twit> twits = this.mDatabase.getTwitsWithTag(search);
        twits.addAll(this.mDatabase.getTwitsWithUserTag(search));
        this.handlerUpdateListTwith(twits);
    }

    private void handlerUpdateListTwith(Set<Twit> twits) {
        this.contenu.removeAll();
        for (Twit twit : twits) {
            this.handlerAddTwit(twit);
        }
        for (IListTwitComponentObserver observer : this.mObservers) {
            observer.notifyViewChange();
        }
    }

    private void handlerAddTwit(Twit twit){
        TwitComponent twitComponent = new TwitComponent(twit);
        this.nbTwit ++;
        this.contenu.add(twitComponent,
                new GridBagConstraints(0, this.nbTwit, 1, 1, 1, 0,
                        GridBagConstraints.NORTH,
                        GridBagConstraints.HORIZONTAL,
                        new Insets(5, 5, 0, 5), 0, 0));
    }

    /**
     * Methode de l'observer ITwitAddComponentObserver
     */
    @Override
    public void notifyNewTwit(Twit twit) {
        this.handlerAddTwit(twit);
        this.mEntityManager.sendTwit(twit);
        for (IListTwitComponentObserver observer : this.mObservers) {
            observer.notifyViewChange();
        }
    }

    @Override
    public void notifyViewChange() {

    }

    /**
     * Les methodes de mon interfaces
     */
    @Override
    public void addObserver(IListTwitComponentObserver observer) {
        this.mObservers.add(observer);
    }

    @Override
    public void deleteObserver(IListTwitComponentObserver observer) {
        this.mObservers.remove(observer);
    }
}
