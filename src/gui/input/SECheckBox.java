package gui.input;

import gui.SEGUIHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class SECheckBox extends JCheckBox {

    private int id;

    public static boolean singleSelection;

    public SECheckBox(String display, int identifier) {
        super(display);
        id = identifier;

        setFocusPainted(false);
        setBackground(new Color(43, 43, 43));
        setForeground(new Color(218, 218, 218));

        addActionListener(this::actionListener);
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

    private void actionListener(ActionEvent e) {
        boolean button = false;
        for(int i = 0; i < SEGUIHandler.instance.enemySelections.size(); i++) {
            if(SEGUIHandler.instance.enemySelections.get(i).isSelected()) {
                if(button) {
                    button = false;
                    break;
                }
                button = true;
            }
        }
        singleSelection = button;
        if(SEGUIHandler.instance.list.getSelectedIndex() != -1)
            SEGUIHandler.instance.editEnemyType.setEnabled(singleSelection);
    }
}
