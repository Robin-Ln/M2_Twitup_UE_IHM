package com.iup.tp.twitup.ihm.components.inscriptionComponent;

import com.iup.tp.twitup.core.EntityManager;
import com.iup.tp.twitup.datamodel.IDatabase;
import com.iup.tp.twitup.datamodel.User;
import com.iup.tp.twitup.ihm.components.fileChooserComponent.FileChooserComponent;
import com.iup.tp.twitup.ihm.components.fileChooserComponent.IFileChooserComponentObserver;
import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.*;

public class InscriptionComponent extends JFrame implements IInscriptionComponent {

    /**
     * Liste des observateurs de modifications de la base.
     */
    private final Set<IInscriptionComponentObserver> mObservers;
    /**
     * Configurer la langue de l'aplication
     */
    private ResourceBundle mBundle;
    /**
     * Nombre de tentavise de connexion
     */
    private Integer mNbConnexion;
    /**
     * Base de données.
     */
    private IDatabase mDatabase;

    /**
     * Gestionnaire de bdd et de fichier.
     */
    private EntityManager mEntityManager;

    /**
     * Composents du formulaire
     */
    private JPanel formulaire;
    private JTextField tag;
    private JTextField name;
    private JPasswordField password;
    private JLabel tagExist;
    private JLabel nameExist;
    private JLabel passwordRequired;
    private JButton submit;
    private JLabel imagePath;

    /**
     * Constructeur
     */
    public InscriptionComponent(ResourceBundle bundle, IDatabase database, EntityManager entityManager) {
        super();
        this.mBundle = bundle;
        this.mObservers = new HashSet<>();
        this.mDatabase = database;
        this.mEntityManager = entityManager;
        this.init();
    }

