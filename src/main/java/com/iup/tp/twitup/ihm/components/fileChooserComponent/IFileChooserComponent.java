package com.iup.tp.twitup.ihm.components.fileChooserComponent;

public interface IFileChooserComponent {

    /**
     * Ajoute un observateur sur les modifications de la vue.
     *
     * @param observer
     */
    void addObserver(IFileChooserComponentObserver observer);

    /**
     * Supprime un observateur sur les modifications de la vue.
     *
     * @param observer
     */
    void deleteObserver(IFileChooserComponentObserver observer);

}
