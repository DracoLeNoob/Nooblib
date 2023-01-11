package fr.ideria.nooblib.discord.command;

import fr.ideria.nooblib.discord.Bot;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;

/**
 * The code that is run when a command is called
 */
public interface CommandExecutor {
    CommandIssue execute(Bot bot, Guild guild, TextChannel channel, Message message, Member author, String[] args);
}