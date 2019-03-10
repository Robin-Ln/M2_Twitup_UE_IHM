package com.iup.tp.twitup.ihm.components.twitAdd;

import com.iup.tp.twitup.core.EntityManager;
import com.iup.tp.twitup.datamodel.Twit;
import com.iup.tp.twitup.datamodel.User;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

public class TwitAddComponent extends JPanel implements ITwitAddComponent {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * Liste des observateurs de modifications de la base.
     */
    private final Set<ITwitAddComponentObserver> mObservers;

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


    public TwitAddComponent(ResourceBundle bundle, User user, EntityManager entityManager) {
        super();

        this.mObservers = new HashSet<>();

        this.mBundle = bundle;

        this.mUser = user;

        this.mEntityManager = entityManager;

        this.init();
    }

    @Override
    public void addObserver(ITwitAddComponentObserver observer) {
        this.mObservers.add(observer);
    }


    @Override
    public void deleteObserver(ITwitAddComponentObserver observer) {
        this.mObservers.remove(observer);
    }

    /**
     * Initialisation du composant
     */
    private void init() {

        this.setLayout(new GridBagLayout());

        Color color = new Color(229,242,247);
        this.setBackground(color);
        this.setBorder(new LineBorder(color, 1, true));
        this.setOpaque(true);

        /**
         * Labelle pas plus de 100 caractère
         */
        JLabel errorLabel = new JLabel(this.mBundle.getString("text.area.error"));
        errorLabel.setForeground(Color.RED);
        errorLabel.setVisible(false);

        /**
         * text area
         */
        JTextArea textArea = new JTextArea();
        textArea.setWrapStyleWord(true);
        textArea.setLineWrap(true);

        textArea.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (textArea.getText().length() < 250) {
                    errorLabel.setVisible(false);
                } else {
                    errorLabel.setVisible(true);
                }
            }
        });


        /**
         * Button ajouter
         */
        JButton ajouterButton = new JButton(this.mBundle.getString("button.ajouter.twit.libelle"));
        ajouterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (textArea.getText().length() < 250) {
                    Twit twit = new Twit(TwitAddComponent.this.mUser, textArea.getText());
                    TwitAddComponent.this.mEntityManager.sendTwit(twit);
                    textArea.setText("");
                }
            }
        });


        /**
         * Ajout des composant dans le layout
         */
        this.add(textArea, new GridBagConstraints(0, 0, 1, 1, 1, 1,
                GridBagConstraints.NORTH,
                GridBagConstraints.BOTH,
                new Insets(5, 5, 5, 5), 0, 0));


        this.add(errorLabel, new GridBagConstraints(0, 1, 1, 1, 1, 1,
                GridBagConstraints.NORTH,
                GridBagConstraints.BOTH,
                new Insets(5, 5, 0, 5), 0, 0));


        this.add(ajouterButton, new GridBagConstraints(1, 0, 1, 2, 0, 1,
                GridBagConstraints.NORTH,
                GridBagConstraints.BOTH,
                new Insets(5, 5, 0, 5), 0, 0));
    }

}
