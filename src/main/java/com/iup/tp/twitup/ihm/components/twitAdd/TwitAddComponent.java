package com.iup.tp.twitup.ihm.components.twitAdd;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.HashSet;
import java.util.Set;

public class TwitAddComponent extends JPanel implements ITwitAddComponent {

    /**
     * Liste des observateurs de modifications de la base.
     */
    private final Set<ITwitAddComponentObserver> mObservers;


    public TwitAddComponent() {
        super();

        this.mObservers = new HashSet<>();

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
        this.setOpaque(true);
        this.setBackground(new Color(50,150,200,70));
        this.setBorder(new LineBorder(Color.CYAN, 4,true));
        this.setPreferredSize(new Dimension(240,50));
    }
}
