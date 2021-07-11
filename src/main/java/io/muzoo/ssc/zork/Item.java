package io.muzoo.ssc.zork;

public class Item {
    private String name;
    private int atk;
    public Item(String name,int atk){
        this.name = name;
        this.atk = atk;
    }
    public String getName(){
        return name;
    }
    public int getAtk(){
        return atk;
    }
}
