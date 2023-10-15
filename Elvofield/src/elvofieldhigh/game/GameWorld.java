/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package elvofieldhigh.game;

import elvofieldhigh.GameConstants;
import elvofieldhigh.Launcher;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * @author Ronnie
 */
public class GameWorld extends JPanel implements Runnable {
    private List<GameObject> gameObjects = new ArrayList<>(500);
    private BufferedImage world;

    private Player t1;
    private Boss boss;
    private Launcher lf;
    private DoorA door1;

    /**
     *
     * @param lf
     */
    public GameWorld(Launcher lf) {
        this.lf = lf;
    }

    @Override
    public void run() {
        t1.unToggleUpPressed();
        t1.unToggleLeftPressed();
        t1.unToggleRightPressed();
        t1.unToggleDownPressed();
        try {
            t1.randomEncounter();
            lf.bgMusic = Resources.getSound("lobby");
            lf.bgMusic.setLooping();
            if (lf.getReset()) {
                resetMap();
                lf.setReset(false);
            }
            while (true) {
                if(this.t1.update()){
                    t1.unToggleUpPressed();
                    t1.unToggleLeftPressed();
                    t1.unToggleRightPressed();
                    t1.unToggleDownPressed();
                    lf.setFrame("battle");
                    lf.setXAxis(t1.getXAxis());
                    lf.setYAxis(t1.getYAxis());
                    return;
                } // update tank
                checkCollisions();
                this.gameObjects.removeIf(g -> g.hasCollided);
                this.repaint();   // redraw game

                /*
                 * Sleep for 1000/144 ms (~6.9ms). This is done to have our
                 * loop run at a fixed rate per/sec.
                */
                Thread.sleep(1000 / 144);
            }
        } catch (InterruptedException c) {
            System.out.println(c);
        }
    }

    private void checkCollisions() {
        for(int i = 0; i < this.gameObjects.size(); i++){
            GameObject temp = this.gameObjects.get(i);
            if (temp instanceof Wall) continue;
            for(int j = 0; j < this.gameObjects.size(); j++) {
                if(i==j) continue;
                GameObject temp2 = this.gameObjects.get(j);
                if (temp.getHitbox().intersects((temp2.getHitbox()))){
                    if (temp2 instanceof Boss && !(temp2.hasCollided)){
                        if (temp instanceof Player) {
                            temp2.hasCollided = true;
                            t1.unToggleUpPressed();
                            t1.unToggleLeftPressed();
                            t1.unToggleRightPressed();
                            t1.unToggleDownPressed();
                            lf.setBoss(1);
                            lf.setFrame("battle");
                            return;
                        }
                    }
                    if (temp2 instanceof Hole){
                        if (temp instanceof Player) {
                            //temp2.hasCollided = true;
                            t1.setInWall(true);
                            return;
                        }
                    }
                    if (temp2 instanceof DoorA && !(temp2.hasCollided)){
                        if (temp instanceof Player) {
                            if(boss.hasCollided){
                                temp2.hasCollided = true;
                                t1.unToggleUpPressed();
                                t1.unToggleLeftPressed();
                                t1.unToggleRightPressed();
                                t1.unToggleDownPressed();
                                this.lf.setFrame("end");
                                return;
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Load all resources for Tank Wars Game. Set all Game Objects to their
     * initial state as well.
     */
    public void InitializeGame() {
        this.world = new BufferedImage(GameConstants.WORLD_WIDTH,
                GameConstants.WORLD_HEIGHT,
                BufferedImage.TYPE_INT_RGB);

        try(BufferedReader mapReader = new BufferedReader(new InputStreamReader
                (Objects.requireNonNull(GameWorld.class.getClassLoader().getResourceAsStream("maps/map1.csv"))))) {
            for (int i = 0; mapReader.ready(); i++) {
                String[] gameObjects = mapReader.readLine().split(",");
                for(int j = 0; j<gameObjects.length; j++){
                    String objectType = gameObjects[j];
                    if (Objects.equals("0", objectType)) continue;
                    this.gameObjects.add(GameObject.gameObjectFactory(objectType, j*30, i*30));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        t1 = new Player(lf.getXAxis(), lf.getYAxis(), Resources.getSprite("pdown1"));
        PlayerControl tc1 = new PlayerControl(t1, KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT,
                KeyEvent.VK_RIGHT);
        this.lf.getJf().addKeyListener(tc1);
        this.gameObjects.add(t1);
    }

    public void resetMap(){
        t1.reset();
        boss = new Boss(1050, 370, Resources.getSprite("boss"));
        this.gameObjects.add(boss);
        boss.hasCollided = false;
        door1 = new DoorA(1170, 60, Resources.getSprite("door1"));
        this.gameObjects.add(door1);
        door1.hasCollided = false;
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        Graphics2D buffer = world.createGraphics();
        buffer.setColor(Color.darkGray);
        buffer.fillRect(0,0,GameConstants.WORLD_WIDTH, GameConstants.WORLD_HEIGHT);
        drawFloor(buffer);
        this.gameObjects.forEach(gObj -> gObj.drawImage(buffer));
        this.t1.drawImage(buffer);
        drawSplitScreen(g2, world);
        drawMiniMap(g2, world);
        g.setFont(new Font("Courier New", Font.BOLD, 32));
        g.drawString(lf.getPlayerName(), 1000,79);
        g.drawString(lf.getPlayerHP() + " / 20", 1000,129);
        g2.drawImage(Resources.getSprite("burrito"), 700, 29, null);
    }

    void drawFloor(Graphics2D kurumi){
        for (int i = 0; i<GameConstants.WORLD_WIDTH;i+=180){
            for (int j=0; j<GameConstants.WORLD_HEIGHT;j+=180){
                kurumi.drawImage(Resources.getSprite("floor"), i, j, null);
            }
        }
    }

    void drawMiniMap(Graphics2D graphic, BufferedImage buffered){
        BufferedImage mini = buffered.getSubimage(0, 0, GameConstants.WORLD_WIDTH, GameConstants.WORLD_HEIGHT);
        AffineTransform at = new AffineTransform();
        //Position of mini map on screen, with the width being in the middle and the height being 0
        at.translate(GameConstants.GAME_SCREEN_WIDTH/2f /*- (GameConstants.GAME_SCREEN_WIDTH*.2f)/2f*/,
                GameConstants.WORLD_HEIGHT-(GameConstants.WORLD_HEIGHT/2));
        at.scale(.5, .5);
        graphic.drawImage(mini, at, null);
    }

    //May not actually be used
    void drawSplitScreen(Graphics2D graphic, BufferedImage buffered){
        BufferedImage left = buffered.getSubimage(t1.getScreenX(),
                t1.getScreenY(),GameConstants.GAME_SCREEN_WIDTH/2,GameConstants.GAME_SCREEN_HEIGHT);
        graphic.drawImage(left, 0, 0, null);
    }

    void addGameWorld(){

    }

//    public static void resetTick(){
//        this.tick = 0;
//    }

}
