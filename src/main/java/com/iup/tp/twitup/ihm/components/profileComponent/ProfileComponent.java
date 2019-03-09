package com.iup.tp.twitup.ihm.components.profileComponent;

import com.iup.tp.twitup.core.EntityManager;
import com.iup.tp.twitup.datamodel.DatabaseAdapter;
import com.iup.tp.twitup.datamodel.IDatabase;
import com.iup.tp.twitup.datamodel.User;

import javax.swing.*;
import java.awt.*;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

public class ProfileComponent extends JPanel implements IProfileComponent {

    /**
     * Liste des observateurs de modifications de la base.
     */
    private final Set<IProfileComponentObserver> mObservers;

    /**
     * Configurer la langue de l'aplication
     */
    private ResourceBundle mBundle;

    /**
     * utilisateur connecter
     */
    private User mUser;

    /**
     * Gestionnaire de bdd et de fichier.
     */
    private EntityManager mEntityManager;

    private IDatabase mDatabase;


    public ProfileComponent(ResourceBundle bundle, User user, EntityManager entityManager, IDatabase database) {
        super();

        this.mObservers = new HashSet<>();

        this.mBundle = bundle;

        this.mUser = user;

        this.mEntityManager = entityManager;

        this.mDatabase = database;

        this.init();
    }

    private void init() {

        mDatabase.addObserver(new DatabaseAdapter() {
            @Override
            public void notifyUserModified(User modifiedUser) {
                // TODO metre a jour les iformation personelle de l'utilosateur si elle sont modifier
            }
        });

        JPanel image = new JPanel();

        JLabel name = new JLabel(this.mUser.getName());

        JLabel tag = new JLabel(this.mUser.getUserTag());

        Integer nbTwits = this.mDatabase.getTwitsWithUserTag(this.mUser.getUserTag()).size();
        JLabel nbTwitsLabel = new JLabel(nbTwits.toString());
        JLabel nbTwitsLibelle = new JLabel(this.mBundle.getString("label.profile.nbTwits.libelle"));

        Integer nbFollowers = this.mUser.getFollows().size();
        JLabel nbFollowersLabel = new JLabel(nbFollowers.toString());
        JLabel nbFollowersLibelle = new JLabel(this.mBundle.getString("label.profile.nbFollowers.libelle"));

        /**
         * Layout
         */
        this.setLayout(new GridBagLayout());
        this.add(image,
                new GridBagConstraints(0, 0, 2, 2, 0, 0,
                        GridBagConstraints.NORTHWEST,
                        GridBagConstraints.BOTH,
                        new Insets(5, 5, 0, 5), 0, 0));

        this.add(name,
                new GridBagConstraints(3, 0, 1, 1, 0, 0,
                        GridBagConstraints.NORTHWEST,
                        GridBagConstraints.NONE,
                        new Insets(5, 5, 0, 5), 0, 0));

        this.add(tag,
                new GridBagConstraints(3, 1, 1, 1, 0, 0,
                        GridBagConstraints.NORTHWEST,
                        GridBagConstraints.NONE,
                        new Insets(5, 5, 0, 5), 0, 0));

        this.add(nbFollowersLibelle,
                new GridBagConstraints(0, 3, 1, 1, 0, 0,
                        GridBagConstraints.NORTHWEST,
                        GridBagConstraints.NONE,
                        new Insets(5, 5, 0, 5), 0, 0));

        this.add(nbFollowersLabel,
                new GridBagConstraints(0, 4, 1, 1, 0, 0,
                        GridBagConstraints.NORTHWEST,
                        GridBagConstraints.NONE,
                        new Insets(5, 5, 0, 5), 0, 0));

        this.add(nbTwitsLibelle,
                new GridBagConstraints(1, 3, 1, 1, 0, 0,
                        GridBagConstraints.NORTHWEST,
                        GridBagConstraints.NONE,
                        new Insets(5, 5, 0, 5), 0, 0));

        this.add(nbTwitsLabel,
                new GridBagConstraints(1, 4, 1, 1, 0, 0,
                        GridBagConstraints.NORTHWEST,
                        GridBagConstraints.NONE,
                        new Insets(5, 5, 0, 5), 0, 0));

    }

    @Override
    public void addObserver(IProfileComponentObserver observer) {
        this.mObservers.add(observer);
    }


    @Override
    public void deleteObserver(IProfileComponentObserver observer) {
        this.mObservers.remove(observer);
    }
}
