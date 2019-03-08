package com.iup.tp.twitup.ihm.components.northLogoutComponent;

import com.iup.tp.twitup.datamodel.User;

public interface INorthLogoutComponentObserver {

    void notifyRequestConnexion();

    void notifyRequestInscription();

    void notifySuccessConnexion(User user);
}
