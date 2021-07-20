package io.muzoo.ssc.zork;

import io.muzoo.ssc.zork.command.Command;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
public class Game {

    private GameOutput output = new GameOutput();
    private CommandParser commandParser = new CommandParser();
    private Room currentRoom;
    private ArrayList<Room> allRooms;
    private int gameState = 0;
    private Player p1;
    private ArrayList<String> saveFile;

    public void run() {
        printWelcome();
        while (true) {
            Scanner scanner = new Scanner(System.in);
            String line = scanner.nextLine();
            if(gameState==1) saveFile.add(line);
            List<String> words = commandParser.parse(line);
            Command command = CommandFactory.get(words.get(0));
            if (command != null) {
                System.out.println("++++++++++++++++++++++++");
                command.execute(this, words.subList(1, words.size()));
                System.out.println("++++++++++++++++++++++++");
            }
        }
    }

    public GameOutput getOutput() {
        return output;
    }

    private void printWelcome() {
        output.println("Welcome to Zork!!");
        output.println("Type 'help' if you need help.");
    }

    public void exit() {
        output.println("Exiting..");
        System.exit(0);
    }

    public void help() {
        List<String> allCmd = CommandFactory.getAllCommands();
        for (String cmd : allCmd) {
            output.println(cmd);
        }
    }

    public void play(List<String> mapname) {
        if(gameState!=0){
            output.println("You are in the Game. Can't load map!!");
            return;
        }
        if(gameState==1){
            output.println("You're currently in the game!!");
            return;
        }
        output.println("Loading.."+mapname.get(0));
        try {
            File file = new File(mapname.get(0) +".txt");
            Scanner scanner = new Scanner(file);
            int n = Integer.parseInt(scanner.nextLine());
            allRooms = new ArrayList<Room>();
            //Creating Rooms
            for(int i=0;i<n;i++){
                Room room = new Room(scanner.nextLine());
                allRooms.add(room);
            }
            //Set all the exits
            for(int i=0;i<n;i++){
                String line = scanner.nextLine();
                String[] exits = line.split(",");
                Room[] rooms = new Room[4];
                for(int j=0;j<rooms.length;j++){
                    if(exits[j].equals("null")){
                        rooms[j] = null;
                    }else{
                        rooms[j] = allRooms.get(Integer.parseInt(exits[j]));
                    }
                }
                allRooms.get(i).setExits(rooms[0],rooms[1],rooms[2],rooms[3]);
            }
            //Creating items
            n = Integer.parseInt(scanner.nextLine());
            for(int i=0;i<n;i++){
                String line = scanner.nextLine();
                String[] data = line.split(",");
                Item it = new Item(data[0],Integer.parseInt(data[1]));
                allRooms.get(Integer.parseInt(data[2])).setItem(it);
            }
            //Creating Monsters
            n = Integer.parseInt(scanner.nextLine());
            for(int i=0;i<n;i++){
                String line = scanner.nextLine();
                String[] data = line.split(",");
                Monster mon = new Monster(data[0],Integer.parseInt(data[1]),Integer.parseInt(data[2]),Integer.parseInt(data[3]));
                allRooms.get(Integer.parseInt(data[4])).setMonster(mon);
            }
            scanner.close();
            currentRoom = allRooms.get(0);
            p1 = new Player("P1",20,20,5);
            gameState = 1;
            output.println("Playing "+mapname.get(0));
            //Create Save File
            saveFile = new ArrayList<>();
            saveFile.add(mapname.get(0));
            info();
        }catch (FileNotFoundException e){
            output.println("Can't load map !!");
        }
    }

    public void info() {
        output.println("Player:" + p1.getInfo());
        printRoom();
    }

