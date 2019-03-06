package com.iup.tp.twitup.ihm.components.listTwitComponent;

public interface IListTwitComponent {
    /**
     * Ajoute un observateur sur les modifications de la vue.
     *
     * @param observer
     */
    void addObserver(IListTwitComponentObserver observer);

    /**
     * Supprime un observateur sur les modifications de la vue.
     *
     * @param observer
     */
    void deleteObserver(IListTwitComponentObserver observer);
}
