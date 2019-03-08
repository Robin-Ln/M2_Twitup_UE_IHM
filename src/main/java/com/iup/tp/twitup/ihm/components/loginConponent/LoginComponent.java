package com.iup.tp.twitup.ihm.components.loginConponent;

import javax.swing.*;
import java.awt.*;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

public class LoginComponent implements ILoginComponent {

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
    private final Set<ILoginComponentObserver> mObservers;

    /**
     * Constructeur
     */
    public LoginComponent(ResourceBundle bundle, Integer nbConnexion) {
        super();
        this.mBundle = bundle;
        this.mNbConnexion = nbConnexion;
        this.mObservers = new HashSet<>();
    }

    /**
     * Methodes
     */
    public void show() {
        JPanel dialogPanel = new JPanel();

        JTextField nameField = new JTextField("MockUser75509");
        JPasswordField passwordField = new JPasswordField("--");
        dialogPanel.setLayout(new GridLayout(0, 2));

        if (this.mNbConnexion > 0) {
            JLabel echecLabel = new JLabel(this.mBundle.getString("dialog.connexion.label.echec"));
            echecLabel.setForeground(Color.RED);
            dialogPanel.add(echecLabel);

            JLabel nbConnexionLabel = new JLabel(this.mNbConnexion.toString());
            nbConnexionLabel.setForeground(Color.RED);
            dialogPanel.add(nbConnexionLabel);
        }


        dialogPanel.add(new JLabel(this.mBundle.getString("dialog.connexion.label.name")));
        dialogPanel.add(nameField);
        dialogPanel.add(new JLabel(this.mBundle.getString("dialog.connexion.label.password")));
        dialogPanel.add(passwordField);

        int result = JOptionPane.showConfirmDialog(null, dialogPanel, this.mBundle.getString("dialog.connexion.label.title"),
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            for (ILoginComponentObserver observer : LoginComponent.this.mObservers) {
                observer.notifyRequestUserConnexion(
                        nameField.getText(),
                        passwordField.getPassword(),
                        LoginComponent.this.mNbConnexion
                );
            }
        }
    }

    /**
     * IInscriptionComponent methodes
     */

    @Override
    public void addObserver(ILoginComponentObserver observer) {
        this.mObservers.add(observer);
    }

    @Override
    public void deleteObserver(ILoginComponentObserver observer) {
        this.mObservers.remove(observer);
    }
}