    private void printRoom() {
        output.println("You are in " + currentRoom.getDescription());
        if (currentRoom.getItem() != null) {
            output.println("Item:" + currentRoom.getItem().getName() + " ATK:" + currentRoom.getItem().getAtk());
        }
        if (currentRoom.getMonster() != null) {
            output.println("Monster:" + currentRoom.getMonster().getInfo());
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
        if (gameState != 1) {
            output.println("You are not currently in the Game!!");
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
        } else {
            currentRoom = nextRoom;
            p1.increaseHp();
            info();
        }
    }

    public void quit() {
        if (gameState != 1) {
            output.println("You are not currently in the Game!!");
            return;
        } else {
            gameState = 0;
            printWelcome();
        }
    }

    public void attackWith() {
        if (gameState != 1) {
            output.println("You are not currently in the Game!!");
            return;
        } else {
            if (currentRoom.getMonster() == null) {
                output.println("Can't attack.");
            } else {
                output.println("ATTACKING !!");
                p1.attack(currentRoom.getMonster());
                if (currentRoom.getMonster().getHp() <= 0) {
                    p1.increaseAtk();
                    currentRoom.setMonster(null);
                    if (checkDefeatAllMonster()) {
                        output.println("You win");
                        quit();
                        return;
                    }
                } else {
                    currentRoom.getMonster().attack(p1);
                    if (p1.getHp() <= 0) {
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
        for (Room room : allRooms) {
            if (room.getMonster() != null) {
                return false;
            }
        }
        return true;
    }

    public void take() {
        if (gameState != 1) {
            output.println("You are not currently in the Game!!");
            return;
        } else {
            if (currentRoom.getItem() == null || p1.getItem() != null) {
                output.println("Can't take.");
            } else {
                output.println("Take item");
                p1.setItem(currentRoom.getItem());
                currentRoom.setItem(null);
                info();
            }
        }
    }

    public void drop() {
        if (gameState != 1) {
            output.println("You are not currently in the Game.");
            return;
        } else {
            if (currentRoom.getItem() != null || p1.getItem() == null) {
                output.println("Can't drop.");
            } else {
                output.println("Drop item");
                currentRoom.setItem(p1.getItem());
                p1.setItem(null);
                info();
            }
        }
    }

    public void autopilot(List<String> filename) {
        if (gameState != 1) {
            output.println("You are not in the Game!!");
            return;
        } else {
            try {
                File file = new File(filename.get(0) + ".txt");
                Scanner scanner = new Scanner(file);
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    output.println(line);
                    List<String> words = commandParser.parse(line);
                    Command command = CommandFactory.get(words.get(0));
                    command.execute(this, words.subList(1, words.size()));
                }
                scanner.close();
            } catch (FileNotFoundException e) {
                output.println("Can't load file");
            }
        }
    }

    public void save(List<String> saveName) {
        if(gameState!=1){
            output.println("You are not in Game!!");
            return;
        }else{
            try {
                File file = new File(saveName.get(0)+".txt");
                FileWriter writer = new FileWriter(file);
                int i=0;
                while(i<saveFile.size()-1){
                    writer.write(saveFile.get(i)+"\n");
                    i++;
                }
                writer.close();
                output.println("Save success "+saveName.get(0));
            } catch (IOException e) {
                output.println("Can't save!!");
            }
        }
    }

    public void load(List<String> loadName) {
        if(gameState!=0){
            output.println("You are in the Game. Can't load !!");
            return;
        }else{
            try {
                File myObj = new File(loadName.get(0) +".txt");
                Scanner myReader = new Scanner(myObj);
                String line= myReader.nextLine();
                play(Collections.singletonList(line));
                while(myReader.hasNextLine()){
                    line = myReader.nextLine();
                    output.println(line);
                    List<String> words = commandParser.parse(line);
                    Command command = CommandFactory.get(words.get(0));
                    command.execute(this,words.subList(1,words.size()));
                }
                myReader.close();
                output.println("Load success "+loadName.get(0));
            }catch (FileNotFoundException e){
                output.println("Can't load save file");
            }
        }
    }
}
