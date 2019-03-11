package com.iup.tp.twitup.ihm.components.listUserComponent;

public interface IListUserComponent {
    /**
     * Ajoute un observateur sur les modifications de la vue.
     *
     * @param observer
     */
    void addObserver(IListUserComponentObserver observer);

    /**
     * Supprime un observateur sur les modifications de la vue.
     *
     * @param observer
     */
    void deleteObserver(IListUserComponentObserver observer);
}
