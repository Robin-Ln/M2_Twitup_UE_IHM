package com.iup.tp.twitup.ihm.components.profileComponent;

import com.iup.tp.twitup.core.EntityManager;
import com.iup.tp.twitup.datamodel.DatabaseAdapter;
import com.iup.tp.twitup.datamodel.IDatabase;
import com.iup.tp.twitup.datamodel.Twit;
import com.iup.tp.twitup.datamodel.User;
import com.iup.tp.twitup.ihm.ImagePanel;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

public class ProfileComponent extends JPanel implements IProfileComponent {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

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
    private JLabel nbFollowerLabel;
    private JLabel nbTwitsLabel;
    private JLabel nbSuscribersLabel;
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

        JPanel image = ImagePanel.getUserImage(this.mUser);

        Color color = Color.WHITE;
        this.setBackground(color);
        this.setBorder(new LineBorder(color, 1, true));
        this.setOpaque(true);


        JLabel name = new JLabel(this.mUser.getName());
        JLabel tag = new JLabel(this.mUser.getUserTag());


        this.nbTwitsLabel = new JLabel();
        JLabel nbTwitsLibelle = new JLabel(this.mBundle.getString("label.profile.nbTwits.libelle"));


        this.nbSuscribersLabel = new JLabel();
        JLabel nbSuscribersLibelle = new JLabel(this.mBundle.getString("label.profile.nbAbonne.libelle"));

        this.nbFollowerLabel = new JLabel();
        JLabel nbFollowerLibelle = new JLabel(this.mBundle.getString("label.profile.nbFollowers.libelle"));

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

        this.add(nbSuscribersLibelle,
                new GridBagConstraints(0, 3, 1, 1, 0, 0,
                        GridBagConstraints.NORTHWEST,
                        GridBagConstraints.NONE,
                        new Insets(5, 5, 0, 5), 0, 0));

        this.add(nbSuscribersLabel,
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

        this.add(nbFollowerLibelle,
                new GridBagConstraints(2, 3, 1, 1, 0, 0,
                        GridBagConstraints.NORTHWEST,
                        GridBagConstraints.NONE,
                        new Insets(5, 5, 0, 5), 0, 0));

        this.add(nbFollowerLabel,
                new GridBagConstraints(2, 4, 1, 1, 0, 0,
                        GridBagConstraints.NORTHWEST,
                        GridBagConstraints.NONE,
                        new Insets(5, 5, 0, 5), 0, 0));

    }

    /**
     * handler
     */

    private void handlerInitCpts() {
        Integer nbSuscribers = this.mUser.getFollows().size();
        Integer nbTwits = this.mDatabase.getUserTwits(ProfileComponent.this.mUser).size();

        Set<String> followers = new HashSet<>();
        for (User user : this.mDatabase.getUsers()) {
            if(user.getFollows().contains(this.mUser.getUserTag())) {
                followers.add(user.getUserTag());
            }
        }
        Integer nbFollowers = followers.size();

        this.nbTwitsLabel.setText(nbTwits.toString());
        this.nbSuscribersLabel.setText(nbSuscribers.toString());
        this.nbFollowerLabel.setText(nbFollowers.toString());
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
