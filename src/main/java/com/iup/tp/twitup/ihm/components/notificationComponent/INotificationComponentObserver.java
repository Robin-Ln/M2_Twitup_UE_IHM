package com.iup.tp.twitup.ihm.components.notificationComponent;

import com.iup.tp.twitup.datamodel.Twit;

import java.util.Set;

public interface INotificationComponentObserver {

    void notifyDisplayNotification(Set<Twit> twits);

}
