package com.iup.tp.twitup.ihm.components.centerComponent;

public interface ICenterComponent {

    /**
     * Ajoute un observateur sur les modifications de la vue.
     *
     * @param observer
     */
    void addObserver(ICenterComponentObserver observer);

    /**
     * Supprime un observateur sur les modifications de la vue.
     *
     * @param observer
     */
    void deleteObserver(ICenterComponentObserver observer);


}
