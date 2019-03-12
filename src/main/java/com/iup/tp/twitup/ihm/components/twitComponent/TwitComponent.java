package com.iup.tp.twitup.ihm.components.twitComponent;

import com.iup.tp.twitup.core.EntityManager;
import com.iup.tp.twitup.datamodel.Twit;
import com.iup.tp.twitup.datamodel.User;
import com.iup.tp.twitup.ihm.ImagePanel;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

public class TwitComponent extends JPanel implements ITwitComponent {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
     * Liste des observateurs de modifications de la base.
     */
    private final Set<ITwitComponentObserver> mObservers;
    private Twit twit;
    private JPanel contenue;
    private JPanel actions;
    /**
     * Configurer la langue de l'aplication
     */
    private ResourceBundle mBundle;

    private User mUser;

    private EntityManager mEntiteManager;

    public TwitComponent(Twit twit, User user, ResourceBundle bundle, EntityManager entityManager) {
        this.mObservers = new HashSet<>();

        this.twit = twit;
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

        JPanel image = ImagePanel.getUserImage(this.twit.getTwiter());
        this.contenue.add(image,
                new GridBagConstraints(0, 0, 1, 2, 0, 0,
                        GridBagConstraints.NORTHWEST,
                        GridBagConstraints.NONE,
                        new Insets(5, 5, 0, 5), 0, 0));

        JLabel userTag = new JLabel(this.twit.getTwiter().getUserTag());
        this.contenue.add(userTag,
                new GridBagConstraints(1, 0, 1, 1, 0, 0,
                        GridBagConstraints.NORTHWEST,
                        GridBagConstraints.NONE,
                        new Insets(5, 5, 0, 5), 0, 0));


        JLabel twitText = new JLabel(this.twit.getText());
        this.contenue.add(twitText,
                new GridBagConstraints(1, 1, 1, 1, 0, 0,
                        GridBagConstraints.NORTHWEST,
                        GridBagConstraints.NONE,
                        new Insets(5, 5, 0, 5), 0, 0));

        /**
         * Actions
         */
        this.actions = new JPanel();
        this.actions.setLayout(new GridBagLayout());

        this.actions.setBackground(Color.WHITE);


        JButton followbutton = new JButton(this.mBundle.getString("button.twit.component.follow.libelle"));
        JButton deletebutton = new JButton(this.mBundle.getString("button.twit.component.delete.libelle"));

        if (this.mUser.getFollows().contains(this.twit.getTwiter().getUserTag())) {
            followbutton.setVisible(false);
            deletebutton.setVisible(true);
        } else {
            followbutton.setVisible(true);
            deletebutton.setVisible(false);
        }

        followbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TwitComponent.this.mUser.addFollowing(TwitComponent.this.twit.getTwiter().getUserTag());
                TwitComponent.this.mEntiteManager.sendUser(TwitComponent.this.mUser);
                followbutton.setVisible(false);
                deletebutton.setVisible(true);
            }
        });

        deletebutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TwitComponent.this.mUser.removeFollowing(TwitComponent.this.twit.getTwiter().getUserTag());
                TwitComponent.this.mEntiteManager.sendUser(TwitComponent.this.mUser);
                followbutton.setVisible(true);
                deletebutton.setVisible(false);
            }
        });


        this.actions.add(deletebutton,
                new GridBagConstraints(0, 0, 1, 1, 0, 0,
                        GridBagConstraints.NORTHWEST,
                        GridBagConstraints.NONE,
                        new Insets(5, 5, 0, 5), 0, 0));

        this.actions.add(followbutton,
                new GridBagConstraints(0, 1, 1, 1, 0, 0,
                        GridBagConstraints.NORTHWEST,
                        GridBagConstraints.NONE,
                        new Insets(5, 5, 0, 5), 0, 0));


        /**
         * ajouts des action et du contenue au twit
         */


        this.add(this.contenue,
                new GridBagConstraints(0, 0, 1, 1, 1, 0,
                        GridBagConstraints.NORTHWEST,
                        GridBagConstraints.HORIZONTAL,
                        new Insets(5, 5, 0, 5), 0, 0));

        this.add(this.actions,
                new GridBagConstraints(1, 0, 1, 1, 0, 0,
                        GridBagConstraints.NORTHWEST,
                        GridBagConstraints.NONE,
                        new Insets(5, 5, 0, 5), 0, 0));
    }


    public Twit getTwit() {
        return twit;
    }

    public void setTwit(Twit twit) {
        this.twit = twit;
    }

    @Override
    public void addObserver(ITwitComponentObserver observer) {
        this.mObservers.add(observer);
    }

    @Override
    public void deleteObserver(ITwitComponentObserver observer) {
        this.mObservers.remove(observer);
    }
}
