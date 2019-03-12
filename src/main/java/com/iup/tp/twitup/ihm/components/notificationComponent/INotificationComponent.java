package com.iup.tp.twitup.ihm.components.notificationComponent;

public interface INotificationComponent {
    /**
     * Ajoute un observateur sur les modifications de la vue.
     *
     * @param observer
     */
    void addObserver(INotificationComponentObserver observer);

    /**
     * Supprime un observateur sur les modifications de la vue.
     *
     * @param observer
     */
    void deleteObserver(INotificationComponentObserver observer);
}
