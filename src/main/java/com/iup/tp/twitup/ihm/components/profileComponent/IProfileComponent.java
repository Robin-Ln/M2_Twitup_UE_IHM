package com.iup.tp.twitup.ihm.components.profileComponent;

public interface IProfileComponent {
    /**
     * Ajoute un observateur sur les modifications de la vue.
     *
     * @param observer
     */
    void addObserver(IProfileComponentObserver observer);

    /**
     * Supprime un observateur sur les modifications de la vue.
     *
     * @param observer
     */
    void deleteObserver(IProfileComponentObserver observer);
}
