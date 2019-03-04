package com.iup.tp.twitup.ihm;

import java.io.File;

public interface ITwitupMainViewObserver {

    void notifyEchangeDirectoryChange(File file);

    void notifyWindowClosing(ITwitupMainView observable);

}
