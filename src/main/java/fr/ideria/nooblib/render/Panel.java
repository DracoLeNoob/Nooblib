package fr.ideria.nooblib.render;

import javax.swing.*;
import java.awt.*;

public abstract class Panel extends JPanel {
    private Color background;

    public Panel(Color background){
        this.background = background;
    }

    @Override protected void paintComponent(Graphics g) {
        g.setColor(background);
        g.fillRect(0, 0, getWidth(), getHeight());

        paintPanel((Graphics2D) g);
    }

    protected abstract void paintPanel(Graphics2D g);

    @Override public void setBackground(Color background) { this.background = background; }
}