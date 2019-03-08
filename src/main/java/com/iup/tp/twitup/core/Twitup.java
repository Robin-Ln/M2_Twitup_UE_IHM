package com.iup.tp.twitup.core;

import com.iup.tp.twitup.common.Constants;
import com.iup.tp.twitup.common.PropertiesManager;
import com.iup.tp.twitup.datamodel.*;
import com.iup.tp.twitup.events.file.IWatchableDirectory;
import com.iup.tp.twitup.events.file.WatchableDirectory;
import com.iup.tp.twitup.ihm.ITwitupMainViewObserver;
import com.iup.tp.twitup.ihm.TwitupMainView;
import com.iup.tp.twitup.ihm.TwitupMainViewAdapter;
import com.iup.tp.twitup.ihm.TwitupMock;
import com.iup.tp.twitup.ihm.components.centerComponent.CenterComponent;
import com.iup.tp.twitup.ihm.components.fileChooserComponent.FileChooserComponent;
import com.iup.tp.twitup.ihm.components.fileChooserComponent.IFileChooserComponentObserver;
import com.iup.tp.twitup.ihm.components.northComponent.NorthComponent;
import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.net.URL;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 * Classe principale l'application.
 * 
 * @author S.Lucas
 */
public class Twitup {
	/**
	 * Base de données.
	 */
	private IDatabase mDatabase;

	/**
	 * Gestionnaire des entités contenu de la base de données.
	 */
	private EntityManager mEntityManager;

	/**
	 * Vue principale de l'application.
	 */
	private TwitupMainView mMainView;

	/**
	 * mNorthCompoent
	 */
	private NorthComponent mNorthCompoent;

	/**
	 * mCenterCompoent
	 */
	private CenterComponent mCenterComponent;


	/**
	 * Classe de surveillance de répertoire
	 */
	private IWatchableDirectory mWatchableDirectory;

	/**
	 * Répertoire d'échange de l'application.
	 */
	private String mExchangeDirectoryPath;

	/**
	 * Idnique si le mode bouchoné est activé.
	 */
	private Boolean mIsMockEnabled = false;

	/**
	 * Nom de la classe de l'UI.
	 */
	private String mUiClassName;

	/**
	 * Properties de l'application
	 */
	private Properties mProperties;

	/**
	 * Conficuration de la langue
	 */
	private String mLocale;

	/**
	 * Constructeur.
	 */
	public Twitup() {
		// Init le properties de l'application
		this.initProperties();

		// Init la langue
		this.initProperties();

		// Init du look and feel de l'application
		this.initLookAndFeel();

		// Initialisation de la base de données
		this.initDatabase();


		if (this.mIsMockEnabled) {
			// Initialisation du bouchon de travail
			this.initMock();
		}

		// Initialisation de l'IHM
		this.initGui();

		// Initialisation du répertoire d'échange
		this.initDirectory();

		// Sauvegarde des paramètre de l'application
		this.saveProperties();
	}

	/**
	 * Initialisation du properties de l'application.
	 */
	private void initProperties() {
		URL url = getClass().getClassLoader().getResource(Constants.CONFIGURATION_FILE);
		String path = url.getPath();
		this.mProperties = PropertiesManager.loadProperties(path);

		this.mLocale = this.mProperties.getProperty(Constants.CONFIGURATION_KEY_LOCAL);
		this.mExchangeDirectoryPath = this.mProperties.getProperty(Constants.CONFIGURATION_KEY_EXCHANGE_DIRECTORY);
		this.mUiClassName = this.mProperties.getProperty(Constants.CONFIGURATION_KEY_UI_CLASS_NAME);
		this.mIsMockEnabled = Boolean.parseBoolean(this.mProperties.getProperty(Constants.CONFIGURATION_KEY_MOCK_ENABLED));
	}


	/**
	 * Sauvegarde du fichier de properties de l'application.
	 */
	private void saveProperties() {
		this.mProperties.setProperty(Constants.CONFIGURATION_KEY_LOCAL, this.mLocale);
		this.mProperties.setProperty(Constants.CONFIGURATION_KEY_EXCHANGE_DIRECTORY, this.mExchangeDirectoryPath);
		this.mProperties.setProperty(Constants.CONFIGURATION_KEY_UI_CLASS_NAME, this.mUiClassName);
		this.mProperties.setProperty(Constants.CONFIGURATION_KEY_MOCK_ENABLED, this.mIsMockEnabled.toString());
	}

