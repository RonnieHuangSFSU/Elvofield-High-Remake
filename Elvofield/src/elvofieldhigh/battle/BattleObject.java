package elvofieldhigh.battle;

import javax.swing.*;

public class BattleObject extends JFrame {
    private String Username;
    private int HP, Atk, Def, MaxHP;
    BattleObject(String name, int hp, int atk, int def, int maxHP) {
        this.Username = name;
        this.HP = hp;
        this.Atk = atk;
        this.Def = def;
        this.MaxHP = maxHP;
    }

    public String getUserName() {
        return Username;
    }

    public void setUserName(String name) {
        Username = name;
    }

    public int getHP(){
        return HP;
    }

    public void setHP(int HP) {
        this.HP = HP;
    }

    public int getAtk() {
        return Atk;
    }

    public void setAtk(int atk) {
        Atk = atk;
    }

    public int getDef() {
        return Def;
    }

    public void setDef(int def) {
        Def = def;
    }

    public int getMaxHP() {
        return MaxHP;
    }

    public void setMaxHP(int maxHP) {
        MaxHP = maxHP;
    }
}
