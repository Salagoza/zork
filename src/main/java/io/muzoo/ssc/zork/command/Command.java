package io.muzoo.ssc.zork.command;

import io.muzoo.ssc.zork.Game;

import java.util.List;

public interface Command {
    int numArgs();
    void execute(Game game, List<String> arguments);
    String getCommand();
}
