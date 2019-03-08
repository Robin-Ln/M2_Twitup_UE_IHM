package com.iup.tp.twitup.ihm.components.northComponent;

import com.iup.tp.twitup.datamodel.User;

public abstract class NorthComponentAdapter implements INorthComponentObserver {
    @Override
    public void notifyRequestConnexion() {

    }

    @Override
    public void notifyRequestLogout() {

    }

    @Override
    public void notifySearchRequest(String search) {

    }

    @Override
    public void notifyRequestInscription() {

    }

    @Override
    public void notifySuccessConnexion(User user) {

    }
}
