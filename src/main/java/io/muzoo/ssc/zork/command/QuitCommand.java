package io.muzoo.ssc.zork.command;

import io.muzoo.ssc.zork.Game;

import java.util.List;

public class QuitCommand implements Command{
    @Override
    public int numArgs() {
        return 0;
    }

    @Override
    public void execute(Game game, List<String> arguments) {
        game.quit();
    }

    @Override
    public String getCommand() {
        return "quit";
    }
}
