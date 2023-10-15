package elvofieldhigh.battle;

import elvofieldhigh.Launcher;
import elvofieldhigh.game.Resources;
import elvofieldhigh.game.Sound;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class Battle extends JFrame implements ActionListener{
    private String AllyName, saveState;
    private /*final*/ Launcher lf;
    int enemyID;
    private /*final*/ JLabel background;
    private boolean special, guard;
    BattleObject Player, Enemy, Ally;

    private int turn = 0;
    private /*final*/ Sound bash, player, heal, victory;

    public static String text;
    private /*final*/ Image temper;
    public String move;

    private final JTextField expressionTextField = new JTextField();
    private final JTextField playerInfo = new JTextField();
    private final JPanel buttonPanel = new JPanel();

    // total of 20 buttons on the calculator,
    // numbered from left to right, top to bottom
    // bText[] array contains the text for corresponding buttons
    private final static String[] buttonText = {
            "attack",
            "defend",
            "spy",
            "items",
            "run",
            ">"
    };

    private final JButton[] buttons = new JButton[buttonText.length];

    public Battle(Launcher lf) {
        if(!(lf.getBoss()==2))
            Player = new BattleObject(lf.getPlayerName(), lf.getPlayerHP(), 4, 1, 20);
        else
            Player = new BattleObject(lf.getPlayerName(), lf.getPlayerHP()+15, 11, 3, lf.getPlayerHP()+15);
        enemyID = (int) Math.ceil(Math.random()*2);
        this.lf = lf;
        lf.bgMusic = Resources.getSound("enemy");
        bash = Resources.getSound("bash");
        player = Resources.getSound("players");
        heal = Resources.getSound("heal");
        victory = Resources.getSound("victory");
        if (lf.getBoss()==1){
            text = "Fairy flies in the sky!";
            Enemy = new BattleObject("Fairy", 12, 5, 1, 12);
            lf.bgMusic = Resources.getSound("boss");
        }
        else if (lf.getBoss()==2){
            text = "Warrior stands firm!";
            Enemy = new BattleObject("Warrior", 33, 9, 4, 30);
            if (Objects.equals(Player.getUserName().toLowerCase(), "lance")) {
                AllyName = "Peggy";
                Player.setMaxHP(Player.getMaxHP()+2);
                Player.setHP(Player.getHP()+2);
                Player.setAtk(Player.getAtk()-3);
                Player.setDef(Player.getDef()-1);
            }
            else
                AllyName = "Lance";
            lf.bgMusic = Resources.getSound("boss");
        }
        else if (enemyID == 1) {
            text = "Ghost appears!";
            Enemy = new BattleObject("Ghost", 10, 69, 0, 10);
        }
        else{
            text = "Bat flies down!";
            Enemy = new BattleObject("Bat", 12, 3, 0, 12);
        }
        temper = Resources.getSprite(Enemy.getUserName());
        ImageIcon end = new ImageIcon(temper);
        lf.bgMusic.setLooping();
        if (Objects.equals(AllyName, "Lance"))
            Ally = new BattleObject(AllyName, 0, Player.getAtk()-3, Player.getDef(), 0);
        else if (Objects.equals(AllyName, "Peggy"))
            Ally = new BattleObject(AllyName, 0, Player.getAtk()-2, Player.getDef(), 0);
        background = new JLabel();
        background.setIcon(end);
        this.add(background);

        //top text
        this.playerInfo.setPreferredSize(new Dimension(300, 40));
        this.playerInfo.setFont(new Font("Courier", Font.BOLD, 24));
        this.playerInfo.setForeground(Color.WHITE);
        this.playerInfo.setHorizontalAlignment(JTextField.CENTER);
        this.playerInfo.setBackground(Color.BLACK);
        updateTopInfo();
        add(playerInfo, BorderLayout.NORTH);
        playerInfo.setEditable(false);


        //bottom text
        this.expressionTextField.setPreferredSize(new Dimension(600, 100));
        this.expressionTextField.setFont(new Font("Courier", Font.BOLD, 24));
        this.expressionTextField.setHorizontalAlignment(JTextField.CENTER);
        this.expressionTextField.setText(text);
        add(expressionTextField, BorderLayout.SOUTH);
        expressionTextField.setEditable(false);

        //buttons on the right
        add(buttonPanel, BorderLayout.EAST);
        buttonPanel.setLayout(new GridLayout(6, 1));

        //create 6 buttons with corresponding text in bText[] array
        JButton tempButtonReference;
        for (int i = 0; i < Battle.buttonText.length; i++) {
            tempButtonReference = new JButton(buttonText[i]);
            tempButtonReference.setFont(new Font("Courier", Font.BOLD, 24));
            buttons[i] = tempButtonReference;
        }

        //add buttons to button panel
        for (int i = 0; i < Battle.buttonText.length; i++) {
            buttonPanel.add(buttons[i]);
        }

        //set up buttons to listen for mouse input
        for (int i = 0; i < Battle.buttonText.length; i++) {
            buttons[i].addActionListener(this);
        }

        setTitle("ENCOUNTER!");
        setSize(500, 400);
        setLocationByPlatform(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void updateTopInfo(){
        if (AllyName == null)
            this.playerInfo.setText(Player.getUserName() + " - " + Player.getHP() + "/" + Player.getMaxHP());
        else
            this.playerInfo.setText(Player.getUserName() + " - " + Player.getHP() + "/" + Player.getMaxHP() + "   " + Ally.getUserName() + " - ?/?");
    }

    public void playerChoice(String choice,int turn){
        if(turn != 2)
            saveState = choice;
        if (guard) {
            Player.setDef(Player.getDef()/2);
            guard = false;
        }
        switch (choice) {
            case ">" -> {
                if(turn != 2) {
                    text = Player.getUserName() + "'s turn!";
                    this.turn--;
                }
            }
            case "attack" -> {
                if (turn == 1)
                    text = Player.getUserName() + " attacks!";
                else {
                    Enemy.setHP(Enemy.getHP() - attacking(Player.getAtk(), Enemy.getUserName(), Enemy.getDef()));
                }
            }
            case "defend" -> {
                text = Player.getUserName() + " is on guard!";
                Player.setDef(Player.getDef()*2);
                guard = true;
                special = true;
            }
            case "spy" -> {
                if (turn == 1)
                    text = Player.getUserName() + " spies on opponent!";
                else {
                    if(lf.getBoss()==0)
                        text = Enemy.getUserName() + " - " + Enemy.getHP() + "/" + Enemy.getMaxHP();
                    else
                        text = Enemy.getUserName() + " - ?/?";
                }
            }
            case "items" -> {
                if (turn == 1)
                    text = Player.getUserName() + " uses a potion!";
                else {
                    if(!(lf.getBoss()==2)) {
                        heal.playSound();
                        if (Player.getHP() + 7 > Player.getMaxHP()) {
                            text = Player.getUserName() + " maxes HP!";
                            Player.setHP(Player.getMaxHP());
                        } else {
                            text = Player.getUserName() + "'s health increases by 7! ";
                            Player.setHP(Player.getHP() + 7);
                        }
                    }
                    else{
                        text = "But they have none!";
                    }
                }
                updateTopInfo();
            }
            case "run" -> {
                if (turn == 1)
                    text = Player.getUserName() + " tries to run!";
                else
                    playerRun();
            }
        }

    }

    public void playerAction(String choice){
        if (Objects.equals(choice, ">")) {
            playerChoice(saveState, 2);
        }
        else
            this.turn--;

    }

    public int attacking(int userAtk, String targetName, int targetDef){
        int reduce = userAtk - targetDef;
        if ((userAtk - targetDef) > 0){
            bash.playSound();
            text = targetName + " lost " + reduce + " HP!";
        }
        else {
            text = targetName + " took no damage!";
            reduce = 0;
        }
        return reduce;
    }

    public void turn(String name){
        text = name + "'s turn!";
    }

    public void allyChoice(){
        double random = Math.ceil(Math.random() * 5);
        if (random <= 2){
            text = Ally.getUserName() + " attacks!";
        }
        else if ((Player.getHP() < (Player.getMaxHP()-7)) && Ally.getUserName().equals("Peggy")) {
            text = Ally.getUserName() + " sings to " + Player.getUserName() + "!";
            turn = 68;
        }
        else{
            if (Objects.equals(Ally.getUserName(), "Lance"))
                text = Ally.getUserName() + " wipes his sweat!";
            else
                text = Ally.getUserName() + " sings to herself!";
            special = true;
        }
    }

    public void allyAction(){
        Enemy.setHP(Enemy.getHP() - attacking(Ally.getAtk(), Enemy.getUserName(), Enemy.getDef()));
    }

    public void playerRun(){
        int random = (int) Math.ceil(Math.random() * 4);
        //System.out.println(random);
        if (lf.getBoss()==0 && random != 1){
            victory();
            text = "And did!";
            this.turn = 10;
        }
        else
            text = "But failed!";
    }

    public void victory(){
        background.setOpaque(false);
        background.repaint();
        lf.bgMusic.stopSound();
        this.turn = 9;
        switch (Enemy.getUserName()) {
            case "Bat" -> {
                text = "Bat flies away!";
            }
            case "Ghost" -> {
                text = "Ghost disappears!";
            }
            case "Fairy" -> {
                text = "Fairy is faints!";
            }
            case "Warrior" -> {
                text = "Warrior falls!";
            }
        }
    }

    public void enemyChoice(){
        switch (Enemy.getUserName()) {
            case "Bat" -> {
                text = "Bat swoops down at " + Player.getUserName() + "!";
            }
            case "Ghost" -> {
                text = "Ghost flies around " + Player.getUserName() + "!";
                turn = 0;
            }
            case "Fairy" -> {
                if(enemyID > 3) {
                    text = "Fairy closely observes " + Player.getUserName() + "!";
                    turn = 0;
                }
                else {
                    text = "Fairy casts ICE at " + Player.getUserName() + "!";
                }
                enemyID = (int) Math.ceil(Math.random()*5);
            }
            case "Warrior" -> {
                if(enemyID > 3) {
                    text = "Warrior stores power!";
                    Enemy.setAtk(Enemy.getAtk()+3);
                    turn = 0;
                }
                else if (enemyID > 2 && (!Objects.equals(Ally.getUserName(), "Peggy"))) {
                    text = "Warrior slashes their sword at " + Ally.getUserName() + "!";
                    Enemy.setAtk(9);
                    turn = 8;
                }
                else {
                    text = "Warrior slashes their sword at " + Player.getUserName() + "!";
                }
                enemyID = (int) Math.ceil(Math.random()*5);
            }
        }
    }

    public void actionPerformed(ActionEvent actionEventObject) {
        String command = actionEventObject.getActionCommand();
        if (turn == 3 && AllyName == null){
            turn = 6;
        }
        if (!Objects.equals(command, "")) {
            switch (turn) {
                case 0 -> {
                    if (Player.getHP() <= 0){
                        dispose();
                        this.lf.setFrame("gameOver");
                        return;
                    }
                    turn(Player.getUserName());
                    if (Objects.equals(command, ">")){
                        player.playSound();
                    }
                }
                case 1 -> {
                    move = command;
                    playerChoice(command, 1);
                }
                case 2 -> {
                    playerAction(command);
                }
                case 3 -> {
                    if (Enemy.getHP() <= 0) {
                        victory();
                    }
                    else
                        turn(Ally.getUserName());
                }
                case 4 -> {
                    allyChoice();
                }
                case 5 -> {
                    allyAction();
                }
                case 69 -> {
                    heal.playSound();
                    if (Player.getHP() + 10 > Player.getMaxHP()) {
                        text = Player.getUserName() + " maxes HP!";
                        Player.setHP(Player.getMaxHP());
                    } else {
                        text = Player.getUserName() + "'s health increases by 10! ";
                        Player.setHP(Player.getHP() + 10);
                    }
                    updateTopInfo();
                    turn = 5;
                }
                case 6 -> {
                    if (Enemy.getHP() <= 0) {
                        victory();
                    }
                    else
                        turn(Enemy.getUserName());
                }
                case 7 -> {
                    enemyChoice();
                }
                case 8 -> {
                    Player.setHP(Player.getHP() - (attacking(Enemy.getAtk(), Player.getUserName(), Player.getDef())));
                    if (Player.getHP() < 0)
                        Player.setHP(0);
                    if (Objects.equals(Enemy.getUserName(), "Warrior"))
                        Enemy.setAtk(9);
                    updateTopInfo();
                    turn = -1;
                }
                case 9 -> {
                    attacking(Enemy.getAtk(), AllyName, Player.getDef()-2);
                    turn = -1;
                }
                case 10 -> {
                    victory.playSound();
                    text = "Victory!";
                }
                //This is how the game ends
                case 11 -> {
                    dispose();
                    if (!(lf.getBoss()==0) &&!(lf.getBoss()==1)){
                        this.lf.setFrame("end");
                        return;
                    }
                    else {
                        lf.setPlayerHP(this.Player.getHP());
                        lf.setBoss(0);
                        this.lf.setFrame("game");
                        return;
                    }
                }
            }
            if ((Objects.equals(command, ">") && turn != 1) || (!Objects.equals(command, ">") && turn == 1)) {
                this.expressionTextField.setText(text);
                turn++;
            }
            if (special){
                turn++;
                special = false;
            }
        }
    }

//    public void drawImage(Graphics g){
//        //Graphics2D buffer = world.createGraphics();
//        Enemy t1 = new Enemy(0, 0, Resources.getSprite("punk"));
//        //g.drawImage(Resources.getSprite("punk"), 0, 0, 0, 0, null);
//    }
}
