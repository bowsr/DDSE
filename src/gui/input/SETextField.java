package gui.input;

import javax.swing.*;
import java.awt.*;

public class SETextField extends JTextField {

    public SETextField() {
        super(8);
        setEditable(true);
        setBackground(new Color(55, 55, 55));
        setForeground(Color.WHITE);
        setBorder(BorderFactory.createLineBorder(new Color(35, 35, 35)));
        setCaretColor(Color.WHITE);
    }

}
