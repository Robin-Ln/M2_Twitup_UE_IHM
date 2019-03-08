package com.iup.tp.twitup.ihm.components.inscriptionComponent;

public interface IInscriptionComponent {

    /**
     * Ajoute un observateur sur les modifications de la vue.
     *
     * @param observer
     */
    void addObserver(IInscriptionComponentObserver observer);

    /**
     * Supprime un observateur sur les modifications de la vue.
     *
     * @param observer
     */
    void deleteObserver(IInscriptionComponentObserver observer);
}
