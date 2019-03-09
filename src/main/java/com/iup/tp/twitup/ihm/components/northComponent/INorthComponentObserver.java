package com.iup.tp.twitup.ihm.components.northComponent;

import com.iup.tp.twitup.datamodel.User;

public interface INorthComponentObserver {
    void notifyRequestConnexion();

    void notifyRequestLogout();

    void notifySearchRequest(String search);

    void notifyRequestInscription();

    void notifySuccessConnexion(User user,Boolean remember);
}
