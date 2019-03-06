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

    /**
     * Nonbre de twit
     */
    private Integer nbTwit;


    /**
     * Contenue de la liste
     */
    JPanel contenu;

    public ListTwitComponent(Set<Twit> twits) {

        this.mObservers = new HashSet<>();
        this.nbTwit = 0;
        this.init(twits);

    }

    private void init(Set<Twit> twits){
        this.setLayout(new GridBagLayout());
        JScrollPane scrollPane = new JScrollPane();

        this.contenu = new JPanel(new GridBagLayout());
        scrollPane.getViewport().add(this.contenu);

        this.add(scrollPane, new GridBagConstraints(0, 0, 1, 1, 1, 1,
                        GridBagConstraints.NORTH,
                        GridBagConstraints.BOTH,
                        new Insets(5, 5, 0, 5), 0, 0));


        for (Twit twit : twits) {
            this.handlerAddTwit(twit);
        }
    }

    private void handlerAddTwit(Twit twit){
        TwitComponent twitComponent = new TwitComponent(twit);
        this.nbTwit ++;
        this.contenu.add(twitComponent,
                new GridBagConstraints(0, this.nbTwit, 1, 1, 1, 0,
                        GridBagConstraints.NORTH,
                        GridBagConstraints.HORIZONTAL,
                        new Insets(5, 5, 0, 5), 0, 0));
    }

    /**
     * Methode de l'observer ITwitAddComponentObserver
     */
    @Override
    public void notifyNewTwit(Twit twit) {
        this.handlerAddTwit(twit);
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
