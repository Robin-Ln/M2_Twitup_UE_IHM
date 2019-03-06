package com.iup.tp.twitup.ihm.components.northComponent;

public interface INorthComponent {

    /**
     * Ajoute un observateur sur les modifications de la vue.
     *
     * @param observer
     */
    void addObserver(INorthComponentObserver observer);

    /**
     * Supprime un observateur sur les modifications de la vue.
     *
     * @param observer
     */
    void deleteObserver(INorthComponentObserver observer);


}
