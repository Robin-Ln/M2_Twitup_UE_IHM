package com.iup.tp.twitup.ihm.components.northLogedComponent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

public class NorthLogedComponent extends JPanel implements INorthLogedComponent {


    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
     * observer
     */
    private final Set<INorthLogedComponentObserver> mObservers;
    /**
     * Configurer la langue de l'aplication
     */
    private ResourceBundle mBundle;

    public NorthLogedComponent(ResourceBundle bundle) {
        super();
        this.mObservers = new HashSet<>();
        this.mBundle = bundle;
        this.init();
    }

    private void init() {
        /**
         * Barre de recherche
         */
        JTextField searchTextField = new JTextField();

        /**
         * Bouton de recherche
         */
        JButton searchButton = new JButton(this.mBundle.getString("button.rechercher.libelle"));
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (INorthLogedComponentObserver observer : NorthLogedComponent.this.mObservers) {
                    observer.notifySearchRequest(searchTextField.getText());
                }
            }
        });

        /**
         * Bouton de deconnexion
         */
        JButton logoutButton = new JButton(this.mBundle.getString("button.logout.libelle"));
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (INorthLogedComponentObserver observer : NorthLogedComponent.this.mObservers) {
                    observer.notifyRequestLogout();
                }
            }
        });

        /**
         * Layout
         */
        this.setLayout(new GridBagLayout());

        this.add(searchTextField,
                new GridBagConstraints(0, 0, 1, 1, 1, 0,
                        GridBagConstraints.NORTH,
                        GridBagConstraints.HORIZONTAL,
                        new Insets(5, 5, 0, 5), 0, 0));

        this.add(searchButton,
                new GridBagConstraints(1, 0, 1, 1, 0, 0,
                        GridBagConstraints.NORTH,
                        GridBagConstraints.NONE,
                        new Insets(5, 5, 0, 5), 0, 0));

        this.add(logoutButton,
                new GridBagConstraints(2, 0, 1, 1, 0, 0,
                        GridBagConstraints.NORTH,
                        GridBagConstraints.NONE,
                        new Insets(5, 5, 0, 5), 0, 0));

    }

    /**
     * Interface observer
     */
    @Override
    public void addObserver(INorthLogedComponentObserver observer) {
        this.mObservers.add(observer);
    }

    @Override
    public void deleteObserver(INorthLogedComponentObserver observer) {
        this.mObservers.remove(observer);
    }
}
