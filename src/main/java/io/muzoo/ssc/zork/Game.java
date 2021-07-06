package io.muzoo.ssc.zork;

import io.muzoo.ssc.zork.command.Command;

import java.util.List;
import java.util.Scanner;
public class Game {

    private GameOutput output = new GameOutput();
    private CommandParser commandParser = new CommandParser();

    public void run(){
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

    public void exit() {
        output.println("Exit");
        System.exit(0);
    }
}
