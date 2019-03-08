package com.iup.tp.twitup.ihm.components.inscriptionComponent;

import com.iup.tp.twitup.datamodel.User;

import javax.swing.*;
import java.awt.*;
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
    public void open() {
        JPanel dialogPanel = new JPanel();
        dialogPanel.setLayout(new GridBagLayout());

        JLabel tagLabel = new JLabel(this.mBundle.getString("dialog.inscription.label.tag"));
        JTextField tag = new JTextField();

        JLabel nameLabel = new JLabel(this.mBundle.getString("dialog.inscription.label.name"));
        JTextField name = new JTextField();

        JLabel passwordLabel = new JLabel(this.mBundle.getString("dialog.inscription.label.password"));
        JPasswordField password = new JPasswordField();

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







        int result = JOptionPane.showConfirmDialog(null, dialogPanel, this.mBundle.getString("dialog.inscription.label.title"),
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {

        }
    }

    /**
     * handler
     */
    private void handlerSuccessInscription(User user) {
//        for (IInscriptionComponentObserver observer : InscriptionComponent.this.mObservers) {
//            int randomInt = new Random().nextInt(999999);
//            User newUser = new User(UUID.randomUUID(),
//                    tag.getText(),
//                    new String(password.getPassword()),
//                    name.getText(),
//                    new HashSet<String>(),
//                    "");
//            observer.notifyRequestUserInscription(newUser);
//        }
    };


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
