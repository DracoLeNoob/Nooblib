package fr.ideria.nooblib.discord.command;

import fr.ideria.nooblib.discord.Bot;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;

public class Command {
    /**
     * The name of the command
     */
    private final String name;
    /**
     * The description of the command
     */
    private final String description;
    /**
     * The correct syntax of the command
     */
    private final String syntax;
    /**
     * The permission needed to execute the command
     */
    private final Permission permission;
    /**
     * The executor of the command, the code that is run when the command is called by someone that have the permission
     */
    private final CommandExecutor executor;

    /**
     * Constructor
     * @param name The name of the command
     * @param description The description of the command
     * @param syntax The correct syntax of the command
     * @param permission The minimal permission required to use the command
     * @param executor The executor of the command
     */
    public Command(String name, String description, String syntax, Permission permission, CommandExecutor executor) {
        this.name = name;
        this.description = description;
        this.syntax = syntax;
        this.permission = permission;
        this.executor = executor;
    }

    /**
     * To execute the command
     * @param guild The guild where the command is called
     * @param channel The channel where the command is called
     * @param message The message that call the command
     * @param author The member that call the command
     * @param args The arguments of the command
     * @return The issue of the command
     */
    public CommandIssue execute(Bot bot, Guild guild, TextChannel channel, Message message, Member author, String[] args){
        return executor.execute(bot, guild, channel, message, author, args);
    }

    // Getters
    public String getName() { return name; }
    public String getDescription() { return description; }
    public String getSyntax() { return syntax; }
    public Permission getPermission() { return permission; }
    public CommandExecutor getExecutor() { return executor; }
}