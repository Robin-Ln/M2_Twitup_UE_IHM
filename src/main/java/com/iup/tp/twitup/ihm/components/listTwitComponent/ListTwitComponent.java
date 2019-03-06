package com.iup.tp.twitup.ihm.components.listTwitComponent;

import com.iup.tp.twitup.datamodel.Twit;
import com.iup.tp.twitup.ihm.components.twitComponent.TwitComponent;

import javax.swing.*;
import java.util.List;

public class ListTwitComponent extends JPanel {


    public ListTwitComponent(List<Twit> twits) {
        for (Twit twit : twits) {
            this.add(new TwitComponent(twit));
        }
    }
}
