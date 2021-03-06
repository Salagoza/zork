package io.muzoo.ssc.zork;

public class Room {
    private String description;
    private Room northExit;
    private Room southExit;
    private Room eastExit;
    private Room westExit;
    private Monster mon;
    private Item item;


    public Room(String description) {
        this.description = description;
    }

    public void setMonster(Monster mon){ this.mon = mon; }
    public void setItem(Item item) {
        this.item = item;
    }

    public void setExits(Room north, Room east, Room south, Room west) {
        if (north != null) {
            northExit = north;
        }
        if (east != null) {
            eastExit = east;
        }
        if (south != null) {
            southExit = south;
        }
        if (west != null) {
            westExit = west;
        }
    }
    public void setExit(String direction, Room neighbor) {
        switch (direction) {
            case "north": {
                northExit = neighbor;
                break;
            }
            case "east": {
                eastExit = neighbor;
                break;
            }
            case "south": {
                southExit = neighbor;
                break;
            }
            case "west": {
                westExit = neighbor;
                break;
            }
        }
    }

    public String getDescription() {
        return description;
    }
    public Room getNorthExit() {
        return northExit;
    }
    public Room getSouthExit() {
        return southExit;
    }
    public Room getEastExit(){
        return eastExit;
    }
    public Room getWestExit(){
        return westExit;
    }
    public Monster getMonster(){ return mon;}
    public Item getItem(){
        return item;
    }

}

