package com.iup.tp.twitup.ihm.components.northLogoutComponent;

public interface INorthLogoutComponent {

    /**
     * Ajoute un observateur sur les modifications de la vue.
     *
     * @param observer
     */
    void addObserver(INorthLogoutComponentObserver observer);

    /**
     * Supprime un observateur sur les modifications de la vue.
     *
     * @param observer
     */
    void deleteObserver(INorthLogoutComponentObserver observer);


}
