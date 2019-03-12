package com.iup.tp.twitup;

import com.iup.tp.twitup.core.Twitup;

/**
 * Classe de lancement de l'application.
 *
 * @author S.Lucas
 */
public class TwitupLauncher {

    /**
     * Launcher.
     *
     * @param args
     */
    public static void main(String[] args) {
//        System.setProperty( "com.sun.xml.bind.v2.bytecode.ClassTailor.noOptimize", "true");
        Twitup twitup = new Twitup();
        twitup.show();
    }

}
