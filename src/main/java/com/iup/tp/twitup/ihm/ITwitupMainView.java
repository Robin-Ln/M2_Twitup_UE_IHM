package com.iup.tp.twitup.ihm;

public interface ITwitupMainView {

    /**
     * Ajoute un observateur sur les modifications de la vue.
     *
     * @param observer
     */
    void addObserver(ITwitupMainViewObserver observer);

    /**
     * Supprime un observateur sur les modifications de la vue.
     *
     * @param observer
     */
    void deleteObserver(ITwitupMainViewObserver observer);


}
