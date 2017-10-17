import file.FileHandler;
import gui.SEGUIHandler;
import http.VersionChecker;
import resource.Resources;

import javax.swing.*;
import java.awt.*;
import java.net.URI;

public class Main {

    public static SEGUIHandler gui;

    public static void main(String args[]) {
        System.out.println("DDSE " + Resources.PROGRAM_VERSION);

        if(checkVersion()) {
            FileHandler.readTemplateFile();
            EventQueue.invokeLater(() -> {
                gui = new SEGUIHandler();
                gui.setVisible();
            });
        }
    }

    private static boolean checkVersion() {
        boolean check;

        try {
            check = VersionChecker.compare(Resources.PROGRAM_VERSION);

            if(check) {
                int val = JOptionPane.showConfirmDialog(null, "Your program version is out of date." +
                        " Would you like to update?\n" +
                        "Current Version: " + Resources.PROGRAM_VERSION + "\n" +
                        "Latest Version:  " + VersionChecker.latest);

                if(val == JOptionPane.OK_OPTION) {
                    Desktop.getDesktop().browse(new URI(Resources.GITHUB_REPO_RELEASES_URI + VersionChecker.latest));
                    return false;
                }else return val != JOptionPane.CANCEL_OPTION;
            }else {
                return true;
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