	/**
	 * Initialisation du look and feel de l'application.
	 */
	private void initLookAndFeel() {
		try {
			if (StringUtils.isBlank(this.mUiClassName)) {
				this.mUiClassName = UIManager.getSystemLookAndFeelClassName();
			}
			UIManager.setLookAndFeel(this.mUiClassName);
		} catch (Exception e) {
			System.err.println("Il y a un problème avec le look and feel : ");
			e.printStackTrace();
		}
	}

	/**
	 * Initialisation de l'interface graphique.
	 */
	private void initGui() {
		// this.mMainView...
		this.mMainView = new TwitupMainView(this.mDatabase,this.mEntityManager,this.createResourceBundle());
		this.mMainView.initGUI();

		this.mMainView.addObserver(new TwitupMainViewAdapter() {
			@Override
			public void notifyEchangeDirectoryChange(File file) {
				if (Twitup.this.isValideExchangeDirectory(file)) {
					Twitup.this.initDirectory(file.getAbsolutePath());
				}
			}
		});

		this.mDatabase.addObserver(new DatabaseAdapter() {
			@Override
			public void notifyTwitAdded(Twit addedTwit) {
				System.out.println("addedTwit :" + addedTwit);
			}

			@Override
			public void notifyTwitDeleted(Twit deletedTwit) {
				System.out.println("deletedTwit :" + deletedTwit);
			}

			@Override
			public void notifyTwitModified(Twit modifiedTwit) {
				System.out.println("modifiedTwit :" + modifiedTwit);
			}

			@Override
			public void notifyUserAdded(User addedUser) {
				System.out.println("addedUser :" + addedUser);
			}

			@Override
			public void notifyUserDeleted(User deletedUser) {
				System.out.println("deletedUser :" + deletedUser);
			}

			@Override
			public void notifyUserModified(User modifiedUser) {
				System.out.println("modifiedUser :" + modifiedUser);
			}
		});
	}

	/**
	 * Initialisation du répertoire d'échange (depuis la conf ou depuis un file
	 * chooser). <br/>
	 * <b>Le chemin doit obligatoirement avoir été saisi et être valide avant de
	 * pouvoir utiliser l'application</b>
	 */
	private void initDirectory() {
		if (StringUtils.isBlank(this.mExchangeDirectoryPath)) {
			FileChooserComponent fileChooserComponent = new FileChooserComponent(this.createResourceBundle());
			fileChooserComponent.addObserver(new IFileChooserComponentObserver() {
				@Override
				public void notifyFileSelected(File file) {
					if (Twitup.this.isValideExchangeDirectory(file)) {
						Twitup.this.mExchangeDirectoryPath = file.getAbsolutePath();
					}
				}

				@Override
				public void notifySelectCanceled() {
					fileChooserComponent.deleteObserver(this);
				}
			});
			fileChooserComponent.show();
		}

		if (StringUtils.isNotBlank(this.mExchangeDirectoryPath)) {
			this.initDirectory(this.mExchangeDirectoryPath);
		} else {
			System.exit(0);
		}

	}

	/**
	 * Indique si le fichier donné est valide pour servire de répertoire
	 * d'échange
	 * 
	 * @param directory
	 *            , Répertoire à tester.
	 */
	private boolean isValideExchangeDirectory(File directory) {
		// Valide si répertoire disponible en lecture et écriture
		return directory != null && directory.exists() && directory.isDirectory() && directory.canRead()
				&& directory.canWrite();
	}

	/**
	 * Initialisation du mode bouchoné de l'application
	 */
	private void initMock() {
		TwitupMock mock = new TwitupMock(this.mDatabase, this.mEntityManager);
		mock.showGUI();

	}

	/**
	 * Initialisation de la base de données
	 */
	private void initDatabase() {
		mDatabase = new Database();
		mEntityManager = new EntityManager(mDatabase);
	}

	/**
	 * Initialisation du répertoire d'échange.
	 * 
	 * @param directoryPath
	 */
	public void initDirectory(String directoryPath) {
		mExchangeDirectoryPath = directoryPath;
		mWatchableDirectory = new WatchableDirectory(directoryPath);
		mEntityManager.setExchangeDirectory(directoryPath);

		mWatchableDirectory.initWatching();
		mWatchableDirectory.addObserver(mEntityManager);

		this.mProperties.setProperty("EXCHANGE_DIRECTORY", directoryPath);
	}

	public void show() {
		this.mMainView.showGUI();
	}

	/**
	 * Permet de créer le bundle resource de l'application
	 */
	private ResourceBundle createResourceBundle() {
		Locale locale = null;
		if (StringUtils.isNotBlank(this.mLocale)) {
			locale = new Locale(this.mLocale, this.mLocale, "");
		} else {
			locale = Locale.getDefault();
		}
		return ResourceBundle.getBundle("local", locale);
	}

}
