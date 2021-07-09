package io.muzoo.ssc.zork;

public class Player {
    private String name;
    private int hp;
    private int maxHp;
    private int atk;

    public Player(String name, int hp, int maxHp,int atk){
        this.name = name;
        this.hp = hp;
        this.maxHp = maxHp;
        this.atk = atk;
    }
    public String getPlayerInfo(){
        String info = "Player:" + name +" " + "HP:" + hp +  "/"+ maxHp +" " + "ATK:" + atk;
        return info;
    }
}
