package com.iup.tp.twitup.ihm.components.profileComponent;

import com.iup.tp.twitup.core.EntityManager;
import com.iup.tp.twitup.datamodel.DatabaseAdapter;
import com.iup.tp.twitup.datamodel.IDatabase;
import com.iup.tp.twitup.datamodel.Twit;
import com.iup.tp.twitup.datamodel.User;
import com.iup.tp.twitup.ihm.ImagePanel;
import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.HashSet;
import java.util.Objects;
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

    private JLabel nbTwitsLabel;
    private JLabel nbFollowersLabel;

    private void init() {

        JPanel image = ImagePanel.getUserImage(this.mUser);


        JLabel name = new JLabel(this.mUser.getName());

        JLabel tag = new JLabel(this.mUser.getUserTag());


        this.nbTwitsLabel = new JLabel();
        JLabel nbTwitsLibelle = new JLabel(this.mBundle.getString("label.profile.nbTwits.libelle"));


        this.nbFollowersLabel = new JLabel();
        JLabel nbFollowersLibelle = new JLabel(this.mBundle.getString("label.profile.nbFollowers.libelle"));

        this.handlerInitCpts();

        /**
         * Observer
         */
        mDatabase.addObserver(new DatabaseAdapter() {
            @Override
            public void notifyUserModified(User modifiedUser) {
                ProfileComponent.this.handlerInitCpts();
            }

            @Override
            public void notifyTwitAdded(Twit addedTwit) {
                ProfileComponent.this.handlerInitCpts();
            }

            @Override
            public void notifyTwitDeleted(Twit deletedTwit) {
                ProfileComponent.this.handlerInitCpts();
            }

            @Override
            public void notifyTwitModified(Twit modifiedTwit) {
                ProfileComponent.this.handlerInitCpts();
            }
        });

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

    /**
     * handler
     */

    private void handlerInitCpts(){
        Integer nbFollowers = this.mUser.getFollows().size();
        Integer nbTwits = this.mDatabase.getUserTwits(ProfileComponent.this.mUser).size();
        this.nbTwitsLabel.setText(nbTwits.toString());
        this.nbFollowersLabel.setText(nbFollowers.toString());
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
