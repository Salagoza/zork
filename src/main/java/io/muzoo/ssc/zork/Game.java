package io.muzoo.ssc.zork;

import io.muzoo.ssc.zork.command.Command;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
public class Game {

    private GameOutput output = new GameOutput();
    private CommandParser commandParser = new CommandParser();
    private Room currentRoom;
    private ArrayList<Room> allRooms;
    private int gameState = 0;
    private Player p1;


    public void run(){
        printWelcome();
        while (true){
            Scanner in = new Scanner(System.in);
            String s = in.nextLine();
            List<String> words = commandParser.parse(s);
            Command command = CommandFactory.get(words.get(0));
            if (command != null){
                command.execute(this,words.subList(1,words.size()));
            }
        }
    }

    public GameOutput getOutput(){
        return output;
    }
    private void printWelcome() {
        output.println("Welcome to Zork");
        output.println("Type 'help' if you need help.");
    }

    public void exit() {
        output.println("Exiting");
        System.exit(0);
    }

    public void help(){
        List<String> allCmd = CommandFactory.getAllCommands();
        for(String cmd : allCmd){
            output.println(cmd);
        }
    }

    public void play(List<String> mapname) {
        if(gameState!=0){
            output.println("You are currently in the Game. Can't load map");
            return;
        }
        if(mapname.get(0).equals("map#1")){
            Room r1 = new Room("Entrance Hall");
            Room r2 = new Room("Room #1");
            allRooms = new ArrayList<>();
            allRooms.add(r1);
            allRooms.add(r2);
            r1.setExits(null,null,r2,null);
            r2.setExits(r1,null,null,null);
            currentRoom = r1;
            gameState = 1;
            p1 = new Player("P1",20,20,5);
            Monster m1 = new Monster("Golem",20,20,5);
            r2.setMonster(m1);
            Item i1 = new Item("Sword",2);
            r1.setItem(i1);
            output.println("Playing in "+mapname.get(0));
            info();
        }else{
            output.println("Can't load map");
        }
    }
    public void info() {
        output.println("Player:"+ p1.getInfo());
        printRoom();
    }

    private void printRoom() {
        output.println("You are in " + currentRoom.getDescription());
        if(currentRoom.getItem()!= null){
            output.println("Item:"+currentRoom.getItem().getName()+" ATK:"+currentRoom.getItem().getAtk());
        }
        if(currentRoom.getMonster()!= null){
            output.println("Monster:"+currentRoom.getMonster().getInfo());
        }
        output.print("Doors: ");
        if (currentRoom.getNorthExit() != null) {
            output.print("north ");
        }
        if (currentRoom.getEastExit() != null) {
            output.print("east ");
        }
        if (currentRoom.getSouthExit() != null) {
            output.print("south ");
        }
        if (currentRoom.getWestExit() != null) {
            output.print("west ");
        }
        output.println("");
    }

    public void go(List<String> args) {
        if(gameState!=1){
            output.println("You are not currently in the Game.");
            return;
        }
        String direction = args.get(0);
        // Try to leave current room.
        Room nextRoom = null;
        if (direction.equals("north")) {
            nextRoom = currentRoom.getNorthExit();
        }
        if (direction.equals("east")) {
            nextRoom = currentRoom.getEastExit();
        }
        if (direction.equals("south")) {
            nextRoom = currentRoom.getSouthExit();
        }
        if (direction.equals("west")) {
            nextRoom = currentRoom.getWestExit();
        }
        if (nextRoom == null) {
            System.out.println("Can't go");
        }
        else {
            currentRoom = nextRoom;
            p1.increaseHp();
            info();
        }
    }

    public void quit() {
        if(gameState!=1){
            output.println("You are not currently in the Game.");
            return;
        }else{
            gameState = 0;
            printWelcome();
        }
    }
    public void attackWith() {
        if(gameState!=1){
            output.println("You are not currently in the Game.");
            return;
        }else{
            if(currentRoom.getMonster()==null){
                output.println("Can't attack.");
            }else{
                output.println("ATTACKING !!");
                p1.attack(currentRoom.getMonster());
                if(currentRoom.getMonster().getHp()<=0){
                    p1.increaseAtk();
                    currentRoom.setMonster(null);
                    if(checkDefeatAllMonster()){
                        output.println("You win");
                        quit();
                        return;
                    }
                }else{
                    currentRoom.getMonster().attack(p1);
                    if(p1.getHp()<=0){
                        output.println("You lose");
                        quit();
                        return;
                    }
                }
                info();
            }
        }
    }

    private boolean checkDefeatAllMonster() {
        for( Room room : allRooms){
            if(room.getMonster()!= null){
                return false;
            }
        }
        return true;
    }

    public void take() {
        if(gameState!=1){
            output.println("You are not currently in the Game.");
            return;
        }else{
            if(currentRoom.getItem()==null || p1.getItem()!=null){
                output.println("Can't take.");
            }else{
                output.println("Take item");
                p1.setItem(currentRoom.getItem());
                currentRoom.setItem(null);
                info();
            }
        }
    }

    public void drop() {
        if(gameState!=1){
            output.println("You are not currently in the Game.");
            return;
        }else{
            if(currentRoom.getItem()!=null || p1.getItem()==null){
                output.println("Can't drop.");
            }else{
                output.println("Drop item");
                currentRoom.setItem(p1.getItem());
                p1.setItem(null);
                info();
            }
        }
    }
}
