package gui;

import javax.swing.*;
import java.awt.*;

public class SEPanel extends JPanel {

    public SEPanel() {
        this(new BorderLayout());
    }

    public SEPanel(LayoutManager layout) {
        super(layout);
        setOpaque(true);
        setBackground(new Color(43, 43, 43));
    }
}
