package fr.ideria.nooblib.discord;

import fr.ideria.nooblib.data.json.Json;
import fr.ideria.nooblib.discord.command.Command;
import fr.ideria.nooblib.discord.command.CommandListener;
import fr.ideria.nooblib.discord.command.CommandManager;
import fr.ideria.nooblib.discord.slashcommand.SlashCommand;
import fr.ideria.nooblib.discord.slashcommand.SlashCommandListener;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.entities.Activity.ActivityType;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import net.dv8tion.jda.api.requests.GatewayIntent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

public class Bot {
    /**
     * Shard manager
     */
    private final JDA jda;
    private final CommandManager commandManager;
    private String id;
    private final HashMap<String, SlashCommand> commands;

    /**
     * Constructor
     * @param json Configuration file of the bot
     */
    public Bot(Json json, GatewayIntent intent, GatewayIntent... otherIntents){
        JDABuilder builder = JDABuilder.createDefault(json.getString("token"), intent, otherIntents);

        builder.setActivity(Activity.of(ActivityType.valueOf(json.getString("activity.type")), json.getString("activity.content")));
        builder.setStatus(OnlineStatus.valueOf(json.getString("status")));

        this.jda = builder.build();
        this.commandManager = new CommandManager();
        commands = new HashMap<>();

        registerEventListener(new CommandListener(this, json));
        registerEventListener(new SlashCommandListener(this));
    }

    /**
     * Get a guild where the bot is in by its id
     * @param guildId The id of the guild
     * @return The specified guild
     */
    public Guild getGuildById(String guildId){
        Guild guild = jda.getGuildById(guildId);
        assert guild != null;

        return guild;
    }

    /**
     * Get a channel from a server id and the channel id
     * @param guildId The id of the guild
     * @param channelId The id of the channel
     * @return The specified channel
     */
    public TextChannel getTextChannelById(String guildId, String channelId){
        Guild guild = getGuildById(guildId);
        TextChannel channel = guild.getTextChannelById(channelId);
        assert channel != null;

        return channel;
    }

    /**
     * To send a message in a specified guild and channel
     * @param guildId The id of the guild
     * @param channelId The id of the channel
     * @param content The content of the message
     */
    public void sendTextMessage(String guildId, String channelId, String content){
        getTextChannelById(guildId, channelId).sendMessage(content).queue();
    }

    /**
     * To send a message in a specified guild and channel
     * @param guildId The id of the guild
     * @param channelId The id of the channel
     * @param embed The content of the message
     */
    public void sendEmbedMessage(String guildId, String channelId, MessageEmbed embed){
        getTextChannelById(guildId, channelId).sendMessageEmbeds(embed).queue();
    }

    public void sendTemporalTextMessage(TextChannel channel, String content, int delay, TimeUnit unit){
        channel.sendMessage(content).queue(message -> message.delete().queueAfter(delay, unit));
    }

    public void sendTemporalEmbedMessage(TextChannel channel, MessageEmbed embed, int delay, TimeUnit unit){
        channel.sendMessageEmbeds(embed).queue(message -> message.delete().queueAfter(delay, unit));
    }

    /**
     * To reply a temporal text message that will be deleted after a certain time and that will delete the
     * message that he's replying to after the same period
     * @param message The message to reply
     * @param content The content of the message that will reply
     * @param delay The delay before deleting the messages
     * @param unit The time unit of the delay
     */
    public void replyTemporalTextMessage(Message message, String content, int delay, TimeUnit unit){
        message.delete().queueAfter(delay, unit);
        sendTemporalTextMessage(message.getChannel().asTextChannel(), content, delay, unit);
    }

    /**
     * To reply a temporal text message that will be deleted after a certain time and that will delete the
     * message that he's replying to after the same period
     * @param message The message to reply
     * @param embed The embed that will reply
     * @param delay The delay before deleting the messages
     * @param unit The time unit of the delay
     */
    public void replyTemporalEmbedMessage(Message message, MessageEmbed embed, int delay, TimeUnit unit){
        message.delete().queueAfter(delay, unit);
        sendTemporalEmbedMessage(message.getChannel().asTextChannel(), embed, delay, unit);
    }

    /**
     * To register a listener
     * @param listener The listener that will be registered
     */
    public void registerEventListener(ListenerAdapter listener){
        jda.addEventListener(listener);
    }

    /**
     * To register a new command
     * @param command The command to register
     */
    public void registerCommand(Command command){
        commandManager.registerCommand(command);
    }

    /**
     * To get a command by its name
     * @param name The name of the command
     * @return The specified command
     */
    public Optional<Command> getCommandByName(String name){
        return commandManager.getCommandByName(name);
    }

    public Member asMember(Guild guild){
        return guild.getMemberById(id);
    }

    public void registerSlashCommand(CommandData commandData){
        jda.updateCommands().addCommands(commandData).queue();
    }
    public List<Guild> getGuilds(){ return jda.getGuilds(); }
    public void registerSlashCommand(SlashCommand command){
        commands.put(command.getId(), command);
    }

    public void queueSlashCommands(){
        List<CommandData> datas = new ArrayList<>();

        commands.keySet().forEach(commandId -> {
            SlashCommand command = commands.get(commandId);

            SlashCommandData data = Commands.slash(command.getId(), command.getDescription())
                    .addOptions(command.getOptions());

            datas.add(data);
        });

        jda.updateCommands().addCommands(datas).queue();
    }

    public HashMap<String, SlashCommand> getCommands(){ return commands; }
    public void setId(String id){
        this.id = id;
    }
}