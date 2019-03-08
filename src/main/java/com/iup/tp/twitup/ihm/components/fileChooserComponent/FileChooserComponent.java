package com.iup.tp.twitup.ihm.components.fileChooserComponent;

import com.iup.tp.twitup.ihm.ITwitupMainViewObserver;
import com.iup.tp.twitup.ihm.components.inscriptionComponent.IInscriptionComponentObserver;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

public class FileChooserComponent implements IFileChooserComponent {

    /**
     * Liste des observateurs de modifications de la base.
     */
    private final Set<IFileChooserComponentObserver> mObservers;

    /**
     * Ressource bundle de l'application
     */
    ResourceBundle mBundle;


    /**
     * Constructeurs
     */

    public FileChooserComponent(ResourceBundle bundle) {
        this.mObservers = new HashSet<>();
        this.mBundle = bundle;
    }

    /**
     * Méthodes
     */
    public void show(){
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle(this.mBundle.getString("dialog.file.choser.tilte"));
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int returnVal = chooser.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            for (IFileChooserComponentObserver observer : this.mObservers) {
                observer.notifyFileSelected(chooser.getSelectedFile());
            }
        } else {
            for (IFileChooserComponentObserver observer : this.mObservers) {
                observer.notifySelectCanceled();
            }
        }
    }


    /**
     * Méthode de l'interface IFileChooserComponent
     */

    @Override
    public void addObserver(IFileChooserComponentObserver observer) {
        this.mObservers.add(observer);
    }

    @Override
    public void deleteObserver(IFileChooserComponentObserver observer) {
        this.mObservers.remove(observer);
    }
}
