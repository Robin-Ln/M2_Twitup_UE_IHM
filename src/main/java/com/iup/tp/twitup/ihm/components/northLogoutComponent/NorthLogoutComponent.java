package com.iup.tp.twitup.ihm.components.northLogoutComponent;

import com.iup.tp.twitup.core.EntityManager;
import com.iup.tp.twitup.datamodel.IDatabase;
import com.iup.tp.twitup.datamodel.User;
import com.iup.tp.twitup.ihm.components.inscriptionComponent.InscriptionComponent;
import com.iup.tp.twitup.ihm.components.inscriptionComponent.InscriptionComponentAdapter;
import com.iup.tp.twitup.ihm.components.loginConponent.ILoginComponentObserver;
import com.iup.tp.twitup.ihm.components.loginConponent.LoginComponent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

public class NorthLogoutComponent extends JPanel implements INorthLogoutComponent {


    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
     * observer
     */
    private final Set<INorthLogoutComponentObserver> mObservers;
    /**
     * Configurer la langue de l'aplication
     */
    private ResourceBundle mBundle;
    /**
     * Base de données.
     */
    private IDatabase mDatabase;
    /**
     * Gestionnaire de bdd et de fichier.
     */
    private EntityManager mEntityManager;

    public NorthLogoutComponent(IDatabase database, EntityManager entityManager, ResourceBundle bundle) {
        super();
        this.mObservers = new HashSet<>();
        this.mBundle = bundle;
        this.mDatabase = database;
        this.mEntityManager = entityManager;
        this.init();
    }

    private void init() {

        this.setBackground(new Color(144,193,217));

        /**
         * Bouton de s'inscrire
         */
        JButton suscribeButton = new JButton(this.mBundle.getString("button.inscription.libelle"));
        suscribeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                NorthLogoutComponent.this.handlerRequestInscription();
            }
        });

        /**
         * Bouton de connexion
         */
        JButton connexionButton = new JButton(this.mBundle.getString("button.connexion.libelle"));
        connexionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                NorthLogoutComponent.this.handlerRequestUserConnexion(0);
            }
        });


        /**
         * Layout
         */
        this.setLayout(new GridBagLayout());

        this.add(suscribeButton,
                new GridBagConstraints(0, 0, 1, 1, 0, 0,
                        GridBagConstraints.EAST,
                        GridBagConstraints.NONE,
                        new Insets(5, 5, 0, 5), 0, 0));

        this.add(connexionButton,
                new GridBagConstraints(1, 0, 1, 1, 0, 0,
                        GridBagConstraints.EAST,
                        GridBagConstraints.NONE,
                        new Insets(5, 5, 0, 5), 0, 0));

    }

    private void handlerRequestInscription() {
        InscriptionComponent inscriptionComponent = new InscriptionComponent(this.mBundle, this.mDatabase, this.mEntityManager);
        inscriptionComponent.addObserver(new InscriptionComponentAdapter() {
        });
        inscriptionComponent.showGUI();
    }

    /**
     * Interface observer
     */
    @Override
    public void addObserver(INorthLogoutComponentObserver observer) {
        this.mObservers.add(observer);
    }

    @Override
    public void deleteObserver(INorthLogoutComponentObserver observer) {
        this.mObservers.remove(observer);
    }

    /**
     * Handler
     */

    /**
     * Handler
     */
    public void handlerRequestUserConnexion(Integer nbConnexion) {

        LoginComponent loginComponent = new LoginComponent(this.mBundle, nbConnexion);
        loginComponent.addObserver(new ILoginComponentObserver() {
            @Override
            public void notifyRequestUserConnexion(String name, char[] password, Integer nbConnexion, Boolean remember) {
                NorthLogoutComponent.this.handlerUserConnexion(name, password, nbConnexion, remember);
            }

            @Override
            public void notifySelectCanceled() {
                loginComponent.deleteObserver(this);
            }
        });

        loginComponent.show();

    }

    public void handlerUserConnexion(String name, char[] password, Integer nbConnexion, Boolean remember) {
        Set<User> users = this.mDatabase.getUsers();
        for (User user : users) {
            if (user.getName().equals(name) && user.getUserPassword().equals(new String(password))) {
                this.handlerSuccessConnexion(user, remember);
                return;
            }
        }
        this.handlerRequestUserConnexion(++nbConnexion);
    }

    private void handlerSuccessConnexion(User user, Boolean remember) {
        for (INorthLogoutComponentObserver observer : this.mObservers) {
            observer.notifySuccessConnexion(user, remember);
        }
    }
}
