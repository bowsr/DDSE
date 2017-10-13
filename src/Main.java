import file.FileHandler;
import gui.SEGUIHandler;

import java.awt.*;

public class Main {

    public static SEGUIHandler gui;

    public static void main(String args[]) {
        System.out.println("DDSE v1.0");

        FileHandler.readTemplateFile();
        EventQueue.invokeLater(() -> {
            gui = new SEGUIHandler();
            gui.setVisible();
        });
    }

}
