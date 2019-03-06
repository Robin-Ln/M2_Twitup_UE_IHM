package com.iup.tp.twitup.ihm.components.twitComponent;

import com.iup.tp.twitup.datamodel.Twit;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class TwitComponent extends JPanel {

    public TwitComponent(Twit twit) {

        this.setBackground(new Color(50,150,200,70));
        this.setBorder(new LineBorder(Color.BLUE, 4,true));

        JLabel mLabel = new JLabel(twit.getText());
        this.add(mLabel);
    }


}
