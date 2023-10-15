package elvofieldhigh.menus;

import elvofieldhigh.Launcher;
import elvofieldhigh.game.Resources;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class GameOverGamePanel extends JPanel {

    private BufferedImage menuBackground;
    private JButton start;
    private JButton exit;
    private Launcher lf;

    public GameOverGamePanel(Launcher lf) {
        this.lf = lf;
        menuBackground = Resources.getSprite("gameOver");
        this.setBackground(Color.BLACK);
        this.setLayout(null);

        start = new JButton("Restart Game");
        start.setFont(new Font("Courier New", Font.BOLD, 24));
        start.setBounds(150, 300, 250, 50);
        start.addActionListener((actionEvent -> {
            /*
             * Reset game to its initial state.
             */
            lf.setBoss(0);
            lf.setPlayerHP(20);
            lf.setReset(true);
            //lf.setBossAppear(true);
            this.lf.setFrame("start");
            lf.bgMusic = Resources.getSound("title");
            lf.bgMusic.setLooping();
        }));


        exit = new JButton("Exit");
        exit.setFont(new Font("Courier New", Font.BOLD, 24));
        exit.setBounds(150, 400, 250, 50);
        exit.addActionListener((actionEvent -> {
            this.lf.closeGame();
        }));

        this.add(start);
        this.add(exit);

    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(this.menuBackground, 0, 0, null);
    }
}
