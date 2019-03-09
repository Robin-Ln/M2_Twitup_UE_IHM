package com.iup.tp.twitup.ihm;

import com.iup.tp.twitup.datamodel.User;

import java.io.File;

public abstract class TwitupMainViewAdapter implements ITwitupMainViewObserver {

    @Override
    public void notifyEchangeDirectoryChange(File file) {

    }

    @Override
    public void notifySuccessConnexion(User user) {

    }

    @Override
    public void notifyRememberUser(User user, Boolean remember) {

    }
}
