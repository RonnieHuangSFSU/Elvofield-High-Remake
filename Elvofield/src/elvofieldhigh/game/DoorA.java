package elvofieldhigh.game;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 *
 * @author Ronnie
 */
public class DoorA extends GameObject{
    float x, y;
    BufferedImage img;

    public DoorA(float x, float y, BufferedImage img) {
        this.x = x;
        this.y = y;
        this.img = img;
        this.hitbox = new Rectangle((int)x,(int)y, this.img.getWidth(), this.img.getHeight());
    }

    @Override
    public void drawImage(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g.drawImage(this.img, (int)x, (int)y, null);
        g2d.drawRect((int)x,(int)y,this.img.getWidth(), this.img.getHeight());
    }
}
