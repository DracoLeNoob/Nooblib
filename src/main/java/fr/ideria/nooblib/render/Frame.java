package fr.ideria.nooblib.render;

import fr.ideria.nooblib.render.input.KeyReader;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Frame {
    public static final int IGNORE = 0;
    public static final int HIDE = 1;
    public static final int DISPOSE = 2;
    public static final int CLOSE = 3;

    private final JFrame window;

    public Frame(){ this.window = new JFrame(); }

    private Frame get(Runnable action){
        action.run();
        return this;
    }

    public Frame setTitle(String title){ return get(() -> window.setTitle(title)); }
    public Frame setSize(int width, int height){ return get(() -> window.setSize(width, height)); }
    public Frame setMinimumSize(int width, int height){ return get(() -> window.setMinimumSize(new Dimension(width, height))); }
    public Frame setMaximumSize(int width, int height){ return get(() -> window.setMaximumSize(new Dimension(width, height))); }
    public Frame setContentPane(Panel panel){ return get(() -> window.setContentPane(panel)); }
    public Frame setCloseMode(int close){ return get(() -> window.setDefaultCloseOperation(close)); }
    public Frame setResizable(boolean resizable){ return get(() -> window.setResizable(resizable)); }
    public Frame setAlwaysOnTop(boolean top){ return get(() -> window.setAlwaysOnTop(top)); }
    public Frame setIcon(BufferedImage icon){ return get(() -> window.setIconImage(icon)); }
    public Frame setKeyListener(KeyReader listener){ return get(() -> window.addKeyListener(listener)); }
    public Frame build(){
        return get(() -> {
            window.setLocationRelativeTo(null);
            window.setVisible(true);
        });
    }

    public void render(){ window.getContentPane().repaint(); }
}