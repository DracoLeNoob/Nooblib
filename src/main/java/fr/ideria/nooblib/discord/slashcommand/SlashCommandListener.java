package fr.ideria.nooblib.discord.slashcommand;

import fr.ideria.nooblib.discord.Bot;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class SlashCommandListener extends ListenerAdapter {
    private final Bot bot;

    public SlashCommandListener(Bot bot) {
        this.bot = bot;
    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        SlashCommand command = bot.getCommands().getOrDefault(event.getName(), null);

        if(command != null)
            command.execute(event);
        else
            System.err.println("null command");
    }
}