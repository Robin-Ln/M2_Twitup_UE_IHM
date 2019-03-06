package com.iup.tp.twitup.ihm;

import java.io.File;

public interface ITwitupMainViewObserver {

    void notifyEchangeDirectoryChange(File file);

    void notifyWindowClosing(ITwitupMainView observable);

    void notifyRequestUserConnexion(String name, char[] password, Integer nbConnexion);

}
