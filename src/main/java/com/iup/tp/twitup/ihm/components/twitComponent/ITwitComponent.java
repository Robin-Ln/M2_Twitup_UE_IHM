package com.iup.tp.twitup.ihm.components.twitComponent;

public interface ITwitComponent {

    /**
     * Ajoute un observateur sur les modifications de la vue.
     *
     * @param observer
     */
    void addObserver(ITwitComponentObserver observer);

    /**
     * Supprime un observateur sur les modifications de la vue.
     *
     * @param observer
     */
    void deleteObserver(ITwitComponentObserver observer);


}
