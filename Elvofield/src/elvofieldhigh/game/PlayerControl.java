/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package elvofieldhigh.game;


import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


/**
 *
 * @author anthony-pc
 */
public class PlayerControl implements KeyListener {

    private Player t1;
    private final int up;
    private final int down;
    private final int right;
    private final int left;
    
    public PlayerControl(Player t1, int up, int down, int left, int right) {
        this.t1 = t1;
        this.up = up;
        this.down = down;
        this.right = right;
        this.left = left;
    }

    @Override
    public void keyTyped(KeyEvent ke) {

    }

    @Override
    public void keyPressed(KeyEvent ke) {
        int keyPressed = ke.getKeyCode();
        if (keyPressed == up /*&& !t1.getDown()*/) {
            this.t1.toggleUpPressed();
            this.t1.unToggleDownPressed();
        }
        if (keyPressed == down /*&& !t1.getUp()*/) {
            this.t1.toggleDownPressed();
        }
        if (keyPressed == left /*&& !t1.getRight()*/) {
            this.t1.toggleLeftPressed();
        }
        if (keyPressed == right /*&& !t1.getLeft()*/) {
            this.t1.toggleRightPressed();
        }
    }

    @Override
    public void keyReleased(KeyEvent ke) {
        t1.updateSprite();
        int keyReleased = ke.getKeyCode();
        if (keyReleased  == up) {
            this.t1.unToggleUpPressed();
        }
        if (keyReleased == down) {
            this.t1.unToggleDownPressed();
        }
        if (keyReleased  == left) {
            this.t1.unToggleLeftPressed();
        }
        if (keyReleased  == right) {
            this.t1.unToggleRightPressed();
        }
    }
}
