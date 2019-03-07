package com.iup.tp.twitup.ihm.components.northLogoutComponent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

public class NorthLogoutComponent extends JPanel implements INorthLogoutComponent {


    /**
     * Configurer la langue de l'aplication
     */
    private ResourceBundle mBundle;

    /**
     * observer
     */
    private final Set<INorthLogoutComponentObserver> mObservers;

    public NorthLogoutComponent(ResourceBundle bundle) {
        super();
        this.mObservers = new HashSet<>();
        this.mBundle = bundle;
        this.init();
    }

    private void init() {

        /**
         * Bouton de s'inscrire
         */
        JButton siginButton = new JButton(this.mBundle.getString("button.inscription.libelle"));
        siginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (INorthLogoutComponentObserver observer : NorthLogoutComponent.this.mObservers) {
                }
            }
        });

        /**
         * Bouton de connexion
         */
        JButton connexionButton = new JButton(this.mBundle.getString("button.connexion.libelle"));
        connexionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (INorthLogoutComponentObserver observer : NorthLogoutComponent.this.mObservers) {
                    observer.notifyRequestConnexion();
                }
            }
        });


        /**
         * Layout
         */
        this.setLayout(new GridBagLayout());
        this.add(siginButton,
                new GridBagConstraints(0, 0, 1, 1, 0, 0,
                        GridBagConstraints.NORTHEAST,
                        GridBagConstraints.NONE,
                        new Insets(5, 5, 0, 5), 0, 0));

        this.add(connexionButton,
                new GridBagConstraints(1, 0, 1, 1, 0, 0,
                        GridBagConstraints.NORTHEAST,
                        GridBagConstraints.NONE,
                        new Insets(5, 5, 0, 5), 0, 0));

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
}
