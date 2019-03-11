package com.iup.tp.twitup.ihm.components.userComponent;

public interface IUserComponent {

    /**
     * Ajoute un observateur sur les modifications de la vue.
     *
     * @param observer
     */
    void addObserver(IUserComponentObserver observer);

    /**
     * Supprime un observateur sur les modifications de la vue.
     *
     * @param observer
     */
    void deleteObserver(IUserComponentObserver observer);


}
