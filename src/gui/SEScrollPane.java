package gui;

import javax.swing.*;
import java.awt.*;

public class SEScrollPane extends JScrollPane {

    public SEScrollPane(Component component) {
        super(component);
        setOpaque(true);
        setBackground(new Color(55, 55, 55));
    }
}
