package com.iup.tp.twitup.core;

import com.iup.tp.twitup.common.PropertiesManager;
import com.iup.tp.twitup.datamodel.*;
import com.iup.tp.twitup.events.file.IWatchableDirectory;
import com.iup.tp.twitup.events.file.WatchableDirectory;
import com.iup.tp.twitup.ihm.ITwitupMainView;
import com.iup.tp.twitup.ihm.ITwitupMainViewObserver;
import com.iup.tp.twitup.ihm.TwitupMainView;
import com.iup.tp.twitup.ihm.TwitupMock;
import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import java.io.File;
import java.net.URL;
import java.util.Properties;

/**
 * Classe principale l'application.
 * 
 * @author S.Lucas
 */
public class Twitup implements IDatabaseObserver, ITwitupMainViewObserver {
	/**
	 * Base de données.
	 */
	protected IDatabase mDatabase;

	/**
	 * Gestionnaire des entités contenu de la base de données.
	 */
	protected EntityManager mEntityManager;

	/**
	 * Vue principale de l'application.
	 */
	protected TwitupMainView mMainView;

	/**
	 * Classe de surveillance de répertoire
	 */
	protected IWatchableDirectory mWatchableDirectory;

	/**
	 * Répertoire d'échange de l'application.
	 */
	protected String mExchangeDirectoryPath;

	/**
	 * Idnique si le mode bouchoné est activé.
	 */
	protected boolean mIsMockEnabled = false;

	/**
	 * Nom de la classe de l'UI.
	 */
	protected String mUiClassName;

	/**
	 * Properties de l'application
	 */
	Properties mProperties;

	/**
	 * Constructeur.
	 */
	public Twitup() {
		// Init le properties de l'application
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
	}

	/**
	 * Initialisation du properties de l'application.
	 */
	private void initProperties() {
		URL url = getClass().getClassLoader().getResource("configuration.properties");
		String path = url.getPath();
		this.mProperties = PropertiesManager.loadProperties(path);
	}

	/**
	 * Initialisation du look and feel de l'application.
	 */
	protected void initLookAndFeel() {
		try {
			String lookAndFeel = this.mProperties.getProperty("UI_CLASS_NAME");
			if (StringUtils.isBlank(lookAndFeel)) {
				lookAndFeel = UIManager.getSystemLookAndFeelClassName();
			}
			UIManager.setLookAndFeel(lookAndFeel);
			this.mProperties.setProperty("UI_CLASS_NAME", lookAndFeel);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Initialisation de l'interface graphique.
	 */
	protected void initGui() {
		// this.mMainView...
		this.mMainView = new TwitupMainView(this.mDatabase,this.mEntityManager);
		this.mMainView.addObserver(this);
		this.mDatabase.addObserver(this);
	}

	/**
	 * Initialisation du répertoire d'échange (depuis la conf ou depuis un file
	 * chooser). <br/>
	 * <b>Le chemin doit obligatoirement avoir été saisi et être valide avant de
	 * pouvoir utiliser l'application</b>
	 */
	protected void initDirectory() {

		String path = this.mProperties.getProperty("EXCHANGE_DIRECTORY");

		if (StringUtils.isNotBlank(path)){
			this.initDirectory(path);
		}

		if (StringUtils.isBlank(this.mExchangeDirectoryPath)) {
			this.mMainView.handlerFileChooser();
		}
	}

	/**
	 * Indique si le fichier donné est valide pour servire de répertoire
	 * d'échange
	 * 
	 * @param directory
	 *            , Répertoire à tester.
	 */
	protected boolean isValideExchangeDirectory(File directory) {
		// Valide si répertoire disponible en lecture et écriture
		return directory != null && directory.exists() && directory.isDirectory() && directory.canRead()
				&& directory.canWrite();
	}

	/**
	 * Initialisation du mode bouchoné de l'application
	 */
	protected void initMock() {
		TwitupMock mock = new TwitupMock(this.mDatabase, this.mEntityManager);
		mock.showGUI();
	}

	/**
	 * Initialisation de la base de données
	 */
	protected void initDatabase() {
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
		this.initMock();
	}

	/**
	 * Methodes de l'interface IDatabaseObserver
	 */

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

	/**
	 * Methodes de l'interface ITwitupMainViewObserver
	 */

	@Override
	public void notifyEchangeDirectoryChange(File file) {
		if (file.isDirectory()) {
			this.initDirectory(file.getAbsolutePath());
		}  else {
			throw new RuntimeException("notifyEchangeDirectoryChange Fail");
		}
	}

	@Override
	public void notifyWindowClosing(ITwitupMainView observable) {
		observable.deleteObserver(this);
		String path = getClass().getClassLoader().getResource("configuration.properties").getPath();
		PropertiesManager.writeProperties(this.mProperties, path);
	}
}
