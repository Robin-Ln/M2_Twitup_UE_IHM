package com.iup.tp.twitup.ihm.components.listTwitComponent;

import com.iup.tp.twitup.datamodel.Twit;
import com.iup.tp.twitup.ihm.components.twitAdd.ITwitAddComponentObserver;
import com.iup.tp.twitup.ihm.components.twitComponent.TwitComponent;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.HashSet;
import java.util.Set;

public class ListTwitComponent extends JPanel implements IListTwitComponent, ITwitAddComponentObserver {

    /**
     * Liste des observateurs de modifications de la base.
     */
    private final Set<IListTwitComponentObserver> mObservers;


    public ListTwitComponent(Set<Twit> twits) {

        this.mObservers = new HashSet<>();


        this.setBackground(new Color(50,150,200,70));
        this.setBorder(new LineBorder(Color.BLUE, 4,true));

        for (Twit twit : twits) {
            this.add(new TwitComponent(twit));
        }
    }

    /**
     * Methode de l'observer ITwitAddComponentObserver
     */
    @Override
    public void notifyNewTwit(Twit twit) {
        this.add(new TwitComponent(twit));
        for (IListTwitComponentObserver observer : this.mObservers) {
            observer.notifyTwitListUpdate();
        }
    }

    /**
     * Les methodes de mon interfaces
     */
    @Override
    public void addObserver(IListTwitComponentObserver observer) {
        this.mObservers.add(observer);
    }

    @Override
    public void deleteObserver(IListTwitComponentObserver observer) {
        this.mObservers.remove(observer);
    }
}
