package io.muzoo.ssc.zork.command;

import io.muzoo.ssc.zork.Game;

import java.util.List;

public class SaveCommand implements Command{
    @Override
    public int numArgs() {
        return 1;
    }

    @Override
    public void execute(Game game, List<String> arguments) {
        game.save(arguments);
    }

    @Override
    public String getCommand() {
        return "save";
    }
}
