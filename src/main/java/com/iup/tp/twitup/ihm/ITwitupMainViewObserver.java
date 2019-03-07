package com.iup.tp.twitup.ihm;

import com.iup.tp.twitup.datamodel.User;

import java.io.File;

public interface ITwitupMainViewObserver {

    void notifyEchangeDirectoryChange(File file);

    void notifyWindowClosing(ITwitupMainView observable);

    void notifySuccessConnexion(User user);

}
