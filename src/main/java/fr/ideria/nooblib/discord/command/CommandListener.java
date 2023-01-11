package fr.ideria.nooblib.discord.command;

import fr.ideria.nooblib.Nooblib;
import fr.ideria.nooblib.data.json.Json;
import fr.ideria.nooblib.discord.Bot;
import fr.ideria.nooblib.discord.command.Command;
import fr.ideria.nooblib.discord.command.CommandIssue;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.MessageEmbed.Field;
import net.dv8tion.jda.api.entities.channel.ChannelType;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * The class that listen all the messages that the bot can read to know if a command is type
 */
public class CommandListener extends ListenerAdapter {
    /**
     * The bot
     */
    private final Bot bot;
    /**
     * The json configuration
     */
    private final Json config;

    /**
     * Constructor
     * @param bot The bot
     * @param config The json configuration
     */
    public CommandListener(Bot bot, Json config) {
        this.bot = bot;
        this.config = config;
    }

    /**
     * Method called when the bot receive a message
     * @param event The event that contains all the information about the message sent
     */
    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        // Ignore the private messages
        if(event.isFromType(ChannelType.PRIVATE)) return;

        // Get the message author as a member and ignore the message if the author is a bot
        Member author = event.getMember();
        if(author == null || author.getUser().isBot()) return;

        // Analyse the message and get the command name and the arguments, ignore the message if it is not a command
        Message message = event.getMessage();
        String content = message.getContentRaw();
        String prefix = config.getString("command.prefix");
        if(!content.startsWith(prefix) || content.length() == prefix.length()) return;

        content = content.substring(1);

        while(content.contains("  "))
            content = content.replace("  ", " ");

        String[] split = content.split(" ");
        String commandName = split[0];
        String[] args = new String[split.length - 1];
        System.arraycopy(split, 1, args, 0, args.length);

        // Get the guild and the channel where the message had been sent
        Guild guild = event.getGuild();
        TextChannel channel = event.getChannel().asTextChannel();

        bot.setId(event.getJDA().getSelfUser().getId());

        // Get the typed command and ignore if the command doesn't exist
        Optional<Command> optional = bot.getCommandByName(commandName);
        if(optional.isEmpty()) return;

        Command command = optional.get();

        // Don't execute the command if the author does not have the permission and inform him
        if(!author.hasPermission(command.getPermission())){
            MessageEmbed embed = Nooblib.createEmbed(config.getJson("command.issue.no_permission"));
            bot.replyTemporalEmbedMessage(message, embed, config.getInteger("temporal_message_duration"), TimeUnit.SECONDS);
        }

        // Get if the command had succeed or not
        CommandIssue issue = command.execute(bot, guild, channel, message, author, args);

        // Send message according to the success or the fail of the command
        switch(issue){
            case ERROR -> {
                MessageEmbed embed = Nooblib.createEmbed(config.getJson("command.issue.error"));
                bot.replyTemporalEmbedMessage(message, embed, config.getInteger("temporal_message_duration"), TimeUnit.SECONDS);
            } case WRONG_SYNTAX -> {
                MessageEmbed embed = Nooblib.createEmbed(config.getJson("command.issue.wrong_syntax"));
                bot.replyTemporalEmbedMessage(message, embed, config.getInteger("temporal_message_duration"), TimeUnit.SECONDS);
            } case SUCCESS -> {
                message.delete().queueAfter(config.getInteger("temporal_message_duration"), TimeUnit.SECONDS);
            }
        }
    }
}