package com.iup.tp.twitup.ihm.components.twitComponent;

import com.iup.tp.twitup.datamodel.Twit;

import javax.swing.*;

public class TwitComponent extends JPanel {

    public TwitComponent(Twit twit) {
        JLabel mLabel = new JLabel(twit.getText());
        this.add(mLabel);
    }


}
