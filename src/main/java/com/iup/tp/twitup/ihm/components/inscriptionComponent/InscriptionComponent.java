package com.iup.tp.twitup.ihm.components.inscriptionComponent;

import com.iup.tp.twitup.core.Twitup;
import com.iup.tp.twitup.datamodel.User;
import com.iup.tp.twitup.ihm.components.fileChooserComponent.FileChooserComponent;
import com.iup.tp.twitup.ihm.components.fileChooserComponent.IFileChooserComponentObserver;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.*;

public class InscriptionComponent implements IInscriptionComponent {

    /**
     * Configurer la langue de l'aplication
     */
    private ResourceBundle mBundle;

    /**
     * Nombre de tentavise de connexion
     */
    private Integer mNbConnexion;

    /**
     * Liste des observateurs de modifications de la base.
     */
    private final Set<IInscriptionComponentObserver> mObservers;

    /**
     * Constructeur
     */
    public InscriptionComponent(ResourceBundle bundle) {
        super();
        this.mBundle = bundle;
        this.mObservers = new HashSet<>();
    }

    /**
     * Methodes
     */
    public void show(JPanel parent) {
        JPanel dialogPanel = new JPanel();
        dialogPanel.setLayout(new GridBagLayout());

        JLabel tagLabel = new JLabel(this.mBundle.getString("dialog.inscription.label.tag"));
        JTextField tag = new JTextField();

        JLabel nameLabel = new JLabel(this.mBundle.getString("dialog.inscription.label.name"));
        JTextField name = new JTextField();

        JLabel passwordLabel = new JLabel(this.mBundle.getString("dialog.inscription.label.password"));
        JPasswordField password = new JPasswordField();

        JLabel imagePath = new JLabel();
        JButton imageButton  = new JButton(this.mBundle.getString("button.inscription.image.libelle"));
        imageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FileChooserComponent fileChooserComponent = new FileChooserComponent(InscriptionComponent.this.mBundle);
                fileChooserComponent.addObserver(new IFileChooserComponentObserver() {
                    @Override
                    public void notifyFileSelected(File file) {
                        imagePath.setText(file.getAbsolutePath());
                    }

                    @Override
                    public void notifySelectCanceled() {
                        fileChooserComponent.deleteObserver(this);
                    }
                });
                fileChooserComponent.show(JFileChooser.FILES_ONLY);
            }
        });


        /**
         * Ajout des composant
         */
        dialogPanel.add(tagLabel,
                new GridBagConstraints(0, 0, 1, 1, 0, 0,
                        GridBagConstraints.NORTHEAST,
                        GridBagConstraints.NONE,
                        new Insets(5, 5, 0, 5), 0, 0));

        dialogPanel.add(tag,
                new GridBagConstraints(1, 0, 1, 1, 1, 0,
                        GridBagConstraints.NORTH,
                        GridBagConstraints.BOTH,
                        new Insets(5, 5, 0, 5), 0, 0));

        dialogPanel.add(nameLabel,
                new GridBagConstraints(0, 1, 1, 1, 0, 0,
                        GridBagConstraints.NORTHEAST,
                        GridBagConstraints.NONE,
                        new Insets(5, 5, 0, 5), 0, 0));

        dialogPanel.add(name,
                new GridBagConstraints(1, 1, 1, 1, 1, 0,
                        GridBagConstraints.NORTH,
                        GridBagConstraints.BOTH,
                        new Insets(5, 5, 0, 5), 0, 0));

        dialogPanel.add(passwordLabel,
                new GridBagConstraints(0, 2, 1, 1, 0, 0,
                        GridBagConstraints.NORTHEAST,
                        GridBagConstraints.NONE,
                        new Insets(5, 5, 0, 5), 0, 0));

        dialogPanel.add(password,
                new GridBagConstraints(1, 2, 1, 1, 1, 0,
                        GridBagConstraints.NORTH,
                        GridBagConstraints.BOTH,
                        new Insets(5, 5, 0, 5), 0, 0));

        dialogPanel.add(imageButton,
                new GridBagConstraints(0, 3, 1, 1, 0, 0,
                        GridBagConstraints.NORTHEAST,
                        GridBagConstraints.NONE,
                        new Insets(5, 5, 0, 5), 0, 0));

        dialogPanel.add(imagePath,
                new GridBagConstraints(1, 3, 1, 1, 1, 0,
                        GridBagConstraints.NORTH,
                        GridBagConstraints.BOTH,
                        new Insets(5, 5, 0, 5), 0, 0));







        int result = JOptionPane.showConfirmDialog(parent, dialogPanel, this.mBundle.getString("dialog.inscription.label.title"),
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            int randomInt = new Random().nextInt(999999);
            User newUser = new User(UUID.randomUUID(),
                    tag.getText(),
                    new String(password.getPassword()),
                    name.getText(),
                    new HashSet<String>(),
                    imagePath.getText());
            this.handlerInscription(newUser);
        }
    }

    /**
     * handler
     */
    private void handlerInscription(User user) {
        for (IInscriptionComponentObserver observer : InscriptionComponent.this.mObservers) {
            observer.notifyRequestUserInscription(user);
        }
    }


    /**
     * IInscriptionComponent methodes
     */

    @Override
    public void addObserver(IInscriptionComponentObserver observer) {
        this.mObservers.add(observer);
    }

    @Override
    public void deleteObserver(IInscriptionComponentObserver observer) {
        this.mObservers.remove(observer);
    }
}
