package io.muzoo.ssc.zork.command;

import io.muzoo.ssc.zork.Game;

import java.util.List;

public class DropCommand implements Command{
    @Override
    public int numArgs() {
        return 0;
    }

    @Override
    public void execute(Game game, List<String> arguments) {
        game.drop();
    }

    @Override
    public String getCommand() {
        return "drop";
    }
}
