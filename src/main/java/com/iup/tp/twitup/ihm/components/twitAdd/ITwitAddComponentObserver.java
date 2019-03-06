package com.iup.tp.twitup.ihm.components.twitAdd;

import com.iup.tp.twitup.datamodel.Twit;

public interface ITwitAddComponentObserver {

    void notifyNewTwit(Twit twit);

    void notifyViewChange();
}
