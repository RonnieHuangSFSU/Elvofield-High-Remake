package elvofieldhigh.game;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 *
 * @author Ronnie
 */
public class Boss extends GameObject{
    float x, y;
    BufferedImage img;

    public Boss(float x, float y, BufferedImage img) {
        this.x = x;
        this.y = y;
        this.img = img;
        this.hitbox = new Rectangle((int)x,(int)y, this.img.getWidth(), this.img.getHeight());
    }

    @Override
    public void drawImage(Graphics g) {
        g.drawImage(this.img, (int)x, (int)y, null);
    }
}
