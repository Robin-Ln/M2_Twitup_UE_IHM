package com.iup.tp.twitup.ihm.components.loginConponent;

public interface ILoginComponentObserver {

    void notifyRequestUserConnexion(String name, char[] password, Integer nbConnexion, Boolean remember);

    void notifySelectCanceled();

}
