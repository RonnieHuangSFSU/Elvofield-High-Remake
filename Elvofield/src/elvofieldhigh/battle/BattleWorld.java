/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package elvofieldhigh.battle;


import elvofieldhigh.Launcher;

import javax.swing.*;


/**
 * @author Ronnie
 */
public class BattleWorld extends JPanel implements Runnable {

    private Launcher lf;

    public BattleWorld(Launcher lf) {
        this.lf = lf;
    }

    @Override
    public void run() {
        new Battle(this.lf);
    }
}
