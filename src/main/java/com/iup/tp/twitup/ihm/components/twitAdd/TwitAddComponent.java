package com.iup.tp.twitup.ihm.components.twitAdd;

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

public class TwitAddComponent extends JPanel implements ITwitAddComponent {

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


    public TwitAddComponent(ResourceBundle bundle, User user) {
        super();

        this.mObservers = new HashSet<>();

        this.mBundle = bundle;

        this.mUser = user;

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
    private void init(){

        this.setBackground(new Color(50,150,200,70));
        this.setBorder(new LineBorder(Color.CYAN, 4,true));

        /**
         * text area
         */
        TextArea textArea = new TextArea();
        this.add(textArea);


        /**
         * Button ajouter
         */
        JButton ajouterButton = new JButton(this.mBundle.getString("button.ajouter.twit.libelle"));
        ajouterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (ITwitAddComponentObserver observer : TwitAddComponent.this.mObservers) {
                    Twit twit = new Twit(TwitAddComponent.this.mUser, textArea.getText());
                    observer.notifyNewTwit(twit);
                }
            }
        });
        this.add(ajouterButton);
    }

}
