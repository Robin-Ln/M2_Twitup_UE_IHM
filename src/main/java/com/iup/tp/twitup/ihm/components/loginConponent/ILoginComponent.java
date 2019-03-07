package com.iup.tp.twitup.ihm.components.loginConponent;

public interface ILoginComponent {

    /**
     * Ajoute un observateur sur les modifications de la vue.
     *
     * @param observer
     */
    void addObserver(ILoginComponentObserver observer);

    /**
     * Supprime un observateur sur les modifications de la vue.
     *
     * @param observer
     */
    void deleteObserver(ILoginComponentObserver observer);
}
