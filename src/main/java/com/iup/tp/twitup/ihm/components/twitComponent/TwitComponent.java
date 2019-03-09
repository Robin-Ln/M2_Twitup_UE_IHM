package com.iup.tp.twitup.ihm.components.twitComponent;

import com.iup.tp.twitup.core.EntityManager;
import com.iup.tp.twitup.datamodel.Twit;
import com.iup.tp.twitup.datamodel.User;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

public class TwitComponent extends JPanel implements ITwitComponent {

    private Twit twit;

    private JPanel contenue;

    private JPanel actions;

    /**
     * Liste des observateurs de modifications de la base.
     */
    private final Set<ITwitComponentObserver> mObservers;

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

    private void init() {
        this.setBackground(new Color(50,150,200,70));
        this.setBorder(new LineBorder(Color.GREEN, 4, true));
        this.setLayout(new GridBagLayout());

        /**
         * Contenue
         */

        this.contenue = new JPanel();
        this.contenue.setBorder(new LineBorder(Color.BLUE, 4, true));
        this.contenue.setLayout(new GridBagLayout());

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

        JButton deletebutton = new JButton(this.mBundle.getString("button.twit.component.delete.libelle"));
        deletebutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TwitComponent.this.removeAll();
                TwitComponent.this.revalidate();
                TwitComponent.this.repaint();
                for (ITwitComponentObserver observer : TwitComponent.this.mObservers) {
                    observer.notifyDeleteTwitComponent(TwitComponent.this);
                }

            }
        });

        JButton followbutton = new JButton(this.mBundle.getString("button.twit.component.follow.libelle"));
        followbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TwitComponent.this.mUser.addFollowing(TwitComponent.this.twit.getTwiter().getUserTag());
                TwitComponent.this.mEntiteManager.sendUser(TwitComponent.this.mUser);
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

    @Override
    public void addObserver(ITwitComponentObserver observer) {
        this.mObservers.add(observer);
    }

    @Override
    public void deleteObserver(ITwitComponentObserver observer) {
        this.mObservers.remove(observer);
    }
}
