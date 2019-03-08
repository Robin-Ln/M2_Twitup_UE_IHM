package com.iup.tp.twitup.ihm.components.listTwitComponent;

import com.iup.tp.twitup.core.EntityManager;
import com.iup.tp.twitup.datamodel.IDatabase;
import com.iup.tp.twitup.datamodel.IDatabaseObserver;
import com.iup.tp.twitup.datamodel.Twit;
import com.iup.tp.twitup.datamodel.User;
import com.iup.tp.twitup.ihm.components.twitComponent.ITwitComponentObserver;
import com.iup.tp.twitup.ihm.components.twitComponent.TwitComponent;
import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

public class ListTwitComponent extends JPanel implements IListTwitComponent, IDatabaseObserver, ITwitComponentObserver {



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
     * Configurer la langue de l'aplication
     */
    private ResourceBundle mBundle;

    private User mUser;

    /**
     * Data base
     */
    /**
     * Base de donénes de l'application.
     */
    private IDatabase mDatabase;

    public ListTwitComponent(Set<Twit> twits, IDatabase database, EntityManager entityManager, ResourceBundle bundle, User user) {
        this.mObservers = new HashSet<>();
        this.nbTwit = 0;
        this.mDatabase = database;
        this.mEntityManager = entityManager;
        this.mBundle = bundle;
        this.mUser = user;
        this.init(twits);

    }

    private void init(Set<Twit> twits){

        this.setLayout(new GridBagLayout());
        JScrollPane scrollPane = new JScrollPane();

        this.contenu = new JPanel(new GridBagLayout());
        this.contenu.setBorder(new LineBorder(Color.DARK_GRAY, 4, true));

        scrollPane.getViewport().add(this.contenu);

        this.add(scrollPane, new GridBagConstraints(0, 0, 1, 1, 1, 1,
                        GridBagConstraints.NORTH,
                        GridBagConstraints.BOTH,
                        new Insets(5, 5, 0, 5), 0, 0));


        for (Twit twit : twits) {
            this.handlerAddTwit(twit);
        }

        this.mDatabase.addObserver(this);
    }

    /**
     * Handler
     */

    public void handlerSreachTwit(String search){

        Set<Twit> twits = new HashSet<>();

        if (StringUtils.isBlank(search)) {
            twits = this.mDatabase.getTwits();
            this.handlerUpdateListTwith(twits);
            return;
        }

        twits = this.mDatabase.getTwitsWithTag(search);
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

        TwitComponent twitComponent = new TwitComponent(twit, this.mUser, this.mBundle);
        twitComponent.addObserver(this);
        this.nbTwit++;
        this.contenu.add(twitComponent,
                new GridBagConstraints(0, this.nbTwit, 1, 1, 1, 0,
                        GridBagConstraints.NORTH,
                        GridBagConstraints.BOTH,
                        new Insets(5, 5, 0, 5), 0, 0));

        this.revalidate();

    }

    private void handlerDeleteTwith(TwitComponent twitComponent) {
        twitComponent.deleteObserver(this);
        twitComponent.removeAll();
        this.contenu.remove(twitComponent);
        this.mDatabase.removeTwit(twitComponent.getTwit());
        this.revalidate();
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

    /**
     * Methode de l'inteface IDatabaseObserver
     */
    @Override
    public void notifyTwitAdded(Twit addedTwit) {
        this.handlerAddTwit(addedTwit);
    }

    @Override
    public void notifyTwitDeleted(Twit deletedTwit) {

    }

    @Override
    public void notifyTwitModified(Twit modifiedTwit) {

    }

    @Override
    public void notifyUserAdded(User addedUser) {

    }

    @Override
    public void notifyUserDeleted(User deletedUser) {

    }

    @Override
    public void notifyUserModified(User modifiedUser) {

    }

    /**
     * Methode de l'interface ITwitComponentObserver
     */
    @Override
    public void notifyDeleteTwitComponent(TwitComponent twitComponent) {
        this.handlerDeleteTwith(twitComponent);
    }
}
