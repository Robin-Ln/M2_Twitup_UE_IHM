package com.iup.tp.twitup.ihm.components.northLogoutComponent;

import com.iup.tp.twitup.datamodel.User;

public abstract class NorthLogoutComponentAdapter implements INorthLogoutComponentObserver {
    @Override
    public void notifyRequestConnexion() {

    }

    @Override
    public void notifyRequestInscription() {

    }

    @Override
    public void notifySuccessConnexion(User user, Boolean remember) {

    }
}
