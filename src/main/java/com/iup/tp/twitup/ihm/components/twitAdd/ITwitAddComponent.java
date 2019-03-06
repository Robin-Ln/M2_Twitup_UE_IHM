package com.iup.tp.twitup.ihm.components.twitAdd;

public interface ITwitAddComponent {

    /**
     * Ajoute un observateur sur les modifications de la vue.
     *
     * @param observer
     */
    void addObserver(ITwitAddComponentObserver observer);

    /**
     * Supprime un observateur sur les modifications de la vue.
     *
     * @param observer
     */
    void deleteObserver(ITwitAddComponentObserver observer);


}
