package elvofieldhigh.game;

import elvofieldhigh.GameConstants;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

/**
 *
 * @author anthony-pc
 */
public class Player extends GameObject{
    private double counter = 0, encounter = 2;

    private float x, screenX;
    private float y, screenY;
    private BufferedImage img;
    private boolean UpPressed;
    private boolean DownPressed;
    private boolean RightPressed;
    private boolean LeftPressed;
    private boolean InteractPressed;
    private boolean inWall;

    Player(float x, float y, BufferedImage img) {
        this.x = x;
        this.y = y;
        this.img = img;
        this.hitbox = new Rectangle((int)x,(int)y, this.img.getWidth(), this.img.getHeight());
    }

    public void reset(){
        this.x = 300;
        this.y = 300;
        this.img = Resources.getSprite("pdown1");
    }

    void toggleUpPressed() {
        this.UpPressed = true;
    }
    void toggleDownPressed() {this.DownPressed = true;}
    void toggleRightPressed() {
        this.RightPressed = true;
    }
    void toggleLeftPressed() {
        this.LeftPressed = true;
    }
    void unToggleUpPressed() {
        this.UpPressed = false;
    }

    void unToggleDownPressed() {
        this.DownPressed = false;
    }

    void unToggleRightPressed() {
        this.RightPressed = false;
    }

    void unToggleLeftPressed() {
        this.LeftPressed = false;
    }
    void unToggleInteract() {
        this.InteractPressed = false;
    }

    boolean update() {
        updateAnimation();
        if (this.UpPressed) {
            this.moveUp();
        }

        if (this.DownPressed) {
            this.moveDown();
        }

        if (this.LeftPressed) {
            this.moveLeft();
        }

        if (this.RightPressed) {
            this.moveRight();
        }

        if (this.InteractPressed){
            this.interact();
        }

        if (UpPressed || DownPressed || LeftPressed || RightPressed){
            encounter--;
        }
        centerScreen();
        if (encounter == 0){
            encounter = Math.ceil(Math.random()*100+300);
            return true;
        }
        else {
            return false;
        }
    }

    private void moveLeft() {
        if (!inWall)
            x -= 2;
        else {
            x += 2;
            unToggleLeftPressed();
            inWall = false;
        }
        checkBorder();
        this.hitbox.setLocation((int)x, (int)y);
    }


    private void moveRight() {
        if (!inWall)
            x += 2;
        else {
            x -= 2;
            unToggleRightPressed();
            inWall = false;
        }
        checkBorder();
        this.hitbox.setLocation((int)x, (int)y);
    }

    private void moveDown() {
        if (!inWall)
            y += 2;
        else {
            y -= 2;
            unToggleDownPressed();
            inWall = false;
        }
       checkBorder();
        this.hitbox.setLocation((int)x, (int)y);
    }

    private void moveUp() {
        if (!inWall)
            y -= 2;
        else {
            y += 2;
            unToggleUpPressed();
            inWall = false;
        }
        checkBorder();
        this.hitbox.setLocation((int)x, (int)y);
    }
    private void interact() {
        //System.out.println("Interact with environment");
        //if (tile player standing in = door) {enter next/earlier room}
    }
    private void checkBorder() {
        if (x < 30) {
            x = 30;
        }
        if (x >= GameConstants.GAME_SCREEN_WIDTH - 88) {
            x = GameConstants.GAME_SCREEN_WIDTH - 88;
        }
        if (y < 40) {
            y = 40;
        }
        if (y >= GameConstants.GAME_SCREEN_HEIGHT - 80) {
            y = GameConstants.GAME_SCREEN_HEIGHT - 80;
        }
    }
    private void centerScreen(){
        this.screenX = this.x - GameConstants.GAME_SCREEN_WIDTH/4f;
        this.screenY = this.y - GameConstants.GAME_SCREEN_HEIGHT/2f;

        if (this.screenX < 0) screenX = 0;
        if (this.screenY < 0) screenY = 0;

        if (this.screenX > GameConstants.WORLD_WIDTH - GameConstants.GAME_SCREEN_WIDTH/2f){
             this.screenX = GameConstants.WORLD_WIDTH - GameConstants.GAME_SCREEN_WIDTH/2f;
        }
        if (this.screenY > GameConstants.WORLD_HEIGHT - GameConstants.GAME_SCREEN_HEIGHT){
             this.screenY = GameConstants.WORLD_HEIGHT - GameConstants.GAME_SCREEN_HEIGHT;
        }
    }

    public void drawImage(Graphics g) {
        AffineTransform rotation = AffineTransform.getTranslateInstance(x, y);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(this.img, rotation, null);
    }

    public int getScreenX(){
        return (int)screenX;
    }

    public int getScreenY(){
        return (int)screenY;
    }

    public void updateSprite(){
        if (UpPressed){
            this.img = Resources.getSprite("pup1");
        }
        else if (DownPressed){
            this.img = Resources.getSprite("pdown1");
        }
        else if (LeftPressed){
            this.img = Resources.getSprite("pleft1");
        }
        else if (RightPressed){
            this.img = Resources.getSprite("pright1");
        }
    }

    public void updateAnimation(){
        if (UpPressed){
            String turtle = "pup%01d";
            String fName = String.format(turtle, animationCounter());
            this.img = Resources.getSprite(fName);
        }
        else if (DownPressed){
            String turtle = "pdown%01d";
            String fName = String.format(turtle, animationCounter());
            this.img = Resources.getSprite(fName);
        }
        else if (LeftPressed){
            String turtle = "pleft%01d";
            String fName = String.format(turtle, animationCounter());
            this.img = Resources.getSprite(fName);
        }
        else if (RightPressed){
            String turtle = "pright%01d";
            String fName = String.format(turtle, animationCounter());
            this.img = Resources.getSprite(fName);
        }

    }

    public int animationCounter(){
        if (counter != 119){
            counter++;

        }
        else {
            counter=0;
        }

        return (int) Math.floor(counter/30);
    }

    public void randomEncounter(){
        encounter = (int) (Math.random() * 300);
    }

    public void setInWall(boolean kurumi) {
        this.inWall = kurumi;
    }

    public float getXAxis() {
        return x;
    }

    public float getYAxis() {
        return y;
    }
}
