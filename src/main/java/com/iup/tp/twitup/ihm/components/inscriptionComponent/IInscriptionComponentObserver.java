package com.iup.tp.twitup.ihm.components.inscriptionComponent;

import com.iup.tp.twitup.datamodel.User;

public interface IInscriptionComponentObserver {

    void notifyRequestUserInscription(User user);

}
