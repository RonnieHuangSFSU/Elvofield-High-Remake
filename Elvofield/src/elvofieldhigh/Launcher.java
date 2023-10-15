package elvofieldhigh;


import elvofieldhigh.battle.BattleWorld;
import elvofieldhigh.game.GameWorld;
import elvofieldhigh.game.Resources;
import elvofieldhigh.game.Sound;
import elvofieldhigh.menus.EndGamePanel;
import elvofieldhigh.menus.GameOverGamePanel;
import elvofieldhigh.menus.StartMenuPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.util.Objects;
import java.util.Scanner;

public class Launcher {
    /*
     * Main panel in JFrame, the layout of this panel
     * will be card layout, this will allow us to switch
     * to sub-panels depending on game state.
     */
    private JPanel mainPanel;
    /*
     * start panel will be used to view the start menu. It will contain
     * two buttons start and exit.
     */
    private float xAxis = 300, yAxis = 300;
    private JPanel startPanel;
    /*
     * game panel is used to show our game to the screen. inside this panel
     * also contains the game loop. This is where out objects are updated and
     * redrawn. This panel will execute its game loop on a separate thread.
     * This is to ensure responsiveness of the GUI. It is also a bad practice to
     * run long-running loops(or tasks) on Java Swing's main thread. This thread is
     * called the event dispatch thread.
     */
    private GameWorld gamePanel;
    private boolean bossAppear = true;
    public Sound bgMusic;
    private BattleWorld battlePanel;
    /*
     * end panel is used to show the end game panel.  it will contain
     * two buttons restart and exit.
     */
    private JPanel gameOverPanel;
    private JPanel endPanel;
    /*
     * JFrame used to store our main panel. We will also attach all event
     * listeners to this JFrame.
     */
    private JFrame jf;
    /*
     * CardLayout is used to manage our sub-panels. This is a layout manager
     * used for our game. It will be attached to the main panel.
     */
    private CardLayout cl;
    private String PlayerName;
    private int PlayerHP = 20, boss = 0;
    private boolean reset = true;

    public Launcher(){
        Scanner myObj = new Scanner(System.in);
        System.out.println("Type name: ");
        String heroName = myObj.nextLine();
        while (heroName.length() > 10){
            System.out.println("Too long, try again: ");
            heroName = myObj.nextLine();
        }
        setPlayerName(heroName);
        this.jf = new JFrame();             // creating a new JFrame object
        this.jf.setTitle("Elvofield"); // setting the title of the JFrame window.
        this.jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // when the GUI is closed, this will also shut down the VM
    }


    private void initUIComponents(){
        Resources.loadResources();
        this.mainPanel = new JPanel(); // create a new main panel
        this.startPanel = new StartMenuPanel(this); // create a new start panel
        this.battlePanel = new BattleWorld(this);
        this.gamePanel = new GameWorld(this); // create a new game panel
        //Resources.loadResources();
        this.gamePanel.InitializeGame(); // initialize game, but DO NOT start game
        this.endPanel = new EndGamePanel(this); // create a new end game pane;
        this.gameOverPanel = new GameOverGamePanel(this); // create a new start panel
        cl = new CardLayout(); // creating a new CardLayout Panel
        this.mainPanel.setLayout(cl); // set the layout of the main panel to our card layout
        this.mainPanel.add(startPanel, "start"); //add the start panel to the main panel
        this.mainPanel.add(gamePanel, "game");   //add the game panel to the main panel
        this.mainPanel.add(battlePanel, "battle");
        this.mainPanel.add(gameOverPanel, "gameOver");
        this.mainPanel.add(endPanel, "end");    // add the end game panel to the main panel
        this.jf.add(mainPanel); // add the main panel to the JFrame
        this.jf.setResizable(false); //make the JFrame not resizable
        this.setFrame("start"); // set the current panel to start panel
    }

    public void setPlayerHP(int setter){
        this.PlayerHP = setter;
    }

    public int getPlayerHP(){
        return this.PlayerHP;
    }

    public void setXAxis(float setter){
        this.xAxis = setter;
    }

    public float getXAxis(){
        return xAxis;
    }

    public void setYAxis(float setter){
        this.yAxis = setter;
    }

    public float getYAxis(){
        return yAxis;
    }

    public void setBoss(int setter){
        boss = setter;
    }

    public int getBoss(){
        return this.boss;
    }

    public String getPlayerName(){
        return PlayerName;
    }

    public void setFrame(String type){
        this.jf.setVisible(false); // hide the JFrame
        if (!Objects.equals(type, "start"))
            bgMusic.stopSound();
        switch(type){
            case "start":
                // set the size of the jFrame to the expected size for the start panel
                this.jf.setSize(GameConstants.START_MENU_SCREEN_WIDTH,GameConstants.START_MENU_SCREEN_HEIGHT);
                break;
            case "battle":
                // set the size of the jFrame to the expected size for the game panel
                //this.jf.setSize(GameConstants.GAME_SCREEN_WIDTH,GameConstants.GAME_SCREEN_HEIGHT);
                this.jf.setSize(0,0);
                //start a new thread for the game to run. This will ensure our JFrame is responsive and
                // not stuck executing the game loop.
                (new Thread(this.battlePanel)).start();
                break;
            case "game":
                // set the size of the jFrame to the expected size for the game panel
                this.jf.setSize(GameConstants.GAME_SCREEN_WIDTH,GameConstants.GAME_SCREEN_HEIGHT);
                //start a new thread for the game to run. This will ensure our JFrame is responsive and
                // not stuck executing the game loop.
                (new Thread(this.gamePanel)).start();
                break;
            case "end":
                // set the size of the jFrame to the expected size for the end panel
                this.jf.setSize(GameConstants.END_MENU_SCREEN_WIDTH,GameConstants.END_MENU_SCREEN_HEIGHT);
                (new Thread(String.valueOf(this.endPanel))).start();
                break;
            case "gameOver":
                // set the size of the jFrame to the expected size for the end panel
                this.jf.setSize(GameConstants.END_MENU_SCREEN_WIDTH,GameConstants.END_MENU_SCREEN_HEIGHT);
                (new Thread(String.valueOf(this.gameOverPanel))).start();
                break;
        }
        this.cl.show(mainPanel, type); // change current panel shown on main panel tp the panel denoted by type.
        this.jf.setVisible(true); // show the JFrame
    }


    public JFrame getJf() {
        return jf;
    }

    public boolean getBossAppear(){
        return bossAppear;
    }

    public void closeGame(){
        this.jf.dispatchEvent(new WindowEvent(this.jf, WindowEvent.WINDOW_CLOSING));
    }

    public void setPlayerName(String name){
        this.PlayerName = name;
    }
    public boolean getReset(){
        return reset;
    }
    public void setReset(boolean temp){
        reset = temp;
    }

    public static void main(String[] args) {
        (new Launcher()).initUIComponents();
    }

}
