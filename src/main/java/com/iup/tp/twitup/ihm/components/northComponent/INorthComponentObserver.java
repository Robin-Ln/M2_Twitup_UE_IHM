package com.iup.tp.twitup.ihm.components.northComponent;

public interface INorthComponentObserver {
    void notifyRequestConnexion();

    void notifyRequestLogout();

    void notifySearchRequest(String search);

    void notifyRequestInscription();
}
