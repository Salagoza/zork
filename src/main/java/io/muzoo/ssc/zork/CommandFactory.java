package io.muzoo.ssc.zork;

import io.muzoo.ssc.zork.command.*;

import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.stream.Collectors;

public class CommandFactory {
    private static final List<Class<? extends Command>> REGISTERED_COMMANDS = Arrays.asList(
            ExitCommand.class, HelpCommand.class, PlayCommand.class, InfoCommand.class, GoCommand.class
    );

    private static  final Map<String,Command> COMMAND_MAP = new HashMap<>();

    static {{
        for (Class<? extends Command> commandClass:REGISTERED_COMMANDS){
            try {
                Command command = commandClass.getDeclaredConstructor().newInstance();
                COMMAND_MAP.put(command.getCommand(),command);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
    }}

    public static Command get(String command){
        return COMMAND_MAP.get(command);
    }
    public static List<String> getAllCommands(){
        return COMMAND_MAP.keySet().stream().collect(Collectors.toList());
    }
}