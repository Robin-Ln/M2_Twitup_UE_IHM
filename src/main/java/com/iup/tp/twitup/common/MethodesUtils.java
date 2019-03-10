package com.iup.tp.twitup.common;

import java.util.HashSet;
import java.util.Set;

public class MethodesUtils {
    static public Set<String> extractTags(String text, String tagDelimiter) {
        Set<String> tags = new HashSet<String>();

        // Ajout d'un caractère spécial pour reconnaitre les éléments
        // réellement
        // taggé
        String specialChar = "~";
        String replacedText = text.replace(tagDelimiter, tagDelimiter + specialChar);

        // Découpage en foncion du délimiteur.
        String[] taggedStrings = replacedText.split(tagDelimiter);

        // Parcours de tous les groupes récupérés
        for (String taggedString : taggedStrings) {
            // Si la chaine courante commencait bien par le délimiteur
            if (taggedString.startsWith(specialChar)) {
                // Récupération du tag (du délimiteur jusqu'au premier
                // espace)
                String newTag = taggedString.split(" ")[0];

                // Suppression du caractère sp�cial
                newTag = newTag.substring(1, newTag.length());

                // Ajout du tag à la liste
                tags.add(newTag);
            }
        }

        return tags;
    }
}
