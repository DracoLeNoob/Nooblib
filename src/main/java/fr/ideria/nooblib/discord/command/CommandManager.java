package fr.ideria.nooblib.discord.command;

import fr.ideria.nooblib.log.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * The object that contains all the commands registered on the bot
 */
public class CommandManager {
    /**
     * The list of the commands
     */
    private final List<Command> commands;

    /**
     * Constructor
     */
    public CommandManager() {
        this.commands = new ArrayList<>();
    }

    /**
     * To register a new command
     * @param command The command that will be registered
     */
    public void registerCommand(Command command){
        if(getCommandByName(command.getName()).isEmpty())
            commands.add(command);
        else
            Log.print("Command " + command.getName() + " is already registered...");
    }

    /**
     * To get a command by its name
     * @param name The name of the command
     * @return The specified command
     */
    public Optional<Command> getCommandByName(String name){
        for(Command command : commands.toArray(new Command[0]))
            if(command.getName().equalsIgnoreCase(name))
                return Optional.of(command);

        return Optional.empty();
    }
}