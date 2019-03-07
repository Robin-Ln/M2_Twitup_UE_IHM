package com.iup.tp.twitup.ihm.components.northLogedComponent;

public interface INorthLogedComponentObserver {

    void notifyRequestLogout();

    void notifySearchRequest(String search);
}
