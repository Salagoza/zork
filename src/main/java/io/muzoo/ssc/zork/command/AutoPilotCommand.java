package io.muzoo.ssc.zork.command;

import io.muzoo.ssc.zork.Game;

import java.util.List;

public class AutoPilotCommand implements Command{
    @Override
    public int numArgs() {
        return 1;
    }

    @Override
    public void execute(Game game, List<String> arguments) {
        game.autopilot(arguments);
    }

    @Override
    public String getCommand() {
        return "autopilot";
    }
}
