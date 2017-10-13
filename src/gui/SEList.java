package gui;

import javax.swing.*;
import java.awt.*;

public class SEList extends JList {

    public SEList(ListModel model) {
        super(model);
        setFont(Font.decode(Font.MONOSPACED));
        setBackground(new Color(55, 55, 55));
        setForeground(Color.WHITE);
    }
}
