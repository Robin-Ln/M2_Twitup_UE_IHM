package com.iup.tp.twitup.ihm.components.notificationComponent;

import com.iup.tp.twitup.datamodel.DatabaseAdapter;
import com.iup.tp.twitup.datamodel.IDatabase;
import com.iup.tp.twitup.datamodel.Twit;
import com.iup.tp.twitup.datamodel.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

public class NotificationComponent extends JPanel implements INotificationComponent {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     * Liste des observateurs de modifications de la base.
     */
    private final Set<INotificationComponentObserver> mObservers;

    private ResourceBundle mBundle;

    private IDatabase mDatabase;

    private User mUser;

    private Set<Twit> twits;

    private JLabel notificationCpt;

    private Integer compteur;

    public NotificationComponent(ResourceBundle bundle, IDatabase database, User user) {
        this.mObservers = new HashSet<>();
        this.mBundle = bundle;
        this.mDatabase = database;
        this.compteur = 0;
        this.twits = new HashSet<>();
        this.mUser = user;
        this.init();
    }

    private void init() {

        this.setLayout(new GridBagLayout());

        this.notificationCpt = new JLabel();

        JButton button = new JButton(this.mBundle.getString("label.notification.libelle"));
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                NotificationComponent.this.handlerDisplayNotification();
            }
        });

        this.add(this.notificationCpt,
                new GridBagConstraints(0, 0, 1, 1, 0, 0,
                        GridBagConstraints.NORTH,
                        GridBagConstraints.NONE,
                        new Insets(5, 5, 0, 5), 0, 0));

        this.add(button,
                new GridBagConstraints(1, 0, 1, 1, 0, 0,
                        GridBagConstraints.NORTH,
                        GridBagConstraints.NONE,
                        new Insets(5, 5, 0, 5), 0, 0));

        this.mDatabase.addObserver(new DatabaseAdapter() {
            @Override
            public void notifyTwitAdded(Twit addedTwit) {
                NotificationComponent.this.handlerNewTwit(addedTwit);
            }
        });
    }

    private void handlerDisplayNotification() {
        for (INotificationComponentObserver observer : this.mObservers) {
            observer.notifyDisplayNotification(this.twits);
        }
        this.handlerClearNotification();
    }

    private void handlerClearNotification() {
        this.compteur = 0;
        this.notificationCpt.setText(this.compteur.toString());
        this.twits.clear();
    }

    private void handlerNewTwit(Twit twit){

        if (this.mUser.getFollows().contains(twit.getTwiter().getUserTag())) {
            this.compteur ++;
            this.notificationCpt.setText(this.compteur.toString());
            this.twits.add(twit);
        }
    }

    @Override
    public void addObserver(INotificationComponentObserver observer) {
        this.mObservers.add(observer);
    }

    @Override
    public void deleteObserver(INotificationComponentObserver observer) {
        this.mObservers.remove(observer);
    }
}
