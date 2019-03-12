package com.iup.tp.twitup.ihm.components.listTwitComponent;

import com.iup.tp.twitup.common.Constants;
import com.iup.tp.twitup.common.MethodesUtils;
import com.iup.tp.twitup.core.EntityManager;
import com.iup.tp.twitup.datamodel.DatabaseAdapter;
import com.iup.tp.twitup.datamodel.IDatabase;
import com.iup.tp.twitup.datamodel.Twit;
import com.iup.tp.twitup.datamodel.User;
import com.iup.tp.twitup.ihm.components.notificationComponent.INotificationComponentObserver;
import com.iup.tp.twitup.ihm.components.twitComponent.TwitComponent;
import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import java.awt.*;
import java.util.*;

public class ListTwitComponent extends JPanel implements IListTwitComponent, INotificationComponentObserver {


    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
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
    private JPanel contenu;
    /**
     * Gestionnaire de bdd et de fichier.
     */
    private EntityManager mEntityManager;
    /**
     * Configurer la langue de l'aplication
     */
    private ResourceBundle mBundle;
    private User mUser;
    private Map<Twit, TwitComponent> twits;

    /**
     * Data base
     */
    /**
     * Base de donénes de l'application.
     */
    private IDatabase mDatabase;

    public ListTwitComponent(IDatabase database, EntityManager entityManager, ResourceBundle bundle, User user) {
        this.mObservers = new HashSet<>();
        this.nbTwit = 0;
        this.mDatabase = database;
        this.mEntityManager = entityManager;
        this.mBundle = bundle;
        this.mUser = user;
        this.twits = new HashMap<>();
        this.init();

    }

    private void init() {

        this.setBackground(new Color(144,193,217));

        this.setLayout(new GridBagLayout());
        JScrollPane scrollPane = new JScrollPane();

        this.contenu = new JPanel(new GridBagLayout());

        this.contenu.setBackground(new Color(144,193,217));

        scrollPane.getViewport().add(this.contenu);

        this.add(scrollPane, new GridBagConstraints(0, 0, 1, 1, 1, 1,
                GridBagConstraints.NORTH,
                GridBagConstraints.BOTH,
                new Insets(5, 5, 0, 5), 0, 0));


        this.handlerUpdateListTwith(this.mDatabase.getTwits());

        this.mDatabase.addObserver(new DatabaseAdapter() {
            @Override
            public void notifyTwitAdded(Twit addedTwit) {
                ListTwitComponent.this.handlerAddTwit(addedTwit);
            }

            @Override
            public void notifyTwitDeleted(Twit deletedTwit) {
                ListTwitComponent.this.handlerDeleteTwit(deletedTwit);
            }

            @Override
            public void notifyTwitModified(Twit modifiedTwit) {
                ListTwitComponent.this.handlerUpdateTwit(modifiedTwit);
            }
        });
    }

    /**
     * Handler
     */

    public void handlerSreachTwit(String search) {

        // si la recherche est vide
        if (StringUtils.isBlank(search)) {
            this.handlerUpdateListTwith(this.mDatabase.getTwits());
            return;
        }

        Set<String> userTags = MethodesUtils.extractTags(search, Constants.USER_TAG_DELIMITER);
        Set<String> wordTags = MethodesUtils.extractTags(search, Constants.WORD_TAG_DELIMITER);

        // si @ et # ne sont pas indiquer dans la recherhce
        if (!StringUtils.contains(search,Constants.USER_TAG_DELIMITER) &&
                !StringUtils.contains(search,Constants.WORD_TAG_DELIMITER)){
            String [] tags = search.split(" ");
            for (String tag: tags) {
                userTags.add(tag);
                wordTags.add(tag);
            }
        }

        Set<Twit> twits = new HashSet<>();

        // lancement de la recherche
        for (Twit twit : this.mDatabase.getTwits()) {
            // est ce que le tag du twiter correspond
            if (userTags.contains(twit.getTwiter().getUserTag())) {
                twits.add(twit);
            }

            // est ce que le twit contien le tag de lutilisateur
            for (String usertag : twit.getUserTags()) {
                if (userTags.contains(usertag)) {
                    twits.add(twit);
                }
            }

            // est ce que le twit contien les #
            for (String wordTag : twit.getTags()) {
                if (wordTags.contains(wordTag)) {
                    twits.add(twit);
                }
            }
        }

        this.handlerUpdateListTwith(twits);

    }

    private void handlerUpdateListTwith(Set<Twit> twits) {
        this.contenu.removeAll();
        this.twits.clear();
        for (Twit twit : twits) {
            this.handlerAddTwit(twit);
        }
        this.revalidate();
        this.repaint();
    }

    private void handlerAddTwit(Twit twit) {

        if (this.twits.containsKey(twit)) {
            return;
        }

        TwitComponent twitComponent = new TwitComponent(twit, this.mUser, this.mBundle, this.mEntityManager);
        this.twits.put(twit, twitComponent);
        this.nbTwit++;
        this.contenu.add(twitComponent,
                new GridBagConstraints(0, this.nbTwit, 1, 1, 1, 0,
                        GridBagConstraints.NORTH,
                        GridBagConstraints.BOTH,
                        new Insets(5, 5, 5, 5), 0, 0));

        this.revalidate();
    }

    private void handlerDeleteTwit(Twit twit) {
        // TODO ne marche pas très bien
        TwitComponent twitComponent = this.twits.get(twit);
        this.twits.remove(twit);
        twitComponent.removeAll();
        this.contenu.remove(twitComponent);
        this.contenu.revalidate();
        this.contenu.repaint();
    }

    private void handlerUpdateTwit(Twit twit) {
        TwitComponent twitComponent = this.twits.get(twit);
        this.twits.remove(twit);
        twitComponent.removeAll();
        twitComponent.setTwit(twit);
        twitComponent.init();
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
     * Notification observer
     */
    @Override
    public void notifyDisplayNotification(Set<Twit> twits) {
        this.handlerUpdateListTwith(twits);
    }
}
