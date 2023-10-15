package elvofieldhigh.game;

import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;

public class Resources {

    private static final Map<String, BufferedImage> sprites = new HashMap<>();
    private static final Map<String, Sound> sounds = new HashMap<>();
    private static final Map<String, List<BufferedImage>> animations = new HashMap<>();

    private static BufferedImage loadSprite(String path) throws IOException{
        return ImageIO.read(Objects.requireNonNull(GameWorld.class.getClassLoader().getResource(path)));
    }

    private static void initSprites(){
        try {
            Resources.sprites.put("burrito", loadSprite("Overworld/burrito.png"));
            Resources.sprites.put("pdown0", loadSprite("Overworld/pdown00.png"));
            Resources.sprites.put("pup0", loadSprite("Overworld/pup00.png"));
            Resources.sprites.put("pleft0", loadSprite("Overworld/pleft00.png"));
            Resources.sprites.put("pright0", loadSprite("Overworld/pright00.png"));
            Resources.sprites.put("pdown1", loadSprite("Overworld/pdown01.png"));
            Resources.sprites.put("pup1", loadSprite("Overworld/pup01.png"));
            Resources.sprites.put("pleft1", loadSprite("Overworld/pleft01.png"));
            Resources.sprites.put("pright1", loadSprite("Overworld/pright01.png"));
            Resources.sprites.put("pdown2", loadSprite("Overworld/pdown02.png"));
            Resources.sprites.put("pup2", loadSprite("Overworld/pup02.png"));
            Resources.sprites.put("pleft2", loadSprite("Overworld/pleft02.png"));
            Resources.sprites.put("pright2", loadSprite("Overworld/pright02.png"));
            Resources.sprites.put("pdown3", loadSprite("Overworld/pdown01.png"));
            Resources.sprites.put("pup3", loadSprite("Overworld/pup01.png"));
            Resources.sprites.put("pleft3", loadSprite("Overworld/pleft01.png"));
            Resources.sprites.put("pright3", loadSprite("Overworld/pright01.png"));
            Resources.sprites.put("boss", loadSprite("Overworld/boss.png"));
            Resources.sprites.put("start", loadSprite("Screens/title.png"));
            Resources.sprites.put("Ghost", loadSprite("Battle/ghost.png"));
            Resources.sprites.put("Warrior", loadSprite("Battle/warrior.png"));
            Resources.sprites.put("Fairy", loadSprite("Battle/fairy.png"));
            Resources.sprites.put("Bat", loadSprite("Battle/bat.png"));
            Resources.sprites.put("gameOver", loadSprite("Screens/gameover.png"));
            Resources.sprites.put("end", loadSprite("Screens/ending.png"));
            Resources.sprites.put("hole", loadSprite("Overworld/hole.png"));
            Resources.sprites.put("floor", loadSprite("Floor/wood2.png"));
            Resources.sprites.put("wall", loadSprite("Overworld/wall.png"));
            Resources.sprites.put("door1", loadSprite("Overworld/door1.png"));
            Resources.sprites.put("door2", loadSprite("Overworld/door2.png"));
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.exit(-1);
        }
    }
    private static void initSounds(){
        AudioInputStream audioStream;
        Clip c;
        Sound s;
        try {



            audioStream = AudioSystem.getAudioInputStream(
                    //Resources.class.getClassLoader().getResource("Sounds/Night.mp3"));
                    Resources.class.getClassLoader().getResource("Sounds/Night1.wav"));
            c = AudioSystem.getClip();
            c.open((audioStream));
            s = new Sound(c);
            s.setVolume(0.5f);
            Resources.sounds.put("bg", s);
            audioStream = AudioSystem.getAudioInputStream(
                    Resources.class.getClassLoader().getResource("Sounds/Hit1.wav"));
            c = AudioSystem.getClip();
            c.open((audioStream));
            s = new Sound(c);
            s.setVolume(0.75f);
            Resources.sounds.put("hit", s);
            audioStream = AudioSystem.getAudioInputStream(
                    Resources.class.getClassLoader().getResource("Sounds/Bash1.wav"));
            c = AudioSystem.getClip();
            c.open((audioStream));
            s = new Sound(c);
            s.setVolume(0.75f);
            Resources.sounds.put("bash", s);
            audioStream = AudioSystem.getAudioInputStream(
                    Resources.class.getClassLoader().getResource("Sounds/Victory.wav"));
            c = AudioSystem.getClip();
            c.open((audioStream));
            s = new Sound(c);
            s.setVolume(0.75f);
            Resources.sounds.put("victory", s);
            audioStream = AudioSystem.getAudioInputStream(
                    Resources.class.getClassLoader().getResource("Sounds/Heal.wav"));
            c = AudioSystem.getClip();
            c.open((audioStream));
            s = new Sound(c);
            s.setVolume(0.75f);
            Resources.sounds.put("heal", s);
            audioStream = AudioSystem.getAudioInputStream(
                    Resources.class.getClassLoader().getResource("Sounds/Players.wav"));
            c = AudioSystem.getClip();
            c.open((audioStream));
            s = new Sound(c);
            s.setVolume(1.00f);
            Resources.sounds.put("players", s);
            audioStream = AudioSystem.getAudioInputStream(
                    Resources.class.getClassLoader().getResource("Sounds/Title.wav"));
            c = AudioSystem.getClip();
            c.open((audioStream));
            s = new Sound(c);
            s.setVolume(0.25f);
            Resources.sounds.put("title", s);
            audioStream = AudioSystem.getAudioInputStream(
                    Resources.class.getClassLoader().getResource("Sounds/Enemy.wav"));
            c = AudioSystem.getClip();
            c.open((audioStream));
            s = new Sound(c);
            s.setVolume(0.25f);
            Resources.sounds.put("enemy", s);
            audioStream = AudioSystem.getAudioInputStream(
                    Resources.class.getClassLoader().getResource("Sounds/Lobby.wav"));
            c = AudioSystem.getClip();
            c.open((audioStream));
            s = new Sound(c);
            s.setVolume(0.25f);
            Resources.sounds.put("lobby", s);
            audioStream = AudioSystem.getAudioInputStream(
                    Resources.class.getClassLoader().getResource("Sounds/Boss.wav"));
            c = AudioSystem.getClip();
            c.open((audioStream));
            s = new Sound(c);
            s.setVolume(0.25f);
            Resources.sounds.put("boss", s);
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }
    public static void loadResources(){
        //initAnimations();
        initSounds();
        initSprites();
    }

    //The uses would be 'Resources.getSprite("door1")'
    public static BufferedImage getSprite(String imageName){
        if (!Resources.sprites.containsKey(imageName)){
            System.out.println("Missing image - " + imageName);
            System.exit(-2);
        }
        return Resources.sprites.get(imageName);
    }
    public static Sound getSound(String soundName){
        if (!Resources.sounds.containsKey(soundName)){
            System.out.println("Missing sound - " + soundName);
            System.exit(-2);
        }
        return Resources.sounds.get(soundName);
    }

    public static List<BufferedImage> getAnimation(String animationName){
        if (!Resources.animations.containsKey(animationName)){
            System.out.println("Missing animation - " + animationName);
            System.exit(-2);
        }
        return Resources.animations.get(animationName);
    }
}
