package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SEFrame extends JFrame {

    private int xPos = 25;
    private int yPos = 25;

    public SEFrame(String title) {
        super(title);
        setLocation(new Point(xPos, yPos));
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent me){
                xPos = me.getX();
                yPos = me.getY();
            }
        });
        addMouseMotionListener(new MouseAdapter() {
            public void mouseDragged(MouseEvent me) {
                setLocation(new Point(me.getXOnScreen() - xPos, me.getYOnScreen() - yPos));
            }
        });
    }



}