    /**
     * Methodes
     */
    public void init() {
        this.setTitle(this.mBundle.getString("dialog.inscription.label.title"));

        this.formulaire = new JPanel();
        formulaire.setLayout(new GridBagLayout());


        this.tag = new JTextField();
        this.tag.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                InscriptionComponent.this.handlerCheckTag();
            }
        });
        this.tagExist = new JLabel(this.mBundle.getString("label.inscription.error.tag.libelle"));
        tagExist.setForeground(Color.RED);
        this.tagExist.setVisible(false);


        this.name = new JTextField();
        this.name.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                InscriptionComponent.this.handlerCheckName();
            }
        });
        this.nameExist = new JLabel(this.mBundle.getString("label.inscription.error.name.libelle"));
        nameExist.setForeground(Color.RED);
        this.nameExist.setVisible(false);


        this.password = new JPasswordField();
        this.password.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                InscriptionComponent.this.handlerCheckPassword();
            }
        });
        this.passwordRequired = new JLabel(this.mBundle.getString("label.inscription.error.password.libelle"));
        passwordRequired.setForeground(Color.RED);
        this.passwordRequired.setVisible(false);

        this.imagePath = new JLabel();
        JButton imageButton = new JButton(this.mBundle.getString("button.inscription.image.libelle"));
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

        this.submit = new JButton(this.mBundle.getString("button.inscription.submit.libelle"));
        this.submit.setEnabled(false);
        this.submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                InscriptionComponent.this.handlerInscription();
            }
        });


        /**
         * Ajout des composant
         */

        /**
         * tag
         */
        formulaire.add(new JLabel(this.mBundle.getString("dialog.inscription.label.tag")),
                new GridBagConstraints(0, 0, 1, 1, 0, 0,
                        GridBagConstraints.NORTHEAST,
                        GridBagConstraints.NONE,
                        new Insets(5, 5, 0, 5), 0, 0));

        formulaire.add(tag,
                new GridBagConstraints(1, 0, 1, 1, 1, 0,
                        GridBagConstraints.NORTH,
                        GridBagConstraints.BOTH,
                        new Insets(5, 5, 0, 5), 0, 0));

        formulaire.add(this.tagExist,
                new GridBagConstraints(0, 1, 2, 1, 0, 0,
                        GridBagConstraints.CENTER,
                        GridBagConstraints.BOTH,
                        new Insets(5, 5, 0, 5), 0, 0));

        /**
         * name
         */

        formulaire.add(new JLabel(this.mBundle.getString("dialog.inscription.label.name")),
                new GridBagConstraints(0, 2, 1, 1, 0, 0,
                        GridBagConstraints.NORTHEAST,
                        GridBagConstraints.NONE,
                        new Insets(5, 5, 0, 5), 0, 0));

        formulaire.add(name,
                new GridBagConstraints(1, 2, 1, 1, 1, 0,
                        GridBagConstraints.NORTH,
                        GridBagConstraints.BOTH,
                        new Insets(5, 5, 0, 5), 0, 0));

        formulaire.add(this.nameExist,
                new GridBagConstraints(0, 3, 2, 1, 0, 0,
                        GridBagConstraints.CENTER,
                        GridBagConstraints.BOTH,
                        new Insets(5, 5, 0, 5), 0, 0));

        /**
         * password
         */

        formulaire.add(new JLabel(this.mBundle.getString("dialog.inscription.label.password")),
                new GridBagConstraints(0, 4, 1, 1, 0, 0,
                        GridBagConstraints.NORTHEAST,
                        GridBagConstraints.NONE,
                        new Insets(5, 5, 0, 5), 0, 0));

        formulaire.add(password,
                new GridBagConstraints(1, 4, 1, 1, 1, 0,
                        GridBagConstraints.NORTH,
                        GridBagConstraints.BOTH,
                        new Insets(5, 5, 0, 5), 0, 0));

        formulaire.add(this.passwordRequired,
                new GridBagConstraints(0, 5, 2, 1, 0, 0,
                        GridBagConstraints.CENTER,
                        GridBagConstraints.BOTH,
                        new Insets(5, 5, 0, 5), 0, 0));

        /**
         * image
         */

        formulaire.add(imageButton,
                new GridBagConstraints(0, 6, 1, 1, 0, 0,
                        GridBagConstraints.NORTHEAST,
                        GridBagConstraints.NONE,
                        new Insets(5, 5, 0, 5), 0, 0));

        formulaire.add(imagePath,
                new GridBagConstraints(1, 6, 1, 1, 1, 0,
                        GridBagConstraints.NORTH,
                        GridBagConstraints.BOTH,
                        new Insets(5, 5, 0, 5), 0, 0));

        /**
         * submit
         */

        formulaire.add(this.submit,
                new GridBagConstraints(0, 7, 2, 1, 1, 0,
                        GridBagConstraints.CENTER,
                        GridBagConstraints.NONE,
                        new Insets(5, 5, 0, 5), 0, 0));

        this.add(formulaire);
        this.pack();
    }

    public void showGUI() {
        // dimenssion
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        screenSize.height /= 4;
        screenSize.width /= 4;
        this.setSize(screenSize);

        this.setLocationRelativeTo(null);

        // Affichage dans l'EDT
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {

                // Affichage
                InscriptionComponent.this.setVisible(true);
            }
        });
    }

    /**
     * handler
     */
    private void handlerInscription() {
        User newUser = new User(UUID.randomUUID(),
                this.tag.getText(),
                new String(password.getPassword()),
                this.name.getText(),
                new HashSet<>(),
                this.imagePath.getText());
        this.mEntityManager.sendUser(newUser);
        this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    }

    private void handlerCheckName() {
        Set<User> users = this.mDatabase.getUsers();
        for (User user : users) {
            if (Objects.equals(user.getName(), this.name.getText())) {
                this.nameExist.setVisible(true);
                this.handlerCheckFormulaireValiditer();
                return;
            }
        }
        this.handlerCheckFormulaireValiditer();
        this.nameExist.setVisible(false);
    }

    private void handlerCheckTag() {
        Set<User> users = this.mDatabase.getUsers();
        for (User user : users) {
            if (Objects.equals(user.getUserTag(), this.tag.getText())) {
                this.tagExist.setVisible(true);
                this.handlerCheckFormulaireValiditer();
                return;
            }
        }
        this.handlerCheckFormulaireValiditer();
        this.tagExist.setVisible(false);
    }

    private void handlerCheckPassword() {
        if (StringUtils.isBlank(new String(this.password.getPassword()))) {
            this.passwordRequired.setVisible(true);
            this.handlerCheckFormulaireValiditer();
            return;
        }
        this.handlerCheckFormulaireValiditer();
        this.passwordRequired.setVisible(false);
    }

    private void handlerCheckFormulaireValiditer() {
        if (this.tagExist.isVisible() && this.nameExist.isVisible() && this.passwordRequired.isVisible()) {
            this.submit.setEnabled(false);
        } else if (StringUtils.isBlank(this.tag.getText()) ||
                StringUtils.isBlank(this.name.getText()) ||
                StringUtils.isBlank(new String(this.password.getPassword()))) {
            this.submit.setEnabled(false);
        } else {
            this.submit.setEnabled(true);
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
