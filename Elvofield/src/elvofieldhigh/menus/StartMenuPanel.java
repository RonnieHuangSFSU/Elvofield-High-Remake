package elvofieldhigh.menus;


import elvofieldhigh.Launcher;
import elvofieldhigh.game.Resources;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class StartMenuPanel extends JPanel {

    private BufferedImage menuBackground;
    private JButton start;
    private JButton exit;
    private Launcher lf;

    public StartMenuPanel(Launcher lf) {
        this.lf = lf;
        lf.bgMusic = Resources.getSound("title");
        lf.bgMusic.setLooping();

        menuBackground = Resources.getSprite("start");
        this.setBackground(Color.BLACK);
        this.setLayout(null);

        start = new JButton("Game");
        start.setFont(new Font("Courier New", Font.BOLD, 24));
        start.setBounds(150, 300, 150, 50);
        start.addActionListener((actionEvent -> {
            this.lf.setFrame("game");
        }));


        exit = new JButton("Preview");
        exit.setSize(new Dimension(200, 100));
        exit.setFont(new Font("Courier New", Font.BOLD, 24));
        exit.setBounds(150, 400, 150, 50);
        exit.addActionListener((actionEvent -> {
            lf.setBoss(2);
            this.lf.setFrame("battle");
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
