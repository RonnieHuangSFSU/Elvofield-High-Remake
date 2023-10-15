/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package elvofieldhigh.game;


import java.awt.*;

/**
 * @author Ronnie
 */
public abstract class GameObject{
    protected boolean hasCollided = false;
    protected Rectangle hitbox;
    public Rectangle getHitbox(){
        return this.hitbox.getBounds();
    }

    public static GameObject gameObjectFactory(String objectType, int i, int j) {
        return switch (objectType){
            case "1" -> //door (backtrack)
                new DoorA(j, i, Resources.getSprite("door1"));
            case "3" -> //hole
                new Hole(j, i, Resources.getSprite("hole"));
            case "5" -> //boss
                new Boss(j, i, Resources.getSprite("boss"));
            case "9" -> //wall
                new Wall(j, i, Resources.getSprite("wall"));
            default ->
                throw new IllegalStateException("Expected Value " + objectType);

        };
    }
    public abstract void drawImage(Graphics g);

    public void setHasCollided(boolean hasCollided) {
        this.hasCollided = hasCollided;
    }


}
