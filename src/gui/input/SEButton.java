package gui.input;

import file.FileHandler;
import gui.SEGUIHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;

public class SEButton extends JButton{

    private SEButton(String text) {
        super(text);
        setFocusPainted(false);
        setBackground(new Color(66, 69, 75));
        setForeground(Color.WHITE);
        setBorder(BorderFactory.createLineBorder(new Color(35, 35, 35)));
        addActionListener(this::actionPerformed);
    }

    private SEButton(String text, int width, int height) {
        this(text);
        setPreferredSize(new Dimension(width, height));
        setSize(new Dimension(width, height));
    }

    public SEButton(String text, int width, int height, String actionCmd) {
        this(text, width, height);
        setActionCommand(actionCmd);
    }

    private void actionPerformed(ActionEvent e) {
        switch(e.getActionCommand()) {
        case "exit":         System.exit(0);
        case "clear_list":   clearConfirmationDialog();
                             break;
        case "delete_entry": deleteSelection();
                             break;
        case "add_selected": addSelection();
                             break;
        case "create_file":  createFile();
                             break;
        case "open_file":    openFile();
                             break;
        case "edit_delay":   editDelay();
                             break;
        case "edit_enemy":   editEnemy();
                             break;
        default: break;
        }
    }

    private void editEnemy() {
        int enemy = -500;
        for(SECheckBox check: SEGUIHandler.instance.enemySelections)
            if(check.isSelected())
                enemy = check.getID();

        if(enemy == -500) return;

        int[] indices = SEGUIHandler.instance.list.getSelectedIndices();
        for(int index: indices)
            SEGUIHandler.instance.elm.get(index).setID(enemy);

        SEGUIHandler.instance.list.clearSelection();
    }

    private void editDelay() {
        int delay = (int) SEGUIHandler.instance.delayspin.getValue();
        int[] indices = SEGUIHandler.instance.list.getSelectedIndices();
        for(int index: indices)
            SEGUIHandler.instance.elm.get(index).setDelay(delay);

        SEGUIHandler.instance.elm.updateSpawnTimes();
        SEGUIHandler.instance.list.clearSelection();
    }

    private void openFile() {
        JFileChooser c = SEGUIHandler.instance.chooser;
        int val = c.showOpenDialog(SEGUIHandler.instance.frame);

        if(val == JFileChooser.APPROVE_OPTION) {
            FileHandler.readFile(c.getSelectedFile());
            SEGUIHandler.instance.loadDataFromFile();
        }
    }

    private void createFile() {
        JFileChooser c = SEGUIHandler.instance.chooser;
        int val = c.showSaveDialog(SEGUIHandler.instance.frame);

        if(val == JFileChooser.APPROVE_OPTION) {
            File file = c.getSelectedFile();
            if(file.exists()) {
                int confirm = JOptionPane.showConfirmDialog(c, "Overwrite existing file?");
                if(confirm == JFileChooser.APPROVE_OPTION)
                    FileHandler.writeNewSpawnsetFile(c.getSelectedFile());
                return;
            }
            FileHandler.writeNewSpawnsetFile(c.getSelectedFile());
        }
    }

    private void addSelection() {
        int delay = (int) SEGUIHandler.instance.delayspin.getValue();
        SECheckBox tmp;
        for(int i = 0; i < SEGUIHandler.instance.enemySelections.size(); i++) {
            tmp = SEGUIHandler.instance.enemySelections.get(i);
            if(tmp.isSelected()) {
                SEGUIHandler.instance.elm.addElement(tmp.getID(), delay);
                delay = 0;
            }
        }
    }

    private void deleteSelection() {
        int[] indices = SEGUIHandler.instance.list.getSelectedIndices();
        for(int i = 0; i < indices.length; i++) {
            SEGUIHandler.instance.elm.remove(indices[i]);
            for(int j = i + 1; j < indices.length; j++) indices[j]--;
        }
    }

    private void clearConfirmationDialog() {
        int val = JOptionPane.showOptionDialog(null, "Are you sure you want to clear the spawn list?", "Clear Confirmation",
                                               JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);
        if(val == 0) {
            SEGUIHandler.instance.elm.clear();
        }
    }

}
