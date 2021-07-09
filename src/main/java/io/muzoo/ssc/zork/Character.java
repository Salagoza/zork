package io.muzoo.ssc.zork;

public class Character {

    protected String name;
    protected int hp;
    protected int maxHp;
    protected int atk;

    public Character(String name, int hp, int maxHp, int atk){
        this.name = name;
        this.hp = hp;
        this.maxHp = maxHp;
        this.atk = atk;
    }
    public String getInfo(){
        String info = name +" " + "HP:" + hp +  "/"+ maxHp +" " + "ATK:" + atk;
        return info;
    }

    public void attack(Character c){
        c.decreaseHp(atk);
    }

    public void decreaseHp(int dhp){
        hp = hp-dhp;
    }

    public int getHp(){
        return hp;
    }

}
