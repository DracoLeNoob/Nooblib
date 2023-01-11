package fr.ideria.nooblib.render.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyReader implements KeyListener {
    private static final boolean[] keys;

    static{
        keys = new boolean[65535];
    }

    public static boolean isKeyPressed(int key){ return keys[key]; }

    @Override public void keyTyped(KeyEvent e) {}
    @Override public void keyPressed(KeyEvent e) { keys[e.getKeyCode()] = true; }
    @Override public void keyReleased(KeyEvent e) { keys[e.getKeyCode()] = false; }
}