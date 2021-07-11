package io.muzoo.ssc.zork;

public class Player extends Character{
    private Item item;
    public Player(String name, int hp, int maxHp, int atk) {
        super(name, hp, maxHp, atk);
    }
    public void increaseAtk(){
        atk++;
    }
    public void increaseHp(){
        hp++;
        if(hp>maxHp){
            hp = maxHp;
        }
    }
    public void setItem(Item item){
        this.item = item;
    }
    public Item getItem(){
        return item;
    }
    public String getInfo(){
        String info = name+" HP:"+hp+"/"+maxHp+" ATK:"+atk;
        if(item!=null){
            info += " Item:"+item.getName()+" ATK:"+item.getAtk();
        }
        return info;
    }
    public void attack(Character c) {
        if(item!=null){
            c.decreaseHp(atk+item.getAtk());
        }else{
            c.decreaseHp(atk);
        }

    }
}
