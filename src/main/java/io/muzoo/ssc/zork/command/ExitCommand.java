package io.muzoo.ssc.zork.command;


import io.muzoo.ssc.zork.Game;

import java.util.List;

public class ExitCommand implements Command{

    @Override
    public int numArgs() {
        return 0;
    }

    @Override
    public void execute(Game game, List<String> args) {
        game.exit();
    }

    @Override
    public String getCommand() {
        return "exit";
    }
}