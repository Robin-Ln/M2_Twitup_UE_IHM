package com.iup.tp.twitup.ihm.components.userComponent;

import com.iup.tp.twitup.core.EntityManager;
import com.iup.tp.twitup.datamodel.Twit;
import com.iup.tp.twitup.datamodel.User;
import com.iup.tp.twitup.ihm.ImagePanel;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

public class UserComponent extends JPanel implements IUserComponent {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
     * Liste des observateurs de modifications de la base.
     */
    private final Set<IUserComponentObserver> mObservers;
    private JPanel contenue;
    /**
     * Configurer la langue de l'aplication
     */
    private ResourceBundle mBundle;

    private User mUser;

    private EntityManager mEntiteManager;

    public UserComponent(User user, ResourceBundle bundle, EntityManager entityManager) {
        this.mObservers = new HashSet<>();
        this.mUser = user;
        this.mBundle = bundle;
        this.mEntiteManager = entityManager;
        this.init();
    }

    public void init() {

        Color color = Color.WHITE;
        this.setBackground(color);
        this.setBorder(new LineBorder(color, 1, true));
        this.setOpaque(true);

        this.setLayout(new GridBagLayout());

        /**
         * Contenue
         */

        this.contenue = new JPanel();
        this.contenue.setLayout(new GridBagLayout());

        this.contenue.setBackground(Color.WHITE);

        JPanel image = ImagePanel.getUserImage(this.mUser);
        this.contenue.add(image,
                new GridBagConstraints(0, 0, 1, 2, 0, 0,
                        GridBagConstraints.NORTHWEST,
                        GridBagConstraints.NONE,
                        new Insets(5, 5, 0, 5), 0, 0));

        JLabel userTag = new JLabel(this.mUser.getUserTag());
        this.contenue.add(userTag,
                new GridBagConstraints(1, 0, 1, 1, 0, 0,
                        GridBagConstraints.NORTHWEST,
                        GridBagConstraints.NONE,
                        new Insets(5, 5, 0, 5), 0, 0));




        /**
         * ajouts des action et du contenue au user
         */


        this.add(this.contenue,
                new GridBagConstraints(0, 0, 1, 1, 1, 0,
                        GridBagConstraints.NORTHWEST,
                        GridBagConstraints.HORIZONTAL,
                        new Insets(5, 5, 0, 5), 0, 0));
    }


    public User getmUser() {
        return mUser;
    }

    public void setmUser(User mUser) {
        this.mUser = mUser;
    }

    @Override
    public void addObserver(IUserComponentObserver observer) {
        this.mObservers.add(observer);
    }

    @Override
    public void deleteObserver(IUserComponentObserver observer) {
        this.mObservers.remove(observer);
    }
}
