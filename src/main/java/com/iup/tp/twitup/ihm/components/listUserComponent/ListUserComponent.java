package com.iup.tp.twitup.ihm.components.listUserComponent;

import com.iup.tp.twitup.core.EntityManager;
import com.iup.tp.twitup.datamodel.DatabaseAdapter;
import com.iup.tp.twitup.datamodel.IDatabase;
import com.iup.tp.twitup.datamodel.Twit;
import com.iup.tp.twitup.datamodel.User;
import com.iup.tp.twitup.ihm.components.twitComponent.TwitComponent;
import com.iup.tp.twitup.ihm.components.userComponent.UserComponent;

import javax.swing.*;
import java.awt.*;
import java.util.*;

public class ListUserComponent extends JPanel implements IListUserComponent {


    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
     * Liste des observateurs de modifications de la base.
     */
    private final Set<IListUserComponentObserver> mObservers;

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
    private Map<User,UserComponent> users;

    /**
     * Base de donénes de l'application.
     */
    private IDatabase mDatabase;

    private Integer nbUser;

    public ListUserComponent(ResourceBundle bundle,IDatabase database, EntityManager entityManager) {
        this.mObservers = new HashSet<>();
        this.mDatabase = database;
        this.mEntityManager = entityManager;
        this.mBundle = bundle;
        this.nbUser = 0;
        this.users = new HashMap<>();
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


        this.handlerUpdateListUser(this.mDatabase.getUsers());

        this.mDatabase.addObserver(new DatabaseAdapter() {

            @Override
            public void notifyUserAdded(User addedUser) {
                ListUserComponent.this.handlerAddUser(addedUser);
            }

            @Override
            public void notifyUserDeleted(User deletedUser) {
                ListUserComponent.this.handlerDeleteUser(deletedUser);
            }

            @Override
            public void notifyUserModified(User modifiedUser) {
                ListUserComponent.this.handlerUpdateUser(modifiedUser);
            }
        });
    }

    /**
     * Handler
     */


    private void handlerUpdateListUser(Set<User> users) {
        this.contenu.removeAll();
        for (User user : users) {
            this.handlerAddUser(user);
        }
        this.revalidate();
        this.repaint();
    }

    private void handlerAddUser(User user) {

        if (this.users.containsKey(user)) {
            return;
        }

        UserComponent userComponent = new UserComponent(user, this.mBundle, this.mEntityManager);
        this.users.put(user, userComponent);
        this.nbUser++;
        this.contenu.add(userComponent,
                new GridBagConstraints(0, this.nbUser, 1, 1, 1, 0,
                        GridBagConstraints.NORTH,
                        GridBagConstraints.BOTH,
                        new Insets(5, 5, 5, 5), 0, 0));

        this.revalidate();
    }

    private void handlerDeleteUser(User user) {
        // TODO ne marche pas très bien
        UserComponent userComponent = this.users.get(user);
        userComponent.removeAll();
        this.users.remove(user);
        this.contenu.remove(userComponent);
        this.contenu.revalidate();
        this.contenu.repaint();
    }

    private void handlerUpdateUser(User user) {
        UserComponent userComponent = this.users.get(user);
        this.users.remove(user);
        userComponent.removeAll();
        userComponent.setmUser(user);
        userComponent.init();
        this.revalidate();
    }

    /**
     * Les methodes de mon interfaces
     */
    @Override
    public void addObserver(IListUserComponentObserver observer) {
        this.mObservers.add(observer);
    }

    @Override
    public void deleteObserver(IListUserComponentObserver observer) {
        this.mObservers.remove(observer);
    }

}
