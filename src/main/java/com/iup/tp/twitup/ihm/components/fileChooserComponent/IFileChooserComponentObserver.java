package com.iup.tp.twitup.ihm.components.fileChooserComponent;

import java.io.File;

public interface IFileChooserComponentObserver {

    void notifyFileSelected(File file);

    void notifySelectCanceled();

}
