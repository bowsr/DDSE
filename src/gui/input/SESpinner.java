package gui.input;

import javax.swing.*;
import java.awt.*;

public class SESpinner extends JSpinner {

    private SESpinner(SpinnerModel sm) {
        super(sm);
//        setBackground(new Color(55, 55, 55));
//        setForeground(Color.WHITE);
        getEditor().getComponent(0).setForeground(Color.WHITE);
        getEditor().getComponent(0).setBackground(new Color(55, 55, 55));
        setBorder(BorderFactory.createLineBorder(new Color(35, 35, 35)));
        for(int i = 0; i < getComponentCount(); i++) {
            Component c = getComponent(i);
            if(c instanceof JButton) {
                c.setBackground(new Color(62, 62, 62));
                ((JButton) c).setBorder(BorderFactory.createLineBorder(new Color(35, 35, 35)));
            }
        }
    }

    public SESpinner(int val, int min, int max, int step) {
        this(new SpinnerNumberModel(val, min, max, step));
    }

    public SESpinner(double val, double min, double max, double step) {
        this(new SpinnerNumberModel(val, min, max, step));
    }

}
