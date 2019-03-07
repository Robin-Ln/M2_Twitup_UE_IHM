package com.iup.tp.twitup.ihm.components.northLogedComponent;

public interface INorthLogedComponent {

    /**
     * Ajoute un observateur sur les modifications de la vue.
     *
     * @param observer
     */
    void addObserver(INorthLogedComponentObserver observer);

    /**
     * Supprime un observateur sur les modifications de la vue.
     *
     * @param observer
     */
    void deleteObserver(INorthLogedComponentObserver observer);


}
