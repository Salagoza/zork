package io.muzoo.ssc.zork;

public class Player extends Character{
    public Player(String name, int hp, int maxHp, int atk) {
        super(name, hp, maxHp, atk);
    }
    public void increaseAtk(){
        this.atk++;
    }
    public void increaseHp(){
        this.hp++;
    }
}
