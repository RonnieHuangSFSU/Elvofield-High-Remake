package elvofieldhigh.menus;

import elvofieldhigh.Launcher;
import elvofieldhigh.game.Resources;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class EndGamePanel extends JPanel {

    private BufferedImage menuBackground;
    private JButton exit;
    private Launcher lf;

    public EndGamePanel(Launcher lf) {
        this.lf = lf;
        menuBackground = Resources.getSprite("end");
        this.setBackground(Color.BLACK);
        this.setLayout(null);

        exit = new JButton("Exit");
        exit.setFont(new Font("Courier New", Font.BOLD, 24));
        exit.setBounds(150, 300, 250, 50);
        exit.addActionListener((actionEvent -> {
            this.lf.closeGame();
        }));

        this.add(exit);

    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(this.menuBackground, 0, 0, null);
    }
}
