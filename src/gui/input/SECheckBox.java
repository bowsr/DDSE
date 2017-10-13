package gui.input;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class SECheckBox extends JCheckBox {

    private int id;

    public SECheckBox(String display, int identifier) {
        super(display);
        id = identifier;

        setFocusPainted(false);
        setBackground(new Color(43, 43, 43));
        setForeground(new Color(218, 218, 218));

    }

    public SECheckBox(String display, int identifier, JPanel panel) {
        this(display, identifier);
        panel.add(this);
    }

    public SECheckBox(String display, int identifier, JPanel panel, ArrayList<SECheckBox> list) {
        this(display, identifier, panel);
        list.add(this);
    }

    public int getID() {
        return id;
    }
}
